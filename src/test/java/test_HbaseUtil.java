import util.HBaseUtil;

import java.util.List;


public class test_HbaseUtil {
    public static void main(String[] args) throws Exception {


        List<String> user = HBaseUtil.scanTable("user");

        user.forEach(System.out::println);

        long user1 = HBaseUtil.rowCount("user");
        System.out.println(user1);

    }
}
