package com.ycdc.nbms.payDiscount.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycdc.nbms.payDiscount.service.PayDiscountService;

import pccom.web.action.BaseController;

/**
 * 营销管理-代缴费优惠控制层
 * @author chenrj
 * @日期 20180109
 */
@Controller
@RequestMapping("/payDiscount")
public class PayDiscountAction extends BaseController{

	@Autowired
	private PayDiscountService pService;
	
	/**
	 * 查询代缴费优惠列表
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryPayCountList.do")
	@ResponseBody
	public void queryPayCountList(HttpServletRequest request, HttpServletResponse response) {
		Object result = pService.queryPayCountList(request,response);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 修改代缴费优惠信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/modifyPayDiscount.do")
	@ResponseBody
	public void modifyPayDiscount(HttpServletRequest request, HttpServletResponse response) {
		Object result = pService.modifyPayDiscount(request,response);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 删除代缴费优惠信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delPayDiscount.do")
	@ResponseBody
	public void delPayDiscount(HttpServletRequest request, HttpServletResponse response) {
		Object result = pService.delPayDiscount(request,response);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 删除代缴费优惠信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addPayDiscount.do")
	@ResponseBody
	public void addPayDiscount(HttpServletRequest request, HttpServletResponse response) {
		Object result = pService.addPayDiscount(request,response);
		this.writeJsonData(result, response);
	}
}
