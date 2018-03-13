package pccom.common.filter;

import java.io.IOException;
import java.util.Random;

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
import org.slf4j.MDC;

import pccom.common.util.Constants;
import pccom.common.util.SignUtil;

/**
 * 防止xss攻击 替换请求参数中的script关键字符
 *
 * @author chang
 * @createDate 2014-11-14
 */
public class XSSFilter implements Filter {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public void setFilterConfig(FilterConfig config) {
        this.config = config;
    }

    public FilterConfig getFilterConfig() {
        return config;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
//        System.out.println("pccom.common.filter.XSSFilter.doFilter()1:"+String.valueOf(req.getLocalPort()));
//        System.out.println("pccom.common.filter.XSSFilter.doFilter()2:"+String.valueOf(req.getRemotePort()));
//        System.out.println("pccom.common.filter.XSSFilter.doFilter()3:"+String.valueOf(req.getServerPort()));
        MDC.put("port", String.valueOf(((HttpServletRequest)request).getLocalPort()));
        
        //logger.debug("?????????????????????");
        //检测生成对应的客户端唯一标示
        setClientId((HttpServletRequest) request, (HttpServletResponse) response);

        XSSHttpServletRequestWrapper xssRequest = new XSSHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    /**
     * 获取cookie
     *
     * @author 雷杨
     * @param request
     * @param name
     * @return
     */
    public String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return "";
        }
        for (int i = 0; i < cookies.length; i++) {
            if (name.equals(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return "";
    }

    /**
     * 获取用户的唯一标识，根据头文件来获取唯一标示
     *
     * @author 雷杨
     * @param request
     * @return
     */
    public void setClientId(HttpServletRequest request, HttpServletResponse response) {
        //初次访问给页面生成一个cookies记录用户唯一标示
        String ycqwjid = getCookie(request, Constants.COOKIE_CLIENT_ID);
        Cookie cookie = null;
        if (!"".equals(ycqwjid)) {
            cookie = new Cookie(Constants.COOKIE_CLIENT_ID, ycqwjid);
        } else {
            cookie = new Cookie(Constants.COOKIE_CLIENT_ID, SignUtil.md5(new Random().nextInt() + "" + System.currentTimeMillis() + request.getHeader("User-Agent")));
        }
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
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
