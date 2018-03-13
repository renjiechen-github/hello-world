
package com.ycdc.appserver.bus.dao.flow;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 待办任务
 * 
 * @author suntf
 * @date 2016年12月26日
 */
public interface IFlowDao
{

	List<Map<String, String>> loadTaskType(@Param("super_id") String super_id);

	List<Map<String, String>> loadAreaList(@Param("super_id") String super_id);

	List<Map<String, String>> myStartTask(@Param("keyWord") String keyWord,
			@Param("state") String state, @Param("typeId") String typeId,
			@Param("startPage") int startPage, @Param("pageSize") int pageSize,
			@Param("operId") String operId, @Param("area_id") String area_id,
			@Param("trading") String trading, @Param("typeids") String typeids);

	List<Map<String, String>> disposetTask(@Param("keyWord") String keyWord,
			@Param("state") String state, @Param("typeId") String typeId,
			@Param("startPage") int startPage, @Param("pageSize") int pageSize,
			@Param("operId") String operId, @Param("rule") String rule,
			@Param("org") String org, @Param("g_id") String g_id,
			@Param("area_id") String area_id, @Param("trading") String trading,
			@Param("typeids") String typeids, @Param("read_all") String read_all);

	List<Map<String, String>> yetTaskList(@Param("keyWord") String keyWord,
			@Param("state") String state, @Param("typeId") String typeId,
			@Param("startPage") int startPage, @Param("pageSize") int pageSize,
			@Param("operId") String operId, @Param("area_id") String area_id,
			@Param("trading") String trading, @Param("typeids") String typeids);
}
