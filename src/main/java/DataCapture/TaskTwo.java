package DataCapture;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.GetDocument;

public class TaskTwo {

    //获取所有链接
    public static Elements getLinks(Document doc){
        Elements links = doc.select("link[href]");
        return links;
    }

    //获取第一个class为“pop_attention”的div
    public static Element getDiv(Document doc){
        Element first = doc.select("div.pop_attention").first();
        return first;
    }

    //获取所有li之后的i标签
    public static Elements getI(Document doc){
        Elements resultLinks = doc.select("li > i");
        return resultLinks;
    }

    public static void main(String[] args) throws Exception {
        String filePath = "src/main/resources/www.ctrip.com.txt";
        Document doc=null;
        if("src/main/resources/www.ctrip.com.txt".equals(filePath)){
            //通过filePath文件路径获取Docment对象
            doc = GetDocument.getDoc(filePath);
        }else{
            throw new Exception("未找到指定文件，解析错误！");
        }
        Elements Links = TaskTwo.getLinks(doc);
        Elements I = TaskTwo.getI(doc);
        Element Div = TaskTwo.getDiv(doc);
        System.out.println("Links:"+Links.size()+",I:"+I.size());
        for (int i = 0; i < 2; i++)
            System.out.println(Links.get(i));
        for (int i = 0; i < 2; i++)
            System.out.println(I.get(i));
        if(Div!=null)
            System.out.println(Div.toString().replace("<div", "<span").replace("/div>", "/span>"));
    }

}
