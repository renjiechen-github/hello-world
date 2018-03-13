package com.room1000.suborder.repairorder.node;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.node.IWait2PayNodeProcess;
import com.room1000.suborder.repairorder.dto.RepairOrderDto;
import com.room1000.suborder.repairorder.service.IRepairOrderService;
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
@Component("RepairOrder.Wait2PayNode")
public class Wait2PayNode extends SubOrderTypeNode implements IWait2PayNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2PayNode.class);
    
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
        logger.info("待支付完成");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        RepairOrderDto repairOrderDto = (RepairOrderDto) variables.get(ActivitiVariableDef.REPAIR_ORDER);
        
        repairOrderDto.clearAssignedDealer();
        repairOrderDto.setState(SubOrderStateDef.PAY);
        repairOrderDto.setStateDate(DateUtil.getDBDateTime());
        // 客户
        repairOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.PAY);
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_PAY);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerId(repairOrderDto.getAssignedDealerId());
        repairOrderService.updateSubOrderWithTrans(repairOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.REPAIR_ORDER);
    }

}
