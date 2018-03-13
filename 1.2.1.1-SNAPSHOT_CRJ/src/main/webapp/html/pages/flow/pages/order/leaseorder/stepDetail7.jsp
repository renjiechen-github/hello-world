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
			<div class="form-group ">
				</div>
				<div id="accordion4">
					<a > <font
						style="color: green">财务收入明细</font>
					</a>
					<!-- 订单基本信息-->
					<div>
							<div id="receive"></div>
					</div>

               <a ><font style="color: green">财务支出明细</font>
					</a>
					<!-- 订单基本信息-->
						<div >
							<div id="paylist"></div>
						</div>
				</div>


				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>备注：</p>
						</div>
						<div class="weui_cell_ft2">
							<textarea rows="6" id="flowremark" maxlength="256"></textarea>
						</div>
					</div>
				</div>
				<div class="form-group "></div>
			<div class="form-group col-sm-12" id="changeDiv4" style="text-align: center; margin: 20px">
					<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">审批通过</button>
			</div>
		</div>
		</section>
		</div>
		<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="/client/js/common.client.js"></script>
		<script type="text/javascript" src="/html/pages/flow/js/order/miLeaseorder/stepDetail7.js"></script>
		<script type="text/javascript" >
	$(function() {
    $( "#accordion4" ).accordion({
      collapsible: true
    });
    $("#ui-accordion-accordion4-panel-0").css('height', 'auto').height();
    
    $("#ui-accordion-accordion4-panel-1").css('height', 'auto').height();
  });
		</script>
		
	</c:if>
   	 <c:if test="${isMobile eq '0'}">
   	 	 <input type="hidden" id="childtype" value="${flowDetaiMap.orderList[0].childtype}">
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
				<label class="col-sm-2  control-label"></label>
				<div class="col-sm-8">
				</div>
					<div class="col-sm-2"></div>
			</div>
				<div class="panel-group " id="accordion">
					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="accordion-toggle" data-toggle="collapse"
									data-parent="#accordion" href="#collapseOne6"> <font
									style="color: green">财务收入信息查看：</font>
								</a>
							</h4>
						</div>
						<!-- 订单基本信息-->
						<div id="collapseOne6" class="panel-collapse collapse in">
							<div class="panel-body">

								<div class="adv-table">
									<table id="receive"
										class="display tablehover table table-bordered dataTable"
										cellspacing="0" width="100%">
										<thead>
											<tr>
												<th></th>
												<th></th>
												<th>名称</th>
												<th>类型</th>
												<th>金额</th>
												<th>开始时间</th>
												<th>结束时间</th>
												<th>状态</th>
												<th></th>
											</tr>
										</thead>
									</table>
								</div>
							</div>
							<a class="accordion-toggle" data-toggle="collapse"
								data-parent="#accordion" href="#collapseOne6">收起</a>
						</div>
					</div>

					<div class="panel">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a class="accordion-toggle" data-toggle="collapse"
									data-parent="#accordion" href="#collapseOne7"> <font
									style="color: red">财务支出信息查看：</font>
								</a>
							</h4>
						</div>
						<!-- 订单基本信息-->
						<div id="collapseOne7" class="panel-collapse collapse ">
							<div class="panel-body">
								<div class="adv-table">
									<table id="paytable"
										class="display tablehover table table-bordered dataTable"
										cellspacing="0" width="100%">
										<thead>
											<tr>
												<th></th>
												<th></th>
												<th>名称</th>
												<th>类型</th>
												<th>金额</th>
												<th>开始时间</th>
												<th>结束时间</th>
												<th>状态</th>
												<th></th>
											</tr>
										</thead>
									</table>
								</div>
								<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapseOne7">收起</a>
							</div>
						</div>
					</div>
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
					<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">审批通过</button>
				</div>
		</div>
	</div>
	</section>
   	<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/stepDetail7.js" ></script>
   	</c:if>
  </body>
</html>
