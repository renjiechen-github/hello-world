var rankHouse = {
    //页面初始化操作
    init: function(){
    	//rankHouse.initType();
        //创建表格
    	rankHouse.createTable();
        //新增操作
    },
	/**
     加载结算类型数据
     **//*
    initType: function(){
        common.loadItem('SETTLEMENTS.TYPE', function(json){
            var html = "";
            for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#jstype').append(html);
        });
    },*/
    /**
     * 表格创建
     */
    createTable: function(){
        table.init({
            id: '#rank_table',
            url: common.root + '/houserank/getrankhouse.do',
            bFilter : false,
            columns: [
                    "rank_code",
					"title",
					"spec",
					"rank_area",
					"fee",
					"rank_type",
					"rank_status",
					"<button title='删除' onclick='project.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button><button onclick='project.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>"
					]
        });	
    }
};
rankHouse.init();
/**
 * 修改的时候两页面传递参数使用
 */
var rowdata = null;