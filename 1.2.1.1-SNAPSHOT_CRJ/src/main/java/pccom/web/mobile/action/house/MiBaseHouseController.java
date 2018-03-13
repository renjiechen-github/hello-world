/**
 * 
 */
package pccom.web.mobile.action.house;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.common.util.BatchSql;
import pccom.web.action.BaseController;
import pccom.web.interfaces.Coupon;
import pccom.web.interfaces.Onstruction;
import pccom.web.mobile.service.house.MiBaseHouseService;
import pccom.web.server.house.BaseHouseService;

/**
 * 房源管理
 * @author suntf
 * @date 2016年9月6日
 */
@Controller
@RequestMapping("mobile/houseMgr/")
public class MiBaseHouseController extends BaseController
{
	@Autowired
	private BaseHouseService baseHouseService;
	
	@Autowired
	private MiBaseHouseService miBaseHouseService;
	
	@Autowired
	private Onstruction onstruction;
	
	@Autowired
	private Coupon coupon;
	
	/**
	 * 获取房源列表信息
	 * @param request
	 * @author suntf
	 * @date 2016年9月6日
	 */
	@RequestMapping("getHouseList.do")
	public void getHouseList(HttpServletResponse response)
	{
		this.writeJsonData(miBaseHouseService.getHouseList(request), response);
	}
	
	/**
	 * 加载房源信息
	 * @param response
	 * @author suntf
	 * @date 2016年9月7日
	 */
	@RequestMapping("loadHouseInfo.do")
	public void loadHouseInfo(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.loadHouseInfo(request), response);
	}
	
	/**
	 * 通过号码获取用户名称
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月7日
	 */
	@RequestMapping("getUserName.do")
	public void getUserName(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.getUserName(request), response);
	}
	
	/**
	 * 加载小区信息
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月7日
	 */
	@RequestMapping("loadAearList.do")
	public void loadAearList(HttpServletResponse response)
	{
		this.writeJsonData(miBaseHouseService.loadAearList(request), response);
	}
	
	/**
	 * 保存房源信息
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月7日
	 */
	@RequestMapping("saveHouse.do")
	public void saveHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.saveHouseInfo(request), response);
	}
	
	/**
	 * 提交房源信息
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月8日
	 */
	@RequestMapping("submitHouse.do")
	public void submitHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.submitHouse(request), response);
	}
	
	/**
	 * 审批房源
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月8日
	 */
	@RequestMapping("approvalHouse.do")
	public void approvalHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.approvalHouse(request), response);
	}
	
	/**
	 * 获取管家列表信息
	 * @param response
	 * @author suntf
	 * @date 2016年9月9日
	 */
	@RequestMapping("getMangerList.do")
	public void  getMangerList(HttpServletResponse response)
	{
		this.writeJsonData(miBaseHouseService.getManageList(), response);
	}
	
	/**
	 * 房源签约
	 * @param response
	 * @author suntf
	 * @date 2016年9月9日
	 */
	@RequestMapping("signHouse.do")
	public void signHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.signHouse(request), response);
	}
	
	/**
	 * 删除房源信息
	 * @param response
	 * @author suntf
	 * @date 2016年9月11日
	 */
	@RequestMapping("deleteHouse.do")
	public void deleteHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.deleteHouse(request), response);
	}

	/**
	 * 验证房源是否已经签约
	 * @param response
	 */
	@RequestMapping("checkHouseStatus.do")
	public void checkHouseStatus(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.validateHouseStatus(request), response);
	}
	
	/**
	 * 撤销房发布
	 * @param response
	 */
	@RequestMapping("soldOut.do")
	public void soldOut(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.soldOut(request), response);
	}
	
	/**
	 * 审批发布房源
	 * @param response
	 */
	@RequestMapping("approvalPublish.do")
	public void approvalPublish(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.approvalPublish(request), response);
	}
	
	/**
	 * 发布房源
	 * @param response
	 */
	@RequestMapping("houseRelease.do")
	public void houseRelease(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.houseRelease(request), response);
	}
	
	/**
	 * 交接房源
	 * @param response
	 */
	@RequestMapping("houseTransfer.do")
	public void houseTransfer(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.houseTransfer(request, onstruction), response);
	}
	
	/**
	 * 设置精品
	 * @param response
	 */
	@RequestMapping("rankIstop.do")
	public void rankIstop(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.rankIstop(request), response);
	}
	
	/**
	 * 查看租赁房源信息
	 * @param response
	 */
	@RequestMapping("seeRankHouseList.do")
	public void seeRankHouseList(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.seeRentHouse(request), response);
	}
	
	/**
	 * 查看租赁房源信息
	 * @param response
	 */
	@RequestMapping("queryRankHouse.do")
	public void queryRankHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.queryRankHouse(request), response);
	}
	
	
	/**
	 * 参考出租房源信息
	 * @param response
	 */
	@RequestMapping("seeRentHouse.do")
	public void seeRentHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.seeRentHouse(request), response);
	}
	
	/**
	 * 查看房源发布参考价格
	 * @param response
	 */
	@RequestMapping("checkHouseMenory.do")
	public void checkHouseMenory(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.checkHouseMenory(request), response);
	}
	
	
	/**
	 * 删除图片
	 * @param response
	 */
	@RequestMapping("removeRemotFile.do")
	public void removeRemotFile(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.removeRemotFile(request), response);
	}
	
	/**
	 * 工程详细内容
	 * @param response
	 */
	@RequestMapping("getProjectInfo.do")
	public void getProjectInfo(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.getProjectInfo(request), response);
	}
	
	/**
	 * 工程详细
	 * @param response
	 */
	@RequestMapping("getProjectList.do")
	public void getProjectList(HttpServletResponse response)
	{
		writeJsonData(baseHouseService.getProjectList(request, response), response);
	}
	
	/**
	 * 处理扫描二维码
	 * @param response
	 */
	@RequestMapping("dealRQcode.do")
	public void dealRQcode(HttpServletResponse response)
	{
		Map<String,Object> param = new HashMap<>();
		String code = req.getValue(request, "code");
		String operId = req.getValue(request, "operId");
		param.put("code", code);
		param.put("oper_id", operId);
		writeJsonData(coupon.getCode(param, new BatchSql()), response);
	}
}
