package com.room1000.core.activiti;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.TaskListener;

/**
 * 
 * Description: 流程中各个环节需要暴露给业务侧使用的接口
 *  
 * Created on 2017年1月13日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IProcessAction extends TaskListener, JavaDelegate {
    
    /**
     * 
     * Description: 分支判断环节需要实现方法
     * 
     * @author jinyanan
     *
     * @param execution execution
     * @throws Exception <br>
     */
//    void notify(DelegateExecution execution) throws Exception;
    
    /**
     * 
     * Description: 人工环节需要实现方法
     * 
     * @author jinyanan
     *
     * @param delegateTask delegateTask
     */
    void notify(DelegateTask delegateTask);
    
    /**
     * 
     * Description: 服务环节需要实现方法
     * 
     * @author jinyanan
     *
     * @param execution execution
     * @throws Exception <br>
     */
    void execute(DelegateExecution execution) throws Exception;
}
