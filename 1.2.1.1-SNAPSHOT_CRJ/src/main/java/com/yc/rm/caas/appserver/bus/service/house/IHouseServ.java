package com.yc.rm.caas.appserver.bus.service.house;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.model.ResultCondition;
import com.yc.rm.caas.appserver.model.contract.AgreementSelect;
import com.yc.rm.caas.appserver.model.house.Area;
import com.yc.rm.caas.appserver.model.house.House;
import com.yc.rm.caas.appserver.model.house.HouseSelectInfo;
import com.yc.rm.caas.appserver.model.house.Project;
import com.yc.rm.caas.appserver.model.house.RankAgreement;
import com.yc.rm.caas.appserver.model.house.RankAgreementSelect;
import com.yc.rm.caas.appserver.model.house.RankHouse;
import com.yc.rm.caas.appserver.model.syscfg.DictiItem;

/**
 * 房源管理
 * 
 * @date 2016年11月28日
 */
public interface IHouseServ {
	List<House> getHouseList(HouseFo condition);

	ResultCondition getResultCondition();


	ResultCondition resultCondition(String flag);
	
	/**
	 * 查询商圈
	 * 
	 * @param father_id
	 * @param area_type
	 * @return
	 */
	List<Area> getAreaList(String father_id, String area_type);

	/**
	 * 根据id查询房源信息
	 * 
	 * @param id
	 * @return
	 */
	House getHouseInfo(String id);

	HouseSelectInfo getHouseSelectInfo();

	Project getProjectInfo(String agreementId);

	AgreementSelect initAgreementSelect();

	List<RankHouse> loadRankHouseList(String house_id);

	RankHouse loadRankHouseInfo(String rankId);

	List<RankAgreement> loadRankAgreementList(String rankId);

	RankAgreementSelect loadRankAgreementSelect(JSONObject json);

	Object saveHouse(House house, HttpServletRequest request);

	List<com.yc.rm.caas.appserver.model.house.Area> loadGroupList(HouseFo condition);

	Object delHouseInfo(JSONObject json);

	Object transferHouse(JSONObject json);

	int isSelfHouse(RankAgreement rankAgreement);

	Object checkRankHouseStatus(String id);

	Object submitHouse(JSONObject json);

	Object spHouse(JSONObject json);

	Object checkHouseState(JSONObject json);

	List<DictiItem> getLvInfo(String cnt);

	//Map<String, String> loadHousePageCnt(JSONObject json, IMktRepServ mktRepServ);

	List<Map<String, String>> loadPayCntList(JSONObject json,HttpServletRequest request);
}
