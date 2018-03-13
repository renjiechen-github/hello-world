package pccom.web.action.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.user.UserService;
/**
 * 用户管理
 * @Controller
 * @author bianbojun
 *
 */
@Controller
public class UserController extends BaseController {
	@Autowired
	public UserService userService;
	/**
	 * 用户查询
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "user/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		userService.getList(request, response);
	} 
	/**
	 * 用户新增
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "user/create.do")
	public void create(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData( userService.userSave(request, response), response);
	} 
	/**
	 * 用户删除(修改状态)
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "user/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(userService.userDelete(request, response), response); 
	} 
	/**
	 * 用户修改
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "user/update.do")
	public void query(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData( userService.userUpdate(request, response), response);
	} 
}
