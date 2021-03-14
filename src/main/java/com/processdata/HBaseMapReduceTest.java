package com.processdata;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.util.ToolRunner;

import com.savedata.SaveData;
import com.util.HBaseUtil;

/*
 * @author ZY
 * @date 2019年2月21日 下午4:51:19
 * @version 1.0
 */
public class HBaseMapReduceTest {

    public static void main(String[] args) throws Exception {
        SaveData.saveCityAndHotelInfo();
        //开始执行
        //HBaseUtil.scanCityTable("average_table");
        // HBaseUtil.scanCityTable("t_city_hotels_info");
        int result = ToolRunner.run(HBaseConfiguration.create(), new HBaseMapReduce(), args);
        HBaseUtil.scanTable("average_table");
        System.exit(result);
    }
}
