package D_DataProcess.AveragePrice.mapper;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.DoubleWritable;

import java.io.IOException;

/**
 * @author GCJL
 * @date 2021/4/28 14:22
 */
public class APMapper extends TableMapper<ImmutableBytesWritable, DoubleWritable> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {
        ImmutableBytesWritable k = new ImmutableBytesWritable();
        DoubleWritable v = new DoubleWritable();

        //get cityName
        byte[] cityName = value.getValue(Bytes.toBytes("cityInfo"), Bytes.toBytes("cityName"));
        k.set(cityName);

        //get Price

        byte[] byte_price = value.getValue(Bytes.toBytes("hotel_info"), Bytes.toBytes("price"));
        double doulble_price = Double.parseDouble(Bytes.toString(byte_price));
        v.set(doulble_price);

        context.write(k, v);

    }
}
