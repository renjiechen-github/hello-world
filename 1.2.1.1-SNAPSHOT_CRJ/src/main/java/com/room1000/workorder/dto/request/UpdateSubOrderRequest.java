package com.room1000.workorder.dto.request;

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
public class UpdateSubOrderRequest {

    /**
     * workOrderId
     */
    private Long workOrderId;
    
    /**
     * appointmentDate
     */
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date appointmentDate;
    
    /**
     * comments
     */
    private String comments;
    
    /**
     * imageUrl
     */
    private String imageUrl;

    /**
     * orderName
     */
    private String orderName;
    
    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
        builder.append("UpdateSubOrderRequest [workOrderId=");
        builder.append(workOrderId);
        builder.append(", appointmentDate=");
        builder.append(appointmentDate);
        builder.append(", comments=");
        builder.append(comments);
        builder.append(", imageUrl=");
        builder.append(imageUrl);
        builder.append(", orderName=");
        builder.append(orderName);
        builder.append("]");
        return builder.toString();
    }
    
}
