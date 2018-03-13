<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>新增退租明细</title>
        <meta http-equiv="pragma" content="no-cache">
        <meta http-equiv="cache-control" content="no-cache">
        <meta http-equiv="expires" content="0">
        <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
        <meta http-equiv="description" content="This is my page">
        <meta name="viewport"
              content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0" />

        <link rel="stylesheet" type="text/css"
              href="/html/adminX/css/bootstrap.css" />
        <link rel="stylesheet" type="text/css"
              href="/client/css/normalize.min.css" />
        <link rel="stylesheet" type="text/css"
              href="/client/css/mission/create_task_detail.min.css" />
        <link rel="stylesheet" type="text/css"
              href="/client/css/preImage/lightbox.min.css" />
        <style type="text/css">
            .daterangepicker.dropdown-menu {
                max-width: none;
                z-index: 298910200;
            }
            .form-horizontal .form-group {
                margin-left: 0px !important;
            }
        </style>
    </head>
    <body>
        <c:choose>
            <c:when test="${param.isMobile eq '1'}">
                <input type="hidden" id="orderId" value="${param.orderId}">
                <input type="hidden" id="agree" value="${param.agree}">
                <input type="hidden" id="task_id" value="${param.task_id}">
                <div class="panel panel-primary">
                    <div class="body">
                        <form class="form-horizontal" id="form2">
                            <div class="weui_cells">
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p><b style="color: red">*</b>待缴费名称:</p>
                                    </div>
                                    <div class="weui_cell_ft" >
                                        <input type="text" id="name" msg='请填写待缴费名称'
                                               class="form-control" dataType="Require" maxlength="40">
                                    </div>
                                </div>
                            </div>
                            <div class="weui_cells">
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p><b style="color: red">*</b>待缴费类型:</p>
                                    </div>
                                    <div class="weui_cell_ft">
                                        <select id="costtype" dataType="Require" msg='请选择待缴类型'
                                                class="form-control"><option value="">请选择...</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="weui_cells" id="childhide1">
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p><b style="color: red">*</b>待缴费金额:</p>
                                    </div>
                                    <div class="weui_cell_ft">
                                        <input type="text" id="cost" msg='请填写正确的金额'
                                               class="form-control" dataType="Double" maxlength="10">
                                    </div>
                                </div>
                            </div>
                            <div class="weui_cells" style="display: none" >
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p>起始度数:</p>
                                    </div>
                                    <div class="weui_cell_ft">
                                        <input msg="请填写开始度数"  class="form-control" dataType="Double" id="starttime" type="text"  maxlength="6" value="0">
                                    </div>
                                </div>
                            </div>
                            <div class="weui_cells" style="display: none" >
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p>结束度数:</p>
                                    </div>
                                    <div class="weui_cell_ft">
                                        <input msg="请填写结束度数" class="form-control" dataType="Double" id="endtime" type="text" maxlength="6" value="0">
                                    </div>
                                </div>
                            </div>

                            <div class="weui_cells" style="display: none" >
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p>计费度数:</p>
                                    </div>
                                    <div class="weui_cell_ft">
                                        <input msg="请填写计费度数" class="form-control" dataType="Double" id="degree" type="text" maxlength="6" value="0">
                                    </div>
                                </div>
                            </div>

                            <div class="weui_cells">
                                <div class="weui_cell">
                                    <div class="weui_cell_bd weui_cell_primary">
                                        <p>缴费说明:</p>
                                    </div>
                                    <div class="weui_cell_ft">
                                        <textarea id="desc" maxlength="256" rows="6"
                                                  class="form-control"></textarea>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="form-group col-sm-12">&nbsp;</div>
                    <div class="form-group col-sm-12" style="text-align: center;">
                        <button class="btn btn-info" onclick="createCost.save();"
                                type="button">保存</button>
                    </div>
                </div>
                <script type="text/javascript" src="/client/js/jquery-1.10.2.js"></script>
                <script type="text/javascript" src="/client/js/common.client.js"></script>
                <script type="text/javascript" src="/client/js/common.js"></script>
                <script type="text/javascript" src="/html/pages/flow/js/order/miLeaseorder/createCost.js"></script>
            </c:when>
            <c:otherwise>
                <input type="hidden" id="js-work-order-id" name="work-order-id" value="${data}">
                <form class="form-horizontal" id="form2">
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-4 control-label"></label>
                        <div class="col-sm-8 col-xs-7"></div>
                        <div class="col-sm-2"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-4 control-label"><b
                                style="color: red">*</b>待缴费名称</label>
                        <div class="col-sm-8 col-xs-7">
                            <input class=" form-control" id="name" dataType="Require" type="text"
                                   msg='请填写待缴费名称' maxlength="40">
                        </div>
                        <div class="col-sm-2"></div>
                    </div>
                    <div class="form-group has-feedback" id="onameDiv">
                        <label class="col-sm-2 col-xs-4 control-label"><b style="color: red">*</b>待缴类型:</label>
                        <div class="col-sm-3 col-xs-7">
                            <select class="form-control" id="costtype" dataType="Require"
                                    msg='请选择待缴类型'><option value="">请选择...</option>
                            </select>
                        </div>
                        <label class="col-sm-2 col-xs-4 control-label"><b style="color: red">*</b>待缴金额:</label>
                        <div class="col-sm-3 col-xs-7">
                            <input class=" form-control" id="cost" msg='请填写正确的金额' type="text"
                                   dataType="Double" maxlength="10">
                        </div>
                        <div class="col-sm-2" style="color:red; font-size:13px;"></div>
                    </div>

                    <div class="form-group has-feedback" id="onameDiv" style="display: none">
                        <label class="col-sm-2 col-xs-4 control-label">起始度数:</label>
                        <div class="col-sm-3 col-xs-7">
                            <input class=" form-control" id="starttime" msg='请填写正确的数值' type="text"
                                   dataType="Money2" maxlength="6">
                        </div>
                        <label class="col-sm-2 col-xs-4 control-label">结束度数:</label>
                        <div class="col-sm-3 col-xs-7">
                            <input class=" form-control" id="endtime" msg='请填写正确的数值' type="text"
                                   dataType="Money2" maxlength="6">
                        </div>
                        <div class="col-sm-2" style="color:red; font-size:13px;"></div>
                    </div>

                    <div class="form-group" style="display: none">
                        <label class="col-sm-2 col-xs-4 control-label">计费度数:</label>
                        <div class="col-sm-3 col-xs-7">
                            <input class=" form-control" msg="请填写计费度数" id="degree" dataType="Money2" type="text"
                                   maxlength="6">
                        </div>
                        <div class="col-sm-7"></div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-xs-4 control-label"><b
                                style="color: red">*</b>缴费说明:</label>
                        <div class="col-sm-8 col-xs-7">
                            <textarea class="form-control" id="desc" maxlength="150" dataType="Require"
                                    msg='请填写缴费说明' rows="6"></textarea>
                        </div>
                        <div class="col-sm-2"></div>
                    </div>
                    <div class="form-group col-sm-12">&nbsp;</div>
                    <div class="form-group col-sm-12" style="text-align: center;">
                        <button class="btn btn-info" onclick="createCost.save();" type="button">保存</button>
                    </div>
                </form>
                <script type="text/javascript"
                src="/html/pages/flow/js/order/leaseorder/createCost.js"></script>
            </c:otherwise>
        </c:choose>
    </body>
</html>
