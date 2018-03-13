var role = {
    init: function(){
        //创建表格
    	role.createTable();
        $('.bnt_add').click(function(){
        	role.add();
        });
    },
    /**
     * 表格创建
     */
    createTable: function(){
        table.init({
            id: '#account_table',
            url: common.root + '/role/getList.do?',
            /**
             * 为html页面追加数据
             */
            columns:["role_name",
    		            "role_decs",  
    		            "<button title='设置权限' onclick='role.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\" ></i></button>"],
            bFilter: false,
            createRow:function(rowindex,colindex,name,value,data)
            {
					 if(colindex ==2 )
				 {
					 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" href="#" onclick="role.edit(this);" >设置权限</a></li></ul></div>';
				 }
				 return value;
			}
        });
    },
    /**
     * 角色修改
     * */
   edit: function(obj){
        rowdata = table.getRowData('account_table', obj);
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'roleupdate',
            title: '角色修改',
            url: '/html/pages/sys/role/role_update.html'
        });
    },
    add: function(){
        common.openWindow({
            type: 3,
            name:'addgroupadd',
            title: '用户新增',
            url: '/html/pages/sys/role/role_add.html'
        });
    }
};
role.init();
var rowdata = null;

