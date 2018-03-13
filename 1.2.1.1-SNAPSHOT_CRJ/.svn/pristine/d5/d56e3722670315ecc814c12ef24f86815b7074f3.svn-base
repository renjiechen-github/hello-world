package com.ycdc.core.plugin.sms;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.model.GetTemplateDetailResponse;
import com.ycdc.core.plugin.sms.dao.SmsSendMessageDAO;
import com.ycdc.core.plugin.sms.po.SmsSendMessagePO;
import com.ycqwj.ycqwjApi.request.SmsRequest;
import pccom.common.util.Constants;
import pccom.common.util.StringHelper;

/**
 * 短信发送
 *
 * @author 孙诚明
 * @since 2016年9月27日
 */
@Component("smsSendMessage")
public class SmsSendMessage {

    private static Logger logger = Logger.getLogger(SmsSendMessage.class);
    // 静态变量
    private static Map<Integer, String> templateMap = null;
    // 存储短信信息
    public static Queue<SmsSendMessagePO> addSmsDBQueue = null;
    // 发送账号安全认证的Access Key ID
    private static String accessKeyId = "f66a1981f0fd4a4f94c454a251d30042";
    // 发送账号安全认证的Secret Access Key
    private static String secretAccessKy = "6e9a4fd9f47442478786c7cb327a6340";
    // 发送使用签名的调用ID
    private static String invokeId = "gSSPl4oo-4cRn-mvUu";
    // SMS服务域名
    private static String endpoint = "sms.bj.baidubce.com";
    // 短信列表
    public static List<GetTemplateDetailResponse> list;
    // 短信发送客户端
    private static SmsClient smsClient = null;

    @Resource
    private SmsSendMessageDAO dao;

    @PostConstruct
    public void init() {
//        addSmsDBQueue = new ConcurrentLinkedQueue<SmsSendMessagePO>();
//
//        templateMap = new HashMap<Integer, String>();
//        templateMap.put(1, "注册验证码");
//        templateMap.put(2, "报修订单");
//        templateMap.put(3, "保洁订单");
//        templateMap.put(4, "投诉订单");
//        templateMap.put(5, "合约付款成功通知");
//        templateMap.put(6, "服务付款成功通知");
//        templateMap.put(7, "预约看房验证码");
//        templateMap.put(8, "房屋支付通知");
//        templateMap.put(9, "预约看房内容");
//        templateMap.put(10, "入住问题订单");
//        templateMap.put(11, "开锁密码");
//        templateMap.put(12, "任务提醒");
//        templateMap.put(13, "租客短信通知");
//        templateMap.put(14, "系统异常通知");
//        templateMap.put(15, "门锁安装通知");
//        templateMap.put(16, "催租通知");
//        templateMap.put(17, "未注册用户短信通知");
//        templateMap.put(18, "公众号(微信)注册后短信通知账号密码");
//        templateMap.put(19, "催租短信");
//        templateMap.put(20, "推荐确认短信");
//        templateMap.put(21, "签约成功提醒短信");
//        templateMap.put(22, "推荐失败提醒");
//        templateMap.put(23, "已推荐并确认提醒");
//        templateMap.put(24, "被推荐人一天未确认提醒");
//
//        // 初始化一个SmsClient
//        SmsClientConfiguration config = new SmsClientConfiguration();
//        config.withCredentials(new DefaultBceCredentials(accessKeyId, secretAccessKy));
//        config.withEndpoint(endpoint);
//        // 实例化发送客户端
//        smsClient = new SmsClient(config);
//        // 获取短信模板
//        ListTemplateResponse templates = smsClient.listTemplate(new SmsRequest());
//        list = templates.getTemplateList();
//        logger.info("模板列表：" + list);
    }

    /**
     * 短信发送
     *
     * @param phoneNumber 手机号
     * @param vars 模板变量及对应值集合（key：短信内容占位符 例如：code，value：具体值）
     * @param templateCode 模板名称标识（SmsSendContants类中 标识）
     * @return 字符串：成功或者失败（失败原因）
     */
    public static String smsSendMessage(String phoneNumber, Map<String, String> vars, int templateCode) {
        String result = "成功";
        logger.info("发送短信信息："+phoneNumber+"--"+vars+"--"+templateCode);
        try {
            switch (templateCode) {
                case SmsSendContants.REGISTER_VALIDATE_NO:
                    if (!SmsRequest.sendRegTenantCode(phoneNumber, StringHelper.get(vars, "code"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.REPAIR_NO://【用户：${user}，报修内容：${context}。上门日期：${date}】
                    if (!SmsRequest.sendMaintaIninteriorCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "context"), StringHelper.get(vars, "date"), Constants.YCQWJ_API)) {
                        result = "失败";
                    } 
                    break;
                case SmsSendContants.CLEANING_NO://【用户：${user}，保洁内容：${context}。上门日期：${date}】
                    if (!SmsRequest.sendCleanIninteriorCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "context"), StringHelper.get(vars, "date"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.COMPLAINT_NO://【用户：${user}，投诉内容：${context}。投诉日期：${date}】
                    if (!SmsRequest.sendCheckHouseIninteriorCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "context"), StringHelper.get(vars, "date"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.CONTRACT_PAY_NO://【用户：${user}，支付合约号：${code}，支付金额：${money}元】
                    if (!SmsRequest.sendAgreementSucceedIninteriorCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "context"), StringHelper.get(vars, "money"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.SERVICE_PAY_NO://【用户：${user}，服务编号：${code}，支付金额：${money}】
                    if (!SmsRequest.sendServeSucceedIninteriorCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "context"), StringHelper.get(vars, "money"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.ENGAGEMENTS_NO://【用户您好：您正在预约银城千万间看房，本次验证码为：${code}， 该验证码在1分钟内有效，请尽快完成填写！】
                    if (!SmsRequest.sendEngaementsNo(phoneNumber, StringHelper.get(vars, "code"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.HOSE_PAY_NOTIFY_NO://【存在单号在进行下单${code}：金额：${money}】
                    if (!SmsRequest.sendSubscribeIninteriorCode(phoneNumber, StringHelper.get(vars, "code"), StringHelper.get(vars, "money"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.ENGAGEMENTS_CONTEXT://【用户：${yh_mobile}，预约房源：${rank_name}（${rank_code}）。当前租金：${fee}元。看房日期：${date}】
                    if (!SmsRequest.sendSubscribeHouseIninteriorCode(phoneNumber, StringHelper.get(vars, "yh_mobile"), StringHelper.get(vars, "rank_name"), StringHelper.get(vars, "rank_code"), StringHelper.get(vars, "fee"), StringHelper.get(vars, "date"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.IN_PRO://【用户：${user}，投诉内容：${context}。投诉日期：${date}】
                    if (!SmsRequest.sendCheckHouseIninteriorCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "context"), StringHelper.get(vars, "date"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.UNLOCK_PWD://【尊敬的：${user}，欢迎入住银城千万间。您的开锁密钥为：${password}#。祝您入住愉快。】
                    if (!SmsRequest.sendUnlockingCode(phoneNumber, StringHelper.get(vars, "user"), StringHelper.get(vars, "password"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.TASK_WARN://【您有一条新的${typename}任务 订单编号：${ocode}任务编号：${code}由${operName}指派给你，请登录系统查看任务】
                    if (!SmsRequest.sendTaskCode(phoneNumber, StringHelper.get(vars, "typename"), StringHelper.get(vars, "ocode"), StringHelper.get(vars, "code"), StringHelper.get(vars, "operName"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.RENTER_NOTIFY://【${username}您好，欢迎入住银城千万间。您的每月租金${cost_a}元整（${cost}），租赁周期${begin_time}至${end_time}。租住服务热线：4008161000，公众号：ycroom】
                    if (!SmsRequest.sendTenantInform(phoneNumber, StringHelper.get(vars, "username"), StringHelper.get(vars, "cost_a"), StringHelper.get(vars, "cost"), StringHelper.get(vars, "begin_time"), StringHelper.get(vars, "end_time"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.OS_ERROR://【${function}功能出现异常，异常描述${describe}】
                    if (!SmsRequest.sendSysUnusual(phoneNumber, StringHelper.get(vars, "function"), StringHelper.get(vars, "describe"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.LOCK_INSTALL://【${username}您好，我们将在${time}为您上门安装智能门锁。安装成功后，您会收到一条开锁的密码短信，请注意查收、保存，谢谢】
                    //没有词条
                    break;
                case SmsSendContants.PAY_NOTIFY://【亲爱的${u}：千万间提醒您，位于${a}的房屋租金${c}元（${t}）可能仍未支付。若您已通过支付宝、微信转账等方式支付但未备注房间号及姓名，请及时与我们联系。建议您关注微信公众号ycroom1000，并用签署房屋租赁合同时登记的手机号关注微信公众号进行付款。公司严禁任何员工要求您将租金以现金或转账的方式支付给个人账户。为保障您的权益，在付款方面遇到任何问题都可以咨询客服热线4008161000。】
                    if (!SmsRequest.sendPress(phoneNumber, StringHelper.get(vars, "u"), StringHelper.get(vars, "a"), StringHelper.get(vars, "c"), StringHelper.get(vars, "t"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.NO_RREGISTER://【欢迎预定千万间房源，您可使用账号${username}，密码123456登录官网或APP查看预定记录】
                    if (!SmsRequest.sendUnRegister(phoneNumber, StringHelper.get(vars, "username"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.AFTER_WXREGISTRATION://【欢迎您注册使用银城千万间系统，您的初始账号为${username}，密码123456，请您及时登录官网或app进行密码修改。】
                    if (!SmsRequest.sendOfficialAccounts(phoneNumber, StringHelper.get(vars, "username"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.DUN_NOTIFY://【尊敬的${username}您好，您租赁的${name}租金即将到期，请您即时支付${remarks}租金 ${cost}元整。如您已经支付请忽略此短信。关注公众号：“银城千万间”可查看支付明细】
                    if (!SmsRequest.sendPressSms(phoneNumber, StringHelper.get(vars, "username"), StringHelper.get(vars, "name"), StringHelper.get(vars, "remarks"), StringHelper.get(vars, "cost"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.RECOMMEND_CONFIRM://【尊敬的${name}您好，手机号为${mobile}的用户向您推荐了${area}的房源，请点击链接进行确认：${url}】
                    if (!SmsRequest.sendRecommendSms(phoneNumber, StringHelper.get(vars, "name"), StringHelper.get(vars, "mobile"), StringHelper.get(vars, "area"), StringHelper.get(vars, "url"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.SIGN_SUCCESS://【您的客户${user_name}已签约，恭喜您获得奖励，请登录APP个人中心->我的客户查看。】
                    if (!SmsRequest.sendRecommendSucc(phoneNumber, StringHelper.get(vars, "user_name"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.RECOMMEND_FAIL://【您推荐的用户${name}因未在有效期内或已被其他经纪人推荐，您的推荐未成功，请知晓。】
                    if (!SmsRequest.sendRecommendDefeated(phoneNumber, StringHelper.get(vars, "name"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.RECOMMEND_REMIND://【您推荐的用户${name}已确认推荐，管家正联系其看房，您可在【我的客户】中查看其看房状态。】
                    if (!SmsRequest.sendRecommendWarn(phoneNumber, StringHelper.get(vars, "name"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                case SmsSendContants.RECOMMEND_UNCONFIRMED://【您推荐的用户${name}还未确认推荐，请您与该用户取得联系。】
                    if (!SmsRequest.sendRecommendNoWarn(phoneNumber, StringHelper.get(vars, "name"), Constants.YCQWJ_API)) {
                        result = "失败";
                    }
                    break;
                default:
                    result = "失败";
            }
        } catch (Exception e) {
            logger.error("短信发送失败：", e);
            result = "失败";
        }
        return result;
    }

    /**
     * 更新短信模板信息
     */
    public static void updateTemplateInfo() {
        // 获取短信模板
//        ListTemplateResponse templates = smsClient.listTemplate(new SmsRequest());
//        list = templates.getTemplateList();
    }

    // 定时入库短信信息
    public void insertSmsInfo() {
//        if (!addSmsDBQueue.isEmpty()) {
//            for (SmsSendMessagePO po : addSmsDBQueue) {
//                int info = dao.isExist(po);
//                if (info == 0) {
//                    dao.insertSmsInfo(po);
//                }
//            }
//            // 入库完成后，清空队列
//            addSmsDBQueue.clear();
//        }

    }
}
