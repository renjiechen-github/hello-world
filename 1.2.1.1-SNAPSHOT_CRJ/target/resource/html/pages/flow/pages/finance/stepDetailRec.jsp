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
		                <th>*收款方行别代码<br />（01-本行 02-国内他行）</th>
		                <th>*收款方客户账号</th>
		                <th>*收款方账户名称</th>
		                <th>收款方开户行名称</th>
		                <th>收款方联行号</th>
		                <th>客户方流水号</th>
		                <th>*金额</th>
		                <th>*用途</th>
		                <th>备注</th>
		                <c:if test="${empty exportToExcel}">
		                	<th>是否已核对</th>
		                </c:if>
		            </tr>
		        </thead>
		        <tbody>
		        	<c:forEach items="${flowDetaiMap.payablelist }" var="map2" >
		        		<tr>
		        			<td>${fn:containsIgnoreCase(map2.cardbank,'建行')?'01':'02'}</td>
		        			<td>${map2.bankcard}</td>	
		        			<td>${map2.cardbank}</td>
		        			<td>${map2.cardowen}</td>
		        			<td></td>
		        			<td></td>
		        			<td>${map2.cost }</td>
		        			<td>${map2.name }</td>
		        			<td></td>
		        			<c:if test="${empty exportToExcel}">
			        			<td>
			        				<c:choose>
			        					<c:when test="${edit=='1'}">
			        						<c:choose>
			        							<c:when test="${cfg_step_id=='3'}">
			        								<input type="checkbox" class="checkbox form-control" id="payaler_${map2.id}"  >
			        							</c:when>
			        							<c:otherwise>
			        								<c:choose>
					        							<c:when test="${map2.is_hd == '1'}">
					        								已核对
					        							</c:when>
					        							<c:otherwise>
					        								未核对
					        							</c:otherwise>
					        						</c:choose>
			        							</c:otherwise>
			        						</c:choose>
			        					</c:when>
			        					<c:otherwise>
			        						<c:choose>
			        							<c:when test="${map2.is_hd == '1'}">
			        								已核对
			        							</c:when>
			        							<c:otherwise>
			        								未核对
			        							</c:otherwise>
			        						</c:choose>
			        					</c:otherwise>
			        				</c:choose>
			        			</td>
		        			</c:if>
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
									<label class="col-sm-2  control-label">备注：</label>
									<div class="col-sm-10">
										<textarea rows="6" id="flowremark" class="form-control"></textarea>
									</div>
								</div>
		                       	<div class="form-group col-sm-12" style="text-align: center;" >
									<button class="btn btn-info" onclick="stepDetailRec.save(2);" type="button">审批通过</button>&nbsp;&nbsp;&nbsp;
									<button class="btn btn-info" onclick="stepDetailRec.save(3);" type="button">审批不通过</button>
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
								<button class="btn btn-info" onclick="stepDetailRec.save(2,${cfg_step_id});" type="button">&nbsp;&nbsp;核对提交&nbsp;&nbsp;</button>&nbsp;&nbsp;&nbsp;
							</div>
                       </div>
	                </div>
		    	</c:when>
		    </c:choose>
	    </c:if>
    </section>
   	<script type="text/javascript" src="/html/flow/js/finance/stepDetailRec.js" ></script>
  </body>
</html>
