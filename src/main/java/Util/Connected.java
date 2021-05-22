package Util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

/**
 * @author GCJL
 * @date 2021/4/27 13:00
 */
public class Connected {
    static Configuration conf = null;

    /**
     *  读取配置文件的方法
     * @param k
     * @return
     * @throws Exception
     */
    private static String readProperties(String k) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("src/main/resources/hadoop.properties");
        properties.load(inputStream);
        return properties.getProperty(k);
    }

    static Connection connection = null;

    public static Connection getHbase() throws Exception {
        conf = HBaseConfiguration.create();

        String quorum = readProperties("HBASE.ZOOKEEPER");
        String port = readProperties("zookeeper.port");
        String masterWithP = String.format("%s:%s",readProperties("master"),port);
        String slave1WithP = String.format("%s:%s",readProperties("slave1"),port);
        String slave2WithP = String.format("%s:%s",readProperties("slave2"),port);

        conf.set(quorum, String.format("%s,%s,%s",masterWithP,slave1WithP,slave2WithP));
        //获取hbase连接对象
        connection = ConnectionFactory.createConnection(conf);
        return connection;
    }


    static FileSystem fs = null;

    public static FileSystem getHDFS() throws Exception {
        String hdfs = String.valueOf(readProperties("HDFS"));
        URI uri = new URI(hdfs);
        conf = new Configuration();
        fs = FileSystem.get(uri, conf, "root");
        return fs;
    }

}
