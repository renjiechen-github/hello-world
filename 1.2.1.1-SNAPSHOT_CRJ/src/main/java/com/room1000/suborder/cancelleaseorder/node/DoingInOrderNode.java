package com.room1000.suborder.cancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
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
@Component("CancelLeaseOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);

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
        logger.debug("管家上门");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();

        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
        cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
        cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.PASSED");
        cancelLeaseOrderValueDto = cancelLeaseService.selectByAttrPath(cancelLeaseOrderValueDto);
        if (cancelLeaseOrderValueDto != null
            && BooleanFlagDef.BOOLEAN_FALSE.equals(cancelLeaseOrderValueDto.getTextInput())) {
            cancelLeaseService.updateSubOrderStateWithTrans(cancelLeaseOrderDto.getCode(),
                SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
            workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
        }
        else {
            cancelLeaseService.updateSubOrderStateWithTrans(cancelLeaseOrderDto.getCode(),
                SubOrderStateDef.DO_IN_ORDER);
            workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        }
        workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, cancelLeaseOrderDto.getAssignedDealerId(),
//            OrderPushModelDef.CANCEL_LEASE_ORDER);
    }

}
