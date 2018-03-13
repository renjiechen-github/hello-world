package com.ycdc.nbms.report.serv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.report.dao.HouseInfoDao;
import com.ycdc.util.page.PageResultDealInterface;

/**
 * 房源信息查询
 * 
 * @author 孙诚明
 * @version 1.0
 * @since 2016年12月15日
 * @category com.ycdc.nbms.report.serv
 */
@Service("houseInfoServ")
public class HouseInfoServImpl extends BaseService implements HouseInfoServ
{
	@Resource
	private HouseInfoDao dao;
	
	private static Map<String, String> sourceMap = new HashMap<String, String>();
	
	static
	{
		sourceMap.put("1", "house365");
		sourceMap.put("2", "58同城");
	}

	/**
	 * 按照条件查询房源信息
	 * 
	 * @param request
	 * @param response
	 */
	@Override
	public void queryHouseInfo(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,Object> map = new HashMap<String,Object>();
		
		// 区域ID
		String areaId = req.getAjaxValue(request, "areaId");
		// 区域名称
		String area = "";
		if (StringUtils.isNotBlank(areaId))
		{
			// 通过ID查询区域名称
			area = dao.getAreaById(areaId);
		}
		map.put("area", area);
		// 房源来源
		String source = req.getAjaxValue(request, "source");
		map.put("source", source);
		// 商圈
		String businessCenter = req.getAjaxValue(request, "businessCenter");
		map.put("businessCenter", businessCenter);
		// 使用类型
		String useType = req.getAjaxValue(request, "useType");
		map.put("useType", useType);
		// 开始租金
		String beginPrice = req.getAjaxValue(request, "beginPrice");
		map.put("beginPrice", beginPrice);
		// 结束租金
		String endPrice = req.getAjaxValue(request, "endPrice");
		map.put("endPrice", endPrice);
		// 城市
		String cityId = req.getAjaxValue(request, "cityId");
		map.put("cityId", cityId);
		
		getPageMapList(request, response, dao, "queryHouseInfo", map,new PageResultDealInterface()
		{
			@Override
			public List<Map<String, Object>> deal(List<Map<String, Object>> list)
			{
				for (Map<String, Object> map : list)
				{
					map.put("source", sourceMap.get(String.valueOf(map.get("source"))));
				}
				
				return list;
			}
		});

	}

}
