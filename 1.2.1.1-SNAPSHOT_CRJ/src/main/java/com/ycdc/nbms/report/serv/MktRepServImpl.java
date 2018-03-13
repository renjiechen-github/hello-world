package com.ycdc.nbms.report.serv;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Service;

//import com.ycdc.appserver.bus.dao.house.HouseMapper;
import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.report.dao.IMktRepDao;

@Service("mktRepServ")
public class MktRepServImpl extends BaseService implements IMktRepServ
{
	@Resource
	private IMktRepDao mktRepDao;

//	@Resource
//	HouseMapper houseMapper;

	private static Map<String, Object> reportView = new HashMap<String, Object>();

	private String date = super.getFmtDate("yyyy-MM");

	private static List<Map<String, Object>> infoList = new ArrayList<Map<String, Object>>();

	static
	{
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("areaid", 10102);
		map1.put("code", "FP025JN");
		infoList.add(map1);
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("areaid", 10101);
		map2.put("code", "FP025GL");
		infoList.add(map2);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("areaid", 10103);
		map3.put("code", "FP025YH");
		infoList.add(map3);
		Map<String, Object> map4 = new HashMap<String, Object>();
		map4.put("areaid", 10104);
		map4.put("code", "FP025QH");
		infoList.add(map4);
		Map<String, Object> map5 = new HashMap<String, Object>();
		map5.put("areaid", 10105);
		map5.put("code", "FP025JY");
		infoList.add(map5);
		Map<String, Object> map6 = new HashMap<String, Object>();
		map6.put("areaid", 10106);
		map6.put("code", "FP025XW");
		infoList.add(map6);
		Map<String, Object> map7 = new HashMap<String, Object>();
		map7.put("areaid", 10107);
		map7.put("code", "FP025QX");
		infoList.add(map7);
		Map<String, Object> map8 = new HashMap<String, Object>();
		map8.put("areaid", 10108);
		map8.put("code", "FP025PK");
		infoList.add(map8);
	}

	@Override
	public Object mktView(HttpServletRequest request)
	{
		// 时间
		String date = super.getValue(request, "date").trim();
		if (!"".equals(date.trim()))
		{
			this.date = date;
		}

		Object obj = null;
		if (reportView.containsKey(this.date))
		{
			logger.info("找到缓存数据");
			obj = reportView.get(this.date);
		} else
		{
			obj = createReport();
		}
		return obj;
	}

	/**
	 * 根据网格ID，获取租差和空置率
	 * 
	 * @param user_id
	 *          网格ID
	 * @param time
	 *          查询时间（yyyy-MM），若为空，按当前系统时间查询
	 * @param allAreadyHouseCnt
	 *          当前网格出租间数
	 * @param allHouseCnt
	 *          当前网格总房间
	 * @return map[cost_val(租差)：200.2,empty_rate(空置率):20.32]
	 */
	public Map<String, String> getMarketData(String user_id, String teamIds, String time, BigInteger allAreadyHouseCnt, BigInteger allHouseCnt)
	{
		if (null != time && !"".equals(time.trim()) && !"null".equals(time.trim()))
		{
			this.date = time;
		}

		// 返回集合
		Map<String, String> result = new HashMap<String, String>();

		// 获取当前天
		String day = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		// 根据用户ID，获取当月收房
		Map<String, String> mrtgYp = mktRepDao.selectYpById(user_id, teamIds, this.date);
		logger.info("已经出租房源的收房总间数：" + mrtgYp);
//		// 根据用户ID，获取当月出租
		Map<String, String> mrtgYr = mktRepDao.selectYrById(user_id, teamIds, this.date);
		logger.info("当月出租房间：" + mrtgYr);
		// 根据用户ID，获取当月收房总价
//		Map<String, String> mrtgYcr = mktRepDao.selectYcrById(user_id, teamIds, day, day);
//		logger.info("已经出租房源的收房总价：" + mrtgYcr);
		Map<String, String> mrtgYcr = mktRepDao.selectYcrById(user_id, teamIds, day, day);
		logger.info("租差价：" + mrtgYcr);
		result.put("cost_val", String.valueOf(mrtgYcr.get("diff")));
		// 根据用户ID，获取当月出租总价
//		Map<String, String> mrtgYra = mktRepDao.selectYraById(user_id, teamIds, this.date);
//		logger.info("出租总价：" + mrtgYra);
		// 计算用户租差比
//		dealCostValByGid(mrtgYp, mrtgYr, mrtgYcr, mrtgYra, result);

		// 当前用户出租间数
		double areadyHouse = Double.valueOf(objToString(allAreadyHouseCnt));
		logger.info("出租间数 = " + areadyHouse);

		// 当前网格总房间
		double allHouse = Double.valueOf(objToString(allHouseCnt));
		logger.info("总房间数 = " + allHouse);

		// 计算空置率（总房间数-出租间数）/ 总房间数
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");
		String empty_rate = "0";
		if (allHouse > 0)
		{
			empty_rate = df.format((allHouse - areadyHouse) / allHouse * 100 < 0 ? 0 : (allHouse - areadyHouse) / allHouse * 100 > 100 ? 100 : (allHouse - areadyHouse) / allHouse * 100);
		}
		result.put("empty_rate", empty_rate);

		// 根据网格ID，获取收房总天间
		// Map<String, String> ypTotal = mktRepDao.selectYpTotalById(g_id,
		// this.date);
		// 根据网格ID，获取出租总天间
		// Map<String, String> yrTotal = mktRepDao.selectYrTotalById(g_id,
		// this.date);
		// 计算网格空置率
		// dealEmptyRateByGid(ypTotal, yrTotal, result);

		return result;
	}

	/**
	 * 计算网格租差比
	 * 
	 * @param mrtgYp
	 *          当前收房
	 * @param mrtgYr
	 *          当前出租
	 * @param mrtgYcr
	 *          收房总价
	 * @param mrtgYra
	 *          出租总价
	 * @param result
	 */
	private void dealCostValByGid(Map<String, String> mrtgYp, Map<String, String> mrtgYr, Map<String, String> mrtgYcr, Map<String, String> mrtgYra, Map<String, String> result)
	{
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");

		// 当前收房
		double ypDou = 0;
		if (null != mrtgYp && mrtgYp.size() > 0)
		{
			ypDou = Double.valueOf(objToString(mrtgYp.get("yp")));
		}

		// 当前出租
		double yrDou = 0;
		if (null != mrtgYr && mrtgYr.size() > 0)
		{
			yrDou = Double.valueOf(objToString(mrtgYr.get("yr")));
		}

		// 收房总价格
		double ycrDou = 0;
		if (null != mrtgYcr && mrtgYcr.size() > 0)
		{
			ycrDou = Double.valueOf(objToString(mrtgYcr.get("ycr")));
		}

		// 出租总价格
		double yraDou = 0;
		if (null != mrtgYra && mrtgYra.size() > 0)
		{
			yraDou = Double.valueOf(objToString(mrtgYra.get("yra")));
		}

		// 收房均价
		String ycrStr = "";
		if (ypDou > 0)
		{
			ycrStr = df.format(ycrDou / ypDou);

		} else
		{
			ycrStr = "0";
		}

		// 出租均价
		String yraStr = "";
		if (yrDou > 0)
		{
			yraStr = df.format(yraDou / yrDou);
		} else
		{
			yraStr = "0";
		}

		// 总租差比（出租总均价-收房总均价）
		double cost_val = Double.valueOf(yraStr) - Double.valueOf(ycrStr);

		result.put("cost_val", df.format(cost_val));
	}

	/**
	 * 根据网格计算空置率
	 * 
	 * @param ypTotal
	 * @param yrTotal
	 * @return
	 */
	private void dealEmptyRateByGid(Map<String, String> ypTotal, Map<String, String> yrTotal, Map<String, String> result)
	{
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");

		double yp = 0;
		if (null != ypTotal && ypTotal.size() > 0)
		{
			// 收房总天间
			String ypStr = String.valueOf(ypTotal.get("ypTotal"));
			if (ypStr.equals("") || ypStr.equals("null"))
			{
				yp = 0;
			} else
			{
				yp = Double.valueOf(ypStr);
			}
		}

		double yr = 0;
		if (null != yrTotal && yrTotal.size() > 0)
		{
			// 出租总天间
			String yrStr = String.valueOf(yrTotal.get("yrTotal"));
			if (yrStr.equals("") || yrStr.equals("null"))
			{
				yr = 0;
			} else
			{
				yr = Double.valueOf(yrStr);
			}
		}

		String empty_rate = "0";
		if (yp > 0)
		{
			empty_rate = df.format((yp - yr) / yp * 100 < 0 ? 0 : (yp - yr) / yp * 100 > 100 ? 100 : (yp - yr) / yp * 100);
		} else
		{
			empty_rate = "0";
		}

		result.put("empty_rate", empty_rate);
	}

	/**
	 * 根据区域划分获取市场报表
	 * 
	 * @return
	 */
	@Override
	public Object createReport()
	{
		logger.info("开始createReport()");
		for (Map<String, Object> map : infoList)
		{
			mktRepDao.updateAreaId(map);
		}

		String[] dates = super.getFmtDateList(date, -12);

		// 初始化区域数据
		List<Map<String, String>> dataList = inint();

		// 存放处理后的记录
		Map<String, List<Object>> temp = new HashMap<String, List<Object>>();

		// 获取12个月的当月收房信息
		List<Map<String, String>> mrtgMp = mktRepDao.selectMp(dates);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgMp);
		// 处理查询后数据
		dealData(temp, mrtgMp, dates, "mp");

		// 获取12个月的当月出租信息
		List<Map<String, String>> mrtgMr = mktRepDao.selectMr(dates);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgMr);
		logger.info("当月出租 = " + mrtgMr);
		// 处理查询后数据
		dealData(temp, mrtgMr, dates, "mr");

		// 获取12个月的累计房源量
		List<Map<String, String>> mrtgYp = mktRepDao.selectYp(dates);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgYp);
		logger.info("累计房源量= " + mrtgYp);
		// 处理查询后数据
		dealData(temp, mrtgYp, dates, "yp");

		// 获取12个月的累计出租量
		List<Map<String, String>> mrtgYr = mktRepDao.selectYr(dates);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgYr);
		logger.info("累计出租量= " + mrtgYr);
		// 处理查询后数据
		dealData(temp, mrtgYr, dates, "yr");
		
		// 获取当前有效房源量
		Map<String, String> mrtgYpValid = mktRepDao.selectYpValid();
		
		// 获取当前有效出租量
		Map<String, String> mrtgYrValid = mktRepDao.selectYrValid();
		
		// 获取当前日期
		String time = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		// 获取12个月的收房总价
		List<Map<String, String>> mrtgYcr = mktRepDao.selectYcr(dates, time);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgYcr);
		logger.info("收房总价= " + mrtgYcr);

		// 获取12个月的出租总价
		List<Map<String, String>> mrtgYra = mktRepDao.selectYra(dates);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgYra);
		logger.info("出租总价= " + mrtgYra);

		// 获取当月的出租总价
		List<Map<String, String>> mrtgMra = mktRepDao.selectMra(dates);
		// 根据区域进行数据处理
		dealInfo(dataList, mrtgMra);
		logger.info("当月出租总价= " + mrtgMra);

		// 处理当月出租总价
		Map<String, String> mrtgMras = new HashMap<>();
		for (Map<String, String> map : mrtgMra)
		{
			if (map.get("area_name").equals("全市"))
			{
				mrtgMras.putAll(map);
				break;
			}
		}

		// 处理当月出租间数
		Map<String, String> mrtgMrs = new HashMap<>();
		for (Map<String, String> mapMr : mrtgMr)
		{
			if (mapMr.get("area_name").equals("全市"))
			{
				mrtgMrs.putAll(mapMr);
				break;
			}
		}

		// 获取当月已出租房间的收房总价
		Map<String, String> mrtgMcr = mktRepDao.selectMcr(this.date, time);
		// 根据区域进行数据处理
		// dealInfo(dataList, mrtgMcr);

		// 获取当月已出租房间的收房间数
		Map<String, String> mrtgMsf = mktRepDao.selectMsf(this.date, time);

		// 存放租差、均价
		Map<String, Object> homePageRent = new HashMap<>();
		// 计算当月均价、租差（首页报表使用）
		dealCostM(homePageRent, mrtgMsf, mrtgMrs, mrtgMcr, mrtgMras);
		logger.info("当月租差=" + homePageRent);

		// 处理查询后数据
		dealDataCost(temp, mrtgYp, mrtgYcr, mrtgYr, mrtgYra, dates, "ycr", "yp", "yra", "yr");
		logger.info("总租差 = " + temp);

		// 获取收房总天间
		List<Map<String, String>> ypTotal = mktRepDao.selectYpTotal(dates);
		dealInfo(dataList, ypTotal);
		logger.info("ypTotal = " + ypTotal);

		// 获取出租总天间
		List<Map<String, String>> yrTotal = mktRepDao.selectYrTotal(dates);
		dealInfo(dataList, yrTotal);
		logger.info("yrTotal = " + yrTotal);

		// 计算空置率
		List<Map<String, String>> tempKzList = dealKp(ypTotal, yrTotal, dates);
		logger.info("空置率 = " + tempKzList);

		// 计算空置率（首页报表使用）
		String hkp = dealHkp(mrtgYpValid, mrtgYrValid);
		logger.info("首页空置率 = " + hkp);
		homePageRent.put("hkp", hkp);
		homePageRent.put("yp_valid", mrtgYpValid.get("yp_valid"));
		homePageRent.put("yr_valid", mrtgYrValid.get("yr_valid"));
		
		
		// 获取收房同比、环比
		List<Map<String, String>> housingRes = mktRepDao.getHousingRes(date);
		dealInfo(dataList, housingRes);
		// 获取出租同比、环比
		List<Map<String, String>> rentRes = mktRepDao.getRentRes(date);
		dealInfo(dataList, rentRes);
		// 处理同比、环比数据
		List<Map<String, String>> tempMonthView = dealTempMonthView(housingRes, rentRes);
		logger.info("同比、环比 = " + tempMonthView);

		List<Map<String, String>> monthView = new ArrayList<Map<String, String>>();
		for (int i = 0; i < tempMonthView.size(); i++)
		{
			Map<String, String> tempMap = tempMonthView.get(i);
			String mapName = tempMap.get("area_name").toString();

			for (int j = 0; j < tempKzList.size(); j++)
			{
				String kzName = tempKzList.get(j) == null ? "" : tempKzList.get(j).get("area_name").toString();
				if (mapName.equals(kzName))
				{
					tempMap.put("kp", tempKzList.get(j).get("kp"));
				}
			}
			monthView.add(tempMap);
		}
		for (int i = 0; i < monthView.size(); i++)
		{
			monthView.get(i).put("purmonthPer", doBiz(String.valueOf(monthView.get(i).get("pur")), String.valueOf(monthView.get(i).get("purmonth"))));
			monthView.get(i).put("puryearPer", doBiz(String.valueOf(monthView.get(i).get("pur")), String.valueOf(monthView.get(i).get("puryear"))));
			monthView.get(i).put("rentmonthPer", doBiz(String.valueOf(monthView.get(i).get("rent")), String.valueOf(monthView.get(i).get("rentmonth"))));
			monthView.get(i).put("rentyearPer", doBiz(String.valueOf(monthView.get(i).get("rent")), String.valueOf(monthView.get(i).get("rentyear"))));
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("mrtgView", temp);
		ret.put("monthView", monthView);
		ret.put("homePageRent", homePageRent);
		reportView.put(date, ret);
		return ret;
	}

	/**
	 * 处理同比、环比数据
	 * 
	 * @param housingRes
	 *          收房同比、环比
	 * @param rentRes
	 *          出租同比、环比
	 * @return
	 */
	private List<Map<String, String>> dealTempMonthView(List<Map<String, String>> housingRes, List<Map<String, String>> rentRes)
	{
		for (Map<String, String> housingMap : housingRes)
		{
			String area_name = housingMap.get("area_name");
			for (Map<String, String> rentMap : rentRes)
			{
				// 如果区域一致
				if (area_name.equals(rentMap.get("area_name")))
				{
					housingMap.putAll(rentMap);
				}
			}
		}
		return housingRes;
	}

	/**
	 * 计算空置率（首页报表使用）
	 * 
	 * @param mrtgYp
	 *          当前有效房源量
	 * @param mrtgYr
	 *          当前有效出租量
	 * @param dates
	 *          时间列表
	 * @return
	 */
	private String dealHkp(Map<String, String> mrtgYp, Map<String, String> mrtgYr)
	{
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");

		// 当前有效房源量
		double yp = Double.valueOf(objToString(mrtgYp.get("yp_valid")));
		
		// 当前有效出租量
		double yr = Double.valueOf(objToString(mrtgYp.get("yp_valid")));
		
		String kp = "0";
		if (yp > 0)
		{
			kp = df.format((yp - yr) / yp * 100 < 0 ? 0 : (yp - yr) / yp * 100 > 100 ? 100 : (yp - yr) / yp * 100);
		} else
		{
			kp = "0";
		}		
		
		return kp;
	}

	/**
	 * 获取空置率
	 * 
	 * @param mrtgYp
	 *          收房总天间
	 * @param mrtgYr
	 *          出租总天间
	 * @param dates
	 *          时间列表
	 * @return
	 */
	private List<Map<String, String>> dealKp(List<Map<String, String>> mrtgYp, List<Map<String, String>> mrtgYr, String[] dates)
	{
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");

		List<Map<String, String>> list = new ArrayList<>();

		for (int i = 0; i < mrtgYp.size(); i++)
		{
			Map<String, String> ypMap = mrtgYp.get(i);

			Map<String, String> yrMap = mrtgYr.get(i);

			Map<String, String> map = new HashMap<String, String>();

			String area_name = ypMap.get("area_name");

			String ypStr = objToString(ypMap.get("ypTotal_" + dates[11]));
			double yp = 0;
			if (ypStr.equals("") || ypStr.equals("null"))
			{
				yp = 0;
			} else
			{
				yp = Double.valueOf(ypStr);
			}

			String yrStr = objToString(yrMap.get("yrTotal_" + dates[11]));
			double yr = 0;
			if (yrStr.equals("") || yrStr.equals("null"))
			{
				yr = 0;
			} else
			{
				yr = Double.valueOf(yrStr);
			}

			String kp = "0";
			if (yp > 0)
			{
				kp = df.format((yp - yr) / yp * 100 < 0 ? 0 : (yp - yr) / yp * 100 > 100 ? 100 : (yp - yr) / yp * 100);
			} else
			{
				kp = "0";
			}

			map.put("area_name", area_name);
			map.put("kp", kp);

			list.add(map);

		}
		return list;
	}

	/**
	 * 获取当月均价、租差
	 * 
	 * @param homePageRent
	 * @param mrtgMsf
	 *          当月已出租房间的收房间数
	 * @param mrtgMrs
	 *          当月出租
	 * @param mrtgMcr
	 *          当月已出租房间的收房总价
	 * @param mrtgMras
	 *          当月出租总价
	 */
	private void dealCostM(Map<String, Object> homePageRent, Map<String, String> mrtgMsf, Map<String, String> mrtgMrs, Map<String, String> mrtgMcr, Map<String, String> mrtgMras)
	{
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");

		// 获取当月已出租房间的收房间数
		double mpDou = Double.valueOf(objToString(mrtgMsf.get("msf_" + this.date)));

		// 获取当月出租
		double mrDou = Double.valueOf(objToString(mrtgMrs.get("mr_" + this.date)));

		// 获取当月已出租房间的收房总价
		double mcrDou = Double.valueOf(objToString(mrtgMcr.get("mcr_" + this.date)));

		// 获取当月出租总价
		double mraDou = Double.valueOf(objToString(mrtgMras.get("mra_" + this.date)));

		// 收房均价
		String mcrStr = "";
		if (mpDou > 0)
		{
			mcrStr = df.format(mcrDou / mpDou);
		} else
		{
			mcrStr = "0";
		}
		homePageRent.put("mcrStr", mcrStr);

		// 出租均价
		String mraStr = "";
		if (mrDou > 0)
		{
			mraStr = df.format(mraDou / mrDou);
		} else
		{
			mraStr = "0";
		}
		homePageRent.put("mraStr", mraStr);

		// 总租差比（出租总均价-收房总均价）
		double yrate = Double.valueOf(mraStr) - Double.valueOf(mcrStr);
		homePageRent.put("yrate", df.format(yrate));

	}

	/**
	 * 处理价格
	 * 
	 * @param temp
	 * @param mrtgYp
	 *          当前房源量
	 * @param mrtgYcr
	 *          收房总价
	 * @param mrtgYr
	 *          当前出租量
	 * @param mrtgYra
	 *          出租总价
	 * @param dates
	 *          时间列表
	 * @param ycr
	 *          收房总价标识
	 * @param yp
	 *          当前房源量总价标识
	 * @param yra
	 *          出租总价标识
	 * @param yr
	 *          当前出租量标识
	 */
	private void dealDataCost(Map<String, List<Object>> temp, List<Map<String, String>> mrtgYp, List<Map<String, String>> mrtgYcr, List<Map<String, String>> mrtgYr, List<Map<String, String>> mrtgYra,
			String[] dates, String ycr, String yp, String yra, String yr)
	{
		// 格式化小数
		DecimalFormat df = new DecimalFormat("0.00");

		// 遍历当前房源量
		for (int i = 0; i < mrtgYp.size(); i++)
		{
			// 收房均价
			List<Object> ycrList = new ArrayList<Object>();
			// 出租均价
			List<Object> yraList = new ArrayList<Object>();
			// 租差比
			List<Object> yrateList = new ArrayList<Object>();

			// 房源量
			Map<String, String> ypMap = mrtgYp.get(i);

			// 收房总价格
			Map<String, String> ycrMap = mrtgYcr.get(i);

			// 出租量
			Map<String, String> yrMap = mrtgYr.get(i);

			// 出租总价格
			Map<String, String> yraMap = mrtgYra.get(i);

			for (int j = 0; j < dates.length; j++)
			{
				// 房源量
				double ypDou = Double.valueOf(objToString(ypMap.get(yp + "_" + dates[j])));

				// 收房总价格
				double ycrDou = Double.valueOf(objToString(ycrMap.get(ycr + "_" + dates[j])));

				// 出租量
				double yrDou = Double.valueOf(objToString(yrMap.get(yr + "_" + dates[j])));

				// 出租总价格
				double yraDou = Double.valueOf(objToString(yraMap.get(yra + "_" + dates[j])));

				// 收房均价
				String ycrStr = "";
				if (ypDou > 0)
				{
					ycrStr = df.format(ycrDou / ypDou);
				} else
				{
					ycrStr = "0";
				}
				ycrList.add(ycrStr);

				// 出租均价
				String yraStr = "";
				if (yrDou > 0)
				{
					yraStr = df.format(yraDou / yrDou);
				} else
				{
					yraStr = "0";
				}
				yraList.add(yraStr);

				// 总租差比（出租总均价-收房总均价）
				double yrate = Double.valueOf(yraStr) - Double.valueOf(ycrStr);
				yrateList.add(df.format(yrate));
			}
			temp.put(yraMap.get("area_name") + "_" + yra, yraList);
			temp.put(ycrMap.get("area_name") + "_" + ycr, ycrList);
			temp.put(ycrMap.get("area_name") + "_yrate", yrateList);
		}
	}

	/**
	 * 处理查询后数据
	 * 
	 * @param temp
	 * @param mp
	 * @param dates
	 * @param string
	 */
	private void dealData(Map<String, List<Object>> temp, List<Map<String, String>> mp, String[] dates, String string)
	{
		for (int i = 0; i < mp.size(); i++)
		{
			Map<String, String> m = mp.get(i);
			// if ("上海市".equals(m.get("area_name").toString()))
			// {
			// continue;
			// }

			List<Object> list = new ArrayList<Object>();
			for (int j = 0; j < dates.length; j++)
			{
				list.add(m.get(string + "_" + dates[j]));
			}
			temp.put(m.get("area_name") + "_" + string, list);
		}

	}

	/**
	 * 处理查询数据
	 * 
	 * @param dataList
	 *          区域初始化数据
	 * @param data
	 *          待处理数据
	 */
	private void dealInfo(List<Map<String, String>> dataList, List<Map<String, String>> data)
	{
		for (Map<String, String> dataMap : dataList)
		{
			String area_name = dataMap.get("area_name");
			for (int i = 0; i < data.size(); i++)
			{
				if (null == data.get(i))
				{
					continue;
				}
				if (area_name.equals(data.get(i).get("area_name")))
				{
					break;
				}
				if (i == data.size() - 1)
				{
					Map<String, String> map = new HashMap<String, String>();
					map.put("area_name", area_name);
					data.add(map);
				}
			}
		}

	}

	/**
	 * 初始化数据
	 * 
	 * @return
	 */
	private List<Map<String, String>> inint()
	{
		List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
		// 查询区域表数据，获取所有区域
		dataList = mktRepDao.getAllAreaName();
		return dataList;
	}

	private static String doBiz(String curr, String other)
	{
		if (StringUtils.equals(curr, "") || StringUtils.equals(curr, "null"))
			curr = "0";
		if (StringUtils.equals(other, "") || StringUtils.equals(other, "null"))
			other = "0";
		double d = 1d;
		if (!(string2double(other) == 0))
		{
			d = string2double(curr) / string2double(other) - 1;
		} else if (!(string2double(curr) == 0))
		{
			d = string2double(curr) / 1 - 1;
		} else
		{
			d = 0;
		}
		// 取小数点后2位，小数点右移2位
		BigDecimal big = new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP).movePointRight(2);
		return String.valueOf(big);
	}

	private static Double string2double(String string)
	{
		Double ret = 0.0d;
		try
		{
			if (string != null)
			{
				ret = Double.valueOf(string);
			}
		} catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Object 转成 String
	 * 
	 * @param obj
	 * @return
	 */
	private String objToString(Object obj)
	{
		if (null == obj)
			return "0";
		else
			return StringUtils.trimToEmpty(String.valueOf(obj));
	}
}
