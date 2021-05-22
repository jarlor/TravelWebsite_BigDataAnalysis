package D_DataProcess.AveragePrice.reducer;


import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;
import java.io.IOException;

/**
 * @author GCJL
 * @date 2021/4/28 14:25
 */
public class ApReducer extends TableReducer<ImmutableBytesWritable, DoubleWritable, ImmutableBytesWritable> {
    @Override
    protected void reduce(ImmutableBytesWritable key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException {
        Put v = new Put(key.get());


        int count = 0;
        double sum = 0;

        for (DoubleWritable value : values) {
            sum += value.get();
            count++;
        }
        double average = sum / count;

        v.addColumn(Bytes.toBytes("info"), Bytes.toBytes("price"), Bytes.toBytes(String.valueOf(average)));

        context.write(key,v);

    }
}
