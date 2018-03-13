var price = 0;
var price2 = 0;
var address = '';
var roomcnt = '';
var rentroomcnt = '';
var area = '0'; 
var rank_code = '';
var priceJSON;
var tmpJSon ;
var payTypeList;
var rank_agreement = 
{
	init:function() 
	{
		$('.form-control').attr('maxlength','50');
		$('#agreementDes').attr('maxlength','200');
	//	 $('#form3').submit();
	//	 return ;
		var flag = common.getWindowsData('signHouse').flag;
		if(flag ==3){//签约页面移除disabled-身份证下拉框新增id(idCard)属性
//			$('#manageId').removeAttr("disabled");
//			$('#mobile').removeAttr("disabled");
			$.each($('#manageId,#mobile,#name,#email,#certificateno,#rankDate,#payType,#rankPrice,#validateDate,#serviceMonery,#propertyMonery,#payway,#deposit,#idCard'),function(){
				$(this).removeAttr("disabled");
			});
		}
		console.log(common.getWindowsData('signHouse'));
		var rankHouseId = common.getWindowsData('signHouse').id;
		var title = common.getWindowsData('signHouse').title;
		var rankType = common.getWindowsData('signHouse').rankType;
		var houseId = common.getWindowsData('signHouse').houseId;
		var agreementId = common.getWindowsData('signHouse').agreementId;
		price  = common.getWindowsData('signHouse').fee;
		price2 = price;
		address  = common.getWindowsData('signHouse').address;
		roomcnt  = common.getWindowsData('signHouse').roomcnt;
		rentroomcnt  = common.getWindowsData('signHouse').rentroomcnt;
		area  = common.getWindowsData('signHouse').area;
		rank_code  = common.getWindowsData('signHouse').rank_code;
		$('#address').val(address);
		$('#roomcnt').val(roomcnt);
		$('#rentroomcnt').val(rentroomcnt);
		$('#area').val(area);
		$('#house_name').text(title);
		$('#agreement_name').val(title);
		$('#agreement_type').text(rankType);
		$('#rank_id').val(rankHouseId);
		$('#houseId').val(houseId);
		$('#id').val(agreementId);
		$('#deposit').val(100*rentroomcnt);
		$('#rank_code').val(rank_code);
		$('#flag').val(flag);
		$('#payType').change(function(){
			var payType = $(this).val();
			var month = $('#rankDate').val();
			for(var i = 0; i < priceJSON.length; i++)
			{
				var item_id = priceJSON[i].item_id;
				var item_value = priceJSON[i].item_value;
				if(item_id == month && item_value == payType)
				{
					console.log("item_id:"+item_id+":"+item_value);
					price = priceJSON[i].item_name;
					$('#rankPrice').val(priceJSON[i].item_name);
				}
			}	
			/**
			if($(this).val()=='0') // 全额付
			{
				var month = $('#rankDate').val();
				if(Math.abs(month) >= 1 && Math.abs(month) <= 3)
				{
					price = rank_agreement.formatFloat(Math.abs(price2)*1.3,1);
					$('#rankPrice').val(price);
				}
				else if(Math.abs(month) > 3 && Math.abs(month) < 7)
				{
					price = rank_agreement.formatFloat(Math.abs(price2)*1.2,1);
					$('#rankPrice').val(price);
				}
				else if(Math.abs(month) > 6 && Math.abs(month) < 12)
				{
					price = rank_agreement.formatFloat(Math.abs(price2)*1.1,1);
					$('#rankPrice').val(price);
				}
				else if(Math.abs(month) == 12)
				{
					price = rank_agreement.formatFloat(Math.abs(price2),1);
					$('#rankPrice').val(price);
				}	
			}
			else if($(this).val()=='12' || $(this).val()=='15')
			{
				price = price2;
				$('#rankPrice').val(price);
			}	
			else
			{
				price = price2;
				$('#rankPrice').val('');
			}	
			*/
		});
		$('#rankDate').change(function(){
			var month = $(this).val();
			$('#payType').html('');
//			$('#payType').html('<option value="">请选择...<option>');
			for(var i = 0; i < priceJSON.length; i++)
			{
				var itemId = priceJSON[i];
				if(itemId.item_id == month)
				{
					var item_name = rank_agreement.getValueBykey(payTypeList,itemId.item_value);
					var html = '<option value="' + itemId.item_value + '">'+ item_name + '</option>';
					$('#payType').append(html);
				}
			}
			var payType = $('#payType').val();
			for(var i = 0; i < priceJSON.length; i++)
			{
				var item_id = priceJSON[i].item_id;
				var item_value = priceJSON[i].item_value;
				if(item_id == month && item_value == payType)
				{
					price = priceJSON[i].item_name;
					$('#rankPrice').val(priceJSON[i].item_name);
				}
			}	
			/**
			if($('#payType').val()=='0') // 全额付
			{
				if(Math.abs(month) >= 1 && Math.abs(month) <= 3)
				{
					price = rank_agreement.formatFloat(Math.abs(price2)*1.3,1);
					$('#rankPrice').val(price);
				}
				else if(Math.abs(month) > 3 && Math.abs(month) < 7)
				{
					price = rank_agreement.formatFloat(Math.abs(price2)*1.2,1);
					$('#rankPrice').val(price);
				}
				else if(Math.abs(month) > 6 && Math.abs(month) < 12)
				{
					price = rank_agreement.formatFloat(Math.abs(price2)*1.1,1);
					$('#rankPrice').val(price);
				}	
				else if(Math.abs(month) == 12)
				{
					price = rank_agreement.formatFloat(Math.abs(price2),1);
					$('#rankPrice').val(price);
				}
			}
			else if($('#payType').val() == '12' && Math.abs(month) < 12)
			{
				layer.tips('付款方式不能选择金融付', $('#rankPrice'),{
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				$('#payType').val('15');
				$('#rankPrice').val('');
			}	
			*/
		});
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
			$('#rankPrice').val(price);
			$('#rankPrice').blur(rank_agreement.checkprice);
			common.dropzone.init(
			{
				id : '#myAwesomeDropzone',
				maxFiles : 4,
			});
		}
		else
		{
			rank_agreement.loadAgreementInfo(agreementId);
		}
	},
	getValueBykey:function(jsonArray,key)
	{
		for(var i = 0; i < jsonArray.length; i++)
		{
			var item_id = jsonArray[i].item_id;
			if(item_id == key)
			{
				return jsonArray[i].item_name;
			}	
		}	
	},
	formatFloat:function(f, digit) 
	{ 
	    var m = Math.pow(10, digit); 
	    return parseInt(f * m, 10) / m; 
	}, 
	selectCertI:function(obj)
	{
		if($(obj).val()==1)
		{
			$('#certificateno').attr('dataType','Require');
		}	
		else
		{
			$('#certificateno').attr('dataType','IdCard');
		}	
	},
	checkprice:function()
	{
		// 新增验证房源价格
		var rankPrice = $('#rankPrice').val();
		var reginx = /^\d{1,}$/;
		if(!reginx.test(rankPrice))
		{
			layer.tips('请输入数字格式价格', $('#rankPrice'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#rankPrice').val('');
			return ;
		}
		var month = $('#rankDate').val();
		if(Math.abs(month) < 12 && $('#payType').val() == '12')
		{
			layer.tips('付款方式不能选择年付', $('#rankPrice'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#payType').val('15');
			$('#rankPrice').val('');
			return ;
		}
		if(Math.abs(rankPrice) < Math.abs(price))
		{
			layer.tips('你输入的价格小于发布房源价格'+price+'!', $('#rankPrice'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#rankPrice').val('');
			return ;
		}
	},
	getUserName:function()
	{
		var mobile = $('#mobile').val();
		var reginx = /^1(3|4|5|7|8)[0-9]{9}$/;
		if(!reginx.test(mobile))
		{
			$("#userId").val('');
			layer.tips('请输入11位数字格式的手机号码', $('#mobile'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#mobile').val('');
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
					if(data.state == 1)
					{
						var name = data.username;
						$("#userId").val(data.id);
						$('#name').val(name);
						if(name != null && name != '' && name != "null")
						{
						//	$('#name').attr("readonly","readonly");
						}
						else
						{
							$('#name').removeAttr("readonly","readonly");
						}
						var certificateno = data.certificateno;
						$('#certificateno').val(certificateno);
						if(certificateno != null && certificateno != '' && certificateno != "null")
						{
				//			$('#certificateno').attr("readonly","readonly");
						}
						else
						{
							$('#certificateno').removeAttr("readonly","readonly");
						}
						
						var email = data.email;
						$('#email').val(email);
						if(email != null && email != '' && email != "null")
						{
				//			$('#certificateno').attr("readonly","readonly");
						}
						else
						{
							$('#email').removeAttr("readonly","readonly");
						}
					}
					else if(data.state == -1)
					{
						$('#mobile').val('');
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
					console.log("data:"+data);
					 
					var flag = common.getWindowsData('signHouse').flag;
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
					$('#payway').val(data.payway);
					$('#deposit').val(data.deposit);
					if(flag==0){//详情页面修改disable
						$('#house_name').removeAttr("disabled");
						$('#agreement_type').removeAttr("disabled");
					}
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
					common.dropzone.init({
						id : '#myAwesomeDropzone',
						maxFiles :4,
						defimg : picArray, 
						clickEventOk: flag == 1?true:false
					});
				
					if(!common.isEmpty(data.old_matched) && data.old_matched.indexOf("##") > -1)
					{
						var innerCnt = new Array();
						var outCnt = new Array();
						if(data.old_matched.split('##').length == 1)
						{
							innerCnt = data.old_matched.split('##')[0];
						}	
						else
						{
							innerCnt = data.old_matched.split('##')[0];
							outCnt = data.old_matched.split('##')[1];
						}
						console.log(innerCnt);
						console.log(outCnt);
						if(!common.isEmpty(innerCnt))
						{
							var innerCntArray = innerCnt.split("|");
							for(var i = 0; i < innerCntArray.length; i++)
							{
								$("#"+innerCntArray[i].split('=')[0]).val(innerCntArray[i].split('=')[1]);
							}
						}
						if(!common.isEmpty(outCnt))
						{
							var outCntArray = outCnt.split("|");
							for(var i = 0; i < outCntArray.length; i++)
							{
								$("#"+outCntArray[i].split('=')[0]).val(outCntArray[i].split('=')[1]);
							}
						}
					}
					$('#email').val(data.email);
					$('#serviceMonery').val(data.serviceMonery);
					$('#propertyMonery').val(data.propertyMonery);
					$('#cardelectric').val(data.electriccard);
					$('#eMeter').val(data.electricity_meter);
					$('#eMeterH').val(data.electricity_meter_h);
					$('#eMeterL').val(data.electricity_meter_l);
					$('#cardWarter').val(data.watercard);
					$('#warterDegrees').val(data.water_meter);
					$('#cardgas').val(data.gascard);
					$('#gasDegrees').val(data.gas_meter);
					if(flag==1)
					{
//						$.each($('#name,#mobile'),function(){
//							$(this).parent().html('<span class="form-control border_0 disabled">'+$(this).val()+'</span>');
//						});
					}
					else
					{
						$('#form2 b').hide();
						$('#house_sign_btn').hide();
						$.each($('#form2 :text'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
						$.each($('.inputChange2'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
						$.each($('#rankPrice'),function(){
							$(this).parent().html('<span class="form-control border_0">'+$(this).val()+'</span>');
						});
						
						$("#agreementDes").parent().html("<div style='height:auto;min-height: 100px;' class='form-control'>"+data.desc_text+"</div>");
						$.each($('#form2 .selectControl'),function(){
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
		if($('#payType').val() == '12' && Math.abs($('#rankDate').val()) < 12)
		{
			layer.tips('付款方式不能选择金融付', $('#rankPrice'),{
                tips: [1, '#d9534f'] //还可配置颜色
            });
			$('#payType').val('15');
			$('#rankPrice').val('');
			return ;
		}
//		$('#rent_deposit').val(rank_agreement.formatFloat(Math.abs($('#rankPrice').val())*1,1));
		$('#rent_deposit').val($('#rankPrice').val());
		var filepath = common.dropzone.getFiles('#myAwesomeDropzone');
                console.log(filepath);
		if (filepath.length == 0) 
		{
			common.alert(
			{
				msg : '请证上传租客身份证!'
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
		var flag = $('#flag').val();
		if(3==flag){//20180118 签约时调用的方法
			var ajax_option =
			{
				url:common.root + "/rankHouse/saveRankAgreement.do",//默认是form action
				success:rank_agreement.resFunc,
				type:'post',
				dataType:'json'
			};
		}else{//20180118 修改合约时调用的方法
			var ajax_option =
			{
					url:common.root + "/agreementMge/UpdateHireAgreement.do",//默认是form action
					success:rank_agreement.resUpdateAg,
					type:'post',
					dataType:'json'
			};
		}
		$('#house_sign_btn').unbind("click");
		$("#form2").ajaxForm(ajax_option).submit();
	},
	resFunc:function(data)
	{
		$('#house_sign_btn').click(rank_agreement.saveRankAgreeMent);
		if(data.result == 1)
		{
			common.alert({
				msg : '操作成功',
				fun : function() {
					$('#form3').attr("action",data.url);
					$('#content').val(data.content);
					$('#signed').val(data.signed);
					$('#key').val(data.key);
					$('#apiid').val(data.apiid);
					$('#form3').submit();
					common.closeWindow('signHouse', 3);
				}
			});
		}
		else if(data.result == 0)
		{
			common.alert({
				msg : data.msg,
				fun : function() {
					common.closeWindow('signHouse', 3);
				}
			});
		}
		else if(data.result == -1)
		{
			common.alert({
				msg : data.msg,
				fun : function() {
					// common.closeWindow('signHouse', 3);
				}
			});
		}else if(data.result == -2){
			common.alert({
				msg : data.msg,
				fun : function() {
					//common.closeWindow('houseSign', 3);
					return ;
				}
			});
		}
		else
		{
			common.alert({
				msg : "网络忙,稍候重试!",
				fun : function() {
					common.closeWindow('signHouse', 3);
				}
			});
		}
	},
	resUpdateAg:function(data)
	{
		$('#house_sign_btn').click(rank_agreement.saveRankAgreeMent);
		if(data.result == 1)
		{
			common.alert({
				msg : '操作成功',
				fun : function() {
					common.closeWindow('signHouse', 3);
				}
			});
		}
		else if(data.result == 0)
		{
			common.alert({
				msg : '操作失败请重试!',
				fun : function() {
					return ;
				}
			});
		}
		else
		{
			common.alert({
				msg : "网络忙,稍候重试!",
				fun : function() {
					common.closeWindow('signHouse', 3);
				}
			});
		}
	},
	loadSelect : function() {
		if(common.getWindowsData('signHouse').flag == 1||common.getWindowsData('signHouse').flag == 3)//20180116 增加判断条件==3
		{
			common.loadItem('GROUP.PAYTYPE', function(json) {
				var html = "";
				payTypeList = json;
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
		common.ajax({
			url : common.root + '/rankHouse/loadPriceAndJL.do?monery='+price,
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, json) {
				if (isloadsucc) 
				{
					priceJSON = json;
					
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