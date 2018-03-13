package com.ycdc.nbms.report.serv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.report.dao.QARepDao;

/**
 * 品控数据指标
 * 
 * @author 孙诚明
 * @since 2016年11月30日
 */
@Service("qaRepServ")
public class QARepServImpl extends BaseService implements QARepServ
{
	@Resource
	private QARepDao dao;

	// 日志
	private Logger logger = org.slf4j.LoggerFactory.getLogger(QARepServImpl.class);

	private static Map<String, Object> reportView = new HashMap<String, Object>();

	// 查询时间
	private String date = super.getFmtDate("yyyy-MM");

	// 服务类型
	private String order_type = "";

	// 服务状态
	private String order_status = "";

	/**
	 * 按照时间查询品控数据（首页展示）
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public Object qaView(HttpServletRequest request, HttpServletResponse response)
	{
		// 查询时间
		String date = getValue(request, "date");
		if ("".equals(date) || "null".equals(date))
		{
			date = new SimpleDateFormat("yyyy-MM").format(new Date());
		}

		Object obj = createReport(date);
		return obj;
	}

	/**
	 * 按照时间查询品控数据（报表展示）
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public Object qaViewTotal(HttpServletRequest request, HttpServletResponse response)
	{
		// 查询结束时间
		String end_date = getValue(request, "date");
		this.date = end_date;

		// 服务类型
		String order_type = getValue(request, "order_type");
		this.order_type = order_type;
		
		// 服务状态
		String order_status = getValue(request, "order_status");
		this.order_status = order_status;

		Object obj = null;
		String key = this.date + this.order_type + this.order_status;
		if (reportView.containsKey(key))
		{
			logger.info("找到缓存数据");
			obj = reportView.get(key);
		} else
		{
			obj = createReportTotal();
		}

		return obj;
	}

	/**
	 * 获取统计数据（报表展示）
	 * 
	 * @return
	 */
	private Object createReportTotal()
	{
		int timeOffset = 12;

		// 根据查询时间计算时间列表
		List<Map<String, Object>> dateList = reckonStartDate(this.date, timeOffset);
		String start_date = String.valueOf(dateList.get(0).get("time"));
		logger.info("查询开始时间：" + start_date);

		// 初始化数据
		initDate(dateList);

		List<Map<String, Object>> list = null;
		// 查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start_date", start_date);
		params.put("end_date", date);
		params.put("order_type", order_type);
		params.put("order_status", order_status);

		// 解析页面传递的类型参数值
		if (order_type.equals(""))
		{
			// 如果为空，查询全部
			list = getAll(params, dateList);
		} else
		{
			list = getCategory(order_type, params, dateList);
		}

		return list;
	}

	/**
	 * 获取统计数据（首页展示）
	 * 
	 * @return
	 */
	public Map<String, Object> createReport(String date)
	{
		Map<String, Object> result = new HashMap<String, Object>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

		String last_month = "";

		try
		{
			Date date1 = sdf.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date1);
			cal.add(Calendar.MONTH, -1);
			last_month = sdf.format(cal.getTime());
		} catch (ParseException e)
		{
			e.printStackTrace();
		}

		// 上月预约量
		int last_reserve = dao.getMonthReserve(last_month);
		logger.info("上月预约量 = " + last_reserve);
		result.put("last_reserve", last_reserve);

		// 本月预约量
		int this_reserve = dao.getMonthReserve(date);
		logger.info("本月预约量 = " + this_reserve);
		result.put("this_reserve", this_reserve);

		// 上月投诉量
		int last_complaint = dao.getMonthComplaint(last_month);
		logger.info("上月投诉量 = " + last_complaint);
		result.put("last_complaint", last_complaint);

		// 本月投诉量
		int this_complaint = dao.getMonthComplaint(date);
		logger.info("本月投诉量 = " + this_complaint);
		result.put("this_complaint", this_complaint);
		
		// 上月闭环量
		int last_close_cycle = dao.getMonthCloseCycle(last_month);
		logger.info("上月闭环量 = " + last_close_cycle);
		result.put("last_close_cycle", last_close_cycle);

		// 本月闭环量
		int this_close_cycle = dao.getMonthCloseCycle(date);
		logger.info("本月闭环量 = " + this_close_cycle);
		result.put("this_close_cycle", this_close_cycle);

		// 上月报修量
		int last_maintenance = dao.getMonthMaintenance(last_month);
		logger.info("上月报修量 = " + last_maintenance);
		result.put("last_maintenance", last_maintenance);

		// 本月报修量
		int this_maintenance = dao.getMonthMaintenance(date);
		logger.info("本月报修量 = " + this_maintenance);
		result.put("this_maintenance", this_maintenance);

		// 上月订单总数（过滤例行保洁）
		int last_order_total = dao.getOrderTotal(last_month);
		logger.info("上月订单总数 = " + last_order_total);
		result.put("last_order_total", last_order_total);
		
		// 上月服务订单处理总天数（过滤例行保洁）
		int last_deal_period = dao.getMonthPeriod(last_month);
		int last_deal = last_order_total == 0 ? 0 : last_deal_period / last_order_total;
		logger.info("上月处理周期 = " + last_deal);
		result.put("last_deal_period", last_deal);

		// 本月订单总数（过滤例行保洁）
		int this_order_total = dao.getOrderTotal(date);
		logger.info("本月订单总数 = " + last_order_total);
		result.put("this_order_total", this_order_total);
		
		// 本月服务订单处理周期（过滤例行保洁）
		int this_deal_period = dao.getMonthPeriod(date);
		int this_deal = this_order_total == 0 ? 0 : this_deal_period / this_order_total;
		logger.info("本月处理周期 = " + this_deal);
		result.put("this_deal_period", this_deal);

		// 未收款合同数
		int no_gather_agreement = dao.getNoGatherAgreement();
		logger.info("未收款合同数 = " + no_gather_agreement);
		result.put("no_gather_agreement", no_gather_agreement);

		return result;
	}

	/**
	 * 按类别查询
	 * 
	 * @param order_type
	 * @param params
	 * @param dateList
	 * @return
	 */
	private List<Map<String, Object>> getCategory(String order_type, Map<String, Object> params,
			List<Map<String, Object>> dateList)
	{
		String key = "";
		switch (order_type)
		{
		case "0": // 看房订单
			key = "reserve_total";
			break;
		case "1": // 维修订单
			key = "maintenance_total";
			break;
		case "2": // 保洁订单
			key = "cleaning_total";
			break;
		case "3": // 投诉订单
			key = "complaint_total";
			break;
		case "4": // 其他订单
			key = "other_total";
			break;
		case "6": // 入住订单
			key = "checkIn_total";
			break;
		case "7": // 退租订单
			key = "refund_total";
			break;
		case "9": // 例行保洁
			key = "routine_cleaning_total";
			break;
		}
		
		params.put("key", key);
		
		// 按分类查询订单
		List<Map<String, Object>> categoryData = dao.getCategoryData(params);
		dealData(dateList, categoryData);
		
		// 分类查询出来周期
		List<Map<String, Object>> periods = dao.getCategoryPeriod(params);
		dealData(dateList, periods);
		logger.info("分类查询结果集：" + dateList);
		
		return dateList;
	}

	/**
	 * 查询全部
	 * 
	 * @param params
	 * @param dateList
	 * @return
	 */
	private List<Map<String, Object>> getAll(Map<String, Object> params,
			List<Map<String, Object>> dateList)
	{
		// 看房订单（预约订单）
		logger.info("看房订单（预约订单）查询SQL：");
		List<Map<String, Object>> reserves = dao.getReserves(params);
		dealData(dateList, reserves);

		// 维修订单
		logger.info("维修订单查询SQL：");
		List<Map<String, Object>> maintenances = dao.getMaintenances(params);
		dealData(dateList, maintenances);
		
		// 保洁订单
		logger.info("保洁订单查询SQL：");
		List<Map<String, Object>> cleanings = dao.getCleanings(params);
		dealData(dateList, cleanings);
		
		// 例行保洁
		logger.info("例行保洁查询SQL：");
		List<Map<String, Object>> routine_cleanings = dao.getRoutineCleanings(params);
		dealData(dateList, routine_cleanings);

		// 投诉订单
		logger.info("投诉订单查询SQL：");
		List<Map<String, Object>> complaint = dao.getComplaints(params);
		dealData(dateList, complaint);

		// 其他订单
		logger.info("其他订单查询SQL：");
		List<Map<String, Object>> others = dao.getOthers(params);
		dealData(dateList, others);

		// 入住问题
		logger.info("入住问题查询SQL：");
		List<Map<String, Object>> checkIns = dao.getCheckIns(params);
		dealData(dateList, checkIns);

		// 退租订单
		logger.info("退租订单查询SQL：");
		List<Map<String, Object>> refunds = dao.getRefunds(params);
		dealData(dateList, refunds);
		
		// 每月处理周期
		logger.info("处理周期查询SQL：");
		List<Map<String, Object>> periods = dao.getPeriods(params); 
		dealData(dateList, periods);
		
		logger.info("dateList = " + dateList);
		
		return dateList;
	}

	/**
	 * 根据查询时间计算时间列表（往前推N个月）
	 * 
	 * @param end_date
	 *          查询时间
	 * @param timeOffset
	 *          时间偏移量（开始时间向前推timeOffset个月）
	 * @return
	 */
	private List<Map<String, Object>> reckonStartDate(String end_date, int timeOffset)
	{
		// 时间列表
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar cl = Calendar.getInstance();
		String start_date = null;
		try
		{
			cl.setTime(sdf.parse(end_date));
			cl.add(Calendar.MONTH, -(timeOffset - 1));
			start_date = sdf.format(cl.getTime());
			// 首先增加用户选择的日期
			map.put("time", start_date);
			list.add(map);
		} catch (ParseException e)
		{
			logger.error("时间转换错误：" + e);
			e.printStackTrace();
		}

		// 根据开始时间计算时间列表
		try
		{
			cl.setTime(sdf.parse(start_date));
		} catch (ParseException e)
		{
			logger.error("时间转换错误：" + e);
			e.printStackTrace();
		}

		// 循环偏移量，计算出所有的日期
		for (int i = timeOffset; i > 1; i--)
		{
			map = new HashMap<String, Object>();
			cl.add(Calendar.MONTH, +1);
			map.put("time", sdf.format(cl.getTime()));
			list.add(map);
		}
		return list;
	}

	/**
	 * 初始化数据
	 * 
	 * @param dateList
	 */
	private void initDate(List<Map<String, Object>> dateList)
	{
		for (Map<String, Object> map : dateList)
		{
			// 看房订单（预约订单）
			map.put("reserve_total", 0);
			// 维修订单
			map.put("maintenance_total", 0);
			// 保洁订单
			map.put("cleaning_total", 0);
			// 例行保洁
			map.put("routine_cleaning_total", 0);
			// 投诉订单
			map.put("complaint_total", 0);
			// 其他订单
			map.put("other_total", 0);
			// 入住问题
			map.put("checkIn_total", 0);
			// 退租订单
			map.put("refund_total", 0);
			// 处理周期
			map.put("period_day", 0);
		}

	}

	/**
	 * 数据汇总
	 * 
	 * @param dateList
	 * @param list
	 */
	private void dealData(List<Map<String, Object>> dateList, List<Map<String, Object>> list)
	{

		for (Map<String, Object> dateMap : dateList)
		{
			// 获取时间，进行对比
			String time = String.valueOf(dateMap.get("time"));
			for (Map<String, Object> map : list)
			{
				if (time.equals(String.valueOf(map.get("time"))))
				{
					// 如果相同，直接放入
					dateMap.putAll(map);
				}
			}
		}

	}

}
