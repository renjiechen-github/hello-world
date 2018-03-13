/**
 * 
 */
package com.ycdc.appserver.bus.service.report;

import com.ycdc.appserver.bus.controller.report.fo.ReportFo;
import com.ycdc.appserver.model.report.ReportBean;

/**
 * @author stephen
 * 
 */
public interface IReportServ {

	public abstract ReportBean getReportInfo(ReportFo fo);

	/**
	 * @param fo
	 * 
	 */
	public abstract int[] getChkOutIngHouseNum(ReportFo fo);

	/**
	 * // A时刻累计退租 已经完成退租的 （收、租）
	 * 
	 * @param fo
	 * 
	 */
	public abstract int[] getChkOutNumByDate(ReportFo fo);

	/**
	 * 退租增量 合约状态失效，退租时间在计算周期内（1天内，1周内）
	 */
	public abstract int[] getChkOutNumByDate2Date(ReportFo fo);

	/**
	 * // A时刻 房屋增量
	 * 
	 * @param fo
	 * 
	 */
	public abstract int[] getHouseCreateNumByDate(ReportFo fo);

	/**
	 * A时刻计算 B-A周期之间房屋增量
	 */
	public abstract int[] getHouseCreateNumByDate2Date(ReportFo fo);
}
