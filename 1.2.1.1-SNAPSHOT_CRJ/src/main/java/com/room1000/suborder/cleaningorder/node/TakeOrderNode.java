package com.room1000.suborder.cleaningorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.service.ICleaningOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ITakeOrderNodeProcess;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
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
@Component("CleaningOrder.TakeOrderNode")
public class TakeOrderNode extends SubOrderTypeNode implements ITakeOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(TakeOrderNode.class);
    
    /**
     * cleaningOrderService
     */
    @Autowired
    private ICleaningOrderService cleaningOrderService;
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("接单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get(ActivitiVariableDef.CLEANING_ORDER);
        
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setAssignedDealerId(cleaningOrderDto.getStaffId());
        cleaningOrderDto.setState(SubOrderStateDef.TAKE_ORDER);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderDto.setSubAssignedDealerId(cleaningOrderDto.getAssignedDealerId());
        
        cleaningOrderService.updateSubOrderWithTrans(cleaningOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, cleaningOrderDto.getAssignedDealerId(), OrderPushModelDef.CLEANING_ORDER);
    }

}
