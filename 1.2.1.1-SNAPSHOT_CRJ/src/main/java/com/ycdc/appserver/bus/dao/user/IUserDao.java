
package com.ycdc.appserver.bus.dao.user;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ycdc.appserver.model.user.User;

public interface IUserDao
{

	User checkLogin(User u);

	User getUserRoles(User u);

	User getUserOrgIds(User u);

	int updateUserRegistrationId(User u);
	
	//更新用户性别和出生日期
	int upUserSexAndBirth(User u);

	List<Map<String, String>> loadUserList(@Param("keyWord") String keyWord,
			@Param("startPage") int startPage, @Param("pageSize") int pageSize,
			@Param("user_id") String user_id, @Param("teamIds") String teamIds);
	//根据手机号查找用户信息
	User getUserId(@Param("mobile") String mobile,@Param("id") String id);
}
