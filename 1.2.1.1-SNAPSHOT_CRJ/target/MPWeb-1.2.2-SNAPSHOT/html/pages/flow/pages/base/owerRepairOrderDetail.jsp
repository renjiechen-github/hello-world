<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>业主维修任务详情查看</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--begin-->
<link href="/html/adminX/js/iCheck/skins/minimal/minimal.css" rel="stylesheet">
<link href="/html/adminX/js/iCheck/skins/square/square.css" rel="stylesheet">
<link href="/html/adminX/js/iCheck/skins/square/red.css" rel="stylesheet">
<link href="/html/adminX/js/iCheck/skins/square/blue.css" rel="stylesheet">
<link rel="stylesheet" href="/html/adminX/js/bootstrap-jquery-confirm/jquery-confirm.css" />
<link rel="stylesheet" href="/html/adminX/js/advanced-datatable/fixedColumns.dataTables.min.css" />
<link rel="stylesheet" href="/html/adminX/js/advanced-datatable/TableTools.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="/html/adminX/js/select2/select2.min.css" />
<link rel="stylesheet" type="text/css" media="all" href="/html/adminX/js/daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" href="/html/adminX/js/bootstrap-datepicker/css/datepicker-custom.css" />
<link rel="stylesheet" type="text/css"
	href="/html/adminX/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" type="text/css" href="/client/css/uploadImage/uploadImage.css" />
<link href="/html/adminX/css/clndr.css" rel="stylesheet">
<link rel="stylesheet" href="/html/adminX/js/morris-chart/morris.css">
<link href="/html/adminX/css/Tendina.css" rel="stylesheet">
<link href="/html/adminX/css/animate.min.css" rel="stylesheet">
<link href="/html/adminX/css/style.min.css" rel="stylesheet">
<link href="/html/adminX/css/style-responsive.css" rel="stylesheet">
<link href="/html/adminX/js/advanced-datatable/css/demo_page.css" rel="stylesheet" />
<link href="/html/adminX/js/advanced-datatable/css/demo_table.css" rel="stylesheet" />
<link rel="stylesheet" href="/html/adminX/js/data-tables/DT_bootstrap.css" />

<link rel="stylesheet" href="/html/adminX/css/but.min.css" />
<link rel="stylesheet" href="/html/adminX/js/dropzone/css/dropzone.min.css">
<link rel="stylesheet" href="/html/adminX/js/colorBox/colorbox.css" />
<link rel="stylesheet" href="/html/adminX/css/timeStaft.css">
<!-- end -->
<style type="text/css">
.form-horizontal .form-group {
	margin-left: 0px !important;
}

.panel-primary>.panel-heading {
	background-color: #e0e1e7;
	border-color: #e0e1e7 !important;
	color: #898989;
}
</style>
</head>
<body>
	<input type="hidden" id="js-work-order-id" name="js-work-order-id" value="com.ycroom:MPWeb:war:1.2.2-SNAPSHOT">
	<input type="hidden" id="js-is-mobile" name="js-is-mobile" value="${isMobile}">
	<input type="hidden" id="js-staff-id" name="js-staff-id" value="${staffId}">
	<input type="hidden" id="js-is-leader" name="js-is-leader" value="${isLeader}">
	<section class="panel panel-default">
	<div class="panel-body">
		<div class="panel-body">
			<form class="form-horizontal adminex-form" role="form">
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">任务编码: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-code"></p>
					</div>
					<label class="col-md-2 col-xs-12 control-label">订单名称: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-order-name"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">状态: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-state-name"></p>
					</div>
					<label class="col-md-2 col-xs-12 control-label">预约时间: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-appointment-date"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">发起人: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-created-person"></p>
					</div>
					<label class="col-md-2 col-xs-12 control-label">创建时间: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-create-date"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">联系方式: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-user-phone"></p>
					</div>
					<label class="col-md-2 col-xs-12 control-label">用户名称: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-user-name"></p>
					</div>
				</div>

				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">维修类型: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-type"></p>
					</div>
					<!-- 					<label class="col-md-2 col-xs-12 control-label">出租合约名称: </label>
					<div class="col-md-4 col-xs-12">
						<p class="form-control-static js-rental-name"></p>
					</div> -->
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">维修地址: </label>
					<div class="col-md-10 col-xs-12">
						<p class="form-control-static js-address"></p>
						<div class="row"></div>
					</div>
				</div>

				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">图片: </label>
					<div class="col-md-10 col-xs-12">

						<c:choose>
							<c:when test="${isMobile eq 'Y'}">
								<div class="upload">
									<!--<a href="javascript:;" class="file" id="myAwesomeDropzone" onclick="jsOwerRepairOrderDetail.uploadNbrAttachment()"> 选择照片上传 </a>-->
									<div id="myAwesomeDropzone-view"></div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="dropzone" id="myAwesomeDropzone"></div>
								<div class="row"></div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label">备注: </label>
					<div class="col-md-10 col-xs-12">
						<p class="form-control-static js-order-comments"></p>
					</div>
				</div>
			</form>
		</div>

		<!-- TAB页 -->
		<div class="panel panel-default">
			<div class="panel-body">
				<div class="custom-tab turquoise-tab col-md-12 col-xs-12">
					<ul class="nav nav-tabs js-tab" role="tablist">
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#CUSTOMER_SERV_ASSIGN" class="text-center"
							aria-controls="CUSTOMER_SERV_ASSIGN" role="tab" data-toggle="tab"> 客服派单 </a></li>
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#BUTLER_TAKE_ORDER" class="text-center"
							aria-controls="BUTLER_TAKE_ORDER" role="tab" data-toggle="tab"> 接单 </a></li>
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#BUTLER_EXECUTE" class="text-center"
							aria-controls="BUTLER_EXECUTE" role="tab" data-toggle="tab"> 执行 </a></li>
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#CUSTOMER_SERV_VISIT" class="text-center"
							aria-controls="CUSTOMER_SERV_VISIT" role="tab" data-toggle="tab"> 客服回访 </a></li>
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#USER_JUDGE" class="text-center"
							aria-controls="USER_JUDGE" role="tab" data-toggle="tab"> 用户评价 </a></li>
					</ul>
				</div>
			</div>
		</div>

		<!-- 各个TAB页内容 -->
		<div class="tab-content">

			<!-- 客服派单 -->
			<div class="tab-pane fade" id="CUSTOMER_SERV_ASSIGN">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="takOrderForm">
							<div class="form-group col-md-12 col-xs-12 js-reassign-manager-div">
								<label class="col-md-2 col-xs-4 control-label">受理人员</label>
								<div class="col-md-4 col-xs-12">
									<select class="form-control" id="managerSelect" dataType="Require" readonly></select>
								</div>
								<div class="col-md-6 col-xs-12"></div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-4 control-label js-comments-label"></label>
								<div class="col-md-10 col-xs-12">
									<textarea
										onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')"
										readonly="readonly" rows="6" class="form-control js-comments-textarea" placeholder="请填写信息"></textarea>
								</div>
							</div>
							<div class="text-center">
								<button class="btn btn-info hidden" id="confirmReassignOrderBtn"
									onclick="jsOwerRepairOrderDetail.assignOrder();" type="button">指派</button>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- 接单 -->
			<div class="tab-pane fade" id="BUTLER_TAKE_ORDER">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="butlerGetHomeForm">
							<div class="hidden form-group col-md-12 col-xs-12 js-reassign-manager-div2">
								<label class="col-md-2 col-xs-4 control-label">受理人员</label>
								<div class="col-md-4 col-xs-12">
									<select class="form-control" id="managerSelect2" dataType="Require"></select>
								</div>
								<div class="col-md-6 col-xs-12"></div>
							</div>

							<div class="form-group">
								<label class="col-md-2 col-xs-12 control-label js-house-inspection-comments-label"> </label>
								<div class="col-md-8 col-xs-12">
									<textarea
										onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')"
										readonly="readonly" rows="6" datatype="Require" msg="请填写信息"
										class="form-control js-house-inspection-comments-textarea"></textarea>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="text-center">
								<button class="btn btn-info hidden" id="butlerGetHomeBtn" onclick="jsOwerRepairOrderDetail.takeOrder()"
									type="button">接单</button>
								<button class="btn btn-info hidden" id="rework" onclick="jsOwerRepairOrderDetail.rework()" type="button">重新指派</button>
								<button class="btn btn-info hidden" id="reassignOrder" onclick="jsOwerRepairOrderDetail.assignOrder1()"
									type="button">指派</button>
								<button class="btn btn-info hidden" id="reback" onclick="jsOwerRepairOrderDetail.reback()" type="button">返回</button>

								<!-- <button class="btn btn-info hidden" id="butlerGetHomeBtn" onclick="jsOwerRepairOrderDetail.takeOrder()" type="button">保存</button> -->
							</div>


						</form>
					</div>
				</div>
			</div>

			<!-- 执行 -->
			<div class="tab-pane fade" id="BUTLER_EXECUTE">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="butlerExecuteForm">
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-12 control-label js-rental-account-upurl-label"></label>
								<div class="col-md-8 col-xs-12">
									<c:choose>
										<c:when test="${isMobile eq 'Y'}">
											<div class="upload">
												<a href="javascript:;" class="file" id="upurl" onclick="jsOwerRepairOrderDetail.uploadhousePicture()">
													选择照片上传 </a>
												<div id="upurl-view"></div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="dropzone" id="upurl"></div>
											<div class="row"></div>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-12 control-label js-rental-account-comments-label"> </label>
								<div class="col-md-8 col-xs-12">
									<textarea
										onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')"
										readonly="readonly" rows="6" datatype="Require" msg="请填写信息"
										class="form-control js-rental-account-comments-textarea"></textarea>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="text-center">
								<button id="rentalAccountBtn" class="btn btn-info hidden" onclick="jsOwerRepairOrderDetail.processOrder('N');" type="button">保存</button>
								<button id="rentalAccountPassBtn" class="btn btn-info hidden" onclick="jsOwerRepairOrderDetail.processOrder('Y');" type="button">提交</button>
								<button class="btn btn-info hidden" id="againAssignOrder" onclick="jsOwerRepairOrderDetail.againAssignOrder()" type="button">重新指派</button>
								<button class="btn btn-info hidden" id="againAssignOrderBtn" onclick="jsOwerRepairOrderDetail.againAssignOrderBtn()" type="button">提交</button>
								<button class="btn btn-info hidden" id="againAssignOrderCancelBtn" onclick="jsOwerRepairOrderDetail.againAssignOrderCancelBtn()" type="button">取消</button>								
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- 客服回访 -->
			<div class="tab-pane fade" id="CUSTOMER_SERV_VISIT">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="marketingExecutiveAuditForm">
							<div class="hidden form-group col-md-12 col-xs-12 js-reassign-manager-div1">
								<label class="col-md-2 col-xs-4 control-label">受理人员</label>
								<div class="col-md-4 col-xs-12">
									<select class="form-control" id="managerSelect1" dataType="Require"></select>
								</div>
								<div class="col-md-6 col-xs-12"></div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-12 control-label js-visit-upurl-label"></label>
								<div class="col-md-8 col-xs-12">
									<c:choose>
										<c:when test="${isMobile eq 'Y'}">
											<div class="upload">
												<a href="javascript:;" class="file" id="js-visit-upurl" onclick="jsOwerRepairOrderDetail.uploadReback()">
													选择照片上传 </a>
												<div id="js-visit-upurl-view"></div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="dropzone" id="js-visit-upurl"></div>
											<div class="row"></div>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-12 control-label js-marketing-executive-audit-comments-label"></label>
								<div class="col-md-8 col-xs-12">
									<textarea
										onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')"
										readonly="readonly" rows="6" class="form-control js-marketing-executive-audit-comments-textarea" datatype="Require" msg="请填写信息"></textarea>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="text-center">
								<button class="btn btn-info hidden" id="marketingExecutiveAuditPassBtn"
									onclick="jsOwerRepairOrderDetail.marketingExecutiveAuditPass()" type="button">通过</button>
								<button class="btn btn-info hidden" id="rentalAccountReturn"
									onclick="jsOwerRepairOrderDetail.rentalAccountReturn()" type="button">客服追踪</button>
								<button class="btn btn-info hidden" id="confirmReassignOrderBtn1"
									onclick="jsOwerRepairOrderDetail.assignOrder2();" type="button">指派</button>
								<button class="btn btn-info hidden" id="reassignOrderBtn"
									onclick="jsOwerRepairOrderDetail.reassignOrderInStaffReview();" type="button">重新派单</button>
								<button class="btn btn-info hidden" id="cancelReassignOrderBtn"
									onclick="jsOwerRepairOrderDetail.cancelReassignOrder();" type="button">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- 用户评价 -->
			<div class="tab-pane fade" id="USER_JUDGE">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="judgeAuditForm">
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-4 control-label js-judge-audit-image-url-label">图片</label>
								<div class="col-md-8 col-xs-12">
									<c:choose>
										<c:when test="${isMobile eq 'Y'}">
											<div class="upload">
												<a href="javascript:;" class="file" id="image-upurl-1"> 选择照片上传 </a>
												<div id="js-judge-audit-image-url-mobile"></div>
											</div>
										</c:when>
										<c:otherwise>
											<div class="dropzone" id="image-upurl-1"></div>
											<div class="row"></div>
										</c:otherwise>
									</c:choose>
								</div>
								<div class="col-sm-2"></div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-4 control-label js-judge-audit-comments-label">用户评论</label>
								<div class="col-md-8 col-xs-12">
									<textarea
										onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')"
										disabled rows="6" class="form-control js-judge-audit-comments-textarea" msg=""></textarea>
								</div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-4 control-label js-judge-audit-time-label">评价时间</label> <label
									class="col-md-2 col-xs-4 control-label js-judge-audit-time-label-value"
									style='text-align: left; white-space: nowrap;'></label>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<section id="cd-timeline" class="cd-container" style="display: none"> </section>
</body>

<!-- begin -->
	<script type="text/javascript" src="/html/adminX/js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="/client/js/common.client.js"></script>
	<script src="/html/adminX/js/jquery-1.10.2.js"></script>
	<script src="/html/adminX/js/jquery-ui-1.9.2.custom.js"></script>
	<script src="/html/adminX/js/jquery-migrate-1.2.1.js"></script>
	<script src="/html/adminX/js/bootstrap.js"></script>
	<script src="/html/adminX/js/modernizr.js"></script>
	<script src="/html/adminX/js/jquery.nicescroll.js"></script>
	<script src="/html/adminX/js/ichartjs/ichart.1.2.js"></script>

	<script src="/html/adminX/js/easypiechart/jquery.easypiechart.js"></script>
	<script src="/html/adminX/js/sparkline/jquery.sparkline.js"></script>
	<script src="/html/adminX/js/iCheck/jquery.icheck.js"></script>
	<script src="/html/adminX/js/flot-chart/jquery.flot.js"></script>
	<script src="/html/adminX/js/flot-chart/jquery.flot.tooltip.js"></script>
	<script src="/html/adminX/js/flot-chart/jquery.flot.resize.js"></script>
	<script src="/html/adminX/js/morris-chart/morris.js"></script>
	<script src="/html/adminX/js/morris-chart/raphael.js"></script>
	<script src="/html/adminX/js/calendar/clndr.js"></script>
	<script src="/html/adminX/js/calendar/moment-2.2.1.js"></script>
	<script src="/html/adminX/js/underscore/underscore-min.js"></script>
	<script src="/html/adminX/js/encrypt/sha1.js"></script>
	<script src="/html/adminX/js/hdialog/js/jquery.hDialog.js"></script>

	<script src="/html/adminX/js/bootstrap-jquery-confirm/jquery-confirm.js"></script>
	<script src="/html/adminX/js/jquery.jcryption.3.1.0.js"></script>
	<script src="/html/adminX/js/cookies/jquery.cookie.js"></script>
	<script src="/html/adminX/js/jQuery.md5.js"></script>
	<script src="/html/adminX/js/layer/layer.js"></script>
	<!--刘飞   服务时间 begin -->
	<!-- <script type="text/javascript" src="/html/adminX/js/bootstrap-datepicker/js/bootstrap-datepicker.js"></script> -->
	<script type="text/javascript" src="/html/adminX/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript"
		src="/html/adminX/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<!--刘飞   服务时间 end -->

	<!-- 孙诚明 时间控件 -->
	<script type="text/javascript" src="/html/js/My97DatePicker/WdatePicker.js"></script>

	<script type="text/javascript" src="/html/adminX/js/advanced-datatable/js/jquery.dataTables.js"></script>

	<script type="text/javascript" src="/html/adminX/js/daterangepicker/moment.js"></script>
	<script type="text/javascript" src="/html/adminX/js/daterangepicker/daterangepicker.js"></script>

	<script type="text/javascript" src="/html/adminX/js/data-tables/DT_bootstrap.js"></script>
	<script type="text/javascript" src="/html/adminX/js/advanced-datatable/table.js"></script>
	<script type="text/javascript" src="/html/adminX/js/advanced-datatable/tableTools.js"></script>
	<script src="/html/adminX/js/Tendina.js"></script>
	<script src="/html/adminX/js/pin/jquery.pin.js"></script>

	<script src="/html/adminX/js/common/common.js"></script>


	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=evk128VzLcjxdpLN2mA8nwSm"></script>
	<script type="text/javascript" src="/html/adminX/js/dropzone/dropzone.js"></script>

	<script type="text/javascript" src="/html/adminX/js/select2/select2.js"></script>

	<!--<link href="/html/adminX/js/table/table.min.css" rel="stylesheet">-->
	<!--<script src="/html/adminX/js/table/table.js"></script>-->
	<!-- <script src="/html/adminX/js/scripts.js"></script> -->
	<script src="/html/adminX/js/jquery.form.js"></script>
	<script src="/html/adminX/js/colorBox/colorbox.js"></script>

<script type="text/javascript" src="/html/pages/flow/js/base/baseWorkOrder.js"></script>
<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/subOrderStateDef.js"></script>
<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/owerRepairOrderDetail.js"></script>
</html>
