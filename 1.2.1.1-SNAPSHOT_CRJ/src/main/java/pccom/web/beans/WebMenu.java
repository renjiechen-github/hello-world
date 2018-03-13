package pccom.web.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pccom.common.SQLconfig;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;


/**
 * 缓存mysql中的菜单类
 * @author 雷杨
 *
 */
public class WebMenu {

	/**
	 * 菜单静态实例
	 */
	public static List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
	
	public void execute(){
		DBHelperSpring db  = (DBHelperSpring) SpringHelper.getBean("dbHelper");
		//取出所有1级菜单
		String sql = SQLconfig.getSql("menu.queryUserMenu");
		List<Map<String,Object>> list = db.queryForList(sql,new Object[]{0});
		//进行循环获取1级菜单内容
		
	}
	
}
