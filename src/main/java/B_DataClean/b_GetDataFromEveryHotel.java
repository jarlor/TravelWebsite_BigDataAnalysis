package B_DataClean;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import pojo.Hotel;

import java.io.*;

/**
 * 获取每一个酒店的信息并注入到hotel实体
 */
public class b_GetDataFromEveryHotel {


    public static List<Hotel> getHotle(String hotelResult) {
        ArrayList<Hotel> hotels = new ArrayList<>();

        JSONObject jsonObject = JSONObject.parseObject(hotelResult);
        List<Hotel> parseArray = JSON.parseArray(jsonObject.getString("hotelPositionJSON"), Hotel.class);

        JSONArray htllist = JSON.parseArray(jsonObject.getString("htllist"));


            for (Hotel hotel : parseArray) {
                for (int i = 0; i < htllist.size(); i++) {
                    Object object = htllist.getJSONObject(i);
                    if(((JSONObject) object).getString("hotelid").equals(hotel.getId())){
                        hotel.setPrice((((JSONObject) object).getDouble("amount")));
                    }
            }
        }
        hotels.addAll(parseArray);
        return hotels;
    }


    /**
     *
     * @return
     */
    public static String getHotelListString( String url) {
        String hotelResult = "";
        try {
            InputStream is = new FileInputStream(new File("src/main/resources/hotelResult.txt"));
            byte[] b = new byte[1024];
            int len = 0;
            try {
                while ((len = is.read(b)) != -1) {
                    String str = new String(b, 0, len);
                    hotelResult += str;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return hotelResult;
    }

    public static void main(String[] args) {
        String hotelResult = b_GetDataFromEveryHotel.getHotelListString("http://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx");
        List<Hotel> hotels = b_GetDataFromEveryHotel.getHotle(hotelResult);
        System.out.println("北京市酒店个数："+hotels.size());
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println(hotels.get(i));
            System.out.println("--------------");
        }
    }

}
