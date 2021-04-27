package Connected;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.net.URI;

/**
 * @author GCJL
 * @date 2021/4/27 10:14
 */
public class HDFS {
    static Configuration conf = null;
    static FileSystem fs = null;

    public HDFS() throws Exception {
        fs=getHDFS();
    }
    public HDFS(URI url,String username) throws Exception {
        conf = new Configuration();
        fs = FileSystem.get(url, conf, username);
    }

    public static FileSystem getHDFS() throws Exception {
        conf = new Configuration();
        fs = FileSystem.get(new URI("hdfs://hadoop1:9000"), conf, "root");
        return fs;
    }


}
