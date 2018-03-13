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
public class CloseBrokerOrderRequest {

    /**
     * code
     */
    private String code;
    
    /**
     * dealerId
     */
    private Long dealerId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CloseBrokerOrderRequest [code=");
        builder.append(code);
        builder.append(", dealerId=");
        builder.append(dealerId);
        builder.append("]");
        return builder.toString();
    }
}
