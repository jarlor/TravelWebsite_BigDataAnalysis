package Dataprocess;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import pojo.HotelInfo;
import util.HBaseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveCityAndHotelInfo {
    public static void main(String[] args) {

        /**
         * 获取并保存酒店和城市数据
         */
        try {
            HBaseUtil.createTable("t_city_hotels_info", new String[] { "cityInfo", "hotel_info" });
        } catch (Exception e) {
            // 创建表失败
            e.printStackTrace();
        }
        List<Put> puts = new ArrayList<>();
        // 添加数据
        try {
            InputStream resourceAsStream = SaveCityAndHotelInfo.class.getClassLoader().getResourceAsStream("aomen.txt");
            String readFileToString = IOUtils.toString(resourceAsStream, "UTF-8");
            List<HotelInfo> parseArray = JSONObject.parseArray(readFileToString, HotelInfo.class);
            String hongkong = IOUtils.toString(SaveCityAndHotelInfo.class.getClassLoader().getResourceAsStream("hongkong.txt"),"UTF-8");
            List<HotelInfo> hongkongHotel = JSONObject.parseArray(hongkong, HotelInfo.class);
            parseArray.addAll(hongkongHotel);
            for (HotelInfo hotelInfo : parseArray) {
                String cityId = hotelInfo.getCity_id();
                String hotelId = hotelInfo.getId();
                Put put = new Put(Bytes.toBytes(cityId + "_" + hotelId));
                // 添加city数据
                put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("cityId"), Bytes.toBytes(cityId));
                put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("cityName"),Bytes.toBytes(hotelInfo.getCity_name()));
                put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("pinyin"), Bytes.toBytes(hotelInfo.getPinyin()));
                put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("collectionTime"),Bytes.toBytes(hotelInfo.getCollectionTime()));
                // 添加hotel数据
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("id"), Bytes.toBytes(hotelInfo.getId()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("name"), Bytes.toBytes(hotelInfo.getName()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("price"), Bytes.toBytes(String.valueOf(hotelInfo.getPrice())));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("lon"), Bytes.toBytes(String.valueOf(hotelInfo.getLon())));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("url"), Bytes.toBytes(hotelInfo.getUrl()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("img"), Bytes.toBytes(hotelInfo.getImg()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("address"), Bytes.toBytes(hotelInfo.getAddress()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("score"), Bytes.toBytes(String.valueOf(hotelInfo.getScore())));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("dpscore"), Bytes.toBytes(String.valueOf(hotelInfo.getDpscore())));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("dpcount"), Bytes.toBytes(String.valueOf(hotelInfo.getDpcount())));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("star"), Bytes.toBytes(hotelInfo.getStar()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("stardesc"),Bytes.toBytes(hotelInfo.getStardesc()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("shortName"),Bytes.toBytes(hotelInfo.getShortName()));
                put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("isSingleRec"),Bytes.toBytes(hotelInfo.getIsSingleRec()));

                puts.add(put);
            }
            // 批量保存数据
            HBaseUtil.putByTable("t_city_hotels_info", puts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
