/**
 * 
 */
package pccom.web.mobile.action.houseagreement;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.mobile.service.houseagreement.MiHouseAgreementService;
import pccom.web.server.agreement.AgreementMgeService;

/**
 * 收房合约管理
 * @author suntf
 * @date 2016年9月10日
 */
@Controller
@RequestMapping("mobile/houseAgreement/")
public class MiHouseAgreementController extends BaseController
{
	@Autowired
	private MiHouseAgreementService miHouseAgreementService;
	
	@Autowired
	private AgreementMgeService agreementMgeService;
	
	@Autowired
	private Financial financial;
	
	@Autowired
	private Onstruction onstruction;
	
	/**
	 * 获取收房合约列表
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("getHouseAgreementList.do")
	public void getHouseAgreementList(HttpServletResponse response)
	{
		this.writeJsonData(miHouseAgreementService.getHouseAgreementList(request), response);
	}
	
	/**
	 * 收房合约明细
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("houseAgreementInfo.do")
	public void houseAgreementInfo(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.agreementInfo(request), response);
	}
	
	/**
	 * 审批合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("approvalAgreement.do")
	public void approvalAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.approvalAgreement(request, financial, onstruction), response);
	}
	
	/**
	 * 取消合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("cancelAgreement.do")
	public void cancelAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.cancelAgreement(request, onstruction), response);
	}
	
	/**
	 * 提交合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("submitAgreement.do")
	public void submitAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.submitAgreement(request), response);
	}
	
	/**
	 * 获取银行列表
	 * @param response
	 * @author suntf
	 * @date 2016年9月10日
	 */
	@RequestMapping("getBankList.do")
	public void getBankList(HttpServletResponse response)
	{
		this.writeJsonData(miHouseAgreementService.getBankList(request), response);
	}
	
	/**
	 * 删除合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月11日
	 */
	@RequestMapping("delAgreement.do")
	public void delAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.delAgreement(request), response);
	}
	
	/**
	 * 审批合约
	 * @param response
	 * @author suntf
	 * @date 2016年9月11日
	 */
	@RequestMapping("spAgeement.do")
	public void spAgeement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.spAgeement(request, financial, onstruction), response);
	}
	
	/**
	 * 合约到期结束合同
	 * @param response
	 */
	@RequestMapping("overHouseAgreement.do")
	public void overHouseAgreement(HttpServletResponse response)
	{
		this.writeJsonData(agreementMgeService.overHouseAgreement(request), response);
	}
	
}
