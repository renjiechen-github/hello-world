var rank_agreement = 
{
	init:function() 
	{
		$('.form-control input').attr('maxlength','50');
		var rankHouseId = undefined;
		var title = undefined;
		var rankType = undefined;
		var houseId = undefined;
		var agreementId = undefined;		
		if (common.getWindowsData('signHouse') != null && common.getWindowsData('signHouse') != undefined) {
			rankHouseId = common.getWindowsData('signHouse').id;
			title = common.getWindowsData('signHouse').title;
			rankType = common.getWindowsData('signHouse').rankType;
			houseId = common.getWindowsData('signHouse').houseId;
			agreementId = common.getWindowsData('signHouse').agreementId;
		}
		if (typeof jsCancelLeaseOrderDetail != "undefined") {
			rankHouseId = jsCancelLeaseOrderDetail.rankHouseId_;
			title = jsCancelLeaseOrderDetail.title_;
			rankType = jsCancelLeaseOrderDetail.rankType_;
			houseId = jsCancelLeaseOrderDetail.houseId_;
			agreementId = jsCancelLeaseOrderDetail.agreementId_;			
		}
		if (typeof jsNewCancelLeaseOrderDetail != "undefined") {
			rankHouseId = jsNewCancelLeaseOrderDetail.rankHouseId_;
			title = jsNewCancelLeaseOrderDetail.title_;
			rankType = jsNewCancelLeaseOrderDetail.rankType_;
			houseId = jsNewCancelLeaseOrderDetail.houseId_;
			agreementId = jsNewCancelLeaseOrderDetail.agreementId_;			
		}		
		
		$('#house_name').text(title);
		$('#agreement_name').val(title);
		$('#agreement_type').text(rankType);
		$('#rank_id').val(rankHouseId);
		$('#houseId').val(houseId);
		$('#id').val(agreementId);
		rank_agreement.loadSelect(); 
		$('#mobile').blur(rank_agreement.getUserName);
		$('#house_sign_btn').click(rank_agreement.saveRankAgreeMent);
		var nowTemp = new Date();
		$('#validateDate').daterangepicker(
		{
			startDate : nowTemp.getFullYear() + '-' + (nowTemp.getMonth() + 1) + '-02',
			timePicker12Hour : false,
			timePicker : false,
			singleDatePicker : true,
			// 分钟间隔时间
			timePickerIncrement : 10,
			format : 'YYYY-MM-DD'
		}, function(start, end, label) {
//			console.log(start.toISOString(), end.toISOString(), label);
		});
		
		if(agreementId=='')
		{
			common.dropzone.init(
			{
				id : '#myAwesomeDropzone',
				maxFiles : 4,
			});
		//	$('.form-control').attr('maxlength','30');
		}
		else
		{
			rank_agreement.loadAgreementInfo(agreementId);
		}
	},
	getUserName:function()
	{
		var mobile = $('#mobile').val();
		var reginx = /^1(3|4|5|7|8)[0-9]{9}$/;
		if(!reginx.test(mobile))
		{
			layer.tips('请输入11位数字格式的手机号码', $('#mobile'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#name').removeAttr("readonly","readonly");
			$('#certificateno').removeAttr("readonly","readonly");
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
					console.log(data);
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
						common.alert({
							msg : '号码重复,请联系宗培新删除数据!'
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
	loadAgreementInfo:function(id)
	{
		
		//加载信息
		common.ajax({
			url : common.root + '/rankHouse/loadAgreementInfo.do',
			dataType : 'json',
			data:{id:id},
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) 
				{
					var flag = undefined;
					if (common.getWindowsData('signHouse') != null && common.getWindowsData('signHouse') != undefined) {
						flag = common.getWindowsData('signHouse').flag;
					}					
					if (typeof jsCancelLeaseOrderDetail != "undefined") {
						flag = jsCancelLeaseOrderDetail.flag_;
					}
					$('#house_name').text(data.house_name);
					$('#agreement_name').val(data.name);
					$('#agreement_code').val(data.sbcode);
					$('#mobile').val(data.mobile);
					$('#name').val(data.username);
					$('#manageId').val(data.agents);
					$('#rankPrice').val(data.cost_a); 
					$('#payType').val(data.pay_type);
					$('#validateDate').val(data.begin_time);
					$('#rankDate').val(data.rent_month);
					$('#agreementDes').val(data.desc_text);
					$('#certificateno').val(data.certificateno);
					var picArray = new Array();
					for (var i = 0; i < data.file_path.split("|").length; i++) {
						if (i == 0) {
							picArray.push({
								path : data.file_path.split("|")[i],
								first : 1
							});
						} else {
							picArray.push({
								path : data.file_path.split("|")[i],
								first : 0
							});
						}
					}
//					console.log(picArray);
					common.dropzone.init({
						id : '#myAwesomeDropzone',
						maxFiles :4,
						defimg : picArray, 
						clickEventOk: flag == 1?true:false
					});
					
					if(flag==1)
					{
						$.each($('#name,#mobile,#certificateno'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
					}
					else
					{
						$('#form2 b').hide();
						$('#house_sign_btn').hide();
						$.each($('#form2 :text'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
						$.each($('#rankPrice'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
						
						$("#agreementDes").parent().html("<div style='height:auto;min-height: 100px;' class='form-control'>"+data.desc_text+"</div>");
						$.each($('#form2 select'),function(){
							  var $span = $(this).parent("div");
							  var selectedValue = $(this).find("option:selected").text();
							  $(this).remove(); 
							  $span.append("<span class='form-control border_0'>"+selectedValue+"</span>");
						});
					}	
				} 
			}
		});
		
	},
	saveRankAgreeMent:function()
	{
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
		var ajax_option =
		{
			url:common.root + "/rankHouse/saveNewRankAgreement.do",//默认是form action
			success:rank_agreement.resFunc,
			type:'post',
			dataType:'json'
		};
		$('#house_sign_btn').unbind("click");
		$("#form2").ajaxForm(ajax_option).submit();
	},
	resFunc:function(data)
	{
		$('#house_sign_btn').click(rank_agreement.saveRankAgreeMent);
		if(data.state == 1)
		{
			common.alert({
				msg : '操作成功',
				fun : function() {
					common.closeWindow('signHouse', 3);
				}
			});
		}
		else if(data.state == -2)
		{
			common.alert({
				msg : '房源状态已经改变,请重新操作!',
				fun : function() {
					common.closeWindow('signHouse', 3);
				}
			});
		}
		else if(data.state == -3)
		{
			common.alert({
				msg : '图片上传失败,请重试!'
			});
		}
		else if(data.state == -4)
		{
			common.alert({
				msg : '房源已签约',
				fun : function() 
				{
					common.closeWindow('signHouse', 3);
				}
			});
		}
		else
		{
			common.alert({
				msg : '网络忙,请重试!'
			});
		}
		
	},
	loadSelect : function() {
		var flag = undefined;
		if (common.getWindowsData('signHouse') != null && common.getWindowsData('signHouse') != undefined) {
			flag = common.getWindowsData('signHouse').flag;
		}
		if (typeof jsCancelLeaseOrderDetail != "undefined") {
			flag = jsCancelLeaseOrderDetail.flag_;
		}
		if(flag == 1)
		{
			common.loadItem('GROUP.PAYTYPE', function(json) {
				var html = "";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#payType').append(html);
			},false);
		}
		else
		{
			common.loadItemAll('GROUP.PAYTYPE', function(json) {
				var html = "";
				for (var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#payType').append(html);
			},false);
		}
		common.loadItem('GROUP.RANKDATE', function(json) {
			var html = "";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#rankDate').append(html);
		},false);
		common.loadItem('DECORATE.TYPE', function(json) {
			var html = "<option value=''>请选择...</option>";
			for (var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#decorate').append(html);
		},false);
		common.ajax({
			url : common.root + '/BaseHouse/getMangerList.do',
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
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},
};
$(rank_agreement.init());