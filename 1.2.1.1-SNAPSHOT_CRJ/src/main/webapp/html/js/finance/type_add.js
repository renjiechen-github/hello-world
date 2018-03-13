var type_add = {
    //页面初始化操作
    init: function(){
        //添加处理事件 
        type_add.addEvent();
		
		type_add.initData();
    },
    //添加处理事件
    addEvent: function(){
        $('#type_add_bnt').click(function(){ 
            type_add.save();
        });
    },
	//加载修改数据信息
	initData:function(){
		if(rowdata != null){
			$('#name').val(rowdata.name);
			$('#remark').val(rowdata.desc_txt);
			$('#id').val(rowdata.id);
		}
	},
    /**
     * 保存操作
     */
    save: function(){
        if (!Validator.Validate('form2')) {
            return;
        } 
        common.ajax({
            url: common.root + '/financial/type/save.do',
            data:{
	             name:$('#name').val(),
	             remark:$('#remark').val(),
				 id:$('#id').val(),
				 pagetype:rowdata==null?'add':'edit'
            },
			dataType:'json',
            loadfun: function(isloadsucc, data){
                if (isloadsucc) {
                    if (data.state == 1) {//操作成功
                 		common.alert({
							msg:'操作成功',
							fun:function(){
								layer.closeAll();
								//调用查询按钮
								table.refreshRedraw('type_table');
							}
						});
                    }else if(data.state == -2){
						layer.tips('请填写财务项目名称','#name');
					}else{
						common.alert({
	                        msg: common.msg.error
	                    });
					}
                }else {
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
        });
    }
};
type_add.init();
//alert(common.getWindowsData('addexp').sa);
