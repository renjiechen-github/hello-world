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
<link rel="stylesheet" type="text/css" href="/client/css/preImage/lightbox.min.css"/>
<link rel="stylesheet" type="text/css" href="/client/css/uploadImage/uploadImage.min.css"/>
<link rel="stylesheet" type="text/css" href="/html/adminX/css/jquery-ui-1.10.3.min.css"/>
<link rel="stylesheet" type="text/css" href="/client/css/mission/create_task_detail.min.css" />
	<%--<link rel="stylesheet" type="text/css" href="/html/adminX/css/bootstrap.min.css"/>--%>
<link rel="stylesheet" type="text/css" href="/html/adminX/css/taskbootstrap.css"/>


<%--

  margin-bottom: 20px;
  background-color: #fff;
  border: 1px solid transparent;
 
  
}--%>
<style type="text/css">

.form-horizontal .form-group {
	margin-left: 0px !important;
}
.body {
	font-family: Arial, Helvetica, sans-serif;
}

.table {
	font-size: 1em;
}

.ui-draggable, .ui-droppable {
	background-position: top;
}
.ui-accordion .ui-accordion-content {
  padding: 1em 0em;
  border-top: 0;
  overflow: auto;
}
.ui-accordion .ui-accordion-icons {
  padding-left: 1em;
}
</style>
</head>
<body>
	<c:if test="${isMobile eq '1'}">
	<input type="hidden" id="task_check_id"  value="${map.taskdetail.task_id}">
	<input type="hidden" id="order_id1" value="${flowDetaiMap.orderList[0].id}">
<div id="accordion">
	
  <a> 订单信息查看</a>
			<div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>退租房源</p>
						</div>
						<div class="weui_cell_ft2" id="rankName"></div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>订单编码</p>
						</div>
						<div class="weui_cell_ft2" id="task_code"
							style="word-break: break-all;">${flowDetaiMap.orderList[0].order_code}</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>订单名称</p>
						</div>
						<div class="weui_cell_ft2" id="task_code">${flowDetaiMap.orderList[0].order_name}</div>
					</div>
				</div>
				<div class="weui_cells" id="childhide">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>类型</p>
						</div>
						<input type="hidden"
							dataId="${flowDetaiMap.orderList[0].childtype}" id="childtype1">
						<div class="weui_cell_ft2" id="childtype">${flowDetaiMap.orderList[0].childtype}</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>联系方式</p>
						</div>
						<div class="weui_cell_ft2" id="task_code">
							<a
								onclick="njyc.phone.callSomeOne('${flowDetaiMap.orderList[0].user_mobile}')">${flowDetaiMap.orderList[0].user_mobile}</a>
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>用户名称</p>
						</div>
						<div class="weui_cell_ft2" id="task_code">${flowDetaiMap.orderList[0].user_name}</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>费用</p>
						</div>
						<div class="weui_cell_ft2" id="task_code">${flowDetaiMap.orderList[0].order_cost}</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>预约时间</p>
						</div>
						<div class="weui_cell_ft2" id="task_code">${flowDetaiMap.orderList[0].service_time}</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>订单说明</p>
						</div>
						<div class="weui_cell_ft2" id="task_code">${flowDetaiMap.orderList[0].order_desc}</div>
					</div>
				</div>
			</div>
<a>验房信息</a>
   <div >
  <div id="hidedic">
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>支付宝账号:</p>
								</div>
								<div class="weui_cell_ft2 right">
										<span style="border:0px" id="alipay" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>银行卡号:</p>
								</div>
								<div class="weui_cell_ft3 ">
										<span style="border:0px" id="bankcard" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>开户银行:</p>
								</div>
								<div class="weui_cell_ft3">
								<span style="border:0px" id="bankname" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										水表起始度数:
									</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="startwaterdegree" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										水表结束度数:
									</p>
								</div>
								<div class="weui_cell_ft2">
										<span style="border:0px" id="endwaterdegree" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										燃气起始度数:
									</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="startgasdegree" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										燃气结束度数:
									</p>
								</div>
								<div class="weui_cell_ft2">
										<span style="border:0px" id="endgasdegree" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										电表起始度数:
									</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="startelectricdegree" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										电表结束度数:
									</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="endelectricdegree" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="upload">
								<div class="weui_cell file" >
									度数附件:
								</div>
								<ul class="ipost-list ui-sortable js_fileList" id="degreepic"></ul>
								<input type="hidden"
									value="${flowDetaiMap.orderList[0].picurls}" id="hideurl1" />
								<input type="hidden" value="5" name="picSize" id="picSize" />
								<input type="hidden" value="" name="picPath" id="picPath" />
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										退房原因:
									</p>
								</div>
								<div class="weui_cell_ft2">
									<span style="border:0px" id="reasons" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>优惠活动说明:</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="favourable" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										钥匙回收:
									</p>
								</div>
								<div class="weui_cell_ft2">
										<span style="border:0px" id="key" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										门卡回收:
									</p>
								</div>
								<div class="weui_cell_ft2">
										<span style="border:0px" id="doorcard" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>其他回收说明:</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="otherdesc" ></span>
								</div>
							</div>
						</div>
						<div class="weui_cells">
							<div class="upload">
								<div class="weui_cell file">
									房源图片:
								</div>
								<ul class="ipost-list ui-sortable js_fileList" id="housepic"></ul>
								<input type="hidden"
									value="${flowDetaiMap.orderList[0].picurls}" id="hideurl" />
								<input type="hidden" value="5" name="picSize" id="picSize" />
								<input type="hidden" value="" name="picPath" id="picPath" />
							</div>
						</div>
						<div class="weui_cells">
							<div class="weui_cell">
								<div class="weui_cell_bd weui_cell_primary2">
									<p>
										验房说明:
									</p>
								</div>
								<div class="weui_cell_ft2">
								<span style="border:0px" id="checkdesc" ></span>
								</div>
							</div>
						</div>
							</div>
								</div>
  <a>待缴费信息</a>
   <div>
    <div id="costdiv">
	</div>
	<div id="costdivcost"></div>  
	 </div>
</div>
		<input type="hidden" id="agree" value="${flowDetaiMap.orderList[0].correspondent}">
		<input type="hidden" id="type" value="${flowDetaiMap.orderList[0].order_type}">
		<input type="hidden" id="task_check_id"  value="${map.taskdetail.task_id}">
		<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="/html/adminX/js/jquery-ui-1.10.3.js"></script>
		<script type="text/javascript" src="/client/js/common.client.js"></script>
		<script type="text/javascript" src="/client/js/common.js"></script>
		<script type="text/javascript" src="/client/js/preImage/lightbox-2.6.js"></script>
	    <script type="text/javascript" src="/html/pages/flow/js/order/miLeaseorder/checkHouse.js"></script>
		<script type="text/javascript" src="/html/pages/flow/js/order/miLeaseorder/stepDetail.js"></script>
<script>
  $(function() {
    $( "#accordion" ).accordion({
      collapsible: true
    });
    
   $("#ui-accordion-accordion-panel-0").css('height', 'auto').height();
   $("#ui-accordion-accordion-panel-1").css('height', 'auto').height();
   $("#ui-accordion-accordion-panel-2").css('height', 'auto').height();
  });
  </script>
		<c:if test="${edit==1}">
			<c:choose>
				<c:when test="${cfg_step_id == '2'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail2.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '3'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail3.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '4'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail4.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '5'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail5.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '6'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail6.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '7'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail7.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '8'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail8.jsp"></jsp:include>
				</c:when>
			</c:choose>
		</c:if>
	</c:if>
	<c:if test="${isMobile eq '0'}">
	
	<input type="hidden" id="task_check_id" 
			value="${map.taskdetail.task_id}">
		<header class="panel-heading custom-tab">
		<ul class="nav nav-tabs" role="tablist" id="myTabs">
			<li role="presentation" class="active"><a href="#ORDER_DESC" aria-controls="ORDER_DESC" role="tab" data-toggle="tab">订单信息</a></li>
			<li role="presentation"><a href="#CHECK_HOUSE_TAB" aria-controls="CHECK_HOUSE_TAB" role="tab" data-toggle="tab">验房信息</a></li>
			<li role="presentation"><a href="#COST_DESC" aria-controls="COST_DESC" role="tab" data-toggle="tab">待缴费信息</a></li>
		</ul>
		</header>
		<div id="myTabContent" class="tab-content">
			<!-- 待支付-->
			<div class="tab-pane fade in active" id="ORDER_DESC">
				<div class="panel-body">
					<form class="form-horizontal" id="form2">

						<input type="hidden" id="agree"
							value="${flowDetaiMap.orderList[0].correspondent}">
							
							<input type="hidden" id="order_id1"
							value="${flowDetaiMap.orderList[0].id}">
							
						<div class="form-group">
							<label class="col-sm-2 control-label text-right">退租房源:</label>
							<div class="col-sm-8 ">
								<span class=" form-control" style="border: 0" id="rankName">${flowDetaiMap.orderList[0].correspondent}</span>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label text-right">订单编码:</label>
							<div class="col-sm-8 ">
								<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_code}
								</span>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group has-feedback" id="onameDiv">
							<label class="col-sm-2 control-label">订单名称:</label>
							<div class="col-sm-6">
								<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_name}</span>
							</div>
							<div class="col-sm-4" style="color:red; font-size:13px;"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">联系方式:</label>
							<div class="col-sm-2 ">
								<span class=" form-control" style="border: 0">
									${flowDetaiMap.orderList[0].user_mobile}</span>
							</div>
							<label class="col-sm-2 control-label">用户名称:</label>
							<div class="col-sm-2">
								<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].user_name}</span>
							</div>
							<div class="col-sm-4"></div>
						</div>
						<input type="hidden" id="type"
							value="${flowDetaiMap.orderList[0].order_type}">
						<div class="form-group" id="typehide">
							<label class="col-sm-2  control-label" id="chiddesc">退租类型：</label>
							<div class="col-sm-8">
								<span class=" form-control" style="border: 0"
									dataId="${flowDetaiMap.orderList[0].childtype}" id="childtype1"></span>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group" id="costhide">
							<label class="col-sm-2 control-label">费用:</label>
							<div class="col-sm-2 ">
								<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_cost}</span>
							</div>
							<label class="col-sm-2 control-label">预约时间:</label>
							<div class="col-sm-4 ">
								<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].service_time}</span>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group" id="pichide">
							<label class="col-sm-2  control-label"><b
								style="color: red"></b>上传图片：</label>
							<div class="col-sm-8">
								<div class="dropzone"
									dataId="${flowDetaiMap.orderList[0].picurls}" id="upurlp"></div>
								<div class="row"></div>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group" id="addresshide">
							<label class="col-sm-2  control-label"><b
								style="color: red"></b>地址：</label>
							<div class="col-sm-8">
								<span class=" form-control" style="border: 0" id="address"></span>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">订单说明:</label>
							<div class="col-sm-8 ">
								<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_desc}</span>
							</div>
							<div class="col-sm-2"></div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label"></label>
							<div class="col-sm-8 ">
							</div>
							<div class="col-sm-2"></div>
						</div>
						
						<div class="form-group">
							<label class="col-sm-2 control-label">&nbsp;</label>
							<div class="col-sm-8 ">
							</div>
							<div class="col-sm-2"></div>
						</div>
						
						
						
					</form>
				</div>
			</div>
			<!--验房信息-->
			<div class="tab-pane " id="CHECK_HOUSE_TAB">
				<div class="panel-body">
					<div id="hidedic">
						<form class="form-horizontal" id="form3">
							<div class="form-group">
								<label class="col-sm-2 control-label">支付宝账号:</label>
								<div class="col-sm-2 ">
									<span class=" form-control" style="border: 0" id="alipay"></span>
								</div>
								<label class="col-sm-2 control-label">银行卡号:</label>
								<div class="col-sm-2">
									<span class=" form-control" style="border: 0" id="bankcard"></span>
								</div>
								<label class="col-sm-2 control-label">开户银行:</label>
								<div class="col-sm-2 ">
									<span class=" form-control" style="border: 0" id="bankname"></span>
								</div>
							</div>
							
							
							<div class="form-group">
								<label class="col-sm-2 control-label">水表起始度数:</label>
								<div class="col-sm-2">
									<span class=" form-control" style="border: 0" id="startwaterdegree"></span>
								</div>
								<label class="col-sm-2 control-label">燃气起始度数:</label>
								<div class="col-sm-2 ">
									<span class=" form-control" style="border: 0" id="startgasdegree"></span>
								</div>
								<label class="col-sm-2 control-label">电表起始度数:</label>
								<div class="col-sm-2">
									<span class=" form-control" style="border: 0" id="startelectricdegree"></span>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">水表结束度数:</label>
								<div class="col-sm-2">
									<span class=" form-control" style="border: 0" id="endwaterdegree"></span>
								</div>
								<label class="col-sm-2 control-label">燃气结束度数:</label>
								<div class="col-sm-2 ">
									<span class=" form-control" style="border: 0" id="endgasdegree"></span>
								</div>
								<label class="col-sm-2 control-label">电表结束度数:</label>
								<div class="col-sm-2">
									<span class=" form-control" style="border: 0" id="endelectricdegree"></span>
								</div>
							</div>
							
							<div class="form-group" >
								<label class="col-sm-2  control-label">度数附件：</label>
								<div class="col-sm-8">
									<div class="dropzone" id="degreepic"></div>
									<div class="row"></div>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">退房原因:</label>
								<div class="col-sm-8 ">
									<span class=" form-control" style="border: 0" id="reasons"></span>
								</div>
								<div class="col-sm-2"></div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label">钥匙回收:</label>
								<div class="col-sm-2 ">
									<span class=" form-control" style="border: 0" id="key"></span>
								</div>
								<label class="col-sm-2 control-label">门卡:</label>
								<div class="col-sm-2">
									<span class=" form-control" style="border: 0" id="doorcard"></span>
								</div>
								<div class="col-sm-4"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">其他物品回收说明:</label>
								<div class="col-sm-8 ">
									<span class=" form-control" style="border: 0" id="otherdesc"></span>
								</div>
								<div class="col-sm-2"></div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">参加优惠活动说明:</label>
								<div class="col-sm-8 ">
									<span class=" form-control" style="border: 0" id="favourable"></span>
								</div>
								<div class="col-sm-2"></div>
							</div>
							 
								<div class="form-group" >
								<label class="col-sm-2  control-label">房源图片：</label>
								<div class="col-sm-8">
									<div class="dropzone" id="housepic"></div>
									<div class="row"></div>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">验房说明:</label>
								<div class="col-sm-8 ">
									<span class=" form-control" style="border: 0" id="checkdesc"></span>
								</div>
								<div class="col-sm-2"></div>
							</div>
								<div class="form-group">
								<label class="col-sm-2 control-label text-right"></label>
								<div class="col-sm-8 ">
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">&nbsp;</label>
								<div class="col-sm-8 ">
								</div>
								<div class="col-sm-2"></div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="tab-pane " id="COST_DESC">
				<div class="panel-body">
						<div class="adv-table">
							<table id="Cost_table"
								class="display tablehover table table-bordered dataTable"
								cellspacing="0" width="100%">
								<thead>
									<tr>
										<th></th>
										<th></th>
										<th>名称</th>
										<th>类型</th>
										<th>金额</th>
										<th>起始度数</th>
										<th>结束度数</th>
										<th>计费度数</th>
										<th></th>
									</tr>
								</thead>
							</table>
						</div>
						<div class="form-group ">
				<label class="col-sm-2  control-label"></label>
				<div class="col-sm-8">
				</div>
					<div class="col-sm-2"></div>
			</div>
	        <div class="form-group ">
				<label class="col-sm-2  control-label">待缴费金额总计：</label>
				<div class="col-sm-8">
				<span  class=" form-control" style="border: 0" id="costCounts"></span>
				</div>
					<div class="col-sm-2"></div>
			</div>
				</div>
			</div>
		</div>
		<script type="text/javascript"src="/html/pages/flow/js/order/leaseorder/stepDetail.js"></script>
		<script type="text/javascript"src="/html/pages/flow/js/order/leaseorder/checkHouse.js"></script>
		<c:if test="${edit==1}">
			<c:choose>
				<c:when test="${cfg_step_id == '2'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail2.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '3'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail3.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '4'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail4.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '5'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail5.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '6'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail6.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '7'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail7.jsp"></jsp:include>
				</c:when>
				<c:when test="${cfg_step_id == '8'}">
					<jsp:include
						page="/html/pages/flow/pages/order/leaseorder/stepDetail8.jsp"></jsp:include>
				</c:when>
			</c:choose>
		</c:if>
	</c:if>
</body>
</html>
