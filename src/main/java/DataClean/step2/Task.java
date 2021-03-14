package DataClean.step2;

import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

public class Task {

    /**
     * 使用fastjson解析数据
     *
     * @param hotelResult 已经为你解析的所需json数据
     * @return
     */
    public List<Hotel> getHotle(String hotelResult) {
        /**********   Begin   **********/
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
        /**********   End   **********/
    }


    /**
     * 由于携程网站经常更新，为了不影响测试，我们直接读取本地文件。
     *
     * @return
     */
    public String getHotelListString(String cityId, String url) {
        String hotelResult = "";
        try {
            InputStream is = new FileInputStream(new File("src/main/java/DataClean/step2/hotelResult.txt"));
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

}
