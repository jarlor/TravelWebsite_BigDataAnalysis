package DataClean.step1;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.jsoup.nodes.Document;
public class Test {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/hotels.ctrip.com_domestic-city-hotel.txt";
        Task task=new Task();
        Document doc =task.getDoc(filePath);
        List<String> cleanHTML = task.cleanHTML(doc);
        System.out.println("第一组数据长度："+cleanHTML.get(0).length()+",第二组数据长度："+cleanHTML.get(1).length());
        System.out.println("第一组数据：\n"+cleanHTML.get(0).substring(0, 830));
        System.out.println("第二组数据：\n"+cleanHTML.get(1).substring(0, 200));
    }
}
