package com.room1000.suborder.ownercancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ITakeOrderNodeProcess;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
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
@Component("OwnerCancelLeaseOrder.TakeOrderNode")
public class TakeOrderNode extends SubOrderTypeNode implements ITakeOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(TakeOrderNode.class);

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
        logger.info("接单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto = workOrderService.getWorkOrderDtoById(workOrderDto.getId());
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = ownerCancelLeaseOrderService
            .getOrderDetailById(workOrderDto.getRefId(), true);
        ownerCancelLeaseOrderService.updateSubOrderStateWithTrans(ownerCancelLeaseOrderDto.getCode(),
            SubOrderStateDef.TAKE_ORDER);

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerId(ownerCancelLeaseOrderDto.getAssignedDealerId());
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, ownerCancelLeaseOrderDto.getAssignedDealerId(),
            OrderPushModelDef.OWNER_CANCEL_LEASE_ORDER);
    }

}
