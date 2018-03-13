 var agreeId="";
var t_grid_add = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	t_grid_add.initType();
		// 添加处理事件
    	t_grid_add.addEvent();
	},
   addEvent: function(){
        $('#t_grid_add_bnt').click(function()
        {
        	t_grid_add.save();
        });
    },
    /**
     加载区域选择
     **/
    initType: function(){
        var type="3";//区域
        var fatherid="";//上级Id
        /**
         * 区域
         * */
        common.loadArea(fatherid,type, function(json)
        {
            var html = "";
            for (var i = 0; i < json.length; i++)
            {
 				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
            }
            $('#area_id').html(html);
        });
        
        common.ajax({
    		url : common.root + '/Grid/getMangerList.do',
    		dataType : 'json',
    		data:{role_Id:"22"},
    		async : false,
    		loadfun : function(isloadsucc, json) 
    		{
    		   if (isloadsucc)
    		   {
    			   var html = " <option value='0' >请选择</option>";
    			   for ( var i = 0; i < json.length; i++) 
    			   {
    				  html += '<option value="' + json[i].id + '" >'+ json[i].name + '</option>';
    			   } 
    			   $('#account_id').html(html);
    			   $('#account_id').select2();
    		   }  
    		   else 
    		   {
    		       common.alert({msg : common.msg.error});
    		   }
    		}
    	});
    },checkCf :function()
    {
    	var returnT=true;
    	common.ajax({
      		url : common.root + '/Grid/checkName.do',
      		dataType : 'json',
      		data:{g_name: $("#g_name").val()},
      		async : false,
      		loadfun : function(isloadsucc, json) 
      		{
      		   if (isloadsucc)
      		   {
      			   if (json.state!=1) 
      			   {
      				   
      				  // $("#g_name").val("");
      				   returnT=false;
    			   }
      		   }  
      		   else 
      		   {
      		       common.alert({msg : common.msg.error});
      		   }
      		}
      	});
    	return returnT;
    },
   save:function()
   {
      if (!Validator.Validate('form2'))
      {
 	      return;
      }
     if (!t_grid_add.checkCf())
     {
    	 common.alert({msg : "当前网格名称已存在请重新填写！"});
    	 return;
	 } 
	  $("#t_grid_add_bnt").attr("disabled",true);
	  common.ajax({
        url: common.root + '/Grid/gridCreate.do',
        data:{areaid:$('#area_id').val(),g_name:$('#g_name').val(),account:$('#account_id').val()},
		dataType:'json',
        loadfun: function(isloadsucc, data)
        {
          if (isloadsucc)
          {
            if (data.state == 1) 
            {
            	common.alert({ msg:"操作成功!"});
				common.closeWindow('t_grid_add',1);
				table.refreshRedraw('grid_table');
            }
            else
            {	
            	$("#t_grid_add_bnt").attr("disabled",false);
				common.alert({ msg: common.msg.error});
			}
          }
            else 
            {
            	$("#t_grid_add_bnt").attr("disabled",false);
                common.alert({ msg: common.msg.error });
            }
        }
        });
	}
};
t_grid_add.init();