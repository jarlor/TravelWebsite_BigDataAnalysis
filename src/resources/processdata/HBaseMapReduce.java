package resources.processdata;

import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.util.HBaseUtil;

/**
 * 使用MapReduce程序处理HBase中的数据并将最终结果存入到另一张表 1中
 */
public class HBaseMapReduce extends Configured implements Tool {

    public static class MyMapper extends TableMapper<Text, DoubleWritable> {
        public static final byte[] column = "price".getBytes();
        public static final byte[] family = "hotel_info".getBytes();

        @Override
        protected void map(ImmutableBytesWritable rowKey, Result result, Context context)
                throws IOException, InterruptedException {
            /********** Begin *********/


            /********** End *********/
        }
    }

    public static class MyTableReducer extends TableReducer<Text, DoubleWritable, ImmutableBytesWritable> {
        @Override
        public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
            /********** Begin *********/

            /********** End *********/
        }

    }






    public int run(String[] args) throws Exception {
        //配置Job
        Configuration conf = HBaseConfiguration.create(getConf());
        conf.set("hbase.zookeeper.quorum", "127.0.0.1");  //hbase 服务地址
        conf.set("hbase.zookeeper.property.clientPort", "2181"); //端口号
        Scanner sc = new Scanner(System.in);
        String arg1 = sc.next();
        String arg2 = sc.next();
        //String arg1 = "t_city_hotels_info";
        //String arg2 = "average_table";
        try {
            HBaseUtil.createTable("average_table", new String[] {"average_infos"});
        } catch (Exception e) {
            // 创建表失败
            e.printStackTrace();
        }
        Job job = configureJob(conf,new String[]{arg1,arg2});
        return job.waitForCompletion(true) ? 0 : 1;
    }

    private Job configureJob(Configuration conf, String[] args) throws IOException {
        String tablename = args[0];
        String targetTable = args[1];
        Job job = new Job(conf,tablename);
        Scan scan = new Scan();
        scan.setCaching(300);
        scan.setCacheBlocks(false);//在mapreduce程序中千万不要设置允许缓存
        //初始化Mapreduce程序
        TableMapReduceUtil.initTableMapperJob(tablename,scan,MyMapper.class, Text.class, DoubleWritable.class,job);
        //初始化Reduce
        TableMapReduceUtil.initTableReducerJob(
                targetTable,        // output table
                MyTableReducer.class,    // reducer class
                job);
        job.setNumReduceTasks(1);
        return job;
    }
}
