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
	<link rel="stylesheet" type="text/css" href="/client/css/xcConfirm/xcConfirm.min.css" />
	<link rel="stylesheet" type="text/css" 	href="/client/css/normalize.min.css" />
		<link rel="stylesheet" type="text/css" href="/client/css/uploadImage/uploadImage.min.css"/>
<style type="text/css">
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
		<section class="panel">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">处理信息</h3>
			</div>
			<form class="form-horizontal" id="form4">
				<div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary2">
						<p>支付宝账号:</p>
					</div>
					<div class="weui_cell_ft2">
						<input  type="text" maxlength='20'style="border: 0;"  id="alipay3">
					</div>
				</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>银行卡号:</p>
						</div>
						<div class="weui_cell_ft2">
						<input type="text"  id="bankcard3" style="border: 0;"  maxlength='19'>
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>开户银行:</p>
						</div>
						<div class="weui_cell_ft2">
						<input  type="text" maxlength='20' style="border: 0;"  id="bankname3">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>水表起始度数:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写水表起始度数"  id="startwaterdegree3" style="border: 0;" dataType="Money" type="text"  maxlength="8">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>水表结束度数:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写水表结束度数" id="endwaterdegree3" style="border: 0;" dataType="Money" type="text"  maxlength="8">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>燃气起始度数:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写燃气起始度数" id="startgasdegree3" dataType="Money" type="text"  maxlength="8">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>燃气结束度数:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写燃气结束度数" id="endgasdegree3" dataType="Money" type="text"  maxlength="8">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>电表起始度数:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写电表起始度数" id="startelectricdegree3" dataType="Money" type="text"  maxlength="8">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>电表结束度数:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写电表结束度数" id="endelectricdegree3" dataType="Money" type="text"  maxlength="8">
						</div>
					</div>
				</div>
				<div class="weui_cells">
			    <div class="upload">
        		<div class="weui_cell file" id="selectImage2" onclick="stepDetail4.selectImages('degreepic3');return false;" >
        			<a href=""><b style="color: red ;" >*</b>度数附件:</a> 
        		</div>
        		<ul class="ipost-list ui-sortable js_fileList" id="degreepic3"></ul> 
        		<input type="hidden" value="${flowDetaiMap.orderList[0].picurls}"  id="hideurl4"  /> 	        	
        		<input type="hidden" value="5" name="picSize" id="picSize" /> 
        		<input type="hidden" value="" name="picPath" id="picPath"  /> 	        		  
    		    </div>
			    </div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>退房原因:</p>
						</div>
						<div class="weui_cell_ft2">
						<select  id="reasons3" style="border: 0;"  dataType="Require" msg="请选择退房原因"  ></select>
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>参加优惠活动说明:</p>
						</div>
						<div class="weui_cell_ft2">
						<input id="favourable3" rows="6"  maxlength="60" style="border: 0;" placeholder="请注明优惠金额" >
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>钥匙回收:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写钥匙回收数量" id="key3" dataType="Integer" type="text"  maxlength="2">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>门卡回收:</p>
						</div>
						<div class="weui_cell_ft2">
						<input msg="请填写门卡回收数量"  id="doorcard3" dataType="Integer" type="text"  maxlength="2">
						</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p>其他物品回收说明:</p>
						</div>
						<div class="weui_cell_ft2">
                         <input id="otherdesc3" rows="6"  maxlength="60" style="border: 0;"  >
						</div>
					</div>
				</div>
				<div class="weui_cells">
			    <div class="upload">
        		<div class="weui_cell file" id="selectImage2" onclick="stepDetail4.selectImages('housepic3');return false;" >
        			<a href=""><b style="color: red ;" >*</b>房源图片:</a> 
        		</div>
        		<ul class="ipost-list ui-sortable js_fileList" id="housepic3"></ul> 
        		<input type="hidden" value="${flowDetaiMap.orderList[0].picurls}"  id="hideurl3"  /> 	        	
        		<input type="hidden" value="5" name="picSize" id="picSize" /> 
        		<input type="hidden" value="" name="picPath" id="picPath"  /> 	        		  
    		    </div>
			    </div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary2">
							<p><b style="color: red ;" >*</b>验房说明:</p>
						</div>
						<div class="weui_cell_ft2">
						<textarea id="checkdesc3" rows="6"  maxlength="128" style="border:0px"  dataType="Require" msg="请填写验房说明" ></textarea>
						</div>
					</div>
				</div>
			 <div class="form-group "></div>
		    <div class="form-group col-sm-12" id="changeDiv4" style="text-align: center; margin: 20px">
			<button class="btn btn-info" onclick="stepDetail4.save(2);"	type="button">保存</button>
		</div>
			</form>
		</div>
		</section>
		<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="/client/js/common.client.js"></script>
		<script type="text/javascript" src="/html/adminX/js/jquery-ui-1.10.3.js"></script>
		<script type="text/javascript" src="/client/js/common.js"></script>
		<script type="text/javascript" src="/html/pages/flow/js/order/miLeaseorder/stepDetail3.js"></script>
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
	         <form class="form-horizontal" id="form4">
	         <div class="form-group"></div>
					<div class="form-group">
						<label class="col-sm-2 control-label">支付宝账号:</label>
						<div class="col-sm-3">
							<input  type="text" maxlength='20' class=" form-control" id="alipay3">
						</div>
						<label class="col-sm-2 control-label">银行卡号:</label>
						<div class="col-sm-3">
							<input type="text" class=" form-control" id="bankcard3" maxlength='19'>
						</div>
						<div class="col-sm-2"><b style="color: red ;border: 0px" class="form-control" >*支付宝或银行卡任选其一</b></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">开户银行:</label>
						<div class="col-sm-8 ">
						<input  type="text" maxlength='20' class="form-control" id="bankname3">
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>水表起始度数:</label>
						<div class="col-sm-3">
						<input msg="请填写水表起始度数" class="form-control" id="startwaterdegree3" dataType="Money2" type="text"  maxlength="8">
						</div>
						<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>水表结束度数:</label>
						<div class="col-sm-3">
						<input msg="请填写水表结束度数" class="form-control" id="endwaterdegree3" dataType="Money2" type="text"  maxlength="8">
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>燃气起始度数:</label>
						<div class="col-sm-3">
						<input msg="请填写燃气起始度数" class="form-control" id="startgasdegree3" dataType="Money2" type="text"  maxlength="8">
						</div>
						<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>燃气结束度数:</label>
						<div class="col-sm-3">
						<input msg="请填写燃气结束度数" class="form-control" id="endgasdegree3" dataType="Money2" type="text"  maxlength="8">
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>电表起始度数:</label>
						<div class="col-sm-3">
						<input msg="请填写电表起始度数" class="form-control" id="startelectricdegree3" dataType="Money2" type="text"  maxlength="8">
						</div>
						<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>电表结束度数:</label>
						<div class="col-sm-3">
						<input msg="请填写电表结束度数" class="form-control" id="endelectricdegree3" dataType="Money2" type="text"  maxlength="8">
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group" id="pichide">
						<label class="col-sm-2  control-label"><b style="color: red ;" >*</b>度数附件：</label>
						<div class="col-sm-8">
							<div class="dropzone" id="degreepic3"></div>
							<div class="row"></div>
						</div>
						<div class="col-sm-2"></div>
					</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label text-right"><b style="color: red ;" >*</b>退房原因:</label>
								<div class="col-sm-8 ">
								<select  class="form-control" id="reasons3"  dataType="Require" msg="请选择退房原因"  ></select>
								</div>
								<div class="col-sm-2"></div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">参加优惠活动说明:</label>
								<div class="col-sm-8 ">
								<input  class="form-control" id="favourable3" maxlength="60">
								</div>
								<div class="col-sm-2"><b style="color: red ;border: 0px" class="form-control" >若有优惠活动，注明优惠金额</b></div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>钥匙回收:</label>
								<div class="col-sm-3 ">
								<input msg="请填写钥匙回收数量" class="form-control" id="key3" dataType="Integer" type="text"  maxlength="2">
								</div>
								<label class="col-sm-2 control-label"><b style="color: red ;" >*</b>门卡:</label>
								<div class="col-sm-3">
								<input msg="请填写门卡回收数量" class="form-control" id="doorcard3" dataType="Integer" type="text"  maxlength="2">
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label text-right">其他物品回收说明:</label>
								<div class="col-sm-8 ">
								<input id="otherdesc3" rows="6"  maxlength="60"  class="form-control">
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group" id="pichide">
								<label class="col-sm-2  control-label"><b style="color: red ;" >*</b>房源图片：</label>
								<div class="col-sm-8">
									<div class="dropzone" id="housepic3"></div>
									<div class="row"></div>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label text-right"><b style="color: red ;" >*</b>验房说明:</label>
								<div class="col-sm-8 ">
								<textarea id="checkdesc3" rows="6"  maxlength="128"  dataType="Require" msg="请填写验房说明"  class="form-control"></textarea>
								</div>
								<div class="col-sm-2"></div>
							</div>
						</form>
				<div class="form-group col-sm-12" >&nbsp;</div>
	            <div class="form-group col-sm-12" id="changeDiv3" style="text-align: center;">
					<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">保存</button>
				</div>
		</div>
	</div>
	</section>
   	<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/stepDetail3.js" ></script>
   	</c:if>
  </body>
</html>
