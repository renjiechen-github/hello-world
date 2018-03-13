package pccom.web.interfaces;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.room1000.workorder.dto.WorkOrderDto;

import pccom.common.util.DateHelper;
import pccom.web.server.BaseService;

/**
 * 施工对外接口
 * @author 刘飞
 *
 */
@Service("houseInterfaces")
public class HouseInterfaces extends BaseService {
	
	/**
	 * 房源释放接口
	 * @author 刘飞
	 * @param{ WorkOrderDto {appointmentDate,takeHouseOrderId}
	 *	}
	 * @return   0失败   1  成功 -1当前房源存在未释放的出租合约
	 * 更改合约状态为失效清算中、赋值退租日期、释放房源
	 */
	@Transactional
	public int managerDone(WorkOrderDto order){
		Map<String, Object> agreeMap=getAgreement(order.getTakeHouseOrderId().toString());
		String house_id=str.get(agreeMap,"house_id");//房源Id
		String sql=getSql("houseInterfaces.getAgreeCount");
		int res = db.queryForInt(sql, new Object[]{house_id});
		if (res>0)
		{
			return -1;
			//当前房源下存在出租合约没有退租完成；
		}
		// 修改当前房源状态为已失效
		if (houseRelease(house_id) != 1) {
           return 0;
		}
		// 修改当前房源下的所有租赁房源进入失效状态
		if (rankRelease(house_id) != 1) {
			return 0;
		}
		// 修改合约退租日期---进入失效待清算
		
		return agreeRelease(DateHelper.getDateString(order.getAppointmentDate(), "yy-MM-dd HH:mm:ss"),String.valueOf(order.getTakeHouseOrderId()));
	}
	
	/**
	 * 判断是否允许新增业主退租订单
	 * @author 刘飞
	 * @param{ WorkOrderDto {takeHouseOrderId}
	 *	}
	 * @return  res >0 不允许新增当前业主退租订单
	 */
	public int getAgreeCount(WorkOrderDto order) {
		Map<String, Object> agreeMap=getAgreement(order.getTakeHouseOrderId().toString());
		String house_id=str.get(agreeMap,"house_id");//房源Id
		String sql=getSql("houseInterfaces.getAgreeCount");
		return db.queryForInt(sql, new Object[]{house_id});
	}
	
	
	/**
	 * 查询出合约信息
	 * @param agreeId 合约Id
	 * @return Map<String, Object>
	 * */
	public Map<String, Object> getAgreement(String agreeId)
    {
		String sql =getSql("houseInterfaces.getAgree");// "SELECT * FROM yc_agreement_tab a WHERE a.`id`=? ";
		Map<String, Object> agreeMap = db.queryForMap(sql, new Object[] { agreeId});
		return agreeMap;
	}
	
	
	/**
	 * @param house_id 房源Id
	 * @return int 1成功 其他失败
	 * */
	private int houseRelease(String house_id)
	{
	    String	sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", "house_status=10");
		return db.update(sql, new Object[] { house_id });
	}
	/**
	 * @param house_id 房源Id
	 * @return int 1成功 其他失败
	 * */
	private int rankRelease(String house_id) {
		String sql = getSql("basehouse.rankHosue.updateRankHouseStatus").replace("####", "rank_status=6");// "UPDATE yc_houserank_tab a SET a.`rank_status`=6  WHERE a.`house_id`=?";
		return db.update(sql, new Object[] { house_id });
	}
	/**
	 * @param {appointmentDate 退租日期，takeHouseOrderId 合约ID}
	 * @return int 1成功 其他失败
	 * */
	private int agreeRelease(String appointmentDate,String takeHouseOrderId) {
		String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " status = ? , checkouttime=? ");
		return db.update(sql,new Object[]{"7",appointmentDate,takeHouseOrderId});
	}
	
	/**
	 * 计算收房退租租金
	 * @author 刘飞
	 * @param{ WorkOrderDto {
	 * takeHouseOrderId ,appointmentDate
	 * } } 
	 * @return  Map<String, String>  retrunMap<"status", "-1">失败   retrunMap<"status", "1">  成功
	 * 计算退租应收或应付的租金
	 * @throws ParseException 
	 */
	public Map<String, String> calculate(WorkOrderDto order) throws ParseException {
		Map<String, String> retrunMap=new HashMap<>();
		//合约信息
		Map<String, Object> agreeMap=getAgreement(order.getTakeHouseOrderId().toString());
		String sql="SELECT sum(cost) FROM financial_payable_tab a WHERE a.secondary = ? and a.category=1 and a.`status`>0 and a.secondary_type=0";
		String allCost = db.queryForString(sql, new Object[] { order.getTakeHouseOrderId() });// 已付房东租金总计
		if (allCost == null || "".equals(allCost)) {
			allCost = "0";
		}
		if ("".equals(str.get(agreeMap, "cost_a"))){
			retrunMap.put("status", "-1");
			return retrunMap;
		}
		String[]  costa=str.get(agreeMap, "cost_a").split("\\|");
		List<String> cost_a = new ArrayList(Arrays.asList(costa));
		
		// 起始时间
		Date begin_time = DateHelper.getStringDate(str.get(agreeMap, "begin_time"), "yyyy-MM-dd");//开始日期
		Date end_time = DateHelper.getStringDate(str.get(agreeMap, "end_time"), "yyyy-MM-dd");//结束日期
		Date appointmentDate =order.getAppointmentDate();
		int years=compareDate(str.get(agreeMap, "begin_time"),str.get(agreeMap, "end_time"),2);//相差年数
		
		//2800|2800|2900|3000|3066|
		//年数-周期金额数不等于0 则将剩下的年付金额赋值最后周期的房租金额
		int cha=years-cost_a.size();
		for (int i = 0; i < cha+1; i++) {
			cost_a.add(costa[costa.length-1]);
		}
		int yearTo=	compareDate(str.get(agreeMap, "begin_time"),DateHelper.getDateString(order.getAppointmentDate(), "yyyy-MM-dd"),2);//退租日期与开始日期相差年数
		
		String nowData = DateHelper.addMonthDate(begin_time, (yearTo*12));// 取出当前年份日期
		float costYear = 0;//年数金额
		
		// 当前已租年数*12*月租金
		for (int i = 0; i < yearTo; i++) {
			 costYear += 12 * Float.parseFloat(cost_a.get(i));
		}
		// 当前已租月份*月租金
		float costMonth = 0;//月份金额
		int month=getMonthSpace(nowData,DateHelper.getDateString(order.getAppointmentDate(), "yyyy-MM-dd"));
		costMonth = month * Float.parseFloat(cost_a.get(yearTo));
		
		//当前已租日期*日租金
		float costDay = 0;//日金额
		//退租日期+30-当前日期
		int day=0;
	    if (Integer.parseInt(order.getAppointmentDate().toString().substring(8,10))>Integer.parseInt(nowData.substring(8,10))) {
		    day=Integer.parseInt(order.getAppointmentDate().toString().substring(8,10))-Integer.parseInt(nowData.substring(8,10));
	    }else if (Integer.parseInt(order.getAppointmentDate().toString().substring(8,10))<Integer.parseInt(nowData.substring(8,10))){
		    day=Integer.parseInt(order.getAppointmentDate().toString().substring(8,10))-Integer.parseInt(nowData.substring(8,10))+30;
	    }
		costDay = day * Float.parseFloat(cost_a.get(yearTo))/30;
		//应退或应收=已交费用-（costDay+costMonth+costYear）负数为应收正数为应退
		float countCost = Float.parseFloat(allCost)-(costDay+costMonth+costYear);
        retrunMap.put("year", String.valueOf(yearTo));
        retrunMap.put("month", String.valueOf(yearTo));
        retrunMap.put("day", String.valueOf(yearTo));
        retrunMap.put("countCost", String.valueOf(countCost));
		return retrunMap;
	}
	
	/**
	 * 计算两个日期相差年数  
	 * */
	private static  int yearDateDiff(Date startDate,Date endDate){  
	   Calendar calBegin = Calendar.getInstance(); //获取日历实例  
	   Calendar calEnd = Calendar.getInstance();  
	   calBegin.setTime(startDate); //字符串按照指定格式转化为日期  
	   calEnd.setTime(endDate);  
	   return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);  
	}  
	
	/**
	 * 返回日期间的月份数
	 * */
	private static  int getMonthSpace(String date1, String date2) throws ParseException {  
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sdf.parse(date2));
		c2.setTime(sdf.parse(date1));
	    if(c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
	    int year1 = c1.get(Calendar.YEAR);
	    int year2 = c2.get(Calendar.YEAR);
	    int month1 = c1.get(Calendar.MONTH);
	    int month2 = c2.get(Calendar.MONTH);
	    int day1 = c1.get(Calendar.DAY_OF_MONTH);
	    int day2 = c2.get(Calendar.DAY_OF_MONTH);
	    // 获取年的差值 假设 d1 = 2015-8-16  d2 = 2011-9-30
	    int yearInterval = year1 - year2;
	    // 如果 d1的 月-日 小于 d2的 月-日 那么 yearInterval-- 这样就得到了相差的年数
	    if(month1 < month2 || month1 == month2 && day1 < day2) yearInterval --;
	    // 获取月数差值
	    int monthInterval =  (month1 + 12) - month2  ;
	    if(day1 < day2) monthInterval --;
	    monthInterval %= 12;
	    return yearInterval * 12 + monthInterval;
	 }
	
	
	/**

	 * @param date1 需要比较的时间 不能为空(null),需要正确的日期格式 ,如：2009-09-12 

	 * @param date2 被比较的时间 为空(null)则为当前时间 

	 * @param stype 返回值类型 0为多少天，1为多少个月，2为多少年 

	 * @return 

	 * 举例：

	 * compareDate("2009-09-12", null, 0);//比较天

	 * compareDate("2009-09-12", null, 1);//比较月

	 * compareDate("2009-09-12", null, 2);//比较年

	*/
	public static int compareDate(String startDay, String endDay, int stype) {
		int n = 0;
		String[] u = { "天", "月", "年" };
		String formatStyle = stype == 1 ? "yyyy-MM" : "yyyy-MM-dd";

		endDay = endDay == null ? getCurrentDate("yyyy-MM-dd") : endDay;

		DateFormat df = new SimpleDateFormat(formatStyle);
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(df.parse(startDay));
			c2.setTime(df.parse(endDay));
		} catch (Exception e3) {
			
		}
		// List list = new ArrayList();
		while (!c1.after(c2)) { // 循环对比，直到相等，n 就是所要的结果
			// list.add(df.format(c1.getTime())); // 这里可以把间隔的日期存到数组中 打印出来
			n++;
			if (stype == 1) {
				c1.add(Calendar.MONTH, 1); // 比较月份，月份+1
			} else {
				c1.add(Calendar.DATE, 1); // 比较天数，日期+1
			}
		}
		n = n - 1;
		if (stype == 2) {
			n = (int) n / 365;
		}
		return n;
	}

	public static String getCurrentDate(String format) {
		Calendar day = Calendar.getInstance();
		day.add(Calendar.DATE, 0);
		SimpleDateFormat sdf = new SimpleDateFormat(format);// "yyyy-MM-dd"
		String date = sdf.format(day.getTime());
		return date;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
