package com.yc.rm.caas.appserver.bus.controller.house;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pccom.web.server.agreement.AgreementMgeService;
import pccom.web.server.house.rankhouse.RankHouseService;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.base.support.BaseController;
import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.bus.service.contract.IContractServ;
import com.yc.rm.caas.appserver.bus.service.house.IHouseServ;
import com.yc.rm.caas.appserver.bus.service.user.IUserServ;
import com.yc.rm.caas.appserver.model.ResultCondition;
import com.yc.rm.caas.appserver.model.contract.ContractBean;
import com.yc.rm.caas.appserver.model.house.Area;
import com.yc.rm.caas.appserver.model.house.House;
import com.yc.rm.caas.appserver.model.house.HouseSelectInfo;
import com.yc.rm.caas.appserver.model.house.Project;
import com.yc.rm.caas.appserver.model.house.RankAgreement;
import com.yc.rm.caas.appserver.model.house.RankAgreementSelect;
import com.yc.rm.caas.appserver.model.house.RankHouse;
import com.yc.rm.caas.appserver.util.AES_CBCUtils;
import com.yc.rm.caas.appserver.util.Apis;

@Controller
@RequestMapping("/caas/house")
public class HouseController extends BaseController {
	@Autowired
	private IHouseServ _houseServImpl;
	@Autowired
	public RankHouseService rankHouseService;
	@Autowired
	public pccom.web.interfaces.RankHouse rankHouse;
	@Autowired
	private pccom.web.interfaces.OrderInterfaces orderInterfaces;
	@Autowired
	private AgreementMgeService agreementMgeService;
//	@Resource
//	private IMktRepServ mktRepServ;
	@Autowired
	private pccom.web.interfaces.Financial financial;
	@Autowired
	private pccom.web.interfaces.Onstruction onstruction;
	@Autowired
	private IContractServ contractServ;
	@Autowired
	private IUserServ userService;

	/**
	 * 获取房源列表信息
	 * 
	 * @param condition
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getHouseList")
	public @ResponseBody
	Object getHouseList(@RequestBody HouseFo fo) {
		log.info("init getHouseList");
		log.debug("condition:" + fo);
		List<House> houseList = new ArrayList<House>();
		houseList = _houseServImpl.getHouseList(fo);
		return houseList;
	}

	/**
	 * 获取条件查询集合
	 * 
	 * @return
	 */
	@RequestMapping("getConditionList")
	public @ResponseBody
	Object getConditionList() {
		log.debug("init getConditionList");
		return _houseServImpl.getResultCondition();
	}

	/**
	 * 获取商圈编号
	 * 
	 * @return
	 */
	@RequestMapping("getAreaList")
	public @ResponseBody
	Object getAreaList(@RequestBody Area area) {
		return _houseServImpl.getAreaList(area.getId(), area.getArea_type());
	}

	/**
	 * 获取房源下拉框信息
	 * 
	 * @return
	 */
	@RequestMapping("getHouseSelectInfo")
	public @ResponseBody
	Object getHouseSelectInfo(@RequestBody int teamId) {
		 //TeamParamControl.getParam(teamId);
		 HouseSelectInfo ret = null;
		 return ret;
		
	}

	/**
	 * 获取房源信息
	 * 
	 * @param house
	 * @return
	 */
	@RequestMapping("getHouseInfo")
	public @ResponseBody
	Object getHouseInfo(@RequestBody House house) {
		return _houseServImpl.getHouseInfo(house.getId());
	}

	/**
	 * 获取工程信息
	 * 
	 * @param agreement
	 */
	@RequestMapping("getProjectInfo")
	public @ResponseBody
	Object getProjectInfo(@RequestBody ContractBean agm) {
		log.debug("getProjectInfo");
		log.debug(JSONObject.toJSONString(agm));
		return _houseServImpl.getProjectInfo(agm.getId());
	}

	/**
	 * 加载出租房源列表
	 * 
	 * @param agreement
	 * @return
	 */
	@RequestMapping("loadRankHouseList")
	public @ResponseBody
	Object loadRankHouseList(@RequestBody House house) {
		log.debug("getAgreementInfo");
		log.debug(JSONObject.toJSONString(house));
		return _houseServImpl.loadRankHouseList(house.getId());
	}

	/**
	 * 初始化合约下拉框
	 * 
	 * @param agreement
	 * @return
	 */
	@RequestMapping("loadRankHouseInfo")
	public @ResponseBody
	Object loadRankHouseInfo(@RequestBody RankHouse rankHouse) {
		log.debug("loadRankHouseInfo");
		log.debug(JSONObject.toJSONString(rankHouse));
		return _houseServImpl.loadRankHouseInfo(rankHouse.getId());
	}

	/**
	 * 出租房源列表信息
	 * 
	 * @param rankHouse
	 * @return
	 */
	@RequestMapping("loadRankAgreementList")
	public @ResponseBody
	Object loadRankAgreementList(@RequestBody RankHouse rankHouse) {
		log.info("loadRankAgreementList");
		log.info(JSONObject.toJSONString(rankHouse));
		return _houseServImpl.loadRankAgreementList(rankHouse.getId());
	}

	/**
	 * 加载出租合约列表下拉框
	 * 
	 * @return
	 */
	@RequestMapping("loadRankAgreementSelect")
	public @ResponseBody
	Object loadRankAgreementSelect(@RequestBody JSONObject json) {
		// log.info(JSONObject.toJSONString(_houseServImpl.loadRankAgreementSelect()));
		return _houseServImpl.loadRankAgreementSelect(json);
	}

	// =================================================================================================================================
	/**
	 * 加载出租合约明细
	 * 
	 * @return
	 */
	@RequestMapping("getRankAgreementInfo")
	public @ResponseBody
	Object getRankAgreementInfo(@RequestBody JSONObject json) {
		log.info("into getRankAgreementInfo");
		log.debug(json.toJSONString());
		return contractServ.getRankAgreementInfo(json);
	}

	/**
	 * 保存合约信息
	 * 
	 * @return
	 */
	@RequestMapping("saveRankAgreement")
	public @ResponseBody
	Object saveRankAgreement(@RequestBody RankAgreement rankAgreement,
			HttpServletRequest request) {
		log.info("into saveRankAgreement");
		log.debug("rankAgreement:" + JSONObject.toJSONString(rankAgreement));
		Object mp = contractServ.saveRankAgreement(rankAgreement, request);
		return mp;
	}

	/**
	 * 处理合约信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("dealFinance")
	public @ResponseBody
	Object dealFinance(HttpServletRequest request) {
		log.info("into dealFinance 空方法");
		return contractServ.dealFinance(request);
	}

	/**
	 * 签约前,验证出租房源状态是否为待签约
	 * 
	 * @param rankAgreement
	 * @return
	 */
	@RequestMapping("checkRankHouseStatus")
	public @ResponseBody
	Object checkRankHouseStatus(@RequestBody RankAgreement rankAgreement) {
		log.info("checkRankHouseStatus");
		log.debug("rankAgreement:" + JSONObject.toJSONString(rankAgreement));
		return _houseServImpl.checkRankHouseStatus(rankAgreement.getId());
	}

	/**
	 * 删除租赁合约信息
	 * 
	 * @param mp
	 * @param request
	 * @return
	 */
	@RequestMapping("delRankAgreement")
	public @ResponseBody
	Object delRankAgreement(@RequestBody JSONObject mp,
			HttpServletRequest request) {
		log.info("into delRankAgreement");
		log.debug("mp:" + JSONObject.toJSONString(mp));
		return this.initRankAgreement(request, mp, rankHouse, financial);
	}

	/**
	 * 删除租赁合约信息 状态为租赁公证
	 * 
	 * @param mp
	 * @param request
	 * @return
	 */
	@RequestMapping("delRankAgreementByStatus")
	public @ResponseBody
	Object delRankAgreementByStatus(@RequestBody JSONObject mp,
			HttpServletRequest request) {
		return this.initRankAgreement(request, mp, rankHouse, financial);
	}

	public Map initRankAgreement(HttpServletRequest request, JSONObject mp,
			pccom.web.interfaces.RankHouse rankHouse,
			pccom.web.interfaces.Financial financial) {
		Map<String, String> resultMp = new HashMap<String, String>();
		Map resultMap = rankHouseService.delRankAgreement(request, rankHouse,
				net.sf.json.JSONObject.fromObject(mp), financial);

		// =============
		/*
		return getReturnMap(db.doInTransaction(new Batch<Object>() {
		
			@Override
			public Object execute() throws Exception {
				// TODO Auto-generated method stub
				String id = StringHelper.get(mp, "id"); // 租赁合约id
				String house_rank_id = StringHelper.get(mp, "house_rank_id"); // 租赁房源
				String houseId = StringHelper.get(mp, "houseId"); // 房源id
				String status = StringHelper.get(mp, "status");
				String operId = StringHelper.get(mp, "operId"); // 操作人
				String sql = getSql(
						"basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证房源状态 如果非1 房源状态改变
				if (this.queryForInt(
						getSql("basehouse.rankHosue.checkRankAgreementStatus"),
						new Object[] { id, status }) != 1) {
					return -2;
				}
				// 插入历史记录
				if (request != null) {
					this.insertTableLog(request, "yc_agreement_tab",
							" and id = '" + id + "'", "删除租赁房源",
							new ArrayList<String>(), operId);
				}
				this.update(
						getSql("basehouse.rankHosue.updateRankAgreementStatus")
								.replace("####", "  isdelete = ? "),
						new Object[] { 0, id });
				Map<String, String> params = new HashMap<>();
				int res = 0;
				params.put("rankid", house_rank_id);
				params.put("houseid", houseId);
				params.put("oper", operId);
				// 验证此合约对应的房源是否还有其他有效合约，如果有，不能更新房源状态，如果没有需要更新房源状态
				if (this.queryForInt(
						getSql("basehouse.rankHosue.checkRankValidAgreement"),
						new Object[] { house_rank_id, id }) == 0) {
					// 更新租赁房源状态 刘飞
					res = rankHouse.approveNo(params, this);
					if (res != 1) {
						this.rollBack();
						return res;
					}
				}
				if ("12".equals(status)) {
					// 雷杨撤销接口
					params = new HashMap<>();
					params.put("agreement_id", id);
					res = financial.repealRentHouse(params, this);
					log.debug(res + "");
					if (res != 1) {
						this.rollBack();
						return -12;
					}
				}
				return 1;
			}
		}));
		*/
		// =============
		String state = super.get(resultMap, "state");
		if ("1".equals(state)) {
			resultMp.put("msg", "操作成功");
		} else if ("-2".equals(state)) {
			resultMp.put("msg", "合约状态发生改变,请重新确认!");
		} else if ("-12".equals(state)) {
			resultMp.put("msg", "当前房源已下架,请勿删除!");
		} else {
			resultMp.put("msg", "网络忙,稍候重试!");
		}
		resultMp.put("state", state);
		return resultMp;
	}

	/**
	 * 收房合约公证
	 * 
	 * @return
	 */
	@RequestMapping("agreementNotarization")
	public @ResponseBody
	Object agreementNotarization(@RequestBody JSONObject mp,
			HttpServletRequest request) {
		String agreementId = super.get(mp, "id"); // 合约id
		String userId = super.get(mp, "userId"); // 用户编号
		String operId = super.get(mp, "operId"); // 操作人编号
		String houseId = super.get(mp, "houseId"); // 房源编号
		return contractServ.rankAgreementNotarization(agreementId, userId,
				operId, houseId, "1", false);
	}

	/**
	 * 出租合约公证
	 * 
	 * @return
	 */
	@RequestMapping("rankAgreementNotarization")
	public @ResponseBody
	Object rankAgreementNotarization(@RequestBody JSONObject mp,
			HttpServletRequest request) {
		String agreementId = super.get(mp, "id"); // 出租合约id
		String userId = super.get(mp, "userId"); // 用户编号
		String operId = super.get(mp, "operId"); // 操作人编号
		String houseId = super.get(mp, "houseId"); // 房源编号
		return contractServ.rankAgreementNotarization(agreementId, userId,
				operId, houseId, "1", true);
	}

	/**
	 * 保存房源信息
	 * 
	 * @param house
	 * @param request
	 * @return
	 */
	@RequestMapping("saveHouse")
	public @ResponseBody
	Object saveHouse(@RequestBody House house, HttpServletRequest request) {
		return _houseServImpl.saveHouse(house, request);
	}

	/**
	 * 加载小区列表信息
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("loadGroupList")
	public @ResponseBody
	Object loadGroupList(@RequestBody HouseFo condition) {
		List<com.yc.rm.caas.appserver.model.house.Area> list = _houseServImpl
				.loadGroupList(condition);
		log.info(JSONObject.toJSONString(list));
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

	/**
	 * 删除房源信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("delHouseInfo")
	public @ResponseBody
	Object delHouseInfo(@RequestBody JSONObject json) {
		return _houseServImpl.delHouseInfo(json);
	}

	/**
	 * 提交房源信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("submitHouse")
	public @ResponseBody
	Object submitHouse(@RequestBody JSONObject json) {
		return _houseServImpl.submitHouse(json);
	}

	/**
	 * 审批房源
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("spHouse")
	public @ResponseBody
	Object spHouse(@RequestBody JSONObject json) {
		log.info("init spHouse");
		log.debug(JSONObject.toJSONString(json));
		return _houseServImpl.spHouse(json);
	}

	/**
	 * 退租房源
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("tzHouse")
	public @ResponseBody
	Object tzHouse(@RequestBody JSONObject json) {
		return rankHouseService.tzHouse(
				net.sf.json.JSONObject.fromObject(json), orderInterfaces);
	}

	/**
	 * 合约查询条件
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("agreementCondition")
	public @ResponseBody
	Object agreementCondition(@RequestBody JSONObject json) {
		String flag = super.get(json, "flag");
		return _houseServImpl.resultCondition(flag);
	}

	/**
	 * 收房合约列表
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("agreementList")
	public @ResponseBody
	List<ContractBean> agreementList(@RequestBody HouseFo condition) {
		return contractServ.agreementList(condition);
	}

	/**
	 * 出租合约列表
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("rankAgreementList")
	public @ResponseBody
	Object rankAgreementList(@RequestBody HouseFo condition) {
		// log.info(JSONObject.toJSONString(condition));
		log.debug(JSONObject.toJSONString(condition));
		return contractServ.rankAgreementList(condition);
	}

	/**
	 * 撤销房源合约
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("cancelHouseAgreement")
	public @ResponseBody
	Object cancelHouseAgreement(@RequestBody JSONObject json) {
		// log.info("json:"+json);
		log.debug("json:" + json);
		return contractServ.cancelHouseAgreement(json);
	}

	/**
	 * 审批房源合约
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("spHouserAgeement")
	public @ResponseBody
	Object spHouserAgeement(@RequestBody JSONObject json,
			HttpServletRequest request) {
		// log.info("json:"+json);
		// final HttpServletRequest request, final Financial financial, final
		// Onstruction
		// onstruction, final JSONObject json
		return contractServ.spHouserAgeement(request, financial, onstruction,
				agreementMgeService, json);
	}

	/**
	 * 获取银行信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("getBankList")
	public @ResponseBody
	Object getBankList(@RequestBody JSONObject json) {
		// log.info(JSONArray.toJSONString(object));
		return contractServ.getBankList(json);
	}

	/**
	 * 获取银行查询条件
	 * 
	 * @return
	 */
	@RequestMapping("getBankSearchConditionList")
	public @ResponseBody
	Object getBankSearchConditionList() {
		// log.info(JSONObject.toJSONString(contractServ.getBankSearchConditionList()));
		return contractServ.getBankSearchConditionList();
	}

	/**
	 * 获取合约明细下拉框
	 * 
	 * @return
	 */
	@RequestMapping("getAgreementDetailSelect")
	public @ResponseBody
	Object getAgreementDetailSelect() {
		// log.info(JSONObject.toJSONString(contractServ.getAgreementDetailSelect()));
		return contractServ.getAgreementDetailSelect();
	}

	/**
	 * 保存收房合约
	 * 
	 * @param agreement
	 * @param request
	 * @return
	 */
	@RequestMapping("saveAgreement")
	public @ResponseBody
	Object saveAgreement(@RequestBody ContractBean agreement,
			HttpServletRequest request) {
		return contractServ.saveHouseAgreement(agreement, request);
	}

	/**
	 * 删除收房合约
	 * 
	 * @param agreement
	 * @param request
	 * @return
	 */
	@RequestMapping("delAgreement")
	public @ResponseBody
	Object delAgreement(@RequestBody JSONObject json, HttpServletRequest request) {
		return agreementMgeService.newDelAgreement(
				net.sf.json.JSONObject.fromObject(json), request);
	}

	/**
	 * 验证房源状态
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("checkHouseState")
	public @ResponseBody
	Object checkHouseState(@RequestBody JSONObject json) {
		return _houseServImpl.checkHouseState(json);
	}

	/**
	 * 待公证审批房源
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("spHouseAgreement")
	public @ResponseBody
	Object spHouseAgreement(@RequestBody JSONObject json,
			HttpServletRequest request) {
		return agreementMgeService
				.approvalHouseAgreement(request,
						net.sf.json.JSONObject.fromObject(json), financial,
						onstruction);
	}

	/**
	 * 获取首页数量
	 * 
	 * @param json
	 * @return
	 */
	/*
	@RequestMapping("loadHousePageCnt")
	public @ResponseBody
	Object loadHousePageCnt(@RequestBody JSONObject json) {
		return _houseServImpl.loadHousePageCnt(json, mktRepServ);
	}*/

	/**
	 * 获取首页数量
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("loadPayCntList")
	public @ResponseBody
	Object loadPayCntList(@RequestBody JSONObject json, HttpServletRequest request) {
		return _houseServImpl.loadPayCntList(json, request);
	}

	/**
	 * 交接房源
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("transferHouse")
	public @ResponseBody
	Object transferHouse(@RequestBody JSONObject json) {
		return _houseServImpl.transferHouse(json);
	}

	/**
	 * 验证操作管家是否在这个网格内
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("isSelfHouse")
	public @ResponseBody
	Object isSelfHouse(@RequestBody RankAgreement rankAgreement) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		// returnMap.put("state", _houseServImpl.isSelfHouse(rankAgreement));
		returnMap.put("state", 1);
		return returnMap;
	}

	/**
	 * 下载合约信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("downloadAgreement")
	public @ResponseBody
	Object downloadAgreement(JSONObject json) {
		String notaryid = (String) json.get("notaryid");
		String flag = (String) json.get("flag"); // 0 下载 其他预览
		log.debug(notaryid);
		Map<String, String> contentMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("result", "-1");
		resultMap.put("msg", "非法请求");
		if ("".equals(notaryid)) {
			return resultMap;
		}
		try {
			String aesKey = RandomStringUtils.random(16, true, true);
			contentMap.put("notaryid", notaryid);
			String encryptContent = AES_CBCUtils.encrypt(aesKey,
					com.alibaba.fastjson.JSONObject.toJSONString(contentMap)
							.getBytes("utf-8"));
			String encryptedKey = com.raising.framework.utils.rsa.RSAUtil
					.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = com.raising.framework.utils.rsa.RSAUtil.sign(
					com.raising.framework.utils.md5.MD5Utils.sign(data)
							.toLowerCase(), Apis.clientPrivateKey);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
			log.debug(Apis.downloadRankAgreement);
			log.debug(flag);
			resultMap.put("url", "0".equals(flag) ? Apis.downloadRankAgreement
					: Apis.previewRankAgreement);
			resultMap.put("apiid", Apis.APIID);
			resultMap.put("content", encryptContent);
			resultMap.put("key", encryptedKey);
			resultMap.put("signed", signed);
			log.debug("resultMap:" + resultMap);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}

}
