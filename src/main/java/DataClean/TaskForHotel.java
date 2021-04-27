package DataClean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Util.GetDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

public class TaskForHotel {

    /**
     * 获取清理后的信息
     *
     * @param doc
     * @return
     */
    public static List<String> cleanHTML(Document doc) {
        /**********   Begin   **********/
        ArrayList<String> list = new ArrayList<>();

        String clean_bas = Jsoup.clean(doc.toString(), Whitelist.basic());
        String clean_sim = Jsoup.clean(doc.toString(), Whitelist.simpleText());

        list.add(clean_bas);
        list.add(clean_sim);

        return list;
        /**********   End   **********/
    }

    public static void main(String[] args) throws IOException {

        String filePath = "src/main/resources/hotels.ctrip.com_domestic-city-hotel.txt";
        //通过filePath文件路径获取Docment对象
        Document doc = GetDocument.getDoc(filePath);
        List<String> cleanHTML = TaskForHotel.cleanHTML(doc);
        System.out.println("第一组数据长度："+cleanHTML.get(0).length()+",第二组数据长度："+cleanHTML.get(1).length());
        System.out.println("第一组数据：\n"+cleanHTML.get(0).substring(0, 830));
        System.out.println("第二组数据：\n"+cleanHTML.get(1).substring(0, 200));
    }

}
