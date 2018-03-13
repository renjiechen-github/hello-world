package com.room1000.suborder.livingproblemorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderDto;
import com.room1000.suborder.livingproblemorder.service.ILivingProblemOrderService;
import com.room1000.suborder.node.IAssignedOrderNodeProcess;
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
@Component("LivingProblemOrder.AssignOrderNode")
public class AssignOrderNode extends SubOrderTypeNode implements IAssignedOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AssignOrderNode.class);

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
        logger.info("指派订单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        LivingProblemOrderDto livingProblemOrderDto = (LivingProblemOrderDto) variables
            .get(ActivitiVariableDef.LIVING_PROBLEM_ORDER);

        // 指定客服处理
        livingProblemOrderDto.clearAssignedDealer();
        livingProblemOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        livingProblemOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
        livingProblemOrderDto.setStateDate(DateUtil.getDBDateTime());

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);

        livingProblemOrderService.updateSubOrderWithTrans(livingProblemOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
