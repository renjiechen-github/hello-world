package pccom.web.action.sys.grid;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pccom.web.action.BaseController;
import pccom.web.server.sys.grid.GridService;

@Controller
public class GridController extends BaseController
{
	@Autowired
	public GridService gridService;
	/**
	 * 网格列表查询
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/getList.do")
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		gridService.getList(request,response);
	} 
	
	/**
	 * 网格对应小区查询
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/getGroupList.do")
	public void getGroupList(HttpServletRequest request, HttpServletResponse response)
	{
		String flag = req.getAjaxValue(request, "flag");
		if ("1".equals(flag)) 
		{
			gridService.getSelectList(request,response);	
		}
		else
		{
			gridService.getGroupList(request,response);
		}
	} 
	
	/**
	 * 新增网格
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/gridCreate.do")
	public void gridCreate(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(gridService.gridCreate(request,response), response);
	} 
	
	/**
	 * 删除网格
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/gridDelete.do")
	public void gridDelete(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(gridService.gridDelete(request,response), response);
	} 
	
	/**
	 * 验证网格是否已存在
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/checkName.do")
	public void checkName(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(gridService.checkName(request,response), response);
	} 
	
	
	/**
	 * 小区修改网格
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/groupUpdate.do")
	public void update(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(gridService.groupUpdate(request,response), response);
	} 
	
	
	/**
	 * 获取管家列表信息
	 * @param response
	 * @author liuf
	 * @date 2016.9.19
	 */
	@RequestMapping("Grid/getMangerList.do")
	public void getMangerList(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(gridService.queryManager(request,response), response);
	}
	
	
	
	
	
	
	/**
	 * 小区删除
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/delete.do")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(gridService.groupDelete(request,response), response);
	} 
	
	/**
	 * 网格指派
	 * @author 刘飞
	 * @param response
	 * @throws ServiceException 
	 */
	@RequestMapping (value = "Grid/dispatch.do")
	public void dispatch(HttpServletRequest request, HttpServletResponse response) {
		this.writeJsonData(gridService.gridDispatch(request,response), response);
	} 
}
