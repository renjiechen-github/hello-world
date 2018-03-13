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
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.node.IWait2CommentNodeProcess;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
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
@Component("CancelLeaseOrder.Wait2CommentNode")
public class Wait2CommentNode extends SubOrderTypeNode implements IWait2CommentNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2CommentNode.class);

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
        logger.info("待评论");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        cancelLeaseOrderDto.clearAssignedDealer();
        cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
        cancelLeaseOrderDto.setState(SubOrderStateDef.COMMENT);
        cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        cancelLeaseService.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_COMMENT);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
        workOrderDto.setSubOrderState(SubOrderStateDef.COMMENT);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.CANCEL_LEASE_ORDER);
    }

}
