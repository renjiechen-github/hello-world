package com.room1000.suborder.cancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.IApply2PayNodeProcess;
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
@Component("CancelLeaseOrder.Apply2PayNode")
public class Apply2PayNode extends SubOrderTypeNode implements IApply2PayNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Apply2PayNode.class);

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
        logger.info("请求支付");
        // 从流程中获取参数
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        cancelLeaseService.updateSubOrderStateWithTrans(cancelLeaseOrderDto.getCode(), SubOrderStateDef.APPLY_2_PAY);

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.APPLY_2_PAY);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.apply2Pay(workOrderDto, cancelLeaseOrderDto.getRentalLeaseOrderId(),
            cancelLeaseOrderDto.getActualDealerId());
    }

}
