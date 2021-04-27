package Util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.net.URI;

/**
 * @author GCJL
 * @date 2021/4/27 13:00
 */
public class Connected {
    static Configuration conf=null;


    static Connection connection=null;
    public static Connection getHbase() throws Exception{
        conf = HBaseConfiguration.create();
        //获取hbase连接对象
         connection= ConnectionFactory.createConnection(conf);
        return connection;
    }


    static FileSystem fs=null;
    public static FileSystem getHDFS() throws Exception {
         conf= new Configuration();
        fs = FileSystem.get(new URI("hdfs://hadoop1:9000"), conf, "root");
        return fs;
    }

}
