package pccom.web.mobile.flow.base;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pccom.web.action.BaseController;
/**
 * 订单
 * @author 刘飞
 *
 */
@Controller
@RequestMapping("mobile/")
public class MobileLeaveController extends BaseController{

	@Autowired
	private MobileLeaveService miLeaveService;
	
	/**
	 * 列表页面
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "miLeaveService/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(miLeaveService.getList(request, response), response);
	} 
	
	/**
	 * 录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "miLeaveService/getInfo.do")
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(miLeaveService.getInfo(request,response), response);
	} 
	
	/**
	 * 新增
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "miLeaveService/create.do")
	public void createOrder(HttpServletRequest request, HttpServletResponse response) {
	    this.writeJsonData( miLeaveService.createOrder(request,response), response);
	}
	
	/**
	 * 修改
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "miLeaveService/update.do")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( miLeaveService.update(request,response), response);
	}
	
	/**
	 * 删除
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "miLeaveService/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		  this.writeJsonData( miLeaveService.delete(request,response), response);
	} 
	
}
