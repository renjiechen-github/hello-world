var lytable={
	/**
	 * 内部参数，不予修改
	 */
	params:{
		
	},
	/**
	 * 初始化操作
	 * @param opt
	 */
	init:function(opt){
		common.log(new Date());
		/**
		 * 默认参数设置
		 */
		var def={
			id:'',//table对应的id值
			url:'',//服务器分页时使用的服务连接地址
			/**
			 * 数据信息
			 */
			data:[],
			/**
			 * 响应到最小的时候默认背景色
			 */
			borderColor:'#eff0f4',
			param:function(){return {};},//使用服务器分页的时候需要传递的参数
			ismultiple:false,//是否需要多选框
			issingle:false,//是否需要单选框
			singleid:"table"+parseInt(Math.random()*100000),//定义table的id值
			select:function(data){},//单选的时候选择后调用的函数
			isexp:true,//是否出现导出按钮
			bProcessing:false,//是否显示默认的加载框 默认为false 因为已经重绘了
			isOverflowShow:true,//溢出界面内容是否显示在首界面添加+
			/**
			 * 创建溢出显示的时候调用的函数
			 * @param data 当前行的数据信息
			 * @param head 已经标记需要添加到溢出中显示的标签
			 */
			overflowfun:function(data,head){},
			/**
			 * 在标签上定义的默认属性
			 */
			tableAttrParam:{
				isshow:'show',
				issort:'sort',
				key:'key',
				isexport:'export',
				hidewidth:'hide'//针对窗口小与多少隐藏，传递px值
			},
			/**
			 * 创建函数执行
			 * @param {Object} rowindex 所在行
			 * @param {Object} colindex 所在列
			 * @param {Object} name 字段名称
			 * @param {Object} value 字段值
			 * @param {Object} data 此行的数据信息
			 */
			createRow:function(rowindex,colindex,name,value,data,row){return value;},
			"oLanguage": {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				'sSearch':'搜索',
				"oPaginate": {
					"sFirst": "首页",
					"sPrevious": "前一页",
					"sNext": "后一页",
					"sLast": "尾页"
				},
				"sZeroRecords": "没有检索到数据",
				sProcessing:'<i class="fa fa-spinner fa-lg fa-spin fa-fw"></i>加载中...',
				sEmptyTable:'很抱歉，暂无数据，你可以尝试更换查询条件进行检索'
			},
			/**
			 * 导出excel名称
			 */
			expname:window.document.title,
			"aLengthMenu": [5,10, 25, 50,100],
			"iDisplayLength":10, //table表格默认每页显示行数
			bStateSave:true, // 是否记录上次状态
			/** 
			 * 绘制结束调用函数
			 */
			fnDrawCallback:function(o){},
			/**
			 * 开始导出操作
			 * 
			 */
			expFun:function(params){
				
			},
			/**
			 * 服务器加载数据失败调用函数
			 */
			loaderror:function(data){
				
			}
		};
		jQuery.extend(def, opt);
		if(def.id == ''){
			this.error('未指定tableid值可以传入id、class等，兼容jquery选择器');
			return;
		}
		var tableObj = $(def.id); 
		//获取参数列表
		var columns = this._fn_getColParam(tableObj, def);
		//this.log(columns);
		//调用ajax获取数据信息
		$.ajax({
			type:'get',
			url: def.url,
			data:this._params(def,tableObj),
			dataType:'json',
			cache:false,
			async:true,
			timeout: 100000,//超时时间
			success:function(data){
                try {
					if(data.state == '-99999'){
						common.alert({
							msg:'您还未登陆或登陆已经失效，请登录。',
							fun:function(){
								window.location.href='login.html';
							} 
						});
						return;
					}else if(data.state == '-99998'){
						common.alert({
							msg:'很抱歉，您没有权限进行此操作。'
						});
						return;
					}
					lytable._loadsucceed(def,tableObj,data);
                }catch (e) {
					console.error(e);
                    def.loaderror( e);
                }
            },
			error: function(data){
				console.error(data);
                def.loaderror(data);
            }
		});
		
		window.onresize=function(){
			lytable._fn_col_hide(tableObj,def);
		};
	},
	_fn_col_hide:function(tableObj,def){
		var w = $(window).width();
		var columns_ = tableObj.data('columns');
		if(columns_ != undefined){
			for(var i=0;i<columns_.length;i++){
				var hide_ = columns_[i][def.tableAttrParam.hidewidth];
				var index = columns_[i]['index'];
				if(hide_ == undefined || hide_ == ''){
					continue;
				}
				lytable.log(parseInt(hide_) >= parseInt(w));
				if(parseInt(hide_) >= parseInt(w)){
					lytable.log('隐藏。。。'+index);
					tableObj.find("thead tr th:nth-child("+(index+1)+"),"+"tbody tr td:nth-child("+(index+1)+")").addClass('hide');
				}else{
					tableObj.find("thead tr th:nth-child("+(index+1)+"),"+"tbody tr td:nth-child("+(index+1)+")").removeClass('hide');
				}
			}
		}
	},
	log:function(msg){
		console.log(msg);
	},
	error:function(msg){
		console.error(msg);
	},
	/**
	 * 获取传递到后台的参数信息
	 */
	_params:function(def,tableObj){
		var sEcho = tableObj.data('sEcho');
		var colparams = lytable._fn_getColParam(tableObj,def);
		var param ={
			sEcho:sEcho==undefined?'0':sEcho,
			iColumns:colparams.length,
			iDisplayStart:0,
			iDisplayLength:def.iDisplayLength,
			iSortCol_0:1,
			sSortDir_0:'asc',
			sizelength:100
		};
		for(var i=0;i<colparams.length;i++){
			param['mDataProp_'+i] = colparams[i][def.tableAttrParam.key];
		}
		tableObj.data('sEcho',sEcho==undefined?0:sEcho+1);
		jQuery.extend(param, def.param());
		lytable.log(param);
		return param;
	},
	/**
	 * 默认的加载成功后调用的函数
	 * @param def
	 * @param tableObj
	 * @param data
	 */
	_loadsucceed:function(def,tableObj,data){
		//开始进行处理数据信息
		tableObj.data('tabledata',data);
		var tbody = document.createElement("tbody");
		tableObj.append(tbody);
		var columns = tableObj.data('columns');
		//开始生成表格数据信息
		for(var i=0;i<data.data.length;i++){
			var tr = document.createElement("tr");
			tr.setAttribute("class", i%2==0?"two":'one');
			//循环创建td标签
			for(var j=0;j<columns.length;j++){
				var td = document.createElement("td");
				var val_div = document.createElement("div");
				var val_div_ = document.createElement("div");
				var title_div = document.createElement("div");
				var title_div_ = document.createElement("div");
				title_div_.setAttribute('class','name_');
				title_div_.innerHTML = columns[j]['name'];
				title_div.setAttribute('title',columns[j]['name']);
				title_div.appendChild(title_div_);
				title_div.setAttribute('class','name');
				
				val_div_.setAttribute('class','val_');
				val_div.setAttribute('class','val');
				var className = "";k
				if(!columns[j][def.tableAttrParam.isshow]){
					className = "hide";
				}
				var val = def.createRow(i,j,name,data.data[i][columns[j][def.tableAttrParam.key]],data,tr);
				if(val == undefined || val == ''){
					val = data.data[i][columns[j][def.tableAttrParam.key]];
				}
				val_div.setAttribute('title',val);
				val_div_.innerHTML = val;
				val_div.appendChild(val_div_);
				td.appendChild(title_div);
				td.appendChild(val_div);
				tr.appendChild(td);
			}
			tbody.appendChild(tr);
			lytable.log(tbody);
		}
		lytable._fn_col_hide(tableObj,def); 
		common.log(new Date());
	},
	/**
	 * 获取表格设置参数信息
	 * @param tableObj
	 * @param id
	 * @returns []
	 */
	_fn_getColParam:function(tableObj,def){
		var columns = tableObj.data('columns');
		if(columns == undefined){
			columns = [];
			tableObj.find('thead th').each(function(i,n){
				var sort = $(this).attr('sort');
				var show = $(this).attr('show');
				var key = $(this).attr('key');
				var export1 = $(this).attr('export');
				var hide = $(this).attr('hide');
				var pas = {};
				pas[def.tableAttrParam.isshow] = show=='false'?false:true;
				pas[def.tableAttrParam.issort] = sort=='true'?true:false;
				pas[def.tableAttrParam.isexport] = export1=='true'?true:false;
				pas[def.tableAttrParam.key] = key ==undefined?'':key;
				pas[def.tableAttrParam.hidewidth] = (hide==undefined?'':hide);
				pas['index'] = i;
				pas['name'] =$(this).text();
				columns.push(pas);
			});
			tableObj.data('columns',columns);
			return columns;
		}else{
			return columns;
		}
	}
};