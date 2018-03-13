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

import org.springframework.stereotype.Service;

import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.report.dao.PrRepDao;

/**
 * 收支金额统计
 * 
 * @author 孙诚明
 * @since 2016年9月29日
 */
@Service("prRepServ")
public class PrRepServImpl extends BaseService implements PrRepServ
{

	private String date = super.getFmtDate("yyyy-MM");

	// 财务类型
	private String cost_type = "";

	// 财务分类
	private String category = "";

	@Resource
	private PrRepDao dao;
	
	/**
	 * 查询财务报表（首页展示）
	 */
	@Override
	public Object prViewCost(HttpServletRequest request, HttpServletResponse response)
	{
		// 时间
		String end_date = getValue(request, "date");
		if ("".equals(end_date))
		{
			end_date = new SimpleDateFormat("yyyy-MM").format(new Date()) + " 00:00:00";
		} else
		{
			this.date = end_date;
		}

		Map<String, Object> result = new HashMap<>();
		
		// 待收款
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("date", date);
		params.put("table_name", "financial_receivable_tab");
		params.put("key1", "receivable");
		params.put("key2", "receivableCount");
		logger.info("查询待收款["+params.get("table_name")+"]SQL：");
		Map<String, Object> receivables = dao.getViewCost(params);
		result.put("receivableCount", receivables.get("receivableCount"));
		result.put("receivable", receivables.get("receivable"));
		
		// 待付款
		params.put("table_name", "financial_payable_tab");
		params.put("key1", "payable");
		params.put("key2", "payableCount");
		logger.info("查询待付款["+params.get("table_name")+"]SQL：");
		Map<String, Object> payables = dao.getViewCost(params);
		result.put("payableCount", payables.get("payableCount"));
		result.put("payable", payables.get("payable"));
		
		return result;
	}	

	/**
	 * 查询财务报表（报表详情）
	 */
	@Override
	public Object prView(HttpServletRequest request, HttpServletResponse response)
	{
		// 时间
		String end_date = getValue(request, "date");

		// 财务类型
		String cost_type = getValue(request, "cost_type");
		this.cost_type = cost_type;

		// 财务分类
		String category = getValue(request, "category");
		this.category = category;

		if ("".equals(end_date))
		{
			end_date = new SimpleDateFormat("yyyy-MM").format(new Date());
		} else
		{
			this.date = end_date;
		}

		logger.info("当前查询时间：" + end_date);

		Object obj = createPrDetail();
		return obj;

	}

	/**
	 * 查询财务报表
	 * 
	 * @param request
	 * @return
	 */
	public Object createPrDetail()
	{
		int timeOffset = 12;

		// 根据查询时间计算时间列表
		List<Map<String, Object>> dateList = reckonStartDate(this.date, timeOffset);
		String start_date = String.valueOf(dateList.get(0).get("time"));
		logger.info("查询开始时间：" + start_date);

		// 初始化金额数据
		initDate(dateList);

		// 根据财务类型，判断表名
		String table_name = "";
		if (this.cost_type.equals("1"))
		{
			// 收入表
			table_name = "financial_receivable_tab";
		} else
		{
			// 支出表
			table_name = "financial_payable_tab";
		}

		List<Map<String, Object>> list = null;
		// 查询参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("start_date", start_date);
		params.put("end_date", this.date);
		params.put("table_name", table_name);
		params.put("cost_type", this.cost_type);
		params.put("category", this.category);

		// 解析页面传递的分类参数值
		if (this.category.equals(""))
		{
			// 如果为空，则查询全部
			list = getAll(params, dateList);
		} else
		{
			list = getCategory(params, dateList);
		}
		return list;
	}

	/**
	 * 按照分类查询数据
	 * 
	 * @param params
	 * @param dateList
	 * @return
	 */
	private List<Map<String, Object>> getCategory(Map<String, Object> params,
			List<Map<String, Object>> dateList)
	{
		String key = "";
		switch (this.category)
		{
		case "1": // 租金
			key = "lease_cost";
			break;
		case "2": // 物业费
			key = "estate_cost";
			break;
		case "3": // 退款
			key = "refund_cost";
			break;
		case "4": // 待缴费
			key = "wait_cost";
			break;
		case "5": // 押金
			key = "deposit_cost";
			break;
		case "6": // 服务费
			key = "service_cost";
			break;
		case "7": // 家居
			key = "home_cost";
			break;
		case "8": // 家电
			key = "appliance_cost";
			break;
		case "9": // 装修
			key = "decorate_cost";
			break;
		case "10": // 其他
			key = "other_cost";
			break;
		case "11": // 水费
			key = "water_cost";
			break;
		case "12": // 电费
			key = "electric_cost";
			break;
		case "13": // 燃气费
			key = "gas_cost";
			break;
		}
		params.put("key", key);
		
		// 按分类查询
		logger.info("查询分类["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> categoryData = dao.getCategoryData(params);
		dealData(dateList, categoryData);
		
		// 查询账款
		logger.info("查询账款["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> costs = dao.getCost(params);
		dealData(dateList, costs);
		
		return dateList;
	}

	/**
	 * 查询全部分类数据
	 * 
	 * @param params
	 * @param dateList
	 * @return
	 */
	private List<Map<String, Object>> getAll(Map<String, Object> params,
			List<Map<String, Object>> dateList)
	{
		// 查询账款
		logger.info("查询账款["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> costs = dao.getCost(params);
		dealData(dateList, costs);
		
		// 查询租金
		logger.info("查询租金["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> lease_costs = dao.getLeaseCost(params);
		dealData(dateList, lease_costs);
		
		// 查询物业费
		logger.info("查询物业费["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> estate_costs = dao.getEstateCost(params);
		dealData(dateList, estate_costs);
		
		// 查询退款
		logger.info("查询退款["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> refund_costs = dao.getRefundCost(params);
		dealData(dateList, refund_costs);
		
		// 查询待缴费
		logger.info("查询待缴费["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> wait_costs = dao.getWaitCost(params);
		dealData(dateList, wait_costs);
		
		// 查询押金
		logger.info("查询押金["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> deposit_costs = dao.getDepositCost(params);
		dealData(dateList, deposit_costs);
		
		// 查询服务费
		logger.info("查询服务费["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> service_costs = dao.getServiceCost(params);
		dealData(dateList, service_costs);
		
		// 查询家居
		logger.info("查询家居["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> home_costs = dao.getHomeCost(params);
		dealData(dateList, home_costs);
		
		// 查询家电
		logger.info("查询家电["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> appliance_costs = dao.getApplianceCost(params);
		dealData(dateList, appliance_costs);
		
		// 查询装修
		logger.info("查询装修["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> decorate_costs = dao.getDecorateCost(params);
		dealData(dateList, decorate_costs);
		
		// 查询其他
		logger.info("查询其他["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> other_costs = dao.getOtherCost(params);
		dealData(dateList, other_costs);
		
		// 查询水费
		logger.info("查询水费["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> water_costs = dao.getWaterCost(params);
		dealData(dateList, water_costs);
		
		// 查询电费
		logger.info("查询电费["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> electric_costs = dao.getElectricCost(params);
		dealData(dateList, electric_costs);
		
		// 查询燃气费
		logger.info("查询燃气费["+params.get("table_name")+"]SQL：");
		List<Map<String, Object>> gas_costs = dao.getGasCost(params);
		dealData(dateList, gas_costs);
		
		return dateList;
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
			// 应收
			map.put("receivable", 0);
			// 应付
			map.put("payable", 0);
			// 账款
			map.put("cost", 0);
			// 租金
			map.put("lease_cost", 0);
			// 物业费
			map.put("estate_cost", 0);
			// 退款
			map.put("refund_cost", 0);
			// 待缴费
			map.put("wait_cost", 0);
			// 押金
			map.put("deposit_cost", 0);
			// 服务费
			map.put("service_cost", 0);
			// 家居
			map.put("home_cost", 0);
			// 家电
			map.put("appliance_cost", 0);
			// 装修
			map.put("decorate_cost", 0);
			// 其他
			map.put("other_cost", 0);
			// 水费
			map.put("water_cost", 0);
			// 电费
			map.put("electric_cost", 0);
			// 燃气费
			map.put("gas_cost", 0);
		}
	}

	/**
	 * 汇总数据
	 * 
	 * @param dateList
	 *          时间列表
	 * @param costList
	 *          金额列表
	 */
	private void dealData(List<Map<String, Object>> dateList, List<Map<String, Object>> costList)
	{
		for (Map<String, Object> dateMap : dateList)
		{
			// 获取时间，进行对比
			String time = String.valueOf(dateMap.get("time"));
			for (Map<String, Object> costMap : costList)
			{
				if (time.equals(String.valueOf(costMap.get("time"))))
				{
					// 如果相同，直接放入
					dateMap.putAll(costMap);
				}
			}
		}
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
