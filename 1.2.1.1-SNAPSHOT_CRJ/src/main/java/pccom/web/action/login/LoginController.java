package pccom.web.action.login;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.login.LoginService;



/**
 * 获取图片验证码操作
 * @author 雷杨
 *
 */
@Controller
public class LoginController extends BaseController
{
	@Autowired
	public LoginService loginService;
	
	/**
	 * 获取区域
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "login/login.do")
	public void login(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(loginService.loginNew(request,response), response);
	} 
	
	/**
	 * 加载菜单信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "login/loadMenu.do")
	public void loadMenu(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(loginService.loadMenu(request,response), response);
	}
	
	/**
	 * 加载用户数据信息
	 * @author 雷杨
	 */
	@RequestMapping (value = "login/loadUser.do")
	public void loadUser(HttpServletRequest request, HttpServletResponse response){
		logger.debug("??????????????????????????????????????");
		this.writeJsonData(loginService.loadUser(request,response), response);
	}
	
	/**
	 * 登出
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "login/loginOut.do")
	public void loginOut(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(loginService.loginOut(request,response), response);
	}
	
	/**
	 * 修改密码
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "login/modifypass.do")
	public void modifypass(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(loginService.modifypass(request,response), response);
	}
	
	/**
	 * 测试表格
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "login/test.do")
	public void test(HttpServletRequest request, HttpServletResponse response){
		logger.debug("????????????????");
		this.writeJsonData(loginService.test(request,response), response);
	}
	
}
