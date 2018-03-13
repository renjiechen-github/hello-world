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
@Component("CleaningOrder.Apply2PayNode")
public class Apply2PayNode extends SubOrderTypeNode implements IApply2PayNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Apply2PayNode.class);

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
        logger.info("请求支付");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        CleaningOrderDto cleaningOrderDto = (CleaningOrderDto) variables.get(ActivitiVariableDef.CLEANING_ORDER);
        
        cleaningOrderDto.clearAssignedDealer();
        cleaningOrderDto.setState(SubOrderStateDef.APPLY_2_PAY);
        cleaningOrderDto.setStateDate(DateUtil.getDBDateTime());
        cleaningOrderService.updateSubOrderWithTrans(cleaningOrderDto, null, "");
        workOrderDto.setSubOrderState(SubOrderStateDef.APPLY_2_PAY);
        workOrderDto.clearSubField();
        workOrderService.updateWorkOrderWithTrans(workOrderDto);
        
        if (cleaningOrderDto.getActualDealerId() == null) {

            SubOrderUtil.apply2Pay(workOrderDto, cleaningOrderDto.getRentalLeaseOrderId(), SystemAccountDef.CUSTOMER);
        }
        else {
            SubOrderUtil.apply2Pay(workOrderDto, cleaningOrderDto.getRentalLeaseOrderId(), cleaningOrderDto.getActualDealerId());
        }
    }

    
    
    
    
    
}
