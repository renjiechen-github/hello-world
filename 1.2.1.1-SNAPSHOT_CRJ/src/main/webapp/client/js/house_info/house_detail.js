 var page_num = 0;
 var isLoading = false;
 var id = ''; // 房源id
 var username = ''; // 户主姓名
 var house_name = ''; // 房源名称
 var roomCnt = ''; //房间数
 var is_cubicle = ''; //是否可割
 var house_code = ''; //房源编码
 var free_rent = ''; // 免租期
 var areaid = ''; // 小区
 var cost_a = ''; // 花费
 var lease_period = ''; //免租期
 var area = '';// 房间面积
 var mobile = ''; // 手机号码
 var user_id = '';// 用户id 
var house_detail = 
{ 
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
			// 导航
			$('.navMap').click(function(){
				var groupAddress = $('#groupAddress').val();
				var groupCoordinate = $('#groupCoordinate').val();
				if(common.isEmpty(groupAddress) || common.isEmpty(groupCoordinate))
				{
					njyc.phone.showShortMessage('请在地图中选择导航地址');
					return ;
				}
				if(groupCoordinate.indexOf(',') == -1)
				{
					njyc.phone.showShortMessage('请选择导航地址信息');
					return ;
				}
				var newCoordinate = groupCoordinate.split(',')[1]+','+groupCoordinate.split(',')[0];
//				njyc.phone.showShortMessage("latlng:"+newCoordinate+":"+groupAddress);
				njyc.phone.navigationMap({dist:'latlng:'+newCoordinate+'|name:'+groupAddress});
			})
			house_detail.loadSelect(); // 加载下拉框信息
			$('.close').click(function(){
				$('.divCenter').hide(); 
			});
//			$('#refreshDiv').click(function(){
//				njyc.phone.getLocation();
//			});
			id = njyc.phone.queryString("id"); // house_id
			if(id == '')
			{
				house_detail.loadMap(); 
				$('.common_button .left3').remove();
				house_detail.loadArearList();
				$('#myTab:not(.active)').remove(); 
				$('#form2 .header').remove();
				$('#form2 .body').css({"padding-top":"0px"});
				// 新增房源信息
				$('#groupName').click(function(){
					$('#areaList').html('');
					$('#groupAreaid').val('');
					page_num = 0;
					$('.divCenter').show();
					house_detail.selectAearInfo();
					return false;
				});
				$('#groupAreaid').change(function(){
					$('#areaList').html('');
					isLoading = false;
					page_num = 0;
					house_detail.selectAearInfo();
				});
				$('#sreachBut').click(function(){
					$('#areaList').html('');
					isLoading = false;
					page_num = 0;
					house_detail.selectAearInfo();
				});
				// 绑定新增按钮
				$('#saveBtn').click(house_detail.save);
				$('#selectImage').click(house_detail.selectImages); 
				$('.common_button').parent().append('<div style="height: 20px;"></div>');
				$('.common_button').remove();
			}
			else
			{
				$('#mark').hide();
				$('#groupName').click(function()
				{
					$('.divCenter').hide();
					return false;
				});
				house_detail.loadHouseInfo(id);
			}
			
			$('#userMobile').blur(house_detail.getUserName);
			house_detail.changeMonthRent();
			$('#lease_period').change(house_detail.changeMonthRent);
		},
		loadHouseInfo:function(id)
		{
			// 加载房源信息
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/loadHouseInfo.do',
				data : {id : id},
				dataType : 'json',
				loadfun : function(isloadsucc, newdata)
				{
					if (isloadsucc) {
						console.log(newdata)
						var data = newdata.houseInfo;
						if(data.house_status < 6 )
						{
							$('#myTab:not(.active)').remove(); 
							$('.body').css({"padding-top":"0px"});
							$('#form2 .header').remove();
						}
 						if(data.house_status > 2)
						{
							$('#agreementInfoDiv').show(); 
							// 展示合约信息 
							var agreementInfo = newdata.agreementInfo;
//							console.log(agreementInfo);
							$("#agreementId").val(agreementInfo.id);
							$('#agreementCode').text(agreementInfo.code);
							$('#agreementName').text(agreementInfo.name);
							$('#owerInfo').val(agreementInfo.user_info);
							$('#agentsID').val(agreementInfo.agents);
							$('#agreementStatus').text(agreementInfo.status);
							$('#agreement_zj').val(agreementInfo.cost_a);
							$('#cz_time').val(agreementInfo.cz_time);
							if(data.house_status > 6)
							{
								// 已经开工加载显示，可以点击查看工程信息
								$('#constructionDiv').show();
								$('#constructionDiv').click(function(){
									njyc.phone.openWebKit({title:'工程信息',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/construction_detail.html?agreementId='+agreementInfo.id});
								})
							}
						}
 						if(data.house_status > 4)
 						{
 							// 可以发布房源
 							house_detail.publishHouse();
 						}	
						$('#house_name').val(data.house_name);
						$('#shi').val(data.spec.split('|')[0]);
						$('#ting').val(data.spec.split('|')[1]);
						$('#wei').val(data.spec.split('|')[2]);
						$('#decorate').val(data.decorate);
						$('#area').val(data.area);
						$('#house_type').val(data.house_type);
						$('#floor').val(data.floor);
						$('#group_id').val(data.group_id);
						$('#groupName p').html(data.group_name);
						$('#groupAddress').val(data.address);
						$('#groupCoordinate').val(data.coordinate);
						house_detail.editLoadMap(data.coordinate);
						$('#descInfo').val(data.descInfo);
						$('#userMobile').val(data.mobile);
						$('#userName').val(data.username);
//						$('#value_date').val(data.value_date);
						$('#lease_period').val(data.lease_period);
						njyc.phone.showPic(data.appendix);
//						$('#cost_a').val(data.cost_a);
						try
						{
							var rentMonths = data.cost_a.split('|');
							var tmpHtml = '';
							for(var i = 0; i < rentMonths.length-1; i++)
							{
								var tmpLength = (i+1);
								tmpHtml += '<input type="number" value="'+rentMonths[i]+'" dataType="Integer3" value msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租"  name="rentMonth" />';
							}
							$('#div_rent').html(tmpHtml);
//							$("input[name='rentMonth']").removeClass('rent_width_5');
//							$("input[name='rentMonth']").addClass('rent_width_5');
						}
						catch(e)
						{
							
						}
						house_detail.changeMonthRent();
						$('#payment').val(data.payment);
						$('#free_rent').val(data.free_rent);
						$('#is_cubicle').val(data.is_cubicle);
						$('#userName').val()?$('#userName').attr("readonly","readonly"):'';
						$('#houseCodeDiv').show();
						$('#house_code_span').text(data.house_code);
						if(data.house_status > 0) // 0 房源处于待提交 可以编辑
						{
							$('#refreshDiv').hide();
							$.each($('.changeInput'),function()
							{
								$(this).attr("readonly","readonly");
							});
							$('select').attr('disabled','disabled');
							$.each($('input[name="rentMonth"]'),function()
							{
								$(this).attr("readonly","readonly");
							});
//							$('#div_rent[name="rentMonth"]').attr("readonly","readonly");
							$("#descInfo").replaceWith("<span class='form-control'>"+data.descInfo+"</span>");
							$('#descInfo').attr("readonly","readonly");
							$('#selectImage').hide();
							$('.item_close').hide();
							
							if(data.house_status == 1) //房源待审批
							{
								$('.common_button .left3').remove();
								$('#saveBtn').text('审批房源');
								$('#saveBtn').click(house_detail.approvalHouse); // 审批房源
							}
							else if(data.house_status == 2) // 待签约
							{
								username = njyc.phone.queryString("username");
								house_name = njyc.phone.queryString("house_name");
								roomCnt = njyc.phone.queryString("roomCnt");
								is_cubicle = njyc.phone.queryString("is_cubicle");
								house_code = njyc.phone.queryString("house_code");
								free_rent = njyc.phone.queryString("free_rent");
								areaid = njyc.phone.queryString("areaid");
								cost_a = njyc.phone.queryString("cost_a");
								lease_period = njyc.phone.queryString("lease_period");
								area = njyc.phone.queryString("area"); 
								mobile = njyc.phone.queryString("mobile");
								user_id = njyc.phone.queryString("user_id");
								$('.common_button .left3').remove();
								$('#saveBtn').text('房源签约');
								$('#saveBtn').click(house_detail.signHouse); // 待签约
							}
							else if(data.house_status == 5) // 施工中
							{
								// 已经发布
								if (data.publish==2) 
								{
									$('.common_button .left3').remove();
									$('#saveBtn').text('撤销发布');
									$('#saveBtn').click(house_detail.soldOut); // 撤销发布
								}
								else if(data.publish==0)
								{
									// 没有发布
									$('#saveBtn').text('发布房源');
									$('#saveBtn').click(house_detail.houseRelease); // 发布房源
									$('#submitBtn').text('编辑房源');
									$('#submitBtn').click(house_detail.editHouseInfo); // 编辑房源
								}
								else
								{
									// 发布待审批
									$('.common_button .left3').remove();
									$('#saveBtn').text('发布房源审批');
									$('#saveBtn').click(function(){
										house_detail.approvalPublish(data.monery);
									}); // 发布房源审批
								}
							}
							else if(data.house_status == 6)
							{
								// 交接房源
								if (data.publish==2) 
								{
									$('#saveBtn').text('撤销发布');
									$('#saveBtn').click(house_detail.soldOut); // 撤销发布
								}
								else if(data.publish==0)
								{
									// 没有发布
									$('#saveBtn').text('发布房源');
									$('#saveBtn').click(house_detail.houseRelease); // 发布房源
								}
								else
								{
									// 发布待审批
									$('#saveBtn').text('发布房源审批');
									$('#saveBtn').click(function(){
										house_detail.approvalPublish(data.monery);
									}); // 发布房源审批
								}
								$('#submitBtn').text('交接房源');
								$('#submitBtn').click(house_detail.houseTransfer);
							}
							else if(data.house_status == 9)
							{
								$('.common_button .left3').remove();
								// 交接房源
								if (data.publish==2) 
								{
									$('#saveBtn').text('撤销发布');
									$('#saveBtn').click(house_detail.soldOut); // 撤销发布
								}
								else if(data.publish==0)
								{
									// 没有发布
									$('#saveBtn').text('发布房源');
									$('#saveBtn').click(house_detail.houseRelease); // 发布房源
								}
								else
								{
									// 发布待审批
									$('#saveBtn').text('发布房源审批');
									$('#saveBtn').click(function(){
										house_detail.approvalPublish(data.monery);
									}); // 发布房源审批
								}
								$('#submitBtn').text('交接房源');
								$('#submitBtn').click(house_detail.houseTransfer);
								$('.common_button').append('<div class="left3"><button id="isTop" type="button" class="">保存</button></div>')
								if(data.is_top==1) 
								{
									// 精品下架
									$('#isTop').text('取消精品');
									$('#isTop').click(function(){
										house_detail.istop(0);
									});
								}	
								else
								{
									// 上架
									$('#isTop').text('设为精品');
									$('#isTop').click(function(){
										house_detail.istop(1);
									});
								}	
							}	
							else if(data.house_status == 10)
							{
								$('.common_button .left3').remove();
								$('#saveBtn').text('删除');
								$('#saveBtn').click(house_detail.delBtn); // 删除房源
							}
							else
							{
								$('.common_button').parent().append("<div style='height: 30px;'></div>");
								$('.common_button').remove();
							}
						}
						else
						{
							 
							$('.common_button').append('<div class="left4"><button id="delBtn" type="button" class="">删除</button></div>');
							$('#delBtn').click(house_detail.delBtn);
							$('#submitBtn').click(house_detail.submitHouse); // 提交房源信息
							$('#selectImage').click(house_detail.selectImages); // 选择图片
							$('#saveBtn').click(house_detail.save); // 保存信息
						}
						$('.common_button').parent().append('<div style="height: 20px;"></div>');
						$('.common_button').remove();
						//展示合约信息
					} else {
						 
					}
				}
			});
		},
		loadArearList:function()
		{
			njyc.phone.loadArea('101', '3', function(json) {
				var html = "";
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].id + '" >'
							+ json[i].area_name + '</option>';
				}
				$('#groupAreaid').append(html);
			});
		},
		loadSelect:function()
		{
			njyc.phone.loadItem('GROUP.SPEC', function(json) {
				var html = "<option value=''>室</option>";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#shi').append(html);
			},false);
			njyc.phone.loadItem('HOUSE.TYPE', function(json) {
				var html = "";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
					+ json[i].item_name + '</option>';
				}
				$('#house_type').append(html);
			},false);
			njyc.phone.loadItem('GROUP.TING', function(json) {
				var html = "<option value=''>厅</option>";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#ting').append(html);
			},false);
			njyc.phone.loadItem('GROUP.PAYTYPE', function(json) 
			{
				var html = "";
				for ( var i = 0; i < json.length; i++) 
				{
					html += '<option data_rent="'+json[i].item_value+'" value="' + json[i].item_id + '" >'
					+ json[i].item_name + '</option>';
				}
				$('#payment').append(html);
			},false);
			njyc.phone.loadItem('GROUP.WEI', function(json) {
				var html = "<option value=''>卫</option>";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#wei').append(html);
			},false);
			njyc.phone.loadItem('DECORATE.TYPE', function(json) {
				var html = "<option value=''>装饰</option>";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#decorate').append(html);
			},false);
			// 加载出租年限
			njyc.phone.loadItem('GROUP.RENT.YEAR', function(json) 
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
		getUserName:function()
		{
			//加载用户姓名
			var mobile = $('#userMobile').val();
			var reginx = /^1(3|4|5|7|8)[0-9]{9}$/;
			if(!reginx.test(mobile))
			{
				njyc.phone.showShortMessage('请输入11位数字格式的手机号码!');
				$('#userMobile').removeAttr("readonly","readonly");
				return ;
			}
//			请输入11位数字格式的手机号码
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getUserName.do',
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
							njyc.phone.showShortMessage('号码重复,请联系宗培新删除数据!');
						}	
					} else {
						 
					}
				}
			});
		},
		changeMonthRent:function()
		{
			// 4年放一行，不足补空
//			console.log($('#leaseTime option:selected').attr("data_rent"));
			var data_rent =$('#lease_period option:selected').attr("data_rent"); //需求选择的时间
			if(common.isEmpty(data_rent))
			{
				data_rent = 3;
			}
			var rentMonth = $("input[name='rentMonth']"); //页面中显示的数据
			var hs = 4;
			var showL = parseInt(Math.abs(data_rent)/Math.abs(hs)); // 应该显示多少行
			var syL = data_rent % hs; // 剩余多少个输入框
			var showHtml = ''; // 显示的输入框
			for(var i = 0; i < showL; i++)
			{
				showHtml += '<div class="weui_cells1" id="div_rent">';
				for(var j = 1; j < Math.abs(hs+1); j++)
				{
					var tmpLength = Math.abs(i * hs) + j;
					showHtml += ' <div class="weui_cell_ft3 right"><input type="number" dataType="Integer3"  msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" name="rentMonth" /></div>';
				}	
				showHtml += '</div>';
			}
			if(syL > 0)
			{
				showHtml += '<div class="weui_cells1" id="div_rent">';
				for(var i = 0; i < hs; i++)
				{
					var tmpLength = Math.abs(showL * hs) + i + 1 ;
					if(syL > i)
					{
						showHtml += '<div class="weui_cell_ft3 right"><input type="number" dataType="Integer3" msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租"  name="rentMonth" /></div>';
					}	
					else
					{
						showHtml += '<div class="weui_cell_ft3 right"><input type="number" style="opacity: 0;" disabled="disabled" dataType="Integer3" msg="请输入第'+tmpLength+'年房租！" maxlength="10" placeholder="第'+tmpLength+'年房租" name="rentMonth" /></div>';
					}
				}
				showHtml += '</div>';
			}
			
			$('#div_rent').html(showHtml);
			$.each(rentMonth, function(i,value){
				$("input[name='rentMonth']:eq("+i+")").val($(this).val());
			});
//			$("input[name='rentMonth']").removeClass('rent_width_2');
//			$("input[name='rentMonth']").addClass('rent_width_2');
		},
//		getUserName:function()
//		{
//			var mobile = $('#userMobile').val();
//			if(mobile == '' || mobile.length != 11)
//			{
////				$('#userName').attr("readonly","readonly");
//				$('#userName').removeAttr("readonly","readonly");
//				return ;
//			}
//			njyc.phone.ajax({
//				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getUserName.do',
//				data : {userMobile : mobile},
//				dataType : 'json',
//				loadfun : function(isloadsucc, data) {
//					if (isloadsucc) {
//						var name = data.username;
//						$('#userName').val(name);
//						if(name != null && name != '' && name != "null")
//						{
//							$('#userName').attr("readonly","readonly");
//						}
//						else
//						{
//							$('#userName').removeAttr("readonly","readonly");
//						}
//					} else {
////						njyc.phone.showShortMessage('网络忙,请稍候重试');
//					}
//				}
//			});
//		},
		save:function()
		{
			if (!Validator.Validate('form2'))
			{
				return;
			}
			var path  = '';
			var picImage = $('input[name="picImage"]');
			if(picImage.length == 0)
			{
				njyc.phone.showShortMessage('请上传图片');
				return;
			}
			$('#saveBtn').unbind("click");
			njyc.phone.showProgress('');
			for(var i = 0; i < picImage.length; i++)
			{
				path+=',' + $(picImage[i]).val();
			}
			if(path != '')
			{
				path = path.substring(1);
			}
			var rentMonths = '';
			$.each($('input[name="rentMonth"]'),function(){
			//	console.log($(this).attr('disabled'));
				if($(this).val() && !$(this).attr('disabled'))
				{
					rentMonths += $(this).val()+'|';
				}
			});
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/saveHouse.do',
				data : {
					house_name : $('#house_name').val(),
					shi : $('#shi').val(),
					ting : $('#ting').val(),
					wei : $('#wei').val(),
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
					regionId : $('#regionId').val(),
					lease_period : $('#lease_period').val(),
					cost_a : rentMonths,
					payment : $('#payment').val(),
					free_rent : $('#free_rent').val(),
					is_cubicle : $('#is_cubicle').val(),
					house_type:$('#house_type').val(),
					id : id
				},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					$('#saveBtn').click(house_detail.save);
					if (isloadsucc) {
						if (data.state == 1) {// 操作成功
							njyc.phone.showShortMessage('操作成功');
							njyc.phone.closeCallBack("refreshlList();");
						} else if (data.state == -3) {
							njyc.phone.showShortMessage('文件上传失败,请重试');
							return;
						} else if (data.state == -4) {
							njyc.phone.showShortMessage('房源名称已经存在!');
//							njyc.phone.closeCallBack("refreshlList();");
							return;
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试');
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试');
					}
					njyc.phone.closeProgress();
				}
			});
			
		},
		selectAearInfo:function()
		{
			if(isLoading)
			{
				return ;
			}
			njyc.phone.showProgress('');
			var pageSize = 25;
			// 加载小区信息
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/loadAearList.do',
				data : {keyword : $('#keyWord').val(),areaid:$('#groupAreaid').val(),pageSize:pageSize,pageNumber:page_num},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						var html = '';
						$('.loadMore').remove();
						for(var i = 0; i < data.length; i++)
						{
							var json = data[i];
//							console.log(json);
							html+='<div class="arearDiv" onclick="house_detail.selectAearID(\''+json.group_name+'\',\''+json.id+'\',\''+json.zone+'\',\''+json.coordinate+'\',\''+json.address+'\')">'+json.group_name+'</div>';
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
		selectAearID:function(areaName,id,areaId,coordinate,address)
		{
			$('.divCenter').hide();
			$('#groupName p').html(areaName); // 小区名称
			$('#regionId').val(areaId); // 区域id
			$('#group_id').val(id); //小区id
			$('#house_name').val(areaName); //小区id
			$('#groupAddress').val(address);
			if(!common.isEmpty(coordinate) && coordinate.indexOf(',') != -1)
			{
				$('#groupCoordinate').val(coordinate);
			}
			house_detail.editLoadMap(coordinate);
		},
		selectImages:function()
		{
			var picSize = $("#picSize").val(); // 可以上传图片数量
			var uploadPic = $('input[name="picImage"]').size();
			if(Math.abs(picSize) > uploadPic)
			{
				njyc.phone.selectImage((Math.abs(picSize) - uploadPic));
			}
			else
			{
				$('#selectImage').hide();
			}	
			return false;
		},
		submitHouse:function()
		{
			//提交房源
			var txt=  "提交房源?";
			window.wxc.xcConfirm(txt, window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/submitHouse.do',
					data : {
						id : id
					},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								njyc.phone.showShortMessage('操作成功!');
								njyc.phone.closeCallBack("refreshlList();");
							} else if(data.state == -2){
								njyc.phone.showShortMessage('房源已被提交,不能更改房源状态!');
								njyc.phone.closeCallBack("refreshlList();");
							} else if(data.state == -3){
								njyc.phone.showShortMessage('房源已被提交,不能更改房源状态!');
								njyc.phone.closeCallBack("refreshlList();");
							} else {
								njyc.phone.showShortMessage('网络忙,请稍候重试');
							}
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试');
						}
					}
				});
			}});
		},
		// 审批房源
		approvalHouse:function(){
			window.wxc.xcConfirm('此房源是否审核通过？', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',okTitle:'通过',cancleTitle:'拒绝',onOk:function(){
				 	isPass = 2;
				 	house_detail.approvalHouseFunction(isPass);
				},onCancel:function(){
					isPass = 0;
					house_detail.approvalHouseFunction(isPass);
				}
			});
		},
		approvalHouseFunction:function(isPass)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/approvalHouse.do',
				data : {
					id : id,
					isPass:isPass
				},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -2){
							njyc.phone.showShortMessage('房源状态已经改变，暂不能审核!');
							njyc.phone.closeCallBack("refreshlList();");
						} else {
							njyc.phone.showShortMessage('房源状态已经改变，请稍候操作!');
							njyc.phone.closeCallBack("refreshlList();");
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		delBtn:function()
		{
			window.wxc.xcConfirm('是否删除房源信息？', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function(){
				// 删除合约信息
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/deleteHouse.do',
					data : {
						id : id
					},
					
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								njyc.phone.showShortMessage('删除成功!');
								njyc.phone.closeCallBack("refreshlList();");
							} else if(data.state == -2){
								njyc.phone.showShortMessage('房源状态发生改变,请重新确认!');
								njyc.phone.closeCallBack("refreshlList();");
							} else if(data.state == -3){
								njyc.phone.showShortMessage('房源已经被签约,不能删除!');
								njyc.phone.closeCallBack("refreshlList();");
							} else {
								njyc.phone.showShortMessage('网络忙,请稍候重试!');
							}
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');
						}
					}
				});
				}
			});
		},
		signHouse:function()
		{
			// 房源签约
			njyc.phone.ajax(
			{
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/checkHouseStatus.do',
				data : {id : id},
				dataType : 'json', 
				loadfun : function(isloadsucc, data) 
				{
					if (isloadsucc) 
					{
						if (data.state == '1')
						{
							njyc.phone.openWebKit({title:'房源签约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/wt_contract_detail.html?id='+id+'&username='+encodeURI(encodeURI(username))+'&house_name='+encodeURI(encodeURI(house_name))+'&roomCnt='+roomCnt+'&is_cubicle='+is_cubicle+'&house_code='+house_code+'&free_rent='+free_rent+'&areaid='+areaid+'&cost_a='+cost_a+'&lease_period='+lease_period+'&area='+area+'&mobile='+mobile+'&user_id='+user_id});
						}
						else
						{
							njyc.phone.showShortMessage('房源或许已经被签约,请重新操作!');
							njyc.phone.closeCallBack("refreshlList();");
						}
					}
				}
			});
		},
		soldOut:function()
		{
			// 撤销发布
			window.wxc.xcConfirm('是否撤销房发布？', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function(){
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/soldOut.do',
					data : {id : id},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								njyc.phone.showShortMessage('操作成功!');
								njyc.phone.closeCallBack("refreshlList();");
							} else {
								njyc.phone.showShortMessage('网络忙,请稍候重试!');
							}
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');
						}
					}
				});
				}
			});
		},
		houseRelease:function()
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/checkHouseMenory.do',
				data : {houseId : id },
				dataType : 'json',
				async: false,
				loadfun : function(isloadsucc, data) 
				{
					if(data.flag<2)
					{
						njyc.phone.showShortMessage('发布的房源价格低于基准价格:'+data.sumcnt+'元,请重新编辑房源价格!');
						return ;
					}
					else
					{
						// 发布房源
						window.wxc.xcConfirm('是否发布房源？', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function(){
							// 发布房源
							njyc.phone.ajax({
								url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/houseRelease.do',
								data : {id : id},
								dataType : 'json',
								loadfun : function(isloadsucc, data) {
									if (isloadsucc) {
										if (data.state == '1') {
											njyc.phone.showShortMessage('操作成功!');
											njyc.phone.closeCallBack("refreshlList();");
										} else if(data.state == '-2'){
											njyc.phone.showShortMessage('当前房源有未编辑房源,请编辑房源信息!');
											// 需要编辑房源信息
											$('.common_button').html('<div class="right3"><button id="saveBtn" type="button" class="">保存</button></div>');
											$('#saveBtn').unbind('click');
											$('#saveBtn').text('编辑房源信息');
											$('#saveBtn').click(house_detail.editHouseInfo);
										} else {
											njyc.phone.showShortMessage('网络忙,请稍候重试!');
										}
									} else {
										njyc.phone.showShortMessage('网络忙,请稍候重试!');
									}
								}
							});
							}
						});
					}	
				}
			});
			
		},
		approvalPublish:function(monery)
		{
			// 发布房源
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/checkHouseMenory.do',
				data : {houseId : id},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						window.wxc.xcConfirm('<p>房源基准参考价格:'+data.sumcnt+'元</p><p>此房源发布是否审核通过？</p>', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',okTitle:'通过',cancleTitle:'拒绝',onOk:function(){
						 	house_detail.approvalPublishFunc(2);
							},onCancel:function(){
								house_detail.approvalPublishFunc(0);
							}
						});
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		editHouseInfo:function()
		{
			// 编辑房源状态
			njyc.phone.openWebKit({title:'编辑房源',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/publish_house/publish_house.html?id='+id});
		},
		approvalPublishFunc:function(isPass)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/approvalPublish.do',
				data : {id : id,isPass:isPass},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == '-2'){
							njyc.phone.showShortMessage('房源状态已经改变，请稍候操作!');
							njyc.phone.closeCallBack("refreshlList();");
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		houseTransfer:function()
		{
			window.wxc.xcConfirm('此房源是否通过交接？', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',okTitle:'通过',cancleTitle:'拒绝',onOk:function(){
				house_detail.houseTransferFunc(9);
			},onCancel:function(){
				house_detail.houseTransferFunc(5);
			}
			});
		},
		houseTransferFunc:function(isPass)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/houseTransfer.do',
				data : {id : id,isPass:isPass},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == '-2'){
							njyc.phone.showShortMessage('房源状态审批,请重新查看!');
							njyc.phone.closeCallBack("refreshlList();");
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		istop:function(state)
		{
			var msg="";
			if (state==1) 
			{
				msg="是否设置为精品房源？";
			}
			else
			{
				msg="是否取消精品房源？";
			}
			window.wxc.xcConfirm(msg, window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function(){
					house_detail.istopFunc(state);
				},onCancel:function(){
//					house_detail.istopFunc(state);
				}
			});
		},
		istopFunc:function(state)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/rankIstop.do',
				data : {id:id, istop:state},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功!');
							njyc.phone.closeCallBack("refreshlList();");
						} else {
							njyc.phone.showShortMessage('网络忙,请稍候重试!');
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		publishHouse:function()
		{
			// 加载可以发布的房源信息
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/seeRentHouse.do',
				data : {id:id},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						for(var i = 0; i < data.length; i++)
						{
							console.log(data[i]);
							var selector = 'select'+i;
							html = '<div class="titleDiv" onclick="house_detail.showRankAgreementInfo(this,\''+data[i].rank_status+'\',\''+data[i].id+'\')">'
//								 + '	<img src="../../images/up.png" width="25px" height="25px" />'
								 + '	<input type="hidden" value="'+data[i].id+'" name="id" /><input type="hidden" value="" name="images" /><input type="hidden" value="'+i+'" name="numberInput" />'
								 + '	<span class="leftSpan"><p>'+data[i].title+'</p><p class="color2">'+data[i].rank_code+'</p></span>'
								 + '	<span class="rightSpan"><p>'+data[i].rankType+'</p><p class="color1">'+data[i].rankStatus+'</p></span>&nbsp;&nbsp'
								 + '</div>'
								 + '<div id="div'+i+'" style="display: block;">'
								 + '	<div class="divPadding">'
								 + '		<div class="textDiv">'
								 + '			<div class="lefeDiv">面积</div>'
								 + '			<div class="rightDiv">'
								 + '				<input type="number"  readonly="readonly" value="'+data[i].rank_area+'" dataType="Integer3" msg="请输入房源面积！" placeholder="20" maxlength="5" name="areaid" />'
								 + '			</div>'
								 + '			<div class="lefeDiv">价格</div>'
								 + '			<div class="rightDiv">'
								 + '				<input type="number"  readonly="readonly" dataType="Integer3" msg="请输入房源价格！" placeholder="2000" value="'+data[i].fee+'" maxlength="5" name="price" />'
								 + '			</div>';
								if(data[i].rank_status == 3)
								{
//									html += '  <div class="rightbtn"> '
//										 + '		<button id="signRankHouse" type="button" onclick="house_detail.signRankHouse(\''+data[i].title+'\',\''+data[i].id+'\',\''+data[i].rankType+'\',\''+id+'\')"  class="">出租签约</button>'
//										 + ' </div>';
								}
								if(data[i].rank_status == 0)
								{
									html += ' <input type="hidden" name="rank_status" value="'+data[i].rank_status+'"/> ';
								}
								html += '	</div>'
									 +  '</div>';
								/* + '	<div class="updownload">'
								 + '		<div class="weui_cell file" onclick="publish_house.selectImages(\''+selector+'\');return false;" id="selectImage'+i+'">'
//								 + '			<a href="">选择文件上传</a>'
								 + '		</div>'
								 + '			<ul class="ipost-list ui-sortable js_fileList" id="'+selector+'"></ul>'
//								 + '			<input type="hidden" value="4" name="picSize" id="picSize" />'
								 + '	</div>';*/
								 if(i != data.length-1)
								{
									 html += ' <hr>';
								}
								$('#agreenmentList').append(html);
								$('.item_close').hide();
//								njyc.phone.showPic(data[i].path,selector);
						}
						if(data.length > 0)
						{
							var html = '<div class="division" onclick="house_detail.contentDivShowOrHide(this)"><div class="left"><hr></div><div class="center"><b class="minus">-</b>&nbsp;租赁房源</div class=""><div class="right"><hr></div></div>';
							$('#contentDiv').prepend(html);
							if($('input[name="rank_status"]').length > 0)
							{
								$('.common_button').append('<div class="left4"><button id="editHouse" type="button" class="">编辑房源</button></div>');
								$('#editHouse').click(house_detail.editHouseInfo)
							}	
						}	
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		showAgreementInfo:function(obj, rank_status, rankId)
		{
			// 查看该房间和该房间下的合约信息
			njyc.phone.openWebKit({title:'房源信息',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/house_agre_detail.html?rankId='+rankId+'&houseId='+id});
		},
		showRankAgreementInfo:function(obj, rank_status, rankId)
		{
			// 查看该房间和该房间下的合约信息
			njyc.phone.openWebKit({title:'房源信息',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/house_agre_detail.html?rankId='+rankId+'&houseId='+id});
		},
		signRankHouse:function(title,id,rankType,houseId)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/checkRankHouseStatus.do',
				data : {id : id},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.openWebKit({title:'租房合约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/cz_contract_detail.html?house_rank_id='+id+'&rankType='+encodeURI(encodeURI(rankType))+'&title='+encodeURI(encodeURI(title))+'&houseId='+houseId+'&id='});
						} else {
							njyc.phone.showShortMessage('房源状态发生改变,请重新确认!');
							njyc.phone.closeCallBack("refreshlList();");
						}
					} else {
						njyc.phone.showShortMessage('网络忙,请稍候重试!');
					}
				}
			});
		},
		loadMap:function() 
		{
			// 加载地图信息
			$('#groupAddress').blur(function()
			{
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
						njyc.phone.showShortMessage("您输入的地址没有解析到结果，请在地图中选取您的地址!");
					}
				}, "南京市");
			});
			var map = new BMap.Map("houseMap", {
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
				house_detail.loadMap();
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
						njyc.phone.showShortMessage("您输入的地址没有解析到结果，请在地图中选取您的地址!");
					}
				}, "南京市");
			});
			var map = new BMap.Map("houseMap", {
				minZoom : 8,
				maxZoom : 18
			});
			map.setCurrentCity("南京"); // 设置地图显示的城市 此项是必须设置的
			map.centerAndZoom(new BMap.Point(split[0], split[1]), 12);
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
		contentDivShowOrHide:function(obj)
		{
			// 合约列表显示或隐藏
			$('#agreenmentList').toggle();
			var content = $(obj).find('b').html();
			if(content == '-')
			{
				$(obj).find('b').html('+');
				$(obj).find('b').removeClass('minus').addClass('add');
			}
			else
			{
				$(obj).find('b').html('-');
				$(obj).find('b').removeClass('add').addClass('minus');
			}	
		},
		showAgreementInfo:function()
		{
			// 显示合约详细
			njyc.phone.openWebKit({title:'委托合约',url:njyc.phone.getSysInfo('serverPath')+'/client/pages/market/wt_contract_detail.html?agreementId='+$('#agreementId').val()+'&id='+id});
		}
		
};
$(document).ready(function(){
	house_detail.init();
	$(document).on('click','.loadMore' ,house_detail.selectAearInfo);
});

function refreshlList()
{
	njyc.phone.closeCallBack("refreshlList();");
}



