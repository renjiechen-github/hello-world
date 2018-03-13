package com.yc.rm.caas.appserver.bus.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.rm.caas.appserver.base.annotation.CaasAPI;
import com.yc.rm.caas.appserver.base.support.BaseController;
import com.yc.rm.caas.appserver.bus.controller.user.fo.UserFo;
import com.yc.rm.caas.appserver.bus.service.user.IUserServ;

@Controller
@RequestMapping("/caas/sysuser")
public class SysUserController extends BaseController
{

	@Autowired
	private IUserServ _userServiceImpl;

	/**
	 * 获取用户列表
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/loadUserList")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "出参")
	public @ResponseBody Map<String, Object> loadUserList(@RequestBody UserFo userFo)
	{
		log.debug("用户查询入参信息：" + userFo);
		userFo.setCreateId(getUser().getUserId());
		return _userServiceImpl.loadUserList(userFo);
	}

	/**
	 * 新增用户
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/addUser")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "1：新增成功，初始密码【12345】，2：手机号已经存在，请重新填写，3：姓名不能为空，4：手机号不能为空，5：根据手机号校验用户失败，6新增用户失败")
	public @ResponseBody int addUser(@RequestBody UserFo userFo)
	{
		log.debug("新增用户入参信息：" + userFo);
		userFo.setCreateId(getUser().getUserId());
		return _userServiceImpl.insertUser(userFo);
	}

	/**
	 * 删除用户信息
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/removeUser")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "1：删除成功，2：用户id为空，3：删除用户失败")
	public @ResponseBody int removeUser(@RequestBody UserFo userFo)
	{
		log.debug("删除用户入参信息：" + userFo);
		return _userServiceImpl.removeCustomer(userFo);
	}

	/**
	 * 更改用户信息
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/modifyUser")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "1：更改成功，2：用户id不能为空，3：用户姓名不能为空，4：更改失败")
	public @ResponseBody int modifyUser(@RequestBody UserFo userFo)
	{
		log.debug("更改用户入参信息：" + userFo);
		userFo.setCreateId(getUser().getUserId());
		return _userServiceImpl.modifyUser(userFo);
	}

	/**
	 * 停用账号
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/updateDisabledUser")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "出参")
	public @ResponseBody int updateDisabledUser(@RequestBody UserFo userFo)
	{
		log.debug("禁用用户账号入参信息：" + userFo);
		return _userServiceImpl.updateDisabledUser(userFo);
	}

	/**
	 * 启用账号
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/updateEnabledUser")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "1：启用成功，2：用户id为空，3：启用失败")
	public @ResponseBody int updateEnabledUser(@RequestBody UserFo userFo)
	{
		log.debug("启用账号入参信息：" + userFo);
		return _userServiceImpl.updateEnabledUser(userFo);
	}

	/**
	 * 按照当前登陆者ID查询团队层级信息
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/getTeamInfoById")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "出参")
	public @ResponseBody List<Map<String, Object>> getTeamInfoById(@RequestBody UserFo userFo)
	{
		log.debug("查询团队层级入参信息：" + userFo);
		userFo.setCreateId(getUser().getUserId());
		return _userServiceImpl.getTeamInfoById(userFo);
	}

	/**
	 * 按照当前登录者ID查询角色信息
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/getRoleInfoById")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "出参")
	public @ResponseBody List<Map<String, Object>> getRoleInfoById(@RequestBody UserFo userFo)
	{
		log.debug("查询角色信息入参信息：" + userFo);
		userFo.setCreateId(getUser().getUserId());
		return _userServiceImpl.getRoleInfoById(userFo);
	}

	/**
	 * 查询用户状态
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getStateInfo")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "出参")
	public @ResponseBody List<Map<String, Object>> getStateInfo()
	{
		return _userServiceImpl.getStateInfo();
	}

	/**
	 * 查询组织信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getOrgInfo")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "出参")
	public @ResponseBody List<Map<String, Object>> getOrgInfo()
	{
		return _userServiceImpl.getOrgInfo();
	}

	/**
	 * 密码初始化
	 * 
	 * @param userFo
	 * @return
	 */
	@RequestMapping(value = "/initializePassword")
	@CaasAPI(TREQMSG = "入参", TRSPMSG = "1：密码初始化成功，2：用户id不能为空，3：密码初始化失败")
	public @ResponseBody int initializePassword(@RequestBody UserFo userFo)
	{
		return _userServiceImpl.modifyInitializePassword(userFo);
	}

}
