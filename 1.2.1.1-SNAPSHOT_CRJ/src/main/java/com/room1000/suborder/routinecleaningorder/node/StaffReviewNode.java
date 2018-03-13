package com.room1000.suborder.routinecleaningorder.node;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.room1000.core.activiti.IProcessAction;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderDto;
import com.room1000.suborder.routinecleaningorder.service.IRoutineCleaningOrderService;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

import pccom.common.util.SpringHelper;

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
public class StaffReviewNode implements IProcessAction {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(StaffReviewNode.class);

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    // @Override
    // public void notify(DelegateExecution execution) throws Exception {
    //
    // }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("客服回访");

        WorkOrderDto workOrderDto = (WorkOrderDto) delegateTask.getVariable(ActivitiVariableDef.WORK_ORDER);
        RoutineCleaningOrderDto routineCleaningOrderDto = (RoutineCleaningOrderDto) delegateTask
            .getVariable(ActivitiVariableDef.ROUTINE_CLEANING_ORDER);

        IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        IRoutineCleaningOrderService routineCleaningOrderService = (IRoutineCleaningOrderService) SpringHelper
            .getBean("RoutineCleaningOrderService");

        // 客服回访
        routineCleaningOrderDto.clearAssignedDealer();
        routineCleaningOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        routineCleaningOrderDto.setState(SubOrderStateDef.STAFF_REVIEW);
        routineCleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        routineCleaningOrderService.updateSubOrderWithTrans(routineCleaningOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.STAFF_REVIEW);
        workOrderDto.setSubAssignedDealerRoleId(routineCleaningOrderDto.getAssignedDealerRoleId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }

}
