import Util.Connected;
import Util.HBaseUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;


public class test_HbaseUtil {
    public static void main(String[] args) throws Exception {


        List<String> user = HBaseUtil.scanTable("user");

        user.forEach(System.out::println);

        long user1 = HBaseUtil.rowCount("user");
        System.out.println(user1);

    }
}
