package DataCapture.step1;
import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Task {
    /**
     * @param filePath	文件路径：backups/www.ctrip.com.txt/
     * @return
     * @throws IOException
     */
    public Document getHtml1(String filePath) throws IOException{
        /**********   Begin   **********/
        Document doc = Jsoup.parse(new File(filePath),"UTF-8");

        return doc;
        /**********   End   **********的/
    }

    /**
     *
     * @param filePath	文件路径：backups/hotels.ctrip.com_domestic-city-hotel.txt/
     * @return
     * @throws IOException
     */
    public Document getHtml2(String filePath) throws IOException{
        /**********   Begin   **********/
        Document document = Jsoup.parse(new File(filePath),"UTF-8");
        return document;
        /**********   End   **********/
    }
}