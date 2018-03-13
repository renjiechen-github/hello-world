/*
 * Copyright (c) 2014  . All Rights Reserved.
 */
package pccom.web.server.sys.grid;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import org.springframework.stereotype.Service;

import pccom.common.util.Batch;
import pccom.web.server.BaseService;

@Service("gridService")
public class GridService extends BaseService {
	
	
	/**
	 * 网格获取列表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response)
	{
		String	areaid = req.getAjaxValue(request, "areaid");
		String	keyWord = req.getAjaxValue(request, "keyWord");
		String sql = getSql("grid.groupQuery.main2");
		List<String> params = new ArrayList<String>();
		if (!"".equals(areaid)) //区域查询
		{
			sql +=" and t.areaid=? "; 
			params.add(areaid);
		}
		if (!"".equals(keyWord)) //关键字
		{
			sql += getSql("grid.groupQuery.keyWord");
			params.add("%" + keyWord + "%");
			params.add("%" + keyWord + "%");
		}
		getPageList(request,response,sql,params.toArray());
	}

	/**
	 * 网格获取小区列表
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void getGroupList(HttpServletRequest request, HttpServletResponse response)
	{
		String	g_id = req.getAjaxValue(request, "g_id");
		String	keyName = req.getAjaxValue(request, "keyName");
		String sql =getSql("group.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(g_id)) {
			sql +=getSql("grid.groupQuery.g_id"); //getSql("group.query.areaid");
			params.add(g_id);
		}
		if (!"".equals(keyName)) {
			sql += " and ( g.group_name like ? or g.address LIKE ?)";//getSql("grid.groupQuery.groupname"); //getSql("group.query.areaid");
			params.add("%"+keyName+"%");
			params.add("%"+keyName+"%");
		}
		getPageList(request,response,sql,params.toArray(),"grid.groupQuery.orderby");
	}
	/**
	 * 网格获取小区列表
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void getSelectList(HttpServletRequest request, HttpServletResponse response)
	{
		String	g_id = req.getAjaxValue(request, "g_id");
		String	areaid = req.getAjaxValue(request, "areaid");
		String	keyword = req.getAjaxValue(request, "keyword");
		String	out_g_id = req.getAjaxValue(request, "out_g_id");//排除本网格内的小区
		String sql =getSql("grid.groupQuery.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(g_id)) {
			sql +=getSql("grid.groupQuery.g_id"); 
			params.add(g_id);
		}
		if (!"".equals(out_g_id)) {
			sql +=" and g.g_id <> ?";//getSql("grid.groupQuery.g_id"); 
			params.add(out_g_id);
		}
		if (!"".equals(areaid)) {
			sql +=getSql("grid.groupQuery.areaid"); 
			params.add(areaid);
		}
		if (!"".equals(keyword)) {
			sql +=getSql("grid.groupQuery.groupname"); 
			params.add("%"+keyword+"%");
			params.add("%"+keyword+"%");
			params.add("%"+keyword+"%");
		}
		getPageList(request,response,sql,params.toArray(),"grid.groupQuery.orderby");
	}
	
	/**
	 * 小区删除
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
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
	 * 删除网格
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object gridDelete(final HttpServletRequest request,final HttpServletResponse response)
	{
	    @SuppressWarnings("unchecked")
		 Object i = db.doInTransaction(new Batch<Object>()
		 {
			 @Override
			public Integer execute() throws Exception
			{
				    String g_id = req.getAjaxValue(request, "g_id");
					this.update(getSql("grid.deleteGrid"),new Object[]{g_id});//删除网格
					this.update(getSql("grid.updateAccountgid"),new Object[]{0,g_id});//释放账号网格
					this.update(getSql("grid.groupUpdategid"), new Object[] {-2, g_id});//释放网格中的小区
					return 1;
			}
		 });
	    return getReturnMap(i);
	}
	/**
	 * 网格更换
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object gridDispatch(final HttpServletRequest request,final HttpServletResponse response) 
	{
	    @SuppressWarnings("unchecked")
		 Object i = db.doInTransaction(new Batch<Object>()
		 {
			 @Override
			public Integer execute() throws Exception
			{
					String oldAccount = req.getAjaxValue(request, "oldAccount");//网格原所属人
					String account = req.getAjaxValue(request, "account");//网格现所属人
					String g_id = req.getAjaxValue(request, "g_id");//网格id
					String sql=getSql("grid.updateAccount");
					this.update(sql,new Object[]{0,oldAccount});
					this.update(sql,new Object[]{g_id,account});
					return 1;
			}
		 });
	    return getReturnMap(i);
	}
	/**
	 * 新增网格
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return Map
	 * @throws ServiceException
	 */
	public Object gridCreate(final HttpServletRequest request, final HttpServletResponse response) 
	{
	    @SuppressWarnings("unchecked")
		 Object i = db.doInTransaction(new Batch<Object>()
		 {
			 @Override
			public Integer execute() throws Exception
			{
					String areaid = req.getAjaxValue(request, "areaid");
					String g_name = req.getAjaxValue(request, "g_name");
					String o_id = req.getAjaxValue(request, "o_id");
					String account = req.getAjaxValue(request, "account");
					int key=0;
					key=this.insert(getSql("grid.create"), new Object[] {g_name, 3,areaid});
					if (key==-2) 
					{
						return key;
					}
					if (!"0".equals(account)) 
					{
						this.update(getSql("grid.updateAccount"),new Object[]{key,account});
					}
					return 1;
			}
		 });
	    return getReturnMap(i);
	}
	
	/**
	 * 获取管理人员名单
	 * @return list
	 * @author liuf
	 * @date 2017年2月17日
	 */
	public List<?> queryManager(HttpServletRequest request,HttpServletResponse response)
	{
		String role_Id = req.getAjaxValue(request, "role_Id");
		String sql = getSql("grid.getMangerList").replace("####", "("+role_Id+")");
		return db.queryForList(sql );
	}
	
	/**
	 * 查询网格名称是否已被使用
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return Map
	 * @throws ServiceException
	 */
	public Object checkName( HttpServletRequest request,HttpServletResponse response) 
	{
		String g_name = req.getAjaxValue(request, "g_name");
	    int res= db.queryForInt(getSql("grid.checkName"), new Object[] {g_name});
		if (res>0)
		{
			return -1;
		}
		return getReturnMap(1);
	}

	/**
	 * 修改小区所属网格
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return Map
	 * @throws ServiceException
	 */
	public Object groupUpdate( HttpServletRequest request,HttpServletResponse response) 
	{
		String id = req.getAjaxValue(request, "id");
		String g_id = req.getAjaxValue(request, "g_id");
		if (g_id==null||"".equals(g_id))
		{
			g_id="-2";
		}
		return getReturnMap(db.update( getSql("grid.groupUpdate"), new Object[] {g_id, id}));
	}
}
