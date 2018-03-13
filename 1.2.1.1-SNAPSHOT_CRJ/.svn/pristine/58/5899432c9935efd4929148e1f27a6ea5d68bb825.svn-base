package com.room1000.suborder.ownercancelleaseorder.dto.request;

import java.util.List;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;

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
public class OwnerCancelLeaseOrderOperationRequest {

    /**
     * code
     */
    private String code;
    
    /**
     * newButlerId
     */
    private Long newButlerId;
    
    /**
     * 处理员工id
     */
    private Long dealerId;
    
    /**
     * cancelLeaseOrderValueList
     */
    private List<OwnerCancelLeaseOrderValueDto> ownerCancelLeaseOrderValueList;
    
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

    public Long getNewButlerId() {
        return newButlerId;
    }

    public void setNewButlerId(Long newButlerId) {
        this.newButlerId = newButlerId;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public List<OwnerCancelLeaseOrderValueDto> getOwnerCancelLeaseOrderValueList() {
        return ownerCancelLeaseOrderValueList;
    }

    public void setOwnerCancelLeaseOrderValueList(List<OwnerCancelLeaseOrderValueDto> ownerCancelLeaseOrderValueList) {
        this.ownerCancelLeaseOrderValueList = ownerCancelLeaseOrderValueList;
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
        builder.append("OwnerCancelLeaseOrderOperationRequest [code=");
        builder.append(code);
        builder.append(", newButlerId=");
        builder.append(newButlerId);
        builder.append(", dealerId=");
        builder.append(dealerId);
        builder.append(", ownerCancelLeaseOrderValueList=");
        builder.append(ownerCancelLeaseOrderValueList);
        builder.append(", submitFlag=");
        builder.append(submitFlag);
        builder.append("]");
        return builder.toString();
    }
    
    
}
