var expend_add={
	init:function(){
		//选择类型后加载对应的次要关联项目
		$('#type').change(function(){
			var id = $(this).val();
			if(id != '1' && id != '0' ){
				$('#secondary').select2("destroy");
				$('#secondary').find("option[value!='']").remove();
				$('#correlation').select2("destroy");
				$('#correlation').find("option[value!='']").remove();
				return false;
			}
				$('.secondary_labe').text('合约信息：').attr('attr','请选择合约明细');
				$('#secondary').select2({
					theme: "classic",
					ajax: {
					    url: common.root+"/financial/getAgrList.do",
					    dataType: 'json',
					    delay: 250,
					    data: function (params) {
					      common.log(params);
					    	return {
					        name: encodeURI(params.term), // search term
					        page: params.page,
					        type:id=='1'?2:1
					      };
					    },
					    processResults: function (data, params) {
					      params.page = params.page || 1;
					      return {
					        results: data.items,
					        pagination: {
					          more: (params.page * 30) < data.total_count
					        }
					      };
					    },
					    cache: true
					  },
					  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
					  minimumInputLength: 1,
					  templateResult: function(repo){
						  return repo.name;
					  }, // omitted for brevity, see the source of this page
					  templateSelection: function(repo){// omitted for brevity, see the source of this page
						  return repo.name;
					  } 
				});
				$('#correlation').select2({
					theme: "classic",
					  ajax: {
						    url: common.root+"/financial/getCorrelation.do",
						    dataType: 'json',
						    delay: 250,
						    data: function (params) {
						      common.log(params);
						    	return {
						    		name : encodeURI(params.term),
									type : id=='1'?2:1,
							        page: params.page,
						      };
						    },
						    processResults: function (data, params) {
						      params.page = params.page || 1;
						      return {
						        results: data.items,
						        pagination: {
						          more: (params.page * 30) < data.total_count
						        }
						      };
						    },
						    cache: true
						  },
						  escapeMarkup: function (markup) { return markup; }, // let our custom formatter work
						  minimumInputLength: 1,
						  templateResult: function(repo){
							  return  repo.name;
						  }, // omitted for brevity, see the source of this page
						  templateSelection: function(repo){// omitted for brevity, see the source of this page
							  return repo.name;
						  } 
					});
		});
		var nowTemp = new Date();
		$('#plat_time,#start_time,#end_time').daterangepicker({
			startDate : nowTemp.getFullYear() + '-'+ (nowTemp.getMonth() + 1) + '-02',
			timePicker12Hour : false,
			singleDatePicker: true,
			format : 'YYYY-MM-DD'
		}, function(start, end, label) {
			console.log(start.toISOString(), end.toISOString(), label);
		});
		
		$('#income_add_bnt').click(function(){
			expend_add.save();
		});
		
		//financial_category_tab
		common.ajax({
        	url: common.root + '/financial/type/getTypeList.do',
        	data:{
        		iDisplayStart:0
        	},
        	dataType: 'json',
        	loadfun: function(isloadsucc, data){
            	if (isloadsucc) {
            		common.log(data); 
            		var html = "";
		            for (var i = 0; i < data.data.length; i++) {
						html += '<option  value="' + data.data[i].id + '" >' + data.data[i].name + '</option>';
		            }
		            $('#category').append(html);
            	}
            }
        });
	},
	/**
	 * 保存操作
	 */
	save:function(){
		if (!Validator.Validate('form2')) {
            return;
        }
		common.ajax({
            url: common.root + '/financial/expend/save.do',
            data:$('#form2').serialize(),
			dataType:'json',
            loadfun: function(isloadsucc, data){
                if (isloadsucc) {
                    if (data.state == 1) {//操作成功
                 		common.alert({
							msg:'操作成功',
							fun:function(){
								common.closeWindow('incomeAdd',3);
							}  
						});
                    }else if(data.state == -2){
                    	common.alert({
							msg:'请先登陆系统',
							fun:function(){
								window.location.href = "/";
							}
						});
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

$(function(){
	expend_add.init();
});