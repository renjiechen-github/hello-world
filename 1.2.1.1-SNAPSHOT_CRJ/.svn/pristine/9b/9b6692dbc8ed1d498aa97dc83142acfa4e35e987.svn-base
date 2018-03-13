package pccom.common;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import pccom.common.util.Constants;
import pccom.common.util.FileHellp;

/**
 * 解析所有sql语句信息
 * @author 雷杨
 *
 */
public class SQLconfig {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(SQLconfig.class);
	
	private static JSONObject sqlJson = null;
	
	/**
	 * 初始化
	 * @author 雷杨
	 */
	public static void init(){
		logger.debug("开始加载sql配置数据文件");
		sqlJson = null;
		String pathname =   SQLconfig.class.getResource("/sqldata.json" ).getPath();
    	if(System.getProperty("os.name").toLowerCase().indexOf("win") > -1){
			pathname = pathname.substring(1);
		}
		logger.debug("读取配置文件："+pathname);
		sqlJson = JSONObject.fromObject(FileHellp.readFileByLines(SQLconfig.class.getResource("/sqldata.json").getFile ()));
		logger.debug("结束加载sql配置数据文件");
	}
	
	/**
	 * 根据key获取sql语句
	 * @author 雷杨
	 * @param keyName
	 * @return
	 */
	public static String getSql(String keyName){
		String result = "";
		try{
			String[] keys = keyName.split("\\.");
			int cnt = keys.length;
			JSONObject obj = sqlJson;
			//logger.debug(sqlJson);
			for(int i=0;i<cnt;i++){
				String key = keys[i];
				//logger.debug(key+"--:"+obj.containsKey(key));
				if(i != cnt - 1){
					obj = obj.getJSONObject(key);
				}else{
					result = obj.getString(key);
				}
			}
		}catch(Exception e){
			logger.debug("获取sql语句出现错误，["+e.getMessage()+"]请核对是否存在对应的参数值："+keyName);
			logger.debug("获取sql语句出现错误，["+e.getMessage()+"]请核对是否存在对应的参数值："+keyName);
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		String json = FileHellp.readFileByLines("E:\\Users\\Administrator\\Workspaces\\MyEclipse 10\\WXWeb\\src\\sqldata.json");
		logger.debug(json);
		Map<String,String> map = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(json),Map.class);
		//logger.debug(map);
		logger.debug(map.get("sasa"));
	}
	
}
