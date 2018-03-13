package com.room1000.suborder.cleaningorder.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Description:
 * 
 * Created on 2017年5月26日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class InputSubOrderInfoRequest {

    /**
     * 出租合约主键
     */
    private Long rentalLeaseOrderId;

    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentDate;

    /**
     * 类型
     */
    private String type;

    /**
     * comments
     */
    private String comments;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 金额在前台进行转换成整型
     */
    private Long cleaningMoney;

    /**
     * 创建客户主键 为C端提供
     */
    private Long createdUserId;

    /**
     * 订单名称
     */
    private String orderName;

    public Long getRentalLeaseOrderId() {
        return rentalLeaseOrderId;
    }

    public void setRentalLeaseOrderId(Long rentalLeaseOrderId) {
        this.rentalLeaseOrderId = rentalLeaseOrderId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCleaningMoney() {
        return cleaningMoney;
    }

    public void setCleaningMoney(Long cleaningMoney) {
        this.cleaningMoney = cleaningMoney;
    }

    public Long getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Long createdUserId) {
        this.createdUserId = createdUserId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InputCleaningOrderInfoRequest [rentalLeaseOrderId=");
        builder.append(rentalLeaseOrderId);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", type=");
        builder.append(type);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", userMobile=");
        builder.append(userMobile);
        builder.append(", imageUrl=");
        builder.append(imageUrl);
        builder.append(", cleaningMoney=");
        builder.append(cleaningMoney);
        builder.append(", createdUserId=");
        builder.append(createdUserId);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append("]");
        return builder.toString();
    }
}
