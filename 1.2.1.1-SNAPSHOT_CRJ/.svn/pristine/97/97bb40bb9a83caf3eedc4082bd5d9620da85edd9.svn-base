var privilege = {
	// 用户角色权限数据
	userRolesData: undefined,
	// 团队数据
	teamJsonData: undefined,
	// 角色数据
	roleJsonData: undefined,
	// 菜单数据
	menusJsonData: undefined,
	// 选中的团队标签
	selectedTeam: undefined,
	// 选中的角色标签
	selectedRole: undefined,
	// 正在编辑的角色数据
	editRoleData: undefined,
	// 选中的菜单标签
	selectedMenu: undefined,
	// true : 添加角色，false : 修改角色
	isAddRole: true,
	// 选中菜单的所有按钮集合
	buttonsOfSelectedMenu: new Array(),
	// 新增角色使用变量
	selectedMenuOfCreate: undefined,
	// 初始化
	init: function() {
		// 监听鼠标滚动事件
		// $('html').bind('mousewheel',function(event){
		// 	var pop = $('.shade .role-pop');
		// 	if (pop != undefined && pop != null) {
		// 		privilege.clearShade();
		// 	}
		// });
		privilege.clearShade();
		common.load.load();
		privilege.ajax({
			url: common.root + '/caas/privilege/getTeams',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			success: function(data) {
				if (data.success == 1) {
					var result = data.result;
					if (typeof(result) == 'string') {
						common.load.hide();
						common.alert({
							cancelButton: false,
							confirmButton: "确定",
							closeIcon: false,
							msg: data.result,
						});
						return;
					}
					privilege.teamJsonData = result;
					var html = '';
					html += privilege.convertTeamData(data.result, 1);
					$('.team').append(html);
				}
				privilege.ajax({
					url: common.root + '/caas/privilege/getUserPrivileges',
					contentType: 'application/json; charset=UTF-8',
					dataType: 'json',
					data: JSON.stringify({
						'userId': $("#userId").val()
					}),
					type: 'POST',
					success: function(data) {
						common.load.hide();
						if (data.success == 1) {
							var result = data.result;
							if (typeof(result) == 'string') {
								common.alert({
									cancelButton: false,
									confirmButton: "确定",
									closeIcon: false,
									msg: data.result,
								});
								return;
							}
							var menus = new Array();
							privilege.userRolesData = result;
							var userData = privilege.userRolesData;
							for (var i = 0; i < userData.length; i++) {
								$.merge(menus, userData[i].menus);
							}
							console.log('uniqueUserMenus ----- begin');
							privilege.uniqueUserMenus(menus);
							console.log('uniqueUserMenus ----- end');
							$('.subnav .create').bind('click', function(event) {
								privilege.createRole(event);
							});
						}
					}
				});
			}
		});

	},
	// 转换团队数据为相应的html标签
	convertTeamData: function(data, level) {
		var html = '';
		var padding = level * 20;
		for (var i = 0; i < data.length; i++) {
			var sign = '-';
			if (data[i].subTeams.length <= 0) {
				sign = '';
			}
			html += '<div id="' + level + '_' + data[i].teamId + '" onclick="privilege.teamClick(event);" class="info">' + '<span class="teamname" style="padding-left: ' + padding + 'px;" onclick="privilege.teamspanClick(event);">' + sign + ' ' + data[i].teamName + '</span>';
			html += privilege.convertTeamData(data[i].subTeams, level + 1);
			html += '</div>';
		}
		return html;
	},
	// 团队名称点击事件
	teamspanClick: function(event) {
		privilege.teamClick(event);
		event.stopPropagation();
	},
	// 团队点击事件
	teamClick: function(event) {
		var element = $(event.target);
		if (element.get(0).tagName.toLowerCase() == 'span') {
			element = $(event.target).parent();
		}
		var teamname = element.find('span:eq(0)');
		teamname.css('background-color', 'rgb(0,147,234)');
		teamname.css('color', 'white');
		var id = element.attr('id').split('_')[1];
		var childrens = element.children('div');
		var name = teamname.html();
		var sign = '';
		if (name != undefined && name != '') {
			var strArr = name.split(' ');
			name = strArr[1];
			sign = strArr[0];
		}
		if (privilege.selectedTeam != undefined && privilege.selectedTeam != null && element.attr('id') != privilege.selectedTeam.attr('id')) {
			privilege.selectedTeam.find('span:eq(0)').css('background-color', 'white');
			privilege.selectedTeam.find('span:eq(0)').css('color', 'rgb(0,147,234)');
			privilege.selectedTeam = element;
			privilege.loadRoles(id);
		} else if (privilege.selectedTeam != undefined && privilege.selectedTeam != null) {
			if (childrens.css('display') == 'none') {
				teamname.html('- ' + name);
				childrens.css({
					'display': 'block'
				});
			} else if (childrens.css('display') == 'block') {
				teamname.html('+ ' + name);
				childrens.css({
					'display': 'none'
				});
			} else {
				if (sign == '-') {
					teamname.html('+ ' + name);
				} else if (sign == '+') {
					teamname.html('- ' + name);
				} else {

				}
			}
		} else {
			privilege.selectedTeam = element;
			privilege.loadRoles(id);
		}
		$('#privilege_title').html(name + "权限管理");
		event.stopPropagation();
	},
	// 加载角色信息
	loadRoles: function(teamId) {
		common.load.load();
		privilege.selectedRole = undefined;
		$('roles').empty();
		$('#menus').empty();
		$('#buttons').empty();
		privilege.ajax({
			url: common.root + '/caas/privilege/getTeamRoles',
			contentType: 'application/json; charset=UTF-8',
			data: JSON.stringify({
				"teamId": teamId,
			}),
			dataType: 'json',
			type: 'POST',
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					var result = data.result;
					if (typeof(result) == 'string') {
						common.alert({
							cancelButton: false,
							confirmButton: "确定",
							closeIcon: false,
							msg: data.result,
						});
						return;
					}
					privilege.roleJsonData = result;
					privilege.convertRoleData(data.result);
				}
			}
		});
	},
	// 转换角色数据为相应的html标签
	convertRoleData: function(data) {
		var html = "";
		html += '';
		for (var i = 0; i < data.length; i++) {
			html += '<div class="rolename" id="' + data[i].id + '" onclick="privilege.chooseRole(event)">';
			html += '<a href="#" onclick="privilege.editRole(event,' + JSON.stringify(data[i]).replace(/\"/g, "'") + ')" class="edit"></a>';
			html += '<span>' + data[i].name + '</span></div>';
		}
		$('#roles').html(html);
	},
	// 选择角色
	chooseRole: function(event) {
		event.stopPropagation();
		event.preventDefault();
		var element = $(event.target)
		if (element.get(0).tagName.toLowerCase() == 'span') {
			element = element.parent();
		} else {
			if (element.get(0).tagName.toLowerCase() == 'a') {
				return;
			}
		}
		common.load.load();
		var id = element.attr('id');
		element.css('color', 'rgb(0,147,234)');
		privilege.ajax({
			url: common.root + '/caas/privilege/getRolePrivileges',
			contentType: 'application/json; charset=UTF-8',
			data: JSON.stringify({
				"id": parseInt(id),
			}),
			dataType: 'json',
			type: 'POST',
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					var result = data.result;
					if (typeof(result) == 'string') {
						common.alert({
							cancelButton: false,
							confirmButton: "确定",
							closeIcon: false,
							msg: data.result,
						});
						return;
					}
					var menus = result;
					if (privilege.selectedRole != undefined && privilege.selectedRole != null && element.attr('id') != privilege.selectedRole.attr('id')) {
						privilege.selectedRole.css('color', 'black');
						privilege.selectedRole = element;
						privilege.showMenus(menus);
					} else if (privilege.selectedRole != undefined && privilege.selectedRole != null) {

					} else {
						privilege.selectedRole = element;
						privilege.showMenus(menus);
					}
				}
			}
		});
	},
	// 转换菜单数据为相应的html标签
	convertMenuData: function(menus, level) {
		if (menus.length > 0) {
			var html = '';
			for (var i = 0; i < menus.length; i++) {
				var menu = menus[i];
				var triangle = 'style="background-image: none;onclick="javascript:;"';
				if (menu.submenus.length > 0) {
					triangle = '';
				}
				html += '<div id="' + i + '_' + menu.id + '" onclick="privilege.menuClick(event,' + JSON.stringify(menu.buttons).replace(/\"/g, "'") + ');" class="list"><a href="#" class="menuname"><span class="triangle" ' + triangle + '></span>' + menu.name + '</a>';
				if (menu.submenus.length > 0) {
					var temp = privilege.convertMenuData(menu.submenus, level + 1);
					if (temp != undefined && temp != null && temp != 'undefined') {
						html += temp;
					}
				}
				html += '</div>';
			}
			return html;
		}
		return '';
	},
	// 展示角色对应的菜单
	showMenus: function(menus) {
		var html = '';
		$('#buttons').empty();
		html += privilege.convertMenuData(menus, 1);
		if (html != undefined && html != null && html != 'undefined') {
			$('#menus').html(html);
		}
	},
	// 菜单点击事件
	menuClick: function(event, buttonData) {
		event.stopPropagation();
		event.preventDefault();
		console.log('event.which  -----  ' + event.which);
		privilege.buttonsOfSelectedMenu = new Array();
		var element = $(event.target);
		if (element.get(0).tagName.toLowerCase() == 'span') {
			element = $(event.target).parent().parent();
		} else {
			if (element.get(0).tagName.toLowerCase() == 'a') {
				element = $(event.target).parent();
			}
		}
		var childrens = element.children('div');
		if (privilege.selectedMenu != undefined && privilege.selectedMenu != null && element.attr('id') != privilege.selectedMenu.attr('id')) {
			privilege.selectedMenu.find('a:eq(0)').css('color', 'black');
			privilege.selectedMenu.parents('.list').find('a:eq(0)').css('color', 'black');
			privilege.selectedMenu = element;

		} else if (privilege.selectedMenu != undefined && privilege.selectedMenu != null) {
			if (event.which != undefined) { // 判断是鼠标点击事件还是jquery触发事件
				if (childrens.css('display') == 'none') {
					element.find('a:eq(0) .triangle').css('transform', 'rotate(0deg)');
					childrens.css({
						'display': 'block'
					});
				} else if (childrens.css('display') == 'block') {
					element.find('a:eq(0) .triangle').css('transform', 'rotate(-90deg)');
					childrens.css({
						'display': 'none'
					});
				} else {

				}
			}
		} else {
			privilege.selectedMenu = element;
		}
		// 菜单变色
		element.find('a:eq(0)').css('color', 'rgb(0,147,234)');
		// 所有祖先菜单变色
		element.parents('.list').find('a:eq(0)').css('color', 'rgb(0,147,234)');
		var menuId = element.attr('id');
		var buttons = buttonData;
		if (menuId != undefined && menuId != null) {
			menuId = menuId.split('_')[1];
		} else {
			return;
		}
		var roles = privilege.userRolesData;
		for (var j = 0; j < roles.length; j++) {
			privilege.uniqueButtonsOfMenu(menuId, roles[j].menus);
		}
		var buttonIds = new Array();
		for (var k = 0; k < buttons.length; k++) {
			buttonIds.push(buttons[k].id);
		}
		var html = '';
		html += '<ul>';
		for (var i = 0; i < privilege.buttonsOfSelectedMenu.length; i++) {
			var button = privilege.buttonsOfSelectedMenu[i];
			if ($.inArray(button.id, buttonIds) >= 0) {
				html += '<li><span class="buttonname">' + button.name + '</span></li>';
			}
		}
		html += '</ul>';
		$('#buttons').empty();
		$('#buttons').append(html);
	},
	// 撤销操作
	refreshButtons: function() {
		if (privilege.selectedMenu != undefined && privilege.selectedMenu != null) {
			privilege.selectedMenu.trigger("click");
		}
	},
	// 编辑角色
	editRole: function(event, data) {
		event.stopPropagation();
		var element = $(event.target);
		privilege.editRoleData = data;
		var role = data;
		var html = '';
		var top = element.offset().top + 15;
		var left = element.offset().left - 240;
		html += '<div class="shade" style="background-color: rgba(255,255,255,0);" onclick="privilege.clearShade();"><div class="role-pop" style="left:' + left + 'px;top:' + top + 'px;"><a href="#" onclick="privilege.createRole(event,' + JSON.stringify(role).replace(/\"/g, "'") + ');" style="border-width: 0;">修改角色</a><a href="#" onclick="privilege.deleteRole(' + JSON.stringify(role).replace(/\"/g, "'") + ');">删除角色</a></div></div>';
		$('.main-content').append(html);
	},
	// 清除遮罩层
	clearShade: function() {
		console.log('clearShade');
		if ($('.shade') != undefined && $('.shade') != null) {
			$('.shade').remove();
		}
	},
	//按钮去重
	uniqueButtonsOfMenu: function(menuId, menus) {
		for (var i = 0; i < menus.length; i++) {
			var menu = menus[i];
			if (menu.id == menuId) {
				for (var j = 0; j < menu.buttons.length; j++) {
					var button = menu.buttons[j];
					var exists = false;
					for (var k = 0; k < privilege.buttonsOfSelectedMenu.length; k++) {
						if (privilege.buttonsOfSelectedMenu[k].id == button.id) {
							exists = true;
							break;
						}
					}
					if (!exists) {
						privilege.buttonsOfSelectedMenu.push(button);
					}
				}
			} else {
				if (menu.submenus.length > 0) {
					privilege.uniqueButtonsOfMenu(menuId, menu.submenus);
				}
			}
		}
	},
	// 用户拥有的菜单的id结合
	userMenuIds: new Array(),
	// 用户拥有的按钮的id集合
	userButtonIds: new Array(),
	// 去重之后的用户菜单
	userMenus: new Array(),
	// 用户菜单和按钮去重
	uniqueUserMenus: function(menusData) {
		for (var i = 0; i < menusData.length; i++) {
			if ($.inArray(menusData[i].id, privilege.userMenuIds) < 0) {
				// 菜单不存在
				privilege.userMenuIds.push(menusData[i].id);
				var menu = new Object();
				menu.id = menusData[i].id;
				menu.name = menusData[i].name;
				menu.parent_menu_id = menusData[i].parent_menu_id;
				menu.url = menusData[i].url;
				menu.seq = menusData[i].seq;
				menu.visible = menusData[i].visible;
				menu.submenus = new Array();
				menu.buttons = new Array();
				if (menusData[i].parent_menu_id != 0) {
					var superMenu = privilege.getMenuById(privilege.userMenus, menusData[i].parent_menu_id);
					superMenu.submenus.push(menu);
				} else {
					privilege.userMenus.push(menu);
				}
			}
			if (menusData[i].submenus.length > 0) {
				privilege.uniqueUserMenus(menusData[i].submenus);
			}
			// 给菜单添加按钮
			if (menusData[i].buttons.length > 0) {
				var menu = privilege.getMenuById(privilege.userMenus, menusData[i].buttons[0].menu_id);
				for (var j = 0; j < menusData[i].buttons.length; j++) {
					var button = menusData[i].buttons[j];
					if ($.inArray(button.id, privilege.userButtonIds) < 0) {
						// 按钮不存在
						privilege.userButtonIds.push(button.id);
						menu.buttons.push(button);
					}
				}
			}
		}
		return privilege.userMenus;
	},
	// 从新菜单集合中获取菜单
	getMenuById: function(menusData, id) {
		var menuInfo;
		for (var i = 0; i < menusData.length; i++) {
			var menu = menusData[i];
			if (menu.id == id) {
				menuInfo = menu;
				break;
			} else {
				if (menu.submenus.length > 0) {
					menuInfo = privilege.getMenuById(menu.submenus, id);
					if (menuInfo != undefined && menuInfo != null) {
						break;
					}
				}
			}
		}
		return menuInfo;
	},
	// 待修改角色信息
	roleMenuIds: new Array(),
	// 角色拥有的按钮的id集合
	roleButtonIds: new Array(),
	initRoleMenuAndButtonIds: function(menus) {
		for (var i = 0; i < menus.length; i++) {
			privilege.roleMenuIds.push(menus[i].id);
			if (menus[i].submenus.length > 0) {
				privilege.initRoleMenuAndButtonIds(menus[i].submenus);
			}
			if (menus[i].buttons.length > 0) {
				for (var j = 0; j < menus[i].buttons.length; j++) {
					var button = menus[i].buttons[j];
					privilege.roleButtonIds.push(button.id);
				}
			}
		}
	},
	// 创建角色新增和修改弹窗
	createRole: function(event, role) {
		event.stopPropagation();
		event.preventDefault();
		privilege.clearShade();
		var flag = true;
		if (role != undefined && role != null) {
			common.load.load();
			privilege.ajax({
				url: common.root + '/caas/privilege/getRolePrivileges',
				contentType: 'application/json; charset=UTF-8',
				data: JSON.stringify({
					"id": role.id,
				}),
				dataType: 'json',
				async: false,
				type: 'POST',
				success: function(data) {
					common.load.hide();
					if (data.success == 1) {
						var result = data.result;
						if (typeof(result) == 'string') {
							common.alert({
								cancelButton: false,
								confirmButton: "确定",
								closeIcon: false,
								msg: data.result,
							});
							return;
						}
						var menus = result;
						privilege.initRoleMenuAndButtonIds(menus);
					}
				}
			});
			flag = false;
		}
		if (privilege.selectedTeam == undefined || privilege.selectedTeam == null) {
				common.alert({
					cancelButton: false,
					confirmButton: "确定",
					closeIcon: false,
					msg: "请选择团队",
				});
				return;
			}		
		var html = '';
		html += '<div class="shade">';
		html += '<section class="createrole-pop" style="top:' + ($(window).height() - 400) / 2 + 'px;">';
		html += '<nav class="nav">';
		html += '<div>';
		var typeName = '';
		if (!flag) {
			typeName = '修改角色';
			privilege.isAddRole = false;
		} else {
			typeName = '新增角色';
			privilege.isAddRole = true;
		}
		html += '<span>' + typeName + '</span>';
		html += '<span class="glyphicon glyphicon-remove remove"></span>';
		html += '</div></nav>';
		html += '<section>';
		html += '<div>';
		html += '<span class="name">角色名称</span>';
		var roleName = '';
		if (!flag) {
			roleName = role.name;
		}
		html += '<input class="rolename" placeholder="请输入角色名称" value="' + roleName + '"/>';
		html += '</div>';
		html += '<span class="name">角色权限</span>';
		html += '<div id="createrole-menu" class="menu createrole-menu">';
		html += privilege.convertUserMenu(privilege.userMenus, 1, flag);
		html += '</div>';
		html += '<div class="subbutton createrole-button">';
		for (var j = 0; j < privilege.userMenuIds.length; j++) {
			var menuId = privilege.userMenuIds[j];
			var menu = privilege.getMenuById(privilege.userMenus, menuId);
			html += '<ul id="' + menuId + '" style="display: none;">';
			for (var i = 0; i < menu.buttons.length; i++) {
				var button = menu.buttons[i];
				var check = '';
				if (!flag && $.inArray(button.id, privilege.roleButtonIds) >= 0) {
					check = 'checked';
				}
				html += '<li><input type="checkbox" id="_' + button.id + '"onclick="privilege.chooseCreateRoleButton(event);" class="chk_1" ' + check + '/><label for="_' + button.id + '"></label><span class="buttonname">' + button.name + '</span></li>';
			}
			html += '</ul>';
		}
		html += '</div>';
		html += '</section>';
		html += '<span class="createrole-opteration">';
		html += '<a id="createRoleSave" href="#" onclick="privilege.addRole(event);" style="float: left;">保存</a>';
		html += '<a href="#" onclick="privilege.clearShade();" style="float: right;">取消</a>';
		html += '</span>';
		html += '</section>';
		html += '</div>';
		$('.wrapper').append(html);
		$('.createrole-pop .remove').bind("click", function(event) {
			privilege.clearShade();
		});
	},
	// 给团队添加角色
	addRole: function(event) {
		var name = $.trim($('.shade').find('.rolename').val());
		var role_desc = '';
		// a标签会导致ajax的url多一个html
		var url = '../caas/privilege/addRoles';
		var parameter = '';
		if (name == undefined || name == null || name == '') {
			common.alert({
				cancelButton: false,
				confirmButton: "确定",
				closeIcon: false,
				msg: "请输入正确的角色名称",
			});
			return;
		}
		common.load.load();
		if (privilege.isAddRole) {
			var teamId = privilege.selectedTeam.attr('id').split('_')[1];
			parameter = [{
				'name': name,
				'team_id': teamId,
				'role_desc': role_desc
			}];
		} else {
			url = '../caas/privilege/modifyRole';
			var id = privilege.editRoleData.id;
			parameter = {
				'name': name,
				'id': id,
				'role_desc': role_desc
			};
		}
		privilege.ajax({
			url: common.root + url,
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify(parameter),
			success: function(data) {
				if (data.success == 1 && data.result != null) {
					var result = data.result;
					if (typeof(result) == 'string') {
						if (privilege.isAddRole || result != '操作成功') {
							common.load.hide();
							common.alert({
								cancelButton: false,
								confirmButton: "确定",
								closeIcon: false,
								msg: data.result,
							});
							return;
						}
					}
					var roleId = result[0];
					if (!(privilege.isAddRole)) {
						roleId = privilege.editRoleData.id;
					}
					var privils = new Array();
					var menuIds = new Array();
					$.each($('.createrole-menu').find('.list'), function() {
						var element = $(this).find('a:eq(0) input');
						var check = element.attr('checked');
						if (check != undefined && check != null) {
							var m_value = roleId; // 角色id
							var access = 'menu_id'; // 菜单
							var a_value = $(this).attr('id').split('_')[1];
							var privil = {
								'm_value': m_value,
								'access': access,
								'a_value': a_value
							};
							privils.push(privil);
							menuIds.push(a_value);
						}
					});
					$.each($('.createrole-button').find('ul'), function() {
						console.log('button');
						var element = $(this);
						// 判断按钮的菜单是否已选中
						if ($.inArray(element.attr('id'), menuIds) >= 0) {
							$.each(element.find('li').find('input'), function() {
								var button = $(this);
								var check = button.attr('checked');
								if (check != undefined && check != null) {
									var m_value = roleId; // 角色id
									var access = 'button_id'; // 菜单
									var a_value = button.attr('id').split('_')[1];
									var privil = {
										'm_value': m_value,
										'access': access,
										'a_value': a_value
									};
									privils.push(privil);
								}
							});
						}
					});
					privilege.modifyRolePrivivlege(privils);

				} else {
					common.load.hide();
					common.alert({
						cancelButton: false,
						confirmButton: "确定",
						closeIcon: false,
						msg: "新增角色失败",
					});
				}
			}
		});
	},
	// 修改团队角色权限
	modifyRolePrivivlege: function(privils) {
		privilege.ajax({
			url: common.root + '../caas/privilege/modifyRolePrivileges',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify(privils),
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					privilege.clearShade();
					common.alert({
						cancelButton: false,
						confirmButton: "确定",
						closeIcon: false,
						msg: data.result,
						fun: function() {
							privilege.loadRoles(privilege.selectedTeam.attr('id').split('_')[1]);
						}
					});
				}
			}
		});
	},
	// 删除团队角色
	deleteRole: function(data) {
		common.load.load();
		var parameter = new Array();
		parameter.push(data.id);
		privilege.ajax({
			url: common.root + '../caas/privilege/deleteRoles',
			contentType: 'application/json; charset=UTF-8',
			dataType: 'json',
			type: 'POST',
			data: JSON.stringify(parameter),
			success: function(data) {
				common.load.hide();
				if (data.success == 1) {
					console.log('删除角色成功');
					privilege.clearShade();
					common.alert({
						cancelButton: false,
						confirmButton: "确定",
						closeIcon: false,
						msg: data.result,
						fun: function() {
							privilege.loadRoles(privilege.selectedTeam.attr('id').split('_')[1]);
						}
					});
				}
			}
		});
	},
	// 将菜单数据转换为相应html标签
	convertUserMenu: function(menus, level, flag) {
		if (menus.length > 0) {
			var html = '';
			for (var i = 0; i < menus.length; i++) {
				var menu = menus[i];
				var triangle = 'style="background-image: none;onclick="javascript:;"';
				if (menu.submenus.length > 0) {
					triangle = '';
				}
				var check = '';
				console.log(privilege.roleMenuIds);
				if (!flag && $.inArray(menu.id, privilege.roleMenuIds) >= 0) {
					check = 'checked';
				}
				html += '<div id="m_' + menu.id + '" onclick="privilege.createRoleMenuClick(event,' + JSON.stringify(menu.buttons).replace(/\"/g, "'") + ');" class="list">';
				html += '<a href="#" class="menuname">';
				html += '<span class="triangle" ' + triangle + '></span><input type="checkbox" id="' + menu.id + '" class="chk_1" ' + check + '/><label onclick="privilege.chooseMenu(event);" for="' + menu.id + '"></label>' + '<span class="buttonname">' + menu.name + '</span></a>';
				if (menu.submenus.length > 0) {
					html += privilege.convertUserMenu(menu.submenus, level + 1, flag);
				}
				html += '</div>';
			}
			return html;
		}
		return undefined;
	},
	// 打勾选中菜单
	chooseMenu: function(event) {
		event.stopPropagation();
		event.preventDefault();
		var element = $(event.target);
		var checked = element.prev().attr('checked');
		if (checked != undefined && checked != null) {
			// 取消选中
			// element.prev().removeAttr('checked');
			element.find('a input').removeAttr('checked');
			element.parent().parent().find('a').find('input').removeAttr('checked');
			console.log('cancel chooseMenu');
			var parent = element.parent().parent();
			var menuId = parent.attr('id').split('_')[1];
			$('.createrole-button').find('#' + menuId).find('li').find('input').removeAttr('checked');
			$.each(parent.find('.list'), function() {
				var ulElement = $(this);
				var subMenuId = ulElement.attr('id').split('_')[1];
				$('.createrole-button').find('#' + subMenuId).find('li').find('input').removeAttr('checked');
			});
		} else {
			// 选中
			// element.prev().attr('checked', true);
			element.parent().parent().find('a').find('input').attr('checked', true);
			element.parent().parents('.list').find('a:eq(0)').find('input').attr('checked', true);
		}
		if (element.get(0).tagName.toLowerCase() == 'span') {
			element = $(event.target).parent().parent();
		} else if (element.get(0).tagName.toLowerCase() == 'label') {
			element = $(event.target).parent().parent();
			if (checked == undefined || checked == null) {
				element.find('a:eq(0) .triangle').css('transform', 'rotate(0deg)');
				element.children('div').css('display', 'block');
			}
		} else {
			if (element.get(0).tagName.toLowerCase() == 'a') {
				element = $(event.target).parent();
			}
		}
		privilege.createRoleShowButtons(element, false);
	},
	// 点击菜单名称
	createRoleMenuClick: function(event) {
		console.log('createRoleMenuClick -------  into');
		event.stopPropagation();
		event.preventDefault();
		var element = $(event.target);
		if (element.get(0).tagName.toLowerCase() == 'span') {
			element = $(event.target).parent().parent();
		} else {
			if (element.get(0).tagName.toLowerCase() == 'a') {
				element = $(event.target).parent();
			}
		}
		privilege.createRoleShowButtons(element, true);
	},
	// flag: true 点击菜单，false 点击checkbox
	createRoleShowButtons: function(element, flag) {
		console.log('createRoleShowButtons -------  into');
		element.find('a:eq(0)').css('color', 'rgb(0,147,234)');
		var childrens = element.children('div');
		if (privilege.selectedMenuOfCreate != undefined && privilege.selectedMenuOfCreate != null) {
			var oldMenuId = privilege.selectedMenuOfCreate.attr('id');
			if (oldMenuId != undefined && oldMenuId != null) {
				oldMenuId = oldMenuId.split('_')[1];
			}
			$('.createrole-button').children('#' + oldMenuId).css('display', 'none');
		}
		if (privilege.selectedMenuOfCreate != undefined && privilege.selectedMenuOfCreate != null && element.attr('id') != privilege.selectedMenuOfCreate.attr('id')) {
			privilege.selectedMenuOfCreate.find('a:eq(0)').css('color', 'black');
			privilege.selectedMenuOfCreate = element;
		} else if (privilege.selectedMenuOfCreate != undefined && privilege.selectedMenuOfCreate != null) {
			if (event.which != undefined && flag) { // 判断是鼠标点击事件还是jquery触发事件
				if (childrens.css('display') == 'none') {
					element.find('a:eq(0) .triangle').css('transform', 'rotate(0deg)');
					childrens.css({
						'display': 'block'
					});
				} else if (childrens.css('display') == 'block') {
					element.find('a:eq(0) .triangle').css('transform', 'rotate(-90deg)');
					childrens.css({
						'display': 'none'
					});
				} else {

				}
			}
		} else {
			privilege.selectedMenuOfCreate = element;
		}
		var menuId = element.attr('id');
		if (menuId != undefined && menuId != null) {
			menuId = menuId.split('_')[1];
		} else {
			return;
		}
		$('.createrole-button').children('#' + menuId).css('display', 'block');
	},
	// 选中创建角色和按钮
	chooseCreateRoleButton: function(event) {
		// 获取ul标签
		var element = $(event.target).parent().parent();
		var menuId = element.attr('id');
		var aElement = $('.createrole-menu').find('#m_' + menuId).find('a:eq(0)');
		aElement.parents('.list').find('a:eq(0)').find('input').attr('checked', true);
	},
	getCookie: function(c_name) {
		if (document.cookie.length > 0) {
			c_start = document.cookie.indexOf(c_name + "=");
			if (c_start != -1) {
				c_start = c_start + c_name.length + 1;
				c_end = document.cookie.indexOf(";", c_start);
				if (c_end == -1) c_end = document.cookie.length;
				return unescape(document.cookie.substring(c_start, c_end));
			}
		}
		return "";
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
			error: function(request, msg, error) {
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
								window.location.href = 'login.html';
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
	privilege.init();
});