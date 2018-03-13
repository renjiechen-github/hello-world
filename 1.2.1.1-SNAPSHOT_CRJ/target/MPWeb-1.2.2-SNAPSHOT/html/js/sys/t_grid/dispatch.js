 var rankid="";
var dispatch =
{
   init : function() 
   {
	  dispatch.initData();
	  dispatch.addEvent();
   },
   addEvent: function()
   {
        $('#orderadd_bnt').click(function()
        {
        	dispatch.save();
        });
    },
    /**
     加载区域选择
     **/
    initData: function()
    {
    	common.ajax({
    		url : common.root + '/Grid/getMangerList.do',
    		dataType : 'json',
    		data:{role_Id:"22"},
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
   save:function()
   {
	   $("#orderadd_bnt").attr("disabled", true);
		 common.ajax({
	            url: common.root + '/Grid/dispatch.do',
	            data:{
	            	oldAccount:rowdata.acid==null||rowdata.acid==''?0:rowdata.acid,
	            	g_id:rowdata.g_id,
	            	account:$('#account').val(),
	            },
				dataType:'json',
	            loadfun: function(isloadsucc, data)
	            {
	                if (isloadsucc)
	                {
	                    if (data.state == 1) 
	                    {
	                    	common.alert({ msg:"操作成功!"});
	                    	common.closeWindow('dispatch',1);
	                        table.refreshRedraw('grid_table');
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