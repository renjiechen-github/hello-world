package com.room1000.suborder.node.impl;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.springframework.stereotype.Component;

import com.room1000.core.activiti.IProcessAction;
import com.room1000.suborder.define.ActivitiVariableDef;
import com.room1000.suborder.factory.SubOrderNodeFactory;
import com.room1000.suborder.node.IReleaseHouseNodeProcess;
import com.room1000.workorder.dto.WorkOrderDto;

/**
 * 
 * Description: 供Activiti调用实现类
 *  
 * Created on 2017年5月22日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Component("ReleaseHouseNode")
public class ReleaseHouseNode implements IProcessAction {

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;

    @Override
    public void notify(DelegateTask delegateTask) {
        this.doProcess(delegateTask.getVariables());
    }

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        this.doProcess(execution.getVariables());
    }
    
    /**
     * 
     * Description: 
     * 
     * @author jinyanan
     *
     * @param variables activiti流程参数
     */
    private void doProcess(Map<String, Object> variables) {
        WorkOrderDto workOrderDto = (WorkOrderDto) variables.get(ActivitiVariableDef.WORK_ORDER);
        IReleaseHouseNodeProcess process = SubOrderNodeFactory.getReleaseHouseNode(workOrderDto.getType());
        process.doProcess(variables);
    }

}
