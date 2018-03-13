 var power={
 	
	init:function(){
		this.bind();
		 // 添加处理事件
		power.addEvent();
        $("#flatTree3").jstree({    
        	"core" : {
        	    "animation" : 0,
        	    "check_callback" : true,
        	    "themes" : { "stripes" : true },
        	    'data' : {
        	      'url' : function (node) {
        	        return node.id === '#' ?'/menu/selects.do' : '/menu/selectchildren.do';
        	      },
        	      'data' : function (node) {
        	        return { 'id' : node.id };
        	      }
        	    }
        	  },
        	  "types" : {
        	    "#" : {
        	      "max_children" : 1,
        	      "max_depth" : 4,
        	      "valid_children" : ["root"]
        	    },
        	    "root" : {
        	      "icon" : "/static/3.3.2/assets/images/tree_icon.png",
        	      "valid_children" : ["default"]
        	    },
        	    "default" : {
        	      "valid_children" : ["default","file"]
        	    },
        	    "file" : {
        	      "icon" : "glyphicon glyphicon-file",
        	      "valid_children" : []
        	    }
        	  },
        	  "plugins" : [
        	    "contextmenu", "dnd", "search",
        	    "state", "types", "wholerow"
        	  ] 
	    });
		$('#searchmenu').click(function(){
			var name = $('#menuname').val();
			$('#flatTree3').jstree(true).search(name); 
		});
	}, 
	  addEvent: function(){
			 $('#searchflat').click(function(){
				 power.save();
	       });
			 $("#flatTree2").change(function(){
				 power.click();
	       });
	  },
	bind : function () {
		$('#flatTree3').on('click.jstree', function(event) {  
			var eventNodeName = event.target.nodeName;               
            if (eventNodeName == 'INS') {
                return;               
            } else if (eventNodeName == 'A') {                   
                var $subject = $(event.target).parent(); 
                var id = $subject .attr('id');
                $("#id").val($subject .attr('id'));
                //根据菜单信息获取权限数据
				common.ajax({
					url : common.root + '/power/selectPower.do',
					data : {id : id},
					async: false,
					dataType : 'json',
					loadfun : function(isloadsucc, data)
					{
						if (isloadsucc)
						{
						var html = "";
						$("#power_id").val(data.list[0].power_id);
		                for (var i = 0; i < data.list.length; i++) {
		                		if(i==0){
		                			if(data.list[i].power_name==null||data.list[i].power_name == ""){
		                				if(data.list[i].state==0){
		                					html += '<label><input class="power" name="power" type="radio" checked="checked" disabled="disabled"  value="' + data.list[i].power_id+ '" /><span style="color:red">权限数据</span></label></br>';
		                				}else{
		                					html += '<label><input class="power" name="power" type="radio" checked="checked"  value="' + data.list[i].power_id+ '" />权限数据</label></br>';
		                				}
		                			}else{
		                				if(data.list[i].state==0){
		                					html += '<label><input class="power" name="power" type="radio" checked="checked" disabled="disabled"  value="' + data.list[i].power_id+ '" /><span style="color:red">' + data.list[i].power_name+ '</span></label></br>';
		                				}else{
		                					html += '<label><input class="power" name="power" type="radio" checked="checked"  value="' + data.list[i].power_id+ '" />' + data.list[i].power_name+ '</label></br>';
		                				}
		                			}
		                		}else{
		                			if(data.list[i].power_name==null||data.list[i].power_name == ""){
		                				if(data.list[i].state==0){
		                					html += '<label><input class="power" name="power" type="radio" disabled="disabled" value="' + data.list[i].power_id+ '" /><span style="color:red" onclick="power.start(this)">权限数据</span></label></br>';
		                				}else{
		                					html += '<label><input class="power" name="power" type="radio"   value="' + data.list[i].power_id+ '" />权限数据</label></br>';
		                				}
		                			}else{
		                				if(data.list[i].state==0){
		                					html += '<label><input class="power" name="power" type="radio"  disabled="disabled" value="' + data.list[i].power_id+ '" /><span style="color:red" onclick="power.start(this)">' + data.list[i].power_name+ '</span></label></br>';
		                				}else{
		                					html += '<label><input class="power" name="power" type="radio"  value="' + data.list[i].power_id+ '" />' + data.list[i].power_name+ '</label></br>';
		                				}
		                			}
		                		}
		                }
		                $('#flatTree2')[0].innerHTML=html;
		                } 
					}
				});
				//获取角色信息
				common.ajax({
					url : common.root + '/account/selectRole.do',
					data : {userMobile : id},
					dataType : 'json',
					async: false,
					loadfun : function(isloadsucc, data)
					{
						if (isloadsucc) {
							var html = "";
			                for (var i = 0; i < data.list.length; i++) {
			     				html += '<label><input name="role" type="checkbox" value="' + data.list[i].role_id+ '" />' + data.list[i].role_name + ' </label> </br>';
			                }
			                $('#flatTree1')[0].innerHTML=html;
						}
					}
				});
				common.ajax({
					url : common.root + '/power/savePowers.do',
					data : {id : $("#power_id").val()},
					dataType : 'json',
					async: false,
					loadfun : function(isloadsucc, data)
					{
							for(var i=0;i<data.list.length;i++){
								$("input[name='role'][value='"+data.list[i].role_id+"']").attr("checked","true");
							}
					}
				});
            } 
        });  
	},
	start: function(obj){
	  common.ajax({
	        url: common.root + '/power/startPower.do',
	        data:{
	        	id : $(obj).parent().find("input").val(),
	        },
			dataType:'json',
	        loadfun: function(isloadsucc, data){
                if (isloadsucc) {
                    if (data.state == '1') {
                        common.alert({
                            msg: '启用成功',
                            fun: function(){
                            	 $('#flatTree2')[0].innerHTML="";
                            	 $('#flatTree1')[0].innerHTML="";
                            }
                        });
                    }  else {
                        common.alert({
                            msg: common.msg.error
                        });
                    }
                }  else {
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
	    });
	},
	click: function(){
		common.ajax({
			url : common.root + '/power/savePowers.do',
			data : {id : $('input[type="radio"]:checked').val()},
			dataType : 'json',
			async: false,
			loadfun : function(isloadsucc, data)
			{
				console.log(data);
				$("input[name='role']").attr("checked",false);
					for(var i=0;i<data.list.length;i++){
						$("input[name='role'][value='"+data.list[i].role_id+"']").attr("checked","true");
					}
			}
		});
	},
	/**
	 * 保存操作
	 */
	save: function(){
		    var role="";//角色
			$("input[name='role']:checked").each(function()
			{
				role  +=$(this).val()+",";  
			}); 
			role=role.split(0, role.length-1);
	  common.ajax({
	        url: common.root + '/power/savePower.do',
	        data:{
	        	role:role,
	        	power:$('input[name="power"]:checked').val(),
	        	id:$("#id").val()
	        },
			dataType:'json',
	        loadfun: function(isloadsucc, data){
	            if (isloadsucc) {
	                if (data.state == 1) {//操作成功
	             		common.alert({
							msg:'操作成功'
						});
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
$(function(){
	power.init();
});
