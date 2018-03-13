package pccom.web.action.sys.account;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.sys.account.AccountService;
@Controller
@RequestMapping("/account")
public class AccountController extends BaseController{
	@Resource
	private  AccountService accountService;
	
	/**
	 * 用户查询
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		accountService.getList(request, response);
	} 
	
	/**
	 * 查询所有的组织
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectOrg.do")
	public void selectOrg(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.selectOrg(request, response),response);
	}
        
        /**
	 * 查询所有的组织
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectTeam.do")
	public void selectTeam(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.selectTeam(request, response),response);
	}
	
	/**
	 * 验证手机号码 是否存在
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectMoblie.do")
	public void selectMoblie(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.selectMoblie(request, response),response);
	} 
	
	/**
	 * 查询所有的角色
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectRole.do")
	public void selectRole(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.selectRole(request, response), response);
	} 
	
	/**
	 * 用户新增
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/create.do")
	public void create(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData( accountService. accountSave(request, response), response);
	} 
	
	/**
	 * 用户停用(修改状态)
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/stop.do")
	public void stop(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.accountStop(request, response), response); 
	} 
	
	/**
	 * 用户启用(修改状态)
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/enable.do")
	public void enable(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.accountEnable(request, response), response); 
	} 
	
	/**
	 * 初始化密码
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/start.do")
	public void start(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(accountService.accountStart(request, response), response); 
	} 
	
	/**
	 * 用户修改
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/update.do")
	public void query(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData( accountService.accountUpdate(request, response), response);
	} 
	
	/**
	 * 用户修改
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/deleteUser.do")
	public void deleteUser(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData( accountService.deleteUser(request, response), response);
	}
	
}
