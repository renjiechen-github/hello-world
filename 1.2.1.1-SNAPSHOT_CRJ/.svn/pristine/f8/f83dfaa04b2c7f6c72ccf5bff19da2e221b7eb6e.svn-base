package com.room1000.suborder.cancelleaseorder.node;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.room1000.core.activiti.IProcessAction;

/**
 * 
 * Description: 
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class JudgeOrderNode implements IProcessAction {
    
    /**
     * logger
     */
    private static Logger logger = LoggerFactory.getLogger(JudgeOrderNode.class);

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param execution execution
     * @throws Exception Exception
     */
    public void notify(DelegateExecution execution) throws Exception {
        logger.info("分支判断，校验受理渠道");
        execution.getVariables().put("channel", "1");
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        // TODO Auto-generated method stub

    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // TODO Auto-generated method stub

    }

}
