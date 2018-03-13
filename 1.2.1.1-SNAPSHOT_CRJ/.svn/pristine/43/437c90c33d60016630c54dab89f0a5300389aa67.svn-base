var teamInfo = {
	isAdmin: false,
	level: 1,
	init: function() {
		teamInfo.clearShade();
		$('#search').bind('click', function() {
			teamInfo.searchTeam(false);
		});
		// 查询登录用户管理的团队信息
		teamInfo.searchTeam(true);
		$('remove').bind('click', function() {
			teamInfo.clearShade();
		});

		// 保存团队信息
		$("#addTeamInfoBtn").click(function() {
			teamInfo.addTeamInfo();
		});

		// 保存团队变更小区信息
		$("#updateGroupInfoBtn").click(function() {
			teamInfo.updateGroupInfo();
		});

		// 保存团队名称信息
		$("#modifyTeamNameInfoBtn").click(function() {
			teamInfo.modifyTeamNameInfo();
		});

		// 保存团队负责人信息
		$("#updateTeamLeaderInfoBtn").click(function() {
			teamInfo.updateTeamLeaderInfo();
		});

		// 保存变更团员信息
		$("#updateUserTeamInfoBtn").click(function() {
			teamInfo.updateUserTeamInfo();
		});

		$('.search-input input').keypress(function(e) {
			teamInfo.enterPress(e);
		});
	},

	// 点击回车键
	enterPress: function(e) {
		var event = e || window.event;
		if (event.keyCode == 13) {
			teamInfo.searchTeam();
		}
		//阻止冒泡
		if (event.stopPropagation) {
			event.stopPropagation(); //标准方法
		} else {
			event.cancelBubble = true; // IE8
		}
		//阻止默认事件
		if (event.perventDefault) {
			event.perventDefault(); //标准方法
		} else {
			event.returnValue = false; // IE8
		}
	},

	// 更改团队成员时，新增团队成员
	updateTeamSelect: function() {
		
		// 已分配成员搜索框置成空
		$("#updateTeamUserSearchInput2").val("");
		
		var checkboxes = $("input[name='updateUserChecked1']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				for (var j = 0; j < updateUndistributedUser.length; j++) {
					if (updateUndistributedUser[j].userId == id) {
						updateUndistributedUser.splice(j, 1);
					}
				}

				var flag = false;
				for (var x = 0; x < updateAssignedUser.length; x++) {
					var assignedUserId = updateAssignedUser[x].userId;
					if (id == assignedUserId) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					var name = $(checkboxes[i]).attr("valname");
					var map = {
						"userId": id,
						"userName": name
					};
					updateAssignedUser.push(map);
				}

				// 删除所在的TR
				n = $(checkboxes[i]).parents("tr").index();
				$("#updateUserTable1").find("tr:eq(" + n + ")").remove();
			}
		}

		var myArr = [];
		for (var item in updateAssignedUser) {
			myArr[item.userId + ';' + item.userName];
		};
		console.log(myArr);

		// 判断，如果未分配的成员全部分配，则，清空整个table
		if ($("input[name='updateUserChecked1']").length < 1) {
			$("#updateUserTable1").find("tr").remove();
		}

		// 选中的信息，放到已分配列表中
		var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateUserChecked2\', \'updateTeamUserCheckAll2\');" id="updateTeamUserCheckAll2"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		for (var x = 0; x < updateAssignedUser.length; x++) {
			html += '<tr><td><input type="checkbox" name="updateUserChecked2" valname="' + updateAssignedUser[x].userName + '" valid="' + updateAssignedUser[x].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + updateAssignedUser[x].userName + '</span><td></tr>';
			htmlTable = true;
		}

		if (!htmlTable) {
			html = "";
		}
		$("#updateUserTable2").html(html);

	},
	
	// 变更小区，新增小区
	updateTeamGroupSelect : function() {
		
		// 已分配小区搜索框置成空
		$("#updateTeamGroupSearchInput2").val("");
		
		var checkboxes = $("input[name='updateGroupChecked1']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				for (var j = 0; j < updateGroupUndistributedUser.length; j++) {
					if (updateGroupUndistributedUser[j].groupId == id) {
						updateGroupUndistributedUser.splice(j, 1);
					}
				}
				var name = $(checkboxes[i]).attr("valname");
				var map = {
						"groupId": id,
						"groupName": name
					};
				updateGroupAssignedUser.push(map);
				
				// 删除所在的tr
				n = $(checkboxes[i]).parents("tr").index();
				$("#updateGroupTable1").find("tr:eq(" + n + ")").remove();
			}
		}
		// 判断，如果未分配的小区全部分配，则，清空整个table
		if ($("input[name='updateGroupChecked1']").length < 1) {
			$("#updateGroupTable1").find("tr").remove();
		}
		
		// 选中的信息，放到已分配列表中
		var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateGroupChecked2\', \'updateTeamGroupCheckAll2\');" id="updateTeamGroupCheckAll2"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		// 选中的信息，放到团队负责人列表中
		var htmlTable = false;
		for (var x = 0; x < updateGroupAssignedUser.length; x++) {
			html += '<tr><td><input type="checkbox" name="updateGroupChecked2" valname="' + updateGroupAssignedUser[x].groupName + '" valid="' + updateGroupAssignedUser[x].groupId + '"/><span style="margin-left: 5px; font-size: 12px;">' + updateGroupAssignedUser[x].groupName + '</span><td></tr>';
			htmlTable = true;
		}
		if (!htmlTable) {
			html = "";
		}
		$("#updateGroupTable2").html(html);		
	},
	
	
	// 新增团队时，增加团队成员
	addTeamSelect: function() {
		
		// 已分配成员搜索框置成空
		$("#addTeamUserSearchInput2").val("");
		
		var checkboxes = $("input[name='userChecked1']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				for (var j = 0; j < addUndistributedUser.length; j++) {
					if (addUndistributedUser[j].userId == id) {
						addUndistributedUser.splice(j, 1);
					}
				}
				var name = $(checkboxes[i]).attr("valname");
				var map = {
					"userId": id,
					"userName": name
				};
				addAssignedUser.push(map);

				// 删除所在的tr
				n = $(checkboxes[i]).parents("tr").index();
				$("#addUserTable1").find("tr:eq(" + n + ")").remove();
			}
		}
		// 判断，如果未分配的成员全部分配，则，清空整个table
		if ($("input[name='userChecked1']").length < 1) {
			$("#addUserTable1").find("tr").remove();
		}

		// 选中的信息，放到已分配列表中
		var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'userChecked2\', \'addTeamUserCheckAll2\');" id="addTeamUserCheckAll2"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		// 选中的信息，放到团队负责人列表中
		var leaderTableHtml = '<tr>';
		$("#addUserTable3").html("");
		var htmlTable = false;
		for (var x = 0; x < addAssignedUser.length; x++) {
			html += '<tr><td><input type="checkbox" name="userChecked2" valname="' + addAssignedUser[x].userName + '" valid="' + addAssignedUser[x].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + addAssignedUser[x].userName + '</span><td></tr>';
			if ((x + 1) % 3 == 0) {
				leaderTableHtml += '</tr><tr>'
			}
			leaderTableHtml += '<td><input type="checkbox" name="userChecked3" valid="' + addAssignedUser[x].userId + '" /><span class="userChecked3" style="margin-left: 5px; margin-right: 20px; font-size: 12px;">' + addAssignedUser[x].userName + '</span></td>';
			htmlTable = true;
		}

		if (!htmlTable) {
			html = "";
			leaderTableHtml = "";
		}
		$("#addUserTable2").html(html);
		$('#addUserTable3').html(leaderTableHtml);
	},
	
	// 变更小区，删除小区
	updateRemoveTeamGroupSelect:function() {
		
		// 未分配小区搜索框置成空
		$("#updateTeamGroupSearchInput1").val("");

		var checkboxes = $("input[name='updateGroupChecked2']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				for (var j = 0; j < updateGroupAssignedUser.length; j++) {
					if (updateGroupAssignedUser[j].groupId == id) {
						updateGroupAssignedUser.splice(j, 1);
					}
				}
				var name = $(checkboxes[i]).attr("valname");
				var map = {
					"groupId": id,
					"groupName": name
				};
				updateGroupUndistributedUser.push(map);

				// 删除所在的tr
				n = $(checkboxes[i]).parents("tr").index();
				$("#updateGroupTable2").find("tr:eq(" + n + ")").remove();
			}
		}

		// 判断，如果已分配的成员全部分配，则，清空整个table
		if ($("input[name='updateGroupChecked2']").length < 1) {
			$("#updateGroupTable2").find("tr").remove();
		}

		// 选中的信息，放到未分配列表中
		var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateGroupChecked1\', \'updateTeamGroupCheckAll1\');" id="updateTeamGroupCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		for (var x = 0; x < updateGroupUndistributedUser.length; x++) {
			html += '<tr><td><input type="checkbox" name="updateGroupChecked1" valname="' + updateGroupUndistributedUser[x].groupName + '" valid="' + updateGroupUndistributedUser[x].groupId + '"/><span style="margin-left: 5px; font-size: 12px;">' + updateGroupUndistributedUser[x].groupName + '</span><td></tr>';
			htmlTable = true;
		}
		if (!htmlTable) {
			html = "";
		}
		$("#updateGroupTable1").html(html);
	},

	// 变更团队成员时，删除的团队成员
	updateRemoveTeamSelect: function() {
		
		// 未分配成员搜索框置成空
		$("#updateTeamUserSearchInput1").val("");
		
		var checkboxes = $("input[name='updateUserChecked2']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				for (var j = 0; j < updateAssignedUser.length; j++) {
					if (updateAssignedUser[j].userId == id) {
						updateAssignedUser.splice(j, 1);
					}
				}
				var name = $(checkboxes[i]).attr("valname");
				var map = {
					"userId": id,
					"userName": name
				};
				updateUndistributedUser.push(map);

				// 删除所在的tr
				n = $(checkboxes[i]).parents("tr").index();
				$("#updateUserTable2").find("tr:eq(" + n + ")").remove();
			}
		}

		// 判断，如果已分配的成员全部分配，则，清空整个table
		if ($("input[name='updateUserChecked2']").length < 1) {
			$("#updateUserTable2").find("tr").remove();
		}

		// 选中的信息，放到未分配列表中
		var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateUserChecked1\', \'updateTeamUserCheckAll1\');" id="updateTeamUserCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		for (var x = 0; x < updateUndistributedUser.length; x++) {
			html += '<tr><td><input type="checkbox" name="updateUserChecked1" valname="' + updateUndistributedUser[x].userName + '" valid="' + updateUndistributedUser[x].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + updateUndistributedUser[x].userName + '</span><td></tr>';
			htmlTable = true;
		}
		if (!htmlTable) {
			html = "";
		}
		$("#updateUserTable1").html(html);
	},

	// 新增团队时，删除团队成员
	removeTeamSelect: function() {
		
		// 未分配成员搜索框置成空
		$("#addTeamUserSearchInput1").val("");
		
		var checkboxes = $("input[name='userChecked2']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				for (var j = 0; j < addAssignedUser.length; j++) {
					if (addAssignedUser[j].userId == id) {
						addAssignedUser.splice(j, 1);
					}
				}
				var name = $(checkboxes[i]).attr("valname");
				var map = {
					"userId": id,
					"userName": name
				};
				addUndistributedUser.push(map);

				// 删除所在的tr
				n = $(checkboxes[i]).parents("tr").index();
				$("#addUserTable2").find("tr:eq(" + n + ")").remove();
			}
		}

		// 判断，如果已分配的成员全部分配，则，清空整个table
		if ($("input[name='userChecked2']").length < 1) {
			$("#addUserTable2").find("tr").remove();
			// 清空负责人列表
			$("#addUserTable3").find("tr").remove();
		} else {
			var str = "<tr>";
			for (var y = 0; y < addAssignedUser.length; y++) {
				if ((y + 1) % 3 == 0) {
					str += '</tr><tr>'
				}
				str += '<td><input type="checkbox" name="userChecked3" valid="' + addAssignedUser[y].userId + '" /><span class="userChecked3" style="margin-left: 5px; margin-right: 20px; font-size: 12px;">' + addAssignedUser[y].userName + '</span></td>';
			}
			$("#addUserTable3").html(str);
		}

		// 选中的信息，放到未分配列表中
		var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'userChecked1\', \'addTeamUserCheckAll1\');" id="addTeamUserCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		for (var x = 0; x < addUndistributedUser.length; x++) {
			html += '<tr><td><input type="checkbox" name="userChecked1" valname="' + addUndistributedUser[x].userName + '" valid="' + addUndistributedUser[x].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + addUndistributedUser[x].userName + '</span><td></tr>';
			htmlTable = true;
		}
		if (!htmlTable) {
			html = "";
		}
		$("#addUserTable1").html(html);
	},

	// 全选/全不选
	checkAll: function(name, id) {
		var checkboxes = $("input[name='" + name + "']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($("#" + id).prop('checked')) {
				$(checkboxes[i]).prop("checked", true);
			} else {
				$(checkboxes[i]).prop("checked", false);
			}
		}
	},

	// 变更团队成员时，搜索团队成员
	updateTeamUserSearch: function(indexStr, dataName) {
		var searchInfo = $.trim($("#updateTeamUserSearchInput" + indexStr).val());
		$("#updateUserTable" + indexStr).find("tr").remove();
		var html = '<tr><td><input type="checkbox" id="updateTeamUserCheckAll' + indexStr + '" onclick="teamInfo.checkAll(\'updateUserChecked' + indexStr + '\', \'updateTeamUserCheckAll' + indexStr + '\');"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		if (searchInfo == "") {
			// 如果搜索框为空，则显示所有的信息
			for (var i = 0; i < dataName.length; i++) {
				html += '<tr><td><input type="checkbox" name="updateUserChecked' + indexStr + '" valname="' + dataName[i].userName + '" valid="' + dataName[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + dataName[i].userName + '</span><td></tr>';
				htmlTable = true;
			}
		} else {
			// 根据输入的内容，有限显示信息
			for (var j = 0; j < dataName.length; j++) {
				if (dataName[j].userName.indexOf(searchInfo) > -1) {
					// 保留此项
					html += '<tr><td><input type="checkbox" name="updateUserChecked' + indexStr + '" valname="' + dataName[j].userName + '" valid="' + dataName[j].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + dataName[j].userName + '</span><td></tr>';
					htmlTable = true;
				}
			}
			if (!htmlTable) {
				html = "";
			}
		}
		$('#updateUserTable' + indexStr).html(html);
	},
	
	// 变更小区，搜索
	updateTeamGroupSearch : function(indexStr, dataName) {
		var searchInfo = $.trim($("#updateTeamGroupSearchInput" + indexStr).val());
		$("#updateGroupTable" + indexStr).find("tr").remove();
		var html = '<tr><td><input type="checkbox" id="updateTeamGroupCheckAll' + indexStr + '" onclick="teamInfo.checkAll(\'updateGroupChecked' + indexStr + '\', \'updateTeamGroupCheckAll' + indexStr + '\');"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		if (searchInfo == "") {
			// 如果搜索框为空，则显示所有的信息
			for (var i = 0; i < dataName.length; i++) {
				html += '<tr><td><input type="checkbox" name="updateGroupChecked' + indexStr + '" valname="' + dataName[i].groupName + '" valid="' + dataName[i].groupId + '"/><span style="margin-left: 5px; font-size: 12px;">' + dataName[i].groupName + '</span><td></tr>';
				htmlTable = true;
			}
		} else {
			// 根据输入的内容，有限显示信息
			for (var j = 0; j < dataName.length; j++) {
				if (dataName[j].groupName.indexOf(searchInfo) > -1) {
					// 保留此项
					html += '<tr><td><input type="checkbox" name="updateGroupChecked' + indexStr + '" valname="' + dataName[j].groupName + '" valid="' + dataName[j].groupId + '"/><span style="margin-left: 5px; font-size: 12px;">' + dataName[j].groupName + '</span><td></tr>';
					htmlTable = true;
				}
			}
			if (!htmlTable) {
				html = "";
			}
		}
		$('#updateGroupTable' + indexStr).html(html);		
	},

	// 新增团队时，搜索团队成员
	addTeamUserSearch: function(indexStr, dataName) {
		var searchInfo = $.trim($("#addTeamUserSearchInput" + indexStr).val());
		$("#addUserTable" + indexStr).find("tr").remove();
		var html = '<tr><td><input type="checkbox" id="addTeamUserCheckAll' + indexStr + '" onclick="teamInfo.checkAll(\'userChecked' + indexStr + '\', \'addTeamUserCheckAll' + indexStr + '\');"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
		var htmlTable = false;
		if (searchInfo == "") {
			// 如果搜索框为空，则显示所有的信息
			for (var i = 0; i < dataName.length; i++) {
				html += '<tr><td><input type="checkbox" name="userChecked' + indexStr + '" valname="' + dataName[i].userName + '" valid="' + dataName[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + dataName[i].userName + '</span><td></tr>';
				htmlTable = true;
			}
		} else {
			// 根据输入的内容，有限显示信息
			for (var j = 0; j < dataName.length; j++) {
				if (dataName[j].userName.indexOf(searchInfo) > -1) {
					// 保留此项
					html += '<tr><td><input type="checkbox" name="userChecked' + indexStr + '" valname="' + dataName[j].userName + '" valid="' + dataName[j].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + dataName[j].userName + '</span><td></tr>';
					htmlTable = true;
				}
			}
			if (!htmlTable) {
				html = "";
			}
		}
		$('#addUserTable' + indexStr).html(html);
	},

	// 删除团员，前端做好判断，点击负责人操作按钮时是没有删除团员的选项
	deleteTeamMem: function(member) {
		var memberId = member.memberId;
		var teamId = member.teamId;
		common.alert({
			msg: '团员将从此团队中删除，确定删除该团员吗？',
			confirm: true,
			fun: function(action) {
				if (action) {
					teamInfo.ajax({
						url: common.root + '/caas/team/removeTeamMember',
						contentType: 'application/json; charset=utf-8',
						dataType: 'json',
						type: 'POST',
						data: JSON.stringify({
							teamId: teamId,
							memberId: memberId
						}),
						success: function(data) {
							if (data.success == 1) {
								var info = data.result;
								if (info == 0) {
									common.alert({
										msg: '删除团员成功',
										fun: function() {
											for (var i = 0; i < 4; i++) {
												var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + teamId);
												if (tmpTeamElem.length > 0) {
													tmpTeamElem.trigger('click');
													break;
												}
											}
										},
									});
								} else if (info > 0) {
									common.alert({
										msg: '删除团员成功，同时释放此团队' + info + '间房源！',
										fun: function() {
											for (var i = 0; i < 4; i++) {
												var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + teamId);
												if (tmpTeamElem.length > 0) {
													tmpTeamElem.trigger('click');
													break;
												}
											}
										},
									});
								} else if (info == -2) {
									common.alert({
										msg: '不能删除团队仅剩的唯一负责人'
									});
								} else {
									common.alert({
										msg: '删除团员失败'
									});
								}
							} else {
								common.alert({
									msg: '删除团员失败'
								});
							}
						}
					});
				}
			}
		});
	},

	// 删除团队
	deleteTeam: function(team) {
		var teamList = team.teamList;
		if (teamList == undefined || teamList == null) {
			teamList = team;
		}

		var teamId = teamList.teamId;

		common.alert({
			msg: '确定删除此团队信息？',
			confirm: true,
			fun: function(action) {
				if (action) {
					teamInfo.ajax({
						url: common.root + '/caas/team/removeTeam',
						contentType: 'application/json; charset=utf-8',
						dataType: 'json',
						type: 'POST',
						data: JSON.stringify({
							teamId: teamId
						}),
						success: function(data) {
							if (data.success == 1) {
								common.alert({
									msg: '删除团队成功!',
									fun: function() {
										var tmpTeamElem = $('.first-team .list-info').find('li').find('#1_' + team.teamId);
										if (tmpTeamElem.length > 0) {
											teamInfo.searchTeam(true);
											return
										}
										for (var i = 0; i < 4; i++) {
											var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + team.parentTeamId);
											if (tmpTeamElem.length > 0) {
												tmpTeamElem.trigger('click');
												break;
											}
										}
									},
								});
							} else {
								common.alert({
									msg: '删除团队失败!'
								});
							}
						}
					});
				}
			}
		});



		teamInfo.ajax({
			url: common.root + '/caas/team/removeTeam',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify({
				teamId: teamId
			}),
			success: function(data) {
				if (data.success == 1) {
					var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateUserChecked1\', \'updateTeamUserCheckAll1\');" id="updateTeamUserCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
					$("#updateUserTable1").find("tr").remove();
					$("#updateUserTable2").find("tr").remove();
					$("#updateTeamUserSearchInput1").val("");
					$("#updateTeamUserSearchInput2").val("");
					var info = data.result;
					updateUndistributedUser = data.result;
					for (var i = 0; i < info.length; i++) {
						html += '<tr><td><input type="checkbox" name="updateUserChecked1" valname="' + info[i].userName + '" valid="' + info[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[i].userName + '</span><td></tr>';
					}
					$('#updateUserTable1').html(html);
				}
			}
		});
	},

	// 打开变更团队成员窗口
	deleteUser: function(team) {
		var teamList = team.teamList;
		if (teamList == undefined || teamList == null) {
			teamList = team;
		}
		$("#updateUserTeamName").val(teamList.teamName);
		var teamId = teamList.teamId;
		$("#updateUserTeamId").val(teamId);
		$("#updateUserParentTeamId").val(teamList.parentTeamId);

		updateAssignedUser = [];
		updateUndistributedUser = [];
		
		$('#updateUserInfo').on('show.bs.modal', function() {
			// 初始化未分配团队成员
			teamInfo.ajax({
				url: common.root + '/caas/team/getUserList',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				type: 'POST',
				async: false,
				data: JSON.stringify({
					ifMem: -1,
					teamId: teamId
				}),
				success: function(data) {
					if (data.success == 1) {
						var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateUserChecked1\', \'updateTeamUserCheckAll1\');" id="updateTeamUserCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span></td></tr>';
						$("#updateUserTable1").find("tr").remove();
						$("#updateTeamUserSearchInput1").val("");
						$("#updateTeamUserSearchInput2").val("");
						var info = data.result;
						updateUndistributedUser = data.result;
						for (var i = 0; i < info.length; i++) {
							html += '<tr><td><input type="checkbox" name="updateUserChecked1" valname="' + info[i].userName + '" valid="' + info[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[i].userName + '</span></td></tr>';
						}
						$('#updateUserTable1').html(html);
					}
				}
			});

			// 初始化已分配团队成员
			teamInfo.ajax({
				url: common.root + '/caas/team/getUserList',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				type: 'POST',
				async: false,
				data: JSON.stringify({
					ifMem: 1,
					teamId: teamId
				}),
				success: function(data) {
					if (data.success == 1) {
						var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateUserChecked2\', \'updateTeamUserCheckAll2\');" id="updateTeamUserCheckAll2"/><span style="margin-left: 5px; font-size: 12px;">全选</span></td></tr>';
						var info = data.result;
						updateAssignedUser = data.result;
						var updateUserIds = "";
						$("#updateUserTable2").find("tr").remove();
						for (var y = 0; y < info.length; y++) {
							html += '<tr><td><input type="checkbox" name="updateUserChecked2" valname="' + info[y].userName + '" valid="' + info[y].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[y].userName + '</span></td></tr>';
							updateUserIds += info[y].userId + ",";
						}
						$('#updateUserTable2').html(html);
						$("#updateUserIds").val(updateUserIds);
					}
				}
			});

		});
		$('#updateUserInfo').modal('show');

	},

	// 保存团队负责人
	updateTeamLeaderInfo: function() {
		var teamId = $("#updateTeamId").val();
		var leaderId = $("#updateLeader").val();
		var parentTeamId = $("#updateLeaderParentTeamId").val();
		
		// 获取团队负责人信息
		var leaderList = new Array();
		var checkboxes = $("input[name='leaderChecked']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				if ($.trim(id) == '') {
					continue;
				}
				leaderList.push({
					leaderId: id
				});
			}
		}
		if (leaderList.length == 0) {
			common.alert({
				msg: '请选择团队负责人'
			});
			return;
		}
		teamInfo.ajax({
			url: common.root + '/caas/team/modifyTeamCharge',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify({
				teamId: teamId,
				leaderList:leaderList
			}),
			success: function(data) {
				if (data.success == 1) {
					var info = data.result;
					if (info == 1) {
						common.alert({
							msg: '修改成功',
							fun: function() {
								var tmpTeamElem = $('.first-team .list-info').find('li').find('#1_' + teamId);
								if (tmpTeamElem.length > 0) {
									teamInfo.searchTeam(true);
									return
								}
								for (var i = 0; i < 4; i++) {
									var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + parentTeamId);
									if (tmpTeamElem.length > 0) {
										tmpTeamElem.trigger('click');
										break;
									}
								}
							},							
						});

						$("#updateTeamLeaderInfo").modal('hide');
					} else {
						common.alert({
							msg: '修改失败'
						});
					}
				} else {
					common.alert({
						msg: '修改失败'
					});
				}
			}
		});

	},

	// 保存团队名称
	modifyTeamNameInfo: function() {

		var teamId = $("#updateTeamId").val();
		var teamName = $("#modifyTeamName").val();
		var parentTeamId = $("#modifyTeamParentTeamId").val();

		teamInfo.ajax({
			url: common.root + '/caas/team/modifyTeamName',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify({
				teamId: teamId,
				teamName: teamName
			}),
			success: function(data) {
				if (data.success == 1) {
					var info = data.result;
					if (info == 1) {
						common.alert({
							msg: '团队名称修改成功',
							fun: function() {
								var tmpTeamElem = $('.first-team .list-info').find('li').find('#1_' + teamId);
								if (tmpTeamElem.length > 0) {
									teamInfo.searchTeam(true);
									return
								}
								for (var i = 0; i < 4; i++) {
									var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + parentTeamId);
									if (tmpTeamElem.length > 0) {
										tmpTeamElem.trigger('click');
										break;
									}
								}
							},							
						});
						$("#modifyTeamNameInfo").modal('hide');
					} else if (info == -1) {
						common.alert({
							msg: '团队名称已经存在，请重新填写'
						});
					} else {
						common.alert({
							msg: '团队名称修改失败'
						});						
					}
				} else {
					common.alert({
						msg: '团队名称修改失败'
					});
				}
			}
		});
	},

	// 打开变更负责人窗口
	openUpdateTeamLeader: function(team) {

		var teamList = team.teamList;
		if (teamList == undefined || teamList == null) {
			teamList = team;
		}
		
		$("#updateName").val(teamList.teamName);
		var teamId = teamList.teamId;
		$("#updateTeamId").val(teamId);
		$("#updateLeaderParentTeamId").val(teamList.parentTeamId);

		var name;
		for (var i = 0; i < 4; i++) {
			var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + teamList.parentTeamId);
			if (tmpTeamElem.length > 0) {
				for (var j = 0; j < tmpTeamElem.length; j++) {
					if (!$(tmpTeamElem[j]).hasClass('member')) {
						name = $(tmpTeamElem[j]).find("span.team-member-name").text();
						var subName = $(tmpTeamElem[j]).find(".team-member-name .manager").text();
						name = name.split(subName)[0];
						break;
					}
				}
			}
		}
		if (name == null || name == undefined) {
			name = '无';
		}
		$('#updateLevel').val(name);

		$('#updateTeamLeaderInfo').on('show.bs.modal', function() {
			teamInfo.ajax({
				url: common.root + '/caas/team/getUserList',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				type: 'POST',
				data: JSON.stringify({
					ifMem: 1,
					teamId: teamId
				}),
				success: function(data) {
					if (data.success == 1) {
						var html = '<tr>';
						$("#updateTeamLeader").find("tr").remove();
						var info = data.result;
						for (var i = 0; i < info.length; i++) {
							var isLeader = info[i].isLeader;
							if ((i + 1) % 3 == 0) {
								html += '</tr><tr>'
							}
							var flag = false;
							
							if (isLeader == 1) {
								html += '<td><input checked type="checkbox" name="leaderChecked" valname="' + info[i].userName + '" valid="' + info[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[i].userName + '</span><td>';
							} else {
								html += '<td><input type="checkbox" name="leaderChecked" valname="' + info[i].userName + '" valid="' + info[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[i].userName + '</span><td>';
							}
						}
						$('#updateTeamLeader').html(html);
					} else {
						common.alert({
							msg: '获取人员失败'
						});
					}
				}
			});
		});
		$('#updateTeamLeaderInfo').modal('show');
	},

	// 打开修改团队名称窗口
	modifyTeamName: function(team) {
		var teamList = team.teamList;
		if (teamList == undefined || teamList == null) {
			teamList = team;
		}
		$("#modifyTeamName").val(teamList.teamName);
		var teamId = teamList.teamId;
		$("#updateTeamId").val(teamId);
		$("#modifyTeamParentTeamId").val(teamList.parentTeamId)
		var teamLevel = teamList.teamLevel;
		if (teamLevel == 1) {
			$('#modifyTeamLevel').val('一级团队');
		} else if (teamLevel == 2) {
			$('#modifyTeamLevel').val('二级团队');
		} else if (teamLevel == 3) {
			$('#modifyTeamLevel').val('三级团队');
		}

		var leaderName = teamList.leaderName;
		$("#modifyTeamLeader").val(leaderName);

		$('#modifyTeamNameInfo').modal('show');
	},

	// 保存团队变更小区
	updateGroupInfo: function() {
		// 团队ID
		var teamId = $("#updateTeamId").val();
		var parentTeamId = $("#updateGroupPranetTeamId").val();

		// 初始已经分配的小区
		var areaId = $("#updateGroupIds").val();
		areaId = areaId.substring(0, areaId.length - 1);
		var oldArr = areaId.split(",");

		// 变更后的待分配小区
		var waitArr = new Array();
		for (var i = 0; i < updateGroupAssignedUser.length; i++) {
			waitArr.push(updateGroupAssignedUser[i].groupId);
		}

		// 对比 前后小区分配情况，计算移除小区ID
		var removeAreaList = new Array();
		for (var i = 0; i < oldArr.length; i++) {
			var flag = true;
			var id = oldArr[i];
			if (id == '') {
				continue;
			}
			for (var j = 0; j < waitArr.length; j++) {
				var waitId = waitArr[j];
				if (id == waitId) {
					flag = false;
					break;
				}
			}
			if (flag) {
				removeAreaList.push({groupId: id});
			}
		}
		
		if (removeAreaList.length == 0) {
			removeAreaList = null;
		}
		
		// 计算新增小区ID
		var areaIdList = new Array();
		for (var ii = 0; ii < waitArr.length; ii++) {
			var flagi = true;
			var idi = waitArr[ii];
			if (idi == '') {
				continue;
			}
			for (var jj = 0; jj < oldArr.length; jj++) {
				var waitIdi = oldArr[jj];
				if (idi == waitIdi) {
					flagi = false;
					break;
				}
			}
			if (flagi) {
				areaIdList.push({groupId: idi});
			}
		}

		if (areaIdList.length == 0) {
			areaIdList = null;
		}
		
		var param;
		var teamMem = $("#teamMem").val();
		var url;
		if (teamMem == 'ifMemRel') {
			var memberId = $("#teamMemUserId").val();
			url = "/caas/team/findAreaMemberRel";
			param = {
					'memberId': memberId,
					'teamId': teamId,
					'areaIdList': areaIdList,
					'removeAreaList': removeAreaList
				};
		} else {
			url = "/caas/team/findAreaRel";
			param = {
					'teamId': teamId,
					'areaIdList': areaIdList,
					'removeAreaList': removeAreaList
			};
		}
		
		common.load.load("正在更新");
		teamInfo.ajax({
			url: common.root + url,
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify(param),
			success: function(data) {
				if (data.success == 1) {
					var info = data.result;
					if (info == 1) {
						common.load.hide();
						common.alert({
							msg: '小区变更成功',
							fun: function() {
								for (var i = 0; i < 4; i++) {
									var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + teamId);
									if (tmpTeamElem.length > 0) {
										tmpTeamElem.trigger('click');
										break;
									}
								}
								
								if (parentTeamId != '') {
									for (var i = 0; i < 4; i++) {
										var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + parentTeamId);
										if (tmpTeamElem.length > 0) {
											tmpTeamElem.trigger('click');
											break;
										}
									}									
								}
							},
						});
						$("#updateGroupInfo").modal('hide');
					} else {
						common.load.hide();
						common.alert({
							msg: '小区变更失败'
						});
					}
				} else {
					common.load.hide();
					common.alert({
						msg: '小区变更失败'
					});
				}
			}
		});

	},

	// 团队变更小区
	updateTeamGroup: function(team) {
		var teamList = team.teamList;
		if (teamList == undefined || teamList == null) {
			teamList = team;
		}
		
		var parentTeamId = teamList.parentTeamId;
		if (parentTeamId == null || parentTeamId == undefined) {
			parentTeamId = "";
		}
		var teamId = teamList.teamId;
		var name = teamList.teamName;
		var userId;
		var teamMem = "ifTeamRel";
		if (name == null || name == undefined) {
			teamMem = "ifMemRel";
			for (var i = 0; i < 4; i++) {
				var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + teamId);
				if (tmpTeamElem.length > 0) {
					for (var j = 0; j < tmpTeamElem.length; j++) {
						if (!$(tmpTeamElem[j]).hasClass('member')) {
							name = $(tmpTeamElem[j]).find("span.team-member-name").text();
							var subName = $(tmpTeamElem[j]).find(".team-member-name .manager").text();
							name = name.split(subName)[0];
							userId = teamList.memberId;
							break;
						}
					}
				}
			}			
		}		
		
		$("#teamMem").val(teamMem);
		$("#teamMemUserId").val(userId);		
		$("#updateTeamName").val(name);
		$("#updateTeamId").val(teamId);
		$("#updateGroupPranetTeamId").val(parentTeamId);
		
		var param1;
		var param2;
		if (teamMem == 'ifTeamRel') {
			param1 = {
					ifTeamRel: 1,
					teamId: teamId
			};
			
			param2 = {
					ifTeamRel: -1,
					teamId: teamId
			};
		}
		if (teamMem == 'ifMemRel') {
			param1 = {
					ifMemRel: 1,
					memberList:[{"userId":userId}]
			};
			
			param2 = {
					ifMemRel: -1,
					memberList:[{"userId":userId}],
					teamId: teamId
			};
		}
		
		updateGroupAssignedUser = [];
		updateGroupUndistributedUser = [];

		// 团队关联到的小区
		teamInfo.ajax({
			url: common.root + '/caas/team/getAreaInfo',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			async: false,
			data: JSON.stringify(param1),
			success: function(data) {
				if (data.success == 1) {
					var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateGroupChecked2\', \'updateTeamGroupCheckAll2\');" id="updateTeamGroupCheckAll2"/><span style="margin-left: 5px; font-size: 12px;">全选</span></td></tr>';
					var info = data.result;
					updateGroupAssignedUser = data.result;
					var updateGroupIds = "";
					$("#updateGroupTable2").find("tr").remove();
					for (var y = 0; y < info.length; y++) {
						html += '<tr><td><input type="checkbox" name="updateGroupChecked2" valname="' + info[y].groupName + '" valid="' + info[y].groupId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[y].groupName + '</span></td></tr>';
						updateGroupIds += info[y].groupId + ",";
					}
					$('#updateGroupTable2').html(html);
					$("#updateGroupIds").val(updateGroupIds);
				} 
			}
		});

		// 团队未分配的小区
		teamInfo.ajax({
			url: common.root + '/caas/team/getAreaInfo',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			async: false,
			data: JSON.stringify(param2),
			success: function(data) {
				if (data.success == 1) {
					var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'updateGroupChecked1\', \'updateTeamGroupCheckAll1\');" id="updateTeamGroupCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span></td></tr>';
					$("#updateGroupTable1").find("tr").remove();
					$("#updateTeamGroupSearchInput1").val("");
					$("#updateTeamGroupSearchInput2").val("");
					var info = data.result;
					updateGroupUndistributedUser = data.result;
					for (var i = 0; i < info.length; i++) {
						html += '<tr><td><input type="checkbox" name="updateGroupChecked1" valname="' + info[i].groupName + '" valid="' + info[i].groupId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[i].groupName + '</span></td></tr>';
					}
					$('#updateGroupTable1').html(html);		
				}
			}
		});
		$('#updateGroupInfo').modal('show');
	},

	// 保存团队成员信息
	updateUserTeamInfo: function() {
		// 团队id
		var teamId = $("#updateUserTeamId").val();
		var parentTeamId = $("#updateUserParentTeamId").val();

		// 初始已经分配的成员
		var userId = $("#updateUserIds").val();
		userId = userId.substring(0, userId.length - 1);
		var oldArr = userId.split(",");

		// 变更后的待分配成员
		var waitArr = new Array();
		for (var i = 0; i < updateAssignedUser.length; i++) {
			waitArr.push(updateAssignedUser[i].userId);
		}

		// 对比 前后小区分配情况，计算移除人员ID
		var removeMemberList = new Array();
		for (var i = 0; i < oldArr.length; i++) {
			var flag = true;
			var id = oldArr[i];
			if (id == '') {
				continue;
			}
			for (var j = 0; j < waitArr.length; j++) {
				var waitId = waitArr[j];
				if (id == waitId) {
					flag = false;
					break;
				}
			}
			if (flag) {
				removeMemberList.push({userId: id});
			}
		}
		
		if (removeMemberList.length == 0) {
			removeMemberList = null;
		}

		// 计算新增人员ID
		var memberList = new Array();
		for (var ii = 0; ii < waitArr.length; ii++) {
			var flagi = true;
			var idi = waitArr[ii];
			if (idi == '') {
				continue;
			}
			for (var jj = 0; jj < oldArr.length; jj++) {
				var waitIdi = oldArr[jj];
				if (idi == waitIdi) {
					flagi = false;
					break;
				}
			}
			if (flagi) {
				memberList.push({
					userId: idi
				});
			}
		}
		
		if (memberList.length == 0) {
			memberList = null;
		}

		var param = {
			'teamId': teamId,
			'memberList': memberList,
			'removeMemberList': removeMemberList
		};

		teamInfo.ajax({
			url: common.root + '/caas/team/modifyTeamMember',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify(param),
			success: function(data) {
				if (data.success == 1) {
					var info = data.result;
					if (info >= 0) {
						common.alert({
							msg: '成员变更成功',
							fun: function() {
								for (var i = 0; i < 4; i++) {
									var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + teamId);
									if (tmpTeamElem.length > 0) {
										tmpTeamElem.trigger('click');
										break;
									}
								}
							},							
						});
						$("#updateUserInfo").modal('hide');
					} else if (info == -2) {
						common.alert({
							msg: '团队必需保留一个负责人'
						});						
					} else {
						common.alert({
							msg: '成员变更失败'
						});
					}
				} else {
					common.alert({
						msg: '成员变更失败'
					});
				}
			}
		});

	},

	// 新增团队信息
	addTeamInfo: function() {
		var teamName = $.trim($("#addTeamName").val());
		if (teamName == '') {
			common.alert({
				msg: '请填写团队名称'
			});
			return;
		}

		// 团队等级
		var teamLevel = $("#addTeamLevelId").val();
		// 父团队id
		var parentTeamId = $("#addParentTeamId").val();

		// 团员列表
		if (addAssignedUser.length < 1) {
			common.alert({
				msg: '请选择团队成员'
			});
			return;
		}
		var memberList = new Array();
		for (var i = 0; i < addAssignedUser.length; i++) {
			memberList.push({
				userId: addAssignedUser[i].userId
			});
		}

		// 获取团队负责人信息
		var leaderList = new Array();
		var checkboxes = $("input[name='userChecked3']");
		for (var i = 0; i < checkboxes.length; i++) {
			if ($(checkboxes[i]).prop('checked')) {
				var id = $(checkboxes[i]).attr("valid");
				if ($.trim(id) == '') {
					continue;
				}
				leaderList.push({
					leaderId: id
				});
			}
		}
		
		if (leaderList.length == 0) {
			common.alert({
				msg: '请选择团队负责人'
			});
			return;
		}
		
		var param = {
			'teamLevel': teamLevel,
			'parentTeamId': parentTeamId,
			'teamName': teamName,
			'memberList': memberList,
			'leaderList': leaderList
		};

		common.load.load('正在添加中');
		teamInfo.ajax({
			url: common.root + '/caas/team/addTeam',
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify(param),
			success: function(data) {
				if (data.success == 1) {
					common.load.hide();
					var info = data.result;
					if (info == 1) {
						common.alert({
							msg: '新增成功',
							fun: function() {
								var tmpTeamElem = $('.first-team .list-info').find('li').find('#1_' + teamLevel);
								if (tmpTeamElem.length > 0) {
									teamInfo.searchTeam(true);
									return
								}
								for (var i = 0; i < 4; i++) {
									var tmpTeamElem = $('.list-info').find('li').find('#' + (i + 1) + '_' + parentTeamId);
									if (tmpTeamElem.length > 0) {
										tmpTeamElem.trigger('click');
										break;
									}
								}
							},								
						});
						$("#addTeamInfo").modal('hide');
					} else if (info == -2) {
						common.alert({
							msg: '团队名称重复，请重新填写'
						});
					} else{
						common.alert({
							msg: '新增失败'
						});
					}
				} else {
					common.load.hide();
					common.alert({
						msg: '新增失败'
					});
				}
			}
		});

	},

	// 打开新增团队窗口，并初始化数据
	openAddTeamWindow: function(className) {
		$('#addTeamInfo').on('show.bs.modal', function() {
			// 初始化团队成员以及负责人
			addUndistributedUser = [];
			addAssignedUser = [];
			teamInfo.ajax({
				url: common.root + '/caas/team/getUserList',
				contentType: 'application/json; charset=utf-8',
				dataType: 'json',
				type: 'POST',
				async: false,
				data: JSON.stringify({
					ifMem: -1
				}),
				success: function(data) {
					if (data.success == 1) {
						var html = '<tr><td><input type="checkbox" onclick="teamInfo.checkAll(\'userChecked1\', \'addTeamUserCheckAll1\');" id="addTeamUserCheckAll1"/><span style="margin-left: 5px; font-size: 12px;">全选</span><td></tr>';
						$("#addUserTable1").find("tr").remove();
						$("#addUserTable2").find("tr").remove();
						$("#addUserTable3").find("tr").remove();
						$("#addTeamName").val("");
						$("#parentTeamName").val("");
						$("#addTeamUserSearchInput1").val("");
						$("#addTeamUserSearchInput2").val("");
						var info = data.result;
						addUndistributedUser = data.result;
						for (var i = 0; i < info.length; i++) {
							html += '<tr><td><input type="checkbox" name="userChecked1" valname="' + info[i].userName + '" valid="' + info[i].userId + '"/><span style="margin-left: 5px; font-size: 12px;">' + info[i].userName + '</span><td></tr>';
						}
						$('#addUserTable1').html(html);

						// 父团队信息
						var parents = $("." + className).data('parentTeam');
						if (parents == null || parents == undefined) {
							$("#addTeamLevelId").val(1);
							$("#parentTeamName").val("无");
						} else {
							var parentInfo = JSON.parse($("." + className).data('parentTeam'));
							$("#addParentTeamId").val(parentInfo.teamId);
							$("#parentTeamName").val(parentInfo.teamName);
							if (parentInfo.teamLevel == '' || parentInfo.teamLevel == undefined || parentInfo.teamLevel == null) {
								$("#addTeamLevelId").val(1);
							} else {
								$("#addTeamLevelId").val(parentInfo.teamLevel + 1);
							}
						}
					}
				}
			});
		});
		$('#addTeamInfo').modal('show');
	},

	teamLevelChoose: function(event) {
		var levelText = $(event.target).text();
		teamInfo.level = $(event.target).attr('id');
		$('#team-level').html(levelText + ' <span class="caret"></span>');
	},
	searchTeam: function(isInit) {
		common.load.load('搜索中');
		teamInfo.ajax({
			url: common.root + (isInit ? '../caas/team/getTeamPage' : '../caas/team/getTeamByDimSearch'),
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			data: isInit ? '' : JSON.stringify({
				'searchType': parseInt(teamInfo.level),
				'teamName': $('#t-name').val()
			}),
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					$('.first-team ul').empty();
					$('.second-team').empty();
					$('.third-team').empty();
					$('.four-team').empty();
					$('.third-team-member').empty();
					var firstHtml = '';
					var secondHtml = '<div class="list-content">';
					var thirdHtml = '<div class="list-content">';
					var fourHtml = '<div class="list-content">';
					var fiveHtml = '<div class="list-content">';
					var teamsLevel1 = new Array();
					var teamsLevel2 = new Array();
					var teamsLevel3 = new Array();
					var teamsLevel4 = new Array();
					var firstMembers = new Array();
					var secondMembers = new Array();
					var thirdMembers = new Array();
					var fourMembers = new Array();
					if (data.result.length <= 0) {
						common.alert({
							msg: "抱歉，没有找到您想要的结果，建议先检查搜索”方式”和搜索“关键字”后重新搜索！"
						}
						);
						return;
					}
					for (var i = 0; i < data.result.length; i++) {
						var teamMap = data.result[i];
						var team1 = teamMap.teamBean1;
						var team2 = teamMap.teamBean2;
						var team3 = teamMap.teamBean3;
						var team4 = teamMap.teamBean4;
						if (teamMap.ifAdmin == 1) {
							teamInfo.isAdmin = true;
						}
						if (team1 != undefined && team1 != null && team1 != 'undefined') {

							var team1Flag = true;
							for (var p = 0; p < teamsLevel1.length; p++) {
								if (teamsLevel1[p].teamId == team1.teamId) {
									team1Flag = false;
									break;
								}
							}
							if (team1Flag) {
								teamsLevel1.push(team1);
								firstHtml += '<li><a id="' + 1 + '_' + team1.teamId + '" href="#" onclick="teamInfo.loadSubTeam(this,' + (teamsLevel1.length - 1) + ');"><span class="selected-pointer">•&nbsp;</span>';
								firstHtml += '<span class="team-member-name">' + team1.teamName + '<span class="manager">(' + team1.leaderName + ')</span></span>';
								if (team1.ifOp == 1 && team1.teamId != -2) {
									firstHtml += '<span href="#" onclick="teamInfo.editTeam(event,' + JSON.stringify(team1).replace(/\"/g, "\'") + ');" class="edit"></span>';
								}
								firstHtml += '<div style="overflow: hidden;height: 100%;">';
								firstHtml += '<span class="house-count">' + team1.houseCnt + '间</span>';
								firstHtml += '</div>';
								firstHtml += '</a></li>';
							}
						}
						if (team2 != undefined && team2 != null && team2 != 'undefined') {
							var team2Flag = true;
							for (var p = 0; p < teamsLevel2.length; p++) {
								if (teamsLevel2[p].teamId == team2.teamId) {
									team2Flag = false;
									break;
								}
							}
							if (team2Flag) {
								teamsLevel2.push(team2);
							}
						}
						if (team3 != undefined && team3 != null && team3 != 'undefined') {
							var team3Flag = true;
							for (var p = 0; p < teamsLevel3.length; p++) {
								if (teamsLevel3[p].teamId == team3.teamId) {
									team3Flag = false;
									break;
								}
							}
							if (team3Flag) {
								teamsLevel3.push(team3);
							}
						}
						if (team4 != undefined && team4 != null && team4 != 'undefined') {
							var team4Flag = true;
							for (var p = 0; p < teamsLevel4.length; p++) {
								if (teamsLevel4[p].teamId == team4.teamId) {
									team4Flag = false;
									break;
								}
							}
							if (team4Flag) {
								teamsLevel4.push(team4);
							}
						}
					}
					if (teamInfo.isAdmin) {
						$('.first-team').append('<a class="increase-button" href="#" onclick="teamInfo.openAddTeamWindow(\'first-team\');"><span class="glyphicon plus" aria-hidden="true">＋</span>&nbsp;新增团队</a>');
					}
					for (var i = 0; i < teamsLevel1.length; i++) {
						var team1 = teamsLevel1[i];
						// 符合条件的成员
						firstMembers = team1.memberList;
						for (var m = 0; m < firstMembers.length; m++) {
							firstMembers[m].isParent = team1.isParent;
						}
						if (teamsLevel2.length > 0) {
							var isTeamEmpty = true;
							for (var k = 0; k < teamsLevel2.length; k++) {
								var team2 = teamsLevel2[k];
								if (team2.parentTeamId == team1.teamId) {
									isTeamEmpty = false;
									break;
								}
							}
							if (isTeamEmpty && firstMembers.length <= 0) {
								continue;
							}
							secondHtml += '<span class="team-father-name">' + team1.teamName + '</span><ul class="list-info">';
							// var hasTeam1 = false
							for (var j = 0; j < teamsLevel2.length; j++) {
								var team2 = teamsLevel2[j];
								if (team2.parentTeamId == team1.teamId) {
									// hasTeam1 = true;
									secondHtml += '<li><a id="' + 2 + '_' + team2.teamId + '" href="#" onclick="teamInfo.loadSubTeam(this,' + j + ');"><span class="selected-pointer">•&nbsp;</span>';
									secondHtml += '<span class="team-member-name">' + team2.teamName + '<span class="manager">(' + team2.leaderName + ')</span></span>';
									if (team2.ifOp == 1) {
										secondHtml += '<span href="#" onclick="teamInfo.editTeam(event,' + JSON.stringify(team2).replace(/\"/g, "\'") + ');" class="edit"></span>';
									}
									secondHtml += '<div style="overflow: hidden;height: 100%;">';
									secondHtml += '<span class="house-count">' + team2.houseCnt + '间</span>';
									secondHtml += '</div>';
									secondHtml += '</a></li>';
									// 符合查询条件的成员
									secondMembers = team2.memberList;
									for (var m = 0; m < secondMembers.length; m++) {
										secondMembers[m].isParent = team2.isParent;
									}
								} else {
									continue;
								}

								if (teamsLevel3.length > 0) {
									var isTeamEmpty = true;
									var isMemberEmpty = true;
									for (var k = 0; k < teamsLevel3.length; k++) {
										var team3 = teamsLevel3[k];
										if (team3.parentTeamId == team2.teamId) {
											isTeamEmpty = false;
											break;
										}
									}
									if (isTeamEmpty && secondMembers.length <= 0) {
										continue;
									}
									thirdHtml += '<span class="team-father-name">' + team2.teamName + '</span><ul class="list-info">';
									// var hasTeam2 = false;
									for (var k = 0; k < teamsLevel3.length; k++) {
										var team3 = teamsLevel3[k];
										if (team3.parentTeamId == team2.teamId) {
											// hasTeam2 = true;
											thirdHtml += '<li><a id="' + 3 + '_' + team3.teamId + '" href="#" onclick="teamInfo.loadSubTeam(this,' + k + ');"><span class="selected-pointer">•&nbsp;</span>';
											thirdHtml += '<span class="team-member-name">' + team3.teamName + '<span class="manager">(' + team3.leaderName + ')</span></span>';
											if (team3.ifOp == 1) {
												thirdHtml += '<span href="#" onclick="teamInfo.editTeam(event,' + JSON.stringify(team3).replace(/\"/g, "\'") + ');" class="edit"></span>';
											}
											thirdHtml += '<div style="overflow: hidden;height: 100%;">';
											thirdHtml += '<span class="house-count">' + team3.houseCnt + '间</span>';
											thirdHtml += '</div>';
											thirdHtml += '</a></li>';
											// 符合查询条件的成员
											thirdMembers = team3.memberList;
											for (var m = 0; m < thirdMembers.length; m++) {
												thirdMembers[m].isParent = team3.isParent;
											}
										} else {
											continue;
										}
										if (teamsLevel4.length > 0) {
											var isTeamEmpty = true;
											for (var n = 0; n < teamsLevel4.length; n++) {
												var team4 = teamsLevel4[n];
												if (team4.parentTeamId == team3.teamId) {
													isTeamEmpty = false;
													break;
												}
											}
											if (isTeamEmpty && thirdMembers.length <= 0) {
												continue;
											}
											fourHtml += '<span class="team-father-name">' + team3.teamName + '</span><ul class="list-info">';
											// var hasTeam3 = false;
											for (var f = 0; f < teamsLevel4.length; f++) {
												var team4 = teamsLevel4[f];
												if (team4.parentTeamId == team3.teamId) {
													// hasTeam3 = true;
													fourHtml += '<li><a id="' + 4 + '_' + team4.teamId + '" href="#" onclick="teamInfo.loadSubTeam(this,' + f + ');"><span class="selected-pointer">•&nbsp;</span>';
													fourHtml += '<span class="team-member-name">' + team4.teamName + '<span class="manager">(' + team4.leaderName + ')</span></span>';
													if (team2.ifOp == 1) {
														fourHtml += '<span href="#" onclick="teamInfo.editTeam(event,' + JSON.stringify(team4).replace(/\"/g, "\'") + ');" class="edit"></span>';
													}
													fourHtml += '<div style="overflow: hidden;height: 100%;">';
													fourHtml += '<span class="house-count">' + team4.houseCnt + '间</span>';
													fourHtml += '</div>';
													fourHtml += '</a></li>';
													// 符合查询条件的成员
													fourMembers = team4.memberList;
													for (var m = 0; m < fourMembers.length; m++) {
														fourMembers[m].isParent = team4.isParent;
													}
												} else {
													continue;
												}
												var isMemberEmpty = true;
												for (var n = 0; n < fourMembers.length; n++) {
													var member5 = fourMembers[n];
													if (member5.teamId == team4.teamId) {
														isMemberEmpty = false;
														break;
													}
												}
												if (isMemberEmpty) {
													continue;
												}
												if (team4.ifOp) {
													var uniqueFourMembers = new Array();
													var fourMemberIds = new Array();
													// 去重第四级团队的成员
													for (var h = 0; h < fourMembers.length; h++) {
														var member = fourMembers[h];
														if (member.teamId == team4.teamId && $.inArray(member.memberId, fourMemberIds) == -1) {
															fourMemberIds.push(member.memberId);
															uniqueFourMembers.push(member);
														}
													}
													if (uniqueFourMembers.length > 0) {
														fiveHtml += '<span class="team-father-name">' + team4.teamName + '</span><ul class="list-info">';
														fiveHtml += teamInfo.convertSubTeamMemberData(uniqueFourMembers, team4.teamId);
														fiveHtml += "</ul>";
													}
												}
											}
											// 判断是否是父团队负责人
											if (team3.ifOp == 1) {
												var uniqueThirdMembers = new Array();
												var thirdMemberIds = new Array();
												// 去重第三级团队的成员
												for (var s = 0; s < thirdMembers.length; s++) {
													var member = thirdMembers[s];
													if (member.teamId == team3.teamId && $.inArray(member.memberId, thirdMemberIds) == -1) {
														thirdMemberIds.push(member.memberId);
														uniqueThirdMembers.push(member);
													}
												}
												if (uniqueThirdMembers.length > 0) {
													// 拼接第三级团队成员
													fourHtml += teamInfo.convertSubTeamMemberData(uniqueThirdMembers, team3.teamId);
												}
											}
											fourHtml += "</ul>";
										} else if (thirdMembers.length > 0) {
											if (team3.ifOp) {
												var uniqueThirdMembers = new Array();
												var thirdMemberIds = new Array();
												// 去重第三级团队的成员
												for (var s = 0; s < thirdMembers.length; s++) {
													var member = thirdMembers[s];
													if (member.teamId == team3.teamId && $.inArray(member.memberId, thirdMemberIds) == -1) {
														thirdMemberIds.push(member.memberId);
														uniqueThirdMembers.push(member);
													}
												}
												// 拼接第三级团队成员
												fourHtml += '<span class="team-father-name">' + team3.teamName + '</span><ul class="list-info">';
												fourHtml += teamInfo.convertSubTeamMemberData(uniqueThirdMembers, team3.teamId);
												fourHtml += "</ul>";
											}
										}

									}
									// 判断是否是父团队负责人
									if (team2.ifOp == 1) {
										var uniqueSecondMembers = new Array();
										var secondMemberIds = new Array();
										// 去重第二级团队的成员
										for (var s = 0; s < secondMembers.length; s++) {
											var member = secondMembers[s];
											if (member.teamId == team2.teamId && $.inArray(member.memberId, secondMemberIds) == -1) {
												secondMemberIds.push(member.memberId);
												uniqueSecondMembers.push(member);
											}
										}
										if (uniqueSecondMembers.length > 0) {
											thirdHtml += teamInfo.convertSubTeamMemberData(uniqueSecondMembers, team2.teamId);
										}

									}
									thirdHtml += "</ul>";
								} else if (secondMembers.length > 0) {
									if (team2.ifOp == 1) {
										var uniqueSecondMembers = new Array();
										var secondMemberIds = new Array();
										// 去重第二级团队的成员
										for (var s = 0; s < secondMembers.length; s++) {
											var member = secondMembers[s];
											if (member.teamId == team2.teamId && $.inArray(member.memberId, secondMemberIds) == -1) {
												secondMemberIds.push(member.memberId);
												uniqueSecondMembers.push(member);
											}
										}
										// 拼接第二级团队成员
										thirdHtml += '<span class="team-father-name">' + team2.teamName + '</span><ul class="list-info">';
										thirdHtml += teamInfo.convertSubTeamMemberData(uniqueSecondMembers, team2.teamId);

										thirdHtml += "</ul>";
									}
								}
							}
							// 判断是否是父团队的负责人
							if (team1.ifOp == 1) {
								var uniqueFirstMembers = new Array();
								var firstMemberIds = new Array();
								// 去重第二级团队的成员
								for (var s = 0; s < firstMembers.length; s++) {
									var member = firstMembers[s];
									if (member.teamId == team1.teamId && $.inArray(member.memberId, firstMemberIds) == -1) {
										firstMemberIds.push(member.memberId);
										uniqueFirstMembers.push(member);
									}
								}
								if (uniqueFirstMembers.length > 0) {
									// 拼接第一级团队成员
									secondHtml += teamInfo.convertSubTeamMemberData(uniqueFirstMembers, team1.teamId);
								}

							}
							secondHtml += "</ul>";
						} else if (firstMembers.length > 0) {
							if (team1.ifOp == 1) {
								var uniqueFirstMembers = new Array();
								var firstMemberIds = new Array();
								// 去重第二级团队的成员
								for (var s = 0; s < firstMembers.length; s++) {
									var member = firstMembers[s];
									if (member.teamId == team1.teamId && $.inArray(member.memberId, firstMemberIds) == -1) {
										firstMemberIds.push(member.memberId);
										uniqueFirstMembers.push(member);
									}
								}
								// 拼接第二级团队成员
								secondHtml += '<span class="team-father-name">' + team1.teamName + '</span><ul class="list-info">';
								secondHtml += teamInfo.convertSubTeamMemberData(uniqueFirstMembers, team1.teamId);

								secondHtml += "</ul>";
							}
						}
					}
					$('.first-team ul').html(firstHtml);
					$('.first-team').data('teamData', {
						teamList: teamsLevel1,
						memberList: new Array()
					});
					$('.second-team').html(secondHtml + '</div>');
					$('.second-team').data('teamData', {
						teamList: teamsLevel2,
						memberList: firstMembers
					});
					$('.third-team').html(thirdHtml + '</div>');
					$('.third-team').data('teamData', {
						teamList: teamsLevel3,
						memberList: secondMembers
					});
					$('.four-team').html(fourHtml + '</div>');
					$('.four-team').data('teamData', {
						teamList: teamsLevel4,
						memberList: thirdMembers
					});
					$('.four-team-member').html(fiveHtml + '</div>');
					$('.four-team-member').data('teamData', {
						teamList: new Array(),
						memberList: fourMembers
					});
				}
			}
		});
	},
	getTeamInfo: function(event) {
		common.load.load();
		teamInfo.ajax({
			url: common.root + '../caas/team/getTeamPage',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					var teams1 = new Array();
					var teams2 = new Array();
					var teams3 = new Array();
					var teams4 = new Array();
					teamInfo.isAdmin = false;
					for (var i = 0; i < data.result.length; i++) {
						var teamBeans = data.result[i];
						var team1 = teamBeans.teamBean1;
						var team2 = teamBeans.teamBean2;
						var team3 = teamBeans.teamBean3;
						var team4 = teamBeans.teamBean4;
						// 判断是不是平台管理员
						if (teamBeans.ifAdmin == 1) {
							teamInfo.isAdmin = true;
						}
						if (team1 != null && team1 != undefined) {
							var repeat = false;
							for (var j = 0; j < teams1.length; j++) {
								if (teams1[j].teamId == team1.teamId) {
									repeat = true;
									break;
								}
							}
							if (!repeat) {
								teams1.push(team1);
							}
						} else if (team2 != null && team2 != undefined) {
							var repeat = false;
							for (var j = 0; j < teams2.length; j++) {
								if (teams2[j].teamId == team2.teamId) {
									repeat = true;
									break;
								}
							}
							if (!repeat) {
								teams2.push(team2);
							}
						} else if (team3 != null && team3 != undefined) {
							var repeat = false
							for (var j = 0; j < teams3.length; j++) {
								if (teams3[j].teamId == team3.teamId) {
									repeat = true;
									break;
								}
							}
							if (!repeat) {
								teams3.push(team3);
							}
						} else if (team4 != null && team4 != undefined) {
							var repeat = false
							for (var j = 0; j < teams4.length; j++) {
								if (teams4[j].teamId == team4.teamId) {
									repeat = true;
									break;
								}
							}
							if (!repeat) {
								teams4.push(team4);
							}
						}
					}
					var html1 = teamInfo.convertRootTeamData(teams1, 1);
					$('.first-team .list-info').html(html1);
					$('.first-team').data('teamData', {
						teamList: teams1
					});
					if (teamInfo.isAdmin) {
						$('.first-team').append('<a class="increase-button" href="#" onclick="teamInfo.openAddTeamWindow(\'first-team\');"><span class="glyphicon plus" aria-hidden="true">＋</span>&nbsp;新增团队</a>');
					}
					var html2 = teamInfo.convertRootTeamData(teams2, 2);
					$('.second-team .list-info').html(html2);
					$('.second-team').data('teamData', {
						teamList: teams2
					});
					var html3 = teamInfo.convertRootTeamData(teams3, 3);
					$('.third-team .list-info').html(html3);
					$('.third-team').data('teamData', {
						teamList: teams3
					});
					var html4 = teamInfo.convertRootTeamData(teams4, 4);
					$('.four-team .list-info').html(html4);
					$('.four-team').data('teamData', {
						teamList: teams4
					});
				}
			}
		});
	},
	// 加载下一级团队
	loadSubTeam: function(element, i) {
		var teamElement = $(element);
		var subElement = teamElement.parents('ul');
		subElement.find('a').css('background-color', 'white');
		subElement.find('span.selected-pointer').css('color', 'white');
		teamElement.find('span.selected-pointer').css('color', 'black');
		subElement.children('li').children('a').removeClass('selected');
		teamElement.addClass('selected');
		var idArr = teamElement.attr('id').split('_');
		var index = idArr[0];
		var teamId = idArr[1];
		var team = '';
		var className = '';
		var str = '<ul class="list-info"></ul>'
		if (index == 1) {
			team = $('.first-team').data('teamData').teamList[i];
			className = '.second-team';
			$('.second-team .list-content').empty();
			$('.third-team .list-content').empty();
			$('.four-team .list-content').empty();
			$('.four-team-member .list-content').empty();
			$('.second-team .list-content').append(str)
			$('.third-team .list-content').append(str)
			$('.four-team .list-content').append(str)
			$('.four-team-member .list-content').append(str)
			if (team.isLeader == 1 || team.isParent == 1) {
				$('.second-team .increase-button').css('display', 'block');
			}
			$('.third-team .increase-button').css('display', 'none');
			$('.four-team-member .increase-button').css('display', 'none');
		} else if (index == 2) {
			team = $('.second-team').data('teamData').teamList[i];
			className = '.third-team';
			$('.third-team .list-content').empty();
			$('.third-team-member .list-content').empty();
			$('.third-team .list-content').append(str)
			$('.four-team-member .list-content').append(str)
			$('.four-team .list-content').empty();
			$('.four-team .list-content').append(str)
			if (team.isLeader == 1 || team.isParent == 1) {
				$('.third-team .increase-button').css('display', 'block');
			}
			$('.four-team .increase-button').css('display', 'none');
			$('.four-team-member .increase-button').css('display', 'none');
		} else if (index == 3) {
			team = $('.third-team').data('teamData').teamList[i];
			className = '.four-team';
			$('.four-team .list-content').empty();
			$('.four-team .list-content').append(str)
			$('.four-team-member .list-content').empty();
			$('.four-team-member .list-content').append(str)
			if (team.isLeader == 1 || team.isParent == 1) {
				$('.four-team .increase-button').css('display', 'block');
			}
			$('.four-team-member .increase-button').css('display', 'none');
		} else if (index == 4) {
			team = $('.four-team').data('teamData').teamList[i];
			className = '.four-team-member';
			$('.four-team-member .list-content').empty();
			$('.four-team-member .list-content').append(str)
			if (team.isLeader == 1 || team.isParent == 1) {
				$('.four-team-member .increase-button').css('display', 'block');
			}
		} else {
			console.log('index   -----------------   ' + index);
			return;
		}
		common.load.load();
		teamInfo.ajax({
			url: common.root + '../caas/team/getTeamByTeamId',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify({
				'teamId': parseInt(teamId)
			}),
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					if (data.result.ifAdmin == 1) {
						teamInfo.isAdmin = true;
					} else {
						teamInfo.isAdmin = false;
					}
					var teams = data.result.teamList;
					var members = data.result.memberList;
					for (var m = 0; m < members.length; m++) {
						members[m].isParent = team.isParent;
					}
					var html = teamInfo.convertSubTeamData(teams, parseInt(index) + 1);
					html += teamInfo.convertSubTeamMemberData(members, teamId);
					$(className).empty();
					$(className).html('<div class="list-content"><ul class="list-info"></ul></div>');
					$(className + ' .list-info').html(html);
					$(className + ' .list-info').prepend('<span class="team-father-name">' + team.teamName + '</span>');
					$(className).data('teamData', data.result);
					if ((team.teamId != -2 && (team.isLeader == 1 || team.isParent == 1)) || teamInfo.isAdmin == true) {
						if (index == 4) {
							$(className).append("<a class=\"increase-button\" onclick='teamInfo.deleteUser(" + JSON.stringify(team) + ");'><span class=\"glyphicon plus\" aria-hidden=\"true\">＋</span>&nbsp;新增成员</a>");
						} else {
							$(className).append('<a class="increase-button" href="#" onclick="teamInfo.openAddTeamWindow(\'' + className.substring(1, className.length) + '\');"><span class="glyphicon plus" aria-hidden="true">＋</span>&nbsp;新增团队</a>');
						}
					}
					$(className).data('parentTeam', JSON.stringify(team));
				}
			}
		});
	},

	// 团队操作
	editTeam: function(event, team) {
		event.stopPropagation();
		event.preventDefault();
		var element = $(event.target).parents('.list-info').parent().parent();
		var html = '';
		var pop = $(event.target);
		var top = pop.offset().top + 25;
		var left = pop.offset().left - 251.5;
		html += '<div class="shade" style="background-color: rgba(255,255,255,0);" onclick="teamInfo.clearShade();"><div class="team-pop ';
		if (team.isParent != 1 && !teamInfo.isAdmin) {
			if (element.hasClass('four-team-member') || element.hasClass('four-team')) {
				left -= 85;
				html += 'pop-3-right';
			} else {
				html += 'pop-3-left';
			}
			html += '" style="left:' + left + 'px;top:' + top + 'px;">';
			html += "<a style=\"border-width: 0;\" href=\"#\" onclick='teamInfo.updateTeamGroup(" + JSON.stringify(team) + ");'>变更小区</a>";
			html += "<a href=\"#\" onclick='teamInfo.modifyTeamName(" + JSON.stringify(team) + ");'>修改团队名称</a>";
			html += "<a href=\"#\" onclick='teamInfo.deleteUser(" + JSON.stringify(team) + ");'>变更团员</a>";
		} else {
			if (element.hasClass('four-team-member') || element.hasClass('four-team')) {
				left -= 85;
				html += 'pop-5-right';
			} else {
				html += 'pop-5-left';
			}
			html += '" style="left:' + left + 'px;top:' + top + 'px;">';
			html += "<a style=\"border-width: 0;\" href=\"#\" onclick='teamInfo.updateTeamGroup(" + JSON.stringify(team) + ");'>变更小区</a>";
			html += "<a href=\"#\" onclick='teamInfo.modifyTeamName(" + JSON.stringify(team) + ",);'>修改团队名称</a>";
			html += "<a href=\"#\" onclick='teamInfo.openUpdateTeamLeader(" + JSON.stringify(team) + ");'>变更负责人</a>";
			html += "<a href=\"#\" onclick='teamInfo.deleteUser(" + JSON.stringify(team) + ");'>变更团员</a>";
			html += "<a href=\"#\" onclick='teamInfo.deleteTeam(" + JSON.stringify(team) + ");'>删除团队</a>";
		}
		html += '<img class="arrow-up" src="/html/yc/rm/caass/appserv/bus/team/img/pop_up.png"></div></div>';
		$('.main-content').append(html);
	},
	// 团队成员操作
	editTeamMember: function(event, member) {
		event.stopPropagation();
		event.preventDefault();
		var element = $(event.target).parents('.list-info').parent().parent();
		var jsonMember = JSON.parse('"' + member + '"');
		var html = '';
		var pop = $(event.target);
		var top = pop.offset().top + 25;
		var left = pop.offset().left - 251.5;
		if (element.hasClass('four-team-member') || element.hasClass('four-team')) {
			left -= 85;
			html += '<div class="shade" style="background-color: rgba(255,255,255,0);" onclick="teamInfo.clearShade();"><div class="team-pop pop-3-right" style="left:' + left + 'px;top:' + top + 'px;">';
		} else {
			html += '<div class="shade" style="background-color: rgba(255,255,255,0);" onclick="teamInfo.clearShade();"><div class="team-pop pop-3-left" style="left:' + left + 'px;top:' + top + 'px;">';
		}
		if (index.user.userId != member.memberId) {
			html += "<a style=\"border-width: 0;\" href=\"#\" onclick='teamInfo.updateTeamGroup(" + JSON.stringify(member) + ");'>变更小区</a>";
		}
		html += "<a href=\"#\" onclick='teamInfo.deleteTeamMem(" + JSON.stringify(member) + ");'>删除团员</a>";
		html += '<img class="arrow-up" src="/html/yc/rm/caass/appserv/bus/team/img/pop_up.png"></div></div>';
		$('.main-content').append(html);
	},
	convertRootTeamData: function(data, index) {
		var html = '';
		for (var i = 0; i < data.length; i++) {
			var team = data[i];
			html += '<li><a id="' + index + '_' + team.teamId + '" href="#" onclick="teamInfo.loadSubTeam(this,' + i + ');"><span class="selected-pointer">•&nbsp;</span>';
			html += '<span class="team-member-name">' + team.teamName + '<span class="manager">(' + team.leaderName + ')</span></span>';
			if (team.teamId != -2) {
				html += '<span href="#" onclick="teamInfo.editTeam(event,"' + team + '");" class="edit"></span>';
			}
			html += '<div style="overflow: hidden;height: 100%;">';
			html += '<span class="house-count">' + team.houseCnt + '间</span>';
			html += '</div>';
			html += '</a></li>';
		}
		return html;
	},
	// 转换子团队信息
	convertSubTeamData: function(data, index) {
		var html = '';
		for (var i = 0; i < data.length; i++) {
			var team = data[i];
			html += '<li><a id="' + index + '_' + team.teamId + '" href="#" onclick="teamInfo.loadSubTeam(this,' + i + ');"><span class="selected-pointer">•&nbsp;</span>';
			html += '<span class="team-member-name">' + team.teamName + '<span class="manager">(' + team.leaderName + ')</span></span>';
			if (team.teamId != -2 && team.isParent == 1) {
				html += '<span href="#" onclick="teamInfo.editTeam(event,' + JSON.stringify(team).replace(/\"/g, "\'") + ');" class="edit"></span>';
			}
			html += '<div style="overflow: hidden;height: 100%;">';
			html += '<span class="house-count">' + team.houseCnt + '间</span>';
			html += '</div>';
			html += '</a></li>';
		}
		return html;
	},
	// 转换子团队成员信息
	convertSubTeamMemberData: function(data, teamId) {
		var html = '';
		for (var i = 0; i < data.length; i++) {
			var member = data[i]
			var manager = '';
			if (member.ifCharge == 1) {
				manager = '<span class="manager">(负责人)</span>';
			}
			html += '<li><a class="member" id="' + teamId + '_' + member.memberId + '" href="#"><span class="selected-pointer">•&nbsp;</span>';
			html += '<span class="team-member-name">' + member.memberName + manager + '</span>';
			if (member.teamId != -2) {
				if (member.ifCharge == 1) {
					if (member.isParent == 1) {
						html += '<span href="#" onclick="teamInfo.editTeamMember(event,' + JSON.stringify(member).replace(/\"/g, "\'") + ');" class="edit"></span>';
					}
				} else {
					html += '<span href="#" onclick="teamInfo.editTeamMember(event,' + JSON.stringify(member).replace(/\"/g, "\'") + ');" class="edit"></span>';
				}
			}
			html += '<div style="overflow: hidden;height: 100%;">';
			html += '<span class="house-count">' + member.memCnt + '间</span>';
			html += '</div>';
			html += '</a></li>';
		}
		return html;
	},
	// 清除遮罩层
	clearShade: function() {
		console.log('clearShade');
		if ($('.shade') != undefined && $('.shade') != null) {
			$('.shade').remove();
		}
	},
	// ajax封装
	ajax: function(opt) {
		var optSuccess = opt.success;
		var url = '';
		if (opt.url != undefined && opt.url != null) {
			url = opt.url;
		}
		var def = {
			url: '',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			async: true,
			error: function(request, msg, error) {
				common.load.hide();
				common.alert({
					cancelButton: false,
					confirmButton: "关闭",
					closeIcon: false,
					msg: '系统内部错误',
					fun: function() {

					}
				});
				console.log(url + ' : error ----- ' + error);
			},
			success: function(data) {
				if (data.success != 1) {
					common.load.hide();
					if (data.success != -1) {
						common.alert({
							cancelButton: false,
							confirmButton: "确定",
							closeIcon: false,
							msg: '您还未登陆或登陆已经失效，请重新登录。',
							fun: function() {
								window.location.href = 'loginNew.html';
							}
						});
					} else {
						common.alert({
							cancelButton: false,
							confirmButton: "确定",
							closeIcon: false,
							msg: data.result,
						});
					}
				} else {
					// 请求没问题
					if (optSuccess != undefined && optSuccess != null) {
						optSuccess(data);
					}
				}
			}
		};
		opt.success = def.success;
		$.extend(def, opt);
		$.ajax(def);
	}
};
$(function() {
	teamInfo.init();
});
// 新增团队成员未分配列表
var addUndistributedUser = [];
// 新增团队成员已分配列表
var addAssignedUser = [];
// 变更团队成员未分配列表
var updateUndistributedUser = [];
// 变更团队成员已分配列表
var updateAssignedUser = [];
// 变更小区已分配列表
var updateGroupUndistributedUser = [];
// 变更小区未分配列表
var updateGroupAssignedUser = [];