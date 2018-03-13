package com.room1000.suborder.ownercancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IFinanceAuditNodeProcess;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
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
@Component("OwnerCancelLeaseOrder.FinanceAuditNode")
public class FinanceAuditNode extends SubOrderTypeNode implements IFinanceAuditNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(FinanceAuditNode.class);

    /**
     * ownerCancelLeaseOrderService
     */
    @Autowired
    private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("财务审核");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);

        ownerCancelLeaseOrderDto.clearAssignedDealer();
        // 财务 角色
        ownerCancelLeaseOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
        ownerCancelLeaseOrderDto.setState(SubOrderStateDef.FINANCE_AUDITING);
        ownerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        ownerCancelLeaseOrderService.updateSubOrderWithTrans(ownerCancelLeaseOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
        workOrderDto.setSubOrderState(SubOrderStateDef.FINANCE_AUDITING);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
