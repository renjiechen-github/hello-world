/**
 * 
 */
package com.yc.rm.caas.appserver.base.office.datacenter.serv;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yc.rm.caas.appserver.base.office.datacenter.dao.IDataCenterDao;
import com.yc.rm.caas.appserver.base.office.datacenter.entity.DataRptCfgBean;
import com.yc.rm.caas.appserver.base.support.BaseService;

/**
 * @author stephen
 * 
 */
@Service("_dataCenterServImpl")
public class DataCenterServImpl extends BaseService implements IDataCenterServ
{
	private static int MONTH = 0;
	@Resource
	private IDataCenterDao _dataCenterDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yc.rm.caas.appserver.base.office.datacenter.serv.IDataCenterServ#
	 * getRptCfgList()
	 */
	@Override
	public List<DataRptCfgBean> getRptCfgList()
	{
		// 判断当前时间点该取哪些报表
		Date date = new Date();
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int day = cale.get(Calendar.DAY_OF_MONTH); // 1==1号
		int weekday = cale.get(Calendar.DAY_OF_WEEK);// 6==周五
		List<DataRptCfgBean> monthlist = null;
		List<DataRptCfgBean> weeklist = null;
		List<DataRptCfgBean> list = new ArrayList<DataRptCfgBean>();
		// 取日期条件符合的bean全集
		weeklist = _dataCenterDao.selectRptCfg(weekday);
		if (weeklist != null && weeklist.size() > 0)
		{
			list.addAll(weeklist);
		}
		if (day == 1)
		{
			monthlist = _dataCenterDao.selectRptCfg(MONTH);
			if (monthlist != null && monthlist.size() > 0)
			{
				list.addAll(monthlist);
			}
		}
		return list;
	}

	public static void main(String[] args)
	{
		Date date = new Date();
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int day = cale.get(Calendar.DAY_OF_MONTH);
		int weekday = cale.get(Calendar.DAY_OF_WEEK);

		System.out.println(day);
		System.out.println(weekday);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.yc.rm.caas.appserver.base.office.datacenter.serv.IDataCenterServ#
	 * getRptInfo(int)
	 */
	@Override
	public List<Map<String, Object>> getRptInfo(int id, int teamid)
	{
		// TODO Auto-generated method stub
		return _dataCenterDao.selectDataRpt(id, teamid);
	}

}
