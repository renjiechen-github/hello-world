package com.yc.rm.caas.appserver.base.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.base.syscfg.dao.ISysCfgDao;
import com.yc.rm.caas.appserver.base.token.Token;
import com.yc.rm.caas.appserver.model.user.User;
import javax.servlet.http.Cookie;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;
import pccom.web.beans.Login;
import pccom.web.listener.SessionManage;


/**
 * 
 * @author Administrator
 * 
 */
public class LoggedInterceptor extends HandlerInterceptorAdapter {

	private static final boolean RUN_TYPE = false; // 是否测试状态
	private Logger logger = org.slf4j.LoggerFactory
			.getLogger(LoggedInterceptor.class);
	private List<String> excludeUrls;// 不需要拦截的资源
	@Autowired
	private ISysCfgDao _sysCfgDao;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * 在Controller的方法调用之后执行
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
	 * 然后SpringMVC会根据声明的前后顺序一个接一个的执行
	 * ，而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
	 * SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
	 * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		boolean flag = true;// 还有其他拦截器
		String ip = request.getRemoteAddr();
		String userId = "";
		String token = "";
		String powerId = "";
		String menuId = "";
		String url = request.getRequestURI();
		Map<String, String> mp = new HashMap<>();
		logger.info("拦截器:" + url);
		if (url.indexOf("caas/") > -1) { // 拦截CAAS
			// 判断是否有token
			Enumeration<String> en = request.getHeaderNames();
			String heads = "";
			while (en.hasMoreElements()) {
				String object = en.nextElement().toString().trim();
				String value = request.getHeader(object);
				heads += "," + object + ":" + value;
				if ("userid".equals(object)) {
					userId = request.getHeader(object);
				} else {
				}
				if ("token".equals(object)) {
					token = request.getHeader(object);
				} else {
				}
			}
			if (StringUtils.isBlank(userId)) {
				User user = (User)request.getSession().getAttribute("user");
				logger.info("user === " + user);
				if (null == user)
				{
					userId = "";
				}
				else 
				{
					userId = String.valueOf(user.getUserId());
				}
			}
                        if(url.contains("/team/")){
                            //获取老的登陆信息注入到ｓｅｓｓｉｏｎ中
                            User user = new User();
                            pccom.web.beans.User us = getUser(request);
                            
                            String sql = "select\n" +
                                "		a.id as \"userId\",\n" +
                                "		a.name as \"userName\",\n" +
                                "		a.passwd as \"password\",\n" +
                                "		a.mobile as \"userPhone\",\n" +
                                "		DATE_FORMAT(a.create_time,'%Y-%m-%d') as \"createTime\",\n" +
                                "		a.state as \"state\",\n" +
                                "		b.item_name as \"stateName\",\n" +
                                "		group_concat(distinct d.id) as \"roleId\",\n" +
                                "		group_concat(distinct d.name) as \"roleName\",\n" +
                                "		group_concat(distinct g.org_id) as \"orgId\", \n" +
                                "		group_concat(distinct h.org_name) as \"orgName\",\n" +
                                "		f.id as \"teamId\",\n" +
                                "		e.top_teamid as \"topTeamid\",\n" +
                                "		f.name as \"teamName\" \n" +
                                "		from yc_account_tab a\n" +
                                "		left join yc_dictionary_item b on a.state=b.item_id \n" +
                                "		left join cf_userrole c on a.id=c.user_id \n" +
                                "		left join cf_role d on c.role_id=d.id \n" +
                                "		left join cf_team_rel e on a.id=e.user_id \n" +
                                "		left join cf_team f on e.team_id=f.id \n" +
                                "		left join yc_account_org_tab g on a.id=g.user_id \n" +
                                "		left join yc_organization_tab h on g.org_id=h.org_id\n" +
                                "  WHERE a.mobile is not null and a.mobile!='' and a.is_delete=1 and b.group_id='ACCOUNT.STATE'\n" +
                                "  and a.mobile=?";
                            Map<String,Object> map = ((DBHelperSpring)SpringHelper.getApplicationContext().getBean("dbHelper",DBHelperSpring.class)).queryForMap(sql,new Object[]{us.getMobile()});
                            request.getSession().setAttribute("user", (User)net.sf.json.JSONObject.toBean(net.sf.json.JSONObject.fromObject(map), User.class));
                        }
			logger.debug(userId);
			logger.debug(token);
			if (!RUN_TYPE) {
				// 如果要访问的资源是不需要验证的
				if (excludeUrls.contains(url)||url.contains("/team/")) {
					logger.debug("AAA");
					flag = true;
				} else if ("".equals(token) && "".equals(userId)) {
					logger.debug("BBB");
					mp.put("appserverCode", "-1");
					flag = false;
				} else if (!token.equals("") && !token.equals(Token.getToken(userId))) {
					logger.debug("CCC");
					mp.put("appserverCode", "-1");
					flag = false;
				} else {
					logger.debug("DDD");
					String powerUrl = url.replace("//", "/");
					// 验证用户是否有权限访问
					Map<String, Object> urlMap = _sysCfgDao
							.checkUrlExists(powerUrl);
					if (urlMap != null && !urlMap.isEmpty()) {
						powerId = String.valueOf(urlMap.get("power_id"));
						int cnt = _sysCfgDao.checkUserPower(userId, powerId);
						if (cnt < 1) {
							logger.info("没权限");
							menuId = String.valueOf(urlMap.get("menu_id"));
							mp.put("appserverCode", "-2");
							flag = false;
						}
					}
				}
				if (!"".equals(heads)) {
					heads = heads.substring(1);
				} else {
				}
			} else {
				flag = true;
			}
			try {
				// 添加访问记录
				_sysCfgDao
						.addHistory(ip, userId, heads, powerId, menuId, url);
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		if (!flag) {
			response.getWriter().print(JSONObject.toJSONString(mp));
		}
		return flag;
	}
        
        /**
	 * 获取session中的用户信息
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public pccom.web.beans.User getUser(HttpServletRequest request){
		String value = getCookie(request,Constants.COOKIE_CLIENT_ID);
		String username = getCookie(request,"username");
		Object o = SessionManage.getSession(username);
		
		//logger.debug(SessionManage.getSessionMap());
		if(o == null){
			pccom.web.beans.User user = new pccom.web.beans.User();
			user.setRoles("22,27,26.28,1,24,25");
			user.setId("82");
			user.setOrgId("1,2,3");
			user.setIs_mobile("1");
			request.getSession().setAttribute("username", user);
			return user;
		}else{
			Login login = (Login)o;
			return login.getUser();
		}
	}
        
        /**
	 * 获取cookie
	 * @author 雷杨
	 * @param request
	 * @param name
	 * @return
	 */
	public String getCookie(HttpServletRequest request,String name){
		Cookie[] cookies = request.getCookies();
		//logger.debug("----------"+request.getCookies());
		if(cookies == null){
			logger.debug("cookies:"+name+"wei kong ");
			return "";
		}
		for(int i=0;i<cookies.length;i++){
			if(name.equals(cookies[i].getName())){
				return cookies[i].getValue();
			}
		}
		return "";
	}
}