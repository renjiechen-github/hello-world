package com.room1000.suborder.cancelleaseorder.dto.request;

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
public class InputCancelLeaseOrderInfoRequest {

    /**
     * 出租合约id
     */
    private Long rentalLeaseOrderId;

    /**
     * 退租时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date cancelLeaseDate;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 备注
     */
    private String comments;

    /**
     * 退租类型
     */
    private String type;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userMobile;
    
    /**
     * 创建客户主键
     * 为C端提供
     */
    private Long createdUserId;
    
    /**
     * 订单名称
     */
    private String orderName;
    
    /**
     * 操作人
     */
    private Long createdStaffId;

    public Long getRentalLeaseOrderId() {
        return rentalLeaseOrderId;
    }

    public void setRentalLeaseOrderId(Long rentalLeaseOrderId) {
        this.rentalLeaseOrderId = rentalLeaseOrderId;
    }

    public Date getCancelLeaseDate() {
        return cancelLeaseDate;
    }

    public void setCancelLeaseDate(Date cancelLeaseDate) {
        this.cancelLeaseDate = cancelLeaseDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Long getCreatedStaffId() {
        return createdStaffId;
    }

    public void setCreatedStaffId(Long createdStaffId) {
        this.createdStaffId = createdStaffId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InputCancelLeaseOrderInfoRequest [rentalLeaseOrderId=");
        builder.append(rentalLeaseOrderId);
        builder.append(", cancelLeaseDate=");
        builder.append(cancelLeaseDate);
        builder.append(", channel=");
        builder.append(channel);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", type=");
        builder.append(type);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", userMobile=");
        builder.append(userMobile);
        builder.append(", createdUserId=");
        builder.append(createdUserId);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append(", createdStaffId=");
        builder.append(createdStaffId);
        builder.append("]");
        return builder.toString();
    }


    
    
}
