var menu_add = {
		//页面初始化操作
	    init : function() {
	    	common.ajax({
				url : common.root + '/menu/select.do',
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
	                var html = "";
	                for (var i = 0; i < data.list.length; i++) {
	     				html += '<option  value="' + data.list[i].menu_id+ '" >' + data.list[i].menu_name + '</option>';
	                }
	                $('#superId').append(html);
				}
				}
	    	});
	    	 // 添加处理事件
	    	menu_add.addEvent();
	    	$(".js-example-basic-single").select2();
	    },
	    addEvent: function()
	    {
	      $('#type_add_bnt').click(function(){
	    	  menu_add.save();
        });
	    },
	    deletemenu: function (obj){
	    	$(obj).parent('div').parent('div').remove();
	    	
	    	var leng=$("input[name='cost']").length;
				  $(".rebuild").each(function(i,val){
					    $(this).attr("id","pic"+i);
					  });
	    },
	    /**
	     *添加
	     */
	    addmenu: function ()
	    {
	    	var ids = "pic"+($("input[name='cost']").length);
	    	var html="";
	    	html=' <div class="row"> '
				+' <label class="col-sm-2  control-label">权限名称</label>'
				+' <div class="col-sm-3">'
				+' <input name="powerName" id="powerName"  dataType="Require" msg="请填写权限名称。"  type="text"  class="form-control" maxlength="10">'
				+' </div>'
				+' <label class="col-sm-2  control-label">权限URL</label>'
				+' <div class="col-sm-3">'	
				+' <input name="powerURL" onblur=" menu_add.getpowerURL();" id="powerURL" dataType="Require" msg="请填写权限URL。" type="text"  class="form-control" maxlength="100">' 
				+' </div>'
				+' <div class="col-sm-1" style="margin-top: 10px">'
				+' <a onclick="menu_add.deletemenu(this);"><i class="fa fa-trash-o"></i>删除..</a>'	
				+' </div>'
				+' </div>';
	    	$('#select').append(html);
	    },
		  //获取权限数据
	    getpowerURL:function()
		{
			var powerURL = $('#powerURL').val();
			common.ajax({
				url : common.root + '/menu/selectpowerurl.do',
				data : {powerURL : powerURL},
				dataType : 'json',
				loadfun : function(isloadsucc, data)
				{
					if (isloadsucc)
					{
						console.log(data);
						if(data!=null&&data!=""){
							common.alert({
								msg :"该权限以在系统存在，请更换"
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
	    save: function(){
			if (!Validator.Validate('form2'))
			{
			    	  return;
			}
			var superId=$("#superId").val();
			var menuUrl=$("#menuUrl").val();
			if(!superId==""&&menuUrl==""){
				common.alert({
					msg:'请填写菜单路径'
				});
				 return;
			}
	  		var powerName="";//权限名称
			$("input[name='powerName']").each(
			function(){		
						powerName +=$(this).val()+",";  
			}); 
			powerName=powerName.split(0, powerName.length-1);
			var powerURL="";//权限url
			$("input[name='powerURL']").each(  
			function(){
				powerURL +=$(this).val()+",";  
			}); 
			powerURL=powerURL.split(0, powerURL.length-1);
		  common.ajax({
		        url: common.root + '/menu/create.do',
		        data:{
		        	superId : $('#superId').val(),
		        	menuName : $('#menuName').val(),
		        	menuUrl : $('#menuUrl').val(),
		        	order_id : $('#order_id').val(),
		        	Namepower:powerName,
		        	URLpower:powerURL
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
};
menu_add.init();