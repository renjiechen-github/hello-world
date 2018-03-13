/*
 * Copyright (c) 2014  . All Rights Reserved.
 */
package pccom.web.server.login;




import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import pccom.common.util.BatchSql;
import pccom.common.util.Constants;
import pccom.common.util.Encrypt;
import pccom.common.util.StringHelper;
import pccom.web.beans.Login;
import pccom.web.beans.User;
import pccom.web.listener.SessionManage;
import pccom.web.server.BaseService;

@Service ("loginService")
public class LoginService extends BaseService
{
	
	/**
	 * 登陆操作
	 * @param request
	 * @param response
	 * @return
	 */
	public Object loginNew(HttpServletRequest request, HttpServletResponse response){
		String userName = req.getAjaxValue(request, "userName").trim();//用户名
		String password = req.getAjaxValue(request, "passWord").trim();//密码
		String sql = getSql("user.checkUser");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{userName});
		String getPassWord = StringHelper.get(map, "passwd");
		String user_id = StringHelper.get(map, "id");
		String state = StringHelper.get(map, "state");
		String cookies = getCookie(request, Constants.COOKIE_CLIENT_ID);
		int exc = 0;
		if(map.isEmpty()){//说明用户名不存在
			exc = -2;
		}else{//存在
			logger.debug(getPassWord+"--"+password);
			if("0".equals(state)){
				exc = -5;
			}else{
				if(!getPassWord.toLowerCase().equals(password.toLowerCase()+"=")){
					exc = -3;
				}else{//登录成功
					exc = 1;
				}
			}
		}
		
		Enumeration<String> e = request.getHeaderNames();
		String heads = "";
		while(e.hasMoreElements()){
			String name = e.nextElement();
			String value =request.getHeader(name);
			heads += name+":"+value;
		}
		logger.debug(userName+"exc:"+exc); 
		
		//验证登陆成功进行判断是否是
		if(exc == 1){
			//检查是否存在对应的登陆信息
			Object obj = SessionManage.getSession(userName);
			if(obj == null){//不存在登陆信息，直接进入
			}else{//存在登陆信息
				Login login = (Login)obj;
				//存在登陆信息需要检查cookies是否是相同，如果不相同需要将原来登陆信息放置到已登陆记录中
				if(!cookies.equals(login.getCookies())){
					SessionManage.addYetLogin(userName+"##"+new Random().nextInt(9999999), login);
				}else{//相同不需要做任何处理
				}
			}
			//校验通过，进行获取用户基本信息
			User user = new User();
			user.setId(StringHelper.get(map, "id"));
			user.setG_id(StringHelper.get(map, "g_id"));
			user.setMobile(StringHelper.get(map, "mobile"));
			user.setName(StringHelper.get(map, "name"));
			user.setIs_mobile("0");
			user.setOrgId(db.queryForString(getSql("user.getOrgs"),new Object[]{user_id}));
			//logger.debug("权限："+(db.queryForString(getSql("user.getRole"),new Object[]{user_id})));
			user.setRoles(db.queryForString(getSql("user.getRole"),new Object[]{user_id}));
			user.setType(StringHelper.get(map, "type"));
			
			//同时覆盖更新最新的session
			Login login1 = new Login();
			login1.setCookies(cookies);
			login1.setMobile(userName);
			login1.setUser(user);
			SessionManage.setSession(request.getSession(), userName, login1);
		}
		//插入登录日志中
		db.update(getSql("log.login"),new Object[]{userName,password,exc,heads,getIP(request)});
		return getReturnMap(exc);
	}

	/**
	 * 登录操作
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object login(HttpServletRequest request, HttpServletResponse response) {
		String userName = req.getAjaxValue(request, "userName").trim();//用户名
		String password = req.getAjaxValue(request, "passWord").trim();//密码
		String sql = getSql("user.checkUser");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{userName});
		String getPassWord = StringHelper.get(map, "passwd");
		String user_id = StringHelper.get(map, "id");
		String state = StringHelper.get(map, "state");
		int exc = 0;
		if(map.isEmpty()){//说明用户名不存在
			exc = -2;
		}else{//存在
			logger.debug(getPassWord+"--"+password);
			if("0".equals(state)){
				exc = -5;
			}else{
				if(!getPassWord.toLowerCase().equals(password.toLowerCase()+"=")){
					exc = -3;
				}else{//登录成功
					exc = 1;
				}
			}
		}
		
		Enumeration<String> e = request.getHeaderNames();
		String heads = "";
		while(e.hasMoreElements()){
			String name = e.nextElement();
			String value =request.getHeader(name);
			heads += name+":"+value;
		}
		logger.debug(userName+"exc:"+exc);  
		if(exc == 1){
			//检查是否已经存在相同登陆信息，如果存在就不在进行设置
			User user1 = getUser(request,userName);
			logger.debug(userName+"user1:"+user1+"---:"+user1.getId());
			if(user1 != null && !"".equals(user1.getId())&& !"null".equals(user1.getId())&&user1.getId() != null){
				logger.debug(userName+"已存在登陆信息");
				//插入登录日志中
				return getReturnMap(db.update(getSql("log.login"),new Object[]{userName,password,exc,heads,getIP(request)}));
			}
			//进行判断是否是已经登录过的用户，存在登录用户就直接注销已经登录的用户
			String sessionName = SessionManage.getSessionName("account"+userName);
			logger.debug(userName+"sessionName:"+sessionName);
			if(!"".equals(sessionName)){//已经存在登录用户，直接强制退出先前登录的用户
				//直接移除登录信息，这样先前登录的信息就会直接失效掉了
				SessionManage.removeSession(request.getSession(),sessionName);
				//记录是否是被挤下来的用户
				SessionManage.setSession(request.getSession(),"jxaccount"+userName+(sessionName.replace("account"+userName, "")) , getIP(request));
			}
			//校验通过，进行获取用户基本信息
			User user = new User();
			user.setId(StringHelper.get(map, "id"));
			user.setG_id(StringHelper.get(map, "g_id"));
			user.setMobile(StringHelper.get(map, "mobile"));
			user.setName(StringHelper.get(map, "name"));
			user.setIs_mobile("0");
			user.setOrgId(db.queryForString(getSql("user.getOrgs"),new Object[]{user_id}));
			//logger.debug("权限："+(db.queryForString(getSql("user.getRole"),new Object[]{user_id})));
			user.setRoles(db.queryForString(getSql("user.getRole"),new Object[]{user_id}));
			user.setType(StringHelper.get(map, "type"));
			//进行设置用户信息
			SessionManage.setSession(request.getSession(),"account"+userName+getCookie(request, Constants.COOKIE_CLIENT_ID) , user);
		}
		//插入登录日志中
		db.update(getSql("log.login"),new Object[]{userName,password,exc,heads,getIP(request)});
		
		return getReturnMap(exc);
	} 
	
	/**
	 * 获取session中的用户信息
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request,String username){
		String value = getCookie(request,Constants.COOKIE_CLIENT_ID);
		Object o = SessionManage.getSession("account"+username+value);
		if(o == null){
			User user = new User();
			return user;
		}else{
			return (User)o;
		}
	}

	/**
	 * 加载菜单信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object loadMenu(HttpServletRequest request,
			HttpServletResponse response) {
		String id = getUser(request).getId();
		logger.debug("1id:"+id);
		if(id == null || "".equals(id)){//未登录
			return getReturnMap(-2);
		}
		//查询出该用户对于的菜单信息
		List<Map<String,Object>> list = db.queryForList(getSql("menu.queryUserMenu.main"),new Object[]{id});
		Map<String,Object> returnMap = getReturnMap(1);
		returnMap.put("menu", JSONArray.fromObject(list));
		return returnMap;
	}
	
	public Object test(HttpServletRequest request,
			HttpServletResponse response) {
		
		return getPageList(request, getSql("menu.queryUserMenu.main"),new Object[]{84}, "menu.queryUserMenu.orderby");
	}

	/**
	 * 加载用户数据信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object loadUser(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("??????????????????");
		User user = getUser(request);
		String id = user.getId();
		logger.debug("1id:"+id);
		if(id == null || "".equals(id)){//未登录
			return getReturnMap(-2);
		}
		Map<String,Object> returnMap = getReturnMap(1);
		//加载菜单项
		List<Map<String,Object>> list = db.queryForList(getSql("menu.queryUserMenu.main")+getSql("menu.queryUserMenu.orderby"),new Object[]{id});
		returnMap.put("menu", JSONArray.fromObject(list));
		
		//加载用户信息
		returnMap.put("user", JSONObject.fromObject(user));
		
		
		
		return returnMap;
	}

	/**
	 * 退出
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		//
		User user = getUser(request);
		String userName = user.getMobile();
		SessionManage.removeSession(request.getSession(),userName);
		return getReturnMap(1);
	}

	/**
	 * 密码修改操作
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object modifypass(HttpServletRequest request,
			HttpServletResponse response) {
		String password = req.getAjaxValue(request, "passWord").trim();//密码
		String newpassWord = req.getAjaxValue(request, "newpassWord").trim();//密码
		String aginpassWord = req.getAjaxValue(request, "aginpassWord").trim();//密码
		User user = getUser(request);
		String userName = user.getMobile();
		String sql = getSql("user.checkUser");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{userName});
		String getPassWord = StringHelper.get(map, "passwd");
		int exc = 0;
		logger.debug("11??????????????????1?????????????????1??????????????????????????????????");
		if("".equals(password)){
			return getReturnMap(-4);
		}
		if("".equals(newpassWord)){
			return getReturnMap(-7);
		}
		if(password.equals(newpassWord)){
			return getReturnMap(-6);
		}  
		if(!newpassWord.equals(aginpassWord)){
			return getReturnMap(-5);
		}
		if(map.isEmpty()){//说明用户名不存在 
			exc = -2;
		}else{//存在
			if(!getPassWord.toLowerCase().equals(password.toLowerCase()+"=")){
				exc = -3;
			}else{//校验成功，进行修改
				sql = getSql("user.modifyPass");
				BatchSql batch = new BatchSql();
				this.insertTableLog(request, batch, "yc_account_tab", " and a.mobile = ? ", "修改密码", new Object[]{userName});
				String pw = new Encrypt("SHA-1").getEncrypt(newpassWord);
				logger.debug("pw:"+pw);
				batch.addBatch(sql,new Object[]{pw,userName});
				if(db.doInTransaction(batch) == 1){
					exc = 1;
				}else{
					exc = -1;
				}
			}
		}
		return getReturnMap(exc);
	}
	
}
