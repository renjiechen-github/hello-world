package com.yc.rm.caas.appserver.bus.service.contract;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.agreement.AgreementMgeService;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.model.contract.ContractBean;
import com.yc.rm.caas.appserver.model.house.RankAgreement;

/**
 * 
 * @author stephen
 * 
 */
public interface IContractServ {
	List<RankAgreement> getRankAgreementInfo(JSONObject json);

	Object saveRankAgreement(RankAgreement rankAgreement,
			HttpServletRequest request);

	Map<String, String> rankAgreementNotarization(String agreementId,
			String userId, String operId, String houseId, String isValidate,
			boolean flag);

	Object dealFinance(HttpServletRequest request);

	List<ContractBean> agreementList(HouseFo condition);

	List<RankAgreement> rankAgreementList(HouseFo condition);

	Object cancelHouseAgreement(JSONObject json);

	Object spHouserAgeement(HttpServletRequest request, Financial financial,
			Onstruction onstruction, AgreementMgeService agreementMgeService,
			JSONObject json);

	List<Map<String, String>> getBankList(JSONObject json);

	Map<String, Object> getBankSearchConditionList();

	Map<String, Object> getAgreementDetailSelect();

	Map<String, String> saveHouseAgreement(ContractBean agreement,
			HttpServletRequest request);

	/**
	 * 获取合约信息
	 * 
	 * @param agreementId
	 * @return
	 */
	ContractBean getAgreementInfo(String agreementId, String house_id);
}
