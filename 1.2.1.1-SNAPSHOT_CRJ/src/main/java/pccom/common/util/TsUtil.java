package pccom.common.util;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import net.sf.json.JSONObject;

import pccom.web.server.BaseService;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

/**
 * 百度推送
 * 
 * @author suntf
 * @Jul 23, 2015
 */
public class TsUtil extends BaseService
{
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(TsUtil.class);
	private static PushKeyPair androidpair = new PushKeyPair(
			Constants.PUSH_ANDROID_API_KEY, Constants.PUSH_ANDROID_SECRET_KEY);// android
																			// 推送key
	private static PushKeyPair iospair = new PushKeyPair(
			Constants.PUSH_IOS_API_KEY, Constants.PUSH_IOS_SECRET_KEY);// ios
																		// 推送key

	/**
	 * 消息推送
	 * 
	 * @param obj
	 */
	public void pushMessage()
	{
		new Thread()
		{
			@Override
			public void run()
			{
				while (true)
				{
					String sql = "select * from (select a.id, a.title, a.content, a.channel_id, a.client_type, client_title, url "
							   + "  from yc_push_task a order by a.operator_date) a limit 0, 200";
//					logger.debug(sql);
					List<Map<String, Object>> list = db.queryForList(sql);
//					logger.debug(list.size());
					if (list.size() > 0)
					{
						for (Map<String, Object> obj : list)
						{
//							logger.debug(obj);
							BatchSql batchSql = new BatchSql();
							String channel_id = StringHelper.get(obj,
									"channel_id");
							String title = StringHelper.get(obj, "title");
							String content = StringHelper.get(obj, "content");
							String id = StringHelper.get(obj, "id");
							sql = "insert into yc_push_task_his (id, title, content, channel_id, client_type, send_date, url, operator_date) "
									+ "select id, title, content, channel_id, client_type, sysdate(), url, operator_date from yc_push_task a where a.id = ?";
//							logger.debug(str.getSql(sql, new Object[]{id}));
							batchSql.addBatch(sql, new Object[] { id });
							sql = "delete from yc_push_task where id = ?";
//							logger.debug(str.getSql(sql, new Object[]{id}));
							batchSql.addBatch(sql, new Object[] { id });
							int res =db.doInTransaction(batchSql);
							
							logger.debug("参数",res);
							String client_type = StringHelper.get(obj,
									"client_type");
							String client_title = StringHelper.get(obj,
									"client_title");
							String url = StringHelper.get(obj,
									"url");
							JSONObject customerJson = new JSONObject();
							customerJson.put("client_title", client_title);
							customerJson.put("url", url);
//							customerJson.put("custom_content", "");
							// 2. 创建BaiduPushClient，访问SDK接口
							PushKeyPair pair = "android".equals(client_type) ? androidpair
									: iospair;
							BaiduPushClient pushClient = new BaiduPushClient(
									pair, BaiduPushConstants.CHANNEL_REST_URL);
							// 3. 注册YunLogHandler，获取本次请求的交互信息
							pushClient.setChannelLogHandler(new YunLogHandler()
							{
								public void onHandle(YunLogEvent event)
								{
									logger.debug(event.getMessage());
								}
							});
							try
							{
								// 4. 设置请求参数，创建请求实例
								JSONObject notification = new JSONObject();
								notification.put("title", title);
								notification.put("description", content);
								notification.put("custom_content", customerJson.toString());
								PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
										.addChannelId(channel_id)
										.addMsgExpires(new Integer(3600 * 5)). // message有效时间
										addMessageType(1).// 1：通知,0:消息.默认为0
															// 注：IOS只有通知.
										addMessage(notification.toString());

								PushMsgToSingleDeviceResponse response = pushClient
										.pushMsgToSingleDevice(request);
								logger.debug(response.getMsgId());
							}
							catch (Exception e)
							{
								// TODO: handle exception
								db.doInTransaction(batchSql);
							}
						}
					}
					else
					{
						try
						{
							Thread.sleep(10000);
						}
						catch (InterruptedException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
}
