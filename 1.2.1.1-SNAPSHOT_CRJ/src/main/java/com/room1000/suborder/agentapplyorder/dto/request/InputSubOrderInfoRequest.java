package com.room1000.suborder.agentapplyorder.dto.request;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Description: 录入信息
 *  
 * Created on 2017年6月22日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class InputSubOrderInfoRequest {

    /**
     * 可以不传，默认当前时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentDate;
    
    /**
     * 传递客户ID，非手机号码
     */
    private Long userId;

    /**
     * 备注
     */
    private String comments;

    /**
     * 用户身份证姓名
     */
    private String userName;

    /**
     * 身份证号码
     */
    private String certNbr;

    /**
     * 用户手机号，如果不传，根据userId查询
     */
    private String userPhone;

    /**
     * 可以不传，默认以申请经纪人_姓名来命名
     */
    private String orderName;

    /**
     * 身份信息照片，多个照片间用逗号分隔
     */
    private String imageUrl;

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getCertNbr() {
        return certNbr;
    }

    public void setCertNbr(String certNbr) {
        this.certNbr = certNbr;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("InputSubOrderInfoRequest [appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", userId=");
        builder.append(userId);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", userName=");
        builder.append(userName);
        builder.append(", certNbr=");
        builder.append(certNbr);
        builder.append(", userPhone=");
        builder.append(userPhone);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append(", imageUrl=");
        builder.append(imageUrl);
        builder.append("]");
        return builder.toString();
    }
    
}
