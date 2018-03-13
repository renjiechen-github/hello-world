package com.room1000.suborder.complaintorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderDto;
import com.room1000.suborder.complaintorder.service.IComplaintOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.ICompleteOrderNodeProcess;
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
@Component("ComplaintOrder.CompleteOrderNode")
public class CompleteOrderNode extends SubOrderTypeNode implements ICompleteOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CompleteOrderNode.class);
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;
    
    /**
     * complaintOrderService
     */
    @Autowired
    private IComplaintOrderService complaintOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("订单关闭");

        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        ComplaintOrderDto complaintOrderDto = (ComplaintOrderDto) variables.get(ActivitiVariableDef.COMPLAINT_ORDER);

        complaintOrderDto.clearAssignedDealer();
        complaintOrderDto.setState(SubOrderStateDef.CLOSED);
        complaintOrderDto.setStateDate(DateUtil.getDBDateTime());
        complaintOrderService.updateSubOrderWithTrans(complaintOrderDto, null, "");
        
        workOrderDto.clearSubField();
        workOrderDto.setState(WorkOrderStateDef.DONE);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
