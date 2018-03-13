 var modals=$('#rankModal');
 var rankid="";
var orderadd = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	//orderadd.initType();
		// 添加处理事件
    	orderadd.addEvent();
    	// 时间按钮选择事件
    	//orderadd.initDateTimeP();
    	// 时间按钮选择事件
		var nowTemp = new Date();
		$('#oserviceTime').daterangepicker(
		{
		   startDate : nowTemp.getFullYear() + '-'+ (nowTemp.getMonth() + 1) + '-02',
		   singleDatePicker : true,
		   timePicker12Hour:false,
		   timePicker: true,
		   timePickerIncrement: 10,
		   format : 'YYYY-MM-DD HH:mm'
		}, function(start, end, label)
		{
		   console.log(start.toISOString(), end.toISOString(), label);
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
    /**
     加载区域选择
     **/
    initType: function(){
    },
   save:function(){
	   if (!Validator.Validate('form2'))
	   {
     	return;
       }
		 common.ajax({
	            url: common.root + '/Order/createOrder.do',
	            data:{
	            	orderName:$('#oname').val(),
	            	ouserMobile:$('#ouserMobile').val(),
	            	ouserName:$('#ouserName').val(),
	            	oserviceTime:$('#oserviceTime').val(),
	            	odesc:$('#odesc').val(),
	            	correspondent:0,
	            	otype:3,
	            	ocost:0,
	            	childtype:0
	            },
				dataType:'json',
	            loadfun: function(isloadsucc, data){
	                if (isloadsucc)
	                {
	                    if (data.state == 1) 
	                    {//操作成功
	                 		common.alert({msg:'操作成功'});
							common.closeWindow('orderadd3',3);
	                    }
	                    else
	                    {
	                    	$("#group_add_bnt").attr("disabled",false);
							common.alert({ msg: common.msg.error});
						}
	                }
	                else 
	                {
	                	$("#group_add_bnt").attr("disabled",false);
	                    common.alert({ msg: common.msg.error });
	                }
	            }
	        });
	},//获取用户名称
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
			loadfun : function(isloadsucc, data) {
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