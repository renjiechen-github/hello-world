package pccom.web.action.sys.power;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.sys.power.PowerService;

@Controller
@RequestMapping("/power")
public class PowerController extends BaseController {
	@Resource
	private PowerService powerService;
	/**
	 * 根据菜单主键查询菜单权限
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectPower.do")
	public void selectPower(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(powerService.selectPower(request, response),response);
	} 
	/**
	 * 根据菜单主键查询菜单权限
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/savePower.do")
	public void savePower(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(powerService.savePower(request, response),response);
	} 
	/**
	 * 根据菜单主键查询菜单权限
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/savePowers.do")
	public void selectPowers(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(powerService.selectPowers(request, response),response);
	} 
	/**
	 * 更新权限的显示
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/startPower.do")
	public void startPower(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(powerService.startPower(request, response),response);
	} 
}
