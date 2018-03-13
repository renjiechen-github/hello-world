package com.room1000.costImport.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * 费用导入服务层
 * @author chenrj
 * @data 20180122
 */
public interface CostImportService {

	Object queryCostImportHandle(HttpServletRequest request, HttpServletResponse response);
	
	Object checkExcelInfo(HttpServletRequest request, HttpServletResponse response,MultipartFile file);
	
	Object importCostInfo(HttpServletRequest request, HttpServletResponse response);
	
	void delTempCostImport(HttpServletRequest request, HttpServletResponse response);
	
	Object downLoadCostImportResult(HttpServletRequest request, HttpServletResponse response);
}
