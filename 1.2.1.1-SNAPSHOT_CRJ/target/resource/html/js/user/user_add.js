var user_add = {
		//页面初始化操作
	    init : function() {
	    	// 时间按钮选择事件
			var nowTemp = new Date();
			$('#groupDate').daterangepicker(
					{
						startDate : nowTemp.getFullYear() + '-'
								+ (nowTemp.getMonth() + 1) + '-02',
						singleDatePicker : true,
						format : 'YYYY-MM-DD'
					}, function(start, end, label) {
						console.log(start.toISOString(), end.toISOString(), label);
					});
					//加载性别
				   common.loadItem('USER.SEX', function(json){
			            var html = "";
			            for (var i = 0; i < json.length; i++) {
			 				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
			            }
			            $('#sex').append(html);
			        });	
				 
				 // 添加处理事件
				   user_add.addEvent();
		    },
		    addEvent: function()
		    {
		      $('#user_add_bnt').click(function(){
		    	  user_add.save();
	        });
		      $('#mobile').blur(function(){
		    	  user_add.getUserName();
	        });
		    },
			  //获取用户名称
			getUserName:function()
			{
				var mobile = $('#mobile').val();
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
							if(data.id!=null&&data.id!=""){
								common.alert({
									msg :"该手机以在系统存在，请更换手机号码"
								});
							}
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
	/**
	 * 保存操作
	 */
	save: function(){
		if (!Validator.Validate('form2')){
		    	  return;
		}
	  common.ajax({
	        url: common.root + '/user/create.do',
	        data:{
	        	username : $('#username').val(),
	        	sex : $('#sex').val(),
	        	mobile : $('#mobile').val(),
	        	certificateno : $('#certificateno').val(),
	        	qq : $('#qq').val(),
	        	email : $('#email').val(),
	        	wechat : $('#wechat').val(),
	        	birthday : $('#groupDate').val(),
	        },
			dataType:'json',
	        loadfun: function(isloadsucc, data){
	            if (isloadsucc) {
	                if (data.state == 1) {//操作成功
	             		common.alert({
							msg:'操作成功'
						});
						common.closeWindow('addgroupadd',3);
	                }else{
	                	$("#group_add_bnt").attr("disabled",false);
						common.alert({
	                        msg: common.msg.error
	                    });
					}
	            }else {
	            	$("#group_add_bnt").attr("disabled",false);
	                common.alert({
	                    msg: common.msg.error
	                });
	            }
	        }
	    });
	}
}
 user_add.init();