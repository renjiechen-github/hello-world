package pccom.web.server.sys.menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import pccom.common.util.Batch;
import pccom.web.server.BaseService;
import sun.util.logging.resources.logging;

@Service("menuService")
public class MenuService extends BaseService {

	/**
	 * 查询所有的菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectMenu(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("sys.selectmenu");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list",JSONArray.fromObject(db.queryForList(sql, new Object[] {})));
		return returnMap;
	}

	/**
	 * 保存菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object saveMenu(final HttpServletRequest request,
			HttpServletResponse response) {
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String superid = req.getAjaxValue(request, "superId");//获取父级主键
				String menuName = req.getAjaxValue(request, "menuName");//获取菜单名称
				String menuUrl = req.getAjaxValue(request, "menuUrl");//获取菜单的url
				String order_id = req.getAjaxValue(request, "order_id");//获取菜单的顺序
				String Namepower = req.getAjaxValue(request, "Namepower");//获取权限的名称
				String URLpower = req.getAjaxValue(request, "URLpower");//获取权限的url
				
				String superId = null;
				String isnext = "0";
				String updatesuperid = getSql("sys.updatesuperid");
				String menu_level = "1";
				if (superid == "") {//无父级菜单
					superId = "0";
				} else {//存在父级
					superId = req.getAjaxValue(request, "superId");
					menu_level = this.queryForString(getSql("sys.getSupperLevel"),new Object[]{superId});
					//更新父级的isnext
					this.update(updatesuperid, new Object[] { superId });
				}
				
				String sqlMenu = getSql("sys.insert");
				//获取插入的菜单主键
				int keyId = this.insert(sqlMenu, new Object[] { menuName,menuUrl, superId, isnext,menu_level,order_id});
				if(keyId <= 0){
					return 0;
				}
				String sqlPower = getSql("sys.insertpower");
				if (Namepower == "") {//无权限数据
					this.update(sqlPower, new Object[] { "", "", keyId });
				} else {//有权限数据
					String[] powerName = Namepower.split(",");//权限名称
					String[] powerURL = URLpower.split(",");//权限url
					for (int i = 0; i < powerName.length; i++) {
						this.update(sqlPower, new Object[] { powerName[i],powerURL[i], keyId });
					}
				}
				return 1;
			}
		});
		return getReturnMap(i);
	}

	/**
	 * 查询父级菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void select(HttpServletRequest request, HttpServletResponse response) {
		String sql = getSql("sys.select");
		try {
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			String result = JSONArray.fromObject(db.queryForList(sql, new Object[] {})).toString();
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询子级菜单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public void selectchildren(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("sys.selectchildren");
		try {
			String id = req.getAjaxValue(request, "id");
			response.setHeader("Content-Type", "application/json;charset=UTF-8");
			String result = JSONArray.fromObject(db.queryForList(sql, new Object[] { id })).toString();
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除菜单(更新菜单的状态)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object delete(final HttpServletRequest request,HttpServletResponse response) {
		//事务处理
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String id = req.getAjaxValue(request, "id");
				//查询菜单是一级或者二级
				String sql = getSql("sys.selectsuper");
				Map<String, Object> map = this.queryForMap(sql,new Object[] { id });
				// 判断是super_id的值
				Integer super_id = Integer.parseInt((String) map.get("super_id"));
				//更新权限状态
				String updatepowers = getSql("sys.updatepowers");
				//删除当前的跟对应自己下级菜单
				this.update(getSql("sys.update"), new Object[] { id, id });
				this.update(getSql("sys.updateNextPower"), new Object[] { id, id });
				
				int cnt = queryForInt(getSql("sys.getNextCnt"),new Object[]{super_id});
				cnt = cnt > 0 ?1:0;
				
				this.update(getSql("sys.updateSupperNext"),new Object[]{cnt,super_id});
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
	public Object selectNew(HttpServletRequest request,
			HttpServletResponse response) {
		//获取前台传入的主键
		String id = req.getAjaxValue(request, "id");
		String sql = getSql("sys.selectsuper");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,new Object[] { id })));
		return returnMap;
	}
	/**
	 * 查询权限数据是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectpowerurl(HttpServletRequest request,
			HttpServletResponse response) {
		//获取前台传入权限数据
		String powerURL = req.getAjaxValue(request, "powerURL");
		String sql = getSql("sys.selectpowerurl");
		return db.queryForList(sql,new Object[] { powerURL });
	}
	/**
	 * 查询权限信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectPower(HttpServletRequest request,
			HttpServletResponse response) {
		//根获取前台传入的主键
		String id = req.getAjaxValue(request, "id");
		String sql = getSql("sys.selectpower");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,new Object[] { id })));
		return returnMap;
	}

	/**
	 * 修改权限信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object updateMenu(final HttpServletRequest request,
			HttpServletResponse response) {
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String menuid = req.getAjaxValue(request, "id");//获取菜单主键
				String menuName = req.getAjaxValue(request, "menuName");//获取菜单名称
				String menuUrl = req.getAjaxValue(request, "menuUrl");//获取菜单url
				String order_id = req.getAjaxValue(request, "order_id");//获取菜单url
				String[] Namepower = req.getAjaxValues(request, "powerName");//获取权限的名称
				String[] URLpower = req.getAjaxValues(request, "powerURL");//获取权限的url
				String[] powerId = req.getAjaxValues(request, "powerId");//获取权限的主键
				String powerids = req.getAjaxValue(request, "powerids");//获取被删除的主键
				String stateids = req.getAjaxValue(request, "stateids");//获取被启用的主键
				logger.debug("menuid:"+menuid);
				String superId = null;
				String isnext = null;
				String updatesuperid = getSql("sys.updatesuperid");
				String superid = req.getAjaxValue(request, "superId");
				if (superid == "") {
					superId = "0";
					isnext = "1";
				} else {
					superId = req.getAjaxValue(request, "superId");
					isnext = "0";
					//更新菜单上级状态
					this.update(updatesuperid, new Object[] { superId });
				}
				String updatesql = getSql("sys.updatemenu");//执行更新菜单sql
				this.update(updatesql, new Object[] { menuName, menuUrl, superId,isnext,order_id, menuid });
				String sqlPower = getSql("sys.insertpower");//新增权限sql
				String updatepower = getSql("sys.updatepower");//更新权限sql
				String deletepower = getSql("sys.deletepower");//删除权限sql
				String updatestates = getSql("power.updatestates");//启用主键
				logger.debug("长度：",Namepower.length);
				if (Namepower.length == 0) {// 没有信息
					this.update(sqlPower, new Object[] { "", "", menuid });
				} else {// 填写数据
					// 判断powerId是否为空
					for (int i = 0; i < Namepower.length; i++) {// 页面参数信息；
							String namepower_ = Namepower[i];
							String URLpower_ = URLpower[i];
							String powerId_ = powerId[i];
							if("".equals(powerId_)){//新增
								this.update(sqlPower, new Object[] { namepower_,URLpower_, menuid });
							}else{//修改
								this.update(updatepower, new Object[] {namepower_, URLpower_,powerId_ });
							}
					}
				}
				// 判断删除的主键是否有
				if (!"".equals(powerids)) {
					String[] powerids_ = powerids.split(",");
					for (int i = 0; i < powerids_.length; i++) {// 删除
						logger.debug("删除权限" + powerids_[i]);
						this.update(deletepower, new Object[] { powerids_[i] });
					}
				}
				//启用权限是否存在
				if (!"".equals(stateids)) {
					String[] stateids_ = stateids.split(",");
					for (int i = 0; i < stateids_ .length; i++) {// 启用
						logger.debug("删除权限" + stateids_ [i]);
						this.update(updatestates, new Object[] { stateids_ [i] });
					}
				}
				return 1;
			}
		});
		return getReturnMap(i);
	}
}
