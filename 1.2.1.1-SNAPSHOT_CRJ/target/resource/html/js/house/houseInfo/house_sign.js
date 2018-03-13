var agreementId = '';
var roomArray = null;
var roomp = 0;
var roomtotalArray = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];
var house_sign = 
{
		init:function()
		{
			$('#carBank').click(house_sign.selectBank);
			
			$('#mcate_select').change(function() {
				house_sign.mcateList();
			});
			
			$('.closeDialog').click(function(){
				$('#sbModal').modal('hide');
			});
			
			$('#serchmcate').click(function(){
				house_sign.mcateList();
			});
			var nowTemp = new Date();
			$('#effectDate').daterangepicker(
			{
				startDate : nowTemp.getFullYear() + '-' + (nowTemp.getMonth() + 1) + '-02',
				timePicker12Hour : false,
				timePicker : false,
				singleDatePicker : true,
				// 分钟间隔时间
				timePickerIncrement : 10,
				format : 'YYYY-MM-DD'
			}, function(start, end, label) {
//				console.log(start.toISOString(), end.toISOString(), label);
			});
			$('#agreementId').val(agreementId);
			common.loadItem('DECORATE.TYPE', function(json) {
				var html = '';
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#decorate').append(html);
			},false);
			common.ajax(
					{
						url : common.root + '/BaseHouse/getMangerList.do',
						dataType : 'json',
						async:false,
						loadfun : function(isloadsucc, json) 
						{
							if (isloadsucc)
							{
								var html = "请选择...";
								for ( var i = 0; i < json.length; i++) 
								{
									html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
								}
								$('#contractManage').append(html);
							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						}
					});
			
			// 加载出租年限
			common.loadItem('GROUP.RENT.YEAR', function(json) 
			{
				var html = "";
				for ( var i = 0; i < json.length; i++) 
				{
					html += '<option data_rent="'+json[i].item_value+'" value="' + json[i].item_id + '" >'
					+ json[i].item_name + '</option>';
				}
				$('#leaseTime').append(html);
			},false);
			// 加载付款方式
			common.loadItem('AGREEMENT.PAYTYPE', function(json) 
			{
				var html = "";
				for ( var i = 0; i < json.length; i++) 
				{
					html += '<option  value="' + json[i].item_id + '" >'
					+ json[i].item_name + '</option>';
				}
				$('#payType').append(html);
			},false);
			house_sign.loadHouseInfo(common.getWindowsData('houseSign').id);
			$('#leaseTime').change(house_sign.changeMonthRent); //绑定租约时长
			
			// 新增
			if(agreementId=='')
			{
				$('#house_sign_bnt').click(house_sign.save);
				common.dropzone.init(
				{
					id : '#myAwesomeDropzone',
					maxFiles : 4,
				});
				common.dropzone.init(
				{
					id : '#propertyAttachment',
					maxFiles : 4,
				});
				common.dropzone.init(
				{
					id : '#agentAttachment',
					maxFiles : 4,
				});
				$('#leaseTime').val(common.getWindowsData('houseSign').lease_period);
//				$('#div_rent').html(tmpHtml);
				$("input[name='rentMonth']").removeClass('rent_width_5');
				$("input[name='rentMonth']").addClass('rent_width_5');
//				$('#lease_period').val(common.getWindowsData('houseSign').lease_period);
				
				$('#houseName').text(common.getWindowsData('houseSign').house_name);
				$('#contractName').val(common.getWindowsData('houseSign').house_name);
				$('#yzInfo').text(common.getWindowsData('houseSign').username);
				$('#cardPressonName').val(common.getWindowsData('houseSign').username);
//				$('#fooler').val(common.getWindowsData('houseSign').floor);
				$('#houseId').val(common.getWindowsData('houseSign').id);
				$('#user_id').val(common.getWindowsData('houseSign').user_id);
				$('#user_mobile').val(common.getWindowsData('houseSign').mobile);
				$('#houseCode').val(common.getWindowsData('houseSign').houseCode);
				$('#free_rent').val(common.getWindowsData('houseSign').free_rent);
				$('#areaid').val(common.getWindowsData('houseSign').areaid);
				$('#is_kg').val(common.getWindowsData('houseSign').is_cubicle);
				try
				{
					var roomCnt = common.getWindowsData('houseSign').roomCnt;
					var rentMonths = common.getWindowsData('houseSign').cost_a.split('|');
					var tmpHtml = '';
					for(var i = 0; i < rentMonths.length-1; i++)
					{
						var tmpLength = (i+1);
						tmpHtml += '&nbsp;&nbsp;<input type="number" value="'+rentMonths[i]+'" dataType="Integer3" value msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />';
					}
					$('#div_rent').html(tmpHtml);
				}
				catch(e)
				{
					
				}
				house_sign.changeMonthRent();
				var numberReg = /^\\d+$/;
				roomCnt = numberReg.test(roomCnt)?0:roomCnt;
			 
				roomArray = new Array(Math.abs(roomCnt)+1);
				var div_pz = '<div id="copyDIV" style="color: #f34c4c;cursor:pointer" title="请先配置1房间，点击此按钮，使他房间和1房间统一配置">一键复制</div>';
				for(var i = 0; i < Math.abs(roomCnt); i++)
				{
					div_pz += '<div><a class="a_no_line">'+(i+1)+'房间配置</a>&nbsp;&nbsp;<span id="room'+(i+1)+'"/></span><input type="hidden" data_room_pz='+i+' name="room_pz_" /></div>' ;
					roomArray[i] = new Map();
				}
				div_pz += '<div><a class="a_no_line">其他房间配置</a>&nbsp;&nbsp;<span id="room'+(Math.abs(roomCnt)+1)+'"></span><input type="hidden" data_room_pz='+Math.abs(roomCnt)+' name="room_pz_" /></div>' ;
				roomArray[Math.abs(roomCnt)] = new Map();
				$('#div_room_pz').html(div_pz);
				$('.a_no_line').click(house_sign.selectRoomPz);
				if($('.a_no_line').length > 2)
				{
					$('#copyDIV').click(house_sign.copypz);
				}
				else
				{
					$('#copyDIV').hide();
				}
			}
			else
			{
				$('#div_room_pz').addClass('div_room_pz_80');
				// 修改或者查看明细
				house_sign.agreementInfo();
//				$('#house_sign_bnt').click(house_sign.save);
			}
//			$('.newUl').find('li').removeClass('active');
//			$('#houseBaseInfo').removeClass('in active');
//			 
//			$('.newUl').find('li').eq(1).addClass('active');
//			$('#signBaseInfo').addClass('in active');
			
		},
		selectBank:function()
		{
			common.openWindow({
				title : '选择银行信息',
				name : 'selectBank',
				type : 1,
				area : [ ($(window).width()-20)+'px', ($(window).height()-20)+'px' ],
				data : {
					flag : 1
				},
				url : '/html/pages/house/agreement/bank_list.html'
			});
		},
		changeMonthRent:function()
		{
			// 5年放一行，不足补空
//			console.log($('#leaseTime option:selected').attr("data_rent"));
			var data_rent =$('#leaseTime option:selected').attr("data_rent"); //需求选择的时间
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
//				console.log($("input[name='rentMonth']:eq("+i+")"));
//				console.log(i); 
				$("input[name='rentMonth']:eq("+i+")").val($(this).val());
			});
			$("input[name='rentMonth']").removeClass('rent_width_5');
			$("input[name='rentMonth']").addClass('rent_width_5');
		},
		selectRoomPz:function()
		{
			var roomTh = $(this).parent().find('input').attr('data_room_pz'); //选择第几个房间配置
			roomp = roomTh;
			$("#tbody_mcate").html('');
			$("#mcate_select").html('<option value="-1">请选择</option>');
             //弹出窗口			
			$('#sbModal').modal({
				show : true,
				backdrop : 'static'
			});
			
			var fatherid="0";
				common.ajax({
					url:common.root+'/sys/queryMcate.do',
					data:{fatherid:fatherid},
					dataType: 'json',
					loadfun: function(isloadsucc, data){
						if(isloadsucc){
							if(data.state == 1){
								 var html = "";
						            for (var i = 0; i < data.list.length; i++) 
						            {
						 				html += '<option  value="' + data.list[i].id + '" >' + data.list[i].name + '</option>';
						            }
						            $('#mcate_select').append(html);
						            house_sign.mcateList();
							}else{
								common.alert({msg:common.msg.error});
								console.error(data);
							}
						}else{
							common.alert({msg:common.msg.error});
							console.error(data);
						}
					}
				});
		},
		save:function()
		{
			
			if (!Validator.Validate('form1'))
			{
				return;
			}
			
			// 房产证
			var filepath = common.dropzone.getFiles('#myAwesomeDropzone');
			if (filepath.length == 0) 
			{
				common.alert(
				{
					msg : '请上传房产证附件!'
				});
				return;
			}
//			$('#house_add_bnt').unbind("click");
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
			$('#picPath').val(path);
			
			// 产权人附件
			filepath = common.dropzone.getFiles('#propertyAttachment');
			if (filepath.length == 0) 
			{
				common.alert(
				{
					msg : '请上传产权人附件!'
				});
				return;
			}
			path = "";
			returnI = false;
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
			$('#propertyPath').val(path);
			
			// 产权人附件
			filepath = common.dropzone.getFiles('#agentAttachment');
			path = "";
			returnI = false;
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
			$('#agentPath').val(path);
			
			var ajax_option =
			{
				url:"/BaseHouse/signHouse.do",//默认是form action
				success:house_sign.resFunc,
				type:'post',
				dataType:'json'
			};
			$('#house_sign_bnt').unbind("click");
			$("#form1").ajaxForm(ajax_option).submit();
		},
		//确定事件
		setMcatePage:function()  {
			var cateMap = roomArray[roomp];
			var cateArray = cateMap.keySet();
//			console.log(cateMap);
//			console.log(cateArray);
			$("#room" + (Math.abs(roomp)+1)).html('');
			var cateHtml = '';
			var inputHtml = '';
			inputHtml += '(' ;
			for ( var i = 0; i < cateArray.length; i++) 
			{
				var kstr = cateArray[i];
//				console.log("cc:"+cateMap.get(kstr));
				if (cateMap.get(kstr) != null && cateMap.get(kstr) != "") 
				{
					inputHtml += kstr + ',' + cateMap.get(kstr) + '|';
//					console.log("dd:"+kstr);
//					console.log(cateMap.get(kstr));
					cateHtml += cateMap.get(kstr);
				}
			}
			inputHtml += ')' ;
//			console.log(cateHtml);
//			console.log(roomp);
			$("#room" + (Math.abs(roomp)+1)).html(cateHtml);
			$("#room" + (Math.abs(roomp)+1)).parent().find('input[name="room_pz_"]').val(inputHtml);
			$('#sbModal').modal('hide');
		},
		//点击增加
		admcmcate:function(aid, aname){
			var cateMap = roomArray[roomp];
			var ccvalue = parseInt($("#cc" + aid).val());
			$("#cc" + aid).val(ccvalue + 1);
			cateMap.put(aid, aname + '=' + (ccvalue + 1));
//			console.log(cateMap);
		},//点击减少
		mimcmcate:function(aid, aname){
			var cateMap = roomArray[roomp];
			if (parseInt($("#cc" + aid).val()) > 0) {
				var ccvalue = parseInt($("#cc" + aid).val());
				$("#cc" + aid).val(ccvalue - 1);

				if (ccvalue > 1) {
					cateMap.put(aid, aname + '=' + (ccvalue - 1));
				} else {
					cateMap.remove(aid);
				}
			}
		},//点击清空
		clearMcatePage:function () {
			roomArray[roomp] = new Map();
			house_sign.mcateList();
		},
		// 保存返回函数处理
		resFunc:function(data)
		{
			$('#house_sign_bnt').click(house_sign.save);
			if(data.state==-2)
			{
				common.alert({
					msg : '此房源已被签约!',
					fun : function() {
						common.closeWindow('houseSign', 3);
						return ;
					}
				}); 
			}
			else if(data.state == 1)
			{
				common.alert({
					msg : '此房源签约成功!',
					fun : function() 
					{
						common.closeWindow('houseSign', 3);
						return ;
					}
				}); 
			}
			else if(data.state == -3)
			{
				common.alert({
					msg : '图片上传失败,请重试!',
				}); 
			}
			else if(data.state == -4)
			{
				common.alert({
					msg : '房间合约状态已发生改变!',
					fun : function() 
					{
						common.closeWindow('houseSign', 3);
						return ;
					}
				}); 
			}
			else  
			{
				common.alert({
					msg : '网络忙,请重试!',
				}); 
			}
		 
		},
		mcateList:function(){
//			console.log(roomp);
			var fatherids="";
			if ($('#mcate_select').val()!=null&&$('#mcate_select').val()!=""&&$('#mcate_select').val()!=-1) {
				fatherids=$('#mcate_select').val();
			}
			var name=$('#exampleInputEmail2').val();
			
			common.ajax({
				url:common.root+'/sys/queryMcate.do',
				data:{fatherid:fatherids,mcatename:name},
				dataType: 'json',
				loadfun: function(isloadsucc, data){
					if(isloadsucc){
						if(data.state == 1){
							 var html = "";
								var cnt = 0;
					            for (var i = 0; i < data.list.length; i++) {
					            	if ("0"==data.list[i].fatherid)
									{
										continue;
									}
					            	cnt ++;
					            	var cValue = 0;
					            	var cateMap = roomArray[roomp];
					            	if (cateMap.get(data.list[i].id) == null|| cateMap.get(data.list[i].id) == "") {
										cateMap.put(data.list[i].id, 0);
									} else {
										cValue = cateMap.get(data.list[i].id).split("=")[1];
									}
					 				html += '<tr> <td>'
					 					+ data.list[i].name
										+ '</td><td width="300px"><form class="form-inline"><div style="display: flex;display: -webkit-box; display: -webkit-inline-box;">'
										+ '<button type="button" style="padding-left: 10px; padding-right: 20px; padding-top: 10px;" class="btn btn-default input-group-addon" onClick="house_sign.admcmcate(\''
										+ data.list[i].id
										+ '\',\''
										+ data.list[i].name
										+ '\')">'
										+ '<span class="glyphicon glyphicon-plus"></span></button>'
										+ '<input type="text" readonly="readonly" class="form-control" id="cc'
										+ data.list[i].id
										+ '" placeholder="0" value='
										+ cValue
										+ ' />'
										+ '<button type="button" style="padding-left: 10px; padding-right: 20px;padding-top: 10px;" class="btn btn-default input-group-addon" onClick="house_sign.mimcmcate(\''
										+ data.list[i].id
										+ '\',\''
										+ data.list[i].name
										+ '\')">'
										+ '<span class="glyphicon glyphicon-minus"></span></button></div></form></td></tr>';
					            }
					            $("#tbody_mcate")[0].innerHTML=html; 
					              
					            $("#totalCount").text('共'+cnt+'条记录');
						}else{
							common.alert({msg:common.msg.error});
							console.error(data);
						}
					}else{
						common.alert({msg:common.msg.error});
						console.error(data);
					}
				}
			});	
		},
		agreementInfo:function()
		{
			// 合约明细
			//alert(agreementId);
			common.ajax({
				url:common.root+'/agreementMge/agreementInfo.do',
				data:{id:agreementId},
				dataType: 'json',
				loadfun: function(isloadsucc, data)
				{
					if(isloadsucc)
					{
//						console.log(data);
						console.log("123data-->"+JSON.stringify(data))
						$('#houseId').val(data.house_id);
						$('#houseName').text(data.house_name);
						$('#user_id').val(data.user_id);
						$('#user_mobile').val(data.user_mobile);
						$('#yzInfo').text(data.username);
						$('#contractId').val(data.sbcode);
						$('#contractName').val(data.name);
						$('#contractManage').val(data.agents);
						$('#is_kg').val(data.is_cubicle);
						$('#certificateno').val(data.certificateno);
						var keys_door = data.keys_count;
						
						if(keys_door != 'null' && keys_door != '' && keys_door != null)
						{
							$('#doorkey').val(keys_door.split('|')[0]);
							$('#fooler').val(keys_door.split('|')[1]);
							$('#personCount').val(keys_door.split('|')[2]);
						}	
//						console.log(room_pz);
//						console.log(data.old_matched);
						if(data.old_matched != "null" && data.old_matched != '' && data.old_matched != null)
						{
							var room_pz = data.old_matched.split(')');
//							console.log(room_pz.length);
							var roomCnt =  room_pz.length - 2;
//							console.log(roomCnt);
							roomArray = new Array(roomCnt);
							var div_pz = '<div id="copyDIV" style="color: #f34c4c;cursor:pointer" title="请先配置1房间，点击此按钮，使他房间和1房间统一配置">一键复制</div>';
							var room_pz_map = '';
							var showRoomPz = '';
							var tmp_map = new Map();
							for(var i = 0; i < roomCnt; i++)
							{
								tmp_map = new Map();
								var room_pz_map = room_pz[i].replace('(','');
//								console.log(room_pz_map);
								var tmp_room2 = room_pz_map.split('|');
								showRoomPz = '';// 房间配置
								for(var j = 0; j < tmp_room2.length; j++)
								{
									var tmp_ = tmp_room2[j];
									if(tmp_ != '') //不为空
									{
										showRoomPz += tmp_.split(',')[1];
										tmp_map.put(tmp_.split(',')[0],tmp_.split(',')[1]);
									}
								}
								div_pz += '<div><a class="a_no_line">'+(i+1)+'房间配置</a>&nbsp;&nbsp;<span id="room'+(i+1)+'">'+showRoomPz+'</span><input type="hidden" data_room_pz='+i+' value="('+room_pz_map+')" name="room_pz_" /></div>' ;
								roomArray[i] = tmp_map;
							}
							room_pz_map = room_pz[roomCnt].replace('(','');
							var tmp_room_ = room_pz_map.split('|');
							showRoomPz= '';
							tmp_map = new Map();
							for(var i = 0; i < tmp_room_.length; i++)
							{
								var tmp_ = tmp_room_[i];
								if(tmp_ != '') //不为空
								{
									showRoomPz += tmp_.split(',')[1];
									tmp_map.put(tmp_.split(',')[0],tmp_.split(',')[1]);
								}
							}	
							div_pz += '<div><a class="a_no_line">其他房间配置</a>&nbsp;&nbsp;<span id="room'+(roomCnt+1)+'">'+showRoomPz+'</span><input type="hidden" data_room_pz='+roomCnt+' value="('+room_pz_map+')" name="room_pz_" /></div>' ;
							roomArray[roomCnt] = tmp_map;
							$('#div_room_pz').removeClass('div_room_pz_80');
							$('#div_room_pz').addClass('div_room_pz_0');
							$('#div_room_pz').html(div_pz);
							if($('.a_no_line').length > 2)
							{
								$('#copyDIV').click(house_sign.copypz);
							}
							else
							{
								$('#copyDIV').hide();
							}
							$("#chiefLi").hide();
						}
						try
						{
							if(!common.isEmpty(data.new_old_matched))
							{
								var matched = data.new_old_matched.split('@@');
								var new_old_matched = matched[0];
								var kt = matched[1];
								if(!common.isEmpty(new_old_matched))
								{
									$("#chiefLi").show();
									house_sign.initRoomCnt(new_old_matched.split('##').length);
									for(var i = 0; i < new_old_matched.split('##').length; i++)
									{
										var matchArray = new_old_matched.split('##')[i];
//										console.log(matchArray.length)
										if(!common.isEmpty(matchArray))
										{
//											console.log(matchArray.length)
											for(var j = 0; j < matchArray.split('|').length; j++)
											{
//												console.log(matchArray.split('|')[j].split('=')[0])
												$("#"+matchArray.split('|')[j].split('=')[0]).val(matchArray.split('|')[j].split('=')[1]);
											}
										}	
									}	
								}
								if(!common.isEmpty(kt))
								{
									var ktArray = kt.split('|');
									for(var i = 0; i < ktArray.length; i++)
									{
										$("#"+ktArray[i].split('=')[0]).val(ktArray[i].split('=')[1]);
									}	
								}
								$('#housePz').hide();
							}
						}
						catch(e)
						{
							
						}
						 
						if(roomArray==null)
						{
							$('#div_room_pz').removeClass('div_room_pz_80');
							$('#div_room_pz').addClass('div_room_pz_100');
						}
						$('.a_no_line').click(house_sign.selectRoomPz);
						$('#payType').val(data.pay_type);
						$('#fee_date').val(data.free_period);
						$('#effectDate').val(data.begin_time);
						$('#leaseTime').val(data.rentMonth);
//						alert(data.rentMonth);
//						console.log(data.rentMonth);
						var rentMonths = data.cost_a.split('|');
						var tmpHtml = '';
						for(var i = 0; i < rentMonths.length-1; i++)
						{
							var tmpLength = (i+1);
							tmpHtml += '&nbsp;&nbsp;<input type="number" value="'+rentMonths[i]+'" dataType="Integer2" value msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />';
						}
						$('#div_rent').html(tmpHtml);
						$("input[name='rentMonth']").removeClass('rent_width_5');
						$("input[name='rentMonth']").addClass('rent_width_5');
						house_sign.changeMonthRent();
						$('#descInfo').text(data.desc_text);
						$('#cardText').val(data.bankcard);
						$('#bankId').val(data.bankid);
						$('#cardPressonName').val(data.cardowen);
						$('#cardWarter').val(data.watercard);
						$('#warterDegrees').val(data.water_meter);
						$('#cardgas').val(data.gascard);
						$('#gasDegrees').val(data.gas_meter);
						$('#cardelectric').val(data.electriccard);
						$('#eMeterH').val(data.electricity_meter_h);
						$('#eMeterL').val(data.electricity_meter_l);
						$('#eMeter').val(data.electricity_meter);
						$('#carBank').val(data.cardbank);
						// 不可修改
						if(common.getWindowsData('houseSign').flag == 0)
						{
							$('#copyDIV').hide();
							$('.a_no_line').unbind("click");
							$('#form1 b').hide();
							$('#house_sign_bnt').hide();
//							$('#div_rent').replaceWith(data.cost_a);
//							$('#div_rent').width("80%");
//							$('input[type="number"]').attr('disabled',true);
							$('#div_rent').html('<span class="form-control">'+data.cost_a+'</span>');
							$.each($('.inputChange2'),function()
							{
								$(this).parent().html('<span class="form-control">'+$(this).val()+'</span>');
							});
							$("#descInfo").replaceWith("<span class='form-control'>"+data.desc_text+"</span>");
							$.each($('select'),function(){
								  var $span = $(this).parent("div");
								  var selectedValue = $(this).find("option:selected").text();
//								  console.log(selectedValue);
								  $(this).remove(); 
								  $span.append("<span class='form-control'>"+selectedValue+"</span>");
							});
						}
						else
						{
							$('#house_sign_bnt').click(house_sign.save);
						}
						try
						{
							if(!common.isEmpty(data.pic_path))
							{
								var picArray = new Array();
								for (var i = 0; i < data.pic_path.split("|").length; i++) 
								{
									if (i == 0) {
										picArray.push({
											path : data.pic_path.split("|")[i],
											first : 1
										});
									} else {
										picArray.push({
											path : data.pic_path.split("|")[i],
											first : 0
										});
									}
								}
								common.dropzone.init({
									id : '#myAwesomeDropzone',
									maxFiles : 4,
									defimg : picArray, 
									removeRemotFuc:function(id,src,fun){return house_sign.removeRemotFunction(id,src,fun)},
									clickEventOk:common.getWindowsData('houseSign').flag==0?false:true
								});
								 
							}
							else
							{
								common.dropzone.init({
									id : '#myAwesomeDropzone',
									maxFiles : 4,
									clickEventOk:common.getWindowsData('houseSign').flag==0?false:true
								});
							}	
							
							if(!common.isEmpty(data.propertyPath))
							{
								var picArray2 = new Array();
								var imageHtml = '';
								for (var i = 0; i < data.propertyPath.split("|").length; i++) {
									if (i == 0) {
										picArray2.push({
											path : data.propertyPath.split("|")[i],
											first : 1
										});
									} else {
										picArray2.push({
											path : data.propertyPath.split("|")[i],
											first : 0
										});
									}
								}
								common.dropzone.init({
									id : '#propertyAttachment',
									maxFiles : 4,
									defimg : picArray2,
									removeRemotFuc:function(id,src,fun){return house_sign.removeRemotFunction(id,src,fun)},
									clickEventOk:common.getWindowsData('houseSign').flag==0?false:true
								});
							}
							else
							{
								common.dropzone.init({
									id : '#propertyAttachment',
									maxFiles : 4,
									clickEventOk:common.getWindowsData('houseSign').flag==0?false:true
								});
							}
							
							if(!common.isEmpty(data.agentPath))
							{
								var picArray3 = new Array();
								for (var i = 0; i < data.agentPath.split("|").length; i++) {
									if (i == 0) {
										picArray3.push({
											path : data.agentPath.split("|")[i],
											first : 1
										});
									} else {
										picArray3.push({
											path : data.agentPath.split("|")[i],
											first : 0
										});
									}
								}
								common.dropzone.init({
									id : '#agentAttachment',
									maxFiles : 4,
									defimg : picArray3,
									removeRemotFuc:function(id,src,fun){return house_sign.removeRemotFunction(id,src,fun)},
									clickEventOk:common.getWindowsData('houseSign').flag==0?false:true
								});
							}
							else
							{
								common.dropzone.init({
									id : '#agentAttachment',
									maxFiles : 4,
									clickEventOk:common.getWindowsData('houseSign').flag==0?false:true
								});
							}	
						}
						catch(e)
						{
							
						}
							
					}
				}
			});	
		},
		initRoomCnt:function(roomCnt)
		{
			$("#roomCnt").val(roomCnt);
			for(var i = 0; i < Math.abs(roomCnt); i++)
			{
				var contentDiv = '';
				var cnt = i;  
				var inputName = roomtotalArray[i];
				contentDiv += '<div class="panel panel-success"><div class="panel-heading" style="height: 10px;" align="center">';
				contentDiv += '<a data-toggle="collapse" data-parent="#accordion" href="#house'+cnt+'"><h4 class="panel-title" style="margin-top: -8px; color: white;">'+(i==0?'主卧':'次卧')+'房间配置</h4></a></div>';
				contentDiv += '<div class="panel-body"><div  class="tab-content">';
				contentDiv += '<div class="panel-collapse collapse '+(cnt==0?' in ':'')+'" id="house'+cnt+'"><div class="panel-body">';
				contentDiv += '<div class="form-group"><label class="col-md-3 control-label leftLabel">床及床垫: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'0"></span></label><label class="col-md-3 control-label leftLabel">单人床: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'1"></span></label><label class="col-md-3 control-label leftLabel">床头柜: <span class="noborder"><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'2"></span></label><label class="col-md-3 control-label leftLabel">衣橱: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'3"></span></label></div>';
				contentDiv += '<div class="form-group"><label class="col-md-3 control-label leftLabel">书桌: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'4"></span></label><label class="col-md-3 control-label leftLabel">空调: <span class="noborder"><input require="false" dataType="Integer2" msg="请填写数字格式" type="text" name="'+inputName+'" id="'+inputName+'5"></span></label><label class="col-md-3 control-label leftLabel">椅子: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'6"></span></label><label class="col-md-3 control-label leftLabel">窗帘: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+'" id="'+inputName+'7"></span></label></div>';
				contentDiv += '</div>';
				contentDiv += '</div>';
				contentDiv += '</div>';
				$('#chief').append(contentDiv);
			}
			$('#chief').append('客厅厨卫设施');
			var contentDiv = '';
			var inputName = 'kt';
			var i = '';
			contentDiv += '<div class="panel panel-success"><div class="panel-heading" style="height: 10px;" align="center">';
			contentDiv += '<a data-toggle="collapse" data-parent="#accordion" href="#house"><h4 class="panel-title" style="margin-top: -8px; color: white;">客厅厨卫设施</h4></a></div>';
			contentDiv += '<div class="panel-body"><div  class="tab-content">';
			contentDiv += '<div class="panel-collapse collapse in" id="house"><div class="panel-body">';
			contentDiv += '<div class="form-group"><label class="col-md-3 control-label leftLabel">油烟机: <span><input require="false" dataType="Integer2" msg="请填写数字格式" type="text" name="'+inputName+i+'" id="'+inputName+'0"></span></label><label class="col-md-3 control-label leftLabel">餐桌: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'1"></span></label><label class="col-md-3 control-label leftLabel">电视机: <span class="noborder"><input require="false" dataType="Integer2" msg="请填写数字格式" type="text" name="'+inputName+i+'" id="'+inputName+'2"></span></label><label class="col-md-3 control-label leftLabel">燃气灶: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'3"></span></label></div>';
			contentDiv += '<div class="form-group"><label class="col-md-3 control-label leftLabel">餐椅: <span><input require="false" dataType="Integer2" msg="请填写数字格式" type="text" name="'+inputName+i+'" id="'+inputName+'4"></span></label><label class="col-md-3 control-label leftLabel">电视柜: <span class="noborder"><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'5"></span></label><label class="col-md-3 control-label leftLabel">热水器: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'7"></span></label><label class="col-md-3 control-label leftLabel">沙发: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'7"></span></label></div>';
			contentDiv += '<div class="form-group"><label class="col-md-3 control-label leftLabel">电冰箱: <span><input require="false" dataType="Integer2" msg="请填写数字格式" type="text" name="'+inputName+i+'" id="'+inputName+'8"></span></label><label class="col-md-3 control-label leftLabel">微波炉: <span class="noborder"><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'9"></span></label><label class="col-md-3 control-label leftLabel">茶几: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'7"></span></label><label class="col-md-3 control-label leftLabel">洗衣机: <span><input type="text" require="false" dataType="Integer2" msg="请填写数字格式" name="'+inputName+i+'" id="'+inputName+'10"></span></label></div>';
			contentDiv += '</div>';
			contentDiv += '</div>';
			contentDiv += '</div>';
			$('#chief').append(contentDiv);
		},
		loadHouseInfo : function(id) {
			common.ajax({
				url : common.root + '/BaseHouse/loadHouseInfo.do',
				data : {
					id : id,flag:0
				},
				dataType : 'json',
				loadfun : function(isloadsucc, newData) {
					if (isloadsucc) {
						var data = newData.houseInfo;
						$('#house_name').val(data.house_name);
						$('#decorate').val(data.decorate);
						$('#area').val(data.area);
						$('#floor').val(data.floor);
						$('#group_id').val(data.group_id);
						$('#groupName').val(data.group_name);
						$('#groupAddress').val(data.address);
						$('#groupCoordinate').val(data.coordinate);
//						$('#descInfo').val(data.descInfo);
//						$('#userMobile').val(data.mobile);
//						$('#userName').val(data.username);
//						$('#value_date').val(data.value_date);
//						$('#lease_period').val(data.lease_period);
//						$('#cost_a').val(data.cost_a);
//						$('#free_rent').val(data.free_rent);
//						$('#is_cubicle').val(data.is_cubicle);
//						house_sign.editLoadMap(data.coordinate);
						 
						$('#houseType').val(data.specName);
//						$('#houseType').removeClass('divFlex');
						$.each($('.changeInput'),function()
						{ 
	//						console.log(this+i);
							$(this).parent().html('<span class="form-control">'+$(this).val()+'</span>');
						//	$(this).html('<span class="form-control">'+val+'</span>');
						});
	//					$('select').attr("disabled","disabled");
						$.each($('#decorate'),function(){
						  var $span = $(this).parent("div");
						  var selectedValue = $(this).find("option:selected").text();
						  $(this).remove(); 
						  $span.append("<span class='form-control'>"+selectedValue+"</span>");
						});
						
						if(agreementId == '')
						{
							$('#payType').val(data.payment);
							$('#effectDate').val(data.value_date);
							$('#fee_date').val(data.free_rent);
						}
						
//						$("#descInfo").replaceWith("<span class='form-control'>"+data.descInfo+"</span>");
//						$('#descInfo').attr("readonly","readonly");
						 
//						$('div').removeClass("textLeft")

					} else {
						common.alert({
							msg : common.msg.error
						});
					}
				}
			});
		},
		editLoadMap:function (coordinate){
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
		copypz:function()
		{
			var cpSpan = $('#room1').text();
			var cpInput = $('#room1').parent().find('input').val();
			console.log(cpSpan);
			console.log(cpInput);
			if(cpSpan == '')
			{
				common.alert({
					msg : '请选择第一个房间配置！',
				}); 
				return ;
			}
			for(var i = 1; i < $('.a_no_line').length; i++)
			{ 
				$('#room'+(i+1)).text(cpSpan);
				$('#room'+(i+1)).parent().find('input').val(cpInput);
				var tmp_map = new Map();
				var room_pz_map = cpInput.replace('(','');
//				console.log(room_pz_map);
				var tmp_room2 = room_pz_map.split('|');
				for(var j = 0; j < tmp_room2.length; j++)
				{
					var tmp_ = tmp_room2[j];
					if(tmp_ != '') //不为空
					{
						tmp_map.put(tmp_.split(',')[0],tmp_.split(',')[1]);
					}
				}
				roomArray[i] = tmp_map;
			}
		},
		removeRemotFunction:function(id,src,fun)
		{ 
			var filepath = common.dropzone.getFiles(id);
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
			common.alert({
				msg :'此文件删除后不能恢复，是否删除？',
				confirm : true,
				closeIcon:true,
				fun:function(action) 
				{
					if(action)
					{
						if(id.indexOf('myAwesomeDropzone') > -1)
						{
							id = 'file_path';
						}	
						else if(id.indexOf('propertyAttachment') > -1)
						{
							id = 'propertyPath';
						}	
						else
						{
							id = 'agentPath';
						}
						common.ajax({
							url : common.root + '/BaseHouse/removeRemotFile.do',
							data : {id:agreementId,src:src,type:id,filePath:path},
							dataType : 'json',
							async:false,
							loadfun :function(isloadsucc, data)
							{
								if (isloadsucc) 
								{ 
									if(data.state !=-1)
									{ 
										fun(); 
									}	
 								}
							}
						});	
					}
					else
					{
						return false;
					}	
				}
				 
			}); 
		}
};
$(function(){
	agreementId = common.getWindowsData('houseSign').agreementId;
	try
	{
		house_sign.init();
	}
	catch(e)
	{
		
	}
});