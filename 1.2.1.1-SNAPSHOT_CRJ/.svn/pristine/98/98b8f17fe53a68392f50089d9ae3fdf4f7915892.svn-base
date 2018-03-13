package com.yc.rm.caas.appserver.bus.service.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yc.rm.caas.appserver.base.support.BaseService;
import com.yc.rm.caas.appserver.bus.controller.user.fo.UserFo;
import com.yc.rm.caas.appserver.bus.dao.user.IUserDao;
import com.yc.rm.caas.appserver.model.user.User;
import com.yc.rm.caas.appserver.util.Encrypt;

@Service("_userServImpl")
public class UserServImpl extends BaseService implements IUserServ
{

	@Autowired
	private IUserDao _userDao;

	/**
	 * 获取用户列表
	 * 
	 * @param userFo
	 *          查询信息
	 * @return 用户列表
	 */
	@Override
	public Map<String, Object> loadUserList(UserFo userFo)
	{
		log.info("loadUserList begin.");
		Map<String, Object> resultMap = new HashMap<>();
		try
		{
			String teamId = userFo.getTeamId();
			if (StringUtils.isBlank(teamId))
			{
				// 查询当前登录者的团队信息
				List<Map<String, Object>> selectTeamInfoById = _userDao.selectTeamInfoById(userFo);
				if (null == selectTeamInfoById || selectTeamInfoById.isEmpty())
				{
					userFo.setTeamId("0");
				}
				else
				{
					String teamIdStr = "";
					for (Map<String, Object> map : selectTeamInfoById) 
					{
						teamIdStr += String.valueOf(map.get("teamId")) + ",";
					}
					teamIdStr = teamIdStr.substring(0, teamIdStr.length() - 1);
					userFo.setTeamId(teamIdStr);
				}
			}
		
			PageHelper.startPage(userFo.getPageNumber(), userFo.getPageSize());
			List<User> userList = _userDao.selectUserList(userFo);
			if (null == userList || userList.isEmpty())
			{
				userList = new ArrayList<>();
			}
			PageInfo<User> pageInfo = new PageInfo<>(userList);
			resultMap.put("total", pageInfo.getTotal());
			resultMap.put("rows", userList);
		} catch (Exception e)	
		{
			log.error("查询用户列表信息错误信息：" + e);
		}

		log.info("loadUserList end.");
		return resultMap;
	}

	/**
	 * 新增用户
	 * 
	 * @param userFo
	 *          用户信息
	 * @return 新增结果
	 */
	@Override
	public int insertUser(UserFo userFo)
	{
		log.info("insertUser begin.");
		
		// 返回结果值
		int resultCode = 1;
		
		// 校验入参是否为空
		resultCode = checkParam(userFo);
		if (resultCode > 1)
		{
			return resultCode;
		}

		// 根据手机号校验用户是否存在(2)
		resultCode = checkUserByPhone(userFo);
		if (resultCode > 1)
		{
			return resultCode;
		}

		try
		{
			// 用户信息入库
			insertUserInfo(userFo);
		} catch (Exception e)
		{
			resultCode = 6;
			log.error("用户信息入库失败原因：" + e);
		}

		log.info("insertUser end");

		return resultCode;
	}

	/**
	 * 删除用户
	 * 
	 * @param userFo
	 * @return
	 */
	@Override
	public int removeCustomer(UserFo userFo)
	{
		log.info("removeUser begin");
		
		// 返回结果值
		int resultCode = 1;
		
		int userId = userFo.getUserId();
		if (userId == 0)
		{
			resultCode = 2;
			log.error("删除用户信息：客户ID不能为空。 id=【" + userId + "】");
			return resultCode;
		}

		try
		{
			deleteUserInfo(userFo);
		} catch (Exception e)
		{
			resultCode = 3;
			log.error("删除用户失败原因：" + e);
		}

		log.info("removeUser end");

		return resultCode;
	}

	/**
	 * 更改用户信息
	 * 
	 * @param userFo
	 * @return
	 */
	@Override
	public int modifyUser(UserFo userFo)
	{
		log.info("modifyUser begin");

		// 返回结果值
		int resultCode = 1;

		String userId = String.valueOf(userFo.getUserId());
		if (userId.equals("") || userId.equals("null"))
		{
			log.error("更改用户信息：用户ID不能为空。 userId=【" + userId + "】");
			resultCode = 2;
			return resultCode;
		}

		String userName = String.valueOf(userFo.getUserName());
		if (userName.equals("") || userName.equals("null"))
		{
			log.error("更改用户信息：用户姓名不能为空。userName=【" + userName + "】");
			resultCode = 3;
			return resultCode;
		}

		try
		{
			updateUserInfo(userFo);
		} catch (Exception e)
		{
			resultCode = 4;
			log.error("删除用户信息错误原因：" + e);
		}

		log.info("modifyUser begin");

		return resultCode;
	}

	/**
	 * 更改用户信息
	 * 
	 * @param userFo
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	{ RuntimeException.class, Exception.class })
	private void updateUserInfo(UserFo userFo)
	{
		String teamId = userFo.getTeamId();
		if (StringUtils.isNotBlank(teamId))
		{
			_userDao.updateRelationByTeam(userFo);			
		}

		String roleId = userFo.getRoleId();
		if (StringUtils.isNotBlank(roleId))
		{
			_userDao.deleteRelationByRole(userFo);
			String[] roleIds = roleId.split(",");
			_userDao.insertRelationByRole(roleIds, userFo.getUserId(), userFo.getCreateId());
		}
		
		String orgId = userFo.getOrgId();
		if (StringUtils.isNotBlank(orgId)) 
		{
			_userDao.deleteRelationByOrg(userFo);
			String[] orgIds = orgId.split(",");
			_userDao.insertRelationByOrg(orgIds, userFo.getUserId(), userFo.getCreateId());
		}
		
		_userDao.updateUser(userFo);
	}

	/**
	 * 删除客户信息
	 * 
	 * @param userFo
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	{ RuntimeException.class, Exception.class })
	private void deleteUserInfo(UserFo userFo) throws Exception
	{
		_userDao.updateUsrIsDelete(userFo);
	}

	/**
	 * 用户信息入库
	 * 
	 * @param customerFo
	 * @param res
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	{ RuntimeException.class, Exception.class })
	private void insertUserInfo(UserFo userFo) throws Exception
	{
		String teamId = userFo.getTeamId();
		String roleId = userFo.getRoleId();
		String orgId = userFo.getOrgId();

		String password = new Encrypt("SHA-1").getEncrypt("12345");
		userFo.setPassword(password);
		_userDao.insertUser(userFo);

		if (StringUtils.isNotBlank(teamId))
		{
			// 入库团队和用户关联关系表
			_userDao.insertRelationByTeam(userFo);
		}

		if (StringUtils.isNotBlank(roleId))
		{
			String[] roleIds = roleId.split(",");
			// 入库角色和用户关联关系表
			_userDao.insertRelationByRole(roleIds, userFo.getUserId(), userFo.getCreateId());
		}
		
		if (StringUtils.isNoneBlank(orgId))
		{
			String[] orgIds = orgId.split(",");
			// 入库组织和用户关联关系表
			_userDao.insertRelationByOrg(orgIds, userFo.getUserId(), userFo.getCreateId());
		}
	}

	/**
	 * 校验入参是否为空
	 * 
	 * @param customerFo
	 * @param res
	 */
	private int checkParam(UserFo userFo)
	{
		int result = 1;

		String userName = String.valueOf(userFo.getUserName());
		if (userName.equals("") || userName.equals("null"))
		{
			// 姓名不能为空
			result = 3;
			return result;
		}

		String userPhone = String.valueOf(userFo.getUserPhone());
		if (userPhone.equals("") || userPhone.equals("null"))
		{
			// 手机号不能为空
			result = 4;
			return result;
		}
		return result;
	}

	/**
	 * 根据手机号校验客户是否存在
	 * 
	 * @param customerFo
	 * @return
	 */
	private int checkUserByPhone(UserFo userFo)
	{
		int result = 1;
		try
		{
			int info = _userDao.selectCustomerByPhone(userFo);
			if (info > 0)
			{
				// 手机号已经存在，请重新填写
				result = 2;
			} else
			{
				// 校验成功
			}
		} catch (Exception e)
		{
			// 根据手机号校验用户失败
			result = 5;
			log.error("根据手机号校验用户失败信息：" + e);
		}
		return result;
	}

	/**
	 * 按照当前登陆者ID查询团队层级信息
	 * 
	 * @param customerFo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getTeamInfoById(UserFo userFo)
	{
		log.info("getTeamInfoById begin");

		List<Map<String, Object>> list = new ArrayList<>();

		String createId = String.valueOf(userFo.getCreateId());
		log.info("需要查询所有团队的当前登陆者ID：【" + createId + "】");

		if (createId.equals("") || createId.equals("null"))
		{
			log.info("团队查询：当前登陆者ID为空！");
			list = new ArrayList<>();
			return list;
		}

		try
		{
			list = _userDao.selectTeamInfoById(userFo);
			if (null == list)
			{
				log.info("没有查询到任何团队信息！");
				list = new ArrayList<>();
			}
		} catch (Exception e)
		{
			list = new ArrayList<>();
			log.error("按照当前登陆者ID查询团队层级错误信息：" + e);
		}

		log.info("getTeamInfoById end");

		return list;
	}

	/**
	 * 按照当前登录者ID查询角色信息
	 * 
	 * @param userFo
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getRoleInfoById(UserFo userFo)
	{
		log.info("getRoleInfoById begin");

		List<Map<String, Object>> list = new ArrayList<>();

		String createId = String.valueOf(userFo.getCreateId());
		log.info("需要查询所有角色的当前登陆者ID：【" + createId + "】");

		if (createId.equals("") || createId.equals("null"))
		{
			log.info("角色查询：当前登陆者ID为空！");
			list = new ArrayList<>();
			return list;
		}

		try
		{
			list = _userDao.selectRoleInfoById(userFo);
			if (null == list)
			{
				log.info("没有查询到任何角色信息！");
				list = new ArrayList<>();
			}
		} catch (Exception e)
		{
			list = new ArrayList<>();
			log.error("按照当前登陆者ID查询角色错误信息：" + e);
		}

		log.info("getRoleInfoById end");

		return list;
	}
	
	/**
	 * 查询用户状态
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getStateInfo()
	{
		log.info("getStateInfo begin");
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		try
		{
			list = _userDao.selectStateInfo();
			if (null == list)
			{
				log.info("没有查询到任何状态信息！");
				list = new ArrayList<>();
			}
		} catch (Exception e)
		{
			list = new ArrayList<>();
			log.error("查询用户状态错误信息：" + e);
		}
		
		log.info("getStateInfo end");
		
		return list;
	}
	
	/**
	 * 查询用户组织信息
	 * 
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getOrgInfo()
	{
		log.info("getOrgInfo begin");
		
		List<Map<String, Object>> list = new ArrayList<>();
		
		try
		{
			list = _userDao.selectOrgInfo();
			if (null == list)
			{
				log.info("没有查询到任何组织信息！");
				list = new ArrayList<>();
			}
		} catch (Exception e)
		{
			list = new ArrayList<>();
			log.error("查询用户组织错误信息：" + e);
		}
		
		log.info("getOrgInfo end");
		
		return list;
	}

	/**
	 * 禁用用户账号
	 * 
	 * @param customerFo
	 * @return
	 */
	@Override
	public int updateDisabledUser(UserFo userFo)
	{
		log.info("updateDisabledUser begin");

		// 返回结果值
		// 1：禁用成功，2：用户id为空，3：禁用失败
		int resultCode = 1;
		
		int userId = userFo.getUserId();
		if (userId < 0)
		{
			log.error("用户ID为空。用户ID：userId=【" + userId + "】");
			resultCode = 2;
			return resultCode;
		}

		try
		{
			_userDao.updateDisabledUser(userFo);
		} catch (Exception e)
		{
			resultCode = 3;
			log.error("账号禁用失败原因：" + e);
		}

		log.info("updateDisabledUser end");
		return resultCode;
	}

	/**
	 * 启用账号
	 * 
	 * @param userFo
	 * @return
	 */
	@Override
	public int updateEnabledUser(UserFo userFo)
	{
		log.info("updateEnabledUser begin");

		// 返回结果值
		int resultCode = 1;
		
		int userId = userFo.getUserId();
		if (userId < 0)
		{
			log.error("用户ID为空。用户ID：userId=【" + userId + "】");
			resultCode = 2;
			return resultCode;
		}

		try
		{
			_userDao.updateEnabledUser(userFo);
		} catch (Exception e)
		{
			resultCode = 3;
			log.error("账号启用失败原因：" + e);
		}

		log.info("updateEnabledUser end");
		return resultCode;
	}

	/**
	 * 初始化客户密码
	 * 
	 * @param userFo
	 * @return
	 */
	@Override
	public int modifyInitializePassword(UserFo userFo)
	{
		log.info("modifyInitializePassword begin");

		// 返回结果值
		int resultCode = 1;
		
		int userId = userFo.getUserId();
		if (userId < 0)
		{
			log.error("用户ID为空。用户ID：userId=【" + userId + "】");
			resultCode = 2;
			return resultCode;
		}
		
		String password = new Encrypt("SHA-1").getEncrypt("12345");
		userFo.setPassword(password);

		try
		{
			_userDao.modifyInitializePassword(userFo);
		} catch (Exception e)
		{
			resultCode = 3;
			log.error("密码初始化失败原因：" + e);
		}

		log.info("modifyInitializePassword end");
		return resultCode;
	}
}
