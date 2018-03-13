package pccom.web.mobile.action.login;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;
import pccom.web.mobile.service.login.MiLoginService;

/**
 * 登录验证
 * @author suntf
 * @date 2016-7-21
 */
@Controller
@RequestMapping("mobile/login/")
public class MiLoginController extends BaseController
{
	@Autowired
	public MiLoginService miLoginService;
	
	/**
	 * 登录验证
	 * @param request
	 */
	@RequestMapping("checkLogin.do")
	public void checkLogin(HttpServletResponse response)
	{
		this.writeJsonData(miLoginService.checkLogin(request), response);
	}
	
	/**
	 * 初始化用户信息
	 * @param request
	 */
	@RequestMapping("initUserInfo.do")
	public void initUserInfo(HttpServletResponse response)
	{
		this.writeJsonData(miLoginService.initUserInfo(request), response);
	}
	
	/**
	 * 更新用户客户端channelId
	 * @param response
	 */
	@RequestMapping("updateChannelId.do")
	public void updateChannelId(HttpServletResponse response)
	{
		this.writeText(miLoginService.updateChannelId(request), response);
	}
	
	/**
	 * 修改密码
	 * @param response
	 */
	@RequestMapping("updatePassword.do")
	public void updatePassword(HttpServletResponse response)
	{
		this.writeText(miLoginService.updatePassword(request), response);
	}
	
	/**
	 * 更新头像
	 * @param response
	 */
	@RequestMapping("modifyHeaderImage.do")
	public void modifyHeaderImage(HttpServletResponse response)
	{
		this.writeText(miLoginService.modifyHeaderImage(request), response);
	}
	
	/**
	 * 修改用户名
	 * @param response
	 */
	@RequestMapping("updateUserName.do")
	public void updateUserName(HttpServletResponse response)
	{
		this.writeText(miLoginService.updateUserName(request), response);
	}
	
}
