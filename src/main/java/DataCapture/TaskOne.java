package DataCapture;
import org.jsoup.nodes.Document;
import util.GetDocument;

public class TaskOne {
    public static void main(String[] args) throws Exception {

        String nextLine = "src/main/resources/www.ctrip.com.txt/";
        Document doc=null;
        if("src/main/resources/www.ctrip.com.txt/".equals(nextLine)){
            //解析路径文件
            doc= GetDocument.getDoc(nextLine);
        }else{
            throw new Exception("未找到指定文件，解析错误！");
        }
        System.out.println(doc.toString().substring(0,560));
    }
}
