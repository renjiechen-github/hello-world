package pccom.web.server.sys.power;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import pccom.common.util.Batch;
import pccom.web.server.BaseService;

@Service("powerService")
public class PowerService extends BaseService {
	/**
	 * 根据菜单主键查询菜单权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectPower(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "id");
		String sql = getSql("power.selectPower");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap
				.put("list", JSONArray.fromObject(db.queryForList(sql,
						new Object[] { id })));
		return returnMap;
	}

	/**
	 * 根据菜单主键添加菜单权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object savePower( final HttpServletRequest request,
			HttpServletResponse response) {
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String id = req.getAjaxValue(request, "id");
				String role = req.getAjaxValue(request, "role");
				String power = req.getAjaxValue(request, "power");
				String del=getSql("power.delete");
				this.update(del, new Object[]{id,power});
				String[] roles = role.split(",");//权限
				String sql= getSql("power.insertPower");
				logger.debug("roles:"+roles.length);
				for(int i=0;i<roles.length;i++){
					if(!"null".equals(roles[i])&&roles[i] != null&&!"".equals(roles[i])){
						this.update(sql, new Object[]{roles[i],id,power});
					}
				}
				return 1;
			}
		});
		return getReturnMap(i);
	}
	/**
	 * 查询菜单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectPowers(HttpServletRequest request,
			HttpServletResponse response) {
		//获取前台传入的主键
		String id = req.getAjaxValue(request, "id");
		String sql = getSql("power.selectPowers");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,new Object[] { id })));
		return returnMap;
	}
	/**
	 * 查询菜单信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object startPower(HttpServletRequest request,
			HttpServletResponse response) {
		//获取前台传入的主键
		String id = req.getAjaxValue(request, "id");
		String sql = getSql("power.updatestates");
		return getReturnMap(db.update(sql,new Object[] { id }));
	}
}
