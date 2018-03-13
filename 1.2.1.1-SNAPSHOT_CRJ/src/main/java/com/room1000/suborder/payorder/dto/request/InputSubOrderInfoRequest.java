package com.room1000.suborder.payorder.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月2日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class InputSubOrderInfoRequest {
    
    /**
     * 支付订单类型
     */
    private String type;
    
    /**
     * 关联表主键
     */
    private Long payRefId;
    
    /**
     * 需要支付金额（收入为正，支出为负），精确到分
     */
    private String payableMoney;
    
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
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentDate;
    
    /**
     * 创建客户主键
     * 为C端提供
     */
    private Long createdUserId;
    
    /**
     * 订单名称
     */
    private String orderName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPayRefId() {
        return payRefId;
    }

    public void setPayRefId(Long payRefId) {
        this.payRefId = payRefId;
    }

    public String getPayableMoney() {
        return payableMoney;
    }

    public void setPayableMoney(String payableMoney) {
        this.payableMoney = payableMoney;
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

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
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
        builder.append("InputSubOrderInfoRequest [type=");
        builder.append(type);
        builder.append(", payRefId=");
        builder.append(payRefId);
        builder.append(", payableMoney=");
        builder.append(payableMoney);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", userMobile=");
        builder.append(userMobile);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", createdUserId=");
        builder.append(createdUserId);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append("]");
        return builder.toString();
    }
}
