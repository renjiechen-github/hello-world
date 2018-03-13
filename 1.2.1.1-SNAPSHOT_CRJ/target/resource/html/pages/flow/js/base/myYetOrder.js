var jsWorkOrder = {
	/**
	 * 初始化操作信息
	 */

	init : function() {
		this.loadData();
		this.typeInit();
		this.stateInit();
		$("#order_type").change(function() {
			$("#searchOrder").click();
		});
		$("#order_state").change(function() {
			$("#searchOrder").click();
		});
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
					// $("#order_type").select2();
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
						html += '<option  value="' + date[i].state + '" >' + date[i].stateName + '</option>';
					}
					$("#order_state").html(html);
					// $("#order_type").select2();
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
			// expname:'财务收入',
			url : common.root + '/workOrder/getWorkOrderList.do',
			columns : [ "code", "name", "type_name", "created_date_str",
			// "appointment_date_str",
			// "created_staff_name",
			"user_name", "user_phone", "sub_state_name", "sub_assigned_dealer_name", "totalTime", "currentStepTime", {
				name : "sub_comments",
				isover : true,
				isshow : false,
				title : '订单详情'
			}, {
				name : "sub_assigned_dealer_role_name",
				isover : true,
				isshow : false,
				title : '当前操作角色'
			} ],
			aaSorting : [ [ 5, "desc" ] ],
			param : function() {
				var a = new Array();
				a.push({
					name : "type",
					value : $('#order_type').val()
				});
				a.push({
					name : "subOrderState",
					value : $('#order_state').val()
				});
				a.push({
					name : "keyword",
					value : $('#keyword').val()
				});
				a.push({
					name : "currentDealerDealtOrder",
					value : "Y"
				});
				return a;
			},
			aoColumnDefs : [ {
				"bSortable" : false,
				"aTargets" : [ 0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12 ]
			} ],
			bnt : [ {
				name : '查看详情',
				isshow : function(data, row) {
					var resReturn = false;
					if (data.queryPermission == "1") {
						resReturn = true;
					}
					return resReturn;
				},				
				fun : function(data, row) {
					common.setCookie('resFlag'+(data.id),'0',null,null,null,null);
					resFlag = false;
					rowdata = data;
					var title = rowdata.type_name;
					window.open(common.root + '/workOrder/path2WorkOrderDetailInfoPage.do?id=' + rowdata.id + '&isMobile=N');
					// common.openWindow({
					// 	url : common.root + '/workOrder/path2WorkOrderDetailPage.do?id=' + rowdata.id + '&isMobile=N',
					// 	name : 'workOrderDetail',
					// 	type : 3,
					// 	title : title
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
				var typeAddress = jsWorkOrder.typeAddress(aData);
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

				if (colindex == 7)// 处理当前处理人判断
				{
					return '<div style="text-align: center;">' + data.sub_assigned_dealer_role_name != null && data.sub_assigned_dealer_role_name != "" ? data.sub_assigned_dealer_role_name
							: "";
					+"&nbsp;" + data.sub_assigned_dealer_name != null || data.sub_assigned_dealer_name != "" ? data.sub_assigned_dealer_name : "";
					+'</div>';
				}
				if (colindex == 1)// 处理当前处理人判断
				{
					return jsWorkOrder.dealColum({
						"value" : value,
						"length" : 8
					});
				}
			}
		});
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
			resData = jsWorkOrder.ajaxAgree(aData.rental_lease_order_id, "/rankHouse/loadAgreementInfo.do");
			break;
		case "G":
			// resData=data.ownerRepairOrderHisList;业主预约
			resData = jsWorkOrder.ajaxAgree(aData.take_house_order_id, "/agreementMge/agreementInfo.do");
			break;
		case "H":
			// resData=data.repairOrderHisList;//维修
			resData = jsWorkOrder.ajaxAgree(aData.rental_lease_order_id, "/rankHouse/loadAgreementInfo.do");
			break;
		case "I":
			// resData=data.routineCleaningOrderHisList;//例行保洁
			resData = jsWorkOrder.ajaxAgree(aData.rental_lease_order_id, "/rankHouse/loadAgreementInfo.do");
			break;
		default:
			break;
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
/**
 * 
 * @param obj
 */
};
$(function() {
	jsWorkOrder.init();
});
var rowdata = null;
var resFlag = false;