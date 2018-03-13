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
import com.room1000.suborder.node.ICompleteOrderNodeProcess;
import com.room1000.workorder.define.WorkOrderStateDef;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("AgentApplyOrder.CompleteOrderNode")
public class CompleteOrderNode extends SubOrderTypeNode implements ICompleteOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(CompleteOrderNode.class);

    /**
     * agentApplyOrderService
     */
    @Autowired
    private IAgentApplyOrderService agentApplyOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.debug("订单关闭");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        AgentApplyOrderDto agentApplyOrderDto = (AgentApplyOrderDto) variables.get(ActivitiVariableDef.AGENT_APPLY_ORDER);
        agentApplyOrderDto.setState(SubOrderStateDef.CLOSED);
        agentApplyOrderDto.setStateDate(DateUtil.getDBDateTime());
        agentApplyOrderService.updateSubOrderWithTrans(agentApplyOrderDto, null, "");

        workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
        workOrderDto.setState(WorkOrderStateDef.DONE);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
