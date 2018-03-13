var agreeId="";
var orderadd = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	orderadd.initType();
		// 添加处理事件
    	orderadd.addEvent();
		//初始化图片上传
		common.dropzone.init({
			id : '#upurl',
			maxFiles:10
			//defimg:[{path:'/upload/tmp/855553.gif',first:1}].
		});
	},
   addEvent: function()
   {
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
    initType: function()
    {
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
        common.ajax({
			url : common.root + '/repairOrder/getRepairType.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc)
				{
					var html = "<option value=''>请选择...</option>";
				    var date=data.repairTypeList;
					for (var i = 0; i < date.length; i++)
		            {
						html += '<option  value="' + date[i].type + '" >' + date[i].typeName + '</option>';
		            }
					$("#childtype").html(html);
				}
			}
    	})
    },
   save:function()
   {
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
	 
		var param = {
				orderName : $('#oname').val(),// 订单名称
         	    appointmentDate : $('#oserviceTime').val(),//预约时间
				comments : $('#odesc').val(),//备注
				userName: $('#ouserName').val(),//用户名称
				userMobile: $('#ouserMobile').val(),//用户手机
				imageUrl:pathe,
				type:$('#childtype').val(),
				takeHouseOrderId:agreeId
            }
	 $("#orderadd_bnt").attr("disabled",true);
		  common.ajax({
					url : common.root
							+ '/owerRepairOrder/inputOwnerRepairOrderInfo.do',
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
								common.closeWindow('orderadd1', 3);
							} else if (data.state == -1) {
								$("#orderadd_bnt").attr("disabled", false);
								common.alert({
									msg : "操作失败"
								});
							} else if (data.state == -2) {
								$("#orderadd_bnt").attr("disabled", false);
								common.alert({
									msg : "图片上传失败"
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
					common.alert({msg : common.msg.error});
				}
			}
		});
	},
	getAgree:function(id,name,username,mobile)
	{
		agreeId=id;
		$('#oname').val("业主预约_"+name);
		$('#agreeId').val(name);
		$('#ouserMobile').val(mobile);
		$('#ouserName').val(username);
	},
};
orderadd.init();