package com.room1000.core.activiti.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.SetProcessDefinitionVersionCmd;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.model.ActivitiStepDto;

/**
 * Description: Created on 2017年1月13日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class ProcessTaskImpl implements IProcessTask {

    /** logger */
    private static Logger logger = LoggerFactory.getLogger(ProcessTaskImpl.class);

    /** processEngine */
    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Override
    public void dispatcherOrder(Long workOrderId) {
        logger.info("workOrderId: " + workOrderId);
        TaskService taskService = processEngine.getTaskService();

        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        
        logger.info("task don`t found by workOrderId :" + workOrderId);

        if (task != null) {
            changeProcessVersion2Lastest(task);
            taskService.complete(task.getId());
        }
    }

    /**
     * 
     * Description: 变更task对应流程定义的版本为最新版本
     * 
     * @author jinyanan
     *
     * @param task task
     */
    private void changeProcessVersion2Lastest(Task task) {
        // 查询当前task对应流程定义
        ProcessDefinition currentProcessDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
            .processDefinitionId(task.getProcessDefinitionId()).singleResult();
        // 当前流程定义的版本
        int currentVersion = currentProcessDefinition.getVersion();
        // 查询该流程定义的最新版本
        int lastestVersion = processEngine.getRepositoryService().createProcessDefinitionQuery()
            .processDefinitionKey(currentProcessDefinition.getKey()).latestVersion().singleResult().getVersion();

        // 如果当前流程未使用最新版本的流程定义，则切换为最新版本
        if (currentVersion < lastestVersion) {
            ProcessEngineConfigurationImpl processEngineConfigurationImpl = (ProcessEngineConfigurationImpl) processEngine
                .getProcessEngineConfiguration();
            CommandExecutor commandExecutor = processEngineConfigurationImpl.getCommandExecutor();
            // 该SetProcessDefinitionVersionCmd有一些不可使用的情况，暂时不知道具体有哪些
            commandExecutor.execute(new SetProcessDefinitionVersionCmd(task.getProcessInstanceId(), lastestVersion));
        }
    }

    @Override
    public Map<String, Object> getInstanceVariables(Long workOrderId) {
        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        return processEngine.getRuntimeService().getVariables(task.getProcessInstanceId());
    }

    @Override
    public void putInstanceVariables(Long workOrderId, Map<String, Object> variables) {
        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        processEngine.getRuntimeService().setVariables(task.getProcessInstanceId(), variables);

    }

    @Override
    public void putInstanceVariable(Long workOrderId, String variableName, Object variableValue) {
        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        if (task == null) {
            return;
        }
        processEngine.getRuntimeService().setVariable(task.getProcessInstanceId(), variableName, variableValue);

    }

    @Override
    public void endProcess(Long workOrderId) throws Exception {
        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        if (task == null) {
            return;
        }

        ActivityImpl endActivity = findActivitiImpl(task.getId(), "end");
        commitProcess(task.getId(), null, endActivity.getId());
    }

    @Override
    public void direct2Activity(Long workOrderId, String activityId) throws Exception {
        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        if (task == null) {
            return;
        }
        else {
            changeProcessVersion2Lastest(task);
        }

        logger.info("activityId: " + activityId);
        ActivityImpl activity = findActivitiImpl(task.getId(), activityId);
        logger.info("activity: " + activity);
        commitProcess(task.getId(), null, activity.getId());
    }

    @Override
    public List<ActivitiStepDto> getAllTaskActiviti(Long workOrderId) throws Exception {
        Task task = processEngine.getTaskService().createTaskQuery()
            .processVariableValueEquals("workOrderId", workOrderId).singleResult();
        if (task == null) {
            return null;
        }

        List<ActivitiStepDto> stepList = new ArrayList<>();

        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(task.getId());
        for (ActivityImpl activity : processDefinition.getActivities()) {
            ActivitiStepDto step = new ActivitiStepDto();
            if ("userTask".equals(activity.getProperty("type"))) {
                String name = (String) activity.getProperty("name");
                String orderState = name.split(",")[0];
                Long orderNbr = Long.valueOf(name.split(",")[1]);
                String taskName = name.split(",")[2];
                step.setOrderState(orderState);
                step.setTaskName(taskName);
                step.setOrderNbr(orderNbr);
                step.setTaskType("userTask");
                stepList.add(step);
            }

        }

        Collections.sort(stepList);
        return stepList;

    }

    /**
     * 提交
     * 
     * @param taskId 当前任务ID
     * @param variables 流程变量
     * @param activityId 流程转向执行任务节点ID<br>
     *            此参数为空，默认为提交操作
     * @throws Exception <br>
     */
    private void commitProcess(String taskId, Map<String, Object> variables, String activityId) throws Exception {
        if (variables == null) {
            variables = new HashMap<String, Object>();
        }
        // 跳转节点为空，默认提交操作
        if (StringUtils.isEmpty(activityId)) {
            processEngine.getTaskService().complete(taskId, variables);
        }
        // 流程转向操作
        else {
            turnTransition(taskId, activityId, variables);
        }
    }

    /**
     * 流程转向操作
     * 
     * @param taskId 当前任务ID
     * @param activityId 目标节点任务ID
     * @param variables 流程变量
     * @throws Exception <br>
     */
    private void turnTransition(String taskId, String activityId, Map<String, Object> variables) throws Exception {
        // 当前节点
        ActivityImpl currActivity = findActivitiImpl(taskId, null);
        // 清空当前流向
        List<PvmTransition> oriPvmTransitionList = clearTransition(currActivity);

        // 创建新流向
        TransitionImpl newTransition = currActivity.createOutgoingTransition();
        // 目标节点
        ActivityImpl pointActivity = findActivitiImpl(taskId, activityId);
        // 设置新流向的目标节点
        newTransition.setDestination(pointActivity);

        // 执行转向任务
        processEngine.getTaskService().complete(taskId, variables);
        // 删除目标节点新流入
        pointActivity.getIncomingTransitions().remove(newTransition);

        // 还原以前流向
        restoreTransition(currActivity, oriPvmTransitionList);
    }

    /**
     * 还原指定活动节点流向
     * 
     * @param activityImpl 活动节点
     * @param oriPvmTransitionList 原有节点流向集合
     */
    private void restoreTransition(ActivityImpl activityImpl, List<PvmTransition> oriPvmTransitionList) {
        // 清空现有流向
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        pvmTransitionList.clear();
        // 还原以前流向
        for (PvmTransition pvmTransition : oriPvmTransitionList) {
            pvmTransitionList.add(pvmTransition);
        }
    }

    /**
     * 清空指定活动节点流向
     * 
     * @param activityImpl 活动节点
     * @return 节点流向集合
     */
    private List<PvmTransition> clearTransition(ActivityImpl activityImpl) {
        // 存储当前节点所有流向临时变量
        List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
        // 获取当前节点所有流向，存储到临时变量，然后清空
        List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
        for (PvmTransition pvmTransition : pvmTransitionList) {
            oriPvmTransitionList.add(pvmTransition);
        }
        pvmTransitionList.clear();

        return oriPvmTransitionList;
    }

    /**
     * 
     * Description: 获取activityId对应的节点
     * 
     * @author jinyanan
     *
     * @param taskId taskId
     * @param activityId activityId
     * @return ActivityImpl
     * @throws Exception <br>
     */
    private ActivityImpl findActivitiImpl(String taskId, String activityId) throws Exception {
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = findProcessDefinitionEntityByTaskId(taskId);

        // 获取当前活动节点ID
        if (StringUtils.isEmpty(activityId)) {
            activityId = findTaskById(taskId).getTaskDefinitionKey();
        }

        // 根据流程定义，获取该流程实例的结束节点
        if (("END").equalsIgnoreCase(activityId)) {
            for (ActivityImpl activityImpl : processDefinition.getActivities()) {
                List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();
                if (pvmTransitionList.isEmpty()) {
                    return activityImpl;
                }
            }
        }

        // 根据节点ID，获取对应的活动节点
        ActivityImpl activityImpl = ((ProcessDefinitionImpl) processDefinition).findActivity(activityId);

        if (activityImpl == null) {
            activityImpl = this.findActivityByName(processDefinition, activityId);
        }

        return activityImpl;
    }

    /**
     * 
     * Description: 根据那么查找ActivityImpl
     * 
     * @author jinyanan
     *
     * @param processDefinition processDefinition
     * @param activityId activityId
     * @return ActivityImpl
     */
    private ActivityImpl findActivityByName(ProcessDefinitionEntity processDefinition, String activityId) {
        List<ActivityImpl> activities = ((ProcessDefinitionImpl) processDefinition).getActivities();
        for (ActivityImpl activityImpl : activities) {
            String name = (String) activityImpl.getProperty("name");
            String orderState = name.split(",")[0];
            if (activityId.equals(orderState)) {
                return activityImpl;
            }
        }
        return null;
    }

    /**
     * 根据任务ID获取流程定义
     * 
     * @param taskId 任务ID
     * @return ProcessDefinitionEntity
     * @throws Exception <br>
     */
    private ProcessDefinitionEntity findProcessDefinitionEntityByTaskId(String taskId) throws Exception {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 取得流程定义
        ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
            .getDeployedProcessDefinition(findTaskById(taskId).getProcessDefinitionId());

        if (processDefinition == null) {
            throw new Exception("流程定义未找到!");
        }

        return processDefinition;
    }

    /**
     * 根据任务ID获得任务实例
     * 
     * @param taskId 任务ID
     * @return TaskEntity
     * @throws Exception <br>
     */
    private TaskEntity findTaskById(String taskId) throws Exception {
        TaskEntity task = (TaskEntity) processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new Exception("任务实例未找到!");
        }
        return task;
    }

}
