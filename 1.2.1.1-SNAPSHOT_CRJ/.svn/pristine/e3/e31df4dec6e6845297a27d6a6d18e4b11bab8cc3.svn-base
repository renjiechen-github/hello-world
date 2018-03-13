var account = {
    init: function(){
        //创建表格
    	account.createTable();
    	 //新增操作
        $('.bnt_add').click(function(){
        	account.add();
        });
        
    },
    /**
     * 表格创建
     */
    createTable: function(){
        table.init({
            id: '#account_table',
            url: common.root + '/account/getList.do?',
            /**
             * 为html页面追加数据
             */
            columns:["aname",
    		            "mobile", 
    		            "role_name",
    		            "org_name",
    		            "create_time", 
    		            "equipment",
    		            "lastlogintime",
    		            "statename"],
    		bnt:[{
			    	 name:'删除',
			    	 isshow:function(data){
			    		 if(data.is_delete == 1){
			    			 return true;
			    		 }
			    		 return false;
			    	 },
			    	 fun:function(data){
			    		 account.delt(data.id,0);
			    	 }
			     },
			     {
			    	 name:'恢复',
			    	 isshow:function(data){
			    		 if(data.is_delete == 0){
			    			 return true;
			    		 }
			    		 return false;
			    	 },
			    	 fun:function(data){
			    		 account.delt(data.id,1);
			    	 }
			     },
    		     {
    		    	 name:'修改',
    		    	 fun:function(data){
    		    		 rowdata = data;
    		    		 account.edit();
    		    	 }
    		     },
    		     {
    		    	 name:'启用',
    		    	 isshow:function(data){
    		    		 if(data.statename != '正常'){
    		    			 return true;
    		    		 }else{
    		    			 return false;
    		    		 }
    		    	 },
    		    	 fun:function(data){
    		    		 rowdata = data;
    		    		 account.en();
    		    	 }
    		     },
    		     {
    		    	 name:'停用',
    		    	 isshow:function(data){
    		    		 if(data.statename == '正常'){
    		    			 return true;
    		    		 }else{
    		    			 return false;
    		    		 }
    		    	 },
    		    	 fun:function(data){
    		    		 rowdata = data;
    		    		 account.del();
    		    	 }
    		     },
    		     {
    		    	 name:'查看详情',
    		    	 fun:function(data){
    		    		 rowdata = data;
    		    		 account.view();
    		    	 }
    		     },
    		     {
    		    	 name:'初始化密码',
    		    	 fun:function(data){
    		    		 rowdata = data;
    		    		 account.start();
    		    	 }
    		     },
    		],
            param: function(){
                var a = new Array();
                /**
                 * 传入参数，模糊查询
                 */
				a.push({name: "username", value: $('#username').val()});
                return a;
            }
        });
    },
    /**
     * 
     * 用户查询
     * */
   view: function(obj){
	   // 获取表格当前行的数据，返回一个json对象
        //rowdata = table.getRowData('account_table', obj);
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'accountview',
            title: '查看详情',
            url: '/html/pages/sys/account/account_view.html'
        });
    },
    /**
     * 用户修改
     * */
   edit: function(obj){
        //rowdata = table.getRowData('account_table', obj);
        //打开对于的窗口
        common.openWindow({
            type: 3,
            name:'userupdate',
            title: '用户修改',
            url: '/html/pages/sys/account/account_update.html'
        });
    },
    /**
     * 删除操作
     * @param obj
     */
    delt:function(id,stat){
    	common.alert({
            msg: '确定需要删除吗？',
            confirm: true,
            fun: function(action){
                if (action) {
                    common.ajax({
                        url: common.root + '/account/deleteUser.do',
                        data: {
                            id: id,
                            stat:stat
                        },
                        dataType: 'json',
                        loadfun: function(isloadsucc, data){
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '删除成功',
                                        fun: function(){
                                            table.refreshRedraw('account_table');
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
        });
    },
    /**
     * 用户停用(修改用户状态)
     * */
    del: function(obj){
        //rowdata = table.getRowData('account_table', obj);
    	common.alert({
        msg: '确定需要停用吗？',
        confirm: true,
        fun: function(action){
            if (action) {
                common.ajax({
                    url: common.root + '/account/stop.do',
                    data: {
                        id: rowdata.id
                    },
                    dataType: 'json',
                    loadfun: function(isloadsucc, data){
                        if (isloadsucc) {
                            if (data.state == '1') {
                                common.alert({
                                    msg: '停用成功',
                                    fun: function(){
                                        table.refreshRedraw('account_table');
                                    }
                                });
                            }else if (data.state == '-1') {
                                common.alert({
                                    msg: '该用户下存在团队只有1人，如法进行停用',
                                    fun: function(){
                                        table.refreshRedraw('account_table');
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
    });
    },
    /**
     * 用户启用(修改用户状态)
     * */
    en: function(obj){
        //rowdata = table.getRowData('account_table', obj);
    	common.alert({
        msg: '确定需要启用吗？',
        confirm: true,
        fun: function(action){
            if (action) {
                common.ajax({
                    url: common.root + '/account/enable.do',
                    data: {
                        id: rowdata.id
                    },
                    dataType: 'json',
                    loadfun: function(isloadsucc, data){
                        if (isloadsucc) {
                            if (data.state == '1') {
                                common.alert({
                                    msg: '启用成功',
                                    fun: function(){
                                        table.refreshRedraw('account_table');
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
    });
    	},
    /**
     * 初始化密码
     * */
    start: function(obj){
         //rowdata = table.getRowData('account_table', obj);
    	common.alert({
        msg: '确定需要初始化吗？',
        confirm: true,
        fun: function(action){
            if (action) {
                common.ajax({
                    url: common.root + '/account/start.do',
                    data: {
                        id: rowdata.id
                    },
                    dataType: 'json',
                    loadfun: function(isloadsucc, data){
                        if (isloadsucc) {
                            if (data.state == '1') {
                                common.alert({
                                    msg: '密码初始化成功',
                                    fun: function(){
                                        table.refreshRedraw('account_table');
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
            url: '/html/pages/sys/account/account_add.html'
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
account.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;

