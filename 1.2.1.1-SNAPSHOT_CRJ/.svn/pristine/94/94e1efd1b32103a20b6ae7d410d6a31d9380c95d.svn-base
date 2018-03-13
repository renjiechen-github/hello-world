<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>我发起的任务</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0" />
<link rel="stylesheet" type="text/css" href="/client/css/normalize.min.css" />
<link rel="stylesheet" type="text/css" href="/client/css/mission/create_task_detail.min.css" />
<style type="text/css">
.form-control-static>a>img {
	width: 100px;
}
.bzh{
    background-color: red !important;
    color: white !important;
}
.bzh td {
    color: white !important;
}
</style>
</head>
<body>
	<c:if test="${isMobile eq '1'}">
		<div class="header">
			<div class="navdiv">
				<ul class="nav-pills">
					<div class="con right active" rl="b1">
						<a href="#"><li role="presentation">任务基本信息</li></a>
					</div>
					<div class="con" rl="b2">
						<a href="#"><li role="presentation">任务处理信息</li></a>
					</div>
				</ul>
			</div>
		</div>
		<div class="body">
			<div class="b1 com cons1 active">
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>任务编号</p>
						</div>
						<div class="weui_cell_ft" id="task_code">${map.taskdetail.task_code}</div>
					</div>
				</div>
				<%--
	    <div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务类型</p>
	            </div>
	        	<div class="weui_cell_ft" id="typenames">${map.taskdetail.typenames}</div>	        	
	        </div>
	    </div>
	    --%>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>任务名称</p>
						</div>
						<div class="weui_cell_ft" id="name">${map.taskdetail.name}</div>
					</div>
				</div>
				<%--
	    <div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务状态</p>
	            </div>
	        	<div class="weui_cell_ft" id="statename">${map.taskdetail.statename}</div>
	        </div>
	    </div>
	    --%>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>发起时间</p>
						</div>
						<div class="weui_cell_ft" id="createtime">${map.taskdetail.createtime}</div>
					</div>
				</div>
				<div class="weui_cells">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>发起人</p>
						</div>
						<div class="weui_cell_ft" id="oper_name">${map.taskdetail.oper_name}</div>
					</div>
				</div>
				<!-- <div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>是否超时</p>
	            </div>
	        	<div class="weui_cell_ft">否</div>
	        </div>
	    </div>
	    <div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务已耗时</p>
	            </div>
	        	<div class="weui_cell_ft">20</div>
	        </div>
	    </div>
	    <div class="weui_cells" style="border-bottom:none;">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务结束时间</p>
	            </div>
	        	<div class="weui_cell_ft">2016-09-30 </div>
	        </div>
	    </div> -->
				<div class="weui_cells" style="border-bottom: none;">
					<div class="weui_cell">
						<div class="weui_cell_bd weui_cell_primary">
							<p>任务说明</p>
						</div>
						<div class="weui_cell_ft" id="remark">${map.taskdetail.remark}</div>
					</div>
				</div>
				<div class="division">
					<div class="left">
						<hr>
					</div>
					<div class="center">↓&nbsp;其他信息</div>
					<div class="right">
						<hr>
					</div>
				</div>
				<div class="nummber">
					<c:if test="${not empty map.taskdetail.html_path }">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<h3 class="panel-title">其他信息</h3>
							</div>
							<div class="panel-body">
								<jsp:include page="/flow/showTaskDetail.do?task_id=${map.taskdetail.task_id}&task_cfg_id=${map.taskdetail.task_cfg_id}&step_id=${map.stepMap.step_id}&cfg_step_id=${map.stepMap.cfg_step_id}"></jsp:include>
							</div>
						</div>
					</c:if>
				</div>
			</div>
			<div class="b2 com cons1" id="actionHtml">
				<c:forEach items="${map.stepList}" var="map1" varStatus="s">

					<div class="info">
						<div class="headdiv">
							<div class="list">
								<p>●&nbsp;${map1.step_name }</p>
							</div>
						</div>
						<div class="content">
							<div class="detail">
								<div class="left">
									<p>创建时间</p>
								</div>
								<div class="right">
									<p>${map1.createtime }</p>
								</div>
							</div>
							<div class="detail">
								<div class="left">
									<p>当前处理人</p>
								</div>
								<div class="right">
									<p>${map1.oper_name }</p>
								</div>
							</div>
							<div class="detail">
								<div class="left">
									<p>当前处理时间</p>
								</div>
								<div class="right">
									<p>${map1.operdate }</p>
								</div>
							</div>
							<div class="detail">
								<div class="left">
									<p>是否存在子任务</p>
								</div>
								<div class="right">
									<p>${empty map1.wait_task_id?'不存在':'存在' }</p>
								</div>
							</div>
							<div class="detail">
								<div class="left">
									<p>当前状态</p>
								</div>
								<div class="right">
									<p>${map1.statename}</p>
								</div>
							</div>
							<div class="detail">
								<div class="left">
									<p>&nbsp;</p>
								</div>
								<div class="left">
									<p>&nbsp;</p>
								</div>
								<div class="left" onclick="showOrHide('${s.count}')">
									<img src="/client/images/mission/3.png" alt="" />
								</div>
							</div>
							<div id='${s.count}' class="hide">
								${map.taskdetail.remark}
								<c:if test="${not empty map1.file}">
									<div class="form-group col-sm-12">
										<label class="col-sm-2  control-label">附件：</label>
										<div class="col-sm-10">
											<div class="form-control-static">
												<c:forTokens items="${map1.file}" delims="," var="map2">
													<c:choose>
														<c:when test="${fn:split(map2, '##')[2] == '1'}">
															<!-- 图片展示 -->
															<a target="_blank" href="${task_path}${fn:split(map2, '##')[0]}"> <img alt="" title="${fn:split(map2, '##')[1]}" src="${task_path}${task_path}${fn:split(map2, '##')[0]}">
															</a>
														</c:when>
														<c:otherwise>
															<br />
															<a href="${task_path}${fn:split(map2, '##')[0]}">${fn:split(map2,
																'##')[1]}</a>
														</c:otherwise>
													</c:choose>
												</c:forTokens>
											</div>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</div>

		<script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
		<script type="text/javascript" src="/client/js/common.client.js"></script>
		<script type="text/javascript">
			$('.navdiv .con').click(function() {
				var rl = $(this).attr('rl');
				$('.navdiv .con').removeClass('active');
				$(this).addClass('active');
				$('.body .com').removeClass('active');
				$('.body .' + rl).addClass('active');
			});
		</script>
	</c:if>
	<c:if test="${isMobile eq '0'}">
		<section class="panel"> <header class="panel-heading custom-tab turquoise-tab">
		<ul class="nav nav-tabs" role="tablist" id="myTabs">
			<li role="presentation" class="active"><a href="#NEW_ORDER_2_DEAL" aria-controls="NEW_ORDER_2_DEAL" role="tab" data-toggle="tab">新订单待处理 </a></li>
			<li role="presentation"><a href="#OTHER_2_DEAL" aria-controls="OTHER_2_DEAL" role="tab" data-toggle="tab">普通待处理 </a></li>
		</ul>
		</header>
		<div id="myTabContent" class="tab-content">
			<!-- 其他类型待处理订单 -->
			<div class="tab-pane fade" id="OTHER_2_DEAL">
				<section class="panel panel-info panel-seach">
				<div class="panel-body">
					<form class="form-inline" role="form">
						<div class="form-group">
							<label for="exampleInputEmail2">发起任务时间:</label> <input type="text" style="width: 275px" name="sendTime" readonly="readonly" id="sendTime" class="form-control" placeholder="2013-01-02 01:00 至 2013-01-02 01:10" class="span4" />
						</div>
						<div class="form-group flowtypediv ">
							<label class="" for="exampleInputEmail2">任务类型:</label> <select class="form-control" t="type" id="flowType0" name="flowType0">
								<option value="">请选择...</option>
								<c:forEach var="map" items="${typeList}">
									<option value="${map.type_id}">${map.type_name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="" for="exampleInputEmail2">任务状态:</label> <select class="form-control" id="secondary_type" name="secondary_type">
								<option value="">请选择...</option>
								<c:forEach var="map" items="${stateList}">
									<option value="${map.item_id}">${map.item_name}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label class="" for="exampleInputEmail2">任务名称:</label> <input type="text" class="form-control" id="taskName" name="taskName" placeholder="模糊查询任务名称">
						</div>
						<div class="form-group">
							<label class="" for="exampleInputEmail2">任务编号:</label> <input type="text" class="form-control" id="taskCode" name="taskCode" placeholder="模糊查询任务编号">
						</div>
						<div class="form-group tools">
							<a href="javascript:;" class="fa fa-search yc_seach" table="flowDisposeTable">查询</a>
						</div>
					</form>
				</div>
				</section>

				<div class="adv-table">
					<table id="flowDisposeTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
						<thead>
							<tr>
								<th></th>
								<th></th>
								<th>任务类型</th>
								<th>任务编号</th>
								<th>任务名称</th>
								<th>发起任务时间</th>
								<th>当前任务状态</th>
								<th>当前步骤名称</th>
								<th></th>
							</tr>
						</thead>
					</table>
				</div>
			</div>


			<!-- 退租单待处理任务 -->
			<div class="tab-pane fade in active" id="NEW_ORDER_2_DEAL">
				<div class="panel-body">
					<form class="form-inline" role="form">
						<div class="form-group">
							<label for="order_type">订单类型:</label> <select class="form-control" id="order_type" name="order_type">
								<option value="">请选择...</option>
							</select>
						</div>
						<div class="form-group">
							<label for="order_state">订单状态:</label> <select class="form-control" id="order_state" name="order_state">
								<option value="">请选择...</option>
								<option value="待支付">待支付</option>
								<option value="待指派订单">待指派订单</option>
								<option value="待接单">待接单</option>
								<option value="待处理">待处理</option>
								<option value="客服回访">客服回访</option>
								<option value="客服回访待追踪">客服回访待追踪</option>
								<option value="待评价">待评价</option>
								<option value="订单关闭">订单关闭</option>
							</select>
						</div>
						<div class="form-group">
							<label for="keyword">模糊查询:</label> <input type="text" class="form-control suitable" id="keyword" placeholder="用户名、用户手机、订单编号">
						</div>
						<span class="form-group tools"><a href="javascript:;" class="fa fa-search yc_seach" id="searchOrder" table="workOrderTable">查询</a> </span>
					</form>
					<div class="form-group"></div>
					<div class="adv-table">
						<table id="workOrderTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
							<thead>
								<tr>
									<th></th>
									<th></th>
									<th>编码</th>
									<th>订单名称</th>
									<th>订单类型</th>
									<th>创建时间</th>
									<th>用户名称</th>
									<th>用户手机号码</th>
									<th>当前状态</th>
									<th>处理人/角色</th>
	                <th>总时长（小时）</th>
	                <th>当前步骤时长（小时）</th>									
									<th></th>
								</tr>
							</thead>
						</table>
					</div>
				</div>
			</div>
		</div>
		</section>
		<script type="text/javascript" src="/html/pages/flow/js/base/myDisposeFlow.js"></script>
		<script type="text/javascript" src="/html/pages/flow/js/base/workOrder.js"></script>
	</c:if>
</body>
</html>
