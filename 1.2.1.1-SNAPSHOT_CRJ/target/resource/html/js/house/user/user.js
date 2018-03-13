var flag = 0;
var user = {
    init: function(){
        //创建表格
    	user.createTable();
    	 //新增操作
        $('.bnt_add').click(function(){
        	user.add();
        });
    },
    /**
     * 表格创建
     */
    createTable: function(){
        table.init({
            id: '#group_table',
            url: common.root + '/user/getList.do?',
            /**
             * 为html页面追加数据
             */
            columns:["username",
    		            "mobile", 
    		            "certificateno",  
    		            "sexname", 
    		            "birthday",
    		            "registertime",
    		            "lastlogintime",
    		            "<button title='修改' onclick='user.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\" ></i></button>&nbsp;<button onclick='user.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button title='查看详情' onclick='user.view(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>"],
            param: function(){
                var a = new Array();
                /**
                 * 传入参数，模糊查询
                 */
				a.push({name: "username", value: $('#username').val()});
                return a;
            },
            bFilter: false,
            createRow:function(rowindex,colindex,name,value,data)
            {
					 if(colindex == 7)
				 {
					 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" href="#" onclick="user.edit(this);" >修改</a></li><li><a class="paddingClass"  href="#" onclick="user.del(this);">删除</a></li><li><a class="paddingClass" onclick="user.view(this);"  href="#">查看详情</a></li></ul></div>';
				 }
				 return value;
			}
        });
    },
    /**
     * 用户查询
     * */
   view: function(obj){
	   // 获取表格当前行的数据，返回一个json对象
        rowdata = table.getRowData('group_table', obj);
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'userview',
            title: '查看详情',
            url: '/html/pages/house/user/user_view.html'
        });
    },
    /**
     * 用户修改
     * */
   edit: function(obj){
        rowdata = table.getRowData('group_table', obj);
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'userupdate',
            title: '用户修改',
            url: '/html/pages/house/user/user_update.html'
        });
    },
    /**
     * 用户删除(修改用户状态)
     * */
    del: function(obj){
         rowdata = table.getRowData('group_table', obj);
    	common.alert({
        msg: '确定需要删除吗？',
        confirm: true,
        fun: function(action){
            if (action) {
                common.ajax({
                    url: common.root + '/user/delete.do',
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
        }
    });},
    /**
     * 用户新增
     * */
    add: function(){
        common.openWindow({
            type: 3,
            name:'addgroupadd',
            title: '用户新增',
            url: '/html/pages/house/user/user_add.html'
        });
    },dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(def.value == "null" || def.value == '')
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
	}
	
}
catch(e)
{
	
} 
user.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;

