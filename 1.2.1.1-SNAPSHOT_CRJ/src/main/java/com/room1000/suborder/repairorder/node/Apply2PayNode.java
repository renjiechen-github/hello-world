package com.room1000.suborder.repairorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.node.IApply2PayNodeProcess;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;
import com.room1000.suborder.utils.SubOrderUtil;
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
@Component("RepairOrder.Apply2PayNode")
public class Apply2PayNode extends SubOrderTypeNode implements IApply2PayNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Apply2PayNode.class);

    /**
     * repairOrderService
     */
    @Autowired
    private IRepairOrderService repairOrderService;

    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("请求支付");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        RepairOrderDto repairOrderDto = (RepairOrderDto) variables.get(ActivitiVariableDef.REPAIR_ORDER);

        repairOrderDto.clearAssignedDealer();
        repairOrderDto.setState(SubOrderStateDef.APPLY_2_PAY);
        repairOrderDto.setStateDate(DateUtil.getDBDateTime());
        repairOrderService.updateSubOrderWithTrans(repairOrderDto, null, "");
        workOrderDto.setSubOrderState(SubOrderStateDef.APPLY_2_PAY);
        workOrderDto.clearSubField();
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.apply2Pay(workOrderDto, repairOrderDto.getRentalLeaseOrderId(),
            repairOrderDto.getActualDealerId());
    }

}
