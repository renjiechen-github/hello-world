var workOrderId;
var createCost = {
	// 页面初始化操作
	init : function() {
		workOrderId = $('#js-work-order-id').val();
		// 加载结算类型
		createCost.initType();
		// 添加处理事件
		createCost.addEvent();
		// 时间按钮选择事件
		createCost.initTime();
	},
	initTime : function() {

	},
	addEvent : function() {
		$('#orderadd_bnt').click(function() {
			orderadd.save();
		});
		$("#endtime,#starttime").blur(function() {
			var starttime = $("#starttime").val();
			var endtime = $("#endtime").val();
			if (parseFloat(endtime) >= parseFloat(starttime)) {
				var degree = endtime - starttime;
				$("#degree").val(degree.toFixed(2));
			}
		});
	},
	/**
	 * 加载区域选择
	 */
	initType : function() {
		$("#cost").val(0.0);
		$("#starttime").val(0.0);
		$("#endtime").val(0.0);
		$("#degree").val(0.0);
		common.loadItem("CERTIFICATELEAVE.TYPE", function(json) {
			var html = "<option value=''>请选择...</option>";
			for (var i = 0; i < json.length; i++) {
                                if(json[i].item_name == '其他'){
                                    html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
                                }
			}
			$('#costtype').html(html);
		});
	},

	save : function() {
		var degree = $.trim($('#degree').val());
		if (degree == '') {
			$("#degree").val(0);
		}
		var starttime = $.trim($('#starttime').val());
		if (starttime == '') {
			$("#starttime").val(0);
		}
		var endtime = $.trim($('#endtime').val());
		if (endtime == '') {
			$("#endtime").val(0);
		}
		var cost = $('#cost').val();
                if(cost == 0){
                    alert("添加明细费用不能为0");
                    retrun;
                } 
		if (!Validator.Validate('form2')) {
			return;
		}
		var starttime = $("#starttime").val();
		var endtime = $("#endtime").val();
		if (parseFloat(endtime) < parseFloat(starttime)) {
			alert("开始度数不能大于结束度数！");
			return;
		}
		common.ajax({
			url : common.root + '/CertificateLeave/create.do',
			data : {
				orderId : workOrderId,
				taskId : -1,
				degree : $('#degree').val(),
				starttime : $('#starttime').val(),
				endtime : $('#endtime').val(),
				name : $('#name').val(),
				type : $('#costtype').val(),
				cost : $('#cost').val(),
				desc : $('#desc').val(),
				step_type : -1
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				common.log("data :" + data);
				common.log("data state: " + data.state);
				if (isloadsucc) {
					if (data.state == -2) {// 操作成功
						common.alert({
							msg : '新增失败'
						});
					} else {
						total = 0;
						common.alert({
							msg : '新增成功'
						});
						common.closeWindow('createCost', 1);
						table.refreshRedraw('wait2PayTable');
					}
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	}
};
$(function() {
	createCost.init();
});
