package com.room1000.suborder.cancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.node.IAssignedOrderNodeProcess;

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
@Component("CancelLeaseOrder.AssignOrderNode")
public class AssignOrderNode extends SubOrderTypeNode implements IAssignedOrderNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(AssignOrderNode.class);

    /**
     * cancelLeaseService
     */
    @Autowired
    private ICancelLeaseOrderService cancelLeaseService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("自动指派订单");
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);
        cancelLeaseService.assignSubOrderWithTrans(cancelLeaseOrderDto.getCode());
    }

}
