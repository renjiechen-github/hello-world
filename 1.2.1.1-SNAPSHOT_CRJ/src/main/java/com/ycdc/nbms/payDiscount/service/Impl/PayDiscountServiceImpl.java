package com.ycdc.nbms.payDiscount.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.payDiscount.dao.PayDiscountMapper;
import com.ycdc.nbms.payDiscount.pojo.PayDiscount;
import com.ycdc.nbms.payDiscount.service.PayDiscountService;

import net.sf.json.JSONArray;

@Service("PayDiscountService")
public class PayDiscountServiceImpl extends BaseService implements PayDiscountService{

	@Autowired
	private PayDiscountMapper mapper;

	@Override
	public Object queryPayCountList(HttpServletRequest request, HttpServletResponse response) {
		logger.info(" queryPayCountList method init ");
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String, Object> codeMap = new HashMap<>();
		String iDisplayLength = req.getAjaxValue(request, "iDisplayLength");//每页显示的条数
		String iDisplayStart = req.getAjaxValue(request, "iDisplayStart");//从第几行开始
		String code = req.getAjaxValue(request, "code");//合约编码
		String userName = req.getAjaxValue(request, "userName");//用户名
		String phone = req.getAjaxValue(request, "phone");//手机号码
		logger.debug("queryPayCountList method params code "+code+"userName"+userName+"phone"+phone);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String aId = "";
			if (!"".equals(code)&&null!=code) {
				codeMap.put("code", code);
				aId = mapper.queryIdByCode(codeMap);
			}
			map.put("aId", aId);
			map.put("userName", userName);//查询条件-精确查询
			map.put("phone", phone);//查询条件-精确查询
			map.put("start", iDisplayStart);
			map.put("pageSize", iDisplayLength);
			logger.debug("queryPayCountList method params aId"+aId+" start "+iDisplayStart+" pageSize "+iDisplayLength);
			List<PayDiscount> result = mapper.queryPayCountList(map);
			resultMap.put("data", JSONArray.fromObject(result));
			resultMap.put("iDisplayLength", iDisplayLength);
			resultMap.put("iDisplayStart", iDisplayStart);
			resultMap.put("sEcho", req.getAjaxValue(request, "sEcho"));
			
			Map<String, Object> totalMap = new HashMap<>();
			totalMap.put("aId", aId);
			totalMap.put("userName", userName);//查询条件-精确查询
			totalMap.put("phone", phone);//查询条件-精确查询
			List<PayDiscount> totalResult = mapper.queryPayCountList(totalMap);//查询总记录数
			resultMap.put("iTotalRecords", totalResult.size());
			resultMap.put("iTotalDisplayRecords", totalResult.size());
			logger.debug("queryPayCountList method iTotalRecords "+totalResult.size()+" iTotalDisplayRecords "+totalResult.size());
			resultMap.put("state", "1");
			return resultMap;
		} catch (Exception e) {
			e.getMessage();
			logger.error("queryPayCountList error "+e);
			resultMap = new HashMap<String, Object>();
			resultMap.put("state", "1");
			return resultMap;
		}
	}

	@Override
	@Transactional
	public Object modifyPayDiscount(HttpServletRequest request, HttpServletResponse response) {
		logger.info(" modifyPayDiscount method init ");
		Map<String, Object> resultMap = new HashMap<String, Object>();
		int result = 0;
		String phone = req.getAjaxValue(request, "phone");
//		String code = req.getAjaxValue(request, "code");
		String cost = req.getAjaxValue(request, "cost");
		String num = req.getAjaxValue(request, "num");
		String aId = req.getAjaxValue(request, "aId");//关联外键-合约表id
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("phone", phone);
		map.put("cost", cost);
		map.put("num", num);
		map.put("aId", aId);
		logger.debug("modifyPayDiscount method params phone "+phone+" cost "+cost+" num "+num+" aId "+aId);
		try {
			result = mapper.modifyPayDiscount(map);
			logger.debug(" modifyPayDiscount method end"+result);
			resultMap.put("status",result);
			return resultMap;
		} catch (Exception e) {
			logger.error(" modifyPayDiscount method error "+e);
			e.getMessage();
			resultMap = new HashMap<String, Object>();
			resultMap.put("status", 0);
			return resultMap;
		}
	}

	@Override
	@Transactional
	public Object delPayDiscount(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(" delPayDiscount method init ");
		int result = 0;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String aId = req.getAjaxValue(request, "aId");
		String phone = req.getAjaxValue(request, "phone");
		logger.debug(" delPayDiscount params aId "+aId+" phone "+phone);
		try {
			
		Map<String, Object> map = new HashMap<String, Object>();
		if (null==aId||"".equals(aId)) {//由合约编号为查询到对应合约id
			logger.debug(" delPayDiscount no code error ");
			resultMap.put("status", result);
			return resultMap;
		}
		map.put("aId", aId);
		map.put("phone", phone);
		result = mapper.deletePayDiscount(map);//根据手机号码和合约id删除优惠记录
		logger.debug(" delPayDiscount result "+result);
		resultMap.put("status", result);
		return resultMap;
		} catch (Exception e) {
			logger.error(" delPayDiscount method error "+e);
			resultMap.put("status", result);
			return resultMap;
		}
	}

	@Override
	@Transactional
	public Object addPayDiscount(HttpServletRequest request, HttpServletResponse response) {
		logger.debug(" addPayDiscount method init ");
		String phone = req.getAjaxValue(request, "phone");
		String code = req.getAjaxValue(request, "code");
		String cost = req.getAjaxValue(request, "cost");
		String num = req.getAjaxValue(request, "num");
		logger.debug(" addPayDiscount method params phone "+phone+" code "+code+" cost "+cost+" num "+num);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> resultMap  = new HashMap<String, Object>(); 
		int result = 0;
		try {
			
			map.put("code", code);
			String aId = mapper.queryIdByCode(map);//合约id
			logger.debug(" addPayDiscount aId "+aId);
			if (null==aId||"".equals(aId)) {
				logger.debug(" addPayDiscount no such code ");
				result = -2;
				resultMap.put("status", result);
				return resultMap;
			}
			map = new HashMap<String, Object>();
			map.put("phone", phone);
			map.put("aId", aId);
			List<PayDiscount> list = mapper.queryPayCountList(map);
			if (list.size()>0) {
				logger.debug(" addPayDiscount same payDiscount ");
				result = -1;
				resultMap.put("status", result);
				return resultMap;
			}
			map = new HashMap<String, Object>();
			map.put("phone", phone);
			map.put("aId", aId);
			map.put("cost", cost);
			map.put("num", num);
			result = mapper.addPayDiscount(map);
			logger.debug(" addPayDiscount method result "+result);
			resultMap.put("status", result);
			return resultMap;
		} catch (Exception e) {
			logger.error(" addPayDiscount method error "+e);
			resultMap.put("status", result);
			return resultMap;
		}
	}

}
