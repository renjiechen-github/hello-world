var subOrder;
var workOrder;
var isMobile;
var isUpload = false; // 是否显示图片上传
var jsOrderUpdateDetail = {

	// 初始化数据信息
	init: function() {
		this.loadData();
	},

	// 订单数据处理
	loadData: function() {
		common.load.load();
		var orderid = common.getCookie('orderid');
		var param = {
			'workOrderId': orderid,
			'singleDetailFlag': 'Y'
		};
		common.ajax({
			url: common.root + '/workOrder/getWorkOrderDetail.do',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			encode: false,
			data: JSON.stringify(param),
			loadfun: function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {

					// 父工单信息
					workOrder = data.workOrder;

					// 子工单信息
					subOrder = data.subOrder;

					// 赋值父工单信息
					jsOrderUpdateDetail.generatorData();
				}
			}
		});
	},

	// 赋值父工单信息
	generatorData: function() {
		// 订单编号
		$('#order_code').html(workOrder.code);
		// 手机号
		$('#ouserMobile').html(workOrder.userPhone);
		// 用户名称
		$('#ouserName').html(workOrder.userName);
		// 预约时间
		$('#oserviceTime').val(workOrder.appointmentDate);
		// 订单状态
		$('#order_status').html(workOrder.subStateName);
		// 订单名称
		$('#orderName').val(workOrder.name);
		// 订单备注
		$('#odesc').text(workOrder.subComments);

		var imageUrl = subOrder.imageUrl;

		if (imageUrl != null && imageUrl != undefined) {
			isUpload = true;
			$(".js-div-upload").removeClass("hidden");
			// 订单图片
			var picurls = subOrder.imageUrl;
			if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined && picurls != 'undefined') {
				var pas = picurls.split(",");
				var paths = new Array();
				for (var int = 0; int < pas.length; int++) {
					if (int == 0) {
						paths.push({
							path: pas[int],
							first: 1
						});
					} else {
						paths.push({
							path: pas[int],
							first: 0
						});
					}
				}
				common.dropzone.init({
					id: '#upurl',
					defimg: paths,
					maxFiles: 10,
					clickEventOk: true
				});
			} else {
				common.dropzone.init({
					id: '#upurl',
					maxFiles: 10,
					clickEventOk: true
				});
			}

		}
	},

	// 保存数据
	saveData: function() {

		// 校验
		if (!Validator.Validate('form2')) {
			$("#orderadd_bnt").attr("disabled", false);
			return;
		}

		var pathe = '';
		if (isUpload) {
			// 获取图片地址
			pathe = jsOrderUpdateDetail.getPicPath('upurl');
		}
		common.alert({
			msg: "确定保存修改的数据吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					$("#orderadd_bnt").attr("disabled", true);
					common.load.load();
					var orderid = common.getCookie('orderid');
					var param = {
						'workOrderId': orderid,
						'comments': $("#odesc").val(),
						'appointmentDate': $("#oserviceTime").val(),
						'imageUrl': pathe,
						'orderName': $.trim($("#orderName").val())
					};
					common.ajax({
						url: common.root + '/workOrder/updateSubOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsOrderUpdateDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 获取图片地址
	getPicPath: function(id) {
		var pathe = "";
		var filepath = common.dropzone.getFiles('#' + id);
		var returnI = false;
		for (var i = 0; i < filepath.length; i++) {
			if (filepath[i].fisrt == 1) {
				pathe = filepath[i].path + ',' + pathe;
			} else {
				pathe += filepath[i].path + ",";
			}
			returnI = true;
		}
		if (returnI) {
			pathe = pathe.substring(0, pathe.length - 1);
		}
		return pathe;
	},

	// 关闭
	closeOpenedWin: function(isloadsucc, data) {
		if (isloadsucc) {
			if (data.state == 1) {
				common.alert({
					msg: '操作成功',
					fun: function() {
						window.opener = null;
						window.open("", "_self");
						window.close();
						if (window) {
							window.location.href = "about:blank";
						}
					}
				});
				if (isMobile == 'Y') {
					window.location.href = 'http://manager.room1000.com?TASK_SUBMIT_SUCCESS=1';
				} else {
					// common.closeWindow('repairOrderDetail', 3);
				}
			} else {
				common.alert({
					msg: common.msg.error
				});
			}
		}
	},

};

$(function() {
	jsOrderUpdateDetail.init();
});