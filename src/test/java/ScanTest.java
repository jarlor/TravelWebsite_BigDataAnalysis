import Util.HBaseUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ScanTest {

    @Test
    public void scanT_city_hotels_info() throws Exception {
        List<String> t_city_hotels_info = HBaseUtil.scanTable("t_city_hotels_info");
        for (String s : t_city_hotels_info) {
            System.out.println(s);
        }
    }

    @Test
    public void scanAveragePrice() throws Exception {
        List<String> averagePrice = HBaseUtil.scanTable("AveragePrice");
        for (String s : averagePrice) {
            System.out.println(s);
        }
    }

    @Test
    public void scanT_hotel_comment() throws Exception {
        List<String> t_hotel_comment = HBaseUtil.scanTable("t_hotel_comment");
        for (String s : t_hotel_comment) {
            System.out.println(s);
        }
    }

    @Test
    public void getName() throws IOException {
        List<String> dataByColumn = HBaseUtil.getDataByColumn("t_city_hotels_info", "hotel_info", "name");
        for (String s : dataByColumn) {
            System.out.println(s);
        }
    }

    @Test
    public void getPrice() throws IOException {
        List<String> dataByColumn = HBaseUtil.getDataByColumn("t_city_hotels_info", "hotel_info", "price");
        for (String s : dataByColumn) {
            System.out.println(s);
        }
    }
}
