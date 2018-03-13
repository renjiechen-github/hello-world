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
import com.room1000.suborder.node.ITakeOrderNodeProcess;
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
@Component("ComplaintOrder.TakeOrderNode")
public class TakeOrderNode extends SubOrderTypeNode implements ITakeOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(TakeOrderNode.class);

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
        logger.info("接单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        ComplaintOrderDto complaintOrderDto = (ComplaintOrderDto) variables.get(ActivitiVariableDef.COMPLAINT_ORDER);

        complaintOrderDto.clearAssignedDealer();
        complaintOrderDto.setAssignedDealerId(complaintOrderDto.getStaffId());
        complaintOrderDto.setState(SubOrderStateDef.TAKE_ORDER);
        complaintOrderDto.setStateDate(DateUtil.getDBDateTime());
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderDto.setSubAssignedDealerId(complaintOrderDto.getAssignedDealerId());
        
        complaintOrderService.updateSubOrderWithTrans(complaintOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, complaintOrderDto.getAssignedDealerId(), OrderPushModelDef.COMPLAINT_ORDER);
    }

}
