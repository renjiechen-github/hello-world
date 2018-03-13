package com.room1000.suborder.ownercancelleaseorder.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class InputSubOrderInfoRequest {

    /**
     * 收房合约id
     */
    private Long takeHouseOrderId;

    /**
     * 退租时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appointmentDate;

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
     * 创建员工主键
     * 为C端提供
     */
    private Long createdStaffId;

    public Long getTakeHouseOrderId() {
        return takeHouseOrderId;
    }

    public void setTakeHouseOrderId(Long takeHouseOrderId) {
        this.takeHouseOrderId = takeHouseOrderId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
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
        builder.append("InputSubOrderInfoRequest [takeHouseOrderId=");
        builder.append(takeHouseOrderId);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
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
