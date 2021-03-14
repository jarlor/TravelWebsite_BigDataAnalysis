package DataCapture.step2;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Task {

    //通过filePath文件路径获取Docment对象
    public Document getDoc1(String filePath) throws IOException{
        /**********   Begin   **********/
        Document document = Jsoup.parse(new File(filePath), "UTF-8");
        return document;
        /**********   End   **********/
    }


    public Document getDoc2(String filePath) throws IOException{
        /**********   Begin   **********/
        Document document = Jsoup.parse(new File(filePath), "UTF-8");
        return document;
        /**********   End   **********/
    }

    //获取所有链接
    public Elements getLinks(Document doc){
        /**********   Begin   **********/
        Elements links = doc.select("link[href]");
        return links;
        /**********   End   **********/
    }

    //获取第一个class为“pop_attention”的div
    public Element getDiv(Document doc){
        /**********   Begin   **********/
        Element first = doc.select("div.pop_attention").first();
        return first;
        /**********   End   **********/
    }

    //获取所有li之后的i标签
    public Elements getI(Document doc){
        /**********   Begin   **********/
        Elements resultLinks = doc.select("li > i");
        return resultLinks;
        /**********   Edn   **********/
    }

}
