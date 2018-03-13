package com.yc.rm.caas.appserver.bus.dao.flow;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.bus.controller.flow.fo.FlowFo;
import com.yc.rm.caas.appserver.model.flow.FlowBean;
import com.ycdc.appserver.bus.dao.flow.IFlowDao;

/**
 * 待办任务
 * @author suntf
 * @date 2016年12月26日
 */

@Component("_caasFlowDao")
public interface ICaasFlowDao
{
	/**
	 * @param fo
	 * @return
	 */
	List<FlowBean> getMyCreateWorkOrderList(FlowFo fo);

	/**
	 * @param fo
	 * @return
	 */
	List<FlowBean> getMyStepWorkOrderList(FlowFo fo);

	/**
	 * @return
	 */
	List<Map> orderStateList();

	/**
	 * @return
	 */
	List<Map> orderTypeList();
	
	
}
