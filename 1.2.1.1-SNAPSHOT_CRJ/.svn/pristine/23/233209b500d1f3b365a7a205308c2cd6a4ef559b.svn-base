package pccom.web.action.system;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.server.system.SystemService;



/**
 * 获取图片验证码操作
 * @author 雷杨
 *
 */
@Controller
public class SystemController extends BaseController
{
	@Autowired
	public SystemService systemService;
	
	/**
	 * 获取区域
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "sys/queryPayList.do")
	public void queryMenuList(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.queryMenuList(request,response), response);
	} 
	
	/**
	 * 查询表基础类型
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "sys/queryItem.do")
	public void queryItem(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.queryItem(request,response), response);
	}
	
	/**
	 * 查询表基础类型
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "sys/queryItemAll.do")
	public void queryItemAll(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.queryItemAll(request,response), response);
	}
	
	/**
	 * 人员查询
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "sys/queryUser.do")
	public void queryUser(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.queryUser(request,response), response);
	}
	
	/**
	 * 获取区域
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "sys/queryArea.do")
	public void queryAreaList(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.getArea(request,response), response);
	} 
	
	/**
	 * 材料配置
	 * @author 刘飞
	 * @param response
	 */
	@RequestMapping (value = "sys/queryMcate.do")
	public void queryMcate(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.queryMcate(request,response), response);
	}
	
	/**
	 * 图片上传
	 * @author 刘飞
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping (value = "sys/uploadFiles.do")
	public void UploadUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
		this.writeJsonData(systemService.UploadUrl(request,response), response);
	} 
	
	/**
	 * 加载图片信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "sys/loadTmpImg.do")
	public void loadTmpImg(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.loadTmpImg(request,response), response);
	}
	
	/**
	 * 设置公共图片
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "sys/setCommonImg.do")
	public void setCommonImg(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.setCommonImg(request,response), response);
	}
	
	/**
	 * 删除图片
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "sys/deleteImg.do")
	public void deleteImg(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(systemService.deleteImg(request,response), response);
	}
	
	/**
	 * 获取用户姓名
	 * @param response
	 * @author liuf
	 * @date 2016年8月29日
	 */
	@RequestMapping("sys/getUserName.do")
	public void getUserName(HttpServletResponse response)
	{
		this.writeJsonData(systemService.getUserName(request), response);
	}
	/**
	 * 获取用户姓名
	 * @param response
	 * @author liuf
	 * @date 2016年8月29日
	 */
	@RequestMapping("sys/getUserNameById.do")
	public void getUserNameById(HttpServletResponse response)
	{
		this.writeJsonData(systemService.getUserNameById(request), response);
	}
	
	
	
	
	/**
	 * 获取管家列表信息
	 * @param response
	 * @author liuf
	 * @date 2016.9.19
	 */
	@RequestMapping("sys/getMangerList.do")
	public void getMangerList(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(systemService.queryManager(request,response), response);
	
	}
	
}
