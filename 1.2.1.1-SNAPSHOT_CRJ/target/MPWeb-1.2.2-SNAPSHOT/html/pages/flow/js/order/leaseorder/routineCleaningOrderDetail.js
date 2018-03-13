var respData;
var workOrder;
var isMobile;
var dealerId;
var routineCleaningOrderValueMap = {};
var isLeader;
var jsRoutineCleaningOrderDetail = {

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
					jsRoutineCleaningOrderDetail.createRoutineCleaningOrderValueMap(respData);
					// 赋值父工单信息
					jsRoutineCleaningOrderDetail.generatorData(respData);
					// 赋值各流程信息
					jsRoutineCleaningOrderDetail.generatorHtml(respData);
					// 初始化各TAB页
					jsRoutineCleaningOrderDetail.initTab(respData);
				}
			}
		});
	},

	// 看房订单转成MAP
	createRoutineCleaningOrderValueMap: function(data) {
		var valueList = data.subOrderValueList;
		for (var i = 0; i < valueList.length; i++) {
			var routineCleaningOrderValue = valueList[i];
			var key = routineCleaningOrderValue.attrPath;
			routineCleaningOrderValueMap[key] = routineCleaningOrderValue;
		}
	},

	// 赋值父工单信息
	generatorData: function(data) {
		$('.js-tab > li').find("a[href='#" + data.stateName + "']").parent().addClass('active');
		$('#' + data.stateName).addClass('in active');
		$('.js-code').html(data.code);
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
		//		this.customerServVisitHtml(data);
		// 用户评价
		this.generatorJudgeHtml(data);
	},

	// 初始化各流程TAB页面
	initTab: function(data) {
		switch (data.state) {
			case subOrderStateDef.ASSIGN_ORDER: // 客服派单
				// 显示按钮和备注
				$('.js-tab > li').find("a[href='#CUSTOMER_SERV_ASSIGN']").parent().addClass('active');
				$('#CUSTOMER_SERV_ASSIGN').addClass('in active');
				if (!resFlag) {
					return;
				}
				$("#managerSelect").attr("disabled", false);
				// 显示指派按钮
				$("#confirmReassignOrderBtn").removeClass("hidden");
				$(".js-comments-textarea").attr('readonly', false);
				break;
				//		case subOrderStateDef.REASSIGNING_IN_STAFF_REVIEW: // 客服回访重新指派
			case subOrderStateDef.TAKE_ORDER: // 供应商接单
			case subOrderStateDef.REASSIGNING:
				// 显示按钮和备注
				$('.js-tab > li').find("a[href='#SUPPLIER_TAKE_ORDER']").parent().addClass('active');
				$('#SUPPLIER_TAKE_ORDER').addClass('in active');
				if (!resFlag) {
					return;
				}
				// 显示按钮
				$("#butlerGetHomeBtn").removeClass("hidden");
				$("#butlerAgainReassign").removeClass("hidden");
				$(".js-house-inspection-comments-textarea").attr('readonly', false);
				break;
			case subOrderStateDef.DO_IN_ORDER:
				// 供应商执行，显示按钮和备注
				$('.js-tab > li').find("a[href='#SUPPLIER_EXECUTE']").parent().addClass('active');
				$('#SUPPLIER_EXECUTE').addClass('in active');
				if (!resFlag) {
					return;
				}
				// 显示按钮
				$("#rentalAccountPassBtn").removeClass("hidden");
				$("#rentalAccountBtn").removeClass("hidden");
				$(".js-rental-account-comments-textarea").attr('readonly', false);
				break;
				//		case subOrderStateDef.STAFF_REVIEW: // 客服回访
				//		case subOrderStateDef.WAIT_2_TACE: // 客服回访待追踪
				//			// 显示按钮和备注
				//			$('.js-tab > li').find("a[href='#CUSTOMER_SERV_VISIT']").parent().addClass('active');
				//			$('#CUSTOMER_SERV_VISIT').addClass('in active');
				//			if (!resFlag) {
				//				return;
				//			}
				//			// 显示按钮
				//			$("#passInStaffReview").removeClass("hidden");
				//			$("#reassignOrderBtn").removeClass("hidden");
				//			$("#staffTace").removeClass("hidden");
				//			$(".js-marketing-executive-audit-comments-textarea").attr('readonly', false);
				//			break;
		}
	},

	// 客服派单
	customerServAssignHtml: function(data) {
		var staffId = data.staffId;

		// 获取供应商信息
		jsRoutineCleaningOrderDetail.ajaxManagerInfo('managerSelect', '28');

		if (staffId != '' && staffId != 'null' && staffId != null && staffId != undefined) {
			$("#managerSelect option[value='" + data.staffId + "']").attr("selected", "selected");
		} else {
			$('#managerSelect').select2({
				'width': '200px'
			});
		}

		// 赋值客服订单说明
		if (data.attrCatg.code == 'ROUTINE_CLEANING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'CUSTOMER_SERV_ASSIGN') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.";
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
		var text = routineCleaningOrderValueMap['ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-comments-textarea').text(text);
		}
	},

	// 供应商接单
	butlerTakeOrderHtml: function(data) {
		// 赋值客服订单说明
		if (data.attrCatg.code == 'ROUTINE_CLEANING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'SUPPLIER_TAKE_ORDER') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "ROUTINE_CLEANING_ORDER_PROCESS.SUPPLIER_TAKE_ORDER.";
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
		var text = routineCleaningOrderValueMap['ROUTINE_CLEANING_ORDER_PROCESS.SUPPLIER_TAKE_ORDER.COMMENTS'];
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
          roleId: '26,28',
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
							type: 'I'
						},
						loadfun: function(isloadsucc, data) {
							if (data.state == 1) {
								var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
								var param = {
									'code': respData.code,
									'dealerId': dealerId,
									'staffId': butlerId,
									'subOrderValueList': [{
										// 'routineCleaningOrderId' : respData.id,
										'attrId': commentsData.id,
										'attrPath': commentsData.attrPath,
										'textInput': $('.js-house-inspection-comments-textarea').val()
									}]
								};
								common.ajax({
									url: common.root + '/routineCleaningOrder/reassignOrder.do',
									dataType: 'json',
									contentType: 'application/json; charset=utf-8',
									encode: false,
									data: JSON.stringify(param),
									loadfun: function(isloadsucc, data) {
										common.load.hide();
										jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
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

		if (data.attrCatg.code == 'ROUTINE_CLEANING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'SUPPLIER_EXECUTE') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "ROUTINE_CLEANING_ORDER_PROCESS.SUPPLIER_EXECUTE.";
						var childAttr = childAttrList[j];
						var attrPath = defaultPath + childAttr.code;
						childAttr.attrPath = attrPath;
						if (childAttr.code == 'COMMENTS') {
							$('.js-rental-account-comments-label').html(childAttr.name);
							$('.js-rental-account-comments-textarea').data('attr', childAttr);
						}

						// 图片
						if (childAttr.code == 'HOUSE_PICTURE') {
							$('.js-customer-serv-upurl').html(childAttr.name);
							$('#upurl-1').data('attr', childAttr);
						}
					}
				}
			}
		}
		var text = routineCleaningOrderValueMap['ROUTINE_CLEANING_ORDER_PROCESS.SUPPLIER_EXECUTE.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-rental-account-comments-textarea').text(text);
		}

		// 回显图片
		var picurls = routineCleaningOrderValueMap['ROUTINE_CLEANING_ORDER_PROCESS.SUPPLIER_EXECUTE.HOUSE_PICTURE'];
		if (isMobile == "Y") {
			// $('#upurl-1').data('attr', childAttr);
			var imageUrl = "";
			if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined) {
				imageUrl = picurls.textInput;
			}
			njyc.phone.showPic(jsRoutineCleaningOrderDetail.changeImagePath(imageUrl), 'js-mobile-upurl-1');
			if (data.state != 'D') {
				$('#upurl-1').addClass('hidden');
				$('#upurl-1 b').addClass('hidden');
			}
		} else {
			if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
				jsRoutineCleaningOrderDetail.initPicurls(picurls, 'upurl-1', true);
			} else {
				jsRoutineCleaningOrderDetail.initPicurls(picurls, 'upurl-1', false);
			}
		}
	},

	// 客服回访TAB页
	customerServVisitHtml: function(data) {
		if (data.attrCatg.code == 'ROUTINE_CLEANING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'CUSTOMER_SERV_VISIT') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.";
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

						// 图片
						if (childAttr.code == 'HOUSE_PICTURE') {
							$('.js-customer-serv-upurl-1').html(childAttr.name);
							$('#upurl-2').data('attr', childAttr);
						}
					}
				}
			}
		}
		var text = routineCleaningOrderValueMap['ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-marketing-executive-audit-comments-textarea').text(text);
		}

		// 回显图片
		var picurls = routineCleaningOrderValueMap['ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.HOUSE_PICTURE'];
		if (isMobile == "Y") {
			// $('#upurl-2').data('attr', childAttr);
			var imageUrl = "";
			if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined) {
				imageUrl = picurls.textInput;
			}
			njyc.phone.showPic(jsRoutineCleaningOrderDetail.changeImagePath(imageUrl), 'js-mobile-upurl-2');
			if (data.state != 'E' && data.state != 'F') {
				$('#upurl-2').addClass('hidden');
				$('#upurl-2 b').addClass('hidden');
			}
		} else {
			if ((data.state == subOrderStateDef.STAFF_REVIEW || data.state == subOrderStateDef.WAIT_2_TACE) && resFlag == true) {
				jsRoutineCleaningOrderDetail.initPicurls(picurls, 'upurl-2', true);
			} else {
				jsRoutineCleaningOrderDetail.initPicurls(picurls, 'upurl-2', false);
			}
		}
	},

	// 用户评价
	generatorJudgeHtml: function(data) {
		if (data.attrCatg.code == 'ROUTINE_CLEANING_ORDER_PROCESS') {
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
				njyc.phone.showPic(jsRoutineCleaningOrderDetail.changeImagePath(picurls), 'js-judge-audit-image-url-mobile');
				if (data.state != 'D') {
					$('#image-upurl-1').addClass('hidden');
					$('#image-upurl-1 b').addClass('hidden');
				}
			} else {
				if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
					jsRoutineCleaningOrderDetail.initPicurls1(picurls, 'image-upurl-1', true);
				} else {
					jsRoutineCleaningOrderDetail.initPicurls1(picurls, 'image-upurl-1', false);
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
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'staffId': $('#managerSelect').val(),
						'subOrderValueList': [{
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/assignOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
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
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-house-inspection-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/takeOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 供应商重新指派按钮
	butlerAgainReassign: function() {
		// 隐藏重新指派按钮，显示管家列表，显示返回、指派按钮
		$("#butlerGetHomeBtn").addClass("hidden");
		$("#butlerAgainReassign").addClass("hidden");
		$("#butlerReassign").removeClass("hidden");
		$("#butlerCancelReassign").removeClass("hidden");
		$(".js-butler-reassign-manager-div").removeClass("hidden");

		// 获取供应商信息
		jsRoutineCleaningOrderDetail.ajaxManagerInfo('butlerAgainReassignSelect', '28');
		$('#butlerAgainReassignSelect').select2({
			'width': '200px'
		});
	},

	// 供应商接单返回按钮
	butlerCancelReassign: function() {
		// 显示重新指派、通过按钮，隐藏管家列表、返回、指派按钮
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
						'dealerId': dealerId,
						'staffId': $('#butlerAgainReassignSelect').val(),
						'subOrderValueList': [{
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-house-inspection-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/reassignOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 供应商执行订单
	processOrder: function(flag) {
		var pathe = "";
		if (isMobile == 'Y') {
			pathe = jsRoutineCleaningOrderDetail.getMobileImagePath($('#js-mobile-upurl-1 input[name="picImage"]'));
		} else {
			pathe = jsRoutineCleaningOrderDetail.getPicPath("upurl-1");
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
					var upurl = $("#upurl-1").data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'submitFlag': flag,
						'subOrderValueList': [{
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-rental-account-comments-textarea').val()
						}, {
							// 'routineCleaningOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/processOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 客服重新指派按钮
	reassignOrderInStaffReview: function() {
		// 隐藏重新指派按钮，显示管家列表，显示返回、指派按钮
		$("#reassignOrderBtn").addClass("hidden");
		$("#passInStaffReview").addClass("hidden");
		$("#staffTace").addClass("hidden");
		$("#reassign").removeClass("hidden");
		$("#cancelReassignOrderBtn").removeClass("hidden");
		$(".js-reassign-manager-div1").removeClass("hidden");

		// 获取供应商信息
		jsRoutineCleaningOrderDetail.ajaxManagerInfo('managerSelect1', '28');
		$('#managerSelect1').select2({
			'width': '200px'
		});
	},

	// 客服重新指派操作
	reassign: function() {
		// 判断是否选择管家
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
			pathe = jsRoutineCleaningOrderDetail.getMobileImagePath($('#js-mobile-upurl-2 input[name="picImage"]'));
		} else {
			pathe = jsRoutineCleaningOrderDetail.getPicPath("upurl-2");
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
						'dealerId': dealerId,
						'staffId': $('#managerSelect1').val(),
						'subOrderValueList': [{
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'routineCleaningOrderId' : respData.id,
							'attrId': passInStaffReviewData.id,
							'attrPath': passInStaffReviewData.attrPath,
							'textInput': 'N'
						}, {
							// 'routineCleaningOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/reassignOrderInStaffReview.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 客服回访返回按钮
	cancelReassignOrder: function() {
		// 显示重新指派、通过按钮，隐藏管家列表、返回、指派按钮
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
			pathe = jsRoutineCleaningOrderDetail.getMobileImagePath($('#js-mobile-upurl-2 input[name="picImage"]'));
		} else {
			pathe = jsRoutineCleaningOrderDetail.getPicPath("upurl-2");
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
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'routineCleaningOrderId' : respData.id,
							'attrId': passInStaffReviewData.id,
							'attrPath': passInStaffReviewData.attrPath,
							'textInput': 'Y'
						}, {
							// 'routineCleaningOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/passInStaffReview.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
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
			pathe = jsRoutineCleaningOrderDetail.getMobileImagePath($('#js-mobile-upurl-2 input[name="picImage"]'));
		} else {
			pathe = jsRoutineCleaningOrderDetail.getPicPath("upurl-2");
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
							// 'routineCleaningOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'routineCleaningOrderId' : respData.id,
							'attrId': upurl.id,
							'attrPath': upurl.attrPath,
							'textInput': pathe
						}]
					};
					common.ajax({
						url: common.root + '/routineCleaningOrder/staffTace.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsRoutineCleaningOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
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
					// common.closeWindow('routineCleaningOrderDetail', 3);
				}
			} else {
				common.alert({
					msg: common.msg.error
				});
			}
		}
	},

	// AJAX获取人员信息
	ajaxManagerInfo: function(data, id) {
		// AJAX获取供应商信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '26,28',
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
			//				njyc.phone.showShortMessage('请上传图片');
			return;
		}
		for (var i = 0; i < picImage.length; i++) {
			path += ',' + $(picImage[i]).val();
		}
		if (path != '') {
			path = path.substring(1);
		}
		return path;
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
	jsRoutineCleaningOrderDetail.init();
});