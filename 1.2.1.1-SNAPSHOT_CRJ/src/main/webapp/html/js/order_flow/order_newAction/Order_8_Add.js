var agreeId="";
var orderadd = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	orderadd.initType();
		// 添加处理事件
    	orderadd.addEvent();
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
         * 加载退租类型
         * */
        common.ajax({
			url : common.root + '/ownerCancelLeaseOrder/getOwnerCancelLeaseOrderType.do',
			dataType : 'json',
			loadfun : function(isloadsucc, data)
			{
				if (isloadsucc)
				{
					var html = "<option value=''>请选择...</option>";
				    var date=data.ownerCancelLeaseOrderTypeList;
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
	  var param = {
				orderName : $('#oname').val(),// 订单名称
         	    appointmentDate : $('#oserviceTime').val()+":00",//预约时间
				comments : $('#odesc').val(),//备注
				userName: $('#ouserName').val(),//用户名称
				userMobile: $('#ouserMobile').val(),//用户手机
				type:$('#childtype').val(),
				takeHouseOrderId:agreeId
            }
	       $("#orderadd_bnt").attr("disabled",true);
		  common.ajax({
					url : common.root + '/ownerCancelLeaseOrder/inputOwnerCancelLeaseOrderInfo.do',
					dataType : 'json',
					contentType : 'application/json; charset=utf-8',
					encode : false,
					data : JSON.stringify(param),
					loadfun : function(isloadsucc, data)
					{
						console.log(data);
						if (isloadsucc)
						{
							if (data.state == 1)
							{ // 操作成功
								common.alert({msg : '操作成功'});
								common.closeWindow('orderadd8', 3);
							}else{
								common.alert({msg : data.errorMsg});
							}
						} 
						else
						{
							$("#orderadd_bnt").attr("disabled", false);
							common.alert({msg : data.errorMsg});
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
		$('#oname').val("新增业主退租_"+name);
		$('#agreeId').val(name);
		$('#ouserMobile').val(mobile);
		$('#ouserName').val(username);
	},
};
orderadd.init();