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
	<style type="text/css">
		.form-horizontal .form-group{
			margin-left: 0px !important;
		}
	</style>
  </head>
  <body>
   <c:if test="${isMobile eq '1'}">
   <input type="hidden" id="type" value="${flowDetaiMap.orderList[0].order_type}">
		<section class="panel">
		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">处理信息</h3>
			</div>
			<div id="changeDiv">
				<div class="form-group ">
					<label class="col-sm-2  control-label">备注：</label>
					<div class="col-sm-10">
						<textarea rows="6" id="flowremark" maxlength="256"
							class="form-control"></textarea>
					</div>
				</div>
				<div class="weui_cells" id="pichide1">
				<div class="upload">
	        		<div class="weui_cell file" id="selectImage3" onclick="stepDetail4.selectImages('fileUrl5');return false;" >
	        			<a href="">选择文件上传</a> 
	        		</div>
	        		<ul class="ipost-list ui-sortable js_fileList" id="fileUrl5"></ul> 
	        		<input type="hidden" value=""  id="hideurl3"  /> 	        	
	        		<input type="hidden" value="10" name="picSize3" id="picSize3" /> 
	        		<input type="hidden" value="" name="picPath3" id="picPath3"  /> 	        		  
	    		</div>
	    		</div>
	    		
			</div>
			<div id="changeDiv2">
				<form class="form-horizontal" id="form2">
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>订单编码</p>
							</div>
							<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_code}</div>
						</div>
					</div>
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>订单名称</p>
							</div>
							<div class="weui_cell_ft">
								<input type="text" class="form-control" dataType="Require"
									msg="订单名称不能为空" maxlength='40' id="oname"
									value="${flowDetaiMap.orderList[0].order_name}">
							</div>
						</div>
					</div>
					<div class="weui_cells" id="childhide1">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>维修类型</p>
							</div>
							<input type="hidden" value="${flowDetaiMap.orderList[0].childtype}" id="childtype2"/>
							<select class="weui_cell_ft" id="childtype3"><option value="">请选择..</option></select>
						</div>
					</div>
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>联系方式</p>
							</div>
							<div class="weui_cell_ft">
								<span style="border: 0">
									${flowDetaiMap.orderList[0].user_mobile}</span>
							</div>
						</div>
					</div>
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>用户名称</p>
							</div>
							<div class="weui_cell_ft">
								<span style="border: 0">
									${flowDetaiMap.orderList[0].user_name}</span>
							</div>
						</div>
					</div>

					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>费用</p>
							</div>
							<div class="weui_cell_ft">
								<c:if test="${flowDetaiMap.orderList[0].order_cost>0}">
									<input type="text" name="ocost" id="ocost" maxlength="7" value="${flowDetaiMap.orderList[0].order_cost}" class="form-control" dataType="double" readonly />
								</c:if>
								<c:if test="${flowDetaiMap.orderList[0].order_cost<=0}">
									<input type="text" name="ocost" id="ocost" maxlength="7"
										value="${flowDetaiMap.orderList[0].order_cost}"
										class="form-control" dataType="double" />
								</c:if>
							</div>
						</div>
					</div>
					
					<div class="weui_cells">
						<div class="weui_cell">
							<div class="weui_cell_bd weui_cell_primary">
								<p>预约时间</p>
							</div>
							<div class="weui_cell_ft">
							<input type="hidden"  id="servicetime1" value="${flowDetaiMap.orderList[0].service_time}">
							<input id="servicetime" dataType="Require" msg="请选择生效时间！" readonly="readonly" name="servicetime" class="inputChange2 form-control" type="text" />
							</div>
						</div>
					</div>
					<div class="form-group ">
						<label class="col-sm-2  control-label">订单说明</label>
						<div class="col-sm-10">
							<textarea rows="6" id="odesc" maxlength="256"
								class="form-control">${flowDetaiMap.orderList[0].order_desc}</textarea>
						</div>
					</div>
				<div class="weui_cells" id="pichides">
				<div class="upload">
	        		<div class="weui_cell file" id="selectImage2" onclick="stepDetail4.selectImages('fileUrl3');return false;" >
	        			<a href="">选择文件上传</a> 
	        		</div>
	        		<ul class="ipost-list ui-sortable js_fileList" id="fileUrl3"></ul> 
	        		<input type="hidden" value="${flowDetaiMap.orderList[0].picurls}"  id="hideurl"  /> 	        	
	        		<input type="hidden" value="10" name="picSize" id="picSize" /> 
	        		<input type="hidden" value="" name="picPath" id="picPath"  /> 	        		  
	    		</div>
				</div>
				</form>
			</div>
			<div class="form-group col-sm-12">&nbsp;</div>
			<div class="form-group col-sm-12" id="changeDiv3" style="text-align: center;">
				<button class="btn btn-info" id="edit" type="button">编辑</button>
				<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">完成</button>
			</div>
			<div class="form-group col-sm-12" id="changeDiv4" style="text-align: center;">
				<button class="btn btn-info" id="back" type="button">返回</button>
				<button class="btn btn-info" onclick="stepDetail4.save(6);"	type="button">保存</button>
			</div>
		</div>
		</section>
		<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="/client/js/common.client.js"></script>
		<script type="text/javascript" src="/client/js/common.js"></script>
		<script type="text/javascript" src="/client/js/mobile_scroll_date/mobiscroll_date.js"></script>
        <script type="text/javascript" src="/client/js/mobile_scroll_date/mobiscroll.js"></script>
		<script type="text/javascript" src="/html/pages/flow/js/order/miWorkorder/stepDetail3.js"></script>
	</c:if>
   	 <c:if test="${isMobile eq '0'}">
   	 <input type="hidden" id="type" value="${flowDetaiMap.orderList[0].order_type}">
   	 <input type="hidden" id="agree" value="${flowDetaiMap.orderList[0].correspondent}">
   	 
   	 <section class="panel">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">处理信息</h3>
		</div>
		<div class="panel-body">
			<div id="changeDiv">
			
				<div class="form-group" id="pic1hide">
					<label class="col-sm-2  control-label">上传图片：</label>
					<div class="col-sm-8">
						<div class="dropzone" id="upurls"></div>
						<div class="row"></div>
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
				 	</div>
				<div id="changeDiv2" >
			<form class="form-horizontal" id="form2">
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">订单编码:</label>
						<div class="col-sm-8 " >
							<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_code} </span>
							
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group has-feedback" id="onameDiv">
						<label class="col-sm-2 control-label"><b style="color: red">*</b>订单名称:</label>
						<div class="col-sm-6"  >
							<input type="text" class="form-control"   dataType="Require" msg="订单名称不能为空"  maxlength='40' id="oname" value="${flowDetaiMap.orderList[0].order_name}">
						</div>
						<div class="col-sm-4" style="color:red; font-size:13px;">*必填，建议保留系统生成名称</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">联系方式:</label>
						<div class="col-sm-2 " >
						<span  class=" form-control" style="border: 0"> ${flowDetaiMap.orderList[0].user_mobile}</span>
						</div>
						<label class="col-sm-2 control-label">用户名称:</label>
						<div class="col-sm-2" >
						<span  class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].user_name}</span>
						</div>
						<div class="col-sm-4"></div>
					</div>
					<div class="form-group" id="childhide">
				    <label class="col-sm-2  control-label"><b style="color: red">*</b>报修类型：</label>
				      <div class="col-sm-8">
					<select dataId="${flowDetaiMap.orderList[0].childtype}" class="form-control" id="childtype"><option value="">请选择...</option>
					</select>
					  </div>
					  <div class="col-sm-2">
					  </div>
				   </div>
					<div class="form-group" >
						<label class="col-sm-2 control-label"><b style="color: red">*</b>费用:</label>
						<div class="col-sm-2 " >
						<c:if test="${flowDetaiMap.orderList[0].order_cost>0}">
						<input type="text" name="ocost" id="ocost" maxlength="7" value="${flowDetaiMap.orderList[0].order_cost}" class="form-control" dataType="double"  readonly/>
					 	</c:if>
					    <c:if test="${flowDetaiMap.orderList[0].order_cost<=0}">
					    <input type="text" name="ocost" id="ocost" maxlength="7" value="${flowDetaiMap.orderList[0].order_cost}" class="form-control" dataType="double" />
					    </c:if>
						</div>
						<label class="col-sm-2 control-label"><b style="color: red">*</b>预约时间:</label>
						<div class="col-sm-4 " >
						<input type="text" dataType="Require" msg="预约时间名称不能为空" name="servicetime" readonly="readonly" id="servicetime" class="form-control" placeholder="2013-01-02 01:00 " value=" ${flowDetaiMap.orderList[0].service_time}" />
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group" id="pichides">
						<label class="col-sm-2  control-label">上传图片：</label>
						<div class="col-sm-8">
							<div class="dropzone" dataId="${flowDetaiMap.orderList[0].picurls}" id="upurl"></div>
							<div class="row"></div>
						</div>
						<div class="col-sm-2"></div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">订单说明:</label>
						<div class="col-sm-8 ">
							<textarea class="form-control" id="odesc"  maxlength="256"  rows="6" >${flowDetaiMap.orderList[0].order_desc}</textarea>
						</div>
						<div class="col-sm-2"></div>
					</div>
				</form>
		</div>
		       <div class="form-group col-sm-12" >&nbsp;</div>
	            <div class="form-group col-sm-12" id="changeDiv3" style="text-align: center;">
					<button class="btn btn-info" id="edit" type="button">编辑</button>
					<button class="btn btn-info" onclick="stepDetail4.save(2);" type="button">完成</button>
				</div>
				 <div class="form-group col-sm-12" id="changeDiv4" style="text-align: center;">
					<button class="btn btn-info" id="back" type="button">返回</button>
					<button class="btn btn-info" onclick="stepDetail4.save(6);" type="button">保存</button>
				</div>
		</div>
	</div>
	</section>
   	<script type="text/javascript" src="/html/pages/flow/js/order/workorder/stepDetail3.js" ></script>
   	</c:if>
  </body>
</html>
