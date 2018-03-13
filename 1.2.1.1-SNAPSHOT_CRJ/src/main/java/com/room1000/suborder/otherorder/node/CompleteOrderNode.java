package com.room1000.suborder.otherorder.node;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ICompleteOrderNodeProcess;
import com.room1000.suborder.otherorder.dto.OtherOrderDto;
import com.room1000.suborder.otherorder.service.IOtherOrderService;
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
@Component("OtherOrder.CompleteOrderNode")
public class CompleteOrderNode extends SubOrderTypeNode implements ICompleteOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CompleteOrderNode.class);

    /**
     * otherOrderService
     */
    @Autowired
    private IOtherOrderService otherOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("订单关闭");

        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OtherOrderDto otherOrderDto = (OtherOrderDto) variables.get(ActivitiVariableDef.OTHER_ORDER);
        
        otherOrderDto.clearAssignedDealer();
        otherOrderDto.setState(SubOrderStateDef.CLOSED);
        otherOrderDto.setStateDate(DateUtil.getDBDateTime());
        otherOrderService.updateSubOrderWithTrans(otherOrderDto, null, "");
        
        workOrderDto.clearSubField();
        workOrderDto.setState(WorkOrderStateDef.DONE);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
