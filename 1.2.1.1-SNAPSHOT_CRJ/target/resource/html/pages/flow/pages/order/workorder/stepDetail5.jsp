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
<style type="text/css">
.form-horizontal .form-group {
	margin-left: 0px !important;
}
</style>
</head>
<body>
	<section class="panel">
	<div class="panel panel-primary">
		<div class="panel-heading">
			<h3 class="panel-title">处理信息</h3>
		</div>
		<div class="panel-body">
		<div class="form-group ">
					<label class="col-sm-2  control-label">备注：</label>
					<div class="col-sm-8">
						<textarea rows="6" id="flowremark"  maxlength="256"  class="form-control"></textarea>
					</div>
					<div class="col-sm-2"></div>
				</div>
				 <div class="form-group col-sm-12" id="changeDiv4" style="text-align: center;">
					<button class="btn btn-info" onclick="stepDetail7.save(2);" type="button">撤销支付</button>
				</div>
		</div>
	</div>
	</section>
	<c:if test="${isMobile eq '1'}"><script type="text/javascript" src="/html/pages/flow/js/order/miWorkorder/stepDetail6.js"></script> </c:if>
	<c:if test="${isMobile eq '0'}"><script type="text/javascript" src="/html/pages/flow/js/order/workorder/stepDetail6.js"></script> </c:if>
</body>
</html>
