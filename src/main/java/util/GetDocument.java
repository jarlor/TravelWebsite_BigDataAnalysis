package util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

public class GetDocument {
    public static Document getDoc(String filePath) throws IOException {
        Document doc = Jsoup.parse(new File(filePath),"UTF-8");
        return doc;
    }
}
