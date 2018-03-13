package com.room1000.suborder.agentapplyorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.agentapplyorder.dto.AgentApplyOrderDto;
import com.room1000.suborder.agentapplyorder.service.IAgentApplyOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IManagerAuditNodeProcess;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 订单审核
 *  
 * Created on 2017年6月2日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("AgentApplyOrder.OrderAuditNode")
public class OrderAuditNode extends SubOrderTypeNode implements IManagerAuditNodeProcess {
    
    /**
     * logger
     */
    private Logger logger = LoggerFactory.getLogger(OrderAuditNode.class);
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;
    
    /**
     * agentApplyOrderService
     */
    @Autowired
    private IAgentApplyOrderService agentApplyOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("订单审批");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        AgentApplyOrderDto agentApplyOrderDto = (AgentApplyOrderDto) variables.get(ActivitiVariableDef.AGENT_APPLY_ORDER);
        agentApplyOrderDto.clearAssignedDealer();
        // 运营角色
        agentApplyOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_OPERATOR);
        agentApplyOrderDto.setState(SubOrderStateDef.ORDER_AUDIT);
        agentApplyOrderDto.setStateDate(DateUtil.getDBDateTime());
        agentApplyOrderService.updateSubOrderWithTrans(agentApplyOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_OPERATOR);
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_AUDIT);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
