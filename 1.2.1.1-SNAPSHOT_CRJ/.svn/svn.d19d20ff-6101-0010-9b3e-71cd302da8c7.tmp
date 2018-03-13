/*
 * Copyright (c) 2014  . All Rights Reserved.
 */
package pccom.web.server.house.group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pccom.common.util.Batch;
import pccom.common.util.FtpUtil;
import pccom.web.beans.SystemConfig;
import pccom.web.server.BaseService;

@Service("groupService")
public class GroupService extends BaseService {
	/**
	 * 小区获取列表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String group_type = req.getAjaxValue(request, "group_type");
		String areaid = req.getAjaxValue(request, "areaid");
		String group_name = req.getAjaxValue(request, "group_name");
		String	status = req.getAjaxValue(request, "status");
		String flag = req.getValue(request, "flag"); // 1 选择小区打开
		String sql = getSql("group.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(group_type)) {
			sql += getSql("group.query.type");
			params.add(group_type);
		}
		if("1".equals(flag))
		{
			status = "1";
		}
		if (!"".equals(status)) {
			sql += getSql("group.query.status");
			params.add(status);
		}
		if (!"".equals(areaid)) {
			sql += getSql("group.query.areaid");
			params.add(areaid);
		}
		if (!"".equals(group_name)) {
			sql += getSql("group.query.groupname");
			params.add("%" + group_name + "%");
			params.add("%" + group_name + "%");
			params.add("%" + group_name + "%");
			params.add("%" + group_name + "%");
			params.add("%" + group_name + "%");
		}
		
		getPageList(request,response,sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_GROUP_HTTP_PATH")),params.toArray(), "group.query.orderby");
	}

	/**
	 * 小区获取对应房源关系
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getRoom(HttpServletRequest request,
			HttpServletResponse response) {
		String group_id = req.getAjaxValue(request, "group_id");
		String bedroom = req.getAjaxValue(request, "bedroom");
		String spec1 = req.getAjaxValue(request, "spec1");
		
		String sql = getSql("grouproom.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(group_id)) {
			sql += getSql("grouproom.query.group_id");
			params.add(group_id);
		}
		if (!"".equals(bedroom)) {
			sql += getSql("grouproom.query.bedroom");
			params.add(bedroom);
		}
		if (!"".equals(spec1)) {
			sql += getSql("grouproom.query.spec1");
			params.add(spec1);
		}
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(
				sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")
						+ SystemConfig.getValue("FTP_GROUP_HTTP_PATH")),
				params.toArray())));
		return returnMap;
	}
	
	/**
	 * 小区获取对应房源报表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object HouseFlow(HttpServletRequest request,
			HttpServletResponse response) {
		String group_id = req.getAjaxValue(request, "group_id");
		String sql = getSql("group.queryHouseFlow");

		//returnMap.put("list",JSONArray.fromObject(db.queryForList(sql, new Object[] {group_id, group_id, group_id, group_id })));
		Map<String, Object> returnMap = db.queryForMap(sql,new Object[] {group_id,group_id,group_id,group_id,group_id,group_id});
		return returnMap;
	}

	/**
	 * 小区修改
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public Object groupUpdate(final HttpServletRequest request,HttpServletResponse response) throws IOException 
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
				{
			@Override
			public Integer execute() throws Exception 
			{
				String id = req.getAjaxValue(request, "group_id");
				String group_type = req.getAjaxValue(request, "type");
				String group_name = req.getAjaxValue(request, "name");
				String group_desc = req.getAjaxValue(request, "desc");
				String traffic = req.getAjaxValue(request, "traffic");
				String developer = req.getAjaxValue(request, "developer");
				String property = req.getAjaxValue(request, "property");
				String build_date = req.getAjaxValue(request, "buildDate");
				String area = req.getAjaxValue(request, "area");
				String areaid = req.getAjaxValue(request, "areaid");
				String plot_ratio = req.getAjaxValue(request, "plotratio");
				String house_count = req.getAjaxValue(request, "houseCount");
				String green_rate = req.getAjaxValue(request, "greenRate");
				String park_count = req.getAjaxValue(request, "parkCount");
				String images = req.getAjaxValue(request, "images");
				String coordinate = req.getAjaxValue(request, "coordinate");
				String address = req.getAjaxValue(request, "address");
				String trading = req.getAjaxValue(request, "trading");
				String roomspec = req.getAjaxValue(request, "roomspec");
				String roomcount = req.getAjaxValue(request, "roomcount");
				String roomcost = req.getAjaxValue(request, "roomcost");
				String roomimage = req.getAjaxValue(request, "roomimage");
				String roomspec1 = req.getAjaxValue(request, "roomspec1");
				String roomarea = req.getAjaxValue(request, "roomarea");
				String sql = getSql("group.insert");
				int keyId;
				keyId = this.insert(sql, new Object[] { areaid, trading,
						group_type, group_name, group_desc, traffic, developer,
						property, build_date, area, plot_ratio, house_count,
						green_rate, park_count, coordinate, 1, address, oper,
						2, id });
				if (keyId != -2) 
				{
					String urlPath="";
					Map<String,String >  newPath = ImageWork(images, keyId, request);
					if ( "1".equals(str.get(newPath, "state")))
					{
						urlPath=str.get(newPath, "newPath");
					}
					else
					{
						logger.debug("图片上传失败");
						throw new Exception("网络图片上传失败");
						//return Integer.parseInt(str.get(newPath, "state"));
					}
					// 小区对应户型价格房源
					String insertroom = getSql("group.insertroom");
					String[] spec = roomspec.split(",");
					//String[] count = roomcount.split(",");
					String[] cost = roomcost.split(",");
					String[] roompic = roomimage.split("\\|");
					String[] spec1 = roomspec1.split(",");
					String[] specarea = roomarea.split(",");
					for (int i = 0; i < spec.length; i++) 
					{
						if (spec[i] != null && !"".equals(spec[i])) 
						{
							int roomKeyId = this.insert(insertroom,new Object[] { keyId, spec[i],0,cost[i],spec1[i],specarea[i] });
								String roomPath	="";	
								if (!"0".equals(roompic[i]))
								{
									Map<String,String > roomnewPath = ImageWork(roompic[i],roomKeyId, request);
									if ( "1".equals(str.get(roomnewPath, "state")))
									{
										roomPath=str.get(roomnewPath, "newPath");
									}
									else
									{
										//return Integer.parseInt(str.get(roomnewPath, "state"));
										logger.debug("图片上传失败");
										throw new Exception("网络图片上传失败");
									}
									//修改图片
									if (!"".equals(roomPath)) 
									{
										String urlSql = getSql("group.roomimage");
										this.update(urlSql, new Object[] { roomPath,roomKeyId });
									}
								}
							}
						else
						{
							}
						}
					// 修改图片地址
					if (!"".equals(urlPath))
					{
						String urlSql = getSql("group.imageup");
						this.update(urlSql, new Object[] { urlPath, keyId });
					}
					String statusSql = getSql("group.updatestatus");
					this.update(statusSql, new Object[] { 1, 0, id });
					return 1;
				} 
				else 
				{
					return -1;
				}
			}
		});
		// return this.getReturnMap(i);
		return getReturnMap(i);
	}

	/**
	 * 小区删除
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
        @Transactional
	public Object groupDelete(HttpServletRequest request,HttpServletResponse response)
	{
		String id = req.getAjaxValue(request, "id");
		String sqlhouse = getSql("group.queryhouse");
		int count = db.queryForInt(sqlhouse, new Object[] { id });
		if (count > 0)
		{
			return getReturnMap(2);
		} 
		else 
		{
			String statusSql = getSql("group.updatestatus");
			return getReturnMap(db.update(statusSql, new Object[] { 3, 1, id }));
		}
	}

	/**
	 * 小区审批通过
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object approveOk(final HttpServletRequest request,HttpServletResponse response)
	{
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>() 
				{
			@Override
			public Integer execute() throws Exception
			{
				String id = req.getAjaxValue(request, "id");
				String sql = getSql("group.query.main")+ getSql("group.query.id");
                                logger.debug("?????????????????????????");
				Map<String, Object> returnMap = db.queryForMap(sql,new Object[] { id });
				String state = str.get(returnMap, "status");
				switch (state)
				{
				case "2":
					String oldId = str.get(returnMap, "old_id");
					// 删除小区
					String delete = getSql("group.deleteT");
					this.update(delete, new Object[] { oldId });
					//修改小区id
					String idupdate = getSql("group.idupdate");
					this.update(idupdate, new Object[] { oldId,id });
					// 删除小区对应关系
					String deletetroom = getSql("group.deleteroom");
					this.update(deletetroom, new Object[] { oldId });
					//修改对应小区关系
					String roomupdate = getSql("group.roomidupdate");
					this.update(roomupdate, new Object[] { oldId,id });
                                        
					// 恢复小区
					String statusSql = getSql("group.updatestatus");
					this.update(statusSql, new Object[] { 1, 1, oldId });
					break;
				case "3":
                                    
					String sqld = getSql("group.delete");
					this.update(sqld, new Object[] { id });
					break;
				}
                                logger.debug("?????????????????????????111111111");
                                //删除团队关系
                                this.update("DELETE a FROM cf_team_rel_area a where a.area_id = ?",new Object[]{id});
                                this.update("DELETE a FROM cf_team_member_area a where a.area_id = ?",new Object[]{id});
				return 1;
			}
		});
		return getReturnMap(i);
	}

	/**
	 * 小区审批拒绝
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object approveNO(final HttpServletRequest request,HttpServletResponse response) 
	{
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
				{
			@Override
			public Integer execute() throws Exception
			{
				String id = req.getAjaxValue(request, "id");
				String sql = getSql("group.query.main")
						+ getSql("group.query.id");
				Map<String, Object> returnMap = this.queryForMap(sql,new Object[] { id });
				String state = str.get(returnMap, "status");
				switch (state) 
				{
				case "2":
					String oldId = str.get(returnMap, "old_id");
					// 删除小区
					String delete = getSql("group.deleteT");
					this.update(delete, new Object[] { id });

					// 删除小区对应关系
					String deletetroom = getSql("group.deleteroom");
					this.update(deletetroom, new Object[] { id });

					// 恢复小区
					String statusSql = getSql("group.updatestatus");
					this.update(statusSql, new Object[] { 1, 1, oldId });
                     break;
				case "3":
					//恢复正常状态
					String statusSqls = getSql("group.updatestatus");
					this.update(statusSqls, new Object[] { 1, 1, id });
					break;
				}
				return 1;
			}
		});
		return getReturnMap(i);
	}

	/**
	 * 小区新增
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	public Object groupSave(final HttpServletRequest request,HttpServletResponse response) 
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
				{
			@Override
			public Integer execute() throws Exception
			{
				String areaid = req.getAjaxValue(request, "areaId");
				String group_type = req.getAjaxValue(request, "type");
				String group_name = req.getAjaxValue(request, "name");
				String group_desc = req.getAjaxValue(request, "desc");
				String traffic = req.getAjaxValue(request, "traffic");
				String developer = req.getAjaxValue(request, "developer");
				String property = req.getAjaxValue(request, "property");
				String build_date = req.getAjaxValue(request, "buildDate");
				String area = req.getAjaxValue(request, "area");
				String plot_ratio = req.getAjaxValue(request, "plotratio");
				String house_count = req.getAjaxValue(request, "houseCount");
				String green_rate = req.getAjaxValue(request, "greenRate");
				String park_count = req.getAjaxValue(request, "parkCount");
				String images = req.getAjaxValue(request, "images");
				String coordinate = req.getAjaxValue(request, "coordinate");
				String address = req.getAjaxValue(request, "address");
				String trading = req.getAjaxValue(request, "trading");
				String roomspec = req.getAjaxValue(request, "roomspec");
				String roomcount = req.getAjaxValue(request, "roomcount");
				String roomspec1 = req.getAjaxValue(request, "roomspec1");
				String roomarea = req.getAjaxValue(request, "roomarea");
				String roomcost = req.getAjaxValue(request, "roomcost");
				String roomimage = req.getAjaxValue(request, "roomimage");
				String sql = getSql("group.insert");
				int keyId;
				keyId = this.insert(sql, new Object[] { areaid, trading,
						group_type, group_name, group_desc, traffic, developer,
						property, build_date, area, plot_ratio, house_count,
						green_rate, park_count, coordinate, 1, address, oper,
						1, "" });
				if (keyId != -2) 
				{
					String urlPath="";
					Map<String,String >  newPath = ImageWork(images, keyId, request);
					if ( "1".equals(str.get(newPath, "state"))) 
					{
						urlPath=str.get(newPath, "newPath");
					}
					else
					{
						logger.debug("图片上传失败");
						throw new Exception("网络图片上传失败");
						//return Integer.parseInt(str.get(newPath, "state"));
					}
					// 小区对应户型价格房源
					String insertroom = getSql("group.insertroom");
					String[] spec = roomspec.split(",");
					//String[] count = roomcount.split(",");
					String[] cost = roomcost.split(",");
					String[] roompic = roomimage.split("\\|");
					String[] spec1 = roomspec1.split(",");
					String[] specarea = roomarea.split(",");
					for (int i = 0; i < spec.length; i++) {
						if (spec[i] != null && !"".equals(spec[i]))
						{
							int roomKeyId = this.insert(insertroom,new Object[] { keyId, spec[i], 0,cost[i],spec1[i],specarea[i] });
							if (!"0".equals(roompic[i])) 
							{
								String roomPath	="";	
								Map<String,String > roomnewPath = ImageWork(roompic[i],roomKeyId, request );
								if ( "1".equals(str.get(roomnewPath, "state")))
								{
									roomPath=str.get(roomnewPath, "newPath");
								}
								else
								{
									logger.debug("图片上传失败");
									throw new Exception("网络图片上传失败");
								}
								if (!"".equals(roomPath))
								{
									//roomPath = roomPath.substring(1);
									String urlSql = getSql("group.roomimage");
									this.update(urlSql, new Object[] { roomPath,
											roomKeyId });
								}
							}
							else 
							{
							}
						}
					}
					// 修改图片地址
					if (!"".equals(urlPath))
					{
						//urlPath = urlPath.substring(1);
						String urlSql = getSql("group.imageup");
						this.update(urlSql, new Object[] { urlPath, keyId });
					}
					return 1;
				} 
				else
				{
					return -1;
				}
			}

		});
		// return this.getReturnMap(i);
		return getReturnMap(i);
	}

	/**
	 * 图片处理公共方法
	 * 
	 * 
	 * 
	 * */
	public Map<String, String> ImageWork(String images, int keyId, HttpServletRequest request)
	{
		String newPath = "";
		FtpUtil ftp = null;
	    Map<String , String> gmap=new HashMap<String, String>();
		try 
		{
			ftp = new FtpUtil();
			if (!"".equals(images)) 
			{
				String[] pathArray = images.split(",");
				for (int j = 0; j < pathArray.length; j++)
				{
					if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_GROUP_HTTP_PATH"))) 
					{
							newPath +="/"+pathArray[j].replace("upload/tmp/", "").replace(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_GROUP_HTTP_PATH"),"")+ "|";
						continue;
					}
					String tmpPath = String.valueOf(keyId)+ UUID.randomUUID().toString().replaceAll("-", "")+ ".png";
						newPath += "/" + keyId +"/" + tmpPath+"|";
						boolean flag = ftp.uploadFile(request.getRealPath("/")+ pathArray[j], tmpPath,SystemConfig.getValue("FTP_GROUP_PATH") +keyId+"/");
						if (!flag) 
						{
							gmap.put("state","-3");
							return gmap;
					    }
				}
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			gmap.put("state","-3");
			return gmap;
		} 
		finally
		{
			// 关闭流
			if (ftp != null) {
				ftp.closeServer();
			}
		}
		gmap.put("state","1");
		gmap.put("newPath", newPath);
		return gmap;
	}

  public Object getGroupInfoByAreaId(HttpServletRequest request, HttpServletResponse response)
  {
    // 获取区域id
    String id = String.valueOf(req.getAjaxValue(request, "id"));
    List<Map<String, Object>> list = new ArrayList<>();
    if (!id.equals("null") && !id.equals(""))
    {
      String sql = "select id,group_name from yc_group_tab a where a.areaid in (" + id + ") order by a.areaid";
      list = db.queryForList(sql);
    }
    return list;
  }
}
