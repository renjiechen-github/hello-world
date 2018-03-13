var index2;
var rank_agreement_list = {
		// 页面初始化操作
		init : function() {
			// 时间按钮选择事件
			var nowTemp = new Date();
			$('#endtime').daterangepicker(
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
			// 创建表格
			rank_agreement_list.createTable();
			// 初始化下拉列表
			rank_agreement_list.initSelect();
		 
			$('#accountid,#status,#trading').change(function() {
				$('#serach').click();
			});
			$('#areaid').change(function() {
				$('#trading').html('<option value="">请选择...</option>');
				$('#serach').click();
				if($(this).val() != '')
				{
					rank_agreement_list.initType($(this).val(),'4','trading');
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
			$('#keyword').keydown(function(e) {
	    		if (e.which == "13") {
	    		
	    			// var focusActId = document.activeElement.id;获取焦点ID，根据ID判断执行事件
	    			$("#serach").click();
	    			return false;// 禁用回车事件
	    		}
	    	});
		},
		initSelect : function() {
			common.loadItem('GROUP.RANK.AGREEMENT', function(json) {
				var html = '<option value="">请选择...</option>';
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].item_id + '" >'
							+ json[i].item_name + '</option>';
				}
				$('#status').html(html);
			});
//			common.loadArea('101', '3', function(json) {
//				var html = "";
//				for ( var i = 0; i < json.length; i++) {
//					html += '<option value="' + json[i].id + '" >'
//							+ json[i].area_name + '</option>';
//				}
//				$('#areaid').append(html);
//			});
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
			rank_agreement_list.initType('101','3','areaid');
//			rank_agreement_list.initType('','4','trading');
//			common.ajax(
//			{
//				url : common.root + '/BaseHouse/getTrading.do',
//				dataType : 'json',
//				async:false,
//				loadfun : function(isloadsucc, json) 
//				{
//					if (isloadsucc)
//					{
//						var html = "";
//						for ( var i = 0; i < json.length; i++) 
//						{
//							html += '<option value="' + json[i].id + '" >'
//								 + json[i].area_name + '</option>';
//						}
//						$('#trading').append(html);
//					}
//				}
//			});	
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
			table.init({
						id : '#rank_agreement_list_table',
						url : common.root + '/rankHouse/rankAgreementList.do',
						expname : '租房合约列表',
						columns : [
								"agree_code",
								"name",
								"title",
								"userInfo",
								"end_time",
								"rankType",
								"cost_a",
								"rentMonth",
								"cost",
								"rankstatus",
								"checkouttime",
								{
									name : "rankspec",
									isshow : false,
									title : '出租房间数',
									isover : true
								},
								{
									name : "rank_area",
									isshow : false,
									title : '面积(㎡)',
									isover : true
								},
								{
									name : "agentName",
									isshow : false,
									title : '管家',
									isover : true
								},
								{
									name : "begin_time",
									isshow : false,
									title : '开始时间',
									isover : true
								},
								{
									name : "create_time",
									isshow : false,
									title : '创建时间',
									isover : true
								},
								{
									name : "areaName",
									isshow : false,
									title : '区域',
									isover : true
								},
//								"<div class=\"btn-group\"><button type=\"button\"  class=\"btn btn-default dropdown-toggle\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">操作<span class=\"caret\"></span></button><ul class=\"dropdown-menu ulClass\"><li><a class=\"paddingClass\" onclick='rank_agreement_list.edit(this,0)' href='#'>查看合约</a></li></ul></div>" 
								],
						   bnt:[ 
								   {
					                	 name:'查看合约',
					                	//控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return true;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.edit2(0);
					                	 }
					                 },
					                 {
					                	 name:'修改合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return data.status == 0?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.edit2(1);
					                	 }
					                 },
					                 {
					                	 name:'删除合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return (data.status == 0 || data.status == 11)?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.del(data.status);
					                	 }
					                 },
					                 {
					                	 name:'提交合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return data.status == 0?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.submitAgreement();
					                	 }
					                 },
					                 {
					                	 name:'审批合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return data.status == 1?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.approvalAgreement();
					                	 }
					                 },
					                 {
					                	 name:'撤销合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
//					                		 return data.status == 2?true:false;
					                		 return false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.cancelAgreement();
					                	 }
					                 },
					                 {
					                	 name:'审批撤销',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return data.status == 4?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.spAgeement();
					                	 }
					                 },
					                 {
					                	 name:'结束合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return data.status == 5?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.overAgreement();
					                	 }
					                 },
					                 {
					                	 name:'结束合约',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
					                		 return data.status == 6?true:false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.overRankAgreement();
					                	 }
					                 },
					                 {
					                	 name:'退租',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
						                		var returnI=false;
						                		console.log(data);
						                		if (data.status=="2") {
				                				var param = {'rentalLeaseOrderId' : data.id};
				                				common.ajax({
													url : common.root+ '/cancelLease/checkCancelLeaseOrder.do',
													dataType : 'json',
													contentType : 'application/json; charset=utf-8',
													encode : false,
													async:false,
													data : JSON.stringify(param),
													loadfun : function(isloadsucc,data) 
													{
														if (isloadsucc) 
														{
														   if (data.haveOrder=="N")
														   {
															   returnI=true;
														   }
														}
													}
												});
												}
												return returnI;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.showTj(data.id);
					                	 }
					                 },
					                 {
					                	 name:'合约公证',
					                	 //控制按钮是否显示 返回true显示，否则不显示 data 当前行的数据信息，row当前行的对象信息
					                	 isshow:function(data,row){
//					                		 return data.status == 7 ? true:false;
					                		 return false;
					                	 },
					                	 fun:function(data,row){
					                		 rowdata = data;
					                		 rank_agreement_list.rankAgreementNotarization(data.id, data.house_id,data.user_id);
					                	 } 
					                 }
						        ],		
						param : function() {
							var a = new Array();
							a.push({
								"name" : "keyWord",
								"value" : $('#keyword').val()
							});
							a.push({
								"name" : "status",
								"value" : $('#status').val()
							});
							a.push({
								"name" : "arear",
								"value" : $('#areaid').val()
							});
							a.push({
								"name" : "accountid",
								"value" : $('#accountid').val()
							});
							a.push({
								"name" : "isSelf",
								"value" : $('#isSelf:checked').val()
							});
							a.push({
								"name" : "trading",
								"value" : $('#trading').val()
							});
							a.push({
								"name" : "endtime",
								"value" : $('#endtime').val()
							});
							a.push({
								"name" : "selectTime",
								"value" : $('#selectTime').val()
							});
							return a;
						},
						bFilter : false,
						createRow:function(rowindex,colindex,name,value,data){
//							 console.log(data[colindex]);
//							console.log(data.status); 
							/* if(data.status == 0 && colindex == 8) // 待提交
							 {
								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,1);" href="#">修改合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.del(this);" href="#">删除合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.submitAgreement(this);" href="#">提交合约</a></li></ul></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='rank_agreement_list.edit(this,1);' title='修改合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-edit\"></i></button>&nbsp;<button onclick='rank_agreement_list.del(this);' title='删除' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button>&nbsp;<button onclick='rank_agreement_list.submitAgreement(this);' title='提交合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
							 }
							 else if(data.status == 1 && colindex == 8) // 提交待审批
							 {
								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.approvalAgreement(this);" href="#">审批合约</a></li></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button><button onclick='rank_agreement_list.approvalAgreement(this);' title='审批合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-filter\"></i></button></div>";
							 }
							 else if(data.status == 2 && colindex == 8) // 2已生效 
							 {
								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.cancelAgreement(this);" href="#">撤销合约</a></li></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='rank_agreement_list.cancelAgreement(this);' title='撤销合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-times\"></i></button></div>";
							 }
//							 else if(data.status == 3 && colindex == 8) // 3 已失效
//							 {
//								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" href="javascript:rank_agreement_list.cancelAgreement(this);">撤销合约</a></li></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>";
//							 }
							 else if(data.status == 4 && colindex == 8) // 4审批撤销
							 {
								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.spAgeement(this);" href="#">审批撤销</a></li></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='rank_agreement_list.spAgeement(this);' title='审批撤销' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
							 }
							 else if(data.status == 5 && colindex == 8) // 合约已到期 合约已到期
							 {
								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.overAgreement(this);" href="#">结束合约</a></li></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='rank_agreement_list.houseRelease(this);' title='发布合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
							 }
							 else if(data.status == 6 && colindex == 8) // 合约已到期 结束合约
							 {
								 return '<div class="btn-group"><button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">操作<span class="caret"></span></button><ul class="dropdown-menu ulClass"><li><a class="paddingClass" onclick="rank_agreement_list.edit(this,0)" href="#">查看合约</a></li><li><a class="paddingClass" onclick="rank_agreement_list.overRankAgreement(this);" href="#">结束合约</a></li></div>'
//								 return "<div style='width: 100%;text-align: center'><button onclick='rank_agreement_list.edit(this,0);' title='查看合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-bars\"></i></button>&nbsp;<button onclick='rank_agreement_list.houseRelease(this);' title='发布合约' class=\"button button-circle button-tiny\"><i class=\"fa fa-check\"></i></button></div>";
							 }
							 */
							 if(colindex == 10)
							 {
								 if (data.checkouttime==null||data.checkouttime==""||data.checkouttime=="undefined")
								 {
									 return '<div style="text-align: center;">&nbsp;&nbsp;-&nbsp;-</div>'
								 }else{
									 return '<div style="text-align: left;">&nbsp;&nbsp;'+data.checkouttime+'</div>'
								 }
								 //return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/djj.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							
							 if(data.status == 0 && colindex == 9)
							 {
								 return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/djj.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							 else if(data.status == 1 && colindex == 9)
							 {
								 return '<div style="color:#ED7210;text-align: center;"><img src="/html/images/dsp.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							 else if(data.status == 2 && colindex == 9)
							 {
								 return '<div style="color:#A94F9A;text-align: center;"><img src="/html/images/yfb.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							 else if(data.status == 3 && colindex == 9)
							 {
								 return '<div style="color:#ff0000;text-align: center;"><img src="/html/images/qyz.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							 else if(data.status == 4 && colindex == 9)
							 {
								 return '<div style="color:#F8B62D;text-align: center;"><img src="/html/images/dtj.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							 else if(data.status == 5 && colindex == 9)
							 {
								 return '<div style="color:#D0B6D7;text-align: center;"><img src="/html/images/ysx.png">&nbsp;&nbsp;'+data.rankstatus+'</div>';
							 }
							 
							 if(colindex == 0)
							 {
								 return "<a onclick='rank_agreement_list.edit(this,0);return false;' href='#' title='点击查看合约信息' style='cursor: pointer;'>"+value+"</a>";
							 }
							 
							 if(colindex == 2)
							 {
								 return rank_agreement_list.dealColum({"value":value,"length":5});
							 }
							 if(colindex == 0)
							 {
								 return rank_agreement_list.dealColum({"value":value,"length":35});
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
		edit:function(obj,flag){
		//	console.log(rowdata);
			rowdata = table.getRowData('rank_agreement_list_table', obj);
			var name = rowdata.title;
			var house_rank_id = rowdata.house_rank_id;
			var agreementId = rowdata.id;
			var house_id = rowdata.house_id;
			var rankType = rowdata.rankType;
			var title = '修改合约';
			if(flag == 0)
			{
				title = '合约详细';
			}
			common.openWindow({
				area:['900px','630px'],
				name:'signHouse',
				type : 3,
				data:{id:house_rank_id,flag:flag,houseId:house_id,rankType:rankType,title:name,agreementId:agreementId},
				title : title,
				url : '/html/pages/house/houseInfo/rank_house_agreement.html'
			});
		},
		edit2:function(flag){
			//	console.log(rowdata);
			var name = rowdata.title;
			var house_rank_id = rowdata.house_rank_id;
			var agreementId = rowdata.id;
			var house_id = rowdata.house_id;
			var rankType = rowdata.rankType;
			var title = '修改合约';
			if(flag == 0)
			{
				title = '合约详细';
			}
			common.openWindow({
				area:['900px','630px'],
				name:'signHouse',
				type : 3,
				data:{id:house_rank_id,flag:flag,houseId:house_id,rankType:rankType,title:name,agreementId:agreementId},
				title : title,
				url : '/html/pages/house/houseInfo/rank_house_agreement.html'
			});
		},
		// 审批合约
		approvalAgreement:function(obj){
			var house_id =rowdata.house_id;
			var id = rowdata.id;
			var isPass = 0;
			common.alert({
				msg : '此合约是否审核通过？',
				confirm : true,
				confirmButton:'通过',
				closeIcon:true,
				cancelButton:'拒绝',
				fun : function(action) 
				{
					if (action)
					{
						isPass = 2;
					}
					common.ajax({
						url : common.root + '/rankHouse/approvalAreement.do',
						data : {
							id : id,
							isPass:isPass,
							house_id:house_id
						},
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data.state == '1') {
									//msg = isPass == 1?'审批通过操作成功!':'操作成功！';
									common.alert({
										msg : '操作成功！',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '合约状态已经改变，请稍候操作!',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
										}
									});
								} else if(data.state == -4){
									common.alert({
										msg : '未查询到房源合约信息!',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
										}
									});
								} else if(data.state == -5){
									common.alert({
										msg : '未查询到财务项目!',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
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
		// 审批合约
		spAgeement:function(obj){
			var id = rowdata.id;
			var houseId = rowdata.house_id;
			var house_rank_id = rowdata.house_rank_id;
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
						url : common.root + '/rankHouse/spRankAgeement.do',
						data : {
							id : id,
							isPass:isPass,
							houseId:houseId,
							house_rank_id:house_rank_id
						},
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data.state == '1') {
									//msg = isPass == 1?'审批通过操作成功!':'操作成功！';
									common.alert({
										msg : '操作成功！',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '合约状态已经改变，请稍候操作!',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
										}
									});
								} else if(data.state == -12){
									common.alert({
										msg : '未查询到财务数据!',
										fun : function() {
											table.refreshRedraw('rank_agreement_list_table');
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
							url : common.root + '/rankHouse/submitRankAgreement.do',
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
												table.refreshRedraw('rank_agreement_list_table');
											}
										});
									} else if(data.state == -2){
										common.alert({
											msg : '合约状态已经改变,不能提交合约!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
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
			var id = rowdata.id;
			var isPass = 4;
			confirmButton:'是';
			cancelButton:'否';
			confirm : true,
			common.alert({
				msg : '您确定结束此合约？',
				closeIcon:true,
				confirm : true,
				fun : function(action) {
					if(action)
					{
						common.ajax({
							url : common.root + '/rankHouse/spOverRankAgeement.do',
							data : {id : id,isPass:isPass},
							dataType : 'json',
							loadfun : function(isloadsucc, data) {
								if (isloadsucc) {
									if (data.state == '1') {
										common.alert({
											msg : '操作成功!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
											}
										});
									} else if(data.state == -2){
										common.alert({
											msg : '合约状态已改变,请重新查看!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
											}
										});
									} else if(data.state == -12){
										common.alert({
											msg : '财务数据没有找到！',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
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
			var isPass = 4;
			confirmButton:'是';
			cancelButton:'否';
			confirm : true,
			common.alert({
				msg : '您确定撤销此合约？',
				confirm : true,
				closeIcon:true,
				fun : function(action) {
					if(action)
					{
						common.ajax({
							url : common.root + '/rankHouse/cancelRankAgreement.do',
							data : {id : id,isPass:isPass},
							dataType : 'json',
							loadfun : function(isloadsucc, data) {
								if (isloadsucc) {
									if (data.state == '1') {
										common.alert({
											msg : '操作成功!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
											}
										});
									} else if(data.state == -2){
										common.alert({
											msg : '合约状态审批,请重新查看!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
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
		// 合同到期 结束合约
		overRankAgreement:function(obj){
//			console.log(rowdata);
			var id = rowdata.id;
			var house_rank_id = rowdata.house_rank_id;
			var house_id = rowdata.house_id;
			confirmButton:'是';
			cancelButton:'否';
			confirm : true,
			common.alert({
				msg : '合同到期是否结束合约？',
				confirm : true,
				closeIcon:true,
				fun : function(action) {
					if(action)
					{
						common.ajax({
							url : common.root + '/rankHouse/overRankAgreement.do',
							data : {id : id,rankId:house_rank_id,houseId:house_id},
							dataType : 'json',
							loadfun : function(isloadsucc, data) {
								if (isloadsucc) {
									if (data.state == '1') {
										common.alert({
											msg : '操作成功!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
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
		del: function(status){
			//获取当前行的数据信息,用来区分是新增还是修改的，同时也是修改的数据信息
			common.alert({
				msg : '确定需要删除合约吗？',
				confirm : true,
				fun : function(action) {
					if (action) {
						common.ajax({
							url : common.root + '/rankHouse/delRankAgreement.do',
							data : {id:rowdata.id,houseId:rowdata.house_id,house_rank_id:rowdata.house_rank_id,status:status},
							dataType : 'json',
							loadfun : function(isloadsucc, data) {
								if (isloadsucc) {
									if (data.state == '1') {
										common.alert({
											msg : '删除成功',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
											}
										});
									} else if(data.state == -2){
										common.alert({
											msg : '合约状态发生改变,请重新确认!',
											fun : function() {
												table.refreshRedraw('rank_agreement_list_table');
											}
										});
									}else if(data.state == -12){
										common.alert({
											msg : '当前房源已下架,请勿删除!',
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
		showTj:function(id)
		{
//			 index2 = layer.open({
//		            type: 1,
//		            title:'房源退租',
//		            skin: 'layui-layer-demo', //加上边框
//		            area: ['300px', '140px'], //宽高
//		            content: ' <div class="head">	<div id="color1" class="leftdiv tab" onmousemove="movediv(this)" onclick="baseHouse.expAllHouse();return false;" onmouseout="normaldiv(this)">全部房源</div>  	<div id="color" class="rightdiv tab" onclick="baseHouse.expKZHouse();return false;" onmousemove="movediv(this)" onmouseout="normaldiv(this)">空置房源</div> </div>'
//		        });
			common.openWindow({
				area:['500px','300px'],
				name:'showTj',
				type : 1,
				data:{id:id},
				title : '合约退租',
				url : '/html/pages/house/rankHouse/tjAgreement.html'
			});
		},
		rankAgreementNotarization(id, house_id, user_id)
		{
		//	console.log('{"id":'+id+',"house_id":'+house_id+',"userId":'+user_id+'}');
			$('#content').val('');
			$('#signed').val('');
			$('#key').val('');
			$('#apiid').val('');
			common.ajax({
				url : common.root + '/rankHouse/rankAgreementNotarization.do',
				data : {id:id,houseId:house_id,userId:user_id},
				dataType : 'json',
				loadfun : function(isloadsucc, data) {
					if (isloadsucc) {
						if (data.result == '1') {
							$('#form3').attr("action",data.url);
							$('#content').val(data.content);
							$('#signed').val(data.signed);
							$('#key').val(data.key);
							$('#apiid').val(data.apiid);
							$('#form3').submit();
						} else if(data.result == -1){
							common.alert({
								msg : data.msg,
								fun : function() {
									table.refreshRedraw('rank_agreement_list_table');
								}
							});
						} else {
							common.alert({
								msg : '网络忙,稍候重试'
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
	}; 
$(rank_agreement_list.init());