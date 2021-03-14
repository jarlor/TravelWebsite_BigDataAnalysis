package DataCapture.step2;
import java.io.IOException;
import java.util.Scanner;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/www.ctrip.com.txt";
        Task task=new Task();
        Document doc=null;
        if("src/main/resources/www.ctrip.com.txt".equals(filePath)){
            doc= task.getDoc1(filePath);
        }else{
            doc= task.getDoc2(filePath);
        }
        Elements Links = task.getLinks(doc);
        Elements I = task.getI(doc);
        Element Div = task.getDiv(doc);
        System.out.println("Links:"+Links.size()+",I:"+I.size());
        for (int i = 0; i < 2; i++)
            System.out.println(Links.get(i));
        for (int i = 0; i < 2; i++)
            System.out.println(I.get(i));
        if(Div!=null)
            System.out.println(Div.toString().replace("<div", "<span").replace("/div>", "/span>"));
    }
}
