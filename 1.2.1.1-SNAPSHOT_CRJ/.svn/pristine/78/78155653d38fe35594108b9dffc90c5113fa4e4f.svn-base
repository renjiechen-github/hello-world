package com.room1000.suborder.ownerrepairorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueDto;
import com.room1000.suborder.ownerrepairorder.service.IOwnerRepairOrderService;

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
public class CheckNeedReassign implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        IOwnerRepairOrderService ownerRepairOrderService = (IOwnerRepairOrderService) SpringHelper.getBean("OwnerRepairOrderService");
        OwnerRepairOrderDto ownerRepairOrderDto = (OwnerRepairOrderDto) variables.get("ownerRepairOrder");
        
        OwnerRepairOrderValueDto ownerRepairOrderValueDto = new OwnerRepairOrderValueDto();
        ownerRepairOrderValueDto.setSubOrderId(ownerRepairOrderDto.getId());
        ownerRepairOrderValueDto.setAttrPath("OWNER_REPAIR_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        ownerRepairOrderValueDto = ownerRepairOrderService.selectByAttrPath(ownerRepairOrderValueDto);
        if (ownerRepairOrderValueDto == null || BooleanFlagDef.BOOLEAN_FALSE.equals(ownerRepairOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
