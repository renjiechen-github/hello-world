package pccom.web.mobile.service.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import pccom.common.util.BatchSql;
import pccom.common.util.Constants;
import pccom.common.util.DateHelper;
import pccom.common.util.StringHelper;
import pccom.web.beans.Login;
import pccom.web.beans.User;
import pccom.web.listener.SessionManage;
import pccom.web.server.BaseService;

/**
 * 登录验证
 * @author suntf
 * @date 2016-7-21
 */
@Service("miLoginService")
public class MiLoginService extends BaseService
{
	/**
	 * 登录操作
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object checkLogin(HttpServletRequest request) 
	{
		JSONObject json = new JSONObject();
		String userName = req.getValue(request, "userName");//用户名
		String password = req.getValue(request, "passWord");//密码
//		String org_id = "";
		/**
		String validateCode = req.getValue(request, "validateCode"); //验证
		Object code = request.getSession().getAttribute("validateCode");
		Enumeration<String> attr = request.getSession().getAttributeNames();
		while (attr.hasMoreElements())
		{
			String string =  attr.nextElement();
			Object value = request.getAttribute(string);
			logger.debug(string+":"+value);
		}
		logger.debug("code:"+code);
		if(code != null)
		{
			if(!code.toString().equalsIgnoreCase(validateCode))
			{
				json.put("res", "-1");
				return json;
			}
		}
		else
		{
			json.put("res", "0");
			return json;
		}
		*/
		String sql = getSql("user.mobileCheckUser");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{userName});
		String getPassWord = StringHelper.get(map, "passwd");
		if(map.isEmpty())
		{
			//说明用户名不存在
			json.put("msg", "用户名不存在");
			json.put("res", -2);
			recordLog(-2, request);
			return json;
		}
		else
		{
			if("0".equals(StringHelper.get(map, "state")))
			{	
				json.put("res", -3);
				json.put("msg", "您的账号被冻结!");
				recordLog(-3, request);
				return json;
			}
			//存在
			if(!getPassWord.equals(password))
			{
				json.put("res", -3);
				json.put("msg", "密码不正确!");
				recordLog(-3, request);
				return json;
			}
		}
		// 验证用户所在的组织信息
//		sql = getSql("user.userOrg");
//		List<Map<String, Object>> orgList = db.queryForList(sql, new Object[]{StringHelper.get(map, "id")});
//		if(orgList.size() == 0)
//		{
//			// 未配置组织联系管理员
//			recordLog(1, request);
//			json.put("res", "3");
//			json.put("msg", "您未配置组织信息,请联系管理员");
//			return json;
//		}
//		else if(orgList.size() > 1)
//		{
//			recordLog(1, request);
//			json.put("res", "4");
//			json.put("msg", "请选择组织");
//			json.put("orgList", JSONArray.fromObject(orgList));
//			return json;
//		}
//		org_id = StringHelper.get(orgList.get(0), "org_id");
//		logger.debug("exc:"+exc);
		User user = new User();
//		if(exc == 1)
//		{
			//进行判断是否是已经登录过的用户，存在登录用户就直接注销已经登录的用户
			String sessionName = SessionManage.getSessionName("account"+userName);
			if(!"".equals(sessionName))
			{
				//已经存在登录用户，直接强制退出先前登录的用户
				//直接移除登录信息，这样先前登录的信息就会直接失效掉了
				SessionManage.removeSession(request.getSession(),sessionName);
			}
			//校验通过，进行获取用户基本信息
			user.setId(StringHelper.get(map, "id"));
			user.setIs_mobile("1");
			user.setMobile(StringHelper.get(map, "mobile"));
			user.setName(StringHelper.get(map, "name"));
			user.setG_id(StringHelper.get(map, "g_id"));
			user.setOrgId(db.queryForString(getSql("user.getOrgs"),new Object[]{user.getId()}));
			user.setRoles(db.queryForString(getSql("user.getRole"),new Object[]{user.getId()}));
			user.setType(StringHelper.get(map, "type"));
			user.setHeadimgurl(StringHelper.get(map, "headerImage"));
//			user.setOrgName(StringHelper.get(orgList.get(0), "org_name"));
			user.setOrgId(db.queryForString(getSql("user.getOrgs"),new Object[]{user.getId()}));
			user.setRoleNames(StringHelper.get(db.queryForMap(getSql("user.getRolesInfo"), new Object[]{user.getId()}), "roleNames"));
			Login login = new Login();
			login.setCookies(getCookie(request, Constants.COOKIE_CLIENT_ID));
			login.setMobile(userName);
			login.setUser(user);
			//进行设置用户信息
			SessionManage.setSession(request.getSession(), userName, login);
//		}
		json.put("res", 1);
//		if("1".equals(org_id)) 
//		{
//			json.put("todoUrl", "");
//		}
//		else if("2".equals(org_id))
//		{
//			json.put("todoUrl", "");
//		}
//		else if("3".equals(org_id))
//		{
//			json.put("todoUrl", "");
//		}
//		imageList.add("http://www.yoka.com/dna/pics/ba1caeb9/97/d35cce9cdb3b11bac9.jpg");
//		imageList.add("http://ws3.cdn.caijing.com.cn/2013-10-08/113380221.jpg");
//		imageList.add("http://img.popoho.com/UploadPic/2011-10/20111024132221149.jpg");
//		imageList.add("http://ww3.sinaimg.cn/large/610dc034jw1f070hyadzkj20p90gwq6v.jpg");
		// 
		
		if(user.getRoles().indexOf("22") > -1)
		{
			// 市场部管家
			json.put("workbenchInfo", "/client/pages/market/house.html");
			json.put("workbenchList", this.getWorkbenchList("3").toString());
		}
		else
		{
			json.put("workbenchInfo", "/client/pages/my/kf.html?flag=1");
		}
		sql = getSql("user.bannerCfg");
		json.put("urlImages", JSONArray.fromObject(db.queryForList(sql, new Object[]{22})).toString());
		sql = getSql("systemconfig.querydictionary").replace("?", " ? and item_id = ? ");
		json.put("todoUrl", str.get(db.queryForMap(sql, new Object[]{"TODO.URL", 22}), "item_name"));
		
		try
		{
			logger.debug(DateHelper.getToday("yyyy.MM.dd HH:mm:ss"));
			json.put("myUrl", "/client/pages/my/my.html?userName="+URLEncoder.encode(user.getName(),"utf-8")+"&loginDate="+DateHelper.getToday("yyyy.MM.dd HH:mm:ss"));
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		json.put("user", JSONObject.fromObject(user));
		logger.debug(json.toString());
		return json;
	}
	
	/**
	 * 
	 */
	public void recordLog(int exc, HttpServletRequest request)
	{
		String userName = req.getValue(request, "userName");//用户名
		String password = req.getValue(request, "passWord");//密码
		Enumeration<String> e = request.getHeaderNames();
		String heads = "";
		while(e.hasMoreElements())
		{
			String name = e.nextElement();
			String value =request.getHeader(name);
			heads += name+":"+value;
		}
		//插入登录日志中
		db.update(getSql("log.login"),new Object[]{userName,password,1,heads,getIP(request)});
	}
	
	/**
	 * 初始化用户信息 （弃用）
	 * @param request
	 */
	public Object initUserInfo(HttpServletRequest request)
	{
		JSONObject json = new JSONObject();
		String userName = req.getValue(request, "userName");//用户名
		String org_id = req.getValue(request, "org_id");
		String sql = getSql("user.mobileCheckUser");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{userName});
		User user = new User();
//		if(exc == 1)
//		{
			//进行判断是否是已经登录过的用户，存在登录用户就直接注销已经登录的用户
			String sessionName = SessionManage.getSessionName("account"+userName);
			if(!"".equals(sessionName))
			{
				//已经存在登录用户，直接强制退出先前登录的用户
				//直接移除登录信息，这样先前登录的信息就会直接失效掉了
				SessionManage.removeSession(request.getSession(),sessionName);
			}
			//校验通过，进行获取用户基本信息
			user.setId(StringHelper.get(map, "id"));
			user.setMobile(StringHelper.get(map, "mobile"));
			user.setName(StringHelper.get(map, "name"));
			user.setOrgId(org_id);
			user.setG_id(StringHelper.get(map, "g_id"));
			user.setHeadimgurl(StringHelper.get(map, "headerImage"));
			user.setRoles(db.queryForString(getSql("user.getRole"),new Object[]{user.getId()}));
			user.setType(StringHelper.get(map, "type"));
			user.setRoleNames(StringHelper.get(db.queryForMap(getSql("user.getRolesInfo"), new Object[]{user.getId()}), "roleNames"));
			user.setOrgName(StringHelper.get(db.queryForMap(getSql("user.getOrgInfo"),new Object[]{org_id}), "org_name"));
			Login login = new Login();
			login.setCookies(getCookie(request, Constants.COOKIE_CLIENT_ID));
			login.setMobile(userName);
			login.setUser(user);
			//进行设置用户信息
			SessionManage.setSession(request.getSession(), userName, login);
//		}
			// org_id 1 市场部 2 工程部 3 全局
			sql = getSql("systemconfig.querydictionary").replace("?", " ? and item_id = ? ");
			json.put("todoUrl", str.get(db.queryForMap(sql, new Object[]{"TODO.URL", org_id}), "item_name"));
//			if("1".equals(org_id)) 
//			{
//				json.put("todoUrl", "/client/pages/market/house.html");
//			}
//			else if("2".equals(org_id))
//			{
//				json.put("todoUrl", "/client/pages/market/house.html");
//			}
//			else if("3".equals(org_id))
//			{
//				json.put("todoUrl", "/client/pages/market/house.html");
//			}
			sql = getSql("user.bannerCfg");
			json.put("urlImages", JSONArray.fromObject(db.queryForList(sql, new Object[]{org_id})).toString());
			json.put("workbenchList", this.getWorkbenchList(org_id).toString());
			try
			{
				json.put("myUrl", "/client/pages/my/my.html?userName="+URLEncoder.encode(user.getName(),"utf-8")+"&loginDate="+DateHelper.getToday("yyyy.MM.dd HH:mm:ss"));
			}
			catch (UnsupportedEncodingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			json.put("user", JSONObject.fromObject(user));
			logger.debug(json.toString());
			return json;
	}
	
	/**
	 * 通过组织获取工作台列表
	 * @param orgId
	 * @return
	 */
	public JSONArray getWorkbenchList(String orgId)
	{
		String sql = getSql("user.workbench");
		logger.debug(str.getSql(sql, new Object[]{orgId}));
		return JSONArray.fromObject(db.queryForList(sql, new Object[]{orgId}));
	}
	
	/**
	 * 更新用户channelID
	 * @param request
	 * @return
	 */
	public int updateChannelId(HttpServletRequest request)
	{
		String channelId = req.getValue(request, "channelId"); 
		String mobile = req.getValue(request, "mobile"); // 手机号码
		String clientType = req.getValue(request, "clientType"); // 客户端类型 Android iOS
		String sql = getSql("user.delChannelId");
		BatchSql batchSql = new BatchSql();
		batchSql.addBatch(sql, new Object[]{mobile, channelId});
		sql = getSql("user.insertChannelId");
		batchSql.addBatch(sql, new Object[]{mobile, channelId, clientType});
		return db.doInTransaction(batchSql);
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	public int updatePassword(HttpServletRequest request)
	{
		String mobile = req.getValue(request, "mobile"); // 手机号码
		String oldPwd = req.getValue(request, "oldPassword"); //原来密码
		String password = req.getValue(request, "newPassword"); // 密码
		
		String sql = getSql("user.mobileCheckUser");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{mobile});
		if(!oldPwd.equals(StringHelper.get(map, "passwd")))
		{
			return -2;
		}
		sql = getSql("user.modifyMobilePass");
		return db.update(sql, new Object[]{password, mobile});
	}
	
	/**
	 * 更新头像
	 * @param request
	 * @return
	 */
	public int modifyHeaderImage(HttpServletRequest request)
	{
		String mobile = req.getValue(request, "mobile"); // 手机号码
		String path = req.getValue(request, "pic_path"); // 图片路径
		logger.debug(path);
		String sql = getSql("user.modifyHeaderImage");
		return db.update(sql, new Object[]{path, mobile});
	}
	
	/**
	 * 修改用户名
	 * @param request
	 * @return
	 */
	public int updateUserName(HttpServletRequest request)
	{
		String mobile = req.getValue(request, "mobile"); // 手机号码
		String name = req.getAjaxValue(request, "name");
		String sql = getSql("user.modifyUserName");
		return db.update(sql, new Object[]{name, mobile});
	}
	
}
