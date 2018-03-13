package com.room1000.suborder.houselookingorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderValueDto;
import com.room1000.suborder.houselookingorder.service.IHouseLookingOrderService;

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
        IHouseLookingOrderService houseLookingOrderService = (IHouseLookingOrderService) SpringHelper.getBean("HouseLookingOrderService");
        HouseLookingOrderDto houseLookingOrderDto = (HouseLookingOrderDto) variables.get("houseLookingOrder");
        
        HouseLookingOrderValueDto houseLookingOrderValueDto = new HouseLookingOrderValueDto();
        houseLookingOrderValueDto.setSubOrderId(houseLookingOrderDto.getId());
        houseLookingOrderValueDto.setAttrPath("HOUSE_LOOKING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        houseLookingOrderValueDto = houseLookingOrderService.selectByAttrPath(houseLookingOrderValueDto);
        if (houseLookingOrderValueDto == null || BooleanFlagDef.BOOLEAN_FALSE.equals(houseLookingOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
