//var workOrderId = "";
var orderadd = {
    //页面初始化操作
	    init : function() {
		// 添加处理事件
    	orderadd.addEvent();
    	
  		// 初始化图片上传
  		common.dropzone.init({
  			id : '#upurl',
  			maxFiles : 10
  		});    	
	},
   addEvent: function(){
        $('#orderadd_bnt').click(function()
        {
        	orderadd.save();
        });
        $('#ouserMobile').blur(function()
        { 
             $('#ouserName').val(orderadd.getUserName());	
        });
    },
   save:function(){
  	 
 		var filepath = common.dropzone.getFiles('#upurl');
		var pathe = "";
		var returnI = false;
		for (var i = 0; i < filepath.length; i++) {
			if (filepath[i].fisrt == 1) {
				pathe = filepath[i].path + ',' + pathe;
			} else {
				pathe += filepath[i].path + ",";
			}
			returnI = true;
		}
		if (returnI) {
			pathe = pathe.substring(0, pathe.length - 1);
		}  	 
  	 
	   if (!Validator.Validate('form2'))
	   {
     	return;
       }
		var param = {
				    orderName : $('#oname').val(),// 订单名称
	            	appointmentDate : $('#oserviceTime').val(),//预约时间
				//	workOrderId : workOrderId,//流程id
					comments : $('#odesc').val(),//备注
					userName: $('#ouserName').val(),//用户名称
					'imageUrl' : pathe,
					userMobile: $('#ouserMobile').val()//用户手机
	            }
	   
	   $("#orderadd_bnt").attr("disabled",true);
		 common.ajax({
			url : common.root + '/otherOrder/inputOtherOrderInfo.do',
			dataType : 'json',
			contentType : 'application/json; charset=utf-8',
			encode : false,
			data : JSON.stringify(param),
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
					if (data.state == 1) { // 操作成功
						common.alert({
							msg : '操作成功'
						});
						common.closeWindow('orderadd3', 3);
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
	},// 获取用户名称
	getUserName:function()
	{
		var mobile = $('#ouserMobile').val();
		if(mobile == '' || mobile.length != 11)
		{
			$('#ouserName').removeAttr("readonly","readonly");
			return ;
		}
		common.ajax({
			url : common.root + '/sys/getUserName.do',
			data : {userMobile : mobile},
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc)
				{
					var name = data.username;
					$('#ouserName').val(name);
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
orderadd.init();