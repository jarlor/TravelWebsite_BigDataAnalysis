package DataCapture.step4;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import org.jsoup.nodes.Document;
public class Test {

    public static void main(String[] args) throws IOException {
        Task task=new Task();
        Document doc = task.getDoc("src/main/resources/hotels.ctrip.com_domestic-city-hotel.txt");
        List<HotelCity> allCitys = task.getAllCitys(doc);
        System.out.println("总共有："+allCitys.size()+"个城市，下面列出前十个：");
        for (int i = 0; i < allCitys.size(); i++) {
            System.out.println(allCitys.get(i));
        }
    }
}
