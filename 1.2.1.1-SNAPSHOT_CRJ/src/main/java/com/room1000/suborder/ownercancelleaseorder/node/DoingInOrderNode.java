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
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
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
@Component("OwnerCancelLeaseOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);

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
        logger.debug("管家上门");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();

        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);

        OwnerCancelLeaseOrderValueDto ownerCancelLeaseOrderValueDto = new OwnerCancelLeaseOrderValueDto();
        ownerCancelLeaseOrderValueDto.setSubOrderId(ownerCancelLeaseOrderDto.getId());
        ownerCancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.PASSED");
        ownerCancelLeaseOrderValueDto = ownerCancelLeaseOrderService.selectByAttrPath(ownerCancelLeaseOrderValueDto);
        if (ownerCancelLeaseOrderValueDto != null
            && BooleanFlagDef.BOOLEAN_FALSE.equals(ownerCancelLeaseOrderValueDto.getTextInput())) {
            ownerCancelLeaseOrderService.updateSubOrderStateWithTrans(ownerCancelLeaseOrderDto.getCode(),
                SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
            ownerCancelLeaseOrderDto.setState(SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
            workOrderDto.setSubOrderState(SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING);
        }
        else {
            ownerCancelLeaseOrderService.updateSubOrderStateWithTrans(ownerCancelLeaseOrderDto.getCode(),
                SubOrderStateDef.DO_IN_ORDER);
            ownerCancelLeaseOrderDto.setState(SubOrderStateDef.DO_IN_ORDER);
            workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        }
        ownerCancelLeaseOrderDto.clearAssignedDealer();
        ownerCancelLeaseOrderDto.setAssignedDealerId(ownerCancelLeaseOrderDto.getStaffId());
        ownerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        ownerCancelLeaseOrderService.updateSubOrderWithTrans(ownerCancelLeaseOrderDto, null, "");
        workOrderDto.setSubAssignedDealerId(ownerCancelLeaseOrderDto.getAssignedDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, ownerCancelLeaseOrderDto.getAssignedDealerId(),
//            OrderPushModelDef.OWNER_CANCEL_LEASE_ORDER);
    }

}
