package com.entity;

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
     * 城市首字符
     */
    private String headPinyin;

    /**
     * 城市拼音
     */
    private String pinyin;

    /**
     * 城市旅馆当前页数
     */
    private int currentPage = 1;

    /**
     * 采集时间
     */
    private long collectionTime;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public long getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(long collectionTime) {
        this.collectionTime = collectionTime;
    }


    @Override
    public String toString() {
        return "HotelCity{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", headPinyin='" + headPinyin + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", currentPage=" + currentPage +
                ", collectionTime=" + collectionTime +
                '}';
    }
}
