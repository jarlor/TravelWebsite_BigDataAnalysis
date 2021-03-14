package DataCapture.step3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Task {

    //通过filePath文件路径获取Docment对象
    public Document getDoc(String filePath) throws IOException {
        /**********   Begin   **********/
        Document document = Jsoup.parse(new File(filePath), "UTF-8","http://www.educoder.net/");
        return document;
        /**********   End   **********/
    }

    //获取所有链接
    public List<String> getLinks(Document doc) {
        /**********   Begin   **********/
        ArrayList<String> list = new ArrayList<>();
        Elements href = doc.select("a[href]");
        for (Element element : href) {
            list.add(element.tagName() + "$" + element.attr("abs:href") + "(" + element.text() + ")");
        }
        return list;
        /**********   End   **********/
    }

    //获取图片
    public List<String> getMedia(Document doc) {
        /**********   Begin   **********/
        List<String> list = new ArrayList<>();
        Elements src = doc.select("[src]");
        for (Element element : src) {
            if (element.tagName().equals("img")) {
                list.add(element.tagName() + "$" + element.attr("abs:src"));
            }
        }
        return list;
        /**********   End   **********/
    }

    //获取link[href]链接
    public List<String> getImports(Document doc) {
        /**********   Begin   **********/
        ArrayList<String> list = new ArrayList<>();
        Elements href = doc.select("link[href]");
        for (Element element : href) {
            list.add(element.tagName() + "$" + element.attr("abs:href") + "(" + element.attr("rel") + ")");
        }
        return list;
        /**********   End   **********/
    }

}
