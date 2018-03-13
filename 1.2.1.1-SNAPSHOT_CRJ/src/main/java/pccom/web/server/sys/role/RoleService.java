package pccom.web.server.sys.role;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import pccom.common.util.Batch;
import pccom.web.server.BaseService;
@Service
public class RoleService extends BaseService{
	/**
	 * 角色管理
	 * @param request
	 * @param response
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		List<String> params = new ArrayList<String>();
		String sql = getSql("role.main");
		getPageList(request,response,sql,params.toArray() );
	}
	/**
	 * 查询菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void selectmenu(HttpServletRequest request, HttpServletResponse response) {
		String sql = getSql("role.selectmenu");
		try {
			String role_id = req.getAjaxValue(request, "role_id");
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			String result = JSONArray.fromObject(db.queryForList(sql, new Object[] {role_id})).toString();
			response.getWriter().print(result.replace("\"{", "{").replace("}\"","}").replaceAll("\\\\",""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void selectpower(HttpServletRequest request,HttpServletResponse response) {
		String sql = getSql("role.selectpower");
		try {
			String id = req.getAjaxValue(request, "id");
			String role_id = req.getAjaxValue(request, "role_id");
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			
			String result = JSONArray.fromObject(db.queryForList(sql, new Object[] {role_id, id })).toString();
			logger.debug(result);
			
			response.getWriter().print(result.replace("\"{", "{").replace("}\"","}").replaceAll("\\\\",""));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 更新角色权限
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Object updaterole(final HttpServletRequest request,HttpServletResponse response) {
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String role_id = req.getAjaxValue(request, "role_id");//获取角色主键
				String arr = req.getAjaxValue(request,    "parr");//获取权限主键
				logger.debug("arr = " + arr);
				String insertPower=getSql("power.insertPower");//新增权限信息
				String del=getSql("role.delete");
				this.update(del, new Object[]{role_id});
				if (!"".equals(arr)) {
					List<Map<String, Object>>	menu_idList = this.queryForList(getSql("role.selectmenuid"), new Object[]{arr});
					logger.debug("menu_idList = " + menu_idList);
					
					String superids = "";
					String[] powerids_ = arr.split(",");
					
						for (Map<String, Object> map : menu_idList) {
							String menu_id = String.valueOf(map.get("menu_id"));
							this.update(insertPower, new Object[]{role_id,menu_id,menu_id});
							superids +=  menu_id + ",";
							for (int i = 0; i < powerids_.length; i++) {//保存
								this.update(insertPower, new Object[]{role_id,menu_id,powerids_[i]});
								this.update(insertPower, new Object[]{role_id,powerids_[i],powerids_[i]});
							}
						}
					superids = superids.substring(0, superids.length() - 1);
					logger.debug("superids = " + superids);
					List<Map<String, Object>> superidList = this.queryForList(getSql("role.selectsuperid"), new Object[]{superids});
					for (Map<String, Object> map : superidList) {
						String superid = String.valueOf(map.get("super_id"));
						this.update(insertPower, new Object[]{role_id,superid,superid});
					}
				}
				return 1;
			}
		});
		return getReturnMap(i);
	}
	/**
	 * 新增角色权限
	 * 
	 * @param request
	 * @param response
	 * @return 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  Object saverole(final HttpServletRequest request,HttpServletResponse response) {
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String powerids = req.getAjaxValue(request, "ids");//获取权限主键
				String role_name = req.getAjaxValue(request, "role_name");//获取角色信息
				String role_decs= req.getAjaxValue(request, "role_decs");//获取角色职能
				String selectmenuid= getSql("role.selectmenuid");//查询菜单主键
				String insertrole= getSql("role.insertrole");//新增角色
				String insertPower=getSql("power.insertPower");//新增权限信息
				//新增角色信息
				int keyId = this.insert(insertrole, new Object[] {role_name,role_decs});
				if (!"".equals(powerids)) {
					List<Map<String, Object>>	menu_idList = this.queryForList(getSql("role.selectmenuid"), new Object[]{powerids});
					logger.debug("menu_idList = " + menu_idList);
					
					String superids = "";
					String[] powerids_ = powerids.split(",");
					
						for (Map<String, Object> map : menu_idList) {
							String menu_id = String.valueOf(map.get("menu_id"));
							this.update(insertPower, new Object[]{keyId,menu_id,menu_id});
							superids +=  menu_id + ",";
							for (int i = 0; i < powerids_.length; i++) {//保存
								this.update(insertPower, new Object[]{keyId,menu_id,powerids_[i]});
								this.update(insertPower, new Object[]{keyId,powerids_[i],powerids_[i]});
							}
						}
					superids = superids.substring(0, superids.length() - 1);
					logger.debug("superids = " + superids);
					List<Map<String, Object>> superidList = this.queryForList(getSql("role.selectsuperid"), new Object[]{superids});
					for (Map<String, Object> map : superidList) {
						String superid = String.valueOf(map.get("super_id"));
						this.update(insertPower, new Object[]{keyId,superid,superid});
					}
				}
				return 1;
			}
		});
		return getReturnMap(i);
	}
	/**
	 * 根据
	 * @param request
	 * @param response
	 * @return
	 */
	public Object findpower(HttpServletRequest request,HttpServletResponse response) {
				//获取前台传入的主键
				String id = req.getAjaxValue(request, "id");
				String sql = getSql("role.findpower");
				Map<String, Object> returnMap = getReturnMap("1");
				returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,new Object[] { id })));
				return returnMap;
	}
}
