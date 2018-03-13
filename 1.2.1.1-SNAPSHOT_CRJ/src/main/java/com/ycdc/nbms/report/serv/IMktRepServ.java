package com.ycdc.nbms.report.serv;

import java.math.BigInteger;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IMktRepServ
{

	Object mktView(HttpServletRequest request);

	/**
	 * 根据网格ID，获取租差和空置率
	 * 
	 * @param user_id
	 *          网格ID
	 * @param time
	 *          查询时间（yyyy-MM），若为空，按当前系统时间查询
	 * @return map[cost_val(租差)：200.2,empty_rate(空置率):20.32%]
	 */
	Map<String, String> getMarketData(String user_id, String teamIds, String time, BigInteger allAreadyHouseCnt, BigInteger allHouseCnt);

	/**
	 * 手工刷新报表
	 * 
	 * @return
	 */
	Object createReport();

}