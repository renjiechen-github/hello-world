package com.room1000.suborder.otherorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
import com.room1000.suborder.otherorder.dto.OtherOrderDto;
import com.room1000.suborder.otherorder.service.IOtherOrderService;
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
@Component("OtherOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);

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
        logger.info("订单执行");

        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OtherOrderDto otherOrderDto = (OtherOrderDto) variables.get(ActivitiVariableDef.OTHER_ORDER);

        otherOrderDto.clearAssignedDealer();
        otherOrderDto.setAssignedDealerId(otherOrderDto.getStaffId());
        otherOrderDto.setState(SubOrderStateDef.DO_IN_ORDER);
        otherOrderDto.setStateDate(DateUtil.getDBDateTime());
        otherOrderService.updateSubOrderWithTrans(otherOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        workOrderDto.setSubAssignedDealerId(otherOrderDto.getAssignedDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, otherOrderDto.getAssignedDealerId(), OrderPushModelDef.OTHER_ORDER);
    }

}
