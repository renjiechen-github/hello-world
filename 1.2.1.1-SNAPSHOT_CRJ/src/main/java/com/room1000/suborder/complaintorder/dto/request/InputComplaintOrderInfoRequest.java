package com.room1000.suborder.complaintorder.dto.request;

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
public class InputComplaintOrderInfoRequest {

    /**
     * 备注
     */
    private String comments;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 投诉时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date complaintDate;
    
    /**
     * 创建客户主键
     * 为C端提供
     */
    private Long createdUserId;
    
    /**
     * 订单名称
     */
    private String orderName;

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

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
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
        builder.append("InputComplaintOrderInfoRequest [comments=");
        builder.append(comments);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", userMobile=");
        builder.append(userMobile);
        builder.append(", complaintDate=");
        builder.append(complaintDate);
        builder.append(", createdUserId=");
        builder.append(createdUserId);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append("]");
        return builder.toString();
    }
    
    
}
