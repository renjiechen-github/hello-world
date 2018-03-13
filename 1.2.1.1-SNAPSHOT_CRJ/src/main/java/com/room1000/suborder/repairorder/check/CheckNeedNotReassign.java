package com.room1000.suborder.repairorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.dto.RepairOrderValueDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;

import pccom.common.util.SpringHelper;

/**
 * 
 * Description: 不需要重新指派订单的逻辑
 *  
 * Created on 2017年3月9日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Deprecated
public class CheckNeedNotReassign implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        IRepairOrderService repairOrderService = (IRepairOrderService) SpringHelper.getBean("RepairOrderService");
        RepairOrderDto repairOrderDto = (RepairOrderDto) variables.get("repairOrder");
        
        RepairOrderValueDto repairOrderValueDto = new RepairOrderValueDto();
        repairOrderValueDto.setSubOrderId(repairOrderDto.getId());
        repairOrderValueDto.setAttrPath("REPAIR_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        repairOrderValueDto = repairOrderService.selectByAttrPath(repairOrderValueDto);
        if (repairOrderValueDto != null && BooleanFlagDef.BOOLEAN_TRUE.equals(repairOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
