package com.room1000.workorder.dto.request;

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
public class AuditBrokerOrderRequest {

    /**
     * workOrderId
     */
    private Long workOrderId;
    
    /**
     * passed 通过与否，Y是通过，N是未通过
     */
    private String passed;
    
    /**
     * 当前处理人，为移动端准备
     */
    private Long dealerId;
    
    /**
     * 备注
     */
    private String comments;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getPassed() {
        return passed;
    }

    public void setPassed(String passed) {
        this.passed = passed;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AuditBrokerOrderRequest [workOrderId=");
        builder.append(workOrderId);
        builder.append(", passed=");
        builder.append(passed);
        builder.append(", dealerId=");
        builder.append(dealerId);
        builder.append(", comments=");
        builder.append(comments);
        builder.append("]");
        return builder.toString();
    }
}
