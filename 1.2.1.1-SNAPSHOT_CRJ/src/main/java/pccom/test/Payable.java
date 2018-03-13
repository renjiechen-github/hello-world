package pccom.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import pccom.common.util.Batch;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.DateHelper;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;
import com.room1000.suborder.houselookingorder.node.StaffReviewNode;

/**
 * 执行支付老书记信息
 * @author 雷杨
 *
 */
@SuppressWarnings("rawtypes")
public class Payable {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(Payable.class);
	public StringHelper str = new StringHelper();
	
	@SuppressWarnings("unchecked")
	public void executeState(){
		DBHelperSpring db = (DBHelperSpring)SpringHelper.getBean("dbHelper");
		logger.debug("----------------------------生成完成："+db.doInTransaction(new Batch() {
			@Override
			public Object execute() throws Exception {
				/***********************生成财务项目信息*******************/
				//先进行删除操作
//				String sql = "delete from financial_settlements_tab";
//				this.update(sql);
				//插入财务项目信息
				String sql = "SELECT a.id,SUM(c.`cost`) cost,a.`cost` sjcost,a.`pay_type`,a.`cost_a`,a.`begin_time`,a.`end_time`,TIMESTAMPDIFF(MONTH,CONCAT(DATE_FORMAT(a.begin_time,'%Y-%m'),'-01'),CONCAT(DATE_FORMAT(a.end_time,'%Y-%m'),'-01')) months "+ 
					"	FROM yc_agreement_tab a,yc_settlement_tab b,yc_bill_tab c"+
					"	WHERE a.`type` = 1 "+
					"	  AND a.`status` IN (2,3)"+
					"	  AND a.id = b.`agreement_id`"+
					"	  AND b.id = c.`set_id`"+
					"	  AND c.type = 0"+
					"	  AND c.`bill_status` = 1"+
					"	 GROUP BY a.id,a.`pay_type`,a.`begin_time`,a.`end_time`,a.`cost`,a.`cost_a` ";
				//将支付合约进行一条一条进行拆分出来
				List<Map<String,Object>> list = this.queryForList(sql);
				
				//进行拆分数据信息
				for(int i=0;i<list.size();i++){
					String id = str.get(list.get(i), "id");//321321|121|21321|3213|
					String cost = str.get(list.get(i), "cost");//总月份数
					String sjcost = str.get(list.get(i), "sjcost");//开始计划付款时间
					String pay_type = str.get(list.get(i), "pay_type");//目标银行
					String cost_a = str.get(list.get(i), "cost_a");//目标用户(用户)
					String begin_time = str.get(list.get(i), "begin_time");//操作人id
					String end_time = str.get(list.get(i), "end_time");//合约id
					String months = str.get(list.get(i), "months");//生成明细对应的类目id financial_category_tab中id
					//实际需要支付金额
					String[] cost_as = cost_a.split("\\|");
					int nowMonth = 0;//记录当前达到的月份
					//循环处理数据
					int cnt = Integer.valueOf(months)/Integer.valueOf(pay_type)+1;
					int je = 0;//初次的金额
					int years = 0;//记录年份
					for(int j=0;j<cnt;j++){
						if(Float.valueOf(je+"") >= 0-Float.valueOf(cost)){
							continue;
						}
						//可能存在情况是 安装当前支付月份分解出的日期落在两个不同金额之内，这样就需要进行分别插入两条记录信息
						nowMonth += Integer.valueOf(pay_type);
						if(years >= cost_as.length){
							continue;
						}
						String cost_ =  cost_as[years];
						je += Integer.valueOf(cost_)*Integer.valueOf(pay_type);
						sql = "SELECT a.`id` FROM financial_payable_tab a  WHERE a.`secondary` = ? ORDER BY a.`plat_time` LIMIT ?,1";
						String receivable_id = this.queryForString(sql,new Object[]{id,j});
						this.update("UPDATE financial_payable_tab a SET a.`status` = 1 , a.`pay_time` = a.`plat_time` WHERE a.id = ?",
								new Object[]{receivable_id});
						if(nowMonth >= 12){
							years ++;
							nowMonth = 0;
						}
					}
					
				}
				return 1;
			}
		}));
	}
	
	@SuppressWarnings("unchecked")
	public void execute(){
		
		DBHelperSpring db = (DBHelperSpring)SpringHelper.getBean("dbHelper");
		//开始执行更新支付老数据信息
		
		logger.debug("----------------------------才分完成："+db.doInTransaction(new Batch() {

			@Override
			public Object execute() throws Exception {
				/***********************生成财务项目信息*******************/
				//先进行删除操作
//				String sql = "delete from financial_settlements_tab";
//				this.update(sql);
				//插入财务项目信息
				String sql = "INSERT INTO financial_settlements_tab "+
						"SELECT a.id,1,CONCAT(a.`name`,'_',a.sbcode),0,0,a.create_time,'',(CASE WHEN a.`status` = 2 THEN 1 ELSE 0 END),null FROM yc_agreement_tab a,yc_house_tab b WHERE a.`house_id` = b.`id` AND a.`type` = 1 AND a.`isdelete` = 1 AND a.`status` IN(2,3) ";
				this.update(sql);
				
				sql = "SELECT " +
						"  a.id, " + 
						"  CONCAT(a.`name`,'_',a.sbcode) name, " + 
						"  a.`user_id`, " + 
						"  a.`user_mobile`, " + 
						"  DATE_FORMAT( " + 
						"    a.`begin_time`, " + 
						"    '%Y-%m-%d %T' " + 
						"  ) begin_time, " + 
						"  DATE_FORMAT( " + 
						"    a.`end_time`, " + 
						"    '%Y-%m-%d' " + 
						"  ) end_time, " + 
						"  a.cost_a, " + 
						"  DATE_FORMAT( " + 
						"    a.`create_time`, " + 
						"    '%Y-%m-%d %T' " + 
						"  ) create_time, " + 
						"  a.bankcard, " + 
						"  a.cardowen, " + 
						"  a.pay_type, " + 
						"  a.cost_a, " + 
						"  DATE_FORMAT( " + 
						"    a.`pay_time`, " + 
						"    '%Y-%m-%d' " + 
						"  ) pay_time,TIMESTAMPDIFF(MONTH,a.`begin_time`,a.`end_time`) months,a.free_period " + 
						"FROM " + 
						"  yc_agreement_tab a " + 
						"WHERE a.`type` = 1 " + 
						"  AND a.`isdelete` = 1  AND a.`status` IN(2,3) ";

				//将支付合约进行一条一条进行拆分出来
				List<Map<String,Object>> list = this.queryForList(sql);
				//进行拆分数据信息
				for(int i=0;i<list.size();i++){
					String cost = str.get(list.get(i), "cost_a");//321321|121|21321|3213|
					String months = str.get(list.get(i), "months");//总月份数
					String plat_time = str.get(list.get(i), "begin_time");//开始计划付款时间
					String target_bank = str.get(list.get(i), "bankcard");//目标银行
					String target_user = str.get(list.get(i), "cardowen");//目标用户(用户)
					String target_type = "1";//发生类型(1:银卡转帐,3:网络平台,5:其它)
					String operid = str.get(list.get(i), "operid");//操作人id
					String agreement_id = str.get(list.get(i), "id");//合约id
					String category = str.get(list.get(i), "id");//生成明细对应的类目id financial_category_tab中id
					String pay_type = str.get(list.get(i), "pay_type");//支付类型：0全额付 1月付3季付6半年付12年付 其他为对应月份
					String free_period = str.get(list.get(i), "free_period");//免租期
					String end_time = str.get(list.get(i), "end_time");
					String sbname = str.get(list.get(i), "name");
					free_period = "".equals(free_period)?"0":free_period;
					if("".equals(operid)){
						operid = "1";
					}
					//财务项目id
					int settlements_id = Integer.valueOf(agreement_id);
					//根据金额来进行拆分总插入次数
					String[] costs = cost.split("\\|");
					//根据年份与月份计算出循环次数
					int monthnum = Integer.valueOf(months);//总月份数量
					pay_type = "0".equals(pay_type)?months:pay_type;
					int cnt = monthnum/Integer.valueOf(pay_type)+(monthnum%Integer.valueOf(pay_type)==0?0:1);//循环次数
					String startTime = plat_time;
					int years = 0;//记录年份
					int nowMonth = 0;//记录当前达到的月份
					for(int j=0;j<cnt;j++){//根据需要插入的条数进行循环
						logger.debug("开始插入：：：：");
						//强字符串日期改成data
						Date date = DateHelper.getStringDate(startTime, "yyyy-MM-dd");
						String enddate = DateHelper.addMonthDate(date, Integer.valueOf(pay_type));
						if(Integer.valueOf(enddate.replace("-", ""))>Integer.valueOf(end_time.replace("-", ""))){
							enddate = end_time;
						}
						//可能存在情况是 安装当前支付月份分解出的日期落在两个不同金额之内，这样就需要进行分别插入两条记录信息
						nowMonth += Integer.valueOf(pay_type);
						
						String cost_ =  costs[years];
						String name = startTime+"至"+enddate+"房租";
						if(j == 0){
							this.update("INSERT INTO financial_payable_tab(`name`,`correlation`,`secondary_type`,`secondary`,`category`,`create_time`,`cost`,`plat_time`,`update_time`,`target_bank`,`target_user`,`target_type`,`operator`,`remarks`,`status`,`BankSlip`,start_time,end_time)VALUES(?,?,?,?,?,NOW(),?,?,NOW(),?,?,?,?,?,1,?,DATE_SUB(DATE_FORMAT(?,'%Y-%m-%d'), interval ? day),DATE_FORMAT(?,'%Y-%m-%d'))",
									new Object[]{sbname,settlements_id,0,agreement_id,1,Integer.valueOf(cost_)*Integer.valueOf(pay_type),startTime,"".equals(target_bank)?"":target_bank,target_user,target_type,operid,name,"",startTime,free_period,enddate});
						}else{
							this.update("INSERT INTO financial_payable_tab(`name`,`correlation`,`secondary_type`,`secondary`,`category`,`create_time`,`cost`,`plat_time`,`update_time`,`target_bank`,`target_user`,`target_type`,`operator`,`remarks`,`status`,`BankSlip`,start_time,end_time)VALUES(?,?,?,?,?,NOW(),?,?,NOW(),?,?,?,?,?,1,?,DATE_FORMAT(?,'%Y-%m-%d'),DATE_FORMAT(?,'%Y-%m-%d'))",
									new Object[]{sbname,settlements_id,0,agreement_id,1,Integer.valueOf(cost_)*Integer.valueOf(pay_type),startTime,"".equals(target_bank)?"":target_bank,target_user,target_type,operid,name,"",startTime,enddate});
						}
						
						startTime = enddate;
						if(nowMonth >= 12){
							years ++;
							nowMonth = 0;
						}
					}
				}
				return 1;
			}
		}));
	}
}
