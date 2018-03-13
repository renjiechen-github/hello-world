package pccom.web.flow.base;
import java.text.ParseException;
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
public class CertificateLeaveController extends BaseController{

	@Autowired
	private CertificateLeaveService certificateLeaveService;
	
	/**
	 * 列表页面
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "CertificateLeave/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		certificateLeaveService.getList(request,response);
	} 
	
	/**
	 * 录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "CertificateLeave/getInfo.do")
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(certificateLeaveService.getInfo(request,response), response);
	} 
	
	/**
	 * 录入数据
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "CheckHouse/insert.do")
	public void checkInsert(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(certificateLeaveService.getInfo(request,response), response);
	} 
	
	/**
	 * 新增
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "CertificateLeave/create.do")
	public void createOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    this.writeJsonData( certificateLeaveService.createOrder(request,response), response);
	}
	
	/**
	 * 修改
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "CertificateLeave/update.do")
	public void updateOrder(HttpServletRequest request, HttpServletResponse response){
	    this.writeJsonData( certificateLeaveService.update(request,response), response);
	}
	
	/**
	 * 删除
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "CertificateLeave/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  this.writeJsonData( certificateLeaveService.delete(request,response), response);
	}
	
}
