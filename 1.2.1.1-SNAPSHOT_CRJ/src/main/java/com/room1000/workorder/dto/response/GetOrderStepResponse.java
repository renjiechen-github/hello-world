package com.room1000.workorder.dto.response;

import java.util.List;

import com.room1000.core.model.ResponseModel;
import com.room1000.workorder.dto.OrderStepDto;

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
public class GetOrderStepResponse extends ResponseModel {
    
    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    /**
     * orderStepList
     */
    private List<OrderStepDto> orderStepList;

    public List<OrderStepDto> getOrderStepList() {
        return orderStepList;
    }

    public void setOrderStepList(List<OrderStepDto> orderStepList) {
        this.orderStepList = orderStepList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GetOrderStepResponse [orderStepList=");
        builder.append(orderStepList);
        builder.append("]");
        return builder.toString();
    }

    
}
