package pccom.web.action.sys.menu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pccom.web.action.BaseController;
import pccom.web.server.sys.menu.MenuService;

@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
	@Autowired
	private MenuService menuService;

	/**
	 * 查询所有的菜单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/select.do")
	public void selectMenu(HttpServletRequest request,
			HttpServletResponse response) {
		this.writeJsonData(menuService.selectMenu(request, response), response);
	}

	/**
	 * 新增菜单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/create.do")
	public void createMenu(HttpServletResponse response) {
		this.writeJsonData(menuService.saveMenu(request, response), response);
	}

	/**
	 * 查询父级菜单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selects.do")
	public void selects(HttpServletRequest request, HttpServletResponse response) {
		menuService.select(request, response);
	}
	/**
	 * 查询父级菜单
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/selectchildren.do")
	public void selectchildren(HttpServletRequest request, HttpServletResponse response) {
		menuService.selectchildren(request, response);
	}
	/**
	 *菜单删除(修改状态)
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(menuService.delete(request, response), response); 
	} 
	/**
	 *查询菜单信息
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectnew.do")
	public void selectnew(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(menuService.selectNew(request, response), response); 
	} 
	/**
	 *查询权限信息
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectpower.do")
	public void selectPower(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(menuService.selectPower(request, response), response); 
	} 
	/**
	 *查询权限信息
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/selectpowerurl.do")
	public void selectpowerurl(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(menuService.selectpowerurl(request, response), response); 
	} 
	/**
	 *查询权限信息
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "/updatemenu.do")
	public void updateMenu(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(menuService.updateMenu(request, response), response); 
	} 
}