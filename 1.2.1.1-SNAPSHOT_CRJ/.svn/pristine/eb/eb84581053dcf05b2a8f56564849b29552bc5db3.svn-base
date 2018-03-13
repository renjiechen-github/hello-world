<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>任务详细信息查看</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		.form-horizontal .form-group{
			margin-left: 0px !important;
		}
	</style>
  </head>
  <body>
  	<%
	  	String exportToExcel = request.getParameter("exportToExcel");
	    if (exportToExcel != null&& exportToExcel.toString().equalsIgnoreCase("YES")) {
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "inline; filename="
	                + "excel.xls");
	    }
	%>
	<section class="panel">
		<div id="tableclassfin" class="adv-table">
	        <table class=" tablehover table table-bordered " cellspacing="0" width="100%">
		        <thead>
		            <tr>	
                    <th>订单编码</th>
					<th>类型</th>
					<th>名称</th>
					<th>预约时间</th>
					<th>用户手机</th>
					<th>用户名称</th>
					<th>费用</th>
					<th>状态</th>
		            </tr>
		        </thead>
		        <tbody>
		        	<c:forEach items="${flowDetaiMap.orderList }" var="map2" >
		        		<tr>
		        			<td>${map2.order_code}</td>
		        			<td>${map2.typename}</td>	
		        			<td>${map2.order_name}</td>
		        			<td>${map2.service_time}</td>
		        			<td>${map2.user_mobile}</td>
		        			<td>${map2.user_name}</td>
		        			<td>${map2.order_cost}</td>
		        			<td>${map2.statusname}</td>
		        		</tr>
		        	</c:forEach>
		        </tbody>
		    </table>
		    <c:if test="${empty exportToExcel}">
			    <a href="/flow/showTaskDetail.do?task_id=${task_id}&task_cfg_id=${task_cfg_id}&step_id=${step_id}&cfg_step_id=${cfg_step_id}&exportToExcel=YES" class="btn btn-default" type="button">导出</a>
			    <br />&nbsp;
		    </c:if>
	    </div>
	    <c:if test="${empty exportToExcel}">
		    <c:choose>
		    	<c:when test="${cfg_step_id == '2'}">
		    		<!-- 第二步处理信息 -->
		    		<div class="panel panel-primary">
	                       <div class="panel-heading">
	                           <h3 class="panel-title">处理信息</h3>
	                       </div>
	                       <div class="panel-body">
						<div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">受理人员：</label>
							<div class="col-sm-4">
								<select class="form-control"  id="account"></select>
							</div>
							<div class="col-sm-6"></div>
						</div>
						<div class="form-group col-sm-12">
									<label class="col-sm-2  control-label">备注：</label>
									<div class="col-sm-10">
										<textarea rows="6" id="flowremark" class="form-control"></textarea>
									</div>
								</div>
		                       	<div class="form-group col-sm-12" style="text-align: center;" >
									<button class="btn btn-info" onclick="stepDetail.save(2);" type="button">审批通过</button>&nbsp;&nbsp;&nbsp;
									<button class="btn btn-info" onclick="stepDetail.save(3);" type="button">审批不通过</button>
								</div>
	                       </div>
	                </div>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '3'}">
		    		<div class="panel panel-primary">
	                       <div class="panel-heading">
	                           <h3 class="panel-title">处理信息</h3>
	                       </div>
	                       <div class="panel-body">
		                       	<div class="form-group col-sm-12">
									<label class="col-sm-2  control-label">备注：</label>
									<div class="col-sm-10">
										<textarea rows="6" id="flowremark" class="form-control"></textarea>
									</div>
								</div>
		                       	<div class="form-group col-sm-12" style="text-align: center;" >
									<button class="btn btn-info" onclick="stepDetail.save(2,${cfg_step_id});" type="button">&nbsp;&nbsp;核对提交&nbsp;&nbsp;</button>&nbsp;&nbsp;&nbsp;
								</div>
	                       </div>
	                </div>
		    	</c:when>
		    </c:choose>
	    </c:if>
	    
    </section>
   	<script type="text/javascript" src="/html/flow/js/order/stepDetail.js" ></script>
  </body>
</html>
