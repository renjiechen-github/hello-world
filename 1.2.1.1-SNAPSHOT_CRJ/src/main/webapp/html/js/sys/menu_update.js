var menu_update = {
		//页面初始化操作
	    init : function() {
	    	common.ajax({
				url : common.root + '/menu/select.do',
				dataType : 'json',
				async: false,
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
	    	menu_update.addEvent();
			//初始化数据
	    	menu_update.initData(); 
	    	
	    },
	    addEvent: function() {
	      $('#type_add_bnt').click(function(){
	    	  menu_update.save();
        });
	      $('#powerURL').blur(function(){
	    	  menu_update.getpowerURL();
        });
	    },
	    deletemenu: function (obj){
	    	$(obj).parent('div').parent('div').remove();
//	    		powerids +=$(obj).parent('div').parent('div').find("input[name='powerId']").val()+",";
	    	$('#powerids').val($('#powerids').val()+$(obj).parent('div').parent('div').find("input[name='powerId']").val()+",");
	    	var leng=$("input[name='cost']").length;
		  $(".rebuild").each(function(i,val){
			    $(this).attr("id","pic"+i);
		  });
	    },
	    statemenu: function (obj){
	    	$(obj).parent('div').parent('div').remove();
//	    		powerids +=$(obj).parent('div').parent('div').find("input[name='powerId']").val()+",";
	    	$('#stateids').val($('#stateids').val()+$(obj).parent('div').parent('div').find("input[name='powerId']").val()+",");
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
	    		+'<input name="powerId" id="powerId"  type="hidden"  class="form-control"/>'
				+' <label class="col-sm-2  control-label">权限名称</label>'
				+' <div class="col-sm-3">'
				+' <input name="powerName" id="powerName"  type="text"  class="form-control" maxlength="10">'
				+' </div>'
				+' <label class="col-sm-2  control-label">权限URL</label>'
				+' <div class="col-sm-3">'	
				+' <input name="powerURL" id="powerURL" type="text"  class="form-control" maxlength="100">' 
				+' </div>'
				+' <div class="col-sm-1">'
				+' <a onclick="menu_update.deletemenu(this);"><i class="fa fa-trash-o"></i>删除..</a>'	
				+' </div>'
				+' </div>';
	    	$('#select').append(html);
	    },
		  //获取权限数据
	    getpowerURL:function()
		{
	    	alert(dsad);
			var powerURL = $('#powerURL').val();
			common.ajax({
				url : common.root + '/menu/selectpowerurl.do',
				data : {powerURL : powerURL},
				dataType : 'json',
				loadfun : function(isloadsucc, data)
				{
					if (isloadsucc)
					{
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
	    //加载修改数据信息
		initData : function() {
				common.ajax({
					url : common.root + '/menu/selectnew.do',
					data : {id : inst.get_selected()},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						 $("#id").val(data.list[0].menu_id);
						 $('#menuName').val(data.list[0].menu_name);
						 $('#menuUrl').val(data.list[0].menu_url);
						 $('#superId').val(data.list[0].super_id);
						 $('#order_id').val(data.list[0].order_id);
						 $(".js-example-basic-single").select2();
					}
				});
				common.ajax({
					url : common.root + '/menu/selectpower.do',
					data : {id : inst.get_selected()},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						 for (var i = 0; i < data.list.length; i++) {
				    	var ids = "pic"+($("input[name='cost']").length);
				    	var html="";
				    	if(data.list[i].state==1){
				    	html=' <div class="row"> '
							+' <label class="col-sm-2  control-label">权限名称</label>'
							+'<input name="powerId" id="powerId"  type="hidden"  value="'+data.list[i].power_id+'"  class="form-control"/>'
							+' <div class="col-sm-3">'
							+' <input name="powerName" id="powerName"  type="text" value="'+data.list[i].power_name+'"  class="form-control" maxlength="10">'
							+' </div>'
							+' <label class="col-sm-2  control-label">权限URL</label>'
							+' <div class="col-sm-3">'	
							+' <input name="powerURL" id="powerURL" type="text" value="'+data.list[i].power_url+'" class="form-control" maxlength="100">' 
							+' </div>'
							+' <div class="col-sm-1"style="margin-top: 10px">'
							+' <a onclick="menu_update.deletemenu(this);" ><i class="fa fa-trash-o"></i>删除..</a>'	
							+' </div>'
							+' </div>';
				    	}else{
				    		html=' <div class="row"> '
								+' <label class="col-sm-2  control-label">权限名称</label>'
								+'<input name="powerId" id="powerId"  type="hidden"  value="'+data.list[i].power_id+'"  class="form-control"/>'
								+' <div class="col-sm-3">'
								+' <input name="powerName" id="powerName"  type="text" value="'+data.list[i].power_name+'"  class="form-control" maxlength="10">'
								+' </div>'
								+' <label class="col-sm-2  control-label">权限URL</label>'
								+' <div class="col-sm-3">'	
								+' <input name="powerURL" id="powerURL" type="text" value="'+data.list[i].power_url+'" class="form-control" maxlength="100">' 
								+' </div>'
								+' <div class="col-sm-1"style="margin-top: 10px">'
								+' <a onclick="menu_update.statemenu(this);">启用..</a>'	
								+' </div>'
								+' </div>';
				    	}
				    	$('#select').append(html);
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
//				layer.tips('我是另外一个tips，只不过我长得跟之前那位稍有些不一样。', '吸附元素选择器', {
//					  tips: [1, '#3595CC'],
//					  time: 4000
//					});
				 return;
			}
//	  		var powerId="";//权限主键
//			$("input[name='powerId']").each(function()
//			{
//				if ($(this).val()==null||$(this).val()=="") {
//					 powerId +="X"+",";  
//				} else{
//					 powerId +=$(this).val()+",";  
//				}
//			}); 
//			powerId=powerId.split(0, powerId.length-1);
//	  		var powerName="";//权限名称
//			$("input[name='powerName']").each(function()
//			{
//				if ($(this).val()+","==null||$(this).val()=="") {
//					powerName  +="X"+",";  
//				} else{
//					    powerName  +=$(this).val()+",";  
//				}
//			}); 
//			powerName=powerName.split(0, powerName.length-1);
//			var powerURL="";//权限url
//			$("input[name='powerURL']").each(  
//			function(){
//				if ($(this).val()+","==null||$(this).val()=="") {
//					powerURL  +="X"+",";  
//				} else{
//					powerURL  +=$(this).val()+",";  
//				}
//			}); 
//			powerURL=powerURL.split(0, powerURL.length-1);
		  common.ajax({
		        url: common.root + '/menu/updatemenu.do',
		        data:$('#form2').serialize(),
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
menu_update.init();