import Util.HBaseUtil;

import java.util.List;

/**
 * @author GCJL
 * @date 2021/5/7 21:53
 */
public class demo11 {
    public static void main(String[] args) throws Exception {
        List<String> t_city_hotels_info = HBaseUtil.scanTable("t_city_hotels_info");
        for (String s : t_city_hotels_info) {
            System.out.println(s);
        }
    }
}