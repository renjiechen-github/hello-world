
package com.yc.rm.caas.appserver.bus.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.bus.controller.user.fo.UserFo;
import com.yc.rm.caas.appserver.model.user.User;

/**
 * @Description 用户管理
 * @author sunchengming
 * @date 2017年7月5日 下午1:59:34
 * 
 */
@Component("_userDao")
public interface IUserDao
{
	/**
	 * 获取用户列表
	 * 
	 * @param userFo
	 *          入参
	 * @return 用户列表
	 */
	List<User> selectUserList(UserFo userFo);

	/**
	 * 新增用户
	 * 
	 * @param userFo
	 *          入参
	 * @return 新增结果
	 */
	int insertUser(UserFo userFo);

	/**
	 * 按照当前登陆者ID查询团队层级信息
	 * 
	 * @param userFo
	 * @return
	 */
	List<Map<String, Object>> selectTeamInfoById(UserFo userFo);

	/**
	 * 按照当前登录者ID查询角色信息
	 * 
	 * @param userFo
	 * @return
	 */
	List<Map<String, Object>> selectRoleInfoById(UserFo userFo);

	/**
	 * 查询用户状态
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectStateInfo();

	/**
	 * 查询用户组织信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> selectOrgInfo();

	/**
	 * 根据手机号校验客户是否存在
	 * 
	 * @param userFo
	 * @return
	 */
	int selectCustomerByPhone(UserFo userFo);

	/**
	 * 入库团队和客户关联关系表
	 * 
	 * @param userFo
	 * @return
	 */
	int insertRelationByTeam(UserFo userFo);

	/**
	 * 入库角色和客户关联关系表
	 * 
	 * @param roleIds
	 *          角色数组
	 * @param userFo
	 * @return
	 */
	int insertRelationByRole(@Param(value = "roleIds") String[] roleIds, @Param(value = "userId") int userId,
			@Param(value = "createId") int createId);

	/**
	 * 入库组织和客户关联关系表
	 * 
	 * @param orgIds
	 * @param userFo
	 * @return
	 */
	int insertRelationByOrg(@Param(value = "orgIds") String[] orgIds, @Param(value = "userId") int userId,
			@Param(value = "createId") int createId);

	/**
	 * 删除客户基本信息
	 * 
	 * @param userFo
	 * @return
	 */
	int updateUsrIsDelete(UserFo userFo);

	/**
	 * 删除客户团队关联关系
	 * 
	 * @param userFo
	 * @return
	 */
	int deleteRelationByTeam(UserFo userFo);

	/**
	 * 删除客户角色关联关系
	 * 
	 * @param userFo
	 * @return
	 */
	int deleteRelationByRole(UserFo userFo);

	/**
	 * 更改客户基本信息
	 * 
	 * @param userFo
	 * @return
	 */
	int updateUser(UserFo userFo);

	/**
	 * 更改客户团队关联信息
	 * 
	 * @param userFo
	 * @return
	 */
	int updateRelationByTeam(UserFo userFo);

	/**
	 * 禁用客户账号
	 * 
	 * @param userFo
	 * @return
	 */
	int updateDisabledUser(UserFo userFo);

	/**
	 * 启用客户账号
	 * 
	 * @param userFo
	 * @return
	 */
	int updateEnabledUser(UserFo userFo);

	/**
	 * 初始化客户密码
	 * 
	 * @param userFo
	 * @return
	 */
	int modifyInitializePassword(UserFo userFo);

	/**
	 * 删除组织和用户关联关系
	 * 
	 * @param userFo
	 * @return
	 */
	int deleteRelationByOrg(UserFo userFo);

}
