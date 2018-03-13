package com.room1000.suborder.cleaningorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderDto;
import com.room1000.suborder.cleaningorder.service.ICleaningOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IAssignedOrderNodeProcess;
import com.room1000.workorder.define.WorkOrderStateDef;
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
@Component("CleaningOrder.AssignOrderNode")
public class AssignOrderNode extends SubOrderTypeNode implements IAssignedOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AssignOrderNode.class);

    /**
     * cleaningOrderService
     */
    @Autowired
    private ICleaningOrderService cleaningOrderService;
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("指派订单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get(ActivitiVariableDef.CLEANING_ORDER);

        // 指定客服处理
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        cleaningOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
        workOrderDto.setState(WorkOrderStateDef.DOING);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);

        cleaningOrderService.updateSubOrderWithTrans(cleaningOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
