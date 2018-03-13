
package com.yc.rm.caas.appserver.base.syscfg.serv;

import java.util.HashMap;
import java.util.Map;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.raising.framework.utils.md5.MD5Utils;
import com.raising.framework.utils.rsa.RSAUtil;
import com.yc.rm.caas.appserver.model.user.User;
import com.yc.rm.caas.appserver.base.support.BaseService;
import com.yc.rm.caas.appserver.model.syscfg.CABean;
import com.yc.rm.caas.appserver.util.AES_CBCUtils;
import com.yc.rm.caas.appserver.util.Apis;

/**
 * 系统配置信息
 * 
 * @author suntf
 * @date 2016年12月8日
 */
@Service("_sysCfgServImpl")
public class SysCfgServImpl extends BaseService implements ISysCfgServ
{

	public final Logger log = Logger.getLogger(this.getClass());

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
			contentmap.put("linkman_name", u.getUserName()); // 用户姓名
			contentmap.put("linkman_phone", String.valueOf(u.getUserPhone())); // 租客手机号码
			contentmap.put("legal_id", u.getCertificateno()); // 联系人身份证
			contentmap.put("userid", String.valueOf(u.getUserId())); // 用户编号
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
			// System.out.println("bodyText:"+bodyText);
			httpresponse.close();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bodyText;
	}
}
