package com.room1000.suborder.cancelleaseorder.check;

import java.util.Map;

import com.room1000.core.activiti.IProcessExpression;
import com.room1000.suborder.cancelleaseorder.define.ChannelDef;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.define.ActivitiVariableDef;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月8日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Deprecated
public class CheckIsPC implements IProcessExpression {

    @Override
    public boolean getResult(Map<String, Object> variables) {
        CancelLeaseOrderDto cancelLeaseOrderDto = (CancelLeaseOrderDto) variables.get(ActivitiVariableDef.CANCEL_LEASE_ORDER);
        if (ChannelDef.CHANNEL_CUSTOMER_SERVICE.equals(cancelLeaseOrderDto.getChannel()) ||
            ChannelDef.CHANNEL_PC.equals(cancelLeaseOrderDto.getChannel()) ||
            ChannelDef.CHANNEL_UP.equals(cancelLeaseOrderDto.getChannel()) ||
            ChannelDef.CHANNEL_WECHAT.equals(cancelLeaseOrderDto.getChannel())) {
            return true;
        }
        return false;
    }

}
