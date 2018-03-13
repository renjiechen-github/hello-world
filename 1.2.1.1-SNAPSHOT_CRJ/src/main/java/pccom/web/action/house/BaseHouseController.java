package pccom.web.action.house;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.logicalcobwebs.concurrent.FJTask.Par;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.common.util.DateHelper;
import pccom.common.util.StringHelper;
import pccom.web.action.BaseController;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.house.BaseHouseService;

import com.ycdc.appserver.bus.service.house.HouseMgrService;

@Controller
public class BaseHouseController extends BaseController {

	@Autowired
	public BaseHouseService baseHouseService;
	
	@Autowired
	public Onstruction onstruction;
	
	@Autowired
	public Financial financial;
	
	@Autowired
	public HouseMgrService miHService;
	/**
	 * 查询房源列表
	 * @author 开开
	 * @param response
	 */
	@RequestMapping (value = "BaseHouse/getHouseList.do")
	public void getHouseList(HttpServletRequest request, HttpServletResponse response){
		baseHouseService.getHouseList(request, response);
	}

  @RequestMapping (value = "BaseHouse/getGroupList.do")
	public void getGroupList(HttpServletRequest request, HttpServletResponse response) {
    this.writeJsonData(baseHouseService.getGroupList(request, response), response);
  }
	
	/**
	 * 导出房源信息
	 * @param response
	 */
	@RequestMapping("BaseHouse/exportHouse.do")
	public void exportHouse(HttpServletResponse response)
	{
		baseHouseService.exportHouse(request, response);
	}
	
	/**
	 * 获取空置房间房间列表
	 * @author suntf
	 * @param response
	 */
	@RequestMapping("BaseHouse/getKZHouseList.do")
	public void getKZHouseList(HttpServletResponse response)
	{
		baseHouseService.getKZHouseList(response, request);
	}
	
	/**
	 * 获取管家列表信息
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/getMangerList.do")
	public void getMangerList(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.getManageList(), response);
	}

	/**
	 * 保存房源信息
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/saveHouseInfo.do")
	public void saveHouseInfo(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.saveHouseInfo(request), response);
	}
	
	/**
	 * 保存房源图片
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/saveHousePic.do")
	public void saveHousePic(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.saveHousePic(request), response);
	}
	
	/**
	 * 保存房源部分信息
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/savePartInfoOfHouse.do")
	public void savePartInfoOfHouse(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.savePartInfoOfHouse(request), response);
	}
	
	/**
	 * 加载房源信息
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/loadHouseInfo.do")
	public void loadHouseInfo(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.loadHouseInfo(request), response);
	}
	
	/**
	 * 删除房屋信息
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/deleteHouse.do")
	public void deleteHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.deleteHouse(request), response);
	}
	
	/**
	 * 提交房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/submitHouse.do")
	public void submitHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.submitHouse(request), response);
	}
	/**
	 * 发布房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@RequestMapping("BaseHouse/houseRelease.do")
	public void houseRelease(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.houseRelease(request), response);
	}
	
	
	
	
	
	
	/**
	 * 发布房源审批
	 * @param response
	 * @author suntf
	 * @date 2016年8月12日
	 */
	@RequestMapping("BaseHouse/approvalPublish.do")
	public void approvalPublish(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.approvalPublish(request), response);
	}
	
	
	
	/**
	 * 审批房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月12日
	 */
	@RequestMapping("BaseHouse/approvalHouse.do")
	public void approvalHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.approvalHouse(request), response);
	}
	
	
	/**
	 * 设置精品||取消精品
	 * @param response
	 * @author 刘飞
	 * @date 2016年9月1日
	 */
	@RequestMapping("BaseHouse/rankIstop.do")
	public void rankIstop(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.rankIstop(request), response);
	}
	
	
	/**
	 * 下架||重新发布
	 * @param response
	 * @author 刘飞
	 * @date 2016年9月1日
	 */
	@RequestMapping("BaseHouse/soldOut.do")
	public void soldOut(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.soldOut(request), response);
	}
	
	/**
	 * 交接房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月13日
	 */
	@RequestMapping("BaseHouse/houseTransfer.do")
	public void houseTransfer(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.houseTransfer(request, onstruction), response);
	}
	
	/**
	 * 签约房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月15日
	 */
	@RequestMapping("BaseHouse/signHouse.do")
	public void signHouse(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.signHouse(request), response);
	}
	
	/**
	 * 验证房源状态
	 * @param response
	 * @author suntf
	 * @date 2016年8月17日
	 */
	@RequestMapping("BaseHouse/checkHouseStatus.do")
	public void checkHouseStatus(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.validateHouseStatus(request), response);
	}
	
	/**
	 * 查看待发布房源
	 * @param response
	 * @author suntf
	 * @date 2016年8月22日
	 */
	@RequestMapping("BaseHouse/seeRentHouse.do")
	public void seeRentHouse(HttpServletResponse response)
	{ 
		this.writeJsonData(baseHouseService.seeRentHouse(request), response);
	}
	
	/**
	 * 获取用户姓名
	 * @param response
	 * @author suntf
	 * @date 2016年8月29日
	 */
	@RequestMapping("BaseHouse/getUserName.do")
	public void getUserName(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.getUserName(request), response);
	}
	
	/**
	 * 获取商圈
	 * @param response
	 */
	@RequestMapping("BaseHouse/getTrading.do")
	public void getTrading(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.getTrading(), response);
	}
	
	/**
	 * 查看房源发布参考价格
	 * @param response
	 */
	@RequestMapping("BaseHouse/checkHouseMenory.do")
	public void checkHouseMenory(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.checkHouseMenory(request), response);
	}
	
	/**
	 * 删除合约远程图片，同时更新数据库表中字段
	 * @param response
	 */
	@RequestMapping("BaseHouse/removeRemotFile.do")
	public void removeRemotFile(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.removeRemotFile(request), response);
	}
	
	/**
	 * 获取工程部基本信息
	 * @param response
	 */
	@RequestMapping("BaseHouse/getProjectInfo.do")
	public void getProjectInfo(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.getProjectInfo(request), response);
	}
	
	/**
	 * 获取工程清单列表
	 * @param response
	 */
	@RequestMapping("BaseHouse/getProjectList.do")
	public void getProjectList(HttpServletResponse response)
	{
		baseHouseService.getProjectList(request, response);
	}
	
	/**
	 * 保存水电煤代缴费
	 * @param response
	 */
	@RequestMapping("BaseHouse/saveSDM.do")
	public void saveSDM(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.saveSDM(request, financial), response);
	}
	
	/**
	 * 保存新签约
	 * @param response
	 */
	@RequestMapping("BaseHouse/signNewAgreement.do")
	public void signNewAgreement(HttpServletResponse response)
	{
		Map<String,String> resultMap = baseHouseService.initNewAgreement(request,miHService);
		this.writeJsonData(resultMap, response);
	}
	
	/**
	 * 价格修改
	 * @param response
	 */
	@RequestMapping("BaseHouse/updateRankPrice.do")
	public void updateRankPrice(HttpServletResponse response)
	{
		this.writeJsonData(baseHouseService.updateRankPrice(request), response);
	}
}
