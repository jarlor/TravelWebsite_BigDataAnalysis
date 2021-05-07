import Util.HBaseUtil;
import org.apache.hadoop.hbase.Cell;

import java.util.List;


public class test_HbaseUtil {
    public static void main(String[] args) throws Exception {

        List<Cell> t_city_hotels_info = HBaseUtil.getRowkey("t_hotel_comment", "6555104_268593428");

        List<String> strings = HBaseUtil.printFormat(t_city_hotels_info);
        for (String string : strings) {
            System.out.println(string);
        }

    }
}
