package com.yc.rm.caas.appserver.bus.service.flow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yc.rm.caas.appserver.base.support.BaseService;
import com.yc.rm.caas.appserver.bus.controller.flow.fo.FlowFo;
import com.yc.rm.caas.appserver.bus.dao.flow.ICaasFlowDao;
import com.yc.rm.caas.appserver.model.flow.FlowBean;

/**
 * @author suntf
 * @date 2016年12月26日
 */
@Service("_flowServImpl")
public class FlowServImpl extends BaseService implements IFlowServ {

	@Autowired
	private ICaasFlowDao _caasFlowDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yc.rm.caas.appserver.bus.service.flow.MFlowService#getWorkOrderList(com
	 * .alibaba.fastjson.JSONObject)
	 */
	@Override
	public List<FlowBean> getWorkOrderList(FlowFo fo) {
		List<FlowBean> retList = new ArrayList<FlowBean>();
		List<FlowBean> myCreate = null;
		if (fo.isMyCreate()) {
			myCreate = _caasFlowDao.getMyCreateWorkOrderList(fo);
		}
		List<FlowBean> myStep = null;
		if (fo.isMyStep()) {
			myStep = _caasFlowDao.getMyStepWorkOrderList(fo);
			if (myStep != null && myStep.size() > 0) {
				for (int i = 0; i < myStep.size(); i++) {
					myStep.get(i).setCanOper(true);
				}
			}
		}
		retList.addAll(myCreate);
		retList.addAll(myStep);
		return retList;
	}

	/* (non-Javadoc)
	 * @see com.yc.rm.caas.appserver.bus.service.flow.MFlowService#orderStateList()
	 */
	@Override
	public Object orderStateList() {
		List<Map> ret = _caasFlowDao.orderStateList();
		return ret;
	}

	/* (non-Javadoc)
	 * @see com.yc.rm.caas.appserver.bus.service.flow.MFlowService#orderTypeList()
	 */
	@Override
	public Object orderTypeList() {
		List<Map> ret = _caasFlowDao.orderTypeList();
		return ret;
	}
}
