var parr=new Array();
var role_update= {
		//页面初始化操作
	    init : function() {
				 // 添加处理事件
	    			role_update.addEvent();
			    	 role_update.initData();
			    	 this.bind();
		    },
		    addEvent: function()
		    {
		      $('#user_add_bnt').click(function(){
		    	  role_update.save();
	        });
		    },
		    //加载修改数据信息
			initData:function(){
				//加载数据
				if(rowdata != null){
					 $("#role_id").val(rowdata.role_id);
					 $('#role_name')[0].innerHTML=rowdata.role_name;
				};
				//加载权限数据
				common.ajax({
					url : common.root + '/role/findpower.do',
					data : {id : rowdata.role_id},
					dataType : 'json',
					async: false,
					loadfun : function(isloadsucc, data)
					{
						if (isloadsucc) {
			                for (var i = 0; i < data.list.length; i++) {
			                	parr.push(data.list[i].power_id);
			                }
			                alert(parr);
						}
					}
				});
		    	 $("#flatTree3").jstree({    
			         	"core" : {
			         	    "animation" : 0,
			         	    "check_callback" : true,
			         	    "themes" : { "stripes" : true },
			         	    'data' : {
			         	      'url' : function (node) {
			         	        return node.id === '#' ?'/role/selectmenu.do?role_id='+rowdata.role_id : '/role/selectpower.do?role_id='+rowdata.role_id;
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
			         	    "state", "types", "wholerow",
			         	    "checkbox"
			         	  ] 
			 	    });
		    	 	$.jstree.reference('#flatTree3').clear_state();
			 		$('#searchmenu').click(function(){
			 			var name = $('#menuname').val();
			 			$('#flatTree3').jstree(true).search(name); 
			 		});
			},
			bind : function () {
				$('#flatTree3').bind("loaded.jstree", function (e, data) {
		    		( $(this).data('jstree')).open_all();
		    		 });
//				$('#flatTree3').on('click.jstree', function(event) {  
//					var eventNodeName = event.target.nodeName; 
//		            if (eventNodeName == 'INS') {
//		                return;               
//		            } else if (eventNodeName == 'A') {                   
//		                var $subject = $(event.target).parent(); 
//		                var id = $subject .attr('id');
//		                var rerturni=true ;
//		                for (var i = 0; i < parr.length; i++) {
//							if (parr[i]==id) {
//								parr.splice(i, 1);
//								rerturni=false;
//							}
//						}
//		                if(rerturni){
//		                	parr.push(id);
//		                }
//		            }
//		            else if(eventNodeName == 'I'){
//		            	var $subject = $(event.target).parent().parent(); 
//		            	var id = $subject .attr('id');
//		            	 var rerturni=true ;
//			                for (var i = 0; i < parr.length; i++) {
//								if (parr[i]==id) {
//									parr.splice(i, 1);
//									rerturni=false;
//								}
//							}
//			                if(rerturni){
//			                	parr.push(id);
//			                }
//		            } 
//		        });  
			},
	/**
	 * 保存操作
	 */
	save: function(){
		
//		var prrs="";
//		  for (var i = 0; i < parr.length; i++) {
//			  prrs +=parr[i]+",";
//		  }
		var ids="";
		var selected = $.jstree.reference('#flatTree3').get_selected(true);
		for(var i=0;i<selected.length;i++){
			ids+=selected[i].id+",";
		}
		  prrs = ids.substring(0, ids.length - 1);
	  common.ajax({
	        url: common.root + '/role/updaterole.do',
	        data:{
	        	role_id:$('#role_id').val(),
	        	parr : prrs
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
role_update.init();