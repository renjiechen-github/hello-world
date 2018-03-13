package pccom.web.interfaces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.server.BaseService;

/**
 * 与金蝶对接接口
 * @author admin
 *
 */
@Controller
public class EasFinancialServer extends BaseService {

	private String token = "jd_ycqwj";
	
	public void writeJsonData(Object data, HttpServletResponse response) {
		try {
			response.setContentType("text/plain;charset=" + "UTF-8"); 
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
	
	/**
	 * 获取当前时间
	 * @param request
	 * @param response
	 */
	@RequestMapping (value = "interfaces/easFinancial/getData.do")
	public void getData(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> map = getReturnMap(1);
		map.put("data", System.currentTimeMillis());
		writeJsonData(map, response);
	}
	
	/**
	 * 
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "interfaces/easFinancial/gethouse.do")
	public void gethouse(HttpServletRequest request, HttpServletResponse response){
		Map<String, String> params = new HashMap<String, String>();
        if(null != request){
            Set<String> paramsKey = request.getParameterMap().keySet();
            for(String key : paramsKey){
                params.put(key, request.getParameter(key));
            }
        }
        params.remove("sign");
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }
        
        String str = content.toString()+"&key="+token;
        
        String timestamp = req.getAjaxValue(request, "timestamp");//时间戳
		String sign = req.getAjaxValue(request, "sign");//编码
		
		
        
	}
}
