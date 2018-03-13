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
import com.room1000.suborder.node.IReleaseHouseNodeProcess;
import com.room1000.suborder.utils.OrderPushModelDef;
import com.room1000.suborder.utils.SubOrderUtil;
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
@Component("CancelLeaseOrder.ReleaseHouseNode")
public class ReleaseHouseNode extends SubOrderTypeNode implements IReleaseHouseNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(ReleaseHouseNode.class);

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
        logger.debug("房源释放");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        cancelLeaseOrderDto.clearAssignedDealer();
        cancelLeaseOrderDto.setAssignedDealerId(cancelLeaseOrderDto.getButlerId());
        cancelLeaseOrderDto.setState(SubOrderStateDef.RELEASE_HOUSE_RANK);
        cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        cancelLeaseService.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");

        workOrderDto.clearSubField();
        workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getButlerId());
        workOrderDto.setSubOrderState(SubOrderStateDef.RELEASE_HOUSE_RANK);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, cancelLeaseOrderDto.getAssignedDealerId(),
            OrderPushModelDef.CANCEL_LEASE_ORDER);
    }

}
