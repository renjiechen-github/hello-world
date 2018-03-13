package com.room1000.suborder.livingproblemorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderDto;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderValueDto;
import com.room1000.suborder.livingproblemorder.service.ILivingProblemOrderService;

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
        ILivingProblemOrderService livingProblemOrderService = (ILivingProblemOrderService) SpringHelper.getBean("LivingProblemOrderService");
        LivingProblemOrderDto livingProblemOrderDto = (LivingProblemOrderDto) variables.get("livingProblemOrder");
        
        LivingProblemOrderValueDto livingProblemOrderValueDto = new LivingProblemOrderValueDto();
        livingProblemOrderValueDto.setSubOrderId(livingProblemOrderDto.getId());
        livingProblemOrderValueDto.setAttrPath("LIVING_PROBLEM_ORDER_PROCESS.CUSTOMER_SERV_VISIT.PASSED");
        livingProblemOrderValueDto = livingProblemOrderService.selectByAttrPath(livingProblemOrderValueDto);
        if (livingProblemOrderValueDto == null || BooleanFlagDef.BOOLEAN_FALSE.equals(livingProblemOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
