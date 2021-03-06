package com.room1000.suborder.cancelleaseorder.check;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderValueDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.ActivitiVariableDef;
import pccom.common.util.SpringHelper;

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
@Deprecated
public class CheckNeedNotPay implements IProcessExpression {

    /**
     * cancelLeaseService
     */
    @Autowired
    private ICancelLeaseOrderService cancelLeaseService;

    @Override
    public boolean getResult(Map<String, Object> variables) {
        if (cancelLeaseService == null) {
            cancelLeaseService = (ICancelLeaseOrderService) SpringHelper.getBean("CancelLeaseOrderService");
        }
        
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables.get(ActivitiVariableDef.CANCEL_LEASE_ORDER);
        
        CancelLeaseOrderValueDto cancelLeaseOrderValueDto = new CancelLeaseOrderValueDto();
        cancelLeaseOrderValueDto.setSubOrderId(cancelLeaseOrderDto.getId());
        cancelLeaseOrderValueDto.setAttrPath("CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY");
        cancelLeaseOrderValueDto = cancelLeaseService.selectByAttrPath(cancelLeaseOrderValueDto);
        if (cancelLeaseOrderValueDto == null || 
            cancelLeaseOrderValueDto.getTextInput() == null || 
            "".equals(cancelLeaseOrderValueDto.getTextInput()) || 
            "0".equals(cancelLeaseOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
