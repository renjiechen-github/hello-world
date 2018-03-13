package com.room1000.suborder.node;

import java.util.Map;

/**
 * 
 * Description: 超接口，定义各个环节的需要执行的方法
 *  
 * Created on 2017年5月22日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IActivitiProcess {

    /**
     * 
     * Description: 具体业务逻辑
     * 
     * @author jinyanan
     *
     * @param variables activiti流程参数
     */
    void doProcess(Map<String, Object> variables);
    
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
