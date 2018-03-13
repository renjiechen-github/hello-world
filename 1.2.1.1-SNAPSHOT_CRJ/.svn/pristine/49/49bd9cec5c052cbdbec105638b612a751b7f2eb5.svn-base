package com.yc.rm.caas.appserver.bus.dao.team;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.bus.controller.team.fo.TeamSelectFo;
import com.yc.rm.caas.appserver.model.house.Group;
import com.yc.rm.caas.appserver.model.team.TeamBean;
import com.yc.rm.caas.appserver.model.team.TeamLeaders;
import com.yc.rm.caas.appserver.model.team.TeamRelHouse;
import com.yc.rm.caas.appserver.model.team.TeamRelation;
import com.yc.rm.caas.appserver.model.user.User;

/**
 * @author 冷文佩
 * @date 20170701
 */
@Component("_teamDao")
public interface ITeamDao
{
	int ifUiqOne(@Param("teamId") int teamId,@Param("userId") int userId);
	
	int maxLevel(@Param("userId") int userId);
	
	/**
	 * 根据团队id，查找它的子团队负责人id符合的人员 
	 * 
	 * @param teamId
	 * @return
	 */
	List<Map<String,Object>> selectSubLeaderByTeamId(@Param("teamId") int teamId);
	
	/**
	 * 根据userId获取他所属团队id
	 * 
	 * @param teamId
	 * @return
	 */
	List<Map<String,Object>> selectTeamIdByMem(@Param("userId") int userId);
	
	/**
	 * 获取所有用户除去已在团队的userId
	 * 
	 * @param teamId
	 * @return
	 */
	List<User> selectAllUser(@Param("teamId") int teamId);
	
	/**
	 * 获取所有团队id
	 * 
	 * @return
	 */
	List<Map<String,Object>> selectAllTeamId();
	
	/**
	 * 根据leaderId直接获取团队id
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> selectTeamIdByUser(@Param("userId") int userId);

	/**
	 * 获取未关联团队的小区列表
	 * 
	 * @param teamId
	 * @return
	 */
	List<Group> selectAreaList(@Param("teamId") int teamId,@Param("parentTeamId") int parentTeamId);

	/**
	 * 某团队名下的该用户未关联的小区列表
	 * 
	 * @param userId
	 * @param teamId
	 * @return
	 */
	List<Group> selectAreaByTeam(@Param("memberId") int memberId, @Param("teamId") int teamId);

	/**
	 * 用户名下关联的小区列表
	 * 
	 * @param userId
	 * @return
	 */
	List<Group> selectAreaByUser(@Param("memberId") int memberId);

	/**
	 * 某团队下关联的小区列表
	 * 
	 * @param teamId
	 * @return
	 */
	List<Group> selectAreaListByTeam(@Param("teamId") int teamId);

	/**
	 * 获取未分配团队的成员列表信息
	 * 
	 * @param teamFo
	 * @return
	 */
	List<User> selectUserList();

	/**
	 * 获取团队下团员信息
	 * 
	 * @param teamFo
	 * @return
	 */
	List<User> selectUserListByTeam(TeamSelectFo teamFo);

	/**
	 * 获取团队列表/本身团队基本信息/子团队信息
	 * 
	 * @param teamFo
	 * @return
	 */
	List<TeamBean> selectTeamInfo(TeamSelectFo teamFo);
	
	/** 
	 * 获取自己团队的基本信息 
	 * 
	 * @param teamFo
	 * @return
	 */
	TeamBean selectTeamInfoOne(TeamSelectFo teamFo);

	/** 
	 * 获取团队负责人信息
	 * 
	 * @param userId
	 * @return
	 */
	List<Map<String,Object>> selectLeaderInfo(@Param("teamId") int teamId,@Param("userId") int userId);
	
	/**
	 * 判断团队是否关联小区
	 * 
	 * @param teamId
	 * @return
	 */
	int selectIfRelArea(@Param("teamId") int teamId);

	/**
	 * 团队关联小区的房源数量
	 * 
	 * @param teamId
	 * @return
	 */
	int selectHouseCnt(@Param("teamId") int teamId);

	/**
	 * 根据所选团队，显示团队下的成员
	 * 
	 * @param teamId
	 * @return
	 */
	List<TeamRelation> selectMemberListById(@Param("teamId") int teamId,@Param("memName") String memName);

	/**
	 * 判断人员是否关联小区
	 * 
	 * @param memberId
	 * @return
	 */
	int selectIfRelMember(@Param("memberId") int memberId,@Param("teamId") int teamId);

	/**
	 * 人员关联小区对应的房源数量
	 * 
	 * @param memberId
	 * @return
	 */
	int selectMemberHCnt(@Param("memberId") int memberId,@Param("teamId") int teamId);

	/**
	 * 根据传入的团队名称，模糊匹配出符合要求的团队名称和id
	 * 
	 * @param teamLevel
	 * @param teamName
	 * @return
	 */
	List<Map<String, Integer>> selectTeamIdList(@Param("teamName") String teamName);

	/**
	 * List<Map<String,Integer>> selectUserIdList(@Param("teamLevel") int
	 * teamLevel,@Param("userName") String userName);
	 *
	 **/

	/**
	 * 是否是团队负责人
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 */
	int selectCharge(@Param("userId") int userId, @Param("teamId") int teamId);

	/**
	 * 找出这个团队的父团队id
	 * 
	 * @param teamId
	 * @return
	 */
	Map<String, Object> selectParentTeamId(@Param("teamId") int teamId);

	/**
	 * 获取负责人所在团队的团队深度
	 * 
	 * @param userId
	 * @return
	 */
	int selectTeamLevel(@Param("userId") int userId);

	/**
	 * 根据leaderId查询查询其下子团队的id和leaderId
	 * 
	 * @param leaderId
	 * @return
	 */
	List<Map<String, Object>> selectSubTeamIdsAndLeaderIds(@Param("teamId") int teamId);

	/**
	 * 判断团队名称是否唯一
	 * 
	 * @param name
	 * @return
	 */
	int selectUniName(@Param("name") String name, @Param("teamId") int teamId);

	/**
	 * 判断是否是平台管理员
	 * 
	 * @param userId
	 * @return
	 */
	int selectPlatFormAdmin(@Param("userId") int userId);

	/**
	 * 插入团队记录
	 * 
	 * @param team
	 * @return
	 */
	int insertTeam(TeamBean team);

	/**
	 * 插入团队关联记录
	 * 
	 * @param teamRel
	 * @return
	 */
	int insertTeamRelation(TeamRelation teamRel);
	
	/**
	 * 新增团队负责人记录
	 * 
	 * @param teamLeader
	 * @return
	 */
	int insertTeamLeader(TeamLeaders teamLeader);

	/**
	 * 根据团队id，删除团队负责人记录
	 * 
	 * @param teamId
	 * @return
	 */
	int delTeamLeaderByTeamId(@Param("teamId") int teamId,@Param("userId") int userId);
	
	/**
	 * 团队关联小区
	 * 
	 * @param relArea
	 * @return
	 */
	int insertAreaRel(TeamRelHouse relArea);

	/**
	 * 人员关联小区
	 * 
	 * @param teamRel
	 * @return
	 */
	int insertMemberRelArea(TeamRelation teamRel);

	/**
	 * 删除团队信息
	 * 
	 * @param team
	 * @return
	 */
	int deleteTeam(TeamBean team);

	/**
	 * 删除团队成员
	 * 
	 * @param userId
	 * @param teamId
	 * @return
	 */
	int deleteTeamMember(@Param("userId") int userId, @Param("teamId") int teamId);

	/**
	 * 删除团队与小区关联
	 * 
	 * @param relArea
	 * @return
	 */
	int deleteAreaRel(TeamRelHouse relArea);

	/**
	 * 删除人员与小区关联
	 * 
	 * @param memberRel
	 * @return
	 */
	int deleteMemberRelAreaList(TeamRelation memberRel);

	/**
	 * 更新团队名称
	 * 
	 * @param team
	 * @return
	 */
	int updateTeamName(TeamBean team);

	/**
	 * 
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 */
	int updateTeamCharge(@Param("ifCharge") int ifCharge,@Param("teamId") int teamId,@Param("userId") int userId);
	
	/**
	 * 判断该人员是否在无主队中
	 * 
	 * @param userId
	 * @return
	 */
	int selectMemInNull(@Param("userId") int userId);
	
	/**
	 * 删除无主队中此人员的信息
	 * 
	 * @param userId
	 * @return
	 */
	int deleteMemInNull(@Param("userId") int userId);
	
	/**
	 * 判断该人员是否在其他团队中
	 * 
	 * @param teamId
	 * @param userId
	 * @return
	 */
	int selectMemInOther(@Param("teamId") int teamId,@Param("userId") int userId);

	/**
	 * 删除原负责人的团队管理菜单权限
	 * 
	 * @param userIds
	 * @return
	 */
	int deleteLeaderRole(@Param("userIds") String userIds);

	/**
	 * 新增新负责人的团队管理菜单权限
	 * 
	 * @param userId
	 * @return
	 */
	int insertLeaderRole(@Param("userId") String userId);

	/**
	 * 查询新负责人是否有团队管理菜单权限
	 * 
	 * @param userId
	 * @return
	 */
	int selectLeaderRole(@Param("userId") String userId);

	/**
	 * 根据用户id，判断当前负责人是否还是团队责任人
	 * @param userId
	 * @return
	 */
	int selectLaderCount(@Param("userId") String userId);
}
