package DataVisualization;

import DataVisualization.Build_WordCloud.CommentWordCloud;
import com.kennycason.kumo.WordCloud;


/**
 * @author GCJL
 * @date 2021/5/7 17:05
 */
public class Run {
    public static void main(String[] args) throws Exception {
        WordCloud wordCloud = new CommentWordCloud().get();
    }
}
