<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>看房订单任务详情查看</title>
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
<link rel="stylesheet" href="/html/adminX/js/advanced-datatable/fixedColumns.dataTables.css" />
<link rel="stylesheet" href="/html/adminX/js/advanced-datatable/TableTools.css" />
<link rel="stylesheet" type="text/css" media="all" href="/html/adminX/js/select2/select2.css" />
<link rel="stylesheet" type="text/css" media="all" href="/html/adminX/js/daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css" href="/html/adminX/js/bootstrap-datepicker/css/datepicker-custom.css" />
<link rel="stylesheet" type="text/css" href="/html/adminX/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="/client/css/uploadImage/uploadImage.css" />
<link href="/html/adminX/css/clndr.css" rel="stylesheet">
<link rel="stylesheet" href="/html/adminX/js/morris-chart/morris.css">
<link href="/html/adminX/css/Tendina.css" rel="stylesheet">
<link href="/html/adminX/css/animate.css" rel="stylesheet">
<link href="/html/adminX/css/style.css" rel="stylesheet">
<link href="/html/adminX/css/style-responsive.css" rel="stylesheet">
<link href="/html/adminX/js/advanced-datatable/css/demo_page.css" rel="stylesheet" />
<link href="/html/adminX/js/advanced-datatable/css/demo_table.css" rel="stylesheet" />
<link rel="stylesheet" href="/html/adminX/js/data-tables/DT_bootstrap.css" />

<link rel="stylesheet" href="/html/adminX/css/but.css" />
<link rel="stylesheet" href="/html/adminX/js/dropzone/css/dropzone.css">
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

.label-col {
	padding-left: 0px;
}
</style>
</head>
<body>
	<input type="hidden" id="js-work-order-id" name="js-work-order-id" value="${id}">
	<input type="hidden" id="js-is-mobile" name="js-is-mobile" value="${isMobile}">
	<input type="hidden" id="js-staff-id" name="js-staff-id" value="${staffId}">
	<input type="hidden" id="broker_id" name="broker_id" />
	<!-- 管家手机号 -->
	<input type="hidden" id="assignedDealerPhone" >
	<!-- 第三方号码 -->
	<input type="hidden" id="bindNbr" >
	<!-- 呼叫状态和话单记录回调地址 -->
	<input type="hidden" id="callBackUrl" >
	<section class="panel panel-default">
	<div class="panel-body">
		<div class="panel-body">
			<form class="form-horizontal adminex-form" role="form">
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label label-col">任务编码: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-code"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">房源标题: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-rental-name"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">订单名称: </label>
					<div class="col-md-2 col-xs-12 label-col" style="padding-right: 0px;">
						<p class="form-control-static js-order-name"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label label-col">订单状态: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-state-name"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">创建时间: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-create-date"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">预约时间: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-appointment-date"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label label-col">订单发起: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-created-person"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">用户名称: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-user-name"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">联系方式: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-user-phone"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label label-col">所选区域: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-area-name"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">所选小区: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-group-name"></p>
					</div>
					<label class="col-md-1 col-xs-12 control-label label-col">管家名称: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-assigned-dealer-name"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label label-col">所选渠道: </label>
					<div class="col-md-2 col-xs-12 label-col">
						<p class="form-control-static js-channel-name"></p>
					</div>
				</div>
				<div class="form-group col-md-12 col-xs-12">
					<label class="col-md-2 col-xs-12 control-label label-col">订单备注: </label>
					<div class="col-md-10 col-xs-12 label-col">
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
						<!-- <li role="presentation" class="col-md-2 col-xs-6"><a href="#CUSTOMER_SERV_ASSIGN" class="text-center" aria-controls="CUSTOMER_SERV_ASSIGN" role="tab" data-toggle="tab"> 客服派单 </a></li> -->
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#BUTLER_TAKE_ORDER" class="text-center" aria-controls="BUTLER_TAKE_ORDER" role="tab" data-toggle="tab"> 管家接单 </a></li>
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#BUTLER_EXECUTE" class="text-center" aria-controls="BUTLER_EXECUTE" role="tab" data-toggle="tab"> 管家执行 </a></li>
						<!-- <li role="presentation" class="col-md-2 col-xs-6"><a href="#CUSTOMER_SERV_VISIT" class="text-center" aria-controls="CUSTOMER_SERV_VISIT" role="tab" data-toggle="tab"> 客服回访 </a></li> -->
						<li role="presentation" class="col-md-2 col-xs-6"><a href="#USER_JUDGE" class="text-center" aria-controls="USER_JUDGE" role="tab" data-toggle="tab"> 用户评价 </a></li>
					</ul>
				</div>
			</div>
		</div>

		<!-- 各个TAB页内容 -->
		<div class="tab-content">

			<!-- 管家接单 -->
			<div class="tab-pane fade" id="BUTLER_TAKE_ORDER">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="butlerGetHomeForm">
							<div class="hidden form-group col-md-12 col-xs-12 js-butler-reassign-manager-div">
								<label class="col-md-2 col-xs-4 control-label">受理人员</label>
								<div class="col-md-4 col-xs-12">
									<select class="form-control" id="butlerAgainReassignSelect" dataType="Require"></select>
								</div>
								<div class="col-md-6 col-xs-12"></div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-12 control-label js-house-inspection-comments-label"> </label>
								<div class="col-md-8 col-xs-12">
									<textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" readonly="readonly" rows="6" datatype="Require" msg="请填写信息" class="form-control js-house-inspection-comments-textarea"></textarea>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="text-center">
								<button class="btn btn-info hidden" id="butlerGetHomeBtn" onclick="jsHouseLookingOrderDetail.takeOrder()" type="button">提交</button>
								<button class="btn btn-info hidden" id="butlerReassign" onclick="jsHouseLookingOrderDetail.butlerReassign();" type="button">指派</button>
								<button class="btn btn-info hidden" id="butlerAgainReassign" onclick="jsHouseLookingOrderDetail.butlerAgainReassign();" type="button">重新派单</button>
								<button class="btn btn-info hidden" id="butlerCancelReassign" onclick="jsHouseLookingOrderDetail.butlerCancelReassign();" type="button">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- 管家执行 -->
			<div class="tab-pane fade" id="BUTLER_EXECUTE">
				<div class="panel panel-default">
					<div class="panel-body">
						<form class="form-horizontal adminex-form" role="form" id="butlerExecuteForm">
							<div class="form-group col-md-12 col-xs-12">
								<div class="hidden js-isLook-radio">
									<label class="col-md-2 col-xs-6 control-label label-col">
									 <b style="color: red">*</b>是否带看
									</label>
									<div id="isLookRadio" class="col-md-2 col-xs-6 label-col" style="padding-rigth: 0px;">
										<div class="radio-inline">
											<input disabled="disabled" id="isLookA" name="isLook" type="radio" value="Y" />
											<label for="isLookA">是</label>
										</div>
										<div class="radio-inline">
											<input disabled="disabled" id="isLookB" name="isLook" type="radio" value="N" />
											<label for="isLookB">否</label>
										</div>
									</div>
								</div>
								<div class="hidden js-isRent-radio">
									<label class="col-md-1 col-xs-6 control-label label-col"><b style="color: red">*</b>是否租房</label>
									<div id="isRentRadio" class="col-md-2 col-xs-6 label-col" style="padding-rigth: 0px;">
										<div class="radio-inline">
											<input disabled="disabled" id="isRentA" name="isRent" type="radio" value="Y" />
											<label for="isRentA">是</label>
										</div>
										<div class="radio-inline">
											<input disabled="disabled" id="isRentB" name="isRent" type="radio" value="N" />
											<label for="isRentB">否</label>
										</div>
									</div>
								</div>
								<div class="col-md-6 col-xs-12"></div>
							</div>
							<div class="hidden form-group col-md-12 col-xs-12 js-noRent-checkbox">
								<label class="col-md-2 col-xs-4 control-label label-col"><b style="color: red">*</b>不租原因</label>
								<div id="noRentCheckbox" class="col-md-10 col-xs-8 label-col" style="padding-rigth: 0px;">
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentA" name="noRent" type="checkbox" value="NORENT_A" />
										<label for="noRentA">不满室内配置</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentB" name="noRent" type="checkbox" value="NORENT_B" />
										<label for="noRentB">价格太贵</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentC" name="noRent" type="checkbox" value="NORENT_C" />
										<label for="noRentC">交通不便</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentD" name="noRent" type="checkbox" value="NORENT_D" />
										<label for="noRentD">不满合租人素质</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentE" name="noRent" type="checkbox" value="NORENT_E" />
										<label for="noRentE">周边配置不全</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentF" name="noRent" type="checkbox" value="NORENT_F" />
										<label for="noRentF">小区无车位</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="noRentG" name="noRent" type="checkbox" value="NORENT_G" />
										<label for="noRentG">选择其他区域房间</label>
									</div>
								</div>
							</div>
							<div class="hidden form-group col-md-12 col-xs-12 js-lookFail-checkbox">
								<label class="col-md-2 col-xs-4 control-label label-col"><b style="color: red">*</b>带看失败原因</label>
								<div id="lookFailCheckbox" class="col-md-10 col-xs-8 label-col" style="padding-rigth: 0px;">
									<div class="checkbox-inline">
										<input disabled="disabled" id="lookFailA" name="lookFail" type="checkbox" value="LOOKFAIL_A" />
										<label for="lookFailA">租客看房时间未确认</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="lookFailB" name="lookFail" type="checkbox" value="LOOKFAIL_B" />
										<label for="lookFailB">租客原有房子未到期</label>
									</div>
									<div class="checkbox-inline">
										<input disabled="disabled" id="lookFailC" name="lookFail" type="checkbox" value="LOOKFAIL_C" />
										<label for="lookFailC">联系过慢，租客已经租好房子</label>
									</div>
								</div>
							</div>

							<div class="hidden form-group col-md-12 col-xs-12 js-reassign-div">
								<label class="col-md-2 col-xs-12 control-label label-col"><b style="color: red">*</b>所选区域: </label>
								<div class="col-md-2 col-xs-12 label-col">
									<select class="form-control" id="areaId" dataType="Require" msg="请选择区域">
										<option value="">请选择..</option>
									</select>
								</div>
								<label class="col-md-1 col-xs-12 control-label label-col">所选小区: </label>
								<div class="col-md-2 col-xs-12 label-col">
									<select class="form-control" id="groupId">
										<option value="">请选择..</option>
									</select>
								</div>
								<label class="col-md-1 col-xs-12 control-label label-col"><b style="color: red">*</b>管家名称: </label>
								<div class="col-md-2 col-xs-12 label-col">
									<select class="form-control" id="butlerId" dataType="Require" msg="请选择管家">
										<option value="">请选择..</option>
									</select>
								</div>
							</div>

							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-12 control-label js-rental-account-comments-label"></label>
								<div class="col-md-8 col-xs-12">
									<textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" placeholder="请填写订单备注信息" rows="6" readonly="readonly" datatype="Require" msg="请填写信息" class="form-control js-rental-account-comments-textarea"></textarea>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="text-center">
								<!-- <button id="contactRenter" class="btn btn-info hidden" onclick="jsHouseLookingOrderDetail.contactRenter();" type="button">联系租客</button> -->
								<button id="rentalAccountBtn" class="btn btn-info hidden" onclick="jsHouseLookingOrderDetail.processOrder('N');" type="button">保存</button>
								<button id="rentalAccountPassBtn" class="btn btn-info hidden" onclick="jsHouseLookingOrderDetail.processOrder('Y');" type="button">提交</button>
								<button id="processAgainReassign" class="btn btn-info hidden" onclick="jsHouseLookingOrderDetail.processAgainReassign();" type="button">重新生成订单</button>
								<button id="processReassign" class="btn btn-info hidden" onclick="jsHouseLookingOrderDetail.processReassign();" type="button">提交</button>
								<button id="processCancelReassign" class="btn btn-info hidden" onclick="jsHouseLookingOrderDetail.processCancelReassign();" type="button">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div>

			<!-- 客服回访 -->
			<!-- 			<div class="tab-pane fade" id="CUSTOMER_SERV_VISIT">
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
								<label class="col-md-2 col-xs-12 control-label js-marketing-executive-audit-comments-label"></label>
								<div class="col-md-8 col-xs-12">
									<textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" readonly="readonly" rows="6" class="form-control js-marketing-executive-audit-comments-textarea" datatype="Require" msg="请填写信息"></textarea>
								</div>
								<div class="col-md-2"></div>
							</div>
							<div class="text-center">
								<button class="btn btn-info hidden" id="passInStaffReview" onclick="jsHouseLookingOrderDetail.passInStaffReview()" type="button">通过</button>
								<button class="btn btn-info hidden" id="reassign" onclick="jsHouseLookingOrderDetail.reassign();" type="button">指派</button>
								<button class="btn btn-info hidden" id="staffTace" onclick="jsHouseLookingOrderDetail.staffTace();" type="button">订单追踪</button>
								<button class="btn btn-info hidden" id="reassignOrderBtn" onclick="jsHouseLookingOrderDetail.reassignOrderInStaffReview();" type="button">重新派单</button>
								<button class="btn btn-info hidden" id="cancelReassignOrderBtn" onclick="jsHouseLookingOrderDetail.cancelReassignOrder();" type="button">返回</button>
							</div>
						</form>
					</div>
				</div>
			</div> -->
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
												<a href="javascript:;" class="file" id="image-upurl-1">
													选择照片上传 </a>
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
									<textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" disabled class="form-control js-judge-audit-comments-textarea" msg=""></textarea>
								</div>
							</div>
							<div class="form-group col-md-12 col-xs-12">
								<label class="col-md-2 col-xs-4 control-label js-judge-audit-time-label">评价时间</label>
								<label class="col-md-2 col-xs-4 control-label js-judge-audit-time-label-value" style='text-align:left;white-space:nowrap;'></label>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<section id="cd-timeline" class="cd-container" style="display: none">
	</section>
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
	<script type="text/javascript" src="/html/adminX/js/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
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
<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/houseLookingOrderDetail.js"></script>
</html>
