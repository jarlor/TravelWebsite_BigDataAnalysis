package DataCapture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pojo.HotelCity;
import util.GetDocument;
import util.StringUtil;

public class TaskFour {

    /**
     * 获取所有城市返回城市信息集合
     *
     * @param doc
     * @return
     */
    public static List<HotelCity> getAllCitys(Document doc) {
        ArrayList<HotelCity> cities = new ArrayList<>();

        Elements pinyin_filter_elements = doc.getElementsByClass("pinyin_filter_detail layoutfix");

        //确保拿到的是第一个包含所有城市的Element
        Element pinyin_filter = pinyin_filter_elements.first();

        //所有的dd
        Elements all_dd = pinyin_filter.getElementsByTag("dd");

        //所有的dt
        Elements all_dt = pinyin_filter.getElementsByTag("dt");


        for (int i = 0; i < all_dt.size(); i++) {

            //找到第i个dt
            Element dt_headPinyin = all_dt.get(i);

            //找到第i个dt
            Element dd_Info = all_dd.get(i);

            //找到第i个dd下所有的子标签 也就是a标签
            Elements all_Info = dd_Info.children();

            for (Element element : all_Info) {

                HotelCity hotelCity = new HotelCity();
                //cityID
                    //用提供的StringUtil类提取出数字
                String cityID = StringUtil.getNumbers(element.attr("href"));
                hotelCity.setCityId(cityID);

                //cityName
                String cityName = element.text();
                hotelCity.setCityName(cityName);

                //headPinyin
                String headPinyin = dt_headPinyin.text();
                hotelCity.setHeadPinyin(headPinyin);

                //pinyin
                String[] href= element.attr("href").split("/");
                    //分割后，这个字符串数组最后一个字符串的长度减去cityID的长度就是pinyin的长度
                int length_piniyin = href[href.length - 1].length() - cityID.length();
                String pinyin =href[href.length - 1].substring(0,length_piniyin);
                hotelCity.setPinyin(pinyin);

                cities.add(hotelCity);
            }
        }
        return cities;
    }

    public static void main(String[] args) throws IOException {

        //通过文件路径获取Docment对象
        Document doc = GetDocument.getDoc("src/main/resources/hotels.ctrip.com_domestic-city-hotel.txt");
        List<HotelCity> allCitys = TaskFour.getAllCitys(doc);
        System.out.println("总共有："+allCitys.size()+"个城市，下面列出前十个：");
        for (int i = 0; i < allCitys.size(); i++) {
            System.out.println(allCitys.get(i));
        }
    }
}
