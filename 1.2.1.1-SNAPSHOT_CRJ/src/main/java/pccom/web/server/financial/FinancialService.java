/*
 * Copyright (c) 2014  . All Rights Reserved.
 */
package pccom.web.server.financial;

import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import com.ycqwj.ycqwjApi.request.PlushRequest;
import com.ycqwj.ycqwjApi.request.SmsRequest;
import com.ycqwj.ycqwjApi.request.WeiXinRequest;
import com.ycqwj.ycqwjApi.request.bean.plush.AppBillBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pccom.common.annotation.LogAnnotation;
import pccom.common.util.AmountUtil;
import pccom.common.util.Batch;
import pccom.common.util.BatchSql;
import pccom.common.util.Constants;
import pccom.common.util.DateHelper;
import pccom.common.util.HttpURLConnHelper;
import pccom.common.util.StringHelper;
import pccom.common.util.Validator;
import pccom.web.beans.User;
import pccom.web.flow.base.FlowBase;
import pccom.web.interfaces.RankHouse;
import pccom.web.job.jd.JdTask;

@Service("financialService")
public class FinancialService extends FlowBase {

    @Autowired
    private pccom.web.interfaces.CommonPayService commonPayService;
    
    private ScheduledExecutorService service = Executors.newScheduledThreadPool(100);

    /**
     * 获取列表
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    @LogAnnotation("出账获取列表")
    public Object getList(HttpServletRequest request,
            HttpServletResponse response) {
        String category_type = req.getAjaxValue(request, "category_type");
        String secondary_type = req.getAjaxValue(request, "secondary_type");//类型 0收房 1出租2服务
        String name = req.getAjaxValue(request, "name");
        String plattime = req.getAjaxValue(request, "plattime");
        String status = req.getAjaxValue(request, "status");
        String isfq = req.getAjaxValue(request, "isfq");
        String ispayemp = req.getAjaxValue(request, "ispayemp");//是否导出可支付信息
        String plattime_state = "";
        String plattime_end = "";
        if (!"".equals(plattime)) {
            plattime_state = plattime.substring(0, plattime.indexOf("至"));
            plattime_end = plattime.substring(plattime.indexOf("至") + 1);
        }
        String paytime = req.getAjaxValue(request, "paytime");
        String paytime_state = "";
        String paytime_end = "";
        if (!"".equals(paytime)) {
            paytime_state = paytime.substring(0, paytime.indexOf("至"));
            paytime_end = paytime.substring(paytime.indexOf("至") + 1);
        }
        String sql = getSql("financial.payable.main");
        if ("1".equals(secondary_type) || "0".equals(secondary_type) || "2".equals(secondary_type)) {
            sql = getSql("financial.payable.mainpay");
        }
        if ("1".equals(ispayemp)) {//可导出数据
            sql = getSql("financial.payable.mainIsEmp");
        }
        List<String> params = new ArrayList<String>();
        if (!"".equals(category_type)) {
            sql += getSql("financial.payable.category_type");
            params.add(category_type);
        }
        if (!"".equals(secondary_type)) {
            sql += getSql("financial.payable.secondary_type");
            params.add(secondary_type);
        }
        if (!"".equals(name)) {
            if ("1".equals(secondary_type) || "0".equals(secondary_type) || "2".equals(secondary_type)) {
                sql += getSql("financial.payable.rename");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
            } else {
                sql += getSql("financial.payable.name");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
            }
            params.add("%" + name + "%");
            params.add("%" + name + "%");
        }
        if (!"".equals(plattime_state)) {
            sql += getSql("financial.payable.plattime_state");
            params.add(plattime_state);
        }
        if (!"".equals(plattime_end)) {
            sql += getSql("financial.payable.plattime_end");
            params.add(plattime_end);
        }
        if (!"".equals(paytime_state)) {
            sql += getSql("financial.payable.paytime_state");
            params.add(paytime_state);
        }
        if (!"".equals(paytime_end)) {
            sql += getSql("financial.payable.paytime_end");
            params.add(paytime_end);
        }
        if (!"".equals(status)) {
            sql += getSql("financial.payable.status");
            params.add(status);
        }
        if ("1".equals(isfq)) {//可发起
            sql += getSql("financial.payable.isfqtrue");
        } else if ("0".equals(isfq)) {//不可发起
            sql += getSql("financial.payable.isfqfalse");
        }
        getPageList(request, response, sql, params.toArray(), "financial.payable.orderby");
        return null;
    }

    /**
     * 获取项目列表
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public void getProjectList(HttpServletRequest request,
            HttpServletResponse response) {
        String jstype = req.getAjaxValue(request, "jstype");
        String xmname = req.getAjaxValue(request, "xmname");
        String xmcode = req.getAjaxValue(request, "xmcode");
        String actiontime = req.getAjaxValue(request, "actiontime");
        String state = req.getAjaxValue(request, "state");
        String updatetime_state = "";
        String updatetime_end = "";
        //logger.debug("?11??:"+actiontime);
        if (!"".equals(actiontime)) {
            updatetime_state = actiontime.substring(0, actiontime.indexOf("至"));
            updatetime_end = actiontime.substring(actiontime.indexOf("至") + 1);
        }
        String sql = getSql("financial.project.query.main");
        List<String> params = new ArrayList<String>();
        if (!"".equals(jstype)) {
            sql += getSql("financial.project.query.type");
            params.add(jstype);
        }
        if (!"".equals(xmname)) {
            sql += getSql("financial.project.query.name");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
        }
        if (!"".equals(updatetime_state)) {
            sql += getSql("financial.project.query.time_start");
            params.add(updatetime_state);
        }
        if (!"".equals(updatetime_end)) {
            sql += getSql("financial.project.query.time_end");
            params.add(updatetime_end);
        }
        if (!"".equals(xmcode)) {
            sql += getSql("financial.project.query.xmcode");
            params.add("%" + xmcode + "%");
        }
        if (!"".equals(state)) {
            sql += getSql("financial.project.query.state");
            params.add(state);
        }

        getPageList(request, response, sql, params.toArray(), "financial.project.query.orderby");
    }

    /**
     * 获取项目列表
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public void getProjectList1(HttpServletRequest request,
            HttpServletResponse response) {
        String jstype = req.getAjaxValue(request, "jstype");
        String xmname = req.getAjaxValue(request, "xmname");
        String xmcode = req.getAjaxValue(request, "xmcode");
        String actiontime = req.getAjaxValue(request, "actiontime");
        String state = req.getAjaxValue(request, "state");
        String updatetime_state = "";
        String updatetime_end = "";

        if (!"".equals(actiontime)) {
            updatetime_state = actiontime.substring(0, actiontime.indexOf("至"));
            updatetime_end = actiontime.substring(actiontime.indexOf("至") + 1);
        }
        String sql = getSql("financial.project.query.newMain");
        List<String> params = new ArrayList<String>();
        if (!"".equals(jstype)) {
            sql += getSql("financial.project.query.type");
            params.add(jstype);
        }
        if (!"".equals(xmname)) {
            sql += getSql("financial.project.query.name");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
        }
        if (!"".equals(updatetime_state)) {
            sql += getSql("financial.project.query.time_start");
            params.add(updatetime_state);
        }
        if (!"".equals(updatetime_end)) {
            sql += getSql("financial.project.query.time_end");
            params.add(updatetime_end);
        }
        if (!"".equals(xmcode)) {
            sql += getSql("financial.project.query.xmcode");
            params.add("%" + xmcode + "%");
        }
        if (!"".equals(state)) {
            sql += getSql("financial.project.query.state");
            params.add(state);
        }
        //logger.debug("sql:"+str.getSql(sql, params.toArray()));

        //创建临时表
        String tmpTableName = "tmp_ly_" + new Random().nextInt(99999);
        db.update(sql.replace("#table#", tmpTableName), params.toArray());
        //查询出符合条件对应的上级信息
        String tmpTableNameSup = "tmp_ly_sup" + new Random().nextInt(99999);
        sql = getSql("financial.project.query.supMain");
        //db.update(sql.replace("#table1#",tmpTableName),params.toArray());
        //查询
        try {
            //logger.debug( sql.replace("#table1#",tmpTableName));
            getPageList(request, response, sql.replace("#table1#", tmpTableName), new Object[]{}, "financial.project.query.orderby");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.update("drop table " + tmpTableName);
            //db.update("drop table "+tmpTableNameSup);
        }
    }

    /**
     * 获取项目列表
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getItemProjectList(HttpServletRequest request,
            HttpServletResponse response) {
        String jstype = req.getAjaxValue(request, "jstype");
        String xmname = req.getAjaxValue(request, "xmname");
        String xmcode = req.getAjaxValue(request, "xmcode");
        String actiontime = req.getAjaxValue(request, "actiontime");
        String state = req.getAjaxValue(request, "state");
        String supper_id = req.getAjaxValue(request, "supper_id");
        String updatetime_state = "";
        String updatetime_end = "";

        if (!"".equals(actiontime)) {
            updatetime_state = actiontime.substring(0, actiontime.indexOf("至"));
            updatetime_end = actiontime.substring(actiontime.indexOf("至") + 1);
        }

        String sql = getSql("financial.project.query.itemMain");
        List<String> params = new ArrayList<String>();
        if (!"".equals(jstype)) {
            sql += getSql("financial.project.query.type");
            params.add(jstype);
        }
        if (!"".equals(xmname)) {
            sql += getSql("financial.project.query.name");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
            params.add("%" + xmname + "%");
        }
        if (!"".equals(updatetime_state)) {
            sql += getSql("financial.project.query.time_start");
            params.add(updatetime_state);
        }
        if (!"".equals(updatetime_end)) {
            sql += getSql("financial.project.query.time_end");
            params.add(updatetime_end);
        }
        if (!"".equals(xmcode)) {
            sql += getSql("financial.project.query.xmcode");
            params.add("%" + xmcode + "%");
        }
        if (!"".equals(state)) {
            sql += getSql("financial.project.query.state");
            params.add(state);
        }
        if (!"".equals(supper_id)) {
            sql += getSql("financial.project.query.supper_id");
            params.add(supper_id);
        }
        Map<String, Object> returnMap = getReturnMap(1);
        returnMap.put("list", JSONArray.fromObject(db.queryForList(sql, params)));
        return returnMap;
    }

    /**
     * 项目保存
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object projectSave(HttpServletRequest request,
            HttpServletResponse response) {
        String type = req.getAjaxValue(request, "type");
        String name = req.getAjaxValue(request, "name");
        String capital_receivable = req.getAjaxValue(request, "capital_receivable");
        String capital_payable = req.getAjaxValue(request, "capital_payable");
        String remark = req.getAjaxValue(request, "remark");
        String pagetype = req.getAjaxValue(request, "pagetype");
        String correlation_id = req.getAjaxValue(request, "correlation_id");

        if ("".equals(type)) {
            return getReturnMap(-2);
        }
        if ("".equals(name)) {
            return getReturnMap(-3);
        }
        if ("".equals(capital_receivable) || (!Validator.isInteger(capital_receivable) && !Validator.isDouble(capital_receivable))) {
            return getReturnMap(-4);
        }
        if ("".equals(capital_payable) || (!Validator.isInteger(capital_payable) && !Validator.isDouble(capital_payable))) {
            return getReturnMap(-5);
        }
        //logger.debug("pagetype11："+pagetype);
        //新增操作
        if ("add".equals(pagetype)) {
            //进行插入操作
            String sql = getSql("financial.project.insert");
            return getReturnMap(db.update(sql, new Object[]{type, name, capital_receivable, capital_payable, remark}));
        } else {
            //进行插入操作
            String sql = getSql("financial.project.update");
            return getReturnMap(db.update(sql, new Object[]{capital_payable, capital_receivable, name, remark, type, correlation_id}));
        }
    }

    /**
     * 获取项目单条记录信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getProjectMap(HttpServletRequest request,
            HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");
        //获取此项目对应的收入与支出项目信息
        Map<String, Object> returnMap = getReturnMap(1);
        returnMap.put("pay", db.queryForList(getSql("financial.project.getPay"), new Object[]{id}));
        returnMap.put("res", db.queryForList(getSql("financial.project.getRes"), new Object[]{id}));
        returnMap.put("qd", db.queryForList(getSql("financial.project.getQdList"), new Object[]{id}));
        returnMap.put("qdres", db.queryForList(getSql("financial.project.getQdResList"), new Object[]{id}));
        return returnMap;
    }

    /**
     * 删除项目信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object deleteProject(HttpServletRequest request,
            HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");
        String sql = getSql("financial.project.delete");
        return getReturnMap(db.update(sql, new Object[]{id}));
    }

    /**
     * 查询财务类型
     *
     * @param request
     * @param response
     */
    public Object getTypeListInfo(HttpServletRequest request, HttpServletResponse response) {
        // TODO 查询条件是啥来着？
        String sql = "select a.id,a.name from financial_category_tab a where a.status=1 and a.istz=1";
        return db.queryForList(sql);
    }

    /**
     * 查询财务类目
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getTypeList(HttpServletRequest request,
            HttpServletResponse response) {
        String name = req.getAjaxValue(request, "lmname");
        String sql = getSql("financial.type.query.main");
        List<String> params = new ArrayList<String>();
        if (!"".equals(name)) {
            sql += getSql("financial.type.query.name");
            params.add("%" + name + "%");
        }
        getPageList(request, response, sql, params.toArray(), "financial.project.query.orderby");
        return null;
    }

    /**
     * 保存 财务类目
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object typeSave(HttpServletRequest request,
            HttpServletResponse response) {
        String name = req.getAjaxValue(request, "name");
        String remark = req.getAjaxValue(request, "remark");
        String pagetype = req.getAjaxValue(request, "pagetype");
        String id = req.getAjaxValue(request, "id");

        User user = getUser(request);
        String oper_id = user.getId();

        if ("".equals(name)) {
            return getReturnMap(-2);
        }
        //新增操作
        if ("add".equals(pagetype)) {
            //进行插入操作
            String sql = getSql("financial.type.insert");
            return getReturnMap(db.update(sql, new Object[]{id, name, remark, oper_id}));
        } else {
            //进行插入操作
            String sql = getSql("financial.type.update");
            BatchSql batch = new BatchSql();
            this.insertTableLog(request, batch, "financial_category_tab", " and a.id = ?", "更新操作", new Object[]{id, name, remark, oper_id, id});
            batch.addBatch(sql, new Object[]{id, name, remark, oper_id, id});
            return getReturnMap(db.doInTransaction(batch));
        }
    }

    /**
     * 获取财务类目单条信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getTypeMap(HttpServletRequest request,
            HttpServletResponse response) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 删除财务类目
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object deletetype(HttpServletRequest request,
            HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");
        String sql = getSql("financial.type.delete");
        return getReturnMap(db.update(sql, new Object[]{id}));
    }

    /**
     * 加载财务中心数据信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object loadFinanceData(HttpServletRequest request,
            HttpServletResponse response) {
        //待收入
        Map<String, Object> dsrMap = db.queryForMap(getSql("financial.dsr.main") + getSql("financial.dsr.dsrdate"), new Object[]{0});
        //待支出
        Map<String, Object> dzcMap = db.queryForMap(getSql("financial.dzc.main") + getSql("financial.dzc.dzcdate"), new Object[]{0});
        //实际收入
        Map<String, Object> sjsrMap = db.queryForMap(getSql("financial.dsr.main"), new Object[]{1});
        //实际支出
        Map<String, Object> sjzcMap = db.queryForMap(getSql("financial.dzc.main"), new Object[]{1});

        Map<String, Object> returnMap = getReturnMap(1);
        returnMap.put("dsrMap", JSONObject.fromObject(dsrMap));
        returnMap.put("dzcMap", JSONObject.fromObject(dzcMap));
        returnMap.put("sjsrMap", JSONObject.fromObject(sjsrMap));
        returnMap.put("sjzcMap", JSONObject.fromObject(sjzcMap));
        returnMap.put("zxtMap", JSONObject.fromObject(srzcZxt(request, response)));
        return returnMap;
    }

    /**
     * 加载每天的数据信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object loadCost(HttpServletRequest request,
            HttpServletResponse response) {
        String date = req.getAjaxValue(request, "date");
        Map<String, Object> returnMap = getReturnMap(1);
        //待收入
        Map<String, Object> dsrMap = db.queryForMap(getSql("financial.dsr.main") + getSql("financial.dsr.date"), new Object[]{0, date});
        //待支出
        Map<String, Object> dzcMap = db.queryForMap(getSql("financial.dzc.main") + getSql("financial.dzc.date"), new Object[]{0, date});
        //实际收入
        Map<String, Object> sjsrMap = db.queryForMap(getSql("financial.dsr.main") + getSql("financial.dzc.date"), new Object[]{1, date});
        //实际支出
        Map<String, Object> sjzcMap = db.queryForMap(getSql("financial.dzc.main") + getSql("financial.dzc.date"), new Object[]{1, date});

        returnMap.put("dsrMap", JSONObject.fromObject(dsrMap));
        returnMap.put("dzcMap", JSONObject.fromObject(dzcMap));
        returnMap.put("sjsrMap", JSONObject.fromObject(sjsrMap));
        returnMap.put("sjzcMap", JSONObject.fromObject(sjzcMap));

        return returnMap;
    }

    /**
     * 收入与支出折线图
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object srzcZxt(HttpServletRequest request,
            HttpServletResponse response) {
        String type = req.getAjaxValue(request, "type");//1日2月份
        if ("".equals(type)) {
            type = "1";
        }
        Map<String, Object> returnMap = getReturnMap(1);
        if ("1".equals(type)) {//星期
            String day = DateHelper.getDay("yyyy-MM-dd", -30);
            //String[] lables = new String[30];
            JSONObject lables = new JSONObject();
            List<Map<String, Object>> srlist = db.queryForList(getSql("financial.receivable.rep.day.main") + getSql("financial.receivable.rep.day.day") + getSql("financial.receivable.rep.day.group"), new Object[]{day});
            List<Map<String, Object>> zclist = db.queryForList(getSql("financial.payable.rep.day.main") + getSql("financial.payable.rep.day.day") + getSql("financial.payable.rep.day.group"), new Object[]{day});
            JSONObject sr = new JSONObject();
            JSONObject zc = new JSONObject();
            //String[] sr = new String[30];
            //String[] zc = new String[30];
            for (int i = 0; i < 30; i++) {
                String day_ = DateHelper.getDay("yyyy-MM-dd", 0 - i);
                lables.put(i, day_);
                for (int j = 0; j < srlist.size(); j++) {
                    if (day_.equals(str.get(srlist.get(j), "DATE"))) {
                        //sr[i] = str.get(srlist.get(j),"cost");
                        sr.put(i, str.get(srlist.get(j), "cost"));
                    }
                }
                for (int j = 0; j < zclist.size(); j++) {
                    if (day_.equals(str.get(zclist.get(j), "DATE"))) {
                        //zc[i] = str.get(zclist.get(j),"cost");
                        zc.put(i, str.get(zclist.get(j), "cost"));
                    }
                }
            }
            returnMap.put("lable", lables);
            returnMap.put("sr", sr);
            returnMap.put("zc", zc);
        } else {
            String day = DateHelper.getToday("yyyy") + "-01-01";
            String[] lables = new String[12];
            for (int i = 0; i < 31; i++) {
                if (i < 9) {
                    lables[i] = DateHelper.getToday("yyyy") + "0" + (i + 1);
                } else {
                    lables[i] = DateHelper.getToday("yyyy") + (i + 1);
                }
            }
            returnMap.put("lable", JSONObject.fromObject(lables));
            returnMap.put("srlist", db.queryForList(getSql("financial.receivable.rep.month.main") + getSql("financial.receivable.rep.month.day") + getSql("financial.receivable.rep.month.group"), new Object[]{day}));
            returnMap.put("zclist", db.queryForList(getSql("financial.payable.rep.month.main") + getSql("financial.payable.rep.month.day") + getSql("financial.payable.rep.month.group"), new Object[]{day}));
        }
        returnMap.put("type", type);
        return returnMap;
    }

    /**
     * 改变状态信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object changePayableState(HttpServletRequest request,
            HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");
        String sql = getSql("financial.payable.changeState");
        return getReturnMap(db.update(sql, new Object[]{id}));
    }

    /**
     * 更改状态信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object changeReceivableState(HttpServletRequest request,
            HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");
        String sql = getSql("financial.receivable.changeState");
        return getReturnMap(db.update(sql, new Object[]{id}));
    }

    /**
     * 获取支付信息
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    public Object getReceivableList(HttpServletRequest request,
            HttpServletResponse response) {

        String category_type = req.getAjaxValue(request, "category_type");
        String secondary_type = req.getAjaxValue(request, "secondary_type");
        String name = req.getAjaxValue(request, "name");
        String plattime = req.getAjaxValue(request, "plattime");
        String status = req.getAjaxValue(request, "status");
        String isfq = req.getAjaxValue(request, "isfq");
        String ispayemp = req.getAjaxValue(request, "ispayemp");//是否导出可支付信息
        String plattime_state = "";
        String plattime_end = "";
        if (!"".equals(plattime)) {
            plattime_state = plattime.substring(0, plattime.indexOf("至"));
            plattime_end = plattime.substring(plattime.indexOf("至") + 1);
        }
        String paytime = req.getAjaxValue(request, "paytime");
        String paytime_state = "";
        String paytime_end = "";
        if (!"".equals(paytime)) {
            paytime_state = paytime.substring(0, paytime.indexOf("至"));
            paytime_end = paytime.substring(paytime.indexOf("至") + 1);
        }
        String sql = getSql("financial.receivable.main");
        //logger.debug("服务订单2"); 
        if ("1".equals(secondary_type) || "0".equals(secondary_type) || "2".equals(secondary_type)) {//出租合约
            sql = getSql("financial.receivable.mainRes");
        }
        if ("1".equals(ispayemp)) {//导出催租信息
            sql = getSql("financial.receivable.mainIsEmp");
        }
        List<String> params = new ArrayList<String>();
        if (!"".equals(category_type)) {
            sql += getSql("financial.receivable.category_type");
            params.add(category_type);
        }
        if (!"".equals(secondary_type)) {
            sql += getSql("financial.receivable.secondary_type");
            params.add(secondary_type);
        }
        if (!"".equals(name)) {
            if ("1".equals(secondary_type) || "2".equals(secondary_type) || "0".equals(secondary_type)) {//出租合约
                sql += getSql("financial.receivable.rename");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
            } else {
                sql += getSql("financial.receivable.name");
                params.add("%" + name + "%");
                params.add("%" + name + "%");
            }
            params.add("%" + name + "%");
            params.add("%" + name + "%");
        }
        if (!"".equals(plattime_state)) {
            sql += getSql("financial.receivable.plattime_state");
            params.add(plattime_state);
        }
        if (!"".equals(plattime_end)) {
            sql += getSql("financial.receivable.plattime_end");
            params.add(plattime_end);
        }
        if (!"".equals(paytime_state)) {
            sql += getSql("financial.receivable.paytime_state");
            params.add(paytime_state);
        }
        if (!"".equals(paytime_end)) {
            sql += getSql("financial.receivable.paytime_end");
            params.add(paytime_end);
        }
        if (!"".equals(status)) {
            sql += getSql("financial.receivable.status");
            params.add(status);
        }
        if ("1".equals(isfq)) {//可发起
            sql += getSql("financial.receivable.isfqtrue");
        } else if ("0".equals(isfq)) {//不可发起
            sql += getSql("financial.receivable.isfqfalse");
        }
        getPageList(request, response, sql, params.toArray(), "financial.receivable.orderby");
        return null;
    }

    /**
     * 发起支付审核
     *
     * @author 雷杨
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object sendPayTask(final HttpServletRequest request,
            HttpServletResponse response) {

        String ids = req.getAjaxValue(request, "ids");
        String flowremark = req.getAjaxValue(request, "flowremark");
        String time = req.getAjaxValue(request, "time");

        String target_type = req.getAjaxValue(request, "target_type");
        String target_user = req.getAjaxValue(request, "target_user");
        String target_bank = req.getAjaxValue(request, "target_bank");
        String target_account = req.getAjaxValue(request, "target_account");
        String target_Serial = req.getAjaxValue(request, "target_Serial");
        setOperContent("发起收入审批操作");
        //选择后直接插入对应信息到表中

        //logger.debug("ids:"+ids);
        //更改对应的状态值
        String sql = "update financial_payable_tab a set a.target_type='" + target_type + "',a.target_user='" + target_user + "',a.target_bank='" + target_bank + "',a.target_account='" + target_account + "',a.target_Serial='" + target_Serial + "',a.status = 3,a.flowremark = '" + flowremark + "',a.time = '" + time + "' where a.id in(" + ids + ")";

        if (db.update(sql) == 0) {
            return getReturnMap(-1, "操作失败");
        } else {
            //测试环境之间修改为已支付状态
            if (Constants.is_test) {
                db.update("update financial_payable_tab a set a.status = 1 where a.id in(" + ids + ")");
            }
            //加入到同步金蝶信息中
            final String[] ids_ = ids.split(",");
            for (int i = 0; i < ids_.length; i++) {
                JdTask.addPool("7", ids_[i]);
            }
            //生成对应的id  commonPayService
            Object obj = db.doInTransaction(new Batch() {
                @Override
                public Object execute() throws Exception {
                    for (int i = 0; i < ids_.length; i++) {
                        String sql = "SELECT b.code FROM financial_payable_tab a ,work_order b WHERE a.secondary = b.id AND a.id = " + ids_[i];
                        commonPayService.releaseHouse(this, request, new RankHouse(), db.queryForString(sql));
                    }
                    return 1;
                }
            });
        }

        return getReturnMap(1, "操作成功");
    }

    @Transactional
    public Object sendRecTask(final HttpServletRequest request,
            HttpServletResponse response) {
        //先插入到表中进行处理
        String ids = req.getAjaxValue(request, "ids");
        String flowremark = req.getAjaxValue(request, "flowremark");
        String time = req.getAjaxValue(request, "time");
        String target_type = req.getAjaxValue(request, "target_type");
        String target_user = req.getAjaxValue(request, "target_user");
        String target_bank = req.getAjaxValue(request, "target_bank");
        String target_account = req.getAjaxValue(request, "target_account");
        String target_Serial = req.getAjaxValue(request, "target_Serial");

        setOperContent("发起支付审批操作");
        //选择后直接插入对应信息到表中

        //更改对应的状态值
        String sql = "update financial_receivable_tab a set a.target_type='" + target_type + "',a.target_user='" + target_user + "',a.target_bank='" + target_bank + "',a.target_account='" + target_account + "',a.target_Serial='" + target_Serial + "',a.status = 3,a.operator = '" + (this.getUser(request).getId()) + "' ,a.pay_time = now(),a.flowremark = '" + flowremark + "',a.time = '" + time + "' where a.id in(" + ids + ")";

        if (db.update(sql) == 0) {
            db.rollback();
            return getReturnMap(-1, "操作失败");
        }

        sql = "UPDATE yc_houserank_tab a SET a.rank_status = 5 "
                + "  WHERE EXISTS(SELECT 1 from financial_receivable_tab b ,yc_agreement_tab c "
                + "               WHERE b.secondary = c.id "
                + "                AND c.house_id = a.id "
                + "               AND b.id in (" + ids + ") "
                + "              AND b.status = 1)";
        if (db.update(sql) == 0) {
            db.rollback();
            return getReturnMap(-1, "操作失败");
        } else {

            if (Constants.is_test) {
                db.update("update financial_receivable_tab a set a.status = 1 where a.id in(" + ids + ")");
            }

            //加入到同步金蝶信息中
            final String[] ids_ = ids.split(",");
            logger.debug("开始进行更新账单信息-------------------------");
            for (int i = 0; i < ids_.length; i++) {
                JdTask.addPool("6", ids_[i]);
                try {

                    if (!updateBill(time, ids_[i], "1", target_Serial, "1".equals(target_type) ? "6" : "2".equals(target_type) ? "2" : "3".equals(target_type) ? "5" : "4".equals(target_type) ? "4" : "5".equals(target_type) ? "6" : "6".equals(target_type) ? "7" : "0".equals(target_type) ? "1" : "6")) {
                        db.rollback();
                        logger.debug("错误信息--------");
                        return getReturnMap(-1, "操作失败");
                    };
                } catch (Exception ex) {
                    logger.error("更新账单出现错误：", ex);
                    db.rollback();
                    return getReturnMap(-1, "操作失败");
                }
            }
            logger.debug("开始进行更新账单信息---------end----------------");

            //logger.debug("开始更新数据信息");
            //生成对应的id  commonPayService
            Object obj = db.doInTransaction(new Batch() {
                @Override
                public Object execute() throws Exception {
                    for (int i = 0; i < ids_.length; i++) {
                        try {
                            commonPayService.updateRankAgreementStatus(ids_[i], this);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String sql = "SELECT b.code FROM financial_receivable_tab a ,work_order b WHERE a.secondary = b.id AND a.id =  " + ids_[i];
                        try {
                            commonPayService.releaseHouse(this, request, new RankHouse(), db.queryForString(sql));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return 1;
                }
            });
        }

        return getReturnMap(1, "操作成功");
    }

    /**
     * 更新账单信息
     *
     * @param fin_id
     * @param tro_no
     * @param fin_type 1 收入 2 支出
     * @param type 1支付宝公众号2微信公众号3支付宝网页支付4支付宝app支付5微信app支付6银行转账7支付宝转账
     * @return
     */
    public boolean updateBill(String time, String fin_id, String fin_type, String tro_no, String type) throws Exception {

        String sql = "";
        //明细信息加入到支付信息中 
        if ("1".equals(fin_type)) {
            sql = "select * from financial_receivable_tab a where a.id = ?";
            Map<String, Object> map = db.queryForMap(sql, new Object[]{fin_id});
            sql = "select sum(a.cost) from yc_coupon_user_detail a where a.financial_id = ?";
            String dis_cost = db.queryForString(sql, new Object[]{fin_id});
            dis_cost = (dis_cost == null || "".equals(dis_cost)) ? "0" : dis_cost;
            sql = "update financial_receivable_tab a set a.yet_cost = a.cost where a.id = ?";
            if (db.update(sql, new Object[]{fin_id}) != 1) {
                return false;
            }

            sql = "insert into financial_yet_tab(yet_cost,type,oper_date,discount_cost,fin_id,target_type,target_Serial,target_account,target_bank,remark,pay_time)values(?,?,now(),?,?,?,?,?,?,?,?)";
            logger.debug("插入已支付明细：" + str.getSql(sql, new Object[]{StringHelper.get(map, "cost"), type, dis_cost, fin_id,
                "1".equals(type) ? "支付宝公众号" : "2".equals(type) ? "微信公众号" : "3".equals(type) ? "支付宝网页支付" : "4".equals(type) ? "支付宝app支付" : "5".equals(type) ? "微信app支付" : "6".equals(type) ? "银行转账" : "支付宝转账",
                tro_no, "", ("1".equals(type) || "3".equals(type)) || "4".equals(type) || "7".equals(type) ? "支付宝" : ("2".equals(type) || "5".equals(type) ? "微信" : ""), "", time}));
            if (db.update(sql, new Object[]{StringHelper.get(map, "cost"), type, dis_cost, fin_id,
                "1".equals(type) ? "支付宝公众号" : "2".equals(type) ? "微信公众号" : "3".equals(type) ? "支付宝网页支付" : "4".equals(type) ? "支付宝app支付" : "5".equals(type) ? "微信app支付" : "6".equals(type) ? "银行转账" : "支付宝转账",
                tro_no, "", ("1".equals(type) || "3".equals(type)) || "4".equals(type) || "7".equals(type) ? "支付宝" : ("2".equals(type) || "5".equals(type) ? "微信" : ""), "", time}) != 1) {
                return false;
            }

            //检查当前明细对应的账单ID多少
            sql = "select a.bill_id from financial_bill_detail_tab a where a.receivable_id = ? ";
            String bill_id = db.queryForString(sql, new Object[]{fin_id});

            sql = "select b.ager_id,c.user_id,d.mobile,d.username \n"
                    + "  from financial_receivable_tab a,financial_settlements_tab b,yc_agreement_tab c,yc_userinfo_tab d\n"
                    + " where a.correlation = b.correlation_id\n"
                    + "   and b.ager_id = c.id \n"
                    + "   and c.user_id = d.id\n"
                    + "	 and a.id = ?";
            Map<String, Object> agerMap = db.queryForMap(sql, new Object[]{fin_id});

            //手动生成一条支付明细信息
            sql = "INSERT INTO financial_bill_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost,is_split)values(?,?,?,?,?,?,?,?,?,3,?,now(),now(),?,?,?,?,1)";
            if (db.update(sql, new Object[]{UUID.randomUUID().toString(), type, time, "", ("1".equals(type) || "3".equals(type)) || "4".equals(type) || "7".equals(type) ? "支付宝" : ("2".equals(type) || "5".equals(type) ? "微信" : ""), "", tro_no, "老app支付明细自动生成账单支付项",
                AmountUtil.subtract(Double.valueOf(StringHelper.get(map, "cost")), Double.valueOf(dis_cost), 2), StringHelper.get(agerMap, "username"), StringHelper.get(agerMap, "mobile"), StringHelper.get(agerMap, "ager_id"), bill_id, dis_cost}) != 1) {
                return false;
            }

            //更新对应的支付金额
            sql = "update financial_bill_tab a set a.yet_cost = a.yet_cost + ?,a.last_remark='完成一笔支付明细',a.last_date = now(),\n"
                    + " discounts_cost = a.discounts_cost +?\n"
                    + " where a.id = ?";
            if (db.update(sql, new Object[]{AmountUtil.subtract(Double.valueOf(StringHelper.get(map, "cost")), Double.valueOf(dis_cost), 2),
                dis_cost, bill_id}) != 1) {
                return false;
            }

            sql = "update financial_bill_tab a set a.complete_date = now(),a.state = 3 where a.cost = a.yet_cost + a.discounts_cost and a.id = ?";
            if (db.update(sql, new Object[]{bill_id}) != 1) {
                return false;
            }

            //检查是否支付完成，如果支付完成需要运行以下内容
            sql = "select a.state from financial_bill_tab a where a.id = ?";
            int state = db.queryForInt(sql, new Object[]{bill_id});
            if (state == 3) {
                //如果当前明细支付完成，需要将所有负值全部设置为已支付
                sql = "insert into financial_yet_tab (fin_id,yet_cost,type,oper_date)\n"
                        + " select a.id,a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0) cost,\n"
                        + " 1,now()\n"
                        + "   from financial_receivable_tab a,financial_bill_detail_tab c\n"
                        + "  where a.id = c.receivable_id\n"
                        + "	  and a.`status` = 0\n"
                        + "		and ABS(a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0)) > 0\n"
                        + "		and c.bill_id = ? ";
                if (db.update(sql, new Object[]{bill_id}) != 1) {
                    return false;
                }
                sql = "update financial_receivable_tab a set a.`status` = 1,a.yet_cost=ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0) where exists(select 1 from financial_bill_detail_tab b where b.receivable_id = a.id and b.bill_id = ?)";
                if (db.update(sql, new Object[]{bill_id}) != 1) {
                    return false;
                }
                //更新支出也为成功
                sql = "insert into financial_yet_tab (fin_id,yet_cost,type,oper_date)\n"
                        + " select a.id,a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0) cost,\n"
                        + " 2,now()\n"
                        + "   from financial_payable_tab a,financial_bill_detail_tab c\n"
                        + "  where a.id = c.payable_id\n"
                        + "	  and a.`status` = 0\n"
                        + "		and ABS(a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0)) > 0\n"
                        + "		and c.bill_id = ? ";
                if (db.update(sql, new Object[]{bill_id}) != 1) {
                    return false;
                }
                sql = "update financial_payable_tab a set a.`status` = 1,a.yet_cost=ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0) where exists(select 1 from financial_bill_detail_tab b where b.payable_id = a.id and b.bill_id = ?)";
                if (db.update(sql, new Object[]{bill_id}) != 1) {
                    return false;
                }
                return true;
            } else {
                return true;
            }

        } else {

        }

        return false;
    }

    /**
     * 选择合约信息
     *
     * @param request
     * @param response
     * @return
     */
    public Object getAgrList(HttpServletRequest request,
            HttpServletResponse response) {
        String type = req.getAjaxValue(request, "type");
        String name = req.getAjaxValue(request, "name");
        String sql = getSql("basehouse.agreement.getAgr");
        Map<String, Object> map = new HashMap();
        map.put("items", JSONArray.fromObject(db.queryForList(sql, new Object[]{type, "%" + name + "%", "%" + name + "%", "%" + name + "%", "%" + name + "%"})));
        return map;
    }

    /**
     * 获取财务项目名称
     *
     * @param request
     * @param response
     * @return
     */
    public Object getCorrelation(HttpServletRequest request,
            HttpServletResponse response) {
        String name = req.getAjaxValue(request, "name");
        String type = req.getAjaxValue(request, "type");
        String sql = getSql("financial.receivable.getCorrelation");
        Map<String, Object> map = new HashMap();
        map.put("items", JSONArray.fromObject(db.queryForList(sql, new Object[]{"%" + name + "%", type})));
        return map;
    }

    /**
     * 收入保存
     *
     * @param request
     * @param response
     * @return
     */
    public Object incomeSave(HttpServletRequest request,
            HttpServletResponse response) {
        String name = req.getAjaxValue(request, "name");
        String type = req.getAjaxValue(request, "type");
        String category = req.getAjaxValue(request, "category");
        String correlation = req.getAjaxValue(request, "correlation");
        String secondary = req.getAjaxValue(request, "secondary");
        String cost = req.getAjaxValue(request, "cost");
        String plat_time = req.getAjaxValue(request, "plat_time");
        String start_time = req.getAjaxValue(request, "start_time");
        String end_time = req.getAjaxValue(request, "end_time");
        String remark = req.getAjaxValue(request, "remark");
        String sql = getSql("financial.receivable.insertNew");
        User u = getUser(request);
        if (u.getId() == null) {
            return getReturnMap(-2);
        }
        return getReturnMap(db.update(sql, new Object[]{name, correlation, type, secondary, category, cost, plat_time, "", "", null, u.getId(), remark, "", start_time, end_time}));
    }

    /**
     * 支出保存
     *
     * @param request
     * @param response
     * @return
     */
    public Object expendSave(HttpServletRequest request,
            HttpServletResponse response) {
        String name = req.getAjaxValue(request, "name");
        String type = req.getAjaxValue(request, "type");
        String category = req.getAjaxValue(request, "category");
        String correlation = req.getAjaxValue(request, "correlation");
        String secondary = req.getAjaxValue(request, "secondary");
        String cost = req.getAjaxValue(request, "cost");
        String plat_time = req.getAjaxValue(request, "plat_time");
        String start_time = req.getAjaxValue(request, "start_time");
        String end_time = req.getAjaxValue(request, "end_time");
        String remark = req.getAjaxValue(request, "remark");
        String sql = getSql("financial.payable.insertNew");
        User u = getUser(request);
        if (u.getId() == null) {
            return getReturnMap(-2);
        }
        return getReturnMap(db.update(sql, new Object[]{name, correlation, type, secondary, category, cost, plat_time, "", "", null, u.getId(), remark, "", start_time, end_time}));
    }

    /**
     * 明细保存
     *
     * @param request
     * @param response
     * @return
     */
    @Transactional
    public Object mxSave(HttpServletRequest request,
            HttpServletResponse response) {
        String[] name = req.getAjaxValues(request, "name");
        String[] type = req.getAjaxValues(request, "type");
        String[] category = req.getAjaxValues(request, "category");
        String[] correlation = req.getAjaxValues(request, "correlation");
        String[] secondary = req.getAjaxValues(request, "secondary");
        String[] cost = req.getAjaxValues(request, "cost");
        String[] plat_time = req.getAjaxValues(request, "plat_time");
        String[] start_time = req.getAjaxValues(request, "start_time");
        String[] end_time = req.getAjaxValues(request, "end_time");
        String[] remark = req.getAjaxValues(request, "remark");
        String[] stype = req.getAjaxValues(request, "stype");

        String[] target_type = req.getAjaxValues(request, "target_type");
        String[] target_bank = req.getAjaxValues(request, "target_bank");
        String[] target_user = req.getAjaxValues(request, "target_user");
        String[] settle_num = req.getAjaxValues(request, "settle_num");

        User u = getUser(request);
        if (u.getId() == null) {
            return getReturnMap(-2);
        }

        String fin_re_ids = "";
        String fin_pay_ids = "";
        Double costs = 0D;
        for (int i = 0; i < name.length; i++) {
            if ("1".equals(stype[i])) {//支出
                if (!"5".equals(category[i])) {
                    target_type[i] = null;
                }
                String sql = getSql("financial.payable.insertNew");
                int exc = db.insert(sql, new Object[]{name[i], correlation[i], type[i], secondary[i], category[i], cost[i], plat_time[i], target_bank[i], target_user[i], target_type[i], u.getId(), remark[i], "", start_time[i], end_time[i], settle_num[i]});
                if (exc <= 0) {
                    db.rollback();
                    return getReturnMap(0);
                }
                fin_pay_ids += +exc + ",";
                costs = AmountUtil.add(costs, 0 - Double.valueOf(cost[i]), 2);
            } else {//收入
                String sql = getSql("financial.receivable.insertNew");
                target_type[i] = null;
                int exc = db.insert(sql, new Object[]{name[i], correlation[i], type[i], secondary[i], category[i], cost[i], plat_time[i], target_bank[i], target_user[i], target_type[i], u.getId(), remark[i], "", start_time[i], end_time[i], settle_num[i]});
                if (exc <= 0) {
                    db.rollback();
                    return getReturnMap(0);
                }
                fin_re_ids += +exc + ",";
                costs = AmountUtil.add(costs, Double.valueOf(cost[i]), 2);
            }
        }
        int types = 1;
        if (costs < 0) {
            types = 2;
        }

        //生成退租账单
        int bill_id = db.insert("INSERT INTO financial_bill_tab (cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                + "  values(?,?,?,now(),now(),'其他账单',?,?,6,now(),?)", new Object[]{costs, "其他账单", types, secondary[0], secondary[0], "其他账单信息"});
        if (bill_id == -1) {
            db.rollback();
            return getReturnMap(0);
        }

        String[] fin_re_id = fin_re_ids.split(",");
        for (int h = 0; h < fin_re_id.length; h++) {
            if (!"".equals(fin_re_id[h])) {
                int exc = db.update("INSERT INTO financial_bill_detail_tab (bill_id,receivable_id)values(?,?)", new Object[]{bill_id, fin_re_id[h]});
                if (exc == 0) {
                    db.rollback();
                    return getReturnMap(0);
                }
            }
        }

        String[] fin_pay_id = fin_pay_ids.split(",");
        for (int h = 0; h < fin_pay_id.length; h++) {
            if (!"".equals(fin_pay_id[h])) {
                int exc = db.update("INSERT INTO financial_bill_detail_tab (bill_id,payable_id)values(?,?)", new Object[]{bill_id, fin_pay_id[h]});
                if (exc == 0) {
                    db.rollback();
                    return getReturnMap(0);
                }
            }
        }
        System.out.println("pccom.web.server.financial.FinancialService.mxSave():" + types);
        if (types == 1) {//发生推送信息  1租金账单2水电燃账单3退租账单4增值保洁账单5增值维修账单6其他账单
            sendNoti(bill_id + "");
        }

        return getReturnMap(1);
    }

    /**
     * 发送通知
     *
     * @param obj
     * @param bill_id
     */
    public void sendNoti(String bill_id) {
        Map<String, Object> userMap = db.queryForMap("select b.user_id,a.cost,DATE_FORMAT(a.plan_time,'%Y-%m-%d') plan_time,(case when a.resource_type = 1 then '租金账单' when a.resource_type = 2 then '水电燃账单' "
                + "when a.resource_type = 3 then '退租账单' when a.resource_type = 4 then '增值保洁账单' when a.resource_type = 5 then '增值维修账单' "
                + "when a.resource_type = 6 then '其他账单' when a.resource_type = 7 then '押金账单' else '其他账单' end )resource_type_name \n"
                + "from financial_bill_tab a,yc_agreement_tab b ,yc_userinfo_tab c\n"
                + "where a.ager_id = b.id \n"
                + "  and b.user_id = c.id \n"
                + "  and a.id = ?\n"
                + "	limit 0,1", new Object[]{bill_id});
        logger.debug("数据：" + StringHelper.getSql("select b.user_id,a.cost,DATE_FORMAT(a.plan_time,'%Y-%m-%d') plan_time,(case when a.resource_type = 1 then '租金账单' when a.resource_type = 2 then '水电燃账单' "
                + "when a.resource_type = 3 then '退租账单' when a.resource_type = 4 then '增值保洁账单' when a.resource_type = 5 then '增值维修账单' "
                + "when a.resource_type = 6 then '其他账单' when a.resource_type = 7 then '押金账单' else '其他账单' end )resource_type_name \n"
                + "from financial_bill_tab a,yc_agreement_tab b ,yc_userinfo_tab c\n"
                + "where a.ager_id = b.id \n"
                + "  and b.user_id = c.id \n"
                + "  and a.id = ?\n"
                + "	limit 0,1", new Object[]{bill_id}));
        String user_id = StringHelper.get(userMap, "user_id");
        String wxuser = StringHelper.get(userMap, "wxuser");
        String cost_ = StringHelper.get(userMap, "cost");
        String plan_time = StringHelper.get(userMap, "plan_time");
        String resource_type_name = StringHelper.get(userMap, "resource_type_name");
        Map<String, String> param = new HashMap<String, String>();
        param.put("messag_type", "2");
        AppBillBean appBillBean = new AppBillBean();
        appBillBean.setKey(Constants.YCQWJ_API);
        appBillBean.setId(user_id);
        appBillBean.setContent("您的" + resource_type_name + "已生成，请注意查收");
        appBillBean.setExreas_param(com.alibaba.fastjson.JSONObject.toJSONString(param));
        try {
            logger.debug("是否发生成功：" + PlushRequest.appBill(appBillBean));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            WeiXinRequest.sendBillSuc("https://m.room1000.com/client/new/index.html?open=client.new.server.payFees.payFees&uid=" + user_id,
                    user_id, Constants.YCQWJ_API, "您的账单已出，请提交缴费信息",
                    resource_type_name, cost_, plan_time, "感谢您的使用，如有疑问，请联系客服。");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取清单列表
     *
     * @param request
     * @param response
     * @return
     */
    public Object getQDList(HttpServletRequest request,
            HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");
        String type = req.getAjaxValue(request, "type");

        Map<String, Object> returnMap = getReturnMap(1);
        if ("1".equals(type)) {//支出
            returnMap.put("data", db.queryForList(getSql("financial.project.getPayQdList1"), new Object[]{id}));
        } else {//收入
            returnMap.put("data", db.queryForList(getSql("financial.project.getResQdList1"), new Object[]{id}));
        }
        return returnMap;
    }

    /**
     * 获取对应的卡券信息
     *
     * @param request
     * @param response
     * @return
     */
    public Object selectCoupon(HttpServletRequest request,
            HttpServletResponse response) {
        String fin_id = req.getAjaxValue(request, "fin_id");//明细id
        String sql = "SELECT a.*,format(a.cost,2) cost_ FROM yc_coupon_user_detail a WHERE a.financial_id = ?";
        return db.queryForList(sql, new Object[]{fin_id});
    }

    public void getBillList(HttpServletRequest request, HttpServletResponse response) {
        String type = req.getAjaxValue(request, "type");
        String resource_type = req.getAjaxValue(request, "resource_type");
        if (resource_type.equals("null")) {
            resource_type = "";
        }
        String state = req.getAjaxValue(request, "state");
        String ager_name = req.getAjaxValue(request, "ager_name");
        String ager_code = req.getAjaxValue(request, "ager_code");
        String username = req.getAjaxValue(request, "username");
        String set_code = req.getAjaxValue(request, "set_code");
        String mobile = req.getAjaxValue(request, "mobile");//是否导出可支付信息
        String plattime = req.getAjaxValue(request, "plattime");
        String sftz = req.getAjaxValue(request, "sftz");
        String plattime_state = "";
        String plattime_end = "";
        if (!"".equals(plattime)) {
            plattime_state = plattime.substring(0, plattime.indexOf("~"));
            plattime_end = plattime.substring(plattime.indexOf("~") + 1);
        }

        //modify  by weijunshi 
        //获取账单的SQL需要用左连接，避免单字段异常导致整个记录无法查询，同时对网格管家需要通过团队中获取，而不是网格
        /*String sql = "select a.*,b.name ager_name,b.code,b.user_mobile,aa.name set_name,aa.code set_code,ab.username,ab.mobile usermobile,g.name wgname,ac.g_name,a.remark, " +
                    "      ae.area_name,fn_checkTaskDo(b.id) sftz,DATE_FORMAT(a.last_date,'%Y-%m-%d %T') last_date_,DATE_FORMAT(a.oper_date,'%Y-%m-%d %T') oper_date_,DATE_FORMAT(a.plan_time,'%Y-%m-%d %T') plan_time_,DATE_FORMAT(a.complete_date,'%Y-%m-%d %T') complete_date_,"
                + " b.begin_time,b.end_time"+
                    "  FROM financial_bill_tab a, " +
                    "       yc_agreement_tab b, " +
                    "       yc_houserank_tab c, " +
                    "       yc_house_tab e, " +
                    "       yc_group_tab f, " +
                    "       t_grid ac," +
                    "       yc_area_tab ae,"+
                    "       yc_account_tab g, " +
                    "       financial_settlements_tab aa, " +
                    "       yc_userinfo_tab ab " +
                    " where a.ager_id = b.id  " +
                    "   AND a.ager_id = aa.ager_id " +
                    "   AND ae.id = f.areaid "+
                    "   AND ac.g_id = g.g_id " +
                    "   AND ab.id = b.user_id " +
                    "   AND b.house_id = c.id  " +
                    "   AND c.house_id = e.id " +
                    "   AND e.group_id = f.id " +
                    "   AND f.g_id = g.g_id";*/
        String sql = getSql("financial.bill.getBillPageList");
        //End by weijunshi 获取账单的SQL需要用左连接，避免单字段异常导致整个记录无法查询，同时对网格管家需要通过团队中获取，而不是网格
        //logger.debug("服务订单2"); 
        List<String> params = new ArrayList<String>();
        logger.debug("resource_type:" + resource_type);
        if ("1".equals(resource_type) || "2".equals(resource_type) || "6".equals(resource_type) || "7".equals(resource_type)) {//导出催租信息
            sql += " and a.resource_type = ?";
            params.add(resource_type);
        } else if ("".equals(resource_type) || "3".equals(resource_type) || "4".equals(resource_type) || "5".equals(resource_type)) {//退租订单
            //modify  by weijunshi 
            //获取账单的SQL需要用左连接，避免单字段异常导致整个记录无法查询，同时对网格管家需要通过团队中获取，而不是网格
            /*sql = "select a.*,b.name ager_name,cc.code order_code,b.code,b.user_mobile,aa.name set_name,aa.code set_code,ab.username,ab.mobile usermobile,g.name wgname,ac.g_name,a.remark, " +
                    "      ae.area_name,fn_checkTaskDo(b.id) sftz,DATE_FORMAT(a.last_date,'%Y-%m-%d %T') last_date_,DATE_FORMAT(a.oper_date,'%Y-%m-%d %T') oper_date_,DATE_FORMAT(a.plan_time,'%Y-%m-%d %T') plan_time_,DATE_FORMAT(a.complete_date,'%Y-%m-%d %T') complete_date_,"
                + " b.begin_time,b.end_time"+
                    "  FROM financial_bill_tab a, " +
                    "       yc_agreement_tab b, " +
                    "       yc_houserank_tab c, " +
                    "       yc_house_tab e, " +
                    "       yc_group_tab f, " +
                    "       t_grid ac," +
                    "       yc_area_tab ae,"+
                    "       yc_account_tab g, " +
                    "       financial_settlements_tab aa, " +
                    "       yc_userinfo_tab ab,work_order cc " +
                    " where a.ager_id = b.id  " +
                    "   AND a.ager_id = aa.ager_id " +
                    "   and a.resource_id = cc.id"+
                    "   AND ae.id = f.areaid "+
                    "   AND ac.g_id = g.g_id " +
                    "   AND ab.id = b.user_id " +
                    "   AND b.house_id = c.id  " +
                    "   AND c.house_id = e.id " +
                    "   AND e.group_id = f.id " +
                    "   AND f.g_id = g.g_id";*/
            sql = getSql("financial.bill.getBillPageListForOnDel");
            //End by weijunshi 获取账单的SQL需要用左连接，避免单字段异常导致整个记录无法查询，同时对网格管家需要通过团队中获取，而不是网格

            if (!resource_type.equals("")) {
                sql += " and a.resource_type = ?";
                params.add(resource_type);
            }
        }
        if (!"".equals(type)) {
            sql += " and a.type = ?";
            params.add(type);
        }
        if (!"".equals(state)) {
            sql += " and a.state = ?";
            params.add(state);
        }

        if (!"".equals(ager_name)) {
            sql += " and b.name like ?";
            params.add("%" + ager_name + "%");
        }
        if (!"".equals(ager_code)) {
            sql += " and b.code like ?";
            params.add("%" + ager_code + "%");
        }
        if (!"".equals(username)) {
            sql += " and ab.username like ?";
            params.add("%" + username + "%");
        }
        if (!"".equals(set_code)) {
            sql += " and aa.code like ?";
            params.add("%" + set_code + "%");
        }
        if (!"".equals(mobile)) {
            sql += " and ab.mobile like ?";
            params.add("%" + mobile + "%");
        }
        if (!"".equals(plattime_state)) {
            sql += " and a.plan_time >= ?";
            params.add(plattime_state);
        }
        if (!"".equals(plattime_end)) {
            sql += " and a.plan_time <= ?";
            params.add(plattime_end);
        }
        if (!"".equals(sftz)) {
            sql += " and fn_checkTaskDo(b.id) = ?";
            params.add(sftz);
        }
        getPageList(request, response, sql, params.toArray(), "order by a.id,a.plan_time");
    }

    public Map<String, Object> getBillLDetailList(HttpServletRequest request, HttpServletResponse response) {
        String id = req.getAjaxValue(request, "id");

        Map<String, Object> returnMap = new HashMap<String, Object>();

        String sql = "select b.name,b.cost,b.remarks,c.name cat_name,b.status\n"
                + "  FROM financial_bill_detail_tab a,financial_receivable_tab b,financial_category_tab c\n"
                + " where a.receivable_id = b.id\n"
                + "   AND b.category = c.id\n"
                + "   AND a.bill_id = ?";
        List<Map<String, Object>> detailList = db.queryForList(sql, new Object[]{id});
        returnMap.put("detailList", detailList);

        sql = "select a.*,DATE_FORMAT(a.callback_time,'%Y-%m-%d %T') callback_time_,DATE_FORMAT(a.create_date,'%Y-%m-%d %T') create_date_,DATE_FORMAT(a.pay_time,'%Y-%m-%d %T') pay_time_,DATE_FORMAT(a.start_time,'%Y-%m-%d %T') start_time_,"
                + "ifnull(d.name, '') oper_name,ifnull(c.username,'') user_name\n"
                + "  FROM financial_bill_pay_tab a"
                + " left join yc_agreement_tab b on a.ager_id=b.id"
                + " left join yc_userinfo_tab c on b.user_id=c.id"
                + " left join yc_account_tab d on a.oper_id=d.id\n"
                + " where a.bill_id = ?";
        List<Map<String, Object>> detailPayList = db.queryForList(sql, new Object[]{id});
        returnMap.put("detailPayList", detailPayList);

        return returnMap;

    }

    /**
     * saveBill
     *
     * @param request
     * @param response
     * @return
     */
    @Transactional
    public Object saveBill(HttpServletRequest request, HttpServletResponse response) {
        User user = getUser(request);
        String userId = user.getId();
        String id = req.getAjaxValue(request, "id");
        String pay_time = req.getAjaxValue(request, "pay_time");
        String type = req.getAjaxValue(request, "type");
        String name = req.getAjaxValue(request, "name");
        String bank_name = req.getAjaxValue(request, "bank_name");
        String bank_account = req.getAjaxValue(request, "bank_account");
        String tro_no = req.getAjaxValue(request, "tro_no");
        String remark = req.getAjaxValue(request, "remark");
        String cost = req.getAjaxValue(request, "cost");
        String dis_cost = req.getAjaxValue(request, "dis_cost");

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if ("".equals(id)) {
            return this.getReturnMap(-1);
        }

        //锁表操作
        String sql = "update financial_bill_tab a set a.id = a.id where a.id = ?";
        if (db.update(sql, new Object[]{id}) != 1) {
            return this.getReturnMap(-1);
        }

        sql = "select a.state,a.cost,a.yet_cost,a.discounts_cost,b.id,c.username,c.mobile\n"
                + "    FROM financial_bill_tab a ,yc_agreement_tab b,yc_userinfo_tab c\n"
                + "   where a.ager_id = b.id\n"
                + "     AND b.user_id = c.id\n"
                + "     AND a.id = ?";
        Map<String, Object> map = db.queryForMap(sql, new Object[]{id});
        String cost_z = StringHelper.get(map, "cost");
        String state = StringHelper.get(map, "state");
        String yet_cost = StringHelper.get(map, "yet_cost");
        String discounts_cost = StringHelper.get(map, "discounts_cost");//优惠金额
        String ager_id = StringHelper.get(map, "id");
        String username = StringHelper.get(map, "username");
        String mobile = StringHelper.get(map, "mobile");

        if (!"1".equals(state)) {
            return this.getReturnMap(-4);
        }

        //可支付金额
        Double dou_cost = AmountUtil.subtract(AmountUtil.subtract(Double.valueOf(cost_z), Double.valueOf(yet_cost), 2), Double.valueOf(discounts_cost), 2);
        //填写的金额大于可支付金额
        if (dou_cost < Double.valueOf(cost)) {
            return this.getReturnMap(-2);
        }

        //计算优惠后的金额是否大于可支付金额
        Double dis_cost_ = AmountUtil.subtract(dou_cost, Double.valueOf(dis_cost), 2);
        if (dis_cost_ < Double.valueOf(cost)) {
            return this.getReturnMap(-3);
        }

        sql = "INSERT INTO financial_bill_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)values(?,?,DATE_FORMAT(?,'%Y-%m-%d %T'),?,?,?,?,?,?,3,?,now(),now(),?,?,?,?)";
        if (db.update(sql, new Object[]{UUID.randomUUID().toString(), type, pay_time, name, bank_name, bank_account, tro_no, remark, cost, this.getUser(request).getId(), mobile, ager_id, id, dis_cost}) != 1) {
            return this.getReturnMap(0);
        }

        Double yetCost = Double.valueOf(cost);

        sql = "UPDATE financial_bill_tab a set a.yet_cost = a.yet_cost +?,a.discounts_cost = a.discounts_cost +?,a.last_date = now(),a.complete_date = now(),a.state = (case when a.yet_cost+a.discounts_cost = a.cost then 3 else 1 end),a.last_remark = '后台添加交易明细' where a.id = ?";
        if (db.update(sql, new Object[]{yetCost, dis_cost, id}) != 1) {
            return this.getReturnMap(0);
        }

        //更新财务明细信息
        if (updateAger(id, ager_id, userId)) {
            return this.getReturnMap(1);
        } else {
            return this.getReturnMap(0);
        }

    }

    public boolean updateAger(final String bill_id, String ager_id, String userId) {
        //检查当前已支付的总金额
        String sql = "select a.id,a.state,a.resource_type,a.ager_id,b.status ager_state,a.resource_id,a.type,b.user_mobile from financial_bill_tab a,yc_agreement_tab b "
                + "where a.ager_id = b.id and a.id = ? ";
        logger.debug("检查当前已支付的总金额:" + StringHelper.getSql(sql, new Object[]{bill_id}));
        Map<String, Object> map = db.queryForMap(sql, new Object[]{bill_id});

        if ("3".equals(StringHelper.get(map, "state"))) {//支付完成
            if ("12".equals(StringHelper.get(map, "ager_state"))) {
                if ("7".equals(StringHelper.get(map, "resource_type"))) {//支付的为押金账单

                  // 全民经纪人修改推荐关系
                  String mobile = String.valueOf(StringHelper.get(map, "user_mobile"));
                  if (!mobile.equals("") && !mobile.equals("null")) {
                    sql = "select a.id,a.open_id from yc_recommend_info a where a.state=1 and a.mobile=?";
                    Map<String, String> recommend = db.queryForMap(sql, new Object[]{mobile});
                    if (!recommend.isEmpty()) {
                      sql = "update yc_recommend_info set state=2,agreement_id=? where id=?";
                      if (db.update(sql, new Object[]{ager_id, String.valueOf(recommend.get("id"))}) == 1) {
                        // 查询推荐人信息
                        String open_id = String.valueOf(recommend.get("open_id"));
                        sql = "select a.mobile from yc_userinfo_tab a where a.is_delete=1 and a.id=?";
                        Map<String, String> userMap = db.queryForMap(sql, new Object[]{open_id});
                        // 更改经纪人推荐关联关系表
                        sql = "update yc_recommend_relation set state=2 where recommend_id=?";
                        db.update(sql, new Object[]{String.valueOf(recommend.get("id"))});
                        Map<String, String> msgMap = new HashMap<String, String>();
                        msgMap.put("user_name", mobile);
                        SmsSendMessage.smsSendMessage(userMap.get("mobile"), msgMap, SmsSendContants.SIGN_SUCCESS);
                      }
                    }
                  }
                    sql = "update yc_agreement_tab a set a.`status` = 2 where a.id = ?";
                    if (db.update(sql, new Object[]{ager_id}) == 1) {
                        sql = "update yc_houserank_tab a set a.rank_status = 5 where a.id = (select b.house_id from yc_agreement_tab b where b.id = ?)";
                        if (db.update(sql, new Object[]{ager_id}) == 1) {

                            sql = "select b.house_id,b.id,b.rank_type from yc_houserank_tab b,yc_agreement_tab c where b.id = c.house_id and c.id = ? ";
                            Map<String, Object> agerMap = db.queryForMap(sql, new Object[]{ager_id});

                            String super_house_id = StringHelper.get(agerMap, "house_id");
                            String house_id = StringHelper.get(agerMap, "id");
                            String rank_type = StringHelper.get(agerMap, "rank_type");

                            if ("0".equals(rank_type)) {//整租 需要将其他的设置为无效
                                sql = "update yc_houserank_tab a set a.rank_status = 6 where a.house_id = ? and a.id <> ?";
                                if (db.update(sql, new Object[]{super_house_id, house_id}) == 1) {
                                    logger.debug("1");
                                    return updateFinancialDetail(bill_id, ager_id);
                                }
                            } else {
                                sql = "update yc_houserank_tab a set a.rank_status = 6 where a.house_id = ? and a.rank_type = 0";
                                if (db.update(sql, new Object[]{super_house_id}) == 1) {
                                    logger.debug("1");
                                    return updateFinancialDetail(bill_id, ager_id);
                                }
                            }


                        }
                    }

                  //资源类型1租金账单2水电燃账单3退租账单4增值保洁账单5增值维修账单6其他账单
                }
            } else if ("3".equals(StringHelper.get(map, "resource_type")) || "4".equals(StringHelper.get(map, "resource_type")) || "5".equals(StringHelper.get(map, "resource_type"))) {
                //先执行完毕
                boolean truth = updateFinancialDetail(bill_id, ager_id);
                final String resource_id = StringHelper.get(map,"resource_id");
                //后线程定时器调用
                service.schedule(new Runnable(){
                    public void run(){
                        String result = "";
                        try {
                          //查出服务订单号
                          result = HttpURLConnHelper.execute(Constants.ROOTURL+"interfaces/common/pay.do?relevance_id="+resource_id+"&relevance_type=1", "");
                          logger.trace("通知result:"+result+"  请求信息："+Constants.ROOTURL+"interfaces/common/pay.do?relevance_id="+resource_id+"&relevance_type=1");
                        } catch (Exception ex) {
                            logger.error("订单进入待评价环节失败：" + bill_id, ex);
                        }
                        if (!"1".equals(result)) {
                            try {
                                //出错
                                SmsRequest.sendSysError("17512510101", "支付ID：" + bill_id + "调用后台服务接口出现异常", Constants.YCQWJ_API);
                                SmsRequest.sendSysError("18252028618", "支付ID：" + bill_id + "调用后台服务接口出现异常", Constants.YCQWJ_API);
                            } catch (Exception ex1) {
                                logger.error("短信发送失败", ex1);
                            }
                        }
                    }
                }, 100, TimeUnit.MILLISECONDS);
                
                return truth;
            } else {
                logger.debug("2");
                return updateFinancialDetail(bill_id, ager_id);
            }
        } else {
            logger.debug("3");
            return updateFinancialDetail(bill_id, ager_id);
        }
        return false;
    }

    /**
     * 更新财务明细数据信息
     */
    @Transactional
    public boolean updateFinancialDetail(String bill_id, String ager_id) {
        //更改自身实现加锁操作
        String sql = "update financial_bill_tab a set a.id = a.id where a.id = ?";
        if (db.update(sql, new Object[]{bill_id}) != 1) {
            db.rollback();
            logger.debug("-----------");
            return false;
        }

        sql = "select a.id,a.state,a.resource_type,a.type from financial_bill_tab a where a.id = ?";
        Map<String, Object> map = db.queryForMap(sql, new Object[]{bill_id});
        String state = StringHelper.get(map, "state");
        String resource_type = StringHelper.get(map, "resource_type");
        String type = StringHelper.get(map, "type");

        //查看当前以及缴费多少
        sql = "select a.bill_id,a.cost,ifnull(a.discounts_cost,0) discounts_cost,a.is_split,a.id,(case when a.type = 1 then '支付宝公众号支付' when a.type = 2 then '微信公众号' when a.type = 3 then '支付宝网页支付' when a.type = 4 then '支付宝app支付' when a.type = 5 then '微信app支付' when a.type = 6 then '银行转账' else '支付宝转账' end) pay_type,"
                + "a.bank_name,a.bank_account,a.tro_no,a.pay_time,a.name "
                + "from financial_bill_pay_tab a where a.bill_id = ? and a.state = 3 and a.is_split = 0";
        logger.debug("查看当前以及缴费多少:" + str.getSql(sql, new Object[]{bill_id}));
        List<Map<String, Object>> list = db.queryForList(sql, new Object[]{bill_id});
        logger.debug("取出所有未支付明细信息:" + list);

        //每一条明细去合计信息
        //循环已经交费记录信息
        for (int i = 0; i < list.size(); i++) {
            logger.debug("list.get(i):" + list.get(i));
            Double cost = Double.valueOf(StringHelper.get(list.get(i), "cost"));//需要缴费金额
            Double discounts_cost = Double.valueOf(StringHelper.get(list.get(i), "discounts_cost"));//优惠金额
            Double yet_cost = AmountUtil.add(cost, discounts_cost, 2);

            String is_split = StringHelper.get(list.get(i), "is_split");//此条交易明细是否已拆分
            String id = StringHelper.get(list.get(i), "id");//
            String pay_type = StringHelper.get(list.get(i), "pay_type");//
            String bank_name = StringHelper.get(list.get(i), "bank_name");//
            String bank_account = StringHelper.get(list.get(i), "bank_account");//
            String pay_time = StringHelper.get(list.get(i), "pay_time");//
            String tro_no = StringHelper.get(list.get(i), "tro_no");//
            String name = StringHelper.get(list.get(i), "name");//
            if ("1".equals(type)) {//收入
                //取出所有未支付明细信息
                sql = "select b.id,b.cost,ifnull(sum(c.yet_cost),0) yet_cost,ifnull(sum(c.discount_cost),0) discount_cost\n"
                        + "  from financial_bill_detail_tab a ,financial_receivable_tab b left join financial_yet_tab c on b.id = c.fin_id and c.type = 1\n"
                        + " where a.receivable_id = b.id \n"
                        + "   and b.isdelete = 1 \n"
                        + "   and a.bill_id = ?\n"
                        + "   and b.`status` = 0\n"
                        + " group by b.id,b.cost order by b.id";
                logger.debug("取出所有未支付明细信息:" + str.getSql(sql, new Object[]{bill_id}));
                List<Map<String, Object>> recList = db.queryForList(sql, new Object[]{bill_id});
                logger.debug("取出所有未支付明细信息:" + recList);
                //看此收入明细对应金额
                if (!recList.isEmpty()) {
                    for (Map<String, Object> map1 : recList) {
                        if (cost == 0D && discounts_cost == 0D) {
                            continue;
                        }
                        Double fin_cost = Double.valueOf(StringHelper.get(map1, "cost"));
                        Double fin_yet_cost = Double.valueOf(StringHelper.get(map1, "yet_cost"));
                        Double fin_discount_cost = Double.valueOf(StringHelper.get(map1, "discount_cost"));
                        String fin_id = StringHelper.get(map1, "id");

                        //如果支付金额为负值直接跳过
                        if (fin_cost < 0) {
                            continue;
                        }

                        //先看财务明细已交的总金额
                        Double yj_fin_cost = AmountUtil.add(fin_yet_cost, fin_discount_cost, 2);

                        //剩余多少没有交
                        Double xc_fin_cost = AmountUtil.subtract(fin_cost, yj_fin_cost, 2);

                        Double cost_ = AmountUtil.subtract(cost, xc_fin_cost, 2);

                        logger.debug("yj_fin_cost:" + yj_fin_cost + "-xc_fin_cost:" + xc_fin_cost + "|cost:" + cost);

                        if (cost_ >= 0d) {//说明账单中的金额足够交当前明细值
                            sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,1,now(),?,?,?,?,?,?,?,?)";
                            logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                            int yet_id = db.insert(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                            if (yet_id <= 0) {
                                db.rollback();
                                return false;
                            }
                            if (!updateSdm(yet_id)) {
                                return false;
                            };
                            //同步到金蝶那边处理
                            JdTask.addPool("8", yet_id + "");
                            xc_fin_cost = AmountUtil.subtract(xc_fin_cost, xc_fin_cost, 2);
                            cost = cost_;
                        } else {//小于0不够，需要拿优惠金额来做抵消
                            sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,1,now(),?,?,?,?,?,?,?,?)";
                            logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                            int yet_id = db.insert(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                            if (yet_id <= 0) {
                                db.rollback();
                                return false;
                            }
                            if (!updateSdm(yet_id)) {
                                return false;
                            };
                            //同步到金蝶那边处理
                            JdTask.addPool("8", yet_id + "");
                            xc_fin_cost = AmountUtil.subtract(xc_fin_cost, cost, 2);
                            cost = 0D;
                        }
                        if (discounts_cost > 0D && xc_fin_cost > 0D) {
                            Double discounts_cost_ = AmountUtil.subtract(discounts_cost, xc_fin_cost, 2);
                            if (discounts_cost_ >= 0d) {//说明账单中的优惠金额足够抵消,
                                sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,1,now(),?,?,?,?,?,?,?,?)";
                                logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                int yet_id = db.insert(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                if (yet_id <= 0) {
                                    db.rollback();
                                    return false;
                                }
                                if (!updateSdm(yet_id)) {
                                    return false;
                                };
                                //同步到金蝶那边处理
                                JdTask.addPool("8", yet_id + "");
                                discounts_cost = discounts_cost_;
                            } else {//说明不够抵消金额
                                sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,1,now(),?,?,?,?,?,?,?,?)";
                                logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                int yet_id = db.insert(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                if (yet_id <= 0) {
                                    db.rollback();
                                    return false;
                                }
                                if (!updateSdm(yet_id)) {
                                    return false;
                                };
                                discounts_cost = 0d;
                                //同步到金蝶那边处理
                                JdTask.addPool("8", yet_id + "");
                            }
                        }
                        sql = "update financial_receivable_tab a set a.yet_cost = ifnull((select sum(ifnull(b.yet_cost,0)+ifnull(b.discount_cost,0)) from financial_yet_tab b where b.fin_id = a.id and b.type = 1),0) where a.id = ?";
                        if (db.update(sql, new Object[]{fin_id}) != 1) {
                            db.rollback();
                            return false;
                        }
                        sql = "update financial_receivable_tab a set a.status = 1 where a.id = ? and a.cost = a.yet_cost ";
                        if (db.update(sql, new Object[]{fin_id}) != 1) {
                            db.rollback();
                            return false;
                        }
                    }
                }
            } else {//支出操作
                //取出所有未支付明细信息
                sql = "select b.id,b.cost,ifnull(sum(c.yet_cost),0) yet_cost,ifnull(sum(c.discount_cost),0) discount_cost\n"
                        + "  from financial_bill_detail_tab a ,financial_payable_tab b left join financial_yet_tab c on b.id = c.fin_id and c.type = 1\n"
                        + " where a.payable_id = b.id \n"
                        + "   and b.isdelete = 1 \n"
                        + "   and a.bill_id = ?\n"
                        + "   and b.`status` = 0\n"
                        + " group by b.id,b.cost order by b.id";
                logger.debug("取出所有未支付明细信息:" + str.getSql(sql, new Object[]{bill_id}));
                List<Map<String, Object>> recList = db.queryForList(sql, new Object[]{bill_id});
                logger.debug("取出所有未支付明细信息:" + recList);
                //看此收入明细对应金额
                if (!recList.isEmpty()) {
                    for (Map<String, Object> map1 : recList) {
                        if (cost == 0D && discounts_cost == 0D) {
                            continue;
                        }
                        Double fin_cost = Double.valueOf(StringHelper.get(map1, "cost"));
                        Double fin_yet_cost = Double.valueOf(StringHelper.get(map1, "yet_cost"));
                        Double fin_discount_cost = Double.valueOf(StringHelper.get(map1, "discount_cost"));
                        String fin_id = StringHelper.get(map1, "id");

                        //如果支付金额为负值直接跳过
                        if (fin_cost < 0) {
                            continue;
                        }

                        //先看财务明细已交的总金额
                        Double yj_fin_cost = AmountUtil.add(fin_yet_cost, fin_discount_cost, 2);

                        //剩余多少没有交
                        Double xc_fin_cost = AmountUtil.subtract(fin_cost, yj_fin_cost, 2);

                        Double cost_ = AmountUtil.subtract(cost, xc_fin_cost, 2);

                        logger.debug("yj_fin_cost:" + yj_fin_cost + "-xc_fin_cost:" + xc_fin_cost + "|cost:" + cost);

                        if (cost_ >= 0d) {//说明账单中的金额足够交当前明细值
                            sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,2,now(),?,?,?,?,?,?,?,?)";
                            logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                            int yet_id = db.insert(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                            if (yet_id <= 0) {
                                db.rollback();
                                return false;
                            }
                            //同步到金蝶那边处理
                            JdTask.addPool("9", yet_id + "");
                            xc_fin_cost = AmountUtil.subtract(xc_fin_cost, xc_fin_cost, 2);
                            cost = cost_;
                        } else {//小于0不够，需要拿优惠金额来做抵消
                            sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,2,now(),?,?,?,?,?,?,?,?)";
                            logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                            int yet_id = db.insert(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                            if (yet_id <= 0) {
                                db.rollback();
                                return false;
                            }
                            //同步到金蝶那边处理
                            JdTask.addPool("9", yet_id + "");
                            xc_fin_cost = AmountUtil.subtract(xc_fin_cost, cost, 2);
                            cost = 0D;
                        }
                        if (discounts_cost > 0D && xc_fin_cost > 0D) {
                            Double discounts_cost_ = AmountUtil.subtract(discounts_cost, xc_fin_cost, 2);
                            if (discounts_cost_ >= 0d) {//说明账单中的优惠金额足够抵消,
                                sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,2,now(),?,?,?,?,?,?,?,?)";
                                logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                int yet_id = db.insert(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                if (yet_id <= 0) {
                                    db.rollback();
                                    return false;
                                }
                                //同步到金蝶那边处理
                                JdTask.addPool("9", yet_id + "");
                                discounts_cost = discounts_cost_;
                            } else {//说明不够抵消金额
                                sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time)values(?,?,2,now(),?,?,?,?,?,?,?,?)";
                                logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                int yet_id = db.insert(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                if (yet_id <= 0) {
                                    db.rollback();
                                    return false;
                                }
                                //同步到金蝶那边处理
                                JdTask.addPool("9", yet_id + "");
                                discounts_cost = 0d;
                            }
                        }
                        sql = "update financial_payable_tab a set a.yet_cost = ifnull((select sum(ifnull(b.yet_cost,0)+ifnull(b.discount_cost,0)) from financial_yet_tab b where b.fin_id = a.id and b.type = 1),0) where a.id = ?";
                        if (db.update(sql, new Object[]{fin_id}) != 1) {
                            db.rollback();
                            return false;
                        }
                        sql = "update financial_payable_tab a set a.status = 1 where a.id = ? and a.cost = a.yet_cost ";
                        if (db.update(sql, new Object[]{fin_id}) != 1) {
                            db.rollback();
                            return false;
                        }
                    }
                }
            }
            //更新明细为已拆分
            sql = "update financial_bill_pay_tab a set a.is_split = 1 where a.id = ?";
            if (db.update(sql, new Object[]{id}) != 1) {//出现错误直接退出
                db.rollback();
                return false;
            }
        }
        if ("3".equals(state)) {
            //如果当前明细支付完成，需要将所有负值全部设置为已支付
            sql = "insert into financial_yet_tab (fin_id,yet_cost,type,oper_date)\n"
                    + " select a.id,a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0) cost,\n"
                    + " 1,now()\n"
                    + "   from financial_receivable_tab a,financial_bill_detail_tab c\n"
                    + "  where a.id = c.receivable_id\n"
                    + "	  and a.`status` = 0\n"
                    + "		and ABS(a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0)) > 0\n"
                    + "		and c.bill_id = ? ";
            if (db.update(sql, new Object[]{bill_id}) != 1) {
                db.rollback();
                return false;
            }
            sql = "update financial_receivable_tab a set a.`status` = 1,a.yet_cost=ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0) where exists(select 1 from financial_bill_detail_tab b where b.receivable_id = a.id and b.bill_id = ?)";
            if (db.update(sql, new Object[]{bill_id}) != 1) {
                db.rollback();
                return false;
            }
            //更新支出也为成功
            sql = "insert into financial_yet_tab (fin_id,yet_cost,type,oper_date)\n"
                    + " select a.id,a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0) cost,\n"
                    + " 2,now()\n"
                    + "   from financial_payable_tab a,financial_bill_detail_tab c\n"
                    + "  where a.id = c.payable_id\n"
                    + "	  and a.`status` = 0\n"
                    + "		and ABS(a.cost - ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0)-ifnull(a.yet_cost,0)) > 0\n"
                    + "		and c.bill_id = ? ";
            if (db.update(sql, new Object[]{bill_id}) != 1) {
                db.rollback();
                return false;
            }
            sql = "update financial_payable_tab a set a.`status` = 1,a.yet_cost=ifnull((select ifnull(sum(b.yet_cost),0)+ifnull(sum(b.discount_cost),0) from financial_yet_tab b where b.fin_id = a.id),0) where exists(select 1 from financial_bill_detail_tab b where b.payable_id = a.id and b.bill_id = ?)";
            if (db.update(sql, new Object[]{bill_id}) != 1) {
                db.rollback();
                return false;
            }
        }

        return true;
    }

    /**
     * 额外更新代缴费抵扣信息
     *
     * @param fin_yet_id
     */
    public boolean updateSdm(int fin_yet_id) {
        logger.debug("开始更新代缴费抵扣信息---------start---------------" + fin_yet_id);
        String sql = "select a.yet_cost,b.category,b.secondary \n"
                + "	  from financial_yet_tab a ,financial_receivable_tab b\n"
                + "	 where a.fin_id = b.id \n"
                + "	   and a.id = ?"
                + " and b.category = 4";
        Map<String, Object> map = db.queryForMap(sql, new Object[]{fin_yet_id});

        if (!map.isEmpty()) {//是代缴费明细，进行水电煤抵扣
            String ager_id = StringHelper.get(map, "secondary");//查出的合约号
            Double dis_cost = Double.valueOf(StringHelper.get(map, "yet_cost"));
            Double count_cost = 0d;//总抵扣多少
            sql = "select b.bill_id from financial_receivable_tab a,financial_bill_detail_tab b,financial_bill_tab c \n"
                    + "		 where a.id = b.receivable_id\n"
                    + "		   and b.bill_id = c.id \n"
                    + "			 and c.state = 1\n"
                    + "		   and a.secondary = ? and a.category in (11,12,13)\n"
                    + "			 order by b.bill_id LIMIT 0,1";
            String bill_id = db.queryForString(sql, new Object[]{ager_id});
            if (!"".equals(bill_id)) {
                String pay_time = DateHelper.getToday("yyyy-MM-dd HH:mm:ss");
//                //做一条代缴费抵扣水电煤数据信息
//                sql = "INSERT INTO financial_bill_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)values"
//                        + "(?,?,DATE_FORMAT(?,'%Y-%m-%d %T'),?,?,?,?,?,?,3,?,now(),now(),?,?,?,?)";
                String id = UUID.randomUUID().toString();
//                if (db.update(sql, new Object[]{id, "8", pay_time, "代缴费抵扣", "无", "无", "", "代缴费抵扣", 0, "", "", ager_id, bill_id, dis_cost}) != 1) {
//                    logger.debug("退出代缴费更新------2---------" + fin_yet_id);
//                    db.rollback();
//                    return false;
//                } else {
                    //更新对应的账单表信息
                    //进行核减金额
                    //取出所有未支付明细信息
                    sql = "select b.id,b.cost,ifnull(sum(c.yet_cost),0) yet_cost,ifnull(sum(c.discount_cost),0) discount_cost\n"
                            + "  from financial_bill_detail_tab a ,financial_receivable_tab b left join financial_yet_tab c on b.id = c.fin_id and c.type = 1\n"
                            + " where a.receivable_id = b.id \n"
                            + "   and b.isdelete = 1 \n"
                            + "   and a.bill_id = ?\n"
                            + "   and b.`status` = 0\n"
                            + " group by b.id,b.cost order by b.id";
                    logger.debug("取出所有未支付明细信息:" + str.getSql(sql, new Object[]{bill_id}));
                    List<Map<String, Object>> recList = db.queryForList(sql, new Object[]{bill_id});
                    logger.debug("取出所有未支付明细信息:" + recList);
                    Double cost = 0D;
                    Double discounts_cost = dis_cost;
                    //看此收入明细对应金额
                    if (!recList.isEmpty()) {
                        String pay_type = "8";
                        String tro_no = "";
                        String bank_account = "";
                        String bank_name = "";
                        String name = "";
                        for (Map<String, Object> map1 : recList) {
                            if (cost == 0D && discounts_cost == 0D) {
                                continue;
                            }
                            Double fin_cost = Double.valueOf(StringHelper.get(map1, "cost"));
                            Double fin_yet_cost = Double.valueOf(StringHelper.get(map1, "yet_cost"));
                            Double fin_discount_cost = Double.valueOf(StringHelper.get(map1, "discount_cost"));
                            String fin_id = StringHelper.get(map1, "id");

                            //如果支付金额为负值直接跳过
                            if (fin_cost < 0) {
                                continue;
                            }

                            //先看财务明细已交的总金额
                            Double yj_fin_cost = AmountUtil.add(fin_yet_cost, fin_discount_cost, 2);

                            //剩余多少没有交
                            Double xc_fin_cost = AmountUtil.subtract(fin_cost, yj_fin_cost, 2);

                            Double cost_ = AmountUtil.subtract(cost, xc_fin_cost, 2);

                            logger.debug("yj_fin_cost:" + yj_fin_cost + "-xc_fin_cost:" + xc_fin_cost + "|cost:" + cost);

                            if (cost_ >= 0d) {//说明账单中的金额足够交当前明细值
                                sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                int yet_id = db.insert(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                if (yet_id <= 0) {
                                    logger.debug("退出代缴费更新------2--------" + fin_yet_id);
                                    db.rollback();
                                    return false;
                                }
                                //同步到金蝶那边处理
                                JdTask.addPool("8", yet_id + "");
                                xc_fin_cost = AmountUtil.subtract(xc_fin_cost, xc_fin_cost, 2);
                                cost = cost_;
                            } else {//小于0不够，需要拿优惠金额来做抵消
                                sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                int yet_id = db.insert(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                if (yet_id <= 0) {
                                    logger.debug("退出代缴费更新------3---------" + fin_yet_id);
                                    db.rollback();
                                    return false;
                                }
                                //同步到金蝶那边处理
                                JdTask.addPool("8", yet_id + "");
                                xc_fin_cost = AmountUtil.subtract(xc_fin_cost, cost, 2);
                                cost = 0D;
                            }
                            if (discounts_cost > 0D && xc_fin_cost > 0D) {
                                Double discounts_cost_ = AmountUtil.subtract(discounts_cost, xc_fin_cost, 2);
                                if (discounts_cost_ >= 0d) {//说明账单中的优惠金额足够抵消,
                                    sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                    logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                    int yet_id = db.insert(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                    if (yet_id <= 0) {
                                        logger.debug("退出代缴费更新------4---------" + fin_yet_id);
                                        db.rollback();
                                        return false;
                                    }
                                    count_cost = AmountUtil.add(count_cost, xc_fin_cost, 2);
                                    //同步到金蝶那边处理
                                    JdTask.addPool("8", yet_id + "");
                                    discounts_cost = discounts_cost_;
                                } else {//说明不够抵消金额
                                    sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                    logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                    int yet_id = db.insert(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                    if (yet_id <= 0) {
                                        logger.debug("退出代缴费更新------5---------" + fin_yet_id);
                                        db.rollback();
                                        return false;
                                    }
                                    count_cost = AmountUtil.add(count_cost, Math.abs(discounts_cost), 2);
                                    discounts_cost = 0d;
                                    //同步到金蝶那边处理
                                    JdTask.addPool("8", yet_id + "");
                                }
                            }
                            sql = "update financial_receivable_tab a set a.yet_cost = ifnull((select sum(ifnull(b.yet_cost,0)+ifnull(b.discount_cost,0)) from financial_yet_tab b where b.fin_id = a.id and b.type = 1),0) where a.id = ?";
                            if (db.update(sql, new Object[]{fin_id}) != 1) {
                                logger.debug("退出代缴费更新------6---------" + fin_yet_id);
                                db.rollback();
                                return false;
                            }
                            sql = "update financial_receivable_tab a set a.status = 1 where a.id = ? and a.cost = a.yet_cost ";
                            if (db.update(sql, new Object[]{fin_id}) != 1) {
                                db.rollback();
                                logger.debug("退出代缴费更新------1---------" + fin_yet_id);
                                return false;
                            }
                        }
                    }
                    //插入
                    sql = "INSERT INTO financial_bill_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)values"
                        + "(?,?,DATE_FORMAT(?,'%Y-%m-%d %T'),?,?,?,?,?,?,3,?,now(),now(),?,?,?,?)";
                    if (db.update(sql, new Object[]{id, "8", pay_time, "代缴费抵扣", "无", "无", "", "代缴费抵扣", 0, "", "", ager_id, bill_id, count_cost}) != 1) {
                        logger.debug("退出代缴费更新------2---------" + fin_yet_id);
                        db.rollback();
                        return false;
                    }
//                }
            }

            //更新合约对应的金额
            sql = "update financial_bill_tab a set a.discounts_cost = a.discounts_cost +? where a.id = ?";
            if (db.update(sql, new Object[]{count_cost, bill_id}) <= 0) {
                db.rollback();
                return false;
            }
            
            //更新对应状态
            sql = "update financial_bill_tab a set a.state = 3 where a.cost <= a.yet_cost + a.discounts_cost and a.id = ? ";
            if (db.update(sql, new Object[]{bill_id}) <= 0) {
                db.rollback();
                return false;
            }

        }
        logger.debug("开始更新代缴费抵扣信息---------end---------------" + fin_yet_id);
        return true;
    }

    /**
     * 手动生成合约账单信息
     */
    @Transactional
    public void cfBill() {
        String testName = "_ly";
        logger.debug("开始生成合约账单信息-------start----------");
        String sql = "drop table tmp_ly_tmp_1 ";
        db.update(sql);
        sql = "create table tmp_ly_tmp_1 as\n"
                + "select a2.ager_id,DATE_FORMAT(b2.plat_time,'%Y-%m') months \n"
                + "				        from financial_settlements_tab a2 ,financial_receivable_tab b2 \n"
                + "							 where a2.correlation_id = b2.correlation \n"
                + "							   and b2.category in(11,12,13)  \n"
                + "								 and b2.secondary_type = 1 \n"
                + "								 and b2.status = 0 \n"
                + "							 group by a2.ager_id,DATE_FORMAT(b2.plat_time,'%Y-%m')";
        db.update(sql);
        //先获取所有合约信息
        sql = "select a1.*, t.id cancletaskid,\n"
                + "     (select count(1) from financial_receivable_tab c where c.secondary = a1.id and c.status = 0 and c.category = 5 and c.isdelete = 1 ) isyz,"
                + "     (select count(1) from financial_receivable_tab c where c.secondary = a1.id and c.status = 0 and c.category = 4 and c.isdelete = 1 ) isprepay,"
                + "     ifnull((select count(1) from work_order e where e.rental_lease_order_id = a1.id and e.type in('B','H') and e.sub_order_state = 'K'  ),0) isbj,"
                + "     (select GROUP_CONCAT(bb.months) from tmp_ly_tmp_1 bb where bb.ager_id = a1.id ) sdmonths "
                + " from yc_agreement_tab a1 LEFT JOIN work_order t on a1.id=t.rental_lease_order_id and t.type = 'A' and t.sub_order_state in ('N','K','T') \n"
                + "where a1.`status` in (12,2,7) and a1.isdelete='1' \n"
                + "and a1.type = 2 order by a1.create_time desc";
        List<Map<String, Object>> list = db.queryForList(sql);

        sql = "update financial_payable_tab a set a.yet_cost = a.cost where a.`status` <> 0";
        if (db.update(sql) != 1) {
            db.rollback();
            logger.debug("更新收支明细已支付金额错误：");
            return;
        }
        sql = "update financial_receivable_tab a set a.yet_cost = a.cost where a.`status` <> 0";
        if (db.update(sql) != 1) {
            db.rollback();
            logger.error("更新收支明细已支付金额错误：");
            return;
        }
        int cnt = list.size();
        for (int i = 0; i < cnt; i++) {

            String order_id = StringHelper.get(list.get(i), "cancletaskid");
            String id = StringHelper.get(list.get(i), "id");
            String status = StringHelper.get(list.get(i), "status");
            String isyz = StringHelper.get(list.get(i), "isyz");
            String isprepaid = StringHelper.get(list.get(i), "isprepay");
            String sdmonths = StringHelper.get(list.get(i), "sdmonths");
            String isbj = StringHelper.get(list.get(i), "isbj");
            String agree_name;
            double cost = 0;
            double yh_cost = 0;
            double yf_cost = 0;
            logger.info("-----------------------当前已运行到：" + i + "总数：" + cnt + "当前ID：" + id + "--------------------------------------------");

//            if( (! "3764".equals(id)) && (! "3773".equals(id))){
//            	
//            	continue;
//            }
            logger.debug("sdmonths:" + sdmonths
                    + " status:" + status + " isyz:" + isyz
                    + " isbj:" + isbj + " order_id:" + order_id);

            //退租合约
            if ("7".equals(status) && (!"".equals(order_id))) {

                //获取退租订单编号,该退租订单必须在租客支付，公司退款或财务审核阶段，其他阶段还是生成正常账单
                //获取所有总费用信息
                sql = "select ifnull(y.text_input, 0) cost, a.name from work_order t LEFT JOIN yc_agreement_tab a on t.rental_lease_order_id=a.id \n"
                        + "LEFT JOIN cancel_lease_order e on t.id=e.work_order_id \n"
                        + " LEFT JOIN cancel_lease_order_value y ON e.id = y.sub_order_id \n"
                        + " where t.rental_lease_order_id= ? and y.attr_path = 'CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY' \n"
                        + " order by  t.created_date desc limit 0,1";
                //如果没有记录，或者账单金额为空，或者退租已完成核算，则不再生成账单
                Map<String, Object> cntMap = db.queryForMap(sql, new Object[]{id});
                if (cntMap.isEmpty() || "0".equals(StringHelper.get(cntMap, "cost"))) {
                    logger.error("金额无效，无需生成账单" + StringHelper.get(cntMap, "cost"));
                    continue;
                }

                //插入一条明细信息
                cost = Double.valueOf(StringHelper.get(cntMap, "cost"));
                agree_name = StringHelper.get(cntMap, "name");
                int bill_id = db.insert("INSERT INTO financial_bill" + testName + "_tab (cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                        + "  values(?,?,?,?,?,now(),now(),'退租账单',?,?,3,now(),?)",
                        new Object[]{Math.abs(cost), 0, yh_cost, "退租账单_" + agree_name, cost > 0 ? "1" : "2", id, order_id, "退租账单信息"});
                if (bill_id <= 0) {
                    db.rollback();
                    logger.error("插入一条账单信息出现错误：" + id + "--" + bill_id);
                    return;
                }
                //插入退租明细到表中
                sql = "insert into financial_bill" + testName + "_detail_tab(bill_id,receivable_id,payable_id)\n"
                        + "select ?,b.id,null\n"
                        + "   from financial_settlements_tab a ,financial_receivable_tab b\n"
                        + " where a.correlation_id = b.correlation\n"
                        + "	 and a.ager_id = ?\n"
                        + "	 and b.isdelete = 1 \n"
                        + "	 union all\n"
                        + "	 select ?,null,b.id\n"
                        + "  from financial_settlements_tab a ,financial_payable_tab b\n"
                        + " where a.correlation_id = b.correlation\n"
                        + "	 and a.ager_id = ?\n"
                        + "	 and b.isdelete = 1 ";
                if (db.update(sql, new Object[]{bill_id, id, bill_id, id}) <= 0) {
                    db.rollback();
                    logger.error("插入退租明细到表中出现错误：" + id);
                    return;
                }

                //插入账单支付明细表
                sql = "INSERT INTO financial_bill" + testName + "_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)\n"
                        + "select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                        + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),3,null,now(),now(),\n"
                        + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                        + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0)"
                        + "   from financial_settlements_tab a ,financial_receivable_tab b\n"
                        + " where a.correlation_id = b.correlation\n"
                        + "	 and a.ager_id = ?\n"
                        + "	 and b.isdelete = 1\n"
                        + "	 and b.status <> 0\n"
                        + "	 union all\n"
                        + "	 select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                        + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0),3,null,now(),now(),\n"
                        + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                        + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0)"
                        + "  from financial_settlements_tab a ,financial_payable_tab b\n"
                        + " where a.correlation_id = b.correlation\n"
                        + "	 and a.ager_id = ?\n"
                        + "	 and b.isdelete = 1\n"
                        + "	 and b.status <> 0";
                if (db.update(sql, new Object[]{bill_id, id, bill_id, id}) != 1) {
                    db.rollback();
                    logger.error("插入账单支付明细表出现错误：" + id + "--" + bill_id);
                    return;
                }
            } else {
                if (!"0".equals(isyz)) {
                    //****************成押金账单
                    sql = "select b.id,b.cost,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id),0) yh_cost,\n"
                            + "			b.`status`,b.start_time,DATE_FORMAT(b.start_time,'%Y年%m月') months ,'押金' type\n"
                            + "  from financial_receivable_tab b\n"
                            + " where \n"
                            + "    b.secondary_type = 1\n"
                            + "	 and b.secondary = ?\n"
                            + "	 and b.category in(5) and b.isdelete = 1 limit 0,1 ";
                    Map<String, Object> map = db.queryForMap(sql, new Object[]{id});
                    if ("0".equals(StringHelper.get(map, "status"))) {//没有支付的生成账单

                        //插入一条明细信息
                        int bill_id = db.insert("INSERT INTO financial_bill" + testName + "_tab (cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                                + "  values(?,?,?,?,?,now(),now(),'系统生成押金账单',?,?,7,now(),?)", new Object[]{StringHelper.get(map, "cost"), 0, 0, "押金账单", Double.valueOf(StringHelper.get(map, "cost")) > 0 ? "1" : "2", id, id, "押金账单信息"});
                        if (bill_id <= 0) {
                            db.rollback();
                            logger.error("押金账单插入一条账单信息出现错误：" + id + "--" + bill_id);
                            return;
                        }

                        //因为状态为0 不会存在对应的支付明细，只需要生成对应关系即可
                        //插入退租明细到表中
                        sql = "insert into financial_bill" + testName + "_detail_tab(bill_id,receivable_id)values(?,?)";
                        if (db.update(sql, new Object[]{bill_id, StringHelper.get(map, "id")}) <= 0) {
                            db.rollback();
                            logger.error("插入押金账单到表中出现错误：" + id);
                            return;
                        }

                    }
                }

                //****************生成租金账单  排除365金融月付
                sql = "select sum(b.cost) cost,DATE_FORMAT(b.start_time,'%Y年%m月') months,\n"
                        + "			sum(ifnull((case when b.`status` <> 0 then (select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id) else 0 end),0)) yh_cost,\n"
                        + "			ifnull(sum((case when b.`status` <> 0 then b.cost else 0 end)),0) yf_cost,b.start_time,DATE_FORMAT(b.start_time,'%Y年%m月') months ,\n"
                        + "			'租金' type,\n"
                        + "			DATE_FORMAT(b.plat_time,'%Y-%m-%d %T') plat_time\n"
                        + "  from financial_receivable_tab b\n"
                        + " where b.secondary = ?\n"
                        + "	 and b.category in(1,4,2,6) \n"
                        + "	 and b.secondary_type = 1 and b.isdelete = 1 \n"
                        + "	 group by months";
                List<Map<String, Object>> zjList = db.queryForList(sql, new Object[]{id});
                for (int j = 0; j < zjList.size(); j++) {
                    cost = Double.valueOf(StringHelper.get(zjList.get(j), "cost"));
                    yh_cost = Double.valueOf(StringHelper.get(zjList.get(j), "yh_cost"));
                    yf_cost = Double.valueOf(StringHelper.get(zjList.get(j), "yf_cost"));
                    String months = StringHelper.get(zjList.get(j), "months");
                    String plat_time = StringHelper.get(zjList.get(j), "plat_time");
                    if (cost == yf_cost + yh_cost) {
                        logger.debug("支付金额相同退出-----:" + id + "---");
                        continue;
                    }
                    //插入一条明细信息
                    int bill_id = db.insert("INSERT INTO financial_bill" + testName + "_tab "
                            + "(cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                            + "  values(?,?,?,?,?,now(),now(),'系统生成租金账单',?,?,1,?,?)",
                            new Object[]{cost, yf_cost, yh_cost, months + "_租金账单", cost > 0 ? "1" : "2", id, id, plat_time, "租金账单信息"});
                    if (bill_id <= 0) {
                        db.rollback();
                        logger.error("租金账单插入一条账单信息出现错误：" + id + "--" + bill_id);
                        return;
                    }

                    //插入账单明细对应关系
                    sql = "insert into financial_bill" + testName + "_detail_tab(bill_id,receivable_id,payable_id)\n"
                            + "select ?,b.id,null \n"
                            + "  from financial_settlements_tab a ,financial_receivable_tab b\n"
                            + " where a.correlation_id = b.correlation\n"
                            + "	 and a.ager_id = ?\n"
                            + "	 and b.isdelete = 1\n"
                            + "	 and b.category in(1,4,2,6)\n"
                            + "	 and b.secondary_type = 1 \n"
                            + "	 and DATE_FORMAT(b.start_time,'%Y年%m月') = ?\n";
                    if (db.update(sql, new Object[]{bill_id, id, months}) <= 0) {
                        db.rollback();
                        logger.error("租金插入插入账单明细对应关系出现错误：" + id);
                        return;
                    }

                    //插入账单支付明细表
                    sql = "INSERT INTO financial_bill" + testName + "_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)\n"
                            + "select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                            + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),3,null,now(),now(),\n"
                            + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                            + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0)"
                            + "   from financial_settlements_tab a ,financial_receivable_tab b\n"
                            + " where a.correlation_id = b.correlation\n"
                            + "	 and a.ager_id = ?\n"
                            + "	 and b.isdelete = 1\n"
                            + "	 and b.status <> 0\n"
                            + "  and b.category in(1,4,2,6) "
                            + "  and b.secondary_type = 1 "
                            + "  and DATE_FORMAT(b.start_time,'%Y年%m月') = ?"
                            + "	 union all\n"
                            + "	 select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                            + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0),3,null,now(),now(),\n"
                            + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                            + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0)"
                            + "  from financial_settlements_tab a ,financial_payable_tab b\n"
                            + " where a.correlation_id = b.correlation\n"
                            + "	 and a.ager_id = ?\n"
                            + "  and b.category in(1,4,2,6) "
                            + "  and b.secondary_type = 1 "
                            + "	 and b.isdelete = 1\n"
                            + "  and DATE_FORMAT(b.start_time,'%Y年%m月') = ?"
                            + "	 and b.status <> 0";
                    if (db.update(sql, new Object[]{bill_id, id, months, bill_id, id, months}) != 1) {
                        db.rollback();
                        logger.error("插入账单支付明细表出现错误：" + id + "--" + bill_id);
                        return;
                    }

                }

                //********************生成365 金融月付账单
                sql = "select sum(b.cost) cost,DATE_FORMAT(b.start_time,'%Y年%m月') months,\n"
                        + "				sum(ifnull((case when b.`status` <> 0 then (select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id) else 0 end),0)) yh_cost,\n"
                        + "				ifnull(sum((case when b.`status` <> 0 then b.cost else 0 end)),0) yf_cost,b.start_time,DATE_FORMAT(b.start_time,'%Y年%m月') months ,\n"
                        + "				'租金' type,\n"
                        + "				DATE_FORMAT(b.plat_time,'%Y-%m-%d %T') plat_time\n"
                        + "		from financial_receivable_tab b\n"
                        + "	 where b.secondary = ?\n"
                        + "		 and b.category in(14) \n"
                        + "		 and b.secondary_type = 1 \n"
                        + "		 and b.isdelete = 1\n"
                        + "		 and b.`status` = 0";
                zjList = db.queryForList(sql, new Object[]{id});
                for (int j = 0; j < zjList.size(); j++) {
                    if (StringHelper.get(zjList.get(j), "cost").isEmpty()) {
                        //没有值需要处理下一条
                        continue;
                    }
                    cost = Double.valueOf(StringHelper.get(zjList.get(j), "cost"));
                    yh_cost = Double.valueOf(StringHelper.get(zjList.get(j), "yh_cost"));
                    yf_cost = Double.valueOf(StringHelper.get(zjList.get(j), "yf_cost"));
                    String months = StringHelper.get(zjList.get(j), "months");
                    String plat_time = StringHelper.get(zjList.get(j), "plat_time");
                    if (cost == yf_cost + yh_cost) {
                        logger.debug("支付金额相同退出-----:" + id + "---");
                        continue;
                    }
                    //插入一条明细信息
                    int bill_id = db.insert("INSERT INTO financial_bill" + testName + "_tab "
                            + "(cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                            + "  values(?,?,?,?,?,now(),now(),'系统生成租金账单',?,?,1,?,?)",
                            new Object[]{cost, yf_cost, yh_cost, months + "_金融月付租金账单", cost > 0 ? "1" : "2", id, id, plat_time, "租金账单信息"});
                    if (bill_id <= 0) {
                        db.rollback();
                        logger.error("租金账单插入一条账单信息出现错误：" + id + "--" + bill_id);
                        return;
                    }

                    //插入账单明细对应关系
                    sql = "insert into financial_bill" + testName + "_detail_tab(bill_id,receivable_id,payable_id)\n"
                            + "select ?,b.id,null \n"
                            + "  from financial_settlements_tab a ,financial_receivable_tab b\n"
                            + " where a.correlation_id = b.correlation\n"
                            + "	 and a.ager_id = ?\n"
                            + "	 and b.isdelete = 1\n"
                            + "	 and b.category in(1,4,2,6)\n"
                            + "	 and b.secondary_type = 1 \n"
                            + "	 and DATE_FORMAT(b.start_time,'%Y年%m月') = ?\n";
                    if (db.update(sql, new Object[]{bill_id, id, months}) <= 0) {
                        db.rollback();
                        logger.error("租金插入插入账单明细对应关系出现错误：" + id);
                        return;
                    }

                    //插入账单支付明细表
                    sql = "INSERT INTO financial_bill" + testName + "_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)\n"
                            + "select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                            + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),3,null,now(),now(),\n"
                            + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                            + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0)"
                            + "   from financial_settlements_tab a ,financial_receivable_tab b\n"
                            + " where a.correlation_id = b.correlation\n"
                            + "	 and a.ager_id = ?\n"
                            + "	 and b.isdelete = 1\n"
                            + "	 and b.status <> 0\n"
                            + "  and b.category in(1,4,2,6) "
                            + "  and b.secondary_type = 1 "
                            + "  and DATE_FORMAT(b.start_time,'%Y年%m月') = ?"
                            + "	 union all\n"
                            + "	 select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                            + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0),3,null,now(),now(),\n"
                            + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                            + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0)"
                            + "  from financial_settlements_tab a ,financial_payable_tab b\n"
                            + " where a.correlation_id = b.correlation\n"
                            + "	 and a.ager_id = ?\n"
                            + "  and b.category in(1,4,2,6) "
                            + "  and b.secondary_type = 1 "
                            + "	 and b.isdelete = 1\n"
                            + "  and DATE_FORMAT(b.start_time,'%Y年%m月') = ?"
                            + "	 and b.status <> 0";
                    if (db.update(sql, new Object[]{bill_id, id, months, bill_id, id, months}) != 1) {
                        db.rollback();
                        logger.error("插入账单支付明细表出现错误：" + id + "--" + bill_id);
                        return;
                    }

                }

//                
//                //*******************生成水电煤账单 ,水电费按月生成
                if ("0".equals(isprepaid)) {
                    //只有没有预缴费时才生成押金，否则只收预缴费
                    if (!"".equals(sdmonths)) {
                        for (int a = 1; a < 12; a++) {
                            String months = "2017-" + (a < 10 ? "0" + a : a);
                            if (!sdmonths.contains(months)) {
                                logger.error("月份错误：" + id + "--" + months + "---" + sdmonths);
                                continue;
                            }
                            sql = "select sum(b.cost) cost,DATE_FORMAT(b.plat_time,'%Y-%m') months,\n"
                                    + "			(case when b.status <> 0 then b.cost else 0 end) yf_cost,max(b.plat_time) plat_time,\n"
                                    + "    	  sum(ifnull((case when b.`status` <> 0 then (select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id) else 0 end),0)) yh_cost\n"
                                    + "		from financial_settlements_tab a ,financial_receivable_tab b\n"
                                    + "	 where a.correlation_id = b.correlation\n"
                                    + "		 and a.ager_id = ?\n"
                                    + "		 and b.category in(11,12,13) \n"
                                    + "		 and DATE_FORMAT(b.plat_time,'%Y-%m') = ?\n"
                                    + "		 and b.secondary_type = 1\n"
                                    + "		 group by months";
                            Map<String, Object> sdMap = db.queryForMap(sql, new Object[]{id, months});

                            if (sdMap.isEmpty()) {
                                logger.error("sdMap is empty：" + id + "--" + months + "---" + sdmonths);
                                continue;

                            }

                            cost = Double.valueOf(StringHelper.get(sdMap, "cost"));
                            yh_cost = Double.valueOf(StringHelper.get(sdMap, "yh_cost"));
                            yf_cost = Double.valueOf(StringHelper.get(sdMap, "yf_cost"));
                            String plat_time = StringHelper.get(sdMap, "plat_time");
                            if (cost == yf_cost + yh_cost) {
                                logger.error("金额为0：" + id + "--" + months);
                                continue;
                            }
                            //核实已交多少水电煤押金
                            //插入一条明细信息
                            int bill_id = db.insert("INSERT INTO financial_bill" + testName + "_tab (cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                                    + "  values(?,?,?,?,?,now(),now(),'系统生成水电煤账单',?,?,2,?,?)", new Object[]{cost, yf_cost, yh_cost, months + "_水电煤账单_", cost > 0 ? "1" : "2", id, id, plat_time, "水电煤账单信息"});
                            if (bill_id <= 0) {
                                db.rollback();
                                logger.error("押金账单插入一条账单信息出现错误：" + id + "--" + bill_id);
                                continue;
                            }

                            //插入账单明细对应关系
                            sql = "insert into financial_bill" + testName + "_detail_tab (bill_id,receivable_id,payable_id)\n"
                                    + "select ?,b.id,null \n"
                                    + "  from financial_settlements_tab a ,financial_receivable_tab b\n"
                                    + " where a.correlation_id = b.correlation\n"
                                    + "	 and a.ager_id = ?\n"
                                    + "	 and b.isdelete = 1\n"
                                    + "	 and b.category in(11,12,13)\n"
                                    + "	 and b.secondary_type = 1 \n"
                                    + "	 and DATE_FORMAT(b.start_time,'%Y-%m') = ?\n";
                            if (db.update(sql, new Object[]{bill_id, id, months}) <= 0) {
                                db.rollback();
                                logger.error("租金插入插入账单明细对应关系出现错误：" + id);
                                return;
                            }

                            //插入账单支付明细表
                            sql = "INSERT INTO financial_bill" + testName + "_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost)\n"
                                    + "select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                                    + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),3,null,now(),now(),\n"
                                    + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                                    + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0)"
                                    + "   from financial_settlements_tab a ,financial_receivable_tab b\n"
                                    + " where a.correlation_id = b.correlation\n"
                                    + "	 and a.ager_id = ?\n"
                                    + "	 and b.isdelete = 1\n"
                                    + "	 and b.status <> 0\n"
                                    + "  and b.category in(11,12,13) "
                                    + "  and b.secondary_type = 1 "
                                    + "  and DATE_FORMAT(b.start_time,'%Y年%m月') = ?"
                                    + "	 union all\n"
                                    + "	 select UUID(),ifnull(b.target_type,0),ifnull(b.pay_time,b.plat_time),ifnull(b.target_user,''),b.target_bank,b.target_account,b.target_Serial,'历史账单生成',\n"
                                    + "			b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0),3,null,now(),now(),\n"
                                    + "			ifnull((select c1.mobile from yc_agreement_tab b1,yc_userinfo_tab c1 where b1.user_id = c1.id and b1.id = a.ager_id),''),a.ager_id,\n"
                                    + "?,ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 2) ,0)"
                                    + "  from financial_settlements_tab a ,financial_payable_tab b\n"
                                    + " where a.correlation_id = b.correlation\n"
                                    + "	 and a.ager_id = ?\n"
                                    + "  and b.category in(11,12,13) "
                                    + "  and b.secondary_type = 1 "
                                    + "	 and b.isdelete = 1\n"
                                    + "  and DATE_FORMAT(b.start_time,'%Y年%m月') = ?"
                                    + "	 and b.status <> 0";
                            if (db.update(sql, new Object[]{bill_id, id, months, bill_id, id, months}) != 1) {
                                db.rollback();
                                logger.error("插入账单支付明细表出现错误：" + id + "--" + bill_id);
                                return;
                            }

                            break;

                        }
                    }
                }

                //*********************生成保洁维修订单(b 保级 h维修)
                if (!"0".equals(isbj)) {
                    sql = "select a.id order_id,a.type,b.cost,b.plat_time\n"
                            + "	   from work_order a,financial_receivable_tab b \n"
                            + "	  where a.id = b.secondary\n"
                            + "		  and b.secondary_type = 2\n"
                            + "		  and a.type in('B','H')\n"
                            + "		  and b.`status` = 0 "
                            + "and b.isdelete = 1 "
                            + "and a.rental_lease_order_id = ? "
                            + "and a.sub_order_state = 'K' ";
                    List<Map<String, Object>> bjList = db.queryForList(sql, new Object[]{id});
                    for (int a = 0; a < bjList.size(); a++) {
                        cost = Double.valueOf(StringHelper.get(bjList.get(a), "cost"));
                        String type = StringHelper.get(bjList.get(a), "type");
                        order_id = StringHelper.get(bjList.get(a), "order_id");
                        String plat_time = StringHelper.get(bjList.get(a), "plat_time");

                        //插入一条明细信息
                        int bill_id = db.insert("INSERT INTO financial_bill" + testName + "_tab (cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                                + "  values(?,?,?,?,?,now(),now(),'合约" + ("B".equals(type) ? "保洁" : "维修") + "账单',?,?,?,?,?)",
                                new Object[]{cost, 0, 0,
                                    ("B".equals(type) ? "保洁" : "维修") + "账单_",
                                    cost > 0 ? "1" : "2",
                                    id, order_id,
                                    "B".equals(type) ? "4" : "5",
                                    plat_time,
                                    ("B".equals(type) ? "保洁" : "维修") + "账单信息"});
                        if (bill_id <= 0) {
                            db.rollback();
                            logger.error("保洁账单插入一条账单信息出现错误：" + id + "--" + bill_id);
                            return;
                        }

                        //插入账单明细对应关系
                        sql = "insert into financial_bill" + testName + "_detail_tab(bill_id,receivable_id,payable_id)\n"
                                + "select ?,b.id,null \n"
                                + "  from financial_receivable_tab b,work_order c\n"
                                + " where b.secondary = c.id "
                                + "	 and b.isdelete = 1\n"
                                + "    and c.id = ? "
                                + "	 and c.type in('B','H')\n"
                                + "	 and b.secondary_type = 2 \n";
                        if (db.update(sql, new Object[]{bill_id, order_id}) <= 0) {
                            db.rollback();
                            logger.error("保洁插入插入账单明细对应关系出现错误：" + id);
                            return;
                        }
                    }
                }
            }

        }

        //插入付款表中
        sql = "insert into financial_yet" + testName + "_tab(yet_cost,type,oper_date,discount_cost,fin_id,target_type,target_Serial,target_account,target_bank,remark)\n"
                + "select b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),1,ifnull(b.pay_time,b.plat_time),\n"
                + "			ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),b.id,b.target_type,b.target_Serial,b.target_account,\n"
                + "			b.target_bank,'历史账单生成'\n"
                + "   from financial_settlements_tab a ,financial_receivable_tab b\n"
                + " where a.correlation_id = b.correlation\n"
                + "	 and b.isdelete = 1\n"
                + "	 and b.status <> 0\n"
                + "	 union all\n"
                + "	 select b.cost-ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),2,ifnull(b.pay_time,b.plat_time),\n"
                + "			ifnull((select sum(b.cost) from yc_coupon_user_detail b where b.financial_id = b.id and b.type = 1) ,0),b.id,b.target_type,b.target_Serial,b.target_account,\n"
                + "			b.target_bank,'历史账单生成'\n"
                + "  from financial_settlements_tab a ,financial_payable_tab b\n"
                + " where a.correlation_id = b.correlation\n"
                + "	 and b.isdelete = 1\n"
                + "	 and b.status <> 0";
        if (db.update(sql) <= 0) {
            db.rollback();
            logger.error("插入付款表中出现错误：");
            return;
        }

        sql = "update financial_bill" + testName + "_tab a set a.cost = abs(a.cost),a.yet_cost = abs(a.yet_cost),a.discounts_cost = abs(a.discounts_cost)";
        if (db.update(sql) != 1) {
            db.rollback();
            logger.error("更新financial_bill_tab绝对值出现错误：");
            return;
        }
        logger.debug("开始生成合约账单信息-------end----------");
    }

    /**
     * 生成水电煤账单
     *
     * @param month 生成的月份
     * @param testName
     */
    public void createSDMBill(String month, String testName) {
//        String testName = "_ly";
        //取出所有没有关联账单并且是有效的数据信息
        String sql = "select a.secondary,ifnull(sum(a.cost),0) cost,c.id\n"
                + "  from financial_receivable_tab a left join financial_bill_detail_tab b on b.receivable_id = a.id \n"
                + "				left join yc_agreement_tab c on c.id = a.secondary and c.`status` in (2,12)\n"
                + "	where a.category in (11,12,13)\n"
                + "	  and a.`status` = 0 \n"
                + "		and a.isdelete = 1\n"
                + "		and b.bill_id is null"
                + "             and DATE_FORMAT(a.plat_time,'%Y-%m') = ?\n"
                + "		and c.id is not null\n"
                + "	group by a.secondary";
        List<Map<String, Object>> list = db.queryForList(sql, new Object[]{month});
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> map = list.get(i);
            String ager_id = StringHelper.get(map, "secondary");
            String cost_1 = StringHelper.get(map, "cost");

            if (Double.valueOf(cost_1) <= 0) {
                continue;
            }

            //插入财务数据表中
            int billid = db.insert("INSERT INTO financial_bill" + testName + "_tab (cost,yet_cost,discounts_cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                    + "  values(?,?,?,?,?,now(),now(),'" + month + "水电煤账单',?,?,?,?,?)",
                    new Object[]{cost_1, 0, 0, month + "水电煤账单",
                        "1", ager_id, ager_id,
                        "2",
                        DateHelper.getToday("yyyy-MM-dd HH:mm:ss"),
                        "水电煤账单信息"});
            if (billid <= 0) {
                logger.error("保洁账单插入一条账单信息出现错误：" + ager_id + "--" + billid);
                db.rollback();
                return;
            }

            sql = "INSERT INTO financial_bill_detail_tab (bill_id,receivable_id) select ?, a.id\n"
                    + "  from financial_receivable_tab a \n"
                    + " where a.secondary = ? \n"
                    + "  and DATE_FORMAT(a.plat_time,'%Y-%m') = ? "
                    + "  and a.category in (11,12,13) "
                    + "and a.isdelete = 1";
            //进行记录到对应明细表中
            int exc = db.update(sql, new Object[]{billid, ager_id,month});
            if (exc == 0) {
                logger.error("保洁账单插入一条账单信息出现错误10：" + ager_id + "--" + billid);
                db.rollback();
                return ;
            }

            //检查是否存在代缴费信息，如果存在代缴费已交款的情况就进行核实抵扣处理
            sql = "select ifnull(sum((case when a.`status` <> 0 and a.yet_cost = 0 then a.cost else (select sum(b.yet_cost) from financial_yet_tab b where b.fin_id = a.id ) end)),0) cost\n"
                    + "  from financial_receivable_tab a \n"
                    + " where a.secondary = ? \n"
                    + "  and a.category = 4 "
                    + "and a.isdelete = 1";
            Double djfCost = Double.valueOf(db.queryForString(sql, new Object[]{ager_id}));
            if (djfCost > 0d) {//存在已交的代缴费信息

                //检查使用了多少代缴费信息
                sql = "select ifnull(sum(a.discounts_cost),0) from financial_bill_pay_tab a where a.remark = '代缴费抵扣' and a.ager_id = ? and a.state = 3";
                Double discost = Double.valueOf(db.queryForString(sql, new Object[]{ager_id}));
                Double syDjfCost = AmountUtil.subtract(djfCost, discost, 2);//剩余代缴费
                if (syDjfCost > 0) {//还存在剩余代缴费，进行平摊到下面金额中
                    Double kdkCost = 0D;//可抵扣金额
                    if (syDjfCost > Double.valueOf(cost_1)) { //直接全额抵扣
                        kdkCost = Double.valueOf(cost_1);
                    } else {
                        kdkCost = syDjfCost;
                    }

                    //进行抵扣动作
                    Double dis_cost = kdkCost;
                    String bill_id = billid + "";
                    if (!"".equals(bill_id)) {
                        String pay_time = DateHelper.getToday("yyyy-MM-dd HH:mm:ss");
                        //做一条代缴费抵扣水电煤数据信息
                        sql = "INSERT INTO financial_bill_pay_tab (id,type,pay_time,name,bank_name,bank_account,tro_no,remark,cost,state,oper_id,oper_date,create_date,mobile,ager_id,bill_id,discounts_cost,is_split)values"
                                + "(?,?,DATE_FORMAT(?,'%Y-%m-%d %T'),?,?,?,?,?,?,3,?,now(),now(),?,?,?,?,1)";
                        String id = UUID.randomUUID().toString();
                        if (db.update(sql, new Object[]{id, "8", pay_time, "代缴费抵扣", "无", "无", "", "代缴费抵扣", 0, "", "", ager_id, bill_id, dis_cost}) != 1) {
                            logger.error("保洁账单插入一条账单信息出现错误1：" + ager_id + "--" + billid);
                            db.rollback();
                            return;
                        } else {
                            //更新对应的账单表信息
                            //进行核减金额
                            //取出所有未支付明细信息
                            sql = "select b.id,b.cost,ifnull(sum(c.yet_cost),0) yet_cost,ifnull(sum(c.discount_cost),0) discount_cost\n"
                                    + "  from financial_bill_detail_tab a ,financial_receivable_tab b left join financial_yet_tab c on b.id = c.fin_id and c.type = 1\n"
                                    + " where a.receivable_id = b.id \n"
                                    + "   and b.isdelete = 1 \n"
                                    + "   and a.bill_id = ?\n"
                                    + "   and b.`status` = 0\n"
                                    + " group by b.id,b.cost order by b.id";
                            logger.debug("取出所有未支付明细信息:" + str.getSql(sql, new Object[]{bill_id}));
                            List<Map<String, Object>> recList = db.queryForList(sql, new Object[]{bill_id});
                            logger.debug("取出所有未支付明细信息:" + recList);
                            Double cost = 0D;
                            Double discounts_cost = dis_cost;
                            //看此收入明细对应金额
                            if (!recList.isEmpty()) {
                                String pay_type = "8";
                                String tro_no = "";
                                String bank_account = "";
                                String bank_name = "";
                                String name = "";
                                for (Map<String, Object> map1 : recList) {
                                    if (cost == 0D && discounts_cost == 0D) {
                                        continue;
                                    }
                                    Double fin_cost = Double.valueOf(StringHelper.get(map1, "cost"));
                                    Double fin_yet_cost = Double.valueOf(StringHelper.get(map1, "yet_cost"));
                                    Double fin_discount_cost = Double.valueOf(StringHelper.get(map1, "discount_cost"));
                                    String fin_id = StringHelper.get(map1, "id");

                                    //如果支付金额为负值直接跳过
                                    if (fin_cost < 0) {
                                        continue;
                                    }

                                    //先看财务明细已交的总金额
                                    Double yj_fin_cost = AmountUtil.add(fin_yet_cost, fin_discount_cost, 2);

                                    //剩余多少没有交
                                    Double xc_fin_cost = AmountUtil.subtract(fin_cost, yj_fin_cost, 2);

                                    Double cost_ = AmountUtil.subtract(cost, xc_fin_cost, 2);

                                    logger.debug("yj_fin_cost:" + yj_fin_cost + "-xc_fin_cost:" + xc_fin_cost + "|cost:" + cost);

                                    if (cost_ >= 0d) {//说明账单中的金额足够交当前明细值
                                        sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                        logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                        int yet_id = db.insert(sql, new Object[]{fin_id, xc_fin_cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                        if (yet_id <= 0) {
                                            logger.error("保洁账单插入一条账单信息出现错误2：" + ager_id + "--" + billid);
                                            db.rollback();
                                            return;
                                        }
                                        //同步到金蝶那边处理
                                        db.insert("insert into yc_notiy_info(notiy_type,resource_id)values(?,?)", new Object[]{8, yet_id});
                                        xc_fin_cost = AmountUtil.subtract(xc_fin_cost, xc_fin_cost, 2);
                                        cost = cost_;
                                    } else {//小于0不够，需要拿优惠金额来做抵消
                                        sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                        logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                        int yet_id = db.insert(sql, new Object[]{fin_id, cost, 0, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                        if (yet_id <= 0) {
                                            logger.error("保洁账单插入一条账单信息出现错误3：" + ager_id + "--" + billid);
                                            db.rollback();
                                            return;
                                        }
                                        //同步到金蝶那边处理
                                        db.insert("insert into yc_notiy_info(notiy_type,resource_id)values(?,?)", new Object[]{8, yet_id});
                                        xc_fin_cost = AmountUtil.subtract(xc_fin_cost, cost, 2);
                                        cost = 0D;
                                    }
                                    if (discounts_cost > 0D && xc_fin_cost > 0D) {
                                        Double discounts_cost_ = AmountUtil.subtract(discounts_cost, xc_fin_cost, 2);
                                        if (discounts_cost_ >= 0d) {//说明账单中的优惠金额足够抵消,
                                            sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                            logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                            int yet_id = db.insert(sql, new Object[]{fin_id, 0, xc_fin_cost, pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                            if (yet_id <= 0) {
                                                logger.error("保洁账单插入一条账单信息出现错误4：" + ager_id + "--" + billid);
                                                db.rollback();
                                                return;
                                            }
                                            //同步到金蝶那边处理
                                            db.insert("insert into yc_notiy_info(notiy_type,resource_id)values(?,?)", new Object[]{8, yet_id});
                                            discounts_cost = discounts_cost_;
                                        } else {//说明不够抵消金额
                                            sql = "insert into financial_yet_tab(fin_id,yet_cost,type,oper_date,discount_cost,target_type,target_Serial,target_account,target_bank,username,pay_id,pay_time,remark)values(?,?,1,now(),?,?,?,?,?,?,?,?,'代缴费抵扣')";
                                            logger.debug("插入财务支付明细：" + str.getSql(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time}));
                                            int yet_id = db.insert(sql, new Object[]{fin_id, 0, Math.abs(discounts_cost), pay_type, tro_no, bank_account, bank_name, name, id, pay_time});
                                            if (yet_id <= 0) {
                                                logger.error("保洁账单插入一条账单信息出现错误5：" + ager_id + "--" + billid);
                                                db.rollback();
                                                return;
                                            }
                                            discounts_cost = 0d;
                                            //同步到金蝶那边处理
                                            db.insert("insert into yc_notiy_info(notiy_type,resource_id)values(?,?)", new Object[]{8, yet_id});
                                        }
                                    }
                                    sql = "update financial_receivable_tab a set a.yet_cost = ifnull((select sum(ifnull(b.yet_cost,0)+ifnull(b.discount_cost,0)) from financial_yet_tab b where b.fin_id = a.id and b.type = 1),0) where a.id = ?";
                                    if (db.update(sql, new Object[]{fin_id}) != 1) {
                                        logger.error("保洁账单插入一条账单信息出现错误6：" + ager_id + "--" + billid);
                                        db.rollback();
                                        return;
                                    }
                                    sql = "update financial_receivable_tab a set a.status = 1 where a.id = ? and a.cost = a.yet_cost ";
                                    if (db.update(sql, new Object[]{fin_id}) != 1) {
                                        db.rollback();
                                        logger.error("保洁账单插入一条账单信息出现错误7：" + ager_id + "--" + billid);
                                        return;
                                    }
                                }
                            }
                        }
                    }

                    //更新合约对应的金额
                    sql = "update financial_bill_tab a set a.discounts_cost = a.discounts_cost +? where a.id = ?";
                    if (db.update(sql, new Object[]{dis_cost, bill_id}) <= 0) {
                        logger.error("保洁账单插入一条账单信息出现错误8：" + ager_id + "--" + billid);
                        db.rollback();
                        return;
                    }
                    
                    //更新对应状态
                    sql = "update financial_bill_tab a set a.state = 3 where a.cost <= a.yet_cost + a.discounts_cost and a.id = ? ";
                    if (db.update(sql, new Object[]{bill_id}) <= 0) {
                        logger.error("保洁账单插入一条账单信息出现错误9：" + ager_id + "--" + billid);
                        db.rollback();
                        return;
                    }

                }

            }

        }

    }

}
