<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新退租订单详情</title>
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
<link rel="stylesheet" type="text/css" href="/html/adminX/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" />
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
.collapse {
	display: none;
}

.in {
	display: block;
}

.form-horizontal .form-group {
		margin-left: 0px !important;
}

.panel-primary>.panel-heading {
		background-color: #e0e1e7;
		border-color: #e0e1e7 !important;
		color: #898989;
}

.bzh {
		background-color: red !important;
		color: white !important;
}

.bzh td {
		color: white !important;
}
</style>
</head>
<body style="position: relative">
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
										<label class="col-md-2 col-xs-12 control-label">出租合约名称: </label>
										<div class="col-md-4 col-xs-12">
												<p class="form-control-static js-rental-name"></p>
										</div>
								</div>
								<div class="form-group col-md-12 col-xs-12">
										<label class="col-md-2 col-xs-12 control-label">退租类型: </label>
										<div class="col-md-4 col-xs-12">
												<p class="form-control-static js-type"></p>
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
										<label class="col-md-2 col-xs-12 control-label">退租时间: </label>
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
										<label class="col-md-2 col-xs-12 control-label">出租合约编码: </label>
										<div class="col-md-4 col-xs-12">
												<p class="form-control-static js-agree-code"></p>
										</div>
								</div>
								<div class="form-group col-md-12 col-xs-12">
										<label class="col-md-2 col-xs-12 control-label">备注: </label>
										<div class="col-md-4 col-xs-12">
												<p class="form-control-static js-order-comments"></p>
										</div>
								</div>
						</form>
				</div>

				<div class="panel panel-default">
						<div class="panel-body">
								<div class="custom-tab turquoise-tab col-md-12 col-xs-12">
										<ul class="nav nav-tabs js-tab" role="tablist">
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#TAKE_ORDER" class="text-center" aria-controls="TAKE_ORDER" role="tab" data-toggle="tab">管家接单</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#HOUSE_RELEASE" class="text-center" aria-controls="HOUSE_RELEASE" role="tab" data-toggle="tab">房源释放</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#BUTLER_GET_HOME" class="text-center" aria-controls="BUTLER_GET_HOME" role="tab" data-toggle="tab">管家上门</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#COST_LIQUIDATION" class="text-center" aria-controls="COST_LIQUIDATION" role="tab" data-toggle="tab">费用清算</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#RENTAL_ACCOUNT" class="text-center" aria-controls="RENTAL_ACCOUNT" role="tab" data-toggle="tab">租务核算</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#MARKETING_EXECUTIVE_AUDIT" class="text-center" aria-controls="MARKETING_EXECUTIVE_AUDIT" role="tab" data-toggle="tab">市场部高管审批</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#FINANCE_AUDIT" class="text-center" aria-controls="FINANCE_AUDIT" role="tab" data-toggle="tab">财务审批</a></li>
												<li role="presentation" class="col-md-2 col-xs-6 col-lg-3"><a href="#USER_JUDGE" class="text-center" aria-controls="USER_JUDGE" role="tab" data-toggle="tab">用户评价</a></li>
										</ul>
								</div>
						</div>
				</div>

				<div class="tab-content">
						<!-- 管家接单 -->
						<div class="tab-pane fade" id="TAKE_ORDER">
								<div class="panel panel-default">
										<div class="panel-body">
												<form class="form-horizontal adminex-form" role="form" id="takOrderForm">
														<div class="form-group col-md-12 col-xs-12 hidden js-reassign-manager-div">
																<label class="col-md-2 col-xs-4 control-label">受理人员</label>
																<div class="col-md-4 col-xs-12">
																		<select class="form-control" id="managerSelect" dataType="Require"></select>
																</div>
																<div class="col-md-6 col-xs-12"></div>
														</div>
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-4 control-label js-comments-label"></label>
																<div class="col-md-10 col-xs-12">
																		<textarea onblur="this.value = this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '')" class="js-comments-textarea form-control" rows="6" placeholder="请填写信息">
																		</textarea>
																</div>
														</div>
														<div class="text-center">
																<button class="btn btn-info" id="takeOrderBtn" onclick="jsNewCancelLeaseOrderDetail.takeOrder()" type="button">接单</button>
																<button class="btn btn-info" id="reassignOrderBtn" onclick="jsNewCancelLeaseOrderDetail.reassignOrder();" type="button">重新派单</button>
																<button class="btn btn-info hidden" id="confirmReassignOrderBtn" onclick="jsNewCancelLeaseOrderDetail.confirmReassignOrder();" type="button">指派</button>
																<button class="btn btn-info hidden" id="cancelReassignOrderBtn" onclick="jsNewCancelLeaseOrderDetail.cancelReassignOrder();" type="button">返回</button>
																<button class="btn btn-info" id="takeOrderCloseBtn" onclick="jsNewCancelLeaseOrderDetail.houseClose()" type="button">订单关闭</button>
														</div>
												</form>
										</div>
								</div>
						</div>

						<!-- 房源释放 -->
						<div class="tab-pane fade" id="HOUSE_RELEASE">
								<div class="panel panel-default">
										<div class="panel-body">
												<form class="form-horizontal adminex-form" role="form" id="houseReleaseForm">
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-12 control-label">上门时间</label>
																<div class="col-md-4 col-xs-12">
																		<input readonly type="text" class="form-control-static js-butler-date" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,readOnly:true})" />
																</div>
														</div>
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-4 control-label js-comments-release-label"></label>
																<div class="col-md-10 col-xs-12">
																		<textarea onblur="this.value = this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '')" class="js-comments-release-textarea form-control" rows="6" placeholder="请填写信息">
																		</textarea>
																</div>
														</div>
														<div class="text-center">
																<button class="btn btn-info" id="houseReleaseBtn" onclick="jsNewCancelLeaseOrderDetail.houseRelease()" type="button">提交信息</button>
																<button class="btn btn-info" id="houseCloseBtn" onclick="jsNewCancelLeaseOrderDetail.houseClose()" type="button">关闭订单</button>
														</div>
												</form>
										</div>
								</div>
						</div>

						<!-- 管家上门 -->
						<div class="tab-pane fade" id="BUTLER_GET_HOME">
								<div class="panel panel-default">
										<div class="panel-body">
												<form class="form-horizontal adminex-form" role="form" id="butlerGetHomeForm">
														<div class="div-butler-info">
																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-alipay-label"></label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-alipay-input" type="text" maxlength="30" class=" form-control">
																		</div>
																		<label class="col-md-1 col-xs-12 control-label js-bank-card-nbr-label"></label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-bank-card-nbr-input" type="text" class=" form-control" maxlength="21">
																		</div>
																		<label style="padding-left: 0px" class="col-md-1 col-xs-12 control-label js-bank-address-label"></label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-bank-adress-input" type="text" maxlength="30" class="form-control">
																		</div>
																		<div class="col-md-2 col-xs-12">
																				<b style="color: red; border: 0px" class="form-control">支付宝或银行卡任选其一 </b>
																		</div>
																</div>

																<div class="form-group">
																		<label style="padding-left: 0px" class="col-md-2 col-xs-12 control-label js-property-fee-label">物业费（元）</label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-property-fee-input" type="text" maxlength="8">
																		</div>

																		<label style="padding-left: 0px" class="col-md-1 col-xs-12 control-label js-waste-fee-label">垃圾费（元）</label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-waste-fee-input" type="text" maxlength="8">
																		</div>
																</div>

																<div class="form-group">
																		<label style="padding-left: 0px" class="col-md-2 col-xs-12 control-label js-water-nbr-from-label"> </label>
																		<div class="col-md-2 col-xs-12">
																				<input readonly="readonly" class="form-control js-water-nbr-from-input" type="text" maxlength="8">
																		</div>
																		<label style="padding-left: 0px" class="col-md-1 col-xs-12 control-label js-water-nbr-to-label"> </label>
																		<div class="col-md-2 col-xs-12">
																				<input msg="请填写水表结束度数" class="form-control js-water-nbr-to-input" datatype="Money2" type="text" maxlength="8">
																		</div>
																		<label style="padding-left: 0px" class="col-md-1 col-xs-12 control-label js-water-unit-price-label">水费单价（元）</label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-water-unit-price-input" type="text" maxlength="8">
																		</div>
																</div>

																<div class="form-group">
																		<label style="padding-left: 0px" class="col-md-2 col-xs-12 control-label js-gas-nbr-from-label"> </label>
																		<div class="col-md-2 col-xs-12">
																				<input readonly="readonly" class="form-control js-gas-nbr-from-input" type="text" maxlength="8">
																		</div>

																		<label style="padding-left: 0px" class="col-md-1 col-xs-12 control-label js-gas-nbr-to-label"> </label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-gas-nbr-to-input" type="text" maxlength="8">
																		</div>
																		<label class="col-md-1 col-xs-12 control-label js-gas-unit-price-label">燃气单价（元）</label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-gas-unit-price-input" type="text" maxlength="8">
																		</div>
																</div>

																<div class="form-group">
																		<label style="padding-left: 0px" class="col-md-2 col-xs-12 control-label js-ammeter-nbr-from-label"> </label>
																		<div class="col-md-2 col-xs-12">
																				<input readonly="readonly" class="form-control js-ammeter-nbr-from-input" type="text" maxlength="8">
																		</div>
																		<label style="padding-left: 0px" class="col-md-1 col-xs-12 control-label js-ammeter-nbr-to-label"> </label>
																		<div class="col-md-2 col-xs-12">
																				<input msg="请填写电表结束度数" class="form-control js-ammeter-nbr-to-input" datatype="Money2" type="text" maxlength="8">
																		</div>
																		<label class="col-md-1 col-xs-12 control-label js-ammeter-unit-price-label">电费单价（元）</label>
																		<div class="col-md-2 col-xs-12">
																				<input class="form-control js-ammeter-unit-price-input" type="text" maxlength="8">
																		</div>
																</div>

																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-nbr-attachment-label"> </label>
																		<div class="col-md-8 col-xs-12">
																				<c:choose>
																						<c:when test="${isMobile eq 'Y'}">
																								<div class="upload">
																										<a href="javascript:;" class="file" id="js-nbr-attachment-upload" onclick="jsNewCancelLeaseOrderDetail.uploadNbrAttachment()"> 选择照片上传 </a>
																										<div id="js-nbr-attachment-images"></div>
																								</div>
																						</c:when>
																						<c:otherwise>
																								<div class="js-nbr-attachment-upload" id="NBR_ATTACHMENT_UPLOAD"></div>
																								<div class="row"></div>
																								<div class="col-md-2"></div>
																						</c:otherwise>
																				</c:choose>
																		</div>
																</div>

																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-cancel-lease-reson-label"> </label>
																		<div class="col-md-3 col-xs-12">
																				<select class="form-control js-cancel-lease-reason-select" datatype="Require" msg="请选择退房原因">
																						<option value="">请选择...</option>
																				</select>
																		</div>
																		<label class="col-md-2 col-xs-12 control-label js-actual-cancel-lease-time-label">实际退租时间</label>
																		<div class="col-md-3 col-xs-12">
																				<input readonly type="text" class="form-control js-actual-cancel-lease-time-input" onFocus="WdatePicker({startDate:'%y-%M-%d %H:%m:%s',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true,readOnly:true})" />
																		</div>
																</div>

																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-promotion-explanation-label"></label>
																		<div class="col-md-8 col-xs-12">
																				<input class="form-control js-promotion-explanation-input" maxlength="60">
																		</div>
																		<div class="col-md-2 col-xs-12">
																				<b style="color: red; border: 0px" class="form-control">若有优惠活动，注明优惠金额</b>
																		</div>
																</div>

																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-key-recovery-label"> </label>
																		<div class="col-md-3 col-xs-12">
																				<input msg="请填写钥匙回收数量" class="form-control js-key-recovery-input" datatype="Integer" type="text" maxlength="2">
																		</div>
																		<label class="col-md-2 col-xs-12 control-label js-door-card-recovery-label"> </label>
																		<div class="col-md-3 col-xs-12">
																				<input msg="请填写门卡回收数量" class="form-control js-door-card-recovery-input" datatype="Integer" type="text" maxlength="2">
																		</div>
																		<div class="col-md-2"></div>
																</div>

																<div class="form-group">
																		<label class="col-md-2 col-xs-6 control-label js-other-recovery-label"></label>
																		<div class="col-md-8 col-xs-12">
																				<input id="OTHER_RECOVERY_INPUT" rows="6" maxlength="60" class="form-control js-other-recovery-input">
																		</div>
																		<div class="col-md-2"></div>
																</div>

																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-house-picture-label"> </label>
																		<div class="col-md-8 col-xs-12">
																				<c:choose>
																						<c:when test="${isMobile eq 'Y'}">
																								<div class="upload">
																										<a href="javascript:;" class="file" id="js-mobile-house-picture-upload" onclick="jsNewCancelLeaseOrderDetail.uploadhousePicture()"> 选择照片上传 </a>
																										<div id="js-house-picture-images"></div>
																								</div>
																						</c:when>
																						<c:otherwise>
																								<div class="js-house-picture-upload" id="HOUSE_PICTURE_UPLOAD"></div>
																								<div class="row"></div>
																								<div class="col-md-2"></div>
																						</c:otherwise>
																				</c:choose>
																		</div>
																</div>
																<div class="form-group">
																		<label class="col-md-2 col-xs-12 control-label js-house-inspection-comments-label"> </label>
																		<div class="col-md-8 col-xs-12">
																				<textarea onblur="jsNewCancelLeaseOrderDetail.commentsDeal(this.value)" rows="6" class="form-control js-house-inspection-comments-textarea"></textarea>
																		</div>
																		<div class="col-md-2"></div>
																</div>																
														</div>

														<div class="form-group hidden div-againAssignOrder">
																<label class="col-md-2 col-xs-12 control-label label-col">管家名称: </label>
																<div class="col-md-2 col-xs-12">
																		<select class="form-control" id="butlerId">
																				<option value="">请选择..</option>
																		</select>
																</div>
														</div>
												
														<div class="text-center">
																<button class="btn btn-info" id="butlerGetHomeBtn" onclick="jsNewCancelLeaseOrderDetail.butlerGetHome('N')" type="button">保存订单</button>
																<button class="btn btn-info" id="butlerGetBtn" onclick="jsNewCancelLeaseOrderDetail.butlerGetHome('Y')" type="button">提交订单</button>
																<button class="btn btn-info hidden" id="againAssignOrder" onclick="jsNewCancelLeaseOrderDetail.againAssignOrder()" type="button">重新指派</button>
																<button class="btn btn-info hidden" id="againAssignOrderBtn" onclick="jsNewCancelLeaseOrderDetail.againAssignOrderBtn()" type="button">提交订单</button>
																<button class="btn btn-info hidden" id="againAssignOrderCancelBtn" onclick="jsNewCancelLeaseOrderDetail.againAssignOrderCancelBtn()" type="button">取消</button>
																<button class="btn btn-info" id="houseCloseGetHomeBtn" onclick="jsNewCancelLeaseOrderDetail.houseClose()" type="button">关闭订单</button>
														</div>
												</form>
										</div>
								</div>
						</div>

						<div class="modal" id="addRefundItem">
								<div class="modal-dialog ">
										<div class="modal-content">
												<div class="modal-header" style="background: #65CEA7; text-align: center;">
														<button type="button" style="overflow: hidden;" class="close" data-dismiss="modal">
																<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
														</button>
														<h4 class="modal-title">新增退款项目</h4>
												</div>
												<div class="modal-body">
														<form class="form-horizontal adminex-form " role="form">
																<div class="tab-content">
																		<div class="tab-pane fade in active">
																				<div class="form-group col-md-12">
																						<label style="padding-left: 0px;" class="col-md-2 col-xs-12 control-label">费用名称</label>
																						<div style="padding-left: 0px;" class="col-md-4 col-xs-12">
																								<input maxlength="10" id="costLiquidationName" type="text" class="form-control" />
																						</div>
																						<label style="padding-left: 0px;" class="col-md-2 col-xs-12 control-label">费用类别</label>
																						<div style="padding-left: 0px;" class="col-md-4 col-xs-12">
																								<select class="form-control" id="costLiquidationType"></select>
																						</div>
																				</div>
																				<div class="form-group col-md-12">
																						<label style="padding-left: 0px;" class="col-md-2 col-xs-12 control-label">结算类型</label>
																						<div style="padding-left: 0px;" class="col-md-4 col-xs-12">
																								<select class="form-control" id="costLiquidationCostType">
																									<option value="1">应收</option>
																									<option value="0">应付</option>
																								</select>
																						</div>
																						<label style="padding-left: 0px;" class="col-md-2 col-xs-12 control-label">费用金额</label>
																						<div style="padding-left: 0px;" class="col-md-4 col-xs-12">
																								<input maxlength="20" id="costLiquidationCost" type="text" class="form-control" />
																						</div>
																				</div>
																				<div class="form-group col-md-12">
																						<label style="padding-left: 0px;" class="col-md-2 col-xs-12 control-label">费用说明</label>
																						<div style="padding-left: 0px;" class="col-md-10 col-xs-12">
																								<textarea id="costLiquidationDescription" rows="6" class="form-control"></textarea>
																						</div>
																				</div>
																		</div>
																</div>
														</form>
												</div>
												<div class="modal-footer" style="text-align: center;">
														<button type="button" style="background-color: #65CEA7; border-color: #65CEA7;" class="btn btn-primary" id="saveRefundItem">保存</button>
														<button type="button" style="background-color: #65CEA7; border-color: #65CEA7;" class="btn btn-primary" data-dismiss="modal">关闭</button>
												</div>
										</div>
								</div>
						</div>
						<!-- 费用清算 -->
						<div class="tab-pane fade" id="COST_LIQUIDATION">
								<div class="panel panel-default">
										<div class="panel-body">
												<div class="form-group">
														<label class="col-md-1 col-xs-6 control-label"> 
															<span style="font-weight: bold">结算款项</span>
														</label> 
														<label class="col-md-1 col-xs-6 control-label"> 
															<a id="costLiquidationBtn" onclick="jsNewCancelLeaseOrderDetail.createCost();">新增结算项</a>
														</label>
												</div>
												<div class="adv-table">
														<table id="wait2PayTable" class="display tablehover table table-bordered dataTable" cellspacing="0">
																<thead>
																		<tr>
																				<th></th>
																				<th></th>
																				<th>名称</th>
																				<th>类别</th>
																				<th>金额</th>
																				<th>备注</th>
																				<th>操作</th>
																		</tr>
																</thead>
														</table>
												</div>

												<div class="panel">
														<div class="panel-heading">
																<div class="form-group">
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span style="font-weight: bold">财务明细</span>
																		</label> 
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span id="closeDiv" style="color: #65CEA7;">收起</span>
																			<span id="openDiv" style="color: #65CEA7; display: none;">展开</span>
																		</label>
																</div>
														</div>
														<div id="financePayDiv" class="panel-collapse">
																<div class="panel-body">
																		<div class="adv-table">
																				<table id="financePayTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
																						<thead>
																								<tr>
																										<th></th>
																										<th></th>
																										<th>类型</th>
																										<th>应付金额</th>
																										<th>已付金额</th>
																										<th>优惠金额</th>
																										<th>开始时间</th>
																										<th>结束时间</th>
																										<th>支付状态</th>
																										<th>备注</th>
																										<th></th>
																								</tr>
																						</thead>
																				</table>
																		</div>
																</div>
														</div>
												</div>
												
												<div class="panel">
														<div class="panel-heading">
																<div class="form-group">
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span style="font-weight: bold">财务结算</span>
																		</label> 
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span id="closeFinanceDiv" style="color: #65CEA7;">收起</span>
																			<span id="openFinanceDiv" style="color: #65CEA7; display: none;">展开</span>
																		</label>
																</div>
														</div>
														<div id="financeDiv" class="panel-collapse">
																<div class="panel-body">
																		<div class="adv-table">
																				<table id="financeTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
																						<thead>
																								<tr>
																										<th></th>
																										<th></th>
																										<th>类型</th>
																										<th>租金</th>
																										<th>物业</th>
																										<th>水电煤代缴费</th>
																										<th>押金</th>
																										<th>服务费</th>
																										<th>其他</th>
																										<th>水费</th>
																										<th>电费</th>
																										<th>燃气费</th>
																										<th>垃圾费</th>
																										<th></th>
																								</tr>
																						</thead>
																				</table>
																				<input type="hidden" value="" id="waterCanKao" />
																				<input type="hidden" value="" id="eMeterCanKao" />
																				<input type="hidden" value="" id="cardgasCanKao" />
																				
																				<input type="hidden" value="" id="waterYingShou" />
																				<input type="hidden" value="" id="eMeterYingShou" />
																				<input type="hidden" value="" id="cardgasYingShou" />																				
																				<p style="color: red">应收：退租时间点之前应该收款多少</p>
																				<p style="color: red">实付：包含所有付款金额，不包含优惠金额</p>
																				<p style="color: red">优惠：退租时间点之前包含的优惠</p>
																				<p style="color: red">总计金额：应收-优惠金额-实付金额（水电煤代缴总计与实付一致）</p>																		
																				<p style="color: red">水电煤代缴费收多少退多少</p>																		
																				<p style="color: red">水电煤包含代缴费抵扣不计入优惠统计</p>																		
																		</div>
																</div>
														</div>
												</div>									

												<div class="form-group col-md-12 col-xs-12">
														<label class="col-md-2 col-xs-12 control-label js-costLiquidation-type-label"> </label>
														<div class="col-md-10 col-xs-12 js-costLiquidation-type-radio">
															<div class="radio-inline">
																<input class="costLiquidationRadioIn" disabled="disabled" type="radio" code="in" value="0" /> 收入
															</div>
															<div class="radio-inline">
																<input class="costLiquidationRadioOut" disabled="disabled" type="radio" code="out" value="1" /> 支出
															</div>
														</div>
												</div>

												<div class="form-group col-md-12 col-xs-12">
														<label class="col-md-2 col-xs-12 control-label js-costLiquidation-total-money-label"> </label>
														<div class="col-md-4 col-xs-12">
																<input type="number" dataType="Double" maxlength="7" class="form-control js-costLiquidation-total-money-input" />
														</div>
												</div>

												<div class="form-group col-md-12 col-xs-12">
														<label class="col-md-2 col-xs-12 control-label js-costLiquidation-comments-label"> </label>
														<div class="col-md-8 col-xs-12">
																<textarea onblur="jsNewCancelLeaseOrderDetail.commentsLiquidationDeal(this.value)" class="form-control js-costLiquidation-comments-textarea" rows="6"></textarea>
														</div>
														<div class="col-md-2"></div>
												</div>

												<div class="text-center">
														<button id="costLiquidationPassBtn" class="btn btn-info" onclick="jsNewCancelLeaseOrderDetail.rentalLiquidationPass();" type="button">提交订单</button>
														<button id="costLiquidationReturnBtn" class="btn btn-info" onclick="jsNewCancelLeaseOrderDetail.rentalLiquidationReturn();" type="button">打回订单至管家上门</button>
												</div>
										</div>
								</div>
						</div>

						<!-- 租务核算 -->
						<div class="tab-pane fade" id="RENTAL_ACCOUNT">
								<div class="panel panel-default">
										<div class="panel-body js-liquidation-rental-account">
												<div class="form-group">
														<label class="col-md-1 col-xs-6 control-label"> 
															<span style="font-weight: bold">结算款项</span>
														</label>
												</div>
												<div class="adv-table">
														<table id="wait2PayRentalTable" class="display tablehover table table-bordered dataTable" cellspacing="0">
																<thead>
																		<tr>
																				<th></th>
																				<th></th>
																				<th>名称</th>
																				<th>类别</th>
																				<th>金额</th>
																				<th>备注</th>
																				<th></th>
																		</tr>
																</thead>
														</table>
												</div>

												<div class="panel">
														<div class="panel-heading">
																<div class="form-group">
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span style="font-weight: bold">财务明细</span>
																		</label> 
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span id="closeRentalFinanceDiv" style="color: #65CEA7;">收起</span> 
																			<span id="openRentalFinanceDiv" style="color: #65CEA7; display: none;">展开</span>
																		</label>
																</div>
														</div>
														<div id="financeRentalPayDiv" class="panel-collapse">
																<div class="panel-body">
																		<div class="adv-table">
																				<table id="financePayRentalTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
																						<thead>
																								<tr>
																										<th></th>
																										<th></th>
																										<th>类型</th>
																										<th>应付金额</th>
																										<th>已付金额</th>
																										<th>优惠金额</th>
																										<th>开始时间</th>
																										<th>结束时间</th>
																										<th>支付状态</th>
																										<th>备注</th>
																										<th></th>
																								</tr>
																						</thead>
																				</table>
																		</div>
																</div>
														</div>
												</div>

												<div class="panel">
														<div class="panel-heading">
																<div class="form-group">
																		<label class="col-md-1 col-xs-6 control-label"> 
																			<span style="font-weight: bold">财务结算</span>
																		</label> 
																			<label class="col-md-1 col-xs-6 control-label"> 
																				<span id="closeRentalDiv" style="color: #65CEA7;">收起</span> 
																				<span id="openRentalDiv" style="color: #65CEA7; display: none;">展开</span>
																		</label>
																</div>
														</div>
														<div id="financeRentalDiv" class="panel-collapse">
																<div class="panel-body">
																		<div class="adv-table">
																				<table id="financeRentalTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
																						<thead>
																								<tr>
																										<th></th>
																										<th></th>
																										<th>类型</th>
																										<th>租金</th>
																										<th>物业</th>
																										<th>水电煤代缴费</th>
																										<th>押金</th>
																										<th>服务费</th>
																										<th>其他</th>
																										<th>水费</th>
																										<th>电费</th>
																										<th>燃气费</th>
																										<th>垃圾费</th>
																										<th></th>
																								</tr>
																						</thead>
																				</table>
																				<p style="color: red">应收：退租时间点之前应该收款多少</p>
																				<p style="color: red">实付：包含所有付款金额，不包含优惠金额</p>
																				<p style="color: red">优惠：退租时间点之前包含的优惠</p>
																				<p style="color: red">总计金额：应收-优惠金额-实付金额（水电煤代缴总计与实付一致）</p>
																				<p style="color: red">水电煤代缴费收多少退多少</p>																		
																				<p style="color: red">水电煤包含代缴费抵扣不计入优惠统计</p>																							
																		</div>
																</div>
														</div>
												</div>

												<div class="form-group col-md-12 col-xs-12">
														<label class="col-md-2 col-xs-12 control-label js-costLiquidation-type-label"> </label>
														<div class="col-md-10 col-xs-12 js-costLiquidation-type-radio">
															<div class="radio-inline">
																<input class="costLiquidationRadioIn" disabled="disabled" type="radio" code="in" value="0" /> 收入
															</div>
															<div class="radio-inline">
																<input class="costLiquidationRadioOut" disabled="disabled" type="radio" code="out" value="1" /> 支出
															</div>															
														</div>
												</div>

												<div class="form-group col-md-12 col-xs-12">
														<label class="col-md-2 col-xs-12 control-label js-costLiquidation-total-money-label"> </label>
														<div class="col-md-4 col-xs-12">
																<input type="number" dataType="Double" maxlength="7" class="form-control js-costLiquidation-total-money-input" />
														</div>
												</div>

												<div class="form-group col-md-12 col-xs-12">
														<label class="col-md-2 col-xs-12 control-label js-costLiquidation-comments-label"> </label>
														<div class="col-md-8 col-xs-12">
																<textarea class="form-control js-costLiquidation-comments-textarea" rows="6"></textarea>
														</div>
														<div class="col-md-2"></div>
												</div>
										</div>
										
										<div class="panel-body">
											<div class="form-group col-md-12 col-xs-12">
													<label class="col-md-2 col-xs-12 control-label js-rental-account-comments-label"> </label>
													<div class="col-md-8 col-xs-12">
															<textarea class="form-control js-rental-account-comments-textarea" rows="6"></textarea>
													</div>
													<div class="col-md-2"></div>
											</div>												
		
											<div class="text-center">
													<button id="rentalAccountPassBtn" class="btn btn-info" onclick="jsNewCancelLeaseOrderDetail.rentalAccountPass();" type="button">提交订单</button>
													<button id="rentalAccountReturnBtn" class="btn btn-info" onclick="jsNewCancelLeaseOrderDetail.rentalAccountReturn();" type="button">打回订单至费用清算</button>
											</div>	
										</div>									
										
								</div>
						</div>

						<!-- 市场部高管审批 -->
						<div class="tab-pane fade" id="MARKETING_EXECUTIVE_AUDIT">
								<div class="panel panel-default">
										<div class="panel-body">
												<form class="form-horizontal adminex-form" role="form" id="marketingExecutiveAuditForm">
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-4 control-label js-marketing-executive-audit-comments-label"></label>
																<div class="col-md-10 col-xs-12">
																		<textarea onblur="this.value = this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '')" class="js-marketing-executive-audit-comments-textarea form-control" rows="6" msg="请填写信息"></textarea>
																</div>
														</div>
														<div class="text-center">
																<button class="btn btn-info" id="marketingExecutiveAuditPassBtn" onclick="jsNewCancelLeaseOrderDetail.marketingExecutiveAuditPass()" type="button">通过</button>
																<button id="marketingExecutiveAuditReturnBtn" class="btn btn-info" onclick="jsNewCancelLeaseOrderDetail.marketingExecutiveAuditReturn();" type="button">打回</button>
														</div>
												</form>
										</div>
								</div>
						</div>

						<!-- 财务审批 -->
						<div class="tab-pane fade" id="FINANCE_AUDIT">
								<div class="panel panel-default">
										<div class="panel-body">
												<form class="form-horizontal adminex-form" role="form" id="financeAuditForm">
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-4 control-label js-finance-audit-comments-label"></label>
																<div class="col-md-10 col-xs-12">
																		<textarea onblur="this.value = this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '')" class="js-finance-audit-comments-textarea form-control" rows="6" msg="请填写信息"></textarea>
																</div>
														</div>
														<div class="text-center">
																<button class="btn btn-info" id="financeAuditPassBtn" onclick="jsNewCancelLeaseOrderDetail.financeAuditPass()" type="button">通过</button>
																<!-- <button id="financeAuditReturnBtn" class="btn btn-info" onclick="jsNewCancelLeaseOrderDetail.financeAuditReturn();" type="button">打回</button> -->
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
																								<a href="javascript:;" class="file" id="upurl-1" onclick="jsNewCancelLeaseOrderDetail.imageUrlPicture()"> 选择照片上传 </a>
																								<div id="js-judge-audit-image-url-mobile"></div>
																						</div>
																				</c:when>
																				<c:otherwise>
																						<div class="dropzone" id="upurl-1"></div>
																						<div class="row"></div>
																				</c:otherwise>
																		</c:choose>
																</div>
																<div class="col-sm-2"></div>
														</div>
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-4 control-label js-judge-audit-comments-label">用户评论</label>
																<div class="col-md-8 col-xs-12">
																		<textarea onblur="this.value = this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '')" disabled class="form-control js-judge-audit-comments-textarea" rows="6" msg=""></textarea>
																</div>
														</div>
														<div class="form-group col-md-12 col-xs-12">
																<label class="col-md-2 col-xs-4 control-label js-judge-audit-time-label">评价时间</label> <label class="col-md-2 col-xs-4 control-label js-judge-audit-time-label-value" style='text-align: left; white-space: nowrap;'></label>
														</div>
												</form>
										</div>
								</div>
						</div>

				</div>
		</div>
		</section>
		<section id="cd-timeline" class="cd-container" style="display: none"> <%--<div class="cd-timeline-block">--%> <%--<div class="cd-timeline-img cd-picture">--%> <%--<img src="/html/images/cd-icon-picture.svg" alt="Picture">--%> <%--</div>--%> <%--<div class="cd-timeline-content">--%> <%--<h2>HTML5+CSS3实现的响应式垂直时间轴</h2>--%> <%--<p>网页时间轴一般用于展示以时间为主线的事件，如企业网站常见的公司发展历程等。本文将给大家介绍一款基于HTML5和CSS3的漂亮的垂直时间轴，它可以响应页面布局，适用于HTML5开发的PC和移动手机WEB应用。</p>--%> <%--<a href="http://www.helloweba.com/view-blog-285.html" class="cd-read-more" target="_blank">阅读全文</a>--%> <%--<span class="cd-date">2015-01-06</span>--%> <%--</div>--%> <%--</div>--%> </section>
</body>

<!-- begin -->
<script type="text/javascript" src="/html/adminX/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/client/js/common.client.js"></script>
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
<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/newCancelLeaseOrderDetail.js"></script>
</html>
