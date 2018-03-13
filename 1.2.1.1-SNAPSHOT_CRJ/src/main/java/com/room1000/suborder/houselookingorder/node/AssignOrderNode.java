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
public class AssignOrderNode implements IProcessAction {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AssignOrderNode.class);

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

//    @Override
//    public void notify(DelegateExecution execution) throws Exception {
//
//    }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("指派订单");
        WorkOrderDto workOrderDto = (WorkOrderDto) delegateTask.getVariable(ActivitiVariableDef.WORK_ORDER);
        HouseLookingOrderDto houseLookingOrderDto = (HouseLookingOrderDto) delegateTask
            .getVariable(ActivitiVariableDef.HOUSE_LOOKING_ORDER);

        IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        IHouseLookingOrderService houseLookingOrderService = (IHouseLookingOrderService) SpringHelper
            .getBean("HouseLookingOrderService");

        // 指定客服处理
        houseLookingOrderDto.clearAssignedDealer();
        houseLookingOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        houseLookingOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);

        houseLookingOrderService.updateSubOrderWithTrans(houseLookingOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {

    }

}
