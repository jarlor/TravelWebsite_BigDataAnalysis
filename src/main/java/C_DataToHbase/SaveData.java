package C_DataToHbase;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import C_DataToHbase.InternalPojo.Hotel;
import C_DataToHbase.InternalPojo.HotelComment;
import Util.HBaseUtil;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import com.alibaba.fastjson.JSONObject;


public class SaveData {


    /**
     * 获取并保存酒店和城市数据
     */
    public static void saveCityAndHotelInfo() throws Exception {
        HBaseUtil.createTable("t_city_hotels_info", new String[]{"cityInfo", "hotel_info"});

        List<Put> puts = new ArrayList<>();

        // 添加数据
        InputStream resourceAsStream = SaveData.class.getClassLoader().getResourceAsStream("aomen.txt");
        String readFileToString = IOUtils.toString(resourceAsStream, "UTF-8");
        List<Hotel> parseArray = JSONObject.parseArray(readFileToString, Hotel.class);
        String hongkong = IOUtils.toString(SaveData.class.getClassLoader().getResourceAsStream("hongkong.txt"), "UTF-8");
        List<Hotel> hongkongHotel = JSONObject.parseArray(hongkong, Hotel.class);
        parseArray.addAll(hongkongHotel);


        for (Hotel hotel : parseArray) {
            String cityId = hotel.getCity_id();
            String hotelId = hotel.getId();
            Put put = new Put(Bytes.toBytes(cityId + "_" + hotelId));
            // 添加city数据
            put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("cityId"), Bytes.toBytes(cityId));
            put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("cityName"),Bytes.toBytes(hotel.getCity_name()));
            put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("pinyin"), Bytes.toBytes(hotel.getPinyin()));
            put.addColumn(Bytes.toBytes("cityInfo"), Bytes.toBytes("collectionTime"),Bytes.toBytes(hotel.getCollectionTime()));
            // 添加hotel数据
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("id"), Bytes.toBytes(hotel.getId()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("name"), Bytes.toBytes(hotel.getName()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("price"), Bytes.toBytes(String.valueOf(hotel.getPrice())));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("lon"), Bytes.toBytes(String.valueOf(hotel.getLon())));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("url"), Bytes.toBytes(hotel.getUrl()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("img"), Bytes.toBytes(hotel.getImg()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("address"), Bytes.toBytes(hotel.getAddress()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("score"), Bytes.toBytes(String.valueOf(hotel.getScore())));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("dpscore"), Bytes.toBytes(String.valueOf(hotel.getDpscore())));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("dpcount"), Bytes.toBytes(String.valueOf(hotel.getDpcount())));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("star"), Bytes.toBytes(hotel.getStar()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("stardesc"),Bytes.toBytes(hotel.getStardesc()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("shortName"),Bytes.toBytes(hotel.getShortName()));
            put.addColumn(Bytes.toBytes("hotel_info"), Bytes.toBytes("isSingleRec"),Bytes.toBytes(hotel.getIsSingleRec()));

            puts.add(put);
        }
        // 批量保存数据
        HBaseUtil.putDataByTable("t_city_hotels_info", puts);
    }

    /**
     * 获取和保存酒店的评论数据
     */
    public static void saveCommentInfo() throws Exception {

        // 创建评论表
            HBaseUtil.createTable("t_hotel_comment", new String[] { "hotel_info", "comment_info" });

            InputStream resourceAsStream = SaveData.class.getClassLoader().getResourceAsStream("comment.txt");
            String readFileToString = IOUtils.toString(resourceAsStream, "UTF-8");
            List<HotelComment> otherCommentListByPage = JSONObject.parseArray(readFileToString, HotelComment.class);
            // 获取数据
            List<Put> puts = new ArrayList<>();
            // 定义Put对象
            for (HotelComment comment : otherCommentListByPage) {

                Put put = new Put((comment.getHotel_id()  + "_" + comment.getId()).getBytes());

                put.addColumn("hotel_info".getBytes(), "hotel_name".getBytes(),comment.getHotel_name().getBytes());
                put.addColumn("hotel_info".getBytes(), "hotel_id".getBytes(), comment.getHotel_id().getBytes());
                // 数据量很大在这里只保存用作分析的数据
                put.addColumn("comment_info".getBytes(), "id".getBytes(), Bytes.toBytes(String.valueOf(comment.getId())));
                put.addColumn("comment_info".getBytes(), "baseRoomId".getBytes(), Bytes.toBytes(String.valueOf(comment.getBaseRoomId())));
                if (comment.getBaseRoomId() != -1 && comment.getBaseRoomName() != null) {
                    put.addColumn("comment_info".getBytes(), "baseRoomName".getBytes(),Bytes.toBytes(comment.getBaseRoomName()));
                }
                put.addColumn("comment_info".getBytes(), "checkInDate".getBytes(), Bytes.toBytes(comment.getCheckInDate()));
                put.addColumn("comment_info".getBytes(), "postDate".getBytes(), Bytes.toBytes(comment.getPostDate()));
                put.addColumn("comment_info".getBytes(), "content".getBytes(), Bytes.toBytes(comment.getContent()));
                put.addColumn("comment_info".getBytes(), "highlightPosition".getBytes(), Bytes.toBytes(comment.getHighlightPosition()));
                put.addColumn("comment_info".getBytes(), "hasHotelFeedback".getBytes(),Bytes.toBytes(String.valueOf(comment.getHasHotelFeedback())));
                put.addColumn("comment_info".getBytes(), "userNickName".getBytes(), Bytes.toBytes(comment.getUserNickName()));

                puts.add(put);
            }
            // 上传数据
            HBaseUtil.putDataByTable("t_hotel_comment", puts);
    }
}
