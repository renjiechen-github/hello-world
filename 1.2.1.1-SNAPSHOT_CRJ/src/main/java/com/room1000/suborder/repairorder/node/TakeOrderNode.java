package com.room1000.suborder.repairorder.node;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ITakeOrderNodeProcess;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;
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
@Component("RepairOrder.TakeOrderNode")
public class TakeOrderNode extends SubOrderTypeNode implements ITakeOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(TakeOrderNode.class);
    
    /**
     * repairOrderService
     */
    @Autowired
    private IRepairOrderService repairOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("接单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        RepairOrderDto repairOrderDto = (RepairOrderDto) variables.get(ActivitiVariableDef.REPAIR_ORDER);

        repairOrderDto.clearAssignedDealer();
        repairOrderDto.setAssignedDealerId(repairOrderDto.getStaffId());
        repairOrderDto.setState(SubOrderStateDef.TAKE_ORDER);
        repairOrderDto.setStateDate(DateUtil.getDBDateTime());
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderDto.setSubAssignedDealerId(repairOrderDto.getAssignedDealerId());
        
        repairOrderService.updateSubOrderWithTrans(repairOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, repairOrderDto.getAssignedDealerId(), OrderPushModelDef.REPAIR_ORDER);
    }

}
