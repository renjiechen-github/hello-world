package com.room1000.suborder.ownercancelleaseorder.node;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.utils.DateUtil;
import com.room1000.core.utils.MoneyUtil;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.node.IWait2PayNodeProcess;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
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
@Component("OwnerCancelLeaseOrder.Wait2PayNode")
public class Wait2PayNode extends SubOrderTypeNode implements IWait2PayNodeProcess {

    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(Wait2PayNode.class);

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
        logger.info("待支付完成");
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        workOrderDto.clearSubField();
        OwnerCancelLeaseOrderDto ownerCancelLeaseOrderDto = (OwnerCancelLeaseOrderDto) variables
            .get(ActivitiVariableDef.OWNER_CANCEL_LEASE_ORDER);

        // 查询是收入还是支出
        if (MoneyUtil.withoutDecimalStrMoney2Long(workOrderDto.getPayableMoney()) > 0L) {
            ownerCancelLeaseOrderDto.setState(SubOrderStateDef.PAY);
        }
        else {
            ownerCancelLeaseOrderDto.setState(SubOrderStateDef.REFUND);
        }

        ownerCancelLeaseOrderDto.clearAssignedDealer();
        ownerCancelLeaseOrderDto.setStateDate(DateUtil.getDBDateTime());
        // 客户
        ownerCancelLeaseOrderDto.setAssignedDealerId(SystemAccountDef.CUSTOMER);

        workOrderDto.setSubOrderState(SubOrderStateDef.PAY);
        workOrderDto.setState(WorkOrderStateDef.WAIT_2_PAY);
        workOrderDto.setStateDate(DateUtil.getDBDateTime());
        workOrderDto.setSubAssignedDealerId(ownerCancelLeaseOrderDto.getAssignedDealerId());
        ownerCancelLeaseOrderService.updateSubOrderWithTrans(ownerCancelLeaseOrderDto, null, "");
        workOrderService.updateWorkOrderWithTrans(workOrderDto);

//        SubOrderUtil.sendMessage(workOrderDto, null, OrderPushModelDef.OWNER_CANCEL_LEASE_ORDER);
    }

}
