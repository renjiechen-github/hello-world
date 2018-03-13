/**
 * 
 */
package com.yc.rm.caas.appserver.bus.controller.contract;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yc.rm.caas.appserver.base.support.BaseController;

/**
 * @author stephen
 * 
 */
@Controller
@RequestMapping("/caas/contract")
public class ContractController extends BaseController {
//	@Autowired
//	private AgreementMgeService agreementMgeService;
//	@Autowired
//	private IContractServ contractServ;
//	@Autowired
//	private pccom.web.interfaces.Financial financial;
//	@Autowired
//	private pccom.web.interfaces.Onstruction onstruction;
//	@Autowired
//	private pccom.web.interfaces.RankHouse rankHouse;
//	@Autowired
//	private RankHouseService rankHouseService;
//
//	/**
//	 * 合约明细
//	 * 
//	 * @param agreement
//	 * @return
//	 */
//	@RequestMapping(value = "getAgreementInfo.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Agreement getAgreementInfo(@RequestBody Agreement agm) {
//		log.debug("getAgreementInfo");
//		log.debug(JSONObject.toJSONString(agm));
//		return contractServ.getAgreementInfo(agm.getId(), agm.getHouse_id());
//	}
//	
//	/**
//	 * 加载出租合约明细
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "getRankAgreementInfo.do", method = RequestMethod.POST)
//	public @ResponseBody
//	List<RankAgreement> getRankAgreementInfo(@RequestBody JSONObject json) {
//		log.info("into getRankAgreementInfo");
//		log.debug(json);
//		return contractServ.getRankAgreementInfo(json);
//	}
//
//	/**
//	 * 保存合约信息
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "saveRankAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object saveRankAgreement(@RequestBody RankAgreement rankAgreement,
//			HttpServletRequest request) {
//		log.info("into saveRankAgreement");
//		log.debug("rankAgreement:" + JSONObject.toJSONString(rankAgreement));
//		Object mp = contractServ.saveRankAgreement(rankAgreement, request);
//		return mp;
//	}
//
//	/**
//	 * 出租合约公证
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "rankAgreementNotarization.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object rankAgreementNotarization(@RequestBody JSONObject mp,
//			HttpServletRequest request) {
//		String agreementId = super.get(mp, "id"); // 出租合约id
//		String userId = super.get(mp, "userId"); // 用户编号
//		String operId = super.get(mp, "operId"); // 操作人编号
//		String houseId = super.get(mp, "houseId"); // 房源编号
//		return contractServ.rankAgreementNotarization(agreementId, userId,
//				operId, houseId, "1", true);
//	}
//
//	/**
//	 * 处理合约信息
//	 * 
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "dealFinance.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object dealFinance(HttpServletRequest request) {
//		log.info("into dealFinance 空方法");
//		return contractServ.dealFinance(request);
//	}
//
//	/**
//	 * 删除租赁合约信息
//	 * 
//	 * @param mp
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "delRankAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object delRankAgreement(@RequestBody JSONObject mp,
//			HttpServletRequest request) {
//		log.info("into delRankAgreement");
//		log.debug("mp:" + JSONObject.toJSONString(mp));
//		return this.initRankAgreement(request, mp, rankHouse, financial);
//	}
//
//	/**
//	 * 删除租赁合约信息 状态为租赁公证
//	 * 
//	 * @param mp
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "delRankAgreementByStatus.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object delRankAgreementByStatus(@RequestBody JSONObject mp,
//			HttpServletRequest request) {
//		return this.initRankAgreement(request, mp, rankHouse, financial);
//	}
//
//	public Map initRankAgreement(HttpServletRequest request, JSONObject mp,
//			pccom.web.interfaces.RankHouse rankHouse,
//			pccom.web.interfaces.Financial financial) {
//		Map<String, String> resultMp = new HashMap<String, String>();
//		Map resultMap = rankHouseService.delRankAgreement(request, rankHouse,
//				net.sf.json.JSONObject.fromObject(mp), financial);
//		String state = super.get(resultMap, "state");
//		if ("1".equals(state)) {
//			resultMp.put("msg", "操作成功");
//		} else if ("-2".equals(state)) {
//			resultMp.put("msg", "合约状态发生改变,请重新确认!");
//		} else if ("-12".equals(state)) {
//			resultMp.put("msg", "当前房源已下架,请勿删除!");
//		} else {
//			resultMp.put("msg", "网络忙,稍候重试!");
//		}
//		resultMp.put("state", state);
//		return resultMp;
//	}
//
//	/**
//	 * 收房合约公证
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "agreementNotarization.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object agreementNotarization(@RequestBody JSONObject mp,
//			HttpServletRequest request) {
//		String agreementId = super.get(mp, "id"); // 合约id
//		String userId = super.get(mp, "userId"); // 用户编号
//		String operId = super.get(mp, "operId"); // 操作人编号
//		String houseId = super.get(mp, "houseId"); // 房源编号
//		return contractServ.rankAgreementNotarization(agreementId, userId,
//				operId, houseId, "1", false);
//	}
//
//	/**
//	 * 收房合约列表
//	 * 
//	 * @param condition
//	 * @return
//	 */
//	@RequestMapping(value = "agreementList.do", method = RequestMethod.POST)
//	public @ResponseBody
//	List<Agreement> agreementList(@RequestBody HouseFo condition) {
//		return contractServ.agreementList(condition);
//	}
//
//	/**
//	 * 出租合约列表
//	 * 
//	 * @param condition
//	 * @return
//	 */
//	@RequestMapping(value = "rankAgreementList.do", method = RequestMethod.POST)
//	public @ResponseBody
//	List<RankAgreement> rankAgreementList(@RequestBody HouseFo condition) {
//		// log.info(JSONObject.toJSONString(condition));
//		log.debug(JSONObject.toJSONString(condition));
//		return contractServ.rankAgreementList(condition);
//	}
//
//	/**
//	 * 撤销房源合约
//	 * 
//	 * @param json
//	 * @return
//	 */
//	@RequestMapping(value = "cancelHouseAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object cancelHouseAgreement(@RequestBody JSONObject json) {
//		// log.info("json:"+json);
//		log.debug("json:" + json);
//		return contractServ.cancelHouseAgreement(json);
//	}
//
//	/**
//	 * 审批房源合约
//	 * 
//	 * @param json
//	 * @return
//	 */
//	@RequestMapping(value = "spHouserAgeement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object spHouserAgeement(@RequestBody JSONObject json,
//			HttpServletRequest request) {
//		// log.info("json:"+json);
//		// final HttpServletRequest request, final Financial financial, final
//		// Onstruction
//		// onstruction, final JSONObject json
//		return contractServ.spHouserAgeement(request, financial, onstruction,
//				agreementMgeService, json);
//	}
//
//	/**
//	 * 获取银行信息
//	 * 
//	 * @param json
//	 * @return
//	 */
//	@RequestMapping(value = "getBankList.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object getBankList(@RequestBody JSONObject json) {
//		// log.info(JSONArray.toJSONString(object));
//		return contractServ.getBankList(json);
//	}
//
//	/**
//	 * 获取银行查询条件
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "getBankSearchConditionList.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object getBankSearchConditionList() {
//		// log.info(JSONObject.toJSONString(contractServ.getBankSearchConditionList()));
//		return contractServ.getBankSearchConditionList();
//	}
//
//	/**
//	 * 获取合约明细下拉框
//	 * 
//	 * @return
//	 */
//	@RequestMapping(value = "getAgreementDetailSelect.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object getAgreementDetailSelect() {
//		// log.info(JSONObject.toJSONString(contractServ.getAgreementDetailSelect()));
//		return contractServ.getAgreementDetailSelect();
//	}
//
//	/**
//	 * 保存收房合约
//	 * 
//	 * @param agreement
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "saveAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object saveAgreement(@RequestBody Agreement agreement,
//			HttpServletRequest request) {
//		return contractServ.saveHouseAgreement(agreement, request);
//	}
//
//	/**
//	 * 删除收房合约
//	 * 
//	 * @param agreement
//	 * @param request
//	 * @return
//	 */
//	@RequestMapping(value = "delAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object delAgreement(@RequestBody JSONObject json, HttpServletRequest request) {
//		return agreementMgeService.newDelAgreement(
//				net.sf.json.JSONObject.fromObject(json), request);
//	}
//
//	/**
//	 * 待公证审批房源
//	 * 
//	 * @param json
//	 * @return
//	 */
//	@RequestMapping(value = "spHouseAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object spHouseAgreement(@RequestBody JSONObject json,
//			HttpServletRequest request) {
//		return agreementMgeService
//				.approvalHouseAgreement(request,
//						net.sf.json.JSONObject.fromObject(json), financial,
//						onstruction);
//	}
//
//	/**
//	 * 下载合约信息
//	 * 
//	 * @param json
//	 * @return
//	 */
//	@RequestMapping(value = "downloadAgreement.do", method = RequestMethod.POST)
//	public @ResponseBody
//	Object downloadAgreement(@RequestBody JSONObject json) {
//		return agreementMgeService.downloadAgreement(net.sf.json.JSONObject
//				.fromObject(json));
//	}
//
//	
}
