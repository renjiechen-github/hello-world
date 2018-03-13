/**
 * date: 2017年4月1日
 */
package com.ycdc.huawei.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycdc.core.base.BaseController;
import com.ycdc.huawei.model.entity.CallEventInfo;
import com.ycdc.huawei.model.entity.Click2CallResult;
import com.ycdc.huawei.model.entity.ExtensionInfo;
import com.ycdc.huawei.model.entity.ExtraParamsters;
import com.ycdc.huawei.model.entity.NotifyCallEntity;
import com.ycdc.huawei.model.entity.StatusAndFeeResult;
import com.ycdc.huawei.service.HuaWeiServiceImpl;
import com.ycdc.huawei.service.IHuaWeiCallBackService;
import com.ycdc.huawei.service.IHuaWeiService;

/**
 * @name: HuaWeiController.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月1日
 */
@Controller
@RequestMapping("/huawei")
public class HuaWeiController extends BaseController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IHuaWeiCallBackService callBackService;
	
	@Autowired
	private IHuaWeiService service;
	
	public static void main(String[] args) {
		HuaWeiServiceImpl impl = new HuaWeiServiceImpl();
		String path = impl.downloadClick2CallRecord("https://117.78.29.67:443", "sp-svan4y4dilil3jeoehk3f9pnf3ng", "2017042006441512056577.wav");
		//logger.debug(path);
	}
	
	/**
	 * 华为发送短信回调接口
	 * @param body 参数
	 * @param request
	 * @return 
	 * @Description: 短信会逐个回调
	 */
	@RequestMapping(value = "/callback.do", method = RequestMethod.POST,consumes = "application/x-www-form-urlencoded", produces = "application/json;charset=UTF-8")
	private @ResponseBody Map<String, String> smsCallBack(@RequestBody String body, HttpServletRequest request) {
		// 解析回调参数
		logger.info("已收到短信回调");
		logger.info(body);
		String jsonString = "{"+body.replace("=", ":\"").replace("&", "\",")+"\"}";
		JSONObject json = JSON.parseObject(jsonString);
		Iterator<String> keys = json.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			String value = json.getString(key);
			try {
				// 使用utf-8反编码
				json.put(key, URLDecoder.decode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new HashMap<String, String>();
	}
	
	/**
	 * 虚拟号码拨号通话回调
	 * @param params
	 * @param request
	 * @return 
	 * @Description: 虚拟号码通话完毕时，华为将回调此方法
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/enabler/voice/notifyCallEvent/v1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Map<String, String> notifyCallEvent(@RequestBody Map<String, Object> params, HttpServletRequest request) {
		// 如果参数为空，抛出空指针异常
		if (params == null) {
			NullPointerException exception = new NullPointerException("接口入参格式/字段错误");
			throw exception;
		}
		logger.info(params.toString());
		// appKey不存在时则不进行操作
		if (!(HuaWeiServiceImpl.APP_KEY_VIR.equals(params.get("appKey")))) {
			logger.info("",params.get("appKey"));
			return new HashMap<>();
		}
		// 进行数据转模型（由于特殊格式原因，无法直接解析）
		NotifyCallEntity entity = new NotifyCallEntity();
		Map<String, Object> callEventMap = (Map<String, Object>)params.get("callEvent");
		if (callEventMap.get("extInfo") != null) {
			Map<String, Object> paraExtInfoMap = (Map<String, Object>)callEventMap.get("extInfo");
			List<Map<String, String>> extParamsList = (List<Map<String, String>>)paraExtInfoMap.get("extParas");
			ExtraParamsters extParams = new ExtraParamsters();
			for (int i = 0; i < extParamsList.size(); i++) {
				switch (extParamsList.get(i).get("key")) {
				case "ReleaseReason":
					extParams.setReleaseReason(extParamsList.get(i).get("value"));
					break;
				case "UniqueId":
					extParams.setUniqueId(extParamsList.get(i).get("value"));
					break;
				case "BindID":
					extParams.setBindID(extParamsList.get(i).get("value"));
					break;
				case "StartTime":
					extParams.setStartTime(extParamsList.get(i).get("value"));
					break;
				case "Duration":
					extParams.setDuration(extParamsList.get(i).get("value"));
					break;

				default:
					break;
				}
			}
			logger.info("收到了",extParamsList);
			ExtensionInfo extInfo = new ExtensionInfo((String)paraExtInfoMap.get("rawCalling"), (String)paraExtInfoMap.get("rawCallingNOA"), (String)paraExtInfoMap.get("rawCalledNOA"), (String)paraExtInfoMap.get("rawCalled"), extParams);
			entity.setCallEvent(new CallEventInfo((String)callEventMap.get("callIdentifier"), (String)callEventMap.get("notificationMode"), (String)callEventMap.get("calling"), (String)callEventMap.get("called"), (String)callEventMap.get("virtualNumber"), (String)callEventMap.get("event"), (String)callEventMap.get("timeStamp"), (String)callEventMap.get("isRecord"), extInfo));
		} else {
			entity.setCallEvent(new CallEventInfo((String)callEventMap.get("callIdentifier"), (String)callEventMap.get("notificationMode"), (String)callEventMap.get("calling"), (String)callEventMap.get("called"), (String)callEventMap.get("virtualNumber"), (String)callEventMap.get("event"), (String)callEventMap.get("timeStamp"), (String)callEventMap.get("isRecord"), new ExtensionInfo()));
		}
		callBackService.virtualCallBack(entity);
		return new HashMap<>();
	}
	
	/**
	 * 双呼点击拨号接口
	 * @param params
	 * @param request
	 * @return 
	 * @Description:
	 */
	@RequestMapping(value = "/click2Call.do", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody Click2CallResult triggerClick2Call(@RequestBody Map<String, String> params, HttpServletRequest request) {
		if (params.get("callerNbr") == null || params.get("calleeNbr") == null && params.get("bindNbr") == null) {
			logger.info("主叫或被叫或运营商号码不能为空");
			return null;
		}
		logger.info(params.toString());
		return service.triggerClick2call(params.get("callerNbr"), params.get("calleeNbr"), params.get("bindNbr"), params.get("callBackUrl"));
	}
	
	/**
	 * 双呼拨号状态回调和话单回调接口
	 * @param params
	 * @param request
	 * @return
	 * @Description:
	 */
	@RequestMapping(value = "/click2CallBack.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> click2CallBack(HttpServletRequest request) {
		InputStream is = null;
		BufferedReader reader = null;
        String line = null;   
        StringBuilder sb;
        try {      
        	is = request.getInputStream();
        	reader = new BufferedReader(new InputStreamReader(is));      
            sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {      
            	sb.append(line);      
            }
            logger.info("开始解析回调数据:"+sb.toString());
            StatusAndFeeResult params = JSONObject.toJavaObject(JSONObject.parseObject(sb.toString()), StatusAndFeeResult.class);
    		logger.info("解析完成: "+params);
    		callBackService.click2CallBack(params);
         } catch (Exception e) {      
             logger.error("解析错误1，",e);
         } finally {     
        	 try{
        		 if(reader != null ){
        			 reader.close();
        		 }
        	 }catch(Exception e){
        		 logger.error("解析错误2，",e);
        	 }
            try {
                 is.close();      
             } catch (Exception e) {      
                logger.error("解析错误3，",e);
            }      
        }
		return new HashMap<String, String>();
	}
}
