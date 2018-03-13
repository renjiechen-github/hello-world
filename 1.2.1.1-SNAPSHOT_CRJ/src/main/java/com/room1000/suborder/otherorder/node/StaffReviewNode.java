package com.room1000.suborder.otherorder.node;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.room1000.core.activiti.IProcessAction;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.otherorder.dto.OtherOrderDto;
import com.room1000.suborder.otherorder.service.IOtherOrderService;
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

//    @Override
//    public void notify(DelegateExecution execution) throws Exception {
//        
//    }

    @Override
    public void notify(DelegateTask delegateTask) {
        logger.info("客服回访");
        
        WorkOrderDto workOrderDto = (WorkOrderDto) delegateTask.getVariable(ActivitiVariableDef.WORK_ORDER);
        OtherOrderDto otherOrderDto = (OtherOrderDto) delegateTask.getVariable(ActivitiVariableDef.OTHER_ORDER);
        
        IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        IOtherOrderService otherOrderService = (IOtherOrderService) SpringHelper.getBean("OtherOrderService");
        
        // 客服回访
        otherOrderDto.clearAssignedDealer();
        otherOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        otherOrderDto.setState(SubOrderStateDef.STAFF_REVIEW);
        otherOrderDto.setStateDate(DateUtil.getDBDateTime());
        otherOrderService.updateSubOrderWithTrans(otherOrderDto, null, "");
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.STAFF_REVIEW);
        workOrderDto.setSubAssignedDealerRoleId(otherOrderDto.getAssignedDealerRoleId());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        
    }

}
