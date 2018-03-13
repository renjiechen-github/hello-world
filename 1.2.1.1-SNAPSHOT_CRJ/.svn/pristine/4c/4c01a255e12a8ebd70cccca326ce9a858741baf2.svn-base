package com.room1000.suborder.livingproblemorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderDto;
import com.room1000.suborder.livingproblemorder.service.ILivingProblemOrderService;
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
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
@Component("LivingProblemOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);

    /**
     * livingProblemOrderService
     */
    @Autowired
    private ILivingProblemOrderService livingProblemOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("订单执行");

        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        LivingProblemOrderDto livingProblemOrderDto = (LivingProblemOrderDto) variables
            .get(ActivitiVariableDef.LIVING_PROBLEM_ORDER);

        livingProblemOrderDto.clearAssignedDealer();
        livingProblemOrderDto.setAssignedDealerId(livingProblemOrderDto.getStaffId());
        livingProblemOrderDto.setState(SubOrderStateDef.DO_IN_ORDER);
        livingProblemOrderDto.setStateDate(DateUtil.getDBDateTime());
        livingProblemOrderService.updateSubOrderWithTrans(livingProblemOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        workOrderDto.setSubAssignedDealerId(livingProblemOrderDto.getAssignedDealerId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, livingProblemOrderDto.getAssignedDealerId(),
//            OrderPushModelDef.LIVING_PROBLEM_ORDER);
    }

}
