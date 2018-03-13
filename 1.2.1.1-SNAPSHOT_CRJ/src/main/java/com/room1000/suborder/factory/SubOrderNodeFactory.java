package com.room1000.suborder.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.room1000.suborder.node.IActivitiProcess;
import com.room1000.suborder.node.IApply2PayNodeProcess;
import com.room1000.suborder.node.IAssignedOrderNodeProcess;
import com.room1000.suborder.node.ICompleteOrderNodeProcess;
import com.room1000.suborder.node.IDoingInOrderNodeProcess;
import com.room1000.suborder.node.IFinanceAuditNodeProcess;
import com.room1000.suborder.node.IManagerAuditNodeProcess;
import com.room1000.suborder.node.IReleaseHouseNodeProcess;
import com.room1000.suborder.node.IRentalAccountNodeProcess;
import com.room1000.suborder.node.ITakeOrderNodeProcess;
import com.room1000.suborder.node.IWait2CommentNodeProcess;
import com.room1000.suborder.node.IWait2PayNodeProcess;

/**
 * 
 * Description: 子订单环节实现工厂类
 *  
 * Created on 2017年5月22日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component
public class SubOrderNodeFactory implements ApplicationContextAware {
    
    /**
     * 指派订单环节
     */
    private static Map<String, IAssignedOrderNodeProcess> assignedOrderNodeBeanMap;

    /**
     * 接单环节
     */
    private static Map<String, ITakeOrderNodeProcess> takeOrderNodeBeanMap;

    /**
     * 订单执行环节
     */
    private static Map<String, IDoingInOrderNodeProcess> doingInOrderNodeBeanMap;

    /**
     * 订单评价环节
     */
    private static Map<String, IWait2CommentNodeProcess> wait2CommentNodeBeanMap;

    /**
     * 申请支付环节
     */
    private static Map<String, IApply2PayNodeProcess> apply2PayNodeBeanMap;

    /**
     * 待支付环节（包括收入与支出）
     */
    private static Map<String, IWait2PayNodeProcess> wait2PayNodeBeanMap;

    /**
     * 订单完成环节
     */
    private static Map<String, ICompleteOrderNodeProcess> completeOrderNodeBeanMap;

    /**
     * 释放房源环节
     */
    private static Map<String, IReleaseHouseNodeProcess> releaseHouseNodeBeanMap;

    /**
     * 租务核算环节
     */
    private static Map<String, IRentalAccountNodeProcess> rentalAccountNodeBeanMap;

    /**
     * 高管审批环节
     */
    private static Map<String, IManagerAuditNodeProcess> managerAuditNodeBeanMap;

    /**
     * 财务审批环节
     */
    private static Map<String, IFinanceAuditNodeProcess> financeAuditNodeBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        assignedOrderNodeBeanMap = generateBeanMap(applicationContext, IAssignedOrderNodeProcess.class);
        
        apply2PayNodeBeanMap = generateBeanMap(applicationContext, IApply2PayNodeProcess.class);
        
        takeOrderNodeBeanMap = generateBeanMap(applicationContext, ITakeOrderNodeProcess.class);
        
        doingInOrderNodeBeanMap = generateBeanMap(applicationContext, IDoingInOrderNodeProcess.class);
        
        wait2CommentNodeBeanMap = generateBeanMap(applicationContext, IWait2CommentNodeProcess.class);
        
        wait2PayNodeBeanMap = generateBeanMap(applicationContext, IWait2PayNodeProcess.class);
        
        completeOrderNodeBeanMap = generateBeanMap(applicationContext, ICompleteOrderNodeProcess.class);
        
        releaseHouseNodeBeanMap = generateBeanMap(applicationContext, IReleaseHouseNodeProcess.class);
        
        rentalAccountNodeBeanMap = generateBeanMap(applicationContext, IRentalAccountNodeProcess.class);
        
        managerAuditNodeBeanMap = generateBeanMap(applicationContext, IManagerAuditNodeProcess.class);
        
        financeAuditNodeBeanMap = generateBeanMap(applicationContext, IFinanceAuditNodeProcess.class);
    }
    
    /**
     * 
     * Description: 通过ApplicationContextAware获取各个接口的实现类
     * 
     * @author jinyanan
     * 
     * @param <T> <T>
     * @param applicationContext applicationContext
     * @param clazz 环节接口
     * @return  beanMap
     */
    private <T extends IActivitiProcess> Map<String, T> generateBeanMap(ApplicationContext applicationContext, Class<T> clazz) {
        Map<String, T> nodeMap = applicationContext.getBeansOfType(clazz);
        Map<String, T> beanMap = new HashMap<>();
        for (Entry<String, T> node : nodeMap.entrySet()) {
            beanMap.put(node.getValue().getWorkOrderType(), node.getValue());
        }
        return beanMap;
    }
    
    /**
     * 
     * Description: 根据订单类型获取指派订单环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 指派订单环节具体实现类
     */
    public static IAssignedOrderNodeProcess getAssignOrderNode(String orderType) {
        return assignedOrderNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取申请支付环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 申请支付环节具体实现类
     */
    public static IApply2PayNodeProcess getApply2PayNode(String orderType) {
        return apply2PayNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取订单执行环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 订单执行环节具体实现类
     */
    public static IDoingInOrderNodeProcess getDoInOrderNode(String orderType) {
        return doingInOrderNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取待支付环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 待支付环节具体实现类
     */
    public static IWait2PayNodeProcess getWait2PayNode(String orderType) {
        return wait2PayNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取订单完成环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 订单完成环节具体实现类
     */
    public static ICompleteOrderNodeProcess getCompleteOrderNode(String orderType) {
        return completeOrderNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取接单环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 接单环节具体实现类
     */
    public static ITakeOrderNodeProcess getTakeOrderNode(String orderType) {
        return takeOrderNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取订单评价环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 订单评价环节具体实现类
     */
    public static IWait2CommentNodeProcess getWait2CommentNode(String orderType) {
        return wait2CommentNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取房源释放环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 房源释放环节具体实现类
     */
    public static IReleaseHouseNodeProcess getReleaseHouseNode(String orderType) {
        return releaseHouseNodeBeanMap.get(orderType);
    }

    /**
     * 
     * Description: 根据订单类型获取租务核算环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 租务核算环节具体实现类
     */
    public static IRentalAccountNodeProcess getRentalAccountNode(String orderType) {
        return rentalAccountNodeBeanMap.get(orderType);
    }
    
    /**
     * 
     * Description: 根据订单类型获取指高管审批环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 高管审批环节具体实现类
     */
    public static IManagerAuditNodeProcess getManagerAuditNode(String orderType) {
        return managerAuditNodeBeanMap.get(orderType);
    }
    
    /**
     * 
     * Description: 根据订单类型获取财务审批环节具体实现类
     * 
     * @author jinyanan
     *
     * @param orderType 订单类型
     * @return 财务审批环节具体实现类
     */
    public static IFinanceAuditNodeProcess getFinanceAuditNode(String orderType) {
        return financeAuditNodeBeanMap.get(orderType);
    }
    
}
