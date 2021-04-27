package com.entity;

public class HotelDetail {

    private int hotelId;

    /**
     * 开业时间
     */
    private String  openingTime;

    /**
     * 重新装修时间
     */
    private String redecoratedTime;

    /**
     * 房间数
     */
    private int roomNum;

    /**
     * 联系方式
     */
    private String contact;

    /**
     * 酒店描述
     */
    private String description;

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getRedecoratedTime() {
        return redecoratedTime;
    }

    public void setRedecoratedTime(String redecoratedTime) {
        this.redecoratedTime = redecoratedTime;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "HotelDetail{" +
                "hotelId=" + hotelId +
                ", openingTime='" + openingTime + '\'' +
                ", redecoratedTime='" + redecoratedTime + '\'' +
                ", roomNum=" + roomNum +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
