package com.room1000.suborder.cancelleaseorder.dto.request;

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
public class RentalMoneyCalculateRequest {

    /**
     * cancelLeaseOrderId
     */
    private Long cancelLeaseOrderId;

    public Long getCancelLeaseOrderId() {
        return cancelLeaseOrderId;
    }

    public void setCancelLeaseOrderId(Long cancelLeaseOrderId) {
        this.cancelLeaseOrderId = cancelLeaseOrderId;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RentalMoneyCalculateRequest [cancelLeaseOrderId=");
        builder.append(cancelLeaseOrderId);
        builder.append("]");
        return builder.toString();
    }
    
    
}
