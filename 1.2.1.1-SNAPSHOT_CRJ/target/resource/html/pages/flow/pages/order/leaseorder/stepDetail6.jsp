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
	<link rel="stylesheet" type="text/css" href="/client/css/mission/create_task_detail.min.css" />
<style type="text/css">
.daterangepicker.dropdown-menu {
	max-width: none;
	z-index: 19891015;
}
.form-horizontal .form-group {
	margin-left: 0px !important;
}
</style>
</head>
  <body>
   <c:if test="${isMobile eq '1'}">
   <input type="hidden" id="type" value="${flowDetaiMap.orderList[0].order_type}">
   	 <input type="hidden" id="agree" value="${flowDetaiMap.orderList[0].correspondent}">
   	 <input type="hidden" id="orderId" value="${flowDetaiMap.orderList[0].id}">
   	 <input type="hidden" id="task_id" name="task_id"value="${map.taskdetail.task_id}">
		<div class="body">
		<section class="panel">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">处理信息</h3>
			</div>
			<div class="form-group ">&nbsp;</div>
				<div id="changeDiv">
            <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary2">
					</div>
					<div class="weui_cell_ft2">
					</div>
				</div>
			</div>
			<div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary2"><span id="costtype1">应退金额：</span>
					</div>
					<div class="weui_cell_ft2">
					<span class=" form-control" style="border: 0" id="cost" dataId="${flowDetaiMap.orderList[0].order_cost}">${flowDetaiMap.orderList[0].order_cost}￥</span>
					</div>
				</div>
			</div>
				<div class="form-group ">&nbsp;</div>
					<div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary2">
						<p>备注：</p>
					</div>
					<div class="weui_cell_ft2">
					<textarea rows="6" id="flowremark"  maxlength="256"  ></textarea>
					</div>
				</div>
			</div>
				</div>
			<div class="form-group col-sm-12" >&nbsp;</div>
			<div class="form-group col-sm-12" id="changeDiv3" style="text-align: center;">
				<button class="btn btn-info" onclick="stepDetail4.save(3);" type="button">审批拒绝</button>
				<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">审批通过</button>
			</div>
		</div>
		</section>
		</div>
		<script type="text/javascript" src="/html/pages/flow/js/order/miLeaseorder/stepDetail6.js"></script>
	</c:if>
   	 <c:if test="${isMobile eq '0'}">
   	 <input type="hidden" id="type" value="${flowDetaiMap.orderList[0].order_type}">
   	 <input type="hidden" id="agree" value="${flowDetaiMap.orderList[0].correspondent}">
   	 <input type="hidden" id="orderId" value="${flowDetaiMap.orderList[0].id}">
   	 <section class="panel">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">处理信息</h3>
		</div>
		<div class="panel-body">
				
				<div class="form-group ">
					<label class="col-sm-2  control-label"><span id="costtype">应退金额：</span></label>
					<div class="col-sm-8">
						<span class=" form-control" style="border: 0" id="cost" dataId="${flowDetaiMap.orderList[0].order_cost}">${flowDetaiMap.orderList[0].order_cost}￥</span>
					</div>
					<div class="col-sm-2"></div>
				</div>
				<div class="form-group ">
					<label class="col-sm-2  control-label">备注：</label>
					<div class="col-sm-8">
						<textarea rows="6" id="flowremark"  maxlength="256"  class="form-control"></textarea>
					</div>
					<div class="col-sm-2"></div>
				</div>
			<div class="form-group col-sm-12" >&nbsp;</div>
			<div class="form-group col-sm-12" id="changeDiv3" style="text-align: center;">
				<button class="btn btn-info" onclick="stepDetail4.save(3);" type="button">审批拒绝</button>
				<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">审批通过</button>
			</div>
		</div>
	</div>
	</section>
   	<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/stepDetail6.js" ></script>
   	</c:if>
  </body>
</html>
