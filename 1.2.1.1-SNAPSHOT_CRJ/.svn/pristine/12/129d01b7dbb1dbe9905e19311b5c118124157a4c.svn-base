package com.room1000.suborder.cancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
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
@Component("CancelLeaseOrder.Wait2PayNode")
public class Wait2PayNode extends SubOrderTypeNode implements IWait2PayNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2PayNode.class);

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
        logger.info("待支付完成");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.CANCEL_LEASE_ORDER);

        // 查询是收入还是支出
        CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
        cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.FINANCE_TYPE");
        cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
        cancelLeaseOrderValueDto = cancelLeaseService.selectByAttrPath(cancelLeaseOrderValueDto);
        if ("1".equals(cancelLeaseOrderValueDto.getTextInput())) {
            cancelLeaseOrderDto.setState(SubOrderStateDef.REFUND);
        }
        else {
            cancelLeaseOrderDto.setState(SubOrderStateDef.PAY);
        }
        cancelLeaseOrderDto.clearAssignedDealer();
        cancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        // 客户
        cancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);

        workOrderDto.setSubOrderState(cancelLeaseOrderDto.getState());
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_PAY);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerId(cancelLeaseOrderDto.getAssignedDealerId());
        cancelLeaseService.updateSubOrderWithTrans(cancelLeaseOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.CANCEL_LEASE_ORDER);
    }

}
