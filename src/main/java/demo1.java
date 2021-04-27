import Connected.HDFS;
import Connected.Hbase;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;

public class demo1 {
    public static void main(String[] args) throws Exception {
        FileSystem hdfs = HDFS.getHDFS();

        Connection hbase = Hbase.getHbase();
        System.out.println(hbase);

        Admin admin = hbase.getAdmin();
        TableName tableName = TableName.valueOf("student");
        boolean b = admin.tableExists(tableName);
        System.out.println(b);


    }
}
