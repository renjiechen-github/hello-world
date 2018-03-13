package com.room1000.cascading.service;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.room1000.cascading.dao.CascadingDao;
import com.ycdc.core.base.BaseService;

/**
 * 下拉框级联查询
 * 
 * @author sunchengming
 * @date 2017年4月14日
 * @version 1.0
 *
 */
@Service("cascadingService")
public class CascadingServiceImpl extends BaseService implements CascadingService
{

	/**
	 * dao
	 */
	@Resource
	private CascadingDao dao;

	@Override
	public Object groupList(HttpServletRequest request, HttpServletResponse response)
	{
		// 区域ID
		String areaId = getValue(request, "areaId");
		List<Map<String, String>> list = null;
		if (!"".equals(areaId))
		{
			list = dao.groupList(areaId);
		}
		if (null == list)
		{
			list = new ArrayList<>();
		}
		return list;
	}

	@Override
	public Object butlerList(HttpServletRequest request, HttpServletResponse response)
	{
		// 区域ID
		String areaId = getValue(request, "areaId");
		String rankId = getValue(request, "rankId");
		String groupId = getValue(request, "groupId");
		List<Map<String, String>> list = null;
		list = dao.butlerList(areaId, rankId, groupId);
		if (null == list)
		{
			list = new ArrayList<>();
		} else
		{
			if (!rankId.equals(""))
			{
				list = list.subList(0, 1);
			}

			if (!areaId.equals(""))
			{
				// 排序：按照姓氏首字母排列
				Collections.sort(list, new Comparator<Map<String, String>>()
				{
					public int compare(Map<String, String> o1, Map<String, String> o2)
					{
						String name1 = String.valueOf(o1.get("name"));
						String name2 = String.valueOf(o2.get("name"));
						Collator instance = Collator.getInstance(Locale.CHINA);
						return instance.compare(name1, name2);
					}
				});
			}
		}

		// 查询所有用户，拼接到指定用户的后面，并且需要根据指定用户去重数据
		List<Map<String, String>> allList = dao.getUserAll();

		if (null != list)
		{
			for (Map<String, String> mapList : list)
			{
				String id = String.valueOf(mapList.get("id"));
				Iterator<Map<String, String>> it = allList.iterator();
				while (it.hasNext())
				{
					Map<String, String> itMap = it.next();
					String userId = String.valueOf(itMap.get("id"));
					if (id.equals(userId))
					{
						it.remove();
					}
				}
			}
		}

		logger.info("allList == " + allList);

		// 排序：按照姓氏首字母排列
		Collections.sort(allList, new Comparator<Map<String, String>>()
		{
			public int compare(Map<String, String> o1, Map<String, String> o2)
			{
				String name1 = String.valueOf(o1.get("name"));
				String name2 = String.valueOf(o2.get("name"));
				Collator instance = Collator.getInstance(Locale.CHINA);
				return instance.compare(name1, name2);
			}
		});

		if (null == list)
		{
			list = new ArrayList<>();
		}
		list.addAll(allList);

		logger.info("list = " + list);
		return list;
	}

	@Override
	public Object recommendInfo(HttpServletRequest request, HttpServletResponse response)
	{
		// 任务编码
		String code = getValue(request, "code");
		Map<String, Object> map = dao.recommendInfo(code);
		// 判断是否为空
		if (null == map || map.size() == 0)
		{
			map = new HashMap<>();
			map.put("id", "");
			return map;
		}

		String id = String.valueOf(map.get("id"));
		if ("null".equals(id) || "".equals(id))
		{
			map = new HashMap<>();
			map.put("id", "");
			return map;
		}

		return map;
	}

	@Override
	public Object getCallBackInfo(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, String> result = new HashMap<>();
		// 查询第三方号码和回调地址
		List<Map<String, String>> list = dao.getCallBackInfo();
		for (Map<String, String> map : list)
		{
			String key = String.valueOf(map.get("key"));
			String value = String.valueOf(map.get("value"));
			if ("CALL_BACK_URL".equalsIgnoreCase(key))
			{
				result.put("callBackUrl", value);
			}
			if ("BIND_NBR".equalsIgnoreCase(key))
			{
				result.put("bindNbr", value);
			}
		}
		return result;
	}

	@Override
	public Object getUserListByAuthority(HttpServletRequest request, HttpServletResponse response)
	{
		String roleId = req.getAjaxValue(request, "roleId");
		String type = req.getAjaxValue(request, "type");
		List<Map<String, String>> list = dao.getUserListByAuthority(roleId, type);

		if (list != null)
		{
			// 排序：按照姓氏首字母排列
			Collections.sort(list, new Comparator<Map<String, String>>()
			{
				public int compare(Map<String, String> o1, Map<String, String> o2)
				{
					String name1 = String.valueOf(o1.get("name"));
					String name2 = String.valueOf(o2.get("name"));
					Collator instance = Collator.getInstance(Locale.CHINA);
					return instance.compare(name1, name2);
				}
			});
		}

		// 查询所有用户，拼接到指定用户的后面，并且需要根据指定用户去重数据
		List<Map<String, String>> allList = dao.getUserAll();

		if (null != list)
		{
			for (Map<String, String> mapList : list)
			{
				String id = String.valueOf(mapList.get("id"));
				Iterator<Map<String, String>> it = allList.iterator();
				while (it.hasNext())
				{
					Map<String, String> itMap = it.next();
					String userId = String.valueOf(itMap.get("id"));
					if (id.equals(userId))
					{
						it.remove();
					}
				}
			}
		}

		// 排序：按照姓氏首字母排列
		Collections.sort(allList, new Comparator<Map<String, String>>()
		{
			public int compare(Map<String, String> o1, Map<String, String> o2)
			{
				String name1 = String.valueOf(o1.get("name"));
				String name2 = String.valueOf(o2.get("name"));
				Collator instance = Collator.getInstance(Locale.CHINA);
				return instance.compare(name1, name2);
			}
		});

		if (null == list)
		{
			list = new ArrayList<>();
		}
		list.addAll(allList);

		return list;
	}

}
