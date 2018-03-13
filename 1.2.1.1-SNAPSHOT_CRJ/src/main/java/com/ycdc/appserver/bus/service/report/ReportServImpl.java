/**
 * 
 */
package com.ycdc.appserver.bus.service.report;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ycdc.appserver.bus.controller.report.fo.ReportFo;
import com.ycdc.appserver.bus.dao.report.IReportDao;
import com.ycdc.appserver.bus.service.BaseService;
import com.ycdc.appserver.model.report.ReportBean;

/**
 * @author stephen
 * 
 */
@Service("reportServImpl")
public class ReportServImpl extends BaseService implements IReportServ {
	@Resource
	private IReportDao reportDao;

	/* (non-Javadoc)
	 * @see com.ycdc.appserver.bus.service.report.IReportSer#getReportInfo(com.ycdc.appserver.bus.controller.report.fo.ReportFo)
	 */
	@Override
	public ReportBean getReportInfo(ReportFo fo) {
		log.debug("fo:" + JSONObject.toJSONString(fo));		
		ReportBean bean = new ReportBean();
		// 存放请求时间
		bean.setDate(fo.getDate());
		bean.setBegin_date(fo.getBegin_date());
		bean.setTime_flag(fo.getTime_flag());		
		// A时刻计算 B-A周期之间房屋增量
		int[] b_a_add = getHouseCreateNumByDate2Date(fo);
		bean.setB_a_add_get(b_a_add[0]);
		bean.setB_a_add_rent(b_a_add[1]);
		// ===================================================================
		// =========================退房统计依赖checkouttime====================
		// A时刻计算 B-A周期之间退租增量 合约状态失效，退租时间在计算周期内（1天内，1周内）
		int[] b_a_chk = getChkOutNumByDate2Date(fo);
		bean.setB_a_chk_get(b_a_chk[0]);
		bean.setB_a_chk_rent(b_a_chk[1]);
		fo.setBegin_date(null);//后面的sql不需要begindate 将begindate设null
		// ===================================================================
		// A时刻 房屋增量
		int[] a_add = getHouseCreateNumByDate(fo);
		bean.setA_add_get(a_add[0]);
		bean.setA_add_rent(a_add[1]);		
		// =========================退房统计依赖checkouttime====================
		// A时刻累计退租 已经完成退租的 （收、租）
		int[] a_chk = getChkOutNumByDate(fo);
		bean.setA_chk_get(a_chk[0]);
		bean.setA_chk_rent(a_chk[1]);
		// ===================================================================
		// A时刻房源数量（收、租）
		int[] a_total = getHouseNum(fo);
		bean.setA_total_get(a_total[0]);
		bean.setA_total_rent(a_total[1]);
		// ===================================================================
		// 最新的退租中数据 筛选状态为退租中的 （收、租），由于退租中状态何时改变的，所以无法统计出历史某一时刻的退租中数据
		int[] a_chkouting = getChkOutIngHouseNum(fo);
		bean.setA_chkouting_get(a_chkouting[0]);
		bean.setA_chkouting_rent(a_chkouting[1]);
		// ===================================================================
		// -- 当前空置 （当前总-当前出）/当前总
		float idle = 0 ;
		try {
			idle = (a_total[0] - a_total[1]) * 100 / a_total[0] ;
		} catch (Exception e) {
			log.error("",e);
		}
		bean.setIdle(idle);
		log.debug("当前空置:" + idle);
		// ===================================================================
		// -- 当前施工中
		int working = reportDao.workingNum(fo);
		bean.setWorkingNum(working);	
		// ===================================================================
		// -- 当前施工中
		int waitHand = reportDao.waitHandNum(fo);
		bean.setWaitHand(waitHand);
		// 当前未开工
		int waitWork = reportDao.waitWorkNum();
		log.debug("当前未开工：" + waitWork);
		bean.setWaitWork(waitWork);
		// 待出租  = 当前总 - 当前出 - 施工中 - 待交接 - 未开工
		bean.setWaitRent(a_total[0] - a_total[1] - bean.getWaitHand()-bean.getWorkingNum() - bean.getWaitRent() - waitWork);
		log.debug("待出租：" + bean.getWaitRent());
		// ===================================================================
		// -- 累计空置 出租（状态=2：合同开始时间~今天 + 状态退租中和失效：合同开始时间~退租时间）/
		// 收房（状态=2：合同开始时间~今天-免租期 + 状态退租中和失效：合同开始时间~退租时间-免租期）
		float cumIdle = reportDao.cumulativeIdle(fo);
		bean.setCumIdle(cumIdle);
		log.debug("累计空置:" + cumIdle);
		// -- 租差 （状态=2 出租合同的月租金）/出租间数 - （对应收房合同的月支出（找到当前这个月对应的房租））/收房间数
		float rentalDiff = reportDao.rentalDiff(fo);
		bean.setRentalDiff(rentalDiff);
		log.debug("租差:" + rentalDiff);
		// -- 单间成本 所有收房合同（家具+装修+家电）/间 ------------------
		float singlePrice = reportDao.getLiveHousePrice(fo);
		bean.setSinglePrice(singlePrice);
		log.debug("单间成本:" + singlePrice);
		// -- 人均效能 当前收房总间数 / 当前有效管家总数 ------------------
		int num = reportDao.getSellerNum();
		int perNum = a_total[0] / num;
		bean.setPerNum(perNum);
		log.debug("人均效能:" + perNum);
		return bean;

	}

	/* (non-Javadoc)
	 * @see com.ycdc.appserver.bus.service.report.IReportSer#getChkOutIngHouseNum(com.ycdc.appserver.bus.controller.report.fo.ReportFo)
	 */
	@Override
	public int[] getChkOutIngHouseNum(ReportFo fo) {
		int[] ret = new int[2];
		fo.setAgreeType(1);
		int getCheckOutNow = reportDao.getChkOutIngHouseNum(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "收房退租中数量:" + getCheckOutNow);
		fo.setAgreeType(2);
		int rentCheckOutNow = reportDao.getChkOutIngHouseNum(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "出租退租中数量:" + rentCheckOutNow);
		ret[0] = getCheckOutNow;
		ret[1] = rentCheckOutNow;
		return ret;
	}

	/**
	 * @param fo
	 * @return
	 */
	private int[] getHouseNum(ReportFo fo) {
		int[] ret = new int[2];
		fo.setAgreeType(1);
		int getNow = reportDao.getHouseNum(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "房源数量:" + getNow);
		fo.setAgreeType(2);
		int rentNow = reportDao.getHouseNum(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "出租数量:" + rentNow);
		ret[0] = getNow;
		ret[1] = rentNow;
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.ycdc.appserver.bus.service.report.IReportSer#getChkOutNumByDate(com.ycdc.appserver.bus.controller.report.fo.ReportFo)
	 */
	@Override
	public int[] getChkOutNumByDate(ReportFo fo) {
		int[] ret = new int[2];
		fo.setAgreeType(1);
		int getCumCheckOutNow = reportDao.getHouseCheckOutNumByDate(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "收房退租累积数量:" + getCumCheckOutNow);
		fo.setAgreeType(2);
		int rentCumCheckOutNow = reportDao.getHouseCheckOutNumByDate(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "出租退租累积数量:" + rentCumCheckOutNow);
		ret[0] = getCumCheckOutNow;
		ret[1] = rentCumCheckOutNow;
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.ycdc.appserver.bus.service.report.IReportSer#getChkOutNumByDate2Date(com.ycdc.appserver.bus.controller.report.fo.ReportFo)
	 */
	@Override
	public int[] getChkOutNumByDate2Date(ReportFo fo) {
		int[] ret = new int[2];
		// -- 收房
		fo.setAgreeType(1);
		int getB2Eout = reportDao.getHouseCheckOutNumByDate(fo);
		log.debug(fo.getBegin_date() + "-" + fo.getDate().replaceAll("00:00:00", "23:59:59") + "收房退租增量:"
				+ getB2Eout);
		// -- 出租
		fo.setAgreeType(2);
		int rentB2Eout = reportDao.getHouseCheckOutNumByDate(fo);
		log.debug(fo.getBegin_date() + "-" + fo.getDate().replaceAll("00:00:00", "23:59:59") + "出租退租增量:"
				+ rentB2Eout);
		ret[0] = getB2Eout;
		ret[1] = rentB2Eout;
		return ret;
	}
	
	public static void main(String[] args) {
		float a = (5653 - 4136) * 100 / 5653;
		//logger.debug(a);
	}

	/* (non-Javadoc)
	 * @see com.ycdc.appserver.bus.service.report.IReportSer#getHouseCreateNumByDate(com.ycdc.appserver.bus.controller.report.fo.ReportFo)
	 */
	@Override
	public int[] getHouseCreateNumByDate(ReportFo fo) {
		int[] ret = new int[2];
		// 收
		fo.setAgreeType(1);
		int getCumNow = reportDao.getHouseCreateNumByDate(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "累计房源数量:" + getCumNow);
		// 出
		fo.setAgreeType(2);
		int rentCumNow = reportDao.getHouseCreateNumByDate(fo);
		log.debug(fo.getDate().replaceAll("00:00:00", "") + "累计出租数量:" + rentCumNow);
		ret[0] = getCumNow;
		ret[1] = rentCumNow;
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.ycdc.appserver.bus.service.report.IReportSer#getHouseCreateNumByDate2Date(com.ycdc.appserver.bus.controller.report.fo.ReportFo)
	 */
	@Override
	public int[] getHouseCreateNumByDate2Date(ReportFo fo) {
		int[] ret = new int[2];
		// 收
		fo.setAgreeType(1);
		int getB2E = reportDao.getHouseCreateNumByDate(fo);
		log.debug(fo.getBegin_date() + "-" + fo.getDate().replaceAll("00:00:00", "23:59:59") + "收房增量:" + getB2E);
		// 出
		fo.setAgreeType(2);
		int rentB2E = reportDao.getHouseCreateNumByDate(fo);
		log.debug(fo.getBegin_date() + "-" + fo.getDate().replaceAll("00:00:00", "23:59:59") + "出租增量:" + rentB2E);
		ret[0] = getB2E;
		ret[1] = rentB2E;
		return ret;
	}
}
