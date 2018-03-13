package com.room1000.suborder.ownercancelleaseorder.check;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;

/**
 * 
 * Description: 校验高管审批时是否进行了打回
 *  
 * Created on 2017年6月13日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("OwnerCancelLeaseOrderCheckNeedReGetHomeInManagerAudit")
public class CheckNeedReGetHomeInManagerAudit implements IProcessExpression {

    /**
     * ownerCancelLeaseOrderService
     */
    @Autowired
    private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

    @Override
    public boolean getResult(Map<String, Object> variables) {
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);

        OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto = new OwnerCancelLeaseOrderValueDto();
        ownerCancelLeaseOrderValueDto.setSubOrderId(ownerCancelLeaseOrderDto.getId());
        // 高管审批结果
        ownerCancelLeaseOrderValueDto.setAttrPath("OCL_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.PASSED");
        ownerCancelLeaseOrderValueDto = ownerCancelLeaseOrderService.selectByAttrPath(ownerCancelLeaseOrderValueDto);
        if (ownerCancelLeaseOrderValueDto == null
            || BooleanFlagDef.BOOLEAN_FALSE.equals(ownerCancelLeaseOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
