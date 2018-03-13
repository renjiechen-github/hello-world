package pccom.web.interfaces;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;

/**
 * 支付对外接口
 * @author 刘飞
 *
 */
@Controller
public class CommonPay extends BaseController 
{
	@Autowired
	public CommonPayService commonPayService;
	@Autowired
	private RankHouse rankHouse;
	
	/**
	 * 结束租赁合约
	 * @param response
	 */
	
	@RequestMapping("interfaces/common/pay.do")
	public void pay(HttpServletResponse response)
	{
		this.writeText(commonPayService.commonPay(request,rankHouse), response);
	}
}
