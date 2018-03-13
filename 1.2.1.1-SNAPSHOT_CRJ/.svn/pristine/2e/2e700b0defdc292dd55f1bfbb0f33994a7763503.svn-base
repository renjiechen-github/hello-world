var grid = {
    init: function(){
        //创建表格
    	grid.createTable();
  	 //查询小区类型----区域选项
    	grid.initType();
    	//区域选择查询
        $('#areaid').change(function(){
       	 $('#search').click();
       });
    	//新增操作
    	
    	 $('.bnt_add').click(function(){
        	  grid.add();
         });
    	 
    	 /*
        //小区类型选择查询
        $('#grid_type').change(function(){
        	 $('#search').click();
        });
      
        //状态选择查询
        $('#status').change(function(){
       	 $('#search').click();
       });*/
// enter监听事件
//    	$('#grid_name').keydown(function(e) {
//    		if (e.which == "13") {
//    			// var focusActId = document.activeElement.id;获取焦点ID，根据ID判断执行事件
//    			$("#search").click();
//    			return false;// 禁用回车事件
//    		}
//    	});
    	
    },
    /**
    加载小区类型数据
    **/
   initType: function(){
       /**
        * 加载区域
        *  * */
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
   
   add: function(){
	   
		var url='/html/pages/sys/t_grid/grid_add.html';
		var title ="新增网格";
		 common.openWindow({
	            type: 1,
	            name:"t_grid_add",
	            title: title,
	            url: url
	        });
   },
    /**
     * 表格创建
     */
    createTable: function()
    {
    	 table.init({
	         id: '#grid_table',
	         url: common.root + '/Grid/getList.do',
	         columns: [
						"g_name",
						"name",
						"area_name",
						],
						bnt:[{
				            name:'管理网格',
							fun:function(data,row)
							{
						    	rowdata = data;
								var url='/html/pages/sys/t_grid/grid_update.html';
								var title =rowdata.g_name+rowdata.name+"网格管理";
								var name="updateg_id";
								 common.openWindow({
							            type: 3,
							            name:name,
							            title: title,
							            url: url
							        });
							}
					    },{
				            name:'修改本网格管家',
							fun:function(data,row)
							{
						    	rowdata = data;
								var url='/html/pages/sys/t_grid/dispatch.html';
								var title ="修改本网格管家";
								 common.openWindow({
							            type: 1,
							            name:"dispatch",
							            title: title,
							            url: url
							        });
							}
						 },{
					            name:'删除网格',
								fun:function(data,row)
								{
				            	    common.alert({
						            msg: '是否删除此网格？',
						            confirm: true,
						            fun: function(action)
						            {
						                if (action) 
						                {
					                	    common.ajax({
					                        url: common.root + '/Grid/gridDelete.do',
					                        data: {g_id:data.g_id},
					                        dataType: 'json',
					                        loadfun: function(isloadsucc, data)
					                        {
					                            if (isloadsucc)
					                            {
					                                if (data.state == '1') 
					                                {
				                                        common.alert({msg: '操作成功'});
				                                        table.refreshRedraw('grid_table');
					                                }
					                            }
					                            else 
					                            {
					                                common.alert({ msg: common.msg.error});
					                            }
					                         }
							                 });
						                	
						                }
						            }
								 })
							        
									
									
								}
							 }],
			  select:function(data)
			  {
			    	rowdata = data;
					var url='/html/pages/sys/t_grid/grid_update.html';
					var title =rowdata.g_name;
					var name=rowdata.g_name+"管理";
					 common.openWindow({
				            type: 3,
				            name:name,
				            title: title,
				            url: url
				        });
			  },
			 param: function()
			 {
                var a = new Array();
                a.push({name : 'areaid',value :  $('#areaid').val()}); 
                a.push({name : 'keyWord',value :  $('#keyWord').val()}); 
                return a;
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
            name:'gridview',
            title: '查看详情',
            url: '/html/pages/house/grid/grid_view.html'
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
            name:'gridupdate',
            title: '小区修改',
            url: '/html/pages/house/grid/grid_update.html'
        });
    }
  
};

grid.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;

