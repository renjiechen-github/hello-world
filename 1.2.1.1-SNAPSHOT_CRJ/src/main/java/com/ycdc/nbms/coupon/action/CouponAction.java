package com.ycdc.nbms.coupon.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.common.util.Batch;
import pccom.common.util.BatchSql;
import pccom.common.util.StringHelper;
import pccom.web.interfaces.Coupon;

import com.ycdc.core.base.BaseController;

/**
 * 卡券模块信息
 * @author admin
 *
 */
@Controller
public class CouponAction extends BaseController {

	@Resource
	private com.ycdc.nbms.coupon.serv.CouponServImpl couponServImpl;
	
	@Autowired
	private Coupon coupon;
	
	/**
	 * 获取卡券信息
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping(value = "coupon/showCoupon.do")
	public void showCoupon(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("????????????");
		couponServImpl.getCouponList(request, response);
	}
	
	/**
	 * 上传图片信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/saveLogoImg.do")
	public void saveLogoImg(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.saveLogoImg(request,response), response);
	}
	
	/**
	 * 同步审核状态
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/tbState.do")
	public void tbState(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.tbState(request,response), response);
	}
	
	/**
	 * 获取二维码信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/qrcode.do")
	public void qrcode(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.qrcode(request,response), response);
	}
	
	/**
	 * 导出卡券对应人员明细
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/expCouponUser.do")
	public void expCouponUser(HttpServletRequest request, HttpServletResponse response){
		couponServImpl.getCouponList(request, response);
	}
	
	/**
	 * 新增操作
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/addCoupon.do")
	public void addCoupon(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.addCoupon(request,response), response);
	}
	
	/**
	 * 修改操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/modifyCoupon.do")
	public void modifyCoupon(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> returnMap = (Map<String,Object>)couponServImpl.modifyCoupon(request,response);
		if("1".equals(StringHelper.get(returnMap, "state"))){//同步成功
			//进行同步库存信息
			returnMap = (Map<String,Object>)couponServImpl.modifySuk(request,response);
		}
		this.writeJsonData(returnMap, response);
	}
	
	/**
	 * 删除卡券操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/deleteCoupon.do")
	public void deleteCoupon(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.deleteCoupon(request,response), response);
	}
	
	/**
	 * 设置无效操作
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/setInvalid.do")
	public void setInvalid(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.setInvalid(request,response), response);
	}
	
	/**
	 * 查看详情
	 * @param request
	 * @param response
	 */
	public void showAddCoupon(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.showAddCoupon(request,response), response);
	}
	
	/**
	 * 获取统计数据
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/datacube.do")
	public void datacube(HttpServletRequest request, HttpServletResponse response){
		couponServImpl.datacube(request, response);
	}
	
	/**
	 * 同步卡券统计信息
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "coupon/synDataCube.do")
	public void synDataCube(HttpServletRequest request, HttpServletResponse response){
		this.writeJsonData(couponServImpl.synDataCube(request, response), response);
	}
}
