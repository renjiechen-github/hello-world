
package com.ycdc.appserver.base.interceptor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import pccom.common.util.StringHelper;

import com.ycdc.appserver.base.token.Token;
import com.ycdc.appserver.bus.dao.syscfg.SyscfgMapper;

public class LoggedInterceptor extends HandlerInterceptorAdapter
{

	private Logger logger = org.slf4j.LoggerFactory.getLogger(LoggedInterceptor.class);
	private List<String> excludeUrls;// 不需要拦截的资源
	@Autowired
	private SyscfgMapper syscfgMapper;

	public List<String> getExcludeUrls()
	{
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls)
	{
		this.excludeUrls = excludeUrls;
	}

	/**
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行。
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, Exception arg3) throws Exception
	{
		// TODO Auto-generated method stub
	}

	/**
	 * 在Controller的方法调用之后执行
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception
	{
		// TODO Auto-generated method stub
	}

	/**
	 * preHandle方法是进行处理器拦截用的，顾名思义，该方法将在Controller处理之前进行调用，
	 * SpringMVC中的Interceptor拦截器是链式的，可以同时存在多个Interceptor，
	 * 然后SpringMVC会根据声明的前后顺序一个接一个的执行，而且所有的Interceptor中的preHandle方法都会在Controller方法调用之前调用。
	 * SpringMVC的这种Interceptor链式结构也是可以进行中断的，这种中断方式是令preHandle的返
	 * 回值为false，当preHandle的返回值为false的时候整个请求就结束了。
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception
	{
		String powerId = "";
		String url = request.getRequestURI();
		logger.info("拦截器:" + url);
		if (url.indexOf("appserver/") > -1)
		{
			String userId = "";
			String token = "";
			Enumeration<String> en = request.getHeaderNames();
			while (en.hasMoreElements())
			{
				String object = en.nextElement().toString().trim();
				if ("userid".equals(object))
				{
					userId = request.getHeader(object);
				}
				if ("token".equals(object))
				{
					token = request.getHeader(object);
				}
			}
			// 如果要访问的资源是不需要验证的
			if (url.indexOf("login.do") > -1 || excludeUrls.contains(url))
			{
				/**
				 * 在这里插入登录接口访问记录没有意义，已更换到登录接口实现类中插入历史记录
				 * @author duanyongrui
				 */
//				addHistory(userId, powerId, url, "", request);
				return true;
			}
			if ("".equals(token) || "".equals(userId))
			{
				addHistory(userId, powerId, url, "", request);
				Map<String, String> mp = new HashMap<>();
				mp.put("appserverCode", "-1");
				response.getWriter().print(JSONObject.fromObject(mp));
				return false;
			}
			if (!token.equals(Token.getToken(userId)) && !token.equals("000000"))
			{
				addHistory(userId, powerId, url, "", request);
				Map<String, String> mp = new HashMap<>();
				mp.put("appserverCode", "-1");
				response.getWriter().print(JSONObject.fromObject(mp));
				return false;
			}
			String powerUrl = url.replace("//", "/");
			// 验证用户是否有权限访问
			Map<String, String> mp = syscfgMapper.checkUrlExists(powerUrl);
			if (mp != null && !mp.isEmpty())
			{
				powerId = StringHelper.get(mp, "power_id");
				int cnt = syscfgMapper.checkUserPower(userId, powerId);
				if (cnt < 1)
				{
					logger.info("没权限");
					addHistory(userId, powerId, url, StringHelper.get(mp, "menu_id"),
							request);
					mp = new HashMap<>();
					mp.put("appserverCode", "-2");
					response.getWriter().print(JSONObject.fromObject(mp));
					return false;
				}
			}
			addHistory(userId, powerId, url, "", request);
		}
		return true;
	}

	/**
	 * 添加访问记录
	 * 
	 * @param userId
	 * @param powerId
	 * @param url
	 * @param menuId
	 */
	public void addHistory(String userId, String powerId, String url, String menuId,
			HttpServletRequest request)
	{
		String ip = this.getIP(request);
		String heads = "";
		Enumeration<String> en = request.getHeaderNames();
		while (en.hasMoreElements())
		{
			String name = (String) en.nextElement();
			String value = request.getHeader(name);
			heads += "," + name + ":" + value;
		}
		if (!"".equals(heads))
		{
			heads = heads.substring(1);
		}
		syscfgMapper.addHistory(ip, userId, heads, powerId, menuId, url);
	}
	
	/**
	 * 获取真实ip
	 * @param request
	 * @return
	 * @Description:
	 */
	public String getIP(HttpServletRequest request){
		String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	       //根据网卡取本机配置的IP   
	       InetAddress inet=null;   
	    try {   
	     inet = InetAddress.getLocalHost();   
	    } catch (UnknownHostException e) {   
	     e.printStackTrace();   
	    }   
	    ipAddress= inet.getHostAddress();   
	      }   
	            
	     }   
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress; 
	}
}