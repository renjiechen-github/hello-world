package pccom.web.flow.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pccom.common.util.Batch;
import pccom.common.util.DateHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.beans.User;
import pccom.web.flow.bean.TaskStepBean;
import pccom.web.flow.interfaces.FlowStepDisposeInterface;
import pccom.web.flow.util.FlowStepState;
import pccom.web.flow.util.FlowTaskState;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Financial.Result;

/**
 * 流程处理实现类
 *
 * @author 雷杨
 *
 */
@Service("flowService")
public class FlowService extends FlowBase {

	
	@Autowired
	private Financial financial;
	
    /**
     * 获取状态信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getStateList(HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        String groupId = "FLOW.INFO.STATUS";// 
        String sql = getSql("systemconfig.querydictionary");
        String sql1 = getSql("task.getPath");
        request.setAttribute("task_path", db.queryForString(sql1));
        return db.queryForList(sql, new Object[]{groupId});
    }

    /**
     * 获取任务类型
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getTypeList(HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        String sql = getSql("task.getType");
        return db.queryForList(sql, new Object[]{0});
    }

    /**
     * 类型级联
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getType(HttpServletRequest request,
            HttpServletResponse response) {
        String sup = req.getAjaxValue(request, "sup");
        String sql = getSql("task.getType");
        Map<String, Object> returnMap = getReturnMap(1);
        returnMap.put("list", db.queryForList(sql, new Object[]{sup}));
        return returnMap;
    }

    /**
     * 获取对应的我发起的任务列表信息
     *
     * @author 雷杨
     * @param request
     * @param response
     */
    public void getMyStartTaskList(HttpServletRequest request,
            HttpServletResponse response) {
        String taskName = req.getAjaxValue(request, "taskName");//任务名称
        String taskCode = req.getAjaxValue(request, "taskCode");//任务编号
        String sendTime = req.getAjaxValue(request, "sendTime");//发起时间
        String types = req.getAjaxValue(request, "types");//类型
        String orderId = req.getAjaxValue(request, "orderId");//订单Id
        String typelike = req.getAjaxValue(request, "typelike");//类型
        String status = req.getAjaxValue(request, "status");//状态
        String lx = req.getAjaxValue(request, "lx");//2代表获取我待处理的任务信息
        User user = getUser(request);

        List<String> params = new ArrayList<String>();
        String sql = getSql("task.myStartTask.main");
        if (!"".equals(taskName)) {
            sql += getSql("task.myStartTask.taskname");
            params.add("%" + taskName + "%");
        }
        if (!"".equals(orderId)) {
            sql += getSql("task.myStartTask.orderId");
            params.add(orderId);
        }
        if (!"".equals(taskCode)) {
            sql += getSql("task.myStartTask.taskcode");
            params.add("%" + taskCode + "%");
        }
        if (!"".equals(status)) {
            sql += getSql("task.myStartTask.taskState");
            params.add(status);
        }

        if (!"".equals(types)) {
            sql += getSql("task.myStartTask.tasktypeids");
            params.add(types);
            params.add("%-" + typelike + "-%");
        }
        if (!"".equals(sendTime)) {
            String state_time = sendTime.split("至")[0];
            String end_time = sendTime.split("至")[1];
            sql += getSql("task.myStartTask.state_time");
            sql += getSql("task.myStartTask.end_time");
            params.add(state_time);
            params.add(end_time);
        }
        if ("2".equals(lx)) {//获取待处理信息
            sql += getSql("task.myStartTask.mycl");
            String org = user.getOrgId();
            String role = user.getRoles();
            String oper = user.getId();
            String[] orgs = org.split(",");
            String[] roles = role.split(",");
            String orgsql = "";
            String rulesql = "";
            for (int i = 0; i < roles.length; i++) {
                if (!"".equals(roles[i])) {
                    rulesql += " OR FIND_IN_SET(?,e.`now_role_id`) > 0";
                    params.add(roles[i]);
                }
            }
            for (int i = 0; i < orgs.length; i++) {
                if (!"".equals(orgs[i])) {
                    orgsql += " OR FIND_IN_SET(?,e.`now_org_id`) > 0";
                    params.add(orgs[i]);
                }
            }
            sql = sql.replace("#ORG#", orgsql).replace("#RULE#", rulesql);
            params.add(oper);
        } else if ("1".equals(lx)) {//我发起的流程
            sql += getSql("task.myStartTask.myfq");
            params.add(user.getId());
        } else if ("3".equals(lx)) {//我处理过的流程

        }
        getPageList(request, response, sql, params.toArray(), "task.myStartTask.orderby");
    }

    /**
     * 支出
     *
     * @author 雷杨
     * @param request
     * @param response
     */
    public void getPayList(HttpServletRequest request, HttpServletResponse response) {
        String agreeRankId = req.getAjaxValue(request, "agreeRankId");//租赁合约ID
        String secondarytype = req.getAjaxValue(request, "secondarytype");//支付类型
        System.out.println("secondarytype:" + secondarytype);
        List<String> params = new ArrayList<String>();
        String sql = "SELECT "
                + "a.*, "
                + " CASE WHEN a.remarks IS NULL THEN '' ELSE a.remarks "
                + " END remark, "
                + " DATE_FORMAT(a.end_time, '%Y-%m-%d') AS endtime, "
                + " DATE_FORMAT(a.start_time, '%Y-%m-%d') AS starttime, "
                + " b.NAME AS typename, "
                + " a.category, "
                + " fn_getdictitemname('FINANCIALPAY.STATUS', a.STATUS) statusname, "
                + " (CASE WHEN a.isdelete = 1 THEN '有效' ELSE '无效' end) isdeleteName,"
                + " ifnull((select sum(e.discount_cost) from financial_yet_tab e where e.fin_id = a.id),0) yhcost, "
                + " ifnull((select sum(e.yet_cost) from financial_yet_tab e where e.fin_id = a.id and e.type =1),0) yet_cost "
                + " FROM financial_payable_tab a, "
                + "    financial_category_tab b, "
                + "    financial_settlements_tab c "
                + " WHERE a.category = b.id "
                + " AND a.correlation = c.correlation_id ";
        if (!"".equals(agreeRankId)) {
            sql += " AND c.ager_id = ? ";
            params.add(agreeRankId);
        }
        if (!"".equals(secondarytype)) {
            sql += " and a.secondary_type = ?";
            params.add(secondarytype);
        }

        getPageList(request, response, sql, params.toArray(), null);
    }

    /**
     * 收入
     *
     * @author 雷杨
     * @param request
     * @param response
     */
    public void getFinancialList(HttpServletRequest request, HttpServletResponse response) {
        String agreeRankId = req.getAjaxValue(request, "agreeRankId");//租赁合约ID
        String secondarytype = req.getAjaxValue(request, "secondarytype");//支付类型
        List<String> params = new ArrayList<String>();
        String sql = "SELECT "
                + "a.*, "
                + " CASE WHEN a.remarks IS NULL THEN '' ELSE a.remarks "
                + " END remark, "
                + " DATE_FORMAT(a.end_time, '%Y-%m-%d') AS endtime, "
                + " DATE_FORMAT(a.start_time, '%Y-%m-%d') AS starttime, "
                + " b.NAME AS typename, "
                + " a.category, "
                + " fn_getdictitemname('FINANCIALPAY.STATUS', a.STATUS) statusname, "
                + " (CASE WHEN a.isdelete = 1 THEN '有效' ELSE '无效' end) isdeleteName,"
                + " ifnull((select sum(e.discount_cost) from financial_yet_tab e where e.fin_id = a.id and e.type =1 ),0) yhcost, "
                + " ifnull((select sum(e.yet_cost) from financial_yet_tab e where e.fin_id = a.id and e.type =1),0) yet_cost"
                + " FROM financial_receivable_tab a, "
                + "    financial_category_tab b, "
                + "    financial_settlements_tab c "
                + " WHERE a.category = b.id "
                + " AND a.correlation = c.correlation_id ";
        if (!"".equals(agreeRankId)) {
            sql += " AND c.ager_id = ? ";
            params.add(agreeRankId);
        }
        if (!"".equals(secondarytype)) {
            sql += " and a.secondary_type = ?";
            params.add(secondarytype);
        }

        getPageList(request, response, sql, params.toArray(), null);
    }

    /**
     * 获取订单详细信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getShowFlowDetail(HttpServletRequest request,
            HttpServletResponse response) {
        String task_id = req.getAjaxValue(request, "task_id");
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        String sql = getSql("task.showDetail.main") + getSql("task.showDetail.taskid");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("taskdetail", db.queryForMap(sql, new Object[]{task_id}));
        String sql1 = getSql("task.getPath");
        request.setAttribute("task_path", db.queryForString(sql1));
        sql = getSql("task.showDetail.showStepList.main") + getSql("task.showDetail.showStepList.orderby");
        returnMap.put("stepList", db.queryForList(sql, new Object[]{task_id}));

        return returnMap;
    }

    /**
     * 获取步骤处理详细信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public String showStepDetail(final HttpServletRequest request,
            HttpServletResponse response) {
        //获取当前步骤详细信息
        Object obj = db.doInTransaction(new Batch() {

            @Override
            public Object execute() throws Exception {
                request.setAttribute("isMobile", getUser(request).getIs_mobile());
                String step_id = req.getAjaxValue(request, "step_id");
                String edit = req.getAjaxValue(request, "edit");
                String sql = getSql("task.showDetail.showBzDetail.main");
                Map<String, Object> map = db.queryForMap(sql, new Object[]{step_id});
                String html_path = str.get(map, "html_path");
                //获取对应的处理class
                if (!"".equals(str.get(map, "dispose_class"))) {
                    try {
                        Class cls = Class.forName(str.get(map, "dispose_class"));
                        Object obj = cls.newInstance();
                        if (obj instanceof FlowStepDisposeInterface) {
                            FlowStepDisposeInterface flow = (FlowStepDisposeInterface) obj;
                            Object returnObj = flow.showStepDetail(this, request, step_id, str.get(map, "task_id"), "1".equals(edit) ? true : false, str.get(map, "cfg_step_id"));
                            if (returnObj instanceof Map) {
                                request.setAttribute("flowDetaiMap", (Map<String, Object>) returnObj);
                                return html_path;
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return -111;
                    }
                } else {
                    return html_path;
                }
            }
        });
        if (obj != null) {
            String result = "flow/pages/base/default";
            return "".equals(String.valueOf(obj)) ? result : String.valueOf(obj);
        } else {
            return "flow/pages/base/default";
        }
    }

    /**
     * 获取我发起的任务列表
     *
     * @author 雷杨
     * @param request
     * @param response
     */
    public void getDisposetTaskList(HttpServletRequest request,
            HttpServletResponse response) {
        String taskName = req.getAjaxValue(request, "taskName");//任务名称
        String taskCode = req.getAjaxValue(request, "taskCode");//任务编号
        String sendTime = req.getAjaxValue(request, "sendTime");//发起时间
        String types = req.getAjaxValue(request, "types");//类型
        String typelike = req.getAjaxValue(request, "typelike");//类型
        String status = req.getAjaxValue(request, "status");//类型
        String lx = req.getAjaxValue(request, "lx");//2代表获取我待处理的任务信息
        User user = getUser(request);
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        List<String> params = new ArrayList<String>();
        String sql = getSql("task.disposetTask.main");

        if (!"".equals(taskName)) {
            sql += getSql("task.disposetTask.taskname");
            params.add("%" + taskName + "%");
        }

        if (!"".equals(status)) {
            sql += getSql("task.disposetTask.taskState");
            params.add(status);
        }

        if (!"".equals(taskCode)) {
            sql += getSql("task.disposetTask.taskcode");
            params.add("%" + taskCode + "%");
        }

        if (!"".equals(types)) {
            sql += getSql("task.disposetTask.tasktypeids");
            params.add(types);
            params.add("%-" + typelike + "-%");
        }

        if (!"".equals(sendTime)) {
            String state_time = sendTime.split("至")[0];
            String end_time = sendTime.split("至")[1];
            sql += getSql("task.disposetTask.state_time");
            sql += getSql("task.disposetTask.end_time");
            params.add(state_time);
            params.add(end_time);
        }
        logger.debug("user.getOrgId():" + user.getOrgId());
        sql += getSql("task.disposetTask.mycl");
        String org = user.getOrgId();
        String role = user.getRoles();
        String oper = user.getId();
        String[] orgs = org.split(",");
        String[] roles = role.split(",");
        String orgsql = "";
        String rulesql = "";
        for (int i = 0; i < roles.length; i++) {
            if (!"".equals(roles[i])) {
                rulesql += " OR FIND_IN_SET(?,e.`now_role_id`) > 0";
                params.add(roles[i]);
            }
        }
        for (int i = 0; i < orgs.length; i++) {
            if (!"".equals(orgs[i])) {
                orgsql += " OR FIND_IN_SET(?,e.`now_org_id`) > 0";
                params.add(orgs[i]);
            }
            logger.debug(orgs[i]);
        }
        sql = sql.replace("#ORG#", orgsql).replace("#RULE#", rulesql);
        params.add(oper);
        getPageList(request, response, sql, params.toArray(), "task.disposetTask.orderby");
    }

    /**
     * 待处理信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object showDetailFlowDetail(final HttpServletRequest request,
            HttpServletResponse response) {
        String step_id = req.getAjaxValue(request, "step_id");
        //获取当前步骤详细信息
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        String sql = getSql("task.showDetail.showBzDetail.main");
        Map<String, Object> map = db.queryForMap(sql, new Object[]{step_id});
        logger.debug("map:" + map);
        sql = getSql("task.showDetail.main") + getSql("task.showDetail.taskid");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("stepMap", map);
        returnMap.put("taskdetail", db.queryForMap(sql, new Object[]{str.get(map, "task_id")}));
        String sql1 = getSql("task.getPath");
        request.setAttribute("task_path", db.queryForString(sql1));
        sql = getSql("task.showDetail.showStepList.main") + getSql("task.showDetail.showStepList.orderby");
        returnMap.put("stepList", db.queryForList(sql, new Object[]{str.get(map, "task_id")}));

        return returnMap;
    }

    /**
     * 已经处理订单详细信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object yetDisposeDitail(HttpServletRequest request,
            HttpServletResponse response) {
        String task_id = req.getAjaxValue(request, "task_id");
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        String sql = getSql("task.showDetail.main") + getSql("task.showDetail.taskid");
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("taskdetail", db.queryForMap(sql, new Object[]{task_id}));

        String sql1 = getSql("task.getPath");
        request.setAttribute("task_path", db.queryForString(sql1));
        sql = getSql("task.showDetail.showStepList.main") + getSql("task.showDetail.showStepList.orderby");
        returnMap.put("stepList", db.queryForList(sql, new Object[]{task_id}));
        return returnMap;
    }

    /**
     * 已处理订单列表
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object yetDisposeTask(HttpServletRequest request,
            HttpServletResponse response) {

        return null;
    }

    /**
     * 获取我已处理的订单信息
     *
     * @author 雷杨
     * @param request
     * @param response
     */
    public void getMyYetTaskList(HttpServletRequest request,
            HttpServletResponse response) {
        String taskName = req.getAjaxValue(request, "taskName");//任务名称
        String taskCode = req.getAjaxValue(request, "taskCode");//任务编号
        String sendTime = req.getAjaxValue(request, "sendTime");//发起时间
        String types = req.getAjaxValue(request, "types");//类型
        String status = req.getAjaxValue(request, "status");//类型
        String typelike = req.getAjaxValue(request, "typelike");//类型
        String lx = req.getAjaxValue(request, "lx");//2代表获取我待处理的任务信息
        User user = getUser(request);

        List<String> params = new ArrayList<String>();
        String sql = getSql("task.yetTaskList.main");
        if (!"".equals(taskName)) {
            sql += getSql("task.yetTaskList.taskname");
            params.add("%" + taskName + "%");
        }
        if (!"".equals(taskCode)) {
            sql += getSql("task.yetTaskList.taskcode");
            params.add("%" + taskCode + "%");
        }
        if (!"".equals(status)) {
            sql += getSql("task.yetTaskList.taskState");
            params.add(status);
        }
        if (!"".equals(types)) {
            sql += getSql("task.yetTaskList.tasktypeids");
            params.add(types);
            params.add("%-" + typelike + "-%");
        }
        if (!"".equals(sendTime)) {
            String state_time = sendTime.split("至")[0];
            String end_time = sendTime.split("至")[1];
            sql += getSql("task.yetTaskList.state_time");
            sql += getSql("task.yetTaskList.end_time");
            params.add(state_time);
            params.add(end_time);
        }
        if ("2".equals(lx)) {//获取待处理信息
            sql += getSql("task.yetTaskList.mycl");
            String org = user.getOrgId();
            String role = user.getRoles();
            String oper = user.getId();
            String[] orgs = org.split(",");
            String[] roles = role.split(",");
            String orgsql = "";
            String rulesql = "";
            for (int i = 0; i < roles.length; i++) {
                if (!"".equals(roles[i])) {
                    rulesql += " OR FIND_IN_SET(?,e.`now_role_id`) > 0";
                    params.add(roles[i]);
                }
            }
            for (int i = 0; i < orgs.length; i++) {
                if (!"".equals(orgs[i])) {
                    orgsql += " OR FIND_IN_SET(?,e.`now_org_id`) > 0";
                    params.add(orgs[i]);
                }
            }
            sql = sql.replace("#ORG#", orgsql).replace("#RULE#", rulesql);
            params.add(oper);
        } else if ("1".equals(lx)) {//我发起的流程
            sql += getSql("task.yetTaskList.mycl");
            params.add(user.getId());
        } else if ("3".equals(lx)) {//我处理过的流程
            sql += getSql("task.yetTaskList.mycl");
            params.add(user.getId());
        }
        getPageList(request, response, sql, params.toArray(), "task.yetTaskList.orderby");
    }

    /**
     * 获取下一步处理信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getNowNextStepList(HttpServletRequest request,
            HttpServletResponse response) {
        request.setAttribute("isMobile", getUser(request).getIs_mobile());
        String step_id = req.getAjaxValue(request, "step_id");//当前步骤id
        String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");//当前配置的流程信息
        String sql = getSql("task.getNowNextStepList.main");
        String next_step_id = db.queryForString(sql, new Object[]{step_id});
        sql = getSql("task.getNowNextStepList.list");
        sql = sql.replace("##", next_step_id);
        //需要检查是否存在并列任务在同步执行
        return db.queryForList(sql, new Object[]{task_cfg_id});
    }

    /**
     * 在任务步骤刚下发的情况下更改对应状态为刚阅读
     *
     * @author 雷杨
     * @param request
     * @param response
     */
    public void changeFlowState(HttpServletRequest request,
            HttpServletResponse response) {
        String sql = getSql("task.disposetTask.changeState");
        db.update(sql, new Object[]{req.getAjaxValue(request, "step_id")});
    }

    /**
     * 处理步骤信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object saveDisposeFlow(final HttpServletRequest request,
            HttpServletResponse response) {
        Object obj = db.doInTransaction(new Batch() {

            @Override
            public Object execute() throws Exception {
                String flowStepState = req.getAjaxValue(request, "flowStepState");
                String flowRemark = req.getAjaxValue(request, "flowRemark");
                String task_id = req.getAjaxValue(request, "task_id");
                String task_code = req.getAjaxValue(request, "task_code");
                String step_id = req.getAjaxValue(request, "step_id");
                String cfg_step_id = req.getAjaxValue(request, "cfg_step_id");
                String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
                User u = getUser(request);
                logger.debug("flowStepState:" + flowStepState);
                if ("".equals(flowStepState)) {
                    return getReturnMap(-1, "请选择处理状态");
                }
                Map<String, Object> stepMap = this.queryForMap(getSql("task.getStep").replace("##", cfg_step_id), new Object[]{task_cfg_id});
                logger.debug("stepMap:" + stepMap);
                if (stepMap.isEmpty()) {//未查询到步骤配置信息
                    return getReturnMap("-61", "未查询到当前步骤配置信息");
                }
                TaskStepBean taskStepBean = new TaskStepBean();
                taskStepBean.setTask_id(task_id + "");
                taskStepBean.setStep_id(step_id);
                taskStepBean.setOper_id(u.getId());
                taskStepBean.setStep_name(str.get(stepMap, "step_name"));
                taskStepBean.setNext_step_id("");
                taskStepBean.setIs_overtime("0");
                taskStepBean.setCfg_step_id(cfg_step_id);
                taskStepBean.setRemark(flowRemark);
                taskStepBean.setState(flowStepState);
                taskStepBean.setDispose_class(str.get(stepMap, "dispose_class"));
                //先处理当前步骤信息
                Map<String, Object> mapStep = disposeStep(request, this, taskStepBean);
                int isover = 0;//任务是否全部执行完毕
                String taskStepId = "";
                String taskStepName = "";
                logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + str.get(mapStep, "state"));
                if ("0".equals(str.get(mapStep, "state"))) {//自定义的下一步操作步骤
                    if ("".equals(str.get(mapStep, "step_id"))) {
                        return getReturnMap(-99, "自定义步骤失败");
                    }
                    if ("".equals(str.get(mapStep, "step_name"))) {
                        return getReturnMap(-99, "自定义步骤失败");
                    }
                    taskStepId = str.get(mapStep, "step_id");
                    taskStepName = str.get(mapStep, "step_name");
                    taskStepBean.setNext_step_id(str.get(mapStep, "step_id"));
                    taskStepBean.setFile_id(str.get(mapStep, "file_id"));
                    if ("9".equals(taskStepBean.getState())) {
                        taskStepBean.setState("2");
                    }
                    //更新当前步骤处理信息
                    String sql = getSql("task.disposetTask.updateStep");
                    this.update(sql, new Object[]{taskStepBean.getFile_id(), taskStepBean.getIs_overtime(), taskStepBean.getNext_step_id(), taskStepBean.getOper_id(), taskStepBean.getRemark(), taskStepBean.getState(), taskStepBean.getStep_id()});
                    return getReturnMap(1, "操作成功");
                } else if ("1".equals(str.get(mapStep, "state"))) {//系统统一定义下一步
                    if (FlowStepState.END_STEP.equals(flowStepState)) {//
                        //根据当前步骤的配置信息查下一步的步骤信息
                        List<Map<String, Object>> nextList = this.queryForList(getSql("task.getStep").replace("##", str.get(stepMap, "next_step_id")), new Object[]{task_cfg_id});
                        String nextStepIds = "";
                        String nextStepNames = "";
                        logger.debug("nextList:" + nextList);
                        //循环插入下一步信息
                        for (int i = 0; i < nextList.size(); i++) {
                            Map<String, Object> nextStepMap = nextList.get(i);
                            //
                            String sql = getSql("task.insertStep");

                            int stepId = this.insert(sql, new Object[]{task_id, str.get(nextStepMap, "step_name"), step_id, str.get(nextStepMap, "step_id"), str.get(nextStepMap, "org_id"), str.get(nextStepMap, "rule_id"), str.get(nextStepMap, "oper")});
                            if (stepId == -1) {
                                return getReturnMap(-33, "创建下一步任务失败");
                            }
                            if ("0".equals(str.get(nextStepMap, "next_step_id"))) {//当前是最后一步，直接结束任务
                                sql = getSql("task.disposetTask.updateStep");
                                this.update(sql, new Object[]{"", "0", "0", u.getId(), "", FlowStepState.END_STEP, stepId});
                                isover = 1;
                            }
                            nextStepIds += stepId + ",";
                            nextStepNames += str.get(nextStepMap, "step_name") + ",";
                        }
                        taskStepId = nextStepIds.substring(0, nextStepIds.length() - 1);
                        taskStepName = nextStepNames.substring(0, nextStepNames.length() - 1);
                        taskStepBean.setNext_step_id(nextStepIds.substring(0, nextStepIds.length() - 1));
                    } else if (FlowStepState.HANG_STEP.equals(flowStepState)) {//挂起 不发送下一步处理 只记录当前步骤信息
                        taskStepId = step_id;
                        taskStepName = str.get(stepMap, "step_name");
                        isover = 2;
                    } else if (FlowStepState.ROLLBACK_STEP.equals(flowStepState)) {//回退当前任务到上一步操作
                        //回退的时候需要区分是否是首第一个步骤就退回了，如果是第一个步骤就进行回退流程直接结束，否则继续

                        //查询出当前步骤对应的上一步处理信息
                        String sql = getSql("task.getStepMap");
                        Map<String, Object> nowStepMap = this.queryForMap(sql, new Object[]{step_id});
                        if (nowStepMap.isEmpty()) {//未查询到当前步骤信息
                            return getReturnMap(-34, "未查询到当前步骤信息");
                        }
                        String nowSupStepId = str.get(nowStepMap, "sup_step_id");//当前步骤对应的上一步处理信息stepid
                        Map<String, Object> supStepMap1 = this.queryForMap(sql, new Object[]{nowSupStepId});
                        String supStepId = str.get(supStepMap1, "sup_step_id");
                        logger.debug("nowSupStepId:" + nowSupStepId);
                        if ("-1".equals(supStepId)) {//在回退就回退到第一步了，此处就直接结束流程
                            isover = 1;
                            //查询出上一步处理人信息
                            Map<String, Object> supStepMap = this.queryForMap(getSql("task.getOverStep"), new Object[]{task_cfg_id});
                            if (supStepMap.isEmpty()) {
                                return getReturnMap(-34, "未查询到上一步骤信息");
                            }
                            sql = getSql("task.insertStep");
                            int stepId = this.insert(sql, new Object[]{task_id, str.get(supStepMap, "step_name"), step_id, str.get(supStepMap, "step_id"), "", "", str.get(supStepMap, "oper_id")});
                            if (stepId == -1) {
                                return getReturnMap(-33, "创建下一步任务失败");
                            }

                            sql = getSql("task.disposetTask.updateStep");
                            this.update(sql, new Object[]{"", "0", "0", u.getId(), "", FlowStepState.END_STEP, stepId});

                            taskStepId = stepId + "";
                            taskStepName = str.get(supStepMap, "step_name");
                            taskStepBean.setNext_step_id(stepId + "");
                        } else {
                            //查询出上一步处理人信息
                            Map<String, Object> supStepMap = this.queryForMap(sql, new Object[]{nowSupStepId});

                            if (supStepMap.isEmpty()) {
                                return getReturnMap(-34, "未查询到上一步骤信息");
                            }

                            sql = getSql("task.insertStep");
                            int stepId = this.insert(sql, new Object[]{task_id, str.get(supStepMap, "step_name"), step_id, str.get(supStepMap, "cfg_step_id"), "", "", str.get(supStepMap, "oper_id")});
                            if (stepId == -1) {
                                return getReturnMap(-33, "创建下一步任务失败");
                            }
                            taskStepId = stepId + "";
                            taskStepName = str.get(supStepMap, "step_name");
                            taskStepBean.setNext_step_id(stepId + "");
                        }
                    }
                    //更新当前步骤处理信息
                    String sql = getSql("task.disposetTask.updateStep");
                    this.update(sql, new Object[]{taskStepBean.getFile_id(), taskStepBean.getIs_overtime(), taskStepBean.getNext_step_id(), taskStepBean.getOper_id(), taskStepBean.getRemark(), taskStepBean.getState(), taskStepBean.getStep_id()});
                    //更新主线任务状态
                    sql = getSql("task.disposetTask.updateTask");
                    this.update(sql.replace("#endtime#", isover == 1 ? "now()" : "null"), new Object[]{0, isover == 2 ? FlowTaskState.BREAK_TASK : isover == 1 ? FlowTaskState.OVER_TASK : FlowTaskState.START_TASK, 0, taskStepId, taskStepName, task_id});
                } else if ("2".equals(str.get(mapStep, "state"))) {//自定义下一步操作，不更改当前步骤状态信息

                    if ("".equals(str.get(mapStep, "step_id"))) {
                        return getReturnMap(-99, "自定义步骤失败");
                    }
                    if ("".equals(str.get(mapStep, "step_name"))) {
                        return getReturnMap(-99, "自定义步骤失败");
                    }
                    taskStepId = str.get(mapStep, "step_id");
                    taskStepName = str.get(mapStep, "step_name");
                    taskStepBean.setNext_step_id(str.get(mapStep, "step_id"));
                    taskStepBean.setFile_id(str.get(mapStep, "file_id"));

                    return getReturnMap(1, "操作成功");
                }
                return mapStep;
            }

        });
        if (obj == null) {
            return getReturnMap(-1, "处理异常");
        } else {
            return (Map<String, Object>) obj;
        }
    }

    /**
     * 获取步骤处理详细信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    public String showTaskDetail(final HttpServletRequest request,
            HttpServletResponse response) {
        //获取当前步骤详细信息
        Object obj = db.doInTransaction(new Batch() {
            @Override
            public Object execute() throws Exception {
                String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
                String task_id = req.getAjaxValue(request, "task_id");
                String step_id = req.getAjaxValue(request, "step_id");
                String edit = req.getAjaxValue(request, "edit");
                String sql = getSql("task.showDetail.showBzDetail.main");
                Map<String, Object> map = db.queryForMap(sql, new Object[]{step_id});

                sql = getSql("task.getCfgTask");
                Map<String, Object> taskMap = db.queryForMap(sql, new Object[]{task_cfg_id});

                String html_path = str.get(taskMap, "html_path");
                String dispose = str.get(taskMap, "dispose_class");
                logger.debug("dispose:" + dispose);
                //获取对应的处理class
                if (!"".equals(dispose)) {
                    try {
                        Class cls = Class.forName(dispose);
                        Object obj = cls.newInstance();
                        if (obj instanceof FlowStepDisposeInterface) {
                            FlowStepDisposeInterface flow = (FlowStepDisposeInterface) obj;
                            Object returnObj = flow.showStepDetail(this, request, step_id, task_id, "1".equals(edit) ? true : false, str.get(map, "cfg_step_id"));
                            if (returnObj instanceof Map) {
                                request.setAttribute("flowDetaiMap", (Map<String, Object>) returnObj);
                                return html_path;
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return -111;
                    }
                } else {
                    return html_path;
                }
            }
        });
        if (obj != null) {
            String result = "flow/pages/base/default";
            return "".equals(String.valueOf(obj)) ? result : String.valueOf(obj);
        } else {
            return "flow/pages/base/default";
        }
    }

    /**
     * 删除操作
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object deleteFlow(final HttpServletRequest request,
            HttpServletResponse response) {
        Object obj = db.doInTransaction(new Batch() {

            @Override
            public Object execute() throws Exception {
                String task_id = req.getAjaxValue(request, "task_id");
                String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
                String sql = getSql("task.getCfgTask");
                Map<String, Object> taskMap = db.queryForMap(sql, new Object[]{task_cfg_id});
                String dispose = str.get(taskMap, "dispose_class");

                User u = getUser(request);
                //删除流程本身信息
                List<String> params = new ArrayList<String>();
                params.add(task_id);
                this.insertTableLog(request, "yc_task_info_tab", " and a.task_id = ?", "删除流程", params, u.getId());
                this.update(getSql("task.deleteFlow"), params.toArray());
                this.insertTableLog(request, "yc_task_step_info_tab", "  and a.task_id = ?", "删除流程", params, u.getId());
                this.update(getSql("task.deleteFlowStep"), params.toArray());
                //获取对应的处理class
                if (!"".equals(dispose)) {
                    try {
                        Class cls = Class.forName(dispose);
                        Object obj = cls.newInstance();
                        if (obj instanceof FlowStepDisposeInterface) {
                            FlowStepDisposeInterface flow = (FlowStepDisposeInterface) obj;
                            Object returnObj = flow.deleteFlow(this, task_id);
                            if (returnObj instanceof Map) {
                                return (Map<String, Object>) returnObj;
                            } else {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } else {
                    return null;
                }
            }
        });
        if (obj == null) {
            return getReturnMap(-3, "操作失败");
        } else {
            return (Map<String, Object>) obj;
        }
    }

    /**
     * 获取任务类型
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getTypeCreateList(HttpServletRequest request,
            HttpServletResponse response) {
        String sql = getSql("task.getType");
        return db.queryForList(sql, new Object[]{0});
    }

    /**
     * 计算退租金额
     *
     * @author 刘飞
     * @param request
     * @param response
     * @return
     * @throws ParseException
     */
    public Object calculate(HttpServletRequest request, HttpServletResponse response) throws ParseException, TaskException {
        Map<String, String> nowCostMap = new HashMap<String, String>();
        String agreeId = req.getAjaxValue(request, "agreeId");
        String order_time = req.getAjaxValue(request, "order_time");
        Map<String, String> agreeMap = db.queryForMap(getSql("orderService.task.getAgreement"), new Object[]{agreeId});
        //查询当前已交费用
        String sql = getSql("orderService.task.getAllCost");
        String allCost = db.queryForString(sql, new Object[]{agreeId});//已交费用总计
        float nowcoat = 0;

        nowCostMap.put("allCost", allCost);
        String start_month = str.get(agreeMap, "begin_time").substring(0, 7) + "-01";
        int start_date = Integer.parseInt(str.get(agreeMap, "begin_time").substring(8, 10));

        String end_month = order_time.substring(0, 7) + "-01";
        int end_date = Integer.parseInt(order_time.substring(8, 10));

        int month = getMonthSpace(start_month, end_month);//已入住月份

        // logger.debug("-------------------------- startmonth"+month);
        nowCostMap.put("cost_a", String.valueOf(str.get(agreeMap, "cost_a")));
        nowCostMap.put("month", String.valueOf(month));
        if (end_date == 31) {
            end_date = 30;
        }
        if (start_date == 31) {
            start_date = 30;
        }
        if (Integer.parseInt(end_month.substring(5, 7)) == 2 && (end_date == 28 || end_date == 29)) {
            end_date = 30;
        }
        int day = end_date - start_date + 1; //取得当月租住天数（签订月份当天10-已租住至当月的天数15）//
        // int day1=returnday(order_time, str.get(agreeMap, "begin_time"));//当月为止已租住天数
        //  logger.debug("-------------------------- startday"+day);
        nowCostMap.put("day", String.valueOf((month * 30) + day));
        if (allCost != null && !"".equals(allCost)) {

        } else {
            allCost = "0";
        }
        //   logger.debug("-------------------------- startday1"+Float.parseFloat(str.get(agreeMap, "cost_a"))*month);
        //   logger.debug("-------------------------- startday2"+(Float.parseFloat(str.get(agreeMap, "cost_a"))/30)*day);
        nowcoat = Float.parseFloat(allCost) - ((Float.parseFloat(str.get(agreeMap, "cost_a")) * month) + ((Float.parseFloat(str.get(agreeMap, "cost_a")) / 30) * day));
        nowcoat = (float) (Math.round(nowcoat * 100) / 100);
        //  logger.debug("-------------------------- startnowcoat"+nowcoat);
        nowCostMap.put("nowcoat", String.valueOf(nowcoat));
        return nowCostMap;
    }
    //返回日期间的当月租期的天数

    private int returnday(String orderTime, String start_time) throws ParseException, TaskException {
        String end_time = DateHelper.getMonthDay(1, "yyyy-MM-dd", start_time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 11; i++) {
            start_time = DateHelper.getMonthDay(1, "yyyy-MM-dd", start_time);
            end_time = DateHelper.getMonthDay(1, "yyyy-MM-dd", start_time);
            if (sdf.parse(start_time).before(sdf.parse(orderTime)) && sdf.parse(orderTime).before(sdf.parse(end_time))) {
                int day = DateHelper.getDaysDiff(sdf.parse(orderTime), sdf.parse(start_time));//当前季度中当前月份已租住的天数
                return day;
            }
        }
        return 0;
    }
    //返回日期间的月份数

    private static int getMonthSpace(String date1, String date2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date2));
        c2.setTime(sdf.parse(date1));
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) {
            return 0;
        }
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
        int yearInterval = year1 - year2;
        // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
        if (month1 < month2 || month1 == month2 && day1 < day2) {
            yearInterval--;
        }
        // 获取月数差值
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) {
            monthInterval--;
        }
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 获取录入数据
     *
     * @author 刘飞
     * @param request
     * @param response
     * @return
     */
    public Object getInfo(HttpServletRequest request, HttpServletResponse response) {
        String task_id = req.getAjaxValue(request, "task_id");
        String sql = getSql("checkhouse.query.main");
        List<String> params = new ArrayList<String>();
        if (!"".equals(task_id)) {
            sql += getSql("checkhouse.query.task_id");
            params.add(task_id);
        }
        Map<String, Object> returnMap = db.queryForMap(sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_LEASEORDER_HTTP_PATH")), params.toArray());
        return returnMap;
    }

    /**
     * 查询财务明细
     * @param request
     * @param response
     * @return
     */
		public Object getFinanceDetails(HttpServletRequest request, HttpServletResponse response)
		{
			Map<String, Object> map = new HashMap<>();
			String ager_id = req.getAjaxValue(request, "ager_id");
			Result a = financial.getFinancialList(ager_id);
			map.put("state", a.getState());
			map.put("data", a.getList());
			return map;
		}

		public Object getFinanceTable(HttpServletRequest request, HttpServletResponse response)
		{
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> list = new ArrayList<>();
			String ager_id = req.getAjaxValue(request, "ager_id");
			int isAgen = Integer.valueOf(req.getAjaxValue(request, "isAgen"));
			int isNormal = Integer.valueOf(req.getAjaxValue(request, "isNormal"));
			String orderId = req.getAjaxValue(request, "orderId");
			// 应收
			Map<String, Object> ysMap = new HashMap<>();
			ysMap.put("typeName", "应收");
			
			// 实付
			Map<String, Object> sfMap = new HashMap<>();
			sfMap.put("typeName", "实付");
			
			// 优惠
			Map<String, Object> yhMap = new HashMap<>();
			yhMap.put("typeName", "优惠");
			
			// 总计
			Map<String, Object> zjMap = new HashMap<>();
			zjMap.put("typeName", "总计");
			String summarizing = "";
			
			// 水费参考值
			String water = "";
			// 电费参考值
			String eMeter = "";
			// 煤气参考值
			String cardgas = "";
			int state = 0;
			try
			{
				Result a = financial.getSettleBill(ager_id, isAgen, isNormal, orderId);
				state = a.getState();
				if (a.getState() != 1) 
				{
					map.put("state", 0);
					map.put("data", a.getMsg());
					return map;
				} else 
				{
					Map<String, Object> infoMap = a.getMap();
					logger.info("infoMap = " + infoMap);
					for (Map.Entry<String, Object> entry : infoMap.entrySet()) 
					{
						String key = entry.getKey();
						// 金额
						String len = String.valueOf(key);
						int num = 0;
						if (len.contains("_"))
						{
							num = Integer.valueOf(String.valueOf(key).split("_")[2]);
						}
						if (key.contains("_yf"))
						{
							dealVal(num, String.valueOf(entry.getValue()), ysMap);
						} else if (key.contains("_sf"))
						{
							dealVal(num, String.valueOf(entry.getValue()), sfMap);
						} else if (key.contains("_yh"))
						{
							dealVal(num, String.valueOf(entry.getValue()), yhMap);
						} else if (key.contains("_zj"))
						{
							dealVal(num, String.valueOf(entry.getValue()), zjMap);
						} else if (key.contains("summarizing")) 
						{
							summarizing = String.valueOf(entry.getValue());
						} else if (key.contains("water"))
						{
							water = String.valueOf(entry.getValue());
						} else if (key.contains("eMeter"))
						{
							eMeter = String.valueOf(entry.getValue());
						} else if (key.contains("cardgas"))
						{
							cardgas = String.valueOf(entry.getValue());
						}
					}
				}
				list.add(ysMap);
				list.add(sfMap);
				list.add(yhMap);
				list.add(zjMap);
				map.put("data", list);
				map.put("summarizing", summarizing);
				map.put("water", water);
				map.put("eMeter", eMeter);
				map.put("cardgas", cardgas);
				map.put("state", state);
			} catch (ParseException e)
			{
				map.put("state", 1);
				map.put("data", list);
				return map;
			}
			
			logger.info("map === " + map);
			return map;
		}

		private void dealVal(int num, String valStr, Map<String, Object> map)
		{
			switch (num)
			{
			case 1:
				map.put("rent", valStr);// 租金
				break;
			case 2:
				map.put("property", valStr); // 物业 
				break;
			case 4:
				map.put("pay", valStr); // 代缴费 
				break;
			case 5:
				map.put("deposit", valStr); // 押金 
				break;
			case 6:
				map.put("serviceFee", valStr); // 服务费
				break;
			case 10:
				map.put("other", valStr); // 其他
				break;
			case 11:
				map.put("waterFee", valStr); // 水费
				break;
			case 12:
				map.put("electricityFee", valStr); // 电费
				break;
			case 13:
				map.put("gasCosts", valStr); // 燃气费
				break;
			case 16:
				map.put("trashFee", valStr); // 垃圾费
				break;
			default:
				map.put("rent", valStr);// 租金
				break;
			}
		}
}
