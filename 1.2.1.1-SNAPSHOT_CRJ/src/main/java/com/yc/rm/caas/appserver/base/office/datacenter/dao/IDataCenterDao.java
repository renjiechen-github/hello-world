/**
 * 
 */
package com.yc.rm.caas.appserver.base.office.datacenter.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yc.rm.caas.appserver.base.office.datacenter.entity.DataRptCfgBean;

/**
 * @author stephen
 * 
 */
public interface IDataCenterDao {
	public List<Map<String, Object>> selectDataRpt(@Param(value = "id")int id,@Param(value = "teamid")int teamid);

	/**
	 * @param types
	 */
	public List<DataRptCfgBean> selectRptCfg(@Param(value = "type") int type);
}
