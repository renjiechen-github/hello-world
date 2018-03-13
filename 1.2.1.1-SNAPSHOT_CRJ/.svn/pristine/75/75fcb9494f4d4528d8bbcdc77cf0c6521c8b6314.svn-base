package pccom.common.util.aes;

import org.slf4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Main {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(Main.class);
	public static void main(String[] args) {
		try {
			String token = "ycroom_financial";//双方规定的token
			String timeStamp = String.valueOf(System.currentTimeMillis());//时间戳
			String nonce = "2121";//随机字符串
			JSONArray json1 = new JSONArray();
			
			JSONObject json = new JSONObject();
			json.put("plat_time","201609182025");
			json.put("cost","253.00");
			json.put("settle_type","1");
			json.put("bank_code","42015532521515255");
			json.put("bank_username","张三");
			json.put("bank_name","建设银行");
			json.put("project_name","项目");
			json.put("remark","说明");
			json.put("project_code","132154");
			json.put("detail_id","121");
			
			JSONArray jsona = new JSONArray();
			JSONObject o1 = new JSONObject();
			o1.put("month", "201502");
			o1.put("cost", "252.00");
			jsona.add(o1);
			JSONObject o2 = new JSONObject();
			o2.put("month", "201502");
			o2.put("cost", "252.00");
			jsona.add(o2);
			json.put("stages", jsona);
			json1.add(json);
			json1.add(json);
			
			logger.debug(json1.toString());
			//加密
			String result = new YCBizMsgCrypt(token).encryptMsg(json1.toString(), timeStamp, nonce);
			logger.debug(result);
			token = "ycroom_financial";//双方规定的token
			//解密
			JSONArray jmJson = new YCBizMsgCrypt(token).decryptJSONArrayMsg(result);
			logger.debug(jmJson.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
