var $modal = $('#my-popup');// 实例化modal
var index2;
var baseHouse = {
	// 页面初始化操作
	init : function() {
		// 创建表格
		baseHouse.createTable();
		// 初始化下拉列表
		baseHouse.initSelect();
		// enter监听事件
		$('#keyword').keydown(function(e) {
			if (e.which == "13") {

				$("#serach").click();
				return false;// 禁用回车事件
			}
		});

		$('#businessCenter').change(function() {
			$('#serach').click();
		});

		$('#areaId').change(function() {
			$('#businessCenter').html('<option value="">请选择...</option>');
			$('#serach').click();
			baseHouse.initType($(this).val(), '4', 'businessCenter');
		});
	},
	initSelect : function() {
		baseHouse.initTypeAreaId('101', '3', 'areaId');
		baseHouse.initType('', '4', 'businessCenter');
	},
	initTypeAreaId : function(fatherid, type, id) {
		common.loadArea(fatherid, type, function(json) {
			var html = "";
			$('#' + id).html('<option value="">请选择...</option>');
			for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].id + '" >'
						+ json[i].area_name + '</option>';
			}
			$('#' + id).append(html);
		});
	},	
	initType : function(fatherid, type, id) {
		common.loadArea(fatherid, type, function(json) {
			var html = "";
			$('#' + id).html('<option value="">请选择...</option>');
			for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].area_name + '" >'
						+ json[i].area_name + '</option>';
			}
			$('#' + id).append(html);
		});
	},
	createTable : function() {
		table.init({
					id : '#basehouse_table',
					url : common.root + '/report/queryHouseInfo.do',
					expname : '房源列表',
					columns : [
							"title",
							"source",
							"price",
							"contacts",
							"phone_number",
							"area",
							"business_center",
							"community",
							"use_type",
							"house_type",
							"acreage",
							"renovation",
							"release_time"],
					param : function() {
						var a = getParamArray();
						return a;
					},
					expPage : function(href) {
						index2 = layer.open({
									type : 1,
									title : '导出房源',
									skin : 'layui-layer-demo', // 加上边框
									area : [ '300px', '140px' ], // 宽高
									content : ' <div class="head">	<div id="color1" class="leftdiv tab" onmousemove="movediv(this)" onclick="baseHouse.expAllHouse();return false;" onmouseout="normaldiv(this)">全部房源</div>'
								});
					},
					createRow : function(rowindex, colindex, name, value, data,row) {
						if (colindex == 0) {
							return '<a href="'+data.url+'" target="_blank">'+value+'</a>';
						}
						return value;
					},

				});
	},
	expAllHouse : function() {
		layer.close(index2);
		var newColumns = new Array();
		newColumns.push({
			name : "title",
			title : "房源标题"
		});
		newColumns.push({
			name : "source",
			title : "信息来源"
		});
		newColumns.push({
			name : "price",
			title : "月租"
		});
		newColumns.push({
			name : "contacts",
			title : "联系人"
		});
		newColumns.push({
			name : "phone_number",
			title : "联系方式"
		});
		newColumns.push({
			name : "area",
			title : "区域"
		});
		newColumns.push({
			name : "business_center",
			title : "商圈"
		});
		newColumns.push({
			name : "community",
			title : "小区"
		});
		newColumns.push({
			name : "use_type",
			title : "类型"
		});
		newColumns.push({
			name : "house_type",
			title : "户型"
		});
		newColumns.push({
			name : "acreage",
			title : "面积(㎡)"
		});
		newColumns.push({
			name : "renovation",
			title : "装修"
		});
		newColumns.push({
			name : "release_time",
			title : "发布时间"
		});
		var href = common.root
				+ '/report/queryHouseInfo.do?isDc=true&expname='
				+ encodeURIComponent(encodeURI('房源列表')) + "&colum="
				+ encodeURIComponent(encodeURI(JSON.stringify(newColumns)))
				+ '&istz_=1';
		var paramlist = getParamArray();
		for (var i = 0; i < paramlist.length; i++) {
			href += "&" + paramlist[i].name + "="
					+ encodeURI(encodeURI(paramlist[i].value));
		}
		console.log(href);
		common.load.load('正在执行导出，请稍等');
		common.ajax({
			url : href,
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				common.load.hide();
				if (isloadsucc) {
					if (data.state == 1) {
						window.location.href = data.name;
					} else {
						common.alert(common.msg.error);
					}
				} else {
					common.alert(common.msg.error);
				}
			}
		});
	},
};

function getParamArray() {
	var paramlist = new Array();
	paramlist.push({
		"name" : "source",
		"value" : $('#source').val()
	});
	paramlist.push({
		"name" : "areaId",
		"value" : $('#areaId').val()
	});
	paramlist.push({
		"name" : "businessCenter",
		"value" : $('#businessCenter').val()
	});
	return paramlist;
}	
baseHouse.init();
