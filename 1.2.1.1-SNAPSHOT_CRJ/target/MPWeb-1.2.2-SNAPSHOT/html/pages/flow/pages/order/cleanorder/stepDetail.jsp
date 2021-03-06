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
    <link rel="stylesheet" type="text/css" href="/html/adminX/css/bootstrap.css"/>
	<link rel="stylesheet" type="text/css" href="/client/css/normalize.min.css"/>
    <link rel="stylesheet" type="text/css" href="/client/css/mission/create_task_detail.min.css"/>
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
  	<c:choose>
  		<c:when test="${edit==1}">
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
	        	<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_name}</div>
	        </div>
	    </div>
	    
	    	<div class="weui_cells">
  			<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>保洁类型</p>
	            </div>
	            <input type="hidden"  dataId="${flowDetaiMap.orderList[0].childtype}" id="childtype1">
	        	<div class="weui_cell_ft" id="childtype" >${flowDetaiMap.orderList[0].childtype}</div>
	        </div>
	    </div>
	    <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>联系方式</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].user_mobile}</div>
				</div>
				</div>
				  <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>用户名称</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].user_name}</div>
				</div>
				</div>

				  <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>费用</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_cost}</div>
				</div>
				</div>
				  <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>预约时间</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].service_time}</div>
				</div>
				</div>
					 <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>订单说明</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_desc}</div>
				</div>
				</div>
					<div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>保洁地址 </p>
	            </div>
	            <input id="agree" type="hidden" value="${flowDetaiMap.orderList[0].correspondent}">
	        	<div class="weui_cell_ft"  id="address"></div>
	            </div>
  		           </div>
					  <div class="weui_cells">
				<div class="upload">
	        		<div class="weui_cell file" id="selectImage1" style="display: none">
	        			<a href="">选择文件上传</a> 
	        		</div>
	        		<ul class="ipost-list ui-sortable js_fileList"  id="fileurl1" ></ul> 
	        	    <input type="hidden" value="${flowDetaiMap.orderList[0].picurls}"  id="hideurl1"  /> 	        	
	        		
	        		<input type="hidden" value="" name="picPath" id="picPath"  /> 	        		  
	    		</div> 	
				</div>
				<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
                <script type="text/javascript" src="/client/js/common.client.js"></script>  
                <script type="text/javascript" src="/client/js/common.js"></script>
                <script type="text/javascript" src="/client/js/preImage/lightbox-2.6.js"></script>
				<script type="text/javascript" src="/html/pages/flow/js/order/miCleanorder/stepDetail.js" ></script>
  			<c:choose>
		    	<c:when test="${cfg_step_id == '2'}">
		    		<jsp:include page="/html/pages/flow/pages/order/workorder/stepDetail2.jsp"></jsp:include>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '3'}">
		    		<jsp:include page="/html/pages/flow/pages/order/cleanorder/stepDetail3.jsp"></jsp:include>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '4'}">
		    		<jsp:include page="/html/pages/flow/pages/order/workorder/stepDetail4.jsp"></jsp:include>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '5'}">
		    		<jsp:include page="/html/pages/flow/pages/order/workorder/stepDetail5.jsp"></jsp:include>
		    	</c:when>
		    </c:choose>
  		</c:when>
  		
  		<c:otherwise>
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
	        	<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_name}</div>
	        </div>
	    </div>
	    	<div class="weui_cells">
  			<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>保洁类型</p>
	            </div>
	            <input type="hidden"  dataId="${flowDetaiMap.orderList[0].childtype}" id="childtype1">
	        	<div class="weui_cell_ft" id="childtype"   >${flowDetaiMap.orderList[0].childtype}</div>
	        </div>
	    </div>
	    <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>联系方式</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].user_mobile}</div>
				</div>
				</div>
				  <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>用户名称</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].user_name}</div>
				</div>
				</div>

				  <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>费用</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_cost}</div>
				</div>
				</div>
				  <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>预约时间</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].service_time}</div>
				</div>
				</div>
					<div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>保洁地址 </p>
	            </div>
	            <input id="agree" type="hidden" value="${flowDetaiMap.orderList[0].correspondent}">
	        	<div class="weui_cell_ft"  id="address"></div>
	            </div>
  		           </div>
					 <div class="weui_cells">
				<div class="weui_cell">
					<div class="weui_cell_bd weui_cell_primary">
						<p>订单说明</p>
					</div>
					<div class="weui_cell_ft" id="task_code">${flowDetaiMap.orderList[0].order_desc}</div>
				</div>
				</div>
				  <div class="weui_cells">
				<div class="upload">
	        		<div class="weui_cell file" id="selectImage" style="display: none">
	        			<a href="">选择文件上传</a> 
	        		</div>
	        		<ul class="ipost-list ui-sortable js_fileList"   id="fileurl"></ul> 
	        	    <input type="hidden" value="${flowDetaiMap.orderList[0].picurls}"  id="hideurl"  /> 	        	
	        		<input type="hidden" value="" name="picPath" id="picPath"  /> 	        		  
	    		</div> 	
				</div>
				<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
				<script type="text/javascript" src="/client/js/preImage/lightbox-2.6.js"></script>
                <script type="text/javascript" src="/client/js/common.client.js"></script>  
                <script type="text/javascript" src="/client/js/common.js"></script>
				<script type="text/javascript" src="/html/pages/flow/js/order/miCleanorder/stepDetail.js" ></script>
  		</c:otherwise>
  	</c:choose>
  	 </c:if>	
    <c:if test="${isMobile eq '0'}">
  	<c:choose>
  		<c:when test="${edit==1}">
  			<form class="form-horizontal" id="form2">
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">订单编码:</label>
						<div class="col-sm-8 " >
							<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_code} </span>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group has-feedback" id="onameDiv">
						<label class="col-sm-2 control-label">订单名称:</label>
						<div class="col-sm-6"  >
						<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_name}</span>
						</div>
						<div class="col-sm-4" style="color:red; font-size:13px;"></div>
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
					<div class="form-group">
				    <label class="col-sm-2  control-label">保洁类型：</label>
				      <div class="col-sm-8">
				      <span class=" form-control" style="border: 0" dataId="${flowDetaiMap.orderList[0].childtype}" id="childtype1"></span>
					  </div>
					  <div class="col-sm-2">
					  </div>
				   </div>
					<div class="form-group">
						<label class="col-sm-2 control-label">费用:</label>
						<div class="col-sm-2 " >
						 <span class=" form-control" style="border: 0" >${flowDetaiMap.orderList[0].order_cost}</span>
						</div>
						<label class="col-sm-2 control-label">预约时间:</label>
						<div class="col-sm-4 " >
						<span class=" form-control" style="border: 0" >${flowDetaiMap.orderList[0].service_time}</span>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2  control-label">上传图片：</label>
						<div class="col-sm-8">
							<div class="dropzone" dataId="${flowDetaiMap.orderList[0].picurls}" id="upurlp"></div>
							<div class="row"></div>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">订单说明:</label>
						<div class="col-sm-8 ">
						<span class=" form-control" style="border: 0" >${flowDetaiMap.orderList[0].order_desc}</span>
						</div>
						<div class="col-sm-2"></div>
					</div>
				</form>
				<script type="text/javascript" src="/html/pages/flow/js/order/cleanorder/stepDetail.js" ></script>
  			<c:choose>
		    	<c:when test="${cfg_step_id == '2'}">
		    		<jsp:include page="/html/pages/flow/pages/order/cleanorder/stepDetail2.jsp"></jsp:include>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '3'}">
		    		<jsp:include page="/html/pages/flow/pages/order/cleanorder/stepDetail3.jsp"></jsp:include>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '4'}">
		    		<jsp:include page="/html/pages/flow/pages/order/cleanorder/stepDetail4.jsp"></jsp:include>
		    	</c:when>
		    	<c:when test="${cfg_step_id == '5'}">
		    		<jsp:include page="/html/pages/flow/pages/order/cleanorder/stepDetail5.jsp"></jsp:include>
		    	</c:when>
		    </c:choose>
  		</c:when>
  		<c:otherwise>
  			<form class="form-horizontal" id="form2">
					<div class="form-group">
						<label class="col-sm-2 control-label text-right">订单编码:</label>
						<div class="col-sm-8 " >
							<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_code} </span>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group has-feedback" id="onameDiv">
						<label class="col-sm-2 control-label">订单名称:</label>
						<div class="col-sm-6"  >
						<span class=" form-control" style="border: 0">${flowDetaiMap.orderList[0].order_name}</span>
						</div>
						<div class="col-sm-4" style="color:red; font-size:13px;"></div>
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
					<div class="form-group">
				    <label class="col-sm-2  control-label">保洁类型：</label>
				      <div class="col-sm-8">
				      <span class=" form-control" style="border: 0" dataId="${flowDetaiMap.orderList[0].childtype}" id="childtype1"></span>
					  </div>
					  <div class="col-sm-2">
					  </div>
				   </div>
					<div class="form-group">
						<label class="col-sm-2 control-label">费用:</label>
						<div class="col-sm-2 " >
						 <span class=" form-control" style="border: 0" >${flowDetaiMap.orderList[0].order_cost}</span>
						</div>
						<label class="col-sm-2 control-label">预约时间:</label>
						<div class="col-sm-4 " >
						<span class=" form-control" style="border: 0" >${flowDetaiMap.orderList[0].service_time}</span>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2  control-label"><b
							style="color: red"></b>上传图片：</label>
						<div class="col-sm-8">
							<div class="dropzone" dataId="${flowDetaiMap.orderList[0].picurls}" id="upurlp"></div>
							<div class="row"></div>
						</div>
						<div class="col-sm-2"></div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">订单说明:</label>
						<div class="col-sm-8 ">
						<span class=" form-control" style="border: 0" >${flowDetaiMap.orderList[0].order_desc}</span>
						</div>
						<div class="col-sm-2"></div>
					</div>
				</form>
				<script type="text/javascript" src="/html/pages/flow/js/order/cleanorder/stepDetail.js" ></script>
  		</c:otherwise>
  	</c:choose>
  	 	 </c:if>	
  </body>
</html>
