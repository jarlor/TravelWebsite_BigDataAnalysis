package Connected;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * @author GCJL
 * @date 2021/4/27 10:37
 */
public class Hbase {
    static Configuration hconf;
    static Connection connection;

    public Hbase() throws Exception {
        connection=getHbase();
    }

    public static Connection getHbase() throws Exception{
        hconf = HBaseConfiguration.create();
        //获取hbase连接对象
        connection = ConnectionFactory.createConnection(hconf);
        return connection;
    }


}
