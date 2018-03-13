var id = '';
var house_id = '';
var house_rank_id = '';
var rank_agreement_info = 
{
		init:function()
		{
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
			var orderflag=njyc.phone.queryString('orderflag');
			if (orderflag=="1")
			{
              $(".common_button").hide(); 
			}  
			
			$("#tzDate").mobiscroll($.extend(opt['datetime'], opt['default']));
			$("#validateDate").mobiscroll($.extend(opt['date'], opt['default']));
			rank_agreement_info.loadSelect();
			$('#mobile').blur(rank_agreement_info.getUserName); // 解绑手机号码
			var title = njyc.phone.queryString('title'); // 
			var rankType = njyc.phone.queryString('rankType'); // 出租类型
			house_id = njyc.phone.queryString('houseId'); //  房间id
			house_rank_id = njyc.phone.queryString('house_rank_id'); // 小房间id
			id = njyc.phone.queryString('id'); // 合约id
			$('#house_name').html(title);
			$('#agreement_name').val(title);
			$('#rankType').html(rankType);
			$('#rank_id').val(house_rank_id);
			$('#houseId').val(house_id);
			$('#id').val(id);
			$('#selectImage').click(rank_agreement_info.selectImages); 
			if(id=='')
			{
				$('#manageId').val(njyc.phone.getSysInfo('userId'));
				$('.common_button .left3').remove();
				$('#saveBtn').click(rank_agreement_info.saveRankAgreement);
			}	
			else
			{
				rank_agreement_info.loadRankAgreementInfo(id);
			}	
			$('.common_button').parent().append('<div style="height: 20px;"></div>');
			$('.common_button').remove();
		},
		loadSelect:function()
		{
			// 加载下拉框
			njyc.phone.loadItem('GROUP.PAYTYPE', function(json) {
				var html = "";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#payType').append(html);
			},false);
			njyc.phone.loadItem('GROUP.RANKDATE', function(json) {
				var html = "";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#rankDate').append(html);
			},false);
			njyc.phone.loadItem('DECORATE.TYPE', function(json) {
				var html = "<option value=''>请选择...</option>";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#decorate').append(html);
			},false);
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getMangerList.do',
				dataType : 'json',
				async:false,
				loadfun : function(isloadsucc, json) {
					if (isloadsucc) 
					{
						var html = "";
						for ( var i = 0; i < json.length; i++) 
						{
							html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
						}
						$('#manageId').append(html);
					} 
					else 
					{
						njyc.phone.showShortMessage('网络错误,请稍候重试');
					}
				}
			});
		},
		getUserName:function()
		{
			//加载用户姓名
			var mobile = $('#mobile').val();
			var reginx = /^1(3|4|5|7|8)[0-9]{9}$/;
			if(!reginx.test(mobile))
			{
				njyc.phone.showShortMessage('请输入11位数字格式的手机号码!');
				$('#mobile').removeAttr("readonly","readonly");
				$('#certificateno').removeAttr("readonly","readonly");
				return ;
			}
//			if(mobile == '' || mobile.length != 11)
//			{
//				$('#userName').attr("readonly","readonly");
//				$('#mobile').removeAttr("readonly","readonly");
//				return ;
//			}
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/getUserName.do',
				data : {
					userMobile : mobile
				},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) 
					{
						if(data.state == 1)
						{
							var name = data.username;
							$('#name').val(name);
							if(name != null && name != '' && name != "null")
							{
								$('#name').attr("readonly","readonly");
							}
							else
							{
								$('#name').removeAttr("readonly","readonly");
							}
							var certificateno = data.certificateno;
							$('#certificateno').val(certificateno);
							if(certificateno != null && certificateno != '' && certificateno != "null")
							{
								$('#certificateno').attr("readonly","readonly");
							}
							else
							{
								$('#certificateno').removeAttr("readonly","readonly");
							}
						}
						else if(data.state == -1)
						{
							$('#mobile').val('');
							njyc.phone.showShortMessage('号码重复,请联系宗培新删除数据!');
						}	
					} else {
						 
					}
				}
			});
		},
		saveRankAgreement:function()
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
			for(var i = 0; i < picImage.length; i++)
			{
				path+=',' + $(picImage[i]).val();
			}
			if(path != '')
			{
				path = path.substring(1);
			}
			njyc.phone.showProgress('');
			$('#picPath').val(path);
			var ajax_option =
			{
				url:njyc.phone.getSysInfo('serverPath') + "/mobile/rankAgreement/saveRankAgreement.do",//默认是form action
				success:rank_agreement_info.resFunc,
				type:'post',
				dataType:'json'
			};
			$("#form2").ajaxForm(ajax_option).submit();
		},
		resFunc:function(data)
		{
			njyc.phone.closeProgress()
			$('#saveBtn').click(rank_agreement_info.saveRankAgreement);
			if(data.state == 1)
			{
				njyc.phone.showShortMessage('操作成功');
				njyc.phone.closeCallBack("refreshlList();");
			}
			else if(data.state == -2)
			{
				njyc.phone.showShortMessage('房源状态已经改变,请重新操作！');
				njyc.phone.closeCallBack("refreshlList();");
			}
			else if(data.state == -3)
			{
				njyc.phone.showShortMessage('图片上传失败,请重试!');
				return;
			}
			else if(data.state == -4)
			{
				njyc.phone.showShortMessage('房源已签约!');
				njyc.phone.closeCallBack("refreshlList();");
			}
			else
			{
				njyc.phone.showShortMessage('网络忙,请重试!');
			}
			
		},
		loadRankAgreementInfo:function(id)
		{
			//加载信息
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/loadRankAgreementInfo.do',
				dataType : 'json',
				data:{id:id},
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) 
					{
						$('#agreement_name').val(data.name);
						$('#agreement_code').val(data.sbcode);
						$('#mobile').val(data.user_mobile);
						$('#name').val(data.username); 
						$('#manageId').val(data.agents);
						$('#rankPrice').val(data.cost_a); 
						$('#payType').val(data.pay_type);
						$('#validateDate').val(data.begin_time);
						$('#rankDate').val(data.rentMonth);
						$('#agreementDes').val(data.desc_text);
						$('#agreement_span').val(data.code);
						$('#certificateno').val(data.certificateno);
						$('#agreementDiv').show();
//						console.log(data);
						var picArray = new Array();
						try
						{
							njyc.phone.showPic(data.file_path);
						}
						catch(e)
						{
							
						}
 
						$.each($('#name,#mobile,#certificateno'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
						if(data.status == '0')
						{
							// 待提交
							$('#saveBtn').click(rank_agreement_info.saveRankAgreement);
							$('#submitBtn').click(rank_agreement_info.submitAgreement);
							$('.common_button').append('<div class="left3"><button id="delBtn" type="button" class="">删除</button></div>');
							$('#delBtn').click(rank_agreement_info.del);
						}
						else
						{ 
							$('#selectImage').hide();
							$('.item_close').hide();
							if(data.status == '1')
							{
								// 审批合约
								$('#saveBtn').html('审批合约');
								$('#saveBtn').click(rank_agreement_info.approvalAgreement);
								$('#submitBtn').parent().remove();
							} 
							else if(data.status == '2')
							{
								// 已生效
								$('#saveBtn').html('撤销合约');
								$('#saveBtn').click(rank_agreement_info.cancelAgreement);
								// 判断是否有可以退租
								if(data.is_tj == 0)
								{
									rank_agreement_info.showTz();
								}
								else
								{
									$('#submitBtn').parent().remove();
								}	
							}	
							else if(data.status == '3')
							{
								// 已失效
								$('.common_button').parent().append('<div stype="height: 20px;"></div>');
								$('.common_button').remove();
							}
							else if(data.status == '4')
							{
								// 撤消待审批
								$('#saveBtn').html('审批撤销');
								$('#saveBtn').click(rank_agreement_info.spAgeement);
//								if(data.is_tj == 1)
//								{
//									rank_agreement_info.showTz();
//								}
//								else
//								{
									$('#submitBtn').parent().remove();
//								}	
							}
							else if(data.status == '5')
							{
								// 撤消待审批
								$('#saveBtn').html('结束合约');
								$('#saveBtn').click(rank_agreement_info.overAgreement);
//								if(data.is_tj == 0)
//								{
//									rank_agreement_info.showTz();
//								}
//								else
//								{
									$('#submitBtn').parent().remove();
//								}	
							}
							else if(data.status == '6')
							{
								// 撤消待审批
								$('#saveBtn').html('结束合约');
								$('#saveBtn').click(rank_agreement_info.overRankAgreement);
//								if(data.is_tj == 0)
//								{
//									rank_agreement_info.showTz();
//								}
//								else
//								{
									$('#submitBtn').parent().remove();
//								}	
							}
							
							$.each($(':text'),function(){
								$(this).attr("readonly","readonly");
							});
							$.each($('#rankPrice'),function(){
								$(this).parent().html('<span>'+$(this).val()+'</span>');
							});
							$('#agreementDes').attr('disabled','disabled');
							$('select').attr('disabled','disabled');
						}	
					} 
				}
			});
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
		approvalAgreement:function()
		{
			window.wxc.xcConfirm('此合约是否审核通过？', window.wxc.xcConfirm.typeEnum.confirm,{okTitle:'通过',cancleTitle:'拒绝',title:'温馨提示',onOk:function(){
				rank_agreement_info.approvalAgreementFuc(2);
			},
			onCancel:function()
			{
				rank_agreement_info.approvalAgreementFuc(0);
			}});
		},
		approvalAgreementFuc:function(isPass)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/approvalAgreement.do',
				data : {
					id : id,
					isPass:isPass,
					house_id:house_id
				},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -2){
							njyc.phone.showShortMessage('合约状态已经改变，请稍候操作!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -4){
							njyc.phone.showShortMessage('未查询到房源合约信息!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -5){
							njyc.phone.showShortMessage('未查询到财务项目!');
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
		spAgeement:function()
		{
			// 审批合约
			window.wxc.xcConfirm('是否将此合约置为无效？', window.wxc.xcConfirm.typeEnum.confirm,{okTitle:'通过',cancleTitle:'拒绝',title:'温馨提示',onOk:function(){
				rank_agreement_info.spAgeementFuc(3);
			},
			onCancel:function()
			{
				rank_agreement_info.spAgeementFuc(2);
			}});
		},
		spAgeementFuc:function(isPass)
		{
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/spRankAgeement.do',
				data : {
					id : id,
					isPass:isPass,
					houseId:house_id,
					house_rank_id:house_rank_id
				},
				dataType : 'json',
				loadfun : function(isloadsucc, data)
				{
					if (isloadsucc)
					{
						if (data.state == '1') {
							njyc.phone.showShortMessage('操作成功!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -2){
							njyc.phone.showShortMessage('合约状态已经改变，请稍候操作!');
							njyc.phone.closeCallBack("refreshlList();");
						} else if(data.state == -12){
							njyc.phone.showShortMessage('未查询到财务数据!');
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
		// 提交合约
		submitAgreement:function()
		{
			// 提交合约
			window.wxc.xcConfirm('确定提交合约吗？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
				njyc.phone.showProgress('');
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/submitRankAgreement.do',
					data : {id : id},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						njyc.phone.closeProgress();
						if (isloadsucc) 
						{
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
		overRankAgreement:function()
		{
			// 合约到期，结束合约
			window.wxc.xcConfirm('合约到期，结束合约？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
				njyc.phone.showProgress('');
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/overRankAgreement.do',
					data : {id : id,rankId:house_rank_id,houseId:house_id},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						njyc.phone.closeProgress()
						if (isloadsucc) 
						{
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
		overAgreement:function()
		{
			// 结束合约
			window.wxc.xcConfirm('您确定结束此合约？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
				njyc.phone.showProgress('');
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/spOverRankAgeement.do',
					data : {id : id,isPass:4},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						njyc.phone.closeProgress()
						if (isloadsucc) 
						{
							if (data.state == '1') 
							{
								njyc.phone.showShortMessage('操作成功');
								njyc.phone.closeCallBack("refreshlList();");
							} 
							else if(data.state == -2)
							{
								njyc.phone.showShortMessage('合约状态已改变,请重新查看!');
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
		cancelAgreement:function()
		{
			// 取消合约
			window.wxc.xcConfirm('您确定撤销此合约？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
				njyc.phone.showProgress('');
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/cancelRankAgreement.do',
					data : {id : id,isPass:4},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						njyc.phone.closeProgress()
						if (isloadsucc) 
						{
							if (data.state == '1') 
							{
								njyc.phone.showShortMessage('操作成功');
								njyc.phone.closeCallBack("refreshlList();");
							} 
							else if(data.state == -2)
							{
								njyc.phone.showShortMessage('合约状态审批,请重新查看!');
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
		del:function()
		{
			// 取消合约
			window.wxc.xcConfirm('确定需要删除合约吗？', window.wxc.xcConfirm.typeEnum.confirm,{title:'温馨提示',onOk:function(){
				njyc.phone.showProgress('');
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/delRankAgreement.do',
					data : {id : id,houseId:house_id,house_rank_id:house_rank_id},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						njyc.phone.closeProgress();
						if (isloadsucc) 
						{
							if (data.state == '1') 
							{
								njyc.phone.showShortMessage('删除成功');
								njyc.phone.closeCallBack("refreshlList();");
							} 
							else if(data.state == -2)
							{
								njyc.phone.showShortMessage('合约状态发生改变,请重新确认!');
								njyc.phone.closeCallBack("refreshlList();");
							}
							else if(data.state == -12)
							{
								njyc.phone.showShortMessage('当前房源已下架,请勿删除!');
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
		showTz:function()
		{
			$('#submitBtn').html('退租');
			$('#colseBtn').click(function(){
				$('.divCenter').hide();
			});
			$('#tjBtn').click(rank_agreement_info.tzAgreement);
			$('#submitBtn').click(function(){
				$('#tzType').removeAttr("disabled");
				$('#tzDiv').show();
			});
		},
		tzAgreement:function()
		{
			//保存退租
			var tjDate = $('#tzDate').val(); // 退租时间
			var tzType = $('#tzType').val(); // 退租类型
			if(tjDate == '')
			{
				njyc.phone.showShortMessage('请选择退租时间!');
				return false;
			}	
			njyc.phone.showProgress('');
			njyc.phone.ajax({
				url : njyc.phone.getSysInfo('serverPath') + '/mobile/rankAgreement/tzInfo.do',
				data : {id : id,tjDate:tjDate,tzType:tzType},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					njyc.phone.closeProgress();
					if (isloadsucc) 
					{
						if (data.state == '1') 
						{
							njyc.phone.showShortMessage('操作成功');
							$('.divCenter').hide();
							njyc.phone.closeWebView();
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
		}
}
$(rank_agreement_info.init());