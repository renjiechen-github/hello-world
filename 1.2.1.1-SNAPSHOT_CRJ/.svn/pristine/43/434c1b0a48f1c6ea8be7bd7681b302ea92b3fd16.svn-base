package pccom.web.server.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import pccom.web.server.BaseService;
/**
 * 用户管理
 * @author bianbojun
 *
 */
@Service("userService")
public class UserService extends BaseService {
	/**
	 * 用户中心
	 * @param request
	 * @param response
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String group_name = req.getAjaxValue(request, "username");
		logger.debug("----------------------"+group_name);
		List<String> params = new ArrayList<String>();
		String sql = getSql("userInfo.main");
		if (!"".equals(group_name)) {
			sql += getSql("userInfo.username");
			params.add("%" + group_name + "%");
			params.add("%" + group_name + "%");
		}
		getPageList(request,response,sql,params.toArray() );
	}
	/**
	 * 用户新增
	 * @param request
	 * @param response
	 * @return
	 */
	public Object userSave(final HttpServletRequest request,HttpServletResponse response) 
	{
		String username = req.getAjaxValue(request, "username");
		String sex = req.getAjaxValue(request, "sex");
		String mobile = req.getAjaxValue(request, "mobile");
		String certificateno = req.getAjaxValue(request, "certificateno");
		String qq = req.getAjaxValue(request, "qq");
		String email = req.getAjaxValue(request, "email");
		String wechat = req.getAjaxValue(request, "wechat");
		String birthday = req.getAjaxValue(request, "birthday");
		String sql = getSql("userInfo.insert");
		return getReturnMap(db.update(sql,new Object[]{username,sex,mobile,certificateno,qq,email,wechat,birthday}));
	}
	/**
	 * 用户删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object userDelete(HttpServletRequest request,HttpServletResponse response)
	{
		String id = req.getAjaxValue(request, "id");
		String sql = getSql("userInfo.delete");
		return getReturnMap( db.update(sql, new Object[] {0, id }));
	}
	/**
	 * 用户修改
	 * @param request
	 * @param response
	 * @return
	 */
	public Object userUpdate(final HttpServletRequest request,HttpServletResponse response) 
	{
		String id = req.getAjaxValue(request, "id");
		String username = req.getAjaxValue(request, "username");
		String sex = req.getAjaxValue(request, "sex");
		String mobile = req.getAjaxValue(request, "mobile");
		String certificateno = req.getAjaxValue(request, "certificateno");
		String qq = req.getAjaxValue(request, "qq");
		String email = req.getAjaxValue(request, "email");
		String wechat = req.getAjaxValue(request, "wechat");
		String birthday = req.getAjaxValue(request, "birthday");
		String sql = getSql("userInfo.update");
		return getReturnMap(db.update(sql,new Object[]{username,sex,mobile,certificateno,qq,email,wechat,birthday,id}));
	}
}
