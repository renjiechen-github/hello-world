package com.room1000.suborder.payorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IManagerAuditNodeProcess;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.service.IPayOrderService;
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
@Component("PayOrder.OrderAuditNode")
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
     * payOrderService
     */
    @Autowired
    private IPayOrderService payOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("订单审批");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        PayOrderDto payOrderDto = (PayOrderDto) variables.get(ActivitiVariableDef.PAY_ORDER);
        payOrderDto.clearAssignedDealer();
        // 运营角色
        payOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_OPERATOR);
        payOrderDto.setState(SubOrderStateDef.ORDER_AUDIT);
        payOrderDto.setStateDate(DateUtil.getDBDateTime());
        payOrderService.updateSubOrderWithTrans(payOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_OPERATOR);
        workOrderDto.setSubOrderState(SubOrderStateDef.ORDER_AUDIT);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
