/**
 * 
 */
package pccom.web.action.house.agreement;

import com.ycdc.appserver.bus.service.house.HouseMgrService;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pccom.web.action.BaseController;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.agreement.AgreementMgeService;

/**
 * 合约管理
 * @author suntf 
 * @date 2016年8月18日
 */
@Controller
@RequestMapping("agreementMge/")
public class AgreementMgeController extends BaseController
{
	@Autowired
	private AgreementMgeService agreementMgeService;
	
	@Autowired
	public Financial financial;
	
	@Autowired
	public Onstruction onstruction;
	
	@Autowired
	public HouseMgrService miHService;
	
	/**
	 * 合约管理
	 * @param response
	 * @author suntf
	 * @date 2016年8月18日
	 */
	@RequestMapping("agreementList.do")
	public void agreementMgeList(HttpServletResponse response)
	{
		agreementMgeService.agreementMgeList(request, response);
	}
	
	/**
	 * 合约明细
	 * @param response
	 * @author suntf
	 * @date 2016年8月18日
	 */
	@RequestMapping("agreementInfo.do")
	public void agreementInfo(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.agreementInfo(request), response);
	}
	
	/**
	 * 合约明细
	 * @param response
	 * @author suntf
	 * @date 2016年8月18日
	 */
	@RequestMapping("newAgreementInfo.do")
	public void newAgreementInfo(HttpServletResponse response)
	{
		logger.debug(JSONObject.fromObject(agreementMgeService.newAgreementInfo(request, miHService)).toString());
		this.writeJsonData(agreementMgeService.newAgreementInfo(request, miHService), response);
	}
	
	/**
	 * 获取银行列表信息
	 * @param response
	 * @author suntf
	 * @date 2016年9月1日
	 */
	@RequestMapping("bankList.do")
	public void bankList(HttpServletResponse response)
	{
		String bankId = req.getValue(request, "bankId"); // 银行大类
		if("-1".equals(bankId))
		{
			this.writeJsonData(agreementMgeService.getBankList(request), response);
		}
		else
		{
			agreementMgeService.getBankList(request, response);
		}
	}
	
	/**
	 * 删除合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@RequestMapping("delAgreement.do")
	public void delAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.delAgreement(request), response);
	}
	
	/**
	 * 提交合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@RequestMapping("submitAgreement.do")
	public void submitAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.submitAgreement(request), response);
	}
	
	/**
	 * 审批合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@RequestMapping("approvalAgreement.do")
	public void approvalAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.approvalAgreement(request, financial, onstruction), response);
	}
	
	/**
	 * 撤销合约
	 * @param response
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@RequestMapping("cancelAgreement.do")
	public void cancelAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.cancelAgreement(request, onstruction), response);
	}
	
	/**
	 * 房源合约审批
	 * @param response
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@RequestMapping("spAgeement.do")
	public void spAgeement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.spAgeement(request, financial, onstruction), response);
	}
	
	/**
	 * 合约到期结束合约
	 * @param response
	 */
	@RequestMapping("overHouseAgreement.do")
	public void overHouseAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.overHouseAgreement(request), response);
	}
	
	/**
	 * 加载合约列表信息
	 * @param response
	 */
	@RequestMapping("loadAgreementList.do")
	public void loadAgreementList(HttpServletResponse response)
	{
		agreementMgeService.loadAgreementList(response, request);
	}
	
	/**
	 * chenrj
	 * 修改出租合约信息
	 * @param response
	 */
	@RequestMapping("UpdateHireAgreement.do")
	public void UpdateHireAgreement(HttpServletResponse response)
	{
		Object result = agreementMgeService.UpdateHireAgreement(request);
		this.writeJsonData(result, response);
	}
	
	/**
	 * chenrj
	 * 修改委托合约信息
	 * @param response
	 */
	@RequestMapping("UpdateEntrustAgreement.do")
	public void UpdateEntrustAgreement(HttpServletResponse response)
	{
		Object result = agreementMgeService.UpdateEntrustAgreement(request);
		this.writeJsonData(result, response);
	} 
	
	/**
	 * 是否展示修改按钮
	 * @param response 
	 */
	@RequestMapping("isShowAgreement.do")
	public void isShow(HttpServletResponse response) {
		Object result = agreementMgeService.isShow(request);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 文件下载
	 * @param request
	 */
	@RequestMapping("downloadAgreement.do")
	public void downloadAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.downloadAgreement(request), response);
	}
}
