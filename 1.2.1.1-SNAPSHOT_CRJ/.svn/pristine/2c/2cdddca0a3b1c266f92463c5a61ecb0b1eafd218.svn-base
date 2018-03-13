var id = '';
var flag = '';
/**
 * 新增房源信息
 * 
 */
var houseAdd = {
	init : function() {
//		// 时间按钮选择事件
//		var nowTemp = new Date();
//		$('#value_date').daterangepicker(
//				{
//					startDate : nowTemp.getFullYear() + '-'
//							+ (nowTemp.getMonth() + 1) + '-02',
//					timePicker12Hour : false,
//					timePicker : false,
//					singleDatePicker : true,
//					// 分钟间隔时间
//					timePickerIncrement : 10,
//					format : 'YYYY-MM-DD'
//				}, function(start, end, label) {
//					console.log(start.toISOString(), end.toISOString(), label);
//				});
		$('#userMobile').blur(houseAdd.getUserName);
		$('#lease_period').change(houseAdd.changeMonthRent); //绑定租约时长
		// 加载下拉框
		houseAdd.loadSelect();

		$('#house_add_bnt').click(function() {
			houseAdd.save();
		});

		if (flag == '0') {
			// 隐藏修改按钮
			$('#house_add_bnt').hide();
		}
		
		houseAdd.changeMonthRent();
		// 修改、查看
		if (id != '') 
		{
			houseAdd.loadHouseInfo(id);
		}
		else // 新增
		{
			$('#myTab:not(.active)').remove(); 
			houseAdd.loadMap();
			$('#groupName').click(houseAdd.loadearId);
		    common.dropzone.init({
				id : '#myAwesomeDropzone',
				maxFiles : 4,
			});
		}
	},
	changeMonthRent:function()
	{
		// 5年放一行，不足补空
//		console.log($('#leaseTime option:selected').attr("data_rent"));
		var data_rent =$('#lease_period option:selected').attr("data_rent"); //需求选择的时间
		var rentMonth = $("input[name='rentMonth']"); //页面中显示的数据
		var hs = 5;
		var showL = parseInt(Math.abs(data_rent)/Math.abs(hs)); // 应该显示多少行
		var syL = data_rent % hs; // 剩余多少个输入框
		var showHtml = ''; // 显示的输入框
		for(var i = 0; i < showL; i++)
		{
			showHtml += '<div class="divFlex paddingB10">';
			for(var j = 1; j < Math.abs(hs+1); j++)
			{
				var tmpLength = Math.abs(i * hs) + j;
				showHtml += '<input type="number" dataType="Integer3"  msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />&nbsp;&nbsp;';
			}	
			showHtml += '</div>';
		}
		if(syL > 0)
		{
			showHtml += '<div class="divFlex paddingB10">';
			for(var i = 0; i < 5; i++)
			{
				var tmpLength = Math.abs(showL * hs) + i + 1 ;
				if(syL > i)
				{
					showHtml += '<input type="number" dataType="Integer3" msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />&nbsp;&nbsp;';
				}	
				else
				{
					showHtml += '<input type="number" style="opacity: 0;" disabled="disabled" dataType="Integer3" msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />&nbsp;&nbsp;';
				}	
			}
			showHtml += '</div>';
		}
		
		$('#div_rent').html(showHtml);
		$.each(rentMonth, function(i,value){
//			console.log($("input[name='rentMonth']:eq("+i+")"));
//			console.log(i); 
			$("input[name='rentMonth']:eq("+i+")").val($(this).val());
		});
		$("input[name='rentMonth']").removeClass('rent_width_5');
		$("input[name='rentMonth']").addClass('rent_width_5');
	},
	getUserName:function()
	{
		var mobile = $('#userMobile').val();
		if(!common.validMobile(mobile))
		{
			layer.tips('请输入11位数字格式的手机号码', $('#userMobile'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#userName').removeAttr("readonly","readonly");
			return ;
		}	
		common.ajax({
			url : common.root + '/BaseHouse/getUserName.do',
			data : {
				userMobile : mobile
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if(data.state == 1)
					{
						var name = data.username;
						$('#userName').val(name);
						if(name != null && name != '' && name != "null")
						{
							$('#userName').attr("readonly","readonly");
						}
						else
						{
							$('#userName').removeAttr("readonly","readonly");
						}
					}
					else if(data.state == -1)
					{
						 $('#userMobile').val('');
						common.alert({
							msg : '号码重复,请联系技术部删除数据!'
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
	loadMap : function() {
		$('#groupAddress').blur(function() {
			var val = $('#groupAddress').val();
			map.clearOverlays();
			// 创建地址解析器实例
			var myGeo = new BMap.Geocoder();
			// 将地址解析结果显示在地图上,并调整地图视野
			myGeo.getPoint(val, function(point) {
				if (point) {
					map.centerAndZoom(point, 16);
					map.addOverlay(new BMap.Marker(point));
					$('#groupCoordinate').val(point.lng + "," + point.lat);
				} else {
					common.alert({
						msg : '您输入的地址没有解析到结果，请在地图中选取您的地址!'
					});
				}
			}, "南京市");
		});

		var map = new BMap.Map("groupMap", {
			minZoom : 8,
			maxZoom : 18
		});
		map.centerAndZoom("南京", 12);
		map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件
		map.setCurrentCity("南京"); // 设置地图显示的城市 此项是必须设置的
		map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
		map.addEventListener("click", function(e) {
			map.clearOverlays();
			map.centerAndZoom(e.point, 16);
			map.addOverlay(new BMap.Marker(e.point));
			$('#groupCoordinate').val(e.point.lng + "," + e.point.lat);

			var geoc = new BMap.Geocoder();
			geoc.getLocation(e.point, function(rs) {
				var addComp = rs.addressComponents;
				$('#groupAddress').val(
						addComp.province + addComp.city + addComp.district
								+ addComp.street + addComp.streetNumber);
			});
		});
	},
	editLoadMap:function (coordinate){
		if(common.isEmpty(coordinate) || coordinate.indexOf(',')==-1)
		{
			houseAdd.loadMap();
			return;
		}
		var split = coordinate.split(",");
		$('#groupAddress').blur(function() {
			var val = $('#groupAddress').val();
			map.clearOverlays();
			// 创建地址解析器实例
			var myGeo = new BMap.Geocoder();
			// 将地址解析结果显示在地图上,并调整地图视野
			myGeo.getPoint(val, function(point) {
				if (point) {
					map.centerAndZoom(point, 16);
					map.addOverlay(new BMap.Marker(point));
					$('#groupCoordinate').val(point.lng + "," + point.lat);
				} else {
					alert("您输入的地址没有解析到结果，请在地图中选取您的地址!");
				}
			}, "南京市");
		});
		var map = new BMap.Map("groupMap", {
			minZoom : 8,
			maxZoom : 18
		});
		map.setCurrentCity("南京"); // 设置地图显示的城市 此项是必须设置的
		map.centerAndZoom(new BMap.Point(split[0], split[1]), 14);
		//map.centerAndZoom("南京", 12);
		map.addControl(new BMap.MapTypeControl()); // 添加地图类型控件 
		map.enableScrollWheelZoom(true); // 开启鼠标滚轮缩放
		var circleOptions = {
				strokeColor : "#FF0000",
				strokeOpacity : 0.35,
				strokeWeight : 2,
				fillColor : "#40E0D0",
				fillOpacity : 0.35
			};
			var point = new BMap.Point(split[0], split[1]);
			var circle = new BMap.Circle(point, 200, circleOptions);
			// circle.zIndex = circleList.length;
			map.addOverlay(circle);
		map.addEventListener("click", function(e) {
			map.clearOverlays();
			map.centerAndZoom(e.point, 16);
			map.addOverlay(new BMap.Marker(e.point));
			$('#groupCoordinate').val(e.point.lng + "," + e.point.lat);
			
			var geoc = new BMap.Geocoder();
			geoc.getLocation(e.point, function(rs){
				var addComp = rs.addressComponents;
				$('#groupAddress').val(addComp.province + addComp.city + addComp.district +addComp.street +addComp.streetNumber);
			});  
		});
	},
	/**
	 * 加载小区信息
	 */
	loadearId : function() {
		
		common.openWindow({
			title : '选择小区',
			name : 'selectArear',
			type : 1,
			area : [ ($(window).width()-20)+'px', ($(window).height()-20)+'px' ],
			data : {
				flag : 1
			},
			url : '/html/pages/house/group/groupIndex.html'
		});
	},
	loadSelect : function() {
		common.loadItem('GROUP.SPEC', function(json) {
			var html = "<option value=''>请选择...</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#shi').append(html);
		},false);
//		common.loadItem('HOUSE.TYPE', function(json) {
//			var html = "";
//			for (var i = 0; i < json.length; i++) {
//				html += '<option value="' + json[i].item_id + '" >'
//				+ json[i].item_name + '</option>';
//			}
//			$('#house_type').append(html);
//		},false);
		common.loadItem('GROUP.PURPOSE', function(json) {
			var html = "";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#purpose').append(html);
		},false);
		common.loadItem('GROUP.TING', function(json) {
			var html = "<option value=''>请选择...</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#ting').append(html);
		},false);
		common.loadItem('GROUP.KITCHEN', function(json) {
			var html = "<option value=''>请选择厨房</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#chu').append(html);
		},false);
		common.loadItem('GROUP.BALCONY', function(json) {
			var html = "<option value=''>请选择阳台</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#yang').append(html);
		},false);
		common.loadItem('GROUP.LIFT', function(json) {
			var html = "<option value=''>请选择电梯</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#dianti').append(html);
		},false);
		common.loadItem('AGREEMENT.PAYTYPE', function(json) 
		{
			var html = "";
			for ( var i = 0; i < json.length; i++) 
			{
				html += '<option data_rent="'+json[i].item_value+'" value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#payment').append(html);
		},false);
		common.loadItem('GROUP.WEI', function(json) {
			var html = "<option value=''>请选择...</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#wei').append(html);
		},false);
		common.loadItem('DECORATE.TYPE', function(json) {
			var html = "<option value=''>请选择...</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#decorate').append(html);
		},false);
		// 加载出租年限
		common.loadItem('GROUP.RENT.YEAR', function(json) 
		{
			var html = "";
			for ( var i = 0; i < json.length; i++) 
			{
				html += '<option data_rent="'+json[i].item_value+'" value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#lease_period').append(html);
		},false);
	},
	/**
	 * 保存操作
	 */
	save : function() {
		if (!Validator.Validate('form2'))
		{
			return;
		}
		var filepath = common.dropzone.getFiles('#myAwesomeDropzone');
		if (filepath.length == 0) 
		{
			common.alert(
			{
				msg : '请选择房源图片!'
			});
			return;
		}
		$('#house_add_bnt').unbind("click");
		var path = "";
		var returnI = false;
		for (var i = 0; i < filepath.length; i++) 
		{
			if (filepath[i].fisrt == 1) 
			{
				path = filepath[i].path + ',' + path;
			}
			else
			{
				path += filepath[i].path + ",";
			}
			returnI = true;
		}
		if (returnI) 
		{
			path = path.substring(0, path.length - 1);
		}
		var rentMonths = '';
		$.each($('input[name="rentMonth"]'),function(){
		//	console.log($(this).attr('disabled'));
			if($(this).val() && !$(this).attr('disabled'))
			{
				rentMonths += $(this).val()+'|';
			}
		});
		common.ajax({
			url : common.root + '/BaseHouse/saveHouseInfo.do',
			data : {
				house_name : $('#house_name').val(),
				shi : $('#shi').val(),
				ting : $('#ting').val(),
				wei : $('#wei').val(),
				chu : $('#chu').val(),
				yang : $('#yang').val(),
				dianti : $('#dianti').val(),
				decorate : $('#decorate').val(),
				area : $('#area').val(),
				floor : $('#floor').val(),
				group_id : $('#group_id').val(),
				groupAddress : $('#groupAddress').val(),
				groupCoordinate : $('#groupCoordinate').val(),
				descInfo : $('#descInfo').val(),
				path : path,
				userMobile : $('#userMobile').val(),
				userName : $('#userName').val(),
//				value_date : $('#value_date').val(),
				regionId : $('#regionId').val(),
				lease_period : $('#lease_period').val(),
				cost_a : rentMonths,
				payment : $('#payment').val(),
				free_rent : $('#free_rent').val(),
				is_cubicle : $('#is_cubicle').val(),
				purpose : $('#purpose').val(),
				house_type : 0,
				id : id
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				$('#house_add_bnt').click(houseAdd.save);
				if (isloadsucc) {
					if (data.state == 1) {// 操作成功
						common.alert({
							msg : '操作成功',
							fun : function() {
								common.closeWindow('addHouse', 3);
							}
						});
					} else if (data.state == -3) {
						common.alert({
							msg : '文件上传失败!'
						});
					} else if (data.state == -4) {
						common.alert({
							msg : '房源名称已经存在!'
						});
						return;
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
	}, 
	loadHouseInfo : function(id) {
		common.ajax({
			url : common.root + '/BaseHouse/loadHouseInfo.do',
			data : {
				id : id
			},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, newdata)
			{
				if (isloadsucc) {
					console.log(newdata.houseInfo);
					var data = newdata.houseInfo;
					if(data.house_status < 4 )
					{
						$('#myTab:not(.active)').remove(); 
					}
					else
					{
						$('.hideDiv').hide();
						if(data.house_status > 3)
						{
							var agreementInfo = newdata.agreementInfo;
							// 展示合约信息
							$('#agreementCode').text(agreementInfo.code);
							$('#agreementName').text(agreementInfo.name);
							$('#owerInfo').text(agreementInfo.user_info);
							$('#agreementDate').text(agreementInfo.cz_time);
							$('#agentsID').text(agreementInfo.agents);
							$('#agreementStatus').text(agreementInfo.status);
							$('#agreement_zj').text(agreementInfo.cost_a);
							$('#agreement_mz').text(agreementInfo.free_period);
							$('#payType').text(agreementInfo.payType);
							$('#agreementCode').click(function(){
								houseAdd.loadAgreementInfo(agreementInfo.id);
							});
							try
							{
								var picArray = new Array();
								var imageHtml = '';
								if(!common.isEmpty(agreementInfo.file_path) && agreementInfo.file_path.indexOf('|')!=-1)
								{
									for (var i = 0; i < agreementInfo.file_path.split("|").length; i++) {
										if (i == 0) {
											picArray.push({
												path : agreementInfo.file_path.split("|")[i],
												first : 1
											});
										} else {
											picArray.push({
												path : agreementInfo.file_path.split("|")[i],
												first : 0
											});
										}
									}
								}
								common.dropzone.init({
									id : '#agreementPic',
									maxFiles : 4,
									defimg : picArray, 
									clickEventOk:false
								});
								picArray = new Array();
								var imageHtml = '';
								if(!common.isEmpty(agreementInfo.propertyPath) && agreementInfo.propertyPath.indexOf('|')!=-1)
								{
									for (var i = 0; i < agreementInfo.propertyPath.split("|").length; i++) {
										if (i == 0) {
											picArray.push({
												path : agreementInfo.propertyPath.split("|")[i],
												first : 1
											});
										} else {
											picArray.push({
												path : agreementInfo.propertyPath.split("|")[i],
												first : 0
											});
										}
									}
								}	
								common.dropzone.init({
									id : '#propertyAttachment',
									maxFiles : 4,
									defimg : picArray, 
									clickEventOk:false
								});
								picArray = new Array();
								var imageHtml = '';
								if(!common.isEmpty(agreementInfo.agentPath) && agreementInfo.agentPath.indexOf('|')!=-1)
								{
									for (var i = 0; i < agreementInfo.agentPath.split("|").length; i++) {
										if (i == 0) {
											picArray.push({
												path : agreementInfo.agentPath.split("|")[i],
												first : 1
											});
										} else {
											picArray.push({
												path : agreementInfo.agentPath.split("|")[i],
												first : 0
											});
										}
									}
								}	
								common.dropzone.init({
									id : '#agentAttachment',
									maxFiles : 4,
									defimg : picArray, 
									clickEventOk:false
								});
							}
							catch(e)
							{
								
							}
							// 横排tab
							/**
							var ulHtml = '<ul id="myTab2" class="nav nav-tabs">';
							var contentDiv = '<div class="panel-body"><div  class="tab-content">';
							var agreementInfos = newdata.zlAgreementInfo;
							var agreement ;
							for(var i = 0; i < agreementInfos.length; i++)
							{
								var cnt = i; 
								agreement = agreementInfos[i];
								ulHtml += '<li class="'+(cnt==0?'active':'')+'"><a href="#house'+cnt+'" data-toggle="tab">'+agreement.title+'</a></li>';
								contentDiv += '<div class="row tab-pane fade '+(cnt==0?' in active':'')+'" id="house'+cnt+'">';
								contentDiv += '<div class="form-group"><label class="col-md-5 control-label leftLabel">合约编号: <span>'+agreement.code+'</span></label><label class="col-md-4 control-label leftLabel">合约名称: <span>'+agreement.name+'</span></label><label class="col-md-3 control-label leftLabel">合约状态: <span class="noborder">'+agreement.rankStatus+'</span></label></div>';
								contentDiv += '<div class="form-group"><label class="col-md-5 control-label leftLabel">租客信息: <span>'+agreement.user_info+'</span></label><label class="col-md-4 control-label leftLabel">合约时间: <span id="agreementDate">'+agreement.cz_time+'</span></label><label class="col-md-3 control-label leftLabel">签约管家：<span id="agentsID">'+agreement.agents+'</span></label></div>';
								contentDiv += '<div class="form-group"><label class="col-md-5 control-label leftLabel">付款方式: <span>'+agreement.pay_type+'</span></label><label class="col-md-4 control-label leftLabel">每月租金: <span id="agreementDate">'+agreement.cost_a+'</span></label><label class="col-md-3 control-label leftLabel">出租类型：<span id="agentsID"></span>'+agreement.rank_type+'</label></div>';
								contentDiv += '<div class="form-group"> <div class="col-md-12"><div class="dropzone" id="agreementPic'+cnt+'"></div><div class="row"></div></div> </div> </div>'
							}
							ulHtml += '</ul>';
							contentDiv += '</div>';
							$('#rankAgreementDiv').html(ulHtml +contentDiv);
							*/
							// 竖排面板
							 
							var agreementInfos = newdata.zlAgreementInfo;
							var agreement ;
							for(var i = 0; i < agreementInfos.length; i++)
							{
								var contentDiv = '';
								var cnt = i; 
								agreement = agreementInfos[i];
								contentDiv += '<div class="panel panel-success"><div class="panel-heading" style="height: 10px;" align="center">';
								contentDiv += '<a data-toggle="collapse" data-parent="#accordion" href="#house'+cnt+'"><h4 class="panel-title" style="margin-top: -8px; color: white;">'+agreement.title+'('+agreement.code+')</h4></a></div>';
								contentDiv += '<div class="panel-body"><div  class="tab-content">';
								contentDiv += '<div class="panel-collapse collapse '+(cnt==0?' in ':'')+'" id="house'+cnt+'"><div class="panel-body">';
								contentDiv += '<div class="form-group"><label class="col-md-5 control-label leftLabel">合约编号: <span>'+agreement.code+'</span></label><label class="col-md-4 control-label leftLabel">合约名称: <span>'+agreement.name+'</span></label><label class="col-md-3 control-label leftLabel">合约状态: <span class="noborder">'+agreement.rankStatus+'</span></label></div>';
								contentDiv += '<div class="form-group"><label class="col-md-5 control-label leftLabel">租客信息: <span>'+agreement.user_info+'</span></label><label class="col-md-4 control-label leftLabel">合约时间: <span id="agreementDate">'+agreement.cz_time+'</span></label><label class="col-md-3 control-label leftLabel">签约管家：<span id="agentsID">'+agreement.agents+'</span></label></div>';
								contentDiv += '<div class="form-group"><label class="col-md-5 control-label leftLabel">付款方式: <span>'+agreement.pay_type+'</span></label><label class="col-md-4 control-label leftLabel">每月租金: <span id="agreementDate">'+agreement.cost_a+'</span></label><label class="col-md-3 control-label leftLabel">出租类型：<span id="agentsID"></span>'+agreement.rank_type+'</label></div>';
								contentDiv += '<div class="form-group"> <div class="col-md-1"></div><div class="col-md-10"><div class="dropzone" id="agreementPic'+cnt+'"></div><div class="row"></div></div> <div class="col-md-1"></div></div> </div>'
								contentDiv += '</div>';
								contentDiv += '</div>';
								contentDiv += '</div>';
								$('#rankAgreementDiv').append(contentDiv);
							}
							 
							
							
							for(var j = 0; j < agreementInfos.length; j++)
							{
								var agreement = agreementInfos[j];
								if(agreement)
								{
									try
									{
										var picArray = new Array();
										var imageHtml = '';
										for (var i = 0; i < agreement.file_path.split("|").length; i++) 
										{
											if (i == 0) 
											{
												picArray.push({
													path : agreement.file_path.split("|")[i],
													first : 1
												});
											} else {
												picArray.push({
													path : agreement.file_path.split("|")[i],
													first : 0
												});
											}
										}
										
										var idName = '#agreementPic'+j; 
										common.dropzone.init({
											id : idName,
											maxFiles : 4,
											defimg : picArray, 
											clickEventOk:flag==1?true:false
										});
									}
									catch(e)
									{
										
									}
									
								}	
							}
							if(data.house_status > 4)
							{
								// 待交接 可以看到工程信息
								houseAdd.loadProject(agreementInfo.id)
							}
						}
						
					}
//					$('#house_type').val(data.house_type);
					$('#purpose').val(data.purpose);
					$('#house_name').val(data.house_name);
					$('#shi').val(data.spec.split('|')[0]);
					$('#ting').val(data.spec.split('|')[1]);
					$('#wei').val(data.spec.split('|')[2]);
					$('#chu').val(data.spec2.split('|')[0]);
					$('#yang').val(data.spec2.split('|')[1]);
					$('#dianti').val(data.spec2.split('|')[2]);
					$('#decorate').val(data.decorate);
					$('#area').val(data.area);
					$('#floor').val(data.floor);
					$('#group_id').val(data.group_id);
					$('#groupName').val(data.group_name);
					$('#groupAddress').val(data.address);
					$('#groupCoordinate').val(data.coordinate);
					$('#descInfo').val(data.descInfo);
					$('#userMobile').val(data.mobile);
					$('#userName').val(data.username);
//					$('#value_date').val(data.value_date);
					$('#lease_period').val(data.lease_period);
//					$('#cost_a').val(data.cost_a);
					$('#payment').val(data.payment);
					$('#free_rent').val(data.free_rent);
					$('#is_cubicle').val(data.is_cubicle);
					houseAdd.editLoadMap(data.coordinate);
					try
					{
						if(!common.isEmpty(data.cost_a) && data.cost_a.indexOf('|')!=-1)
						{
							var rentMonths = data.cost_a.split('|');
							var tmpHtml = '';
							for(var i = 0; i < rentMonths.length-1; i++)
							{
								var tmpLength = (i+1);
								tmpHtml += '&nbsp;&nbsp;<input type="number" value="'+rentMonths[i]+'" dataType="Integer3" value msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />';
							}
							$('#div_rent').html(tmpHtml);
							$("input[name='rentMonth']").removeClass('rent_width_5');
							$("input[name='rentMonth']").addClass('rent_width_5');
						}
						houseAdd.changeMonthRent();
						var picArray = new Array();
						if(!common.isEmpty(data.appendix))
						{
							for (var i = 0; i < data.appendix.split("|").length; i++) 
							{
								if (i == 0) 
								{
									picArray.push({
										path : data.appendix.split("|")[i],
										first : 1
									});
								} else {
									picArray.push({
										path : data.appendix.split("|")[i],
										first : 0
									});
								}
							}
						}
						common.dropzone.init({
							id : '#myAwesomeDropzone',
							maxFiles : 4,
							defimg : picArray, 
							clickEventOk:flag == 0? false:true
						});
					}
					catch(e)
					{
						
					}
					$('#userName').val()?$('#userName').attr("readonly","readonly"):'';
					if(flag == 0) // 0 查看房源详细 1编辑房源
					{
						$('#houseType').html("<span class='form-control'>"+data.specName+data.specName2+"</span>");
						$('#houseType').removeClass('divFlex');
						$("#form2 b").hide();
						$.each($('.changeInput'),function()
						{ 
	//						console.log(this+i);
							$(this).parent().html('<span class="form-control">'+$(this).val()+'</span>');
						//	$(this).html('<span class="form-control">'+val+'</span>');
						});
	//					$('select').attr("disabled","disabled");
						$.each($('select'),function(){
						  var $span = $(this).parent("div");
						  var selectedValue = $(this).find("option:selected").text();
						  $(this).remove(); 
						  $span.append("<span class='form-control'>"+selectedValue+"</span>");
						});
						$('#div_rent').html("<span class='form-control'>"+(common.isEmpty(data.cost_a)?'':data.cost_a)+"</span>");
						$("#descInfo").replaceWith("<span class='form-control'>"+data.descInfo+"</span>");
						$('#descInfo').attr("readonly","readonly");
					}
//					$('div').removeClass("textLeft")
					
					//展示合约信息
					

				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},
	loadProject:function(agreementId)
	{
		var html  = '<div class="col-md-12">'
				  + '<div class="panel" data-collapsed="0">'
				  + '  <div class="panel-body">'
				  + '    <div class="row">'
				  + '      <div class="form-group">'
				  + '        <label class="col-md-5 control-label leftLabel">当前户型：<span id="hx"></span></label>'
				  + '        <label class="col-md-4 control-label leftLabel">流转时间: <span id="lztime"></span></label>'
				  + '        <label class="col-md-3 control-label leftLabel">开工时间: <span id="kgtime"></span></label>'
				  + '      </div>'
				  + '      <div class="form-group">'
				  + '        <label class="col-md-5 control-label leftLabel">完工时间：<span id="wgtime"></span></label>'
				  + '        <label class="col-md-4 control-label leftLabel">装修金额: <span id="zxmonery"></span></label>'
				  + '        <label class="col-md-3 control-label leftLabel">家具金额：<span id="jjmonery"></span></label>'
				  + '      </div>'
				  + '      <div class="form-group">'
				  + '        <label class="col-md-5 control-label leftLabel">家电金额: <span id="djmonery"></span></label>'
				  + '        <label class="col-md-4 control-label leftLabel">保洁与其它：<span id="othermonery"></span></label>'
				  + '        <label class="col-md-3 control-label leftLabel">工程总计: <span id="totalmonery"></span></label>'
				  + '      </div>'
				  + '      <div class="form-group">'
				  + '        <label class="col-md-5 control-label leftLabel">工程进度: <span id="gcjd"></span></label>'
				  + '      </div>'
				  + '    </div>'
				  + '    </div>'
				  + '  </div>';
//		html = "";
		html += '<div class="panel panel-success">';
		html += '<div class="panel-heading" style="height: 10px;" align="center"><a data-toggle="collapse" data-parent="#accordion" href="#project1"><h4 class="panel-title" style="margin-top: -8px; color: white;">家具配套清单</h4></a></div>';
		html += '<div class="panel-body"><div  class="tab-content">';
		html += '<div class="panel-collapse collapse in" id = "project1">'
			 +  '<div class="panel-body">';
		html +=  '<div class="adv-table">'
			 + '  <table id="jjDiv_table"'
		     + '    class="display tablehover table table-bordered dataTable"'
		     + '    cellspacing="0" width="100%">'
		     + '    <thead>'
		     + '      <tr>'
		     + '        <th></th>'
			 + '        <th></th>'
			 + '        <th>材料名称</th>'
			 + '        <th>供应商</th>'
			 + '        <th>品牌</th>'
			 + '        <th>规格</th>'
			 + '        <th>编码</th>'
			 + '        <th>单价</th>'
			 + '        <th></th>'
		   	 + '      </tr>'
		   	 + '    </thead>'
		   	 + '  </table>'
		   	 + '</div>';
		 html += "</div></div></div></div></div><br/>";
		 html += '<div class="panel panel-success"><div class="panel-heading" style="height: 10px;" align="center">';
		 html += '<a data-toggle="collapse" data-parent="#accordion" href="#project2"><h4 class="panel-title" style="margin-top: -8px; color: white;">家电配套清单</h4></a></div>';
		 html += '<div class="panel-body"><div  class="tab-content">';
		 html += '<div class="panel-collapse collapse" id = "project2"><div class="panel-body">';
		 html +=  '<div class="adv-table">'
				+ '  <table id="jdDIV"'
				+ '    class="display tablehover table table-bordered dataTable"'
				+ '    cellspacing="0" width="100%">'
				+ '    <thead>'
				+ '      <tr>'
				+ '        <th></th>'
				+ '        <th></th>'
				+ '        <th>材料名称</th>'
				+ '        <th>供应商</th>'
				+ '        <th>品牌</th>'
				+ '        <th>规格</th>'
				+ '        <th>编码</th>'
				+ '        <th>单价</th>'
				+ '        <th></th>'
				+ '      </tr>'
				+ '    </thead>'
				+ '  </table>'
				+ '</div>';
		 html += "</div></div></div></div></div><br/>";
		 html += '<div class="panel panel-success"><div class="panel-heading" style="height: 10px;" align="center">';
		 html += '<a data-toggle="collapse" data-parent="#accordion" href="#project3"><h4 class="panel-title" style="margin-top: -8px; color: white;">其它相关清单</h4></a></div>';
		 html += '<div class="panel-body"><div  class="tab-content">';
		 html += '<div class="panel-collapse collapse" id = "project3"><div class="panel-body">';
		 html +=  '<div class="adv-table">'
				+ '  <table id="qtDIV"'
				+ '    class="display tablehover table table-bordered dataTable"'
				+ '    cellspacing="0" width="100%">'
				+ '    <thead>'
				+ '      <tr>'
				+ '        <th></th>'
				+ '        <th></th>'
				+ '        <th>材料名称</th>'
				+ '        <th>供应商</th>'
				+ '        <th>品牌</th>'
				+ '        <th>规格</th>'
				+ '        <th>编码</th>'
				+ '        <th>单价</th>'
				+ '        <th></th>'
				+ '      </tr>'
				+ '    </thead>'
				+ '  </table>'
				+ '</div>';
		 html += "</div></div></div></div></div></div>";
		// 加载工程信息
		$('#gcInfo').html(html);
		// 加载工程基本信息
		common.ajax({
			url : common.root + '/BaseHouse/getProjectInfo.do',
			data : {agreementId : agreementId},
			dataType : 'json',
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					$('#hx').text(data.spce);
					$('#lztime').text(data.createtime);
					$('#kgtime').text(data.constructiontime);
					$('#wgtime').text(data.completetime);
					$('#zxmonery').text(data.money);
					$('#jjmonery').text(data.furnituremoney);
					$('#djmonery').text(data.appliancesmoney);
					$('#totalmonery').text(data.total);
					$('#othermonery').text(data.othermoney);
					$('#gcjd').text(data.STATUS);
				}
			}});	
		// 加载清单信息
		houseAdd.loadTableInfo('#jjDiv_table',1,agreementId);
		houseAdd.loadTableInfo('#jdDIV',2,agreementId);
		houseAdd.loadTableInfo('#qtDIV',0,agreementId);
	},
	loadTableInfo:function(selector, flag, agreementId)
	{
		table.init({
            id: selector,
            "aLengthMenu": [5,10, 25, 50,100],
            "iDisplayLength":20,
            isexp:false,
            bStateSave:false,
            "oLanguage": {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				'sSearch':'搜索',
				"oPaginate": {
					"sFirst": "首页",
					"sPrevious": "前一页",
					"sNext": "后一页",
					"sLast": "尾页"
				},
				"sZeroRecords": "没有检索到数据",
				sProcessing:'<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
				sEmptyTable:'很抱歉，暂无数据'
			},
            url: common.root + '/BaseHouse/getProjectList.do',
            columns:["name",
    		            "supname", 
    		            "brand",  
    		            "spec", 
    		            "code",
    		            "price"],
            param: function(){
                var a = new Array();
				a.push({name: "flag", value: flag});
				a.push({name: "agreementId", value: agreementId});
                return a;
            } 
        });
		$('.dataTables_length').hide();
	},
	loadAgreementInfo:function(agreementId)
	{  
		var title = '合约详细';
		common.openWindow({
			area:['900px','630px'],
			name:'houseSign',
			type : 3,
			data:{agreementId:agreementId,flag:0,id:id},
			title : title,
			url : '/html/pages/house/houseInfo/houseSign.html'
		});
	}

};
flag = common.getWindowsData('addHouse').flag; // 0 查看房源详细 1编辑房源
id = common.getWindowsData('addHouse').id;
houseAdd.init();
