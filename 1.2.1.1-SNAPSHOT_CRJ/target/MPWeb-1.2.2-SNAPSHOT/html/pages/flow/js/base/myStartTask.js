var myStartTask={
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
            id: '#flowexample',
            url: common.root + '/flow/getMyStartTaskList.do',
            columns: ["typenames",
					"task_code", 
					"name", 
					"createtime",  
					"statename",
					"<button title='查看详细' onclick='myStartTask.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button><button onclick='myStartTask.deleteFlow(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>"],
            aaSorting:[[4,"desc"]],
            aoColumnDefs:[{ "bSortable": false, "aTargets": [0,1,2,3,5,6]}],
            param: function(){
                var a = new Array();
				a.push({ "name": "taskCode", "value": $('#taskCode').val()});
				a.push({ "name": "taskName", "value": $('#taskName').val()});
				a.push({ "name": "sendTime", "value": $('#sendTime').val()});
				a.push({ "name": "status", "value": $('#secondary_type').val()});
				a.push({ "name": "lx", "value": 1});
				var size = $('.flowtypediv select').size();
				var types = '-';
				var typelike ='';
				for(var i=0;i<size;i++){
					var v = $('#flowType'+i).val();
					if(v != '' && v!=undefined){
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
            },
            /**
			 * 创建函数执行
			 * @param {Object} rowindex 所在行
			 * @param {Object} colindex 所在列
			 * @param {Object} name 字段名称
			 * @param {Object} value 字段值 
			 * @param {Object} data 此行的数据信息 json数据格式
			 */ 
            createRow:function(rowindex,colindex,name,value,data,row){
            	if(name == '操作'&&data.state!=0){//只有在新增的时候才可以进行删除操作
            		common.log(11);
            		return "<button title='查看详细' onclick='myStartTask.edit(this);' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button>";
            	}
            	 if(colindex == 2)
    			 {
    				 return myStartTask.dealColum({"value":value,"length":8});
    			 }
            	return value;
            }
        });
	},
	dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(def.value == "null" || def.value == '' || def.value == null)
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
	/**
	 * 删除流程操作
	 * @param obj
	 */
	deleteFlow:function(obj){
		var objc = table.getRowData('flowexample', obj);
		if (objc.task_cfg_id=="1"||objc.task_cfg_id=="2") {
			common.alert({
				msg:'是否确定删除?',
				confirm:true,
				fun:function(action){
					if(action){
						common.load.load('删除中');
						var data = table.getRowData('flowexample', obj);
						common.ajax({
							url: common.root + '/flow/deleteFlow.do',
							data:{task_cfg_id:data.task_cfg_id,task_id:data.task_id},
						    dataType: 'json',
						    loadfun: function(isloadsucc, data){
						    	common.load.hide();
						    	if(isloadsucc){
						    		
						    		if(data.state == 1){
						    			common.alert('操作成功');
						    			table.refreshRedraw('flowexample');
						    		}else{
						    			common.alert(data.msg);
						    		}
						    	}else{
						    		common.alert(common.msg.error);
						    	}
						    }
						});
					}
				}
			});
		}
		else
		{
			common.alert({msg:'服务订单不允许删除'})
			return;
		}
		
	},
	/**
	 * 加载处理事件信息
	 */
	loadEvent:function(){
		$(".flowtypediv ").on("change","select[t='type']",function(){
			var val = $(this).val();
			var len = $(this).attr('id').replace('flowType','');
			var size = $('.flowtypediv select').size();
			common.log(len);   
			for(var i=size;i>len;i--){
				$('#flowType'+i).remove();
			}
			size = $('.flowtypediv select').size();
			if(val == ''){
				return ;
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
		var data = table.getRowData('flowexample', obj);
		common.log(data);
		common.openWindow({
			url:common.root + '/flow/showFlowDetail.do?step_id='+data.step_id+'&task_id='+data.task_id,
			name:'showFlowDetail',
			type:3,
			title:data.typenames+"_我发起的任务",
			data:{ 
				
			}
		});
	},
	
	del:function(obj){
		
	}
};
$(function(){
	myStartTask.init();
});