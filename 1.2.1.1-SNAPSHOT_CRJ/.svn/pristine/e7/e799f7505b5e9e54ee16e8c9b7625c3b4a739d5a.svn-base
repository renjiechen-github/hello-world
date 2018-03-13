
package com.ycdc.core.plugin.jpush.serv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ycdc.core.plugin.jpush.controller.fo.JPushFo;
import com.ycdc.core.plugin.jpush.dao.IPushDao;
import com.ycdc.core.plugin.jpush.entity.PushMsgBean;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

// UserServiceImpl userLoginService
@Service("pushService")
public class PushServImpl extends BaseService implements IPushServ
{

	private final static String MNG_AK = "89222ba133f5447b89fd5194";
	private final static String MNG_MS = "4d6fe2dcde534e55407a8e63";
	private final static String C_AK = "3d6dd19e278d5e2fe4b89d91";
	private final static String C_MS = "784ae6cb04f912fed48c7bee";
	
	private static Map<String, Object> confMap = new HashMap<>();
	
	@Resource
	private IPushDao pushDao;
	
	@PostConstruct
	public void selectSystemConf()
	{
		confMap = pushDao.selectSystemConf();
	}
	

	/**
	 * 返回发送失败个数
	 * 
	 * @param cPushList
	 * @return
	 */
	private int callServer(List<Object>[] cPushList, String MS, String AK)
	{
		int success = 0;
		JPushClient jPushClient = new JPushClient(MS, AK);
		PushResult pushResult = null;
		for (int i = 0; i < cPushList[0].size(); i++)
		{
			PushMsgBean pushBean = (PushMsgBean) cPushList[1].get(i);
			try
			{
				pushResult = jPushClient.sendPush((PushPayload) cPushList[0].get(i));
			}
			catch (APIConnectionException e)
			{
				e.printStackTrace();
			}
			catch (APIRequestException e)
			{
				e.printStackTrace();
			}
			// List<PushMsgBean>
			if (pushResult != null && pushResult.getResponseCode() == 200)
			{
				success++;
				// 插入记录 发送信息执行成功
				pushBean.setIfSend(1);
			}
			else
			{
				// 插入记录 发送信息执行失败
				pushBean.setIfSend(0);
			}
			try
			{
				int flag = pushDao.insertJPushMsg(pushBean);
				log.debug("",flag);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return cPushList[0].size() - success;
	}

	private static PushPayload buildPushToAll(String notification_title,
			String msg_title, String msg_content, String extrasparam, boolean pushConf)
	{
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.android_ios())
				.setAudience(Audience.all())
				.setNotification(
						Notification
								.newBuilder()
								.setAlert(notification_title)
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.setAlert(notification_title)
												.setTitle(notification_title)
												// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
												.addExtra("extras", extrasparam).build())
								.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns
								// title、title、subtitle等
										.setAlert(notification_title)
										// 直接传alert
										// 此项是指定此推送的badge自动加1
										// .incrBadge(1)
										.setBadge(0)
										// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
										// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
										.setSound("sound.caf")
										// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
										.addExtra("extras", extrasparam)
										// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
										// .setContentAvailable(true)
										.build()).build())
				// Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				// sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				// .setMessage(
				// Message.newBuilder().setMsgContent(msg_content)
				// .setTitle(msg_title).addExtra("extras", extrasparam)
				// .build()).setOptions(Options.newBuilder()
				// // 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
				// .setApnsProduction(false)
				// // 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
				// .setSendno(1)
				// // 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
				// .setTimeToLive(86400).build())
				.setOptions(Options.newBuilder().setApnsProduction(pushConf).build())
				.build();
	}

	private static PushPayload buildPushToAlias(String alias, String notification_title,
			String msg_title, String msg_content, String extrasparam, boolean pushConf)
	{
		return PushPayload.newBuilder()
		// 指定要推送的平台，all代表当前应用配置了的所有平台，也可以传android等具体平台
				.setPlatform(Platform.all())
				// 指定推送的接收对象，all代表所有人，也可以指定已经设置成功的tag或alias或该应应用客户端调用接口获取到的registration
				// id
				.setAudience(Audience.alias(alias))
				// jpush的通知，android的由jpush直接下发，iOS的由apns服务器下发，Winphone的由mpns下发
				.setNotification(
						Notification
								.newBuilder()
								// 指定当前推送的android通知
								.addPlatformNotification(
										AndroidNotification.newBuilder()
												.setAlert(notification_title)
												.setTitle(notification_title)
												// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
												.addExtra("extras", extrasparam).build())
								// 指定当前推送的iOS通知
								.addPlatformNotification(IosNotification.newBuilder()
								// 传一个IosAlert对象，指定apns
								// title、title、subtitle等
										.setAlert(notification_title)
										// 直接传alert
										// 此项是指定此推送的badge自动加1
										// .incrBadge(1)
										.setBadge(0)
										// 此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
										// 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
										.setSound("sound.caf")
										// 此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
										.addExtra("extras", extrasparam)
										// 此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
										// 取消此注释，消息推送时ios将无法在锁屏情况接收
										// .setContentAvailable(true)
										.build()).build())
				// Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
				// sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
				// [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
				// .setMessage(
				// Message.newBuilder().setMsgContent(msg_content)
				// .setTitle(msg_title).addExtra("extras", extrasparam)
				// .build()).setOptions(Options.newBuilder()
				// 此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
				// .setApnsProduction(false)
				// 此字段是给开发者自己给推送编号，方便推送者分辨推送记录
				// .setSendno(1)
				// 此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天；
				// .setTimeToLive(86400).build())
				.setOptions(Options.newBuilder().setApnsProduction(pushConf).build())
				.build();
	}

	@Override
	public String send(PushMsgBean pmb)
	{
		List<PushMsgBean> list = new ArrayList<PushMsgBean>();
		list.add(pmb);
		log.info("消息推送1：" + list);
		return this.send(list);
	}

	@Override
	public String send(List<PushMsgBean> list)
	{
		int fail = 0;
		String str = "总数[" + list.size() + "]，执行失败[";
		List<Object>[] mPushList = new ArrayList[2];
		mPushList[0] = new ArrayList();
		mPushList[1] = new ArrayList();
		// List<PushPayload> mDelayList = new ArrayList<PushPayload>();
		List<Object>[] cPushList = new ArrayList[2];
		cPushList[0] = new ArrayList();
		cPushList[1] = new ArrayList();
		// List<PushPayload> cDelayList = new ArrayList<PushPayload>();
		// 将pushbean分为延迟和实时两组
		PushPayload pushPayload = null;
		
		String pushConfStr = String.valueOf(confMap.get("value"));
		boolean pushConf = false;
		if (pushConfStr.equals("") || pushConfStr.equals("null") || pushConfStr.equals("1"))
		{
			// 正式
			pushConf = true;
		}
		else
		{
			// 测试
			pushConf = false;
		}
		
		log.info("当前jpush的环境（true:正式，false:测试）" + pushConf);
		
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getDelayDate() != null && !list.get(i).getDelayDate().equals("")
					&& !list.get(i).getDelayDate().equals("null"))
			{
				log.info("delayDate 字段为空或者空字符后者null字符串delayDate【"+list.get(i).getDelayDate()+"】");
				// 暂不支持
				// delayList.add(pushPayload);
			}
			else
			{
				// 指定用户regid发送
				if (!"-1".equals(list.get(i).getAlias()))
				{
					pushPayload = buildPushToAlias(list.get(i).getAlias(), list.get(i)
							.getMsg(), "", "", list.get(i).getExtrasparam(), pushConf);
				}
				// 全网发送
				else
				{
					pushPayload = buildPushToAll(list.get(i).getMsg(), "", "", list
							.get(i).getExtrasparam(), pushConf);
				}
				// type = 0 发送内网员工
				if (list.get(i).getType() == 0)
				{
					mPushList[0].add(pushPayload);
					mPushList[1].add(list.get(i));
				}
				// 发送客户
				else
				{
					cPushList[0].add(pushPayload);
					cPushList[1].add(list.get(i));
				}
			}
		}
		if (mPushList[0] != null && mPushList[0].size() > 0)
		{
			fail += callServer(mPushList, MNG_MS, MNG_AK);
		}
		if (cPushList[0] != null && cPushList[0].size() > 0)
		{
			fail += callServer(cPushList, C_MS, C_AK);
		}
		return str + fail + "]";
	}

	/**
	 * 返回发送信息列表
	 * 
	 * @param jPushFo
	 * @return
	 */
	@Override
	public List<PushMsgBean> selectPushList(JPushFo jPushFo)
	{
		List<PushMsgBean> pushMsgList = null;
		try
		{
			pushMsgList = pushDao.selectPushList(jPushFo);
		}
		catch (Exception e)
		{
			log.error("",e);
		}
		return pushMsgList;
	}
}