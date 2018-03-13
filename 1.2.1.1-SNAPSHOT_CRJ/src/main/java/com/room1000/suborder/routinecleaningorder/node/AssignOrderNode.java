package com.room1000.suborder.routinecleaningorder.node;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IAssignedOrderNodeProcess;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;
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
@Component("RoutineCleaningOrder.AssignOrderNode")
public class AssignOrderNode extends SubOrderTypeNode implements IAssignedOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AssignOrderNode.class);
    
    /**
     * routineCleaningOrderService
     */
    @Autowired
    private IRoutineCleaningOrderService routineCleaningOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("指派订单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        RoutineCleaningOrderDto routineCleaningOrderDto =  (RoutineCleaningOrderDto) variables.get(ActivitiVariableDef.ROUTINE_CLEANING_ORDER);
        
        // 指定客服处理
        routineCleaningOrderDto.clearAssignedDealer();
        routineCleaningOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        routineCleaningOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
        routineCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        
        routineCleaningOrderService.updateSubOrderWithTrans(routineCleaningOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
