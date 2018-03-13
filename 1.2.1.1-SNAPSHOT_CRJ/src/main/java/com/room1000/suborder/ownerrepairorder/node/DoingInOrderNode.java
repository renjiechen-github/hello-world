package com.room1000.suborder.ownerrepairorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.service.IOwnerRepairOrderService;
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
@Component("OwnerRepairOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);

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
        logger.info("订单执行");

        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OwnerRepairOrderDto ownerRepairOrderDto = (OwnerRepairOrderDto) variables
            .get(ActivitiVariableDef.OWNER_REPAIR_ORDER);

        ownerRepairOrderDto.clearAssignedDealer();
        ownerRepairOrderDto.setAssignedDealerId(ownerRepairOrderDto.getStaffId());
        ownerRepairOrderDto.setState(SubOrderStateDef.DO_IN_ORDER);
        ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());
        ownerRepairOrderService.updateSubOrderWithTrans(ownerRepairOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        workOrderDto.setSubAssignedDealerId(ownerRepairOrderDto.getAssignedDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, ownerRepairOrderDto.getAssignedDealerId(),
//            OrderPushModelDef.OWNER_REPAIR_ORDER);
    }

}
