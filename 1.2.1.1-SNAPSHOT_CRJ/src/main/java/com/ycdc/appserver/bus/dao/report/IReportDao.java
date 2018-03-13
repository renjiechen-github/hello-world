/**
 * 
 */
package com.ycdc.appserver.bus.dao.report;

import com.ycdc.appserver.bus.controller.report.fo.ReportFo;

/**
 * @author stephen
 * 
 */
public interface IReportDao {
	/**
	 * 当前房屋增量 0点之前 ---- 不关注合约状态
	 * 
	 * @param fo
	 * @return
	 */
	public int getHouseCreateNumByDate(ReportFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int getHouseCheckOutNumByDate(ReportFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int getChkOutIngHouseNum(ReportFo fo);

	public int getHouseNum(ReportFo fo);

	public float cumulativeIdle(ReportFo fo);

	public float rentalDiff(ReportFo fo);

	public float getLiveHousePrice(ReportFo fo);

	/**
	 * @return
	 */
	public int getSellerNum();

	/**
	 * @param fo
	 * @return
	 */
	public int workingNum(ReportFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int waitHandNum(ReportFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int waitWorkNum();
}
