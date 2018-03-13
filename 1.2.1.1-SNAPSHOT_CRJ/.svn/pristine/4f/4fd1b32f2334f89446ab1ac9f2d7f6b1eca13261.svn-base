package com.room1000.suborder.ownercancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.IApply2PayNodeProcess;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;

/**
 * Description:
 * 
 * Created on 2017年5月5日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("OwnerCancelLeaseOrder.Apply2PayNode")
public class Apply2PayNode extends SubOrderTypeNode implements IApply2PayNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Apply2PayNode.class);

    /**
     * ownerCancelLeaseOrderService
     */
    @Autowired
    private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

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
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);

        ownerCancelLeaseOrderService.updateSubOrderStateWithTrans(ownerCancelLeaseOrderDto.getCode(),
            SubOrderStateDef.APPLY_2_PAY);

        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.APPLY_2_PAY);
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.apply2Pay(workOrderDto, ownerCancelLeaseOrderDto.getTakeHouseOrderId(),
            ownerCancelLeaseOrderDto.getActualDealerId());
    }

}
