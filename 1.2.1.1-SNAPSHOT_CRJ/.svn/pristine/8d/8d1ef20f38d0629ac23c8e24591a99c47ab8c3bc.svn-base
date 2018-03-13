package pccom.web.job.jd;

import com.yc.api.client.YcApi;
import com.yc.api.client.YcApiConfig;
import com.yc.api.client.YcApiRetCode;
import com.yc.api.client.http.HttpClient;
import com.yc.api.client.util.JsonUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.DateHelper;
import pccom.common.util.SignUtil;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;

/**
 * 金蝶任务
 *
 * @author admin
 *
 */
public class JdTask {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JdTask.class);

    static ExecutorService pool = Executors.newFixedThreadPool(100);

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock wLock = rwl.writeLock();

    private static StringHelper str;

    private static DBHelperSpring db;

//	static WSRoom1KBizFacadeSrvProxy biz ;
    /**
     * 是否已经登录
     */
    static boolean isLogin;

    static {
        db = SpringHelper.getApplicationContext().getBean("dbHelper", DBHelperSpring.class);// 数据操作
        str = new StringHelper();
    }

    /**
     * 添加执行任务
     *
     * @param notiy_type
     * @param resource_id
     * @return 1加入队列成功 -4001 参数值notiy_type与resource_id不能为空 -4002插入数据库失败 -4003
     * 重复插入
     */
    public static int addPool(String notiy_type, String resource_id) {
        String sql = "select count(1) from yc_notiy_info a where a.notiy_type = ? and a.resource_id = ?";
        int e = db.queryForInt(sql, new Object[]{notiy_type, resource_id});
        if (e != 0) {
            return -4003;
        }
        //添加数据到数据库中
        sql = "insert into yc_notiy_info(notiy_type,resource_id)values(?,?)";
        int exc = db.insert(sql, new Object[]{notiy_type, resource_id});
        if (exc < 0) {
            return -4002;
        }

        JdTaskJob jdTaskJob = new JdTaskJob(exc + "");
        //加入线程执行
        if (!Constants.is_test) {
            pool.execute(jdTaskJob);
        }
        return 1;
    }

    /**
     * 本地测试回调接口
     *
     * @param notiy_type
     * @param resource_id
     */
    public static void isTestHd(String notiy_type, String resource_id) {
        if (Constants.is_test) {//如果是测试环境直接调用回显示信息
            //检查对应的合约是否是我们付款给租客的类型
            if ("6".equals(notiy_type) || "7".equals(notiy_type)) {

            }
            String url = "http://localhost:8187/interfaces/jdSyn/financeNotification.do";
            Map<String, Object> map = new HashMap<>();
            map.put("id", resource_id);
            map.put("timestamp", System.currentTimeMillis());
            map.put("time", DateHelper.getToday("yyyy-MM-dd HH:mm:ss"));

            StringBuffer content = new StringBuffer();
            List<String> keys = new ArrayList<String>(map.keySet());
            Collections.sort(keys);
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                String value = String.valueOf(map.get(key));
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            }

            String str = content.toString() + "&key=jdhouse";
            logger.debug(str);
            String sign = SignUtil.md5(str);
            map.put("sign", sign);

            logger.debug(url + "?" + content.toString() + "&sign=" + sign);
        }
    }

    public static void addPool(JdTaskJob jdTaskJob) {
        if (!Constants.is_test) {
            pool.execute(jdTaskJob);
        }
    }

    /**
     * 添加执行任务
     *
     * @return 1加入队列成功 -4001 参数值notiy_type与resource_id不能为空 -4002插入数据库失败
     */
    public static int addPool(Map<String, Object> params) {
        String notiy_type = str.get(params, "notiy_type");
        String resource_id = str.get(params, "resource_id");
        if (!Constants.is_test) {
            return addPool(notiy_type, resource_id);
        } else {
            return 1;
        }
    }

    /**
     * 登陆操作
     */
    public static void login() {
        wLock.lock();
        try {
            //测试环境 不进行实际登陆操作
            logger.debug("!Constants.is_test && !isLogin:" + (!Constants.is_test && !isLogin));
            if (!Constants.is_test && !isLogin) {
				YcApiConfig.getInstance().setMerchantNo("20170515133129814");
                 YcApiConfig.getInstance().setUrl("http://58.213.154.78:8989/apisrv");
//				YcApiConfig.getInstance().setCerFile("D:\\Users\\Administrator\\Workspaces\\work1\\MPWeb\\src\\main\\resources\\ycapi_20170515133129814.cer");
                 YcApiConfig.getInstance().setCerFile(JdTask.class.getResource("/ycapi_20170515133129814.cer").getPath());
                 logger.debug("开始登陆1:" + YcApiConfig.getInstance().getUrl() + "/ycapi.do");
                 logger.trace("重新登陆金蝶");
                 HttpClient.setHttpUrl(YcApiConfig.getInstance().getUrl() + "/ycapi.do");
                 logger.debug("开始登陆");
                 isLogin = YcApi.login();
                 logger.debug("登陆状态：" + isLogin);
                 logger.trace("登陆状态："+isLogin);

                //测试环境-----
//                YcApiConfig.getInstance().setMerchantNo("20170607113012505");
//                YcApiConfig.getInstance().setUrl("http://58.213.154.78:8989/apisrv_test");
////				YcApiConfig.getInstance().setCerFile("D:\\Users\\Administrator\\Workspaces\\work1\\MPWeb\\src\\main\\resources\\ycapi_20170515133129814.cer");
//                YcApiConfig.getInstance().setCerFile(JdTask.class.getResource("/ycapi_ceshi.cer").getPath());
//                logger.debug("开始登陆1");
//                logger.trace("重新登陆金蝶");
//                HttpClient.setHttpUrl(YcApiConfig.getInstance().getUrl() + "/ycapi.do");
//                logger.debug("开始登陆");
//                isLogin = YcApi.login();
//                logger.debug("登陆状态：" + isLogin);

            }
        } catch (Exception e) {
            logger.debug("登陆金蝶失败：", e);
        } finally {
            wLock.unlock();
        }
    }

}

/**
 * 异步任务处理信息
 *
 * @author admin
 *
 */
class JdTaskJob implements Runnable {

    public final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    private String notiy_id;

    private CountDownLatch latch = null;

    private DBHelperSpring db = SpringHelper.getApplicationContext().getBean("dbHelper", DBHelperSpring.class);// 数据操作

    public JdTaskJob(String notiy_id) {
        this.notiy_id = notiy_id;
    }

    public JdTaskJob(String notiy_id, CountDownLatch latch) {
        this.notiy_id = notiy_id;
        this.latch = latch;
    }

    @Override
    public void run() {
        String sql = "select * from yc_notiy_info a where a.id = ?  ";
        Map<String, Object> map = db.queryForMap(sql, new Object[]{notiy_id});
        logger.debug("获取执行的金蝶同步的数据：" + map);
        JdTask.login();
        if (!map.isEmpty() && JdTask.isLogin) {
            //执行同步操作
            String id = StringHelper.get(map, "id");
            String notiy_type = StringHelper.get(map, "notiy_type");//通知类型1合约通知2新增收款明细3合约作废通知4新增财务支出明细5退租6收款通知7付款通知8新的账单付款通知9新的收款通知
            String resource_id = StringHelper.get(map, "resource_id");
            String notiy_state = StringHelper.get(map, "notiy_state");//通知状态1刚创建2通知成功3通知失败
            JSONObject json = new JSONObject();
            //链接金蝶
            try {
                if ("1".equals(notiy_type)) {//合约通知
                    //获取合约信息
                    sql = "SELECT a.code number,"
                            + "     a.type-1 contractType,"
                            //                            + "       (CASE WHEN a.type = 2 THEN (SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = a.house_id and b.house_id = e.house_id AND e.type = 1 AND e.status = 2 LIMIT 0 ,1) ELSE '' end) gatherNumber,"
                            + "       (CASE WHEN a.type = 2 THEN (SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = a.house_id and b.house_id = e.house_id AND e.type = 1  LIMIT 0 ,1) ELSE '' end) gatherNumber,"
                            + "    a.name,"
                            + "    a.cost amount,"
                            + "    a.cost_a priceMonth,concat(DATE_FORMAT(a.begin_time,'%Y-%m-%d'),' 00:00:00') startDate,concat(DATE_FORMAT(a.end_time,'%Y-%m-%d'),' 00:00:00') endDate,"
                            + "     TIMESTAMPDIFF(month,a.begin_time,a.end_time) lease_period,"
                            + "     ifnull(a.free_period,0) unLease_period,"
                            + "     (CASE WHEN a.status = 12 THEN 1 when a.status = 2 then 3 else 0 end) billStatus,"
                            + "     0 currency,"
                            + "		DATE_FORMAT(date_sub(a.begin_time,interval ifnull(a.free_period,0) day),'%Y-%m-%d %T') bizDate,"
                            + "		a.pay_type paymentType,"
                            + "		(case when ifnull(a.payway,5) = 0 then 3 else ifnull(a.payway,5) end) settleType,"
                            + "    (SELECT f.username FROM yc_userinfo_tab f WHERE f.id = a.user_id LIMIT 0,1) customer"
                            + " FROM yc_agreement_tab a  "
                            + " WHERE a.id = ?"
                            + " and a.name not like '%测试%'"
                            + " and ((exists(select 1 from financial_receivable_tab b WHERE b.secondary = a.id and b.secondary_type = 1) and a.type = 2) or a.type = 1) ";
                    logger.debug("金蝶同步查询合约信息："+StringHelper.getSql(sql, new Object[]{resource_id}));
                    Map<String, Object> hyMap = db.queryForMap(sql, new Object[]{resource_id});
                    if (hyMap.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        //查询出对应的支付明细信息
                        sql = "SELECT "
                                + "	  DATE_FORMAT(a.start_time, '%Y-%m-%d %T') periodStart,"
                                + "	  DATE_FORMAT(a.end_time, '%Y-%m-%d %T') periodEnd,"
                                + "	  (CASE WHEN a.category = 14 THEN 1 ELSE a.category"
                                + "	  END) account,"
                                + "	  0 detailCurrency,"
                                + "	  (CASE WHEN a.cost > 0 THEN a.cost ELSE 0 end) detailAmount,"
                                + "	  0 billType,"
                                + "	  IFNULL((SELECT SUM( g.discount_cost ) FROM financial_yet_tab g WHERE g.type = 1 AND g.fin_id = a.id), 0)+(CASE WHEN a.cost > 0 THEN 0 ELSE 0-a.cost end) discountAmount"
                                + "	FROM financial_receivable_tab a"
                                + "	WHERE a.secondary = ? and a.secondary_type in(0,1) and a.name not like '%测试%'";
                        List<Map<String, Object>> srList = db.queryForList(sql, new Object[]{resource_id});
                        logger.debug("获取支付明细信息1：" + StringHelper.getSql(sql, new Object[]{resource_id}));

                        sql = "SELECT DATE_FORMAT(a.start_time,'%Y-%m-%d %T') periodStart,DATE_FORMAT(a.end_time,'%Y-%m-%d %T') periodEnd,"
                                + "    (case when a.category = 14 then 1 else a.category end) account,"
                                + "    0 detailCurrency,"
                                + "   (CASE WHEN a.cost > 0 THEN a.cost ELSE 0 end) detailAmount,1 billType,"
                                + "   ifnull((SELECT SUM( g.discount_cost ) FROM financial_yet_tab g WHERE g.type = 2 AND g.fin_id = a.id ),0)+(CASE WHEN a.cost > 0 THEN 0 ELSE 0-a.cost end) discountAmount "
                                + " FROM financial_payable_tab a "
                                + " WHERE a.secondary = ? and a.secondary_type in(0,1) and a.name not like '%测试%'";

                        List<Map<String, Object>> zcList = db.queryForList(sql, new Object[]{resource_id});
                        logger.debug("获取支付明细信息2：" + StringHelper.getSql(sql, new Object[]{resource_id}));
                        logger.debug("获取支付明细信息3：" + zcList);

                        //如果是收房合约进行计算出租金总金额
                        if ("0".equals(StringHelper.get(hyMap, "contractType"))) {//收房合约
                            float amo = 0f;
                            for (int i = 0; i < zcList.size(); i++) {
                                String account = StringHelper.get(zcList.get(i), "account");
                                if ("1".equals(account)) {
                                    float discountAmount = Float.valueOf(StringHelper.get(zcList.get(i), "discountAmount"));
                                    float detailAmount = Float.valueOf(StringHelper.get(zcList.get(i), "detailAmount"));
                                    amo += discountAmount + detailAmount;
                                }
                            }
                            hyMap.put("amount", amo);
                        }
                        String meth = "addGather";//收房
                        if ("0".equals(StringHelper.get(hyMap, "contractType"))) {//收房合约 0  1
                            hyMap.put("cu", "025QT06");
                            hyMap.put("company", "025QT06");
                            hyMap.put("group", "B50");
                        } else {//出租合约
                            meth = "addRent";//
                            hyMap.put("cu", "025QT06");
                            hyMap.put("company", "025QT06");
                            hyMap.put("group", "B51");
                        }
                        srList.addAll(zcList);
                        hyMap.put("details", srList);
                        List<Map<String, Object>> mapList = new ArrayList<>();
                        mapList.add(hyMap);
                        json.put("bills", JSONArray.fromObject(mapList));
                        String result = YcApi.sendRequest(meth, json.toString(), true);
                        //logger.debug("返回参数值");
                        if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                            update(id, 1, result, json.toString());
                        } else {
                            update(id, 2, result, json.toString());
                        }
                        logger.debug("返回数据信息" + result);
                    }
                } else if ("2".equals(notiy_type)) {//新建房源通知

                } else if ("3".equals(notiy_type)) {//合约作废通知
                    //获取合约信息
                    sql = "SELECT a.code number,"
                            + "     a.type-1 billType,DATE_FORMAT(a.checkouttime,'%Y-%m-%d %T')  cancelDate,'off' contractState  "
                            + " FROM yc_agreement_tab a  "
                            + " WHERE a.id = ? and a.name not like '%测试%'";
                    Map<String, Object> hyMap = db.queryForMap(sql, new Object[]{resource_id});
                    logger.debug("hyMap:" + hyMap);
                    if (hyMap.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        //进行调用接口
                        hyMap.put("cu", "025QT06");
                        hyMap.put("company", "025QT06");
                        json = JSONObject.fromObject(hyMap);
                        logger.debug("请求信息：" + json.toString());

                        String result = YcApi.sendRequest("cancelContract", json.toString(), true);
                        logger.debug("返回参数值");
                        if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                            update(id, 1, result, json.toString());
                        } else {
                            update(id, 2, result, json.toString());
                        }
                        logger.debug("返回数据信息" + result);
                    }
                } else if ("4".equals(notiy_type)) {//新增财务明细

                } else if ("5".equals(notiy_type)) {//退租  类似合约作废通知
                    //获取合约信息
                    sql = "SELECT a.code number,"
                            + "     a.type-1 billType,DATE_FORMAT(a.checkouttime,'%Y-%m-%d %T')  cancelDate,'off' contractState"
                            +//off或者cancel 失效或者作废
                            " FROM yc_agreement_tab a  "
                            + " WHERE a.id = ? and a.name not like '%测试%'";
                    Map<String, Object> hyMap = db.queryForMap(sql, new Object[]{resource_id});
                    if (hyMap.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        //进行调用接口
                        json = JSONObject.fromObject(hyMap);
                        logger.debug("请求信息：" + json.toString());
//							String result = HttpURLConnHelper.execute(url, json.toString());
                        String result = YcApi.sendRequest("cancelContract", json.toString(), true);
                        if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                            update(id, 1, result, json.toString());
                        } else {
                            update(id, 2, result, json.toString());
                        }
                        logger.debug("返回数据信息" + result);
                    }
                } else if ("6".equals(notiy_type)) {//收款通知   一般为租客付款给我们的价格
                    sql = "select a.id number,(case when a.secondary_type = 0 then 0 else a.secondary_type end) contractType,"
                            + "   0 billType,0 currency,a.cost amount,c.code rentNumber,c.type,"
                            + "	(SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = c.house_id and b.house_id = e.house_id AND e.type = 1 LIMIT 0 ,1)  gatherNumber,"
                            + "   b.code fiNumber,"
                            + "   (case when a.category = 14 then 1 else a.category end) account,DATE_FORMAT(a.plat_time,'%Y-%m-%d %T') periodStart,DATE_FORMAT(IFNULL(a.pay_time,a.plat_time),'%Y-%m-%d %T') periodEnd,"
                            + "   DATE_FORMAT((case when a.time is not null and a.time <> '' then a.time when a.pay_time is not null then a.pay_time else a.plat_time end),'%Y-%m-%d %T') bizDate,"
                            + "   ifnull(a.target_type,5) settlementType,"
                            + "   (case when ifnull(a.target_user,ifnull(e.username,ifnull(c.cardowen,'无'))) = '' AND e.username is null then '无' when ifnull(a.target_user,ifnull(e.username,ifnull(c.cardowen,'无'))) = '' AND e.username is not null then e.username else ifnull(a.target_user,ifnull(e.username,ifnull(c.cardowen,'无'))) end) bankAccountName,"
                            + "   (case when ifnull(a.target_bank,ifnull(c.cardbank,'无')) = '' then '无' else ifnull(a.target_bank,ifnull(c.cardbank,'无')) end) bankName,"
                            + "   (case when ifnull(a.target_account,ifnull(c.bankcard,'无')) = '' then '无' else ifnull(a.target_account,ifnull(c.bankcard,'无')) end) bankAccount,"
                            + "   (case when ifnull(a.target_Serial,'无') = '' then '无' else ifnull(a.target_Serial,'无') end) serialNumber,"
                            + "   ifnull((CASE WHEN a.remarks = '' THEN '无' ELSE a.remarks end),'无') vnote, "
                            + "   ifnull((SELECT SUM(g.cost) FROM yc_coupon_user_detail g WHERE g.type = 1 and g.financial_id = a.id ),0) discountAmount "
                            + " from financial_receivable_tab a ,financial_settlements_tab b,yc_agreement_tab c,yc_userinfo_tab e "
                            + " where b.correlation_id = a.correlation "
                            + "   and b.ager_id = c.id "
                            + "   and e.id = c.user_id "
                            + "   and a.id = ?  and c.name not like '%测试%'";
                    List<Map<String, Object>> list = db.queryForList(sql, new Object[]{resource_id});
                    if (list.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        Map<String, Object> hyMap = list.get(0);
                        hyMap.put("cu", "025QT06");
                        hyMap.put("company", "025QT06");

                        //检查对应的状态是否是服务订单，如果是服务订单不传送
                        if ("2".equals(StringHelper.get(hyMap, "contractType")) || "3".equals(StringHelper.get(hyMap, "contractType"))) {
                            update(id, 4, "当前为服务订单不进行传送", "当前为服务订单不进行传送");
                            return;
                        }

                        if ("1".equals(StringHelper.get(hyMap, "type"))) {//如果是对应业主合约 那么付款明细需要传入转换为负值进行传 如：支付金额为0，优惠金额为100
                            //需要重新查询对应的合约编号
                            String gatherNumber = StringHelper.get(hyMap, "rentNumber");
                            String amount = StringHelper.get(hyMap, "amount");
                            hyMap.put("gatherNumber", gatherNumber);
                            hyMap.put("billType", 1);
                            hyMap.put("amount", 0);
                            hyMap.put("discountAmount", amount);

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
                            String result = YcApi.sendRequest("addGatherPay", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addGatherPay" + json.toString());
                            } else {
                                update(id, 2, result, "addGatherPay" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        } else {
                            //检查金额是否为负数，如果是负数进行转换
                            String amount = StringHelper.get(hyMap, "amount");//优惠金额
                            String discountAmount = StringHelper.get(hyMap, "discountAmount");//优惠金额

                            if (Float.valueOf(amount) <= 0) {
                                hyMap.put("amount", 0);
                                hyMap.put("discountAmount", Float.valueOf(discountAmount) + (0 - Float.valueOf(amount)));
                            }

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
//								String result = HttpURLConnHelper.execute(url, json.toString());
                            String result = YcApi.sendRequest("addRentRec", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addRentRec" + json.toString());
                            } else {
                                update(id, 2, result, "addRentRec" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        }
                    }
                } else if ("7".equals(notiy_type)) {//付款通知   一般为我们付给业主的金额
                    sql = "select a.id number,a.secondary_type contractType,"
                            + "   1 billType,0 currency,a.cost amount,c.code gatherNumber,c.type,"
                            + "   b.code fiNumber,c.house_id,"
                            + "   (case when a.category = 14 then 1 else a.category end) account,DATE_FORMAT(a.plat_time,'%Y-%m-%d %T') periodStart,DATE_FORMAT(IFNULL(a.pay_time,a.plat_time),'%Y-%m-%d %T') periodEnd,"
                            + "   DATE_FORMAT((case when a.time is not null and a.time <> '' then a.time when a.pay_time is not null then a.pay_time else a.plat_time end),'%Y-%m-%d %T') bizDate,"
                            + "   ifnull(a.target_type,5) settlementType,"
                            + "   (case when ifnull(a.target_user,ifnull(e.username,ifnull(c.cardowen,'无'))) = '' AND e.username is null then '无' when ifnull(a.target_user,ifnull(e.username,ifnull(c.cardowen,'无'))) = '' AND e.username is not null then e.username else ifnull(a.target_user,ifnull(e.username,ifnull(c.cardowen,'无'))) end) bankAccountName,"
                            + "   (case when ifnull(a.target_bank,ifnull(c.cardbank,'无')) = '' then '无' else ifnull(a.target_bank,ifnull(c.cardbank,'无')) end) bankName,"
                            + "   (case when ifnull(a.target_account,ifnull(c.bankcard,'无')) = '' then '无' else ifnull(a.target_account,ifnull(c.bankcard,'无')) end) bankAccount,"
                            + "   (case when ifnull(a.target_Serial,'无') = '' then '无' else ifnull(a.target_Serial,'无') end) serialNumber,"
                            + "   ifnull((CASE WHEN a.remarks = '' THEN '无' ELSE a.remarks end),'无') vnote, "
                            + "   ifnull((SELECT SUM(g.cost) FROM yc_coupon_user_detail g WHERE g.type = 2 and financial_id = a.id ),0) discountAmount "
                            + " from financial_payable_tab a,financial_settlements_tab b,yc_agreement_tab c,yc_userinfo_tab e "
                            + " where b.correlation_id = a.correlation "
                            + "   and b.ager_id = c.id "
                            + "   and e.id = c.user_id "
                            + "   and a.id = ? and c.name not like '%测试%'";
                    List<Map<String, Object>> list = db.queryForList(sql, new Object[]{resource_id});
                    if (list.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        Map<String, Object> hyMap = list.get(0);
                        hyMap.put("cu", "025QT06");
                        hyMap.put("company", "025QT06");

                        //检查对应的状态是否是服务订单，如果是服务订单不传送
                        if ("2".equals(StringHelper.get(hyMap, "contractType")) || "3".equals(StringHelper.get(hyMap, "contractType"))) {
                            update(id, 4, "当前为服务订单不进行传送", "当前为服务订单不进行传送");
                            return;
                        }

                        if ("2".equals(StringHelper.get(hyMap, "type"))) {//如果是对应的租客合约，那么付款明细需要传入转换为负值进行传 如：支付金额为0，优惠金额为100
                            String amount = StringHelper.get(hyMap, "amount");
                            String discountAmount = StringHelper.get(hyMap, "discountAmount");
                            String house_id = StringHelper.get(hyMap, "house_id");
                            String gatherNumber = StringHelper.get(hyMap, "gatherNumber");
                            hyMap.put("amount", 0);
                            hyMap.put("discountAmount", amount);
                            hyMap.put("rentNumber", gatherNumber);
                            hyMap.put("billType", 0);
                            //查询出对应的收房合约
                            sql = "SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = ? and b.house_id = e.house_id AND e.type = 1 and e.name not like '%测试%'  LIMIT 0 ,1";
                            String gatherNumber_ = db.queryForString(sql, new Object[]{house_id});
                            hyMap.put("gatherNumber", gatherNumber_);

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
                            String result = YcApi.sendRequest("addRentRec", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addRentRec" + json.toString());
                            } else {
                                update(id, 2, result, "addRentRec" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        } else {
                            //检查金额是否为负数，如果是负数进行转换
                            String amount = StringHelper.get(hyMap, "amount");//优惠金额
                            String discountAmount = StringHelper.get(hyMap, "discountAmount");//优惠金额

                            if (Float.valueOf(amount) <= 0) {
                                hyMap.put("amount", 0);
                                hyMap.put("discountAmount", Float.valueOf(discountAmount) + (0 - Float.valueOf(amount)));
                            }

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
                            String result = YcApi.sendRequest("addGatherPay", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addGatherPay" + json.toString());
                            } else {
                                update(id, 2, result, "addGatherPay" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        }
                    }
                } else if ("8".equals(notiy_type)) {//新的账单付款通知   一般为租客付款给我们的价格
                    sql = "select CONCAT(a.id,'_',f.id) number,(case when a.secondary_type = 0 then 0 else a.secondary_type end) contractType,\n" +
                        " 0 billType,0 currency,(f.yet_cost+f.discount_cost) amount,c.code rentNumber,c.type,\n" +
                        "(SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = c.house_id and b.house_id = e.house_id AND e.type = 1 LIMIT 0 ,1)  gatherNumber,\n" +
                        " b.code fiNumber,\n" +
                        " (case when a.category = 14 then 1 else a.category end) account,DATE_FORMAT(a.plat_time,'%Y-%m-%d %T') periodStart,DATE_FORMAT(IFNULL(f.pay_time,a.plat_time),'%Y-%m-%d %T') periodEnd,\n" +
                        " DATE_FORMAT(ifnull(f.pay_time,a.plat_time),'%Y-%m-%d %T') bizDate,\n" +
                        " ifnull((case when f.target_type ='支付宝服务号支付' then '0' \n" +
                        "						  when f.target_type ='银卡转帐' then '1'  \n" +
                        "							when f.target_type ='微信公众号支付' then '2'  \n" +
                        "							when f.target_type ='支付宝转账' then '6'  \n" +
                        "							when f.target_type ='微信公众号' then '2'  \n" +
                        "							when f.target_type ='支付宝app支付' then '4'  \n" +
                        "                                                       when f.target_type ='微信app支付' then '3'      " +
                        "							when f.target_type ='支付宝网页支付' then '0'  \n" +
                        "							when f.target_type ='支付宝转账' then '6' \n" +
                        "							when f.target_type ='银行转账' then '1' 	\n" +
                        "							else f.target_type end),5) settlementType,\n" +
                        " ifnull((case when f.username = '' or f.username is null then e.username else f.username end),'无') bankAccountName,\n" +
                        " ifnull((case when f.target_bank = '' or f.target_bank is null then '无' else f.target_bank end),'无') bankName,\n" +
                        " ifnull((case when f.target_account = '' or f.target_account is null then '无' else f.target_account end),'无') bankAccount,\n" +
                        " ifnull((case when f.target_Serial = '' then '无' else f.target_Serial end),'无') serialNumber,\n" +
                        " ifnull((CASE WHEN f.remark = '' THEN '无' ELSE a.remarks end),'无') vnote, \n" +
                        " ifnull(f.discount_cost,0) discountAmount \n" +
                        "from financial_receivable_tab a ,financial_settlements_tab b,yc_agreement_tab c,yc_userinfo_tab e,financial_yet_tab f \n" +
                        "where b.correlation_id = a.correlation \n" +
                        " and b.ager_id = c.id \n" +
                        " and f.id = ? \n" +
                        " and e.id = c.user_id \n" +
                        " and a.id = f.fin_id and f.type = 1 and c.name not like '%测试%'";
                    logger.debug("sql:" + StringHelper.getSql(sql, new Object[]{resource_id}));
                    List<Map<String, Object>> list = db.queryForList(sql, new Object[]{resource_id});
                    if (list.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        Map<String, Object> hyMap = list.get(0);
                        
                        if("无".equals(StringHelper.get(hyMap, "bankName"))){
                            int settlementType = Integer.valueOf(StringHelper.get(hyMap, "settlementType"));
                            if(settlementType == 0||settlementType == 6||settlementType == 4){
                                hyMap.put("bankName", "支付宝");
                            }else if(settlementType == 2||settlementType == 3){
                                hyMap.put("bankName", "微信");
                            }
                        }
                        
                        hyMap.put("cu", "025QT06");
                        hyMap.put("company", "025QT06");

                        sql = "select count(1) from yc_notiy_info a where a.resource_id = ? and a.notiy_type = 6 and a.notiy_state = 2";
                        if(db.queryForInt(sql,new Object[]{StringHelper.get(hyMap, "number").split("_")[0]}) > 0){
                            update(id, 4, "当前账单存在账单明细被整体传送，不在进行传送", "当前账单存在账单明细被整体传送，不在进行传送");
                            return;
                        }
                        
                        if (Double.valueOf(StringHelper.get(hyMap, "amount")) == 0D) {
                            update(id, 4, "当前账单金额为0不需要进行传递", "当前账单金额为0不需要进行传递");
                            return;
                        }

                        //检查对应的状态是否是服务订单，如果是服务订单不传送
                        if ("2".equals(StringHelper.get(hyMap, "contractType")) || "3".equals(StringHelper.get(hyMap, "contractType"))) {
                            update(id, 4, "当前为服务订单不进行传送", "当前为服务订单不进行传送");
                            return;
                        }

                        if ("1".equals(StringHelper.get(hyMap, "type"))) {//如果是对应业主合约 那么付款明细需要传入转换为负值进行传 如：支付金额为0，优惠金额为100
                            //需要重新查询对应的合约编号
                            String gatherNumber = StringHelper.get(hyMap, "rentNumber");
                            String amount = StringHelper.get(hyMap, "amount");
                            hyMap.put("gatherNumber", gatherNumber);
                            hyMap.put("billType", 1);
                            hyMap.put("amount", 0);
                            hyMap.put("discountAmount", amount);

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
                            String result = YcApi.sendRequest("addGatherPay", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addGatherPay" + json.toString());
                            } else {
                                update(id, 2, result, "addGatherPay" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        } else {
                            //检查金额是否为负数，如果是负数进行转换
                            String amount = StringHelper.get(hyMap, "amount");//优惠金额
                            String discountAmount = StringHelper.get(hyMap, "discountAmount");//优惠金额

                            if (Float.valueOf(amount) <= 0) {
                                hyMap.put("amount", 0);
                                hyMap.put("discountAmount", Float.valueOf(discountAmount) + (0 - Float.valueOf(amount)));
                            }

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
//								String result = HttpURLConnHelper.execute(url, json.toString());
                            String result = YcApi.sendRequest("addRentRec", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addRentRec" + json.toString());
                            } else {
                                update(id, 2, result, "addRentRec" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        }
                    }
                } else if ("9".equals(notiy_type)) {//新的付款通知   一般为我们付给业主的金额
                    sql = "select CONCAT(a.id,'_',f.id) number,(case when a.secondary_type = 0 then 0 else a.secondary_type end) contractType,"
                            + "   0 billType,0 currency,(f.yet_cost+f.discount_cost) amount,c.code rentNumber,c.type,"
                            + "	(SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = c.house_id and b.house_id = e.house_id AND e.type = 1 LIMIT 0 ,1)  gatherNumber,"
                            + "   b.code fiNumber,"
                            + "   (case when a.category = 14 then 1 else a.category end) account,DATE_FORMAT(a.plat_time,'%Y-%m-%d %T') periodStart,DATE_FORMAT(IFNULL(f.pay_time,a.plat_time),'%Y-%m-%d %T') periodEnd,"
                            + "   DATE_FORMAT(ifnull(f.pay_time,a.plat_time),'%Y-%m-%d %T') bizDate,"
                            + "   ifnull((case when f.target_type like '%支付宝%' then '0' when f.target_type like '%微信%' then 2 else 5 end),5) settlementType,"
                            + "   ifnull((case when f.username = '' then '无' else f.username end),'无') bankAccountName,"
                            + "   ifnull((case when f.target_bank = '' then '无' else f.target_bank end),'无') bankName,"
                            + "   ifnull((case when f.target_account = '' then '无' else f.target_account end),'无') bankAccount,"
                            + "   ifnull((case when f.target_Serial = '' then '无' else f.target_Serial end),'无') serialNumber,"
                            + "   ifnull((CASE WHEN f.remark = '' THEN '无' ELSE a.remarks end),'无') vnote, "
                            + "   ifnull(f.discount_cost,0) discountAmount "
                            + " from financial_payable_tab a ,financial_settlements_tab b,yc_agreement_tab c,yc_userinfo_tab e,financial_yet_tab f "
                            + " where b.correlation_id = a.correlation "
                            + "   and b.ager_id = c.id "
                            + "   and f.id = ?"
                            + "   and e.id = c.user_id "
                            + "   and a.id = f.fin_id and f.type = 2 and c.name not like '%测试%'";

                    List<Map<String, Object>> list = db.queryForList(sql, new Object[]{resource_id});
                    if (list.isEmpty()) {
                        update(id, 2, "未查询到数据信息", json.toString());
                    } else {
                        Map<String, Object> hyMap = list.get(0);
                        hyMap.put("cu", "025QT06");
                        hyMap.put("company", "025QT06");

                        sql = "select count(1) from yc_notiy_info a where a.resource_id = ? and a.notiy_type = 7 and a.notiy_state = 2";
                        if(db.queryForInt(sql,new Object[]{StringHelper.get(hyMap, "number").split("_")[0]}) > 0){
                            update(id, 4, "当前账单存在账单明细被整体传送，不在进行传送", "当前账单存在账单明细被整体传送，不在进行传送");
                            return;
                        }
                        
                        if (Double.valueOf(StringHelper.get(hyMap, "amount")) == 0D) {
                            update(id, 4, "当前账单金额为0不需要进行传递", "当前账单金额为0不需要进行传递");
                            return;
                        }

                        //检查对应的状态是否是服务订单，如果是服务订单不传送
                        if ("2".equals(StringHelper.get(hyMap, "contractType")) || "3".equals(StringHelper.get(hyMap, "contractType"))) {
                            update(id, 4, "当前为服务订单不进行传送", "当前为服务订单不进行传送");
                            return;
                        }

                        if ("2".equals(StringHelper.get(hyMap, "type"))) {//如果是对应的租客合约，那么付款明细需要传入转换为负值进行传 如：支付金额为0，优惠金额为100
                            String amount = StringHelper.get(hyMap, "amount");
                            String discountAmount = StringHelper.get(hyMap, "discountAmount");
                            String house_id = StringHelper.get(hyMap, "house_id");
                            String gatherNumber = StringHelper.get(hyMap, "gatherNumber");
                            hyMap.put("amount", 0);
                            hyMap.put("discountAmount", amount);
                            hyMap.put("rentNumber", gatherNumber);
                            hyMap.put("billType", 0);
                            //查询出对应的收房合约
                            sql = "SELECT e.code FROM yc_houserank_tab b,yc_agreement_tab e WHERE b.id = ? and b.house_id = e.house_id AND e.type = 1 and e.name not like '%测试%'  LIMIT 0 ,1";
                            String gatherNumber_ = db.queryForString(sql, new Object[]{house_id});
                            hyMap.put("gatherNumber", gatherNumber_);

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
                            String result = YcApi.sendRequest("addRentRec", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addRentRec" + json.toString());
                            } else {
                                update(id, 2, result, "addRentRec" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        } else {
                            //检查金额是否为负数，如果是负数进行转换
                            String amount = StringHelper.get(hyMap, "amount");//优惠金额
                            String discountAmount = StringHelper.get(hyMap, "discountAmount");//优惠金额

                            if (Float.valueOf(amount) <= 0) {
                                hyMap.put("amount", 0);
                                hyMap.put("discountAmount", Float.valueOf(discountAmount) + (0 - Float.valueOf(amount)));
                            }

                            list.clear();
                            list.add(hyMap);
                            json.put("bills", JSONArray.fromObject(list));
                            logger.debug("请求信息：" + json.toString());
                            String result = YcApi.sendRequest("addGatherPay", json.toString(), true);
                            logger.debug("返回参数值");
                            if (YcApiRetCode.SUCCESS.equals((String) JsonUtil.toMap(result).get("code"))) {
                                update(id, 1, result, "addGatherPay" + json.toString());
                            } else {
                                update(id, 2, result, "addGatherPay" + json.toString());
                            }
                            logger.debug("返回数据信息" + result);
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("金蝶同步错误", e);
                JdTask.isLogin = false;
                JdTask.login();
            } finally {
                if (latch != null) {
                    latch.countDown();
                }
            }
        } else {
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    /**
     * 更新状态信息
     *
     * @state 1成功2失败
     */
    public void update(String id, int state, String remark, String send_remark) {
        String date = DateHelper.getToday("yyyy-MM-dd HH:mm:ss");

        
        String sql = "UPDATE yc_notiy_info a SET a.notiy_state = ?,a.noti_succeed_time = ? ,a.noti_cnt = a.noti_cnt +1,a.noti_time = NOW(),a.noti_remark = ?,a.send_remark=? WHERE a.id = ?";
        db.update(sql, new Object[]{state + 1, state == 1 ? date : "", remark, send_remark, id});
    }

    /**
     * 收房通知
     *
     * @param id
     * @param resource_id
     */
    public void sftz(String id, String resource_id) {
        //查询出合约通知信息

    }

}
