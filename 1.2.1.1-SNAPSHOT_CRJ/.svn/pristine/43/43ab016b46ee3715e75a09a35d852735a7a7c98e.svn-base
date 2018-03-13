package com.room1000.workorder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.room1000.core.mybatis.SuperMapper;
import com.room1000.timedtask.dto.Staff4OrderCountDto;
import com.room1000.workorder.dto.TypeCountDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.dto.request.QryWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.TeamWorkOrderPagerListRequest;
import com.yc.rm.caas.appserver.model.user.User;

/**
 * 
 * Description:
 * 
 * Created on 2017年6月1日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface WorkOrderDtoMapper extends SuperMapper
{
	/**
	 * Description: deleteByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param id
	 *          id
	 * @return int int<br>
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * Description: insert<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int insert(WorkOrderDto record);

	/**
	 * Description: insertSelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int insertSelective(WorkOrderDto record);

	/**
	 * Description: selectByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param id
	 *          id
	 * @return WorkOrderDto WorkOrderDto<br>
	 * @mbg.generated
	 */
	WorkOrderDto selectByPrimaryKey(Long id);

	/**
	 * Description: updateByPrimaryKeySelective<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(WorkOrderDto record);

	/**
	 * Description: updateByPrimaryKey<br>
	 *
	 * @author jinyanan <br>
	 * @param record
	 *          record
	 * @return int int<br>
	 * @mbg.generated
	 */
	int updateByPrimaryKey(WorkOrderDto record);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param code
	 *          code
	 * @return WorkOrderDto
	 */
	WorkOrderDto selectByCode(String code);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param id
	 *          id
	 * @return WorkOrderDto
	 */
	WorkOrderDto selectDetailById(Long id);

	/**
	 * 
	 * Description: 根据条件查询所有订单
	 * 
	 * @author jinyanan
	 *
	 * @param req
	 *          req
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> selectByReqCond(QryWorkOrderPagerListRequest req);

	/**
	 * 
	 * Description: 查询超时未评价的订单
	 * 
	 * @author jinyanan
	 *
	 * @param overtime
	 *          超时的期限
	 * @return List<WorkOrderDto>
	 */
	List<WorkOrderDto> selectOvertimeCommentWorkOrder(Long overtime);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param object
	 *          object
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> selectByCond(Object object);

	List<Map<String, String>> selectTeamTask(Object object);

	/**
	 * 
	 * Description:
	 * 
	 * @author jinyanan
	 *
	 * @param object
	 *          object
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> select4CustomerService(Object object);

	/**
	 * 
	 * Description: 查询各个订单的数量
	 * 
	 * @author jinyanan
	 *
	 * @return List<TypeCountDto>
	 */
	List<TypeCountDto> selectTypeCount();

	/**
	 * 
	 * Description: 查询各个员工名下待处理的任务数量
	 * 
	 * @author jinyanan
	 *
	 * @return List<Staff4OrderCountDto>
	 */
	List<Staff4OrderCountDto> selectStaff4OrderCount();

	/**
	 * 查询超长时间界限节点
	 * 
	 * @return
	 */
	String queryTimeCode();
	
	/**
	 * 
	 * @return
	 */
	String querySendHousekeeperDealWithTimeCode();

	/**
	 * 根据用户id，获取所在团队的负责人和父团队
	 * 
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> getTeamLeaderInfo(@Param(value = "user_id") String id);

	/**
	 * 根据团队id，获取父团队信息
	 * 
	 * @param parent_teamid
	 * @return
	 */
	List<Map<String, Object>> getTeamParInfo(@Param(value = "team_id") String parent_teamid);

	/**
	 * 根据用户id列表，获取所有的角色id
	 * 
	 * @param idStr
	 * @return
	 */
	String getRoleIds(@Param(value = "idStr") String idStr);

	List<WorkOrderDto> selectTeamTaskByCon(TeamWorkOrderPagerListRequest req);

	/**
	 * 根据团队列表获取人员
	 * 
	 * @param teamIds
	 * @return
	 */
	List<User> getUserIdsByTeamIds(@Param(value = "teamIds") String teamIds);

	/**
	 * 根据团队列表获取负责人
	 * 
	 * @param teamIds
	 * @return
	 */
	List<Map<String, Object>> getLeaderIdsByTeamIds(@Param(value = "teamIds") String teamIds);

	/**
	 * 根据工单id，更改工单处理人
	 * 
	 * @param map
	 * @return
	 */
	int updateWorkOrderButlerById(Map<String, Object> map);

	/**
	 * 根据用户id，获取是否是平台管理员
	 * 
	 * @param user_id
	 * @return
	 */
	int getManagerInfo(@Param(value = "id") String user_id);

	List<Map<String, Object>> queryHousekeeperDealWithInfo(@Param(value = "time") String time);
}