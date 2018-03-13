package com.room1000.timedtask.dto;

import java.util.List;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月5日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class Staff4OrderCountDto {

    /**
     * 员工id
     */
    private Long dealerId;

    /**
     * 员工名称
     */
    private String dealerName;

    /**
     * 员工手机号
     */
    private String dealerPhone;

    /**
     * 待处理订单及数量
     */
    private List<OrderCountDto> orderCountList;

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerPhone() {
        return dealerPhone;
    }

    public void setDealerPhone(String dealerPhone) {
        this.dealerPhone = dealerPhone;
    }

    public List<OrderCountDto> getOrderCountList() {
        return orderCountList;
    }

    public void setOrderCountList(List<OrderCountDto> orderCountList) {
        this.orderCountList = orderCountList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Staff4OrderCountDto [dealerId=");
        builder.append(dealerId);
        builder.append(", dealerName=");
        builder.append(dealerName);
        builder.append(", dealerPhone=");
        builder.append(dealerPhone);
        builder.append(", orderCountList=");
        builder.append(orderCountList);
        builder.append("]");
        return builder.toString();
    }
    
    

}
