package com.room1000.costImport.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.room1000.costImport.service.CostImportService;

import pccom.web.action.BaseController;

/**
 * 服务中心-费用导入控制层
 * @author chenrj
 * @data 20180122
 */
@Controller
@RequestMapping("/costImport")
public class CostImportAction extends BaseController{
	@Autowired
	private CostImportService service;
	
	/**
	 * 查询费用导入信息
	 * @author chenrj
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryCostImport.do")
	@ResponseBody
	public void queryCostImport(HttpServletRequest request, HttpServletResponse response) {
		Object result = service.queryCostImportHandle(request,response);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 校验Excel格式及异常数据
	 * @author chenrj
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/checkExcelInfo.do")
	@ResponseBody
	public void checkExcelInfo(HttpServletRequest request, HttpServletResponse response,@RequestParam(value="file", required=false) MultipartFile file) {
		Object result = service.checkExcelInfo(request,response,file);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 导入费用信息
	 * @author chenrj
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/insertCostImport.do", method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public void insertCostImport(HttpServletRequest request, HttpServletResponse response){
		Object result = service.importCostInfo(request,response);
		this.writeJsonData(result, response);
	}
	
	/**
	 * 删除临时表中的对应handleId的数据
	 * @author chenrj
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/delTempCostImport.do")
	@ResponseBody
	public void delTempCostImport(HttpServletRequest request, HttpServletResponse response){
		service.delTempCostImport(request,response);
	}
	/**
	 * 下载导入结果
	 * @author chenrj
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/downLoadCostImportResult.do")
	@ResponseBody
	public void downLoadCostImportResult(HttpServletRequest request, HttpServletResponse response) {
		Object result = service.downLoadCostImportResult(request,response);
//		this.writeJsonData(result,response);
	}
}
