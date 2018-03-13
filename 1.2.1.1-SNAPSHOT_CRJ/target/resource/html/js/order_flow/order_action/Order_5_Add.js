 var agreeId="";
var orderadd = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	orderadd.initType();
		// 添加处理事件
    	orderadd.addEvent();
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
		//初始化图片上传
		common.dropzone.init({
			id : '#upurl',
			maxFiles:10
			//defimg:[{path:'/upload/tmp/855553.gif',first:1}].
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
            $('#area').append(html);
        });
        /**
         * 加载维修类型
         * */
        common.loadItem('ORDERSERVICE.TYPE', function(json)
        {
            var html = "";
            for (var i = 0; i < json.length; i++) 
            {
     		   html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#childtype').append(html);
        });
        $('#ocost').val(0.0);  
    },
   save:function(){
	   if (!Validator.Validate('form2'))
	   {
     	return;
       }
	   
	var filepath = common.dropzone.getFiles('#upurl');
	var pathe = "";
	var returnI = false;
	for ( var i = 0; i < filepath.length; i++) 
	{
		if(filepath[i].fisrt==1)
		{
			pathe = filepath[i].path + ',' + pathe;
		}
		else
		{
			pathe += filepath[i].path + ",";
		}
		returnI = true;
	}
	if (returnI)
	{
		pathe = pathe.substring(0, pathe.length - 1);
	}
	
	$("#orderadd_bnt").attr("disabled",true);
	 common.ajax({
        url: common.root + '/Order/createOrder.do',
        data:{
        	orderName:$('#oname').val(),
        	correspondent:agreeId,
        	ouserMobile:$('#ouserMobile').val(),
        	ouserName:$('#ouserName').val(),
        	oserviceTime:$('#oserviceTime').val(),
        	ocost:0,
        	childtype:$('#childtype').val(),
        	odesc:$('#odesc').val(),
        	upurl:pathe,
        	otype:5
             },
		dataType:'json',
        loadfun: function(isloadsucc, data)
        {
          if (isloadsucc)
          {
            if (data.state == 1) 
            {//操作成功
         		common.alert({msg:'操作成功'});
				common.closeWindow('orderadd1',3);
            }
            else if(data.state == -1)
            {
            	$("#orderadd_bnt").attr("disabled",false);
				common.alert({ msg: "操作失败"});
			} else if(data.state == -2)
            {
            	$("#orderadd_bnt").attr("disabled",false);
				common.alert({ msg: "图片上传失败"});
			} else if(data.state == -3)
            {
            	$("#orderadd_bnt").attr("disabled",false);
				common.alert({ msg: "财务新增错误"});
			}
          }
            else 
            {
            	$("#orderadd_bnt").attr("disabled",false);
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
	getAgree:function(id,name,username,mobile)
	{
		agreeId=id;
		$('#agreeId').val(name);
		$('#oname').val("业主报修-" + name);
		$('#ouserMobile').val(mobile);
		$('#ouserName').val(username);
	},
};
orderadd.init();