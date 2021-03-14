package DataCapture.step4;

public class HotelCity {
    /**
     * 城市Id
     */
    private String cityId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 城市首字母
     */
    private String headPinyin;
    /**
     * 城市拼音
     */
    private String pinyin;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getHeadPinyin() {
        return headPinyin;
    }

    public void setHeadPinyin(String headPinyin) {
        this.headPinyin = headPinyin;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "HotelCity [cityId=" + cityId + ", cityName=" + cityName
                + ", headPinyin=" + headPinyin + ", pinyin=" + pinyin + "]";
    }



}
