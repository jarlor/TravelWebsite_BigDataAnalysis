package DataVisualization;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class WordCloud_img {
    public WordCloud get() throws IOException {
        /**********     Begin   **********/
        //1.为实例化词云做准备
        FrequencyAnalyzer fre = new FrequencyAnalyzer();
        fre.setWordFrequenciesToReturn(200);

        List<WordFrequency> wordList = fre.load("wordcloud.txt");

        Dimension di = new Dimension(500,312);

        //2.修改词云的实例化
        WordCloud wordCloud = null;

        wordCloud = new WordCloud(di, CollisionMode.PIXEL_PERFECT);
        //3.生成词云并写入图片

        wordCloud.build(wordList);
        wordCloud.writeToFile("imgs/wordcloud_img.png");

        /**********     End   **********/
        return wordCloud;
    }
}
