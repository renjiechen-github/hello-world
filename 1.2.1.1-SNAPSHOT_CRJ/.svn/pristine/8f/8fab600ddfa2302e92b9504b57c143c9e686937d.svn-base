package com.room1000.suborder.ownercancelleaseorder.check;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.workorder.dto.WorkOrderDto;

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
@Component(value = "OwnerCancelLeaseOrderCheckNeedPay")
public class CheckNeedPay implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        if (workOrderDto.getPayableMoney() != null && !"0".equals(workOrderDto.getPayableMoney())) {
            return true;
        }
        return false;
    }

}
