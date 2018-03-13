package pccom.web.action.house.rankhouse;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.interfaces.OrderInterfaces;
import pccom.web.interfaces.RankHouse;
import pccom.web.server.agreement.AgreementMgeService;
import pccom.web.server.house.rankhouse.RankHouseService;

import com.ycdc.appserver.bus.service.house.HouseMgrService;
import com.ycdc.appserver.model.house.RankAgreement;

@Controller
public class RankHouseController extends BaseController
{
	@Autowired
	public RankHouseService rankHouseService;
	
	@Autowired
	private Financial financial;
	
	@Autowired
	private RankHouse rankHouse;
	
	@Autowired
	private OrderInterfaces orderInterfaces;
	
	@Autowired
	private HouseMgrService miHService;
	
	@Autowired
	private AgreementMgeService agreementMgeService;
	
	@Autowired
	public Onstruction onstruction;
	
	/**
	 * 查看出租房源
	 * @param response
	 * @author liuf
	 * @throws Exception 
	 * @date 2016年8月22日
	 */
	@RequestMapping("houserank/getrankhouse.do")
	public void getRankHouse(HttpServletResponse response) throws Exception
	{
		rankHouseService.getRankHouse(request,response);
	}
	
	/**
	 * 查看出租房源
	 * @param response
	 * @author liuf
	 * @throws Exception 
	 * @date 2016年8月22日
	 */
	@RequestMapping("houserank/getuphouse.do")
	public void getUphouse(HttpServletResponse response) throws Exception
	{
		this.writeJsonData(rankHouseService.getUphouse(request,response), response);
	}
	
	/**
	 * 修改发布房源
	 * @param response
	 * @author liuf
	 * @throws IOException 
	 * @date 2016年8月22日
	 */
	@RequestMapping("houserank/update.do")
	public void RankHouseUpdate(HttpServletResponse response) throws IOException
	{
		this.writeJsonData(rankHouseService.rankHouseUpdate(request), response);
	}
	
	/**
	 * 修改
	 * @param response
	 * @author liuf
	 * @throws IOException 
	 * @date 2016年8月30日
	 */
	@RequestMapping("houserank/Rankseve.do")
	public void rankSeve(HttpServletResponse response) throws IOException
	{
		this.writeJsonData(rankHouseService.rankSeve(request), response);
	}
	
	/**
	 * 查看老合约图片
	 * @param response
	 */
	@RequestMapping(value = "houserank/viewFile.do")
	public void testFile(HttpServletResponse response)
	{
		 this.writeJsonData(rankHouseService.viewFile(request), response);
	}
	
	
	
	
	/**
	 * 验证房源状态是否改变
	 * @param response
	 * @author suntf
	 * @date 2016年8月23日
	 */
	@RequestMapping("houserank/checkRankHouseStatus.do")
	public void checkRankHouseStatus(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.checkRankHouseStatus(request), response);
	}
	
	/**
	 * 保存租赁房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月23日
	 */
	@RequestMapping("rankHouse/saveNewRankAgreement.do")
	public void saveNewRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.saveRankAgreement(request), response);
	}
	
	/**
	 * 保存租赁房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月23日
	 */
	@RequestMapping("rankHouse/saveRankAgreement.do")
	public void saveRankAgreement(HttpServletResponse response)
	{
//		String jsonStr =  "{\"operId\":\"82\",\"begin_time\":\"2016-12-12\",\"water_meter\":\"4115\",\"agents\":\"2\",\"is_tj\":\"\",\"rankType\":\"\",\"watercard\":\"2536\",\"rankstatus\":\"\",\"totalRoomCnt\":\"2\",\"propertyMonery\":\"21323\",\"id\":\"\",\"username\":\"刘飞\",\"pay_type\":\"3\",\"name\":\"仙鹤茗苑次卧C\",\"user_id\":\"3565\",\"rank_area\":\"\",\"rankId\":\"3489\",\"rent_month\":\"1\",\"roomarea\":\"23\",\"certificateno\":\"254585\",\"gascard\":\"2323\",\"file_path\":\"\",\"electricity_meter\":\"2asdf\",\"cost_a\":\"\",\"areaid\":\"\",\"desc_text\":\"测试\",\"code\":\"\",\"gas_meter\":\"2323\",\"cost\":\"\",\"house_name\":\"\",\"electricity_meter_h\":\"562\",\"rentRoomCnt\":\"23\",\"price\":\"121\",\"areaName\":\"\",\"rank_code\":\"16FP025GL02040R06735\",\"agentName\":\"\",\"address\":\"23asdef\",\"email\":\"12535@qq.com\",\"end_time\":\"\",\"electriccard\":\"5659\",\"father_id\":\"\",\"payType\":\"\",\"electricity_meter_l\":\"25895\",\"house_id\":\"846\",\"serviceMonery\":\"23213\",\"mobile\":\"15371688388\"}";
//		Gson gson = new Gson();
		RankAgreement rankAgreement = rankHouseService.initRankAgreement(request);
		logger.debug("ddd:"+JSONObject.fromObject(rankAgreement));
//		logger.debug("cc:"+JSONObject.saveRankAgreement(miHService.saveRankAgreement(rankAgreement)));
		Object mp = miHService.saveRankAgreement(rankAgreement, request);
		logger.debug("mp:"+mp);
		this.writeJsonData(mp, response);
	}
	
	/**
	 * 租房合约列表
	 * @param response
	 * @author suntf
	 * @date 2016年8月23日
	 */
	@RequestMapping("rankHouse/rankAgreementList.do")
	public void rankAgreementList(HttpServletResponse response)
	{
		rankHouseService.rankAgreementList(request, response);
	}
	
	/**
	 * 加载租赁合约信息
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/loadAgreementInfo.do")
	public void loadAgreementInfo(HttpServletResponse response)
	{
		JSONObject json = new JSONObject();
		json.put("id", req.getValue(request, "id"));
		json.put("flag", "0");
		RankAgreement rankAgreement = new RankAgreement();
		List<RankAgreement> rankAgreements = miHService.getRankAgreementInfo(json);
		if(rankAgreements != null)
		{
			rankAgreement = rankAgreements.get(0);
		}
		logger.debug(req.getValue(request, "id"));
		logger.debug(JSONObject.fromObject(rankAgreement).toString());
		this.writeText(JSONObject.fromObject(rankAgreement), response);
	}
	/**
	 * 删除租赁合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/delRankAgreement.do")
	public void delRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.initDelRankHouse(request, rankHouse, financial), response);
	}
	
	/**
	 * 删除已公证待付款出租合约
	 * @param request
	 */
	@RequestMapping("rankHouse/delRankAgreementByStatus.do")
	public void delRankAgreementByStatus(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.initDelRankHouse(request, rankHouse, financial), response);
	}
	
	/**
	 * 审批需撤销合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/spRankAgeement.do")
	public void spRankAgeement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.spRankAgeement(request, financial, rankHouse), response);
	}
	
	/**
	 * 审批合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/approvalAreement.do")
	public void approvalAreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.approvalAreement(request, financial, rankHouse), response);
	}
	
	/**
	 * 提交合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/submitRankAgreement.do")
	public void submitRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.submitRankAgreement(request), response);
	}
	
	/**
	 * 结束合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/overRankAgeement.do")
	public void overRankAgeement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.overRankAgeement(request), response);
	}
	
	/**
	 * 
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/spOverRankAgeement.do")
	public void spOverRankAgeement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.spOverRankAgeement(request), response);
	}
	 
	/**
	 * 撤销合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@RequestMapping("rankHouse/cancelRankAgreement.do")
	public void cancelRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.cancelRankAgreement(request), response);
	}
	
	/**
	 * 结束租赁合约
	 * @param response
	 */
	@RequestMapping("rankHouse/overRankAgreement.do")
	public void overRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.overRankAgreement(request, rankHouse), response);
	}
	
	/**
	 * 结束租赁合约
	 * @param response
	 */
	@RequestMapping("interfaces/rankHouse/approveNo.do")
	public void approveNo(HttpServletResponse response)
	{
		this.writeText(rankHouseService.approveNo(request, rankHouse), response);
	}
	
	/**
	 * 退租
	 * @param response
	 */
	@RequestMapping("houserank/tzInfo.do")
	public void tzInfo(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.tzInfo(request, orderInterfaces), response);
	}
	
	
	/**
	 * 回调信息
	 * @param response
	 */
	@RequestMapping("/ca/dealRankAgreementInfo.do")
	public void dealFinance(HttpServletResponse response)
	{
		this.writeText(rankHouseService.dealFinance(request, financial, rankHouse), response);
	}
	
	@RequestMapping("/ca/dealCRankAgreementInfo.do")
	public void dealCFinance(HttpServletResponse response)
	{
		this.writeText(rankHouseService.dealCFinance(request, financial, rankHouse), response);
	}
	
	/**
	 * 回调信息
	 * @param response
	 */
	@RequestMapping("/ca/dealAgreementInfo.do")
	public void dealFinanceBak(HttpServletResponse response)
	{
		this.writeText(rankHouseService.dealFinance(request, financial, rankHouse), response);
	}
	
	/**
	 * 出租合约ca公证
	 * @param response
	 */
	@RequestMapping("rankHouse/rankAgreementNotarization.do")
	public void rankAgreementNotarization(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.rankAgreementNotarization(miHService, request), response);
	}
	
	
	/**
	 * 回调处理合约信息
	 * @param response
	 */
	@RequestMapping("/ca/caCallBackAgreement.do")
	public void caCallBackAgreement(HttpServletResponse response)
	{
		this.writeText(agreementMgeService.caCallBackAgreement(request, financial, onstruction), response);
	}
	
	/**
	 * ca回调参数处理
	 * @param request
	 */
	@RequestMapping("rankHouse/caCallBackUrl.do")
	public void caCallBackUrl(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.caCallBackUrl(request,rankHouse, financial), response);
	}
	
	/**
	 * ca回调参数处理 房源合约信息
	 * @param request
	 */
	@RequestMapping("rankHouse/caDealAgreement.do")
	public void caCallBackHouseAgreement(HttpServletResponse response)
	{
		this.writeJsonData(rankHouseService.caCallBackDealAgreement(request,rankHouse, financial, agreementMgeService), response);
	}
	
	public void autoDelRankAgreement()
	{
		rankHouseService.autoDelRankAgreement(null, rankHouse, financial);
	}
	
	/**
	 * 加载价格和几率
	 * @param response
	 */
	@RequestMapping("rankHouse/loadPriceAndJL.do")
	public void loadPriceAndJL(HttpServletResponse response)
	{
		String monery = req.getValue(request, "monery"); // 房源价格
		this.writeJsonData(miHService.getLvInfo(monery), response);
	}
}
