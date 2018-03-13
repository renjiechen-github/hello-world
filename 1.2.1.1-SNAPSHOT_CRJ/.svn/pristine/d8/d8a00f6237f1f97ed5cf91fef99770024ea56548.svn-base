<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>全民经纪人审批</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--begin-->
<link href="/html/adminX/js/iCheck/skins/minimal/minimal.css"
	rel="stylesheet">
<link href="/html/adminX/js/iCheck/skins/square/square.css"
	rel="stylesheet">
<link href="/html/adminX/js/iCheck/skins/square/red.css"
	rel="stylesheet">
<link href="/html/adminX/js/iCheck/skins/square/blue.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="/html/adminX/js/bootstrap-jquery-confirm/jquery-confirm.css" />
<link rel="stylesheet"
	href="/html/adminX/js/advanced-datatable/fixedColumns.dataTables.css" />
<link rel="stylesheet"
	href="/html/adminX/js/advanced-datatable/TableTools.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="/html/adminX/js/select2/select2.css" />
<link rel="stylesheet" type="text/css" media="all"
	href="/html/adminX/js/daterangepicker/daterangepicker-bs3.css" />
<link rel="stylesheet" type="text/css"
	href="/html/adminX/js/bootstrap-datepicker/css/datepicker-custom.css" />
<link rel="stylesheet" type="text/css"
	href="/html/adminX/js/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" />
<link rel="stylesheet" type="text/css"
	href="/client/css/uploadImage/uploadImage.css" />
<link href="/html/adminX/css/clndr.css" rel="stylesheet">
<link rel="stylesheet" href="/html/adminX/js/morris-chart/morris.css">
<link href="/html/adminX/css/Tendina.css" rel="stylesheet">
<link href="/html/adminX/css/animate.css" rel="stylesheet">
<link href="/html/adminX/css/style.css" rel="stylesheet">
<link href="/html/adminX/css/style-responsive.css" rel="stylesheet">
<link href="/html/adminX/js/advanced-datatable/css/demo_page.css"
	rel="stylesheet" />
<link href="/html/adminX/js/advanced-datatable/css/demo_table.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="/html/adminX/js/data-tables/DT_bootstrap.css" />

<link rel="stylesheet" href="/html/adminX/css/but.css" />
<link rel="stylesheet"
	href="/html/adminX/js/dropzone/css/dropzone.css">
<!-- end -->
<style type="text/css">
.form-horizontal .form-group {
	margin-left: 0px !important;
}

.border_0 {
	border: 0px;
}

.panel-primary>.panel-heading {
	background-color: #e0e1e7;
	border-color: #e0e1e7 !important;
	color: #898989;
}
</style>
</head>
<body>
	<input type="hidden" id="js-work-order-id" name="js-work-order-id" value="${id}">
	<input type="hidden" id="js-is-mobile" name="js-is-mobile" value="${isMobile}">
    <input type="hidden" id="js-staff-id" name="js-staff-id" value="${staffId}">
	
	<section class="panel panel-default">
	<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#baseInfo" data-toggle="tab">基本信息</a></li>
			<li><a href="#agreeInfo" data-toggle="tab">房间内部家具</a></li> 
			<li><a href="#ggInfo" data-toggle="tab">公共区域物品</a></li> 
	</ul>
	<div class="panel-body">
		<form id="form2" class="form-horizontal adminex-form" role="form">
			<div id="myTabContent" class="tab-content">
				<div class="row tab-pane fade in active" id="baseInfo">
				<div class="col-md-12 col-xs-12">
					<div class="panel" data-collapsed="0">
						<div class="panel-body">
							<div class="row">
							   <div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b style="color: red">*</b> 签约经纪人：
									</label>
									<div class="col-md-8 col-xs-12">
										<span id="broker" class="form-control border_0" style="color:red"></span>
									</div>
							    </div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label">所属房源：</label>
									<div class="col-md-4 col-xs-12">
										<span id="house_name" class="form-control border_0"></span>
										 <input id="rank_id" name="rank_id" type="hidden" />
										 <input id="houseId" name="houseId" type="hidden" /> 
										 <input id="id" name="id" type="hidden" />
										 <input id="address" name="address" type="hidden" /> 
										 <input id="roomcnt" name="roomcnt" type="hidden" />
										 <input id="rentroomcnt" name="rentroomcnt" type="hidden" />
										 <input id="area" name="area" type="hidden" />
										 <input type="hidden" value="" id="userId" name="userId"  />
										 <input type="hidden" value="" id="rank_code" name="rank_code"  />
									</div>
									<label class="col-md-2 col-xs-12 control-label">合约类型：</label>
									<div class="col-md-4 col-xs-12">
										<span id="agreement_type" class="form-control border_0 "></span>
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 合约名称：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="agreement_name" name="agreement_name"
											class="form-control" dataType="Custom" regexp="^([\u4e00-\u9fa5A-Za-z0-9-_]{1,})$"
											msg="请输入合法的合约名称！" placeholder="请输入合约名称" type="text" />
									</div>

									<label class="col-md-2 col-xs-12 control-label" style="display: none;">
										<b style="color: red">*</b> 合约编号：
									</label>
									<div class="col-md-4 col-xs-12" style="display: none;">
										<input id="agreement_code" placeholder="请输入合约编号"
											name="agreement_code" maxlength="10" class="form-control"
											type="text" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 审核管家：
									</label>
									<div class="col-md-4 col-xs-12">
										<select id="manageId" name="manageId" dataType="Require"
											msg="请选择签约管家！" class="form-control selectControl"
											msg="请选择管家"><option value="">请选择...</option>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 租客号码：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="mobile" name="mobile" msg="请填写11位数字格式租客号码"
											dataType="Mobile" placeholder="请输入数字格式租客号码" maxlength="11"
											class="form-control" type="text" />
									</div>

									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 租客姓名：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="name" name="name" dataType="Require"
											msg="请输入租客姓名！" maxlength="5" placeholder="请输入租客姓名"
											class="form-control" type="text" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> <select
										onchange="brokerOrderDetail.selectCertI(this)"><option
												value="0">身份证号</option>
											<option value="1">其他证件</option></select>：
									</label>
									<div class="col-md-4 col-xs-12">
										<input type="text" name="certificateno" dataType="IdCard"
											msg="请输入合法的产权人身份证号码！" id="certificateno"
											placeholder="请输入身份证号码" value=""
											class="form-control inputChange2" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 邮箱地址：
									</label>
									<div class="col-md-4 col-xs-12">
										<input type="text" name="email" dataType="Email"
											msg="请输入合法的email" id="email" placeholder="请输入email"
											value="" class="form-control inputChange2" />
									</div>
								</div>
								<div class="form-group">
								    <label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 租期：
									</label>
									<div class="col-md-4 col-xs-12" >
										<select id="rankDate" class="form-control selectControl"
											dataType="Require" name="rankDate" msg="请选择租期"><option
												value="">请选择...</option>
										</select>
									</div>
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 付款方式：
									</label>
									<div class="col-md-4 col-xs-12">
										<select id="payType" name="payType" dataType="Require"
											msg="请选择付款方式！" class="form-control selectControl"><option
												value="">请选择...</option>
										</select>
									</div>
									
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 合约租金：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="rankPrice" name="rankPrice" dataType="Double2"
											maxlength="8" msg="请填写合约租金！" class="form-control"
											type="number" />
										<input type="hidden" value="" name="rent_deposit" id="rent_deposit" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 生效时间：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="validateDate" readonly="readonly"
											dataType="Require" msg="请选择生效时间！" name="validateDate"
											class="form-control" type="text" />
									</div>
									
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 服务费：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="serviceMonery" name="serviceMonery" dataType="Double2"
											maxlength="8" msg="请填写合约服务费！" class="form-control inputChange2"
											type="number" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 物业费：
									</label>
									<div class="col-md-4 col-xs-12">
										<input id="propertyMonery" name="propertyMonery"
											dataType="Double2" maxlength="8" msg="请填写物业费！"
											class="form-control inputChange2" type="number" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>电卡帐号：</label>
									<div class="col-md-4 col-xs-12">
										<input type="text" name="cardelectric" dataType="Require"
											msg="请输入电卡帐号！" placeholder="请输入电卡帐号" id="cardelectric"
											value="" class="form-control inputChange2" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>电表总值：</label>
									<div class="col-md-4 col-xs-12">
										<input type="number" name="eMeter" dataType="Double2"
											msg="请输入电表总值！" placeholder="请输入电表总值" id="eMeter" value=""
											class="form-control inputChange2" />
									</div>
								</div>

								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>电表峰值：</label>
									<div class="col-md-4 col-xs-12">
										<input type="number" name="eMeterH" dataType="Double2"
											msg="请输入电表峰值！" placeholder="请输入电表峰值" id="eMeterH" value=""
											class="form-control inputChange2" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>电表谷值：</label>
									<div class="col-md-4 col-xs-12">
										<input type="number" name="eMeterL" dataType="Double2"
											msg="请输入电表谷值！" placeholder="请输入电表谷值" id="eMeterL" value=""
											class="form-control inputChange2" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>水卡帐号：</label>
									<div class="col-md-4 col-xs-12">
										<input type="text" name="cardWarter" dataType="Double2"
											msg="请输入水卡帐号！" placeholder="请输入水卡帐号" id="cardWarter"
											value="" class="form-control inputChange2" />
									</div>
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>水表读数：</label>
									<div class="col-md-4 col-xs-12">
										<input type="number" name="warterDegrees" dataType="Double2"
											msg="请输入水表读数！" placeholder="请输入水表读数" id="warterDegrees"
											value="" class="form-control inputChange2" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label">燃气帐号：</label>
									<div class="col-md-4 col-xs-12">
										<input type="text" name="cardgas" placeholder="请输入燃气帐号"
											id="cardgas" value="" class="form-control inputChange2" />
									</div>
									<label class="col-md-2 col-xs-12 control-label">燃气读数：</label>
									<div class="col-md-4 col-xs-12">
										<input type="number" name="gasDegrees" placeholder="请输入燃气读数"
											id="gasDegrees" value="" class="form-control inputChange2" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label">  <b
										style="color: red">*</b>支付类型：</label>
									<div class="col-md-4 col-xs-12">
										<select id="payway" name="payway" class="form-control selectControl">
											<option value="0">支付宝</option>
											<option value="1">微信</option>
											<option value="2">银行卡</option>
										</select>
									</div>
									<label class="col-md-2 col-xs-12 control-label"><b
										style="color: red">*</b>水电煤押金：</label>
									<div class="col-md-4 col-xs-12">
										<input type="number" name="deposit" dataType="Double2"
											msg="请输入水电煤押金！" placeholder="请输入水电煤押金"
											id="deposit" value="" class="form-control inputChange2" />
									</div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label"> <b
										style="color: red">*</b> 租客身份证上传：
									</label>
									<div class="col-md-8 col-xs-12">
										<c:choose>
											<c:when test="${isMobile eq 'Y'}">
												<div class="upload">
													<a href="javascript:;" class="file" id="myAwesomeDropzone"
														onclick="brokerOrderDetail.uploadNbrAttachment()">
														选择照片上传 </a>
													<div id="myAwesomeDropzone-view"></div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="dropzone" id="myAwesomeDropzone"></div>
										        <div class="row"></div>
											</c:otherwise>
										</c:choose>
										
										<input type="hidden" id="picPath" name="picPath" />
									</div>
									<div class="col-md-2 col-xs-12"></div>
								</div>
								<div class="form-group">
									<label class="col-md-2 col-xs-12 control-label">合约描述：</label>
									<div class="col-md-8 col-xs-12">
										<textarea rows="6" class="form-control" id="agreementDes"
											name="agreementDes"></textarea>
									</div>
									<div class="col-md-2"></div>
								</div>
							</div>
						</div>
						</div>
					</div>
				</div>
				<div id="agreeInfo" class="row tab-pane fade">
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">双人床：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" require="false" dataType="Integer2" msg="请填写数字格式" placeholder="请输入双人床数量"
								id="a0" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">矮柜：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入矮柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a1" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">空调：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入空调数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a2" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">单人床：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" placeholder="请输入单人床数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a3" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">茶几：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入茶几数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a4" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">彩电：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入彩电数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a5" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">床垫：</label>
						<div class="col-md-2">
							<input type="text" name="innerCnt" placeholder="请输入床垫数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a6" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">柜：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a7" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">遥控器：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入遥控器数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a8" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">床头柜：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" placeholder="请输入床头柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a9" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">吊橱：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入吊橱数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a10" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">沙发：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入沙发数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a11" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">衣柜：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" placeholder="请输入衣柜柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a12" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">台灯：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入台灯数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a13" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">电视柜：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入电视柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a14" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">书桌：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" placeholder="请输入书桌数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a15" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">落地灯：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入落地灯数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a16" value="" class="form-control inputChange2" /> 
						</div>
						<label class="col-md-2 col-xs-12 control-label">沙发床：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入沙发床数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a17" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">椅子：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" placeholder="请输入书桌数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a18" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">吊灯：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入吊灯数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a19" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">化妆台：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入化妆台数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a20" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">窗帘：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="innerCnt" placeholder="请输入窗帘数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a21" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">壁灯：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入壁灯数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a22" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">化妆椅：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="innerCnt" placeholder="请输入化妆椅数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="a23" value="" class="form-control inputChange2" />
						</div>
					</div>
				</div>
				<div id="ggInfo" class="row tab-pane fade">
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">油烟机：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="outCnt" placeholder="请输入油烟机数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b0" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">餐桌：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="outCnt" placeholder="请输入餐桌数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b1" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">燃气灶：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="outCnt" placeholder="请输入燃气灶数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b2" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">餐椅：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="outCnt" placeholder="请输入餐椅数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b3" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">电冰箱：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="outCnt" placeholder="请输入电冰箱数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b4" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">洗衣机：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="outCnt" placeholder="请输入洗衣机数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b5" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">热水器：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="outCnt" placeholder="请输入热水器数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b6" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">鞋柜：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="outCnt" placeholder="请输入鞋柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b7" value="" class="form-control inputChange2" />
						</div>
						<label class="col-md-2 col-xs-12 control-label">微波炉：</label>
						<div class="col-md-2 col-xs-12">
							<input type="number" name="outCnt" placeholder="请输入微波炉数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b8" value="" class="form-control inputChange2" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-md-2 col-xs-12 control-label">空调：</label>
						<div class="col-md-2 col-xs-12">
							<input type="text" name="outCnt" placeholder="请输入床头柜数量" require="false" dataType="Integer2" msg="请填写数字格式"
								id="b9" value="" class="form-control inputChange2" />
						</div>
					</div>
				</div>
			</div>
			<div class="form-group">
			<label class="col-md-2 col-xs-12 control-label">审批备注：</label>
			<div class="col-md-8 col-xs-12">
			<textarea rows="6" class="form-control" id="comments" ></textarea>
			</div>
               <div class="col-md-2 col-xs-12"></div>
		</div>
		<div class="text-center hidden" id="hidden_but">
		<button class="btn btn-info"  onclick="brokerOrderDetail.approve('Y')" type="button">通过</button>
		<button class="btn btn-info" onclick="brokerOrderDetail.approve('N')" type="button">拒绝</button>
	</div>
		</form>
	</div>
		<form id="form3" action="" method="post" target="_blank">
			<input type="hidden" name="content" id="content" value="" />
			<input type="hidden" name="signed" id="signed" value="" /> 
			<input type="hidden" name="key" id="key" value="" /> 
			<input type="hidden" name="apiid" id="apiid" value="" />
		</form>
	</section>
</body>
<!-- begin -->
<c:if test="${isMobile eq 'Y'}">
	<script type="text/javascript"
		src="/html/adminX/js/jquery-1.10.2.js"></script>
	<script type="text/javascript" src="/client/js/common.client.js"></script>
</c:if>
<script type="text/javascript"
	src="/html/adminX/js/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/dropzone/dropzone.js"></script>
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
<script src="/html/adminX/js/morris-chart/raphael-min.js"></script>
<script src="/html/adminX/js/calendar/clndr.js"></script>
<script src="/html/adminX/js/calendar/moment-2.2.1.js"></script>
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.2/underscore-min.js"></script>
<script src="/html/adminX/js/encrypt/sha1.js"></script>
<script src="/html/adminX/js/hdialog/js/jquery.hDialog.js"></script>
<script src="/html/adminX/js/bootstrap-jquery-confirm/jquery-confirm.js"></script>
<script src="/html/adminX/js/jquery.jcryption.3.1.0.js"></script>
<script src="/html/adminX/js/cookies/jquery.cookie.js"></script>
<script src="/html/adminX/js/jQuery.md5.js"></script>
<script src="/html/adminX/js/layer/layer.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/advanced-datatable/js/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/daterangepicker/moment.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/daterangepicker/daterangepicker.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/data-tables/DT_bootstrap.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/advanced-datatable/table.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/advanced-datatable/tableTools.js"></script>
<script src="/html/adminX/js/Tendina.js"></script>
<script src="/html/adminX/js/pin/jquery.pin.js"></script>
<script src="/html/adminX/js/common/common.js"></script>
<script type="text/javascript"
	src="/html/adminX/js/select2/select2.js"></script>
<link href="/html/adminX/js/table/table.css" rel="stylesheet">
<script src="/html/adminX/js/table/table.js"></script>
<script src="/html/adminX/js/jquery.form.js"></script>
<!-- end -->
<script type="text/javascript"
	src="/html/pages/flow/js/order/leaseorder/subOrderStateDef.js"></script>
<script type="text/javascript"
	src="/html/pages/flow/js/order/leaseorder/brokerOrderDetail.js"></script>
</html>
