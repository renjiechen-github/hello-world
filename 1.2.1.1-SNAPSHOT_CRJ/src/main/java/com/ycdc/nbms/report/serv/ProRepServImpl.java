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
import com.ycdc.nbms.report.dao.ProRepDao;

/**
 * 工程数据指标
 * 
 * @author 孙诚明
 * @since 2016年11月30日
 */
@Service("proRepServ")
public class ProRepServImpl extends BaseService implements ProRepServ
{
	@Resource
	private IMktRepServ mktRepServ;

	@Resource
	private ProRepDao dao;

	// 日志
	private Logger logger = org.slf4j.LoggerFactory.getLogger(ProRepServImpl.class);

	HttpServletRequest request;

	private String date = super.getFmtDate("yyyy-MM");

	/**
	 * 按照时间查询工程数据（首页展示）
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public Object proView(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;

		Object obj = createReport();
		return obj;
	}

	/**
	 * 按照时间查询工程数据（报表展示）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public Object proTotal(HttpServletRequest request, HttpServletResponse response)
	{
		// 时间
		String end_date = getValue(request, "date");
		if ("".equals(end_date))
		{
			end_date = new SimpleDateFormat("yyyy-MM").format(new Date());
		} else
		{
			this.date = end_date;
		}
		logger.info("当前查询时间：" + end_date);

		Object obj = createProDetail();

		return obj;
	}

	/**
	 * 工程指标
	 * 
	 * @return
	 */
	private Object createProDetail()
	{
		int timeOffset = 12;

		// 根据查询时间计算时间列表
		List<Map<String, Object>> dateList = reckonStartDate(this.date, timeOffset);
		String start_date = String.valueOf(dateList.get(0).get("time"));
		logger.info("查询开始时间：" + start_date);

		// 初始化数据
		initDate(dateList);
		
		// 开工量
		List<Map<String, Object>> constructions = dao.getConstruction(start_date, this.date);
		// 验收量
		
		
		return null;
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
			// 开工量
			map.put("constructions", 0);
			// 验收量
			map.put("checks", 0);
		}
	}

	/**
	 * 获取统计数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> createReport()
	{
		Map<String, Object> result = new HashMap<String, Object>();

		Map<String, Object> map1 = (Map<String, Object>) mktRepServ.mktView(this.request);

		Map<String, List<String>> map2 = (Map<String, List<String>>) map1.get("mrtgView");

		List<String> list = map2.get("全市_yp");

		// 总房间数
		String house_total_str = String.valueOf(list.get(list.size() - 1));
		double house_total = Double.valueOf(house_total_str);
		logger.debug("总房间数 = " + house_total);

		// 管家数量（当前激活可用帐号数）
		String manager_total_str = dao.getManTotal();
		double manager_total = Double.valueOf(manager_total_str);
		logger.debug("管家总数 = " + manager_total_str);
		result.put("manager_total", manager_total);
		
		// 全公司当前激活可用帐号数
		String manager_all_str = dao.getManAll();
		double manager_all = Double.valueOf(manager_all_str);
		logger.debug("全公司当前激活可用帐号数 = " + manager_all);
		
		// 总房屋套数
		String house_sets_str = dao.getHouseSets();
		double house_sets = Double.valueOf(house_sets_str);
		logger.debug("总房屋套数 = " + house_sets_str);

		// 总的施工完成天数
		String total_day_str = dao.getTotalDay();
		double total_day = Double.valueOf(total_day_str);
		logger.debug("总的施工完成天数 = " + total_day_str);

		// 施工周期（总的完成天数 / 总房屋套数）
		long pro_av = Math.round(total_day / house_sets);
		logger.debug("施工周期 = " + pro_av);
		result.put("pro_av", pro_av);

		// 总金额
		String total_money_str = dao.getTotalMoney();
		total_money_str = total_money_str.replace(",", "");
		double total_money = Double.valueOf(total_money_str);
		logger.debug("施工总金额 = " + total_money_str);

		// 单间成本（总金额 / 总房间数）
		long single_cost = 0;
		if (house_total != 0)
		{
			single_cost = Math.round(total_money / house_total / 60);
		} 
		logger.debug("单间成本 = " + single_cost);
		result.put("single_cost", single_cost);

		// 人均效能（总房间数 / 全公司当前激活可用帐号数）
		long per_capita = 0;
		if (manager_all != 0)
		{
			per_capita = Math.round(house_total / manager_all);
		}
		result.put("per_capita", per_capita);
		result.put("manager_all", manager_all);

		return result;
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
}
