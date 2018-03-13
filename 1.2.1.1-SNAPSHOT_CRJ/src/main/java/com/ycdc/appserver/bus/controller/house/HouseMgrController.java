package com.ycdc.appserver.bus.controller.house;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.rm.caas.appserver.bus.service.team.ITeamServ;
import com.ycdc.appserver.bus.service.house.HouseMgrService;
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
import com.ycdc.appserver.model.user.User;
import com.ycdc.nbms.report.serv.IMktRepServ;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pccom.common.util.StringHelper;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.interfaces.OrderInterfaces;
import pccom.web.server.agreement.AgreementMgeService;
import pccom.web.server.house.rankhouse.RankHouseService;

/**
 * @author suntf
 * @date 2016年11月28日
 */
@Controller
@RequestMapping("appserver/miHouseMgr/")
public class HouseMgrController {
    //public static final Logger logger = org.slf4j.LoggerFactory.getLogger(HouseMgrController.class);

    @Autowired
    private HouseMgrService miHService;
    @Autowired
    public RankHouseService rankHouseService;
    @Autowired
    public pccom.web.interfaces.RankHouse rankHouse;
    @Autowired
    private OrderInterfaces orderInterfaces;
    @Autowired
    private AgreementMgeService agreementMgeService;
    @Resource
    private IMktRepServ mktRepServ;
    @Autowired
    private Financial financial;
    @Autowired
    private Onstruction onstruction;
    @Autowired
	private ITeamServ _teamServImpl;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 获取房源列表信息
     *
     * @param condition
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "getHouseList.do", method = RequestMethod.POST)
    public @ResponseBody
    List<House> getHouseList(@RequestBody HouseCondition condition,
            HttpServletRequest request, HttpServletResponse response) {
        logger.debug("init getHouseList");
        logger.debug("condition:" + condition);
        // logger.debug(JSONArray.fromObject( miHService.getHouseList(condition)));
        return miHService.getHouseList(condition);
    }

    /**
     * 获取条件查询集合
     *
     * @return
     */
    @RequestMapping(value = "getConditionList.do", method = RequestMethod.POST)
    public @ResponseBody
    ResultCondition getConditionList() {
        // logger.debug(JSONObject.fromObject(miHService.getResultCondition()));
        return miHService.getResultCondition();
    }

    /**
     * 获取商圈编号
     *
     * @return
     */
    @RequestMapping(value = "getAreaList.do", method = RequestMethod.POST)
    public @ResponseBody
    List<Area> getAreaList(@RequestBody Area area) {
        // logger.debug(JSONArray.fromObject(miHService.getAreaList(area.getId(),
        // area.getArea_type())));
        return miHService.getAreaList(area.getId(), area.getArea_type());
    }

    /**
     * 获取房源下拉框信息
     *
     * @return
     */
    @RequestMapping(value = "getHouseSelectInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    HouseSelectInfo getHouseSelectInfo() {
        logger.debug(JSONObject.fromObject(miHService.getHouseSelectInfo()).toString());
        return miHService.getHouseSelectInfo();
    }

    /**
     * 获取房源信息
     *
     * @param house
     * @return
     */
    @RequestMapping(value = "getHouseInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    House getHouseInfo(@RequestBody House house) {
//        logger.debug(JSONObject.fromObject(miHService.getHouseInfo(house.getId())));
        return miHService.getHouseInfo(house.getId());
    }

    /**
     * 获取工程信息
     *
     * @param agreement
     */
    @RequestMapping(value = "getProjectInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    Project getProjectInfo(@RequestBody Agreement agreement) {
//        logger.debug(JSONObject.fromObject(miHService.getProjectInfo(agreement.getId())));
        return miHService.getProjectInfo(agreement.getId());
    }

    /**
     * 合约明细
     *
     * @param agreement
     * @return
     */
    @RequestMapping(value = "getAgreementInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    Agreement getAgreementInfo(@RequestBody Agreement agreement) {
        logger.debug(JSONObject.fromObject(miHService.getAgreementInfo(agreement.getId(),
                agreement.getHouse_id())).toString());
        // logger.debug(JSONObject.fromObject(miHService.getAgreementInfo(agreement.getId(),agreement.getHouse_id())));
        return miHService.getAgreementInfo(agreement.getId(), agreement.getHouse_id());
    }

    /**
     * 初始化合约下拉框
     *
     * @param agreement
     * @return
     */
    @RequestMapping(value = "initAgreementSelect.do", method = RequestMethod.POST)
    public @ResponseBody
    AgreementSelect initAgreementSelect(@RequestBody Agreement agreement) {
        // logger.debug("cc:"+JSONObject.fromObject(miHService.initAgreementSelect()));
        return miHService.initAgreementSelect();
    }

    /**
     * 加载出租房源列表
     *
     * @param agreement
     * @return
     */
    @RequestMapping(value = "loadRankHouseList.do", method = RequestMethod.POST)
    public @ResponseBody
    List<RankHouse> loadRankHouseList(@RequestBody House house) {
//        logger.debug(JSONArray.fromObject(miHService.loadRankHouseList(house.getId())));
        return miHService.loadRankHouseList(house.getId());
    }

    /**
     * 初始化合约下拉框
     *
     * @param agreement
     * @return
     */
    @RequestMapping(value = "loadRankHouseInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    RankHouse loadRankHouseInfo(@RequestBody RankHouse rankHouse) {
        // logger.debug(JSONObject.fromObject(miHService.loadRankHouseInfo(rankHouse.getId())));
        return miHService.loadRankHouseInfo(rankHouse.getId());
    }

    /**
     * 出租房源列表信息
     *
     * @param rankHouse
     * @return
     */
    @RequestMapping(value = "loadRankAgreementList.do", method = RequestMethod.POST)
    public @ResponseBody
    List<RankAgreement> loadRankAgreementList(@RequestBody RankHouse rankHouse) {
        // logger.debug(JSONObject.fromObject(rankHouse));
        // logger.debug(JSONArray.fromObject(miHService.loadRankAgreementList(rankHouse.getId())));
        return miHService.loadRankAgreementList(rankHouse.getId());
    }

    /**
     * 加载出租合约列表下拉框
     *
     * @return
     */
    @RequestMapping(value = "loadRankAgreementSelect.do", method = RequestMethod.POST)
    public @ResponseBody
    RankAgreementSelect loadRankAgreementSelect(@RequestBody JSONObject json) {
        // logger.debug(JSONObject.fromObject(miHService.loadRankAgreementSelect()));
        return miHService.loadRankAgreementSelect(json);
    }

    /**
     * 加载出租合约明细
     *
     * @return
     */
    @RequestMapping(value = "getRankAgreementInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    List<RankAgreement> getRankAgreementInfo(@RequestBody JSONObject json) {
        // logger.debug("cc:"+JSONObject.fromObject(miHService.getRankAgreementInfo(rankAgreement.getId())));
        return miHService.getRankAgreementInfo(json);
    }

    /**
     * 通过号码加载用户信息
     *
     * @return
     */
    @RequestMapping(value = "getUserInfoByMobile.do", method = RequestMethod.POST)
    public @ResponseBody
    User getUserInfoByMobile(@RequestBody User user) {
        // logger.debug("cc:"+JSONObject.fromObject(miHService.getUserInfoByMobile(user.getMobile())));
        return miHService.getUserInfoByMobile(user.getMobile());
    }

    /**
     * 保存合约信息
     *
     * @return
     */
    @RequestMapping(value = "saveRankAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object saveRankAgreement(@RequestBody RankAgreement rankAgreement,
            HttpServletRequest request) {
        Object mp = miHService.saveRankAgreement(rankAgreement, request);
        return mp;
    }

    /**
     * 保存经纪人合约
     *
     * @param rankAgreement
     * @param request
     * @return
     */
    @RequestMapping(value = "saveCRankAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object saveCRankAgreement(@RequestBody RankAgreement rankAgreement,
            HttpServletRequest request) {
        Object mp = miHService.saveCRankAgreement(rankAgreement, request);
        return mp;
    }

    /**
     * 处理合约信息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "dealFinance.do", method = RequestMethod.POST)
    public @ResponseBody
    Object dealFinance(HttpServletRequest request) {
        return miHService.dealFinance(request);
    }

    /**
     * 签约前,验证出租房源状态是否为待签约
     *
     * @param rankAgreement
     * @return
     */
    @RequestMapping(value = "checkRankHouseStatus.do", method = RequestMethod.POST)
    public @ResponseBody
    Object checkRankHouseStatus(@RequestBody RankAgreement rankAgreement) {
        // logger.debug("rankId:"+rankAgreement.getId());
        return miHService.checkRankHouseStatus(rankAgreement.getId());
    }

    /**
     * 删除租赁合约信息
     *
     * @param mp
     * @param request
     * @return
     */
    @RequestMapping(value = "delRankAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object delRankAgreement(@RequestBody JSONObject mp, HttpServletRequest request) {
        return this.initRankAgreement(request, mp, rankHouse, financial);
    }

    /**
     * 删除租赁合约信息 状态为租赁公证
     *
     * @param mp
     * @param request
     * @return
     */
    @RequestMapping(value = "delRankAgreementByStatus.do", method = RequestMethod.POST)
    public @ResponseBody
    Object delRankAgreementByStatus(@RequestBody JSONObject mp, HttpServletRequest request) {
        return this.initRankAgreement(request, mp, rankHouse, financial);
    }

    public Map initRankAgreement(HttpServletRequest request, JSONObject mp,
            pccom.web.interfaces.RankHouse rankHouse, Financial financial) {
        Map<String, String> resultMp = new HashMap<String, String>();
        Map resultMap = rankHouseService.delRankAgreement(request, rankHouse, mp,
                financial);
        String state = StringHelper.get(resultMap, "state");
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
     * 出租合约公证
     *
     * @return
     */
    @RequestMapping(value = "rankAgreementNotarization.do", method = RequestMethod.POST)
    public @ResponseBody
    Object rankAgreementNotarization(@RequestBody JSONObject mp,
            HttpServletRequest request) {
        String agreementId = StringHelper.get(mp, "id"); // 出租合约id
        String userId = StringHelper.get(mp, "userId"); // 用户编号
        String operId = StringHelper.get(mp, "operId"); // 操作人编号
        String houseId = StringHelper.get(mp, "houseId"); // 房源编号
        return miHService.rankAgreementNotarization(agreementId, userId, operId, houseId,
                "1");
    }

    /**
     * 收房合约公证
     *
     * @return
     */
    @RequestMapping(value = "agreementNotarization.do", method = RequestMethod.POST)
    public @ResponseBody
    Object agreementNotarization(@RequestBody JSONObject mp, HttpServletRequest request) {
        String agreementId = StringHelper.get(mp, "id"); // 合约id
        String userId = StringHelper.get(mp, "userId"); // 用户编号
        String operId = StringHelper.get(mp, "operId"); // 操作人编号
        String houseId = StringHelper.get(mp, "houseId"); // 房源编号
        return miHService
                .agreementNotarization(agreementId, userId, operId, houseId, "1");
    }

    /**
     * 保存房源信息
     *
     * @param house
     * @param request
     * @return
     */
    @RequestMapping(value = "saveHouse.do", method = RequestMethod.POST)
    public @ResponseBody
    Object saveHouse(@RequestBody House house, HttpServletRequest request) {
        return miHService.saveHouse(house, request);
    }

    /**
     * 加载小区列表信息
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "loadGroupList.do", method = RequestMethod.POST)
    public @ResponseBody
    Object loadGroupList(@RequestBody HouseCondition condition) {
        List<com.ycdc.appserver.model.house.Area> list = miHService
                .loadGroupList(condition);
        logger.debug(JSONArray.fromObject(list).toString());
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
    @RequestMapping(value = "delHouseInfo.do", method = RequestMethod.POST)
    public @ResponseBody
    Object delHouseInfo(@RequestBody JSONObject json) {
        return miHService.delHouseInfo(json);
    }

    /**
     * 提交房源信息
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "submitHouse.do", method = RequestMethod.POST)
    public @ResponseBody
    Object submitHouse(@RequestBody JSONObject json) {
        return miHService.submitHouse(json);
    }

    /**
     * 审批房源
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "spHouse.do", method = RequestMethod.POST)
    public @ResponseBody
    Object spHouse(@RequestBody JSONObject json) {
        return miHService.spHouse(json);
    }

    /**
     * 退租房源
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "tzHouse.do", method = RequestMethod.POST)
    public @ResponseBody
    Object tzHouse(@RequestBody JSONObject json) {
        return rankHouseService.tzHouse(json, orderInterfaces);
    }

    /**
     * 合约查询条件
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "agreementCondition.do", method = RequestMethod.POST)
    public @ResponseBody
    ResultCondition agreementCondition(@RequestBody JSONObject json) {
        String flag = StringHelper.get(json, "flag");
        // logger.debug(JSONObject.fromObject(miHService.agreementCondition(flag)));
        return miHService.agreementCondition(flag);
    }

    /**
     * 收房合约列表
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "agreementList.do", method = RequestMethod.POST)
    public @ResponseBody
    List<Agreement> agreementList(@RequestBody HouseCondition condition) {
        return miHService.agreementList(condition);
    }

    /**
     * 出租合约列表
     *
     * @param condition
     * @return
     */
    @RequestMapping(value = "rankAgreementList.do", method = RequestMethod.POST)
    public @ResponseBody
    List<RankAgreement> rankAgreementList(@RequestBody HouseCondition condition, HttpServletRequest request) {
        // logger.debug(JSONObject.fromObject(condition));
        logger.debug(JSONObject.fromObject(condition).toString());
        return miHService.rankAgreementList(condition, request);
    }

    /**
     * 撤销房源合约
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "cancelHouseAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object cancelHouseAgreement(@RequestBody JSONObject json) {
        // logger.debug("json:"+json);
        logger.debug("json:" + json);
        return miHService.cancelHouseAgreement(json);
    }

    /**
     * 审批房源合约
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "spHouserAgeement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object spHouserAgeement(@RequestBody JSONObject json, HttpServletRequest request) {
        // logger.debug("json:"+json);
        // final HttpServletRequest request, final Financial financial, final Onstruction
        // onstruction, final JSONObject json
        return miHService.spHouserAgeement(request, financial, onstruction,
                agreementMgeService, json);
    }

    /**
     * 获取银行信息
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "getBankList.do", method = RequestMethod.POST)
    public @ResponseBody
    Object getBankList(@RequestBody JSONObject json) {
        // logger.debug(JSONArray.fromObject(object));
        return miHService.getBankList(json);
    }

    /**
     * 获取银行查询条件
     *
     * @return
     */
    @RequestMapping(value = "getBankSearchConditionList.do", method = RequestMethod.POST)
    public @ResponseBody
    Object getBankSearchConditionList() {
        // logger.debug(JSONObject.fromObject(miHService.getBankSearchConditionList()));
        return miHService.getBankSearchConditionList();
    }

    /**
     * 获取合约明细下拉框
     *
     * @return
     */
    @RequestMapping(value = "getAgreementDetailSelect.do", method = RequestMethod.POST)
    public @ResponseBody
    Object getAgreementDetailSelect() {
        // logger.debug(JSONObject.fromObject(miHService.getAgreementDetailSelect()));
        return miHService.getAgreementDetailSelect();
    }

    /**
     * 保存收房合约
     *
     * @param agreement
     * @param request
     * @return
     */
    @RequestMapping(value = "saveAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object saveAgreement(@RequestBody Agreement agreement, HttpServletRequest request) {
        return miHService.saveHouseAgreement(agreement, request);
    }

    /**
     * 删除收房合约
     *
     * @param agreement
     * @param request
     * @return
     */
    @RequestMapping(value = "delAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object delAgreement(@RequestBody JSONObject json, HttpServletRequest request) {
        return agreementMgeService.newDelAgreement(json, request);
    }

    /**
     * 验证房源状态
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "checkHouseState.do", method = RequestMethod.POST)
    public @ResponseBody
    Object checkHouseState(@RequestBody JSONObject json) {
        return miHService.checkHouseState(json);
    }

    /**
     * 待公证审批房源
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "spHouseAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object spHouseAgreement(@RequestBody JSONObject json, HttpServletRequest request) {
        return agreementMgeService.approvalHouseAgreement(request, json, financial,
                onstruction);
    }

    /**
     * 获取首页数量
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "loadHousePageCnt.do", method = RequestMethod.POST)
    public @ResponseBody
    Object loadHousePageCnt(@RequestBody JSONObject json, HttpServletRequest request) {
    	// 获取登录人id
		String userId = request.getHeader("userid");
    	Map<String, Object> results = miHService.loadHousePageCnt(json, mktRepServ, request);
    	List<Map<String, Object>> teamIds = _teamServImpl.getTeamIdsByCharge(Integer.parseInt(userId));
    	if (teamIds != null && teamIds.size() > 0) {
    		results.put("teamIds", teamIds);
    	}
        return results;
    }
    
    /**
     * 获取首页数量
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "loadPayCntList.do", method = RequestMethod.POST)
    public @ResponseBody
    Object loadPayCntList(@RequestBody JSONObject json, HttpServletRequest request) {
        return miHService.loadPayCntList(json,request);
    }

    /**
     * 交接房源
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "transferHouse.do", method = RequestMethod.POST)
    public @ResponseBody
    Object transferHouse(@RequestBody JSONObject json) {
        return miHService.transferHouse(json);
    }

    /**
     * 验证操作管家是否在这个网格内
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "isSelfHouse.do", method = RequestMethod.POST)
    public @ResponseBody
    Object isSelfHouse(@RequestBody RankAgreement rankAgreement) {
        /**
         * 2017-03-24 解除验证操作管家是否在这个网格内限制
         */
        //return rankHouse.getReturnMap(miHService.isSelfHouse(rankAgreement));
        return rankHouse.getReturnMap(1);
    }

    /**
     * 下载合约信息
     *
     * @param json
     * @return
     */
    @RequestMapping(value = "downloadAgreement.do", method = RequestMethod.POST)
    public @ResponseBody
    Object downloadAgreement(@RequestBody JSONObject json) {
        return agreementMgeService.downloadAgreement(json);
    }
}
