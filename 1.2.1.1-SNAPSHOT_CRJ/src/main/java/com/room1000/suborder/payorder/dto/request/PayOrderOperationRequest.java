package com.room1000.suborder.payorder.dto.request;

import java.util.List;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.payorder.dto.PayOrderValueDto;

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
public class PayOrderOperationRequest {
    
    /**
     * code
     */
    private String code;

    /**
     * 当前处理人，为移动端准备
     */
    private Long dealerId;
    
    /**
     * subOrderValueList
     */
    private List<PayOrderValueDto> subOrderValueList;

    /**
     * 如果仅是保存，传N，如果是提交，传Y
     */
    private String submitFlag = BooleanFlagDef.BOOLEAN_TRUE;

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

    public List<PayOrderValueDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<PayOrderValueDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public String getSubmitFlag() {
        return submitFlag;
    }

    public void setSubmitFlag(String submitFlag) {
        this.submitFlag = submitFlag;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("PayOrderOperationRequest [code=");
        builder.append(code);
        builder.append(", dealerId=");
        builder.append(dealerId);
        builder.append(", subOrderValueList=");
        builder.append(subOrderValueList);
        builder.append(", submitFlag=");
        builder.append(submitFlag);
        builder.append("]");
        return builder.toString();
    }
    
}
