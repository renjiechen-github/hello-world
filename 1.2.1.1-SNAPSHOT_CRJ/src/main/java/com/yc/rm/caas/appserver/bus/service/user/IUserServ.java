
package com.yc.rm.caas.appserver.bus.service.user;

import java.util.List;
import java.util.Map;

import com.yc.rm.caas.appserver.bus.controller.user.fo.UserFo;

/**
 * @Description 用户信息管理
 * @author sunchengming
 * @date 2017年7月5日 下午1:43:14
 * 
 */
public interface IUserServ
{
	/**
	 * 获取用户列表
	 * 
	 * @param userFo
	 *          查询信息
	 * @return 用户列表
	 */
	Map<String, Object> loadUserList(UserFo userFo);

	/**
	 * 新增用户
	 * 
	 * @param userFo
	 *          用户信息
	 * @return 新增结果
	 */
	int insertUser(UserFo userFo);

	/**
	 * 删除用户
	 * 
	 * @param userFo
	 * @return
	 */
	int removeCustomer(UserFo userFo);

	/**
	 * 禁用账号
	 * 
	 * @param userFo
	 * @return
	 */
	int updateDisabledUser(UserFo userFo);

	/**
	 * 启用账号
	 * 
	 * @param userFo
	 * @return
	 */
	int updateEnabledUser(UserFo userFo);

	/**
	 * 更改用户信息
	 * 
	 * @param userFo
	 * @return
	 */
	int modifyUser(UserFo userFo);

	/**
	 * 按照当前登陆者ID查询团队层级信息
	 * 
	 * @param userFo
	 * @return
	 */
	List<Map<String, Object>> getTeamInfoById(UserFo userFo);

	/**
	 * 按照当前登录者ID查询角色信息
	 * 
	 * @param userFo
	 * @return
	 */
	List<Map<String, Object>> getRoleInfoById(UserFo userFo);

	/**
	 * 查询用户状态
	 * 
	 * @param userFo
	 * @return
	 */
	List<Map<String, Object>> getStateInfo();

	/**
	 * 初始化密码
	 * 
	 * @param userFo
	 * @return
	 */
	int modifyInitializePassword(UserFo userFo);

	/**
	 * 查询组织信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> getOrgInfo();
}
