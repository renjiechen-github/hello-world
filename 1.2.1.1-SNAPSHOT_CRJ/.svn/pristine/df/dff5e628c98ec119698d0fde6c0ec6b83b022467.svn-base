package com.room1000.workorder.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;

/**
 * 
 * Description: 子订单Service工厂
 *  
 * Created on 2017年5月4日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component
public class SubOrderFactory implements ApplicationContextAware {

    /**
     * 存储子订单Service实例
     */
    private static Map<String, ISubOrderService> subOrderServiceBeanMap;
    
    /**
     * 存储子订单中有pay需求的实例
     */
    private static Map<String, ISubOrderPayService> subOrderPayServiceBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, ISubOrderService> map = applicationContext.getBeansOfType(ISubOrderService.class);
        subOrderServiceBeanMap = new HashMap<>();
        for (Entry<String, ISubOrderService> subOrderService : map.entrySet()) {
            subOrderServiceBeanMap.put(subOrderService.getValue().getWorkOrderType(), subOrderService.getValue());
        }
        
        Map<String, ISubOrderPayService> subOrderPayMap = applicationContext.getBeansOfType(ISubOrderPayService.class);
        subOrderPayServiceBeanMap = new HashMap<>();
        for (Entry<String, ISubOrderPayService> subOrderPayService : subOrderPayMap.entrySet()) {
            subOrderPayServiceBeanMap.put(subOrderPayService.getValue().getWorkOrderType(), subOrderPayService.getValue());
        }
    }
    
    /**
     * 
     * Description: 根据订单类型查询对应订单Service
     * 
     * @author jinyanan
     *
     * @param <T> <T>
     * @param orderType 订单类型
     * @return 子订单Service实例
     */
    @SuppressWarnings("unchecked")
    public static <T extends ISubOrderService> T getSubOrderService(String orderType) {
        return (T) subOrderServiceBeanMap.get(orderType);
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param <T> <T>
     * @param orderType 订单类型
     * @return 子订单Service实例
     */
    @SuppressWarnings("unchecked")
    public static <T extends ISubOrderPayService> T getSubOrderPayService(String orderType) {
        return (T) subOrderPayServiceBeanMap.get(orderType);
    }
    
}
