package com.yc.rm.caas.appserver.bus.service.team;

import java.util.List;
import java.util.Map;

import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamRelAreaFo;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamRelFo;
import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamSelectFo;
import com.yc.rm.caas.appserver.model.house.Group;
import com.yc.rm.caas.appserver.model.user.User;

/**
 * @date 20170701
 * @author 冷文佩
 *
 */
public interface ITeamServ
{
	/**
	 * 获取某用户名下的成员列表，memberId和memberName
	 * 
	 * @param userId
	 * @return
	 */
	List<User> getMemberListByUserId(int userId);
	
	/**
	 * 获取某团队的团队负责人id列表
	 * 
	 * @param teamId
	 * @return
	 */
	List<Map<String,Object>> getLeaders(int teamId);
	
	/**
	 * 获取某用户名下的管理的团队列表，team_id
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> getTeamIdListByUserId(int userId);
	
	/**
	 * 获取某用户名下的小区列表，groupId和groupName
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> getGroupListByUserId(int userId);
	
	/**
	 * 某团队名下的该用户未关联的小区列表/获取未分配团队的小区列表/获取某用户名下关联的小区列表
	 * 
	 * @param teamFo
	 * @return
	 */
	List<Group> getAreaInfo(TeamSelectFo teamFo);

	/**
	 * 获取人员列表
	 * 
	 * @param teamFo
	 * @return
	 */
	List<User> getUserList(TeamSelectFo teamFo);

	/**
	 * 查询新建的团队等级，返回正数为正常，返回负数则不能新建
	 * 
	 * @param userId
	 * @return
	 */
	int getTeamLevel(int userId);
	
	/**
	 * 获取团队首页信息
	 * 
	 * @param teamFo
	 * @return
	 */
	List<Map<String, Object>> getTeamPage(TeamSelectFo teamFo);

	/**
	 * 根据teamId显示下级团队情况
	 * 
	 * @param teamFo
	 * @return
	 */
	Map<String, Object> getTeamByTeamId(TeamSelectFo teamFo);

	/**
	 * 根据团队名称模糊匹配符合条件的团队信息
	 * 
	 * @param teamFo
	 * @return
	 */
	List<Map<String, Object>> getTeamByDimSearch(TeamSelectFo teamFo);

	/**
	 * 新建团队
	 * 
	 * @param teamFo
	 * @return
	 */
	int addTeam(TeamSelectFo teamFo);

	/**
	 * 新增团员
	 * 
	 * @param teamFo
	 * @return
	 */
	int addTeamMember(TeamSelectFo teamFo);

	/**
	 * 删除团队
	 * 
	 * @param teamFo
	 * @return
	 */
	int removeTeam(TeamSelectFo teamFo);

	/**
	 * 修改团队名称
	 * 
	 * @param teamFo
	 * @return
	 */
	int modifyTeamName(TeamSelectFo teamFo);

	/**
	 * 变更团队负责人
	 * 
	 * @param teamFo
	 * @return
	 */
	int modifyTeamCharge(TeamSelectFo teamFo);

	/**
	 * 变更团队成员
	 * 
	 * @param teamRelFo
	 * @return
	 */
	int modifyTeamMember(TeamRelFo teamRelFo);

	/**
	 * 删除团员
	 * 
	 * @param teamRelFo
	 * @return
	 */
	int removeTeamMember(TeamRelFo teamRelFo);

	/**
	 * 团队关联小区
	 * 
	 * @param AreaFo
	 * @return
	 */
	int findAreaRel(TeamRelAreaFo AreaFo);

	/**
	 * 小区关联人员
	 * 
	 * @param teamFo
	 * @return
	 */
	int findAreaMemberRel(TeamRelFo teamFo);

	/**
	 * 获取某用户名下管理的团队列表，平台管理员返回所有团队，团队管理员返回所管理的团队和子团队，一般人员返回null
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String, Object>> getTeamIdsByCharge(int userId);

	List<Map<String, Object>> getTeamIdsByTeamId(int teamId);

}
