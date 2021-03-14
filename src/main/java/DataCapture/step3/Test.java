package DataCapture.step3;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.jsoup.nodes.Document;
public class Test {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/hotel.ctrip.com.txt";
        Task task=new Task();
        Document doc =task.getDoc(filePath);
        List<String> links = task.getLinks(doc);
        List<String> media = task.getMedia(doc);
        List<String> imports = task.getImports(doc);
        System.out.println("links:"+links.size()+",media:"+media.size()+",imports:"+imports.size());
        for (int i = 0; i < 3; i++)
            System.out.println(links.get(i));
        for (int i = 0; i < 2; i++)
            System.out.println(media.get(i));
        for (int i = 0; i < 3; i++)
            System.out.println(imports.get(i));
    }
}
