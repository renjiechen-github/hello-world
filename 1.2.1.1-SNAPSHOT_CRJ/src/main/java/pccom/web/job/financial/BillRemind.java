/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pccom.web.job.financial;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;

/**
 *
 * 账单催租，微信推送
 *
 * @author eliayng
 */
@Service("billRemind")
public class BillRemind {

    @Resource(name = "dbHelper")
    private DBHelperSpring db;

    public final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    /**
     * 执行入口
     *
     * @description
     * @author 雷杨 Jun 11, 2014
     */
    @Scheduled(cron = "0 0 9 ? * *") //每天9点跑数据
    @Transactional
    public void execute() {
        String sql = "select a.name,b.user_id,a.cost,DATE_FORMAT(a.plan_time,'%Y-%m-%d') plan_time,(case when a.resource_type = 1 then '租金账单' when a.resource_type = 2 then '水电燃账单' \n"
                + "	when a.resource_type = 3 then '退租账单' when a.resource_type = 4 then '增值保洁账单' when a.resource_type = 5 then '增值维修账单' \n"
                + "	when a.resource_type = 6 then '其他账单' when a.resource_type = 7 then '押金账单' else '其他账单' end )resource_type_name \n"
                + "	from financial_bill_tab a,yc_agreement_tab b ,yc_userinfo_tab c\n"
                + "	where a.ager_id = b.id \n"
                + "		and b.user_id = c.id \n"
                + "		and a.type = 1\n"
                + "		and a.state = 1 \n"
                + "		and a.cost > a.yet_cost+a.discounts_cost \n"
                + "		and a.resource_type <>8\n"
                + "		and DATE_FORMAT(DATE_ADD(now(),INTERVAL ? day),'%Y%m%d') = DATE_FORMAT(a.plan_time,'%Y%m%d') ";
        List<Map<String, Object>> list = db.queryForList(sql, new Object[]{SystemConfig.getValue("noti_bill_day")});
        int cnt = list.size();
        for (int i = 0; i < cnt; i++) {
            String user_id = StringHelper.get(list.get(i), "user_id");
//            if(!"6193".equals(user_id)){
//                continue;
//            }
            String wxuser = StringHelper.get(list.get(i), "wxuser");
            String cost_ = StringHelper.get(list.get(i), "cost");
            String plan_time = StringHelper.get(list.get(i), "plan_time");
            String resource_type_name = StringHelper.get(list.get(i), "resource_type_name");
            String name = StringHelper.get(list.get(i), "name");
            try {
                if ("1".equals(SystemConfig.getValue("SEND_WX_TS"))) {
                    sql = "insert into yc_send_msg_cz(url,user_id,name,cost,plan_time,send_time) values(?,?,?,?,?,now())";
                    if (db.update(sql, new Object[]{"https://m.room1000.com/client/new/index.html?open=client.new.server.payFees.payFees&uid=" + user_id, user_id,
                        name, cost_, plan_time}) > 0) {
//                        WeiXinRequest.sendBillSuc("https://m.room1000.com/client/new/index.html?open=client.new.server.payFees.payFees&uid=" + user_id,
//                            user_id, Constants.YCQWJ_API, "系统检测到您有账单未完成支付，请及时支付，如已支付请忽略本信息，祝您生活愉快。",
//                            name, cost_, plan_time, "感谢您的使用，缴费完毕后，请查询系统账单是否消除，如有疑问，请联系客服。");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
