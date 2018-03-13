package com.room1000.suborder.ownercancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ICompleteOrderNodeProcess;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.workorder.define.WorkOrderStateDef;
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
@Component("OwnerCancelLeaseOrder.CompleteOrderNode")
public class CompleteOrderNode extends SubOrderTypeNode implements ICompleteOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CompleteOrderNode.class);

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
        logger.info("订单关闭");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);
        ownerCancelLeaseOrderService.updateSubOrderStateWithTrans(ownerCancelLeaseOrderDto.getCode(),
            SubOrderStateDef.CLOSED);

        workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
        workOrderDto.setState(WorkOrderStateDef.DONE);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
