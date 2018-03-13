package com.room1000.suborder.routinecleaningorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderValueDto;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;

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
        IRoutineCleaningOrderService routineCleaningOrderService = (IRoutineCleaningOrderService) SpringHelper.getBean("RoutineCleaningOrderService");
        RoutineCleaningOrderDto routineCleaningOrderDto = (RoutineCleaningOrderDto) variables.get("routineCleaningOrder");
        
        RoutineCleaningOrderValueDto routineCleaningOrderValueDto = new RoutineCleaningOrderValueDto();
        routineCleaningOrderValueDto.setSubOrderId(routineCleaningOrderDto.getId());
        routineCleaningOrderValueDto.setAttrPath("ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        routineCleaningOrderValueDto = routineCleaningOrderService.selectByAttrPath(routineCleaningOrderValueDto);
        if (routineCleaningOrderValueDto != null && BooleanFlagDef.BOOLEAN_TRUE.equals(routineCleaningOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
