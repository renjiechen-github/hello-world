/**
 * 
 */
package pccom.web.mobile.service.house;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import pccom.web.server.BaseService;

/**
 * 房源管理
 * @author suntf
 * @date 2016年9月6日
 */
@Service("miBaseHouseService")
public class MiBaseHouseService extends BaseService
{
	public Object getHouseList(HttpServletRequest request)
	{
		String keyword = req.getAjaxValue(request, "keyword");
		String areaid = req.getAjaxValue(request, "areaid"); 
		String status = req.getAjaxValue(request, "status");
		String publish = req.getValue(request, "publish"); // 发布状态
		String isSelf = req.getValue(request, "isSelf"); // 看自己的
		String kzhouse = req.getValue(request, "kzhouse"); // 空置房源
		String trading = req.getValue(request, "trading"); // 商圈
		String sql = getSql("basehouse.getHouseList.selectMainbg") + getSql("basehouse.getHouseList.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(areaid))
		{
			sql += getSql("basehouse.getHouseList.areaid");
			params.add(areaid);
		}
		if (!"".equals(keyword)) 
		{
			sql += getSql("basehouse.getHouseList.keyword");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
		}
		if (!"".equals(status)) 
		{
			if("1".equals(status))
			{
				sql += getSql("basehouse.getHouseList.newStatus").replace("####", " in (0,1,2,3)");
			}
			else if("2".equals(status))
			{	
				sql += getSql("basehouse.getHouseList.newStatus").replace("####", " in (4,5)");
			}
			else
			{	
				sql += getSql("basehouse.getHouseList.status");
				params.add(status);
			}
		}
		
		if(!"".equals(trading))
		{
			sql += getSql("basehouse.getHouseList.trading");
			params.add(trading);
		}
		
		if("1".equals(isSelf))
		{
			sql += getSql("basehouse.getHouseList.user_id");
			params.add(this.getUser(request).getId());
		}
		
		if (!"".equals(publish))
		{
			sql += getSql("basehouse.getHouseList.publish");
			params.add(publish);
		}
		sql += getSql("basehouse.getHouseList.groupBy") +  getSql("basehouse.getHouseList.selectMainend");
		if("1".equals(kzhouse))
		{
			sql += getSql("basehouse.getHouseList.kzhouse");
		}
		sql += getSql("basehouse.getHouseList.orderBy");
		logger.debug(str.getSql(sql, params.toArray()));
		return this.getMobileList(request, sql, params);
	}
	
	/**
	 * 加载小区信息
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年9月7日
	 */
	public List<?> loadAearList(HttpServletRequest request)
	{
		String keyword = req.getAjaxValue(request, "keyword");
		String areaid = req.getAjaxValue(request, "areaid"); 
		String sql = getSql("group.query.main") + getSql("group.query.status");
		List<String> params = new ArrayList<String>();
		params.add("1");
		if (!"".equals(areaid)) 
		{
			sql += getSql("group.query.areaid");
			params.add(areaid);
		}
		if (!"".equals(keyword)) 
		{
			sql += getSql("group.query.groupname");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
		}
		sql += getSql("group.query.orderby");
		logger.debug(str.getSql(sql, params.toArray()));
		return this.getMobileList(request, sql, params);
	}
}
