package A_DataCapture;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import Util.GetDocument;

/**
 * 从解析的元素里获取数据
 */
public class c_GetDataFromParseElement {

    //获取所有链接
    public static List<String> getLinks(Document doc) {
        ArrayList<String> list = new ArrayList<>();
        Elements href = doc.select("a[href]");
        for (Element element : href) {
            list.add(element.tagName() + "$" + element.attr("abs:href") + "(" + element.text() + ")");
        }
        return list;
    }

    //获取图片
    public static List<String> getMedia(Document doc) {
        List<String> list = new ArrayList<>();
        Elements src = doc.select("[src]");
        for (Element element : src) {
            if (element.tagName().equals("img")) {
                list.add(element.tagName() + "$" + element.attr("abs:src"));
            }
        }
        return list;
    }

    //获取link[href]链接
    public static List<String> getImports(Document doc) {
        ArrayList<String> list = new ArrayList<>();
        Elements href = doc.select("link[href]");
        for (Element element : href) {
            list.add(element.tagName() + "$" + element.attr("abs:href") + "(" + element.attr("rel") + ")");
        }
        return list;
    }

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/hotel.ctrip.com.txt";
        //通过filePath文件路径获取Docment对象
        Document doc = GetDocument.getDoc(filePath);
        List<String> links = c_GetDataFromParseElement.getLinks(doc);
        List<String> media = c_GetDataFromParseElement.getMedia(doc);
        List<String> imports = c_GetDataFromParseElement.getImports(doc);
        System.out.println("links:"+links.size()+",media:"+media.size()+",imports:"+imports.size());
        for (int i = 0; i < links.size(); i++)
            System.out.println(links.get(i));
        for (int i = 0; i < media.size(); i++)
            System.out.println(media.get(i));
        for (int i = 0; i < imports.size(); i++)
            System.out.println(imports.get(i));
    }

}
