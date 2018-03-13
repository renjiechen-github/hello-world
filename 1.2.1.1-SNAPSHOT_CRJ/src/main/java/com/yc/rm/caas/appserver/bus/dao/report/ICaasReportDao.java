/**
 * 
 */
package com.yc.rm.caas.appserver.bus.dao.report;

import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.bus.controller.view.fo.ViewFo;

/**
 * @author stephen
 * 
 */

@Component("_caasReportDao")
public interface ICaasReportDao {
	/**
	 * 当前房屋增量 0点之前 ---- 不关注合约状态
	 * 
	 * @param fo
	 * @return
	 */
	public int getHouseCreateNumByDate(ViewFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int getHouseCheckOutNumByDate(ViewFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int getChkOutIngHouseNum(ViewFo fo);

	public int getHouseNum(ViewFo fo);

	public float cumulativeIdle(ViewFo fo);

	public float rentalDiff(ViewFo fo);

	public float getLiveHousePrice(ViewFo fo);

	/**
	 * @return
	 */
	public int getSellerNum();

	/**
	 * @param fo
	 * @return
	 */
	public int workingNum(ViewFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int waitHandNum(ViewFo fo);

	/**
	 * @param fo
	 * @return
	 */
	public int waitWorkNum();

}
