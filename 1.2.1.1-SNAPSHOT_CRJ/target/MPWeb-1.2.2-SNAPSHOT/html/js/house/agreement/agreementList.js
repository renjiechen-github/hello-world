var agreementList = {
	// 页面初始化操作
	init : function() {
		// 创建表格
		agreementList.createTable();
		// 初始化下拉列表
		agreementList.initSelect();
		$('#accountid,#status,#trading').change(function() {
			$('#serach').click(); 
		});
		$('#areaid').change(function() {
			$('#trading').html('<option value="">请选择...</option>');
			$('#serach').click();
			if($(this).val() != '')
			{
				agreementList.initType($(this).val(),'4','trading');
			}	
		});
		$('#isSelf').click(function() 
		{
			if($('#isSelf').attr('checked'))
			{
				$('#isSelf').prop('checked',true);
			}
			else
			{
				$('#isSelf').prop('checked',false)
			}
			$('#serach').click();
		});
		$('#myDiv').click(function() 
		{
			if($('#isSelf').attr('checked'))
			{
				$('#isSelf').attr('checked',false);
			}
			else
			{
				$('#isSelf').attr('checked',true)
			}
			$('#serach').click();
		});
		
		// 时间按钮选择事件
		var nowTemp = new Date();
		$('#createtime').daterangepicker(
		{
			startDate : nowTemp.getFullYear() + '-'
					+ (nowTemp.getMonth() + 1) + '-02',
			timePicker12Hour : false,
			timePicker : false,
			separator : '~',
			// 分钟间隔时间
			timePickerIncrement : 10,
			format : 'YYYY-MM-DD'
			}, function(start, end, label) {
				console.log(start.toISOString(), end.toISOString(), label);
		});
		$('#keyword').keydown(function(e) {
    		if (e.which == "13") {
    		
    			// var focusActId = document.activeElement.id;获取焦点ID，根据ID判断执行事件
    			$("#serach").click();
    			return false;// 禁用回车事件
    		}
    	});
	},
	initSelect : function() {
		common.loadItem('GROUP.AGREEMENT', function(json) {
			var html = '<option value="">请选择...</option>';
			for ( var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#status').html(html);
		});
		agreementList.initType('101','3','areaid');
//		agreementList.initType('','4','trading');
		// accountid
		common.ajax({
			url : common.root + '/BaseHouse/getMangerList.do',
			dataType : 'json',
			loadfun : function(isloadsucc, json) {
				if (isloadsucc) {
					var html = '<option value="">请选择...</option>';
					for ( var i = 0; i < json.length; i++) 
					{
						html += '<option value="' + json[i].id + '" >'
								+ json[i].name + '</option>';
					}
					$('#accountid').html(html);
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},
	initType:function(fatherid, type ,id){
	   common.loadArea(fatherid,type, function(json){
	        var html = '<option value="">请选择...</option>';
	        $('#'+id).html('');
	        for (var i = 0; i < json.length; i++) {
				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
	        }
	        $('#'+id).html(html);
	    });
	},
	createTable : function() {
		table
				.init({
					id : '#agreementList_table',
					url : common.root + '/agreementMge/agreementList.do',
					expname : '房源合约',
					columns : [
							"house_code",
							"name",
							"xq_name",
							"userInfo",
							"agreement_time",
							"agreement_statue",
							"cost",
							"agentName",
							{
								name : "watercard",
								isshow : false,
								title : '水卡',
								isover : false
							},
							{
								name : "water_meter",
								isshow : false,
								title : '水表读数',
								isover : false
							},
							{
								name : "electriccard",
								isshow : false,
								title : '电卡帐号',
								isover : false
							},
							{
								name : "electricity_meter",
								isshow : false,
								title : '电表总值',
								isover : false
							},
							{
								name : "gascard",
								isshow : false,
								title : '燃气帐号',
								isover : false
							},
							{
								name : "gas_meter",
								isshow : false,
								title : '燃气读数',
								isover : false
							},
							{
								name : "create_time",
								isshow : false,
								title : '创建时间',
								isover : false
							}
//							"<div class=\"btn-group\"><button type=\"button\"  class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">操作<span class=\"caret\"></span></button><ul class='dropdown-menu ulClass'><li><a class='paddingClass' onclick='agreementList.edit(this,0);' href='#'>查看合约</a></li></ul></div>" 
							],
					param : function() {
						var a = getParamArray();
//						a.push({
//							"name" : "keyWord",
//							"value" : $('#keyword').val()
//						});
//						a.push({
//							"name" : "status",
//							"value" : $('#status').val()
//						});
//						a.push({
//							"name" : "arear",
//							"value" : $('#areaid').val()
//						});
//						a.push({
//							"name" : "accountid",
//							"value" : $('#accountid').val()
//						});
//						a.push({
//							"name" : "createtime",
//							"value" : $('#createtime').val()
//						}); 
//						a.push({
//							"name" : "isSelf",
//							"value" : $('#isSelf:checked').val()
//						});
//						a.push({
//							"name" : "trading",
//							"value" : $('#trading').val()
//						});
						return a;
					},
					expPage:function()
					{
						console.log('ccccc');
						var newColumns= new Array();
						newColumns.push({name:"code",title:"房源编码"});
						newColumns.push({name:"name",title:"合约名称"});
						newColumns.push({name:"agentName",title:"管家"});
						newColumns.push({name:"xq_name",title:"区域"});
						newColumns.push({name:"payType",title:"支付类型"});
						newColumns.push({name:"user_name",title:"房东姓名"});
						newColumns.push({name:"certificateno",title:"房东证件"});
						newColumns.push({name:"user_mobile",title:"房东号码"});
						newColumns.push({name:"free_period",title:"免租时长"});
						newColumns.push({name:"begin_time",title:"合约开始时间"});
						newColumns.push({name:"end_time",title:"合约结束时间"});
						newColumns.push({name:"cost",title:"总费用"});
						newColumns.push({name:"cost_a",title:"每年费用"});
						newColumns.push({name:"bankcard",title:"银行卡号"});
						newColumns.push({name:"cardowen",title:"开户人"});
						newColumns.push({name:"cardbank",title:"开户行"});
						newColumns.push({name:"keys_count",title:"房屋钥匙(门禁|楼层|入户)"});
						newColumns.push({name:"old_matched",title:"房间配置"});
						newColumns.push({name:"agreement_statue",title:"合约状态"});
						newColumns.push({name:"watercard",title:"水卡帐号"});
						newColumns.push({name:"water_meter",title:"水表读数"});
						newColumns.push({name:"gascard",title:"燃气帐号"});
						newColumns.push({name:"gas_meter",title:"燃气读数"});
						newColumns.push({name:"electriccard",title:"电表卡号"});
						newColumns.push({name:"electricity_meter",title:"电表总值"});
						newColumns.push({name:"electricity_meter_h",title:"电表峰值"});
						newColumns.push({name:"electricity_meter_l",title:"电表谷值"});
						newColumns.push({name:"create_time",title:"录入时间"});
						var href = common.root + '/agreementMge/agreementList.do?isDc=true&expname='+encodeURIComponent(encodeURI('房源合约'))+"&colum="+encodeURIComponent(encodeURI(JSON.stringify(newColumns)))+'&istz_=1';
//						var paramlist = $('#agreementList_table').data('paramlist');
						var paramlist = getParamArray();
						
						for(var i=0;i<paramlist.length;i++){
							href += "&"+paramlist[i].name+"="+encodeURI(encodeURI(paramlist[i].value));
						}
						common.load.load('正在执行导出，请稍等...');
						common.ajax({
							url: href,  
				        	dataType: 'json',
				        	loadfun: function(isloadsucc, data){
				        		common.load.hide(); 
				            	if (isloadsucc) {
				            		if(data.state == 1){
										window.location.href = data.name;
				            		}else{
				            			common.alert(common.msg.error);
				            		}
				            	}else{
				            		common.alert(common.msg.error);
				            	}
				            }
						});
					},
					bFilter : false,
					bnt:[ 
						 {
		                	 name:'查看合约',
		                	 isshow:function(data,row){
		                		 return true;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.edit(0);
		                	 }
		                 },
		                 {
		                	 name:'修改合约',
		                	 isshow:function(data,row){
		                		 return data.status == 0? true : false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.edit(1);
		                	 }
		                 },
		                 {
		                	 name:'删除合约',
		                	 isshow:function(data,row){
		                		 return data.status == 0? true : false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.del(1);
		                	 }
		                 },
		                 {
		                	 name:'提交合约',
		                	 isshow:function(data,row){
		                		 return data.status == 0? true : false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.submitAgreement();
		                	 }
		                 },
		                 {
		                	 name:'审批合约',
		                	 isshow:function(data,row){
		                		 return data.status == 1? true : false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.approvalAgreement(data.status);
		                	 }
		                 },
		                 {
		                	 name:'撤销合约',
		                	 isshow:function(data,row){
//		                		 alert(data.is_work == 0);
//		                		 return (data.status == 2 && data.is_work == 0)? true : false;
		                		 return false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.cancelAgreement();
		                	 }
		                 },
		                 {
		                	 name:'审批撤销',
		                	 isshow:function(data,row){
		                		 return data.status == 4 ? true : false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.spAgeement(data.status);
		                	 }
		                 },
		                 {
		                	 name:'结束合约',
		                	 isshow:function(data,row){
		                		 return data.status == 5 ? true : false;
		                	 },
		                	 fun:function(data,row){
		                		 rowdata = data;
		                		 agreementList.overAgreement();
		                	 }
		                 },
						 
					 ],
					createRow:function(rowindex,colindex,name,value,data){
						// console.log(data[colindex]);
//						console.log(data.house_status); 
						/** if(data.status == 0 && colindex == 8) // 待提交
						 {
							return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.edit(this,1);" href="#">修改合约</a></li><li><a class="paddingClass" onclick="agreementList.del(this);" href="#">删除合约</a></li><li><a class="paddingClass" onclick="agreementList.submitAgreement(this);" href="#">提交合约</a></li></ul></div>';
//							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.edit(this,1);' title='修改合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button>&nbsp;<button onclick='agreementList.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button onclick='agreementList.submitAgreement(this);' title='提交合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
						 }
						 else if(data.status == 1 && colindex == 8) // 提交待审批
						 {
							 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.approvalAgreement(this);" href="#">审批合约</a></li></ul></div>';
//							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button><button onclick='agreementList.approvalAgreement(this);' title='审批合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-filter\"></i></button></div>";
						 }
						 else if(data.status == 2 && colindex == 8 && data.is_work == 0) // 2已生效 
						 { 
							 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.cancelAgreement(this);" href="#">撤销合约</a></li></ul></div>';
//							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.cancelAgreement(this);' title='撤销合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button></div>";
						 }
//						 else if(data.status == 3 && colindex == 8) // 3 已失效
//						 {
//							 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" href="javascript:agreementList.cancelAgreement(this);">撤销合约</a></li></ul></div>';
//							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>";
//						 }
						 else if(data.status == 4 && colindex == 8) // 4审批撤销
						 {
							 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.spAgeement(this);" href="#">审批撤销</a></li></ul></div>';
//							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.spAgeement(this);' title='审批撤销' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
						 }
						 else if(data.status == 5 && colindex == 8) // 结束待审批
						 {
							 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="agreementList.edit(this,0);"  href="#"">查看合约</a></li><li><a class="paddingClass" onclick="agreementList.overAgreement(this);" href="#">结束合约</a></li></ul></div>';
//							 return "<div style='width: 100%;text-align: center'><button onclick='agreementList.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='agreementList.houseRelease(this);' title='发布合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
						 }*/
						 
						 if(data.status == 0 && colindex == 5)
						 {
							 return '<div style="color:#F8B62D;text-align: center;"><img src="/html/images/dtj.png">&nbsp;&nbsp;'+data.agreement_statue+'</div>';
						 }
						 else if(data.status == 1 && colindex == 5)
						 {
							 return '<div style="color:#ED7210;text-align: center;"><img src="/html/images/dsp.png">&nbsp;&nbsp;'+data.agreement_statue+'</div>';
						 }
						 else if(data.status == 2 && colindex == 5)
						 {
							 return '<div style="color:#3E82BC;text-align: center;"><img src="/html/images/dkg.png">&nbsp;&nbsp;'+data.agreement_statue+'</div>';
						 }
						 else if(data.status == 3 && colindex == 5)
						 {
							 return '<div style="color:#E74086;text-align: center;"><img src="/html/images/jsdsp.png">&nbsp;&nbsp;'+data.agreement_statue+'</div>';
						 }
						 else if(data.status == 4 && colindex == 5)
						 {
							 return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/djj.png">&nbsp;&nbsp;'+data.agreement_statue+'</div>';
						 }
						 else if(data.status == 5 && colindex == 5)
						 {
							 return '<div style="color:#D0B6D7;text-align: center;"><img src="/html/images/ysx.png">&nbsp;&nbsp;'+data.agreement_statue+'</div>';
						 }
						 
						 if(colindex == 0)
						 {
							 return "<a onclick='agreementList.edit2("+data.id+","+data.house_id+");return false;' href='#' title='点击查看合约信息' style='cursor: pointer;'>"+value+"</a>";
						 }
						 
					 
						 if(colindex == 1 || colindex == 3)
						 {
							 return agreementList.dealColum({"value":value,"length":16});
						 }
						 if(colindex == 0)
						 {
							 return agreementList.dealColum({"value":value,"length":35});
						 }
						 return value;
					}
				});
	},
	dealColum:function(opt)
	{
		 var def = {value:'',length:5};
		 jQuery.extend(def, opt);
		 if(common.isEmpty(def.value))
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
	// 修改
	edit:function(flag){
		var id = rowdata.id;
		var house_id = rowdata.house_id;
		var title = '修改合约';
		if(flag == 0)
		{
			title = '合约详细';
		}
		common.openWindow({
			area:['900px','630px'],
			name:'houseSign',
			type : 3,
			data:{agreementId:id,flag:flag,id:house_id},
			title : title,
			url : '/html/pages/house/houseInfo/houseSign.html'
		});
	},
	edit2:function(id,house_id){
		var title = '合约详细';
		common.openWindow({
			area:['900px','630px'],
			name:'houseSign',
			type : 3,
			data:{agreementId:id,flag:0,id:house_id},
			title : title,
			url : '/html/pages/house/houseInfo/houseSign.html'
		});
	},
	// 审批合约
	approvalAgreement:function(status){
		var id = rowdata.id;
		var houseId = rowdata.house_id;
		var isPass = 0;
		common.alert({
			msg : '此合约是否审核通过？',
			confirm : true,
			closeIcon:true,
			confirmButton:'通过',
			cancelButton:'拒绝',
			fun : function(action) 
			{
				if (action)
				{
					isPass = 2;
				}
				common.ajax({
					url : common.root + '/agreementMge/approvalAgreement.do',
					data : {
						id : id,
						isPass:isPass,
						houseId:houseId,
						status:status
					},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								//msg = isPass == 1?'审批通过操作成功!':'操作成功！';
								common.alert({
									msg : '操作成功！',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							} else if(data.state == -12){
								common.alert({
									msg : ' 未查询到财务项目信息  !',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							} else if(data.state == -2){
								common.alert({
									msg : '房源状态已经改变，请稍候操作!',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							}else {
								common.alert({
									msg : common.msg.error
								});
							}
						} else {
							common.alert({
								msg : common.msg.error
							});
						}
					}
				});
			}
		});
		
	},
	// 审批合约
	spAgeement:function(status){
		var id = rowdata.id;
		var houseId = rowdata.house_id;
		var isPass = 2;
		common.alert({
			msg : '是否将此合约置为无效？',
			confirm : true,
			confirmButton:'通过',
			cancelButton:'拒绝',
			closeIcon:true,
			fun : function(action) 
			{
				if (action)
				{
					isPass = 3;
				}
				common.ajax({
					url : common.root + '/agreementMge/spAgeement.do',
					data : {
						id : id,
						isPass:isPass,
						houseId:houseId,
						status:status
					},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								//msg = isPass == 1?'审批通过操作成功!':'操作成功！';
								common.alert({
									msg : '操作成功！',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							} else if(data.state == -2){
								common.alert({
									msg : '房源状态已经改变，请稍候操作!',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							} else if(data.state == -10){
								common.alert({
									msg : '工程已经开工,不能撤销!',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							}else if(data.state == -12){
								common.alert({
									msg : ' 未查询到财务项目信息  ',
									fun : function() {
										table.refreshRedraw('agreementList_table');
									}
								});
							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						} else {
							common.alert({
								msg : common.msg.error
							});
						}
					}
				});
			}
		});
	},
	// 提交合约
	submitAgreement:function(obj){
		var id = rowdata.id;
		common.alert({
			msg : '确定提交合约吗？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/agreementMge/submitAgreement.do',
						data : {
							id : id
						},
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data.state == '1') {
									common.alert({
										msg : '提交成功',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '合约状态已经改变,不能提交合约!',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
									});
								}
							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						}
					});
				}
			}
		});
	},
	// 结束合约
	overAgreement:function(obj){
//		console.log(table.getRowData('agreementList_table', obj));
		var id = rowdata.id;
		var house_id = rowdata.house_id;
		common.alert({
			msg : '确定结束房源合约吗？',
			confirm : true, 
			closeIcon:true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/agreementMge/overHouseAgreement.do',
						data : {id:id, houseId:house_id},
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data.state == '1') {
									common.alert({
										msg : '操作成功',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
									});
								}
							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						}
					});
				}
			}
		});
	},
	// 取消合约
	cancelAgreement:function(obj){
		var id = rowdata.id;
		var housid = rowdata.house_id;
		var isPass = 4;
		confirmButton:'是';
		cancelButton:'否';
		confirm : true,
		common.alert({
			msg : '您确定撤销此合约？',
			confirm : true,
			fun : function(action) {
				if(action)
				{
					common.ajax({
						url : common.root + '/agreementMge/cancelAgreement.do',
						data : {id : id,isPass:isPass,housid:housid},
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data.state == '1') {
									common.alert({
										msg : '操作成功!',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '房源状态审批,请重新查看!',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								}
								else if(data.state == -11)
								{
									common.alert({
										msg : '房源已开工无法撤销!',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								}
								else
								{
									common.alert({
										msg : common.msg.error
									});
								}
							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						}
					});
				}	
			}
		});
	},
	del: function(obj){
//		var table1 = table.getTable('agreementList_table');
		//获取当前行的数据信息,用来区分是新增还是修改的，同时也是修改的数据信息
//		console.log(rowdata);
		common.alert({
			msg : '确定删除合约吗？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/agreementMge/delAgreement.do',
						data : {id:rowdata.id,houseId:rowdata.house_id,status:rowdata.status},
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
//								console.log(data);
								if (data.state == '1') {
									common.alert({
										msg : '删除成功',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '合约状态发生改变,请重新确认!',
										fun : function() {
											table.refreshRedraw('agreementList_table');
										}
									});
								} else {
									common.alert({
										msg : common.msg.error
									});
								}
							} else {
								common.alert({
									msg : common.msg.error
								});
							}
						}
					});
				}
			}
		});
	},
//	//房源签约
//	houseSign:function(obj){
//		 console.log(table.getRowData('agreementList_table', obj));
//		var username = table.getRowData('agreementList_table', obj).username; //房主姓名
//		var user_id = table.getRowData('agreementList_table', obj).user_id; //房主id
//		var mobile = table.getRowData('agreementList_table', obj).mobile; //房主mobile
//		var house_name = table.getRowData('agreementList_table', obj).house_name; //房源名称
//		var id = table.getRowData('agreementList_table', obj).id;
//		var floor = table.getRowData('agreementList_table', obj).floor; // 楼层
//		var roomCnt = table.getRowData('agreementList_table', obj).spec.split("|"); //roomCnt
//		var is_cubicle = table.getRowData('agreementList_table', obj).is_cubicle; //roomCnt
//		var houseCode = table.getRowData('agreementList_table', obj).house_code; //roomCnt
//		var free_rent = table.getRowData('agreementList_table', obj).free_rent; //roomCnt
//		var title = '签约房源';
//		// 验证房源现在状态是否签约中，如果是签约中，不能签约房源
//		common.ajax(
//		{
//			url : common.root + '/agreementList/checkHouseStatus.do',
//			data : {id : id},
//			dataType : 'json', 
//			loadfun : function(isloadsucc, data) 
//			{
//				if (isloadsucc) 
//				{
//					if (data.state == '1') 
//					{
//						common.openWindow({
//							area:['900px','630px'],
//							name:'houseSign',
//							type : 3,
//							data:{id:id,username:username,user_id:user_id,mobile:mobile,house_name:house_name,floor:floor,roomCnt:roomCnt,is_cubicle:is_cubicle,houseCode:houseCode,free_rent:free_rent},
//							title : title,
//							url : '/html/pages/house/houseInfo/houseSign.html'
//						});
//					}
//					else
//					{
//						common.alert(
//						{
//							msg : '房源或许已经被签约,请重新操作!'
//						});
//						table.refreshRedraw('agreementList_table');
//					}
//				}
//			}
//		});
//	},
};
agreementList.init();

function getParamArray()
{
	var paramlist = new Array();
	paramlist.push({
		"name" : "keyWord",
		"value" : $('#keyword').val()
	});
	paramlist.push({
		"name" : "status",
		"value" : $('#status').val()
	});
	paramlist.push({
		"name" : "arear",
		"value" : $('#areaid').val()
	});
	paramlist.push({
		"name" : "accountid",
		"value" : $('#accountid').val()
	});
	paramlist.push({
		"name" : "createtime",
		"value" : $('#createtime').val()
	}); 
	paramlist.push({
		"name" : "isSelf",
		"value" : $('#isSelf:checked').val()
	});
	paramlist.push({
		"name" : "trading",
		"value" : $('#trading').val()
	});
	return paramlist;
}
