package com.room1000.suborder.otherorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.otherorder.dto.OtherOrderDto;
import com.room1000.suborder.otherorder.dto.OtherOrderValueDto;
import com.room1000.suborder.otherorder.service.IOtherOrderService;

import pccom.common.util.SpringHelper;

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
public class CheckNeedNotReassign implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        IOtherOrderService otherOrderService = (IOtherOrderService) SpringHelper.getBean("OtherOrderService");
        OtherOrderDto otherOrderDto = (OtherOrderDto) variables.get("otherOrder");
        
        OtherOrderValueDto otherOrderValueDto = new OtherOrderValueDto();
        otherOrderValueDto.setSubOrderId(otherOrderDto.getId());
        otherOrderValueDto.setAttrPath("OTHER_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        otherOrderValueDto = otherOrderService.selectByAttrPath(otherOrderValueDto);
        if (otherOrderValueDto != null && BooleanFlagDef.BOOLEAN_TRUE.equals(otherOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
