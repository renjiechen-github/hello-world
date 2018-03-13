package pccom.web.flow.base;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.OrderInterfaces;
import pccom.web.interfaces.RankHouse;

/**
 * 订单
 * @author 刘飞
 *
 */
@Controller
public class OrderController extends BaseController{
	@Autowired
	private OrderService orderService;
	@Autowired
	private OrderInterfaces orderInterfaces;
	@Autowired
	private RankHouse rankHouse;
	/**
	 * 订单列表页面
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "Order/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		orderService.getList(request,response);
	} 
	
	
	/**
	 * 订单列表页面
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "Order/shGetList.do")
	public void shgetList(HttpServletRequest request, HttpServletResponse response) {
		orderService.shGetList(request,response);
	}
	
	
	/**
	 * 订单新增
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "Order/createOrder.do")
	public void createOrder(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( orderService.createOrder(request,response), response);
	}
	
	/**
	 * 订单修改
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "Order/updateOrder.do")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( orderService.updateOrder(request,response), response);
	}
	/**
	 * 订单派单--发起任务
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "Order/dispatch.do")
	public void dispatch(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( orderService.dispatch(request,response), response);
	}
	/**
	 * 关闭订单
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "Order/closeOrder.do")
	public void closeOrder(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( orderService.closeOrder(request,response), response);
	}

	/**
	 * 订单状态报表
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Order/orderReport.do")
	public void orderReport(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(orderService.orderReport(request,response), response);
	} 
	
	/**
	 * 订单查询当前财务中的租赁合约ID
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Order/getFinancial.do")
	public void getFinancial(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(orderService.getFinancial(request,response), response);
	} 
	
	
	
	
	/**
	 * 订单类型报表
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Order/typeReport.do")
	public void typeReport(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(orderService.typeReport(request,response), response);
	} 
	
	/**
	 * 订单撤销支付
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Order/repealOrder.do")
	public void repealOrder(HttpServletRequest request, HttpServletResponse response) {
		  this.writeJsonData( orderService.repealOrder(request,response), response);
	} 
	
	/**
	 * 结束租赁合约
	 * @param response
	 */
	@RequestMapping("interfaces/Order/createOrder.do")
	public void createOrder(HttpServletResponse response)
	{
		this.writeText(orderService.interfacesCreateOrder(request,orderInterfaces,rankHouse), response);
	}
	
}
