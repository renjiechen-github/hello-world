package com.room1000.suborder.houselookingorder.dto.request;

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
     * 行政区主键
     */
    private Long areaId;
    
    /**
     * 小区主键
     */
    private Long groupId;

    /**
     * 房屋主键
     */
    private Long houseId;
    
    /**
     * 渠道
     */
    private String channel;

    /**
     * 预约时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentDate;

    /**
     * 备注
     */
    private String comments;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String userMobile;

    /**
     * 创建客户主键
     * 为C端提供
     */
    private Long createdUserId;
    
    /**
     * 指派处理人
     */
    private Long assignedDealerId;
    
    /**
     * 订单名称
     */
    private String orderName;
    
    /**
     * recommendId
     */
    private Long recommendId;
    
    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
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

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAssignedDealerId() {
        return assignedDealerId;
    }

    public void setAssignedDealerId(Long assignedDealerId) {
        this.assignedDealerId = assignedDealerId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getRecommendId() {
        return recommendId;
    }

    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InputHouseLookingOrderInfoRequest [areaId=");
        builder.append(areaId);
        builder.append(", groupId=");
        builder.append(groupId);
        builder.append(", houseId=");
        builder.append(houseId);
        builder.append(", channel=");
        builder.append(channel);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", userMobile=");
        builder.append(userMobile);
        builder.append(", createdUserId=");
        builder.append(createdUserId);
        builder.append(", assignedDealerId=");
        builder.append(assignedDealerId);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append(", recommendId=");
        builder.append(recommendId);
        builder.append("]");
        return builder.toString();
    }
}
