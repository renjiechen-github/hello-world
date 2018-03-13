package com.ycdc.appserver.bus.service.house;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.agreement.AgreementMgeService;
import net.sf.json.JSONObject;

import com.ycdc.appserver.model.house.Agreement;
import com.ycdc.appserver.model.house.AgreementSelect;
import com.ycdc.appserver.model.house.House;
import com.ycdc.appserver.model.house.HouseCondition;
import com.ycdc.appserver.model.house.HouseSelectInfo;
import com.ycdc.appserver.model.house.Project;
import com.ycdc.appserver.model.house.RankAgreement;
import com.ycdc.appserver.model.house.RankAgreementSelect;
import com.ycdc.appserver.model.house.RankHouse;
import com.ycdc.appserver.model.house.ResultCondition;
import com.ycdc.appserver.model.syscfg.Area;
import com.ycdc.appserver.model.syscfg.DictiItem;
import com.ycdc.appserver.model.user.User;
import com.ycdc.nbms.report.serv.IMktRepServ;

/**
 * 房源管理
 * @author suntf
 * @date 2016年11月28日
 */
public interface HouseMgrService
{
	List<House> getHouseList(HouseCondition condition);
	
	ResultCondition getResultCondition();
	
	List<Area> getAreaList(String father_id, String area_type);
	
	House getHouseInfo(String id);
	
	HouseSelectInfo getHouseSelectInfo();
	
	Project getProjectInfo(String agreementId);
	
	/**
	 * 获取合约信息
	 * @param agreementId
	 * @return
	 */
	Agreement getAgreementInfo(String agreementId, String house_id);
	
	AgreementSelect initAgreementSelect();
	
	List<RankHouse> loadRankHouseList(String house_id);
	
	RankHouse loadRankHouseInfo(String rankId);
	
	List<RankAgreement> loadRankAgreementList(String rankId);
	
	RankAgreementSelect loadRankAgreementSelect(JSONObject json);
	
	List<RankAgreement> getRankAgreementInfo(JSONObject json);
	
	User getUserInfoByMobile(String mobile);
	
	Object saveRankAgreement(RankAgreement rankAgreement, HttpServletRequest request);
	
	Object saveCRankAgreement(RankAgreement rankAgreement, HttpServletRequest request);
	
	Object dealFinance(HttpServletRequest request);
	
	Object checkRankHouseStatus(String id);
	
	Object rankAgreementNotarization(String agreementId, String userId, String operId, String houseId, String isValidate);
	
	Object agreementNotarization(String agreementId, String userId, String operId, String houseId, String isValidate);
	
	Object saveHouse(House house, HttpServletRequest request);
	
	List<com.ycdc.appserver.model.house.Area> loadGroupList(HouseCondition condition);
	
	Object delHouseInfo(JSONObject json);
	
	Object transferHouse(JSONObject json);
	
	int isSelfHouse(RankAgreement rankAgreement);
	
	Object submitHouse(JSONObject json);
	
	Object spHouse(JSONObject json);
	
	ResultCondition agreementCondition(String flag);
	
	List<Agreement> agreementList(HouseCondition condition);
	
	List<RankAgreement> rankAgreementList(HouseCondition condition, HttpServletRequest request);
	
	Object cancelHouseAgreement(JSONObject json);
	
	Object spHouserAgeement(HttpServletRequest request, Financial financial, Onstruction onstruction, AgreementMgeService agreementMgeService, JSONObject json);
	
	Map<String,String> saveHouseAgreement(Agreement agreement, HttpServletRequest request);
	
	List<Map<String,String>> getBankList(JSONObject json);
	
	Map<String,Object> getBankSearchConditionList();
	
	Map<String,Object> getAgreementDetailSelect();
	
	Object checkHouseState(JSONObject json);
	
	List<DictiItem> getLvInfo(String cnt);
	
	Map<String,Object> loadHousePageCnt(JSONObject json, IMktRepServ mktRepServ, HttpServletRequest request);
	
	List<Map<String,String>> loadPayCntList(JSONObject json,HttpServletRequest request);
}
