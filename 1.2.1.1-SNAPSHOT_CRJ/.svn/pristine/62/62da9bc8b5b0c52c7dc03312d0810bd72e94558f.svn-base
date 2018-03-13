package com.room1000.suborder.cancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IFinanceAuditNodeProcess;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("CancelLeaseOrder.FinanceAuditNode")
public class FinanceAuditNode extends SubOrderTypeNode implements IFinanceAuditNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(FinanceAuditNode.class);

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

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("财务审核");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        cancelLeaseOrderDto.clearAssignedDealer();
        // 财务 角色
        cancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
        cancelLeaseOrderDto.setState(SubOrderStateDef.FINANCE_AUDITING);
        cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        cancelLeaseService.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
        workOrderDto.setSubOrderState(SubOrderStateDef.FINANCE_AUDITING);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
