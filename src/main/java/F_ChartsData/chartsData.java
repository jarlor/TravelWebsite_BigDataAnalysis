package F_ChartsData;

import Util.Connected;
import Util.HBaseUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author GCJL
 * @date 2021/5/7 22:27
 */
public class chartsData {
    /**
     * 获取前number个澳门酒店名称和价格
     * @param number 指定获取几个酒店的数据
     * @return
     * @throws IOException
     */
    public static Map<String, String> dataOfAoMen(int number) throws IOException {
        List<Result> databyCName = HBaseUtil.getDatabyCName("澳门", "t_city_hotels_info", number);
        Map<String, String> dataByColumn = HBaseUtil.getDataByColumn(databyCName,
                "hotel_info", "name",
                "hotel_info", "price");
        return dataByColumn;
    }

    /**
     * 获取前number个香港酒店名称和价格
     * @param number 指定获取几个酒店的数据
     * @return
     * @throws IOException
     */
    public static Map<String, String> dataOfHongKong(int number) throws IOException {
        List<Result> databyCName = HBaseUtil.getDatabyCName("香港", "t_city_hotels_info", number);
        Map<String, String> dataByColumn = HBaseUtil.getDataByColumn(databyCName,
                "hotel_info", "name",
                "hotel_info", "price");
        return dataByColumn;
    }

    /**
     * 获取各城市的酒店平均价格
     * @return
     * @throws Exception
     */
    public static Map<String,String> dataOfPrice() throws Exception {
        Connection conn = Connected.getHbase();
        Table table = conn.getTable(TableName.valueOf("AveragePrice"));

        HashMap<String, String> soult = new HashMap<>();

        Scan scan = new Scan();
        ResultScanner scanner = table.getScanner(scan);

        for (Result result : scanner) {
            String k = Bytes.toString(result.getRow());
            String v = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("price")));
            soult.put(k,v);
        }
        table.close();
        conn.close();
        return soult;
    }



}
