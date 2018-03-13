var datapermission = {
	init : function() {
		
		// 加载初始化查询信息
		datapermission.initType();
		
		// 保存
		$(".btn_info").click(function() {
			datapermission.btn_info("保存");
		});
		
		$(".cancel_info").click(function() {
			datapermission.cancel_info();
		});
		
		$("#serach").click(function() {
			datapermission.serach();
		});
		
		$(".form-select").change(function() {
			datapermission.serach();
		});
		
		$('input[type=checkbox]').click(function(){
			$(".btn_none").hide();
			$(".btn_info").show();			
		});
		
	},
	
	chanageAdd : function(query_id, update_id, assign_id, close_id) {
		// 判断查询是否选中
		if ($("#" + query_id).attr('checked')) {
			$("#" + query_id).attr("disabled", true);
		} else {
			$("#" + query_id).attr("disabled", true);
			$("#" + query_id).attr("checked", true);
		}
		
		var update = $("#" + update_id).attr('checked');
		var assign = $("#" + assign_id).attr('checked');
		var close = $("#" + close_id).attr('checked');
		if (!update && !assign && !close) {
			$("#" + query_id).attr("disabled", false);
		}

	},
	
	serach : function() {
		var role_id = $(".form-select").val();
		if (role_id == -1) {
			$('input[type=checkbox]').each(function(){ 
				$(this).attr("disabled", true);
				$(this).attr("checked", false);
			});
			$(".btn_none").show();
			$(".btn_info").hide();
		} else {
			common.load.load();
			var param = {
					role_id : role_id
			};
			common.ajax({
				url : common.root + '/datapermission/getPermissionsInfo.do',
				dataType : 'json',
				encode : false,
				contentType : 'application/json; charset=utf-8',
				data : JSON.stringify(param),
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						common.load.hide();
						if (data.state == 1) {
							$('input[type=checkbox]').each(function(){ 
								$(this).attr("disabled", false);
							});							
							$(".btn_none").show();
							$(".btn_info").hide();		
							var list = data.data;
							datapermission.clearInfo();
							if (list.length == 0) {
								// 清空所有选项
								datapermission.clearInfo();
							} else {
								for (var i = 0; i < list.length; i++) {
									var type_id = list[i].type_id;
									if (type_id == 1) {
										datapermission.order(list[i]);
									} else if (type_id == 2) {
										datapermission.agreement(list[i]);
									} else if (type_id == 3) {
										datapermission.exports(list[i]);
									}
								}
								
								// 判断查询权限是否需要禁用
							}
						} else {
							common.alert({
								msg : "没有查询到权限信息"
							});
						}
					} else {
						common.load.hide();
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});		
		}
	},
	
	cancel_info : function() {
		datapermission.clearInfo();
		datapermission.btn_info("清除");
	},
	
	clearInfo : function() {
		$("#houseLook input[type='checkbox']").removeAttr("checked");
		$("#repair input[type='checkbox']").removeAttr("checked");
		$("#cleaning input[type='checkbox']").removeAttr("checked");
		$("#complaint input[type='checkbox']").removeAttr("checked");
		$("#cancelLease input[type='checkbox']").removeAttr("checked");
		$("#other input[type='checkbox']").removeAttr("checked");
		$("#owerRepair input[type='checkbox']").removeAttr("checked");
		$("#routineCleaning input[type='checkbox']").removeAttr("checked");
		$("#livingProblem input[type='checkbox']").removeAttr("checked");
		$("#renterAgreement input[type='checkbox']").removeAttr("checked");
		$("#ownerAgreement input[type='checkbox']").removeAttr("checked");
		$("#exportInfo input[type='checkbox']").removeAttr("checked");
	},
	
	order : function(data) {
		var sub_type_id = data.sub_type_id; 
		var add = data.add_permission;
		var query = data.query_permission;
		var assign = data.assign_permission;
		var close = data.close_permission;
		var update = data.update_permission;
		switch (sub_type_id) {
			case 'A' : // 退租
				$("#cancelLease").attr("data", data.id);
				datapermission.setPermission(add, 'cancelLeaseAdd');
				datapermission.setPermission(query, 'cancelLeaseQuery');
				datapermission.setPermission(assign, 'cancelLeaseAssign');
				datapermission.setPermission(close, 'cancelLeaseClose');
				datapermission.setPermission(update, 'cancelLeaseUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('cancelLeaseQuery','cancelLeaseUpdate','cancelLeaseAssign','cancelLeaseClose');
				}
				break;
			case 'B' : // 保洁
				$("#cleaning").attr("data", data.id);
				datapermission.setPermission(add, 'cleaningAdd');
				datapermission.setPermission(query, 'cleaningQuery');
				datapermission.setPermission(assign, 'cleaningAssign');
				datapermission.setPermission(close, 'cleaningClose');
				datapermission.setPermission(update, 'cleaningUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('cleaningQuery','cleaningUpdate','cleaningAssign','cleaningClose');
				}			
				break;				
			case 'C' : // 投诉
				$("#complaint").attr("data", data.id);
				datapermission.setPermission(add, 'complaintAdd');
				datapermission.setPermission(query, 'complaintQuery');
				datapermission.setPermission(assign, 'complaintAssign');
				datapermission.setPermission(close, 'complaintClose');
				datapermission.setPermission(update, 'complaintUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('complaintQuery','complaintUpdate','complaintAssign','complaintClose');
				}				
				break;				
			case 'D' : // 看房
				$("#houseLook").attr("data", data.id);
				datapermission.setPermission(add, 'houseLookAdd');
				datapermission.setPermission(query, 'houseLookQuery');
				datapermission.setPermission(assign, 'houseLookAssign');
				datapermission.setPermission(close, 'houseLookClose');
				datapermission.setPermission(update, 'houseLookUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('houseLookQuery','houseLookUpdate','houseLookAssign','houseLookClose');
				}				
				break;				
			case 'E' : // 入住问题
				$("#livingProblem").attr("data", data.id);
				datapermission.setPermission(add, 'livingProblemAdd');
				datapermission.setPermission(query, 'livingProblemQuery');
				datapermission.setPermission(assign, 'livingProblemAssign');
				datapermission.setPermission(close, 'livingProblemClose');
				datapermission.setPermission(update, 'livingProblemUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('livingProblemQuery','livingProblemUpdate','livingProblemAssign','livingProblemClose');
				}					
				break;				
			case 'F' : // 其他
				$("#other").attr("data", data.id);
				datapermission.setPermission(add, 'otherAdd');
				datapermission.setPermission(query, 'otherQuery');
				datapermission.setPermission(assign, 'otherAssign');
				datapermission.setPermission(close, 'otherClose');
				datapermission.setPermission(update, 'otherUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('otherQuery','otherUpdate','otherAssign','otherClose');
				}				
				break;				
			case 'G' : // 业主维修
				$("#owerRepair").attr("data", data.id);
				datapermission.setPermission(add, 'owerRepairAdd');
				datapermission.setPermission(query, 'owerRepairQuery');
				datapermission.setPermission(assign, 'owerRepairAssign');
				datapermission.setPermission(close, 'owerRepairClose');
				datapermission.setPermission(update, 'owerRepairUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('owerRepairQuery','owerRepairUpdate','owerRepairAssign','owerRepairClose');
				}				
				break;				
			case 'H' : // 维修
				$("#repair").attr("data", data.id);
				datapermission.setPermission(add, 'repairAdd');
				datapermission.setPermission(query, 'repairQuery');
				datapermission.setPermission(assign, 'repairAssign');
				datapermission.setPermission(close, 'repairClose');
				datapermission.setPermission(update, 'repairUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('repairQuery','repairUpdate','repairAssign','repairClose');
				}				
				break;				
			case 'I' : // 例行保洁
				$("#routineCleaning").attr("data", data.id);
				datapermission.setPermission(add, 'routineCleaningAdd');
				datapermission.setPermission(query, 'routineCleaningQuery');
				datapermission.setPermission(assign, 'routineCleaningAssign');
				datapermission.setPermission(close, 'routineCleaningClose');
				datapermission.setPermission(update, 'routineCleaningUpdate');
				if (assign != 0 || close != 0 || update != 0) {
					datapermission.chanageAdd('routineCleaningQuery','routineCleaningUpdate','routineCleaningAssign','routineCleaningClose');
				}				
				break;
		}
	},
	
	exports : function(data) {
		var export_permission = data.export_permission;
		$("#exportInfo").attr("data", data.id);
		datapermission.setPermission(export_permission, 'exportInfoOperate');
	},
	
	agreement : function(data) {
		var sub_type_id = data.sub_type_id; 
		var add = data.add_permission;
		var query = data.query_permission;
		var assign = data.assign_permission;
		var close = data.close_permission;
		var update = data.update_permission;		
		switch (sub_type_id) {
		case 'A' : // 业主合约
			$("#ownerAgreement").attr("data", data.id);
			datapermission.setPermission(add, 'ownerAgreementAdd');
			datapermission.setPermission(query, 'ownerAgreementQuery');
			datapermission.setPermission(assign, 'ownerAgreementAssign');
			datapermission.setPermission(close, 'ownerAgreementClose');
			datapermission.setPermission(update, 'ownerAgreementUpdate');
			if (assign != 0 || close != 0 || update != 0) {
				datapermission.chanageAdd('ownerAgreementQuery','ownerAgreementUpdate','ownerAgreementAssign','ownerAgreementClose');
			}			
			break;
		case 'B' : // 租客合约
			$("#renterAgreement").attr("data", data.id);
			datapermission.setPermission(add, 'renterAgreementAdd');
			datapermission.setPermission(query, 'renterAgreementQuery');
			datapermission.setPermission(assign, 'renterAgreementAssign');
			datapermission.setPermission(close, 'renterAgreementClose');
			datapermission.setPermission(update, 'renterAgreementUpdate');
			if (assign != 0 || close != 0 || update != 0) {
				datapermission.chanageAdd('renterAgreementQuery','renterAgreementUpdate','renterAgreementAssign','renterAgreementClose');
			}			
			break;
		}
	},
	
	setPermission : function(info, id) {
		if (info == 1) {
			$("#" + id).attr('checked', 'checked');
		} else {
			$("#" + id).removeAttr('checked');
		}
	},

	initType : function() {
		// 获取角色信息
		common.ajax({
			url : common.root + '/account/selectRole.do',
			dataType : 'json',
			encode : false,
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.state == 1) {
						var html = '<option  value="-1" >请选择...</option>';
						$(".form-select").html("");
						var info = data.list;
						for (var i = 0; i < info.length; i++) {
							html += '<option  value="' + info[i].role_id + '" >' + info[i].role_name + '</option>';
						}
						$('.form-select').html(html);
						$('.form-select').select2({
							'width' : '229px'
						});
						
						var role_id = $(".form-select").val();
						if (role_id == -1) {
							$('input[type=checkbox]').each(function(){ 
								$(this).attr("disabled", true); 
							});							
						}
					} else {
						common.alert({
							msg : "没有查询到角色信息"
						});
					}

				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},

	btn_info : function(str){
		
		// 获取角色信息
		var role_id = $(".form-select").val();
		if (role_id == '' || role_id == '-1') {
			common.alert({
				msg : "请选择角色"
			});
			$(".form-select").focus();
			return;
		}

		// 看房
		var look_id = $("#houseLook").attr("data");
		var look_add_permission = datapermission.permissionInfo("houseLookAdd");
		var look_query_permission = datapermission.permissionInfo("houseLookQuery");
		var look_update_permission = datapermission.permissionInfo("houseLookUpdate");
		var look_assign_permission = datapermission.permissionInfo("houseLookAssign");
		var look_close_permission = datapermission.permissionInfo("houseLookClose");
		
		// 维修
		var repair_id = $("#repair").attr("data");
		var repair_add_permission = datapermission.permissionInfo("repairAdd");
		var repair_query_permission = datapermission.permissionInfo("repairQuery");
		var repair_update_permission = datapermission.permissionInfo("repairUpdate");
		var repair_assign_permission = datapermission.permissionInfo("repairAssign");
		var repair_close_permission = datapermission.permissionInfo("repairClose");
		
		// 保洁
		var cleaning_id = $("#cleaning").attr("data");
		var cleaning_add_permission = datapermission.permissionInfo("cleaningAdd");
		var cleaning_query_permission = datapermission.permissionInfo("cleaningQuery");
		var cleaning_update_permission = datapermission.permissionInfo("cleaningUpdate");
		var cleaning_assign_permission = datapermission.permissionInfo("cleaningAssign");
		var cleaning_close_permission = datapermission.permissionInfo("cleaningClose");			
		
		// 投诉
		var complaint_id = $("#complaint").attr("data");
		var complaint_add_permission = datapermission.permissionInfo("complaintAdd");
		var complaint_query_permission = datapermission.permissionInfo("complaintQuery");
		var complaint_update_permission = datapermission.permissionInfo("complaintUpdate");
		var complaint_assign_permission = datapermission.permissionInfo("complaintAssign");
		var complaint_close_permission = datapermission.permissionInfo("complaintClose");		
		
		// 退租
		var cancelLease_id = $("#cancelLease").attr("data");
		var cancelLease_add_permission = datapermission.permissionInfo("cancelLeaseAdd");
		var cancelLease_query_permission = datapermission.permissionInfo("cancelLeaseQuery");
		var cancelLease_update_permission = datapermission.permissionInfo("cancelLeaseUpdate");
		var cancelLease_assign_permission = datapermission.permissionInfo("cancelLeaseAssign");
		var cancelLease_close_permission = datapermission.permissionInfo("cancelLeaseClose");			
		
		// 其他
		var other_id = $("#other").attr("data");
		var other_add_permission = datapermission.permissionInfo("otherAdd");
		var other_query_permission = datapermission.permissionInfo("otherQuery");
		var other_update_permission = datapermission.permissionInfo("otherUpdate");
		var other_assign_permission = datapermission.permissionInfo("otherAssign");
		var other_close_permission = datapermission.permissionInfo("otherClose");			
		
		// 业主维修
		var owerRepair_id = $("#owerRepair").attr("data");
		var owerRepair_add_permission = datapermission.permissionInfo("owerRepairAdd");
		var owerRepair_query_permission = datapermission.permissionInfo("owerRepairQuery");
		var owerRepair_update_permission = datapermission.permissionInfo("owerRepairUpdate");
		var owerRepair_assign_permission = datapermission.permissionInfo("owerRepairAssign");
		var owerRepair_close_permission = datapermission.permissionInfo("owerRepairClose");		
		
		// 例行保洁
		var routineCleaning_id = $("#routineCleaning").attr("data");
		var routineCleaning_add_permission = datapermission.permissionInfo("routineCleaningAdd");
		var routineCleaning_query_permission = datapermission.permissionInfo("routineCleaningQuery");
		var routineCleaning_update_permission = datapermission.permissionInfo("routineCleaningUpdate");
		var routineCleaning_assign_permission = datapermission.permissionInfo("routineCleaningAssign");
		var routineCleaning_close_permission = datapermission.permissionInfo("routineCleaningClose");		
		
		// 入住问题
		var livingProblem_id = $("#livingProblem").attr("data");
		var livingProblem_add_permission = datapermission.permissionInfo("livingProblemAdd");
		var livingProblem_query_permission = datapermission.permissionInfo("livingProblemQuery");
		var livingProblem_update_permission = datapermission.permissionInfo("livingProblemUpdate");
		var livingProblem_assign_permission = datapermission.permissionInfo("livingProblemAssign");
		var livingProblem_close_permission = datapermission.permissionInfo("livingProblemClose");			
		
		// 租客合约
		var renterAgreement_id = $("#renterAgreement").attr("data");
		var renterAgreement_add_permission = datapermission.permissionInfo("renterAgreementAdd");
		var renterAgreement_query_permission = datapermission.permissionInfo("renterAgreementQuery");
		var renterAgreement_update_permission = datapermission.permissionInfo("renterAgreementUpdate");
		var renterAgreement_assign_permission = datapermission.permissionInfo("renterAgreementAssign");
		var renterAgreement_close_permission = datapermission.permissionInfo("renterAgreementClose");		
		
		// 业主合约
		var ownerAgreement_id = $("#ownerAgreement").attr("data");
		var ownerAgreement_add_permission = datapermission.permissionInfo("ownerAgreementAdd");
		var ownerAgreement_query_permission = datapermission.permissionInfo("ownerAgreementQuery");
		var ownerAgreement_update_permission = datapermission.permissionInfo("ownerAgreementUpdate");
		var ownerAgreement_assign_permission = datapermission.permissionInfo("ownerAgreementAssign");
		var ownerAgreement_close_permission = datapermission.permissionInfo("ownerAgreementClose");
		
		// 导出
		var exportInfo_id = $("#exportInfo").attr("data");
		var export_permission = datapermission.permissionInfo("exportInfoOperate");
		
		var param = {
				role_id : role_id,
				data : [{
					id : cancelLease_id,
					type_id : 1,
					sub_type_id : 'A',
					sub_type_name : '退租订单',
					query_permission : cancelLease_query_permission,
					add_permission : cancelLease_add_permission,
					update_permission : cancelLease_update_permission,
					assign_permission : cancelLease_assign_permission,
					close_permission : cancelLease_close_permission
				},{
					id : cleaning_id,
					type_id : 1,
					sub_type_id : 'B',
					sub_type_name : '保洁订单',
					query_permission : cleaning_query_permission,
					add_permission : cleaning_add_permission,
					update_permission : cleaning_update_permission,
					assign_permission : cleaning_assign_permission,
					close_permission : cleaning_close_permission					
				},{
					id : complaint_id,
					type_id : 1,
					sub_type_id : 'C',
					sub_type_name : '投诉订单',
					query_permission : complaint_query_permission,
					add_permission : complaint_add_permission,
					update_permission : complaint_update_permission,
					assign_permission : complaint_assign_permission,
					close_permission : complaint_close_permission					
				},{
					id : look_id,
					type_id : 1,
					sub_type_id : 'D',
					sub_type_name : '看房订单',
					query_permission : look_query_permission,
					add_permission : look_add_permission,
					update_permission : look_update_permission,
					assign_permission : look_assign_permission,
					close_permission : look_close_permission					
				},{
					id : livingProblem_id,
					type_id : 1,
					sub_type_id : 'E',
					sub_type_name : '入住问题订单',
					query_permission : livingProblem_query_permission,
					add_permission : livingProblem_add_permission,
					update_permission : livingProblem_update_permission,
					assign_permission : livingProblem_assign_permission,
					close_permission : livingProblem_close_permission					
				},{
					id : other_id,
					type_id : 1,
					sub_type_id : 'F',
					sub_type_name : '其他订单',
					query_permission : other_query_permission,
					add_permission : other_add_permission,
					update_permission : other_update_permission,
					assign_permission : other_assign_permission,
					close_permission : other_close_permission					
				},{
					id : owerRepair_id,
					type_id : 1,
					sub_type_id : 'G',
					sub_type_name : '业主维修订单',
					query_permission : owerRepair_query_permission,
					add_permission : owerRepair_add_permission,
					update_permission : owerRepair_update_permission,
					assign_permission : owerRepair_assign_permission,
					close_permission : owerRepair_close_permission					
				},{
					id : repair_id,
					type_id : 1,
					sub_type_id : 'H',
					sub_type_name : '维修订单',
					query_permission : repair_query_permission,
					add_permission : repair_add_permission,
					update_permission : repair_update_permission,
					assign_permission : repair_assign_permission,
					close_permission : repair_close_permission					
				},{
					id : routineCleaning_id,
					type_id : 1,
					sub_type_id : 'I',
					sub_type_name : '例行保洁订单',
					query_permission : routineCleaning_query_permission,
					add_permission : routineCleaning_add_permission,
					update_permission : routineCleaning_update_permission,
					assign_permission : routineCleaning_assign_permission,
					close_permission : routineCleaning_close_permission					
				},{
					id : ownerAgreement_id,
					type_id : 2,
					sub_type_id : 'A',
					sub_type_name : '业主合约',
					query_permission : ownerAgreement_query_permission,
					add_permission : ownerAgreement_add_permission,
					update_permission : ownerAgreement_update_permission,
					assign_permission : ownerAgreement_assign_permission,
					close_permission : ownerAgreement_close_permission
				},{
					id : renterAgreement_id,
					type_id : 2,
					sub_type_id : 'B',
					sub_type_name : '租客合约',
					query_permission : renterAgreement_query_permission,
					add_permission : renterAgreement_add_permission,
					update_permission : renterAgreement_update_permission,
					assign_permission : renterAgreement_assign_permission,
					close_permission : renterAgreement_close_permission					
				},{
					id : exportInfo_id,
					type_id : 3,
					sub_type_id : 'A',
					sub_type_name : '导出',
					query_permission : 0,
					add_permission : 0,
					update_permission : 0,
					assign_permission : 0,
					close_permission : 0,
					export_permission : export_permission
				}]
		};
		
		common.alert({
			msg : "确定"+str+"权限数据？",
			confirm : true,
			fun : function(action) {
				if (action) {
					common.load.load();
					common.ajax({
						url : common.root + '/datapermission/udpatePermissionInfo.do',
						dataType : 'json',
						encode : false,
						contentType : 'application/json; charset=utf-8',
						data : JSON.stringify(param),
						loadfun : function(isloadsucc, data) {
							common.load.hide();
							if (isloadsucc) {
								if (data > 0) {
									common.alert({
										msg : "操作成功"
									});
									
								} else {
									common.alert({
										msg : "没有查询到角色信息"
									});
								}

							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						}
					});					
				}
			}
		});		
	},
	
	permissionInfo : function(id) {
		var permission;
		if ($("#" + id).attr('checked')) {
			permission = 1;
		} else {
			permission = 0;
		}
		return permission;
	},
	
};

datapermission.init();
