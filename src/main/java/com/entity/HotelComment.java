package com.entity;

import java.util.List;


public class HotelComment {

    private int id;
    private String hotel_name;
    private String hotel_id;
    private int baseRoomId;
    private String baseRoomName;
    private String checkInDate;
    private String postDate;
    private String content;
    private String highlightPosition;
    private int hasHotelFeedback;
    private int isCanFeedback;
    private int ratingPoint;
    private String travelType;
    private String userNickName;
    private int commenterGrade;
    private int usefulNumber;
    private int canClickUseful;
    private int source;
    private String ctripBookRemark;
    private String orderId;
    private List<FeedbackListBean> feedbackList;
    private List<ImageListBean> imageList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBaseRoomId() {
        return baseRoomId;
    }

    public void setBaseRoomId(int baseRoomId) {
        this.baseRoomId = baseRoomId;
    }

    public String getBaseRoomName() {
        return baseRoomName;
    }

    public void setBaseRoomName(String baseRoomName) {
        this.baseRoomName = baseRoomName;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHighlightPosition() {
        return highlightPosition;
    }

    public void setHighlightPosition(String highlightPosition) {
        this.highlightPosition = highlightPosition;
    }

    public int getHasHotelFeedback() {
        return hasHotelFeedback;
    }

    public void setHasHotelFeedback(int hasHotelFeedback) {
        this.hasHotelFeedback = hasHotelFeedback;
    }

    public int getIsCanFeedback() {
        return isCanFeedback;
    }

    public void setIsCanFeedback(int isCanFeedback) {
        this.isCanFeedback = isCanFeedback;
    }

    public int getRatingPoint() {
        return ratingPoint;
    }

    public void setRatingPoint(int ratingPoint) {
        this.ratingPoint = ratingPoint;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getCommenterGrade() {
        return commenterGrade;
    }

    public void setCommenterGrade(int commenterGrade) {
        this.commenterGrade = commenterGrade;
    }

    public int getUsefulNumber() {
        return usefulNumber;
    }

    public void setUsefulNumber(int usefulNumber) {
        this.usefulNumber = usefulNumber;
    }

    public int getCanClickUseful() {
        return canClickUseful;
    }

    public void setCanClickUseful(int canClickUseful) {
        this.canClickUseful = canClickUseful;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public String getCtripBookRemark() {
        return ctripBookRemark;
    }

    public void setCtripBookRemark(String ctripBookRemark) {
        this.ctripBookRemark = ctripBookRemark;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<FeedbackListBean> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<FeedbackListBean> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<ImageListBean> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageListBean> imageList) {
        this.imageList = imageList;
    }

    public static class FeedbackListBean {
        private String title;
        private String content;
        private int source;
        private List<?> imageList;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public List<?> getImageList() {
            return imageList;
        }

        public void setImageList(List<?> imageList) {
            this.imageList = imageList;
        }
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(String hotel_id) {
        this.hotel_id = hotel_id;
    }

    public static class ImageListBean {
        private String smallImage;
        private String bigImage;

        public String getSmallImage() {
            return smallImage;
        }

        public void setSmallImage(String smallImage) {
            this.smallImage = smallImage;
        }

        public String getBigImage() {
            return bigImage;
        }

        public void setBigImage(String bigImage) {
            this.bigImage = bigImage;
        }
    }
}
