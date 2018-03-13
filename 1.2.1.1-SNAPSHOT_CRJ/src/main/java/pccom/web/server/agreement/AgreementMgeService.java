/**
 * 
 */

package pccom.web.server.agreement;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raising.framework.utils.md5.MD5Utils;
import com.raising.framework.utils.rsa.RSAUtil;
import com.ycdc.appserver.bus.service.house.HouseMgrService;
import com.ycdc.appserver.bus.service.syscfg.AES_CBCUtils;
import com.ycdc.appserver.bus.service.syscfg.Apis;
import com.ycdc.core.plugin.sms.SmsSendMessage;

import net.sf.json.JSONObject;
import pccom.common.util.Batch;
import pccom.common.util.FtpUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.beans.User;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.BaseService;

/**
 * 合约管理
 * 
 * @author suntf
 * @date 2016年8月18日
 */
@Service("agreementMgeService")
public class AgreementMgeService extends BaseService
{

	/**
	 * 合约管理
	 * 
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年8月18日
	 */
	public void agreementMgeList(HttpServletRequest request, HttpServletResponse response)
	{
		String status = req.getValue(request, "status"); // 合约状态 
		String keyWord = req.getAjaxValue(request, "keyWord"); // 关键字
		String accountid = req.getValue(request, "accountid"); // 合约管家
		String createtime = req.getAjaxValue(request, "createtime");
		String arear = req.getValue(request, "arear"); // 区域
		String isSelf = req.getValue(request, "isSelf"); // 自己
		String trading = req.getValue(request, "trading"); // 商圈
		// String type = req.getValue(request, "type"); // 房屋类型
		List<String> param = new ArrayList<String>();
		String sql = getSql("basehouse.agreement.agreementList");
		if (!"".equals(createtime)) 
		{
			sql += getSql("basehouse.agreement.condition_time");
			param.add(createtime.split("~")[0]);
			param.add(createtime.split("~")[1]);
		}
		// if (!"".equals(type))
		// {
		// sql += getSql("basehouse.agreement.condition_type");
		// param.add(type);
		// }
		if ("1".equals(isSelf))
		{
			// 自己
			sql += getSql("basehouse.agreement.condition_user_id");
			param.add(this.getUser(request).getId());
		}
		if (!"".equals(status))
		{
			sql += getSql("basehouse.agreement.condition_status");
			param.add(status);
		}
		if (!"".equals(accountid))
		{
			sql += getSql("basehouse.agreement.condition_account");
			param.add(accountid);
		}
		if (!"".equals(arear))
		{
			sql += getSql("basehouse.agreement.condition_arear");
			param.add(arear);
		}
		if (!"".equals(trading))
		{
			sql += getSql("basehouse.agreement.trading");
			param.add(trading);
		}
		if (!"".equals(keyWord))
		{
			sql += getSql("basehouse.agreement.condition_keyWord");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
		}
		// logger.debug(str.getSql(sql, param.toArray()));
		// logger.debug(getSql("basehouse.agreement.orderBy"));
		this.getPageList(request, response, sql, param.toArray(),
				"basehouse.agreement.orderBy");
	}

	/**
	 * 是否展示修改按钮
	 * @param request
	 * @return resultMap
	 */
	public Object isShow(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<>();
		User user = getUser(request);
		String roleId = user.getRoles();//角色主键
		String agreementType = req.getAjaxValue(request, "aType");//1:委托合约 2:出租合约
		logger.info("isShow method params "+"agreementType "+agreementType+" roleId "+roleId);
		String sql = "";
		if ("1".equals(agreementType)) {//1:委托合约
			sql = "select count(0) from yc_data_permission a where a.role_id in ("+roleId+") and a.type_id=2 and a.sub_type_id='A' and a.update_permission=1";
		}else {//2:出租合约
			sql = "select count(0) from yc_data_permission a where a.role_id in ("+roleId+") and a.type_id=2 and a.sub_type_id='B' and a.update_permission=1";
		}
		logger.debug("isShow method sql "+sql);
		int status = db.queryForInt(sql);
		logger.debug("isShow method status"+status);
		if (status>0) {
			status=1;
		}else{
			status=0;
		}
		resultMap.put("status", status+"");
		return resultMap;
	}
	
	/**
	 * chenrj
	 * 修改出租合约信息
	 * @param request
	 * @return
	 */
	@Transactional
	public Object UpdateHireAgreement(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		int flag =0;
		try {
			logger.info("UpdateHireAgreement method init");
			//上传合约图片并更新数据
			String path = req.getAjaxValue(request, "picPath"); // 图片路径
			String id = req.getAjaxValue(request, "id");
			String oldMatched = getOldMatched(request);//拼接生成厨具家具字段
			logger.debug("UpdateHireAgreement param oldMatched "+oldMatched);
			logger.debug("UpdateHireAgreement param path "+path);
				String paramSql = createUpdateHireAgreementSql(request);//生成set部分sql语句
				if (null==paramSql||"".equals(paramSql)) {
					resultMap.put("result", "0");//修改失败
					resultMap.put("msg", "faild1");
					return resultMap;
				}
				logger.debug("UpdateHireAgreement param paramSql "+paramSql);
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", paramSql+",old_matched = ?");
				logger.debug("UpdateHireAgreement param sql "+sql);
				Object[] objects = createUpdateHireAgreementParams(request,oldMatched);//生成查询语句？对应参数
				if (null!=path&&!"".equals(path)) {
						Map<String, String> reMap = ImageWork(path,request);//上传图片并返回file_path字段
                        logger.debug("pccom.web.server.agreement.AgreementMgeService.UpdateHireAgreement():"+reMap);
						if(!"1".equals(StringHelper.get(reMap, "state"))){
                                        resultMap.put("result", "0");//上传失败
                                        resultMap.put("msg", "图片上传失败");
                                        return resultMap;
                                    }
                        @SuppressWarnings("static-access")
						String roomPath = str.get(reMap, "newPath");
						String newSql = getSql("basehouse.agreement.updateAgreement").replace(
								"####", "file_path=?");
						flag =db.update(newSql, new Object[]{//更新file_path字段
								roomPath,id
						});
				}
                                else {
					resultMap.put("result", "0");//修改失败
					resultMap.put("msg", "faild2");
					return resultMap;
				}
				if (1==flag) {
					logger.debug("UpdateHireAgreement param objects "+Arrays.toString(objects));
					flag = db.update(sql, objects);//更新除file_path外的字段
				}else {
					resultMap.put("result", "0");//修改失败
					resultMap.put("msg", "faild3");
					logger.debug("UpdateHireAgreement method db update error "+flag);
					return resultMap;
				}
				logger.debug("UpdateHireAgreement method end "+id);
		} catch (Exception e) {
			e.getMessage();
			logger.error("UpdateHireAgreement method error "+e);
			resultMap.put("result", "0");//修改失败
			resultMap.put("msg", "faild4");
			return resultMap;
		}
		resultMap.put("result", "1");//修改成功
		resultMap.put("msg", "success");
		return resultMap;
	}
	
	
	/**
	 * chenrj
	 * 修改委托合约信息
	 * @param request
	 * @return
	 */
	@Transactional
	public Object UpdateEntrustAgreement(HttpServletRequest request) {
		int result = 0;
		Map<String, Object> resultMap = new HashMap<>();
		try { 
			logger.info("UpdateEntrustAgreement method init");
			//上传3种类型附件并更新表中相应字段
			result = UpdateFileForEntrustAgreement(request);
			if (0==result) {//文件上传失败中止操作
				logger.debug(" UpdateEntrustAgreement method updatFile error "+result);
				resultMap.put("result", 0);
				resultMap.put("msg", "faild");
				return resultMap;
			}
			//更新修改的信息到合约表(不包括修改的图片路径)
			String newOldMatched = getNewOldMatched(request);
			String paramSql = createUpdateEntrustAgreementSql(request,newOldMatched);//获取sql中set语句
			if (null==paramSql||"".equals(paramSql)) {
				return result;
			}
			String sql = getSql("basehouse.agreement.updateAgreement").replace(//获取update sql语句
					"####", paramSql);
			logger.debug("UpdateEntrustAgreement sql"+sql);
			Object[] objects = createUpdateEntrustAgreementParams(request,newOldMatched);//获取sql中？对应的参数
			logger.debug("UpdateEntrustAgreement sql params"+Arrays.toString(objects));
			result = db.update(sql, objects);
			if (result==1) {
				resultMap.put("result", "1");//修改成功
				resultMap.put("msg", "success");
			}else{
				resultMap.put("result", "0");//修改失败
				resultMap.put("msg", "faild");
			}
			logger.debug("UpdateEntrustAgreement method end"+result);
			return resultMap;
		} catch (Exception e) {
			e.getMessage();
			logger.error("UpdateEntrustAgreement method error"+e);
			resultMap.put("result", "0");//修改失败
			resultMap.put("msg", "faild");
			return resultMap;
		}
	}
	
	/**
	 * chenrj
	 * 上传并更新委托合约附件
	 * @param request
	 * @return
	 */
	@SuppressWarnings("static-access")
	@Transactional
	private int UpdateFileForEntrustAgreement(HttpServletRequest request) {
		int result = 0;
		boolean upRes = false;
		String newPath = "";
		Map<String, String> resultMap = new HashMap<>();
		List<String> params = new ArrayList<>();
		StringBuffer sqlBuffer = new StringBuffer();
		//上传合约图片
		String path = ""; // 图片路径
		if (validateParam(req.getAjaxValue(request, "picPath"))) {//file_path 房产附件
			path = req.getAjaxValue(request, "picPath");
			resultMap = ImageWork(path, request);
			newPath = str.get(resultMap, "newPath");
			params.add(newPath);
			sqlBuffer.append("file_path = ?,");
			if (!"".equals(newPath)) {
				upRes = true;
			}
		}
		if (validateParam(req.getAjaxValue(request, "propertyPath"))) {//产权人附件
			path = req.getAjaxValue(request, "propertyPath");
			resultMap = ImageWork(path, request);
			newPath = str.get(resultMap, "newPath");
			params.add(newPath);
			sqlBuffer.append("propertyPath = ?,");
			if (!"".equals(newPath)) {
				upRes = true;
			}
		}
		if (validateParam(req.getAjaxValue(request, "agentPath"))) {//代理人附件
			path = req.getAjaxValue(request, "agentPath");
			resultMap = ImageWork(path, request);
			newPath = str.get(resultMap, "newPath");
			params.add(newPath);
			sqlBuffer.append("agentPath = ?,");
			if (!"".equals(newPath)) {
				upRes = true;
			}
		}
		if (upRes) {
			params.add(req.getAjaxValue(request, "agreementId"));//需更新记录的id
			if (','==sqlBuffer.charAt(sqlBuffer.length()-1)) {
				sqlBuffer.replace(sqlBuffer.length()-1, sqlBuffer.length(), "");
			}
			Object[] objects = new Object[params.size()];
			for (int i = 0; i < params.size(); i++) {
				objects[i]=params.get(i);
			}
			logger.debug(" UpdateFileForEntrustAgreement sqlBuffer"+sqlBuffer);
			//更新字段
			String sql = getSql("basehouse.agreement.updateAgreement").replace(
					"####", sqlBuffer.toString());
			logger.debug(" UpdateFileForEntrustAgreement sql"+sql);
			logger.debug(" UpdateFileForEntrustAgreement objects"+Arrays.toString(objects));
			result = db.update(sql, objects);
			return result;
		}else {
			logger.debug(" UpdateFileForEntrustAgreement upload file error "+result);
			return result;
		}
	}
	
	/**
	 * 图片处理公共方法
	 * chenrj
	 * 
	 * 
	 * */
	private Map<String, String> ImageWork(String images, HttpServletRequest request)
	{
		String newPath = "";
		String houseId = req.getAjaxValue(request, "houseId");
		String houseSplit = "/" + houseId + SystemConfig.getValue("FTP_RANK_AGREEMENT_PATH"); // 房源分隔
		String id = req.getAjaxValue(request, "id");
		images = images.replace(",", "|");
		FtpUtil ftp = null;
	    Map<String , String> gmap=new HashMap<String, String>();
		try 
		{
			ftp = new FtpUtil();
			if (!"".equals(images)) 
			{
				String[] pathArray = images.split("\\|");
				for (int j = 0; j < pathArray.length; j++)
				{
					if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"))) 
					{
						logger.debug(" imageWork pathArray "+pathArray[j]);
						newPath += pathArray[j].replace(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"),"")+ "|";
							continue;
					}
					String tmpPath = String.valueOf(id)+ UUID.randomUUID().toString().replaceAll("-", "")+ ".png";
						newPath += houseSplit + id + "/" + tmpPath+"|";
						boolean flag = ftp.uploadFile(request.getRealPath("/")+ pathArray[j], tmpPath,SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit +id+"/");
						if (!flag) 
						{
							gmap.put("state","-3");
							return gmap;
					    }
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			gmap.put("state","-3");
			return gmap;
		} 
		finally
		{
			// 关闭流
			if (ftp != null) {
				ftp.closeServer();
			}
		}
		newPath = newPath.substring(0,newPath.length()-1);
		gmap.put("state","1");
		gmap.put("newPath", newPath);
		logger.debug(" ImageWork method result "+newPath);
		return gmap;
	}
	/**
	 * chenrj
	 * 委托合约生成sql参数方法
	 * @param params
	 * @return sql-sql语句中set部分的字段
	 */
	private String createUpdateEntrustAgreementSql(HttpServletRequest request,String newOldMatched){
		StringBuffer sql = new StringBuffer();
		if (validateParam(req.getAjaxValue(request, "propertyFile"))) {//产权证明文件
			sql.append("propertyType = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "mortgage"))) {//抵押情况
			sql.append("mortgage = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "propertyCode"))) {//产权证明文件编号
			sql.append("propertyCode = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "propertMan"))) {//产权人
			sql.append("propertyPerson = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "co_owner"))) {//共有权人
			sql.append("co_owner = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "wtmobile"))) {//委托人号码
			sql.append("wtmobile = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "wtAddress"))) {//委托人住址
			sql.append("wtAddress = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "wtIDCard"))) {//委托人身份证
			sql.append("wtIDCard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "wtname"))) {//委托方姓名
			sql.append("wtname = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "wtemail"))) {//委托方邮件
			sql.append("wtemail = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "dlmobile"))) {//代理人号码
			sql.append("dlmobile = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "dlAddress"))) {//代理人住址
			sql.append("dlAddress = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "dlIDCard"))) {//代理人身份证
			sql.append("dlIDCard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "dlname"))) {//代理人姓名
			sql.append("dlname = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "dlemail"))) {//代理方邮件
			sql.append("dlemail = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardText"))) {//业主卡号
			sql.append("bankcard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardPressonName"))) {//开卡人姓名
			sql.append("cardowen = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "carBank"))) {//开户银行名
			sql.append("cardbank = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardelectric"))) {//电卡账号
			sql.append("electriccard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "eMeter"))) {//电表总值
			sql.append("electricity_meter = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "eMeterH"))) {//电表峰值
			sql.append("electricity_meter_h = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "eMeterL"))) {//电表谷值
			sql.append("electricity_meter_l = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardWarter"))) {//水卡账号
			sql.append("watercard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "warterDegrees"))) {//水表读数
			sql.append("water_meter = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardgas"))) {//燃气账号
			sql.append("gascard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "gasDegrees"))) {//燃气读数
			sql.append("gas_meter = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "descInfo"))) {//合约描述
			sql.append("desc_text = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "contractName"))) {//合约名称
			sql.append("name = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "doorkey"))) {//合约描述
			sql.append("keys_count = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "oldMatched"))) {//旧合约中房间配置
			sql.append("old_matched = ?,");
		}
		if (validateParam(newOldMatched)) {//新合约中房间配置
			sql.append("new_old_matched = ?,");
		}
		if (','==(sql.charAt(sql.length()-1))) {
			sql = sql.replace(sql.length()-1, sql.length(), "");
		}
		return sql.toString();
	}
	
	/**
	 * chenrj
	 * 委托合约生成sql的查询参数
	 * @param request
	 * @return objects 查询语句所需要的参数值
	 */
	private Object[] createUpdateEntrustAgreementParams(HttpServletRequest request,String newOldMatched){
		List<String> params = new ArrayList<>();	
		if (validateParam(req.getAjaxValue(request, "propertyFile"))) {//产权证明文件
			params.add(req.getAjaxValue(request, "propertyFile"));
		}
		if (validateParam(req.getAjaxValue(request, "mortgage"))) {//抵押情况
			params.add(req.getAjaxValue(request, "mortgage"));
		}
		if (validateParam(req.getAjaxValue(request, "propertyCode"))) {//产权证明文件编号
			params.add(req.getAjaxValue(request, "propertyCode"));
		}
		if (validateParam(req.getAjaxValue(request, "propertMan"))) {//产权人
			params.add(req.getAjaxValue(request, "propertMan"));
		}
		if (validateParam(req.getAjaxValue(request, "co_owner"))) {//共有权人
			params.add(req.getAjaxValue(request, "co_owner"));
		}
		if (validateParam(req.getAjaxValue(request, "wtmobile"))) {//委托人号码
			params.add(req.getAjaxValue(request, "wtmobile"));
		}
		if (validateParam(req.getAjaxValue(request, "wtAddress"))) {//委托人住址
			params.add(req.getAjaxValue(request, "wtAddress"));
		}
		if (validateParam(req.getAjaxValue(request, "wtIDCard"))) {//委托人身份证
			params.add(req.getAjaxValue(request, "wtIDCard"));
		}
		if (validateParam(req.getAjaxValue(request, "wtname"))) {//委托方姓名
			params.add(req.getAjaxValue(request, "wtname"));
		}
		if (validateParam(req.getAjaxValue(request, "wtemail"))) {//委托方邮件
			params.add(req.getAjaxValue(request, "wtemail"));
		}
		if (validateParam(req.getAjaxValue(request, "dlmobile"))) {//代理人号码
			params.add(req.getAjaxValue(request, "dlmobile"));
		}
		if (validateParam(req.getAjaxValue(request, "dlAddress"))) {//代理人住址
			params.add(req.getAjaxValue(request, "dlAddress"));
		}
		if (validateParam(req.getAjaxValue(request, "dlIDCard"))) {//代理人身份证
			params.add(req.getAjaxValue(request, "dlIDCard"));
		}
		if (validateParam(req.getAjaxValue(request, "dlname"))) {//代理人姓名
			params.add(req.getAjaxValue(request, "dlname"));
		}
		if (validateParam(req.getAjaxValue(request, "dlemail"))) {//代理方邮件
			params.add(req.getAjaxValue(request, "dlemail"));
		}
		if (validateParam(req.getAjaxValue(request, "cardText"))) {//业主卡号
			params.add(req.getAjaxValue(request, "cardText"));
		}
		if (validateParam(req.getAjaxValue(request, "cardPressonName"))) {//开卡人姓名
			params.add(req.getAjaxValue(request, "cardPressonName"));
		}
		if (validateParam(req.getAjaxValue(request, "carBank"))) {//开户银行名
			params.add(req.getAjaxValue(request, "carBank"));
		}
		if (validateParam(req.getAjaxValue(request, "cardelectric"))) {//电卡账号
			params.add(req.getAjaxValue(request, "cardelectric"));
		}
		if (validateParam(req.getAjaxValue(request, "eMeter"))) {//电表总值
			params.add(req.getAjaxValue(request, "eMeter"));
		}
		if (validateParam(req.getAjaxValue(request, "eMeterH"))) {//电表峰值
			params.add(req.getAjaxValue(request, "eMeterH"));
		}
		if (validateParam(req.getAjaxValue(request, "eMeterL"))) {//电表谷值
			params.add(req.getAjaxValue(request, "eMeterL"));
		}
		if (validateParam(req.getAjaxValue(request, "cardWarter"))) {//水卡账号
			params.add(req.getAjaxValue(request, "cardWarter"));
		}
		if (validateParam(req.getAjaxValue(request, "warterDegrees"))) {//水表读数
			params.add(req.getAjaxValue(request, "warterDegrees"));
		}
		if (validateParam(req.getAjaxValue(request, "cardgas"))) {//燃气账号
			params.add(req.getAjaxValue(request, "cardgas"));
		}
		if (validateParam(req.getAjaxValue(request, "gasDegrees"))) {//燃气读数
			params.add(req.getAjaxValue(request, "gasDegrees"));
		}
		if (validateParam(req.getAjaxValue(request, "descInfo"))) {//合约描述
			params.add(req.getAjaxValue(request, "descInfo"));
		}
		if (validateParam(req.getAjaxValue(request, "contractName"))) {
			params.add(req.getAjaxValue(request, "contractName"));
		}
 		if (validateParam(req.getAjaxValue(request, "keys_count"))) {
			String keys_count = req.getAjaxValue(request, "keys_count");//门禁钥匙楼层入户字段
			params.add(keys_count);
		}
 		if (validateParam(req.getAjaxValue(request, "oldMatched"))) {
			params.add(req.getAjaxValue(request, "oldMatched"));//旧合约房间配置字段
		}
		if (validateParam(newOldMatched)) {//新合约房间配置字段
			params.add(newOldMatched);
		}
 		if (validateParam(req.getAjaxValue(request, "agreementId"))) {
			params.add(req.getAjaxValue(request, "agreementId"));
		}
		Object[] objects = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			objects[i]=params.get(i);
		}
		return objects;
	}
	
	/**
	 * chenrj
	 * 委托合约中家具及厨卫字段参数
	 * @param request
	 * @return newoldmatched 家具及厨卫信息
	 */
	private String getNewOldMatched (HttpServletRequest request) {
		String roomCnt = req.getAjaxValue(request, "roomCnt");
		String[] array =
			{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
					"x", "y", "z" };
			String regx = "^\\d+$";
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(roomCnt);
			if (!matcher.matches())
			{
				roomCnt = "0";
			}
			int cnt = Integer.parseInt(roomCnt);
			String old_m = "";
			for (int i = 0; i < cnt; i++)
			{
				String name = array[i];
				String[] values = req.getValues(request, name);
				for (int j = 0; j < values.length; j++)
				{
					if (j == 0 && i > 0)
					{
						old_m += "##";
					}
					if (j > 0)
					{
						old_m += "|";
					}
					old_m += name + j + "=" + values[j];
				}
			}
			logger.debug(old_m);
			String[] kt = req.getValues(request, "kt"); // 客厅
			for (int i = 0; i < kt.length; i++)
			{
				if (i == 0)
				{
					old_m += "@@" + "kt" + i + "=" + kt[i];
				} else
				{
					old_m += "|" + "kt" + i + "=" + kt[i];
				}
			}
			return old_m;
	}
	
	/**
	 * chenrj
	 * 出租合约生成sql参数方法
	 * @param params
	 * @return
	 */
	private String createUpdateHireAgreementSql(HttpServletRequest request){
		StringBuffer sql = new StringBuffer();
		if (validateParam(req.getAjaxValue(request, "cardelectric"))) {//电卡账号
			sql.append("electriccard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "eMeter"))) {//电表总值
					sql.append("electricity_meter = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "eMeterH"))) {//电表峰值
					sql.append("electricity_meter_h = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "eMeterL"))) {//电表谷值
					sql.append("electricity_meter_l = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardWarter"))) {//水卡账号
					sql.append("watercard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "warterDegrees"))) {//水表读数
					sql.append("water_meter = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "cardgas"))) {//燃气账号
					sql.append("gascard = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "agreement_name"))) {//合约名称
					sql.append("name = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "agreementDes"))) {//合约描述
					sql.append("desc_text = ?,");
		}
		if (validateParam(req.getAjaxValue(request, "gasDegrees"))) {//燃气读数
					sql.append("gas_meter = ?,");
		}
		if (','==(sql.charAt(sql.length()-1))) {
			sql = sql.replace(sql.length()-1, sql.length(), "");
		}
		return sql.toString();
	}
	
	/**
	 * chenrj
	 * 出租合约生成sql的查询参数
	 * @param request
	 * @return
	 */
	private Object[] createUpdateHireAgreementParams(HttpServletRequest request,String oldMatched){
		List<String> params = new ArrayList<>();	
		if (validateParam(req.getAjaxValue(request, "cardelectric"))) {
			params.add(req.getAjaxValue(request, "cardelectric"));
		}
		if (validateParam(req.getAjaxValue(request, "eMeter"))) {
			params.add(req.getAjaxValue(request, "eMeter"));
		}
 		if (validateParam(req.getAjaxValue(request, "eMeterH"))) {
			params.add(req.getAjaxValue(request, "eMeterH"));
		}
 		if (validateParam(req.getAjaxValue(request, "eMeterL"))) {
			params.add(req.getAjaxValue(request, "eMeterL"));
		}
 		if (validateParam(req.getAjaxValue(request, "cardWarter"))) {
			params.add(req.getAjaxValue(request, "cardWarter"));
		}
 		if (validateParam(req.getAjaxValue(request, "warterDegrees"))) {
			params.add(req.getAjaxValue(request, "warterDegrees"));
		}
 		if (validateParam(req.getAjaxValue(request, "cardgas"))) {
			params.add(req.getAjaxValue(request, "cardgas"));
		}
 		if (validateParam(req.getAjaxValue(request, "agreement_name"))) {
			params.add(req.getAjaxValue(request, "agreement_name"));
		}
 		if (validateParam(req.getAjaxValue(request, "agreementDes"))) {
			params.add(req.getAjaxValue(request, "agreementDes"));
		}
 		if (validateParam(req.getAjaxValue(request, "gasDegrees"))) {
			params.add(req.getAjaxValue(request, "gasDegrees"));
		}
 		if (validateParam(oldMatched)) {
			params.add(oldMatched);
		}
 		if (validateParam(req.getAjaxValue(request, "id"))) {
 			params.add(req.getAjaxValue(request, "id"));
		}
		Object[] objects = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			objects[i]=params.get(i);
		}
		return objects;
	}
	
	/**
	 * chenrj
	 * 拼接家具厨具字段-
	 * @param request
	 * @return olgMatched 家具厨具字段
	 */
	private String getOldMatched(HttpServletRequest request){
		StringBuffer oleMatched = new StringBuffer();
		String a0 = req.getAjaxValue(request, "a0");
		if (validateParam(a0)) {
			oleMatched.append("a0="+a0);
		}else {
			oleMatched.append("a0=");
		}
		String a1 = req.getAjaxValue(request, "a1");
		if (validateParam(a1)) {
			oleMatched.append("|a1="+a1);
		}else {
			oleMatched.append("|a1=");
		}
		String a2 = req.getAjaxValue(request, "a2");
		if (validateParam(a2)) {
			oleMatched.append("|a2="+a2);
		}else {
			oleMatched.append("|a2=");
		}
		String a3 = req.getAjaxValue(request, "a3");
		if (validateParam(a3)) {
			oleMatched.append("|a3="+a3);
		}else {
			oleMatched.append("|a3=");
		}
		String a4 = req.getAjaxValue(request, "a4");
		if (validateParam(a4)) {
			oleMatched.append("|a4="+a4);
		}else {
			oleMatched.append("|a4=");
		}
		String a5 = req.getAjaxValue(request, "a5");
		if (validateParam(a5)) {
			oleMatched.append("|a5="+a5);
		}else {
			oleMatched.append("|a5=");
		}
		String a6 = req.getAjaxValue(request, "a6");
		if (validateParam(a6)) {
			oleMatched.append("|a6="+a6);
		}else {
			oleMatched.append("|a6=");
		}
		String a7 = req.getAjaxValue(request, "a7");
		if (validateParam(a7)) {
			oleMatched.append("|a7="+a7);
		}else {
			oleMatched.append("|a7=");
		}
		String a8 = req.getAjaxValue(request, "a8");
		if (validateParam(a8)) {
			oleMatched.append("|a8="+a8);
		}else {
			oleMatched.append("|a8=");
		}
		String a9 = req.getAjaxValue(request, "a9");
		if (validateParam(a9)) {
			oleMatched.append("|a9="+a9);
		}else {
			oleMatched.append("|a9=");
		}
		String a10 = req.getAjaxValue(request, "a10");
		if (validateParam(a10)) {
			oleMatched.append("|a10="+a10);
		}else {
			oleMatched.append("|a10=");
		}
		String a11 = req.getAjaxValue(request, "a11");
		if (validateParam(a11)) {
			oleMatched.append("|a11="+a11);
		}else {
			oleMatched.append("|a11=");
		}
		String a12 = req.getAjaxValue(request, "a12");
		if (validateParam(a12)) {
			oleMatched.append("|a12="+a12);
		}else {
			oleMatched.append("|a12=");
		}
		String a13 = req.getAjaxValue(request, "a13");
		if (validateParam(a13)) {
			oleMatched.append("|a13="+a13);
		}else {
			oleMatched.append("|a13=");
		}
		String a14 = req.getAjaxValue(request, "a14");
		if (validateParam(a14)) {
			oleMatched.append("|a14="+a14);
		}else {
			oleMatched.append("|a14=");
		}
		String a15 = req.getAjaxValue(request, "a15");
		if (validateParam(a15)) {
			oleMatched.append("|a15="+a15);
		}else {
			oleMatched.append("|a15=");
		}
		String a16 = req.getAjaxValue(request, "a16");
		if (validateParam(a16)) {
			oleMatched.append("|a16="+a16);
		}else {
			oleMatched.append("|a16=");
		}
		String a17 = req.getAjaxValue(request, "a17");
		if (validateParam(a17)) {
			oleMatched.append("|a17="+a17);
		}else {
			oleMatched.append("|a17=");
		}
		String a18 = req.getAjaxValue(request, "a18");
		if (validateParam(a18)) {
			oleMatched.append("|a18="+a18);
		}else {
			oleMatched.append("|a18=");
		}
		String a19 = req.getAjaxValue(request, "a19");
		if (validateParam(a19)) {
			oleMatched.append("|a19="+a19);
		}else {
			oleMatched.append("|a19=");
		}
		String a20 = req.getAjaxValue(request, "a20");
		if (validateParam(a20)) {
			oleMatched.append("|a20="+a20);
		}else {
			oleMatched.append("|a20=");
		}
		String a21 = req.getAjaxValue(request, "a21");
		if (validateParam(a21)) {
			oleMatched.append("|a21="+a21);
		}else {
			oleMatched.append("|a21=");
		}
		String a22 = req.getAjaxValue(request, "a22");
		if (validateParam(a22)) {
			oleMatched.append("|a22="+a22);
		}else {
			oleMatched.append("|a22=");
		}
		String a23 = req.getAjaxValue(request, "a23");
		if (validateParam(a23)) {
			oleMatched.append("|a23="+a23);
		}else {
			oleMatched.append("|a23=");
		}
		String b0 = req.getAjaxValue(request, "b0");
		if (validateParam(b0)) {
			oleMatched.append("##b0="+b0);
		}else {
			oleMatched.append("##b0=");
		}
		String b1 = req.getAjaxValue(request, "b1");
		if (validateParam(b1)) {
			oleMatched.append("|b1="+b1);
		}else {
			oleMatched.append("|b1=");
		}
		String b2 = req.getAjaxValue(request, "b2");
		if (validateParam(a0)) {
			oleMatched.append("|b2="+b2);
		}else {
			oleMatched.append("|b2=");
		}
		String b3 = req.getAjaxValue(request, "b3");
		if (validateParam(b3)) {
			oleMatched.append("|b3="+b3);
		}else {
			oleMatched.append("|b3=");
		}
		String b4 = req.getAjaxValue(request, "b4");
		if (validateParam(b4)) {
			oleMatched.append("|b4="+b4);
		}else {
			oleMatched.append("|b4=");
		}
		String b5 = req.getAjaxValue(request, "b5");
		if (validateParam(b5)) {
			oleMatched.append("|b5="+b5);
		}else {
			oleMatched.append("|b5=");
		}
		String b6 = req.getAjaxValue(request, "b6");
		if (validateParam(b6)) {
			oleMatched.append("|b6="+b6);
		}else {
			oleMatched.append("|b6=");
		}
		String b7 = req.getAjaxValue(request, "b7");
		if (validateParam(b7)) {
			oleMatched.append("|b7="+b7);
		}else {
			oleMatched.append("|b7=");
		}
		String b8 = req.getAjaxValue(request, "b8");
		if (validateParam(b8)) {
			oleMatched.append("|b8="+b8);
		}else {
			oleMatched.append("|b8=");
		}
		String b9 = req.getAjaxValue(request, "b9");
		if (validateParam(b9)) {
			oleMatched.append("|b9="+b9);
		}else {
			oleMatched.append("|b9=");
		}
		logger.debug(" getOldMatched result "+a0+a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12+a13+a14+a15+a16+b0+b1+b2+b3+b4+b5+b6);
		return oleMatched.toString();
	}
	
	/**
	 * 参数校验
	 * @param param
	 * @return
	 */
	private boolean validateParam(String param){
		boolean result = false;
		if (null!= param &&!"".equals(param)) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 获取银行列表信息
	 * 
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月1日
	 */
	public void getBankList(HttpServletRequest request, HttpServletResponse response)
	{
		String bank_name = req.getAjaxValue(request, "group_name"); // 银行名称
		String areaid = req.getValue(request, "areaid"); // 区域信息
		String bankId = req.getValue(request, "bankId"); // 银行大类
		String sql = getSql("basehouse.agreement.bank_list");
		List<String> list = new ArrayList<>();
		if (!"".equals(bank_name))
		{
			sql += getSql("basehouse.agreement.bank_name");
			list.add("%" + bank_name + "%");
		}
		if (!"".equals(areaid))
		{
			sql += getSql("basehouse.agreement.bank_arear");
			list.add(areaid);
		}
		if (!"".equals(bankId))
		{
			sql += getSql("basehouse.agreement.father_id");
			list.add(bankId);
		}
		this.getPageList(request, response, sql, list.toArray());
	}

	/**
	 * 获取银行大类
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年9月5日
	 */
	public Object getBankList(HttpServletRequest request)
	{
		String bankId = req.getValue(request, "bankId"); // 银行大类
		String sql = getSql("basehouse.agreement.bank_list");
		List<String> list = new ArrayList<>();
		if (!"".equals(bankId))
		{
			sql += getSql("basehouse.agreement.father_id");
			list.add(bankId);
		}
		return db.queryForList(sql, list);
	}

	/**
	 * 合约明细
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月18日
	 */
	public Object agreementInfo(HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // 合约id
		String qz = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		String sql = getSql("basehouse.agreement.agreementInfo");
		logger.debug(str.getSql(sql, new Object[] { qz, "|" + qz, qz, "|" + qz, qz,
				"|" + qz, id }));
		return db.queryForMap(sql, new Object[] { qz, "|" + qz, qz, "|" + qz, qz,
				"|" + qz, id });
	}

	/**
	 * 新出租合约
	 * 
	 * @param request
	 * @param houseMgrService
	 * @return
	 */
	public Object newAgreementInfo(HttpServletRequest request,
			HouseMgrService houseMgrService)
	{
		String id = req.getValue(request, "id"); // 合约id
		return houseMgrService.getAgreementInfo(id, "");
	}

	/**
	 * 删除合约状态
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	public Object delAgreement(HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // 合约id
		String houseId = req.getValue(request, "houseId"); // 房源id
		String status = req.getValue(request, "status"); // 房源状态
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("houseId", houseId);
		json.put("status", status);
		json.put("operId", this.getUser(request).getId());
		logger.debug(json.toString());
		return this.newDelAgreement(json, request);
	}

	/**
	 * 删除合约信息
	 * 
	 * @param request
	 * @return
	 */
	public Object newDelAgreement(final JSONObject json, final HttpServletRequest request)
	{
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				String id = StringHelper.get(json, "id"); // 合约id
				String houseId = StringHelper.get(json, "houseId"); // 房源id
				String status = StringHelper.get(json, "status"); // 状态
				String operId = StringHelper.get(json, "operId"); // 操作人
				Map<String, String> resultMap = new HashMap<>();
				resultMap.put("state", "1");
				resultMap.put("msg", "操作成功");
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约状态 是否被删除或者状态已经改变
				sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####",
						" and type =1 and isdelete = 1");
				logger.debug(str.getSql(sql, new Object[] { id, status }));
				if (this.queryForInt(sql, new Object[] { id, status }) == 0)
				{
					resultMap.put("state", "-2");
					resultMap.put("msg", "合约状态已经改变");
					return resultMap;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"删除合约信息", new ArrayList<String>(), operId);
				// 删除合约信息
				sql = getSql("basehouse.agreement.updateAgreement").replace("####",
						" isdelete = 0 ");
				this.update(sql, new Object[] { id });
				// 更新房源信息 房源状态更新为待签约
				this.insertTableLog(request, "yc_house_tab",
						" and id ='" + houseId + "'", "更新房源为待签约",
						new ArrayList<String>(), operId);
				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						" house_status = 2");
				this.update(sql, new Object[] { houseId });
				return resultMap;
			}
		});
		logger.debug(obj.toString());
		if (obj == null)
		{
			return getReturnMap("-1", "网络忙");
		}
		return obj;
	}

	/**
	 * 提交合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object submitAgreement(final HttpServletRequest request)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String id = req.getValue(request, "id"); // 合约id
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约状态 是否被删除或者状态已经改变
				sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####",
						" and type = 1 and isdelete = 1");
				if (this.queryForInt(sql, new Object[] { id, 0 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"提交合约信息", new ArrayList<String>(), getUser(request).getId());
				// 提交合约信息
				sql = getSql("basehouse.agreement.updateAgreement").replace("####",
						" status = 1 ");
				this.update(sql, new Object[] { id });
				return 1;
			}
		}));
	}

	/**
	 * 审批合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object approvalAgreement(final HttpServletRequest request,
			final Financial financial, final Onstruction onstruction)
	{
		String id = req.getValue(request, "id"); // 合约id
		String isPass = req.getValue(request, "isPass"); // 合约状态 0 合约审核不通过 2 审核通过
		String houseId = req.getValue(request, "houseId"); // 房源id
		String status = req.getValue(request, "status"); // 合约状态
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("houseId", houseId);
		json.put("isPass", isPass);
		json.put("status", status);
		json.put("operId", this.getUser(request).getId());
		return this.approvalHouseAgreement(request, json, financial, onstruction);
	}

	@SuppressWarnings("unchecked")
	public Object approvalHouseAgreement(final HttpServletRequest request,
			final JSONObject json, final Financial financial,
			final Onstruction onstruction)
	{
		return db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				Map<String, String> mp = new HashMap<>();
				String id = StringHelper.get(json, "id"); // 合约id
				String isPass = StringHelper.get(json, "isPass"); // 合约状态 0 合约审核不通过 2 审核通过
				String houseId = StringHelper.get(json, "houseId"); // 房源id
				String operId = StringHelper.get(json, "operId"); // 房源id
				String status = StringHelper.get(json, "status");
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否被审批
				sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####",
						" and type = 1 and a.`isdelete` = 1 ");
				if (this.queryForInt(sql, new Object[] { id, status }) == 0)
				{
					mp.put("msg", "合约状态改变");
					mp.put("state", "-2");
					return mp;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"0".equals(isPass) ? "合约审核不通过" : "审核通过", new ArrayList<String>(),
						operId);
				// String result = "".equals(notaryid)?"":" ,notaryid='"+notaryid+"'";
				if ("12".equals(status) && "0".equals(isPass))
				{
					// 删除合约 更新房源
					sql = getSql("basehouse.agreement.updateAgreement").replace("####",
							" `isdelete` = 0 ");
					this.update(sql, new Object[] { id });
					// 更新房源
					sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
							" house_status = 2");
					this.update(sql, new Object[] { houseId });
				}
				else
				{
					// 提交合约信息
					sql = getSql("basehouse.agreement.updateAgreement").replace("####",
							" status = ? ");
					logger.debug(str.getSql(sql, new Object[] { isPass, id }));
					this.update(sql, new Object[] { isPass, id });
				}
				// 调用 刘飞接口 雷杨接口
				if ("2".equals(isPass))
				{
					// 更新房源状态
					this.insertTableLog(request, "yc_house_tab", " and id ='" + houseId
							+ "'", "修改房源状态为待开工", new ArrayList<String>(), operId);
					sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
							" house_status = 4");
					logger.debug(str.getSql(sql, new Object[] { houseId }));
					this.update(sql, new Object[] { houseId });
					// 雷杨新增财务信息
					Map<String, Object> params = this.queryForMap(
							getSql("financial.houseAgreementInfo"), new Object[] { id });
					String freeRankType = StringHelper.get(params, "freeRankType");
					String cost_a = StringHelper.get(params, "cost_a");
					String free_period = StringHelper.get(params, "free_period");
					if ("2".equals(freeRankType))
					{
						String temp_free_period = "";
						for (int i = 0; i < cost_a.split("\\|").length; i++)
						{
							temp_free_period += "|" + free_period;
						}
						if (!"".equals(temp_free_period))
						{
							params.put("free_period", temp_free_period.substring(1));
						}
					}
					params.put("operid", operId);
					int res = financial.takeHouse(params, this);
					if (res != 1)
					{
						Map<String, String> exceptionMap = new HashMap<>();
						exceptionMap.put("function", "在线签约");
						exceptionMap.put("describe", "收房合约，调用财务接口失败，返回:-12,agreementId:"
								+ id);
						SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
						this.rollBack();
						mp.put("msg", "财务项目内部错误");
						mp.put("state", "-12");
						return mp;
					}
					// 刘飞插入工程数据
					res = onstruction.insertOn(params, this);
					if (res != 1)
					{
						Map<String, String> exceptionMap = new HashMap<>();
						exceptionMap.put("function", "在线签约");
						exceptionMap.put("describe",
								"收房合约，调用工程模块调用失败，返回:-12,agreementId:" + id);
						SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
						this.rollBack();
						mp.put("msg", "工程项目内部错误");
						mp.put("state", "-10");
						return mp;
					}
				}
				mp.put("msg", "操作成功");
				mp.put("state", "1");
				return mp;
			}
		});
	}

	/**
	 * 撤销合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object cancelAgreement(final HttpServletRequest request,
			final Onstruction onstruction)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String housid = req.getValue(request, "housid"); // 房源id
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否在已生效状态
				sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####",
						" and type = 1 ");
				if (this.queryForInt(sql, new Object[] { id, 2 }) == 0)
				{
					return -2;
				}
				/*
				 * Map map = new HashMap(); // checkstatus map.put("houseid", housid); int
				 * res = onstruction.checkstatus(map, this); if (res != 1) { return res; }
				 */
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"合约撤销待审核", new ArrayList<String>(), getUser(request).getId());
				// 提交合约信息
				sql = getSql("basehouse.agreement.updateAgreement").replace("####",
						" status = ? ");
				this.update(sql, new Object[] { 4, id });
				return 1;
			}
		}));
	}

	/**
	 * 将合约置为无效
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object spAgeement(final HttpServletRequest request, final Financial financial,
			final Onstruction onstruction)
	{
		// return getReturnMap(db.doInTransaction(new Batch<Object>()
		// {
		// /*
		// * @see pccom.common.util.BatchBase#execute()
		// */
		// @Override
		// public Object execute() throws Exception
		// {
		// String id = req.getValue(request, "id"); // 合约id
		// String isPass = req.getValue(request, "isPass"); // 通过 3 合同无效 不通过 2 已经生效
		// String houseId = req.getValue(request, "houseId");
		// logger.debug(isPass+":"+houseId);
		// String sql = getSql("basehouse.agreement.updateAgreement").replace("####",
		// " id = ? ");
		// this.update(sql, new Object[]{id,id});
		// // 验证合约是否在已生效状态
		// sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####",
		// " and type =1 ");
		// if(this.queryForInt(sql, new Object[]{id,4})==0)
		// {
		// return -2;
		// }
		//
		// this.insertTableLog(request, "yc_agreement_tab", " and id='"+id+"'",
		// "3".equals(isPass)?"将合约置为无效":"合同还是有效", new ArrayList<String>(),
		// getUser(request).getId());
		//
		// // 合约失效
		// sql = getSql("basehouse.agreement.updateAgreement").replace("####",
		// " status = ? ");
		// this.update(sql, new Object[]{isPass,id});
		//
		// if("3".equals(isPass))
		// {
		// this.insertTableLog(request, "yc_house_tab", " and id ='"+houseId+"'",
		// "合同失效更新房源信息", new ArrayList<String>(), getUser(request).getId());
		// //更新房源信息
		// sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
		// " house_status = ?, update_time = now() ");
		// this.update(sql, new Object[]{10, houseId});
		//
		//
		// // 刘飞 雷杨
		// // 雷杨撤销财务
		// Map<String, String> params = new HashMap<>();
		//
		//
		// params.put("houseid", houseId);
		// int res = onstruction.deleteOn(params, this);
		// if(res != 1)
		// {
		// this.rollBack();
		// return res;
		// }
		// //刘飞
		// params.clear();
		//
		// params.put("agreement_id", id);
		// res = financial.repealHouse(params, this);
		// if(res != 1)
		// {
		// this.rollBack();
		// return -12;
		// }
		//
		//
		//
		// }
		// return 1;
		// }
		// }));
		JSONObject json = new JSONObject();
		String id = req.getValue(request, "id"); // 合约id
		String isPass = req.getValue(request, "isPass"); // 通过 3 合同无效 不通过 2 已经生效
		String houseId = req.getValue(request, "houseId");
		json.put("id", id);
		json.put("isPass", isPass);
		json.put("houseId", houseId);
		json.put("operId", this.getUser(request).getId());
		return this.spHouserAgeement(request, financial, onstruction, json);
	}

	/**
	 * ca回调处理合约信息
	 * 
	 * @param request
	 * @param financial
	 * @param onstruction
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object caCallBackAgreement(final HttpServletRequest request,
			final Financial financial, final Onstruction onstruction)
	{
		final String id = req.getValue(request, "agreementId"); // 合约id
		final String encryptedkey = req.getValue(request, "key");
		String signed = req.getValue(request, "signed");
		String encryptcontent = req.getValue(request, "content");
		boolean verify = false;
		try
		{
			verify = RSAUtil.verify(MD5Utils.sign(encryptcontent + encryptedkey)
					.toLowerCase(), Apis.notaryPublicKey, signed);
			if (verify)
			{
				// 更新流水号
				String decodeKey = RSAUtil.decryptByPrivateKey(encryptedkey,
						Apis.clientPrivateKey);
				logger.debug("解密后的AES秘钥 = " + decodeKey);
				String decryptResult = AES_CBCUtils.decrypt(decodeKey,
						request.getParameter("content"));
				logger.debug(decryptResult);
				JSONObject resMap = JSONObject.fromObject(decryptResult);
				final String notaryid = StringHelper.get(resMap, "notaryid");
				logger.debug(notaryid);
				if ("".equals(notaryid))
				{
					Map<String, String> exceptionMap = new HashMap<>();
					exceptionMap.put("function", "在线签约");
					exceptionMap.put("describe", "CA认证获取流水号失败");
					SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
					return "-1";
				}
				// 更新合约状态为 12
				return db.doInTransaction(new Batch<Object>()
				{

					/**
					 * @return
					 * @throws Exception
					 * @date 2017年1月18日
					 */
					@Override
					public Object execute() throws Exception
					{
						// TODO Auto-generated method stub
						//
						String sql = getSql("basehouse.agreement.updateAgreement")
								.replace("####", " id = ? ");
						this.update(sql, new Object[] { id, id });
						// 验证合约是否待公证
						sql = getSql("basehouse.agreement.checkAgreementInfo").replace(
								"####", " and type = 1 and a.`isdelete` = 1 ");
						if (this.queryForInt(sql, new Object[] { id, "11" }) == 0)
						{
							return -2;
						}
						// 更新合约状态为12 公证待审批
						sql = getSql("basehouse.agreement.updateAgreement").replace(
								"####", " notaryid = ?, `status` = 12 ");
						this.update(sql, new Object[] { notaryid, id });
						return 1;
					}
				});
			}
			else
			{
				Map<String, String> exceptionMap = new HashMap<>();
				exceptionMap.put("function", "在线签约");
				exceptionMap.put("describe", "收房签约握手失败");
				SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
				return "-1";
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Map<String, String> exceptionMap = new HashMap<>();
			exceptionMap.put("function", "在线签约");
			exceptionMap.put("describe", "收房签约握手失败");
			SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
		}
		return 1;
	}

	/**
	 * 将合约置为无效
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Map spHouserAgeement(final HttpServletRequest request,
			final Financial financial, final Onstruction onstruction,
			final JSONObject json)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				String id = StringHelper.get(json, "id"); // 合约id
				String isPass = StringHelper.get(json, "isPass"); // 通过 3 合同无效 不通过 2 已经生效
				String houseId = StringHelper.get(json, "houseId");
				String operId = StringHelper.get(json, "operId");
				logger.debug(isPass + ":" + houseId);
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否在已生效状态
				sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####",
						" and type =1 ");
				if (this.queryForInt(sql, new Object[] { id, 4 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"3".equals(isPass) ? "将合约置为无效" : "合同还是有效",
						new ArrayList<String>(), operId);
				// 合约失效
				sql = getSql("basehouse.agreement.updateAgreement").replace("####",
						" status = ? "+("3".equals(isPass)?" ,checkouttime = now() ":""));
				this.update(sql, new Object[] { isPass, id });
				if ("3".equals(isPass))
				{
					this.insertTableLog(request, "yc_house_tab", " and id ='" + houseId
							+ "'", "合同失效更新房源信息", new ArrayList<String>(), operId);
					// 更新房源信息
					sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
							" house_status = ?, update_time = now() ");
					this.update(sql, new Object[] { 10, houseId });
					// 刘飞 雷杨
					// 雷杨撤销财务
					Map<String, String> params = new HashMap<>();
					params.put("houseid", houseId);
					int res = onstruction.deleteOn(params, this);
					if (res != 1)
					{
						this.rollBack();
						return res;
					}
					// 刘飞
					params.clear();
					params.put("agreement_id", id);
					res = financial.repealHouse(params, this);
					if (res != 1)
					{
						this.rollBack();
						return -12;
					}
				}
				return 1;
			}
		}));
	}

	/**
	 * 合约到期结束到期
	 * 
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object overHouseAgreement(final HttpServletRequest request)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String houseId = req.getValue(request, "houseId"); // 房源id
				String agreementId = req.getValue(request, "id"); // 合约id
				String oper = getUser(request).getId();
				this.insertTableLog(request, "yc_agreement_tab", " and id ='"
						+ agreementId + "'", "租赁合约到期,结束合同", new ArrayList<String>(), oper);
				this.insertTableLog(request, "yc_house_tab",
						" and id ='" + houseId + "'", "租赁合约到期,修改房源信心",
						new ArrayList<String>(), oper);
				// 更新合约未失效
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " status = 3");
				this.update(sql, new Object[] { agreementId });
				// 更新房源失效
				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						" house_status = 10");
				this.update(sql, new Object[] { houseId });
				return 1;
			}
		}));
	}

	/**
	 * 合约信息
	 * 
	 * @param response
	 * @param request
	 */
	public void loadAgreementList(HttpServletResponse response, HttpServletRequest request)
	{
		String type = req.getValue(request, "agreementType"); // 1 收房合约 2 出租合约
		String status = req.getValue(request, "status"); // 合约状态
		String keyWord = req.getAjaxValue(request, "keyWord"); // 关键字
		String accountid = req.getValue(request, "accountid"); // 合约管家
		String createtime = req.getAjaxValue(request, "createtime");
		String arear = req.getValue(request, "arear"); // 区域
		String isSelf = req.getValue(request, "isSelf"); // 自己
		String trading = req.getValue(request, "trading"); // 商圈
		// String type = req.getValue(request, "type"); // 房屋类型
		List<String> param = new ArrayList<String>();
		String sql = "";
		if ("1".equals(type))
		{
			sql = getSql("basehouse.agreement.newAgreementList");
		}
		else
		{
			sql = getSql("basehouse.agreement.newRankAgreement");
		}
		if (!"".equals(createtime))
		{
			sql += getSql("basehouse.agreement.condition_time");
			param.add(createtime.split("~")[0]);
			param.add(createtime.split("~")[1]);
		}
		// if (!"".equals(type))
		// {
		// sql += getSql("basehouse.agreement.condition_type");
		// param.add(type);
		// }
		if ("1".equals(isSelf))
		{
			if ("1".equals(type)) // 收房
			{
				// 自己
				sql += getSql("basehouse.agreement.condition_user_id");
				param.add(this.getUser(request).getId());
			}
			else
			{
				// 自己
				sql += getSql("basehouse.agreement.condition_user2_id");
				param.add(this.getUser(request).getId());
			}
		}
		if (!"".equals(status))
		{
			sql += getSql("basehouse.agreement.condition_status");
			param.add(status);
		}
		if (!"".equals(accountid))
		{
			sql += getSql("basehouse.agreement.condition_account");
			param.add(accountid);
		}
		if (!"".equals(arear))
		{
			sql += getSql("basehouse.agreement.condition_arear");
			param.add(arear);
		}
		if (!"".equals(trading))
		{
			sql += getSql("basehouse.agreement.trading");
			param.add(trading);
		}
		if (!"".equals(keyWord))
		{
			sql += getSql("basehouse.agreement.condition_keyWord");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
		}
		// logger.debug(str.getSql(sql, param.toArray()));
		// logger.debug(getSql("basehouse.agreement.orderBy"));
		this.getPageList(request, response, sql, param.toArray(),
				"basehouse.agreement.orderBy");
	}

	public Map<String, String> downloadAgreement(JSONObject json)
	{
		String notaryid = StringHelper.get(json, "notaryid");
		String flag = StringHelper.get(json, "flag"); // 0 下载 其他预览
		logger.debug(notaryid);
		Map<String, String> contentMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("result", "-1");
		resultMap.put("msg", "非法请求");
		if ("".equals(notaryid))
		{
			return resultMap;
		}
		try
		{
			String aesKey = RandomStringUtils.random(16, true, true);
			contentMap.put("notaryid", notaryid);
			String encryptContent = AES_CBCUtils.encrypt(
					aesKey,
					com.alibaba.fastjson.JSONObject.toJSONString(contentMap).getBytes(
							"utf-8"));
			String encryptedKey = RSAUtil
					.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
					Apis.clientPrivateKey);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
			logger.debug(Apis.downloadRankAgreement);
			logger.debug(flag);
			resultMap.put("url", "0".equals(flag) ? Apis.downloadRankAgreement
					: Apis.previewRankAgreement);
			resultMap.put("apiid", Apis.APIID);
			resultMap.put("content", encryptContent);
			resultMap.put("key", encryptedKey);
			resultMap.put("signed", signed);
			logger.debug("resultMap:" + resultMap);
		}
		catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 出租合约ca认证
	 * 
	 * @param agreementId
	 * @param userId
	 * @param operId
	 * @param houseId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> downloadAgreement(HttpServletRequest request)
	{
		String notaryid = req.getValue(request, "notaryid"); // 合约id
		JSONObject json = new JSONObject();
		json.put("notaryid", notaryid);
		json.put("flag", "0");
		return this.downloadAgreement(json);
	}
}
