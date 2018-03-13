package pccom.web.listener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;

/**
 * 管理session参数值
 * @author 雷杨
 *
 */
public class SessionManage {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(SessionListener.class);
	private static Map<String,Object> sessionMap = new HashMap<String, Object>();
	private static Map<String,Object> yetLoginMap = new HashMap<String, Object>();
	
	public static Object checkExistsLogin(String name){
		return yetLoginMap.get(name);
	}
	
	public static Map<String,Object> getYetLogin(String name){
		Map<String,Object> map = new HashMap<String,Object>();
		Set<String> set = yetLoginMap.keySet();
		if(set.isEmpty()){
			return map;
		}
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String name_ = it.next();
			if(name_.contains(name)){
				map.put(name_, yetLoginMap.get(name_));
			}
		}
		return map;
	}
	
	public static void addYetLogin(String name,Object val){
		yetLoginMap.put(name, val);
	}
	
	public static void removeYetLogin(String name){
		yetLoginMap.remove(name);
	}
	
	public static void setSession(HttpSession session,String name,Object value){
		sessionMap.put(name, value);
		try{
			session.setAttribute(name, value);
		}catch (Exception e) {
			
		}
	}
	
	public static void updateSession(HttpSession session,String name,Object value){
		sessionMap.put(name, value);
		try{
			session.setAttribute(name, value);
		}catch (Exception e) {
			
		}
	}
	
	public static void removeSession(HttpSession session,String name){
		sessionMap.remove(name);
		try{
			session.removeAttribute(name);
		}catch (Exception e) {
			
		}
	}
	
	/**
	 * 获取session名称是否已经存在，如果存在就返回session名称
	 * @author 雷杨
	 * @param name
	 * @return
	 */
	public static String getSessionName(String name){
		logger.debug(sessionMap.toString());
		Set<String> set = sessionMap.keySet();
		if(set.isEmpty()){
			return "";
		}
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String name_ = it.next();
			if(name_.contains(name)){
				return name_;
			}
		}
		return "";
	}
	
	public static Object getSession(String name){
		return sessionMap.get(name);
	}
	
	public static Map<String,Object> getSessionMap(){
		return sessionMap;
	}
	
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("18252200", "12121");
		map.put("18252200", "12122");
		map.put("18252200", "12123");
		logger.debug(map.toString());
	}
	
}
