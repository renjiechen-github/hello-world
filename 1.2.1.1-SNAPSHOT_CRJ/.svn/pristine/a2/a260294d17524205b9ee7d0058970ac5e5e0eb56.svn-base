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
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

import pccom.common.util.SpringHelper;

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
@Component("CleaningOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);
    
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
        logger.info("订单执行");
        
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get(ActivitiVariableDef.CLEANING_ORDER);
        
        if (workOrderService == null) {
            workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        }
        if (cleaningOrderService == null) {
            cleaningOrderService = (ICleaningOrderService) SpringHelper.getBean("CleaningOrderService");
        }
        
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setAssignedDealerId(cleaningOrderDto.getStaffId());
        cleaningOrderDto.setState(SubOrderStateDef.DO_IN_ORDER);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        cleaningOrderService.updateSubOrderWithTrans(cleaningOrderDto, null, "");
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        workOrderDto.setSubAssignedDealerId(cleaningOrderDto.getAssignedDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        
//        SubOrderUtil.sendMessage(workOrderDto, cleaningOrderDto.getAssignedDealerId(), OrderPushModelDef.CLEANING_ORDER);
    }

}
