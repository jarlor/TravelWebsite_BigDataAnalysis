package A_DataCapture;

import Util.GetDocument;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 抓取携程旅游网的数据
 */
public class a_GetSourceData {
    public static Document getSourceData() throws IOException {
        String nextLine = "src/main/resources/www.ctrip.com.txt/";
        Document doc = null;
        return GetDocument.getDoc(nextLine);
    }
}
