package pccom.web.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;

import pccom.common.SQLconfig;
import pccom.common.util.Batch;
import pccom.common.util.BatchSql;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.StringHelper;
import pccom.web.beans.User;
import pccom.web.flow.base.FlowBase;
import pccom.web.listener.SessionManage;

/**
 * 接口集成对应的公共类
 * @author 雷杨
 *
 */
public class Base extends FlowBase {

//	public DBHelperSpring db;// 数据操作
//	public final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
//	public StringHelper str = new StringHelper();
	
//	@Resource(name="dbHelper")
//	public void setDb(DBHelperSpring db) {
//		this.db = db;
//	}
//	
//	public DBHelperSpring getDb() {
//		return db;
//	}
	
	/**
     * 獲取sql語句
     * @author 雷杨
     * @param key
     * @return
     */
    public String getSql(String key){
    	return SQLconfig.getSql(key);
    }
    
    /**
	 * 获取session中的用户信息
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request){
		String value = getCookie(request,Constants.COOKIE_CLIENT_ID);
		String username = getCookie(request,"username");
		Object o = SessionManage.getSession("account"+username+value);
		//logger.debug(SessionManage.getSessionMap());
		if(o == null){
			return new User();
		}else{
			return (User)o;
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
	
	/**
	 * 
	 * 
	 * @author 雷杨
	 * @param obj
	 * @param sql
	 * @param params
	 * @return 1成功 0失败
	 */
	public int update(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			((BatchSql)obj).addBatch(sql,params);
			return 1;
		} else if (obj instanceof Batch){
			try {
				return ((Batch)obj).update(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else{
//			logger.error("对象传递错误，无法查询结果："+sql);
			return db.update(sql,params); 
		}
	}
	
	
	/**
	 * 
	 * 
	 * @author 刘飞
	 * @param obj
	 * @param sql
	 * @param params
	 * @return 1成功 0失败
	 */
	public int queryForInt(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			((BatchSql)obj).addBatch(sql,params);
			return 1;
		} else if (obj instanceof Batch){
			try {
				return ((Batch)obj).queryForInt(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else{
			return db.queryForInt(sql,params);
//			logger.error("对象传递错误，无法查询结果："+sql);
//			return 0;
		}
	}
	
	/**
	 * 新增并获取自增长序列
	 * @author 雷杨
	 * @param obj
	 * @param sql
	 * @param params
	 * @return
	 */
	public int insert(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.insert(sql, params);
		} else if (obj instanceof Batch){
			try {
				int exc = ((Batch)obj).insert(sql, params);
				if(exc == -2){
					return -1;
				}
				return exc;
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else{
//			logger.error("对象传递错误，无法查询结果："+sql);
//			return -1;
			return db.insert(sql,params);
		}
	}
	
	public String queryString(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.queryForString(sql,params);
		} else if(obj instanceof Batch){
			return ((Batch)obj).queryForString(sql, params);
		} else {
//			logger.error("对象传递错误，无法查询结果："+sql);
			return db.queryForString(sql,params);
		}
	}
	
	public Map<String,Object> queryMap(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.queryForMap(sql,params);
		} else if(obj instanceof Batch){
			return ((Batch)obj).queryForMap(sql, params);
		} else {
//			logger.error("对象传递错误，无法查询结果："+sql);
			return db.queryForMap(sql, params);
		}
	}
	
	public List<Map<String,Object>> queryList(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.queryForList(sql,params);
		} else if(obj instanceof Batch){
			try {
				return ((Batch)obj).queryForList(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<Map<String,Object>>();
			}
		} else {
//			logger.error("对象传递错误，无法查询结果："+sql);
			return db.queryForList(sql,params);
		}
	}
	
}
