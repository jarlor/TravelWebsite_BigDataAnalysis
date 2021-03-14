package DataCapture.step1;
import java.io.IOException;
import org.jsoup.nodes.Document;

public class Test {
    public static void main(String[] args) throws IOException {
        Task task=new Task();
        String nextLine = "src/main/resources/www.ctrip.com.txt/";
        Document doc=null;
        if("backups/www.ctrip.com.txt/".equals(nextLine)){
            doc= task.getHtml1(nextLine);
        }else{
            doc = task.getHtml2(nextLine);
        }
        System.out.println(doc.toString().substring(0,560));
    }
}
