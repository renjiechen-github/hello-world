var agreeId = "";
var orderadd = {
	// 页面初始化操作
	init : function() {
		// 加载结算类型
		orderadd.initType();
		// 添加处理事件
		orderadd.addEvent();

		// 初始化图片上传
		common.dropzone.init({
			id : '#upurl',
			maxFiles : 10
		});
	},

	// 添加事件处理
	addEvent : function() {
		$('#orderadd_bnt').click(function() {
			orderadd.save();
		});

		$('#ouserMobile').blur(function() {
			$('#ouserName').val(orderadd.getUserName());
		});
	},

	// 加载区域选择
	initType : function() {
		// 加载维修类型
		var param = {};
		common.ajax({
			url : common.root + '/repairOrder/getRepairType.do',
			dataType : 'json',
      contentType: 'application/json; charset=utf-8',
      encode: false,
      data : JSON.stringify(param),
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = "<option value=''>请选择...</option>";
					var date = data.repairTypeList;
					for (var i = 0; i < date.length; i++) {
						html += '<option  value="' + date[i].type + '" >' + date[i].typeName + '</option>';
					}
					$("#childtype").html(html);
				}
			}
		});
	},

	// 保存数据
	save : function() {
		if (!Validator.Validate('form2')) {
			return;
		}

		var filepath = common.dropzone.getFiles('#upurl');
		var pathe = "";
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
		$("#orderadd_bnt").attr("disabled", true);
		common.load.load('正在执行，请稍等...');
		var param = {
			'orderName' : $('#oname').val(),
			'rentalLeaseOrderId' : agreeId,
			'userMobile' : $('#ouserMobile').val(),
			'userName' : $('#ouserName').val(),
			'appointmentDate' : $('#oserviceTime').val(),
			'type' : $('#childtype').val(),
			'comments' : $('#odesc').val(),
			'imageUrl' : pathe,
			'orderName' : $("#orderName").val()
		};		
		
		common.ajax({
			url : common.root + '/repairOrder/inputRepairOrderInfo.do',
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
						common.closeWindow('orderadd1', 3);
					} else if (data.state == -4) { // 操作失败
						$("#orderadd_bnt").attr("disabled", false);
						common.alert({
							msg : '当前房源已生成订单，请勿重复操作'
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
	},

	// 获取用户名称
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
		$('#agreeId').val(name);
		$('#ouserMobile').val(mobile);
		$('#ouserName').val(username);
		$('#orderName').val("修_" + name);		
	},
};
orderadd.init();