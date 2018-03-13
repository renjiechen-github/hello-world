package com.room1000.suborder.payorder.check;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.payorder.dto.PayOrderDto;
import com.room1000.suborder.payorder.dto.PayOrderValueDto;
import com.room1000.suborder.payorder.service.IPayOrderService;

/**
 * 
 * Description: 订单审核未通过
 * 
 * Created on 2017年6月5日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("PayOrderCheckNotPassInOrderAudit")
public class CheckNotPassInOrderAudit implements IProcessExpression {

    /**
     * payOrderService
     */
    @Autowired
    private IPayOrderService payOrderService;

    @Override
    public boolean getResult(Map<String, Object> variables) {
        PayOrderDto payOrderDto = (PayOrderDto) variables.get(ActivitiVariableDef.PAY_ORDER);

        PayOrderValueDto payOrderValueDto = new PayOrderValueDto();
        payOrderValueDto.setSubOrderId(payOrderDto.getId());
        payOrderValueDto.setAttrPath("PAY_ORDER_PROCESS.OPERATOR_AUDIT.PASSED");
        payOrderValueDto = payOrderService.selectByAttrPath(payOrderValueDto);
        if (payOrderValueDto == null || BooleanFlagDef.BOOLEAN_FALSE.equals(payOrderValueDto.getTextInput())) {
            return true;
        }
        return false;
    }

}
