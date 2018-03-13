package pccom.web.flow.base;
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
public class CheckHouseController extends BaseController{

	@Autowired
	private CheckHouseService checkHouseService;
	
	/**
	 * 列表页面
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "checkHouse/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		checkHouseService.getList(request,response);
	} 
	
	/**
	 * 录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "checkHouse/getInfo.do")
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(checkHouseService.getInfo(request,response), response);
	} 
	
	/**
	 * 录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "checkHouse/insert.do")
	public void checkInsert(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(checkHouseService.getInfo(request,response), response);
	} 
	
	/**
	 * 新增
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "checkHouse/create.do")
	public void createOrder(HttpServletRequest request, HttpServletResponse response) {
	    this.writeJsonData( checkHouseService.createOrder(request,response), response);
	}
	
	/**
	 * 修改
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "checkHouse/update.do")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( checkHouseService.update(request,response), response);
	}
	
	/**
	 * 删除
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "checkHouse/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		  this.writeJsonData(checkHouseService.delete(request,response), response);
	} 
	
}
