/**
 * date: 2017年3月31日
 */
package com.ycdc.huawei.service;

import java.io.File;
import java.util.Map;

import com.ycdc.huawei.model.entity.*;

/**
 * @name: IHuaWeiService.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年3月31日
 */
public interface IHuaWeiService
{

	/**
	 * 发送短信
	 * 
	 * @param params
	 * @return 短信发送结果实体
	 * @Description: 给其他人发送短信并指定发送方号码
	 */
	public SmsResultsEntity sendSms(SmsSendEntity params);

	/**
	 * 发送模板短信
	 * 
	 * @param params
	 * @return 短信发送结果实体
	 * @Description: 给其他人发送短信并指定发送方号码
	 */
	public SmsResultsEntity sendSmsViaTemplate(SmsSendEntity params);
//	/**
//	 * 绑定虚拟电话号码
//	 * 
//	 * @param params
//	 * @return 绑定结果实体
//	 * @Description: 绑定的虚拟号可以由华为随机分配也可以由我方指定
//	 */
//	public VirBindResultEntity bindVirtualNumber(VirBindEntity params);
//
//	/**
//	 * 解绑虚拟号码
//	 * 
//	 * @param params
//	 * @return 解绑结果（code:返回码, 数据类型: String; description:返回码的描述信息, 数据类型: String）
//	 * @Description: 可根据虚拟号码或小号绑定ID解绑
//	 */
//	 public Map<String,String> unbindVirtualNumber(String virtualNumber, String aParty, String bParty, String operId);
//
//	/**
//	 * 
//	 * @param params
//	 * @return 解绑结果（code:返回码, 数据类型: String; description:返回码的描述信息, 数据类型: String）
//	 * @Description: 仅能根据绑定关系ID进行转绑
//	 */
//	/**
//	 * 转绑虚拟号码
//	 * 
//	 * @param virtualNumber
//	 *          虚拟号码
//	 * @param aParty
//	 *          A方号码
//	 * @param bParty
//	 *          B方现有号码
//	 * @param bPartyNew
//	 *          B方待绑定的新号码
//	 * @param operId
//	 *          操作人id
//	 * @return 绑定结果
//	 * @Description:
//	 */
//	 public Map<String, String> modifyBind(String virtualNumber, String aParty, String bParty, String bPartyNew, String operId);
//
//	/**
//	 * 查询虚拟号码绑定信息
//	 * 
//	 * @param virtualNumber
//	 *          绑定的虚拟小号号码 格式：国家码+手机号。示例：8613800000000
//	 * @return 绑定信息
//	 * @Description: 只提供单个虚拟小号号码查询
//	 */
//	 public VirQueryResultEntity queryVirtualBumber(String virtualNumber);
//
//	/**
//	 * 查询录音文件列表
//	 * 
//	 * @param params
//	 * @return 查询结果
//	 * @Description: 录音在生成后的5分钟后才可以查询得到。
//	 * @see 录音文件存放时间为3天。录音3天后将被清理，并且不可以修复。若商户需要下载录音文件，必须在录音产生后3天内将录音取走。
//	 */
//	 public RecordQueryResultEntity queryRecordList(RecordQueryEntity params);
//
//	/**
//	 * 下载录音文件
//	 * 
//	 * @param recordId
//	 * @param createTime
//	 * @param aPartNumber
//	 *          A方电话号码
//	 * @param virNumber
//	 *          虚拟小号号码
//	 * @param bPartNumber
//	 *          B方电话号码
//	 * @return 录音文件(已存至本地磁盘)
//	 * @Description: recordId和createTime从录音文件列表中获取
//	 */
//	 public File downloadRecord(String recordId, String createTime, String subscriptionId,String virNumber, String operId);
//
//	/**
//	 * 删除录音文件
//	 * 
//	 * @param recordId
//	 * @return 删除结果（code:返回码, 数据类型: String; description:返回码的描述信息, 数据类型: String）
//	 * @Description: recordId从录音文件列表中获取
//	 */
//	 public Map<String, String> deleteRecord(String recordId);

	/**
	 * 启动双呼
	 * 
	 * @param caller
	 *          主叫方号码 (加+86)
	 * @param callee
	 *          被叫方号码 (加+86)
	 * @param bindNbr
	 *          第三方号码（也是主显号码）;号码仅支持全局号码格式，比如 +8613866887021。
	 * @param callBackUrl
	 * 			呼叫状态和话单记录回调地址(需要现网)
	 * @return 双呼启动结果
	 * @Description:
	 */
	public Click2CallResult triggerClick2call(String caller, String callee, String bindNbr, String callBackUrl);

	/**
	 * 下载录音文件(录音文件只在华为服务器保存七天)
	 * 
	 * @param url
	 *          录音服务器域名或IP：从话单中recordDomain字段获取。
	 * @param bucketname
	 *          对象桶名：从话单中recordBucketName字段获取。
	 * @param objectName
	 *          对象名：从话单中recordObjectName字段获取。
	 * @return
	 * @Description:
	 */
	public String downloadClick2CallRecord(String url, String bucketname, String objectName);
}
