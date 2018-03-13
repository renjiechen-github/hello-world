var flag = 0;
var group = {
    init: function(){
    	  //查询小区类型----区域选项
    	group.initType();
        //创建表格
    	group.createTable();
    	 //新增操作
        $('.bnt_add').click(function(){
        	group.add();
        });
        //小区类型选择查询
        $('#group_type').change(function(){
        	 $('#search').click();
        });
      //区域选择查询
        $('#areaid').change(function(){
       	 $('#search').click();
       });
        //状态选择查询
        $('#status').change(function(){
       	 $('#search').click();
       });
        
        
     // enter监听事件
//    	$('#group_name').keydown(function(e) {
//    		if (e.which == "13") {
//    			// var focusActId = document.activeElement.id;获取焦点ID，根据ID判断执行事件
//    			$("#search").click();
//    			return false;// 禁用回车事件
//    		}
//    	});
        
    	
        $("#showTipMsg").animate({
        	opacity: 'hide',
        },20000);
    },
    /**
    加载小区类型数据
    **/
   initType: function(){
       common.loadItem('GROUP.TYPE', function(json){
           var html = "<option value=''>请选择...</option>";
           for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
           }
           $('#group_type').html(html);
       });
       /**
        * 加载小区状态
        * */
       common.loadItem('GROUP.STATUS', function(json){
    	   var html = "<option value=''>请选择...</option>";
           for (var i = 0; i < json.length; i++) {
        	   if(flag ==1 && json[i].item_id == 1)
        	   {
        		   html += '<option  value="' + json[i].item_id + '" selected>' + json[i].item_name + '</option>';
        	   }
        	   else
        	   {
        		   html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
        	   }
           }
           $('#status').html(html);
       });
       
       /**
        * 加载区域
        * 
        * */
       var type="3";//区域类型
       var fatherid="";//上级Id
       common.loadArea(fatherid,type, function(json){
    	   var html = "<option value=''>请选择...</option>";
           for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
           }
           $('#areaid').html(html);
       });
   },
    /**
     * 表格创建
     */
    createTable: function(){
    	var columns = [];
    	var btn = null;
    	if(flag != 1)
    	{
    		btn = [
             {
				 name:'查看详情',
				 isshow:function(data,row)
				 {
					 return true;
	           	 },
	           	 fun:function(data,row)
	           	 {
	           		 rowdata = data;
	           		 group.view();
	           	 }
			  },
			  {
				  name:'审批通过',
				  isshow:function(data,row)
				  {
					  return (data.status=="2" || data.status=="3") ? true :false ;
				  },
				  fun:function(data,row)
				  {
					  rowdata = data;
					  group.approve(1);
				  }
			  },
			  {
				  name:'审批拒绝',
				  isshow:function(data,row)
				  {
					  return (data.status=="2" || data.status=="3") ? true :false ;
				  },
				  fun:function(data,row)
				  {
					  rowdata = data;
					  group.approve(0);
				  }
			  },
			  {
				  name:'修改',
				  isshow:function(data,row)
				  {
					  return (data.status=="1") ? true :false ;
				  },
				  fun:function(data,row)
				  {
					  rowdata = data;
					  group.edit();
				  }
			  },
			  {
				  name:'删除',
				  isshow:function(data,row)
				  {
					  return (data.status=="1") ? true :false ;
				  },
				  fun:function(data,row)
				  {
					  rowdata = data;
					  group.del();
				  }
			  }
		    ]
    		
    	}	
    	var name, isover, title;
    	if(flag == 1)
    	{
    		columns =  ["area_name",
    		            "group_name", 
    		            "type_name",  
    		            "address", 
    		            {name:"group_desc",isover:true,isshow:false,title:'小区说明'},
    		            "basecount",
    		            "createtime",
    		            "status_name"];
    	}
    	else
    	{
    		columns =  ["area_name",
    		            "group_name", 
    		            "type_name",  
    		            "address", 
    		            {name:"group_desc",isover:true,isshow:false,title:'小区说明'},
    		            "basecount",
    		            "createtime",
    		            "status_name",
//    		            "<button title='修改' onclick='group.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\" ></i></button>&nbsp;<button onclick='group.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button title='查看详情' onclick='group.view(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>"
    		            ];

    	}
        table.init({
            id: '#group_table',
            aButtons:[],
            "iDisplayLength":flag == 1 ? 5:10,
            url: common.root + '/group/getList.do?',
            issingle:flag == 1 ? true:false,
            isexp:flag == 1 ? false:true,
            bStateSave:flag == 1 ? false:true,
            columns:columns,
            param: function(){
                var a = new Array();
				a.push({name: "group_type", value: $('#group_type').val()});
				a.push({name:'areaid',value:$('#areaid').val()});
				a.push({name:'group_name',value:$('#group_name').val()});
				a.push({name:'status',value:$('#status').val()});
				a.push({name:'flag',value:flag});
                return a;
            },
            select:function(data){
            	console.log(data);
            	if($.type(data) == 'object')
            	{
        			$('#group_id').val(data.id);
        			$('#groupName').val(data.group_name);
        			$('#regionId').val(data.zone);
        			$('#house_name').val(data.group_name);
        			common.closeWindow('selectArear');
            	}
            },
            bnt:btn,
            bFilter: false,
            createRow:function(rowindex,colindex,name,value,data)
            {
            	 if(colindex == 1 || colindex == 3)
				 {
					 return group.dealColum({"value":value,"length":5});
				 }
            	
            	//初始化按钮
//				 if(colindex == 7&&data.status=="2")
//				 {
//					 return	'<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" href="#" onclick="group.approve(this,1);" >审批通过</a></li><li><a class="paddingClass"  href="#" onclick="group.approve(this,0);">审批拒绝</a></li><li><a class="paddingClass" onclick="group.view(this);"  href="#">查看详情</a></li></ul></div>';
//				//	 return "<div style='width:100%;text-align: center;'><button onclick='group.approve(this,1);' title='审批通过' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button>&nbsp;&nbsp;<button onclick='group.approve(this,0);' title='审批拒绝' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button title='查看详情' onclick='group.view(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button></div>";
//				 }else  if(colindex == 7&&data.status=="3")
//				 {
//					 
//					 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" href="#" onclick="group.approve(this,1);" >审批通过</a></li><li><a class="paddingClass"  href="#" onclick="group.approve(this,0);">审批拒绝</a></li><li><a class="paddingClass" onclick="group.view(this);"  href="#">查看详情</a></li></ul></div>';
//				//	return "<div style='width:100%;text-align: center;'><button onclick='group.approve(this,1);' title='审批通过' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button>&nbsp;&nbsp;<button onclick='group.approve(this,0);' title='审批拒绝' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button title='查看详情' onclick='group.view(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button></div>";
//				 }else  if(colindex == 7&&data.status=="1")
//				 {
//					 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" href="#" onclick="group.edit(this);" >修改</a></li><li><a class="paddingClass"  href="#" onclick="group.del(this);">删除</a></li><li><a class="paddingClass" onclick="group.view(this);"  href="#">查看详情</a></li></ul></div>';
//				 }
				 //初始化状态
				 if(data.status == 1 && colindex == 6)
				 {
					 return '<div style="color:#A94F9A;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.status_name+'</div>';
				 }
				 else if(data.status == 2 && colindex == 6)
				 {
						 return '<div style="color:#7570B3;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.status_name+'</div>';
				 }
				 else if(data.status == 3 && colindex == 6)
				 {
					 return '<div style="color:#D0B6D7;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.status_name+'</div>';
			     }
				 
				 return value;
			}
        });
    },
    /**
     * 小区查看
     * 
     * 
     * */
   view: function(){
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'groupview',
            title: '查看详情',
            url: '/html/pages/house/group/group_view.html'
        });
    },
    /**
     * 小区修改
     * 
     * 
     * */
   edit: function(){
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'groupupdate',
            title: '小区修改',
            url: '/html/pages/house/group/group_update.html'
        });
    },
    /**
     * 修改审批
     * 
     * 
     * */
   approve: function(state){
        var urlp="";
        var tishi="确认审批失败？";
        if (state==1) {
          	urlp=common.root + '/group/approveOk.do';
          	tishi="确认审批通过？";
  		}else{
  			urlp=common.root + '/group/approveNO.do';
  		}
        common.alert({
            msg: tishi,
            confirm: true,
            fun: function(action){
                if (action) {
                    common.ajax({
                        url: urlp,
                        data: {
                            id: rowdata.id
                        },
                        dataType: 'json',
                        loadfun: function(isloadsucc, data){
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '操作成功',
                                        fun: function(){
                                            table.refreshRedraw('group_table');
                                        }
                                    });
                                }else {
                                    common.alert({
                                        msg: common.msg.error
                                    });
                                }
                            }
                            else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        }
                    });
                    
                    
                    
                    
                }
            }
        }); 
        },
    /**
     * 小区删除
     * 
     * 
     * */
    del: function(){
    	common.alert({
        msg: '确定需要删除吗？',
        confirm: true,
        fun: function(action){
            if (action) {
                common.ajax({
                    url: common.root + '/group/delete.do',
                    data: {
                        id: rowdata.id
                    },
                    dataType: 'json',
                    loadfun: function(isloadsucc, data){
                        if (isloadsucc) {
                            if (data.state == '1') {
                                common.alert({
                                    msg: '删除成功',
                                    fun: function(){
                                        table.refreshRedraw('group_table');
                                    }
                                });
                            } else if (data.state == '2') {
                           	 common.alert({
                                 msg: "当前小区有关联房源，不能删除"
                             });
						}
                            else {
                                common.alert({
                                    msg: common.msg.error
                                });
                            }
                        }
                        else {
                            common.alert({
                                msg: common.msg.error
                            });
                        }
                    }
                });
            }
        }
    });},
    /**
     * 小区新增
     * 
     * 
     * */
    add: function(){
        rowdata = null;
        common.openWindow({
            type: 3,
            name:'addgroupadd',
            title: '小区新增',
            url: '/html/pages/house/group/group_add.html'
        });
    },dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(common.isEmpty(def.value))
		 {
			 return "";
		 }
		 if(def.value.length > def.length)
		 {
			 return "<div title='"+def.value+"'>"+def.value.substr(0,def.length)+"...</div>";
		 }
		 else
		 {
			 return def.value;
		 }
	},
   /* *//**
     * 导入
     * *//*
    import: function(){
        rowdata = null;
        common.openWindow({
            type: 1,
            name:'addgroupadd',
            title: '小区新增',
            url: '/html/pages/house/group/group_add.html'
        });
    },*/
};

try
{
	flag = common.getWindowsData('selectArear').flag;
	if(flag == 1)
	{
		$('#showTipMsg').show();
		$("#xq_status").hide();
		$('.bnt_add').hide();
	}
	
}
catch(e)
{
	
} 
group.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;

