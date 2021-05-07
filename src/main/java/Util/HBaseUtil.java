package Util;

import java.io.*;
import java.util.*;

import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.*;

public class HBaseUtil {

    private static Connection conn;


    private static void startConn() {
        try {
            conn = Connected.getHbase();  //获取hbase连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void closeConn() {
        try {
            conn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//DDL

    /**
     * 创建表
     *
     * @param tableName   表名
     * @param columnNames 列族的动态数组
     * @throws Exception
     */
    public static void createTable(String tableName, String... columnNames) throws IOException {
        startConn();
        //获取表对象操作
        Admin admin = conn.getAdmin();
        TableName tableNameObj = TableName.valueOf(Bytes.toBytes(tableName));
        //判断表是否为空
        if (tableName != null && !tableName.isEmpty()) {

            if (!admin.tableExists(tableNameObj)) {
                HTableDescriptor hdr = new HTableDescriptor(tableNameObj);
                for (String columnName : columnNames) {
                    hdr.addFamily(new HColumnDescriptor(columnName));
                }
                admin.createTable(hdr);
            }
        }
        closeConn();
    }

    /**
     * 刪除表
     *
     * @param tableName 表名
     * @throws Exception
     */
    public static void deleteTable(String tableName) throws Exception {
        startConn();
        if (tableName != null && !tableName.isEmpty()) {
            Admin admin = conn.getAdmin();
            TableName tableNameObj = TableName.valueOf(Bytes.toBytes(tableName));
            admin.disableTable(tableNameObj);
            admin.deleteTable(tableNameObj);
        }
        closeConn();
    }

    /**
     * 列出所有表
     *
     * @return
     * @throws IOException
     */
    public static List<String> listTables() throws IOException {
        startConn();
        ArrayList<String> strings = new ArrayList<>();
        Admin admin = conn.getAdmin();

        TableName[] tableNames = admin.listTableNames();
        for (TableName tableName : tableNames) {
            strings.add(tableName.toString());
        }

        closeConn();
        return strings;

    }

//DML

    /**
     * 删除指定行键数据
     *
     * @param tablename
     * @param rowkey
     * @throws Exception
     */
    public static void delDataByRowkey(String tablename, String rowkey) throws Exception {
        startConn();
        Table table = conn.getTable(TableName.valueOf(tablename));
        Delete delete = new Delete(Bytes.toBytes(rowkey));
        table.delete(delete);
        table.close();
        closeConn();
    }

    /**
     * 根据结果集查询数据
     * @param sourceResults 源数据
     * @param family 列族
     * @param column 列名
     * @return
     * @throws IOException
     */
    public static Map<String,String> getDataByColumn(List<Result> sourceResults, String familyk, String columnk,String familyv, String columnv) throws IOException {
        startConn();
        Scan scan = new Scan();


        byte[] prek = null;
        String k = null;
        byte[] prev = null;
        String v = null;
        HashMap<String, String> soult = new HashMap<>();

        for (Result result : sourceResults) {
            prek = result.getValue(Bytes.toBytes(familyk), Bytes.toBytes(columnk));
            k = Bytes.toString(prek);

            prev = result.getValue(Bytes.toBytes(familyv), Bytes.toBytes(columnv));
            v = Bytes.toString(prev);
            soult.put(k,v);
        }
        closeConn();
        return soult;
    }

    /**
     *  查询指定城市的result和要查询的长度
     * @param cityName
     * @param tablename
     * @param dataLength
     * @return
     * @throws IOException
     */
    public static List<Result> getDatabyCName(String cityName, String tablename, int dataLength) throws IOException {
        startConn();
        Table table = conn.getTable(TableName.valueOf(tablename));
        Scan scan = new Scan();

        ResultScanner scanner = table.getScanner(scan);

        ArrayList<Result> value = new ArrayList<Result>();
        byte[] precityName=null;
        for (Result result : scanner) {
            precityName=result.getValue(Bytes.toBytes("cityInfo"), Bytes.toBytes("cityName"));
//            System.out.println(Bytes.toString(precityName));
            if ((dataLength>0)&&Bytes.toString(precityName).equals(cityName)) {
                dataLength--;
                value.add(result);
            }
        }
        closeConn();
    return value;
    }

    /**
     * 删除指定列
     *
     * @param tablename
     * @param rowkey
     * @param family
     * @param columns
     * @throws Exception
     */
    public static void delDataByColumuns(String tablename, String rowkey, String family, String... columns) throws Exception {
        startConn();
        Table table = conn.getTable(TableName.valueOf(tablename));
        Delete delete = new Delete(Bytes.toBytes(rowkey));

        for (String column : columns) {
            delete.addColumn(Bytes.toBytes(family), Bytes.toBytes(column));
        }

        table.delete(delete);
        table.close();
        closeConn();
    }

    /**
     * 通过rowkey删除数据
     *
     * @param tablename
     * @param rowkey
     */
    public static void delDataByRowKey(String tablename, String rowkey) throws Exception {
        startConn();

        Table table = conn.getTable(TableName.valueOf(tablename));

        Delete delete = new Delete(Bytes.toBytes(rowkey));

        table.delete(delete);
        closeConn();
    }

    /**
     * 往指定表添加数据
     *
     * @param tablename 表名
     * @param puts      需要添加的数据
     * @return long 返回执行时间
     * @throws IOException
     */
    public static long putDataByTable(String tablename, List<Put> puts) throws Exception {
        startConn();
        long currentTime = System.currentTimeMillis();

        Table table = conn.getTable(TableName.valueOf(Bytes.toBytes(tablename)));
        try {
            table.put(puts);
        } finally {
            table.close();
            closeConn();
        }

        return System.currentTimeMillis() - currentTime;  //返回插入数据花费的时间(毫秒)
    }


    /**
     * 扫描表
     *
     * @param tName
     * @throws Exception
     */
    public static List<String> scanTable(String tName) throws Exception {
        startConn();
        Table table = conn.getTable(TableName.valueOf(tName));
        ArrayList<String> value = new ArrayList<>();

        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            List<Cell> cells = result.listCells();
            List<String> strings = printFormat(cells);
            value.addAll(strings);
        }
        table.close();
        closeConn();

        return value;
    }

    public static void clearTable(String tablename) throws IOException {
        startConn();
        Table table = conn.getTable(TableName.valueOf(tablename));

        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        Delete delete = null;
        for (Result result : scanner) {
            delete = new Delete(result.getRow());
            table.delete(delete);
        }

        closeConn();
    }

    /**
     * 根据行键获取数据
     *
     * @param tablename
     * @param rowkey
     * @return
     * @throws IOException
     */
    public static List<Cell> getDataByRowkey(String tablename, String rowkey) throws Exception {
        startConn();
        Table table = conn.getTable(TableName.valueOf(tablename));
        Get get = new Get(Bytes.toBytes(rowkey));
        Result result = table.get(get);

        List<Cell> cells = result.listCells();
        table.close();
        closeConn();
        return cells;
    }

    /**
     * 打印输出Cells
     *
     * @param cells
     * @return
     * @throws Exception
     */
    public static List<String> printFormat(List<Cell> cells) throws Exception {
        startConn();
        ArrayList<String> strings = new ArrayList<>();

        byte[] row = null;
        byte[] family = null;
        byte[] qualifier = null;
        byte[] value = null;

        String format = null;
        for (Cell cell : cells) {
            row = CellUtil.cloneRow(cell); //rowkey
            family = CellUtil.cloneFamily(cell);
            qualifier = CellUtil.cloneQualifier(cell);
            value = CellUtil.cloneValue(cell);

            format = String.format("rowkey:%s\tcolumnFamily:%s\tcolumn:%s\tvalue:%s"
                    , Bytes.toString(row), Bytes.toString(family), Bytes.toString(qualifier), Bytes.toString(value));

            strings.add(format);
        }
        closeConn();
        return strings;
    }


    /**
     * 行个数
     *
     * @param tName
     * @return
     */
    public static long getRowCount(String tName) throws Exception {
        startConn();
        long rowCount = 0;
        try {
            TableName tableName = TableName.valueOf(Bytes.toBytes(tName));
            Table table = conn.getTable(tableName);
            Scan scan = new Scan();
            ResultScanner resultScanner = table.getScanner(scan);
            for (Result result : resultScanner) {
                rowCount += result.size();
            }
            System.out.println("rowCount-->" + rowCount);
        } catch (IOException e) {
        } finally {
            closeConn();
        }
        return rowCount;
    }

}