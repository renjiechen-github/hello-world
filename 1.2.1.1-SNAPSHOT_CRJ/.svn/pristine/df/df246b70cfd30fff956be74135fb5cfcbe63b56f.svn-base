// 页面传递参数使用
var rowdata = null;
var userInfo = {
	init : function() {

		// 加载初始化查询信息
		userInfo.initType();

		// 创建表格
		userInfo.createTable();

		// 用户状态查询
		$('#state').change(function() {
			$('#userTable').bootstrapTable('refreshOptions',{pageNumber:1,pageSize:10});
		});

		// 登录设备查询
		$("#device").change(function() {
			$('#userTable').bootstrapTable('refreshOptions',{pageNumber:1,pageSize:10});
		});

		// 团队查询
		$("#teamId").change(function() {
			$('#userTable').bootstrapTable('refreshOptions',{pageNumber:1,pageSize:10});
		});

		// 角色查询
		$("#roleId").change(function() {
			$('#userTable').bootstrapTable('refreshOptions',{pageNumber:1,pageSize:10});
		});

		// 打开新增窗口
		$("#addNewUser").click(function() {
			userInfo.add();
		});

		// 新增用户保存
		$("#addUserInfoBtn").click(function() {
			userInfo.addUserInfo();
		});
		
		// 更新用户信息
		$("#updateUserInfoBtn").click(function() {
			userInfo.updateUserInfo();
		});
	},

	initType : function() {

		// 获取用户状态
		common.ajax({
			url : common.root + '/caas/sysuser/getStateInfo',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			encode : false,
			loadfun : function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {
					var html = '<option value="">状态</option>';
					$("#state").html("");
					for (var i = 0; i < data.length; i++) {
						html += '<option  value="' + data[i].state + '" >' + data[i].stateName + '</option>';
					}
					$('#state').html(html);
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});

		// 获取团队信息
		common.ajax({
			url : common.root + '/caas/sysuser/getTeamInfoById',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			encode : false,
			data : JSON.stringify({}),
			loadfun : function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {
					var html = '<option value="">团队</option>';
					$("#teamId").html("");
					for (var i = 0; i < data.length; i++) {
						html += '<option  value="' + data[i].teamId + '" >' + data[i].teamName + '</option>';
					}
					$('#teamId').html(html);
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});

		// 获取用户角色
		common.ajax({
			url : common.root + '/caas/sysuser/getRoleInfoById',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			encode : false,
			data : JSON.stringify({}),
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = '<option value="">角色</option>';
					$("#roleId").html("");
					for (var i = 0; i < data.length; i++) {
						html += '<option  value="' + data[i].roleId + '" >' + data[i].roleName + '</option>';
					}
					$('#roleId').html(html);
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});

		// 时间控件
		var now_time = new Date();
		var query_year = now_time.getFullYear();// 年
		var query_month = now_time.getMonth() + 1;// 月
		query_month = query_month < 10 ? "0" + query_month : query_month;
		var query_day = now_time.getDate();// 日
		query_day = query_day < 10 ? "0" + query_day : query_day;
		var query_begin_time = query_year + "-" + query_month + "-01 00:00:00";
		var query_end_time = query_year + "-" + query_month + "-" + query_day + " 23:59:59";
		$("#createTime").daterangepicker({
			startDate : query_begin_time,
			endDate : query_end_time,
			timePicker12Hour : false,
			separator : '~',
			format : 'YYYY-MM-DD HH:mm:ss'
		}, function(start, end, label) {
			console.log(start.toISOString(), end.toISOString(), label);
		});
	},

	// 表格创建
	createTable : function() {
		$('#userTable').bootstrapTable(
						{
							url : common.root + '/caas/sysuser/loadUserList', // 请求后台的URL（*）
							method : 'post', // 请求方式（*）
							dataType : 'json',
							contentType : 'application/json; charset=utf-8',
							striped : true, // 是否显示行间隔色
							cache : false, // 是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
							pagination : true, // 是否显示分页（*）
							sortable : true, // 是否启用排序
							sortOrder : "desc", // 排序方式
							queryParamsType : "undefined",
							responseHandler : userInfo.responseHandler,
							queryParams : userInfo.queryParams,// 传递参数（*）
							sidePagination : "server", // 分页方式：client客户端分页，server服务端分页（*）
							pageNumber : 1, // 初始化加载第一页，默认第一页
							pageSize : 10, // 每页的记录行数（*）
							pageList : [ 10, 25, 50, 100 ], // 可供选择的每页的行数（*）
							minimumCountColumns : 2, // 最少允许的列数
							uniqueId : "userId", // 每一行的唯一标识，一般为主键列
							cardView : false, // 是否显示详细视图
							detailView : false, // 是否显示父子表
							rowStyle : userInfo.rowStyle,
							showExport : true, // 是否显示导出
							exportDataType : "all", // basic', 'all', 'selected'.
							exportTypes : [ 'xlsx' ],
							exportOptions : {
								fileName : '用户列表',
								ignoreColumn : [ 0, 12 ]
							},
							columns : [
									{
										title : '序号',
										align : 'center',
										formatter : function(value, row, index) {
											var page = $("#userTable").bootstrapTable("getPage");
											return page.pageSize * (page.pageNumber - 1) + index + 1;
										}
									},
									{
										field : 'userName',
										align : 'center',
										title : '姓名'
									},
									{
										field : 'userPhone',
										align : 'center',
										title : '手机号'
									},
									{
										field : 'roleName',
										align : 'center',
										title : '角色'
									},
									{
										field : 'orgName',
										align : 'center',
										title : '组织'
									},
									{
										field : 'teamName',
										align : 'center',
										title : '团队'
									},
									{
										field : 'createTime',
										align : 'center',
										title : '注册时间'
									},
									{
										field : 'device',
										align : 'center',
										title : '设备'
									},
									{
										field : 'ipAdress',
										align : 'center',
										title : 'IP地址'
									},
									{
										field : 'lastLoginTime',
										align : 'center',
										title : '最后操作时间'
									},
									{
										field : 'stateName',
										align : 'center',
										title : '当前状态'
									},
									{
										align : 'center',
										title : '操作',
										formatter : function(value, row, index) {
											var html = '<div class="dropdown" style="padding-top: 0px;">'
													+ '<a id="menu1" type="button" data-toggle="dropdown" class="fa fa-external-link" style="color: #000000; font-size: 18px; text-decoration:none; cursor:pointer;"></a>'
													+ '<ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="menu1">'
											if (row.isDelete == 1) {
												html += '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="userInfo.del('+ row.userId + ', 0)">删除</a></li>'
											} else {
												html += '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="userInfo.del('+ row.userId + ', 1)">恢复</a></li>'
											}
											html += "<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"#\" onclick='userInfo.update("+ JSON.stringify(row) + ", 0)'>修改</a></li>"
											if (row.state == 1) {
												html += '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="userInfo.disabledOrEnabled('+ row.userId + ', 0)">停用</a></li>'
											} else {
												html += '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="userInfo.disabledOrEnabled('+ row.userId + ', 1)">启用</a></li>'
											}
											html += "<li role=\"presentation\"><a role=\"menuitem\" tabindex=\"-1\" href=\"#\" onclick='userInfo.update("+ JSON.stringify(row) + ", 1)'>查看详情</a></li>"
													+ '<li role="presentation"><a role="menuitem" tabindex="-1" href="#" onclick="userInfo.initializePassword('+ row.userId + ')">初始化密码</a></li>' + '</ul></div>';
											return html;
										}
									} ]
						});
	},

	// 处理返回的数据
	responseHandler : function(res) {
		if (res.state == '-99999') {
			common.alert({
				msg : '您还未登陆或登陆已经失效，请登录。',
				fun : function() {
					window.location.href = 'loginNew.html';
				}
			});
			return {};
		} else if (res.appserverCode == -1){
			common.alert({
				msg : '您还未登陆或登陆已经失效，请登录。',
				fun : function() {
					window.location.href = 'loginNew.html';
				}		
			});
		} else if (res.state == '-99998') {
			common.alert({
				msg : '很抱歉，您没有权限进行此操作。'
			});
			return {};
		} else if (res.state == '-99997') {
			common.alert({
				msg : '很抱歉，您已在其他地方登陆了系统，如不是本人操作可重新登陆修改密码。',
				fun : function() {
					window.location.href = 'loginNew.html';
				}
			});
			return {};
		} else {
			return {
				"rows" : res.rows,
				"total" : res.total
			};
		}
	},

	// 下标为奇数的行加颜色
	rowStyle : function(row, index) {
		var bool = index % 2 == 0 ? false : true;
		if (bool) {
			return {
				css : {
					"background-color" : "#D5EBFE"
				}
			};
		} else {
			return {};
		}
	},

	// 查询参数
	queryParams : function(params) {
		// 注册时间
		var createTime = $("#createTime").val();
		var beginTime;
		var endTime;
		if (createTime != "") {
			beginTime = createTime.split("~")[0];
			endTime = createTime.split("~")[1];
		} else {
			beginTime = "";
			endTime = "";
		}
		var temp = {
			pageNumber : params.pageNumber, // 页码
			pageSize : params.pageSize,// 每页显示数量
			keyWord : $("#keyWord").val(), // 关键词搜索
			state : $("#state").val(), // 状态id
			teamId : $("#teamId").val(), // 团队id
			device : $("#device").val(), // 登录设备
			roleId : $("#roleId").val(), // 角色id
			beginTime : beginTime, // 查询开始时间
			endTime : endTime,// 查询结束时间
		};
		return JSON.stringify(temp);
	},

	// 用户修改或查看
	update : function(row, type) {
		
		var orgId = row.orgId;
		if (orgId != null) {
			if (orgId.substr(0, 1) == ',') {
				orgId = orgId.substring(1);
			}
			if (orgId.charAt(orgId.length - 1) == ",") {
				orgId = orgId.substring(0, orgId.length-1);
			}			
		}
		
		$("#updateUserId").val(row.userId);
		$("#updateUserName").val(row.userName);
		$("#updateUserPhone").val(row.userPhone);
		$("#updateUserText").val(row.descText);
		
		$('#updateUserInfo').on('show.bs.modal', function() {
			// 获取团队信息
			common.ajax({
				url : common.root + '/caas/sysuser/getTeamInfoById',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				encode : false,
				data : JSON.stringify({}),
				loadfun : function(isloadsucc, data) {
					common.load.hide();
					if (isloadsucc) {
						var html = '<option value="">请选择</option>';
						$("#updateUserTeam").html("");
						for (var i = 0; i < data.length; i++) {
							html += '<option  value="' + data[i].teamId + '" >' + data[i].teamName + '</option>';
						}
						$('#updateUserTeam').html(html);
						$("#updateUserTeam option[value='"+row.teamId+"']").attr("selected", true);
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});

			// 获取用户角色
			common.ajax({
				url : common.root + '/caas/sysuser/getRoleInfoById',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				encode : false,
				data : JSON.stringify({}),
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						var html = '<option value="">请选择</option>';
						$("#updateUserRole").html("");
						for (var i = 0; i < data.length; i++) {
							html += '<option  value="' + data[i].roleId + '" >' + data[i].roleName + '</option>';
						}
						$('#updateUserRole').html(html);
						var roleSelect = $('#updateUserRole').select2();
            window.setTimeout(function(){
            	roleSelect.val(row.roleId.split(',')).trigger("change"); 
            }, 300);
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});
			
			// 获取用户组织
			common.ajax({
				url : common.root + '/caas/sysuser/getOrgInfo',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				encode : false,
				data : JSON.stringify({}),
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						var html = '<option value="">请选择</option>';
						$("#updateUserOrg").html("");
						for (var i = 0; i < data.length; i++) {
							html += '<option  value="' + data[i].orgId + '" >' + data[i].orgName + '</option>';
						}
						$('#updateUserOrg').html(html);
            var orgSelect = $('#updateUserOrg').select2();
            window.setTimeout(function(){
            	orgSelect.val(orgId.split(',')).trigger("change"); 
            }, 300);
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});			
		});
		
		$('#updateUserPhone').attr("disabled", "disabled");
		if (type == 1) {
			$("#updateUserInfoBtn").hide();
			$('#updateUserName').attr("disabled", "disabled");
			$('#updateUserOrg').attr("disabled", "disabled");
			$('#updateUserRole').attr("disabled", "disabled");
			$('#updateUserTeam').attr("disabled", "disabled");
			$('#updateUserText').attr("disabled", "disabled");
		} else {
			$("#updateUserInfoBtn").show();
		}
		$("#updateUserInfo").modal('show');
	},

	// 初始化密码
	initializePassword : function(userId) {
		common.alert({
			msg : '确定初始化密码？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/caas/sysuser/initializePassword',
						data : JSON.stringify({
							userId : userId
						}),
						dataType : 'json',
						encode : false,
						contentType : 'application/json; charset=utf-8',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data == 1) {
									common.alert({
										msg : '操作成功！密码【12345】',
										fun : function() {
											$('#userTable').bootstrapTable('refresh');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
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

	// 禁用账号
	// state（1：需要启用，0：需要禁用）
	disabledOrEnabled : function(userId, state) {
		var url;
		var str;
		if (state == 0) {
			url = common.root + '/caas/sysuser/updateDisabledUser';
			str = "确认账号停用？";
		} else {
			str = "确定账号启用？"
			url = common.root + '/caas/sysuser/updateEnabledUser';
		}
		common.alert({
			msg : str,
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : url,
						data : JSON.stringify({
							userId : userId,
							state : state
						}),
						encode : false,
						dataType : 'json',
						contentType : 'application/json; charset=utf-8',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data == 1) {
									common.alert({
										msg : '操作成功',
										fun : function() {
											$('#userTable').bootstrapTable('refresh');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
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

	// 用户删除
	// isDelete（0：需要删除，1：需要恢复）
	del : function(userId, isDelete) {
		var str;
		var str1;
		if (isDelete == 0) {
			str = "确定需要删除吗？";
			str1 = "删除成功";
		} else {
			str = "需要确定恢复吗？";
			str1 = "恢复成功";
		}
		var param = {
			"userId" : userId,
			"isDelete" : isDelete
		};
		common.alert({
			msg : str,
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/caas/sysuser/removeUser',
						data : JSON.stringify(param),
						dataType : 'json',
						encode : false,
						contentType : 'application/json; charset=utf-8',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data == 1) {
									common.alert({
										msg : str1,
										fun : function() {
											$('#userTable').bootstrapTable('refresh');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
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

	// 打开新增用户窗口
	add : function() {
		
		$("#addUserId").val("");
		$("#addUserName").val("");
		$("#addUserPhone").val("");
		$("#addUserSex").val("");
		$("#addUserTeam").val("");
		$("#addUserText").val("");
		$("#addUserRole").val("");
		$("#addUserOrg").val("");
		
		$('#addUserInfo').on('show.bs.modal', function() {
			// 获取团队信息
			common.ajax({
				url : common.root + '/caas/sysuser/getTeamInfoById',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				encode : false,
				data : JSON.stringify({}),
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						var html = '';
						$("#addUserTeam").html("");
						for (var i = 0; i < data.length; i++) {
							html += '<option  value="' + data[i].teamId + '" >' + data[i].teamName + '</option>';
						}
						$('#addUserTeam').html(html);
						$("#addUserTeam option[value='']").attr("selected", true);
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});

			// 获取用户角色
			common.ajax({
				url : common.root + '/caas/sysuser/getRoleInfoById',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				encode : false,
				data : JSON.stringify({}),
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						var html = '';
						$("#addUserRole").html("");
						for (var i = 0; i < data.length; i++) {
							html += '<option  value="' + data[i].roleId + '" >' + data[i].roleName + '</option>';
						}
						$('#addUserRole').html(html);
            $('#addUserRole').select2();
            $("#addUserRole option[value='']").attr("selected", true);
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});
			
			// 获取用户组织
			common.ajax({
				url : common.root + '/caas/sysuser/getOrgInfo',
				dataType : 'json',
				contentType : 'application/json; charset=utf-8',
				encode : false,
				data : JSON.stringify({}),
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						var html = '';
						$("#addUserOrg").html("");
						for (var i = 0; i < data.length; i++) {
							html += '<option  value="' + data[i].orgId + '" >' + data[i].orgName + '</option>';
						}
						$('#addUserOrg').html(html);
            $('#addUserOrg').select2();
            $("#addUserOrg option[value='']").attr("selected", true);
					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});			
			
		});

		$("#addUserInfo").modal();
	},

	// 保存新增用户信息
	addUserInfo : function() {
		
		var addUserName = $.trim($("#addUserName").val());
		var addUserPhone = $.trim($("#addUserPhone").val());
		var addUserSex = $.trim($("#addUserSex").val());
		var addUserOrg = $.trim($("#addUserOrg").val());
		var addUserRole = $.trim($("#addUserRole").val());
		var addUserTeam = $.trim($("#addUserTeam").val());
		var addUserText = $.trim($("#addUserText").val());
		
		var param = {
			userName : addUserName,
			userPhone : addUserPhone,
			orgId : addUserOrg,
			roleId : addUserRole,
			teamId : addUserTeam,
			descText : addUserText
		};
		
		common.alert({
			msg : '确定新增该用户？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/caas/sysuser/addUser',
						data : JSON.stringify(param),
						dataType : 'json',
						encode : false,
						contentType : 'application/json; charset=utf-8',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data == 1) {
									common.alert({
										msg : '新增成功，初始密码【12345】',
										fun : function() {
											$("#addUserInfo").modal('hide');
											$('#userTable').bootstrapTable('refresh');
										}
									});
								} else if (data == 2) {
									common.alert({
										msg : '手机号已经存在，请重新填写',
									});
								} else {
									common.alert({
										msg : common.msg.error
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
	
	// 更新用户信息
	updateUserInfo : function() {
		
		var updateUserId = $("#updateUserId").val();
		var updateUserName = $.trim($("#updateUserName").val());
		var updateUserPhone = $.trim($("#updateUserPhone").val());
		var updateUserOrg = $.trim($("#updateUserOrg").val());
		var updateUserRole = $.trim($("#updateUserRole").val());
		var updateUserTeam = $.trim($("#updateUserTeam").val());
		var updateUserText = $.trim($("#updateUserText").val());
		
		var param = {
				userId : updateUserId,
				userName : updateUserName,
				userPhone : updateUserPhone,
				orgId : updateUserOrg,
				roleId : updateUserRole,
				teamId : updateUserTeam,
				descText : updateUserText
		};
		
		common.alert({
			msg : '确定更新该用户？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/caas/sysuser/modifyUser',
						data : JSON.stringify(param),
						dataType : 'json',
						encode : false,
						contentType : 'application/json; charset=utf-8',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data == 1) {
									common.alert({
										msg : '信息更新成功',
										fun : function() {
											$("#updateUserInfo").modal('hide');
											$('#userTable').bootstrapTable('refresh');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
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

	
};

userInfo.init();
