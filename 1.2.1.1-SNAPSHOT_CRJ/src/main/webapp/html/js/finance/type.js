var financetype = {
    //页面初始化操作
    init: function(){
        //创建表格
        financetype.createTable();
        //新增操作
        $('.bnt_add').click(function(){
            financetype.add();
        });
    },
    /**
     * 表格创建
     */
    createTable: function(){
        table.init({
            id: '#type_table',
            url: common.root + '/financial/type/getTypeList.do',
            columns: ["id",
					"name",
					"desc_txt", 
					"operdate"], 
            param: function(){
                var a = new Array(); 
				a.push({name:'lmname',value:$('#lmname').val()});
                return a;
            },
            bFilter: false
        });
    },
	/**
	 * 编辑
	 * @param {Object} obj
	 */
    edit: function(obj){
        var num = $(obj).parents('tr').attr('num');
        //获取当前行的数据信息,用来区分是新增还是修改的，同时也是修改的数据信息
        rowdata = table.getRowData('type_table', obj);
        //打开对于的窗口
        common.openWindow({
            type: 1,
			name:'addcw',
            title: '财务类目修改',
            url: '/html/pages/finance/type_add.html'
        });
    }, 
    //删除项目信息
    del: function(obj){
        common.alert({
            msg: '确定需要删除吗？',
            confirm: true,
            fun: function(action){
                if (action) {
                    common.ajax({
                        url: common.root + '/financial/type/deleteType.do',
                        data: {
                            id: table.getRowData('type_table', obj).id
                        },
                        dataType: 'json',
                        loadfun: function(isloadsucc, data){
                            if (isloadsucc) {
                                if (data.state == '1') {
                                    common.alert({
                                        msg: '删除成功',
                                        fun: function(){
                                            table.refreshRedraw('type_table');
                                        }
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
        });
    },
	/**
	 * 修改
	 */
    add: function(){
        rowdata = null;
        common.openWindow({
            type: 1,
            title: '财务类目新增',
			name:'addcw',
            url: '/html/pages/finance/type_add.html'
        });
    }
};
financetype.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;
