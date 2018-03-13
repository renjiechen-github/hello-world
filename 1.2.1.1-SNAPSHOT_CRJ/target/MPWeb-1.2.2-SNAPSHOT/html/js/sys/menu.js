 var sysmenu={
 	
	init:function(){
		 //新增操作
        $('.bnt_add').click(function(){
        	sysmenu.add();
        });
        $.jstree.defaults.contextmenu = {
			select_node : true,
			show_at_node : true,
			items : function (o, cb){
				return {
					"remove" : { 
						"separator_before"	: true,
						"icon"				: false,
						"separator_after"	: false,
						"_disabled"			:false , //(this.check("delete_node", data.reference, this.get_parent(data.reference), "")),
						"label"				: "删除",
						"action"			: function (data) {
							var inst = $.jstree.reference(data.reference),
							obj = inst.get_node(data.reference);
							if(inst.is_selected(obj)) {
//    								console.log(inst.get_selected());
//    								inst.delete_node(inst.get_selected());
				                common.ajax({
				                    url: common.root + '/menu/delete.do',
				                    data: {
				                        id: inst.get_selected()
				                    },
				                    dataType: 'json',
				                    loadfun: function(isloadsucc, data){
				                        if (isloadsucc) {
				                            if (data.state == '1') {
				                                common.alert({
				                                    msg: '删除成功',
				                                    fun: function(){
				        								inst.delete_node(inst.get_selected());
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
							}
							else {
								inst.delete_node(obj);
							}
						}
					},
					"Edit" : {
						"separator_before"	: true,
						"icon"				: false,
						"separator_after"	: false,
						"_disabled"			: false, //(this.check("delete_node", data.reference, this.get_parent(data.reference), "")),
						"label"				: "修改",
						"action"			: function (data) {
							 inst = $.jstree.reference(data.reference)
					        //打开对应的窗口
					        common.openWindow({
					            type: 3,
					            name:'userupdate',
					            title: '菜单修改',
					            url: '/html/pages/sys/menu_update.html'
					        });
						}
					},
				};	
			}
		};
//		var jstree = $('#flatTree3').jstree({});
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
	/**
	 * 菜单新增
	 */
	add: function(){
        common.openWindow({
            type: 3,
            name:'sysmenuadd',
            title: '菜单新增',
            url: '/html/pages/sys/menu_add.html'
        });
    }	
 };
$(function(){
	sysmenu.init();
});
