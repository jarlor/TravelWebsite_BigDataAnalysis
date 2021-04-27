package com.getdata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.entity.Hotel;
import com.entity.HotelCity;
import com.util.HttpUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * 发送分页Ajax请求的Handle
 */
public class HotelHandle {


    //测试获取一个城市数据
    public void testGetOneCity(){
        HotelCity city = new HotelCity();
        city.setPinyin("macau");
        city.setCityName("澳门");
        city.setCityId("59");
        city.setHeadPinyin("A");
        String str = getHotelListString(city);
        JSONPObject json = new JSONPObject(str);
        System.out.println(json);
    }


    public static void main(String[] args) {
        Hotel hotel = new Hotel();
        hotel.setId("6555104");
        hotel.setDpcount(1402);
        try {
            getHotelCommentObject(hotel,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static String getHotelListString(HotelCity city){
        //params为模拟分页ajax请求必要的参数
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("__VIEWSTATEGENERATOR", "DB1FBB6D");
        params.put("cityName", city.getCityName());
//		params.put("StartTime", "2016-11-24");
//		params.put("DepTime", "2016-11-25");
        params.put("txtkeyword", "");
        params.put("Resource", "");
        params.put("Room", "");
        params.put("Paymentterm", "");
        params.put("BRev", "");
        params.put("Minstate", "");
        params.put("PromoteType", "");
        params.put("PromoteDate", "");
        params.put("operationtype", "NEWHOTELORDER");
        params.put("PromoteStartDate", "");
        params.put("PromoteEndDate", "");
        params.put("OrderID", "");
        params.put("RoomNum", "");
        params.put("IsOnlyAirHotel", "F");
        params.put("cityId", city.getCityId());
        params.put("cityPY", city.getPinyin());
//		params.put("cityCode", "1853");
//		params.put("cityLat", "22.1946");
//		params.put("cityLng", "113.549");
        params.put("positionArea", "");
        params.put("positionId", "");
        params.put("keyword", "");
        params.put("hotelId", "");
        params.put("htlPageView", "0");
        params.put("hotelType", "F");
        params.put("hasPKGHotel", "F");
        params.put("requestTravelMoney", "F");
        params.put("isusergiftcard", "F");
        params.put("useFG", "F");
        params.put("HotelEquipment", "");
        params.put("priceRange", "-2");
        params.put("hotelBrandId", "");
        params.put("promotion", "F");
        params.put("prepay", "F");
        params.put("IsCanReserve", "F");
        params.put("OrderBy", "99");
        params.put("OrderType", "");
        params.put("k1", "");
        params.put("k2", "");
        params.put("CorpPayType", "");
        params.put("viewType", "");
//		params.put("checkIn", "2016-11-24");
//		params.put("checkOut", "2016-11-25");
        params.put("DealSale", "");
        params.put("ulogin", "");
        params.put("hidTestLat", "0%7C0");
//		params.put("AllHotelIds", "436450%2C371379%2C396332%2C419374%2C345805%2C436553%2C425997%2C436486%2C436478%2C344977%2C5605870%2C344983%2C371396%2C344979%2C2572033%2C699384%2C425795%2C419823%2C2010726%2C5772619%2C1181591%2C2005951%2C345811%2C371381%2C371377");// TODO
        params.put("psid", "");
        params.put("HideIsNoneLogin", "T");
        params.put("isfromlist", "T");
        params.put("ubt_price_key", "htl_search_result_promotion");
        params.put("showwindow", "");
        params.put("defaultcoupon", "");
        params.put("isHuaZhu", "False");
        params.put("hotelPriceLow", "");
        params.put("htlFrom", "hotellist");
        params.put("unBookHotelTraceCode", "");
        params.put("showTipFlg", "");
//		params.put("hotelIds", "436450_1_1,371379_2_1,396332_3_1,419374_4_1,345805_5_1,436553_6_1,425997_7_1,436486_8_1,436478_9_1,344977_10_1,5605870_11_1,344983_12_1,371396_13_1,344979_14_1,2572033_15_1,699384_16_1,425795_17_1,419823_18_1,2010726_19_1,5772619_20_1,1181591_21_1,2005951_22_1,345811_23_1,371381_24_1,371377_25_1");// TODO
        params.put("markType", "1");
        params.put("zone", "");
        params.put("location", "");
        params.put("type", "");
        params.put("brand", "");
        params.put("group", "");
        params.put("feature", "");
        params.put("equip", "");
        params.put("star", "");
        params.put("sl", "");
        params.put("s", "");
        params.put("l", "");
        params.put("price", "");
        params.put("a", "0");
        params.put("keywordLat", "");
        params.put("keywordLon", "");
        params.put("contrast", "0");
        params.put("page", String.valueOf(city.getCurrentPage()));
        params.put("contyped", "0");
        params.put("productcode", "");
        //获取到的是Json字符串
        String result = HttpUtil.getInstance().httpPost("http://hotels.ctrip.com/Domestic/Tool/AjaxHotelList.aspx", params);
        // 数据中有转义符直接转JSON报错，所以这里重新拼接所需要的JSON数据
        String tempHotel = result.substring(result.indexOf("hotelPositionJSON")-1, result.length());
        // 确保截取到indexOf("biRecord"), 减2是因为需要]符号
        String hotelArray = tempHotel.substring(0, tempHotel.indexOf("biRecord") - 2);
        String tempTotalCount = result.substring(result.indexOf("hotelAmount")-1, result.length());
        String totalCount = tempTotalCount.substring(0, tempTotalCount.indexOf(","));
        // 截取酒店价格数据
        String price = "";
        try {
            price = result.substring(result.indexOf("htllist\":\"[{")-1, result.indexOf("]\",\"spreadhotel\"") + 2);
        } catch (Exception e) {
        }
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append(totalCount);
        sb.append(",");
        sb.append(hotelArray);
        if (!"".equals(price)) {
            sb.append(",");
            sb.append(price.replace("\"[{", "[{").replace("}]\"", "}]"));
        }
        sb.append("}");
        return sb.toString().replace("\\", "");
    }



    public static String getHotelCommentObject(Hotel hotel,int currentPage) throws Exception {
        //模拟请求参数
        String postStr = "{\"hotelId\":" + hotel.getId()+ ",\"pageIndex\": " + currentPage+",\"pageSize\":10,\"tagId\":0,\"groupTypeBitMap\":2,\"needStatisticInfo\":0,\"order\":0,\"basicRoomName\":\"\",\"travelType\":-1,\"head\":{\"cid\":\"09031128411861109366\",\"ctok\":\"\",\"cver\":\"1.0\",\"lang\":\"01\",\"sid\":\"8888\",\"syscode\":\"09\",\"auth\":\"\",\"extension\":[]}}";
        JSONObject jsonObject = JSON.parseObject(postStr);

        //params.put("callback","CASCdPCiWzogsOray");
        String path = "http://m.ctrip.com/restapi/soa2/14605/gethotelcomment";
        String result = HttpUtil.getInstance().sendRequest(path,"POST",jsonObject.toString());
        //System.out.println(result);
        return result;
    }
}
