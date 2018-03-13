var yetDisposeTask={
	/**
	 * 初始化操作信息
	 */
	init:function(){
		this.loadData();
		this.loadEvent();
	},
	/**
	 * 加载数据信息
	 */
	loadData:function(){
		table.init({
            id: '#flowYetTable',
            url: common.root + '/flow/getMyYetTaskList.do',
            columns: ["typenames",
					"task_code", 
					"name", 
					"createtime",  
					"statename",
					"step_name",
					"<button title='查看详细' onclick='yetDisposeTask.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button>"],
            param: function(){
                var a = new Array();
				a.push({ "name": "taskCode", "value": $('#taskCode').val()});
				a.push({ "name": "taskName", "value": $('#taskName').val()});
				a.push({ "name": "sendTime", "value": $('#sendTime').val()});
				a.push({ "name": "lx", "value": 3});
				var size = $('.flowtypediv select').size();
				var types = '-';
				var typelike ='';
				for(var i=0;i<size;i++){
					var v = $('#flowType'+i).val();
					if(v != ''){
						types += v+'-';
					}
				}
				types = types.substr(1);
				common.log(types);
				types = types.substr(0,types.length-1);
				common.log(types);
				typelike = types.indexOf("-")>=0?types.substr(types.lastIndexOf("-")+1):types;
				common.log(typelike);
				a.push({ "name": "types", "value": types});
				a.push({ "name": "typelike", "value": typelike});
                return a;
            }
        });
	},
	/**
	 * 加载处理事件信息
	 */
	loadEvent:function(){
		$(".flowtypediv ").on("change","select[t='type']",function(){
			var val = $(this).val();
			var len = $(this).attr('id').replace('flowType','');
			var size = $('.flowtypediv select').size();
			if(val == ''){
				for(var i=size;i>len;i--){
					$('#flowType'+i).remove();
				}
				return;
			}
			common.ajax({
				url: common.root + '/flow/getType.do',
				data:{sup:val},
			    dataType: 'json',
			    loadfun: function(isloadsucc, data){
			    	if(isloadsucc){
			    		common.log(data);
			    		if(data.list.length > 0 ){
				    		var html = '<select class="form-control" t="type" id="flowType'+size+'" name="flowType'+size+'" ><option value="" >请选择...</option>';
				    		for(var i=0;i<data.list.length;i++){
				    			html += '<option value="'+data.list[i].type_id+'" >'+data.list[i].type_name+'</option>';
				    		}
				    		html += '</select>';
				    		$('.flowtypediv').append(html);
			    		}else{//需要进行移除后面的信息
			    			for(var i=size;i>len;i--){
								$('#flowType'+i).remove();
							}
			    		}
			    	}else{
			    		common.alert(common.msg.error);
			    	}
			    }
			});
		});
		var nowTemp = new Date();
		$('#sendTime').daterangepicker({
			startDate:nowTemp.getFullYear()+'-'+(nowTemp.getMonth()+1)+'-02',
			timePicker12Hour:false,
	        timePicker: true,
			separator:'至',
			//分钟间隔时间
	        timePickerIncrement: 10,
	        format: 'YYYY-MM-DD HH:mm'
        }, function(start, end, label) {
        	common.log(start.toISOString(), end.toISOString(), label);
        });
	},
	/**
	 * 
	 * @param obj
	 */
	edit:function(obj){
		var data = table.getRowData('flowYetTable', obj);
		common.log(data);
		common.openWindow({
			url:common.root + '/flow/yetDisposeDitail.do?task_id='+data.task_id,
			name:'showYetFlowDetail',
			type:3,
			data:{
				
			}
		});
	}
};
$(function(){
	yetDisposeTask.init();
});
