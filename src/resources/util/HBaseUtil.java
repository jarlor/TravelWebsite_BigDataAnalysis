package resources.util;

import java.io.*;
import java.util.*;
import java.util.Base64;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vdurmont.emoji.EmojiParser;

public class HBaseUtil {

    private static Configuration conf;
    private static Connection conn;

    private ThreadPoolUtil threadPool = ThreadPoolUtil.init(); // 初始化线程池
    private static final Logger logger = LoggerFactory.getLogger(HBaseUtil.class);

    static {
        // 静态代码块初始化config对象
        try {
            if (conf == null) {
                conf = HBaseConfiguration.create();
                //conf.set("hbase.zookeeper.quorum", "116.196.80.141"); // hbase 服务地址
                //conf.set("hbase.zookeeper.property.clientPort", "42181"); // 端口号
                conf.set("hbase.zookeeper.quorum", "127.0.0.1"); // hbase 服务地址
                conf.set("hbase.zookeeper.property.clientPort", "2181"); // 端口号
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建表
     *
     * @param tableName
     *            表名
     * @param columnNames
     *            列族的动态数组
     * @throws Exception
     */
    public static void createTable(String tableName, String... columnNames) throws Exception {
        if (tableName != null && !tableName.isEmpty()) {
            Admin admin = getConnection().getAdmin();
            TableName tableNameObj = TableName.valueOf(Bytes.toBytes(tableName));
            if (!admin.tableExists(tableNameObj)) {
                TableDescriptorBuilder builder = TableDescriptorBuilder.newBuilder(tableNameObj);
                for (String columnName : columnNames) {
                    ColumnFamilyDescriptorBuilder columnBuild = ColumnFamilyDescriptorBuilder
                            .newBuilder(Bytes.toBytes(columnName));
                    ColumnFamilyDescriptor family = columnBuild.build();
                    builder.setColumnFamily(family);
                }
                admin.createTable(builder.build());
            }
        }
    }

    /**
     * 刪除表
     *
     * @param tableName
     *            表名
     * @throws Exception
     */
    public static void deleteTable(String tableName) throws Exception {
        if (tableName != null && !tableName.isEmpty()) {
            Admin admin = getConnection().getAdmin();
            TableName tableNameObj = TableName.valueOf(Bytes.toBytes(tableName));
            admin.disableTable(tableNameObj);
            admin.deleteTable(tableNameObj);
        }
    }

    /**
     * 多线程同步提交
     *
     * @param tableName
     *            表名称
     * @param puts
     *            待提交参数
     * @param waiting
     *            是否等待线程执行完成 true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
     */
    public void batchPut(final String tableName, final List<Put> puts, boolean waiting) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    putByTable(tableName, puts);
                } catch (Exception e) {
                    logger.error("batchPut failed . ", e);
                }
            }
        });

        if (waiting) {
            try {
                threadPool.awaitTermination();
            } catch (InterruptedException e) {
                logger.error("HBase put job thread pool await termination time out.", e);
            }
        }
    }

    /**
     * 多线程异步提交
     *
     * @param tableName
     *            表名称
     * @param puts
     *            待提交参数
     * @param waiting
     *            是否等待线程执行完成 true 可以及时看到结果, false 让线程继续执行，并跳出此方法返回调用方主程序
     */
    public void batchAsyncPut(final String tableName, final List<Put> puts, boolean waiting) {
        Future f = threadPool.submit(new Runnable() {
            public void run() {
                try {
                    putAsy(tableName, puts);
                } catch (Exception e) {
                    System.out.println("异步提交异常");
                    e.printStackTrace();
                }
            }
        });

        if (waiting) {
            try {
                f.get();
            } catch (InterruptedException e) {
                System.out.println("多线程异步提交返回数据执行失败.");
            } catch (ExecutionException e) {
                System.out.println("多线程异步提交返回数据执行失败.");
            }
        }
    }

    /**
     * 往指定表添加数据
     *
     * @param tablename
     *            表名
     * @param puts
     *            需要添加的数据
     * @return long 返回执行时间
     * @throws IOException
     */
    public static long putByTable(String tablename, List<Put> puts) throws Exception {
        long currentTime = System.currentTimeMillis();
        Connection conn = getConnection();
        Table table = conn.getTable(TableName.valueOf(Bytes.toBytes(tablename)));
        try {
            table.put(puts);
        } finally {
            table.close();
            closeConnect(conn);
        }
        return System.currentTimeMillis() - currentTime;
    }

    /**
     * 异步往指定表添加数据
     *
     * @param tablename
     *            表名
     * @param puts
     *            需要添加的数据
     * @return long 返回执行时间
     * @throws IOException
     */
    public static long putAsy(String tablename, List<Put> puts) throws Exception {
        long currentTime = System.currentTimeMillis();
        Connection conn = getConnection();
        final BufferedMutator.ExceptionListener listener = new BufferedMutator.ExceptionListener() {
            @Override
            public void onException(RetriesExhaustedWithDetailsException e, BufferedMutator mutator) {
                for (int i = 0; i < e.getNumExceptions(); i++) {
                    System.out.println("Failed to sent put " + e.getRow(i) + ".");
                }
            }
        };
        BufferedMutatorParams params = new BufferedMutatorParams(TableName.valueOf(tablename)).listener(listener);
        params.writeBufferSize(5 * 1024 * 1024);

        final BufferedMutator mutator = conn.getBufferedMutator(params);
        try {
            mutator.mutate(puts);
            mutator.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mutator.close();
            closeConnect(conn);
        }
        return System.currentTimeMillis() - currentTime;
    }

    /**
     * 关闭连接对象
     *
     * @param conn
     */
    private static void closeConnect(Connection conn) {
        // 实现关闭连接对象功能
        if (null != conn) {
            try {
                conn.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 获取连接对象
     */
    public static Connection getConnection() {
        // 实现一个返回连接对象的函数
        try {
            if (conn == null || conn.isClosed()) {
                conn = ConnectionFactory.createConnection(conf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 扫描表
     *
     * @param tName
     * @throws Exception
     */
    public static void scanTable(String tName) throws Exception {
        Connection conn = getConnection();
        TableName tableName = TableName.valueOf(Bytes.toBytes(tName));
        Table table = conn.getTable(tableName);

        // Filter filter1 = new FamilyFilter(CompareOperator.EQUAL, new
        // BinaryComparator(Bytes.toBytes("school_info")));
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println("row:" + new String(result.getRow(), "utf-8"));
            for (Cell cell : result.listCells()) {
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                byte[] b = CellUtil.cloneValue(cell);
                String value = new String(b, "utf-8");
                System.out.println(family + ":" + qualifier + " " + value);
            }
        }
        closeConnect(conn);
    }

    /**
     * 行个数
     *
     * @param tName
     * @return
     */
    public static long rowCount(String tName) {
        long rowCount = 0;
        try {
            Connection conn = getConnection();
            TableName tableName = TableName.valueOf(Bytes.toBytes(tName));
            Table table = conn.getTable(tableName);
            Scan scan = new Scan();
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                rowCount += result.size();
            }
            System.out.println("rowCount-->" + rowCount);
        } catch (IOException e) {
        }
        return rowCount;
    }

    /**
     *
     * @param rowkeyList
     * @return
     * @throws IOException
     */
    public static void qurryTableInfo(String tName, String rowkey) throws IOException {
        try {
            Connection conn = getConnection();
            TableName tableName = TableName.valueOf(Bytes.toBytes(tName));
            Table table = conn.getTable(tableName);
            Get get = new Get(Bytes.toBytes(rowkey));
            Result result = table.get(get);
            for (Cell cell : result.listCells()) {
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                byte[] b = CellUtil.cloneValue(cell);
                String value = Bytes.toString(b);
                if (qualifier.equals("collectionTime") || qualifier.equals("isSingleRec")) {
                    continue;
                }
                if (qualifier.equals("content")) {
                    value = EmojiParser.removeAllEmojis(value);
                }
                System.out.println(family + ":" + qualifier + " " + value);
            }
        } catch (IOException e) {
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * 扫描表
     *
     * @param tName
     * @throws Exception
     */
    public static void scanCityTable(String tName) throws Exception {
        scanCityTable(tName,new Scan());

    }

    public static boolean isIn(String mStr, String[] strings) {
        for (String str : strings) {
            if (str.equals(mStr)) {
                return true;
            }
        }
        return false;
    }

    public static void scanCityTable(String tName, Scan scan)  throws Exception {

        Connection conn = getConnection();
        TableName tableName = TableName.valueOf(Bytes.toBytes(tName));
        Table table = conn.getTable(tableName);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            System.out.println("word:" + new String(result.getRow(), "utf-8"));
            for (Cell cell : result.listCells()) {
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                byte[] b = CellUtil.cloneValue(cell);
                String value = String.valueOf(Bytes.toInt(b));
                System.out.println(family + ":" + qualifier + " " +value );
            }
        }
        closeConnect(conn);


    }
}
