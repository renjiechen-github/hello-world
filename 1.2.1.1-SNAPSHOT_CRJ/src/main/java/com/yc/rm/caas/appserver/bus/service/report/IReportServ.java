/**
 * 
 */
package com.yc.rm.caas.appserver.bus.service.report;

import com.yc.rm.caas.appserver.bus.controller.view.fo.ViewFo;
import com.yc.rm.caas.appserver.model.report.ReportBean;

/**
 * @author stephen
 * 
 */
public interface IReportServ {

	public abstract ReportBean getReportInfo(ViewFo fo);

	/**
	 * @param fo
	 * 
	 */
	public abstract int[] getChkOutIngHouseNum(ViewFo fo);

	/**
	 * // A时刻累计退租 已经完成退租的 （收、租）
	 * 
	 * @param fo
	 * 
	 */
	public abstract int[] getChkOutNumByDate(ViewFo fo);

	/**
	 * 退租增量 合约状态失效，退租时间在计算周期内（1天内，1周内）
	 */
	public abstract int[] getChkOutNumByDate2Date(ViewFo fo);

	/**
	 * // A时刻 房屋增量
	 * 
	 * @param fo
	 * 
	 */
	public abstract int[] getHouseCreateNumByDate(ViewFo fo);

	/**
	 * A时刻计算 B-A周期之间房屋增量
	 */
	public abstract int[] getHouseCreateNumByDate2Date(ViewFo fo);
}
