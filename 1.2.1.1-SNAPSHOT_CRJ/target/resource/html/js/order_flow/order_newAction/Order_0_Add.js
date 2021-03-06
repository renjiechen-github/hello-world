var modals = $('#rankModal');
var rankid = "";
var orderadd = {
	// 页面初始化操作
	init : function() {
		// 加载结算类型
		orderadd.initType();
		
		// 加载渠道
		orderadd.initChannel();
		
		// 添加处理事件
		orderadd.addEvent();
		$('#areaId').select2({'width':'150px'});
		$('#groupId').select2({'width':'150px'});
		$('#butlerId').select2({'width':'150px'});
	},

	addEvent : function() {
		$('#orderadd_bnt').click(function() {
			orderadd.save();
		});

		$('#ouserMobile').blur(function() {
			$('#ouserName').val(orderadd.getUserName());
		});
		
		$('#areaId').change(function() {			
			orderadd.groupSelect();
		});
		
		$('#groupId').change(function() {		
			orderadd.butlerSelect();		
		});
	},
	
	// 加载渠道信息
	initChannel : function() {
		// 加载维修类型
		var param = {};
		common.ajax({
			url : common.root + '/houseLookingOrder/getHouseLookingOrderChannel.do',
			dataType : 'json',
      contentType: 'application/json; charset=utf-8',
      encode: false,
      data : JSON.stringify(param),
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = "";
					var date = data.houseLookingOrderChannelList;
					for (var i = 0; i < date.length; i++) {
						html += '<option  value="' + date[i].channel + '" >' + date[i].channelName + '</option>';
					}
					$("#channel").append(html);
				}
			}
		});		
	},

	// 加载区域选择
	initType : function() {
		var type = "3";// 区域
		var fatherid = "";// 上级Id

		// 区域
		common.loadArea(fatherid, type, function(json) {
			var html = "";
			for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
			}
			$('#area').append(html);
			$('#areaId').append(html);
			$('#areaId').select2({'width':'150px'});
		});

		// 租住类型
		common.loadItem('RANKHOUSE.TYPE', function(json) {
			var html = "";
			for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
			}
			$('#rankType').append(html);
		});
	},
	
	// 根据小区ID，获取管家信息
	butlerSelect : function() {
		$("#rankName").val("");
		$("#orderName").val("");
		var groupId = $("#groupId").val();
		if (groupId == '') {
			var areaId = $("#areaId").val();
			orderadd.getButlerList(areaId,undefined);
			return;
		}
		common.ajax({
			url : common.root + '/cascading/butlerList.do',
			dataType : 'json',
			data : {
				groupId : groupId
			},
			async: false,
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					$("#butlerId").empty();
					var len = data.length;
					if (len == 0) {
						$("#butlerId").prepend("<option value=''>请选择..</option>");
						$("#select2-butlerId-container").text("请选择..");
					} else {
						var html = '<option value="">请选择..</option>';
						for (var i = 0; i < data.length; i++) {
							html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
						}
						$("#butlerId").append(html);
						$("#select2-butlerId-container").text("请选择..");
						$('#butlerId').select2({'width':'150px'});
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});			
	},	
	
	// 根据区域ID，获取小区、管家信息
	groupSelect : function () {
		$("#rankName").val("");
		$("#orderName").val("");
		var areaId = $("#areaId").val();
		// 首先清空小区下拉信息
		$("#groupId").empty();
		if (areaId == "") {
			// 如果没有选中区域
			$("#groupId").prepend("<option value=''>请选择..</option>");
			$("#select2-groupId-container").text("请选择..");
		} else {
			orderadd.getGroupList(areaId);
		}
		
		// 清空管家信息
		var butlerId = $("#butlerId").val();
		$("#butlerId").empty();
		if (areaId == "") {
			// 如果没有选中区域
			$("#butlerId").prepend("<option value=''>请选择..</option>");
			$("#select2-butlerId-container").text("请选择..");			
		} else {
			orderadd.getButlerList(areaId, undefined);
		}
	},
	
	// 级联管家信息
	getButlerList : function(areaId, rankId) {
		$("#butlerId").empty();
		var area_id;
		var rank_id;
		if (areaId == undefined || areaId == null) {
			area_id = "";
		} else {
			area_id = areaId;
		}
		if (rankId == undefined || rankId == null) {
			rank_id = "";
		} else {
			rank_id = rankId;
		}
		common.ajax({
			url : common.root + '/cascading/butlerList.do',
			dataType : 'json',
			data : {
				'areaId' : area_id,
				'rankId' : rank_id
			},
			async: false,
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var len = data.length;
					if (len == 0) {
						$("#butlerId").prepend("<option value=''>请选择..</option>");
						$("#select2-butlerId-container").text("请选择..");
					} else {
						if (rank_id != '') {
							// 清空管家信息
							$("#butlerId").empty();
							var html = '';
							for (var i = 0; i < data.length; i++) {
								html += '<option value="' + data[i].id + '" >' + data[i].name + '</option>';
							}
							$("#butlerId").append(html);
							$("#select2-butlerId-container").text(data[0].name);
							
							// 清理区域和小区
							$("#groupId option[value='"+data[0].group_id+"']").attr("selected", "selected");
							$("#select2-groupId-container").text(data[0].group_name);
							$("#areaId option[value='"+data[0].area_id+"']").attr("selected", "selected");
							$("#select2-areaId-container").text(data[0].area_name);
						}
						
						if (area_id != '') {
							var html = '<option value="">请选择..</option>';
							for (var i = 0; i < data.length; i++) {
								html += '<option value="' + data[i].id + '" >' + data[i].name + '</option>';
							}
							$("#butlerId").append(html);
							$("#select2-butlerId-container").text("请选择..");							
						}
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});		
	},
	
	// 级联小区信息
	getGroupList : function(areaId) {
		$("#groupId").empty();
		common.ajax({
			url : common.root + '/cascading/groupList.do',
			dataType : 'json',
			data : {
				areaId : areaId
			},
			loadfun : function(isloadsucc, data) {
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
						$('#groupId').select2({'width':'150px'});
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});			
	},
	

	Open : function() {
		modals.modal();
		orderadd.rankhouse();
	},

	rankhouse : function() {
		$('#rankType').change(function() {
			$('#search').click();
		});
		var tableobj = $("#rank_table").data('table');
		if (tableobj == undefined) {
			table.init({
				id : '#rank_table',
				url : common.root + '/houserank/getrankhouse.do',
				columns : [ "rank_code", "title", "rank_type", "spec", "rank_area", ],
				param : function() {
					var a = new Array();
					a.push({
						name : "type",
						value : $('#rankType').val()
					});
					a.push({
						name : 'status',
						value : "3"
					});
					if ($("#groupId").val() != null && $("#groupId").val() != undefined && $("#groupId").val() != '') {
						a.push({
							name : 'groupid',
							value : $('#groupId').val()
						});						
					}
					if ($("#areaId").val() != null && $("#areaId").val() != undefined && $("#areaId").val() != '') {
						a.push({
							name : 'areaid',
							value : $('#areaId').val()
						});						
					}										
					a.push({
						name : 'houseName',
						value : $('#keywordT').val()
					});
					return a;
				},
				issingle : true,
				isexp : false,
				select : function(data) {
					modals.modal('hide');
					if ($('#rankName').val() == data.title) {
						return;
					}
					$('#rankName').val(data.title);
					$('#orderName').val("看_"+data.title);
					orderadd.getButlerList(undefined,data.id);
				},
				createRow : function(rowindex, colindex, name, value, data) {
					if (colindex == 1) {
						return orderadd.dealColum({
							"value" : value,
							"length" : 5
						});
					}
				}
			});
		} else {
			$('#search').click();
		}
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

	// 表单提交
	save : function() {
		if (!Validator.Validate('form2')) {
			return;
		}
		var mobile = $.trim($("#ouserMobile").val());
		if (mobile == '') {
			common.alert({
				msg : '手机号不能为空'
			});
			return;
		}

		var reg = /^((13[0-9])|(14[1,4-8])|(15[0-3,5-9])|(16[6])|(17[0-8])|(18[0-9])|(19[8,9]))\d{8}$/;
    // 正则判断手机号
    if (!reg.test(mobile)) {
        common.alert({
            msg: "请输入正确的手机号"
        });
        return;
    }		

		$("#orderadd_bnt").attr("disabled", true);
		common.load.load('正在执行，请稍等...');
		var orderName = $("#orderName").val();
		if ($.trim(orderName) == "") {
			orderName = "看房订单";
		}
		if ($('#butlerId').val() == null || $('#butlerId').val() == undefined) {
			common.alert({
				msg: '请选择管家'
			});
			return;
		}
		var param = {
			'houseId' : rankid,
			'comments' : $('#odesc').val(),
			'userName' : $('#ouserName').val(),
			'userMobile' : $('#ouserMobile').val(),
			'orderName' : orderName,
			'channel' : $('#channel').val(),
			'groupId' : $('#groupId').val(),
			'areaId' : $('#areaId').val(),
			'assignedDealerId' : $('#butlerId').val(),
			'appointmentDate' : $('#oserviceTime').val()
		};
		
		common.ajax({
			url : common.root + '/houseLookingOrder/inputHouseLookingOrderInfo.do',
			dataType : 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data : JSON.stringify(param),
			loadfun : function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {
					if (data.state == 1) {// 操作成功
						common.alert({
							msg : '操作成功'
						});
						common.closeWindow('orderadd0', 3);
					} else if (data.state == -4) { // 操作失败
						$("#orderadd_bnt").attr("disabled", false);
						common.alert({
							msg : '当前房源已生成订单，请勿重复操作'
						});
					} else {
						$("#orderadd_bnt").attr("disabled", false);
						common.alert({
							msg : common.msg.error
						});
					}
				} else {
					$("#orderadd_bnt").attr("disabled", false);
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},

	// 获取用户名称
	getUserName : function() {
		var mobile = $('#ouserMobile').val();
		if (mobile == '' || mobile.length != 11) {
			$('#ouserName').removeAttr("readonly", "readonly");
			return;
		}
		common.ajax({
			url : common.root + '/sys/getUserName.do',
			data : {
				userMobile : mobile
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var name = data.username;
					$('#ouserName').val(name);
					if (name != null && name != '' && name != "null") {
						$('#ouserName').attr("readonly", "readonly");
					} else {
						$('#ouserName').removeAttr("readonly", "readonly");
					}
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},
};
orderadd.init();