package com.ycdc.nbms.coupon.serv;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.common.util.DateHelper;
import pccom.common.util.HttpURLConnHelper;
import pccom.web.beans.User;

import com.ycdc.bean.CglibBean;
import com.ycdc.bean.NextVal;
import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.coupon.dao.CouponDao;
import com.ycdc.util.page.Page;

/**
 * 收支金额统计
 * 
 * @author 孙诚明
 * @since 2016年9月29日
 */
@Service("couponServ")
public class CouponServImpl extends BaseService
{
	/**
	 * 卡券上传图片接口
	 */
	private static String UPLOADIMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建卡券接口
	 */
	private static String CREATE_COUPON= "https://api.weixin.qq.com/card/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 查询Code接口
	 */
	private static String GET_COUPON= "https://api.weixin.qq.com/card/code/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 核销Code接口
	 */
	private static String CONSUME_COUPON = "https://api.weixin.qq.com/card/code/consume?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建二维码接口
	 */
	private static String CREATE_QRCODE = "https://api.weixin.qq.com/card/qrcode/create?access_token=ACCESS_TOKEN";
	
	/**
	 * 修改库存接口
	 */
	private static String MODIFYSTOCK = "https://api.weixin.qq.com/card/modifystock?access_token=ACCESS_TOKEN";
	
	/**
	 * 修改卡券信息
	 */
	private static String MODIFCOUPON = "https://api.weixin.qq.com/card/update?access_token=ACCESS_TOKEN";
	
	/**
	 * 查看卡券详情(查询某个card_id的创建信息、审核状态以及库存数量。)待审核 审核失败 通过审核 卡券被商户删除 在公众平台投放过的卡券
	 */
	private static String GET_CARD = "https://api.weixin.qq.com/card/get?access_token=ACCESS_TOKEN";
	
	/**
	 * 删除卡券操作
	 */
	private static String DELETE_COUPON = "https://api.weixin.qq.com/card/delete?access_token=TOKEN";
	
	/**
	 * 拉取统计数据
	 */
	private static String DATACUBE = "https://api.weixin.qq.com/datacube/getcardbizuininfo?access_token=ACCESS_TOKEN";
	
	@Resource
	private CouponDao dao;
	
	private String server_id = "gh_687009b000e8";

	/**
	 * 查询操作
	 * 
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@Transactional
	public void getCouponList(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code_type", req.getAjaxValue(request, "code_type"));
		map.put("card_type", req.getAjaxValue(request, "card_type"));
		map.put("wx_state", req.getAjaxValue(request, "wx_state"));
		map.put("status", req.getAjaxValue(request, "status"));
		map.put("name", req.getAjaxValue(request, "name"));
		getPageMapList(request, response,dao, "selectQueryCoupon",map);
	}
	
	public static void main(String[] args) {
		String url = GET_COUPON.replace("ACCESS_TOKEN", "F_1_baj2ldRuvpAUy_TKrNY1FjxlcPd4fDnyn76jUSMUjiyX6y8tU1Y2LebuSh1bQBlhBq7bDD_n2hRbwpDtPh5R5pldUzYSwEZynqdJceAUnpikPBAreANLPl1iOG_wNBYjAGAESP");
//		logger.debug(url);
//		logger.debug(HttpURLConnHelper.executessl(url,"POST", "{\"card_id\" : \"card_id_123\",\"code\" : \"123456789\",\"check_consume\" : true}"));
	}
	
	/**
	 * 上传图片到微信接口
	 * @param file
	 * @return
	 */
	public JSONObject uploadWxImg(File file,String requestUrl){
		String content_type = "";
		String filename = file.getName();
		String filePath = file.getPath();
//		String requestUrl = UPLOADIMG.replace("ACCESS_TOKEN", "-K68T9PO3zixolNsVgiGLl4LmwebSBO9OIXY4MCGhdUu_-89Q9TGhWgD3mFR63n9ZnhBJWQknmOtw976CbJo_1j8fW_U1bjtWx335MgYkZA7Kj3MZdsdakK0ohvP4WcRRVUfAIAYCB").replace("TYPE", "jpg");
        String result = "";
        String end = "\r\n";
        String twoHyphens = "--"; //用于拼接
        String boundary = "*****"; //用于拼接 可自定义
		URL submit = null;
        JSONObject json = null;
        DataOutputStream dos = null;
        FileInputStream fis = null;
        InputStream is = null;
        try {
            submit = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) submit.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            // 获取输出流对象，准备上传文件
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; name=\"" + file + "\";filename=\"" + filename
                    + ";filelength=\"" + filePath + ";Content-Type=\"" + content_type + end);
            dos.writeBytes(end);
            // 对文件进行传输
            fis = new FileInputStream(file);
            byte[] buffer = new byte[8192]; // 8k
            int count = 0;
            while((count = fis.read(buffer)) != -1) {
                dos.write(buffer, 0, count);
            }
            fis.close(); // 关闭文件流

            dos.writeBytes(end);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            dos.flush();

            is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            result = br.readLine();
            json = JSONObject.fromObject(result);
        }
        catch (Exception e) {
            e.printStackTrace();
            json = new JSONObject();
            json.put("errcode", "-1");
            json.put("msg", "出现异常："+e.getMessage());
        }finally{
        	if(dos != null){
        		try {
					dos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        	if(is != null){
        		try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
        return json;
	}
	
	/**
	 * 导出卡券对应的可用户明细信息
	 * 
	 * @param request
	 * @param response
	 */
	public void expCouponUser(HttpServletRequest request,
			HttpServletResponse response){
		
	}

	/**
	 * 查看详情
	 * @param request
	 * @param response
	 * @return
	 */
	public Object showAddCoupon(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}

	/**
	 * 设置无效
	 * @param request
	 * @param response
	 * @return
	 */
	public Object setInvalid(HttpServletRequest request,
			HttpServletResponse response) {
		return null;
	}
	
	/**
	 * 新增操作
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional
	public Object addCoupon(HttpServletRequest request,
			HttpServletResponse response) {
		String brand_name = req.getAjaxValue(request,"brand_name");//商户名字
		String name = req.getAjaxValue(request,"name");//卡券名称
		String logo_url_ = req.getAjaxValue(request,"logo_url_");//LOGO图标
		String service_phone = req.getAjaxValue(request,"service_phone");//客服电话
		String card_type = req.getAjaxValue(request,"card_type");//卡券类型
		String code_type = req.getAjaxValue(request,"code_type");//券面码型
		String rule_discount = req.getAjaxValue(request,"rule_discount");//折扣额度
		String reduce_cost = req.getAjaxValue(request,"reduce_cost");//减免金额
		String get_limit = req.getAjaxValue(request,"get_limit");//每人可领券的数量限制
		String can_share = req.getAjaxValue(request,"can_share");//卡券领取页面是否可分享
		String can_give_friend = req.getAjaxValue(request,"can_give_friend");//卡券是否可转赠
		String color = req.getAjaxValue(request,"color");//卡券背景颜色
		String notice = req.getAjaxValue(request,"notice");//卡券使用提醒
		String se_time = req.getAjaxValue(request,"se_time");//卡券有效期 0固定日期 1领取后
		String begin_timestamp = req.getAjaxValue(request,"begin_timestamp");//
		String fixed_begin_term = req.getAjaxValue(request,"fixed_begin_term");//领取后多少天生效
		String fixed_term = req.getAjaxValue(request, "fixed_term");//有效期日期
		String zdxf_c = req.getAjaxValue(request,"zdxf_c");//使用条件 最低消费
		String rule_least_cost = req.getAjaxValue(request,"rule_least_cost");//满多少元可用
		String syfw_c = req.getAjaxValue(request,"syfw_c");//使用条件 
		String accept_category = req.getAjaxValue(request,"accept_category");// 适用商品
		String reject_category = req.getAjaxValue(request,"reject_category");//不适用商品
		String rule_can_use_with_other_discount = req.getAjaxValue(request,"rule_can_use_with_other_discount");//优惠共享
		String description = req.getAjaxValue(request,"description");//卡券使用说明
		String rule_degree = req.getAjaxValue(request,"rule_degree");//作用类型 0只作用在合约上，整体的满减等  1作用在合约明细上，针对某一月的金额增减
		String rule_degree_month = req.getAjaxValue(request, "rule_degree_month");//生效月份
		String sku_quantity = req.getAjaxValue(request,"sku_quantity");//库存
		String deal_detail = req.getAjaxValue(request, "deal_detail");//详情
		String rule_month_cost = req.getAjaxValue(request, "rule_month_cost");//月租券对应的每月金额
		String use_all_locations = "true";//默认设置全部门店适应
		String bind_openid = "0";//是否指定用户领取
		String isnew = req.getAjaxValue(request,"isnew");//是否新租客使用
		try{
			User user = getUser(request);
			if(user == null || user.getId() == null || "".equals(user.getId())){//未登陆
				return getReturnMap("-4");
			}
			
			/**
			 * 获取微信对应的token值
			 */
			String token = queryString(dao, "getWxServerToken", new HashMap());
			//先将login图片上传到微信
			File file = new File(request.getRealPath("/")+logo_url_);
			String requestUrl = UPLOADIMG.replace("ACCESS_TOKEN", token).replace("TYPE", "jpg");
			logger.debug("requestUrl:"+requestUrl);
			JSONObject fileJson = uploadWxImg(file, requestUrl);
			logger.debug(fileJson.toString());
			String logoUrl = "";//图标地址
			if(fileJson.containsKey("url")){//上传成功了
				logoUrl = fileJson.getString("url");
			}else{
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return getReturnMap("-2");//上传失败
			}
			logger.debug("card_type:"+card_type);
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("item_id", "6".equals(card_type)?"4":card_type);
			String cardCode = queryString(dao, "getCardCode", paramsMap);
			logger.debug("cardCode:"+cardCode);
			
			/*************************************先插入到数据库中，等会再进行手动同步到微信端*start******************************************************/
//			Map<String,Object> cardTyprMap = new HashMap<String,Object>();
//			cardTyprMap.put("remark", card_type);
//			String cardType = queryString(dao, "getCardTypr", cardTyprMap);
			Map<String,Object> couponMap = new HashMap<String,Object>();
			couponMap.put("code_type", code_type);
			couponMap.put("logo_url", logoUrl);
			couponMap.put("color", color);
			couponMap.put("brand_name", brand_name);
			couponMap.put("title", name);
			couponMap.put("notice", notice);
			couponMap.put("description", description);
//			couponMap.put("location_id_list", null);
			couponMap.put("use_all_locations", use_all_locations);
			couponMap.put("sku_quantity", sku_quantity);
			couponMap.put("type", se_time);
			couponMap.put("rule_start_month", "1".equals(rule_degree)?rule_degree_month:null);
			if("".equals(begin_timestamp)){
				couponMap.put("begin_timestamp", "");
				couponMap.put("end_timestamp", "");
			}else{
				couponMap.put("begin_timestamp", begin_timestamp.split("至")[0].substring(0,10)+" 00:00:00");
				couponMap.put("end_timestamp", begin_timestamp.split("至")[1].substring(0,10)+" 23:59:59");
			}
			
			couponMap.put("fixed_begin_term", fixed_begin_term);
			couponMap.put("end_timestamp_", "");
			couponMap.put("use_custom_code", 0);
			couponMap.put("bind_openid", bind_openid);
			couponMap.put("service_phone", service_phone);
			couponMap.put("fixed_term", fixed_term);
			couponMap.put("get_limit", get_limit);
			couponMap.put("can_share", can_share);
			couponMap.put("can_give_friend", can_give_friend);
			couponMap.put("card_type", card_type);
			couponMap.put("rule_degree", rule_degree);
			couponMap.put("rule_can_use_with_other_discount", "true".equals(rule_can_use_with_other_discount)?"1":"0");
			couponMap.put("rule_least_cost", "1".equals(zdxf_c)?"".equals(rule_least_cost)?null:rule_least_cost:null);
			couponMap.put("rule_discount", rule_discount);
			couponMap.put("accept_category", "0".equals(syfw_c)?accept_category:null);
			couponMap.put("reject_category", "0".equals(syfw_c)?reject_category:null);
			couponMap.put("oper_id", user.getId());
			couponMap.put("isnew", isnew);
			couponMap.put("rule_month_cost","6".equals(card_type)?rule_month_cost:null);
			couponMap.put("reduce_cost", "".equals(reduce_cost)?null:reduce_cost);
			couponMap.put("deal_detail",deal_detail);
			
			int id = insertExc(dao, "addCoupon", "id", couponMap);
			logger.debug("id:"+id);
			if(id > 0){//插入数据库信息成功，进行同步数据到微信端
				/*************************************同步到卡券信息到微信端**start*****************************************************/
	
				//进行组装数据信息
				JSONObject json = new JSONObject();
				JSONObject cardCodeJson = new JSONObject();
				
				//base_info
				JSONObject baseInfoJson = new JSONObject();
				//库存
				JSONObject skuJson = new JSONObject();
				skuJson.put("quantity", sku_quantity);
				
				baseInfoJson.put("logo_url", logoUrl);
				baseInfoJson.put("brand_name", brand_name);
				baseInfoJson.put("code_type", code_type);
				baseInfoJson.put("title", name);
				baseInfoJson.put("color", color);
				baseInfoJson.put("notice", notice);
				baseInfoJson.put("service_phone", service_phone);
				baseInfoJson.put("description", description);
					
				//卡券有效期 0固定日期 1领取后
				JSONObject dateInfoJson = new JSONObject();
				dateInfoJson.put("type", "0".equals(se_time)?"DATE_TYPE_FIX_TIME_RANGE":"DATE_TYPE_FIX_TERM");
				if("0".equals(se_time)){//固定日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date begiDate;
					Date endDate;
					try {
						logger.debug(begin_timestamp.split("至")[0]+":00"+"-----------"+begin_timestamp.split("至")[1].substring(0,10)+" 23:59:59");
						begiDate = sdf.parse(begin_timestamp.split("至")[0].substring(0,10)+" 00:00:00");
						endDate = sdf.parse(begin_timestamp.split("至")[1].substring(0,10)+" 23:59:59");
					} catch (ParseException e) {
						e.printStackTrace();
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return getReturnMap("-3");
					}
					logger.debug(System.currentTimeMillis()+"------------"+begiDate.getTime());
					
					dateInfoJson.put("begin_timestamp", begiDate.getTime()/1000);
					dateInfoJson.put("end_timestamp", endDate.getTime()/1000);
				}else{
					dateInfoJson.put("fixed_term", fixed_term);
					dateInfoJson.put("fixed_begin_term", fixed_begin_term);
				}
				
				baseInfoJson.put("date_info",dateInfoJson);
				baseInfoJson.put("sku", skuJson);
				baseInfoJson.put("get_limit", get_limit);
				baseInfoJson.put("can_share", "0".equals(can_share)?false:true);
				baseInfoJson.put("can_give_friend","0".equals(can_give_friend)?false:true);
				baseInfoJson.put("use_all_locations", "true".equals(use_all_locations)?true:false);
				baseInfoJson.put("bind_openid", "0".equals(bind_openid)?false:true);
				
				JSONObject advancedInfoJson = new JSONObject();
				JSONObject useConditionJson = new JSONObject();
				useConditionJson.put("can_use_with_other_discount", "true".equals(rule_can_use_with_other_discount)?true:false);
				advancedInfoJson.put("use_condition", useConditionJson);
				if("4".equals(card_type)){//兑换券
					if("1".equals(zdxf_c)){//最低消费
						useConditionJson.put("least_cost", rule_least_cost);
					}
				}
				logger.debug("zdxf_c:"+zdxf_c);
				logger.debug("syfw_c:"+syfw_c);
				if("0".equals(syfw_c)){//适用商品
					useConditionJson.put("reject_category", reject_category);
					useConditionJson.put("accept_category", accept_category);
				}
				logger.debug("?????111111111??????");
				cardCodeJson.put("base_info", baseInfoJson);
				cardCodeJson.put("advanced_info", advancedInfoJson);
				
				
				if("2".equals(card_type)){//代金券
					cardCodeJson.put("reduce_cost",Float.valueOf(reduce_cost)*100);
					if("1".equals(zdxf_c)){//最低消费
						advancedInfoJson.put("least_cost", rule_least_cost);
						cardCodeJson.put("least_cost",Integer.valueOf(rule_least_cost)*100);
					}  
				}else if("3".equals(card_type)){//折扣券
					cardCodeJson.put("discount",Integer.valueOf(rule_discount));
				}else if("4".equals(card_type)){//兑换券,或者
					cardCodeJson.put("gift", deal_detail);
				}else if("5".equals(card_type)){//优惠券
					cardCodeJson.put("default_detail", deal_detail);
				}
//				else if("6".equals(card_type)){//月租券
//					cardCodeJson.put("gift", "第"+rule_degree_month+"个月用户");
//				}
				
				JSONObject cardJson = new JSONObject();
				cardJson.put("card_type", cardCode);
				cardJson.put(cardCode.toLowerCase(), cardCodeJson);
				
				json.put("card", cardJson);
				
				logger.debug("生成对应的json数据信息1："+json);
				String serverUrl = CREATE_COUPON.replace("ACCESS_TOKEN", token);
				logger.debug("serverUrl："+serverUrl);
				JSONObject result = HttpURLConnHelper.executessl(serverUrl, "POST", json);
				logger.debug("result:"+result);
				if(result.containsKey("errcode")){
					String errcode = result.getString("errcode");
					if("0".equals(errcode)){//同步微信成功
						//更新card_id到数据库中
						Map<String,Object> cardMap = new HashMap<String,Object>();
						cardMap.put("key", "card_id");
						cardMap.put("value", result.getString("card_id"));
						cardMap.put("id", id);
						int exc = updateExc(dao, "updateCoupon", cardMap);
						if(exc == 1){
							return getReturnMap(1);
						}else{
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return getReturnMap(-6);
						}
					}else{//同步失败
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return getReturnMap(-5);
					}
				}else{//同步失败
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return getReturnMap(-5);
				}
				/*************************************同步到卡券信息到微信端**end*****************************************************/
			}else{//插入数据库失败
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return getReturnMap(-3);
			}
			/*************************************先插入到数据库中，等会再进行手动同步到微信端*end******************************************************/
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 处理图片上传信息
	 * @param request
	 * @param response
	 * @return
	 */
	public Object saveLogoImg(HttpServletRequest request,
			HttpServletResponse response) {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String path = "/upload/tmp/";// 文件存儲路徑，臨時文件
		InputStream stream = null;
		OutputStream bos = null;
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {
					//logger.debug("表单参数名:" + item.getFieldName()
					//		+ "，表单参数值:" + item.getString("UTF-8"));
					String itemName = item.getFieldName();
					String itemValue = item.getString("UTF-8");
				} else {
					if (item.getName() != null && !item.getName().equals("")) {
						//logger.debug("上传文件的大小:" + item.getSize());
						//logger.debug("上传文件的类型:" + item.getContentType());
						// item.getName()返回上传文件在客户端的完整路径名称
						//logger.debug("上传文件的名称:" + item.getName());
						long size = item.getSize();
						String contentType = item.getContentType();
						String name_ = item.getName();
						logger.debug(name_);
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						String tmppath = request.getRealPath("/") + path;
						//logger.debug("tmppath:" + tmppath);
						
						stream = item.getInputStream();// 把文件读入
						bos = new FileOutputStream(tmppath + "/"
								+ name_);
						int bytesRead = 0;
						byte[] buffer = new byte[8192];
						while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
							bos.write(buffer, 0, bytesRead);// 将文件写入服务器
						}
						Map<String,Object> returnMap = getReturnMap(1);
						returnMap.put("path", path+name_);
						return returnMap;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(bos != null){
				try{
					bos.close();
				}catch (Exception e) {
					
				}
			}
			if(stream != null){
				try{
					stream.close();
				}catch (Exception e) {
				}
			}
		}
		return getReturnMap(0);
	}

	/**
	 * 同步状态信息 将库存、状态同步到数据库
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional
	public Object tbState(HttpServletRequest request,
			HttpServletResponse response) {
		try{
			String id = req.getAjaxValue(request, "id");
			String card_id = req.getAjaxValue(request, "card_id");
			
			String token = queryString(dao, "getWxServerToken", new HashMap());
			String serverUrl = GET_CARD.replace("ACCESS_TOKEN", token);
			logger.debug("serverUrl:"+serverUrl);
			JSONObject json = new JSONObject();
			json.put("card_id", card_id);
			//调用状态信息进行同步
			JSONObject resultJson = HttpURLConnHelper.executessl(serverUrl, "POST", json);
			logger.debug(resultJson.toString());
			if(resultJson.containsKey("errcode")){//访问成功
				if(resultJson.getString("errcode").equals("0")){//成功
					JSONObject cardJson = resultJson.getJSONObject("card");
					String card_type = cardJson.getString("card_type");
					JSONObject baseInfoJson = cardJson.getJSONObject(card_type.toLowerCase()).getJSONObject("base_info");
					String status = baseInfoJson.getString("status");//状态信息
					JSONObject skuJson = baseInfoJson.getJSONObject("sku");
					logger.debug(skuJson.toString());
					String total_quantity = skuJson.getString("total_quantity");//卡券全部库存的数量
					String quantity = skuJson.getString("quantity");//卡券现有库存的数量
					//进行更新对应的状态信息
					Map<String,Object> paramsMap = new HashMap<String,Object>();
					paramsMap.put("key", "wx_state");
					paramsMap.put("value", "CARD_STATUS_NOT_VERIFY".equals(status)?"1":"CARD_STATUS_VERIFY_FAIL".equals(status)?"2":"CARD_STATUS_VERIFY_OK".equals(status)?"3":"CARD_STATUS_DELETE".equals(status)?"4":"CARD_STATUS_DISPATCH".equals(status)?"5":status);
					paramsMap.put("id", id);
					updateExc(dao, "updateCoupon", paramsMap);
					Map<String,Object> paramsMap1 = new HashMap<String,Object>();
					paramsMap1.put("key", "sku_quantity");
					paramsMap1.put("value", total_quantity);
					paramsMap1.put("id", id);
					updateExc(dao, "updateCoupon", paramsMap1);
					
					Map<String,Object> paramsMap2 = new HashMap<String,Object>();
					paramsMap2.put("key", "sy_quantity");
					paramsMap2.put("value", quantity);
					paramsMap2.put("id", id);
					updateExc(dao, "updateCoupon", paramsMap2);
					
					Map<String,Object> paramsMap3 = new HashMap<String,Object>();
					paramsMap3.put("key", "lq_cnt");
					paramsMap3.put("value", Integer.valueOf(total_quantity)-Integer.valueOf(quantity));
					paramsMap3.put("id", id);
					updateExc(dao, "updateCoupon", paramsMap3);
					return getReturnMap(1);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return getReturnMap(-1);
	}

	/**
	 * 生成对应的二维码信息,并更新到数据库中
	 * @param request
	 * @param response
	 * @return
	 */
	public Object qrcode(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "id");
		String card_id = req.getAjaxValue(request, "card_id");
		
		String token = queryString(dao, "getWxServerToken", new HashMap());
		String serverUrl = CREATE_QRCODE.replace("ACCESS_TOKEN", token);
		logger.debug("serverUrl:"+serverUrl);
		JSONObject json = new JSONObject();
		json.put("action_name", "QR_CARD");
		JSONObject actionInfojson = new JSONObject();
		JSONObject cardJson = new JSONObject();
		cardJson.put("card_id", card_id);
		actionInfojson.put("card", cardJson);
		json.put("action_info", actionInfojson);
		JSONObject resultJson = HttpURLConnHelper.executessl(serverUrl, "POST", json);
		logger.debug(resultJson.toString());
		if(resultJson.containsKey("errcode")){
			if(resultJson.getString("errcode").equals("0")){
				String show_qrcode_url = resultJson.getString("show_qrcode_url");
				Map<String,Object> paramsMap = new HashMap<String,Object>();
				paramsMap.put("key", "qrcode");
				paramsMap.put("value", show_qrcode_url);
				paramsMap.put("id", id);
				int exc = update(dao, "updateCoupon", paramsMap);
				if(exc == 1){
					Map<String,Object> returnMap = getReturnMap("1");
					returnMap.put("url", resultJson.getString("show_qrcode_url"));
					return returnMap;
				}
			}
		}
		return getReturnMap("-1");
	}

	/**
	 * 删除卡券操作信息
	 * @param request
	 * @param response
	 * @return
	 */
	public Object deleteCoupon(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "id");
		String card_id = req.getAjaxValue(request, "card_id");
		String token = queryString(dao, "getWxServerToken", new HashMap());
		String serverUrl = DELETE_COUPON.replace("TOKEN", token);
		
		//获取单条信息进行核对，是否是存在card_id
		Map<String,Object> paramsMap = new HashMap<String,Object>();
		paramsMap.put("id", id);
		List<Map<String,Object>> list = queryList(dao, "selectQueryCoupon", paramsMap);
		if(list.size() != 1){
			return getReturnMap(-2);
		}
		Map<String,Object> map = list.get(0);
		card_id = str.get(map,"card_id");
		if("".equals(card_id)){//操作失败，没有对应的card_id信息
			Map<String,Object> paramsMap1 = new HashMap<String,Object>();
			paramsMap1.put("key", "state");
			paramsMap1.put("value", 0);
			paramsMap1.put("id", id);
			int exc = update(dao, "updateCoupon", paramsMap1);
			if(exc == 1){
				return getReturnMap("1");
			}
		}else{
			logger.debug("serverUrl:"+serverUrl);
			JSONObject json = new JSONObject();
			json.put("card_id", card_id);
			JSONObject resultJson = HttpURLConnHelper.executessl(serverUrl, "POST", json);
			logger.debug(resultJson.toString());
			if(resultJson.containsKey("errcode")){
				if(resultJson.getString("errcode").equals("0")){
					Map<String,Object> paramsMap1 = new HashMap<String,Object>();
					paramsMap1.put("key", "state");
					paramsMap1.put("value", 0);
					paramsMap1.put("id", id);
					int exc = update(dao, "updateCoupon", paramsMap1);
					if(exc == 1){
						return getReturnMap("1");
					}
				}
			}
		}
		
		return getReturnMap("-1");
	}

	/**
	 * 修改卡券信息
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional
	public Object modifyCoupon(HttpServletRequest request,
			HttpServletResponse response) {
		String brand_name = req.getAjaxValue(request,"brand_name");//商户名字
		String name = req.getAjaxValue(request,"name");//卡券名称
		String logo_url_ = req.getAjaxValue(request,"logo_url_");//LOGO图标
		String service_phone = req.getAjaxValue(request,"service_phone");//客服电话
		String card_type = req.getAjaxValue(request,"card_type");//卡券类型
		String code_type = req.getAjaxValue(request,"code_type");//券面码型
		String get_limit = req.getAjaxValue(request,"get_limit");//每人可领券的数量限制
		String can_share = req.getAjaxValue(request,"can_share");//卡券领取页面是否可分享
		String can_give_friend = req.getAjaxValue(request,"can_give_friend");//卡券是否可转赠
		String color = req.getAjaxValue(request,"color");//卡券背景颜色
		String notice = req.getAjaxValue(request,"notice");//卡券使用提醒
		String se_time = req.getAjaxValue(request,"se_time");//卡券有效期 0固定日期 1领取后
		String begin_timestamp = req.getAjaxValue(request,"begin_timestamp");//
		String description = req.getAjaxValue(request,"description");//卡券使用说明
		String rule_degree = req.getAjaxValue(request,"rule_degree");//作用类型 0只作用在合约上，整体的满减等  1作用在合约明细上，针对某一月的金额增减
		String rule_degree_month = req.getAjaxValue(request, "rule_degree_month");//生效月份
		String sku_quantity = req.getAjaxValue(request,"sku_quantity");//库存
		String deal_detail = req.getAjaxValue(request, "deal_detail");//详情
		String use_all_locations = "true";//默认设置全部门店适应
		String bind_openid = "0";//是否指定用户领取
		String isnew = req.getAjaxValue(request,"isnew");//是否新租客使用
		String id = req.getAjaxValue(request, "id");
		String card_id = req.getAjaxValue(request, "card_id");
		try{
			User user = getUser(request);
			if(user == null || user.getId() == null || "".equals(user.getId())){//未登陆
				return getReturnMap("-4");
			}
			
			/**
			 * 获取微信对应的token值
			 */
			String token = queryString(dao, "getWxServerToken", new HashMap());
			String logoUrl = "";//图标地址
			if(logo_url_.indexOf("http") != 0){//没有http开头说明变动
				//先将login图片上传到微信
				File file = new File(request.getRealPath("/")+logo_url_);
				String requestUrl = UPLOADIMG.replace("ACCESS_TOKEN", token).replace("TYPE", "jpg");
				JSONObject fileJson = uploadWxImg(file, requestUrl);
				logger.debug(fileJson.toString());
				if(fileJson.containsKey("url")){//上传成功了
					logoUrl = fileJson.getString("url");
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return getReturnMap("-2");//上传失败
				}
			}else{
				logoUrl = logo_url_;
			}
			
			logger.debug("card_type:"+card_type);
			Map<String,Object> paramsMap = new HashMap<String,Object>();
			paramsMap.put("item_id", "6".equals(card_type)?"4":card_type);
			String cardCode = queryString(dao, "getCardCode", paramsMap);
			logger.debug("cardCode:"+cardCode);
			
			/*************************************先插入到数据库中，等会再进行手动同步到微信端*start******************************************************/
			Map<String,Object> couponMap = new HashMap<String,Object>();
			couponMap.put("code_type", code_type);
			couponMap.put("logo_url", logoUrl);
			couponMap.put("color", color);
			couponMap.put("brand_name", brand_name);
			couponMap.put("title", name);
			couponMap.put("notice", notice);
			couponMap.put("description", description);
			couponMap.put("use_all_locations", use_all_locations);
			couponMap.put("type", se_time);
			couponMap.put("rule_start_month", "1".equals(rule_degree)?rule_degree_month:null);
			if("".equals(begin_timestamp)){
				couponMap.put("begin_timestamp", "");
				couponMap.put("end_timestamp", "");
			}else{
				couponMap.put("begin_timestamp", begin_timestamp.split("至")[0].substring(0,10)+" 00:00:00");
				couponMap.put("end_timestamp", begin_timestamp.split("至")[1].substring(0,10)+" 23:59:59");
			}
			couponMap.put("end_timestamp_", "");
			couponMap.put("use_custom_code", 0);
			couponMap.put("bind_openid", bind_openid);
			couponMap.put("service_phone", service_phone);
			couponMap.put("get_limit", Integer.valueOf(get_limit));
			couponMap.put("can_share", can_share);
			couponMap.put("can_give_friend", can_give_friend);
			couponMap.put("card_type", card_type);
			couponMap.put("rule_degree", rule_degree);
			couponMap.put("oper_id", user.getId());
			couponMap.put("isnew", isnew);
			couponMap.put("id", id);
			
			int exc = updateExc(dao, "modifyCoupon",couponMap);
			logger.debug("id:"+id);
			if(exc > 0){//更新数据库信息成功，进行同步数据到微信端
				/*************************************同步到卡券信息到微信端**start*****************************************************/
				//进行组装数据信息
				JSONObject json = new JSONObject();
				JSONObject cardCodeJson = new JSONObject();
				
				//base_info
				JSONObject baseInfoJson = new JSONObject();
				
				baseInfoJson.put("logo_url", logoUrl);
				baseInfoJson.put("code_type", code_type);
				baseInfoJson.put("title", name);
				baseInfoJson.put("color", color);
				baseInfoJson.put("notice", notice);
				baseInfoJson.put("service_phone", service_phone);
				baseInfoJson.put("description", description);
				
				//卡券有效期 0固定日期 1领取后
				JSONObject dateInfoJson = new JSONObject();
				if("0".equals(se_time)){
					dateInfoJson.put("type", "0".equals(se_time)?"DATE_TYPE_FIX_TIME_RANGE":"DATE_TYPE_FIX_TERM");
					if("0".equals(se_time)){//固定日期
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Date begiDate;
						Date endDate;
						try {
							logger.debug(begin_timestamp.split("至")[0]+":00"+"-----------"+begin_timestamp.split("至")[1].substring(0,10)+" 23:59:59");
							begiDate = sdf.parse(begin_timestamp.split("至")[0].substring(0,10)+" 00:00:00");
							endDate = sdf.parse(begin_timestamp.split("至")[1].substring(0,10)+" 23:59:59");
						} catch (ParseException e) {
							e.printStackTrace();
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							return getReturnMap("-3");
						}
						logger.debug(System.currentTimeMillis()+"------------"+begiDate.getTime());
						
						dateInfoJson.put("begin_timestamp", begiDate.getTime()/1000);
						dateInfoJson.put("end_timestamp", endDate.getTime()/1000);
					}
					baseInfoJson.put("date_info",dateInfoJson);
				}
				baseInfoJson.put("get_limit", Integer.valueOf(get_limit));
				baseInfoJson.put("can_share", "0".equals(can_share)?false:true);
				baseInfoJson.put("can_give_friend","0".equals(can_give_friend)?false:true);
				
				logger.debug("???????????");
				cardCodeJson.put("base_info", baseInfoJson);
				
				json.put("card_id", card_id);
				json.put(cardCode.toLowerCase(), cardCodeJson);
				
				logger.debug("更新生成对应的json数据信息1："+json);
				String serverUrl = MODIFCOUPON.replace("ACCESS_TOKEN", token);
				logger.debug("serverUrl："+serverUrl);
				JSONObject result = HttpURLConnHelper.executessl(serverUrl, "POST", json);
				logger.debug("result:"+result);
				if(result.containsKey("errcode")){
					String errcode = result.getString("errcode");
					if("0".equals(errcode)){//同步微信成功
						if("true".equals(result.getString("send_check"))){//需要进行审核
							Map<String,Object> paramsMap1 = new HashMap<String,Object>();
							paramsMap1.put("key", "wx_state");
							paramsMap1.put("value", 1);
							paramsMap1.put("id", id);
							exc = update(dao, "updateCoupon", paramsMap1);
							if(exc != 1){//更改审核状态失败
								return getReturnMap(-9);
							}
						}
						return getReturnMap(1);
					}else{//同步失败
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return getReturnMap(-5);
					}
				}else{//同步失败
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return getReturnMap(-5);
				}
				/*************************************同步到卡券信息到微信端**end*****************************************************/
			}else{//插入数据库失败
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return getReturnMap(-3);
			}
			/*************************************先插入到数据库中，等会再进行手动同步到微信端*end******************************************************/
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	/**
	 * 更新库存信息
	 * @param request
	 * @param response
	 * @return
	 */
	public Map<String, Object> modifySuk(HttpServletRequest request,
			HttpServletResponse response) {
		String id = req.getAjaxValue(request, "id");
		String card_id = req.getAjaxValue(request, "card_id");
		String sku_quantity = req.getAjaxValue(request,"sku_quantity");//库存
		String yc_kc = req.getAjaxValue(request,"ys_kc");//原始库存
		
		if(!sku_quantity.equals(yc_kc)){
			//修改数据库
			Map<String,Object> paramsMap1 = new HashMap<String,Object>();
			paramsMap1.put("key", "sku_quantity");
			paramsMap1.put("value", sku_quantity);
			paramsMap1.put("id", id);
			int exc = update(dao, "updateCoupon", paramsMap1);
			if(exc == 1){
				String token = queryString(dao, "getWxServerToken", new HashMap());
				String serverUrl = MODIFYSTOCK.replace("ACCESS_TOKEN", token);
				JSONObject json = new JSONObject();
				json.put("card_id", card_id);
				if(Integer.valueOf(sku_quantity) - Integer.valueOf(yc_kc) > 0){
					json.put("increase_stock_value", Integer.valueOf(sku_quantity) - Integer.valueOf(yc_kc));
				}else{
					json.put("reduce_stock_value", Integer.valueOf(yc_kc) - Integer.valueOf(sku_quantity));
				}
				JSONObject result = HttpURLConnHelper.executessl(serverUrl, "POST", json);
				logger.debug("更新库存信息："+result);
				if(result.containsKey("errcode")){
					if("0".equals(result.getString("errcode"))){
						return getReturnMap(1);
					}
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return getReturnMap(-11);
				}else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return getReturnMap(-12);
				}
			}else{
				return getReturnMap(-13);
			}
		}else{
			return getReturnMap(1);
		}
		
	}

	/**
	 * 获取统计卡券信息
	 * @param request
	 * @param response
	 */
	public void datacube(HttpServletRequest request,
			HttpServletResponse response) {
		String time = req.getAjaxValue(request, "time");
		String card_id = req.getAjaxValue(request, "card_id");
		String id = req.getAjaxValue(request, "id");
		String statrTime = "";
		String endTime = "";
		if(!"".equals(time)){
			statrTime = time.split("至")[0];
			endTime = time.split("至")[1];
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("statrTime", endTime);
		map.put("endTime", statrTime);
		map.put("card_id", card_id);
		getPageMapList(request, response,dao, "getDatacube",map);
	}

	/**
	 * 同步卡券统计信息
	 * @param request
	 * @param response
	 */
	@Transactional
	public Object synDataCube(HttpServletRequest request,
			HttpServletResponse response) {
		String time = req.getAjaxValue(request, "time");
		String card_id = req.getAjaxValue(request, "card_id");
		String id = req.getAjaxValue(request, "id");
		String statrTime = time.split("至")[0];
		String endTime = time.split("至")[1];
		String cond_source = "1";
		if("pJPVytyjGGq4rPgT-d-5OsM-oH2w".equals(card_id)){
			cond_source = "0";
		}
		
		/**
		 * 获取微信对应的token值
		 */
		String token = queryString(dao, "getWxServerToken", new HashMap());
		String requestUrl = DATACUBE.replace("ACCESS_TOKEN", token);
		JSONObject json = new JSONObject();
		json.put("begin_date", statrTime);
		json.put("end_date", endTime);
		json.put("cond_source", cond_source);
		JSONObject result = HttpURLConnHelper.executessl(requestUrl, "POST", json);
		logger.debug("更新库存信息："+result);
		if(!result.containsKey("errcode")){
//			if("0".equals(result.getString("errcode"))){
//				return getReturnMap(1);
//			}
			JSONArray jsonList = result.getJSONArray("list");
			for(int i=0;i<jsonList.size();i++){
				String ref_date = jsonList.getJSONObject(i).getString("ref_date");
				String view_cnt = jsonList.getJSONObject(i).getString("view_cnt");
				String view_user = jsonList.getJSONObject(i).getString("view_user");
				String receive_cnt = jsonList.getJSONObject(i).getString("receive_cnt");
				String receive_user = jsonList.getJSONObject(i).getString("receive_user");
				String verify_cnt = jsonList.getJSONObject(i).getString("verify_cnt");
				String verify_user = jsonList.getJSONObject(i).getString("verify_user");
				String given_cnt = jsonList.getJSONObject(i).getString("given_cnt");
				String given_user = jsonList.getJSONObject(i).getString("given_user");
				String expire_cnt = jsonList.getJSONObject(i).getString("expire_cnt");
				String expire_user = jsonList.getJSONObject(i).getString("expire_user");
				String view_friends_cnt = jsonList.getJSONObject(i).getString("view_friends_cnt");
				String view_friends_user = jsonList.getJSONObject(i).getString("view_friends_user");
				String receive_friends_cnt = jsonList.getJSONObject(i).getString("receive_friends_cnt");
				String receive_friends_user = jsonList.getJSONObject(i).getString("receive_friends_user");
				String verify_friends_cnt = jsonList.getJSONObject(i).getString("verify_friends_cnt");
				String verify_friends_user = jsonList.getJSONObject(i).getString("verify_friends_user");
				
				Map<String,Object> paramsMap = new HashMap<String,Object>();
				paramsMap.put("ref_date",ref_date);
				paramsMap.put("card_id",card_id);
				paramsMap.put("view_cnt",view_cnt);
				paramsMap.put("view_user",view_user);
				paramsMap.put("receive_cnt",receive_cnt);
				paramsMap.put("receive_user",receive_user);
				paramsMap.put("verify_cnt",verify_cnt);
				paramsMap.put("verify_user",verify_user);
				paramsMap.put("given_cnt",given_cnt);
				paramsMap.put("given_user",given_user);
				paramsMap.put("expire_cnt",expire_cnt);
				paramsMap.put("expire_user",expire_user);
				
				paramsMap.put("view_friends_cnt",view_friends_cnt);
				paramsMap.put("view_friends_user",view_friends_user);
				paramsMap.put("receive_friends_cnt",receive_friends_cnt);
				paramsMap.put("receive_friends_user",receive_friends_user);
				paramsMap.put("verify_friends_cnt",verify_friends_cnt);
				paramsMap.put("verify_friends_user",verify_friends_user);
				
				paramsMap.put("card_id",card_id);
				paramsMap.put("ref_date",ref_date);
				int exc = update(dao, "synDataCube", paramsMap);
				if(exc != 1 ){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return getReturnMap(-1);
				}
			}
			return getReturnMap(1);
		}else{
			return getReturnMap(-2);
		}
	}
	
	
	
}
