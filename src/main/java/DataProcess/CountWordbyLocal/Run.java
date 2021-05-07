package DataProcess.CountWordbyLocal;

import DataProcess.CountWordbyLocal.GetData.getData;
import DataProcess.CountWordbyLocal.WordParticiple.wordParticiple;
import Util.HBaseUtil;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author GCJL
 * @date 2021/5/7 15:21
 */
public class Run {
    public static void main(String[] args) throws Exception {

        Map<String, Integer> sourceData = wordParticiple.analyzeData(getData.getDataFromLocal());

        ArrayList<Put> puts = new ArrayList<>();

        Put put = null;
        for (String key : sourceData.keySet()) {
            put = new Put(key.getBytes());
            put.addColumn(Bytes.toBytes("word_info"),
                    Bytes.toBytes("count"),
                    Bytes.toBytes(String.valueOf(sourceData.get(key))));
            puts.add(put);
        }
        HBaseUtil.putDataByTable("CountWord", puts);
    }
}
