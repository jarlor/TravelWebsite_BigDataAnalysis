package DataVisualization.Build_WordCloud;

import Util.Connected;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import com.kennycason.kumo.wordstart.CenterWordStart;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author GCJL
 * @date 2021/5/7 17:20
 */
public class CommentWordCloud {
    public WordCloud get() throws Exception {

        Connection conn = Connected.getHbase();


        //1.读取Hbase表中数据显示
        TableName tableName = TableName.valueOf(Bytes.toBytes("CountWord"));
        Table table = conn.getTable(tableName);
        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);
        List<WordFrequency> words = new ArrayList<>();
        for (Result result : scanner) {
            String word = new String(result.getRow(), "utf-8");
            byte[] value = result.getValue("word_info".getBytes(), "count".getBytes());
            int count = Integer.parseInt(Bytes.toString(value));
            WordFrequency wordFrequency = new WordFrequency(word, count);
            if (count > 10) {
                words.add(wordFrequency);
            }
        }
        //2.生成并渲染图片
        Dimension dimension = new Dimension(500, 312);
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);
        Font font = new Font("宋体", 2, 24);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        wordCloud.setBackgroundColor(Color.WHITE);
        wordCloud.setBackground(new PixelBoundryBackground("src/main/resources/SourceImgs/whale_small.png"));
        wordCloud.setWordStartStrategy(new CenterWordStart());
        wordCloud.setAngleGenerator(new AngleGenerator(0));
        wordCloud.build(words);
        wordCloud.writeToFile("src/main/resources/TargetImgs/wordcloud_comment.png");
        return wordCloud;
    }
}
