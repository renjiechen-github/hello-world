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
	<meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link rel="stylesheet" type="text/css" href="/client/css/mission/create_task_detail.min.css" />
    <link rel="stylesheet" type="text/css" href="/client/css/xcConfirm/xcConfirm.min.css" />
    <link rel="stylesheet" type="text/css" href="/html/adminX/css/jquery-ui-1.10.3.min.css"/>
    
<style type="text/css">
		.form-horizontal .form-group{
			margin-left: 0px !important;
		}
		.form-control-static>a>img{
			width: 100px;
		}
	</style>
	<style type="text/css">
		.form-horizontal .form-group{
			margin-left: 0px !important;
		}
		.panel-primary>.panel-heading{
			background-color: #e0e1e7;
    		border-color: #e0e1e7 !important;
    		color: #898989;
    		width: 100%;
		}
	</style>
  </head>
  <body> 
   <c:if test="${isMobile eq '1'}">
   <div class="header">
	<div class="navdiv" >
		<ul class="nav-pills">
			<div class="con right active" rl="b1"><a href="#"><li role="presentation">任务基本信息</li></a></div>
			<div class="con" rl="b2"><a href="#"><li role="presentation">任务处理信息</li></a></div>
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
	    <%--<div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务类型</p>
	            </div>
	        	<div class="weui_cell_ft" id="typenames">${map.taskdetail.typenames}</div>	        	
	        </div>
	    </div>
	    --%><div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务名称</p>
	            </div>
	        	<div class="weui_cell_ft" id="name">${map.taskdetail.name}</div>
	        </div>
	    </div>
	    <div class="weui_cells">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务状态</p>
	            </div>
	        	<div class="weui_cell_ft" id="statename">${map.taskdetail.statename}</div>
	        </div>
	    </div>
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
	    <div class="weui_cells" style="border-bottom:none;">
	     	<div class="weui_cell">
	            <div class="weui_cell_bd weui_cell_primary">
	               <p>任务说明</p>
	            </div>
	        	<div class="weui_cell_ft" id="remark"> ${map.taskdetail.remark} </div>
	        </div>
	    </div>
	   <div class="division">
			<div class="left">
			<hr>
			</div>
			<div class="center">↓&nbsp;其他信息
			</div>
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
	<c:forEach items="${map.stepList}" var="map1" varStatus="s" >
	<div class="info">
			<div class="headdiv">
				<div class="list"><p>●&nbsp;${map1.step_name }</p></div>
			</div>
			<div class="content">
		     	<div class="detail">
		            <div class="left"><p>创建时间</p></div>
		        	<div class="right"><p>${map1.createtime }</p></div>
		        </div>
		        <div class="detail">
		            <div class="left"><p>当前处理人</p></div>
		        	<div class="right"><p>${map1.oper_name }</p></div>
		        </div>
		        <div class="detail">
		            <div class="left"><p>当前处理时间</p></div>
		        	<div class="right"><p>${map1.operdate }</p></div>
		        </div><%--
		        <div class="detail">
		            <div class="left"><p>是否存在子任务</p></div>
		        	<div class="right"><p>${empty map1.wait_task_id?'不存在':'存在' }</p></div>
		        </div>
		        --%><div class="detail">
		            <div class="left"><p>状态</p></div>
		        	<div class="right"><p>${map1.statename}</p></div>
		        </div>
		        <div class="detail">
		            <div class="left"><p>&nbsp;</p></div>
		        	<div class="left"><p>&nbsp;</p></div>
		        	<div class="right" onclick="showOrHide('${s.count}')"><img src="/client/images/mission/3.png" alt="" /></div>
		        </div>
		        <div id='${s.count}' style="display: none">
								<div class="form-group col-sm-12">
									<label class="col-sm-2  control-label">备注：</label>
									<div class="col-sm-10">
										<div class="form-control-static" style="word-break: break-all;">
											<c:if test="${empty map1.remark}">
                                                                                          暂无备注
                              </c:if>
											${map1.remark}
										</div>
									</div>
								</div>
		        <c:if test="${not empty map1.file}">
					<div class="form-group col-sm-12">
					<label class="col-sm-2  control-label">附件：</label>
					  <div class="col-sm-10">
						<div class="form-control-static">
							<c:forTokens items="${map1.file}" delims="," var="map2" >
								<c:choose>
									<c:when test="${fn:split(map2, '##')[2] == '1'}">
										<!-- 图片展示 -->
										<a target="_blank" href="${task_path}${fn:split(map2, '##')[0]}" >
											<img alt="" title="${fn:split(map2, '##')[1]}" src="${task_path}${fn:split(map2, '##')[0]}">
										</a>
									</c:when>
									<c:otherwise>
										<br />
										<a href="${fn:split(map2, '##')[0]}" >${fn:split(map2, '##')[1]}</a>
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
<script type="text/javascript" >
function showOrHide(hide1) {
	var id = "#" + hide1;
	var display = $(id).css('display');
	if (display == 'none') {
		$(id).show();
	} else {
		$(id).hide();
	}
}
	$('.navdiv .con').click(function(){
		var rl = $(this).attr('rl');
		$('.navdiv .con').removeClass('active');
		$(this).addClass('active');
		$('.body .com').removeClass('active');
		$('.body .'+rl).addClass('active');
	});
</script> 
 </c:if>
 
 
  <c:if test="${isMobile  eq '0'}">
	<input type="hidden" id="task_id" name="task_id" value="" >
	<input type="hidden" id="task_code" name="task_code" value="" >
	<section class="panel">
        <header class="panel-heading custom-tab">
            <ul class="nav nav-tabs">
                <li class="active">
                    <a data-toggle="tab" href="#home3">
                        <i class="fa fa-home"></i>
                        任务基本信息 
                    </a>
                </li>
                <li class="">
                    <a data-toggle="tab" href="#about3">
                        <i class="fa fa-retweet"></i>
                        任务处理信息
                    </a>
                </li>
                <!-- <li class="">
                    <a data-toggle="tab" href="#contact3">
                        <i class="fa fa-envelope-o"></i>
                        任务详细
                    </a>
                </li> -->
            </ul>
        </header>
        <div class="panel-body">
            <div class="tab-content">
                <div id="home3" class="tab-pane active">
                	<form class="form-horizontal adminex-form " role="form">
	                    <div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">任务编号：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.task_code}</p>
							</div>
							<label class="col-sm-2  control-label">任务名称：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.name}</p>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">任务类型：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.typenames}</p>
							</div>
							<label class="col-sm-2  control-label">任务状态：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.statename}</p>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">发起人：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.oper_name}</p>
							</div>
							<label class="col-sm-2  control-label">发起时间：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.createtime}</p>
							</div>
						</div>
						<%--<div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">是否超时：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.isTimeout==1?'是':'否'}</p>
							</div>
							<label class="col-sm-2  control-label">任务已耗时：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.already_duration}</p>
							</div>
						</div>
						<div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">任务结束时间：</label>
							<div class="col-sm-4">
								<p class="form-control-static">${map.taskdetail.end_time}</p>
							</div>
							<label class="col-sm-2  control-label"></label>
							<div class="col-sm-4">
							</div>
						</div>
						--%><c:if test="${not empty map.taskdetail.file}">
            				<div class="form-group col-sm-12">
								<label class="col-sm-2  control-label">附件：</label>
								<div class="col-sm-10">
									<div class="form-control-static">
										<c:forTokens items="${map.taskdetail.file}" delims="," var="map2" >
											<c:choose>
												<c:when test="${fn:split(map2, '##')[2] == '1'}">
													<!-- 图片展示 -->
													<a target="_blank" href="${task_path}${fn:split(map2, '##')[0]}" >
														<img alt="" title="${fn:split(map2, '##')[1]}" src="${task_path}${fn:split(map2, '##')[0]}">
													</a>
												</c:when>
												<c:otherwise>
													<br />
													<a href="${task_path}${fn:split(map2, '##')[0]}" >${fn:split(map2, '##')[1]}</a>
												</c:otherwise>
											</c:choose>
										</c:forTokens>
									</div>
								</div>
							</div>
            			</c:if>
						<div class="form-group col-sm-12">
							<label class="col-sm-2  control-label">任务说明：</label>
							<div class="col-sm-10">
								<div class="form-control-static" style="word-break: break-all;">${map.taskdetail.remark}</div>
							</div>
						</div>
						<div style="clear: both;" ></div>
		            	<br />
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
	            	</form>
                </div>
                <div id="about3" class="tab-pane">
                	<table id="detailFlowTable" class="table table-bordered table-striped table-condensed cf" >
                		<thead>
	                		<tr>
	                			<th>序号</th>
	                			<th>步骤名称</th>
	                			<th>创建时间</th>
	                			<th>当前处理人</th>
	                			<th>当前处理时间</th>
	                			<th>是否存在子任务</th>
	                			<th>状态</th>
	                			<th>是否超时</th>
	                			<th>等待处理人</th>
	                			<th>操作</th>
	                		</tr>
                		</thead>
                		<tbody>
                			<c:forEach items="${map.stepList}" var="map1" varStatus="s" >
                				<tr>
	                				<td>${s.count}</td>
	                				<td>${map1.step_name }</td>
	                				<td>${map1.createtime }</td>
	                				<td>${map1.oper_name }</td>
	                				<td>${map1.operdate }</td>
	                				<td>${empty map1.wait_task_id?'不存在':'存在' }</td>
	                				<td>${map1.statename }</td>
	                				<td>${map1.is_overtime==1?"已超时":"未超时" }</td>
	                				<td>${map1.account}</td>
	                				<td>
	                					<button onclick="showFlowDetail.detail(this,'${map1.step_id}','${map1.cfg_step_id}');" title="查看详细" class="button button-circle button-tiny"><i class="fa fa-plus"></i></button>
	                				</td>
                				</tr>
                				<tr class="hide" id="hide_flow_step_${map1.step_id}" >
                					<td colspan="9" >
                						<c:if test="${not empty map1.file}">
                							<div class="form-group col-sm-12">
												<label class="col-sm-2  control-label">附件：</label>
												<div class="col-sm-10">
													<div class="form-control-static">
														<c:forTokens items="${map1.file}" delims="," var="map2" >
															<c:choose>
																<c:when test="${fn:split(map2, '##')[2] == '1'}">
																	<!-- 图片展示 -->
																	<a target="_blank" href="${task_path}${fn:split(map2, '##')[0]}" >
																		<img alt="" title="${fn:split(map2, '##')[1]}" src="${task_path}${fn:split(map2, '##')[0]}">
																	</a>
																</c:when>
																<c:otherwise>
																	<br />
																	<a href="${task_path}${fn:split(map2, '##')[0]}" >${fn:split(map2, '##')[1]}</a>
																</c:otherwise>
															</c:choose>
														</c:forTokens>
													</div>
												</div>
											</div>
                						</c:if>
                						<div class="form-group col-sm-12">
											<label class="col-sm-2  control-label">备注：</label>
											<div class="col-sm-10">
												<div class="form-control-static" style="word-break: break-all;">
													<c:choose>
														<c:when test="${empty map1.remark}">
															暂无备注
														</c:when>
														<c:otherwise>
															${map1.remark}
														</c:otherwise>
													</c:choose>
												</div>
											</div>
										</div>
										<div class="col-sm-12" id="showFlowDetail_${map1.step_id}" ></div>
                					</td>
                				</tr>
                			</c:forEach>
                		</tbody>
                	</table>
                </div>
                <!-- <div id="contact3" class="tab-pane">Contact</div> -->
            </div>
        </div>
    </section>
   	<script type="text/javascript" src="/html/flow/js/base/showFlowDetail.js" ></script>
   	 </c:if>
  </body>
</html>
