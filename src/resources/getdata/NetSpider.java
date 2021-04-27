package resources.getdata;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.entity.Hotel;
import com.entity.HotelCity;
import com.entity.HotelComment;
import com.util.HttpUtil;
import com.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据采集And数据清洗
 * 采集携程旅游网站数据
 */
public class NetSpider {

    public static void main(String[] args) {
        //测试
        String result = HttpUtil.getInstance().httpGet(null, "http://hotels.ctrip.com/domestic-city-hotel.html");
        Document root_document = Jsoup.parse(result);
        Elements pinyin_filter_elements = root_document.getElementsByClass("pinyin_filter_detail layoutfix");
        // 包含所有城市的Element
        Element pinyin_filter = pinyin_filter_elements.first();
        //拼音首字符Elements
        Elements pinyins = pinyin_filter.getElementsByTag("dt");
        //所有dd的Elements
        Elements hotelsLinks = pinyin_filter.getElementsByTag("dd");


        //获取城市列表
        List<HotelCity> cities = new ArrayList<HotelCity>();
        for(int i = 0 ; i < pinyins.size();i++ ){
            Element pinyinElement = pinyins.get(i);
            Element hotelsLink = hotelsLinks.get(i);
            Elements links = hotelsLink.children();
            for(Element link:links){
                HotelCity city = new HotelCity();
                //城市名称
                String cityName = link.html();
                city.setCityName(cityName);
                //链接地址
                String href = link.attr("href");
                //cityId 为href后的数字
                String cityId = StringUtil.getNumbers(href);
                city.setCityId(cityId);
                //城市首字母大写
                city.setHeadPinyin(pinyinElement.html());
                //城市拼音
                String pintyin = href.replaceAll("/hotel/","").replace(cityId,"");
                city.setPinyin(pintyin);
                //数据获取的时间戳
                city.setCollectionTime(System.currentTimeMillis());
                city.setCurrentPage(1);
                //添加
                cities.add(city);
            }

            for (HotelCity city:cities) {
                try {
                    //获取每个城市对应的数据
                    List<Hotel> hotelList = new ArrayList<>();
                    getAllCityOfHotels(hotelList,city);
                    System.out.println(hotelList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    //获取某个城市的数据
    public static  HotelCity getCity(String cityName){
        String result = HttpUtil.getInstance().httpGet(null, "http://hotels.ctrip.com/domestic-city-hotel.html");
        Document root_document = Jsoup.parse(result);
        Elements pinyin_filter_elements = root_document.getElementsByClass("pinyin_filter_detail layoutfix");
        // 包含所有城市的Element
        Element pinyin_filter = pinyin_filter_elements.first();

        Elements citys = pinyin_filter.getElementsMatchingText(cityName);

        Element cityElement = citys.last();

        HotelCity city = new HotelCity();
        //城市名称
        city.setCityName(cityName);
        //链接地址
        String href = cityElement.attr("href");
        //cityId 为href后的数字
        String cityId = StringUtil.getNumbers(href);
        city.setCityId(cityId);
        //城市首字母大写
        //城市拼音
        String pintyin = href.replaceAll("/hotel/","").replace(cityId,"");
        city.setPinyin(pintyin);
        //数据获取的时间戳
        city.setCollectionTime(System.currentTimeMillis());
        city.setCurrentPage(1);
        return city;

    }


    /**
     * 获取携程旅游网站所有城市数据
     * @return
     */
    public static List<HotelCity> getAllCity(){
        String result = HttpUtil.getInstance().httpGet(null, "http://hotels.ctrip.com/domestic-city-hotel.html");
        Document root_document = Jsoup.parse(result);
        Elements pinyin_filter_elements = root_document.getElementsByClass("pinyin_filter_detail layoutfix");
        // 包含所有城市的Element
        Element pinyin_filter = pinyin_filter_elements.first();
        //拼音首字符Elements
        Elements pinyins = pinyin_filter.getElementsByTag("dt");
        //所有dd的Elements
        Elements hotelsLinks = pinyin_filter.getElementsByTag("dd");

        //获取城市列表
        List<HotelCity> cities = new ArrayList<HotelCity>();
        for(int i = 0 ; i < pinyins.size();i++ ) {
            Element pinyinElement = pinyins.get(i);
            Element hotelsLink = hotelsLinks.get(i);
            Elements links = hotelsLink.children();
            for (Element link : links) {
                HotelCity city = new HotelCity();
                //城市名称
                String cityName = link.html();
                city.setCityName(cityName);
                //链接地址
                String href = link.attr("href");
                //cityId 为href后的数字
                String cityId = StringUtil.getNumbers(href);
                city.setCityId(cityId);
                //城市首字母大写
                city.setHeadPinyin(pinyinElement.html());
                //城市拼音
                String pintyin = href.replaceAll("/hotel/", "").replace(cityId, "");
                city.setPinyin(pintyin);
                //数据获取的时间戳
                city.setCollectionTime(System.currentTimeMillis());
                city.setCurrentPage(1);
                //添加
                cities.add(city);
            }
        }
        return cities;
    }

    /**
     * 获取城市所有酒店分页的数据
     * @param city
     * @return
     * @throws Exception
     */
    public static void getAllCityOfHotels(List<Hotel> hotelList, HotelCity city)throws Exception{
        //获取第一页数据
        String result = HotelHandle.getHotelListString(city);
        // 解析酒店数据
        JSONObject resultObj = JSONObject.parseObject(result);
        int hotelAmount = resultObj.getIntValue("hotelAmount");
        int currentPage = hotelAmount % 25 == 0 ? hotelAmount / 25 : hotelAmount / 25 + 1;
        JSONArray jsonArray = resultObj.getJSONArray("hotelPositionJSON");
        //所有宾馆数据
        List<Hotel> hotels1 = JSON.parseArray(jsonArray.toJSONString(), Hotel.class);
        //获取所有宾馆价格数据
        JSONArray pricaArray = resultObj.getJSONArray("htllist");
        for (int i = 0 ; i< hotels1.size();i++){
            JSONObject o = (JSONObject)pricaArray.get(i);
            Hotel hotel = hotels1.get(i);
            String hotelId = o.getString("hotelid");
            double price = 0;
            try {
                price = o.getDoubleValue("amount");
            } catch (Exception e) {
            }
            if (hotel.getId().equals(hotelId)) {
                hotel.setPrice(price);
            }
            hotel.setCity_id(city.getCityId());
            hotel.setCity_name(city.getCityName());
            hotel.setPinyin(city.getPinyin());
        }
        hotelList.addAll(hotels1);
        if(currentPage == city.getCurrentPage()){
            return;
        }else {
            city.setCurrentPage(city.getCurrentPage()+1);
            getAllCityOfHotels(hotelList,city);
        }
    }


    /**
     * 获取所有酒店评论的数据
     */
    public static List<HotelComment> getOtherCommentListByPage(Hotel hotel,int currentPage){
        try {
            String pageInfo = HotelHandle.getHotelCommentObject(hotel,currentPage);
            JSONObject jsonInfo = JSON.parseObject(pageInfo);
            JSONArray othersCommentList = jsonInfo.getJSONArray("othersCommentList");
            List<HotelComment> commentList = JSON.parseArray(othersCommentList.toString(),HotelComment.class);
            if(commentList != null && commentList.size()>0){
                for(HotelComment comment : commentList) {
                    comment.setHotel_id(hotel.getId());
                    comment.setHotel_name(hotel.getName());
                }
                return commentList;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
