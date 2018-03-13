package com.room1000.suborder.houselookingorder.node;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.room1000.core.activiti.IProcessAction;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.service.IHouseLookingOrderService;
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
    private static final Logger logger = LoggerFactory.getLogger(StaffReviewNode.class);

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
        logger.debug("客服回访");

        WorkOrderDto workOrderDto = (WorkOrderDto) delegateTask.getVariable(ActivitiVariableDef.WORK_ORDER);
        HouseLookingOrderDto houseLookingOrderDto = (HouseLookingOrderDto) delegateTask
            .getVariable(ActivitiVariableDef.HOUSE_LOOKING_ORDER);

        IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        IHouseLookingOrderService houseLookingOrderService = (IHouseLookingOrderService) SpringHelper
            .getBean("HouseLookingOrderService");

        // 客服回访
        houseLookingOrderDto.clearAssignedDealer();
        houseLookingOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        houseLookingOrderDto.setState(SubOrderStateDef.STAFF_REVIEW);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());
        houseLookingOrderService.updateSubOrderWithTrans(houseLookingOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.STAFF_REVIEW);
        workOrderDto.setSubAssignedDealerRoleId(houseLookingOrderDto.getAssignedDealerRoleId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }

}
