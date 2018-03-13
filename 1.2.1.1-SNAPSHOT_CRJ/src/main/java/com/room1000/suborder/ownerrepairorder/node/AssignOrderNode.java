package com.room1000.suborder.ownerrepairorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemRoleDef;
import com.room1000.suborder.node.IAssignedOrderNodeProcess;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.service.IOwnerRepairOrderService;
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
@Component("OwnerRepairOrder.AssignOrderNode")
public class AssignOrderNode extends SubOrderTypeNode implements IAssignedOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AssignOrderNode.class);
    
    /**
     * ownerRepairOrderService
     */
    @Autowired
    private IOwnerRepairOrderService ownerRepairOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("指派订单");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OwnerRepairOrderDto ownerRepairOrderDto = (OwnerRepairOrderDto) variables.get(ActivitiVariableDef.OWNER_REPAIR_ORDER);

        // 指定客服处理
        ownerRepairOrderDto.clearAssignedDealer();
        ownerRepairOrderDto.setAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);
        ownerRepairOrderDto.setState(SubOrderStateDef.ASSIGN_ORDER);
        ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.ASSIGN_ORDER);
        workOrderDto.setSubAssignedDealerRoleId(SystemRoleDef.ROLE_CUSTOMER_SERVICE);

        ownerRepairOrderService.updateSubOrderWithTrans(ownerRepairOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
    }

}
