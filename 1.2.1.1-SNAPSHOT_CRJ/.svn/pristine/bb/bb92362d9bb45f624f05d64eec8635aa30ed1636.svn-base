package pccom.web.job;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;

import pccom.common.util.DBHelperSpring;
import pccom.common.util.StringHelper;

/**
 * 催租定时任务
 * @author admin
 *
 */
@Service("cleanTmpJob")
public class UrgeZu {

	public final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	@Resource(name="dbHelper")
	protected DBHelperSpring db;
	
	// @Scheduled(cron = "0 0 20 * * ?") //每天定点执行一次 
	public void execute (){
		logger.debug("发送短信---");
		try{
			String sql = "select f.username u,f.mobile,a.cost c,a.remarks t,e.name a "+
						"  from financial_receivable_tab a,"+
						"       financial_category_tab b,"+
						"       yc_account_tab c,"+
						"       yc_agreement_tab e,"+
						"       yc_userinfo_tab f,"+
						"       yc_account_tab g,"+
						"       financial_settlements_tab e1"+ 
						"  where a.correlation = e1.correlation_id"+ 
						"  and a.secondary = e.id "+
						"  and e.user_id = f.id "+
						"  and a.category = b.id "+
						"  AND b.`status` = 1 "+
						"  AND e.`agents` = g.`id`"+ 
						"  and a.operator = c.`id` "+
						"  and a.secondary_type = '1'"+ 
						"  AND a.category = 1"+
						"  and a.status = '0'  "+
						"  and DATE_SUB(a.start_time,INTERVAL 15 DAY) <= NOW()"+ 
						"  and a.status = 0 "+
						"  and a.isdelete = 1 "+
						"  and not exists(select 1 from yc_task_financial_receivable b where b.payable_tab_id = a.id AND b.`is_over` = 0) ";
			List<Map<String,Object>> list = db.queryForList(sql);
			int cnt = list.size();
			logger.debug("本次需要发送的催租短信条数为："+cnt);
			
			for(int i=0;i<cnt;i++){
	//			String username = StringHelper.get(list.get(i), "username");
				String mobile = StringHelper.get(list.get(i), "mobile");
				logger.debug("发送催租短信："+list.get(i));
				list.get(i).remove("mobile");
	//			String cost = StringHelper.get(list.get(i), "cost");
	//			String remarks = StringHelper.get(list.get(i), "remarks");
	//			String name = StringHelper.get(list.get(i), "name");
				
				SmsSendMessage.smsSendMessage(mobile, (Map)list.get(i), SmsSendContants.PAY_NOTIFY);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception e){
			logger.debug("催租短信发送失败：",e);
		}
	}
}
