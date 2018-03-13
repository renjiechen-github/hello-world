/**
 * 
 */
package com.yc.rm.caas.appserver.bus.controller.view;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.rm.caas.appserver.base.support.BaseController;
import com.yc.rm.caas.appserver.bus.controller.view.fo.ViewFo;
import com.yc.rm.caas.appserver.bus.service.report.IReportServ;
import com.yc.rm.caas.appserver.model.report.ReportBean;

/**
 * @author stephen
 * 
 */
@Controller
@RequestMapping("/caas/view")
public class ViewController extends BaseController {
	@Resource
	private IReportServ _reportServImpl;

	/**
	 * @param person
	 * @return
	 */
	@RequestMapping("/getReportView")
	public @ResponseBody
	Object getReportView() {
		log.info("into getReportView");
		ViewFo fo = new ViewFo();
		fo.setDate("2017-04-10 00:00:00");
		fo.setBegin_date("2017-04-01 00:00:00");// 测试设定偏移日期
		ReportBean reportBean = _reportServImpl.getReportInfo(fo);
		return reportBean;
	}

	/**
	 * 退租中的房源数
	 * 
	 * @param fo
	 * 
	 * 
	 * 
	 */
	@RequestMapping("/getChkOutIngHouseNum")
	public @ResponseBody
	Object getChkOutIngHouseNum() {
		log.info("into getChkOutIngHouseNum");
		ViewFo fo = new ViewFo();
		fo.setDate("2017-03-27 00:00:00");
		fo.setBegin_date("2017-03-21 00:00:00");// 测试设定偏移日期
		return _reportServImpl.getChkOutIngHouseNum(fo);
	}

	/**
	 * // A时刻累计退租 已经完成退租的 （收、租）
	 * 
	 * @param fo
	 * 
	 */
	@RequestMapping("/getChkOutNumByDate")
	public @ResponseBody
	Object getChkOutNumByDate(ViewFo fo) {
		return _reportServImpl.getChkOutNumByDate(fo);
	}

	/**
	 * 退租增量 合约状态失效，退租时间在计算周期内（1天内，1周内）
	 */
	@RequestMapping("/getChkOutNumByDate2Date")
	public @ResponseBody
	Object getChkOutNumByDate2Date(ViewFo fo) {
		return _reportServImpl.getChkOutNumByDate2Date(fo);
	}

	/**
	 * // A时刻 房屋增量
	 * 
	 * @param fo
	 * 
	 */
	@RequestMapping("/getHouseCreateNumByDate")
	public @ResponseBody
	Object getHouseCreateNumByDate(ViewFo fo) {
		return _reportServImpl.getHouseCreateNumByDate(fo);
	}

	/**
	 * A时刻计算 B-A周期之间房屋增量
	 */
	@RequestMapping("/getHouseCreateNumByDate2Date")
	public @ResponseBody
	Object getHouseCreateNumByDate2Date(ViewFo fo) {
		return _reportServImpl.getHouseCreateNumByDate2Date(fo);
	}
}
