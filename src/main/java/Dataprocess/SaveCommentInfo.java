package Dataprocess;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import pojo.HotelComment;
import util.HBaseUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SaveCommentInfo {

    /**
     * 获取和保存酒店的评论数据
     */
    public static void main(String[] args) {
        // 创建评论表
        try {
            HBaseUtil.createTable("t_hotel_comment", new String[] { "hotel_info", "comment_info" });
        } catch (Exception e) {
            // 创建表失败
            e.printStackTrace();
        }

        try {
            InputStream resourceAsStream = SaveCommentInfo.class.getClassLoader().getResourceAsStream("comment.txt");
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
            HBaseUtil.putByTable("t_hotel_comment", puts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
