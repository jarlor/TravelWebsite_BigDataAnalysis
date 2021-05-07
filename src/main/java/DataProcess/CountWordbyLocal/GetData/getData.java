package DataProcess.CountWordbyLocal;

import DataToHbase.HotelComment;
import DataToHbase.SaveData;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author GCJL
 * @date 2021/5/7 14:59
 */
public class getData {
    public Map<String,String> getDataFromLocal() throws IOException {
        InputStream resourceAsStream = SaveData.class.getClassLoader().getResourceAsStream("comment.txt");
        String readFileToString = IOUtils.toString(resourceAsStream, "UTF-8");
        List<HotelComment> result = JSONObject.parseArray(readFileToString, HotelComment.class);
        for (HotelComment comment : result) {
            comment.getHotel_id()  + "_" + comment.getId()
        }

        HashMap<String, String> value = new HashMap<>();




    }
}
