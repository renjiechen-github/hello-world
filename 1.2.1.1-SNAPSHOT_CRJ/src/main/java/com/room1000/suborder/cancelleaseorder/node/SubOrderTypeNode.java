package com.room1000.suborder.cancelleaseorder.node;

import com.room1000.workorder.define.WorkOrderTypeDef;

/**
 * 
 * Description: 退租
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class SubOrderTypeNode {
    
    public String getWorkOrderType() {
        return WorkOrderTypeDef.CANCEL_LEASE_ORDER;
    }
}
