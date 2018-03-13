package pccom.web.action.sys.role;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.sys.role.RoleService;

@RequestMapping("/role")
@Controller
public class RoleController extends BaseController {
	@Resource
	private RoleService roleService;

	/**
	 * 角色管理
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		roleService.getList(request, response);
	}
	/**
	 * 查询菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selectmenu.do")
	public void selectmenu(HttpServletRequest request, HttpServletResponse response) {
		roleService.selectmenu(request, response);
	}
	/**
	 * 查询权限信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selectpower.do")
	public void selectpower(HttpServletRequest request, HttpServletResponse response) {
		roleService.selectpower(request, response);
	}
	/**
	 * 更新权限
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/updaterole.do")
	public void updaterole(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(roleService.updaterole(request, response), response);
	}
	/**
	 * 根据角色主键查看权限信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/findpower.do")
	public void findpower(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(roleService.findpower(request, response), response);
	}
	/**
	 * 保存权限信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saverole.do")
	public void saverole(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(roleService.saverole(request, response), response);
	}
}
