package com.room1000.workorder.service;

/**
 * 
 * Description: 支付相关接口
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface ISubOrderPayService {

    /**
     * 
     * Description: 支付
     * 
     * @author jinyanan
     *
     * @param code code
     * @param staffId staffId
     * @param paidMoney paidMoney
     */
    void payWithTrans(String code, Long staffId, Long paidMoney);
    
    /**
     * 
     * Description: 获取当前子订单类型，必须实现
     * 
     * @author jinyanan
     *
     * @return 订单类型
     */
    String getWorkOrderType();
}
