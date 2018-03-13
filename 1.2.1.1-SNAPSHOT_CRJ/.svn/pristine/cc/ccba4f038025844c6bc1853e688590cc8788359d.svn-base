package com.room1000.suborder.ownercancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IRentalAccountNodeProcess;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderValueDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

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
@Component("OwnerCancelLeaseOrder.RentalAccountNode")
public class RentalAccountNode extends SubOrderTypeNode implements IRentalAccountNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(RentalAccountNode.class);

    /**
     * ownerCancelLeaseOrderService
     */
    @Autowired
    private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("租务核算");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);

        ownerCancelLeaseOrderDto.clearAssignedDealer();
        // 租务角色
        ownerCancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_RENTAL);
        ownerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerRoleId(ownerCancelLeaseOrderDto.getAssignedDealerRoleId());

        OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto = new OwnerCancelLeaseOrderValueDto();
        ownerCancelLeaseOrderValueDto.setSubOrderId(ownerCancelLeaseOrderDto.getId());
        ownerCancelLeaseOrderValueDto.setAttrPath("OWNER_CANCEL_LEASE_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.PASSED");
        ownerCancelLeaseOrderValueDto = ownerCancelLeaseOrderService.selectByAttrPath(ownerCancelLeaseOrderValueDto);
        if (ownerCancelLeaseOrderValueDto != null
            && BooleanFlagDef.BOOLEAN_FALSE.equals(ownerCancelLeaseOrderValueDto.getTextInput())) {
            ownerCancelLeaseOrderDto.setState(SubOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING);
            workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING);
        }
        else {
            ownerCancelLeaseOrderValueDto = new OwnerCancelLeaseOrderValueDto();
            ownerCancelLeaseOrderValueDto.setSubOrderId(ownerCancelLeaseOrderDto.getId());
            ownerCancelLeaseOrderValueDto.setAttrPath("OWNER_CANCEL_LEASE_ORDER_PROCESS.FINANCE_AUDIT.PASSED");
            ownerCancelLeaseOrderValueDto = ownerCancelLeaseOrderService
                .selectByAttrPath(ownerCancelLeaseOrderValueDto);
            if (ownerCancelLeaseOrderValueDto != null
                && BooleanFlagDef.BOOLEAN_FALSE.equals(ownerCancelLeaseOrderValueDto.getTextInput())) {
                ownerCancelLeaseOrderDto.setState(SubOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING);
                workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING);
            }
            else {
                ownerCancelLeaseOrderDto.setState(SubOrderStateDef.RENTAL_ACCOUNTING);
                workOrderDto.setSubOrderState(SubOrderStateDef.RENTAL_ACCOUNTING);
            }
        }
        ownerCancelLeaseOrderService.updateSubOrderWithTrans(ownerCancelLeaseOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
