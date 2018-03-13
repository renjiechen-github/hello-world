package com.room1000.suborder.ownerrepairorder.dto.request;

import java.util.List;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueDto;

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
public class OwnerRepairOrderOperationRequest {

    /**
     * code
     */
    private String code;

    /**
     * staffId
     */
    private Long staffId;

    /**
     * 当前处理人，为移动端准备
     */
    private Long dealerId;

    /**
     * subOrderValueList
     */
    private List<OwnerRepairOrderValueDto> subOrderValueList;

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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public List<OwnerRepairOrderValueDto> getSubOrderValueList() {
        return subOrderValueList;
    }

    public void setSubOrderValueList(List<OwnerRepairOrderValueDto> subOrderValueList) {
        this.subOrderValueList = subOrderValueList;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
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
        builder.append("OwnerRepairOrderOperationReq [code=");
        builder.append(code);
        builder.append(", staffId=");
        builder.append(staffId);
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
