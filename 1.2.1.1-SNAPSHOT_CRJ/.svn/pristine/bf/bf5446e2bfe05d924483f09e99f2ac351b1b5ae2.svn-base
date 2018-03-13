package com.room1000.suborder.cleaningorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderValueDto;
import com.room1000.suborder.cleaningorder.service.ICleaningOrderService;

import pccom.common.util.SpringHelper;

/**
 * 
 * Description: 重新指派订单的逻辑
 *  
 * Created on 2017年3月9日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Deprecated
public class CheckNeedReassign implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        ICleaningOrderService cleaningOrderService = (ICleaningOrderService) SpringHelper.getBean("CleaningOrderService");
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get("cleaningOrder");
        
        CleaningOrderValueDto cleaningOrderValueDto = new CleaningOrderValueDto();
        cleaningOrderValueDto.setSubOrderId(cleaningOrderDto.getId());
        cleaningOrderValueDto.setAttrPath("CLEANING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        cleaningOrderValueDto = cleaningOrderService.selectByAttrPath(cleaningOrderValueDto);
        if (cleaningOrderValueDto == null || BooleanFlagDef.BOOLEAN_FALSE.equals(cleaningOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
