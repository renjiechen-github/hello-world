var role_add= {
		//页面初始化操作
	    init : function() {
				 // 添加处理事件
	    			role_add.addEvent();
	    			 $.ajaxSetup({cache:false});
			    	 $("#flatTree3").jstree({    
			         	"core" : {
			         	    "animation" : 0,
			         	    "check_callback" : true,
			         	    "themes" : { "stripes" : true },
			         	    'data' : {
			         	      'url' : function (node) {
			         	        return node.id === '#' ?'/role/selectmenu.do' : '/role/selectpower.do';
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
			         	    "state", "types", "wholerow","checkbox" 
			         	  ] 
			 	    });
			    	$.jstree.reference('#flatTree3').clear_state();
			 		$('#searchmenu').click(function(){
			 			var name = $('#menuname').val();
			 			$('#flatTree3').jstree(true).search(name); 
			 		});
		    },
		    addEvent: function()
		    {
		      $('#user_add_bnt').click(function(){
		    	  role_add.save();
	        });
		    },
	/**
	 * 保存操作
	 */
	save: function(){
		var ids="";
		var selected = $.jstree.reference('#flatTree3').get_selected(true);
		for(var i=0;i<selected.length;i++){
			ids+=selected[i].id+",";
		}
	  common.ajax({
	        url: common.root + '/role/saverole.do',
	        data:{
	        	ids : ids,
	        	role_name:$("#role_name").val(),
	        	role_decs:$("#role_decs").val()
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
role_add.init();