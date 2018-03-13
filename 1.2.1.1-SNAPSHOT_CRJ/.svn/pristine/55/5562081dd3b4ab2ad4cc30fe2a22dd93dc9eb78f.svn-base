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
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.service.IPayOrderService;
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
@Component("PayOrder.DoingInOrderNode")
public class DoingInOrderNode extends SubOrderTypeNode implements IDoingInOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(DoingInOrderNode.class);
    
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
        logger.info("订单执行");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        PayOrderDto payOrderDto = (PayOrderDto) variables.get(ActivitiVariableDef.PAY_ORDER);
        payOrderDto.clearAssignedDealer();
        // 客服角色
        payOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        payOrderDto.setState(SubOrderStateDef.DO_IN_ORDER);
        payOrderDto.setStateDate(DateUtil.getDBDateTime());
        payOrderService.updateSubOrderWithTrans(payOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
