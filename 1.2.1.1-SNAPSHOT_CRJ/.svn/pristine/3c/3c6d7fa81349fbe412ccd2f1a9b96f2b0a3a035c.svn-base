package com.room1000.suborder.complaintorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderDto;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderValueDto;
import com.room1000.suborder.complaintorder.service.IComplaintOrderService;

import pccom.common.util.SpringHelper;

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
public class CheckNeedNotReassign implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        IComplaintOrderService complaintOrderService = (IComplaintOrderService) SpringHelper.getBean("ComplaintOrderService");
        ComplaintOrderDto complaintOrderDto = (ComplaintOrderDto) variables.get("complaintOrder");
        
        ComplaintOrderValueDto complaintOrderValueDto = new ComplaintOrderValueDto();
        complaintOrderValueDto.setSubOrderId(complaintOrderDto.getId());
        complaintOrderValueDto.setAttrPath("COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        complaintOrderValueDto = complaintOrderService.selectByAttrPath(complaintOrderValueDto);
        if (complaintOrderValueDto == null || BooleanFlagDef.BOOLEAN_TRUE.equals(complaintOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
