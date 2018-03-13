package com.ycdc.nbms.datapermission.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.datapermission.dao.DataPermissionMapper;
import com.ycdc.nbms.datapermission.model.DataPermission;
import com.ycdc.nbms.datapermission.model.DataPermissionRequest;
import com.ycdc.nbms.datapermission.model.DataPermissionResponse;

import pccom.web.beans.User;

/**
 * 数据权限配置
 * 
 * @author sunchengming
 * @date 2017年9月20日 下午2:46:29
 * 
 */
@Service("DataPermissionServ")
public class DataPermissionServImpl extends BaseService implements DataPermissionServ
{

	@Autowired
	private DataPermissionMapper dataPermissionMapper;

	@Override
	public int udpatePermissionInfo(HttpServletRequest request, DataPermissionRequest req)
	{
		User user = getUser(request);
		if (null == user || user.getId().equals(""))
		{
			return 0;
		}
		int user_id = Integer.valueOf(getUser(request).getId());
		List<DataPermission> datas = req.getData();
		int role_id = req.getRole_id();
		
		// 根据角色id和类型id查看是否有记录
		for (DataPermission data : datas) 
		{
			int total = dataPermissionMapper.getTotalByRoleId(role_id, data.getType_id(), data.getSub_type_id());
			if (total == 0)
			{
				data.setRole_id(role_id);
				data.setCreater_id(user_id);
				dataPermissionMapper.insertPermissions(data);
			}
		}
		
		// 根据role_id查询是否存在记录
		int recordTotal = dataPermissionMapper.getRecordTotalByRoleId(role_id);
		
		int result = 0;
		try
		{
			if (recordTotal > 0)
			{
				// 有记录，更改其数据权限
				result = updateDataPermission(datas, role_id, user_id);
			} else
			{
				// 没有记录，新增权限
				result = addDataPermission(datas, role_id, user_id);
			}
		} catch (Exception e)
		{
			logger.info("更改数据权限错误：" + e);
		}

		return result;
	}

	@Override
	public DataPermissionResponse getPermissionsInfo(DataPermissionRequest req)
	{
		List<DataPermission> dataInfo = null;
		DataPermissionResponse response = new DataPermissionResponse();
		response.setState(1);
		try
		{
			dataInfo = dataPermissionMapper.getPermissionsInfo(req);
			
			// 根据角色id查询菜单权限
//			List<Map<String, Object>> list = dataPermissionMapper.getMenuInfo(req);
		} catch (Exception e)
		{
			dataInfo = new ArrayList<>();
			response.setState(0);
			logger.error("根据角色id【role_id=" + req.getRole_id() + "】查询数据权限报错。错误信息【" + e + "】");
		}

		response.setData(dataInfo);
		return response;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	{ RuntimeException.class, Exception.class })
	private int addDataPermission(List<DataPermission> datas, int role_id, int user_id) throws Exception
	{
		int info = dataPermissionMapper.batchInsertPermissions(datas, role_id, user_id);
		return info;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	{ RuntimeException.class, Exception.class })
	private int updateDataPermission(List<DataPermission> datas, int role_id, int user_id)
	{
		int info = dataPermissionMapper.batchUpdatePermissions(datas, role_id, user_id);
		return info;
	}

	@Override
	public List<Map<String, Object>> getPermissionsInfoByRoleIds(String roleIds, int typeId)
	{
		String roleId = "";
		String[] roleArry = roleIds.split(",");
		for (String string : roleArry)
		{
			if (string.trim().equals("")) {
				continue;
			}
			roleId += string + ",";
		}
		roleId = roleId.substring(0, roleId.length() - 1);
		
		List<Map<String, Object>> result = new ArrayList<>();
		List<Map<String, Object>> roleIdList = dataPermissionMapper.getPermissionsInfoByRoleIds(roleId, typeId);
		result.addAll(roleIdList); 
		
		// 根据子类型去重
		for (Map<String, Object> map : roleIdList)
		{
			String subTypeId = String.valueOf(map.get("subTypeId"));
			String queryPermission = String.valueOf(map.get("queryPermission"));
			String addPermission = String.valueOf(map.get("addPermission"));
			String updatePermission = String.valueOf(map.get("updatePermission"));
			String assignPermission = String.valueOf(map.get("assignPermission"));
			String closePermission = String.valueOf(map.get("closePermission"));
			for (Map<String, Object> maps : result) 
			{
				String subTypeId1 = String.valueOf(maps.get("subTypeId"));
				if (subTypeId.equals(subTypeId1)) 
				{
					if (queryPermission.equals("1"))
					{
						maps.put("queryPermission", "1");
					}
					if (addPermission.equals("1"))
					{
						maps.put("addPermission", "1");
					}
					if (updatePermission.equals("1"))
					{
						maps.put("updatePermission", "1");
					}
					if (assignPermission.equals("1"))
					{
						maps.put("assignPermission", "1");
					}
					if (closePermission.equals("1"))
					{
						maps.put("closePermission", "1");
					}
				}
			}
		}
		List<Map<String, Object>> results = new ArrayList<>();
		Set<Map<String, Object>> setMap = new HashSet<>(result);
		for (Map<String, Object> map : setMap)
		{
			results.add(map);
		}
		logger.info("权限数据：" + results);
		return results;
	}

	@Override
	public int judgeExportButton(HttpServletRequest request, DataPermissionRequest req)
	{
		User user = getUser(request);
		if (null == user) 
		{
			return 0;
		}
		if (null == user.getRoles())
		{
			return 0;
		}
		String roleIds = user.getRoles();
		// 去掉字符串中空的信息
		roleIds = roleIds.replace(" ", "").replace(",,", ",");
		if (roleIds.startsWith(","))
		{
			roleIds = roleIds.substring(1, roleIds.length());
		}
		// 按照角色查询导出配置信息
		int total = dataPermissionMapper.getExportInfoByRoleIds(roleIds);
		if (total > 0)
		{
			total = 1;
		}
		return total;
	}
}
