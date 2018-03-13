package pccom.web.mobile.action.setting;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.mobile.service.setting.SettingService;

/**
 * 系统设置
 * @author suntf
 * @2014-10-23
 */
@Controller
@RequestMapping(value = "/mobile/setting")
public class SettingController extends BaseController
{
	/**
	 * spring 类型注入
	 */
	@Autowired
	public SettingService settingService;

	/**
	 * 获取当前最新版本及是否自动升级信息
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value="/getMaxVersionMark.do")
	public void getMaxVersionMark(HttpServletResponse response) 
	{
		this.writeJsonData(settingService.getMaxVersionMark(request),response);
	}
	
	/**
	 * 获取当前最新版本号
	 * @param response
	 */
	@RequestMapping(value="/getMaxVersionCode.do")
	public void getMaxVersionCode(HttpServletResponse response) 
	{
		this.writeJsonData(settingService.getMaxVersionCode(request),response);
	}
}
