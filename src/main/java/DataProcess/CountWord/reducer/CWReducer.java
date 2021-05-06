package DataProcess.CountWord.reducer;


import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * @author GCJL
 * @date 2021/4/28 14:25
 */
public class CWReducer extends TableReducer<Text, IntWritable, ImmutableBytesWritable> {

    private static byte[] family =  "word_info".getBytes();
    private static byte[] column = "count".getBytes();

    @Override
    public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        Put put = new Put(Bytes.toBytes(key.toString()));
        put.addColumn(family,column,Bytes.toBytes(sum));
        context.write(null,put);
    }

}
