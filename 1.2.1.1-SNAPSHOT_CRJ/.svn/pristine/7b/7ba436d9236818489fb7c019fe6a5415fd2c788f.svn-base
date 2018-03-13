var agreeId = ""; 
var workOrderId = "";
var orderadd = {
	// 页面初始化操作
	init : function() {
		// 加载结算类型
		// orderadd.initType();
		// 添加处理事件
		orderadd.addEvent();

		// common.ajax({
		// 	url : common.root + '/cancelLease/startCancelLeaseOrderProcess.do',
		// 	dataType : 'json',
		// 	loadfun : function(isloadsucc, data) {
		// 		if (data.state == 1) {
		// 			workOrderId = data.workOrderId;
		// 		} else {
		// 			common.alert({
		// 					msg : '流程启动失败，请重新进入该页面'
		// 				});
		// 		}
		// 	}
		// });
	},
	addEvent : function() {
		$('#orderadd_bnt').click(function() {
			orderadd.save();
		});

		$('#ouserMobile').blur(function() {
			$('#ouserName').val(orderadd.getUserName());
		});
	},
	// initType : function() {
	// 	common.loadItem('EVICTION.TYPE', function(json) {
	// 		var html = "";
	// 		for (var i = 0; i < json.length; i++) {
	// 			html += '<option  value="' + json[i].item_id + '" >'
	// 					+ json[i].item_name + '</option>';
	// 		}
	// 		$('#childtype').append(html);
	// 	});
	// },
	save : function() {
		if (!Validator.Validate('form2')) {
			return;
		}
		$("#orderadd_bnt").attr("disabled", true);
		common.load.load();
		var param = {
            'rentalLeaseOrderId': agreeId,
            'cancelLeaseDate' : $('#oserviceTime').val(),
			'channel' : "B",
			'type' : $('.js-cancel-lease-type').val(),
			'comments' : $('#odesc').val(),
			'userName': $('#ouserName').val(),
			'userMobile': $('#ouserMobile').val(),
			'orderName' : $("#orderName").val()
            
        };
		common.ajax({
			url : common.root + '/cancelLease/inputCancelLeaseOrderInfo.do',
			dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data : JSON.stringify(param),
			loadfun : function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {
					if (data.state == 1) {// 操作成功
						common.alert({
							msg : '操作成功'
						});
						common.closeWindow('orderadd6', 3);
					} else if (data.errorMsg) { // 操作失败
						$("#orderadd_bnt").attr("disabled", false);
						common.alert({
							msg : data.errorMsg
						});
					} else {
						$("#orderadd_bnt").attr("disabled", false);
						common.alert({
							msg : common.msg.error
						});
					}
				} else {
					$("#orderadd_bnt").attr("disabled", false);
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},// 获取用户名称
	getUserName : function() {
		var mobile = $('#ouserMobile').val();
		if (mobile == '' || mobile.length != 11) {
			$('#ouserName').removeAttr("readonly", "readonly");
			return;
		}
		common.ajax({
			url : common.root + '/sys/getUserName.do',
			data : {
				userMobile : mobile
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var name = data.username;
					$('#ouserName').val(name);
					if (name != null && name != '' && name != "null") {
						$('#ouserName').attr("readonly", "readonly");
					} else {
						$('#ouserName').removeAttr("readonly", "readonly");
					}
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},
	getAgree : function(id, name, username, mobile) {
		agreeId = id;
		$('#rankName').val(name);
		$('#orderName').val("退租_" + name);
		$('#ouserMobile').val(mobile);
		$('#ouserName').val(username);
	},
};
orderadd.init();