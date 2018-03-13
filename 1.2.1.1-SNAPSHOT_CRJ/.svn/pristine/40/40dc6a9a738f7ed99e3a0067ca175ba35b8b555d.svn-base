 var rankid="";
var dispatch = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	dispatch.initType();
		// 添加处理事件
    	dispatch.addEvent();
	},
   addEvent: function(){
        $('#orderadd_bnt').click(function()
        {
        	dispatch.save();
        });
    },
    /**
     加载区域选择
     **/
    initType: function(){
    	var role ="";
    	switch (rowdata.order_type) {
		case 0:
			role="22";
			break;
		case 1:
			role="26,28";
			break;
		case 2:
			role="26,28";
			break;
		case 3:
			role="27";
			break;
		case 4:
			role="27,22";
			break;
		case 5:
			role="26,28,27";
			break;
		case 6:
			role="26,28";
			break;
		case 7:
			role="22";
			break;
		case 9:
			role="26,28";
			break;
		}
    	common.ajax({
    		url : common.root + '/sys/getMangerList.do',
    		dataType : 'json',
    		data:{role_Id:role},
    		async : false,
    		loadfun : function(isloadsucc, json) 
    		{
    		  if (isloadsucc)
    		  {
    			var html = "请选择...";
    			for ( var i = 0; i < json.length; i++) 
    			{
    				html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
    			}
    			$('#account').append(html);
    			$('#account').select2();
    		  } 
    		  else 
    		  {
    		   common.alert({msg : common.msg.error});
    		  }
    		}
    	});
    },
   save:function(){
	   if (!Validator.Validate('form2'))
	   {
     	return;
       }
	   $("#orderadd_bnt").attr("disabled", true);
		 common.ajax({
	            url: common.root + '/Order/dispatch.do',
	            data:{
	            	id:rowdata.id,
	            	odesc:$('#odesc').val(),
	            	account:$('#account').val(),
	            	otype:rowdata.order_type,
	            	ocode:rowdata.order_code
	            },
				dataType:'json',
	            loadfun: function(isloadsucc, data){
	                if (isloadsucc)
	                {
	                    if (data.state == 1) 
	                    {//操作成功
	                 		common.alert({msg:'操作成功'});
	                 		$("#group_add_bnt").attr("disabled",false);
							common.closeWindow('dispatch',1);
							try {
								//common.closeWindow('orderview'+rowdata.order_type,3);
								table.refreshRedraw('order_table');
							} catch (e) {
								console.log("22222222222222222222222222221111");
							}
	                    }
	                    else if (data.state == -4) 
	                    {
	                    	common.alert({msg:'当前状态不允许派单'});
	                    	$('#orderadd_bnt').removeAttr("disabled");
	                    }	
	                    else
	                    {
							common.alert({ msg: common.msg.error});
							$("#orderadd_bnt").attr("disabled",false);
						}
	                }
	                else 
	                {
	                    common.alert({ msg: common.msg.error });
	                    $("#orderadd_bnt").attr("disabled",false);
	                }
	            }
	        });
	},
};
dispatch.init();