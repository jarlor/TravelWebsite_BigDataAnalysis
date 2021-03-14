package DataClean.step1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class Task {

    //通过filePath文件路径获取Docment对象
    public Document getDoc(String filePath) throws IOException {
        /**********   Begin   **********/
        Document doc = Jsoup.parse(new File(filePath), "utf-8", "http://www.educoder.com");
        return doc;
        /**********   End   **********/
    }

    /**
     * 获取清理后的信息
     *
     * @param doc
     * @return
     */
    public List<String> cleanHTML(Document doc) {
        /**********   Begin   **********/
        ArrayList<String> list = new ArrayList<>();

        String clean_bas = Jsoup.clean(doc.toString(), Whitelist.basic());
        String clean_sim = Jsoup.clean(doc.toString(), Whitelist.simpleText());

        list.add(clean_bas);
        list.add(clean_sim);

        return list;
        /**********   End   **********/
    }

}
