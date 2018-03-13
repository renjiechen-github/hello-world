<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>任务详细信息查看</title>
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
    <link rel="stylesheet" href="/html/adminX/js/bootstrap-jquery-confirm/jquery-confirm.css"/>
    <link rel="stylesheet" href="/html/adminX/js/advanced-datatable/fixedColumns.dataTables.css"/>
    <link rel="stylesheet" href="/html/adminX/js/advanced-datatable/TableTools.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="/html/adminX/js/select2/select2.min.css"/>
    <link rel="stylesheet" type="text/css" media="all" href="/html/adminX/js/daterangepicker/daterangepicker-bs3.css"/>
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
  <input type="hidden" id="js-work-order-id" name="js-work-order-id" value="${id}">
  <input type="hidden" id="js-is-mobile" name="js-is-mobile" value="${isMobile}">
  <input type="hidden" id="js-staff-id" name="js-staff-id" value="${staffId}">
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
          <label class="col-md-2 col-xs-12 control-label">备注: </label>
          <div class="col-md-10 col-xs-12">
            <p class="form-control-static js-order-comments"></p>
          </div>
        </div>
      </form>
    </div>

    <div class="panel panel-default">
      <div class="panel-body">
        <div class="custom-tab turquoise-tab col-md-12 col-xs-12">
          <ul class="nav nav-tabs js-tab" role="tablist">
          <!--  <li role="presentation" class="col-md-2 col-xs-6"><a href="#HOUSE_RELEASE" class="text-center" aria-controls="HOUSE_RELEASE" role="tab" data-toggle="tab">房源释放</a></li> -->  
            <li role="presentation" class="col-md-2 col-xs-6"><a href="#TAKE_ORDER" class="text-center" aria-controls="TAKE_ORDER" role="tab" data-toggle="tab"> 管家接单 </a></li>
            <li role="presentation" class="col-md-2 col-xs-6"><a href="#BUTLER_GET_HOME" class="text-center" aria-controls="BUTLER_GET_HOME" role="tab" data-toggle="tab">管家上门 </a></li>
            <li role="presentation" class="col-md-2 col-xs-6"><a href="#RENTAL_ACCOUNT" class="text-center" aria-controls="RENTAL_ACCOUNT" role="tab" data-toggle="tab">租务核算 </a></li>
            <li role="presentation" class="col-md-2 col-xs-6"><a href="#MARKETING_EXECUTIVE_AUDIT" class="text-center" aria-controls="MARKETING_EXECUTIVE_AUDIT" role="tab" data-toggle="tab"> 高管审批 </a></li>
           <!--   <li role="presentation" class="col-md-2 col-xs-6"><a href="#FINANCE_AUDIT" class="text-center" aria-controls="FINANCE_AUDIT" role="tab" data-toggle="tab"> 财务审批 </a></li>-->
            <li role="presentation" class="col-md-2 col-xs-6"><a href="#USER_JUDGE" class="text-center" aria-controls="USER_JUDGE" role="tab" data-toggle="tab"> 用户评价 </a></li>
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
                  <textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" class="form-control js-comments-textarea" class="form-control" placeholder="请填写信息"></textarea>
                </div>
              </div>
              <div class="text-center">
                <button class="btn btn-info" id="takeOrderBtn" onclick="JsOwnerCancelLeaseOrderDetail.takeOrder()" type="button">接单</button>
                <button class="btn btn-info" id="reassignOrderBtn" onclick="JsOwnerCancelLeaseOrderDetail.reassignOrder();" type="button">重新派单</button>
                <button class="btn btn-info hidden" id="confirmReassignOrderBtn" onclick="JsOwnerCancelLeaseOrderDetail.confirmReassignOrder();" type="button">指派</button>
                <button class="btn btn-info hidden" id="cancelReassignOrderBtn" onclick="JsOwnerCancelLeaseOrderDetail.cancelReassignOrder();" type="button">返回</button>
              </div>
            </form>
          </div>
        </div>
      </div>
      
      <!-- 房源释放 
      <div class="tab-pane fade" id="HOUSE_RELEASE">
        <div class="panel panel-default">
          <div class="panel-body">
            <form class="form-horizontal adminex-form" role="form" id="houseReleaseForm">
              <div class="form-group col-md-12 col-xs-12">
                <label class="col-md-2 col-xs-4 control-label js-comments-release-label"></label>
                <div class="col-md-10 col-xs-12">
                  <textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" class="form-control js-comments-release-textarea" class="form-control" placeholder="请填写信息"></textarea>
                </div>
              </div>
              <div class="text-center">
                <button class="btn btn-info" id="houseReleaseBtn" onclick="JsOwnerCancelLeaseOrderDetail.houseRelease()" type="button">提交</button>
                <button class="btn btn-info" id="houseCloseBtn" onclick="JsOwnerCancelLeaseOrderDetail.houseClose()" type="button">关闭</button>
              </div>
            </form>
          </div>
        </div>
      </div>-->

      <!-- 管家上门 -->
      <div class="tab-pane fade" id="BUTLER_GET_HOME">
        <div class="panel panel-default">
          <div class="panel-body">
            <form class="form-horizontal adminex-form" role="form" id="butlerGetHomeForm">
              <div class="form-group">
                <label class="col-md-2 col-xs-12 control-label js-alipay-label"></label>
                <div class="col-md-3 col-xs-12">
                  <input class="form-control js-alipay-input" type="text" maxlength="30" class=" form-control">
                </div>
                <label class="col-md-2 col-xs-12 control-label js-bank-card-nbr-label"></label>
                <div class="col-md-3 col-xs-12">
                  <input class="form-control js-bank-card-nbr-input" type="text" class=" form-control" maxlength="21">
                </div>
                <div class="col-md-2 col-xs-12">
                  <b style="color: red; border: 0px" class="form-control"> *支付宝或银行卡任选其一 </b>
                </div>
              </div>

              <div class="form-group">
                <label class="col-md-2 col-xs-12 control-label js-bank-address-label"></label>
                <div class="col-md-8 col-xs-12">
                  <input class="form-control js-bank-adress-input" type="text" maxlength="30" class="form-control">
                </div>
                <div class="col-md-2"></div>
              </div>

              <div class="form-group">
                <label class="col-md-2 col-xs-12 control-label js-water-nbr-from-label"> </label>
                <div class="col-md-3 col-xs-12">
                  <input msg="请填写水表起始度数" class="form-control js-water-nbr-from-input" datatype="Money2" type="text" maxlength="8">
                </div>

                <label class="col-md-2 col-xs-12 control-label js-water-nbr-to-label"> </label>
                <div class="col-md-3 col-xs-12">
                  <input msg="请填写水表结束度数" class="form-control js-water-nbr-to-input" datatype="Money2" type="text" maxlength="8">
                </div>
                <div class="col-md-2"></div>
              </div>

              <div class="form-group">
                <label class="col-md-2 col-xs-12 control-label js-gas-nbr-from-label"> </label>
                <div class="col-md-3 col-xs-12">
                  <input class="form-control js-gas-nbr-from-input" type="text" maxlength="8">
                </div>

                <label class="col-md-2 col-xs-12 control-label js-gas-nbr-to-label"> </label>
                <div class="col-md-3 col-xs-12">
                  <input class="form-control js-gas-nbr-to-input" type="text" maxlength="8">
                </div>
                <div class="col-md-2"></div>
              </div>

              <div class="form-group">
                <label class="col-md-2 col-xs-12 control-label js-ammeter-nbr-from-label"> </label>
                <div class="col-md-3 col-xs-12">
                  <input msg="请填写电表起始度数" class="form-control js-ammeter-nbr-from-input" datatype="Money2" type="text" maxlength="8">
                </div>
                <label class="col-md-2 col-xs-12 control-label js-ammeter-nbr-to-label"> </label>
                <div class="col-md-3 col-xs-12">
                  <input msg="请填写电表结束度数" class="form-control js-ammeter-nbr-to-input" datatype="Money2" type="text" maxlength="8">
                </div>
                <div class="col-md-2"></div>
              </div>

              <div class="form-group">
                <label class="col-md-2 col-xs-12 control-label js-nbr-attachment-label"> </label>
                <div class="col-md-8 col-xs-12">
                  <c:choose>
                    <c:when test="${isMobile eq 'Y'}">
                      <div class="upload">
                        <a href="javascript:;" class="file" id="js-nbr-attachment-upload" onclick="JsOwnerCancelLeaseOrderDetail.uploadNbrAttachment()"> 选择照片上传 </a>
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
                <div class="col-md-8 col-xs-12">
                  <select class="form-control js-cancel-lease-reason-select" datatype="Require" msg="请选择退房原因">
                    <option value="">请选择...</option>
                  </select>
                </div>
                <div class="col-md-2"></div>
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
                        <a href="javascript:;" class="file" id="js-mobile-house-picture-upload" onclick="JsOwnerCancelLeaseOrderDetail.uploadhousePicture()"> 选择照片上传 </a>
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
                  <textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" datatype="Require" msg="请填写验房说明" class="form-control js-house-inspection-comments-textarea"></textarea>
                </div>
                <div class="col-md-2"></div>
              </div>

              <div class="text-center">
                <button class="btn btn-info" id="butlerGetHomeBtn" onclick="JsOwnerCancelLeaseOrderDetail.butlerGetHome('N')" type="button">保存</button>
                <button class="btn btn-info" id="butlerGetBtn" onclick="JsOwnerCancelLeaseOrderDetail.butlerGetHome('Y')" type="button">提交</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- 租务核算 -->
      <div class="tab-pane fade" id="RENTAL_ACCOUNT">
        <div class="panel panel-default">
          <div class="panel-body">
            <div class="form-group">
              <label class="col-md-2 col-xs-6 control-label"> <a id="createCostBtn" onclick="JsOwnerCancelLeaseOrderDetail.createCost();">添加待缴费细目</a>
              </label>
              <div class="col-md-8 col-xs-12">
                <span class="form-control" style="border: 0"></span>
              </div>
              <div class="col-md-2"></div>
            </div>

            <div class="adv-table">
              <table id="wait2PayTable" class="display tablehover table table-bordered dataTable" cellspacing="0">
                <thead>
                  <tr>
                    <th></th>
                    <th></th>
                    <th>待缴名称</th>
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

            <div>
              <label class="col-md-4 col-xs-6 control-label">金额总计：</label>
              <div class="col-md-8 col-xs-6">
                <span class="form-control" style="border: 0" id="costCountp"></span>
              </div>
            </div>

            <div id="accordion">
              <div class="panel">
                <div class="panel-heading">
                  <h4 class="panel-title">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#financeIncomeDiv"> <font style="color: green">财务收入信息查看：</font>
                    </a>
                  </h4>
                </div>
                <div id="financeIncomeDiv" class="panel-collapse collapse in">
                  <div class="panel-body">
                    <div class="adv-table">
                      <table id="financeIncomeTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
                        <thead>
                          <tr>
                            <th></th>
                            <th></th>
                            <th>名称</th>
                            <th>类型</th>
                            <th>金额</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>状态</th>
                            <th></th>
                          </tr>
                        </thead>
                      </table>
                    </div>
                  </div>
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#financeIncomeDiv">收起</a>
                </div>

              </div>

              <div class="panel">
                <div class="panel-heading">
                  <h4 class="panel-title">
                    <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#financePayDiv"> <font style="color: red">财务支出信息查看：</font>
                    </a>
                  </h4>
                </div>
                <div id="financePayDiv" class="panel-collapse collapse ">
                  <div class="panel-body">
                    <div class="adv-table">
                      <table id="financePayTable" class="display tablehover table table-bordered dataTable" cellspacing="0" width="100%">
                        <thead>
                          <tr>
                            <th></th>
                            <th></th>
                            <th>名称</th>
                            <th>类型</th>
                            <th>金额</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>状态</th>
                            <th></th>
                          </tr>
                        </thead>
                      </table>
                    </div>
                  </div>
                  <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#financePayDiv">收起</a>
                </div>
              </div>
            </div>

            <div class="form-group col-md-12 col-xs-12">
              <label class="col-md-2 col-xs-12 control-label js-finance-type-label"> </label>
              <div class="col-md-10 col-xs-12 js-finance-type-radio"></div>
            </div>

            <div class="form-group col-md-12 col-xs-12">
              <label class="col-md-2 col-xs-12 control-label js-total-money-label"> </label>
              <div class="col-md-4 col-xs-12">
                <input type="number" dataType="Double" maxlength="7" class="form-control js-total-money-input" />
              </div>
              <div class="col-md-6">已付租金：<span id="allCost"></span>&nbsp;月租：<span id="cost_a"></span>&nbsp;入住天数：<span id="day"></span>天 &nbsp;<span id="cost_name"></span>：<span id="nowcoat"></span></div>
            </div>

            <div class="form-group col-md-12 col-xs-12">
              <label class="col-md-2 col-xs-12 control-label js-rental-account-comments-label"> </label>
              <div class="col-md-8 col-xs-12">
                <textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" class="form-control js-rental-account-comments-textarea"></textarea>
              </div>
              <div class="col-md-2"></div>
            </div>

            <div class="text-center">
              <button id="rentalAccountPassBtn" class="btn btn-info" onclick="JsOwnerCancelLeaseOrderDetail.rentalAccountPass();" type="button">保存</button>
              <button id="rentalAccountReturnBtn" class="btn btn-info" onclick="JsOwnerCancelLeaseOrderDetail.rentalAccountReturn();" type="button">打回</button>
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
                  <textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" class="js-marketing-executive-audit-comments-textarea form-control" msg="请填写信息"></textarea>
                </div>
              </div>
              <div class="text-center">
                <button class="btn btn-info" id="marketingExecutiveAuditPassBtn" onclick="JsOwnerCancelLeaseOrderDetail.marketingExecutiveAuditPass()" type="button">通过</button>
                <button id="marketingExecutiveAuditReturnBtn" class="btn btn-info" onclick="JsOwnerCancelLeaseOrderDetail.marketingExecutiveAuditReturn();" type="button">打回</button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <!-- 财务审批 
      <div class="tab-pane fade" id="FINANCE_AUDIT">
        <div class="panel panel-default">
          <div class="panel-body">
            <form class="form-horizontal adminex-form" role="form" id="financeAuditForm">
              <div class="form-group col-md-12 col-xs-12">
                <label class="col-md-2 col-xs-4 control-label js-finance-audit-comments-label"></label>
                <div class="col-md-10 col-xs-12">
                  <textarea onblur="this.value=this.value.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g,'')" rows="6" class="form-control js-finance-audit-comments-textarea" class="form-control" msg="请填写信息"></textarea>
                </div>
              </div>
              <div class="text-center">
                <button class="btn btn-info" id="financeAuditPassBtn" onclick="JsOwnerCancelLeaseOrderDetail.financeAuditPass()" type="button">通过</button>
              
              </div>
            </form>
          </div>
        </div>
      </div>-->
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
<script type="text/javascript" src="/html/pages/flow/js/order/leaseorder/ownerCancelLeaseOrderDetail.js"></script>
</html>
