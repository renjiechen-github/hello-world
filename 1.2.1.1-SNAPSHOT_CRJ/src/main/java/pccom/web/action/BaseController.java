package pccom.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import pccom.common.util.RequestHelper;
import pccom.common.util.StringHelper;



/**
 * 控制组件基类
 * @author chang
 * @createDate Mar 1, 2013
 * @description
 */
public class BaseController {
	public static final String AJAX_MESSAGE = "success";
	public static final String SUCCESS = "true";
	public static final String FAIL = "fail";
	
	public StringHelper str = new StringHelper();
	public RequestHelper req = new RequestHelper();
	
	protected Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	
	public void debug(String message) {
		logger.debug(message);
	}
	public void error(String message) {
		logger.error(message);
	}
	public void info(String message) {
		logger.info(message);
	}
	
	@Autowired
	public HttpServletRequest request;
	
	public void renderText(HttpServletResponse response, String result) throws IOException {
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-store"); 
		response.setHeader("Pragma", "no-cache"); 
		response.setDateHeader("Expires", 0); 
		out.print(result);
		out.flush();
		out.close();
	}
	
	private MessageSource messageSource;
	
	@Resource(name="messageSource")
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(String key) {
		return this.messageSource.getMessage(key, null, null);
	}
	
	public void writeText(Object data, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeJsonData(Object data, HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");
			if (data instanceof Map) {
				response.getWriter().print(JSONObject.fromObject(data));
			} else if (data instanceof List) {
				JSONArray array = JSONArray.fromObject(((List) data).toArray());
				response.getWriter().print(array);
			} else {
				response.getWriter().print(JSONObject.fromObject(data));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
