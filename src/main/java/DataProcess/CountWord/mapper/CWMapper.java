package DataProcess.CountWord.mapper;

import com.vdurmont.emoji.EmojiParser;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apdplat.word.WordSegmenter;
import org.apdplat.word.segmentation.Word;

import java.io.IOException;
import java.util.List;

/**
 * @author GCJL
 * @date 2021/4/28 14:22
 */

public class CWMapper extends TableMapper<Text, IntWritable> {
    private static byte[] family = "comment_info".getBytes();
    private static byte[] column = "content".getBytes();

    @Override
    protected void map(ImmutableBytesWritable rowKey, Result result, Context context) throws IOException, InterruptedException {

        byte[] value = result.getValue(family, column);
        String word = new String(value,"utf-8");
        if(!word.isEmpty()){
            String filter = EmojiParser.removeAllEmojis(word);
            List<Word> segs = WordSegmenter.seg(filter);
            for(Word cont : segs) {
                Text text = new Text(cont.getText());
                IntWritable v = new IntWritable(1);
                context.write(text,v);
            }
        }
    }

}
