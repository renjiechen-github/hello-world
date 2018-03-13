package pccom.common.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import pccom.web.beans.Login;
import pccom.web.beans.User;
import pccom.web.listener.SessionManage;
import pccom.common.SQLconfig;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SignUtil;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;

/**
 * 登陆统一检验
 * @author chang
 * @createDate 2014-11-14
 */
public class LoginFilter implements Filter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void setFilterConfig(FilterConfig config) {
		this.config = config; 
	}

	public FilterConfig getFilterConfig() {
		return config; 
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//进行统一检验是否已经登陆过，如果没有登陆需要进行登陆操作
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		String url = req.getRequestURI();
		//设置项目名称
		request.setAttribute("app",req.getContextPath());
		
		if(url.lastIndexOf(".do")>0){
		}
		
		if(url.indexOf("interfaces") > -1){
			chain.doFilter(req, rep);
			return;
		}
		
		if (url.indexOf("cancelLease/") > -1 ||
            url.indexOf("flow/getPayList.do") > -1 ||
            url.indexOf("flow/getFinancialList.do") > -1 ||
            url.indexOf("flow/getFinanceTable.do") > -1 ||
            url.indexOf("flow/getFinanceDetails.do") > -1 ||
            url.indexOf("CertificateLeave/getList.do") > -1 ||
            url.indexOf("rankHouse/loadAgreementInfo.do") > -1 ||
            url.indexOf("login/loadUser.do") > -1 ||
            url.indexOf("cleaningOrder/") > -1 ||
            url.indexOf("complaintOrder/") > -1 ||
            url.indexOf("houseLookingOrder/") > -1 ||
            url.indexOf("cascading/") > -1 || 
            url.indexOf("dingding/") > -1 ||
            url.indexOf("huawei/") > -1 || 
            url.indexOf("sys") > -1 ||
            url.indexOf("livingProblemOrder/") > -1 ||
            url.indexOf("otherOrder/") > -1 ||
            url.indexOf("queryItemAll") > -1 ||
            url.indexOf("ownerRepairOrder/") > -1 ||
            url.indexOf("getUserNameById.do") > -1 ||
            url.indexOf("repairOrder/") > -1 ||
            url.indexOf("routineCleaningOrder/") > -1 ||
            url.indexOf("getMangerList.do") > -1 ||
            url.indexOf("agreementMge/agreementInfo.do") > -1 ||
            url.indexOf("owerRepairOrder") > -1 ||
            url.indexOf("workOrder/") > -1 ||
            url.indexOf("ownerCancelLeaseOrder/") > -1 ||
            url.indexOf("/financial/type/getTypeListInfo.do") > -1 ||
            url.indexOf("/financial/type/getTypeList.do") > -1 ||
            url.indexOf("/CertificateLeave/create.do") > -1 ||
            url.indexOf("/CertificateLeave/delete.do") > -1 ||
            url.indexOf("/datapermission/judgeExportButton.do") > -1) {
            chain.doFilter(req, rep);
            return;
        }
		
		//logger.debug(url);  
		if(url.indexOf("client/pages") > -1 || url.indexOf("mobile/") > -1 || url.indexOf("sys/queryArea.do") > -1 || url.indexOf("sys/queryItem.do") > -1 || url.indexOf("sys/queryMcate.do") > -1 || url.indexOf("appserver/") > -1 || url.indexOf("/ca/dealRankAgreementInfo.do") > -1 || url.indexOf("rankHouse/caCallBackUrl.do") > -1 || url.indexOf("rankHouse/caDealAgreement.do") > -1 || url.indexOf("dealCaAgreement.jsp") > -1 || url.indexOf("dealCaHouseAgreement.jsp") > -1)
		{
//			logger.debug("cccccc");
			chain.doFilter(req, rep);
			return ;
		}
		String accept = getHeader(req,"Accept");
		if((url.lastIndexOf(".do")>0 || url.lastIndexOf(".html")>0)&&url.lastIndexOf("login.do")==-1){
			logger.debug(url); 
			DBHelperSpring db  = (DBHelperSpring) SpringHelper.getBean("dbHelper");
			User user = getUser(req);
			//进行记录访问信息
			String menu_id = "";
			String power_id = "";
			if(url.lastIndexOf(".html")>0){//访问的是菜单链接 
				//查询出菜单id
				menu_id = db.queryForString(SQLconfig.getSql("menu.getMenuId"),new Object[]{url.substring(1).trim().replaceAll("//", "/")});
			}else{
				Map<String,Object> map = db.queryForMap(SQLconfig.getSql("menu.getPowerMenu"),new Object[]{url.substring(1).trim().replaceAll("//", "/")});
				power_id = StringHelper.get(map, "power_id");
				menu_id = StringHelper.get(map, "menu_id");
			}
			Enumeration<String> e = req.getHeaderNames();
			String heads = "";
			while(e.hasMoreElements()){
				String name = e.nextElement();
				String value =req.getHeader(name);
				heads += name+":"+value;
			}
			//记录访问日志
			db.update(SQLconfig.getSql("systemconfig.visit"),new Object[]{getIP(req),user.getId(),heads,power_id,menu_id,user.getRoles(),url});
		}
		if(accept!= null && accept.indexOf("json")>0&&url.lastIndexOf(".do")>0&&url.lastIndexOf("login.do")==-1){//只检查json数据请求信息
			//是否已经登陆
			String value = getCookie(req,Constants.COOKIE_CLIENT_ID);
			String username = getCookie(req,"username");
			Object o = SessionManage.getSession(username);
			Object obj = req.getSession().getAttribute("user");
			
			if (obj == null) 
			{
				if(o == null){//未登陆
					logger.debug(url+"未登陆10"+"---url:"+url);
					result(-99999,rep);
				}else{//存在登陆信息。
					//看是否是相同的cookies
					Login login = (Login)o;
					User user = login.getUser();
					if(!login.getCookies().equals(value)){//不相等，需要看是否存在已登陆信息被挤下
						Map<String,Object> yetMap = SessionManage.getYetLogin(username);
						logger.debug("yetMap:"+yetMap);
						if(yetMap.isEmpty()){//空说明没有登陆信息
							logger.debug(url+"未登陆11"+"---url:"+url);
							result(-99999,rep);
						}else{
							Set<String> set = yetMap.keySet();
							Iterator<String> it = set.iterator();
							boolean truth = false;
							while(it.hasNext()){
								String name_ = it.next();
								Login yetlogin = (Login)yetMap.get(name_);
								if(yetlogin.getCookies().equals(value)){//存在对应的cookies说明是被挤下
									//移除被挤下的信息
									SessionManage.removeYetLogin(name_);
									logger.debug(url+"被挤下"+"---url:"+url);
									truth = true;
									result(-99997,rep);
								}
							}
							if(!truth){
								chain.doFilter(req, rep);
							}
						}
					}else{//相等说明是正常的登陆信息
						String root = Constants.root;
						if("/".equals(Constants.root)){
							root = "";
						}
						logger.debug("url--:"+url.replace(root, "").substring(1));
						if(checkPower(url.replace(root, "").substring(1), user.getRoles())){
							chain.doFilter(req, rep);
						}else{//没有权限访问
							logger.debug(url+"没有访问权限"+"---url:"+url);
							result(-99998,rep);
						}
					}
				}
			}
			else
			{
				chain.doFilter(req, rep);
			}
		}else{
			chain.doFilter(req, rep);
		}
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
	 * 校验是否有权限访问链接
	 * @author 雷杨
	 * @param url
	 * @param userid
	 * @return
	 */
	public boolean checkPower(String url,String roleids){
		DBHelperSpring db  = (DBHelperSpring) SpringHelper.getBean("dbHelper");
		//先看链接是否在表中存在
		String getPowersql = SQLconfig.getSql("menu.getpower");
		String power_id = db.queryForString(getPowersql,new Object[]{url});
		logger.debug("校验是否有权限访问链接1:"+StringHelper.getSql(getPowersql, new Object[]{url}));
		if("".equals(power_id)){//没有对应的链接信息可以直接访问
			return true;
		}else{
			String sql = SQLconfig.getSql("menu.checkPower").replace("##", roleids);
			logger.debug("校验是否有权限访问链接2:"+StringHelper.getSql(sql, new Object[]{power_id}));
			if(db.queryForInt(sql,new Object[]{power_id}) == 0){
				return false;
			}else{
				return true;
			}
		}
	}
	
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
	
	public String getHeader(HttpServletRequest request,String name){
		try{
			return request.getHeader(name);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void result(int state,HttpServletResponse response){
		try {
			response.setCharacterEncoding("UTF-8");
			JSONObject json = new JSONObject();
			json.put("state", state);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
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
		if(cookies == null){
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
	 * 获取用户的唯一标识，根据头文件来获取唯一标示
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public void setClientId(HttpServletRequest request,HttpServletResponse response){
		//初次访问给页面生成一个cookies记录用户唯一标示
		String ycqwjid = getCookie(request, Constants.COOKIE_CLIENT_ID);
		Cookie cookie = null;
		if(!"".equals(ycqwjid)){
			cookie = new Cookie(Constants.COOKIE_CLIENT_ID, ycqwjid);
		}else{
			cookie = new Cookie(Constants.COOKIE_CLIENT_ID, SignUtil.md5(new Random().nextInt()+""+System.currentTimeMillis()+request.getHeader("User-Agent")));
		}
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
	}

	public void destroy() {
		config = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	public FilterConfig config;

}
