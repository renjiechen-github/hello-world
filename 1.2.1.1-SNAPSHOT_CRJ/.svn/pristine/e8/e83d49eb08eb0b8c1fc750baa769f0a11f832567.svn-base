package com.room1000.suborder.ownerrepairorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.node.IWait2CommentNodeProcess;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderDto;
import com.room1000.suborder.ownerrepairorder.service.IOwnerRepairOrderService;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
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
@Component("OwnerRepairOrder.Wait2CommentNode")
public class Wait2CommentNode extends SubOrderTypeNode implements IWait2CommentNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2CommentNode.class);

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
        logger.info("订单评论");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        OwnerRepairOrderDto ownerRepairOrderDto = (OwnerRepairOrderDto) variables
            .get(ActivitiVariableDef.OWNER_REPAIR_ORDER);

        ownerRepairOrderDto.clearAssignedDealer();
        ownerRepairOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
        ownerRepairOrderDto.setState(SubOrderStateDef.COMMENT);
        ownerRepairOrderDto.setStateDate(DateUtil.getDBDateTime());
        ownerRepairOrderService.updateSubOrderWithTrans(ownerRepairOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerId(SystemAccountDef.CUSTOMER);
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_COMMENT);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubOrderState(SubOrderStateDef.COMMENT);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.OWNER_REPAIR_ORDER);
    }

}
