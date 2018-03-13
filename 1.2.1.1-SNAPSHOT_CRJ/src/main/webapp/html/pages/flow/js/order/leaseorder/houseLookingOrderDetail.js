var respData;
var workOrder;
var isMobile;
var dealerId;
var houseLookingOrderValueMap = {};
var jsHouseLookingOrderDetail = {

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

		$('#areaId').change(function() {
			jsHouseLookingOrderDetail.groupSelect();
		});

		$('#groupId').change(function() {
			jsHouseLookingOrderDetail.butlerSelect();
		});

		// 是否带看
		$("#isLookRadio").change(function() {
			jsHouseLookingOrderDetail.isLookRadio();
		});

		// 是否租房
		$("#isRentRadio").change(function() {
			jsHouseLookingOrderDetail.isRentRadio();
		});
	},

	// 改变是否租房单选
	isRentRadio: function() {
		var isRentVar = $("input[name='isRent']:checked").val();
		if (isRentVar == 'Y') {
			// 隐藏重新派单按钮
			$("#processAgainReassign").addClass("hidden");
			// 隐藏不租原因
			$(".js-noRent-checkbox").addClass("hidden");
			$('input[name=noRent]:checked').attr('checked', false);
		} else {
			var broker_id = $("#broker_id").val();
			if (broker_id != '') {
				
			}
			$("#processAgainReassign").removeClass("hidden");
			// 显示不租原因
			$(".js-noRent-checkbox").removeClass("hidden");
		}
	},

	// 改变是否带看单选
	isLookRadio: function() {
		// 隐藏重新生成派单按钮
		$("#processAgainReassign").addClass("hidden");
		var isLookVar = $("input[name='isLook']:checked").val();
		if (isLookVar == 'Y') {
			// 显示是否租房
			$(".js-isRent-radio").removeClass("hidden");
			// 隐藏带看失败原因
			$(".js-lookFail-checkbox").addClass("hidden");
			$('input[name=lookFail]:checked').attr('checked', false);
		} else {
			$("#processAgainReassign").removeClass("hidden");
			// 显示带看失败的原因
			$(".js-lookFail-checkbox").removeClass("hidden");
			// 隐藏是否租房
			$(".js-isRent-radio").addClass("hidden");
			$('input[name=isRent]:checked').attr('checked', false);
			// 隐藏不租原因
			$(".js-noRent-checkbox").addClass("hidden");
			$('input[name=noRent]:checked').attr('checked', false);
		}
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
					jsHouseLookingOrderDetail.createHouseLookingOrderValueMap(respData);
					// 赋值父工单信息
					jsHouseLookingOrderDetail.generatorData(respData);
					// 赋值各流程信息
					jsHouseLookingOrderDetail.generatorHtml(respData);
					// 初始化各TAB页
					jsHouseLookingOrderDetail.initTab(respData);
				}
			}
		});
	},

	// 看房订单转成MAP
	createHouseLookingOrderValueMap: function(data) {
		var valueList = data.subOrderValueList;
		for (var i = 0; i < valueList.length; i++) {
			var houseLookingOrderValue = valueList[i];
			var key = houseLookingOrderValue.attrPath;
			houseLookingOrderValueMap[key] = houseLookingOrderValue;
		}
	},

	// 赋值父工单信息
	generatorData: function(data) {
		$('.js-tab > li').find("a[href='#" + data.stateName + "']").parent().addClass('active');
		$('#' + data.stateName).addClass('in active');
		$('.js-code').html(data.code);
		var typeName = data.houseName;
		if (typeName == null || typeName == 'null') {
			typeName = "";
		}
		if (isMobile == "Y") {
			$('.js-rental-name').html(typeName);
		} else {
			$('.js-rental-name').html("<a onclick='jsHouseLookingOrderDetail.sigleHouseInfo(" + data.houseId + ")'>" + typeName + "</a>");
		}
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
		$('.js-area-name').html(data.areaName);
		$('.js-group-name').html(data.groupName);
		$('.js-assigned-dealer-name').html(data.butlerName);
		$('.js-channel-name').html(data.channelName);
		$("#assignedDealerPhone").val(data.assignedDealerPhone);
	},

	// 赋值各流程信息
	generatorHtml: function(data) {
		// 客服派单
		// this.customerServAssignHtml(data);
		// 管家接单
		this.butlerTakeOrderHtml(data);
		// 管家执行
		this.butlerExecuteHtml(data);
		// 客服回访
		// this.customerServVisitHtml(data);
		// 用户评价
		this.generatorJudgeHtml(data);
	},

	// 初始化各流程TAB页面
	initTab: function(data) {
		switch (data.state) {
			// case subOrderStateDef.ASSIGN_ORDER: // 客服派单
			// // 显示按钮和备注
			// $('.js-tab >
			// li').find("a[href='#CUSTOMER_SERV_ASSIGN']").parent().addClass('active');
			// $('#CUSTOMER_SERV_ASSIGN').addClass('in active');
			// if (!resFlag) {
			// return;
			// }
			// $("#managerSelect").attr("disabled", false);
			// // 显示指派按钮
			// $("#confirmReassignOrderBtn").removeClass("hidden");
			// $(".js-comments-textarea").attr('readonly', false);
			// break;
			// case subOrderStateDef.REASSIGNING_IN_STAFF_REVIEW: // 客服回访重新指派
			case subOrderStateDef.REASSIGNING:
			case subOrderStateDef.TAKE_ORDER: // 管家接单
				// 显示按钮和备注
				$('.js-tab > li').find("a[href='#BUTLER_TAKE_ORDER']").parent().addClass('active');
				$('#BUTLER_TAKE_ORDER').addClass('in active');
				if (!resFlag) {
					return;
				}
				// 显示按钮
				$("#butlerGetHomeBtn").removeClass("hidden");
				$("#butlerAgainReassign").removeClass("hidden");
				$(".js-house-inspection-comments-textarea").attr('readonly', false);
				break;
			case subOrderStateDef.DO_IN_ORDER:
				// 管家执行，显示按钮和备注
				$('.js-tab > li').find("a[href='#BUTLER_EXECUTE']").parent().addClass('active');
				$('#BUTLER_EXECUTE').addClass('in active');
				if (!resFlag) {
					return;
				}
				// 显示按钮
				/*$("#contactRenter").removeClass("hidden");*/
				$("#rentalAccountPassBtn").removeClass("hidden");
				$("#rentalAccountBtn").removeClass("hidden");
				$('.js-isLook-radio').removeClass("hidden");
				$("input[name='isLook']").attr("disabled", false);
				$("input[name='isRent']").attr("disabled", false);
				$("input[name='noRent']").attr("disabled", false);
				$("input[name='lookFail']").attr("disabled", false);
				// 根据任务编码获取被推荐人信息
				common.ajax({
					url: common.root + '/cascading/recommendInfo.do',
					dataType: 'json',
					data: {
						code: data.code
					},
					async: false,
					loadfun: function(isloadsucc, json) {
						if (isloadsucc) {
							$("#broker_id").val(json.id);
						} else {
							common.alert({
								msg: common.msg.error
							});
						}
					}
				});
				$(".js-rental-account-comments-textarea").attr('readonly', false);

				// 获取第三方号码和回调地址
				common.ajax({
					url: common.root + '/cascading/getCallBackInfo.do',
					dataType: 'json',
					data: {},
					async: false,
					loadfun: function(isloadsucc, json) {
						if (isloadsucc) {
							$("#bindNbr").val(json.bindNbr);
							$("#callBackUrl").val(json.callBackUrl);
						} else {
							common.alert({
								msg: common.msg.error
							});
						}
					}
				});

				break;
				// case subOrderStateDef.STAFF_REVIEW: // 客服回访
				// case subOrderStateDef.WAIT_2_TACE: // 客服回访待追踪
				// // 显示按钮和备注
				// $('.js-tab >
				// li').find("a[href='#CUSTOMER_SERV_VISIT']").parent().addClass('active');
				// $('#CUSTOMER_SERV_VISIT').addClass('in active');
				// if (!resFlag) {
				// return;
				// }
				// // 显示按钮
				// $("#passInStaffReview").removeClass("hidden");
				// $("#reassignOrderBtn").removeClass("hidden");
				// $("#staffTace").removeClass("hidden");
				// $(".js-marketing-executive-audit-comments-textarea").attr('readonly',
				// false);
				// break;
		}
	},

	// 客服派单
	customerServAssignHtml: function(data) {
		var butlerId = data.butlerId;
		// AJAX获取管家信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '22',
          type: workOrder.type
      },
			async: false,
			loadfun: function(isloadsucc, json) {
				if (isloadsucc) {
					var html = "<option value=''> 请选择...</option>";
					for (var i = 0; i < json.length; i++) {
						html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
					}
					$('#managerSelect').html(html);
					if (butlerId != '' && butlerId != 'null' && butlerId != null && butlerId != undefined) {
						$("#managerSelect option[value='" + data.butlerId + "']").attr("selected", "selected");
					} else {
						$('#managerSelect').select2({
							'width': '200px'
						});
					}
				} else {
					common.alert({
						msg: common.msg.error
					});
				}
			}
		});

		// 赋值客服订单说明
		if (data.attrCatg.code == 'HOUSE_LOOKING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'CUSTOMER_SERV_ASSIGN') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "HOUSE_LOOKING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.";
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
		var text = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-comments-textarea').text(text);
		}
	},

	// 管家接单
	butlerTakeOrderHtml: function(data) {
		// 赋值客服订单说明
		if (data.attrCatg.code == 'HOUSE_LOOKING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'BUTLER_TAKE_ORDER') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "HOUSE_LOOKING_ORDER_PROCESS.BUTLER_TAKE_ORDER.";
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
		var text = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.BUTLER_TAKE_ORDER.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-house-inspection-comments-textarea').text(text);
		}
	},

	// 管家执行
	butlerExecuteHtml: function(data) {

		// AJAX获取管家信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '22',
          type: workOrder.type
      },
			async: false,
			loadfun: function(isloadsucc, json) {
				if (isloadsucc) {
					var html = "<option value=''> 请选择...</option>";
					for (var i = 0; i < json.length; i++) {
						html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
					}
					$('#managerSelect').html(html);
					$('#managerSelect').select2({
						'width': '200px'
					});
				} else {
					common.alert({
						msg: common.msg.error
					});
				}
			}
		});

		if (data.attrCatg.code == 'HOUSE_LOOKING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'BUTLER_EXECUTE') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "HOUSE_LOOKING_ORDER_PROCESS.BUTLER_EXECUTE.";
						var childAttr = childAttrList[j];
						var attrPath = defaultPath + childAttr.code;
						childAttr.attrPath = attrPath;
						// 备注
						if (childAttr.code == 'COMMENTS') {
							$('.js-rental-account-comments-label').html(childAttr.name);
							$('.js-rental-account-comments-textarea').data('attr', childAttr);
						}
						// 是否带看
						if (childAttr.code == 'IS_LOOK') {
							$('.js-isLook-radio').data('attr', childAttr);
						}
						// 是否租房
						if (childAttr.code == 'IS_RENT') {
							$('.js-isRent-radio').data('attr', childAttr);
						}
						// 不租原因
						if (childAttr.code == 'NO_RENT') {
							$('.js-noRent-checkbox').data('attr', childAttr);
						}
						// 带看失败原因
						if (childAttr.code == 'LOOK_FAIL') {
							$('.js-lookFail-checkbox').data('attr', childAttr);
						}
					}
				}
			}
		}
		// 设置备注回显
		var text = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.BUTLER_EXECUTE.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			$('.js-rental-account-comments-textarea').text(text);
		}

		// 回显是否带看
		var isLook = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.BUTLER_EXECUTE.IS_LOOK'];
		if (isLook != '' && isLook != 'null' && isLook != null && isLook != undefined) {
			$(".js-isLook-radio").removeClass("hidden");
			isLook = isLook.textInput;
			$("#isLookRadio").find("input[value='" + isLook + "']").attr('checked', 'checked');
			if (isLook == "Y") {
				// 如果带看了，显示是否租房
				$(".js-isRent-radio").removeClass("hidden");
				var isRent = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.BUTLER_EXECUTE.IS_RENT'];
				if (isRent != '' && isRent != 'null' && isRent != null && isRent != undefined) {
					isRent = isRent.textInput;
					$("#isRentRadio").find("input[value='" + isRent + "']").attr('checked', 'checked');
				}
				if (isRent == "N") {
					// 如果没有租房，显示不租原因
					$(".js-noRent-checkbox").removeClass("hidden");
					// 显示重新指派
					$("#processAgainReassign").removeClass("hidden");
					var noRent = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.BUTLER_EXECUTE.NO_RENT'];
					if (noRent != '' && noRent != 'null' && noRent != null && noRent != undefined) {
						noRent = noRent.textInput;
						var noRentArray = noRent.split(",");
						for (var i = 0; i < noRentArray.length; i++) {
							$("#noRentCheckbox").find("input[value='" + noRentArray[i] + "']").attr('checked', 'checked');
						}
					}
				}
			} else {
				// 如果没带看，显示带看失败原因
				$(".js-lookFail-checkbox").removeClass("hidden");
				// 显示重新指派
				$("#processAgainReassign").removeClass("hidden");
				var lookFail = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.BUTLER_EXECUTE.LOOK_FAIL'];
				if (lookFail != '' && lookFail != 'null' && lookFail != null && lookFail != undefined) {
					lookFail = lookFail.textInput;
					var lookFailArray = lookFail.split(",");
					for (var i = 0; i < lookFailArray.length; i++) {
						$("#lookFailCheckbox").find("input[value='" + lookFailArray[i] + "']").attr('checked', 'checked');
					}
				}
			}
		}
	},

	// 客服回访TAB页
	customerServVisitHtml: function(data) {
		if (data.attrCatg.code == 'HOUSE_LOOKING_ORDER_PROCESS') {
			for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
				var childCatg = data.attrCatg.attrCatgChildren[i];
				if (childCatg.code == 'CUSTOMER_SERV_VISIT') {
					var childAttrList = childCatg.attrChildren;
					for (var j = 0; j < childAttrList.length; j++) {
						var defaultPath = "HOUSE_LOOKING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.";
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
					}
				}
			}
		}
		var text = houseLookingOrderValueMap['HOUSE_LOOKING_ORDER_PROCESS.CUSTOMER_SERV_VISIT.COMMENTS'];
		if (text != '' && text != 'null' && text != null && text != undefined) {
			text = text.textInput;
			// 设置备注回显
			$('.js-marketing-executive-audit-comments-textarea').text(text);
		}
	},

	// 用户评价
	generatorJudgeHtml: function(data) {
		if (data.attrCatg.code == 'HOUSE_LOOKING_ORDER_PROCESS') {
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
				njyc.phone.showPic(jsHouseLookingOrderDetail.changeImagePath(picurls), 'js-judge-audit-image-url-mobile');
				if (data.state != 'D') {
					$('#image-upurl-1').addClass('hidden');
					$('#image-upurl-1 b').addClass('hidden');
				}
			} else {
				if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
					jsHouseLookingOrderDetail.initPicurls(picurls, 'image-upurl-1', true);
				} else {
					jsHouseLookingOrderDetail.initPicurls(picurls, 'image-upurl-1', false);
				}
			}
		}
	},

	changeImagePath: function(images) {
		if (!images || images.length == 0) {
			return '';
		}
		return images.replace(/,/g, '|');
	},

	// 图片显示
	initPicurls: function(picurls, id, flag) {
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
		// 判断是否选择管家
		if ($('#managerSelect').val() == null || $('#managerSelect').val() == "") {
			common.alert({
				msg: "请选择指派人！"
			});
			return;
		}

		var manager = $('#managerSelect option:selected').text();
		common.alert({
			msg: "确定指派给管家(" + manager + ")吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-comments-textarea').data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'butlerId': $('#managerSelect').val(),
						'subOrderValueList': [{
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/assignOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 管家接单
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
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-house-inspection-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/takeOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 管家重新指派按钮
	butlerAgainReassign: function() {
		// 隐藏重新指派按钮，显示管家列表，显示返回、指派按钮
		$("#butlerGetHomeBtn").addClass("hidden");
		$("#butlerAgainReassign").addClass("hidden");
		$("#butlerReassign").removeClass("hidden");
		$("#butlerCancelReassign").removeClass("hidden");
		$(".js-butler-reassign-manager-div").removeClass("hidden");

		// AJAX获取管家信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '22',
          type: workOrder.type
      },
			async: false,
			loadfun: function(isloadsucc, json) {
				if (isloadsucc) {
					var html = "<option value=''> 请选择...</option>";
					for (var i = 0; i < json.length; i++) {
						html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
					}
					$('#butlerAgainReassignSelect').html(html);
					$('#butlerAgainReassignSelect').select2({
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

	// 管家接单返回按钮
	butlerCancelReassign: function() {
		// 显示重新指派、通过按钮，隐藏管家列表、返回、指派按钮
		$("#butlerGetHomeBtn").removeClass("hidden");
		$("#butlerAgainReassign").removeClass("hidden");
		$("#butlerReassign").addClass("hidden");
		$("#butlerCancelReassign").addClass("hidden");
		$(".js-butler-reassign-manager-div").addClass("hidden");
	},

	// 管家重新指派操作
	butlerReassign: function() {
		// 判断是否选择管家
		if ($('#butlerAgainReassignSelect').val() == null || $('#butlerAgainReassignSelect').val() == "") {
			common.alert({
				msg: "请选择指派人！"
			});
			return;
		}
		var manager = $('#butlerAgainReassignSelect option:selected').text();
		common.alert({
			msg: "确定重新指派给管家（" + manager + "）吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'butlerId': $('#butlerAgainReassignSelect').val(),
						'subOrderValueList': [{
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-house-inspection-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/reassignOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 管家执行订单
	processOrder: function(flag) {
		// 判断是否带看
		var isLookVar = $("input[name='isLook']:checked").val() == undefined ? "" : $("input[name='isLook']:checked").val() == "undefined" ? "" : $("input[name='isLook']:checked").val();
		// 是否租房
		var isRentVar = $("input[name='isRent']:checked").val() == undefined ? "" : $("input[name='isRent']:checked").val() == "undefined" ? "" : $("input[name='isRent']:checked").val();
		// 不租原因
		var noRentLen = $("input[name='noRent']:checked").length;
		// 带看失败
		var lookFailLen = $("input[name='lookFail']:checked").length;
		if (isLookVar == "") {
			common.alert({
				msg: '请选择是否带看!'
			});
			return;
		} else {
			if (isLookVar == 'Y') {
				if (isRentVar == "") {
					common.alert({
						msg: '请选择是否租房!'
					});
					return;
				} else {
					if (isRentVar == 'N') {
						// 判断不租原因
						if (noRentLen == 0) {
							common.alert({
								msg: '请选择不租原因!'
							});
							return;
						}
					}
				}
			} else {
				// 判断带看失败原因
				if (lookFailLen == 0) {
					common.alert({
						msg: '请选择带看失败原因!'
					});
					return;
				}
			}
		}

		// 获取不租原因所有的值
		var noRentVar = "";
		if (noRentLen > 0) {
			$("input[name='noRent']:checked").each(function() {
				noRentVar += $(this).val() + ",";
			});
			noRentVar = noRentVar.substring(0, noRentVar.length - 1);
		}

		// 获取失败带看原因所有的值
		var lookFailVar = "";
		if (lookFailLen > 0) {
			$("input[name='lookFail']:checked").each(function() {
				lookFailVar += $(this).val() + ",";
			});
			lookFailVar = lookFailVar.substring(0, lookFailVar.length - 1);
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
					var isLook = $('.js-isLook-radio').data('attr'); // 是否带看
					var isRent = $('.js-isRent-radio').data('attr'); // 是否租房
					var noRent = $('.js-noRent-checkbox').data('attr'); // 不租原因
					var lookFail = $('.js-lookFail-checkbox').data('attr'); // 带看失败原因
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'submitFlag': flag,
						'subOrderValueList': [{
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-rental-account-comments-textarea').val()
						}, {
							// 'houseLookingOrderId' : respData.id,
							'attrId': isLook.id,
							'attrPath': isLook.attrPath,
							'textInput': isLookVar
						}, {
							// 'houseLookingOrderId' : respData.id,
							'attrId': isRent.id,
							'attrPath': isRent.attrPath,
							'textInput': isRentVar
						}, {
							// 'houseLookingOrderId' : respData.id,
							'attrId': noRent.id,
							'attrPath': noRent.attrPath,
							'textInput': noRentVar
						}, {
							// 'houseLookingOrderId' : respData.id,
							'attrId': lookFail.id,
							'attrPath': lookFail.attrPath,
							'textInput': lookFailVar
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/processOrder.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 管家选择重新生成订单
	processAgainReassign: function() {
		// 隐藏按钮保存和提交按钮
		$("#rentalAccountBtn").addClass("hidden");
		$("#rentalAccountPassBtn").addClass("hidden");
		$("#processAgainReassign").addClass("hidden");
		$(".js-isLook-radio").addClass("hidden");
		$(".js-isRent-radio").addClass("hidden");
		$(".js-noRent-checkbox").addClass("hidden");
		$(".js-lookFail-checkbox").addClass("hidden");

		// 显示返回和提交按钮
		$("#processReassign").removeClass("hidden");
		$("#processCancelReassign").removeClass("hidden");

		// 加载区域
		var type = "3"; // 区域
		var fatherid = ""; // 上级Id
		common.loadArea(fatherid, type, function(json) {
			var html = "";
			for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
			}
			$('#areaId').append(html);
			$('#areaId').select2({
				'width': '150px'
			});
			$('#groupId').select2({
				'width': '150px'
			});
			$('#butlerId').select2({
				'width': '150px'
			});
		});

		$(".js-reassign-div").removeClass("hidden");

	},

	// 根据小区ID，获取管家信息
	butlerSelect: function() {
		var groupId = $("#groupId").val();
		common.ajax({
			url: common.root + '/cascading/butlerList.do',
			dataType: 'json',
			data: {
				groupId: groupId
			},
			loadfun: function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.id != '' && data.id != undefined && data.id != 'undefined') {
						$("#butlerId option[value='" + data.id + "']").attr("selected", "selected");
						$("#select2-butlerId-container").text(data.name);
					} else {
						$("#butlerId option[value='']").attr("selected", "selected");
						$("#select2-butlerId-container").text("请选择..");
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});
	},

	// 根据区域ID，获取小区、管家信息
	groupSelect: function() {
		var areaId = $("#areaId").val();
		// 首先清空小区下拉信息
		$("#groupId").empty();
		if (areaId == "") {
			// 如果没有选中区域
			$("#groupId").prepend("<option value=''>请选择..</option>");
			$("#select2-groupId-container").text("请选择..");
		} else {
			jsHouseLookingOrderDetail.getGroupList(areaId);
		}

		// 清空管家信息
		var butlerId = $("#butlerId").val();
		$("#butlerId").empty();
		if (areaId == "") {
			// 如果没有选中区域
			$("#butlerId").prepend("<option value=''>请选择..</option>");
			$("#select2-butlerId-container").text("请选择..");
		} else {
			jsHouseLookingOrderDetail.getButlerList(areaId);
		}
	},

	// 级联管家信息
	getButlerList: function(areaId) {
		common.ajax({
			url: common.root + '/cascading/butlerList.do',
			dataType: 'json',
			data: {
				areaId: areaId
			},
			loadfun: function(isloadsucc, data) {
				if (isloadsucc) {
					var len = data.length;
					if (len == 0) {
						$("#butlerId").prepend("<option value=''>请选择..</option>");
						$("#select2-butlerId-container").text("请选择..");
					} else {
						var html = '<option value="">请选择..</option>';
						for (var i = 0; i < data.length; i++) {
							html += '<option value="' + data[i].id + '" >' + data[i].name + '</option>';
						}
						$("#butlerId").append(html);
						$("#select2-butlerId-container").text("请选择..");
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});
	},

	// 级联小区信息
	getGroupList: function(areaId) {
		common.ajax({
			url: common.root + '/cascading/groupList.do',
			dataType: 'json',
			data: {
				areaId: areaId
			},
			loadfun: function(isloadsucc, data) {
				if (isloadsucc) {
					var len = data.length;
					if (len == 0) {
						$("#groupId").prepend("<option value=''>请选择..</option>");
						$("#select2-groupId-container").text("请选择..");
					} else {
						var html = '<option value="">请选择..</option>';
						for (var i = 0; i < data.length; i++) {
							html += '<option value="' + data[i].id + '" >' + data[i].group_name + '</option>';
						}
						$("#groupId").append(html);
						$("#select2-groupId-container").text("请选择..");
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});
	},

	// 管家执行返回操作
	processCancelReassign: function() {
		// 隐藏返回和提交按钮
		$("#processReassign").addClass("hidden");
		$("#processCancelReassign").addClass("hidden");
		$(".js-reassign-div").addClass("hidden");

		// 显示返回和提交按钮
		$("#rentalAccountBtn").removeClass("hidden");
		$("#rentalAccountPassBtn").removeClass("hidden");
		$("#processAgainReassign").removeClass("hidden");
		$(".js-isLook-radio").removeClass("hidden");
	},

	// 点击重新生成新订单，并且提交当前订单
	processReassign: function() {
		var broker_id = $("#broker_id").val();
		var flag = true;
		// 校验
		var areaId = $("#areaId").val();
		if (areaId == '') {
			flag = false;
			common.alert({
				msg: "请选择区域！"
			});
			return;
		}
		var butlerId = $("#butlerId").val();
		if (butlerId == '') {
			flag = false;
			common.alert({
				msg: "请选择管家！"
			});
			return;
		}
		// 预约时间
		var d = new Date();
		d.setDate(d.getDate() + 1);
		var m = d.getMonth() + 1;
		m = m < 10 ? "0" + m : m;
		var day = d.getDate();
		day = day < 10 ? "0" + day : day;
		var time = d.getFullYear() + '-' + m + '-' + day + " " + d.getHours() + ":" + d.getMinutes() + ":" + d.getSeconds();
		
		var param;
		if (broker_id == '') {
			param = {
					'houseId': "",
					'comments': $('.js-order-comments').html(),
					'userName': $('.js-user-name').html(),
					'userMobile': $('.js-user-phone').html(),
					'orderName': "看房订单",
					'channel': respData.channel,
					'groupId': $('#groupId').val(),
					'areaId': $('#areaId').val(),
					'assignedDealerId': $('#butlerId').val(),
					'appointmentDate': time
			};
		} else {
			param = {
					'houseId': "",
					'comments': $('.js-order-comments').html(),
					'userName': $('.js-user-name').html(),
					'userMobile': $('.js-user-phone').html(),
					'orderName': "经纪人推荐",
					'channel': "K",
					'groupId': $('#groupId').val(),
					'areaId': $('#areaId').val(),
					'assignedDealerId': $('#butlerId').val(),
					'appointmentDate': time,
					'recommendId': broker_id
			};			
		}

		common.ajax({
			url: common.root + '/houseLookingOrder/inputHouseLookingOrderInfo.do',
			dataType: 'json',
			contentType: 'application/json; charset=utf-8',
			encode: false,
			data: JSON.stringify(param),
			loadfun: function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {
					if (data.state == 1) { // 操作成功
						common.alert({
							msg: '操作成功'
						});

						// 提交当前订单，默认选中不租原因（选择其他区域房间->noRentG）
						// 选择带看
						$("#isLookA").attr("checked", "checked");
						// 选择不租
						$("#isRentB").attr("checked", "checked");
						// 选择不租原因
						$("#noRentG").attr("checked", "checked");
						jsHouseLookingOrderDetail.processOrder('Y');
					} else if (data.state == -4) { // 操作失败
						common.alert({
							msg: '当前房源已生成订单，请勿重复操作'
						});
					} else {
						common.alert({
							msg: common.msg.error
						});
					}
				} else {
					common.alert({
						msg: common.msg.error
					});
				}
			}
		});
	},

	// 双呼
	contactRenter: function() {
		common.alert({
			msg: '确定联系租客？',
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					// 租客手机号
					var callee = $(".js-user-phone").text();
					callee = "+86" + callee;
					// 管家手机号
					var caller = $("#assignedDealerPhone").val();
					caller = "+86" + caller;
					// 第三方号码
					var bindNbr = $("#bindNbr").val();
					// 回调地址
					var callBackUrl = $("#callBackUrl").val();
					var param = {
						'calleeNbr': callee,
						'callerNbr': caller,
						'bindNbr': bindNbr,
						'callBackUrl': callBackUrl
					};
					common.ajax({
						url: common.root + '/huawei/click2Call.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							if (isloadsucc) {
								alert(data.resultdesc);
							} else {
								common.alert({
									msg: common.msg.error
								});
							}
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

		// AJAX获取管家信息
		common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '22',
          type: workOrder.type
      },
			async: false,
			loadfun: function(isloadsucc, json) {
				if (isloadsucc) {
					var html = "<option value=''> 请选择...</option>";
					for (var i = 0; i < json.length; i++) {
						html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
					}
					$('#managerSelect1').html(html);
					$('#managerSelect1').select2({
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
		common.alert({
			msg: "确定重新指派给管家（" + manager + "）吗？",
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
					var passInStaffReviewData = $('#passInStaffReview').data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'butlerId': $('#managerSelect1').val(),
						'subOrderValueList': [{
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'houseLookingOrderId' : respData.id,
							'attrId': passInStaffReviewData.id,
							'attrPath': passInStaffReviewData.attrPath,
							'textInput': 'N'
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/reassignOrderInStaffReview.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
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
		common.alert({
			msg: '确定审批通过？',
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
					var passInStaffReviewData = $('#passInStaffReview').data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}, {
							// 'houseLookingOrderId' : respData.id,
							'attrId': passInStaffReviewData.id,
							'attrPath': passInStaffReviewData.attrPath,
							'textInput': 'Y'
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/passInStaffReview.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
						}
					});
				}
			}
		});
	},

	// 客服订单追踪
	staffTace: function() {
		common.alert({
			msg: '确定待追踪？',
			confirm: true,
			fun: function(action) {
				if (action) {
					common.load.load();
					var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
					var param = {
						'code': respData.code,
						'dealerId': dealerId,
						'subOrderValueList': [{
							// 'houseLookingOrderId' : respData.id,
							'attrId': commentsData.id,
							'attrPath': commentsData.attrPath,
							'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
						}]
					};
					common.ajax({
						url: common.root + '/houseLookingOrder/staffTace.do',
						dataType: 'json',
						contentType: 'application/json; charset=utf-8',
						encode: false,
						data: JSON.stringify(param),
						loadfun: function(isloadsucc, data) {
							common.load.hide();
							jsHouseLookingOrderDetail.closeOpenedWin(isloadsucc, data);
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
					msg: '操作成功'
				});
				if (isMobile == 'Y') {
					window.location.href = 'http://manager.room1000.com?TASK_SUBMIT_SUCCESS=1';
				} else {
					// common.closeWindow('houseLookingOrderDetail', 3);
				}
			} else {
				common.alert({
					msg: common.msg.error
				});
			}
		}
	},

	sigleHouseInfo: function(id) {
		// 查看单个房间信息
		common.openWindow({
			name: 'sigleHouseInfo',
			type: 1,
			data: {
				id: id
			},
			title: '查看房间信息',
			url: '/html/pages/house/houseInfo/sigleHouseInfo.html'
		});
	},

};
$(function() {
	jsHouseLookingOrderDetail.init();
});