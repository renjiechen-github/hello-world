package com.room1000.suborder.houselookingorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderDto;
import com.room1000.suborder.houselookingorder.service.IHouseLookingOrderService;
import com.room1000.suborder.node.ITakeOrderNodeProcess;
import com.room1000.suborder.utils.OrderPushModelDef;
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
@Component("HouseLookingOrder.TakeOrderNode")
public class TakeOrderNode extends SubOrderTypeNode implements ITakeOrderNodeProcess {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(TakeOrderNode.class);
    
    /**
     * houseLookingOrderService
     */
    @Autowired
    private IHouseLookingOrderService houseLookingOrderService;
    
    /**
     * workOrderService
     */
    @Autowired
    private IWorkOrderService workOrderService;

    @Override
    public void doProcess(Map<String, Object> variables) {
        logger.info("houselookingorder TakeOrderNode start");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        HouseLookingOrderDto houseLookingOrderDto = (HouseLookingOrderDto) variables.get(ActivitiVariableDef.HOUSE_LOOKING_ORDER);
        
        workOrderDto = workOrderService.getWorkOrderDtoById(houseLookingOrderDto.getWorkOrderId());
        logger.debug("workOrderDto: " + workOrderDto);
        houseLookingOrderDto = houseLookingOrderService.getOrderDetailById(houseLookingOrderDto.getId(), true);
        logger.debug("houseLookingOrderDto: " + houseLookingOrderDto);
            
        houseLookingOrderDto.clearAssignedDealer();
        houseLookingOrderDto.setAssignedDealerId(houseLookingOrderDto.getButlerId());
        houseLookingOrderDto.setState(SubOrderStateDef.TAKE_ORDER);
        houseLookingOrderDto.setStateDate(DateUtil.getDBDateTime());
        
        workOrderDto.clearSubField();
        workOrderDto.setSubOrderState(SubOrderStateDef.TAKE_ORDER);
        workOrderDto.setSubAssignedDealerId(houseLookingOrderDto.getAssignedDealerId());
        
        houseLookingOrderService.updateSubOrderWithTrans(houseLookingOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

        SubOrderUtil.sendMessage(workOrderDto, houseLookingOrderDto.getAssignedDealerId(), OrderPushModelDef.HOUSE_LOOKING_ORDER);
    }

}
