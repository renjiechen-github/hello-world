
package com.ycdc.appserver.bus.service.syscfg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import pccom.common.util.DateHelper;
import pccom.common.util.StringHelper;

import com.raising.framework.utils.md5.MD5Utils;
import com.raising.framework.utils.res.Res;
import com.raising.framework.utils.rsa.RSAUtil;
import com.ycdc.appserver.bus.service.BaseService;
import com.ycdc.appserver.model.house.RankAgreement;
import com.ycdc.appserver.model.syscfg.CABean;
import com.ycdc.appserver.model.user.User;
import com.ycdc.appserver.util.ConvertNum;

/**
 * 系统配置信息
 * 
 * @author suntf
 * @date 2016年12月8日
 */
@Service("sysCfgService")
public class SysCfgServiceImp extends BaseService implements SysCfgService
{

	public final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());

	/*
	 * act A 新增 U 修改 用户鉴权 如果请求报错，放回空值
	 */
	@Override
	public String authentorUser(User u, String act)
	{
		String bodyText = "";
		try
		{
			Map<String, String> contentmap = new HashMap<String, String>();
			// 1.生成16位随机的AES KEY
			String aesKey = RandomStringUtils.random(16, true, true);
			contentmap.put("linkman_name", u.getName()); // 用户姓名
			contentmap.put("linkman_phone", u.getMobile()); // 租客手机号码
			contentmap.put("legal_id", u.getCertificateno()); // 联系人身份证
			contentmap.put("userid", u.getId()); // 用户编号
			contentmap.put("email", u.getEmail()); // 用户邮箱
			contentmap.put("act", act);
			String contentmapString = com.alibaba.fastjson.JSONObject
					.toJSONString(contentmap);
			// 2.使用AES KEY加密后的主体内容——》加密数据段1
			String encryptContent = AES_CBCUtils.encrypt(aesKey,
					contentmapString.getBytes("utf-8"));
			// 3.将AES的密钥使用公证处提供的RSA公钥加密得到——》加密数据段2
			String encryptedKey = RSAUtil
					.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
					Apis.clientPrivateKey);
			bodyText = this.postMessage(new CABean(Apis.userAuthorUrl, Apis.APIID,
					encryptContent, signed, encryptedKey));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bodyText;
	}

	/**
	 * ca处理公共类
	 * 
	 * @param ca
	 * @return
	 */
	@Override
	public String postMessage(CABean ca)
	{
		String bodyText = "";
		try
		{
			HttpResponse httpresponse = HttpRequest.post(ca.getUrl())
					.formEncoding("UTF-8").form("apiid", ca.getAppid())
					.form("content", ca.getContent()).form("key", ca.getKey())
					.form("signed", ca.getSigned()).timeout(10000).send();
			bodyText = httpresponse.bodyText();
			log.debug("bodyText:" + bodyText);
			// logger.debug("bodyText:"+bodyText);
			httpresponse.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bodyText;
	}

	public void test(RankAgreement rankAgreement) throws Exception
	{
		String aesKey = RandomStringUtils.random(16, true, true);
		Map<String, String> params = new HashMap<String, String>(); // 合约要显示的参数
		params.put("username", rankAgreement.getUsername());
		params.put("certificateno", rankAgreement.getCertificateno());
		params.put("address", rankAgreement.getAddress());
		params.put("roomcnt", rankAgreement.getTotalRoomCnt());
		params.put("rentroomcnt", rankAgreement.getRentRoomCnt());
		params.put("roomarea", rankAgreement.getRoomarea());
		params.put("numbermonery", rankAgreement.getPrice());
		params.put("stringmonery", ConvertNum.NumToChinese(rankAgreement.getPrice()));
		params.put("bgyear", rankAgreement.getBegin_time().split("-")[0]);
		params.put("bgmonth", rankAgreement.getBegin_time().split("-")[1]);
		params.put("bgday", rankAgreement.getBegin_time().split("-")[2]);
		String endDay = DateHelper.addMonthDate(
				new SimpleDateFormat("yyyy-mm-dd").parse(rankAgreement.getBegin_time()),
				Integer.parseInt(rankAgreement.getRent_month()));
		;
		params.put("endyear", endDay.split("-")[0]);
		params.put("endmonth", endDay.split("-")[1]);
		params.put("endday", endDay.split("-")[2]);
		params.put("month", rankAgreement.getRent_month());
		String currentday = DateHelper.getToday();
		params.put("currentday", currentday);
		params.put("watercard", rankAgreement.getWatercard());
		params.put("electriccard", rankAgreement.getElectriccard());
		params.put("gascard", rankAgreement.getGascard());
		params.put("water_meter", rankAgreement.getWater_meter());
		params.put("electricity_meter", rankAgreement.getElectricity_meter());
		params.put("electricity_meter_h", rankAgreement.getElectricity_meter_h());
		params.put("electricity_meter_l", rankAgreement.getElectricity_meter_l());
		params.put("gas_meter", rankAgreement.getGas_meter());
		params.put("currenyear", currentday.split("-")[0]);
		params.put("currenmonth", currentday.split("-")[1]);
		params.put("currentday", currentday.split("-")[2]);
		Map<String, Object> contentMap = new HashMap<String, Object>();
		contentMap.put("userid", "82");
		contentMap.put("docid", rankAgreement.getId());
		contentMap.put("sign_type", "1");
		contentMap.put("parent_docid", "0");
		List<Map<String, Object>> signDocs = new ArrayList<>();
		Map<String, Object> signDoc = new HashMap<>();
		signDoc.put("sign_doc_title", rankAgreement.getName());
		signDoc.put("template_id", "3c128f77-0abc-41ab-8170-081c7d0c5ca0");
		signDoc.put("element_template", params);
		signDocs.add(signDoc);
		contentMap.put("sign_docs", signDocs);
		log.debug(JSONObject.fromObject(contentMap).toString());
		// logger.debug(JSONObject.fromObject(contentMap));
		String contentmapString = Res.WriteJson2String(contentMap);
		log.debug(contentmapString);
		// logger.debug(contentmapString);
		String EncryptContent = AES_CBCUtils.encrypt(aesKey,
				contentmapString.getBytes("utf-8"));
		String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
		String data = EncryptContent + encryptedKey;
		String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
				Apis.clientPrivateKey);
		String bodyText = "";
		log.debug(Apis.APIID);
		// logger.debug(Apis.APIID);
		HttpResponse httpresponse = HttpRequest.post(Apis.showAgreement)
				.form("apiid", Apis.APIID).form("content", EncryptContent)
				.form("key", encryptedKey).form("signed", signed).timeout(10000).send();
		bodyText = httpresponse.bodyText();
		log.debug("bodyText:" + bodyText);
		// logger.debug("bodyText:"+bodyText);
		httpresponse.close();
		log.debug(bodyText);
		// logger.debug(bodyText);
	}

	public static void main(String[] args) throws ParseException
	{
		// Map<String, String> contentmap = new HashMap<String, String>();
		// // 1.生成16位随机的AES KEY
		// String aesKey = RandomStringUtils.random(16, true, true);
		// contentmap.put("notaryid", "XNYCAR201612262232021010004463099"); // 用户姓名
		// String contentmapString =
		// com.alibaba.fastjson.JSONObject.toJSONString(contentmap);
		// String encryptedKey;
		// try
		// {
		// String EncryptContent = AES_CBCUtils.encrypt(aesKey,
		// contentmapString.getBytes("utf-8"));
		// encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
		// String data = EncryptContent + encryptedKey;
		// String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
		// Apis.clientPrivateKey);
		// logger.debug(Apis.APIID);
		// Response.ContentType = "application/octet-stream";
		// HttpResponse httpresponse =
		// HttpRequest.post(Apis.showRankAgreement).form("apiid",
		// Apis.APIID).form("content", EncryptContent).form("key", encryptedKey)
		// .form("signed", signed).timeout(10000).send();
		//
		// }
		// catch (Exception e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }$$
//		String beginTime = "2016-12-12";
//		String type2Des = "";
//		String tmpBeginTime = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		List<Map<String, String>> list = new ArrayList<>();
//		for (int i = 0; i < 3; i++)
//		{
//			Date date = sdf.parse(beginTime);
//			String endTime = DateHelper.addMonthDate(date, 12);
//			beginTime = endTime;
//			Map<String, String> mp = new HashMap<>();
//			mp.put("bgTime", beginTime);
//			mp.put("endTime", endTime);
//			list.add(mp);
//		}
//		for (int i = 0; i < list.size(); i++)
//		{
//			Map<String, String> mp = list.get(i);
//			String bgTime = StringHelper.get(mp, "bgTime");
//			String endTime = StringHelper.get(mp, "endTime");
//			Calendar now = Calendar.getInstance();
//			now.setTime(sdf.parse(bgTime));
//			now.set(Calendar.DATE, now.get(Calendar.DATE) + 3);
//			bgTime = sdf.format(now.getTime());
//			type2Des += bgTime + "至" + endTime + "<br/>";
//		}
//		logger.debug(list);
//		logger.debug(type2Des);
	}
}
