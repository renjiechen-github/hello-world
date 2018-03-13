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
import com.room1000.suborder.node.IWait2PayNodeProcess;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.service.IPayOrderService;
import com.room1000.workorder.define.WorkOrderStateDef;
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
@Component("PayOrder.Wait2PayNode")
public class Wait2PayNode extends SubOrderTypeNode implements IWait2PayNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2PayNode.class);
    
    /**
     * payOrderService
     */
    @Autowired
    private IPayOrderService payOrderService;
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("待支付完成");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        PayOrderDto payOrderDto = (PayOrderDto) variables.get(ActivitiVariableDef.PAY_ORDER);

        payOrderDto.clearAssignedDealer();
        // 财务
        payOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
        payOrderDto.setState(SubOrderStateDef.REFUND);
        payOrderDto.setStateDate(DateUtil.getDBDateTime());

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.REFUND);
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_PAY);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_FINANCIAL);
        payOrderService.updateSubOrderWithTrans(payOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

    }

}
