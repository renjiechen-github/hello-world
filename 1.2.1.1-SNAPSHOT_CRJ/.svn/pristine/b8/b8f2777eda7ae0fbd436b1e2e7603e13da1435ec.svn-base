var respData;
var workOrder;
var isMobile;
var dealerId;
var repairOrderValueMap = {};
var isLeader;
var jsRepairOrderDetail = {

	// 初始化数据信息
	init: function() {
		var id = $('#js-work-order-id').val();
		if (common.getCookie('resFlag' + (id)) == '1' || common.getCookie('resFlag' + (id)) == 'undefined' || common.getCookie('resFlag' + (id)) == '') {
			resFlag = true;
		} else {
			resFlag = false;
		}
		isMobile = $('#js-is-mobile').val();
		dealerId = $('#js-staff-id').val();
		isLeader = $('#js-is-leader').val();
		this.loadData(id);
	},

	// 订单数据处理
	loadData: function(workOrderId) {
		common.load.load();
		var param = {
			'workOrderId': workOrderId
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
					if (resFlag) {
						// 创建时间轴
						baseWorkOrder.loadStaft(workOrderId);
					}
					// 看房订单信息
					respData = data.subOrder;
					// 看房订单转成MAP
					jsRepairOrderDetail.createRepairOrderValueMap(respData);
					// 赋值父工单信息
					jsRepairOrderDetail.generatorData(respData);
					// 赋值各流程信息
					jsRepairOrderDetail.generatorHtml(respData);
					// 初始化各TAB页
					jsRepairOrderDetail.initTab(respData);
				}
			}
		});
	},

	// 看房订单转成MAP
	createRepairOrderValueMap: function(data) {
		var valueList = data.subOrderValueList;
		for (var i = 0; i < valueList.length; i++) {
			var repairOrderValue = valueList[i];
			var key = repairOrderValue.attrPath;
			repairOrderValueMap[key] = repairOrderValue;
		}
	},

	// 赋值父工单信息
	generatorData: function(data) {
		$('.js-tab > li').find("a[href='#" + data.stateName + "']").parent().addClass('active');
		$('#' + data.stateName).addClass('in active');
		$('.js-code').html(workOrder.code);
		$('.js-rental-name').html(data.rentalLeaseOrderName);
		$('.js-state-name').html(workOrder.subStateName);
		$('.js-create-date').html(data.createdDate);
		$('.js-order-comments').html(data.comments);
		$('.js-appointment-date').html(workOrder.appointmentDate);
		var createdStaffName = "";
		if (workOrder.createdStaffName != null && workOrder.createdStaffName != "") {
			createdStaffName = workOrder.createdStaffName;
		} else if (workOrder.createdUserName != null && workOrder.createdUserName != "") {
			createdStaffName = workOrder.createdUserName;
		} else if (workOrder.userName != null && workOrder.userName != "") {
			createdStaffName = workOrder.userName;
		}
		$('.js-created-person').html(createdStaffName);
		$('.js-user-phone').html(workOrder.userPhone);
		if (workOrder.userPhone != null && workOrder.userPhone != "" && isMobile == "Y") {
			$('.js-user-phone').click(function() {
				njyc.phone.callSomeOne(workOrder.userPhone);
			});
			$('.js-user-phone').css('color', '#0040ff')
		}
		$('.js-user-name').html(workOrder.userName);
		$('.js-order-name').html(workOrder.name);
		$('.js-type-name').html(data.typeName);


		// 根据合约ID，获取房源地址
		common.ajax({
			url: common.root + '/rankHouse/loadAgreementInfo.do',
			data: {
				id: data.rentalLeaseOrderId
			},
			dataType: 'json',
			async: false,
			loadfun: function(isloadsucc, data) {
				if (isloadsucc) {
					var address = data.address;
					if (address != null && address != "") {
						$('.js-address').html(address);
					} else {


					}
				}
			}
		});

		if (isMobile == "Y") {
			var picurls = data.imageUrl;
			if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined && picurls != 'undefined') {
				njyc.phone.showPic(jsRepairOrderDetail.changeImagePath(data.imageUrl), 'js-mobile-upurl');
				$('#js-mobile-upurl b').addClass('hidden')
			} else {
				if ($('#js-mobile-upurl') != null && $('#js-mobile-upurl') != undefined && $('#js-mobile-upurl') != 'undefined') {
					$('#js-mobile-upurl').addClass('remove');
				}

			}

		} else {
			// 图片回显
			var picurls = data.imageUrl;
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
					id: '#upurl-3',
					defimg: paths,
					maxFiles: 10,
					clickEventOk: false
				});
			} else {
				common.dropzone.init({
					id: '#upurl-3',
					maxFiles: 10,
					clickEventOk: false
				});
			}
		}
	},

	// 赋值各流程信息
	generatorHtml: function(data) {
		// 客服派单
		this.customerServAssignHtml(data);
		// 供应商接单
		this.butlerTakeOrderHtml(data);
		// 供应商执行
		this.butlerExecuteHtml(data);
		// 客服回访
		// this.customerServVisitHtml(data);
		// 用户评价
		this.generatorJudgeHtml(data);
	},

	// 初始化各流程TAB页面
	initTab: function(data) {
		var payableMoney = data.payableMoney;
		var paidMoney = data.paidMoney;
		switch (data.state) {
			case subOrderStateDef.ASSIGN_ORDER: // 客服派单
				// 显示按钮和备注
				$('.js-tab > li').find("a[href='#CUSTOMER_SERV_ASSIGN']").parent().addClass('active');
				$('#CUSTOMER_SERV_ASSIGN').addClass('in active');
				// 待付
				$(".js-should-cost").val(0);
				// 已支付
				$(".js-already-cost").text(0);
				if (!resFlag) {
					return;
				}
				$("#managerSelect").attr("disabled", false);
				// 显示指派按钮
				$("#confirmReassignOrderBtn").removeClass("hidden");
				$(".js-comments-textarea").attr('readonly', false);
				break;
			case subOrderStateDef.TAKE_ORDER: // 供应商接单
			case subOrderStateDef.REASSIGNING: // 重新指派订单
				// 显示按钮和备注
				$('.js-tab > li').find("a[href='#SUPPLIER_TAKE_ORDER']").parent().addClass('active');
				$('#SUPPLIER_TAKE_ORDER').addClass('in active');
				// 待付
				$(".js-should-cost").val(0);
				// 已支付
				$(".js-already-cost").text(0);
				if (!resFlag) {
					return;
				}
				// 显示按钮
				$("#butlerGetHomeBtn").removeClass("hidden");
				$("#butlerAgainReassign").removeClass("hidden");
				$(".js-house-inspection-comments-textarea").attr('readonly', false);
				break;
				// case subOrderStateDef.REASSIGNING_IN_STAFF_REVIEW: // 客服回访重新指派
				// // 显示按钮和备注
				// $('.js-tab >
				// li').find("a[href='#SUPPLIER_TAKE_ORDER']").parent().addClass('active');
				// $('#SUPPLIER_TAKE_ORDER').addClass('in active');
				// // 待付
				// $(".js-should-cost").val(0);
				// // 已支付
				// $(".js-already-cost").text(paidMoney / 100);
				// if (!resFlag) {
				// return;
				// }
				// // 显示按钮
				// $("#butlerGetHomeBtn").removeClass("hidden");
				// $("#butlerAgainReassign").removeClass("hidden");
				// $(".js-house-inspection-comments-textarea").attr('readonly', false);
				// break;
			case subOrderStateDef.DO_IN_ORDER: // 供应商执行
				// 显示按钮和备注
				$('.js-tab > li').find("a[href='#SUPPLIER_EXECUTE']").parent().addClass('active');
				$('#SUPPLIER_EXECUTE').addClass('in active');
				$(".js-should-cost").attr('readonly', false);
				// 待付
				$(".js-should-cost").val(payableMoney / 100);
				// 已支付
				if (parseInt(paidMoney) > 0) {
					$(".js-already-cost").text(paidMoney / 100);
				} else {
					$(".js-already-cost").text(0);
				}
				if (!resFlag) {
					return;
				}
				// 显示按钮
				$("#rentalAccountPassBtn").removeClass("hidden");
				$("#rentalAccountBtn").removeClass("hidden");
				$(".js-should-cost").attr("disabled", false);
				$(".js-rental-account-comments-textarea").attr('readonly', false);
				break;
				// case subOrderStateDef.STAFF_REVIEW: // 客服回访
				// case subOrderStateDef.WAIT_2_TACE: // 客服回访待追踪
				// // 显示按钮和备注
				// $('.js-tab >
				// li').find("a[href='#CUSTOMER_SERV_VISIT']").parent().addClass('active');
				// $('#CUSTOMER_SERV_VISIT').addClass('in active');
				// // 待付
				// $(".js-should-cost").val(0);
				// // 已支付
				// if (parseInt(paidMoney) > 0) {
				// $(".js-already-cost").text(paidMoney / 100);
				// } else {
				// $(".js-already-cost").text(0);
				// }
				// if (!resFlag) {
				// return;
				// }
				// // 显示按钮
				// $("#passInStaffReview").removeClass("hidden");
				// $("#reassignOrderBtn").removeClass("hidden");
				// $("#staffTace").removeClass("hidden");
				// $(".js-marketing-executive-audit-comments-textarea").attr('readonly', false);
				// break;
			case subOrderStateDef.PAY: // 客服支付
				// 待支付（总金额）-已支付
				var cost = (parseInt(payableMoney) - parseInt(paidMoney)) / 100;
				// 待付
				$(".js-should-cost").val(cost);
				// 已支付
				$(".js-already-cost").text(paidMoney / 100);
				break;
			default:
				// 已支付
				$(".js-already-cost").text(paidMoney / 100);
				// 待付
				$(".js-should-cost").val(0);
				break;
		}
	},

	// 客服派单
	customerServAssignHtml: function(data) {
		var staffId = data.staffId;

		// 获取供应商信息
		jsRepairOrderDetail.ajaxManagerInfo('managerSelect', '28');

		if (staffId != '' && staffId != 'null' && staffId != null && staffId != undefined) {
			$("#managerSelect option[value='" + data.staffId + "']").attr("selected", "selected");
		} else {
			$('#managerSelect').select2({
				'width': '200px'
			});
		}

		// 赋值客服订单说明
		if (data.attrCatg.code == 'REPAIR_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'CUSTOMER_SERV_ASSIGN') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "REPAIR_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.";
						var childAttr = childAttrList[j];
						var attrPath = defaultPath + childAttr.code;
						childAttr.attrPath = attrPath;
						if (childAttr.code == 'COMMENTS') {
							$('.js-comments-label').html(childAttr.name);
							$('.js-comments-textarea').data('attr', childAttr);
						}
					}
				}
			}
		}
		var text = repairOrderValueMap['REPAIR_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-comments-textarea').text(text);
		}
	},

	// 供应商接单
	butlerTakeOrderHtml: function(data) {
		// 赋值客服订单说明
		if (data.attrCatg.code == 'REPAIR_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'SUPPLIER_TAKE_ORDER') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "REPAIR_ORDER_PROCESS.SUPPLIER_TAKE_ORDER.";
						var childAttr = childAttrList[j];
						var attrPath = defaultPath + childAttr.code;
						childAttr.attrPath = attrPath;
						if (childAttr.code == 'COMMENTS') {
							$('.js-house-inspection-comments-label').html(childAttr.name);
							$('.js-house-inspection-comments-textarea').data('attr', childAttr);
						}
					}
				}
			}
		}
		var text = repairOrderValueMap['REPAIR_ORDER_PROCESS.SUPPLIER_TAKE_ORDER.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-house-inspection-comments-textarea').text(text);
		}
	},

	// 管家上门阶段，显示重新指派
	againAssignOrder: function() {
		$(".div-againAssignOrder").removeClass("hidden");
		$("#againAssignOrderCancelBtn").removeClass("hidden");
		$("#againAssignOrderBtn").removeClass("hidden");

		// 隐藏    	
		$(".div-butler-info").addClass("hidden");
		$("#rentalAccountBtn").addClass("hidden");
		$("#rentalAccountPassBtn").addClass("hidden");
		$("#againAssignOrder").addClass("hidden");

		// AJAX获取管家信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '26,28,29',
          type: workOrder.type
      },
			async: false,
			loadfun: function(isloadsucc, json) {
				if (isloadsucc) {
					var html = "<option value=''> 请选择...</option>";
					for (var i = 0; i < json.length; i++) {
						html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
					}
					$('#butlerId').html(html);
					$('#butlerId').select2({
						'width': '200px'
					});
				} else {
					common.alert({
						msg: common.msg.error
					});
				}
			}
		});
	},

	// 管家上门阶段，隐藏重新指派
	againAssignOrderCancelBtn: function() {
		$(".div-butler-info").removeClass("hidden");
		$("#rentalAccountBtn").removeClass("hidden");
		$("#rentalAccountPassBtn").removeClass("hidden");
		$("#againAssignOrder").removeClass("hidden");

		// 隐藏
		$(".div-againAssignOrder").addClass("hidden");
		$("#againAssignOrderCancelBtn").addClass("hidden");
		$("#againAssignOrderBtn").addClass("hidden");
	},

	// 管家上门阶段，提交指派订单
	againAssignOrderBtn: function() {
		// 管家id
		var butlerId = $('#butlerId').val();
		if (butlerId == null || butlerId == "") {
			common.alert({
				msg: "请选择指派人！"
			});
			return;
		}

		// 获取当前订单id
		var workOrderId = $("#js-work-order-id").val();
		var manager = $('#butlerId option:selected').text();
		common.alert({
			msg: "确定指派给（" + manager + "）吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					common.ajax({
						url: common.root + '/workOrder/directActivity.do',
						dataType: 'json',
						encode: false,
						data: {
							workOrderId: workOrderId,
							butlerId: butlerId,
							subOrderId: respData.id,
							type: 'H'
						},
						loadfun: function(isloadsucc, data) {
							if (data.state == 1) {
								var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
								var param = {
									'code': respData.code,
									'staffId': butlerId,
									'dealerId': dealerId,
									'subOrderValueList': [{
										'attrId': commentsData.id,
										'attrPath': commentsData.attrPath,
										'textInput': $('.js-house-inspection-comments-textarea').val()
									}]
								};
								common.ajax({
									url: common.root + '/repairOrder/reassignOrder.do',
									dataType: 'json',
									contentType: 'application/json; charset=utf-8',
									encode: false,
									data: JSON.stringify(param),
									loadfun: function(isloadsucc, data) {
										common.load.hide();
										jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
									}
								});
							}
						}
					});
				}
			}
		});

	},

	// 供应商执行
	butlerExecuteHtml: function(data) {

		// 判断当前用户是否是负责人
		if (isLeader == 'Y') {
			// 显示重新指派按钮
			$("#againAssignOrder").removeClass("hidden");
		}


		if (data.attrCatg.code == 'REPAIR_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'SUPPLIER_EXECUTE') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "REPAIR_ORDER_PROCESS.SUPPLIER_EXECUTE.";
						var childAttr = childAttrList[j];
						var attrPath = defaultPath + childAttr.code;
						childAttr.attrPath = attrPath;
						if (childAttr.code == 'COMMENTS') {
							$('.js-rental-account-comments-label').html(childAttr.name);
							$('.js-rental-account-comments-textarea').data('attr', childAttr);
						}

						// 图片
						if (childAttr.code == 'HOUSE_PICTURE') {
							$('.js-customer-serv-upurl-1').html(childAttr.name);
							$('#upurl-1').data('attr', childAttr);
						}
					}
				}
			}
		}

		// 设置备注回显
		var text = repairOrderValueMap['REPAIR_ORDER_PROCESS.SUPPLIER_EXECUTE.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			$('.js-rental-account-comments-textarea').text(text);
		}

		// 回显图片
		var picurls = repairOrderValueMap['REPAIR_ORDER_PROCESS.SUPPLIER_EXECUTE.HOUSE_PICTURE'];
		if (isMobile == "Y") {
			var imageUrl = "";
			if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined) {
				imageUrl = picurls.textInput;
			}
			njyc.phone.showPic(jsRepairOrderDetail.changeImagePath(imageUrl), 'js-mobile-upurl-1');
			if (data.state != 'D') {
				$('#upurl-1').addClass('hidden');
				$('#upurl-1 b').addClass('hidden');
			}
		} else {
			if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
				jsRepairOrderDetail.initPicurls(picurls, 'upurl-1', true);
			} else {
				jsRepairOrderDetail.initPicurls(picurls, 'upurl-1', false);
			}
		}
	},

	// 客服回访TAB页
	customerServVisitHtml: function(data) {
		if (data.attrCatg.code == 'REPAIR_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'CUSTOMER_SERV_VISIT') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "REPAIR_ORDER_PROCESS.CUSTOMER_SERV_VISIT.";
						var childAttr = childAttrList[j];
						var attrPath = defaultPath + childAttr.code;
						childAttr.attrPath = attrPath;
						if (childAttr.code == 'COMMENTS') {
							$('.js-marketing-executive-audit-comments-label').html(childAttr.name);
							$('.js-marketing-executive-audit-comments-textarea').data('attr', childAttr);
						}
						if (childAttr.code == 'PASSED') {
							$('#passInStaffReview').data('attr', childAttr);
						}
						if (childAttr.code == 'HOUSE_PICTURE') {
							$('.js-customer-serv-upurl-2').html(childAttr.name);
							$('#upurl-2').data('attr', childAttr);
						}
					}
				}
			}
		}
		var text = repairOrderValueMap['REPAIR_ORDER_PROCESS.CUSTOMER_SERV_VISIT.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-marketing-executive-audit-comments-textarea').text(text);
		}

		var picurls = repairOrderValueMap['REPAIR_ORDER_PROCESS.CUSTOMER_SERV_VISIT.HOUSE_PICTURE'];
		if (isMobile == "Y") {
			// $('#upurl-2').data('attr', childAttr);
			var imageUrl = "";
			if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined) {
				imageUrl = picurls.textInput;
			}
			njyc.phone.showPic(jsRepairOrderDetail.changeImagePath(imageUrl), 'js-mobile-upurl-2');
			if (data.state != 'E' && data.state != 'F') {
				$('#upurl-2').addClass('hidden');
				$('#upurl-2 b').addClass('hidden');
			}
		} else {
			if ((data.state == subOrderStateDef.STAFF_REVIEW || data.state == subOrderStateDef.WAIT_2_TACE) && resFlag == true) {
				jsRepairOrderDetail.initPicurls(picurls, 'upurl-2', true);
			} else {
				jsRepairOrderDetail.initPicurls(picurls, 'upurl-2', false);
			}
		}
	},
	// 用户评价
	generatorJudgeHtml: function(data) {
		if (data.attrCatg.code == 'REPAIR_ORDER_PROCESS') {
			var comments = '';
			var commentDate = '';
			var picurls = '';
			var html = '<div class="form-group col-md-12 col-xs-12"> ' +
				'<label class="col-md-2 col-xs-4 control-label">用户评分</label> ' +
				'</div><div class="form-group col-md-12 col-xs-12">'
			if (workOrder.orderCommentaryList.length > 0) {
				comments = workOrder.orderCommentaryList[0].comments;
				commentDate = workOrder.orderCommentaryList[0].commentDate;
				picurls = workOrder.orderCommentaryList[0].imageUrl;
			}
			for (var i = 0; i < workOrder.orderCommentaryList.length; i++) {
				var commentary = workOrder.orderCommentaryList[i];
				html += '<label class="col-md-2 col-xs-4 control-label">' + commentary.typeName + '</label>' +
					'<label class="col-md-2 col-xs-4 control-label" style="'
				var color = '#993300'
				if (parseInt(commentary.score) < 3) {
					color = '#ff0000';
				} else if (parseInt(commentary.score) > 3) {
					color = '#778899'
				}
				html += 'color: ' + color + ';text-align:left;white-space:nowrap;">' + commentary.score + '</label>';
			}
			html += '</div>';
			$('#judgeAuditForm').prepend(html);
			$('.js-judge-audit-time-label-value').html(commentDate);
			$('.js-judge-audit-comments-textarea').attr('value', comments);

			// 图片回显
			if (isMobile == "Y") {
				njyc.phone.showPic(jsRepairOrderDetail.changeImagePath(picurls), 'js-judge-audit-image-url-mobile');
				if (data.state != 'D') {
					$('#image-upurl-1').addClass('hidden');
					$('#image-upurl-1 b').addClass('hidden');
				}
			} else {
				if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
					jsRepairOrderDetail.initPicurls1(picurls, 'image-upurl-1', true);
				} else {
					jsRepairOrderDetail.initPicurls1(picurls, 'image-upurl-1', false);
				}
			}
		}
	},

	// 图片显示
	initPicurls1: function(picurls, id, flag) {
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
				id: '#' + id,
				defimg: paths,
				maxFiles: 10,
				clickEventOk: flag
			});
		} else {
			common.dropzone.init({
				id: '#' + id,
				maxFiles: 10,
				clickEventOk: flag
			});
		}
	},

	// 客服指派操作
	assignOrder: function() {
		// 判断是否选择供应商
		if ($('#managerSelect').val() == null || $('#managerSelect').val() == "") {
			common.alert({
				msg: "请选择指派人！"
			});
			return;
		}

		var manager = $('#managerSelect option:selected').text();
		common.alert({
			msg: "确定指派给供应商(" + manager + ")吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-comments-textarea').data('attr');
					var upurl = $("#upurl").data('attr');
					var param = {
						'code': respData.code,
						'staffId': $('#managerSelect').val(),
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/assignOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 供应商接单
	takeOrder: function() {
		common.alert({
			msg: "确定保存吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-house-inspection-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/takeOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 供应商重新指派按钮
	butlerAgainReassign: function() {
		// 隐藏重新指派按钮，显示供应商列表，显示返回、指派按钮
		$("#butlerGetHomeBtn").addClass("hidden");
		$("#butlerAgainReassign").addClass("hidden");
		$("#butlerReassign").removeClass("hidden");
		$("#butlerCancelReassign").removeClass("hidden");
		$(".js-butler-reassign-manager-div").removeClass("hidden");

		// 获取供应商信息
		jsRepairOrderDetail.ajaxManagerInfo('butlerAgainReassignSelect', '28');
		$('#butlerAgainReassignSelect').select2({
			'width': '200px'
		});
	},

	// 供应商接单返回按钮
	butlerCancelReassign: function() {
		// 显示重新指派、通过按钮，隐藏供应商列表、返回、指派按钮
		$("#butlerGetHomeBtn").removeClass("hidden");
		$("#butlerAgainReassign").removeClass("hidden");
		$("#butlerReassign").addClass("hidden");
		$("#butlerCancelReassign").addClass("hidden");
		$(".js-butler-reassign-manager-div").addClass("hidden");
	},

	// 供应商重新指派操作
	butlerReassign: function() {
		// 判断是否选择供应商
		if ($('#butlerAgainReassignSelect').val() == null || $('#butlerAgainReassignSelect').val() == "") {
			common.alert({
				msg: "请选择指派人！"
			});
			return;
		}
		var manager = $('#butlerAgainReassignSelect option:selected').text();
		common.alert({
			msg: "确定重新指派给供应商（" + manager + "）吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
					var param = {
						'code': respData.code,
						'staffId': $('#butlerAgainReassignSelect').val(),
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-house-inspection-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/reassignOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 供应商执行订单
	processOrder: function(flag) {
		// 待付金额
		var payableMoney = $(".js-should-cost").val();
		var reg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
		// 正则判断金额填写是否正确
		if (!reg.test(payableMoney) || payableMoney < 0) {
			common.alert({
				msg: "金额填写错误！"
			});
			return true;
		}
		payableMoney = payableMoney * 100;

		// 已付金额
		var payidMoney = $(".js-already-cost").text();
		payidMoney = payidMoney * 100;

		// 金额总计
		var cost = parseInt(payableMoney) + parseInt(payidMoney);
		var upurl = $("#upurl-1").data('attr');
		var pathe = "";
		if (isMobile == 'Y') {
			pathe = jsRepairOrderDetail.getMobileImagePath($('#js-mobile-upurl-1 input[name="picImage"]'));
		} else {
			pathe = jsRepairOrderDetail.getPicPath("upurl-1");
		}

		var str = "";
		if (flag == 'Y') {
			str = "确定提交吗？";
		} else {
			str = "确定保存吗？";
		}

		common.alert({
			msg: str,
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-rental-account-comments-textarea').data('attr');
					var totalMoneyData = $('#cost').data('attr');
					var param = {
						'code': respData.code,
						'submitFlag': flag,
						'dealerId': dealerId,
						'payableMoney': cost,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-rental-account-comments-textarea').val()
						}, {
							// 'repairOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/processOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 客服重新指派按钮
	reassignOrderInStaffReview: function() {
		// 隐藏重新指派按钮，显示供应商列表，显示返回、指派按钮
		$("#reassignOrderBtn").addClass("hidden");
		$("#passInStaffReview").addClass("hidden");
		$("#staffTace").addClass("hidden");
		$("#reassign").removeClass("hidden");
		$("#cancelReassignOrderBtn").removeClass("hidden");
		$(".js-reassign-manager-div1").removeClass("hidden");

		// 获取供应商信息
		jsRepairOrderDetail.ajaxManagerInfo('managerSelect1', '28');
		$('#managerSelect1').select2({
			'width': '200px'
		});
	},

	// 客服重新指派操作
	reassign: function() {
		// 判断是否选择供应商
		if ($('#managerSelect1').val() == null || $('#managerSelect1').val() == "") {
			common.alert({
				msg: "请选择指派人！"
			});
			return;
		}

		// 判断是否填写重新指派的原因备注
		var comments = $('.js-marketing-executive-audit-comments-textarea').val();
		if (comments == null || comments == '') {
			common.alert("重新派单时请输入原因");
			return;
		}

		var manager = $('#managerSelect1 option:selected').text();
		var pathe = "";
		if (isMobile == 'Y') {
			pathe = jsRepairOrderDetail.getMobileImagePath($('#js-mobile-upurl-2 input[name="picImage"]'));
		} else {
			pathe = jsRepairOrderDetail.getPicPath("upurl-2");
		}
		common.alert({
			msg: "确定重新指派给供应商（" + manager + "）吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
					var passInStaffReviewData = $('#passInStaffReview').data('attr');
					var upurl = $("#upurl-2").data('attr');
					var param = {
						'code': respData.code,
						'staffId': $('#managerSelect1').val(),
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'repairOrderId' : respData.id,
							'attrId': passInStaffReviewData.id,
							'attrPath': passInStaffReviewData.attrPath,
							'textInput': 'N'
						}, {
							// 'repairOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/reassignOrderInStaffReview.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 客服回访返回按钮
	cancelReassignOrder: function() {
		// 显示重新指派、通过按钮，隐藏供应商列表、返回、指派按钮
		$("#reassignOrderBtn").removeClass("hidden");
		$("#passInStaffReview").removeClass("hidden");
		$("#staffTace").removeClass("hidden");
		$("#reassign").addClass("hidden");
		$("#cancelReassignOrderBtn").addClass("hidden");
		$(".js-reassign-manager-div1").addClass("hidden");
	},

	// 客服审批通过
	passInStaffReview: function() {
		var pathe = "";
		if (isMobile == 'Y') {
			pathe = jsRepairOrderDetail.getMobileImagePath($('#js-mobile-upurl-2 input[name="picImage"]'));
		} else {
			pathe = jsRepairOrderDetail.getPicPath("upurl-2");
		}
		common.alert({
			msg: '确定审批通过？',
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
					var passInStaffReviewData = $('#passInStaffReview').data('attr');
					var upurl = $("#upurl-2").data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'repairOrderId' : respData.id,
							'attrId': passInStaffReviewData.id,
							'attrPath': passInStaffReviewData.attrPath,
							'textInput': 'Y'
						}, {
							// 'repairOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/passInStaffReview.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},
	// 客服订单追踪
	staffTace: function() {
		var pathe = "";
		if (isMobile == 'Y') {
			pathe = jsRepairOrderDetail.getMobileImagePath($('#js-mobile-upurl-2 input[name="picImage"]'));
		} else {
			pathe = jsRepairOrderDetail.getPicPath("upurl-2");
		}
		common.alert({
			msg: '确定待追踪？',
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
					var upurl = $("#upurl-2").data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'repairOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'repairOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/repairOrder/staffTace.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRepairOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// AJAX获取人员信息
	ajaxManagerInfo: function(data, id) {
		// AJAX获取供应商信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '26,28,29',
          type: workOrder.type
      },
			async: false,
			loadfun: function(isloadsucc, json) {
				if (isloadsucc) {
					var html = "<option value=''> 请选择...</option>";
					for (var i = 0; i < json.length; i++) {
						html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
					}
					$('#' + data).html(html);
				} else {
					common.alert({
						msg: common.msg.error
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

	// 图片显示
	initPicurls: function(picurls, id, flag) {
		if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined && picurls != 'undefined') {
			picurls = picurls.textInput;
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
					id: '#' + id,
					defimg: paths,
					maxFiles: 10,
					clickEventOk: flag
				});
			} else {
				common.dropzone.init({
					id: '#' + id,
					maxFiles: 10,
					clickEventOk: flag
				});
			}
		} else {
			common.dropzone.init({
				id: '#' + id,
				maxFiles: 10,
				clickEventOk: flag
			});
		}
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

	changeImagePath: function(images) {
		if (!images || images.length == 0) {
			return '';
		}
		return images.replace(/,/g, '|');
	},
	getMobileImagePath: function(inputsEl) {
		var path = '';
		var picImage = inputsEl;
		if (picImage.length == 0) {
			return "";
		}
		for (var i = 0; i < picImage.length; i++) {
			path += ',' + $(picImage[i]).val();
		}
		if (path != '') {
			path = path.substring(1);
		}
		return path;
	},
	uploadNbrAttachment: function() {
		var picSize = 10; // 可以上传图片数量
		var uploadPic = $('#js-mobile-upurl input[name="picImage"]').size();
		if (Math.abs(picSize) > uploadPic) {
			njyc.phone.selectImage((Math.abs(picSize) - uploadPic), 'js-mobile-upurl');
		} else {
			$('#upurl-3').hide();
		}
	},

	uploadhousePicture: function() {
		var picSize = 10; // 可以上传图片数量
		var uploadPic = $('#js-mobile-upurl-1 input[name="picImage"]').size();
		if (Math.abs(picSize) > uploadPic) {
			njyc.phone.selectImage((Math.abs(picSize) - uploadPic), 'js-mobile-upurl-1');
		} else {
			$('#upurl-1').hide();
		}
	},
	uploadReback: function() {
		var picSize = 10; // 可以上传图片数量
		var uploadPic = $('#js-mobile-upurl-2 input[name="picImage"]').size();
		if (Math.abs(picSize) > uploadPic) {
			njyc.phone.selectImage((Math.abs(picSize) - uploadPic), 'js-mobile-upurl-2');
		} else {
			$('#upurl-2').hide();
		}
	},
	getPath: function(id) {
		var filepath = common.dropzone.getFiles('#' + id);
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
		return pathe;
	},

};

$(function() {
	jsRepairOrderDetail.init();
});