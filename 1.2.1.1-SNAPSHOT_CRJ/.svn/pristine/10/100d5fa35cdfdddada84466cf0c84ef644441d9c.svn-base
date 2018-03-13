/**
 * 
 */
package com.ycdc.appserver.bus.controller.report;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycdc.appserver.bus.controller.BaseController;
import com.ycdc.appserver.bus.controller.report.fo.ReportFo;
import com.ycdc.appserver.bus.service.report.IReportServ;
import com.ycdc.appserver.model.report.ReportBean;

/**
 * @author stephen
 * 
 */
@Controller
@RequestMapping("homeView/report")
public class ReportController extends BaseController
{
	@Resource
	private IReportServ reportServImpl;

	/**
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "/getReportView.do")
	public @ResponseBody ReportBean getReportView(@RequestBody ReportFo fo)
	{
		log.info("into getReportView" + fo.getDate());
		ReportBean reportBean = reportServImpl.getReportInfo(fo);
		return reportBean;
	}

	/**
	 * 退租中的房源数
	 * 
	 * @param fo
	 * 
	 */
	@RequestMapping(value = "/getChkOutIngHouseNum.do", method = RequestMethod.GET)
	public @ResponseBody int[] getChkOutIngHouseNum()
	{
		log.info("into getChkOutIngHouseNum");
		ReportFo fo = new ReportFo();
		fo.setDate("2017-03-27 00:00:00");
		fo.setBegin_date("2017-03-21 00:00:00");// 测试设定偏移日期
		return reportServImpl.getChkOutIngHouseNum(fo);
	}

	/**
	 * // A时刻累计退租 已经完成退租的 （收、租）
	 * 
	 * @param fo
	 * 
	 */
	@RequestMapping(value = "/getChkOutNumByDate.do", method = RequestMethod.GET)
	public @ResponseBody int[] getChkOutNumByDate(ReportFo fo)
	{
		return reportServImpl.getChkOutNumByDate(fo);
	}

	/**
	 * 退租增量 合约状态失效，退租时间在计算周期内（1天内，1周内）
	 */
	@RequestMapping(value = "/getChkOutNumByDate2Date.do", method = RequestMethod.GET)
	public @ResponseBody int[] getChkOutNumByDate2Date(ReportFo fo)
	{
		return reportServImpl.getChkOutNumByDate2Date(fo);
	}

	/**
	 * // A时刻 房屋增量
	 * 
	 * @param fo
	 * 
	 */
	@RequestMapping(value = "/getHouseCreateNumByDate.do", method = RequestMethod.GET)
	public @ResponseBody int[] getHouseCreateNumByDate(ReportFo fo)
	{
		return reportServImpl.getHouseCreateNumByDate(fo);
	}

	/**
	 * A时刻计算 B-A周期之间房屋增量
	 */
	@RequestMapping(value = "/getHouseCreateNumByDate2Date.do", method = RequestMethod.GET)
	public @ResponseBody int[] getHouseCreateNumByDate2Date(ReportFo fo)
	{
		return reportServImpl.getHouseCreateNumByDate2Date(fo);
	}
}
