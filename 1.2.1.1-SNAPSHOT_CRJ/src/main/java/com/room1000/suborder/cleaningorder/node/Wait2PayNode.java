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
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.node.IWait2PayNodeProcess;
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
@Component("CleaningOrder.Wait2PayNode")
public class Wait2PayNode extends SubOrderTypeNode implements IWait2PayNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2PayNode.class);
    
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
        logger.info("待支付完成");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get(ActivitiVariableDef.CLEANING_ORDER);
        
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setState(SubOrderStateDef.PAY);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        // 客户
        cleaningOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);
        workOrderDto.setSubOrderState(SubOrderStateDef.PAY);
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_PAY);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerId(cleaningOrderDto.getAssignedDealerId());
        cleaningOrderService.updateSubOrderWithTrans(cleaningOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.CLEANING_ORDER);
    }

}
