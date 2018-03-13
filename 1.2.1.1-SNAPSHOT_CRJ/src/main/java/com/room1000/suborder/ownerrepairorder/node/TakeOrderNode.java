package com.room1000.suborder.ownerrepairorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ITakeOrderNodeProcess;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.service.IOwnerRepairOrderService;
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
@Component("OwnerRepairOrder.TakeOrderNode")
public class TakeOrderNode extends SubOrderTypeNode implements ITakeOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(TakeOrderNode.class);

    /**
     * ownerRepairOrderService
     */
    @Autowired
    private IOwnerRepairOrderService ownerRepairOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("接单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OwnerRepairOrderDto ownerRepairOrderDto = (OwnerRepairOrderDto) variables
            .get(ActivitiVariableDef.OWNER_REPAIR_ORDER);

        ownerRepairOrderDto.clearAssignedDealer();
        ownerRepairOrderDto.setAssignedDealerId(ownerRepairOrderDto.getStaffId());
        ownerRepairOrderDto.setState(SubOrderStateDef.TAKE_ORDER);
        ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderDto.setSubAssignedDealerId(ownerRepairOrderDto.getAssignedDealerId());

        ownerRepairOrderService.updateSubOrderWithTrans(ownerRepairOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, ownerRepairOrderDto.getAssignedDealerId(),
            OrderPushModelDef.OWNER_REPAIR_ORDER);
    }

}
