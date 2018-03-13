/**
 * 人员选择信息列表
 */
var _commonUser_ = { 
	init:function(){
		var issingle = true;
		var ismultiple = false;
		if(!common.userobj.issingle){
			issingle = false;
			ismultiple = true;
		}
		var table1 = table.init({
   			id:'#_usercommon_',
			loadObj:'._common_user__',
   			url:common.root+'/sys/queryUser.do',
   			columns:[{name:"name",isdc:false},
					{name:"mobile",isdc:false}],
			issingle:issingle,
			ismultiple:ismultiple,
   			param:function(){
   				var a = new Array();
   				a.push({ "name": "orgids", "value": common.userobj.orgids});
   				a.push({ "name": "roles", "value": common.userobj.roles});
				a.push({ "name": "username", "value": $('#username').val()});
   				return a;
   			}, 
			select:function(data){
				if($.type(data) == 'object'){
					table.obj.pop();
					var fun = $("#layerframe__userwindow__").data('succee');
					try{
						fun(data);
					}catch(e){alert(e)}
					common.closeWindow('__userwindow__');
					
				}else if($.isArray(data)){
					table.obj.pop();
					var fun = $("#layerframe__userwindow__").data('succee');
					try{
						fun(data);
					}catch(e){alert(e)}
					common.closeWindow('__userwindow__');
				}
			},
			/**
			 * 创建函数执行
			 * @param {Object} rowindex 所在行
			 * @param {Object} colindex 所在列
			 * @param {Object} name 字段名称
			 * @param {Object} value 字段值 
			 * @param {Object} data 此行的数据信息 json数据格式
			 */
            createRow:function(rowindex,colindex,name,value,data){
				if(name == 'id'){
					return '<input type="checkbox" style="width: 20px" class="checkbox form-control" id="agree" name="agree">';
				}
				return value;
			}
   		});
	}
}

_commonUser_.init();
