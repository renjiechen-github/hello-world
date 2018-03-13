package com.room1000.suborder.repairorder.check;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;

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
@Component(value = "RepairOrderCheckNeedPay")
public class CheckNeedPay implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        RepairOrderDto repairOrderDto = (RepairOrderDto) variables.get(ActivitiVariableDef.REPAIR_ORDER);
        
        if (repairOrderDto.getPayableMoney() > repairOrderDto.getPaidMoney()) {
            return true;
        }
        return false;
    }

}
