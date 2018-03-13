/**
 * 
 */
package pccom.web.mobile.action.rankagreement;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.OrderInterfaces;
import pccom.web.interfaces.RankHouse;
import pccom.web.mobile.service.rankagreement.MiRankAgreementService;
import pccom.web.server.house.rankhouse.RankHouseService;

/**
 * 租赁合约
 * @author suntf
 * @date 2016年9月10日
 */
@Controller
@RequestMapping("mobile/rankAgreement/")
public class MiRankAgreementController extends BaseController
{
	@Autowired
	private MiRankAgreementService miRankAgreementService;
	
	@Autowired
	private RankHouseService RankHouseService;
	
	@Autowired
	private Financial financial;
	
	@Autowired
	private RankHouse rankHouse;
	
	@Autowired
	private OrderInterfaces orderInterfaces;
	
	/**
	 * 租赁合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("getRankAgreementList.do")
	public void getRankAgreementList(HttpServletResponse response)
	{
		this.writeJsonData(miRankAgreementService.getRankAgreementList(request), response);
	}
	
	/**
	 * 提交合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("approvalAgreement.do")
	public void approvalAgreement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.approvalAreement(request, financial, rankHouse), response);
	}
	
	/**
	 * 将合约置为无效
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("spRankAgeement.do")
	public void spRankAgeement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.spRankAgeement(request, financial, rankHouse), response);
	}
	
	/**
	 * 提交合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("submitRankAgreement.do")
	public void submitAgreement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.submitRankAgreement(request), response);
	}
	
	/**
	 * 结束合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("spOverRankAgeement.do")
	public void spOverRankAgeement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.spOverRankAgeement(request), response);
	}
	
	/**
	 * 取消合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("cancelRankAgreement.do")
	public void cancelRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.cancelRankAgreement(request), response);
	}
	
	
	/**
	 * 获取房源地址
	 * @param response
	 * @author liuf
	 * @date 2017年1月05日
	 */
	@RequestMapping("getAddress.do")
	public void getAddress(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.getAddress(request), response);
	}
	
	/**
	 * 删除合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("delRankAgreement.do")
	public void delRankAgreement(HttpServletResponse response)
	{
		String id = req.getValue(request, "id"); // 租赁id
		String house_rank_id = req.getValue(request, "house_rank_id"); // 租赁房源
		String houseId = req.getValue(request, "houseId"); // 房源id
		String status = req.getValue(request, "status");
		JSONObject mp = new JSONObject();
		mp.put("id", id);
		mp.put("house_rank_id", house_rank_id);
		mp.put("houseId", houseId);
		mp.put("status", status);
		mp.put("operId", RankHouseService.getUser(request).getId());
		this.writeJsonData(RankHouseService.delRankAgreement(request, rankHouse, mp, financial), response);
	}
	
	/**
	 * 出租合约信息
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("loadRankAgreementInfo.do")
	public void loadRankAgreementInfo(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.loadAgreementInfo(request), response);
	}
	
	/**
	 * 保存合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("saveRankAgreement.do")
	public void saveRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.saveRankAgreement(request), response);
	}
	

	/**
	 * 更新房源
	 * @param response
	 */
	@RequestMapping("updateRankHouse.do")
	public void updateRankHouse(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.updateRankHouse(request), response);
	}
	
	/**
	 * 验证房源是否已经被签约
	 * @param response
	 */
	@RequestMapping("checkRankHouseStatus.do")
	public void checkRankHouseStatus(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.checkRankHouseStatus(request), response);
	}
	
	
	/**
	 * 单个房间和该房间的合约
	 * @param response
	 */
	@RequestMapping("getuphouse.do")
	public void getuphouse(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.getUphouse(request, response), response);
	}
	
	/**
	 * 合同到期，结束合约
	 * @param response
	 */
	@RequestMapping("overRankAgreement.do")
	public void overRankAgreement(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.overRankAgreement(request, rankHouse), response);
	}
	
	/**
	 * 退租信息
	 * @param response
	 */
	@RequestMapping("tzInfo.do")
	public void tzInfo(HttpServletResponse response)
	{
		this.writeJsonData(RankHouseService.tzInfo(request,orderInterfaces), response);
	}
}
