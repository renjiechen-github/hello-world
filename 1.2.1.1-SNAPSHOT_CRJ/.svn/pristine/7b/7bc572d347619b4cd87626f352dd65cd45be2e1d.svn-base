package com.room1000.suborder.cancelleaseorder.node;


import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessAction;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

import pccom.common.util.SpringHelper;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月8日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("CancelLeaseOrderCreateOrderNode")
public class CreateOrderNode implements IProcessAction {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CreateOrderNode.class);

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * cancelLeaseService
     */
    @Autowired
    private ICancelLeaseOrderService cancelLeaseService;
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;
    
//    @Override
//    public void notify(DelegateExecution execution) throws Exception {
//        
//    }

    @Override
    public void notify(DelegateTask delegateTask) {
        
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.info("生成退租订单");
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) execution.getVariable(ActivitiVariableDef.CANCEL_LEASE_ORDER);
        WorkOrderDto workOrderDto = (WorkOrderDto) execution.getVariable(ActivitiVariableDef.WORK_ORDER);
        
        if (workOrderService == null) {
            workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
        }
        if (cancelLeaseService == null) {
            cancelLeaseService = (ICancelLeaseOrderService) SpringHelper.getBean("CancelLeaseOrderService");
        }
        
        cancelLeaseService.updateSubOrderStateWithTrans(cancelLeaseOrderDto.getCode(), SubOrderStateDef.ORDER_INPUT);
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_INPUT);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
