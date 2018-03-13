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
var flag=0;
var isMobile;
var dealerId;
var brokerOrderDetail = 
{
	init:function() 
	{
		if (typeof (resFlag) == 'undefined') {
			resFlag = true;
		}	
		if (resFlag) {
			$('#hidden_but').removeClass("hidden");
		}
		//var agreementId = 13459;
		var id = $('#js-work-order-id').val();
		isMobile = $('#js-is-mobile').val();
		dealerId = $('#js-staff-id').val();
		brokerOrderDetail.loadData(id);
		$('.form-control input').attr('maxlength','50');
		brokerOrderDetail.loadSelect();
		//brokerOrderDetail.loadAgreementInfo(agreementId);
	},
	loadData : function(workOrderId) {
	    common.load.load();
	    var param = {
	      'workOrderId': workOrderId
	    };
	    common.ajax({
	      url : common.root + '/workOrder/getWorkOrderDetail.do',
	      dataType : 'json',
	      contentType: 'application/json; charset=utf-8',
	      encode: false,
	      data : JSON.stringify(param),
	      loadfun : function(isloadsucc, data) {
	        common.load.hide();
	        if (isloadsucc) {
	        	// 父工单信息
	        	brokerOrderDetail.loadAgreementInfo(data.workOrder.rentalLeaseOrderId);
	        	if (!resFlag) {
					$("#comments").val(data.workOrder.subComments);
					$("#comments").attr("disabled",true);
				}
	        }
	      }
	    });
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
					$('#house_name').html(data.house_name);
					$('#agreement_type').html(data.rankType);
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
				    if (isMobile=="Y")
				    {
				 	   $('#myAwesomeDropzone').addClass('hidden');
				 	   $('#myAwesomeDropzone-view b').addClass('hidden');
				       njyc.phone.showPic(brokerOrderDetail.changeImagePath(data.file_path), 'myAwesomeDropzone-view');
					}
				    else
					{	
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
					if (data.file_path!=null&&data.file_path!="") {
						common.dropzone.init({
							id : '#myAwesomeDropzone',
							maxFiles :4,
							defimg : picArray, 
							clickEventOk: flag == 1?true:false
						});
					}else{
						common.dropzone.init({
							id : '#myAwesomeDropzone',
							maxFiles :4,
							clickEventOk: flag == 1?true:false
						});
					}
					}
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
					console.log(data);
					$('#broker').html(brokerOrderDetail.getUser(data.broker));
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
	loadSelect : function() {
		if(flag == 1)
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
				html += '<option value="' + json[i].item_id + '" >'+ json[i].item_name +'</option>';
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
	getUser : function(userMobile) {
		var name = "";
		common.ajax({
			url : common.root + '/sys/getUserName.do',
			data : {userMobile : userMobile},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					name = data.username+"&nbsp;&nbsp;联系方式："+userMobile;
				} 
			}
		});
		return name;
    },
	approve : function(returnI) {
		var msg = "是否确认审批成功？";
		if (returnI == "N") {
			var msg = "是否确认审批拒绝？";
		}
		var str=/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/;
		var comments = $("#comments").val();
		if (str.test(comments)) {
			common.alert({msg : "只允许填写汉字，字母，数字，不允许出现特殊字符！"});
			return;
		}
		common.alert({
			msg : msg,
			confirm : true,
			fun : function(action) {
				if (action) {
					common.load.load();
					var param = {'workOrderId': $('#js-work-order-id').val(),passed:returnI,dealerId:dealerId,comments:$("#comments").val()};	
					common.ajax({
						url : common.root + '/workOrder/auditBrokerOrder.do',
						dataType : 'json',
						contentType : 'application/json; charset=utf-8',
						encode : false,
						data : JSON.stringify(param),
						loadfun : function(isloadsucc, data) {
							common.load.hide();
							brokerOrderDetail.closeOpenedWin(isloadsucc,data);
						}
					});
				}
			}
		});
	},
	// 关闭  
	  closeOpenedWin: function(isloadsucc, data) {
		  console.log(data);
	    if (isloadsucc) {
	      if (data.state == 1) {
	        common.alert({ msg : '操作成功'});
	        if (isMobile == 'Y') {
	          window.location.href = 'http://manager.room1000.com?TASK_SUBMIT_SUCCESS=1';
	        } else {
	          common.closeWindow('brokerOrderDetail', 3);
	        }
	      } else {
	        common.alert({ msg : data.responseJSON.errorMsg});
	      }
	    }else{
	    	 common.alert({ msg : data.responseJSON.errorMsg});
	    }
	  },
	  uploadNbrAttachment : function() {
			var picSize = 10; // 可以上传图片数量
			var uploadPic = $('input[name="picImage"]').size();
			if (Math.abs(picSize) > uploadPic) {
				njyc.phone.selectImage((Math.abs(picSize) - uploadPic),'myAwesomeDropzone-view');
			} else {
				$('#myAwesomeDropzone').hide();
			}
		},
	  changeImagePath : function(images)
	  {
		 if (!images || images.length == 0) 
		 {
			 return '';
		 }
		 return images.replace(/,/g, '|');
	  },
};
$(brokerOrderDetail.init());