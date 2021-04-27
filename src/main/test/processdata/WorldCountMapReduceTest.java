package com.processdata;

import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.ValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.util.ToolRunner;
import org.apdplat.word.WordSegmenter;
import com.savedata.SaveData;
import com.util.HBaseUtil;


public class WorldCountMapReduceTest {

    public static void main(String[] args) {

        try {
            SaveData.saveCommentInfo();
            //先进行分词初始化，避免map超时
            WordSegmenter.seg("test");
            int result = ToolRunner.run(HBaseConfiguration.create(), new WorldCountMapReduce(), args);
            ValueFilter rowFilter = new ValueFilter(CompareOperator.GREATER, new BinaryComparator(Bytes.toBytes(100)));
            Scan scan = new Scan();
            scan.setFilter(rowFilter);
            HBaseUtil.scanCityTable("comment_word_count", scan);
            HBaseUtil.deleteTable("comment_word_count");
            System.exit(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
