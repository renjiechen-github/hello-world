package pccom.web.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.room1000.workorder.dto.WorkOrderDto;
import com.ycqwj.ycqwjApi.request.PlushRequest;
import com.ycqwj.ycqwjApi.request.WeiXinRequest;
import com.ycqwj.ycqwjApi.request.bean.plush.AppBillBean;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pccom.common.util.AmountUtil;
import pccom.common.util.Constants;
import pccom.common.util.DateHelper;
import pccom.common.util.StringHelper;
import pccom.web.flow.base.TaskException;
import pccom.web.job.jd.JdTask;

/**
 * 财务对外接口
 *
 * @author 雷杨
 *
 */
@Service("financial")
public class Financial extends Base {

    @Autowired
    public HouseInterfaces houseInterfaces;

    /**
     * 收房合约接口
     *
     * @author 雷杨
     * @param params{ settlements_name//财务类目名称 收房 对应 房源名称 type:结算类型 remarks：备注
     * 财务项目备注 cost：321321|121|21321|3213| months:总月份数 play_type:支付类型：0全额付
     * 1月付3季付6半年付12年付 其他为对应月份 plat_time:开始计划付款时间(格式：2015-02-02) target_bank：目标银行
     * target_user：目标用户(用户) target_type:发生类型(1:银卡转帐,3:网络平台,5:其它) operid:操作人id
     * agreement_id:合约id free_period:免租期 category：生成明细对应的类目id
     * financial_category_tab中id }
     * @return -4 财务项目已存在或房源已生成了项目名称，可能重复操作了 -5 新增财务项目语句执行错误 -3 金额填写错误，存在空值 1 成功
     * @example params1.put("type", "1"); params1.put("settlements_name",
     * "111"); params1.put("remarks", "1"); params1.put("cost", "11|21|");
     * params1.put("months", "16"); params1.put("play_type", "5");
     * params1.put("plat_time", "2015-02-02"); params1.put("target_bank", "1");
     * params1.put("target_user", "1"); params1.put("target_type", "1");
     * params1.put("operid", "1"); params1.put("agreement_id", "1");
     * params1.put("category", "1"); BatchSql batch = new BatchSql();
     * logger.debug(financial.takeHouse(params1, batch));
     * db.doInTransaction(batch);
     */
    public int takeHouse(Map params, Object obj) {
        //检查是否存在相同名称的财务项目
        String settlements_name = str.get(params, "settlements_name");//财务项目名称
        String type = str.get(params, "type");//财务项目名称
        String remarks = str.get(params, "remarks");//财务项目名称
        String cost = str.get(params, "cost");//321321|121|21321|3213|
        String months = str.get(params, "months");//总月份数
        String plat_time = str.get(params, "plat_time");//开始计划付款时间
        String target_bank = str.get(params, "target_bank");//目标银行
        String target_user = str.get(params, "target_user");//目标用户(用户)
        String target_type = str.get(params, "target_type");//发生类型(1:银卡转帐,3:网络平台,5:其它)
        String operid = str.get(params, "operid");//操作人id
        String agreement_id = str.get(params, "agreement_id");//合约id
        String category = str.get(params, "category");//生成明细对应的类目id financial_category_tab中id
        String pay_type = str.get(params, "pay_type");//支付类型：0全额付 1月付3季付6半年付12年付 其他为对应月份
        String free_period = str.get(params, "free_period");//免租期  1|0|1  30

//		if(free_period.indexOf("|") < 0){
//			free_period = "".equals(free_period)?"0":free_period;
//		}else{
//			free_period = free_period
//		}
        //检查是否存在相同的财务项目名称，如果存在不予操作
        String correlation = queryString(obj, getSql("financial.payable.getcategory"), new Object[]{agreement_id, 0});
        logger.debug("correlation:" + correlation);
        if (!"".equals(correlation)) {
            return -4;
        }

        //查询出对应的房源名称信息
        String sql = getSql("basehouse.agreement.getSBcode");
        String sbname = queryString(obj, sql, new Object[]{agreement_id});
        //生成财务项目信息
        int settlements_id = insert(obj, getSql("financial.project.insert"), new Object[]{type, sbname, 0, 0, remarks, 0, agreement_id});
        if (settlements_id == -1) {//语句执行错误
            return -5;
        }

        //更新财务编号
        int exc1 = update(obj, getSql("financial.project.updatecode"), new Object[]{});
        if (exc1 != 1) {
            return -5;
        }

        //进行新增支出明细
        //根据金额来进行拆分总插入次数
        String[] costs = cost.split("\\|");
        String[] free_periods = new String[costs.length]; //每年免租期
        if (free_period.indexOf("|") < 0) {
            free_periods[0] = "".equals(free_period) ? "0" : free_period;
            for (int i = 1; i < costs.length; i++) {
                free_periods[i] = "0";
            }
        } else {
            free_periods = free_period.split("|");
        }

        //根据年份与月份计算出循环次数
        int monthnum = Integer.valueOf(months);//总月份数量
        pay_type = "0".equals(pay_type) ? months : pay_type;
        int cnt = monthnum / Integer.valueOf(pay_type) + (monthnum % Integer.valueOf(pay_type) == 0 ? 0 : 1);//循环次数

        String startTime = plat_time;
        int years = 0;//记录年份
        int nowMonth = 0;//记录当前达到的月份
        logger.debug("cnt:" + cnt + "---" + startTime + "---" + pay_type);
        //第一个日期需要减去免租期
        for (int i = 0; i < cnt; i++) {//根据需要插入的条数进行循环
            if (nowMonth == 0) {
                startTime = DateHelper.checkOption(0 - Integer.valueOf(free_periods[years]), startTime);
                logger.debug("startTime:" + startTime);
            }
            //强字符串日期改成data
            Date date = DateHelper.getStringDate(startTime, "yyyy-MM-dd");
            if (i == 0) {//第一次需要有生效日期算起
                date = DateHelper.getStringDate(plat_time, "yyyy-MM-dd");
            }
            String enddate = DateHelper.addMonthDate(date, Integer.valueOf(pay_type));

            //可能存在情况是 安装当前支付月份分解出的日期落在两个不同金额之内，这样就需要进行分别插入两条记录信息
            nowMonth += Integer.valueOf(pay_type);

            String cost_ = costs[years];
            String name = startTime + "至" + enddate + "房租";
            int exc = insert(obj, getSql("financial.payable.insert"), new Object[]{sbname, settlements_id, 0, agreement_id, category, Integer.valueOf(cost_) * Integer.valueOf(pay_type), startTime, target_bank, target_user, target_type, operid, name, "", startTime, enddate});
            if (exc <= 0) {
                return 0;
            }
            startTime = enddate;
            if (nowMonth >= 12) {
                years++;
                nowMonth = 0;
            }
        }

        //执行成功，添加到同步列表中
        JdTask.addPool("1", agreement_id);
        return 1;
    }

    /**
     * 撤销收房合同接口
     *
     * @author 雷杨
     * @param params{ agreement_id:合约id }
     * @return -1 未查询到财务项目信息 1 成功
     */
    public int repealHouse(Map<String, String> params, Object obj) {
        String agreement_id = str.get(params, "agreement_id");//合同id

        String correlation = queryString(obj, getSql("financial.payable.getcategory"), new Object[]{agreement_id, 0});
        logger.debug("correlation:" + correlation);
        if ("".equals(correlation)) {
            return -1;
        }

        int exc = update(obj, getSql("financial.project.delete"), new Object[]{correlation});
        //调用此接口后需要进行全部置为无效状态
        //update(obj,getSql("financial.payable.repeal"),new Object[]{agreement_id,0});
        if (exc == 0) {
            return 0;
        }
        //删除对应的合约信息
        exc = update(obj, getSql("financial.payable.deletePay"), new Object[]{agreement_id, 0});
        //调用此接口后需要进行全部置为无效状态
        //update(obj,getSql("financial.payable.repeal"),new Object[]{agreement_id,0});
        if (exc == 0) {
            return 0;
        }
        //删除对应的合约信息
        exc = update(obj, getSql("financial.receivable.deletePay"), new Object[]{correlation, 0});
        //调用此接口后需要进行全部置为无效状态
        //update(obj,getSql("financial.payable.repeal"),new Object[]{agreement_id,0});
        if (exc == 0) {
            return 0;
        }
        //执行成功，添加到同步列表中
        JdTask.addPool("3", agreement_id);
        return 1;
    }

    /**
     * 出租合同
     *
     * @author 雷杨
     * @param params{ house_id:房源id 收房的房源id cost：321321|121|21321|3213|
     * months:总月份数 play_type:支付类型：0全额付 1月付3季付6半年付12年付 其他为对应月份
     * plat_time:开始计划付款时间(格式：2015-02-02) operid:操作人id agreement_id:合约id 出租合同id
     * free_period：免租期 category：生成明细对应的类目id financial_category_tab中id }
     * @return -1 未查询到房源合约信息 -2 未查询到财务项目 1 成功
     */
    public int rentHouse(Map<String, String> params, Object obj) {
//		String house_id = str.get(params, "house_id");//房源id 收房的房源id
        String cost = str.get(params, "cost_a");//321321|121|21321|3213|
        String months = str.get(params, "months");//总月份数
        String plat_time = str.get(params, "plat_time");//开始计划付款时间
        String operid = str.get(params, "operid");//操作人id
        String agreement_id = str.get(params, "agreement_id");//合约id
        String category = str.get(params, "category");//生成明细对应的类目id financial_category_tab中id
        String play_type = str.get(params, "pay_type");//支付类型：0全额付 1月付3季付6半年付12年付 其他为对应月份
        String agreementId = str.get(params, "father_id");//房源id对应的合约id
        String free_period = str.get(params, "free_period");//免租期
        free_period = "".equals(free_period) ? "0" : free_period;
        logger.debug("params:" + params);

        //获取月份信息
        String sql1 = "select period_diff(DATE_FORMAT(DATE_ADD(a.end_time,INTERVAL 1 day),'%Y%m'),DATE_FORMAT(a.begin_time,'%Y%m')) from yc_agreement_tab a where a.id = ?";
        String month = db.queryForString(sql1, new Object[]{agreement_id});
        if ("".equals(month) || "0".equals(month)) {
            months = "1";
        } else {
            months = month;
        }

        //根据房源id查询出对应的财务项目
//		String agreementId = queryString(obj, getSql("agreement.getAgreementId"), new Object[]{house_id,1});
        if ("".equals(agreementId)) {
            logger.debug("agreementId：" + agreementId);
            return -1;
        }

        String yj = db.queryForString("select a.rent_deposit from yc_agreement_tab a where a.id =?", new Object[]{agreement_id});

        if ("".equals(yj)) {
            logger.debug("押金为空：" + agreementId);
            return -1;
        }

        String isJrf = "0";//是否是金融付款 1是金融代付 0不是
        if ("15".equals(play_type)) {
            isJrf = "1";
            play_type = months;
        }

        //查询出对应的房源名称信息
        String sql = getSql("basehouse.agreement.getCzSBcode");
        String sbname = queryString(obj, sql, new Object[]{agreement_id});

        String supper_settlements_id = queryString(obj, getSql("financial.payable.getcategory"), new Object[]{agreementId, 0});
        logger.debug("supper_settlements_id:" + supper_settlements_id);
        if ("".equals(supper_settlements_id)) {

            return -1;
        }

        //查询出出租房名称
        String settlements_name = queryString(obj, getSql("basehouse.agreement.getHousName"), new Object[]{agreement_id});
        logger.debug("查询出出租房名称:" + settlements_name);
        if ("".equals(settlements_name)) {
            return -1;
        }

        //生成财务项目信息
        int settlements_id = insert(obj, getSql("financial.project.insert"), new Object[]{2, sbname, 0, 0, settlements_name, supper_settlements_id, agreement_id});
        if (settlements_id == -1) {//语句执行错误
            return -2;
        }

        //更新财务编号
        int exc1 = update(obj, getSql("financial.project.updatecode"), new Object[]{});
        if (exc1 != 1) {
            return -5;
        }

        //根据合约查询出对应的财务项目
//		String correlation = queryString(obj, getSql("financial.payable.getcategory"), new Object[]{agreementId,0});
//		if("".equals(correlation)){
//			return -2;
//		}
        //进行新增收入明细
        //根据金额来进行拆分总插入次数
        //根据年份与月份计算出循环次数
        int monthnum = Integer.valueOf(months);//总月份数量
        play_type = "0".equals(play_type) ? months : play_type;
        logger.debug(play_type + "--" + months);
        int cnt = monthnum / Integer.valueOf(play_type) + (monthnum % Integer.valueOf(play_type) == 0 ? 0 : 1);//循环次数

        if ("1".equals(isJrf)) {
            cnt = monthnum;
            play_type = "1";
        }

        String startTime = plat_time;
        int years = 0;//记录年份
        int nowMonth = 0;//记录当前达到的月份
        logger.debug("cnt:" + cnt);
        startTime = DateHelper.checkOption(0 - Integer.valueOf(free_period), startTime);
        logger.debug("startTime:" + startTime);

        //账单中需要使用到的财务编号
        String finIds = "";

        int exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, 5, Float.valueOf(yj), startTime, "", "", null, operid, "押金", "", startTime, startTime});
        if (exc == -1) {
            return 0;
        }
        //添加押金账单
        exc = createBill(exc + "", obj, startTime.substring(0, 7) + "月押金账单", "1", agreement_id, startTime, "新建押金账单", "7", "1");
        if (exc == 0) {
            return 0;
        }

        //插入代缴费信息
        sql = "SELECT a.serviceMonery,a.propertyMonery,a.deposit FROM yc_agreement_tab a WHERE a.id = ?";
        Map<String, Object> map = db.queryForMap(sql, new Object[]{agreement_id});
        String serviceMonery = StringHelper.get(map, "serviceMonery");
        String propertyMonery = StringHelper.get(map, "propertyMonery");
        String deposit = StringHelper.get(map, "deposit");//水电煤押金

        logger.debug("查询出代缴费信息map:" + map);

        if (!isNumber(serviceMonery) && !"".equals(serviceMonery)) {
            serviceMonery = "0";
        }

        if (!isNumber(propertyMonery) && !"".equals(propertyMonery)) {
            propertyMonery = "0";
        }

        if (!isNumber(deposit) && !"".equals(deposit)) {
            deposit = "0";
        }

        /**
         * 金融月付第一个月
         */
        String jryf_one = "";
        /**
         * 金融月付第二个月
         */
        String jryf_two = "";
        String jryf_two_zf = "";
        if ("1".equals(isJrf)) {//金融代付生成1个月房租
            Date date = DateHelper.getStringDate(startTime, "yyyy-MM-dd");
            String enddate = DateHelper.addMonthDate(date, Integer.valueOf(1));
            exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, category, Float.valueOf(cost), startTime, "", "", null, operid, startTime + "至" + enddate + "房租", "", startTime, enddate});
            if (exc == 0) {
                return 0;
            }
            jryf_one = exc + ",";

            String statetime = enddate;
            Date date1 = DateHelper.getStringDate(statetime, "yyyy-MM-dd");
            String enddate1 = DateHelper.addMonthDate(date1, monthnum - 1);
            //生成金融代付类目
            exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, 14, Float.valueOf(cost) * (monthnum - 1), statetime, "", "", null, operid, statetime + "至" + enddate1 + "房租", "", statetime, enddate1});
            if (exc == 0) {
                return 0;
            }
            jryf_two = exc + ",";
            play_type = "1";
        }

        for (int i = 0; i < cnt; i++) {//根据需要插入的条数进行循环
            //强字符串日期改成data
            Date date = DateHelper.getStringDate(startTime, "yyyy-MM-dd");
            if (i == 0) {//第一次需要有生效日期算起
                date = DateHelper.getStringDate(plat_time, "yyyy-MM-dd");
            }
            String enddate = DateHelper.addMonthDate(date, Integer.valueOf(play_type));
            //可能存在情况是 安装当前支付月份分解出的日期落在两个不同金额之内，这样就需要进行分别插入两条记录信息
            nowMonth += Integer.valueOf(play_type);
            String cost_ = cost;
            if (i == cnt - 1) {
                enddate = DateHelper.checkOption(-1, enddate);
            }
            String name = startTime + "至" + enddate;

            if (!"1".equals(isJrf)) {//金融代付不生成房租，
                float costfz = Float.valueOf(cost_) * Integer.valueOf(play_type);
                if (costfz >= 10000) {
                    int cost_cz = 8000;
                    int cnt1 = (int) (costfz % cost_cz > 0 ? (costfz / cost_cz + 1) : costfz / cost_cz);
                    for (int j = 0; j < cnt1; j++) {
                        if (j == cnt1 - 1) {
                            exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, category, costfz - cost_cz * j, startTime, "", "", null, operid, name + "房租" + j, "", startTime, enddate});
                            if (exc == -1) {
                                return 0;
                            }
                            finIds += exc + ",";
                        } else {
                            exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, category, cost_cz, startTime, "", "", null, operid, name + "房租" + j, "", startTime, enddate});
                            if (exc == -1) {
                                return 0;
                            }
                            finIds += exc + ",";
                        }
                    }
                } else {
                    exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, category, costfz, startTime, "", "", null, operid, name + "房租", "", startTime, enddate});
                    if (exc == -1) {
                        return 0;
                    }
                    finIds += exc + ",";
                }
            }

            logger.debug("是否需要插入物业费：" + propertyMonery + "----" + (!"".equals(propertyMonery.trim()) && !"0".equals(propertyMonery.trim()) && !"0.0".equals(propertyMonery.trim())));
            if (!"".equals(propertyMonery.trim()) && !"0".equals(propertyMonery.trim()) && !"0.0".equals(propertyMonery.trim())) {//存在物业费
                if (Float.valueOf(propertyMonery) * Integer.valueOf(play_type) != 0) {
                    exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, 2, Float.valueOf(propertyMonery) * Integer.valueOf(play_type), startTime, "", "", null, operid, name + "物业费", "", startTime, enddate});
                    if (exc == -1) {
                        return 0;
                    }
                    finIds += exc + ",";
                    if (i == 0 && "1".equals(isJrf)) {
                        jryf_one += exc + ",";
                    } else if ("1".equals(isJrf)) {
                        jryf_two_zf += exc + ",";
                    }
                }
            }

            logger.debug("是否需要插入服务费：" + serviceMonery + "----" + (!"".equals(serviceMonery.trim()) && !"0".equals(serviceMonery.trim()) && !"0.0".equals(serviceMonery.trim())));
            if (!"".equals(serviceMonery.trim()) && !"0".equals(serviceMonery.trim()) && !"0.0".equals(serviceMonery.trim())) {//存在服务费
                if (Float.valueOf(serviceMonery) * Integer.valueOf(play_type) != 0) {
                    exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, 6, Float.valueOf(serviceMonery) * Integer.valueOf(play_type), startTime, "", "", null, operid, name + "服务费", "", startTime, enddate});
                    if (exc == -1) {
                        return 0;
                    }
                    finIds += exc + ",";
                    if (i == 0 && "1".equals(isJrf)) {
                        jryf_one += exc + ",";
                    } else if ("1".equals(isJrf)) {
                        jryf_two_zf += exc + ",";
                    }
                }
            }

            if (!"".equals(deposit.trim()) && !"0".equals(deposit.trim()) && !"0.0".equals(deposit.trim())) {//水电煤押金
                if (Float.valueOf(deposit) * Integer.valueOf(play_type) != 0) {
                    exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{sbname, settlements_id, 1, agreement_id, 4, Float.valueOf(deposit) * Integer.valueOf(play_type), startTime, "", "", null, operid, "每月水电煤预交费", "", startTime, enddate});
                    if (exc == -1) {
                        return 0;
                    }
                    finIds += exc + ",";
                    if (i == 0 && "1".equals(isJrf)) {
                        jryf_one += exc + ",";
                    } else if ("1".equals(isJrf)) {
                        jryf_two_zf += exc + ",";
                    }
                }
            }

            //进行拆分租房账单
            if ("1".equals(isJrf)) {//金融月付需要生成两个账单
                //先合并第一个月的租金信息  只有第一个循环进入
                if (i == 0) {
                    exc = createBill(jryf_one, obj, startTime.substring(0, 7) + "月租金账单", "1", agreement_id, startTime, "新建账单", "1", "1");
                    if (exc == 0) {
                        return 0;
                    }
                    exc = createBill(jryf_two, obj, "365金融代付账单", "1", agreement_id, startTime, "新建账单", "8", "1");
                    if (exc == 0) {
                        return 0;
                    }
                } else {
                    exc = createBill(jryf_two_zf, obj, startTime.substring(0, 7) + "月租金账单,不包含租金", "1", agreement_id, startTime, "新建账单", "1", "1");
                    if (exc == 0) {
                        return 0;
                    }
                    jryf_two_zf = "";
                }
            } else {
                exc = createBill(finIds, obj, startTime.substring(0, 7) + "月租金账单", "1", agreement_id, startTime, "新建账单", "1", "1");
                if (exc == 0) {
                    return 0;
                }
                finIds = "";
            }

            startTime = enddate;
            if (nowMonth >= 12) {
                years++;
                nowMonth = 0;
            }
        }

        //执行成功，添加到同步列表中
        JdTask.addPool("1", agreement_id);
        return 1;
    }

    //金额验证  
    public boolean isNumber(String str) {
        Pattern pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式  
        Matcher match = pattern.matcher(str);
        if (match.matches() == false) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 创建账单
     *
     * @param finIds
     * @param obj
     * @param name
     * @param type
     * @param cost
     * @param ager_id
     * @param plan_time
     * @return
     */
    public int createBill(String finIds, Object obj, String name, String type, String ager_id, String plan_time, String remark, String resource_type, String state) {
        int exc = 0;
        int bill_id = insert(obj, "INSERT INTO financial_bill_tab (name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark,state) "
                + "  values(?,?,now(),now(),'合约生成新建账单',?,?,?,DATE_FORMAT(?,'%Y-%m-%d %T'),?,?)", new Object[]{name, type, ager_id, ager_id, resource_type, plan_time, remark, state});
        if (bill_id == -1) {
            return 0;
        }
        logger.debug("finIds:" + finIds);
        String[] fin_id = finIds.split(",");
        //添加明细信息
        for (int h = 0; h < fin_id.length; h++) {
            if (!"".equals(fin_id[h])) {
                exc = update(obj, "INSERT INTO financial_bill_detail_tab (bill_id,receivable_id)values(?,?)", new Object[]{bill_id, fin_id[h]});
                if (exc == 0) {
                    return 0;
                }
            }
        }
        //更新金额
        String cost_bill = queryString(obj, "select sum(b.cost) FROM financial_bill_detail_tab a,financial_receivable_tab b where a.receivable_id = b.id and a.bill_id = ? ", new Object[]{bill_id});
        return update(obj, "UPDATE financial_bill_tab a set a.cost = ? where a.id = ?", new Object[]{cost_bill, bill_id});
    }

    /**
     * 撤销出租接口
     *
     * @author 雷杨
     * @param params{ agreement_id:合约id }
     * @return -1 未查询到财务项目信息 1 成功
     */
    public int repealRentHouse(Map<String, String> params) {
        return repealRentHouse(params, null);
    }

    /**
     * 撤销出租接口
     *
     * @author 雷杨
     * @param params{ agreement_id:合约id }
     * @return -1 未查询到财务项目信息 那没办法 1 成功
     */
    public int repealRentHouse(Map<String, String> params, Object obj) {
        String agreement_id = str.get(params, "agreement_id");//合同id
        String correlation = queryString(obj, getSql("financial.receivable.getcategory"), new Object[]{agreement_id, 1});
        logger.debug("correlation:" + correlation);
        if ("".equals(correlation)) {
            return -1;
        }
        int exc = update(obj, getSql("financial.project.delete"), new Object[]{correlation});
        if (exc == 0) {
            return 0;
        }
        //删除对应的合约信息
        exc = update(obj, getSql("financial.payable.deletePayOrder"), new Object[]{correlation});
        if (exc != 1) {
            return -1;
        }
        //调用此接口后需要进行全部置为无效状态
        update(obj, getSql("financial.receivable.deleteReceivableOrder"), new Object[]{correlation});
        if (exc == 0) {
            return -1;
        }
        //执行成功，添加到同步列表中
        JdTask.addPool("3", agreement_id);
        return 1;
    }

    /**
     * 财务审批通过调用接口
     *
     * @param params{ agreement_id:合约id }
     * @return -1 未查询到财务项目信息 1 成功
     */
    public int repealOrderRentHouse(Map<String, String> params) {
        return repealOrderRentHouse(params, null);
    }

    public int repealOrderRentHouse(Map<String, String> params, Object obj) {
        String agreement_id = str.get(params, "agreement_id");//合同id
        String correlation = queryString(obj, getSql("financial.receivable.getcategory"), new Object[]{agreement_id, 1});
        logger.debug("correlation:" + correlation);
        if ("".equals(correlation)) {
            return -1;
        }

        return 1;
    }

    /**
     * 服务订单
     *
     * @param params{ agreement_id:合约id cost:金额 operid:当前操作人 order_id:订单id }
     * @return -1未查询到合约信息 -2 未查询到房间信息 0操作失败 >1
     *
     */
    @Transactional
    public int orderRent(Map<String, String> params) {
        return orderRent(params, null);
    }

    /**
     * 服务订单
     *
     * @param params{ agreement_id:合约id cost:金额 operid:当前操作人 order_id:订单id }
     * @param obj
     * @return -1未查询到合约信息 -2 未查询到房间信息 0操作失败 >1
     *
     */
    public int orderRent(Map<String, String> params, Object obj) {
        logger.debug("插入财务开始-11----------------------------------------");
        String agreementId = str.get(params, "agreement_id");
        String cost = str.get(params, "cost");
        String operid = str.get(params, "operid");
        String order_id = str.get(params, "order_id");

        String settlements_id = queryString(obj, getSql("financial.receivable.getcategory"), new Object[]{agreementId, 1});
        if ("".equals(settlements_id)) {
            logger.debug("没有查到settlements_id-----------------------------------------");
            return -1;
        }
        //查询出出租房名称
        String settlements_name = queryString(obj, getSql("basehouse.agreement.getHousName"), new Object[]{agreementId});

        if ("".equals(settlements_name)) {
            logger.debug("没有查到settlements_name-----------------------------------------");
            return -2;
        }
        String startTime = DateHelper.getToday("YYYY-MM-dd HH:mm:ss");
        settlements_name += "_服务费";
        int exc = insert(obj, getSql("financial.receivable.insert"), new Object[]{settlements_name, settlements_id, 2, order_id, 6, Float.parseFloat(cost), startTime, "", "", null, operid, "", "", startTime, startTime});
        if (exc == 0) {
            logger.debug("插入财务报错-----------------------------------------");
            return 0;
        }

        //根据合约订单ＩＤ查询是否是对应的 B保洁订单 H维修订单
        String type = queryString(obj, "select a.type FROM work_order a where a.id = ?", new Object[]{order_id});

        int bill_id = insert(obj, "INSERT INTO financial_bill_tab (name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                + "  values(?,?,now(),now(),'合约" + ("B".equals(type) ? "保洁" : "维修") + "账单',?,?,?,now(),?)", new Object[]{("B".equals(type) ? "保洁" : "维修") + "账单", "1", agreementId, order_id, "B".equals(type) ? "4" : "5", ("B".equals(type) ? "保洁" : "维修") + "账单信息"});
        if (bill_id == -1) {
            return 0;
        }

        exc = update(obj, "INSERT INTO financial_bill_detail_tab (bill_id,receivable_id)values(?,?)", new Object[]{bill_id, exc});
        if (exc == 0) {
            return 0;
        }

        //更新总价格信息
        String sql = "UPDATE financial_bill_tab a set a.cost = ?  "
                + "  where a.id = ?";
        exc = update(obj, sql, new Object[]{cost, bill_id});
        if (exc == 0) {
            return 0;
        }

        logger.debug("插入财务结束-----------------------------------------");
        return 1;
    }

    /**
     * 服务订单
     *
     * @param params{ agreement_id:合约id cost:金额 operid:当前操作人 order_id:订单id }
     * @param obj
     * @return -1未查询到合约信息 -2 未查询到房间信息 0操作失败 >1
     *
     */
    @Transactional
    public int orderPay(Map<String, String> params) {
        return orderPay(params, null);
    }

    public int orderPay(Map<String, String> params, Object obj) {
        String agreementId = str.get(params, "agreement_id");
        String cost = str.get(params, "cost");
        String operid = str.get(params, "operid");
        String order_id = str.get(params, "order_id");
        String settlements_id = queryString(obj, getSql("financial.receivable.getcategory"), new Object[]{agreementId, 1});
        if ("".equals(settlements_id)) {
            return -1;
        }
        //查询出出租房名称
        String settlements_name = queryString(obj, getSql("basehouse.agreement.getHousName"), new Object[]{agreementId});
        if ("".equals(settlements_name)) {
            return -2;
        }
        String startTime = DateHelper.getToday("YYYY-MM-dd HH:mm:ss");
        settlements_name += "_退租结算";
        int exc = update(obj, getSql("financial.payable.insert"), new Object[]{settlements_name, settlements_id, 2, order_id, 6, Float.parseFloat(cost), startTime, "", "", null, operid, "", "", startTime, startTime});
        if (exc == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 服务订单退租付款
     *
     * @param params{ agreement_id:合约id cost:金额 operid:当前操作人 order_id:订单id }
     * @param obj
     * @return -1未查询到合约信息 -2 未查询到房间信息 0操作失败 >1操作成功
     */
    @Transactional
    public int orderPayNew(Map<String, String> params) {
        return orderPayNew(params, null);
    }

    public int orderPayNew(Map<String, String> params, Object obj) {
        String agreementId = str.get(params, "agreement_id");
        String cost = str.get(params, "cost");
        String operid = str.get(params, "operid");
        String order_id = str.get(params, "order_id");
        String settlements_id = queryString(obj, getSql("financial.receivable.getcategory"), new Object[]{agreementId, 1});
        if ("".equals(settlements_id)) {
            return -1;
        }
        //查询出出租房名称
        String settlements_name = queryString(obj, getSql("basehouse.agreement.getHousName"), new Object[]{agreementId});
        if ("".equals(settlements_name)) {
            return -2;
        }

        //删除对应的合约数据
        int exc = deleteFinancial(obj, settlements_id, agreementId, order_id);
        if (exc != 1) {
            return 0;
        }

        //插入对应的退租单条明细信息
        String ordersql = getSql("certificateLeaveService.main") + getSql("certificateLeaveService.orderId");
        List<Map<String, Object>> leaveMap = queryList(obj, ordersql, new Object[]{order_id});//查询所有代缴费信息
        Map<String, String> pramas = new HashMap<String, String>();
        pramas.put("order_id", order_id);//
        pramas.put("operid", operid);//操作人
        pramas.put("settlements_name", settlements_name);//房间名称
        pramas.put("settlements_id", settlements_id);//项目Id

        String updateCertificateLeave = getSql("certificateLeaveService.info.update").replace("####", " financial_type=? , financial_id=? ");//"update yc_certificateleave_tab set financial_type=? ,set financial_id=? where id=?";

        for (Map<String, Object> mp : leaveMap) {
            String type = returnType(str.get(mp, "type"));//代缴费对应财务数据类型
            if (type != null) {
                int res = 1;
                if (Float.parseFloat(str.get(mp, "cost")) > 0) //价格大于0，表示收入
                {
                    res = insertSel(pramas, mp, -1, type, obj);//插入收入数据
                    if (res < 0) {
                        return 0;
                    }
                    res = update(obj, updateCertificateLeave, new Object[]{1, res, str.get(mp, "id")});//更改代缴费财务类型-财务id
                    if (res < 0) {
                        return 0;
                    }
                } else if (Float.parseFloat(str.get(mp, "cost")) < 0) //价格小于0，表示支出
                {
                    res = insertSel(pramas, mp, 1, type, obj);//插入支出数据
                    if (res < 0) {
                        return 0;
                    }
                    res = update(obj, updateCertificateLeave, new Object[]{0, res, str.get(mp, "id")});//更改代缴费财务类型-财务id
                    if (res < 0) {
                        return 0;
                    }
                }
                if (res < 1) {
                    return 0;
                }
            }
        }

        //失效所有已付的订单信息
        String sql = "select ifnull(c.text_input, 0) as '金额总计'  "
                + " from work_order a "
                + " left join cancel_lease_order b on a.id=b.work_order_id "
                + " left join cancel_lease_order_value c on b.id=c.sub_order_id "
                + " and c.attr_path='CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY' "
                + " where a.type='A' "
                + "  and a.id = ? ";
        String orderCost = queryString(obj, sql, new Object[]{order_id});
        if ("".equals(orderCost)) {
            return 0;
        }
        Double orderCost_ = Double.valueOf(orderCost);
        String type = "1";
        if (orderCost_ <= 0) {
            type = "2";
        }

        if (Math.abs(orderCost_) != 0D) {
            //冻结其他账单信息
            exc = update(obj, "update financial_bill_tab a set a.state = 4 ,a.last_date = now(),a.last_remark = '生成退租账单进行挂起不需要继续支付' where a.ager_id = ? ", new Object[]{agreementId});
            if (exc == 0) {
                return 0;
            }

            int bill_id = insert(obj, "INSERT INTO financial_bill_tab (cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                    + "  values(?,?,?,now(),now(),'合约退租账单',?,?,3,now(),?)", new Object[]{Math.abs(orderCost_), "退租账单", type, agreementId, order_id, "退租账单信息"});
            if (bill_id == -1) {
                return 0;
            }

            List<Map<String, Object>> list = queryList(obj, "select a.id from financial_receivable_tab a,financial_settlements_tab b  "
                    + " where a.correlation = b.correlation_id  "
                    + "   AND a.isdelete = 1 AND a.isdelete = 1 "
                    + "   AND b.ager_id = ?", new Object[]{agreementId});
            for (int i = 0; i < list.size(); i++) {
                //插入账单
                exc = update(obj, "INSERT INTO financial_bill_detail_tab (bill_id,receivable_id)values(?,?)", new Object[]{bill_id, StringHelper.get(list.get(i), "id")});
                if (exc == 0) {
                    return 0;
                }
            }

            list = queryList(obj, "select a.id from financial_payable_tab a,financial_settlements_tab b  "
                    + " where a.correlation = b.correlation_id  "
                    + "   AND a.isdelete = 1 AND a.isdelete = 1 "
                    + "   AND b.ager_id = ?", new Object[]{agreementId});
            for (int i = 0; i < list.size(); i++) {
                //插入账单
                exc = update(obj, "INSERT INTO financial_bill_detail_tab (bill_id,payable_id) values(?,?)", new Object[]{bill_id, StringHelper.get(list.get(i), "id")});
                if (exc == 0) {
                    return 0;
                }
            }
            if ("1".equals(type)) {//发生推送信息  1租金账单2水电燃账单3退租账单4增值保洁账单5增值维修账单6其他账单
                sendNoti(obj, bill_id + "");
            }
        } else {

        }

//        //更新总价格信息
//        sql = "UPDATE financial_bill_tab a set a.cost = (  "
//                + "select ABS(ifnull(sum(c.cost),0)-ifnull(sum(b.cost),0))  "
//                + "  from financial_bill_detail_tab a LEFT JOIN financial_payable_tab b ON a.payable_id = b.id\n"
//                + "               LEFT JOIN financial_receivable_tab c on c.id = a.receivable_id  "
//                + " where a.bill_id = a.id)  "
//                + "  where a.id = ?";
//        exc = update(obj, sql, new Object[]{bill_id});
//        if (exc == 0) {
//            return 0;
//        }
        return 1;
    }

    /**
     * 计算相差天数
     *
     * @param day1
     * @param day2
     * @return
     * @throws ParseException
     */
    public Long jgDay(String day1, String day2) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(day1);
        Date date2 = format.parse(day2);
        return (date1.getTime() - date2.getTime()) / (24 * 3600 * 1000);
    }

    /**
     * 时间比较
     *
     * @param DATE1
     * @param DATE2
     * @return 1 date1>date2 -1 date2<date1 0 相等
     */
    public int compare_date(String DATE1, String DATE2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 发送通知
     *
     * @param obj
     * @param bill_id
     */
    public void sendNoti(Object obj, String bill_id) {
        Map<String, Object> userMap = queryMap(obj, "select a.name,b.user_id,a.cost,DATE_FORMAT(a.plan_time,'%Y-%m-%d') plan_time,(case when a.resource_type = 1 then '租金账单' when a.resource_type = 2 then '水电燃账单' "
                + "when a.resource_type = 3 then '退租账单' when a.resource_type = 4 then '增值保洁账单' when a.resource_type = 5 then '增值维修账单' "
                + "when a.resource_type = 6 then '其他账单' when a.resource_type = 7 then '押金账单' else '其他账单' end )resource_type_name \n"
                + "from financial_bill_tab a,yc_agreement_tab b ,yc_userinfo_tab c\n"
                + "where a.ager_id = b.id \n"
                + "  and b.user_id = c.id \n"
                + "  and a.id = ?\n"
                + "	limit 0,1", new Object[]{bill_id});
        String user_id = StringHelper.get(userMap, "user_id");
        String name = StringHelper.get(userMap, "name");
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
        appBillBean.setExreas_param(JSONObject.toJSONString(param));
        try {
            logger.debug("是否发生成功：" + PlushRequest.appBill(appBillBean));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            WeiXinRequest.sendBillSuc("https://m.room1000.com/client/new/index.html?open=client.new.server.payFees.payFees&uid=" + user_id,
                    user_id, Constants.YCQWJ_API, "您有一份新的账单生成，请及时进行支付。如已支付请忽略本消息，祝您生活愉快",
                    name, cost_, plan_time, "感谢您的使用，如有疑问，请联系客服。");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 删除财务收入与支出数据信息，退租使用，当前时间点之后的全部设置为无效，当前时间点之前的 如果未支付就不予设置支付
     *
     * @param obj
     * @param settlements_id
     * @return -1 退租时间没有
     */
    public int deleteFinancial(Object obj, String settlements_id, String arge_id, String order_id) {
        //获取退租时间
        String sql = "SELECT a.appointment_date FROM work_order a WHERE a.rental_lease_order_id = ? AND a.type = 'A' and a.id = ?";
        String tz_date = db.queryForString(sql, new Object[]{arge_id, order_id});
        if ("".equals(tz_date)) {
            logger.error("退租删除数据错误，未查询到退租时间：" + str.getSql(sql, new Object[]{arge_id}));
            return -1;
        }
        //小于退租时间并且已支付状态的变更为失效
        sql = "UPDATE financial_receivable_tab a SET a.isdelete = 0 "
                + "  WHERE a.correlation = ? "
                + "    AND ((a.start_time < ? and a.status > 0) OR a.start_time >= ?)";
        if (update(obj, sql, new Object[]{settlements_id, tz_date, tz_date}) != 1) {
            return -1;
        }
        sql = "UPDATE financial_payable_tab a SET a.isdelete = 0 "
                + "  WHERE a.correlation = ? "
                + "    AND ((a.start_time < ? and a.status > 0) OR a.start_time >= ?)";
        return update(obj, sql, new Object[]{settlements_id, tz_date, tz_date});
    }

    /**
     * 删除财务收入与支出数据
     *
     * @param Object obj 事务对象 settlements_id项目的id
     * @return 1成功 -1失败
     *
     */
    public int deleteFinancial(Object obj, String settlements_id) {
        //删除对应的合约信息
        int exc = update(obj, getSql("financial.payable.deletePayOrder"), new Object[]{settlements_id});
        if (exc != 1) {
            return -1;
        }
        //调用此接口后需要进行全部置为无效状态
        update(obj, getSql("financial.receivable.deleteReceivableOrder"), new Object[]{settlements_id});
        if (exc == 0) {
            return -1;
        }
        return exc;
    }

    private int insertSel(Map<String, String> params, Map<String, Object> leaveMap, int state, String type, Object obj) {
        String cost = str.get(leaveMap, "cost");//价格
        String operid = str.get(params, "operid");//操作人
        String order_id = str.get(params, "order_id");//合约Id
        String settlements_name = str.get(params, "settlements_name") + "_" + str.get(leaveMap, "name");//财务项目名称
        String settlements_id = str.get(params, "settlements_id");//财务项目Id
        String startTime = DateHelper.getToday("YYYY-MM-dd HH:mm:ss");//开始结束时间
        String sql = "";
        float costs = Float.parseFloat(cost);
        if (state == -1) {
            sql = getSql("financial.receivable.insert");
        } else if (state == 1) {
            costs = -costs;
            sql = getSql("financial.payable.insert");
        }
        int exc = insert(obj, sql, new Object[]{settlements_name, settlements_id, 2, order_id, type, costs, startTime, "", "", null, operid, "", "", startTime, startTime});
        return exc;
    }

    private String returnType(String key) {
        switch (key) {
            case "0":
                key = "11";//水费
                return key;
            case "1":
                key = "12";//电费
                return key;
            case "2":
                key = "2";//物业
                return key;
            case "3":
                key = "10";//其他 
                return key;
            case "4":
                key = "13";//燃气
                return key;
        }
        return null;
    }

    /**
     * 服务订单退租付款入住合约
     *
     * @param params{ WorkOrderDto operid }
     * @return -1未查询到合约信息 -2 未查询到房间信息 0操作失败 >1操作成功
     */
    @Transactional
    public int orderTakeHousePayNew(WorkOrderDto order, int operid) {
        Object obj = null;
        Map<String, Object> agreeMap = houseInterfaces.getAgreement(order.getTakeHouseOrderId().toString());//合约信息
        String settlements_id = queryString(obj, getSql("financial.payable.getcategory"), new Object[]{order.getTakeHouseOrderId(), 0});
        if ("".equals(settlements_id)) {
            return -1;
        }
        if (deleteFinancial(obj, settlements_id) != 1)//删除对应的合约信息
        {
            return -1;
        }
        return dealLeaveMap(obj, order, operid, agreeMap, settlements_id); //代缴费信息处理
    }

    /**
     * 撤销服务订单
     *
     * @param params{ order_id:订单id }
     * @param obj
     * @return
     */
    @Transactional
    public int repealOrder(Map<String, String> params) {
        return repealOrder(params, null);
    }

    /**
     * 处理待缴费信息
     *
     * @param params{ List<Map<String, Object>> leaveMap 待缴费信息
     * Map<String,String> pramas 财务信息数据 }
     * @param obj
     * @return
     */
    private int dealLeaveMap(Object obj, WorkOrderDto order, int operid, Map<String, Object> agreeMap, String settlements_id) {
        String ordersql = getSql("certificateLeaveService.main") + getSql("certificateLeaveService.orderId");
        List<Map<String, Object>> leaveMap = queryList(obj, ordersql, new Object[]{order.getId()});//查询所有代缴费信息
        Map<String, String> pramas = new HashMap<String, String>();
        pramas.put("order_id", order.getId().toString());//
        pramas.put("operid", String.valueOf(operid));//操作人
        pramas.put("settlements_name", str.get(agreeMap, "name"));//房间名称
        pramas.put("settlements_id", settlements_id);//项目Id
        String updateCertificateLeave = getSql("certificateLeaveService.info.update").replace("####", " financial_type=? , financial_id=? ");//"update yc_certificateleave_tab set financial_type=? ,set financial_id=? where id=?";
        for (Map<String, Object> mp : leaveMap) {
            String type = returnType(str.get(mp, "type"));//代缴费对应财务数据类型
            if (type != null) {
                int res = 1;
                if (Float.parseFloat(str.get(mp, "cost")) > 0) //价格大于0，表示收入
                {
                    res = insertSel(pramas, mp, -1, type, obj);//插入收入数据
                    if (res < 0) {
                        return 0;
                    }
                    res = update(obj, updateCertificateLeave, new Object[]{1, res, str.get(mp, "id")});//更改代缴费财务类型-财务id
                    if (res < 0) {
                        return 0;
                    }
                } else if (Float.parseFloat(str.get(mp, "cost")) < 0) //价格小于0，表示支出
                {
                    res = insertSel(pramas, mp, 1, type, obj);//插入支出数据
                    if (res < 0) {
                        return 0;
                    }
                    res = update(obj, updateCertificateLeave, new Object[]{0, res, str.get(mp, "id")});//更改代缴费财务类型-财务id
                    if (res < 0) {
                        return 0;
                    }
                }
                if (res < 1) {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * 撤销服务订单
     *
     * @param params{ order_id:订单id }
     * @param obj
     * @return
     */
    public int repealOrder(Map<String, String> params, Object obj) {
        String order_id = str.get(params, "order_id");
        String sql = getSql("financial.receivable.checkIsover");
        int res = queryForInt(obj, sql, new Object[]{order_id});
        if (res > 0) {
            return 0;
        }
        int exc = update(obj, getSql("financial.receivable.deletePay"), new Object[]{order_id, 2});
        //调用此接口后需要进行全部置为无效状态
        if (exc == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 撤销服务订单
     *
     * @param params{ order_id:订单id }
     * @param obj
     * @return
     */
    @Transactional
    public int repealPay(Map<String, String> params) {
        return repealPay(params, null);
    }

    /**
     * 撤销服务订单
     *
     * @param params{ order_id:订单id }
     * @param obj
     * @return
     */
    public int repealPay(Map<String, String> params, Object obj) {
        String order_id = str.get(params, "order_id");
        String sql = getSql("financial.payable.checkIsover");
        int res = queryForInt(obj, sql, new Object[]{order_id});
        if (res > 0) {
            return 0;
        }
        int exc = update(obj, getSql("financial.payable.deletePay"), new Object[]{order_id, 2});
        //调用此接口后需要进行全部置为无效状态
        if (exc == 0) {
            return 0;
        }
        return 1;
    }

    /**
     * 缴纳水电费
     *
     * {water:12$$32$$2016-07-08#2016-08-01,eMeter=12$$32$$2016-07-08#2016-08-01,cardgas=12$$32$$2016-07-08#2016-08-01,agreement_id:12312}
     *
     * @param params
     * @param obj
     * @return -1 未找到合约信息 -2没有租客 -3生成水费失败 -4 生成电费失败 -5生成燃气费失败
     * @throws Exception
     */
    public int jiaonashuidianfei(Map<String, Object> params, Object obj) throws Exception {
        String agreement_id = StringHelper.get(params, "agreement_id");
        //查询该收房合约下面是否存在 出租合约 ，如存在出租合约 才可进行操作
        String water = StringHelper.get(params, "water");//水费
        String eMeter = StringHelper.get(params, "eMeter");//电
        String cardgas = StringHelper.get(params, "cardgas");//煤气
        String operator = StringHelper.get(params, "operator");//煤气
        String handleId = StringHelper.get(params, "handleId");//操作id

        //核查该合约下面是否存在出租合约
        String sql = "SELECT a.house_id FROM yc_agreement_tab a WHERE a.type = 1 and a.status =2 and a.id = ?";
        String house_id = db.queryForString(sql, new Object[]{agreement_id});
        if ("".equals(house_id)) {//未找到合约信息
            return -1;
        }
        //找出租客
        sql = "SELECT b.begin_time,(case when b.checkouttime is not null && b.checkouttime < b.end_time then DATE_FORMAT(b.checkouttime,'%Y-%m-%d')  else b.end_time end) end_time,b.id,c.correlation_id,b.status"
                + "  FROM yc_houserank_tab a ,yc_agreement_tab b,financial_settlements_tab c"
                + " WHERE a.id = b.house_id"
                + " AND b.id = c.ager_id"
                + /*" and a.rank_status = 5 "+*/ " AND a.house_id = ?"
                + " AND b.type = 2 "
                + " and b.isdelete = 1 ";
        List<Map<String, Object>> list = db.queryForList(sql, new Object[]{house_id});
        logger.debug("水电费租客信息：" + list);
        logger.debug("水电费租客信息：" + str.getSql(sql, new Object[]{house_id}));
        if (list.size() == 0) {
            return -2;
        }

//        int bill_id = insert(obj, "INSERT INTO financial_bill_tab (name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
//                + "  values(?,?,now(),now(),'水电煤账单',?,?,2,now(),?)", new Object[]{"水电煤账单", "1", agreement_id, agreement_id, "水电煤账单"});
//        if (bill_id == -1) {
//            return 0;
//        }
        //拆分水费
        int exc = scCost(obj, list, "水费", water, operator, 0,handleId);
        if (exc != 1) {
            return -3;
        }
        int exc1 = scCost(obj, list, "电费", eMeter, operator, 0,handleId);
        if (exc1 != 1) {
            return -4;
        }
        int exc2 = scCost(obj, list, "燃气费", cardgas, operator, 0,handleId);
        if (exc2 != 1) {
            return -5;
        }

//        //更新总价格信息
//        sql = "UPDATE financial_bill_tab a \n"
//                + "  set a.cost = (SELECT SUM(c.cost)\n"
//                + "                  FROM financial_bill_detail_tab b,financial_receivable_tab c \n"
//                + "                 where b.receivable_id = c.id\n"
//                + "                   AND b.bill_id = a.id)\n"
//                + "  where a.id = ?";
//        exc = update(obj, sql, new Object[]{bill_id});
//        if (exc == 0) {
//            return 0;
//        }
//
//        sendNoti(obj, bill_id + "");
        return 1;
    }

    /**
     * 计算水电燃费信息
     *
     * @param obj
     * @param list
     * @param title
     * @param params
     * @param operator
     * @return
     * @throws ParseException
     */
    public int scCost(Object obj, List<Map<String, Object>> list, String title, String params, String operator, int bill_id,String handleId) throws ParseException {
        if (!"".equals(params)) {
            String[] waters = params.split("\\$\\$");
            String ds = waters[0];//度数
            String cost = waters[1];//费用
            String time = waters[2];//时间段
            String chaobiao = "";
            if (waters.length == 4) {
                chaobiao = waters[3];
            }
            String start_time = time.split("#")[0];//开始时间
            String end_time = time.split("#")[1];//结束时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_time_date = sdf.parse(start_time);
            Date end_time_date = sdf.parse(end_time);
            //核算出总的平坦天数
            int days = 0;
            String days_ = "";
            for (int i = 0; i < list.size(); i++) {
                String begin_time = StringHelper.get(list.get(i), "begin_time");
                String end_times = StringHelper.get(list.get(i), "end_time");
                String status = StringHelper.get(list.get(i), "status");
                Date hybegin_time_date = sdf.parse(begin_time);
                Date hyend_times_date = sdf.parse(end_times);

                if (start_time_date.getTime() > hyend_times_date.getTime()) {
                    //费用开始时间大于合约结束时间 就不计算
                } else if (hybegin_time_date.getTime() > end_time_date.getTime()) {
                    //合约的开始时间大于费用的开始时间 就不计算
                } else if (hybegin_time_date.getTime() <= end_time_date.getTime() && hybegin_time_date.getTime() <= start_time_date.getTime() && hyend_times_date.getTime() > end_time_date.getTime()) {
                    //合约的开始时间小于费用的结束时间大于费用的开始时间  合约的结束时间大于费用的结束时间

                } else if (hybegin_time_date.getTime() <= end_time_date.getTime() && hybegin_time_date.getTime() <= start_time_date.getTime() && hyend_times_date.getTime() <= end_time_date.getTime()) {
                    //合约的开始时间小于费用的结束时间大于费用的开始时间  合约的结束时间小鱼等于费用的结束时间

                } else if (hybegin_time_date.getTime() <= end_time_date.getTime() && hyend_times_date.getTime() <= end_time_date.getTime()) {
                    //合约的开始时间大于费用的结束时间  合约的结束时间小鱼等于费用的结束时间

                }

                Date beginTimeData = null;
                Date endTimeData = null;
                String begintime = "";
                String endtime = "";

                //合约的开始时间大于等于费用的开始时间
                if (hybegin_time_date.getTime() >= start_time_date.getTime()) {
                    if (hybegin_time_date.getTime() <= end_time_date.getTime()) {
                        beginTimeData = hybegin_time_date;
                        if (hyend_times_date.getTime() <= end_time_date.getTime()) {
                            endTimeData = hyend_times_date;
                        } else {
                            endTimeData = end_time_date;
                        }
                    }
                } else if (hybegin_time_date.getTime() < start_time_date.getTime()) {
                    if (hyend_times_date.getTime() > start_time_date.getTime() && hyend_times_date.getTime() <= end_time_date.getTime()) {
                        beginTimeData = start_time_date;
                        endTimeData = hyend_times_date;
                    } else if (hyend_times_date.getTime() > start_time_date.getTime() && hyend_times_date.getTime() > end_time_date.getTime()) {
                        beginTimeData = start_time_date;
                        endTimeData = end_time_date;
                    }
                }

                if (beginTimeData != null && endTimeData != null) {
                    int num = daysBetween(beginTimeData, endTimeData);
                    days += num;
                    days_ += num + "#" + DateHelper.getDateString(beginTimeData, "yyyy-MM-dd") + "#" + DateHelper.getDateString(endTimeData, "yyyy-MM-dd") + "#" + i + "#" + status + ",";
                    logger.debug("时间信息：" + begin_time + "--" + end_times + "--" + start_time + "--" + end_time + "--" + days);
                }
            }

            if (days != 0) {
                //每天需要得费用
                float day_cost = Float.valueOf(cost) / Float.valueOf(days);
                logger.debug("days:" + days);
                //核算一天需要得天数
                String[] days__ = days_.split(",");
                for (int i = 0; i < days__.length; i++) {
                    int day = Integer.valueOf(days__[i].split("#")[0]);
                    String starttime = days__[i].split("#")[1];
                    String endtime = days__[i].split("#")[2];
                    String status = days__[i].split("#")[4];
                    int cnt = Integer.valueOf(days__[i].split("#")[3]);
                    float cunt_cost = day_cost * Float.valueOf(day);
                    if (cunt_cost != 0) {
                        //合约无效于失效待清算状态不予插入
                        if (!"3".equals(status) && !"7".equals(status)) {
                            String correlation_id = StringHelper.get(list.get(cnt), "correlation_id");
                            String id = StringHelper.get(list.get(cnt), "id");
                            String sql = "INSERT INTO financial_receivable_tab (name,category,correlation,secondary_type,secondary,create_time,cost,plat_time,update_time,operator,remarks,status,start_time,end_time,handleId) "
                                    + "VALUES(?,?,?,1,?,NOW(),?,NOW(),NOW(),?,?,0,?,?,?)";
                            int exc = update(obj, sql, new Object[]{title + "待缴费", "水费".equals(title) ? "11" : "电费".equals(title) ? "12" : "13", correlation_id, id, cunt_cost, operator, starttime + "至" + endtime + " " + title + "抄表信息：" + chaobiao + " 总度数为：" + ds, starttime, endtime,handleId});
//                            String sql = "INSERT INTO financial_receivable_tab (name,category,correlation,secondary_type,secondary,create_time,cost,plat_time,update_time,operator,remarks,status,start_time,end_time) "
//                                    + "VALUES(?,?,?,1,?,NOW(),?,?,NOW(),?,?,0,?,?)";
//                            int exc = update(obj, sql, new Object[]{title + "待缴费", "水费".equals(title) ? "11" : "电费".equals(title) ? "12" : "13", correlation_id, id, cunt_cost,endtime, operator, starttime + "至" + endtime + " " + title + "抄表信息：" + chaobiao + " 总读数为：" + ds, starttime, endtime});
                            if (exc <= 0) {
                                return 0;
                            }
//                            exc = update(obj, "INSERT INTO financial_bill_detail_tab (bill_id,receivable_id)values(?,?)", new Object[]{bill_id, exc});
//                            if (exc == 0) {
//                                return 0;
//                            }
                        }
                    }
                }
            }
        }
        return 1;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate 较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args) {
//    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//         try {
//			Date d1=sdf.parse("2017-03-04 10:10:10");
//			Date d2=sdf.parse("2017-04-11 00:00:00");  
//			if(d1.getTime() > d2.getTime()){
//				logger.debug("11");
//			}else{
//				logger.debug("12");
//			}
//			logger.debug(daysBetween(d2, d1));
//			
//			int costfz = 10000;
//			int cost_cz = 8000;
//			int exc = costfz%cost_cz>0?(costfz/cost_cz +1):costfz/cost_cz;
//			logger.debug(exc);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}  
    }

    /**
     * 计算退租金额
     *
     * @author 刘飞
     * @param agreeId 合约id
     * @param order_time 退租日期
     * @return Map<String , String > cost_a 月租 allCost 已交费用 nowcoat >0 应退或
     * nowcoat <0是应付 nowcoat = 0 不退也不付 day 入住天数
     */
    public Object calculate(Map params) {
        Map<String, String> nowCostMap = new HashMap<String, String>();
        String agreeId = str.get(params, "agreeId"); //合约id
        String order_time = str.get(params, "order_time");//退租日期
        float nowcoat = 0;
        Map<String, String> agreeMap = db.queryForMap(getSql("orderService.task.getAgreement"), new Object[]{agreeId});//合约信息
        //查询当前已交费用
        String sql = getSql("orderService.task.getAllCost");
        String allCost = db.queryForString(sql, new Object[]{agreeId});//已交费用总计
        if (allCost == null || "".equals(allCost)) {
            allCost = "0";
        }
        String start_month = str.get(agreeMap, "begin_time").substring(0, 7) + "-01";//开始月份
        int start_date = Integer.parseInt(str.get(agreeMap, "begin_time").substring(8, 10));//开始日期
        String end_month = order_time.substring(0, 7) + "-01";//结束月份
        int end_date = Integer.parseInt(order_time.substring(8, 10));//结束日期
        if (end_date == 31) {
            end_date = 30;
        }
        if (start_date == 31) {
            start_date = 30;
        }
        //退租月份等于二月且退租日期等于28/29
        if (Integer.parseInt(end_month.substring(5, 7)) == 2 && (end_date == 28 || end_date == 29)) {
            //开始日期不等于28日 1.28
            if (start_date != 28) {
                end_date = 30;
            }
        }
        int day = 0;
        int month = 0;
        //退租日大于等于开始日  已交费用-{【月租金*月份数】+【（退租日-开始日+1）*（月租/30）}
        if (end_date >= start_date) {
            try {
                month = getMonthSpace(start_month, end_month);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//已入住月份
            day = (end_date - start_date) + 1; //取得当月租住天数（签订月份当天10-已租住至当月的天数15）//
            //退租日小于等于开始日  已交费用-{【月租金*(月份数-1)】+【（30-开始日）+（退租日+1）*（月租/30）】}  
        } else if (end_date < start_date) {
            try {
                month = getMonthSpace(start_month, end_month) - 1;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//已入住月份
            day = end_date + (30 - start_date) + 1; //取得当月租住天数（签订月份当天10-已租住至当月的天数15）//
        }
        //开始日为1.30/1.31且退租日包含在2月内  已交费用-{【月租金*(月份数-1)】+【（30-开始日）+（退租日+2）*（月租/30）】}
        if ((start_date == 30 || start_date == 31) && Integer.parseInt(order_time.substring(5, 7)) == 2) {
            try {
                month = getMonthSpace(start_month, end_month) - 1;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }//已入住月份
            if (end_date == 30) {
                end_date = 28;
            }
            day = end_date + (30 - start_date) + 2;
        }
        nowcoat = Float.parseFloat(allCost) - ((Float.parseFloat(str.get(agreeMap, "cost_a")) * month) + ((Float.parseFloat(str.get(agreeMap, "cost_a")) / 30) * day));
        nowcoat = (float) (Math.round(nowcoat * 100) / 100);
        nowCostMap.put("cost_a", String.valueOf(str.get(agreeMap, "cost_a")));
        nowCostMap.put("day", String.valueOf((month * 30) + day));
        nowCostMap.put("allCost", allCost);//已交费用
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
     *
     * @param ager_id 合约ID
     * @type 1冻结 2启用
     * @return
     */
    @Transactional
    public int stateBill(String ager_id, String type) {

        if ("1".equals(type)) {
            return update(null, "update financial_bill_tab a set a.state = 4,a.last_date=now(),a.last_remark='退租冻结' where a.ager_id = ? and a.state = 1",
                    new Object[]{ager_id});
        } else {
            return update(null, "update financial_bill_tab a set a.state = 1,a.last_date=now(),a.last_remark='解除冻结' where a.ager_id = ? and a.state = 4 and a.last_remark = '退租冻结'",
                    new Object[]{ager_id});
        }

    }

    /**
     * 冻结账单
     *
     * @param ager_id 合约ID
     * @param type 0 清算打回到上门 1租务打回到清算在到上门
     * @return
     */
    @Transactional
    public Result freezeBill(String ager_id) {
        Result result = new Result();

        //检查是否存在退租账单
//        String sql = "select count(1) from financial_bill_tab a where a.ager_id = '14411' and a.resource_type = 3";
        //冻结所有账单
        int exc = db.update("update financial_bill_tab a set a.state = 4,a.last_date=now(),a.last_remark='退租冻结' where a.ager_id = ? and a.state = 1",
                new Object[]{ager_id});

        if (exc != 1) {
            result.setState(0);
            result.setMsg("冻结账单失败");
        } else {
            result.setState(1);
            result.setMsg("操作成功");
        }
        return result;
    }

    /**
     * 解冻冻结账单
     *
     * @param ager_id 合约ID
     * @return
     */
    @Transactional
    public Result unFreezeBill(String ager_id) {
        Result result = new Result();

        String sql = "select a.id from work_order a where a.rental_lease_order_id = ? and a.type = 'N' and a.sub_order_state!='I' ";
        String orderId = db.queryForString(sql, new Object[]{ager_id});

        //检查是否包含退租账单
        sql = "select count(1) from financial_bill_tab a where a.ager_id = ? and a.resource_type = 3 and a.resource_id = ?";
        int cnt = db.queryForInt(sql, new Object[]{ager_id, orderId});
        int exc = 0;
        if (cnt > 0) {//包含退租账单 只解除最新的退租账单
            sql = "select max(a.id) from financial_bill_tab a where a.ager_id = ? and a.resource_type = 3";
            String bill_id = db.queryForString(sql, new Object[]{ager_id});
            if ("".equals(bill_id)) {
                result.setState(0);
                result.setMsg("解冻账单失败,未查询到退租账单");
                return result;
            }
            exc = db.update("update financial_bill_tab a set a.state = 1,a.last_date=now(),a.last_remark='解除冻结' where a.id = ? and a.state = 4",
                    new Object[]{bill_id});
        } else {//不包含退租账单 
            exc = db.update("update financial_bill_tab a set a.state = 1,a.last_date=now(),a.last_remark='解除冻结' where a.ager_id = ? and a.state = 4 and a.last_remark = '退租冻结'",
                    new Object[]{ager_id});
        }

        if (exc != 1) {
            result.setState(0);
            result.setMsg("解冻账单失败");
        } else {
            result.setState(1);
            result.setMsg("操作成功");
        }
        return result;
    }

    /**
     * 解冻冻结账单
     *
     * @param ager_id 合约ID
     * @return
     */
    @Transactional
    public Result closeOrderUnFreezeBill(String ager_id) {
        Result result = new Result();

//        String sql = "select count(1) from financial_bill_tab a where a.ager_id = ? and a.resource_type = 3";
//        int cnt = db.queryForInt(sql, new Object[]{ager_id});
        int exc = 0;

        //解除所有非退租账单
        exc = db.update("update financial_bill_tab a set a.state = 1,a.last_date=now(),a.last_remark='解除冻结' where a.ager_id = ? and a.state = 4 and a.resource_type <> 3",
                new Object[]{ager_id});
        if (exc != 1) {
            result.setState(0);
            result.setMsg("关闭订单解冻账单失败");
            return result;
        }

        //冻结所有退租账单
        exc = db.update("update financial_bill_tab a set a.state = 4,a.last_date=now(),a.last_remark='退租冻结' where a.ager_id = ? and a.state = 1 and a.resource_type = 3",
                new Object[]{ager_id});
        if (exc != 1) {
            result.setState(0);
            result.setMsg("关闭订单冻结退租账单失败");
        } else {
            result.setState(1);
            result.setMsg("操作成功");
        }

        return result;
    }

    /**
     * 获取财务明细数据信息
     *
     * @return
     */
    @Transactional
    public Result getFinancialList(String ager_id) {
        System.out.println("pccom.web.interfaces.Financial.getFinancialList()" + ager_id);
        Result result = new Result();
        String sql = "select c.`name` type_name,ROUND(b.cost,2) cost,\n"
                + "			 ifnull((select sum(c1.yet_cost) from financial_yet_tab c1 where c1.fin_id = b.id and c1.type = 1 ),0) yet_cost,\n"
                + "			 ifnull((select sum(c1.discount_cost) from financial_yet_tab c1 where c1.fin_id = b.id and c1.type = 1 ),0) discount_cost,\n"
                + "			 (case when b.cost = b.yet_cost then '已支付' when b.yet_cost = 0 then '未支付' else '支付中' end) state,\n"
                + "			 b.remarks,\n"
                + "                     DATE_FORMAT(b.start_time,'%Y-%m-%d') start_time,DATE_FORMAT(b.end_time,'%Y-%m-%d') end_time, "
                + "			 '收入' type,\n"
                + "			 (case when b.isdelete = 0 then '无效' else '有效' end) status\n"
                + "  from financial_settlements_tab a,financial_receivable_tab b,financial_category_tab c \n"
                + " where b.category = c.id\n"
                + "   and a.correlation_id = b.correlation\n"
                + "	 and a.ager_id = ?\n"
                + "	 union all\n"
                + " select c.`name` type_name,ROUND(0-b.cost,2) cost,\n"
                + "			 0-ifnull((select sum(c1.yet_cost) from financial_yet_tab c1 where c1.fin_id = b.id and c1.type = 2 ),0) yet_cost,\n"
                + "			 ifnull((select sum(c1.discount_cost) from financial_yet_tab c1 where c1.fin_id = b.id and c1.type = 2 ),0) discount_cost,\n"
                + "			 (case when b.cost = b.yet_cost then '已支付' when b.yet_cost = 0 then '未支付' else '支付中' end) state,\n"
                + "			 b.remarks,\n"
                + "                     DATE_FORMAT(b.start_time,'%Y-%m-%d') start_time,DATE_FORMAT(b.end_time,'%Y-%m-%d') end_time, "
                + "			 '收入' type,\n"
                + "			 (case when b.isdelete = 0 then '无效' else '有效' end) status\n"
                + "  from financial_settlements_tab a,financial_payable_tab b,financial_category_tab c \n"
                + " where b.category = c.id\n"
                + "   and a.correlation_id = b.correlation\n"
                + "	 and a.ager_id = ?";
        result.setState(1);
        result.setList(db.queryForList(sql, new Object[]{ager_id, ager_id}));
        return result;
    }

    /**
     * 判断 startTime是否大于等于endTime
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public boolean compareDate(String startTime, String endTime) {
        return compare_date(startTime, endTime) >= 0;
    }

    /**
     * 缴纳水电费
     *
     * {water:12$$32$$2016-07-08#2016-08-01,eMeter=12$$32$$2016-07-08#2016-08-01,cardgas=12$$32$$2016-07-08#2016-08-01,agreement_id:12312}
     *
     * @param params
     * @param obj
     * @return -1 未找到合约信息 -2没有租客 -3生成水费失败 -4 生成电费失败 -5生成燃气费失败
     * @throws Exception
     */
    public Result jiaonashuidianfeiSplit(Map<String, Object> params) throws Exception {
        Result result = new Result();
        String agreement_id = StringHelper.get(params, "agreement_id");
        String now_agreement_id = StringHelper.get(params, "now_agreement_id");
        //查询该收房合约下面是否存在 出租合约 ，如存在出租合约 才可进行操作
        String water = StringHelper.get(params, "water");//水费
        String eMeter = StringHelper.get(params, "eMeter");//电
        String cardgas = StringHelper.get(params, "cardgas");//煤气
        String operator = StringHelper.get(params, "operator");//操作人
        logger.debug("params:" + params);
        //核查该合约下面是否存在出租合约
        String sql = "SELECT a.house_id FROM yc_agreement_tab a WHERE a.type = 1 and a.status =2 and a.id = ?";
        String house_id = db.queryForString(sql, new Object[]{agreement_id});
        if ("".equals(house_id)) {//未找到合约信息
            result.setState(0);
            return result;
        }
        //找出租客
        sql = "SELECT b.begin_time,(case when b.checkouttime is not null then DATE_FORMAT(b.checkouttime,'%Y-%m-%d')  else b.end_time end) end_time,b.id,c.correlation_id,b.status"
                + "  FROM yc_houserank_tab a ,yc_agreement_tab b,financial_settlements_tab c"
                + " WHERE a.id = b.house_id"
                + " AND b.id = c.ager_id"
                + /*" and a.rank_status = 5 "+*/ " AND a.house_id = ?"
                + " AND b.type = 2 "
                + " and b.isdelete = 1 ";
        List<Map<String, Object>> list = db.queryForList(sql, new Object[]{house_id});
        logger.debug("水电费租客信息：" + list);
        logger.debug("水电费租客信息：" + str.getSql(sql, new Object[]{house_id}));
        if (list.size() == 0) {
            result.setState(0);
            return result;
        }

        //拆分水费
        Double exc = splitCost(list, "水费", water, operator, 0, now_agreement_id);
//        if (exc != 1) {
//            result.setState(0);
//            return result;
//        }
        Double exc1 = splitCost(list, "电费", eMeter, operator, 0, now_agreement_id);
//        if (exc1 != 1) {
//            result.setState(0);
//            return result;
//        }
        Double exc2 = splitCost(list, "燃气费", cardgas, operator, 0, now_agreement_id);
//        if (exc2 != 1) {
//            result.setState(0);
//            return result;
//        }
        result.setState(1);
        result.setMsg(exc + "#" + exc1 + "#" + exc2);
        return result;
    }

    public Double splitCost(List<Map<String, Object>> list, String title, String params, String operator, int bill_id, String now_agreement_id) throws ParseException {
        Double costCnt = 0d;
        if (!"".equals(params)) {
            String[] waters = params.split("\\$\\$");
            String ds = waters[0];//度数
            String cost = waters[1];//费用
            String time = waters[2];//时间段
            String chaobiao = "";
            if (waters.length == 4) {
                chaobiao = waters[3];
            }
            String start_time = time.split("#")[0];//开始时间
            String end_time = time.split("#")[1];//结束时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_time_date = sdf.parse(start_time);
            Date end_time_date = sdf.parse(end_time);
            //核算出总的平坦天数
            int days = 0;
            String days_ = "";
            for (int i = 0; i < list.size(); i++) {
                String begin_time = StringHelper.get(list.get(i), "begin_time");
                String end_times = StringHelper.get(list.get(i), "end_time");
                String status = StringHelper.get(list.get(i), "status");
                Date hybegin_time_date = sdf.parse(begin_time);
                Date hyend_times_date = sdf.parse(end_times);
                String id = StringHelper.get(list.get(i), "id");

                if (start_time_date.getTime() > hyend_times_date.getTime()) {
                    //费用开始时间大于合约结束时间 就不计算
                } else if (hybegin_time_date.getTime() > end_time_date.getTime()) {
                    //合约的开始时间大于费用的开始时间 就不计算
                } else if (hybegin_time_date.getTime() <= end_time_date.getTime() && hybegin_time_date.getTime() <= start_time_date.getTime() && hyend_times_date.getTime() > end_time_date.getTime()) {
                    //合约的开始时间小于费用的结束时间大于费用的开始时间  合约的结束时间大于费用的结束时间

                } else if (hybegin_time_date.getTime() <= end_time_date.getTime() && hybegin_time_date.getTime() <= start_time_date.getTime() && hyend_times_date.getTime() <= end_time_date.getTime()) {
                    //合约的开始时间小于费用的结束时间大于费用的开始时间  合约的结束时间小鱼等于费用的结束时间

                } else if (hybegin_time_date.getTime() <= end_time_date.getTime() && hyend_times_date.getTime() <= end_time_date.getTime()) {
                    //合约的开始时间大于费用的结束时间  合约的结束时间小鱼等于费用的结束时间

                }

                Date beginTimeData = null;
                Date endTimeData = null;
                String begintime = "";
                String endtime = "";

                //合约的开始时间大于等于费用的开始时间
                if (hybegin_time_date.getTime() >= start_time_date.getTime()) {
                    if (hybegin_time_date.getTime() <= end_time_date.getTime()) {
                        beginTimeData = hybegin_time_date;
                        if (hyend_times_date.getTime() <= end_time_date.getTime()) {
                            endTimeData = hyend_times_date;
                        } else {
                            endTimeData = end_time_date;
                        }
                    }
                } else if (hybegin_time_date.getTime() < start_time_date.getTime()) {
                    if (hyend_times_date.getTime() > start_time_date.getTime() && hyend_times_date.getTime() <= end_time_date.getTime()) {
                        beginTimeData = start_time_date;
                        endTimeData = hyend_times_date;
                    } else if (hyend_times_date.getTime() > start_time_date.getTime() && hyend_times_date.getTime() > end_time_date.getTime()) {
                        beginTimeData = start_time_date;
                        endTimeData = end_time_date;
                    }
                }

                if (beginTimeData != null && endTimeData != null) {
                    logger.debug("计算天数：" + id + ":" + beginTimeData + "---" + endTimeData);
                    int num = daysBetween(beginTimeData, endTimeData);
                    days += num;
                    days_ += num + "#" + DateHelper.getDateString(beginTimeData, "yyyy-MM-dd") + "#" + DateHelper.getDateString(endTimeData, "yyyy-MM-dd") + "#" + i + "#" + status + ",";
                    logger.debug(id + "时间信息：" + begin_time + "--" + end_times + "--" + start_time + "--" + end_time + "--" + days);
                }
            }

            if (days != 0) {
                //每天需要得费用
                float day_cost = Float.valueOf(cost) / Float.valueOf(days);
                logger.debug("days:" + days);
                //核算一天需要得天数
                String[] days__ = days_.split(",");
                for (int i = 0; i < days__.length; i++) {
                    logger.debug("days__[i]:" + days__[i]);
                    int day = Integer.valueOf(days__[i].split("#")[0]);
                    String starttime = days__[i].split("#")[1];
                    String endtime = days__[i].split("#")[2];
                    String status = days__[i].split("#")[4];
                    int cnt = Integer.valueOf(days__[i].split("#")[3]);
                    Double cunt_cost = day_cost * Double.valueOf(day);
                    if (cunt_cost != 0) {
                        //合约无效于失效待清算状态不予插入
                        if ("7".equals(status)) {
                            String id = StringHelper.get(list.get(cnt), "id");
                            logger.debug("now_agreement_id:" + now_agreement_id + "  id:" + id);
                            if (id.equals(now_agreement_id)) {
                                costCnt = AmountUtil.add(costCnt, cunt_cost, 2);
                            }
                        }
                    }
                }
            }
        }
        return costCnt;
    }

    /**
     * 获取退租结算财务数据信息
     *
     * @param ager_id
     * @param isAgen 是否需要重新结算，1需要 0不需要，默认不需要
     * @param isNormal 是否正常退租 1是正常 0非正常
     * @param orderId 订单ID
     * @return
     */
    public Result getSettleBill(String ager_id, int isAgen, int isNormal, String orderId) throws ParseException {
        Result result = new Result();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String sql = "select * from work_order a where a.rental_lease_order_id = ? and a.type = 'N' and a.sub_order_state!='I' ";
        Map<String, Object> workMap = db.queryForMap(sql, new Object[]{ager_id});

        //应付
        Double ljfCost = 0D;//垃圾费用
        Double typeCost_yf_1 = 0D;//租金
        Double typeCost_yf_2 = 0D;//物业费
        Double typeCost_yf_4 = 0D;//代缴费
        Double typeCost_yf_5 = 0D;//押金
        Double typeCost_yf_6 = 0D;//服务费
        Double typeCost_yf_10 = 0D;//其他
        Double typeCost_yf_11 = 0D;//水费
        Double typeCost_yf_12 = 0D;//电费
        Double typeCost_yf_13 = 0D;//煤气费
        Double typeCost_yf_16 = 0D;//煤气费
        //实付
        Double typeCost_sf_1 = 0D;//租金
        Double typeCost_sf_2 = 0D;//物业费
        Double typeCost_sf_4 = 0D;//代缴费
        Double typeCost_sf_5 = 0D;//押金
        Double typeCost_sf_6 = 0D;//服务费
        Double typeCost_sf_10 = 0D;//其他
        Double typeCost_sf_11 = 0D;//水费
        Double typeCost_sf_12 = 0D;//电费
        Double typeCost_sf_13 = 0D;//煤气费
        Double typeCost_sf_16 = 0D;//煤气费
        //优惠
        Double typeCost_yh_1 = 0D;//租金
        Double typeCost_yh_2 = 0D;//物业费
        Double typeCost_yh_4 = 0D;//代缴费
        Double typeCost_yh_5 = 0D;//押金
        Double typeCost_yh_6 = 0D;//服务费
        Double typeCost_yh_10 = 0D;//其他
        Double typeCost_yh_11 = 0D;//水费
        Double typeCost_yh_12 = 0D;//电费
        Double typeCost_yh_13 = 0D;//煤气费
        Double typeCost_yh_16 = 0D;//煤气费
        //超前付款
        Double typeCost_cq_1 = 0D;//租金
        Double typeCost_cq_2 = 0D;//物业费
        Double typeCost_cq_4 = 0D;//代缴费
        Double typeCost_cq_5 = 0D;//押金
        Double typeCost_cq_6 = 0D;//服务费
        Double typeCost_cq_10 = 0D;//其他
        Double typeCost_cq_11 = 0D;//水费
        Double typeCost_cq_12 = 0D;//电费
        Double typeCost_cq_13 = 0D;//煤气费
        Double typeCost_cq_16 = 0D;//煤气费
        //该付款但是未付款
        Double typeCost_gf_1 = 0D;//租金
        Double typeCost_gf_2 = 0D;//物业费
        Double typeCost_gf_4 = 0D;//代缴费
        Double typeCost_gf_5 = 0D;//押金
        Double typeCost_gf_6 = 0D;//服务费
        Double typeCost_gf_10 = 0D;//其他
        Double typeCost_gf_11 = 0D;//水费
        Double typeCost_gf_12 = 0D;//电费
        Double typeCost_gf_13 = 0D;//煤气费
        Double typeCost_gf_16 = 0D;//煤气费
        //总计
        Double typeCost_zj_1 = 0D;//租金
        Double typeCost_zj_2 = 0D;//物业费
        Double typeCost_zj_4 = 0D;//代缴费
        Double typeCost_zj_5 = 0D;//押金
        Double typeCost_zj_6 = 0D;//服务费
        Double typeCost_zj_10 = 0D;//其他
        Double typeCost_zj_11 = 0D;//水费
        Double typeCost_zj_12 = 0D;//电费
        Double typeCost_zj_13 = 0D;//煤气费
        Double typeCost_zj_16 = 0D;//煤气费
        
        Double typeCost_dd_11 = 0D;//煤气费
        Double typeCost_dd_12 = 0D;//煤气费
        Double typeCost_dd_13 = 0D;//煤气费

        //代缴费抵扣
        Double typeCost_dk_11 = 0D;//水费
        Double typeCost_dk_12 = 0D;//电费
        Double typeCost_dk_13 = 0D;//煤气费

        Double typeCost_tz = 0D;//退租订单产生的实付金额

        if (isAgen == 1) {//需要重新计算
            sql = "delete a from yc_certificateleave_tab a where a.order_id = ? and a.is_sys = 1 ";
            if (db.update(sql, new Object[]{orderId}) != 1) {
                result.setState(0);
                result.setMsg("重新结算删除数据失败");
                return result;
            }

//            sql = "SELECT a.appointment_date,a.sub_order_state,(case when a.appointment_date < DATE_SUB(b.end_time,INTERVAL 15 day) then 1 else 0 end) is_wy FROM work_order a,yc_agreement_tab b \n"
//                    + "where a.rental_lease_order_id = b.id\n"
//                    + "  and a.id = ?";
            sql = "select ifnull(a.water_meter,0) water_meter,ifnull(a.gas_meter,0)gas_meter,ifnull(a.electricity_meter,0)electricity_meter,a.father_id,a.cost cost_a,DATE_FORMAT(a.checkouttime,'%Y-%m-%d') checkouttime,DATE_FORMAT(a.begin_time,'%Y-%m-%d') begin_time,DATE_FORMAT(a.end_time,'%Y-%m-%d') end_time,a.id from yc_agreement_tab a where a.id=?";
            Map<String, Object> orderMap = db.queryForMap(sql, new Object[]{ager_id});
            String ager_endtime = StringHelper.get(orderMap, "end_time");//合约结束日期
            String ager_begintime = StringHelper.get(orderMap, "begin_time");//合约结束日期
            String water_meter = StringHelper.get(orderMap, "water_meter");
            String gas_meter = StringHelper.get(orderMap, "gas_meter");
            String electricity_meter = StringHelper.get(orderMap, "electricity_meter");
            String cost_a = StringHelper.get(orderMap, "cost_a");//合约总价
            if (orderMap.isEmpty()) {
                result.setState(0);
                result.setMsg("该订单异常：未查询到退租订单");
                return result;
            }
            String appointment_date = StringHelper.get(orderMap, "checkouttime");//退租时间

            //查詢出當前訂單所有的收支明細
            sql = "SELECT b.name,b.start_time,b.end_time,b.cost,ifnull((select sum(e.yet_cost) from financial_yet_tab e where e.fin_id = b.id and e.type = 1),0) yet_cost,b.category,b.status state ,"
                    + "ifnull((select sum(e.discount_cost) from financial_yet_tab e where e.fin_id = b.id and e.type = 1 and  (e.remark <> '代缴费抵扣' or e.remark is null) ),0) yh, "
                    + "ifnull(case when b.category in (11,12,13) then (select sum(e.discount_cost) from financial_yet_tab e where e.remark = '代缴费抵扣' and e.fin_id = b.id) else 0 end ,0) djf_cost "
                    + " FROM work_order a,financial_receivable_tab b,financial_settlements_tab c "
                    + " WHERE a.rental_lease_order_id = c.ager_id "
                    + "  AND b.correlation = c.correlation_id "
                    + "  and a.id = ? ";
            logger.debug("-------------查詢出當前訂單所有的收支明細:" + StringHelper.getSql(sql, new Object[]{orderId}));
            List<Map<String, Object>> list = db.queryForList(sql, new Object[]{orderId});
            if (list.isEmpty()) {
                result.setState(0);
                result.setMsg("该订单异常：未查询到收支明细");
                return result;
            }

            logger.info("compareDate(appointment_date, ager_endtime) ==== " + (compareDate(appointment_date, ager_endtime)));
            if (compareDate(appointment_date, ager_endtime)) {//退租时间大于合约结束时间，需要计算单租的钱
                //单价
                Double xyDay = Double.valueOf(jgDay(ager_endtime, ager_begintime).toString());
                logger.info("xyDay1111111 = " + xyDay);
                Double zDayCost = AmountUtil.divide(Double.valueOf(cost_a), xyDay, 2);
                logger.info("zDayCost1111111 = " + zDayCost+"---"+cost_a);
                //需要额外付款天数
                Double zDay = Double.valueOf(jgDay(appointment_date, ager_endtime).toString());
                logger.info("zDay1111111 = " + zDay+"---"+appointment_date+"-----"+ager_endtime);
                Double cost_yf_1 = AmountUtil.multiply(zDayCost, zDay, 2);
                logger.info("cost_yf_11111111 = " + cost_yf_1);
                typeCost_yf_1 = AmountUtil.add(cost_yf_1, typeCost_yf_1, 2);
                logger.info("typeCost_yf_11111111 = " + typeCost_yf_1);
            }

            for (int i = 0; i < list.size(); i++) {
                logger.debug("list:map:" + list.get(i));
                //            System.out.println("pccom.web.flow.base.CertificateLeaveService.disCost()" + list.get(i));
                String start_time = StringHelper.get(list.get(i), "start_time").substring(0, 10);
                String end_time = StringHelper.get(list.get(i), "end_time").substring(0, 10);
                Double cost = AmountUtil.add(Double.valueOf(StringHelper.get(list.get(i), "cost")), 0d, 2);//明细需要支付的金额
                Double yet_cost = AmountUtil.add(Double.valueOf(StringHelper.get(list.get(i), "yet_cost")), 0d, 2);//用户支付的钱交费不包含优惠金额 只退还用户支付的钱
                Double yh = AmountUtil.add(Double.valueOf(StringHelper.get(list.get(i), "yh")), 0d, 2);//优惠金额
                String state = StringHelper.get(list.get(i), "state");
                Double category = Double.valueOf(StringHelper.get(list.get(i), "category"));
                Double djf_cost = Double.valueOf(StringHelper.get(list.get(i), "djf_cost"));//代缴费抵扣金额
                if ("财务退租结算".equals(StringHelper.get(list.get(i), "name"))) {
                    continue;
                }
                //已经存在支付记录
                if (category == 1) {//租金
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //应付
                        typeCost_yf_1 = AmountUtil.add(cost, typeCost_yf_1, 2);
                        //实际支付
                        //typeCost_sf_1 = AmountUtil.add(yet_cost, typeCost_sf_1, 2);
                        //优惠
                        typeCost_yh_1 = AmountUtil.add(yh, typeCost_yh_1, 2);

                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_1 = AmountUtil.add(typeCost_gf_1, gfCost > 0 ? gfCost : 0, 2);

                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());

                        //间隔总天数
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        logger.debug("需要付款总天数：" + xyDay + " zDay:" + zDay + " zDayCost:" + zDayCost);
                        System.out.println("需要付款总天数：" + xyDay + " zDay:" + zDay + " zDayCost:" + zDayCost);
                        //应付 = 退租日期需要缴费金额-已支付的优惠金额
                        Double cost_yf_1 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        typeCost_yf_1 = AmountUtil.add(cost_yf_1, typeCost_yf_1, 2);
                        //实际支付
                        //typeCost_sf_1 = AmountUtil.add(yh >= cost_yf_1 ? 0 : yet_cost > cost_yf_1 ? cost_yf_1 : cost_yf_1 >= yet_cost ? yet_cost : 0, typeCost_yf_1, 2);
                        //typeCost_sf_1 = AmountUtil.add(yet_cost, typeCost_sf_1, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_1 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_1, 2);

                        //应退计算(朝前付款金额)
                        typeCost_cq_1 = AmountUtil.add(cost_yf_1 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_1, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_1, 2) : 0, typeCost_cq_1, 2);

                    } else {
                        //应退
//                        typeCost_cq_1 = AmountUtil.add(yet_cost > 0 ? yet_cost : 0, typeCost_cq_1, 2);
                    }
                    //实际支付
                    typeCost_sf_1 = AmountUtil.add(yet_cost, typeCost_sf_1, 2);
                } else if (category == 2) {//物业费
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //应付
                        typeCost_yf_2 = AmountUtil.add(cost, typeCost_yf_2, 2);
                        //实际支付
                        //typeCost_sf_2 = AmountUtil.add(yet_cost, typeCost_sf_2, 2);
                        //优惠
                        typeCost_yh_2 = AmountUtil.add(yh, typeCost_yh_2, 2);

                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_2 = AmountUtil.add(typeCost_gf_2, gfCost > 0 ? gfCost : 0, 2);

                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());
                        //间隔总天数
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        //应付 = 退租日期需要缴费金额-已支付的优惠金额
                        Double cost_yf_2 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        typeCost_yf_2 = AmountUtil.add(cost_yf_2, typeCost_yf_2, 2);
                        //实际支付
                        //typeCost_sf_2 = AmountUtil.add(yh >= cost_yf_2 ? 0 : yet_cost > cost_yf_2 ? cost_yf_2 : cost_yf_2 >= yet_cost ? yet_cost : 0, typeCost_yf_2, 2);
                        //typeCost_sf_2 = AmountUtil.add(yet_cost, typeCost_sf_2, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_2 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_2, 2);

                        //应退计算(朝前付款金额)
                        typeCost_cq_2 = AmountUtil.add(cost_yf_2 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_2, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_2, 2) : 0, typeCost_cq_2, 2);

                    } else {
                        //实际支付
                    }
                    typeCost_sf_2 = AmountUtil.add(yet_cost, typeCost_sf_2, 2);
                } else if (category == 4) {//代缴费
                    //应交
                    typeCost_yf_4 = AmountUtil.add(cost, typeCost_yf_4, 2);
                    //实际付款
                    typeCost_sf_4 = AmountUtil.add(yet_cost, typeCost_sf_4, 2);
                    //应退
                    typeCost_cq_4 = AmountUtil.add(yet_cost > 0 ? yet_cost : 0, typeCost_cq_4, 2);

                } else if (category == 5) {//押金
                    typeCost_yf_5 = AmountUtil.add(cost, typeCost_yf_5, 2);
                    typeCost_sf_5 = AmountUtil.add(yet_cost, typeCost_sf_5, 2);
                    typeCost_yh_5 = AmountUtil.add(yh, typeCost_yh_5, 2);
                    if (isNormal == 1) {
                        typeCost_zj_5 = 0 - typeCost_sf_5;
                    }
                } else if (category == 6) {
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //应付
                        typeCost_yf_6 = AmountUtil.add(cost, typeCost_yf_6, 2);
                        //实际支付
//                        typeCost_sf_6 = AmountUtil.add(yet_cost, typeCost_sf_6, 2);
                        //优惠
                        typeCost_yh_6 = AmountUtil.add(yh, typeCost_yh_6, 2);

                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_6 = AmountUtil.add(typeCost_gf_6, gfCost > 0 ? gfCost : 0, 2);

                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());
                        //间隔总天数
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        //应付 = 退租日期需要缴费金额-已支付的优惠金额
                        Double cost_yf_6 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        typeCost_yf_6 = AmountUtil.add(cost_yf_6, typeCost_yf_6, 2);
                        //实际支付
//                        typeCost_sf_6 = AmountUtil.add(yh >= cost_yf_6 ? 0 : yet_cost > cost_yf_6 ? cost_yf_6 : cost_yf_6 >= yet_cost ? yet_cost : 0, typeCost_yf_6, 2);
//                        typeCost_sf_6 = AmountUtil.add(yet_cost, typeCost_sf_6, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_6 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_6, 2);

                        //应退计算(朝前付款金额)
                        typeCost_cq_6 = AmountUtil.add(cost_yf_6 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_6, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_6, 2) : 0, typeCost_cq_6, 2);

                    } else {
                        //实际支付
//                        typeCost_sf_6 = AmountUtil.add(yet_cost, typeCost_sf_6, 2);
                    }
                    typeCost_sf_6 = AmountUtil.add(yet_cost, typeCost_sf_6, 2);
                } else if (category == 10) {//其他
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //应付
                        typeCost_yf_10 = AmountUtil.add(cost, typeCost_yf_10, 2);
                        //实际支付
//                        typeCost_sf_10 = AmountUtil.add(yet_cost, typeCost_sf_10, 2);
                        //优惠
                        typeCost_yh_10 = AmountUtil.add(yh, typeCost_yh_10, 2);

                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_10 = AmountUtil.add(typeCost_gf_10, gfCost > 0 ? gfCost : 0, 2);

                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());
                        //间隔总天数
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        //应付 = 退租日期需要缴费金额-已支付的优惠金额
                        Double cost_yf_10 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        typeCost_yf_10 = AmountUtil.add(cost_yf_10, typeCost_yf_10, 2);
                        //实际支付
                        //typeCost_sf_10 = AmountUtil.add(yh >= cost_yf_10 ? 0 : yet_cost > cost_yf_10 ? cost_yf_10 : cost_yf_10 >= yet_cost ? yet_cost : 0, typeCost_yf_10, 2);
//                        typeCost_sf_10 = AmountUtil.add(yet_cost, typeCost_sf_10, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_10 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_10, 2);

                        //应退计算(朝前付款金额)
                        typeCost_cq_10 = AmountUtil.add(cost_yf_10 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_10, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_10, 2) : 0, typeCost_cq_10, 2);

                    } else {
                        //实际支付
//                        typeCost_sf_10 = AmountUtil.add(yet_cost, typeCost_sf_10, 2);
                    }
                    typeCost_sf_10 = AmountUtil.add(yet_cost, typeCost_sf_10, 2);
                } else if (category == 11) {//水
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //实际支付
//                        typeCost_sf_11 = AmountUtil.add(yet_cost, typeCost_sf_11, 2);
                        //优惠 
                        typeCost_yh_11 = AmountUtil.add(yh, typeCost_yh_11, 2);
                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_11 = AmountUtil.add(typeCost_gf_11, gfCost > 0 ? gfCost : 0, 2);
                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());
                        //间隔总天数 
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        //应付 = 退租日期需要缴费金额-已支付的优惠金额
                        Double cost_yf_11 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        //实际支付
//                        typeCost_sf_11 = AmountUtil.add(yet_cost, typeCost_sf_11, 2);
                        //typeCost_sf_11 = AmountUtil.add(yh >= cost_yf_11 ? 0 : yet_cost > cost_yf_11 ? cost_yf_11 : cost_yf_11 >= yet_cost ? yet_cost : 0, typeCost_yf_11, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_11 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_11, 2);
                        //应退计算(朝前付款金额)
                        typeCost_cq_11 = AmountUtil.add(cost_yf_11 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_11, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_11, 2) : 0, typeCost_cq_11, 2);
                    } else {
                        //实际支付
//                        typeCost_sf_11 = AmountUtil.add(yet_cost, typeCost_sf_11, 2);
                    }
                    typeCost_sf_11 = AmountUtil.add(yet_cost, typeCost_sf_11, 2);
                    logger.debug("水费总额：" + typeCost_dk_11 + "--" + djf_cost);
                    typeCost_dk_11 = AmountUtil.add(typeCost_dk_11, djf_cost, 2);
                } else if (category == 12) {//电
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //实际支付
//                        typeCost_sf_12 = AmountUtil.add(yet_cost, typeCost_sf_12, 2);
                        logger.debug("typeCost_yh_12：" + typeCost_yh_12);
                        //优惠
                        typeCost_yh_12 = AmountUtil.add(yh, typeCost_yh_12, 2);
                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_12 = AmountUtil.add(typeCost_gf_12, gfCost > 0 ? gfCost : 0, 2);
                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());
                        //间隔总天数
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        //应付 = 退租日期需要缴费金额-已支付的优惠金额
                        Double cost_yf_12 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        //实际支付
//                        typeCost_sf_12 = AmountUtil.add(yet_cost, typeCost_sf_12, 2);
                        //typeCost_sf_12 = AmountUtil.add(yh >= cost_yf_12 ? 0 : yet_cost > cost_yf_12 ? cost_yf_12 : cost_yf_12 >= yet_cost ? yet_cost : 0, typeCost_yf_12, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_12 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_12, 2);
                        //应退计算(朝前付款金额)
                        typeCost_cq_12 = AmountUtil.add(cost_yf_12 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_12, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_12, 2) : 0, typeCost_cq_12, 2);
                    } else {
                        //实际支付
//                        typeCost_sf_12 = AmountUtil.add(yet_cost, typeCost_sf_12, 2);
                    }
                    typeCost_sf_12 = AmountUtil.add(yet_cost, typeCost_sf_12, 2);
                    typeCost_dk_12 = AmountUtil.add(typeCost_dk_12, djf_cost, 2);
                } else if (category == 13) {//煤
                    if (compareDate(appointment_date, end_time)) {//appointment_date>=end_time
                        //实际支付
//                        typeCost_sf_13 = AmountUtil.add(yet_cost, typeCost_sf_13, 2);
                        //优惠
                        typeCost_yh_13 = AmountUtil.add(yh, typeCost_yh_13, 2);
                        //应收金额  应付 - 实际支付 - 优惠
                        Double gfCost = AmountUtil.subtract(AmountUtil.subtract(cost, yet_cost, 2), yh, 2);
                        typeCost_gf_13 = AmountUtil.add(typeCost_gf_13, gfCost > 0 ? gfCost : 0, 2);
                    } else if (compareDate(appointment_date, start_time) && compare_date(appointment_date, end_time) < 0) {//appointment_date大于等于start_time 小鱼end_time
                        //先计算需要付款的天数
                        Double xyDay = Double.valueOf(jgDay(appointment_date, start_time).toString());
                        //间隔总天数
                        Double zDay = Double.valueOf(jgDay(end_time, start_time).toString());
                        Double zDayCost = AmountUtil.divide(cost, zDay, 2);
                        Double cost_yf_13 = AmountUtil.multiply(zDayCost, xyDay, 2) > cost ? cost : AmountUtil.multiply(zDayCost, xyDay, 2);
                        //实际支付
//                        typeCost_sf_13 = AmountUtil.add(yet_cost, typeCost_sf_13, 2);
                        //typeCost_sf_13 = AmountUtil.add(yh >= cost_yf_13 ? 0 : yet_cost > cost_yf_13 ? cost_yf_13 : cost_yf_13 >= yet_cost ? yet_cost : 0, typeCost_yf_13, 2);
                        //优惠
                        Double zDayYhCost = AmountUtil.divide(yh, zDay, 2);
                        typeCost_yh_13 = AmountUtil.add(AmountUtil.multiply(zDayYhCost, xyDay, isAgen), typeCost_yh_13, 2);
                        //应退计算(朝前付款金额)
                        typeCost_cq_13 = AmountUtil.add(cost_yf_13 < yet_cost ? AmountUtil.subtract(yet_cost, cost_yf_13, 2) < 0 ? 0 : AmountUtil.subtract(yet_cost, cost_yf_13, 2) : 0, typeCost_cq_13, 2);
                    } else {
                        //实际支付
//                        typeCost_sf_13 = AmountUtil.add(yet_cost, typeCost_sf_13, 2);
                    }
                    typeCost_sf_13 = AmountUtil.add(yet_cost, typeCost_sf_13, 2);
                    typeCost_dk_13 = AmountUtil.add(typeCost_dk_13, djf_cost, 2);
                }
            }

            //查询出所有退租订单对应缴费金额,全部放到其他实付中
            sql = "select ifnull(sum(case when a.type = 1 then a.yet_cost+a.discounts_cost else 0-a.yet_cost-a.discounts_cost end),0) from financial_bill_tab a where a.ager_id = ? and a.resource_type = 3 ";
            System.out.println("查询出所有退租订单对应缴费金额,全部放到其他实付中:" + StringHelper.getSql(sql, new Object[]{ager_id}));
            String tzCoststr = db.queryForString(sql, new Object[]{ager_id});
            if (!"".equals(tzCoststr)) {
                typeCost_tz = Double.valueOf(tzCoststr);
            }

            //单独计算水电煤费用  
            sql = "select  \n"
                    + "ifnull(b.text_input,0) as '水表初始值',\n"
                    + "ifnull(c.text_input,0) as '水表结束值',\n"
                    + "ifnull(d.text_input,0) as '电表初始值',\n"
                    + "ifnull(e.text_input,0) as '电表结束值',\n"
                    + "ifnull(f.text_input,0) as '燃气初始值',\n"
                    + "ifnull(g.text_input,0) as '燃气结束值',\n"
                    + "ifnull(h.text_input,0) as '水费单价',\n"
                    + "ifnull(i.text_input,0) as '燃气单价',\n"
                    + "ifnull(j.text_input,0) as '电费单价',\n"
                    + "ifnull(k.text_input,0) as '物业费',\n"
                    + "ifnull(l.text_input,0) as '垃圾费'\n"
                    + "from work_order a   \n"
                    + "left join cancel_lease_order_value b on a.ref_id=b.sub_order_id\n"
                    + "and b.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM'\n"
                    + "left join cancel_lease_order_value c on a.ref_id=c.sub_order_id\n"
                    + "and c.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_TO'\n"
                    + "left join cancel_lease_order_value d on a.ref_id=d.sub_order_id\n"
                    + "and d.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM'\n"
                    + "left join cancel_lease_order_value e on a.ref_id=e.sub_order_id\n"
                    + "and e.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_TO'\n"
                    + "left join cancel_lease_order_value f on a.ref_id=f.sub_order_id\n"
                    + "and f.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM'\n"
                    + "left join cancel_lease_order_value g on a.ref_id=g.sub_order_id\n"
                    + "and g.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_TO'\n"
                    + "left join cancel_lease_order_value h on a.ref_id=h.sub_order_id\n"
                    + "and h.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_UNIT_PRICE'\n"
                    + "left join cancel_lease_order_value i on a.ref_id=i.sub_order_id\n"
                    + "and i.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_UNIT_PRICE'\n"
                    + "left join cancel_lease_order_value j on a.ref_id=j.sub_order_id\n"
                    + "and j.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_UNIT_PRICE'\n"
                    + "left join cancel_lease_order_value k on a.ref_id=k.sub_order_id\n"
                    + "and k.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.PROPERTY_FEE'\n"
                    + "left join cancel_lease_order_value l on a.ref_id=l.sub_order_id\n"
                    + "and l.attr_path='CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WASTE_FEE'\n"
                    + "where a.id =?";
            Map<String, Object> map = db.queryForMap(sql, new Object[]{orderId});
            if (!map.isEmpty()) {
                Double wyCost = Double.valueOf(StringHelper.get(map, "物业费"));//物业费
                typeCost_yf_2 = AmountUtil.add(typeCost_yf_2, wyCost, 2);
                typeCost_yf_16 = Double.valueOf(StringHelper.get(map, "垃圾费"));//垃圾费

                //电费总和
                Double sStart = Double.valueOf(StringHelper.get(map, "水表初始值"));//电表初始值
                Double send = Double.valueOf(StringHelper.get(map, "水表结束值"));//电表初始值
                Double sCost = Double.valueOf(StringHelper.get(map, "水费单价"));//电表初始值
                Double dStart = Double.valueOf(StringHelper.get(map, "电表初始值"));//电表初始值
                Double dend = Double.valueOf(StringHelper.get(map, "电表结束值"));//电表初始值
                Double dCost = Double.valueOf(StringHelper.get(map, "电费单价"));//电表初始值
                Double rStart = Double.valueOf(StringHelper.get(map, "燃气初始值"));//电表初始值
                Double rend = Double.valueOf(StringHelper.get(map, "燃气结束值"));//电表初始值
                Double rCost = Double.valueOf(StringHelper.get(map, "燃气单价"));//电表初始值

                String begin_time = StringHelper.get(orderMap, "begin_time");//开始时间
                String end_time = StringHelper.get(orderMap, "end_time");//结束时间
                //{water:12$$32$$2016-07-08#2016-08-01,eMeter=12$$32$$2016-07-08#2016-08-01,cardgas=12$$32$$2016-07-08#2016-08-01,agreement_id:12312}
                Map<String, Object> map1 = new HashMap<String, Object>();
                //检查水电费对应信息
                //检查对应费用的开始结束时间
                sql = "select  DATE_FORMAT((case when a.lastDate <= b.begin_time then b.begin_time \n" +
"					   when a.lastDate >= b.end_time and a.firstDate > b.begin_time then a.firstDate \n" +
"						 when a.lastDate < b.end_time and a.lastDate > b.begin_time then a.lastDate),'%Y-%m-%d') lastDate \n"
                        + "  from yc_wegcost_tab a ,yc_agreement_tab b \n"
                        + " where a.agerId = b.father_id \n"
                        + "   and b.id = ?\n"
                        + "	 and a.type = 11 order by a.lastDate desc limit 1";
                String lastDatewater = db.queryForString(sql, new Object[]{ager_id});
                if (!"".equals(lastDatewater) && compareDate(lastDatewater, appointment_date)) {
                    result.setState(0);
                    result.setMsg("水费最后一期结束时间不能大于退租时间。");
                    return result;
                }
                String begin_time_water = "".equals(lastDatewater) ? begin_time : compareDate(lastDatewater, appointment_date) ? appointment_date : lastDatewater;
                map1.put("water", AmountUtil.subtract(send, sStart, 2) + "$$" + AmountUtil.multiply(AmountUtil.subtract(send, sStart, 2), sCost, 2) + "$$" + begin_time_water + "#" + appointment_date);
                sql = "select  DATE_FORMAT((case when a.lastDate <= b.begin_time then b.begin_time \n" +
"					   when a.lastDate >= b.end_time and a.firstDate > b.begin_time then a.firstDate \n" +
"						 when a.lastDate < b.end_time and a.lastDate > b.begin_time then a.lastDate),'%Y-%m-%d') lastDate \n"
                        + "  from yc_wegcost_tab a ,yc_agreement_tab b \n"
                        + " where a.agerId = b.father_id \n"
                        + "   and b.id = ?\n"
                        + "	 and a.type = 12 order by a.lastDate desc limit 1";
                String lastDateeMeter = db.queryForString(sql, new Object[]{ager_id});
                if (!"".equals(lastDateeMeter) && compareDate(lastDateeMeter, appointment_date)) {
                    result.setState(0);
                    result.setMsg("电费最后一期结束时间不能大于退租时间。");
                    return result;
                }
                String begin_time_eMeter = "".equals(lastDateeMeter) ? begin_time : compareDate(lastDateeMeter, appointment_date) ? appointment_date : lastDateeMeter;
                map1.put("eMeter", AmountUtil.subtract(dend, dStart, 2) + "$$" + AmountUtil.multiply(AmountUtil.subtract(dend, dStart, 2), dCost, 2) + "$$" + begin_time_eMeter + "#" + appointment_date);
                sql = "select  DATE_FORMAT((case when a.lastDate <= b.begin_time then b.begin_time \n" +
"					   when a.lastDate >= b.end_time and a.firstDate > b.begin_time then a.firstDate \n" +
"						 when a.lastDate < b.end_time and a.lastDate > b.begin_time then a.lastDate),'%Y-%m-%d') lastDate \n"
                        + "  from yc_wegcost_tab a ,yc_agreement_tab b \n"
                        + " where a.agerId = b.father_id \n"
                        + "   and b.id = ?\n"
                        + "	 and a.type = 13 order by a.lastDate desc limit 1";
                String lastDatecardgas = db.queryForString(sql, new Object[]{ager_id});
                if (!"".equals(lastDatecardgas) && compareDate(lastDatecardgas, appointment_date)) {
                    result.setState(0);
                    result.setMsg("燃气费最后一期结束时间不能大于退租时间。");
                    return result;
                }
                String begin_time_cardgas = "".equals(lastDatecardgas) ? begin_time : compareDate(lastDatecardgas, appointment_date) ? appointment_date : lastDatecardgas;
                map1.put("cardgas", AmountUtil.subtract(rend, rStart, 2) + "$$" + AmountUtil.multiply(AmountUtil.subtract(rend, rStart, 2), rCost, 2) + "$$" + begin_time_cardgas + "#" + appointment_date);
                map1.put("agreement_id", StringHelper.get(orderMap, "father_id"));
                map1.put("now_agreement_id", StringHelper.get(orderMap, "id"));
                try {
                    Result res = jiaonashuidianfeiSplit(map1);
                    logger.debug("res:" + res.getState() + "--" + res.getMsg());
                    if (res.getState() == 0) {
                        result.setState(0);
                        result.setMsg("水电煤拆分出现错误。");
                        return result;
                    }
                    typeCost_yf_11 = Double.valueOf(res.getMsg().split("#")[0]);
                    typeCost_yf_12 = Double.valueOf(res.getMsg().split("#")[1]);
                    typeCost_yf_13 = Double.valueOf(res.getMsg().split("#")[2]);
                    logger.debug("typeCost_yf_13:"+typeCost_yf_13);
                    sql = "select ifnull(sum(case when a.category = 11 then a.cost else 0 end),0) ty11, \n"
                            + "			 ifnull(sum(case when a.category = 12 then a.cost else 0 end),0) ty12,\n"
                            + "			 ifnull(sum(case when a.category = 13 then a.cost else 0 end),0) ty13\n"
                            + " from financial_receivable_tab a where a.category in (11,12,13) and a.secondary = ?";
                    Map<String, Object> sdmMap = db.queryForMap(sql, new Object[]{ager_id});
                    logger.debug("sdmMap:"+sdmMap);
                    typeCost_yf_11 = AmountUtil.add(typeCost_yf_11, Double.valueOf(StringHelper.get(sdmMap, "ty11")), 2);
                    typeCost_yf_12 = AmountUtil.add(typeCost_yf_12, Double.valueOf(StringHelper.get(sdmMap, "ty12")), 2);
                    typeCost_yf_13 = AmountUtil.add(typeCost_yf_13, Double.valueOf(StringHelper.get(sdmMap, "ty13")), 2);
                    logger.debug("1typeCost_yf_13:"+typeCost_yf_13);
                    //进行重新计算建议值
                    Double waterMeter = Double.valueOf(water_meter);
                    Double gasMeter = Double.valueOf(gas_meter);
                    Double electricityMeter = Double.valueOf(electricity_meter);
                    
                    Map<String, Object> map2 = new HashMap<String, Object>();
                    map2.put("water", AmountUtil.subtract(send, waterMeter, 2) + "$$" + AmountUtil.multiply(AmountUtil.subtract(send, waterMeter, 2), sCost, 2) + "$$" + begin_time + "#" + appointment_date);
                    map2.put("eMeter", AmountUtil.subtract(dend, electricityMeter, 2) + "$$" + AmountUtil.multiply(AmountUtil.subtract(dend, electricityMeter, 2), dCost, 2) + "$$" + begin_time + "#" + appointment_date);
                    map2.put("cardgas", AmountUtil.subtract(rend, gasMeter, 2) + "$$" + AmountUtil.multiply(AmountUtil.subtract(rend, gasMeter, 2), rCost, 2) + "$$" + begin_time + "#" + appointment_date);
                    map2.put("agreement_id", StringHelper.get(orderMap, "father_id"));
                    map2.put("now_agreement_id", StringHelper.get(orderMap, "id"));
                    Result res1 = jiaonashuidianfeiSplit(map2);
                    logger.debug("res1:" + res1.getState() + "--" + res1.getMsg());
                    resultMap.put("water", Double.valueOf(res1.getMsg().split("#")[0]));
                    resultMap.put("eMeter", Double.valueOf(res1.getMsg().split("#")[1]));
                    resultMap.put("cardgas", Double.valueOf(res1.getMsg().split("#")[2]));
                    
                } catch (Exception ex) {
                    logger.error("水电煤拆分出错：", ex);
                    result.setState(0);
                    result.setMsg("水电煤拆分出现错误。");
                    return result;
                }
            } else {
                result.setState(0);
                result.setMsg("该订单异常：水电煤录入数据为空，清算失败，请到管家上门步骤录入水电煤信息。");
                return result;
            }

            //跑完确定水电费单独计算，不予增加的内容合并
            typeCost_dd_11 = typeCost_yf_11;
            typeCost_dd_12 = typeCost_yf_12;
            typeCost_dd_13 = typeCost_yf_13;
            logger.debug("3typeCost_yf_13:"+typeCost_dd_13);
            //查询出自己增加内容
            sql = "select a.type,a.cost from yc_certificateleave_tab a where a.order_id = ? and a.is_sys = 0";
            List<Map<String, Object>> seftList = db.queryForList(sql, new Object[]{orderId});
            logger.debug(StringHelper.getSql(sql, new Object[]{orderId}));
            for (int i = 0; i < seftList.size(); i++) {
                String financial_type = StringHelper.get(seftList.get(i), "type");
                String cost = StringHelper.get(seftList.get(i), "cost");
                if ("1".equals(financial_type)) {
                    typeCost_yf_1 = AmountUtil.add(typeCost_yf_1, Double.valueOf(cost), 2);
                }
                if ("2".equals(financial_type)) {
                    typeCost_yf_2 = AmountUtil.add(typeCost_yf_2, Double.valueOf(cost), 2);
                }
                if ("6".equals(financial_type)) {
                    typeCost_yf_6 = AmountUtil.add(typeCost_yf_6, Double.valueOf(cost), 2);
                }
                if ("5".equals(financial_type)) {
                    typeCost_yf_5 = AmountUtil.add(typeCost_yf_5, Double.valueOf(cost), 2);
                    typeCost_zj_5 = AmountUtil.add(typeCost_zj_5, Double.valueOf(cost), 2);
                } 
                if ("6".equals(financial_type)) {
                    typeCost_yf_6 = AmountUtil.add(typeCost_yf_6, Double.valueOf(cost), 2);
                }
                if ("10".equals(financial_type)) {
                    typeCost_yf_10 = AmountUtil.add(typeCost_yf_10, Double.valueOf(cost), 2);
                }
                if ("11".equals(financial_type)) {
                    typeCost_yf_11 = AmountUtil.add(typeCost_yf_11, Double.valueOf(cost), 2);
                }
                if ("12".equals(financial_type)) {
                    typeCost_yf_12 = AmountUtil.add(typeCost_yf_12, Double.valueOf(cost), 2);
                }
                if ("13".equals(financial_type)) {
                    typeCost_yf_13 = AmountUtil.add(typeCost_yf_13, Double.valueOf(cost), 2);
                }
                if ("16".equals(financial_type)) {
                    typeCost_yf_16 = AmountUtil.add(typeCost_yf_16, Double.valueOf(cost), 2);
                }
            }

//            typeCost_zj_1 = typeCost_yf_1 - typeCost_sf_1 < 0 ? typeCost_yf_1 - typeCost_sf_1 : typeCost_yf_1 - typeCost_sf_1 - typeCost_yh_1 > 0 ? typeCost_yf_1 - typeCost_sf_1 - typeCost_yh_1 : 0;
            typeCost_zj_1 = typeCost_yf_1 - typeCost_sf_1 - typeCost_yh_1;
            //插入核算表中 
            if (insertCertificateleave(1, orderId, typeCost_zj_1)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
            //typeCost_zj_2 = typeCost_yf_2 - typeCost_sf_2 < 0 ? typeCost_yf_2 - typeCost_sf_2 : typeCost_yf_2 - typeCost_sf_2 - typeCost_yh_2 > 0 ? typeCost_yf_2 - typeCost_sf_2 - typeCost_yh_2 : 0;
            typeCost_zj_2 = typeCost_yf_2 - typeCost_sf_2 - typeCost_yh_2;
            if (insertCertificateleave(2, orderId, typeCost_zj_2)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
            typeCost_zj_4 = 0 - typeCost_sf_4;
            if (insertCertificateleave(4, orderId, typeCost_zj_4)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
            if (insertCertificateleave(5, orderId, typeCost_zj_5)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
            //typeCost_zj_6 = typeCost_yf_6 - typeCost_sf_6 < 0 ? typeCost_yf_6 - typeCost_sf_6 : typeCost_yf_6 - typeCost_sf_6 - typeCost_yh_6 > 0 ? typeCost_yf_6 - typeCost_sf_6 - typeCost_yh_6 : 0;;
            typeCost_zj_6 = typeCost_yf_6 - typeCost_sf_6 - typeCost_yh_6;
            if (insertCertificateleave(6, orderId, typeCost_zj_6)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
//            typeCost_zj_10 = typeCost_yf_10 - typeCost_sf_10 - typeCost_tz < 0 ? typeCost_yf_10 - typeCost_sf_10 - typeCost_tz : typeCost_yf_10 - typeCost_sf_10 - typeCost_tz - typeCost_yh_10 > 0 ? typeCost_yf_10 - typeCost_sf_10 - typeCost_tz - typeCost_yh_10 : 0;;
            typeCost_zj_10 = typeCost_yf_10 - typeCost_sf_10 - typeCost_yh_10 - typeCost_tz;
            if (insertCertificateleave(10, orderId, typeCost_zj_10)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
//            typeCost_zj_11 = typeCost_yf_11 - typeCost_sf_11 < 0 ? typeCost_yf_11 - typeCost_sf_11 : typeCost_yf_11 - typeCost_sf_11 - typeCost_yh_11 > 0 ? typeCost_yf_11 - typeCost_sf_11 - typeCost_yh_11 : 0;;;
            typeCost_zj_11 = typeCost_yf_11 - typeCost_sf_11 - typeCost_yh_11;
            typeCost_dd_11 = typeCost_dd_11 - typeCost_sf_11 - typeCost_yh_11;
            if (insertCertificateleave(11, orderId, typeCost_dd_11)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
//            typeCost_zj_12 = typeCost_yf_12 - typeCost_sf_12 < 0 ? typeCost_yf_12 - typeCost_sf_12 : typeCost_yf_12 - typeCost_sf_12 - typeCost_yh_12 > 0 ? typeCost_yf_12 - typeCost_sf_12 - typeCost_yh_12 : 0;;
            typeCost_zj_12 = typeCost_yf_12 - typeCost_sf_12 - typeCost_yh_12;
            typeCost_dd_12 = typeCost_dd_12 - typeCost_sf_12 - typeCost_yh_12;
            if (insertCertificateleave(12, orderId, typeCost_dd_12)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
//            typeCost_zj_13 = typeCost_yf_13 - typeCost_sf_13 < 0 ? typeCost_yf_13 - typeCost_sf_13 : typeCost_yf_13 - typeCost_sf_13 - typeCost_yh_13 > 0 ? typeCost_yf_13 - typeCost_sf_13 - typeCost_yh_13 : 0;;
            typeCost_zj_13 = typeCost_yf_13 - typeCost_sf_13 - typeCost_yh_13;
            typeCost_dd_13 = typeCost_dd_13 - typeCost_sf_13 - typeCost_yh_13;
            if (insertCertificateleave(13, orderId, typeCost_dd_13)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };
            typeCost_zj_16 = typeCost_yf_16 - typeCost_sf_16;
            if (insertCertificateleave(16, orderId, typeCost_zj_16)) {
                result.setState(0);
                result.setMsg("该订单异常：插入核算表失败");
                return result;
            };

            sql = "delete a from yc_order_tz_tab a where a.order_id = ?";
            if (db.update(sql, new Object[]{orderId}) < 1) {
                result.setState(0);
                result.setMsg("该订单异常：删除汇总清单失败");
                return result;
            }
            //记录相应参数值
            if (insertOrderTz(1, orderId, 1, typeCost_yf_1)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(2, orderId, 1, typeCost_yf_2)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(4, orderId, 1, typeCost_yf_4)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(5, orderId, 1, typeCost_yf_5)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(6, orderId, 1, typeCost_yf_6)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(10, orderId, 1, typeCost_yf_10)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(11, orderId, 1, typeCost_yf_11)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(12, orderId, 1, typeCost_yf_12)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(13, orderId, 1, typeCost_yf_13)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(16, orderId, 1, typeCost_yf_16)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };

            if (insertOrderTz(1, orderId, 2, typeCost_sf_1)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(2, orderId, 2, typeCost_sf_2)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(4, orderId, 2, typeCost_sf_4)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(5, orderId, 2, typeCost_sf_5)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(6, orderId, 2, typeCost_sf_6)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(10, orderId, 2, typeCost_sf_10 + " 其他退租账单金额：" + typeCost_tz)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(11, orderId, 2, typeCost_sf_11)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(12, orderId, 2, typeCost_sf_12)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(13, orderId, 2, typeCost_sf_13)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(16, orderId, 2, typeCost_sf_16)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };

            if (insertOrderTz(1, orderId, 3, typeCost_yh_1)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(2, orderId, 3, typeCost_yh_2)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(4, orderId, 3, typeCost_yh_4)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(5, orderId, 3, typeCost_yh_5)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(6, orderId, 3, typeCost_yh_6)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(10, orderId, 3, typeCost_yh_10)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(11, orderId, 3, "优惠：" + typeCost_yh_11 + "  抵扣：" + typeCost_dk_11)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(12, orderId, 3, "优惠：" + typeCost_yh_12 + "  抵扣：" + typeCost_dk_12)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(13, orderId, 3, "优惠：" + typeCost_yh_13 + "  抵扣：" + typeCost_dk_13)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(16, orderId, 3, typeCost_yh_16)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };

            if (insertOrderTz(1, orderId, 4, typeCost_zj_1)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(2, orderId, 4, typeCost_zj_2)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(4, orderId, 4, typeCost_zj_4)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(5, orderId, 4, typeCost_zj_5)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(6, orderId, 4, typeCost_zj_6)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(10, orderId, 4, typeCost_zj_10)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(11, orderId, 4, typeCost_zj_11)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(12, orderId, 4, typeCost_zj_12)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(13, orderId, 4, typeCost_zj_13)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            if (insertOrderTz(16, orderId, 4, typeCost_zj_16)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };
            //记录上次退租订单总额
            if (insertOrderTz(17, orderId, 5, tzCoststr)) {
                result.setState(0);
                result.setMsg("该订单异常：记录汇总参数失败");
                return result;
            };

            //应付
            resultMap.put("typeCost_yf_1", typeCost_yf_1);//租金
            resultMap.put("typeCost_yf_2", typeCost_yf_2);//物业费
            resultMap.put("typeCost_yf_4", typeCost_yf_4);//代缴费
            resultMap.put("typeCost_yf_5", typeCost_yf_5);//押金
            resultMap.put("typeCost_yf_6", typeCost_yf_6);//服务费
            resultMap.put("typeCost_yf_10", typeCost_yf_10);//其他
            resultMap.put("typeCost_yf_11", typeCost_yf_11);//水费
            resultMap.put("typeCost_yf_12", typeCost_yf_12);//电费
            resultMap.put("typeCost_yf_13", typeCost_yf_13);//煤气费
            resultMap.put("typeCost_yf_16", typeCost_yf_16);//垃圾费

            //实付
            resultMap.put("typeCost_sf_1", typeCost_sf_1);//租金
            resultMap.put("typeCost_sf_2", typeCost_sf_2);//物业费
            resultMap.put("typeCost_sf_4", typeCost_sf_4);//代缴费
            resultMap.put("typeCost_sf_5", typeCost_sf_5);//押金
            resultMap.put("typeCost_sf_6", typeCost_sf_6);//服务费
            resultMap.put("typeCost_sf_10", typeCost_sf_10 + " 其他退租账单金额：" + typeCost_tz);//其他
            resultMap.put("typeCost_sf_11", typeCost_sf_11);//水费
            resultMap.put("typeCost_sf_12", typeCost_sf_12);//电费
            resultMap.put("typeCost_sf_13", typeCost_sf_13);//煤气费
            resultMap.put("typeCost_sf_16", typeCost_sf_16);//垃圾费
            //优惠
            resultMap.put("typeCost_yh_1", typeCost_yh_1);//租金
            resultMap.put("typeCost_yh_2", typeCost_yh_2);//物业费
            resultMap.put("typeCost_yh_4", typeCost_yh_4);//代缴费
            resultMap.put("typeCost_yh_5", typeCost_yh_5);//押金
            resultMap.put("typeCost_yh_6", typeCost_yh_6);//服务费
            resultMap.put("typeCost_yh_10", typeCost_yh_10);//其他
            resultMap.put("typeCost_yh_11", "优惠：" + typeCost_yh_11 + "  抵扣：" + typeCost_dk_11);//水费
            resultMap.put("typeCost_yh_12", "优惠：" + typeCost_yh_12 + "  抵扣：" + typeCost_dk_12);//电费
            resultMap.put("typeCost_yh_13", "优惠：" + typeCost_yh_13 + "  抵扣：" + typeCost_dk_13);//煤气费
            resultMap.put("typeCost_yh_16", typeCost_yh_16);//垃圾费
            //总计
            resultMap.put("typeCost_zj_1", AmountUtil.add(typeCost_zj_1, 0d, 2));//租金
            resultMap.put("typeCost_zj_2", AmountUtil.add(typeCost_zj_2, 0d, 2));//物业费
            resultMap.put("typeCost_zj_4", AmountUtil.add(typeCost_zj_4, 0d, 2));//代缴费
            resultMap.put("typeCost_zj_5", AmountUtil.add(typeCost_zj_5, 0d, 2));//押金
            resultMap.put("typeCost_zj_6", AmountUtil.add(typeCost_zj_6, 0d, 2));//服务费
            resultMap.put("typeCost_zj_10", AmountUtil.add(typeCost_zj_10, 0d, 2));//其他
            resultMap.put("typeCost_zj_11", AmountUtil.add(typeCost_zj_11, 0d, 2));//水费
            resultMap.put("typeCost_zj_12", AmountUtil.add(typeCost_zj_12, 0d, 2));//电费
            resultMap.put("typeCost_zj_13", AmountUtil.add(typeCost_zj_13, 0d, 2));//煤气费
            resultMap.put("typeCost_zj_16", AmountUtil.add(typeCost_zj_16, 0d, 2));//垃圾费

            //汇总
            resultMap.put("summarizing", hzCost(typeCost_zj_16, typeCost_zj_1, typeCost_zj_2, typeCost_zj_4, typeCost_zj_5, typeCost_zj_6, typeCost_zj_10, typeCost_zj_11, typeCost_zj_12, typeCost_zj_13));

        } else {//不需要重新计算
            sql = "select * from yc_order_tz_tab a where a.order_id = ?";
            List<Map<String, Object>> list = db.queryForList(sql, new Object[]{orderId});
            if (!list.isEmpty()) {
                double tzCostHis = 0;
                for (int i = 0; i < list.size(); i++) {
                    if ("1".equals(StringHelper.get(list.get(i), "type"))) {
                        resultMap.put("typeCost_yf_" + StringHelper.get(list.get(i), "id"), StringHelper.get(list.get(i), "val"));
                    } else if ("2".equals(StringHelper.get(list.get(i), "type"))) {
                        resultMap.put("typeCost_sf_" + StringHelper.get(list.get(i), "id"), StringHelper.get(list.get(i), "val"));
                    } else if ("3".equals(StringHelper.get(list.get(i), "type"))) {
                        resultMap.put("typeCost_yh_" + StringHelper.get(list.get(i), "id"), StringHelper.get(list.get(i), "val"));
                    } else if ("4".equals(StringHelper.get(list.get(i), "type"))) {
                        resultMap.put("typeCost_zj_" + StringHelper.get(list.get(i), "id"), StringHelper.get(list.get(i), "val"));
                    } else if ("5".equals(StringHelper.get(list.get(i), "type"))) {
                        tzCostHis = Double.valueOf(StringHelper.get(list.get(i), "val"));
                    }
                }
                //汇总
                resultMap.put("summarizing", hzCost(StringHelper.get(resultMap, "typeCost_zj_16"), StringHelper.get(resultMap, "typeCost_zj_1"), StringHelper.get(resultMap, "typeCost_zj_2"), StringHelper.get(resultMap, "typeCost_zj_4"), StringHelper.get(resultMap, "typeCost_zj_5"), StringHelper.get(resultMap, "typeCost_zj_6"), StringHelper.get(resultMap, "typeCost_zj_10"), StringHelper.get(resultMap, "typeCost_zj_11"), StringHelper.get(resultMap, "typeCost_zj_12"), StringHelper.get(resultMap, "typeCost_zj_13")));
            } else {
                resultMap.put("summarizing", 0);
            }
        }

        result.setState(1);
        result.setMap(resultMap);
        
        logger.debug("执行结果："+result.getMap());
        
        return result;
    }

    private boolean insertOrderTz(int id, String order_id, int type, Object val) {
        String sql = "insert into yc_order_tz_tab(id,order_id,type,val)values(?,?,?,?)";

        if (db.update(sql, new Object[]{id, order_id, type, val}) <= 0) {
            return true;
        }
        return false;
    }

    private boolean insertCertificateleave(int type, String orderId, Double cost) {
        if (cost == 0D) {
            return false;
        }
        String sql = "insert into yc_certificateleave_tab (name,type,cost,costdesc,createtime,starttime,endtime,degree,order_id,step_type,task_id,is_sys) values(?,?,?,?,now(),?,?,?,?,?,?,1)";
        if (db.update(sql, new Object[]{"系统自动核算生成", type, cost, "系统自动核算生成", "0", "0", "0", orderId, "-1", "-1"}) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 汇总金额计算
     *
     * @param cost
     * @return
     */
    private Double hzCost(Object... cost) {
        Double hzCost = 0D;
        for (Object c : cost) {
            hzCost = AmountUtil.add(hzCost, Double.valueOf(c + ""), 2);
        }
        return hzCost;
    }

    /**
     * 退租订单财务保存
     *
     * @param orderId 订单ID
     * @return
     */
    @Transactional
    public Result saveOrder(String orderId) {
        Result result = new Result();

        //rental_lease_order_id
        String sql = "select * from work_order a where a.id = ? ";
        Map<String, Object> workMap = db.queryForMap(sql, new Object[]{orderId});
        String ager_id = StringHelper.get(workMap, "rental_lease_order_id");

        int exc1 = update(null, "update financial_bill_tab a set a.state = 4,a.last_date=now(),a.last_remark='退租冻结' where a.ager_id = ? and a.state = 1",
                new Object[]{ager_id});
        if (exc1 <= 0) {
            result.setState(0);
            result.setMsg("操作历史账单失败。");
            return result;
        }
        System.out.println("pccom.web.interfaces.Financial.saveOrder():::" + orderId);
        sql = "select a.correlation_id,a.`name` from financial_settlements_tab a where a.ager_id = ?";
        Map<String, Object> settMap = db.queryForMap(sql, new Object[]{ager_id});
        String correlation_id = StringHelper.get(settMap, "correlation_id");

        //插入汇总数据信息
        sql = "select sum(case when a.type = 4 then a.val else 0-a.val end) from yc_order_tz_tab a where a.order_id = ? and a.type in (4)";
        String hzCostStr = db.queryForString(sql, new Object[]{orderId});
        if ("".equals(hzCostStr)) {
            result.setState(0);
            result.setMsg("获取总明明细错误。");
            return result;
        }
        Double hzCost = Double.valueOf(hzCostStr);
        if (hzCost != 0D) {
            int type = 1;
            //插入财务数据明细中
            if (hzCost > 0) {//收入
                type = 1;
                sql = getSql("financial.receivable.insert");
            } else {
                type = 2;
                hzCost = -hzCost;
                sql = getSql("financial.payable.insert");
            }
            int exc = db.insert(sql, new Object[]{"财务退租结算", correlation_id, 2, orderId, 10, hzCost, DateHelper.getToday("yyyy-MM-dd HH:mm:ss"), "", "", null, "1", "", "", DateHelper.getToday("yyyy-MM-dd HH:mm:ss"), DateHelper.getToday("yyyy-MM-dd HH:mm:ss")});
            if (exc <= 0) {
                result.setState(0);
                result.setMsg("插入退租明细到财务失败。");
                return result;
            }
            //生成退租账单
            int bill_id = db.insert("INSERT INTO financial_bill_tab (cost,name,type,oper_date,last_date,last_remark,ager_id,resource_id,resource_type,plan_time,remark) "
                    + "  values(?,?,?,now(),now(),'合约退租账单',?,?,3,now(),?)", new Object[]{Math.abs(hzCost), "退租账单", type, ager_id, orderId, "退租账单信息"});
            if (bill_id == -1) {
                result.setState(0);
                result.setMsg("插入退租明细到财务失败。");
                return result;
            }

            if (type == 2) {//支出
                exc = db.update("INSERT INTO financial_bill_detail_tab (bill_id,payable_id)values(?,?)", new Object[]{bill_id, exc});
                if (exc == 0) {
                    result.setState(0);
                    result.setMsg("插入退租明细到财务失败。");
                    return result;
                }
            } else {//收入
                exc = db.update("INSERT INTO financial_bill_detail_tab (bill_id,receivable_id)values(?,?)", new Object[]{bill_id, exc});
                if (exc == 0) {
                    result.setState(0);
                    result.setMsg("插入退租明细到财务失败。");
                    return result;
                }
            }

            if ("1".equals(type)) {//发生推送信息  1租金账单2水电燃账单3退租账单4增值保洁账单5增值维修账单6其他账单
                sendNoti(null, bill_id + "");
            }
        }
        result.setState(1);
        result.setMsg("操作成功");
        
        return result;
    }

    /**
     * 判断退租账单是否已支付
     *
     * @param ager_id
     * @return true 存在未支付的退租账单 false不存在未支付的账单
     */
    public boolean checkTzBillPayState(String ager_id) {
        String sql = "select count(1) from financial_bill_tab a where a.ager_id = ? and a.resource_type = 3 and a.state = 1";
        return db.queryForInt(sql, new Object[]{ager_id}) > 0;
    }

    public class Result {

        /**
         * 状态 1成功 0失败
         */
        private int state;

        /**
         * 错误记录内容
         */
        private String msg;

        /**
         * 返回的数据列表信息，只有state为1时存在数据
         */
        private List<Map<String, Object>> list;

        /**
         * 返回的参数信息 t_cashPledge 退款押金金额 t_advancedCost 提前缴费信息退款
         * s_advancedFkyhCost 超前付款给用户需收回
         *
         */
        private Map<String, Object> map;

        @Override
        public String toString() {
            return "Result{" + "state=" + getState() + ", msg=" + getMsg() + ", list=" + getList() + ", map=" + getMap() + '}';
        }

        /**
         * 状态 1成功 0失败
         *
         * @return the state
         */
        public int getState() {
            return state;
        }

        /**
         * 状态 1成功 0失败
         *
         * @param state the state to set
         */
        public void setState(int state) {
            this.state = state;
        }

        /**
         * 错误记录内容
         *
         * @return the msg
         */
        public String getMsg() {
            return msg;
        }

        /**
         * 错误记录内容
         *
         * @param msg the msg to set
         */
        public void setMsg(String msg) {
            this.msg = msg;
        }

        /**
         * 返回的数据列表信息，只有state为1时存在数据
         *
         * @return the list
         */
        public List<Map<String, Object>> getList() {
            return list;
        }

        /**
         * 返回的数据列表信息，只有state为1时存在数据
         *
         * @param list the list to set
         */
        public void setList(List<Map<String, Object>> list) {
            this.list = list;
        }

        /**
         * 返回的参数信息 t_cashPledge 退款押金金额 t_advancedCost 提前缴费信息退款
         * s_advancedFkyhCost 超前付款给用户需收回
         *
         * @return the map
         */
        public Map<String, Object> getMap() {
            return map;
        }

        /**
         * 返回的参数信息 t_cashPledge 退款押金金额 t_advancedCost 提前缴费信息退款
         * s_advancedFkyhCost 超前付款给用户需收回
         *
         * @param map the map to set
         */
        public void setMap(Map<String, Object> map) {
            this.map = map;
        }

    }
}
