package pccom.web.interfaces;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import pccom.common.util.HttpURLConnHelper;

/**
 * 卡券接口
 * @author admin
 *
 */
@Service("coupon")
public class Coupon extends Base {

	/**
	 * 核销Code接口
	 */
	private static String  CONSUME = "https://api.weixin.qq.com/card/code/consume?access_token=TOKEN";
	
	/**
	 * 查询Code接口
	 */
	private static String GETCODE = "https://api.weixin.qq.com/card/code/get?access_token=TOKEN";
	
	private static String serverID = "gh_687009b000e8";
	
	/**
	 * 查询code接口
	 * @param params
	 * @param obj
	 * @return
	 */
	public Map<String,Object> getCode(Map params, Object obj){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		String code = str.get(params, "code");//code编号
		String oper_id = str.get(params, "oper_id");//核销人员编号
//		String card_id = str.get(params, "card_id");//卡券编号
		if("".equals(code)){
			returnMap.put("state","-1");
			returnMap.put("msg", "扫码信息不能为空");
			return returnMap;
		}
		//先进查询code接口状态信息
		//先查询token信息
		String sql = "select a.wx_token from yc_wx_server_info a where a.server_id = '"+serverID+"'";
		String token = queryString(obj, sql, null);
		if("".equals(token)){
			returnMap.put("state","-2");
			returnMap.put("msg", "token获取失败");
			return returnMap;
		}
		
		String serverUrl = GETCODE.replace("TOKEN", token);
		logger.debug("serverUrl:"+serverUrl);
		JSONObject json = new JSONObject();
		json.put("code", code);
		json.put("check_consume", true);
		JSONObject resultJson = HttpURLConnHelper.executessl(serverUrl, "POST", json);
		logger.debug("卡券核销记录信息："+resultJson);
		if(resultJson.containsKey("errcode")){
			if("0".equals(resultJson.getString("errcode"))){//查询成功
				JSONObject cardJson = resultJson.getJSONObject("card");
				String card_id = cardJson.getString("card_id");//对应的卡券信息
				String begin_time = cardJson.getString("begin_time");//有效期
				String end_time = cardJson.getString("end_time");//有效期
				String openid = resultJson.getString("openid");//用户编号
				String user_card_status = resultJson.getString("user_card_status");//卡券状态
				String can_consume = resultJson.getString("can_consume");//是否可进行核销
				
				//查询对应卡券信息，看是那种类型的卡券，如果是兑换券 就不予真正核销
				sql = "SELECT a.card_type,a.type,a.begin_timestamp,a.end_timestamp,a.fixed_begin_term,a.isnew FROM yc_coupon_info a WHERE a.card_id = ?";
				Map<String,Object> cardMap = queryMap(obj, sql, new Object[]{card_id});
				if(cardMap.isEmpty()){
					returnMap.put("state",-7);
					returnMap.put("msg", "未查询到该卡券信息，请联系管理员进行核对！");
					return returnMap;
				}
				//先检查是否存在对应卡券用户信息，如果不存在需先进行插入
				sql = "SELECT a.id FROM yc_coupon_user a WHERE a.card_id = ? AND a.code = ? AND a.user = ?";
				String couponuser_id = queryString(obj, sql, new Object[]{card_id,code,openid});
				int coupon_user_id = -1;
				logger.debug("coupon_user_id:"+couponuser_id);
				if(!"".equals(couponuser_id)){
					coupon_user_id = Integer.valueOf(couponuser_id);
				}
				logger.debug("coup11on_user_id:"+coupon_user_id);
				if(coupon_user_id == -1){
					logger.debug("----1-----");
					sql = "INSERT INTO yc_coupon_user(card_id,code,isuser,isdraw,user,hx_oper_id)VALUES(?,?,?,?,?,?)";
					coupon_user_id = insert(obj, sql, new Object[]{card_id,
							code,"2","1",openid,oper_id});
					logger.debug("----2-----");
					if(coupon_user_id < 0 ){
						returnMap.put("state",-9);
						returnMap.put("msg", "计入数据库失败，请重新扫码。");
						return returnMap;
					}
					//同时减去对应的库存信息
					sql = "UPDATE yc_coupon_info a SET a.sy_quantity = a.sy_quantity -1 WHERE a.card_id = ? ";
					int exc = update(obj, sql, new Object[]{card_id});
					if(exc != 1){
						returnMap.put("state",-8);
						returnMap.put("msg", "库存核减失败，请联系后台处理。");
						return returnMap;
					}
				}
				//根据用户oper_id获取对应的用户信息
				sql = "SELECT a.mobile,b.username,b.id FROM yc_wx_user_info a,yc_userinfo_tab b WHERE a.mobile = b.mobile and a.wxuser = ?";
				Map<String,Object> userMap = queryMap(obj, sql, new Object[]{openid});
				if(userMap.isEmpty()){//为空说明没有关注，或没有绑定，或状态异常
					//默认插入一条新记录到表中
					returnMap.put("state",-6);
					returnMap.put("msg", "但该用户未关注“银城千万间”公众号或未绑定用户信息或用户状态异常，请引导用户排查。");
					return returnMap;
				}
				
				if("1".equals(str.get(cardMap, "isnew"))){//需要检查是否是新租客，新租客不予使用
					sql = queryString(obj, "SELECT COUNT(1) FROM yc_agreement_tab a WHERE a.type = 2 and a.user_mobile = ?", new Object[]{str.get(userMap, "mobile")});
					returnMap.put("state",-8);
					returnMap.put("msg", "经核实，您不是新租客户，无法使用此优惠券。");
					return returnMap;
				}
				
				//查询出结果后进行更新用户信息操作操作
				sql = "update yc_coupon_user a set username = ?,usermobile=?,userid=? where id = ?";
				int exc = update(obj, sql, new Object[]{str.get(userMap, "username"),str.get(userMap, "mobile"),str.get(userMap, "id"),coupon_user_id});
				if(exc == 0){
					returnMap.put("state",-8);
					returnMap.put("msg", "卡券信息插入失败，请联系管理员核对信息。");
					return returnMap;
				}
				if(!"4".equals(str.get(cardMap, "CARD_TYPE"))){//只有兑换券进行核销 到此结束
					returnMap.put("state",1);
					returnMap.put("msg", "核对成功，请不要进行删除卡券信息，请在合同审批通过后支付使用。");
					return returnMap;
				}
				if("true".equals(can_consume)){//可以进行核销
					//调用核销接口进行核销
					sql = "select a.wx_token from yc_wx_server_info a where a.server_id = '"+serverID+"'";
					token = queryString(obj, sql, null);
					serverUrl = CONSUME.replace("TOKEN", token);
					json = new JSONObject();
					json.put("code", code);
					resultJson = HttpURLConnHelper.executessl(serverUrl, "POST", json);
					logger.debug(resultJson.toString());
					if(resultJson.containsKey("errcode")){
						if("0".equals(resultJson.getString("errcode"))){
							//进行更新状态信息
							sql = "update yc_coupon_user a set a.isuser = ?, a.usertime = now() where a.id = ? ";
							returnMap.put("state",1);
							returnMap.put("msg", "卡券核销成功。");
							return returnMap;
						}else{
							returnMap.put("state",-7);
							returnMap.put("msg", "卡券核销失败,请核对。");
							return returnMap;
						}
					}else{
						returnMap.put("state",-7);
						returnMap.put("msg", "卡券核销失败");
						return returnMap;
					}
				}else{
					returnMap.put("state",-5);
					returnMap.put("msg", "该卡券无法进行核销，状态异常："+user_card_status);
					return returnMap;
				}
			}else{
				if("40099".equals(resultJson.getString("errcode"))){
					returnMap.put("state",resultJson.getString("errcode"));
					returnMap.put("msg", "无效的code，已被使用，请核对！");
					return returnMap;
				}
				returnMap.put("state",resultJson.getString("errcode"));
				returnMap.put("msg", resultJson.getString("errmsg"));
				return returnMap;
			}
		}else{
			returnMap.put("state","-3");
			returnMap.put("msg", "卡券查询失败");
			return returnMap;
		}
	}
	
	
	public static void main(String[] args) {
		String serverUrl = "https://api.weixin.qq.com/card/user/getcardlist?access_token=PFCzKVZUKnz6FM8E6rgykDLc9D6vukP3BXkBF_f9Geaq0iMN1u1UGf1E8jZ--NJq3VVeM8A7BZJ98cLNPfal25EzJCBQXcUXxzlTYnw3j4seA8U07tQnh8uQJL40rhl6BXVhAHALHF";
		JSONObject json = new JSONObject();
		json.put("openid", "oJPVytypM77bDYg1nEWbEm_ilYZE");
		//logger.debug(HttpURLConnHelper.executessl(serverUrl, "POST", json));
	}
	
	
}
