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

import pccom.common.util.SignUtil;
import pccom.web.server.BaseService;

/**
 * 360同步接口
 * @author admin
 *
 */
@Controller
public class JdSyn  extends BaseService {
	public final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());
	private String token = "jdhouse";
	
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
	@RequestMapping (value = "interfaces/jdSyn/getData.do")
	public void getData(HttpServletRequest request, HttpServletResponse response){
		//
		
		Map<String,Object> map = getReturnMap(1);
		map.put("data", System.currentTimeMillis());
		writeJsonData(map, response);
	}
	
	/**
	 * 金蝶财务通知
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping (value = "interfaces/jdSyn/financeNotification.do")
	public void gethouse(HttpServletRequest request, HttpServletResponse response){
		//获取传递数据拼接信息
		Map<String, String> params = new HashMap<String, String>();
        if(null != request){
            Set<String> paramsKey = request.getParameterMap().keySet();
            for(String key : paramsKey){
                params.put(key, request.getParameter(key));
            }
        }
        
        logger.debug("金蝶推送数据信息："+params); 
        
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
        
		String id = req.getAjaxValue(request, "id");//fina_id
		String timestamp = req.getAjaxValue(request, "timestamp");//时间戳
		String sign = req.getAjaxValue(request, "sign");//编码
		String time = req.getAjaxValue(request, "time");//支付时间
		
		try{
			//检查timestamp是否过期  超过10分钟无效
			if((System.currentTimeMillis()-Long.valueOf(timestamp))/1000/60 > 2){//timestamp时间过期
				writeJsonData(getReturnMap(-1), response);
				return ;
			}
		}catch(Exception e){
			e.printStackTrace();
			writeJsonData(getReturnMap(-2), response);
			return ;
		}
		
		if(!SignUtil.md5(str).equals(sign)){//签名失败
			writeJsonData(getReturnMap(-3), response);
			return;
        }
		
		String[] ids = id.split(",");
		String[] times = time.split(",");
		int cnt = ids.length;
		int cnttime = times.length;
		if(cnt != cnttime){
			writeJsonData(getReturnMap(-7), response);
			return ;
		}
		//检查id那些是不存在对应的通知信息
		if(cnt > 100){
			writeJsonData(getReturnMap(-4), response);
			return;
		}
		
		String sql = "SELECT COUNT(1) FROM yc_notiy_info a WHERE a.resource_id = ? AND a.notiy_state = 2 AND a.notiy_type = 7";
		for(int i=0;i<cnt;i++){
			if(db.queryForInt(sql,new Object[]{ids[i]}) == 0){
				writeJsonData(getReturnMap(-5), response);
				return;
			}
		}
		
		sql = "update financial_payable_tab a SET a.status = 1,a.pay_time = now() WHERE a.id IN("+id+")";
		int exc = db.update(sql);
		if(exc == 1){
			Map<String,Object> map = getReturnMap(1);
			writeJsonData(map, response);
		}else{
			writeJsonData(getReturnMap(-6), response);
		}
		
	}
	
	public static void main(String[] args) {
//		logger.debug(SignUtil.md5("1111"));
//		String url = "http://192.168.90.205/interfaces/jdSyn/financeNotification.do";
//		
//		Map<String,Object> map = new HashMap<>();
//		map.put("id", "114206");
//		map.put("timestamp", "1489649746354");
//		
//		StringBuffer content = new StringBuffer();
//		List<String> keys = new ArrayList<String>(map.keySet());
//        Collections.sort(keys);
//        for (int i = 0; i < keys.size(); i++) {
//            String key = keys.get(i);
//            String value = String.valueOf(map.get(key));
//            content.append((i == 0 ? "" : "&") + key + "=" + value);
//        }
//        
//        String str = content.toString()+"&key=jdhouse";
//		logger.debug(str);
//		String sign = SignUtil.md5(str);
//		map.put("sign", sign);
//		
//		logger.debug(url+"?"+content.toString()+"&sign="+sign);
	}
	
}
