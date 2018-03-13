package com.room1000.suborder.cancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IRentalAccountNodeProcess;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

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
@Component("CancelLeaseOrder.RentalAccountNode")
public class RentalAccountNode extends SubOrderTypeNode implements IRentalAccountNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(RentalAccountNode.class);

    /**
     * cancelLeaseService
     */
    @Autowired
    private ICancelLeaseOrderService cancelLeaseService;

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
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        cancelLeaseOrderDto.clearAssignedDealer();
        // 租务角色
        cancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_RENTAL);
        cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerRoleId(cancelLeaseOrderDto.getAssignedDealerRoleId());

        CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
        cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
        cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.PASSED");
        cancelLeaseOrderValueDto = cancelLeaseService.selectByAttrPath(cancelLeaseOrderValueDto);
        if (cancelLeaseOrderValueDto != null
            && BooleanFlagDef.BOOLEAN_FALSE.equals(cancelLeaseOrderValueDto.getTextInput())) {
            cancelLeaseOrderDto.setState(SubOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING);
            workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING);
        }
        else {
            cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
            cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
            cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.FINANCE_AUDIT.PASSED");
            cancelLeaseOrderValueDto = cancelLeaseService.selectByAttrPath(cancelLeaseOrderValueDto);
            if (cancelLeaseOrderValueDto != null
                && BooleanFlagDef.BOOLEAN_FALSE.equals(cancelLeaseOrderValueDto.getTextInput())) {
                cancelLeaseOrderDto.setState(SubOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING);
                workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING);
            }
            else {
                cancelLeaseOrderDto.setState(SubOrderStateDef.RENTAL_ACCOUNTING);
                workOrderDto.setSubOrderState(SubOrderStateDef.RENTAL_ACCOUNTING);
            }
        }
        cancelLeaseService.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
