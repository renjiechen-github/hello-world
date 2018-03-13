package com.room1000.suborder.cancelleaseorder.check;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.cancelleaseorder.define.CancelLeaseOrderTypeDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.define.ActivitiVariableDef;

/**
 * 
 * Description: 
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component(value = "CancelLeaseOrderCheckIsBreakContractWithOutLiving")
public class CheckIsBreakContractWithOutLiving implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables.get(ActivitiVariableDef.CANCEL_LEASE_ORDER);
        if (CancelLeaseOrderTypeDef.BREAK_CONTRACT_WITHOUT_LIVING.equals(cancelLeaseOrderDto.getType())) {
            return true;
        }
        return false;
    }

}
