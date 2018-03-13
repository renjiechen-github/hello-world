/*
 * Copyright (c) 2014  . All Rights Reserved.
 */
package pccom.web.server.system;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycdc.nbms.datapermission.service.DataPermissionServ;

import net.sf.json.JSONArray;
import pccom.web.beans.User;
import pccom.web.server.BaseService;

@Service("systemService")
public class SystemService extends BaseService {
	
	@Autowired
	private DataPermissionServ serv;

	public Object queryMenuList(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("???????????");

		return null;
	}

	/**
	 * 查询表基础类型
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object queryItem(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = req.getAjaxValue(request, "group_id");//
		String sql = getSql("systemconfig.querydictionary");
		List<Map<String, Object>> lists = db.queryForList(sql,new Object[] { groupId });
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(lists));
		return returnMap;
	}
	


	/**
	 * 查询表基础类型
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object queryItemAll(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = req.getAjaxValue(request, "group_id");//
		String sql = getSql("systemconfig.querydictionaryAll");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,
				new Object[] { groupId })));
		return returnMap;
	}

	/**
	 * 人员查询
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object queryUser(HttpServletRequest request,
			HttpServletResponse response) {
		List<String> params = new ArrayList<String>();
		String orgids = req.getAjaxValue(request, "orgids");
		String roles = req.getAjaxValue(request, "roles");
		String username = req.getAjaxValue(request, "username");
		String sql = getSql("user.queryUser.main");
		if (!"".equals(orgids)) {
			sql += getSql("user.queryUser.org").replace("##", orgids);
		}
		if (!"".equals(roles)) {
			sql += getSql("user.queryUser.role").replace("##", roles);
		}
		if (!"".equals(username)) {
			sql += getSql("user.queryUser.mhquery");
			params.add("%" + username + "%");
			params.add("%" + username + "%");
		}
		return getPageList(request, sql, params.toArray());
	}

	/**
	 * 区域查询
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getArea(HttpServletRequest request,
			HttpServletResponse response) {
		String type = req.getAjaxValue(request, "type");
		String fatherid = req.getAjaxValue(request, "fatherid");
		String sql = getSql("area.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(type)) {
			sql += getSql("area.query.type");
			params.add(type);
		}
		if (!"".equals(fatherid)) {
			sql += getSql("area.query.fatherid").replaceAll("\\?", fatherid);
		}
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql, params.toArray())));
		return returnMap;
	}

	/**
	 * 区域查询
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object queryMcate(HttpServletRequest request,
			HttpServletResponse response) {
		String fatherid = req.getAjaxValue(request, "fatherid");
		String mcatename = req.getAjaxValue(request, "mcatename");
		
		String sql = getSql("mcate.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(fatherid)) {
			sql += getSql("mcate.query.fatherid");
			params.add(fatherid);
		}
		
		if (mcatename!=null&&!"".equals(mcatename)) {
			sql += getSql("mcate.query.mcatename");
			params.add("%" + mcatename + "%");
		}
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql, params.toArray())));
		return returnMap;
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public Object UploadUrl(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String name = "";// 文件名稱
		String path = "";// 文件存儲路徑，臨時文件
		InputStream stream = null;
		OutputStream bos = null;
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					//logger.debug("表单参数名:" + item.getFieldName()
					//		+ "，表单参数值:" + item.getString("UTF-8"));
					String itemName = item.getFieldName();
					String itemValue = item.getString("UTF-8");
					if("path".equals(itemName)){
						path = itemValue;
					}else if("name".equals(itemName)){
						name = itemValue;
					}
				} else {
					if (item.getName() != null && !item.getName().equals("")) {
						//logger.debug("上传文件的大小:" + item.getSize());
						//logger.debug("上传文件的类型:" + item.getContentType());
						// item.getName()返回上传文件在客户端的完整路径名称
						//logger.debug("上传文件的名称:" + item.getName());
						long size = item.getSize();
						String contentType = item.getContentType();
						String name_ = item.getName();
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						if (path.startsWith("/")) {
                            path = path.substring(1); 
                        }
						String tmppath = request.getRealPath("/") + path;
						//logger.debug("tmppath:" + tmppath);
						
						stream = item.getInputStream();// 把文件读入
						bos = new FileOutputStream(tmppath + "/"
								+ name);
						int bytesRead = 0;
						byte[] buffer = new byte[8192];
						while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
							bos.write(buffer, 0, bytesRead);// 将文件写入服务器
						}
						//插入上传数据库中
						String sql = getSql("systemconfig.insertfilelog");
						db.update(sql,new Object[]{name_,contentType,name,getUser(request).getId(),size,path,getIP(request)});
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("上传错误");
		} finally {
			if(bos != null){
				try{
					bos.close();
				}catch (Exception e) {
					
				}
			}
			if(stream != null){
				try{
					stream.close();
				}catch (Exception e) {
				}
			}
		}
		return null;
	}

	/**
	 * 加载图片信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object loadTmpImg(HttpServletRequest request,
			HttpServletResponse response) {
		String page = req.getAjaxValue(request, "page");//页码
		String type = req.getAjaxValue(request, "type");//类型
		List<String> params = new ArrayList<String>();
		String sql = getSql("systemconfig.loadfile.main");
		if("1".equals(type)){//自己的临时图片
			sql += getSql("systemconfig.loadfile.operid");
			params.add(getUser(request).getId());
		}else if("2".equals(type)){//公共的图片
			sql += getSql("systemconfig.loadfile.iscommon");
			params.add("1");
		}
		Map<String,Object> returnMap = getReturnMap(1);
		returnMap.put("list",db.queryForList(sql,params));
		return returnMap;
	}

	/**
	 * 设置公共图片
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object setCommonImg(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "ids");
		
		return getReturnMap(db.update(getSql("systemconfig.setCommonImg").replace("##",id)));
	}

	/**
	 * 删除图片
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object deleteImg(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "ids");
		String paths = req.getAjaxValue(request, "paths");
		//logger.debug(paths);
		String[] path = paths.split(",");
		//logger.debug(path.length);
		for(int i=0;i<path.length;i++){
			File file = new File(request.getRealPath("/")+path[i]);
			if(file.exists()){
				file.delete();
				logger.debug("删除成功");
			}
		}
		
		return getReturnMap(db.update(getSql("systemconfig.deleteImg").replace("##",id)));
	}
	/**
	 * 获取用户姓名
	 * @param request
	 * @return
	 * @author liuf
	 * @date 2016年8月29日
	 */
	public Object getUserName(HttpServletRequest request)
	{
		String mobile = req.getValue(request, "userMobile");
		String sql = getSql("basehouse.houseInfo.checkOwnerExist");
		return db.queryForMap(sql, new Object[]{mobile});
	}
	/**
	 * 获取用户姓名
	 * @param request
	 * @return
	 * @author liuf
	 * @date 2016年8月29日
	 */
	public Object getUserNameById(HttpServletRequest request)
	{
		String id = req.getValue(request, "id");
		String sql = getSql("basehouse.houseInfo.getUserNameById");
		return db.queryForMap(sql, new Object[]{id});
	}

}
