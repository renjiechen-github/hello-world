package com.room1000.suborder.cleaningorder.check;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.define.ActivitiVariableDef;

/**
 * 
 * Description: 需要支付的逻辑校验
 *  
 * Created on 2017年3月9日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component(value = "CleaningOrderCheckNeedPay")
public class CheckNeedPay implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get(ActivitiVariableDef.CLEANING_ORDER);
        
        if (cleaningOrderDto.getCleaningMoney() != null && cleaningOrderDto.getCleaningMoney() > 0) {
            return true;
        }
        return false;
    }

}
