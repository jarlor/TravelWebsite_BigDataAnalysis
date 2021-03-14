package DataClean.step2;
import java.util.List;
import java.util.Scanner;
public class Test {
    public static void main(String[] args) {
        Task task=new Task();
        String hotelResult = task.getHotelListString("1", "http://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx");
        List<Hotel> hotle = task.getHotle(hotelResult);
        System.out.println("北京市酒店个数："+hotle.size());
        for (int i = 0; i < hotle.size(); i++) {
            System.out.println(hotle.get(i));
        }
    }
}
