package com.room1000.suborder.repairorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;

/**
 * 
 * Description: 不需要支付的逻辑校验
 *  
 * Created on 2017年3月9日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Deprecated
public class CheckNeedNotPay implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        RepairOrderDto repairOrderDto = (RepairOrderDto) variables.get("repairOrder");
        
        if (repairOrderDto.getPayableMoney() <= repairOrderDto.getPaidMoney()) {
            return true;
        }
        return false;
    }

}
