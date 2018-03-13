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
public class GetWorkOrderDetailRequest {

    /**
     * workOrderId
     */
    private Long workOrderId;
    
    /**
     * 如果只查询子订单信息，则置为Y；空或者N则查询全量信息（全量信息的查询速度有欠缺）
     */
    private String singleDetailFlag;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getSingleDetailFlag() {
        return singleDetailFlag;
    }

    public void setSingleDetailFlag(String singleDetailFlag) {
        this.singleDetailFlag = singleDetailFlag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GetWorkOrderDetailRequest [workOrderId=");
        builder.append(workOrderId);
        builder.append(", singleDetailFlag=");
        builder.append(singleDetailFlag);
        builder.append("]");
        return builder.toString();
    }
    
    
}
