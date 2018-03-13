/**
 * date: 2017年5月18日
 */
package com.ycdc.dingding.service;

import java.util.*;

import org.slf4j.Logger;

import com.alibaba.fastjson.*;
import com.ycdc.dingding.model.entity.*;
import com.ycdc.util.HTTPSHelper;

/**
 * @name: LockServiceImpl.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月18日
 */
public class LockServiceImpl implements ILockService {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	// 域名
	private static String HOSTNAME = "lockapi.dding.net";
	// 端口号
	private static String PORT = "";
	// 固定前戳
	private static String PATH = "/openapi/v1/";
	// 商户名
	private static String merchant = "千万间";
	
	private static String CLIENT_ID = "70d025f39aeae4695099b95f";
	private static String CLIENT_SECRET = "ccde33fff39f1203c670467c23277e49";
	// 获取access_token (POST)
	private  static String GET_ACCESS_TOKEN = "access_token";
	/**
	 * 房屋操作
	 */
	// 添加一个公寓。(POST)
	private  static String ADD_HOME = "add_home";
	// 更新一个公寓信息或者状态。(POST)
	private  static String UPDATE_HOME = "update_home";
	// 删除一个公寓，如果有设备绑定，则直接删除网关即可。(POST)
	private  static String DEL_HOME = "del_home";
	// 查询单个房屋的状态，支持 GET 与 POST。
	private  static String FIND_HOME_STATE = "find_home_state";
	// 查询多个房屋的状态，只支持 POST。
	private  static String FIND_HOME_STATES = "find_home_states";
	// 查询单个房屋的设备列表，支持 GET 与 POST。
	private  static String FIND_HOME_DEVICE = "find_home_device";
	// 用于前端查询房间列表上的设备信息. 使用其它接口需要前端分别遍历请放公寓, 房间, 门锁, 再由 paas 查询状态, 层级过深,所以将以上操作在 saas 层包装一次.(POST)
	private  static String FIND_HOME_DEVICES = "find_home_devices";
	// 给指定的公寓添加房间(POST)
	private  static String ADD_ROOM = "add_room";
	// 更新房间信息(POST)
	private  static String UPDATE_ROOM = "update_room";
	// 删除一个房间，如果下房间绑定了门锁，同时会删除门锁。(POST)
	private  static String DEL_ROOM = "del_room";
	// 根据一些条件，获取相关的房源和设备信息(不传参数返回所有)(GET)
	private  static String SEARCH_HOME_INFO = "search_home_info";
	// 获取查询结果的总数(GET)
	private  static String COUNT_HOME_INFO = "count_home_info";
	// 获取公寓中某个网关信息(GET)
	private  static String GET_CENTER_INFO = "get_center_info";
	// 获取公寓所有网关信息(GET)
	private  static String GET_CENTER_INFO_ARR = "get_center_info_arr";
	// 获取门锁信息(GET)
	private  static String GET_LOCK_INFO = "get_lock_info";
	/**
	 * 密码操作
	 */
	// 获取门锁密码列表。(GET)
	private static String FETCH_PASSWORDS = "fetch_passwords";
	// 获取管理员密码明文。(GET)
	private static String GET_DEFAULT_PASSWORD_PLAINTEXT = "get_default_password_plaintext";
	// 获取动态密码明文。(GET)
	private static String GET_DYNAMIC_PASSWORD_PLAINTEXT = "get_dynamic_password_plaintext";
	// 添加密码（注意：新设备添加完成后，如果没有管理员密码，必须先调用该接口设置管理员密码）(POST)
	private static String ADD_PASSWORD = "add_password";
	// 修改密码:有效期、名称或描述。(POST)
	private static String UPDATE_PASSWORD = "update_password";
	// 删除密码。(POST)
	private static String DELETE_PASSWORD = "delete_password";
	// 冻结密码。(POST)
	private static String FROZEN_PASSWORD = "frozen_password";
	// 解冻密码。(POST)
	private static String UNFROZEN_PASSWORD = "unfrozen_password";
	/**
	 * 历史记录查询
	 */
	// 查询开门历史记录。(GET)
	private static String GET_LOCK_EVENTS = "get_lock_events";
	// 查询开门历史记录数量。(GET)
	private static String COUNT_LOCK_EVENTS = "count_lock_events";
	/**
	 * 激活码操作
	 */
	// 下发激活码。(POST)
	private static String ADD_PASSWORD_WITHOUT_CENTER = "add_password_without_center";
	// 修改激活码。(POST)
	private static String UPDATE_PASSWORD_WITHOUT_CENTER = "update_password_without_center";
	/**
	 * 查询设备操作
	 */
	// 查询设备操作，查询条件包括操作人、操作类型、以及操作时间段，支持单个条件或多条件联合查询。(GET)
	private static String SEARCH_DEVICE_OP_LOG = "search_device_op_log";
	// 查询设备操作记录数量。(GET)
	private static String COUNT_DEVICE_OP_LOG = "count_device_op_log";
	/**
	 * 工单操作
	 */
	// 添加工单(POST)
	private static String ADD_TICKET = "add_ticket";
	// 更新工单。(POST)
	private static String UPDATE_TICKET = "update_ticket";
	// 删除工单。(POST)
	private static String DELETE_TICKET = "delete_ ticket";
	// 获取某个工单。(GET)
	private static String GET_TICKET_BY_ID = "get_ticket_by_id";
	// 获取公寓的所有工单。(GET)
	private static String GET_TICKET_BY_HOME = "get_ticket_by_home";
	// 更新工单状态。(POST)
	private static String UPDATE_TICKET_STATE = "update_ticket_state";
	
	private String access_token;
	
	private static int expire_time;
	
	/**
	 * 
	 * @param clientId
	 * @param clientSecret
	 * @Description: 获取接口权限
	 */
	public void getAccessToken(String clientId, String clientSecret) {
		Map<String, String> requestPropertys = new HashMap<String, String>();
		requestPropertys.put("Content-Type", "application/json");
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("client_id", clientId);
		paramsters.put("client_secret", clientSecret);
		try {
			String string = HTTPSHelper.defaultHelper.getHttpsConnection(HOSTNAME, PORT, PATH+GET_ACCESS_TOKEN, "POST", requestPropertys, paramsters);
			if (string != null && !(string.isEmpty())) {
				Map<String, Object> responseMap = (Map<String, Object>)JSONObject.parseObject(string);
				if ((int)(responseMap.get("ErrNo")) == 0) {
					this.access_token = (String)responseMap.get("access_token");
					expire_time = (int)responseMap.get("expires_time");
				} else {
					logger.debug("【getAccessToken】 ErrMsg = "+ responseMap.get("ErrMsg"));
				}
			}
			logger.debug("【getAccessToken】 dataString = "+ string);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @name: LockServiceImpl.java
	 * @Description: 参数错误
	 * @author duanyongrui
	 * @since: 2017年5月22日
	 */
	class InvalidArgumentException extends Exception {

		/**
		 * 版本号
		 */
		private static final long serialVersionUID = 1L;
		
		public InvalidArgumentException(String message) {
			super(message);
		}
		
	}
	
	private String getHttpsConnection(String path, String method, Object params) {
		try {
			if (params instanceof List) {
				InvalidArgumentException exception = new InvalidArgumentException("参数不能为数组类型");
				throw exception;
			}
		} catch (InvalidArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		
		this.checkExpireTime();
		Map<String, String> requestPropertys = new HashMap<String, String>();
		if (!(method.toLowerCase().equals("get"))) {
			requestPropertys.put("Content-Type", "application/json");
		}
		Map<String, Object> paramsters = JSONObject.parseObject(JSONObject.toJSON(params).toString(), new TypeReference<HashMap<String,Object>>(){});
		paramsters.put("access_token", this.access_token);
		String result = "";
		try {
			result = HTTPSHelper.defaultHelper.getHttpsConnection(HOSTNAME, PORT, path, method, requestPropertys, paramsters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 
	 * @Description: 校验权限是否过期，若过期则重新获取权限
	 */
	public void checkExpireTime() {
		int time = (int) (new Date().getTime()/1000);
		if (expire_time < time + 3600*24) {
			this.getAccessToken(CLIENT_ID, CLIENT_SECRET);
		}
	}
	
	public int addHome(Home home) {
		String string = this.getHttpsConnection(PATH+ADD_HOME, "POST", home);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0 && home.getHome_id().equals(result.get("home_id"))) {
			logger.debug("【addHome】 success = "+home.getHome_id());
			return 0;
		} else {
			logger.debug("【addHome】 failure = "+home.getHome_id()+"-----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int updateHome(HomeUpdateInfo homeInfo) {
		String string = this.getHttpsConnection(PATH+UPDATE_HOME, "POST", homeInfo);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【updateHome】 success = "+homeInfo.getHome_id());
			return 0;
		} else {
			logger.debug("【updateHome】 failure = "+homeInfo.getHome_id()+"-----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int deleteHome(String homeId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		String string = this.getHttpsConnection(PATH+DEL_HOME, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【deleteHome】 success = "+homeId);
			return 0;
		} else {
			logger.debug("【deleteHome】 failure = "+homeId+"-----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public HomeState findHomeState(String homeId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		String string = this.getHttpsConnection(PATH+FIND_HOME_STATE, "POST", paramsters);
		Map<String, Object> responseObj = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(responseObj.get("ErrNo")) == 0) {
			logger.debug("【findHomeState】 success = "+responseObj);
			HomeState state = JSONObject.toJavaObject((JSON)JSONObject.toJSON(responseObj.get("result")), HomeState.class);
			return state;
		} else {
			logger.debug("【findHomeState】 failure = "+homeId+"-----reason = "+responseObj.get("ErrMsg"));
			return null;
		}
	}
	
	public Map<String, HomeState> findHomeStates(List<String> homeIds) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("home_id", homeIds);
		String string = this.getHttpsConnection(PATH+FIND_HOME_STATES, "POST", paramsters);
		Map<String, Object> responseObj = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(responseObj.get("ErrNo")) == 0) {
			logger.debug("【findHomeStates】 success = "+responseObj);
			Map<String, HomeState> states = JSONObject.parseObject(JSONObject.toJSONString(responseObj.get("result")), new TypeReference<Map<String, HomeState>>(){});
			return states;
		} else {
			logger.debug("【findHomeStates】 failure = "+homeIds+"-----reason = "+responseObj.get("ErrMsg"));
			return null;
		}
	}
	
	public List<HomeDevice> findHomeDevice(String homeId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		String string = this.getHttpsConnection(PATH+FIND_HOME_DEVICE, "POST", paramsters);
		Map<String, Object> responseObj = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(responseObj.get("ErrNo")) == 0) {
			logger.debug("【findHomeDevice】 success = "+responseObj);
			List<HomeDevice> devices = JSONObject.parseObject(JSONObject.toJSONString(responseObj.get("result")), new TypeReference<List<HomeDevice>>(){});
			return devices;
		} else {
			logger.debug("【findHomeDevice】 failure = "+homeId+"-----reason = "+responseObj.get("ErrMsg"));
			return null;
		}
	}
	
	public Map<String, List<HomeDevice>> findHomeDevices(List<String> homeIds) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("home_ids", homeIds);
		String string = this.getHttpsConnection(PATH+FIND_HOME_DEVICES, "POST", paramsters);
		Map<String, Object> responseObj = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(responseObj.get("ErrNo")) == 0) {
			logger.debug("【findHomeDevices】 success = "+responseObj);
			Map<String, List<HomeDevice>> devices = JSONObject.parseObject(JSONObject.toJSONString(responseObj.get("result")), new TypeReference<Map<String, List<HomeDevice>>>(){});
			return devices;
		} else {
			logger.debug("【findHomeDevices】 failure = "+homeIds+"-----reason = "+responseObj.get("ErrMsg"));
			return null;
		}
	}
	
	public int addRoom(Room room) {
		String string = this.getHttpsConnection(PATH+ADD_ROOM, "POST", room);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0 && room.getRoom_id().equals(result.get("room_id"))) {
			logger.debug("【addRoom】 success = "+room.getRoom_id());
			return 0;
		} else {
			logger.debug("【addRoom】 failure = "+room.getRoom_id()+"-----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int updateRoom(RoomUpdateInfo roomInfo) {
		String string = this.getHttpsConnection(PATH+UPDATE_ROOM, "POST", roomInfo);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【updateRoom】 success = "+roomInfo.getRoom_id());
			return 0;
		} else {
			logger.debug("【updateRoom】 failure = "+roomInfo.getRoom_id()+"-----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int deleteRoom(String homeId, String roomId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		String string = this.getHttpsConnection(PATH+DEL_ROOM, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【deleteRoom】 success home_id = "+homeId+"  room_id = "+roomId);
			return 0;
		} else {
			logger.debug("【deleteRoom】 failure home_id = "+homeId+"  room_id = "+roomId+"-----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public List<SearchHomeInfoResult> searchHomeInfo(SearchHomeCondition condition) {
		String string = "";
		if (condition == null) {
			string = this.getHttpsConnection(PATH+SEARCH_HOME_INFO, "GET", new SearchHomeCondition());
		} else {
			string = this.getHttpsConnection(PATH+SEARCH_HOME_INFO, "GET", condition);
		}
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【searchHomeInfo】 success");
			return JSONObject.parseArray((String)(JSONObject.toJSONString(result.get("home_list"))), SearchHomeInfoResult.class);
		} else {
			logger.debug("【searchHomeInfo】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public int getHomeCount(HomeCountCondition condition) {
		String string = "";
		if (condition == null) {
			string = this.getHttpsConnection(PATH+COUNT_HOME_INFO, "GET", new HomeCountCondition());
		} else {
			string = this.getHttpsConnection(PATH+COUNT_HOME_INFO, "GET", condition);
		}
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getHomeCount】 success");
			return (int)result.get("count");
		} else {
			logger.debug("【getHomeCount】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public HomeCenterInfo getCenterInfo(String homeId, String uuid) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		paramsters.put("uuid", uuid);
		String string = this.getHttpsConnection(PATH+GET_CENTER_INFO, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getCenterInfo】 success");
			result.remove("ErrNo");
			result.remove("ErrMsg");
			return JSONObject.parseObject(JSONObject.toJSONString(result), HomeCenterInfo.class);
		} else {
			logger.debug("【getCenterInfo】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public List<HomeCenterInfo> getCentersInfo(String homeId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		String string = this.getHttpsConnection(PATH+GET_CENTER_INFO_ARR, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getCentersInfo】 success");
			return JSONObject.parseArray((String)(JSONObject.toJSONString(result.get("centers"))), HomeCenterInfo.class);
		} else {
			logger.debug("【getCentersInfo】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public LockInfo getLockInfo(String homeId, String roomId, String uuid) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		String string = this.getHttpsConnection(PATH+GET_LOCK_INFO, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getLockInfo】 success");
			result.remove("ErrNo");
			result.remove("ErrMsg");
			return JSONObject.parseObject((String)(JSONObject.toJSONString(result)), LockInfo.class);
		} else {
			logger.debug("【getLockInfo】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public List<LockPasswordInfo> fetchPassword(String homeId, String roomId, String uuid) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		String string = this.getHttpsConnection(PATH+FETCH_PASSWORDS, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【fetchPassword】 success");
			return JSONObject.parseArray((JSONObject.toJSONString(result.get("passwords"))), LockPasswordInfo.class);
		} else {
			logger.debug("【fetchPassword failure】 -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public String getDefaultPasswordPlaintext(String homeId, String roomId, String uuid) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		String string = this.getHttpsConnection(PATH+GET_DEFAULT_PASSWORD_PLAINTEXT, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getDefaultPasswordPlaintext】 success");
			return (String)result.get("password");
		} else {
			logger.debug("【getDefaultPasswordPlaintext】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public DynamicPasswordInfo getDynamicPasswordPlaintext(String homeId, String roomId, String uuid) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		String string = this.getHttpsConnection(PATH+GET_DYNAMIC_PASSWORD_PLAINTEXT, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getDynamicPasswordPlaintext】 success");
			result.remove("ErrNo");
			result.remove("ErrMsg");
			return JSONObject.parseObject((String)(JSONObject.toJSONString(result)), DynamicPasswordInfo.class);
		} else {
			logger.debug("【getDynamicPasswordPlaintext】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public int addPassword(Password password) {
		try {
			if (password.getPermission_end() <= password.getPermission_begin()) {
				throw new Exception("【addPassword】密码有效期的结束时间必须大于开始时间");
			} else if (password.getPassword().length() != 6) {
				throw new Exception("【addPassword】密码必须是六位");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
		String string = this.getHttpsConnection(PATH+ADD_PASSWORD, "POST", password);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【addPassword】 success");
			return (int)result.get("id");
		} else {
			logger.debug("【addPassword】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int updatePassword(PasswordUpdateInfo passwordInfo) {
		try {
			if (passwordInfo.getPermission_end() <= passwordInfo.getPermission_begin()) {
				throw new InvalidArgumentException("【updatePassword】密码有效期的结束时间必须大于开始时间");
			} else if (passwordInfo.getPassword().length() != 6) {
				throw new InvalidArgumentException("【updatePassword】密码必须是六位");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -1;
		}
		String string = this.getHttpsConnection(PATH+UPDATE_PASSWORD, "POST", passwordInfo);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【updatePassword】 success");
			return 0;
		} else {
			logger.debug("【updatePassword】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int deletePassword(String homeId, String roomId, String uuid, int passwordId) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		paramsters.put("password_id", passwordId);
		String string = this.getHttpsConnection(PATH+DELETE_PASSWORD, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【deletePassword】 success");
			return 0;
		} else {
			logger.debug("【deletePassword】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int frozenPassword(String homeId, String roomId, String uuid, int passwordId) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		paramsters.put("password_id", passwordId);
		String string = this.getHttpsConnection(PATH+FROZEN_PASSWORD, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【frozenPassword】 success");
			return 0;
		} else {
			logger.debug("【frozenPassword】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int unfrozenPassword(String homeId, String roomId, String uuid, int passwordId) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("home_id", homeId);
		paramsters.put("room_id", roomId);
		paramsters.put("uuid", uuid);
		paramsters.put("password_id", passwordId);
		String string = this.getHttpsConnection(PATH+UNFROZEN_PASSWORD, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【unfrozenPassword】 success");
			return 0;
		} else {
			logger.debug("【unfrozenPassword】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public List<LockOpenEventInfo> getLockEvents(LockEventsCondition condition) {
		String string = "";
		if (condition == null) {
			string = this.getHttpsConnection(PATH+GET_LOCK_EVENTS, "GET", new SearchHomeCondition());
		} else {
			string = this.getHttpsConnection(PATH+GET_LOCK_EVENTS, "GET", condition);
		}
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getLockEvents】 success");
			return JSONObject.parseArray((String)(JSONObject.toJSONString(result.get("lock_events"))), LockOpenEventInfo.class);
		} else {
			logger.debug("【getLockEvents】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public int getLockEventsCount(LockOpenCountCondition condition) {
		String string = "";
		if (condition == null) {
			string = this.getHttpsConnection(PATH+COUNT_LOCK_EVENTS, "GET", new LockOpenCountCondition());
		} else {
			string = this.getHttpsConnection(PATH+COUNT_LOCK_EVENTS, "GET", condition);
		}
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getLockEventsCount】 success");
			return (int)result.get("count");
		} else {
			logger.debug("【getLockEventsCount】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public ActivationPasswordResult activationPasswordWithoutCenter(ActivationPassword passwordInfo) {
		try {
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			Date date = new Date();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			int time = (int)(calendar.getTime().getTime()/1000);
			if (passwordInfo.getPermission_end() <= time) {
				throw new InvalidArgumentException("【activationPasswordWithoutCenter】密码有效期的结束时间必须为下下整点之后的时间。比如，现在时间为 15:28,结束时间必须 为 17:00 以后的时间。");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		String string = this.getHttpsConnection(PATH+ADD_PASSWORD_WITHOUT_CENTER, "POST", passwordInfo);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【activationPasswordWithoutCenter】 success");
			result.remove("ErrNo");
			result.remove("ErrMsg");
			return JSONObject.parseObject((String)(JSONObject.toJSONString(result)), ActivationPasswordResult.class);
		} else {
			logger.debug("【activationPasswordWithoutCenter】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public ActivationPasswordResult updateActivationCodeWithoutCenter(ActivationPassword passwordInfo) {
		try {
			Calendar calendar = Calendar.getInstance(Locale.CHINA);
			Date date = new Date();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR, 2);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			int time = (int)(calendar.getTime().getTime()/1000);
			if (passwordInfo.getPermission_end() <= time) {
				throw new InvalidArgumentException("【updateActivationCodeWithoutCenter】密码有效期的结束时间必须为下下整点之后的时间。比如，现在时间为 15:28,结束时间必须 为 17:00 以后的时间。");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		String string = this.getHttpsConnection(PATH+UPDATE_PASSWORD_WITHOUT_CENTER, "POST", passwordInfo);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【updateActivationCodeWithoutCenter】 success");
			result.remove("ErrNo");
			result.remove("ErrMsg");
			return JSONObject.parseObject((String)(JSONObject.toJSONString(result)), ActivationPasswordResult.class);
		} else {
			logger.debug("【updateActivationCodeWithoutCenter】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public List<LockOperation> searchDeviceOpLog(SearchDeviceOpCondition condition) {
		String string = "";
		if (condition == null) {
			string = this.getHttpsConnection(PATH+SEARCH_DEVICE_OP_LOG, "GET", new SearchDeviceOpCondition());
		} else {
			string = this.getHttpsConnection(PATH+SEARCH_DEVICE_OP_LOG, "GET", condition);
		}
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【searchDeviceOpLog】 success");
			return JSONObject.parseArray((JSONObject.toJSONString(result.get("op_list"))), LockOperation.class);
		} else {
			logger.debug("【searchDeviceOpLog】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public int getDeviceOpLogCount(DeviceOpCountCondition condition) {
		String string = "";
		if (condition == null) {
			string = this.getHttpsConnection(PATH+COUNT_DEVICE_OP_LOG, "GET", new DeviceOpCountCondition());
		} else {
			string = this.getHttpsConnection(PATH+COUNT_DEVICE_OP_LOG, "GET", condition);
		}
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getDeviceOpLogCount】 success");
			return (int)result.get("count");
		} else {
			logger.debug("【getDeviceOpLogCount】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public String addTicket(Ticket ticket) {
		String string = this.getHttpsConnection(PATH+ADD_TICKET, "POST", ticket);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【addTicket】 success");
			return (String)(result.get("ticket_id"));
		} else {
			logger.debug("【addTicket】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public int updateTicket(TicketUpdateInfo ticketInfo) {
		String string = this.getHttpsConnection(PATH+UPDATE_TICKET, "POST", ticketInfo);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【updateTicket】 success");
			return 0;
		} else {
			logger.debug("【updateTicket】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public int deleteTicket(String ticketId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("ticket_id", ticketId);
		String string = this.getHttpsConnection(PATH+DELETE_TICKET, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【deleteTicket】 success");
			return 0;
		} else {
			logger.debug("【deleteTicket】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public TicketDetailInfo getTicketById(String ticketId) {
		Map<String, String> paramsters = new HashMap<String, String>();
		paramsters.put("ticket_id", ticketId);
		String string = this.getHttpsConnection(PATH+GET_TICKET_BY_ID, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getTicketById】 success");
			return JSONObject.parseObject((JSONObject.toJSONString(result.get("ticket_info"))), TicketDetailInfo.class);
		} else {
			logger.debug("【getTicketById】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public List<TicketDetailInfo> getTicketByHome(String homeId, int serviceType) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("home_id", homeId);
		paramsters.put("service_type", serviceType);
		String string = this.getHttpsConnection(PATH+GET_TICKET_BY_HOME, "GET", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【getTicketByHome】 success");
			return JSONObject.parseArray((JSONObject.toJSONString(result.get("home_ticket_list"))), TicketDetailInfo.class);
		} else {
			logger.debug("【getTicketByHome】 failure -----reason = "+result.get("ErrMsg"));
		}
		return null;
	}
	
	public int updateTicketState(String ticketId, int ticketState) {
		Map<String, Object> paramsters = new HashMap<String, Object>();
		paramsters.put("ticket_id", ticketId);
		paramsters.put("ticket_state", ticketState);
		String string = this.getHttpsConnection(PATH+UPDATE_TICKET_STATE, "POST", paramsters);
		Map<String, Object> result = JSONObject.parseObject(string, new TypeReference<HashMap<String,Object>>(){});
		if ((int)(result.get("ErrNo")) == 0) {
			logger.debug("【updateTicketState】 success");
			return 0;
		} else {
			logger.debug("【updateTicketState】 failure -----reason = "+result.get("ErrMsg"));
		}
		return -1;
	}
	
	public static void main(String[] args) {
		LockServiceImpl service = new LockServiceImpl();
//		service.getAccessToken(CLIENT_ID, CLIENT_SECRET);
//		service.addHome(new Home("南京市", "建邺区", "华山路99号积贤雅苑2803", "积贤雅苑", "2233", "积贤雅苑2803", "段永瑞接口调试"));
//		service.updateHome(new HomeUpdateInfo("2233", "华山路99号", "积贤雅苑2801", "合租房"));
//		service.deleteHome("2233");
//		ArrayList<String> homeIds = new ArrayList<>();
//		homeIds.add("2233");
//		homeIds.add("2234");
//		service.findHomeState("2233");
//		service.findHomeStates(homeIds);
//		service.findHomeDevice("2233");
//		service.findHomeDevices(homeIds);
//		service.addRoom(new Room("2233", "3333", "A房间", "主卧带独立阳台"));
//		service.updateRoom(new RoomUpdateInfo("2233", "3333", "B房间", "主卧带独立阳台"));
//		service.deleteRoom("2233", "3333");
//		service.logger.debug(service.searchHomeInfo(new SearchHomeCondition("积贤雅苑", null, null, 0, 10)).get(0).getRooms().get(0).getRoom_id());
//		service.getHomeCount(new HomeCountCondition("积贤", null, null));
//		service.getCenterInfo("2233", null);
//		service.getCentersInfo("2233");
//		service.getLockInfo("2233", null, null);
//		service.fetchPassword("2233", null, null);
//		service.getLockEventsCount(new LockOpenCountCondition("2233", null, null, 0, (int)(new Date().getTime()/1000)));
		List<String> roomsList = new ArrayList<String>();
		roomsList.add("2233");
//		service.addTicket(new Ticket("2233", 1, 1, roomsList, new TicketSubscribe(new Long(new Date().getTime()).toString(), "100", "段永瑞", "18356097560", "测试信息"), "测试工单"));
//		service.updateTicket(new TicketUpdateInfo("ISCN1705231608668", roomsList, new TicketSubscribe(new Long(new Date().getTime()+3600*24*1000).toString(), "100", "段永瑞", "18356097560", "这是测试工单，请勿操作"), "这是测试工单，请勿操作"));
//		service.getTicketByHome("2233", 1);
		service.updateTicketState("ISCN1705231608668", 1);
	}
	
}
