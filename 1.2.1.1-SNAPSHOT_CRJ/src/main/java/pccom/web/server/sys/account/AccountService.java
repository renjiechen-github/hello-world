package pccom.web.server.sys.account;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import pccom.common.util.Batch;
import pccom.common.util.Encrypt;
import pccom.common.util.StringHelper;
import pccom.web.server.BaseService;
@Service("accountService")
public class AccountService extends BaseService{
	
	/**
	 * 系统管理员
	 * liuf
	 * */
	public static final int SUPPER_MANAGER = 1; // 系统管理员
	/**
	 * 商务助理
	 * liuf
	 * */
	public static final int BUSINESS_ASSISTANT = 23; // 商务助理
	/**
	 *  供应商
	 * liuf
	 * */
	public static final int SUPPLIER= 28; // 供应商
	/**
	 * 客服
	 * liuf
	 * */
	public static final int CUSTOMER_MANAGER = 27; //客服
	/**
	 * 工程管家
	 * liuf
	 * */
	public static final int ENGIEERING  = 26; // 工程管家
	/**
	 *  市场管家
	 * liuf
	 * */
	public static final int HOUSEKEEPER  = 22; // 市场管家
	/**
	 *平台管理员
	 * liuf
	 * */
	public static final int PLATFORM  = 25; // 平台管理员
	/**
	 * 售后
	 * liuf
	 * */
	public static final int  CUSTOMER_SERVICE = 29; // 售后
	/**
	 * 高管
	 * liuf
	 * */
	public static final int TOP_MANAGER  = 24; // 高管
	
	/**
	 * 财务
	 * liuf
	 * */
	public static final int FINANCE  = 42; // 财务
	
	/**
	 * 人员中心
	 * @param request
	 * @param response
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String group_name = req.getAjaxValue(request, "username");
		List<String> params = new ArrayList<String>();
		String sql = getSql("account.main");
		if (!"".equals(group_name)) {
			sql += getSql("account.username");
			params.add("%" + group_name + "%");
			params.add("%" + group_name + "%");
		}
		getPageList(request,response,sql,params.toArray() );
	}
	/**
	 * 查询所有的组织
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectOrg(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("account.selectOrg");
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list",JSONArray.fromObject(db.queryForList(sql, new Object[] {})));
		return returnMap;
	}
        
        /**
	 * 查询所有的组织
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectTeam(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = "select a.id org_id,a.name org_name FROM cf_team a where a.is_delete = 0";
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list",JSONArray.fromObject(db.queryForList(sql, new Object[] {})));
		return returnMap;
	}
	/**
	 * 验证手机号码存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectMoblie(final HttpServletRequest request,HttpServletResponse response) {
		String mobile = req.getAjaxValue(request, "userMobile");
		String sql = getSql("account.queryMoblie");
		List list = db.queryForList(sql, new Object[] {mobile});
		if(list.size() != 0 ){
			return getReturnMap("0");
		}else{
			return getReturnMap("1");
		}
//		Map<String, Object> returnMap = getReturnMap("1");
//		//returnMap.put("list",JSONArray.fromObject());
//		return returnMap;
	}
	/**
	 * 查询所有的角色
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object selectRole(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("account.selectRole");
		Map<String, Object> returnMap = getReturnMap("1");
		List<Map<String, Object>> list = db.queryForList(sql, new Object[] {});
		// 排序：按照姓氏首字母排列
    Collections.sort(list, new Comparator<Map<String, Object>>() {
      public int compare(Map<String, Object> o1, Map<String, Object> o2) {
          String name1=String.valueOf(o1.get("role_name"));
          String name2=String.valueOf(o2.get("role_name")); 
          Collator instance = Collator.getInstance(Locale.CHINA);
          return instance.compare(name1, name2);
      }
    });	
		returnMap.put("list",JSONArray.fromObject(list));
		return returnMap;
	}
	/**
	 * 人员新增
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object accountSave(final HttpServletRequest request,HttpServletResponse response) 
	{
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
				String name = req.getAjaxValue(request, "aname");
				String desc_text = req.getAjaxValue(request, "desc_text");
				String mobile = req.getAjaxValue(request, "mobile");
				String create_time = req.getAjaxValue(request, "create_time");
				String Role = req.getAjaxValue(request, "Role");
				String Org = req.getAjaxValue(request, "Org");
                                String team = req.getAjaxValue(request, "team");
				String passwd =new Encrypt("SHA-1").getEncrypt("12345");
				String sql = getSql("account.insert");
				
				
				String[] roles = Role.split(",");
				String[] orgs = Org.split(",");
                                String[] teams = team.split(",");
				int user_id= this.insert(sql,new Object[]{name,mobile,"0",desc_text,passwd});
				if(user_id == -2){
					
				}
				for(int i=0;i<roles.length;i++){
					this.update(getSql("account.insertRole"),new Object[]{user_id,roles[i]});
				}
				for(int i=0;i<orgs.length;i++){
					this.update(getSql("account.insertOrg"),new Object[]{user_id,orgs[i]});
				}
                                for(int i=0;i<teams.length;i++){
                                        List<Map<String,Object>> list = this.queryForList("SELECT t2.id,t2.parent_teamid\n" +
                                                            "    FROM ( \n" +
                                                            "        SELECT \n" +
                                                            "                @r AS _id, \n" +
                                                            "                (SELECT @r := parent_teamid FROM cf_team WHERE id = _id) AS parent_teamid, \n" +
                                                            "                 @l := @l + 1 AS lvl \n" +
                                                            "        FROM \n" +
                                                            "                (SELECT @r := ?, @l := 0) vars, \n" +
                                                            "                cf_team h \n" +
                                                            "        WHERE @r <> 0) T1 \n" +
                                                            "    JOIN cf_team T2 \n" +
                                                            "    ON T1._id = T2.id\n" +
                                                            "ORDER BY id",new Object[]{teams[i]});
                                        String top_teamid = "";
                                        if(list.size() != 0){
                                            top_teamid = StringHelper.get(list.get(0), "id");
                                        }
					this.update("INSERT INTO cf_team_rel(team_id,user_id,top_teamid)values(?,?,?)",new Object[]{teams[i],user_id,top_teamid});
				}
				return 1;
			}
		});
		return getReturnMap(i);
	
	}
	/**
	 * 人员停用
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object accountStop(HttpServletRequest request,HttpServletResponse response)
	{
		String id = req.getAjaxValue(request, "id");
		logger.debug(id);
		
                if(db.queryForList("select b.team_id FROM cf_team_leaders b WHERE b.team_id IN(\n" +
                                    "select a.team_id FROM cf_team_rel a where a.user_id = ?)\n" +
                                    "group BY b.team_id HAVING count(1) = 1",new Object[]{id}).size() > 0){
                    return getReturnMap(-1);
                }
                
                String sql = getSql("account.state");
		return getReturnMap( db.update(sql, new Object[] {0,id }));
	}
	/**
	 * 人员启用
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public Object accountEnable(HttpServletRequest request,HttpServletResponse response)
	{
		String id = req.getAjaxValue(request, "id");
		logger.debug(id);
		String sql = getSql("account.state");
		logger.debug("sadfaasssssssssssssssssssssss");
		logger.debug(sql);
		return getReturnMap( db.update(sql, new Object[] {1,id }));
	}
	/**
	 * 人员修改
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object accountUpdate(final HttpServletRequest request,HttpServletResponse response) 
	{
		//事务回滚
		Object i = db.doInTransaction(new Batch<Object>() {
			@Override
			public Integer execute() throws Exception {
					String id = req.getAjaxValue(request, "id");
					String aname = req.getAjaxValue(request, "aname");
					String mobile = req.getAjaxValue(request, "mobile");
					String desc_text = req.getAjaxValue(request, "desc_text");
					String create_time = req.getAjaxValue(request, "groupDate");
                                        String team = req.getAjaxValue(request, "team");
					String role = req.getAjaxValue(request, "Role");
					String org = req.getAjaxValue(request, "Org");
					//修改基本信息
					this.update(getSql("account.update"),new Object[]{aname,mobile,desc_text,id});
					String[] roles = role.split(",");
					String[] orgs = org.split(",");
                                        String[] teams = team.split(",");
					//先进行删除人员与角色对应关系
					this.update(getSql("account.deleteRole"),new Object[]{id});
					this.update(getSql("account.deleteOrg"),new Object[]{id});
					for(int i=0;i<roles.length;i++){
						this.update(getSql("account.insertRole"),new Object[]{id,roles[i]});
					}
					for(int i=0;i<orgs.length;i++){
						this.update(getSql("account.insertOrg"),new Object[]{id,orgs[i]});
					}
                                        this.update("delete from cf_team_rel where user_id = ?",new Object[]{id});
                                        for(int i=0;i<teams.length;i++){
                                                List<Map<String,Object>> list = this.queryForList("SELECT t2.id,t2.parent_teamid\n" +
                                                                    "    FROM ( \n" +
                                                                    "        SELECT \n" +
                                                                    "                @r AS _id, \n" +
                                                                    "                (SELECT @r := parent_teamid FROM cf_team WHERE id = _id) AS parent_teamid, \n" +
                                                                    "                 @l := @l + 1 AS lvl \n" +
                                                                    "        FROM \n" +
                                                                    "                (SELECT @r := ?, @l := 0) vars, \n" +
                                                                    "                cf_team h \n" +
                                                                    "        WHERE @r <> 0) T1 \n" +
                                                                    "    JOIN cf_team T2 \n" +
                                                                    "    ON T1._id = T2.id\n" +
                                                                    "ORDER BY id",new Object[]{teams[i]});
                                                String top_teamid = "";
                                                if(list.size() != 0){
                                                    top_teamid = StringHelper.get(list.get(0), "id");
                                                }
                                                this.update("INSERT INTO cf_team_rel(team_id,user_id,top_teamid)values(?,?,?)",new Object[]{teams[i],id,top_teamid});
                                        }
					
					return 1;
				}
			});
		return getReturnMap(i);
	}
	
	/**
	 * 初始化用户密码
	 * @param request
	 * @param response
	 * @return
	 */
	public Object accountStart(HttpServletRequest request,HttpServletResponse response) {
		String id = req.getAjaxValue(request, "id");
		String passwd=new Encrypt("SHA-1").getEncrypt("12345");
		String sql = getSql("account.startpassword");
		return getReturnMap(db.update(sql,new Object[]{passwd,id}));
	}
	
	/**
	 * 删除操作
	 * @param request
	 * @param response
	 * @return
	 */
	public Object deleteUser(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "id");
		String stat = req.getAjaxValue(request, "stat");
		String sql = getSql("account.deleteUser");
		return getReturnMap(db.update(sql,new Object[]{stat,id}));
	}
}
