package com.room1000.timedtask.service;

/**
 * 
 * Description: 订单定时任务
 *  
 * Created on 2017年3月20日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IOrderTaskService {

    /**
     * 
     * Description: 自动好评
     * 
     * @author jinyanan
     *
     */
    void autoOrderCommentaryWithTrans();
    
    /**
     * 
     * Description: 根据合约endTime生成退租订单
     * 
     * @author jinyanan
     *
     */
    void autoCreateCancelLeaseOrderWithTrans();
    
    /**
     * 
     * Description: 自动推送各个管家手中需要处理的订单数量
     * 
     * @author jinyanan
     *
     */
    void autoSendWait2DealWorkOrderMessage();
    
    /**
     * 自动发起推送，通知管家进行上门结算
     */
    void autoSendHousekeeperDealWithMessage();
}
