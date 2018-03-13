package com.ycdc.core.plugin.lbs;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ycdc.core.plugin.lbs.dao.PlaceSearchDAO;

/**
 * 百度 Place圆形区域检索
 * 
 * @author 孙诚明
 * @date 2017年2月22日
 */
@Component("placeSearch")
public class PlaceSearch
{
	// 日志
	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(PlaceSearch.class);

	@Autowired
	private PlaceSearchDAO dao;

	/**
	 * 访问应用（AK）
	 */
	private static String ak = "GPORnUbFexQSIphkUiVjOeh11zwBnoYa";

	/**
	 * 地址到经纬度坐标URL
	 */
	// private static String geocoderUrl =
	// "http://api.map.baidu.com/geocoder/v2/";
	private static String geocoderUrl = "http://api.map.baidu.com/place/v2/search";

	/**
	 * 某个区域的某类POI数据URL
	 */
	private static String placeUrl = "http://api.map.baidu.com/place/v2/search";

	/**
	 * 全量更新小区交通信息
	 */
	public void getRrafficInfoByAll()
	{
		// 获取所有小区信息
		List<Map<String, Object>> groups = dao.getGroupByAll();

		// 遍历小区信息
		for (Map<String, Object> map : groups)
		{
			// 根据小区信息，获取周边交通,并更新数据库
			getRrafficJson(map);
		}
	}

	/**
	 * 根据小区ID，获取小区周边交通信息
	 * 
	 * @param id
	 *          小区ID
	 * @return
	 */
	public String getRrafficInfoById(String id)
	{
		// 根据小区ID，获取小区信息
		Map<String, Object> map = dao.getGroupById(id);

		// 根据小区信息，获取周边交通
		String resultJson = getRrafficJson(map);

		return resultJson;
	}

	/**
	 * 全量更新小区坐标
	 */
	public void updateLocationByAll()
	{
		// 查询所有小区信息
		List<Map<String, Object>> groupNames = dao.getGroupByAll();

		// 调取百度接口，获取经纬度坐标
		for (Map<String, Object> map : groupNames)
		{
			// 根据小区名称，获取小区坐标,并更新入库
			getLocationByName(map);
		}
	}

	/**
	 * 根据小区ID，更新小区坐标
	 * 
	 * @param id
	 *          小区ID
	 * @return
	 */
	public String updateLocationById(String id)
	{
		// 根据小区ID，获取小区信息
		Map<String, Object> map = dao.getGroupById(id);

		// 根据小区名称，获取小区坐标,并更新入库
		String location = getLocationByName(map);

		return location;
	}

	/**
	 * 根据小区名称，获取小区坐标
	 * 
	 * @param map
	 * @return
	 */
	private String getLocationByName(Map<String, Object> map)
	{
		// 拼装URL
		String url = geocoderUrl + "?q=" + map.get("group_name") + "&region=南京&output=json&ak=" + ak;
		LOG.info("小区坐标URL = " + url);
		String location = "";
		try
		{
			// 返回结果
			String result = getInfoByHttp(url);
			// 解析JSON字符串
			location = dealJsonByGeocoder(result).trim();
			map.put("location", location);
			// 根据小区ID，更新小区坐标
			dao.updateLocationById(map);
		} catch (Exception e)
		{
			e.printStackTrace();
			return location;
		}

		return location;
	}

	/**
	 * 根据小区信息，获取周边交通,并更新数据库
	 * 
	 * @param map
	 *          小区信息
	 * @return
	 */
	private String getRrafficJson(Map<String, Object> map)
	{
		String resultJson = "";
		// 小区ID
		String id = String.valueOf(map.get("id"));
		// 小区坐标
		String location = String.valueOf(map.get("coordinate"));
		// 小区名称
		String groupName = String.valueOf(map.get("group_name"));

		if (!location.equals("") && !location.equals("null"))
		{
			// 处理坐标
			String[] array = location.split(",");
			location = array[1] + "," + array[0];

			if (location != null && !"".equals(location) && !"null".equals(location))
			{
				// 获取公交信息
				Set<String> busInfo = getRrafficInfo(location, groupName, "公交");

				// 获取地铁信息
				Set<String> metroInfo = getRrafficInfo(location, groupName, "地铁");

				// 按照交通分类，处理交通信息为JSON字符串
				resultJson = dealRrafficInfo(busInfo, metroInfo, groupName);
			}
		}

		// 根据小区ID，更新公交信息
		dao.updateRrafficById(id, resultJson);
		return resultJson;
	}

	/**
	 * 处理交通信息
	 * 
	 * @param busInfo
	 *          公交信息
	 * @param metroInfo
	 *          地铁信息
	 * @param groupName
	 *          小区名称
	 * @return
	 */
	private String dealRrafficInfo(Set<String> busInfo, Set<String> metroInfo, String groupName)
	{
		String resultStr = null;

		Map<String, Object> result = new LinkedHashMap<>();

		Map<String, Object> metro = null;

		if ((null == metroInfo || metroInfo.size() == 0) && (null == busInfo || busInfo.size() == 0))
		{
			resultStr = "";
		} else
		{
			// 地铁信息
			List<Map<String, Object>> metroList = new ArrayList<>();
			if (null != metroInfo && metroInfo.size() > 0)
			{
				for (String str : metroInfo)
				{
					metro = new LinkedHashMap<>();
					String[] array = str.split("-");
					// 交通信息
					String metroStr = array[0];
					// 站名
					String name = array[1];
					// 距离
					String distance = array[2];
					metro.put(metroStr, name);
					metro.put("distance", distance);
					metroList.add(metro);
				}
			}
			result.put("metro", metroList);

			// 公交信息
			List<Map<String, Object>> busList = new ArrayList<>();
			Map<String, Object> bus = null;
			if (null != busInfo && busInfo.size() > 0)
			{
				for (String str : busInfo)
				{
					bus = new LinkedHashMap<>();
					String[] array = str.split("-");
					// 交通信息
					String busStr = array[0];
					// 站名
					String name = array[1];
					// 距离
					String distance = array[2];

					bus.put(busStr, name);
					bus.put("distance", distance);
					busList.add(bus);
				}
			}
			result.put("bus", busList);
		}

		if (result.size() > 0)
		{
			resultStr = JSON.toJSONString(result);
		}
		LOG.info("需要返回的交通信息= " + resultStr);
		return resultStr;
	}

	/**
	 * 根据经纬度，获取公交信息
	 * 
	 * @param location
	 *          经纬度
	 * @param groupName
	 *          小区名称
	 * @param query
	 *          获取的信息
	 * @return
	 */
	private Set<String> getRrafficInfo(String location, String groupName, String query)
	{
		// 百度接口URL
		StringBuffer sb = new StringBuffer();
		// 如果不为空，则调用百度接口，获取交通信息
		sb.append(placeUrl).append("?query=" + query + "&scope=2&output=json").append("&location=" + location).append("&radius=2000&filter=sort_name:distance|sort_rule:1")
				.append("&ak=" + ak);

		LOG.info("交通信息URL=" + sb.toString());

		// 存放交通信息
		Set<String> set = null;
		try
		{
			// 返回结果
			String result = getInfoByHttp(sb.toString());
			// 解析JSON字符串，获取交通信息
			set = dealJSONBySearch(result);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return set;
	}

	/**
	 * 解析交通信息
	 * 
	 * @return
	 */
	private Set<String> dealJSONBySearch(String jsonStr)
	{
		// JSON字符串转换成集合
		Map<String, String> map = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>()
		{
		});

		// 存放解析后数据
		Set<String> set = new HashSet<>();

		// 临时表
		Set<String> temporarySet = new HashSet<>();

		if ("0".equals(map.get("status")))
		{
			// 返回正确
			List<Map<String, String>> results = JSON.parseObject(map.get("results"), new TypeReference<List<Map<String, String>>>()
			{
			});

			// 去重复
			for (Map<String, String> resultMap : results)
			{
				// 获取交通信息
				String address = resultMap.get("address");
				// 站台名称
				String name = resultMap.get("name");
				// 获取距离
				Map<String, String> detail_info = JSON.parseObject(resultMap.get("detail_info"), new TypeReference<Map<String, String>>()
				{
				});
				String distance = detail_info.get("distance");

				if (null != address && !address.equals("null") && !address.equals(""))
				{
					String[] arry = address.split(";");
					for (String string : arry)
					{
						String str = string + "-" + name;
						if (!"".equals(str))
						{
							// 如果临时表中不存在数据
							if (!temporarySet.contains(str))
							{
								// 入临时表
								temporarySet.add(str);
								str += "-" + distance;
								set.add(str);
							}
						}
					}
				}
			}
		}
		return set;
	}

	/**
	 * HTTP调取百度接口，获取信息
	 * 
	 * @return 小区坐标
	 */
	private String getInfoByHttp(String url) throws Exception
	{
		URL detail_url = new URL(url);
		URI uri = new URI(detail_url.getProtocol(), detail_url.getHost(), detail_url.getPath(), detail_url.getQuery(), null);
		String result = "";
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build(); CloseableHttpResponse httpResponse = httpClient.execute(new HttpGet(uri)))
		{
			// 设置网站编码并爬取网站信息
			result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		} catch (Exception e)
		{
			LOG.error("访问百度接口错误=" + e);
		}
		return result;
	}

	/**
	 * 解析坐标JSON字符串
	 * 
	 * @param jsonStr
	 *          需要解析的坐标JSON字符串
	 * @return 坐标字符串
	 * @throws Exception
	 */
	private String dealJsonByGeocoder(String jsonStr) throws Exception
	{
		String location = "";
		if (!jsonStr.equals(""))
		{
			Map<String, Object> listMap = JSON.parseObject(jsonStr, new TypeReference<Map<String, Object>>()
			{
			});
			String status = String.valueOf(listMap.get("status"));
			if ("0".equals(status))
			{
				// 返回正确
				List<Map<String, String>> results = JSON.parseObject(String.valueOf(listMap.get("results")), new TypeReference<List<Map<String, String>>>()
				{
				});

				if (null != results && results.size() > 0)
				{
					String loc = String.valueOf(results.get(0).get("location"));
					if (!loc.equals("null") && !"".equals(loc))
					{
						Map<String, String> resultMap = JSON.parseObject(loc, new TypeReference<Map<String, String>>()
						{
						});

						if (null != resultMap && resultMap.size() > 0)
						{
							String lng = String.valueOf(resultMap.get("lng"));
							String lat = String.valueOf(resultMap.get("lat"));
							location = lng + "," + lat;
						}
					}
				}

			}
		}
		LOG.info("location = " + location);
		return location;
	}
}
