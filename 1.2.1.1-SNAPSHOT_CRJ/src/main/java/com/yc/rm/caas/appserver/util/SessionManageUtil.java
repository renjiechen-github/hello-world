package com.yc.rm.caas.appserver.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yc.rm.caas.appserver.model.user.Login;
import com.yc.rm.caas.appserver.model.user.User;

/**
 * @Description session管理
 * @author sunchengming
 * @date 2017年7月19日 下午3:49:54
 * 
 */
public class SessionManageUtil
{
	private static Map<String, Object> sessionMap = new HashMap<String, Object>();
	private static Map<String, Object> yetLoginMap = new HashMap<String, Object>();

	public static void setSession(String userPhone, Object value)
	{
		sessionMap.put(userPhone, value);
	}

	public static void removeSession(String userPhone)
	{
		HttpSession httpSession = getSession();
		sessionMap.remove(userPhone);
		try
		{
			httpSession.removeAttribute(userPhone);
		} catch (Exception e)
		{

		}
	}

	public static Login getLogin()
	{
		Login login = new Login();
		HttpSession session = getSession();
		try
		{
			login = (Login) session.getAttribute(getCookies("username"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return login;
	}

	public static User getUser()
	{
		User user = new User();
		HttpSession httpSession = getSession();
		try
		{
			Login login = (Login) httpSession.getAttribute(getCookies("username"));
			user = login.getUser();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return user;
	}

	public static String getCookies(String name)
	{
		HttpServletRequest request = getRequest();
		Cookie[] cookies = request.getCookies();
		if (cookies == null)
		{
			return "";
		}
		for (int i = 0; i < cookies.length; i++)
		{
			if (name.equals(cookies[i].getName()))
			{
				return cookies[i].getValue();
			}
		}
		return "";
	}

	public static Object getSession(String name)
	{
		return sessionMap.get(name);
	}

	public static Map<String, Object> getYetLogin(String name)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		Set<String> set = yetLoginMap.keySet();
		if (set.isEmpty())
		{
			return map;
		}
		Iterator<String> it = set.iterator();
		while (it.hasNext())
		{
			String name_ = it.next();
			if (name_.contains(name))
			{
				map.put(name_, yetLoginMap.get(name_));
			}
		}
		return map;
	}

	public static void addYetLogin(String name, Object val)
	{
		yetLoginMap.put(name, val);
	}

	public static void removeYetLogin(String name)
	{
		yetLoginMap.remove(name);
	}

	public static String getHeaders()
	{
		HttpServletRequest request = getRequest();
		Enumeration<String> e = request.getHeaderNames();
		String heads = "";
		while (e.hasMoreElements())
		{
			String name = e.nextElement();
			String value = request.getHeader(name);
			heads += name + ":" + value;
		}
		return heads;
	}

	public static String getIP()
	{
		HttpServletRequest request = getRequest();
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1"))
			{
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try
				{
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e)
				{
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15)
		{ // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0)
			{
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public static HttpSession getSession()
	{
		HttpSession session = null;
		try
		{
			session = getRequest().getSession();
		} catch (Exception e)
		{
		}
		return session;
	}

	public static HttpServletRequest getRequest()
	{
		ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attrs.getRequest();
	}
}
