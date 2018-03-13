var agreementId = '';
var houseId = ''; // 房源编号
var roomArray = null;
var roomp = 0;
var house_agreement_info = 
{
	// 收房合约
	init:function()
	{
		$('.navdiv li').click(function()
		{
			var rl = $(this).attr('rl');
			$('.navdiv li').removeClass('active');
			$(this).addClass('active');
			$('.body .com').removeClass('active');
			$('.body .'+rl).addClass('active');
		});
		
		// 初始化日期
		var currYear = (new Date()).getFullYear();	
		var opt={};
		opt.date = {preset : 'date'};
		opt.datetime = {preset : 'datetime'};
		opt.time = {preset : 'time'};
		opt.default = {
			theme: 'android-ics light', //皮肤样式
			display: 'modal', //显示方式 
			mode: 'scroller', //日期选择模式
			dateFormat: 'yyyy-mm-dd',
			lang: 'zh',
			showNow: true,
			nowText: "今天",
			startYear: currYear - 50, //开始年份
			endYear: currYear + 10 //结束年份
		};

		$("#effectDate").mobiscroll($.extend(opt['date'], opt['default']));
		
		$('#bigBankId').change(function(){
			$('#areaList').html('');
			isLoading = false;
			page_num = 0;
			house_agreement_info.selectBankList();
		});
		$('#sreachBut').click(function(){
			$('#areaList').html('');
			isLoading = false;
			page_num = 0;
			house_agreement_info.selectBankList();
		});
		$('#select_bank .close').click(function(){
			$('#select_bank').hide();
		});
		$('#room_pz_select .close').click(function(){
			$('#room_pz_select').hide();
		});
		$('#emptyBtn').click(house_agreement_info.clearMcatePage);
		$('#okBtn').click(house_agreement_info.setMcatePage);
		house_agreement_info.loadSelect();
		agreementId = njyc.phone.queryString("agreementId");
		$('#agreementId').val(agreementId);
		houseId = njyc.phone.queryString("id");
		$('#select_bank .body').css({});
		$('#houseId').val(njyc.phone.queryString('id'));
		$('#carBank').click(function(){
			$('#areaList').html('');
			$('#bankId').val('');
			page_num = 0;
			$('#select_bank').show();
			return false;
		});
		if(agreementId == '')
		{
			$('#contractManage').val(njyc.phone.getSysInfo('userId'));
			$('#leaseTime').change(house_agreement_info.changeMonthRent);
			$('.yzInfo').html(njyc.phone.queryString('username'));
			house_agreement_info.loadBigList();
			$('.houseName').html(njyc.phone.queryString("house_name"));
			$('#submitBtn').parent().remove();
			$('#saveBtn').click(house_agreement_info.save);
			$('.houseName').html(njyc.phone.queryString("house_name"));
			$('#contractName').val(njyc.phone.queryString("house_name"));
			$('.yzInfo').html(njyc.phone.queryString('username'));
			$('#cardPressonName').val(njyc.phone.queryString('username'));
			$('#user_id').val(njyc.phone.queryString('user_id'));
			$('#user_mobile').val(njyc.phone.queryString('mobile'));
			$('.houseCode').html(njyc.phone.queryString('house_code'));
			$('#houseCode').val(njyc.phone.queryString('house_code'));
			$("#leaseTime").val(njyc.phone.queryString('lease_period'));
			$('#areaid').val(njyc.phone.queryString('areaid'));
			$('#area').html(njyc.phone.queryString('area'));
			$('#specName').html(njyc.phone.queryString('specName'));
			$('#floor').html(njyc.phone.queryString('floor'));
//			alert($('#floor').html());
			var numberReg = /^\\d+$/;
			var roomCnt = njyc.phone.queryString('roomCnt');
			roomCnt = numberReg.test(roomCnt)?0:roomCnt;
			try
			{ 
				var rentMonths = njyc.phone.queryString("cost_a").split('|');
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
			roomArray = new Array(Math.abs(roomCnt)+1);
			var div_pz = '<div id="copyDIV" style="color: #f34c4c;">一键复制</div>';
			for(var i = 0; i < Math.abs(roomCnt); i++)
			{
				div_pz += '<div><a class="a_no_line">'+(i+1)+'房间配置</a>&nbsp;&nbsp;<span id="room'+(i+1)+'"/></span><input type="hidden" data_room_pz='+i+' name="room_pz_" /></div>' ;
				roomArray[i] = new Map();
			}
			div_pz += '<div><a class="a_no_line">其他房间配置</a>&nbsp;&nbsp;<span id="room'+(Math.abs(roomCnt)+1)+'"></span><input type="hidden" data_room_pz='+Math.abs(roomCnt)+' name="room_pz_" /></div>' ;
			roomArray[Math.abs(roomCnt)] = new Map();
			$('#div_room_pz').html(div_pz);
			if($('.a_no_line').length > 2)
			{
				$('#copyDIV').click(house_agreement_info.copypz);
			}
			else
			{
				$('#copyDIV').hide();
			}
			$('.a_no_line').click(house_agreement_info.selectRoomPz);
			$('#mcate_select').change(house_agreement_info.mcateList);
	//		$('#floor').html(njyc.phone.queryString('floor'));
			$('#is_kg').val(njyc.phone.queryString('is_cubicle'));
//			$('#selectImage').click(house_agreement_info.selectImages);
			house_agreement_info.changeMonthRent();
		}
		else
		{
			// 加载合约明细
			house_agreement_info.agreementInfo();
		}	
		
	},
	loadSelect:function()
	{
		// 加载下拉框信息
		// 装饰
		njyc.phone.loadItem('DECORATE.TYPE', function(json) {
			var html = '';
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
					 + json[i].item_name + '</option>';
			}
			$('#decorate').append(html);
		},false);
		
		njyc.phone.ajax(
		{
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getMangerList.do',
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
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
		
		// 加载出租年限
		njyc.phone.loadItem('GROUP.RENT.YEAR', function(json) 
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
		njyc.phone.loadItem('GROUP.PAYTYPE', function(json)
		{
			var html = "";
			for ( var i = 0; i < json.length; i++)
			{
				html += '<option  value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#payType').append(html);
		},false);
	},
	changeMonthRent:function()
	{
		// 4年放一行，不足补空
		var data_rent =$('#leaseTime option:selected').attr("data_rent"); //需求选择的时间
		var rentMonth = $("input[name='rentMonth']"); //页面中显示的数据
		var hs = 4;
		var showL = parseInt(Math.abs(data_rent)/Math.abs(hs)); // 应该显示多少行
		var syL = data_rent % hs; // 剩余多少个输入框
		var showHtml = ''; // 显示的输入框
		for(var i = 0; i < showL; i++)
		{
			showHtml += '<div class="weui_cell"><div class="row">';
			for(var j = 1; j < Math.abs(hs+1); j++)
			{
				var tmpLength = Math.abs(i * hs) + j;
				showHtml += '<div class="common"><input type="number" dataType="Integer3"  msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" /></div>';
			}	
			showHtml += '</div></div>';
		}
		if(syL > 0)
		{
			showHtml += '<div class="weui_cell"><div class="row">';
			for(var i = 0; i < hs; i++)
			{
				var tmpLength = Math.abs(showL * hs) + i + 1 ;
				if(syL > i)
				{
					showHtml += '<div class="common"><input type="number" dataType="Integer3" msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" /></div>';
				}	
				else
				{
					showHtml += '<div class="common"><input type="number" style="opacity: 0;" disabled="disabled" dataType="Integer3" msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" /></div>';
				}
			}
			showHtml += '</div></div>';
		}
		$('#div_rent').html(showHtml);
		$.each(rentMonth, function(i,value){
			$("input[name='rentMonth']:eq("+i+")").val($(this).val());
		});
	},
	save:function()
	{
		if (!Validator.Validate('form1'))
		{
			return;
		}
		var filepath = $('#selectImage1 input[name="picImage"]');
		
		if(filepath.length==0)
		{
			njyc.phone.showShortMessage('请选择房产证附件!');
			return;
		}
		
		var filepath2 = $('#selectPropertyPath input[name="picImage"]');
		if(filepath2.length==0)
		{
			njyc.phone.showShortMessage('请选择产权人附件!');
			return;
		}
		
		$('#saveBtn').unbind("click");
		njyc.phone.showProgress(''); 
		var path = "";
		var returnI = false;
		for (var i = 0; i < filepath.length; i++) 
		{
			path += ',' + $(filepath[i]).val();
			returnI = true;
		}
		if (returnI)
		{
			path = path.substring(1);
		}
		$('#picPath').val(path);
		
		path = "";
		returnI = false;
		for (var i = 0; i < filepath2.length; i++) 
		{
			path += ',' + $(filepath2[i]).val();
			returnI = true;
		}
		if (returnI)
		{
			path = path.substring(1);
		}
		$('#propertyPath').val(path);
		
		var filepath3 = $('#selectAgentPath input[name="picImage"]');
		path = "";
		returnI = false;
		for (var i = 0; i < filepath3.length; i++) 
		{
			path += ',' + $(filepath3[i]).val();
			returnI = true;
		}
		if (returnI)
		{
			path = path.substring(1);
		}
		$('#agentPath').val(path);
		
		var ajax_option =
		{
			url:njyc.phone.getSysInfo('serverPath') + "/mobile/houseMgr/signHouse.do",//默认是form action
			success:house_agreement_info.resFunc,
			type:'post',
			dataType:'json'
		};
		$("#form1").ajaxForm(ajax_option).submit();
	},
	// 保存返回函数处理
	resFunc:function(data)
	{
		njyc.phone.closeProgress()
		$('#saveBtn').click(house_agreement_info.save);
		if(data.state==-2)
		{
			njyc.phone.showShortMessage('此房源已被签约!');
			njyc.phone.closeCallBack("refreshlList()");
		}
		else if(data.state == 1)
		{
			njyc.phone.showShortMessage('操作成功!');
			njyc.phone.closeCallBack("refreshlList()");
		}
		else if(data.state == -3)
		{
			njyc.phone.showShortMessage('图片上传失败,请重试!');
			return ;
		}
		else if(data.state == -4)
		{
			njyc.phone.showShortMessage('房间合约状态已发生改变!');
			njyc.phone.closeCallBack("refreshlList()");
		}
		else  
		{
			njyc.phone.showShortMessage('网络忙,请重试！');
			return;
		}
	 
	},
	selectImages:function(selector)
	{
		var picSize = $("#picSize").val(); // 可以上传图片数量
		var uploadPic = $('#'+selector+' input[name="picImage"]').size();
		if(Math.abs(picSize) > uploadPic)
		{
			njyc.phone.selectImage((Math.abs(picSize) - uploadPic),selector);
		}
		else
		{
			$('#'+selector).hide();
		}	
		return false;
	},
	agreementInfo:function()
	{
		// 合约明细
		//alert(agreementId);
		njyc.phone.ajax({
			url:njyc.phone.getSysInfo('serverPath')+'/mobile/houseAgreement/houseAgreementInfo.do',
			data:{id:agreementId},
			dataType: 'json',
			loadfun: function(isloadsucc, data)
			{
				if(isloadsucc)
				{
//					console.log(data);
					$('.yzInfo').html(data.username);
					$('.houseName').html(data.house_name);
					$('#user_id').val(data.user_id);
					$('#user_mobile').val(data.user_mobile);
					$('#yzInfo').text(data.username);
					$('#contractId').val(data.sbcode);
					$('#contractName').val(data.name);
					$('#contractManage').val(data.agents);
					$('#is_kg').val(data.is_cubicle);
					$('#certificateno').val(data.certificateno);
					$('#aggreenDiv').show();
					$('#agreementInput').val(data.code);
					var keys_door = data.keys_count;
					
					if(keys_door != 'null' && keys_door != '' && keys_door != null)
					{
						$('#doorkey').val(keys_door.split('|')[0]);
						$('#fooler').val(keys_door.split('|')[1]);
						$('#personCount').val(keys_door.split('|')[2]);
					}	
//					console.log(room_pz);
//					console.log(data.old_matched);
					if(data.old_matched != "null" && data.old_matched != '' && data.old_matched != null)
					{
						var room_pz = data.old_matched.split(')');
//						console.log(room_pz.length);
						var roomCnt =  room_pz.length - 2;
//						console.log(roomCnt);
						roomArray = new Array(roomCnt);
						var div_pz = '<div id="copyDIV" style="color: #f34c4c;">一键复制</div>';
						var room_pz_map = '';
						var showRoomPz = '';
						var tmp_map = new Map();
						for(var i = 0; i < roomCnt; i++)
						{
							tmp_map = new Map();
							var room_pz_map = room_pz[i].replace('(','');
//							console.log(room_pz_map);
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
						$('#div_room_pz').html(div_pz);
						if($('.a_no_line').length > 2)
						{
							$('#copyDIV').click(house_agreement_info.copypz);
						}
						else
						{
							$('#copyDIV').hide();
						}
					}
//					console.log(data);
					$('.a_no_line').click(house_agreement_info.selectRoomPz);
					$('#payType').val(data.pay_type);
					$('#fee_date').val(data.free_period);
					$('#effectDate').val(data.begin_time);
					$('#leaseTime').val(data.rentMonth);
					var rentMonths = data.cost_a.split('|');
					var tmpHtml = '';
					for(var i = 0; i < rentMonths.length-1; i++)
					{
						var tmpLength = (i+1);
						tmpHtml += '&nbsp;&nbsp;<input type="number" value="'+rentMonths[i]+'" dataType="Integer2" value msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" class="form-control"  name="rentMonth" />';
					}
					$('#div_rent').html(tmpHtml);
//					$("input[name='rentMonth']").removeClass('rent_width_5');
//					$("input[name='rentMonth']").addClass('rent_width_5');
					house_agreement_info.changeMonthRent();
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
					
					if(data.status == 0) // 待提交
					{
						$('#mcate_select').change(house_agreement_info.mcateList);
//						$('#selectImage').click(house_agreement_info.selectImages);
						$('#leaseTime').change(house_agreement_info.changeMonthRent);
						$('.common_button').append('<div class="left3"><button id="delBtn" type="button" class="">删除</button></div>');
						$('#delBtn').click(house_agreement_info.delBtn); //删除按钮
						house_agreement_info.loadBigList();
						$('#saveBtn').click(house_agreement_info.save); // 保存修改合约
						$('#submitBtn').click(house_agreement_info.submitAgreement); //提交合约
					}
					else if(data.status == 1) // 提交待审批
					{
						$('#carBank').unbind("click");
						$('#submitBtn').parent().remove();
						$('#saveBtn').click(house_agreement_info.approvalAgreement); // 审批合约
						$('#saveBtn').html('审批合约');
					}	
					else if(data.status == 2) // 已生效
					{
						$('#carBank').unbind("click");
						$('#submitBtn').parent().remove();
						$('#saveBtn').click(house_agreement_info.canlceAgreement); // 撤销合约
						$('#saveBtn').html('撤销合约');
					}
//					else if(data.status == 3) // 已失效
//					{
//						
//					}	
					else if(data.status == 4) // 撤消待审批
					{
						$('#carBank').unbind("click");
						$('#submitBtn').parent().remove();
						$('#saveBtn').click(house_agreement_info.spAgeement);
						$('#saveBtn').html('撤销审批合约');
					}	
					else if(data.status == 5) // 结束待审批
					{
						$('#carBank').unbind("click");
						$('#submitBtn').parent().remove();
						$('#saveBtn').click(house_agreement_info.overAgreement);
						$('#saveBtn').html('结束合约');
					}	
					else
					{
						$('#carBank').unbind("click");
						$('.common_button').parent().append('<div style="height: 20px;"></div>');
						$('.common_button').remove();
					}
					try
					{
//						njyc.phone.showShortMessage(data.pic_path);
						njyc.phone.showPic(data.pic_path,'selectImage1');
						njyc.phone.showPic(data.propertyPath,'selectPropertyPath');
						njyc.phone.showPic(data.agentPath,'selectAgentPath');
					}
					catch(e)
					{
						
					}
					// 0 待提交 可以修改
					if(data.status > 0)
					{
//						$('.a_no_line').unbind("click");
						$('#copyDIV').hide();
						$.each($('.inputChange2'),function()
						{
							$(this).attr("readonly","readonly");
						});
						$.each($('input[name="rentMonth"]'),function()
						{
							$(this).attr("readonly","readonly"); 
						});
						$("#descInfo").replaceWith("<span class='form-control'>"+data.desc_text+"</span>");
						$('select').attr('disabled','disabled');
						$('.file').hide();
						$('.item_close').hide();
					}
					else
					{
						$('#saveBtn').click(house_agreement_info.save);
					}
					
//					$('#carBank').unbind("click");
					$('.common_button').parent().append('<div style="height: 20px;"></div>');
					$('.common_button').remove();
				}
			}
		});	
	},
	selectRoomPz:function()
	{
		var roomTh = $(this).parent().find('input').attr('data_room_pz'); //选择第几个房间配置
		roomp = roomTh;
		$("#pzList").html('');
		$("#mcate_select").html('<option value="-1">请选择</option>');
		$('#room_pz_select').show();
		
		var fatherid="0";
		njyc.phone.ajax({
				url:njyc.phone.getSysInfo('serverPath')+'/sys/queryMcate.do',
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
					            house_agreement_info.mcateList();
						}else{
//							common.alert({msg:common.msg.error});
//							console.error(data);
						}
					}else{
//						common.alert({msg:common.msg.error});
//						console.error(data);
					}
				}
			});
	},
	//确定事件
	setMcatePage:function()  {
		var cateMap = roomArray[roomp];
		var cateArray = cateMap.keySet();
//		console.log(cateMap);
//		console.log(cateArray);
		$("#room" + (Math.abs(roomp)+1)).html('');
		var cateHtml = '';
		var inputHtml = '';
		inputHtml += '(' ;
		for ( var i = 0; i < cateArray.length; i++) 
		{
			var kstr = cateArray[i];
//			console.log("cc:"+cateMap.get(kstr));
			if (cateMap.get(kstr) != null && cateMap.get(kstr) != "") 
			{
				inputHtml += kstr + ',' + cateMap.get(kstr) + '|';
//				console.log("dd:"+kstr);
//				console.log(cateMap.get(kstr));
				cateHtml += cateMap.get(kstr);
			}
		}
		inputHtml += ')' ;
//		console.log(cateHtml);
//		console.log(roomp);
		$("#room" + (Math.abs(roomp)+1)).html(cateHtml);
		$("#room" + (Math.abs(roomp)+1)).parent().find('input[name="room_pz_"]').val(inputHtml);
		$('#room_pz_select').hide();
	},
	//点击增加
	admcmcate:function(aid, aname){
		var cateMap = roomArray[roomp];
		var ccvalue = parseInt($("#cc" + aid).val());
		$("#cc" + aid).val(ccvalue + 1);
		cateMap.put(aid, aname + '=' + (ccvalue + 1));
//		console.log(cateMap);
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
		house_agreement_info.mcateList();
	},
	mcateList:function(){
//		console.log(roomp);
		var fatherids="";
		if ($('#mcate_select').val()!=null&&$('#mcate_select').val()!=""&&$('#mcate_select').val()!=-1) {
			fatherids=$('#mcate_select').val();
		}
		var name=$('#exampleInputEmail2').val();
		njyc.phone.ajax({
			url:njyc.phone.getSysInfo('serverPath')+'/sys/queryMcate.do',
			data:{fatherid:fatherids,mcatename:name},
			dataType: 'json',
			loadfun: function(isloadsucc, data){
				if(isloadsucc){
					if(data.state == 1){
						 var html = "<table>";
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
				 				html += '<tr style="text-align: center;"> <td style="">'
				 					+ data.list[i].name
									+ '</td><td width="80%"><form style="margin-top: 1px;margin-buttom:1px;"><div style="display: flex;display: -webkit-box; display: -webkit-inline-box;margin-left: 10px;">'
									+ '<button type="button" style="padding: 3px 10px;" onClick="house_agreement_info.admcmcate(\''
									+ data.list[i].id
									+ '\',\''
									+ data.list[i].name
									+ '\')">'
									+ '<b style="font-size:14px;">+</b></button>'
									+ '&nbsp;<input type="number" style="text-align: center;border-top: 0px; border-left: 0px; border-right: 0px;" id="cc'
									+ data.list[i].id
									+ '" placeholder="0" readonly="readonly" value='
									+ cValue
									+ ' />'
									+ '&nbsp;<button type="button" style="padding: 3px 11px;" onClick="house_agreement_info.mimcmcate(\''
									+ data.list[i].id
									+ '\',\''
									+ data.list[i].name
									+ '\')">'
									+ '<b style="font-size:18px;">-</b></button></div></form></td></tr>';
				            }
//				            $("#tbody_mcate")[0].innerHTML=html; 
				            $('#pzList').html(html+'</table>');
//				            $("#totalCount").text('共'+cnt+'条记录');
					}else{
//						common.alert({msg:common.msg.error});
//						console.error(data);
					}
				}else{
//					common.alert({msg:common.msg.error});
//					console.error(data);
				}
			}
		});	
	},
	delBtn:function()
	{
		window.wxc.xcConfirm('确定删除此合约？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
			njyc.phone.showProgress('');
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/delAgreement.do',
				data : {id:agreementId,houseId:houseId},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					njyc.phone.closeProgress()
					if (isloadsucc) {
						if (data.state == '1') {	
							njyc.phone.showShortMessage('删除成功!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -2){
							njyc.phone.showShortMessage('合约状态发生改变,请重新确认!');
							njyc.phone.closeCallBack("refreshlList();");
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');

						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		}});
	},
	spAgeement:function()
	{
		//审批合约
		window.wxc.xcConfirm('是否将此合约置为无效？', window.wxc.xcConfirm.typeEnum.confirm,{okTitle:'通过',cancleTitle:'拒绝',title:'温馨提示',onOk:function(){
			house_agreement_info.spAgeementFunc(3);
		},
		onCancel:function()
		{
			house_agreement_info.spAgeementFunc(2);
		}});
	},
	spAgeementFunc:function(isPass)
	{
		njyc.phone.showProgress('');
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/spAgeement.do',
			data : {
				id : agreementId,
				isPass:isPass,
				houseId:houseId
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.state == '1') {
						njyc.phone.showShortMessage('操作成功!');
						njyc.phone.closeCallBack("refreshlList();");
					} else if(data.state == -2){
						njyc.phone.showShortMessage('房源状态已经改变，请稍候操作!');
						njyc.phone.closeCallBack("refreshlList();");
					} else if(data.state == -10){
						njyc.phone.showShortMessage('工程已经开工,不能撤销!');
						njyc.phone.closeCallBack("refreshlList();");
					}else if(data.state == -12){
						njyc.phone.showShortMessage('未查询到财务数据!');
						njyc.phone.closeCallBack("refreshlList();");
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				} else {
					njyc.phone.showShortMessage('网络忙,请稍候重试!');
				}
				njyc.phone.closeProgress()
			}
		});
	},
	approvalAgreement:function()
	{
		window.wxc.xcConfirm('此合约是否审核通过？', window.wxc.xcConfirm.typeEnum.confirm,{okTitle:'通过',cancleTitle:'拒绝',title:'温馨提示',onOk:function(){
				house_agreement_info.approvalAgreementFuc(2);
			},
			onCancel:function()
			{
				house_agreement_info.approvalAgreementFuc(0);
			}});
	},
	approvalAgreementFuc:function(isPass)
	{
		njyc.phone.showProgress('');
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/approvalAgreement.do',
			data : {
				id : agreementId,
				isPass:isPass,
				houseId:houseId
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				njyc.phone.closeProgress()
				if (isloadsucc) {
					if (data.state == '1') {
						njyc.phone.showShortMessage('操作成功!');
						njyc.phone.closeCallBack("refreshlList();");
					} else if(data.state == -2){
						njyc.phone.showShortMessage('房源状态已经改变，请稍候操作!');
						njyc.phone.closeCallBack("refreshlList();");
					} else if(data.state == -12){
						njyc.phone.showShortMessage('未找到财务数据!');
						njyc.phone.closeCallBack("refreshlList();");
					} else if(data.state == -10){
						njyc.phone.showShortMessage('开工失败!');
						njyc.phone.closeCallBack("refreshlList();");
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试');
					}
				} else {
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
	},
	canlceAgreement:function()
	{
		window.wxc.xcConfirm('您确定撤销此合约？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
			njyc.phone.showProgress('');
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/cancelAgreement.do',
				data : {id : agreementId,isPass:4,housid:houseId},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) 
					{
						njyc.phone.closeProgress();
						if (data.state == '1') 
						{
							njyc.phone.showShortMessage('操作成功');
							njyc.phone.closeCallBack("refreshlList();");
						} 
						else if(data.state == -2)
						{
							njyc.phone.showShortMessage('房源状态审批!');
							njyc.phone.closeCallBack("refreshlList();");
						}
						else if(data.state == -11)
						{
							njyc.phone.showShortMessage('房源已开工无法撤销!');
							njyc.phone.closeCallBack("refreshlList();");
						}
						else
						{
							njyc.phone.showShortMessage('网络忙,请稍候重试');
						}
					} 
					else
					{
						njyc.phone.showShortMessage('网络忙,请稍候重试');
					}
				}
			});
		}});
	},
	submitAgreement:function()
	{
		// 提交合约
		window.wxc.xcConfirm('确定提交合约吗？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
			njyc.phone.showProgress('');
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/submitAgreement.do',
				data : {id : agreementId},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) 
					{
						njyc.phone.closeProgress();
						if (data.state == '1') 
						{
							njyc.phone.showShortMessage('操作成功');
							njyc.phone.closeCallBack("refreshlList();");
						} 
						else if(data.state == -2)
						{
							njyc.phone.showShortMessage('合约状态已经改变,不能提交合约!');
							njyc.phone.closeCallBack("refreshlList();");
						}
						else
						{
							njyc.phone.showShortMessage('网络忙,请稍候重试');
						}
					} 
					else
					{
						njyc.phone.showShortMessage('网络忙,请稍候重试');
					}
				}
			});
		}});
	},
	overAgreement:function()
	{
		// 合约到期结束合约
		window.wxc.xcConfirm('确定结束房源合约吗？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
			njyc.phone.showProgress('');
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/overHouseAgreement.do',
				data : {id : agreementId,houseId:houseId},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) 
					{
						njyc.phone.closeProgress();
						if (data.state == '1') 
						{
							njyc.phone.showShortMessage('操作成功');
							njyc.phone.closeCallBack("refreshlList();");
						} 
						else
						{
							njyc.phone.showShortMessage('网络忙,请稍候重试');
						}
					} 
					else
					{
						njyc.phone.showShortMessage('网络忙,请稍候重试');
					}
				}
			});
		}});
	},
	selectBankList:function()
	{
		if(isLoading)
		{
			return ;
		}
		njyc.phone.showProgress('');
		var pageSize = 25;
		// 加载小区信息
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/getBankList.do',
			data : {group_name : $('#keyWord').val(),bankId:$('#bigBankId').val(),pageSize:pageSize,pageNumber:page_num},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					var html = '';
					$('.loadMore').remove();
					for(var i = 0; i < data.length; i++)
					{
						var json = data[i];
//						console.log(json);
						html+='<div class="arearDiv" onclick="house_agreement_info.selectBankID(\''+json.name+'\',\''+json.code+'\')">'+json.name+'</div>';
					}
					$('#areaList').append(html);
					if(data.length >= pageSize)
					{
						$('#areaList').append('<div class="loadMore">加载更多</div>');
					}
				} else {
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
		page_num++;
		isLoading = false;
		njyc.phone.closeProgress()
	},
	loadBigList:function()
	{
		// 加载银行大类
		njyc.phone.ajax({
			url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseAgreement/getBankList.do',
			data : {bankId:-1,pageSize:10000,pageNumber:0},
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc)
				{
					var html = '';
					for(var i = 0; i < data.length; i++)
					{
						var json = data[i];
						html+='<option value="'+json.id+'">'+json.name+'</div>';
					}
					$('#bigBankId').append(html);
				}
				else
				{
					njyc.phone.showShortMessage('网络忙,请稍候重试');
				}
			}
		});
	},
	selectBankID:function(name,id)
	{
		page_num = 0;
		$('#select_bank').hide();
		$('#carBank').val(name); // 
		$('#bankId').val(id); //
		return false;
	},
	copypz:function()
	{
		var cpSpan = $('#room1').text();
		var cpInput = $('#room1').parent().find('input').val();
//		console.log(cpSpan);
//		console.log(cpInput);
		if(cpSpan == '')
		{ 
			njyc.phone.showShortMessage('请选择第一个房间配置!');
			return ;
		}
		for(var i = 1; i < $('.a_no_line').length; i++)
		{ 
			$('#room'+(i+1)).text(cpSpan);
			$('#room'+(i+1)).parent().find('input').val(cpInput);
			var tmp_map = new Map();
			var room_pz_map = cpInput.replace('(','');
//			console.log(room_pz_map);
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
	}
};
$(document).ready(function(){
	house_agreement_info.init();
	$(document).on('click','.loadMore' ,house_agreement_info.selectBankList);
});
