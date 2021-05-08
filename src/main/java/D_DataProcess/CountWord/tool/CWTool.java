package D_DataProcess.CountWord.tool;

import D_DataProcess.CountWord.mapper.CWMapper;
import D_DataProcess.CountWord.reducer.CWReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;

/**
 * @author GCJL
 * @date 2021/4/28 14:19
 */
public class CWTool implements Tool {
    public int run(String[] args) throws Exception {

        //获取job实例
        Job job = Job.getInstance();
        job.setJarByClass(CWTool.class);

        //mapper
        TableMapReduceUtil.initTableMapperJob(
                "t_hotel_comment",
                new Scan(),
                CWMapper.class,

                Text.class,
                IntWritable.class,
                job
        );

        //reducer
        TableMapReduceUtil.initTableReducerJob(
                "CountWord",
                CWReducer.class,
                job
        );

        //execute
        boolean status = job.waitForCompletion(true);
        return status ? JobStatus.State.SUCCEEDED.getValue() : JobStatus.State.FAILED.getValue();
    }

    public void setConf(Configuration conf) {

    }

    public Configuration getConf() {
        return null;
    }
}
