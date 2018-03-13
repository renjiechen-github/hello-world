var orderList = {
	/**
	 * 初始化操作信息
	 */
	init : function() {

		// 初始化时间
		var now_time = new Date();
		var pre_time = new Date();
		pre_time.setDate(pre_time.getDate() - 1);
		// 年
		var query_year = now_time.getFullYear();
		var pre_query_year = pre_time.getFullYear();
		// 月
		var query_month = now_time.getMonth() + 1;
		query_month = query_month < 10 ? "0" + (query_month) : query_month;
		var pre_query_month = pre_time.getMonth() + 1;
		pre_query_month = pre_query_month < 10 ? "0" + (pre_query_month) : pre_query_month;
		// 日
		var query_day = now_time.getDate();
		query_day = query_day < 10 ? "0" + query_day : query_day;
		var pre_query_day = pre_time.getDate();
		pre_query_day = pre_query_day < 10 ? "0" + pre_query_day : pre_query_day;

		var query_begin_time = pre_query_year + "-" + pre_query_month + "-" + pre_query_day + " 00:00:00";
		var query_end_time = query_year + "-" + query_month + "-" + query_day + " 23:59:59";

		$("#createtime").daterangepicker({
			startDate : query_begin_time,
			endDate : query_end_time,
			timePicker12Hour : false,
			separator : '~',
			format : 'YYYY-MM-DD HH:mm:ss'
		}, function(start, end, label) {
			console.log(start.toISOString(), end.toISOString(), label);
		});
		$("#createtime").val(query_begin_time + "~" + query_end_time);
		this.loadData();
		this.typeInit();
		this.loadEvent();
		this.stateInit();
		this.reportInit();
	},

	reportInit : function() {
		common.ajax({
			url : common.root + '/workOrder/getTypeCount.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var Aarr = new Array();
					var Barr = new Array();
					var Carr = new Array();
					var Darr = new Array();
					var Earr = new Array();
					var Farr = new Array();
					var Garr = new Array();
					var Harr = new Array();
					var Iarr = new Array();
					var Allarr = new Array();
					for (var i = 0; i < data.typeCountList.length; i++) {
						var datas = data.typeCountList[i];
						switch (datas.type) {
						case "A":
							Aarr = orderList.stateNullRes(datas, Aarr);
							break;
						case "B":
							Barr = orderList.stateNullRes(datas, Barr);
							break;
						case "C":
							Carr = orderList.stateNullRes(datas, Carr);
							break;
						case "D":
							Darr = orderList.stateNullRes(datas, Darr);
							break;
						case "E":
							Earr = orderList.stateNullRes(datas, Earr);
							break;
						case "F":
							Farr = orderList.stateNullRes(datas, Farr);
							break;
						case "G":
							Garr = orderList.stateNullRes(datas, Garr);
							break;
						case "H":
							Harr = orderList.stateNullRes(datas, Harr);
							break;
						case "I":
							Iarr = orderList.stateNullRes(datas, Iarr);
							break;
						case "All":
							Allarr = orderList.stateNullRes(datas, Allarr);
							break;
						}
					}
					$("#typeA").html(orderList.arrayNnullres(Aarr[0]) + "(" + orderList.arrayNnullres(Aarr[1]) + ")");
					$("#typeB").html(orderList.arrayNnullres(Barr[0]) + "(" + orderList.arrayNnullres(Barr[1]) + ")");
					$("#typeC").html(orderList.arrayNnullres(Carr[0]) + "(" + orderList.arrayNnullres(Carr[1]) + ")");
					$("#typeD").html(orderList.arrayNnullres(Darr[0]) + "(" + orderList.arrayNnullres(Darr[1]) + ")");
					$("#typeE").html(orderList.arrayNnullres(Earr[0]) + "(" + orderList.arrayNnullres(Earr[1]) + ")");
					$("#typeF").html(orderList.arrayNnullres(Farr[0]) + "(" + orderList.arrayNnullres(Farr[1]) + ")");
					$("#typeG").html(orderList.arrayNnullres(Garr[0]) + "(" + orderList.arrayNnullres(Garr[1]) + ")");
					$("#typeH").html(orderList.arrayNnullres(Harr[0]) + "(" + orderList.arrayNnullres(Harr[1]) + ")");
					$("#typeI").html(orderList.arrayNnullres(Iarr[0]) + "(" + orderList.arrayNnullres(Iarr[1]) + ")");
					$("#count0").html(orderList.arrayNnullres(Allarr[0]) + "(" + orderList.arrayNnullres(Allarr[1]) + ")");
				}
			}
		})
	},
	stateNullRes : function(datas, array) {
		if (datas.state != null) {
			array[1] = datas.typeCnt;// 已完成
		} else {
			array[0] = datas.typeCnt;// 未完成
		}
		return array;
	},
	arrayNnullres : function(data) {
		if (data != null && data != "" && data != "undefined") {

		} else {
			data = 0;
		}
		return data;
	},
	loadEvent : function() {
		common.ajax({
			url : common.root + '/workOrder/getWorkOrderTypeByPermission.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = "<option value=''>请选择...</option>";
					var date = data;
					for (var i = 0; i < date.length; i++) {
						if (date[i].item_id == '9') {
							// 新增例行保洁订单无需展示
							continue;
						}
						html += '<option  value="' + date[i].item_id + '" >' + date[i].item_name + '</option>';
					}
					$('#addul').html(html);
				}
			}
		});

		$("#addul").change(function() {
			orderList.addButtype($('#addul').val(), $('#addul option:selected').text());
		});
		// enter监听事件
		$('#keyword').keydown(function(e) {
			if (e.which == "13") {
				$("#serach").click();
				return false;// 禁用回车事件
			}
		});
		// $("#order_type").change(function() {
		// $("#search").click();
		// });
		// $("#order_state").change(function() {
		// $("#search").click();
		// });
		// $("#order_grade").change(function() {
		// $("#search").click();
		// });
	},
	typeInit : function() {
		common.ajax({
			url : common.root + '/workOrder/getWorkOrderType.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = "<option value=''>请选择...</option>";
					var date = data.workOrderTypeList;
					for (var i = 0; i < date.length; i++) {
						html += '<option  value="' + date[i].type + '" >' + date[i].typeName + '</option>';
					}
					$("#order_type").html(html);
				}
			}
		})
	},
	stateInit : function() {
		common.ajax({
			url : common.root + '/workOrder/getSubOrderState.do	',
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = "<option value=''>请选择...</option>";
					var date = data.subOrderStateList;
					for (var i = 0; i < date.length; i++) {
						if (date[i].state == "s") {
							continue;
						}
						html += '<option  value="' + date[i].state + '" >' + date[i].stateName + '</option>';
					}
					$("#order_state").html(html);
				}
			}
		})
	},

	/**
	 * 加载数据信息
	 */
	loadData : function() {
		table.init({
			id : '#workOrderTable',
			url : common.root + '/workOrder/getWorkOrderList.do',
			columns : [ "code", "name", "type_name", {
				name : "created_date_str",
				isover : true,
				isshow : true,
				title : '创建日期',
			}, "user_name", "user_phone", "sub_state_name", {
				name : "orderCommentaryList",
				isover : true,
				isshow : true,
				title : '评分',

			}, "sub_assigned_dealer_name", "totalTime", "currentStepTime", {
				name : "sub_comments",
				isover : true,
				isshow : false,
				title : '订单详情'
			}, {
				name : "sub_assigned_dealer_role_name",
				isover : true,
				isshow : false,
				title : '当前操作角色'
			}, {
				name : "address",
				isover : true,
				isshow : false,
				title : "地址"
			}, {
				name : "agreement_code",
				isover : true,
				isshow : false,
				title : "收房合同编码"
			} ],
			// 设置默认排序
			aaSorting : [ [ 5, 'desc' ] ],
			param : function() {
				var a = new Array();
				var createtime = $('#createtime').val();
				var createdDateStart;
				var createdDateEnd;
				if (createtime == "") {
					createdDateStart = "";
					createdDateEnd = "";
				} else {
					var createtimeLen = createtime.split("~");
					createdDateStart = createtimeLen[0];
					createdDateEnd = createtimeLen[1];
				}

				a.push({
					name : "type",
					value : $('#order_type').val()
				});
				a.push({
					name : "subOrderState",
					value : $('#order_state').val()
				});
				a.push({
					name : "totalScore",
					value : $('#order_grade').val()
				});
				a.push({
					name : "keyword",
					value : $('#keyword').val()
				});
				a.push({
					name : "createdDateStart",
					value : createdDateStart
				});
				a.push({
					name : "createdDateEnd",
					value : createdDateEnd
				});
				return a;
			},
			aoColumnDefs : [ {
				"bSortable" : false,
				"aTargets" : [ 0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12 ]
			} ],
			bnt : [ {
				name : '指派订单',
				isshow : function(data, row) {
					var resReturn = false;
					if (data.sub_state_name == "指派订单") {
						if (data.assignPermission == "1") {
							resReturn = true;
						}
					}
					return resReturn;
				},
				fun : function(data, row) {
					rowdata = data;
					var url = '/html/pages/order_flow/order_newAction/dispatch.html';
					var name = "dispatch";
					common.openWindow({
						type : 1,
						name : name,
						title : "派单",
						url : url
					});
				}
			}, {
				name : '关闭订单',
				isshow : function(data, row) {
					var resReturn = false;//
					if (data.type != "A") {
						if (data.type == "B")// 保洁订单
						{
							if ("K".indexOf(data.sub_order_state) > -1) // 保洁订单为等待客户支付时可以关闭
							{
								if (data.closePermission == "1") {
									resReturn = true;
								}
							}
						} else {
							if ("ABC".indexOf(data.sub_order_state) > -1) // 其他订单派单时、接单时可以关闭
							{
								if (data.closePermission == "1") {
									resReturn = true;
								}
							}
						}
					}
					return resReturn;
				},
				fun : function(data, row) {
					common.alert({
						msg : "是否确定关闭订单？",
						confirm : true,
						fun : function(action) {
							if (action) {
								common.load.load();
								var param = {
									'workOrderId' : data.id
								};
								common.ajax({
									url : common.root + '/workOrder/endProcess.do',
									dataType : 'json',
									contentType : 'application/json; charset=utf-8',
									encode : false,
									data : JSON.stringify(param),
									loadfun : function(isloadsucc, data) {
										if (isloadsucc) {
											common.load.hide();
											common.alert({
												msg : "操作成功！"
											});
											table.refreshRedraw('workOrderTable');
										} else {
											common.load.hide();
											common.alert({
												msg : common.msg.error
											});
										}
									}
								});
							}
						}
					});
				}
			}, {
				name : '修改订单',
				isshow : function(data, row) {
					var resReturn = false;
					if ("ABCDP".indexOf(data.sub_order_state) > -1) {
						if (data.updatePermission == "1") {
							resReturn = true;
						}
					}
					return resReturn;
				},
				fun : function(data, row) {
					common.setCookie('orderid', data.id, null, null, null, null);
					var url = '/html/pages/order_flow/order_newAction/orderUpdateDetail.html';
					window.open(url);
					// var title = rowdata.type_name;
					// var name = "orderupdate";
					// common.openWindow({
					// type : 3,
					// name : name,
					// title : "修改订单",
					// url : url
					// });
				}
			}, {
				name : '撤销支付',
				isshow : function(data, row) {
					var resReturn = false;
					return resReturn;
				},
				fun : function(data, row) {
					rowdata = data;
					var urlp = "";
					common.alert({
						msg : '是否撤销支付？',
						confirm : true,
						fun : function(action) {
							if (action) {

							}
						}
					});
				}
			}, {
				name : '查看详情',
				isshow : function(data, row) {
					var resReturn = false;
					if (data.queryPermission == "1") {
						resReturn = true;
					}
					return resReturn;
				},
				fun : function(data, row) {
					common.setCookie('resFlag' + (data.id), '0', null, null, null, null);
					resFlag = false;
					rowdata = data;
					var title = rowdata.type_name;
					window.open(common.root + '/workOrder/path2WorkOrderDetailInfoPage.do?id=' + rowdata.id + '&isMobile=N');
					// common.openWindow({
					// url : common.root + '/workOrder/path2WorkOrderDetailPage.do?id=' +
					// rowdata.id + '&isMobile=N',
					// name : 'workOrderDetail',
					// type : 3,
					// title : title
					// });
				}
			} ],
			bFilter : false,
			overflowfun : function(aData, head) {
				var html = "";
				var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
				var param = {
					'workOrderId' : aData.id
				};
				common.ajax({
					url : common.root + '/workOrder/getHis.do',
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					encode : false,
					data : JSON.stringify(param),
					async : false,
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							sOut += '<tr><th>执行人</th>' + '<th>步骤名称</th>' + ' <th>执行时间</th>' + '  <th>处理步骤时长</th><th>备注</th>' + ' </tr>';
							var resData = data.subOrderHisList;
							var date = "";
							var length = resData.length;
							for (var i = 0; i < length; i++) {
								if (i == 0) {
									date = resData[0].createdDate;
								}
								var changeMessage;
								var subOrderValueHisList = resData[i].subOrderValueList;
								if (subOrderValueHisList == null || subOrderValueHisList.length == 0) {
									changeMessage = "";
								} else {
									for (var j = 0; j < subOrderValueHisList.length; j++) {
										if (subOrderValueHisList[j].attrPath.endsWith('.COMMENTS') || subOrderValueHisList[j].attrPath.endsWith('.HOUSE_INSPECTION_COMMENTS')) {
											changeMessage = subOrderValueHisList[j].textInput;
											break;
										}
									}
								}
								var stateName = "";
								if (resData[i].stateName != null && resData[i].stateName != "") {
									stateName = resData[i].stateName;
								}
								sOut += '<tr>' + ' <td>' + resData[i].updatePersonName + '</td>' + ' <td>' + stateName + '</td>' + ' <td>' + resData[i].updateDate + '</td>' + ' <td>'
										+ resData[i].currentStepTime + '</td>' + ' <td>' + changeMessage + '</td>' + ' </tr>';
								date = resData[i].updateDate;
							}
						}
					}
				});
				// 处理创建人
				var create_oper = "";
				if (aData.created_staff_name != null && aData.created_staff_name != "") {
					create_oper = aData.created_staff_name
				} else if (aData.created_user_name != null && aData.created_user_name != "") {
					create_oper = aData.created_user_name;
				} else if (aData.user_name != null && aData.user_name != "") {
					create_oper = aData.user_name;
				}
				sOut += '<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">创建人:&nbsp;&nbsp;&nbsp;' + create_oper + '</div>';
				sOut += '<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">预约时间:&nbsp;&nbsp;&nbsp;' + aData.appointment_date_str + '</div>';
				// 订单备注
				sOut += '<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">订单详情:&nbsp;&nbsp;&nbsp;' + aData.sub_comments + '</div>';
				// 地址查询
				var typeAddress = orderList.typeAddress(aData);
				if (typeAddress != null && typeAddress != "") {
					sOut += '<div class="row" style="margin-left: 10px;margin-top:10px;margin-right:10px;">地址:&nbsp;&nbsp;&nbsp;' + typeAddress + '</div>';
				}
				sOut += '</table>';
				return sOut;
			},
			createRow : function(rowindex, colindex, name, value, data, row) {

				// 订单关闭
				if (data.sub_order_state != 'I') {
					if (data.currentStepTime != '' || data.totalTime != '') {
						if (parseInt(data.currentStepTime) >= parseInt(data.timeCode) || parseInt(data.totalTime) >= parseInt(data.timeCode)) {
							$(row).addClass('bzh');
						}
					}
				}

				if (colindex == 7) {
					var div = '<div style="text-align: center; min-width: 70px">';
					if (data.orderCommentaryList.length > 0) {
						var orderCommentaryList = data.orderCommentaryList.split('\n');
						for (var i = 0; i < orderCommentaryList.length; i++) {
							var typeName = orderCommentaryList[i].split(':')[0];
							var score = parseInt(orderCommentaryList[i].split(':')[1]);
							var subDiv = '<span style="text-align: center;">' + typeName + ':' + '<span style="color:' + (score == 3 ? '#993300' : (score < 3 ? '#ff0000' : '#778899')) + ';">'
									+ score + '</span>' + '</span><br />'
							div += subDiv;
						}
					}
					div += '</div>';
					return div;
				}
				if (colindex == 8)// 处理当前处理人判断
				{
					return '<div style="text-align: center;">' + data.sub_assigned_dealer_role_name != null && data.sub_assigned_dealer_role_name != "" ? data.sub_assigned_dealer_role_name
							: "";
					+"&nbsp;" + data.sub_assigned_dealer_name != null || data.sub_assigned_dealer_name != "" ? data.sub_assigned_dealer_name : "";
					+'</div>';
				}
				if (colindex == 1)// 处理当前处理人判断
				{
					return orderList.dealColum({
						"value" : value,
						"length" : 8
					});
				}
			}
		});
	},
	typeSwitch : function(type, data) {
		var resData = null;
		switch (type) {
		case "A":
			resData = data.cancelLeaseOrderHisList;
			return resData;
		case "D":
			resData = data.houseLookingOrderHisList;
			return resData;
		case "C":
			resData = data.complaintOrderHisList;
			return resData;
		case "B":
			resData = data.cleaningOrderHisList;
			return resData;
		case "E":
			resData = data.livingProblemOrderHisList;
			return resData;
		case "F":
			resData = data.otherOrderHisList;
			return resData;
		case "G":
			resData = data.ownerRepairOrderHisList;
			return resData;
		case "H":
			resData = data.repairOrderHisList;
			return resData;
		case "I":
			resData = data.routineCleaningOrderHisList;
			return resData;
		default:
			return resData;
		}
	},
	typeAddress : function(aData) {
		var resData = "";
		switch (aData.type) {
		case "B":
			// resData=data.cleaningOrderHisList;//保洁
			resData = orderList.ajaxAgree(aData.rental_lease_order_id, "/rankHouse/loadAgreementInfo.do");
			break;
		case "G":
			// resData=data.ownerRepairOrderHisList;业主预约
			resData = orderList.ajaxAgree(aData.take_house_order_id, "/agreementMge/agreementInfo.do");
			break;
		case "H":
			// resData=data.repairOrderHisList;//维修
			resData = orderList.ajaxAgree(aData.rental_lease_order_id, "/rankHouse/loadAgreementInfo.do");
			break;
		case "I":
			// resData=data.routineCleaningOrderHisList;//例行保洁
			resData = orderList.ajaxAgree(aData.rental_lease_order_id, "/rankHouse/loadAgreementInfo.do");
			break;
		default:
			break;
		}
		return resData;
	},
	dealColum : function(opt) {
		var def = {
			value : '',
			length : 5
		};
		jQuery.extend(def, opt);
		if (def.value == "null" || def.value == '' || def.value == null) {
			return "";
		}
		if (def.value.length > def.length) {
			return "<div title='" + def.value + "'>" + def.value.substr(0, def.length) + "...</div>";
		} else {
			return def.value;
		}
	},
	ajaxAgree : function(id, url) {
		var address = "";
		common.ajax({
			url : common.root + url,
			data : {
				id : id
			},
			dataType : 'json',
			async : false,
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.address != null && data.address != "") {
						address = data.address;
					}
				}
			}
		});
		return address;
	},
	// 新增弹出层
	addButtype : function(type, orderName) {
		var url = '/html/pages/order_flow/order_newAction/Order_' + type + '_Add.html';
		var title = orderName;
		var name = "orderadd" + type;
		common.openWindow({
			type : 3,
			name : name,
			title : title,
			url : url
		});
	},
	dealColum : function(opt) {
		var def = {
			value : '',
			length : 5
		};
		jQuery.extend(def, opt);
		if (common.isEmpty(def.value)) {
			return "";
		}
		if (def.value.length > def.length) {
			return "<div title='" + def.value + "'>" + def.value.substr(0, def.length) + "...</div>";
		} else {
			return def.value;
		}
	},
};
$(function() {
	orderList.init();
});
var rowdata = null;
var resFlag = false;