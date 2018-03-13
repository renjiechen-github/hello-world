package pccom.web.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pccom.common.SQLconfig;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;

/**
 * 系統默認配置信息
 * @author 雷杨
 *
 */
public class SystemConfig {

	private static List<Map<String,Object>> confList = new ArrayList<Map<String,Object>>();
	
	/**
	 * 開始設置系統基本信息
	 * @author 雷杨
	 */
	public static void setSystemConf(){
		confList.clear();
		String sql = SQLconfig.getSql("systemconfig.queryparamforinit");
		DBHelperSpring db  = (DBHelperSpring) SpringHelper.getBean("dbHelper");
		confList = db.queryForList(sql);
	}
	
	/**
	 * 根據傳入的key獲取參數值
	 * @author 雷杨
	 * @param key
	 */
	public static String getValue(String keyName){
		for(int i=0;i<confList.size();i++){
			String key = StringHelper.get(confList.get(i), "KEY");
			String value = StringHelper.get(confList.get(i), "VALUE");
			if(key.equals(keyName)){
				return value;
			}
		}
		return "";
	}
	
}
