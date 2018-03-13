var $modal = $('#my-popup');// 实例化modal
var $updatePricemodal = $('#updateModal');// 实例化modal
var index2;
var baseHouse = {
	// 页面初始化操作
	init : function() {
		
		// 初始化时间
		var now_time = new Date();
		var pre_time = new Date();
		pre_time.setFullYear(pre_time.getFullYear() - 1);
		// 年
		var query_year = now_time.getFullYear();
		var pre_query_year = pre_time.getFullYear();
		// 月
		var query_month = now_time.getMonth() + 1;
		query_month = query_month < 10 ? "0" + (query_month) : query_month;
		var pre_query_month = pre_time.getMonth() + 1;
		pre_query_month = pre_query_month < 10 ? "0" + (pre_query_month) : pre_query_month;
		// 日
		var query_day = now_time.getDate();
		query_day = query_day < 10 ? "0" + query_day : query_day;
		var pre_query_day = pre_time.getDate();
		pre_query_day = pre_query_day < 10 ? "0" + pre_query_day : pre_query_day;

		var query_begin_time = pre_query_year + "-" + pre_query_month + "-" + pre_query_day;
		var query_end_time = query_year + "-" + query_month + "-" + query_day;

		$("#createtime").daterangepicker({
			startDate : query_begin_time,
			endDate : query_end_time,
			timePicker12Hour : false,
			separator : '~',
			format : 'YYYY-MM-DD'
		}, function(start, end, label) {
			console.log(start.toISOString(), end.toISOString(), label);
		});		
		$("#createtime").val(query_begin_time + "~" + query_end_time);
		// 初始化下拉列表
		baseHouse.initSelect();
		baseHouse.initTrading();
		baseHouse.initgroup();
		// 创建表格
		baseHouse.createTable();

		$('.bnt_add').click(function() {
			baseHouse.add();
		});
		 // enter监听事件
    	$('#keyword').keydown(function(e) {
    		if (e.which == "13") {
    			$("#serach").click();
    			return false;// 禁用回车事件
    		}
    	});
		
		$('#publish,#status,#trading').change(function() {
			// $('#serach').click();
		});
		
		$('#areaid').change(function() {
			$('#trading').html('<option value="">请选择...</option>');
			// $('#serach').click();
			if($('#areaid').val() != '') {
				baseHouse.initType($(this).val(),'4','trading');
			}
			if($('#areaid').val() != '') {
				baseHouse.selectGroup($(this).val());
			}			
		});
		
		$('#isSelf').click(function() {
			if($('#isSelf').attr('checked'))
			{
				$('#isSelf').prop('checked',true);
			}
			else {
				$('#isSelf').prop('checked',false)
			}
			// $('#serach').click();
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
			// $('#serach').click();
		});
		$('#kzhouse').click(function() 
		{
			if($('#kzhouse').attr('checked'))
			{
				$('#kzhouse').prop('checked',true);
			}
			else
			{
				$('#kzhouse').prop('checked',false)
			}
			// $('#serach').click();
		});
		$('#myDiv2').click(function() 
		{
			if($('#kzhouse').attr('checked'))
			{
				$('#kzhouse').attr('checked',false);
			}
			else
			{
				$('#kzhouse').attr('checked',true)
			}
			// $('#serach').click();
		});
	},
	
	// 初始化
	initTrading : function() {
		var html = '<option value="">请选择...</option>';
		$('#trading').html(html);
		$('#trading').select2({'width' : '200px'});
	},
	
	/**
	 * 初始化小区
	 */
	initgroup : function() {
		var html = '<option value="">请选择...</option>';
		$('#groupid').html(html);
		$('#groupid').select2({'width' : '200px'});
	},
	
	/**
	 * 小区
	 */
	selectGroup : function(id) {
		common.ajax({
			url : common.root + '/group/getGroupInfoByAreaId.do',
			data : {
				id : id
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc) {
	      	var html = '<option value="">请选择...</option>';
	        $("#groupid").html('');
	        for (var i = 0; i < data.length; i++) {
	 					html += '<option  value="' + data[i].id + '" >' + data[i].group_name + '</option>';
	        }
	        $("#groupid").html(html);
	        $("#groupid").select2({'width' : '200px'});
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});		
	},
	
	initSelect : function() {
		common.loadItem('BASEHOUSE.STATUS', function(json) {
			var html = '<option value="">请选择...</option>';
			for ( var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
						+ json[i].item_name + '</option>';
			}
			$('#status').html(html);
		});
		common.loadItem('BASEHOUSE.PUBLISH', function(json) {
			var html = '<option value="">请选择...</option>';
			for ( var i = 0; i < json.length; i++) {
				html += '<option value="' + json[i].item_id + '" >'
				+ json[i].item_name + '</option>';
			}
			$('#publish').html(html);
		});
	    baseHouse.initType('101','3','areaid');
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
				$('#' + id).select2({'width' : '200px'});
	},
	
	createTable : function() {
		table
				.init({
					id : '#basehouse_table',
					url : common.root + '/BaseHouse/getHouseList.do',
					expname : '收房列表',
					columns : [
							"house_code",
							"house_name",
							"address",
							"areaName",
							"sqname",
							"group_name",
							"specName",
							"newSpecName",
							"username",
							"houseStatus",
							"publishname",
							"nm",
							"name",
							{
								name : "decorateType",
								isshow : false,
								title : '原装修',
								isover : true
							},
							{
								name : "area",
								isshow : false,
								title : '面积',
								isover : true
							}
							],
					param : function() {
						var a = getParamArray();
						return a;
					},
					expPage:function(href)
					{
						baseHouse.exportHouse();
					},
					bFilter : false,
					overflowfun:function(aData,head)
					{
						var sOut = "";
							var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;"><tr>';
							for(var i=0;i<head.length;i++)
							{
								var name = head[i].name;
								sOut += '<td>'+head[i].title+': '+aData[name]+'</td>';
							}
							if(aData.house_status > 4)
							{
								var id = aData.id;
								common.ajax({
									url : common.root + '/BaseHouse/checkHouseMenory.do',
									async:false,
									data : {
										houseId : id 
									},
									dataType : 'json',
									loadfun : function(isloadsucc, data) 
									{
										if (isloadsucc)
										{
											sOut += '<td>收房价格:'+data.agreementMenory+'元</td><td>工程价格:'+data.allOnstructionMonery+'元</td>' +'<td>房源基准参考价格: '+data.sumcnt+'元</td>';
										}
									}
								});	
							} 
							sOut += '</tr></table><div class="row" id="appenrow">';
							if(aData.house_status=='9'||aData.house_status=='5'||aData.house_status=='6')
							{
								common.ajax(
								{
									url : common.root + '/BaseHouse/seeRentHouse.do',
									data : {id : aData.id},
									dataType : 'json',
									async:false,
									loadfun : function(isloadsucc, data) 
									{
										if (isloadsucc)
										{
											console.log(data);
											var html="";
										  var html3="";
											for(var i=0;i<data.length;i++)
											{
												var html1="";// 按钮
												var value = data[i];
												var path="";
												path=value.path;
												if (aData.house_status=='9') {
													if ((value.rank_status=='1'||value.rank_status=='0'||value.rank_status=='2')&&aData.publish==0)
													{
														html1='<button onclick=\'baseHouse.sigleHouseInfo('+value.id+');\' style="margin-top: -20px;" class=\"btn btn-default\">详情</button>&nbsp;<button  type="button" class="btn btn-default" style="margin-top: -20px;"   onclick="baseHouse.rankUpdate('+ aData.id+ ','+ value.id+','+aData.group_id+',\''+aData.spec+'\')" >修改</button>';
													}
													else if (value.rank_status=='3') {
														html1='<button onclick=\'baseHouse.sigleHouseInfo('+value.id+');\' style="margin-top: -20px;" class=\"btn btn-default\">详情</button>&nbsp;<button onclick="baseHouse.signSigleHouse(\''+value.title+'\',\''+value.id+'\',\''+value.rankType+'\',\''+aData.id+'\',\''+value.fee+'\',\''+aData.address+'\',\''+aData.m+'\',\''+value.rank_type+'\',\''+aData.area+'\',\''+value.rank_code+'\',\''+value.roomcnt+'\');" style="margin-top: -20px;" class=\"btn btn-default\">签约</button>';
														html1+='<button onclick="baseHouse.updateRankHousePrice(\''+value.id+'\',\''+value.fee+'\',\''+value.title+'\',\''+value.rank_code+'\')" style="margin-top: -20px;"  class=\"btn btn-default\">调价</button>'
													}
													else{
														html1='<button onclick=\'baseHouse.sigleHouseInfo('+value.id+');\' style="margin-top: -20px;" class=\"btn btn-default\">详情</button>&nbsp;';
														html1+='<button onclick="baseHouse.updateRankHousePrice(\''+value.id+'\',\''+value.fee+'\',\''+value.title+'\',\''+value.rank_code+'\')" style="margin-top: -20px;"  class=\"btn btn-default\">调价</button>'
													}
												}
													else
													{
														if ((value.rank_status=='1'||value.rank_status=='0')&&aData.publish==0)
														{
															html1='<button onclick=\'baseHouse.sigleHouseInfo('+value.id+');\' style="margin-top: -20px;" class=\"btn btn-default\">详情</button>&nbsp;<button  type="button" class="btn btn-default" style="margin-top: -20px;"   onclick="baseHouse.rankUpdate('+ aData.id+ ','+ value.id+','+aData.group_id+',\''+aData.spec+'\')" >修改</button>';
														}
														else
														{
															html1='<button onclick=\'baseHouse.sigleHouseInfo('+value.id+');\' style="margin-top: -20px;" class=\"btn btn-default\">详情</button>&nbsp;';
														}
													}
													
											if (value.rank_type=="0") {
												html3 ='<div class="col-md-3 form-horizontal adminex-form ">'
													
													+'	<div class="panel-group " id="accordion">'
													+'	<div class="panel panel-info " style="border: 1px solid #46B8DA;">'
													+'		<div class="panel-heading">'
													+'				<h4 class="panel-title">'
													+'<div class="row">'
										            +'<div class="col-md-8">'+value.title+'</div>'
													+'	<div class="col-md-4" >'+value.rankType+' <span id="status'+value.id+'" ><font color="red">'+value.rankStatus+'</font></span></div>'
													+'	</div'
													+'	</h4>'
													+'		</div>'
													+'		<div id="collapsep" class="panel-collapse collapse in">'
													+'			<div class="panel-body ">'
													+'				<div class="form-group">'
													+'					<div class="col-md-6 "><font color="#555">面积：</font></div>'
													+'					<div class="col-md-6">'
													+'						<span  id="area'+value.id+'" style="color:#555">'+value.rank_area+'</span>'
													+'					</div>'
													+'				</div>'
													+'				<div class="form-group">'
													+'					<label class="col-md-6"><font color="#555">价格：</font></label>'
													+'					<div class="col-md-6">'
													+'						<span  id="fee'+value.id+'"  style="color:#555">'+value.fee+'</span>'
													+'					</div>'
													+'				</div>'
													+'			</div>'
													+'		</div>'
													+'		<div class="modal-footer" style="height: 10px;">'+html1+''                                          // id,rankArea,fee,path
													+'		</div>'
													+'	</div>'
													+'</div>'
													+'</div>';
											}else{
												html +='<div class="col-md-3 form-horizontal adminex-form ">'
													+'	<div class="panel-group " id="accordion">'
													+'	<div class="panel panel-info " style="border: 1px solid #46B8DA;">'
													+'		<div class="panel-heading">'
													+'				<h4 class="panel-title">'
													+'<div class="row">'
										            +'<div class="col-md-8">'+value.title+'</div>'
													+'	<div class="col-md-4" >'+value.rankType+' <span id="status'+value.id+'" ><font color="red">'+value.rankStatus+'</font></span></div>'
													+'	</div'
													+'	</h4>'
													+'		</div>'
													+'		<div id="collapsep" class="panel-collapse collapse in">'
													+'			<div class="panel-body ">'
													+'				<div class="form-group">'
													+'					<div class="col-md-6 "><font color="#555">面积：</font></div>'
													+'					<div class="col-md-6">'
													+'						<span  id="area'+value.id+'" style="color:#555" >'+value.rank_area+'</span>'
													+'					</div>'
													+'				</div>'
													+'				<div class="form-group">'
													+'					<label class="col-md-6"><font color="#555">价格：</font></label>'
													+'					<div class="col-md-6">'
													+'						<span  id="fee'+value.id+'"  style="color:#555">'+value.fee+'</span>'
													+'					</div>'
													+'				</div>'
													+'			</div>'
													+'		</div>'
													+'		<div class="modal-footer" style="height: 10px;">'+html1+''                                          // id,rankArea,fee,path
													+'		</div>'
													+'	</div>'
													+'</div>'
													+'</div>';
											}
											}
											sOut += html3+html+'</div>';
										}
									}
								});
								return sOut;
							}
							else
							{
								return sOut;
							}
					},
					bnt:[{
							 name:'查看房源',
							 isshow:function(data,row)
							 {
		                		 return true;
		                	 },
		                	 fun:function(data,row)
		                	 {
		                		 rowdata = data;
		                		 baseHouse.edit(0);
		                	 }
						},
						{
							 name:'修改房源',
							 isshow:function(data,row)
							 {
								 return data.house_status == 0?true:false;
		                	 },
		                	 fun:function(data,row)
		                	 {
		                		 rowdata = data;
		                		 baseHouse.edit(1);
		                	 }
						},
						{
							name:'删除房源',
							isshow:function(data,row)
							{
								return (data.house_status == 0 || data.house_status == 10)?true:false;
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.del();
							}
						},
						{
							name:'提交房源',
							isshow:function(data,row)
							{
								return data.house_status == 0?true:false;
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.submitHouse();
							}
						},
						{
							name:'审批房源',
							isshow:function(data,row)
							{
								return data.house_status == 1?true:false;
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.approvalHouse();
							}
						},
						{
							name:'签约房源',
							isshow:function(data,row)
							{
								return data.house_status == 2?true:false;
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.houseSign();
							}
						},
						{
							name:'撤销发布',
							isshow:function(data,row)
							{
								if((data.house_status == 5 || data.house_status == 6 || data.house_status == 9) && data.publish == 2)
								{
									return true;
								}	
								else
								{
									return false;
								}	 
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.soldOut(1);
							}
						},
						{
							name:'发布房源',
							isshow:function(data,row)
							{
								if((data.house_status == 5 || data.house_status == 6 || data.house_status == 9) && data.publish == 0)
								{
									return true;
								}	
								else
								{
									return false;
								}	 
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.houseRelease(row);
							}
						},
						{
							name:'发布房源审批',
							isshow:function(data,row)
							{
								if((data.house_status == 5 || data.house_status == 6 || data.house_status == 9) && data.publish != 0 && data.publish != 2)
								{
									return true;
								}	
								else
								{
									return false;
								}	 
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.approvalPublish(data.monery);
							}
						},
						
						{
							name:'交接房源',
							isshow:function(data,row)
							{
								if(data.house_status == 6)
								{
									return true;
								}	
								else
								{
									return false;
								}	 
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.houseTransfer();
							}
						},
						{
							name:'取消精品',
							isshow:function(data,row)
							{
								if(data.house_status == 9 && data.is_top == 1)
								{
									return true;
								}	
								else
								{
									return false;
								}	 
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.istop(0,data.id);
							}
						},
						{
							name:'设为精品',
							isshow:function(data,row)
							{
								if(data.house_status == 9 && data.is_top == 0)
								{
									return true;
								}	
								else
								{
									return false;
								}	 
							},
							fun:function(data,row)
							{
								rowdata = data;
								baseHouse.istop(1,data.id);
							}
						},
						{
							 name:'缴纳水电煤费',
							 isshow:function(data,row)
							 {
		                		 // return data.n > 0?true:false;
								 return true;
		                	 },
		                	 fun:function(data,row)
		                	 {
		                		 rowdata = data;
		                		 baseHouse.submitSDM();
		                	 }
						}
					],
					createRow:function(rowindex,colindex,name,value,data,row){
						 if(colindex == 1 || colindex == 3 || colindex == 2)
						 {
							 return baseHouse.dealColum({"value":value,"length":5});
						 }
						 
						 if(data.house_status == -1 && colindex == 8)
						 {
							 return '<div style="color:#94C749;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 0 && colindex == 9)
						 {
							 return '<div style="color:#F8B62D;text-align: center;"><img src="/html/images/dtj.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 1 && colindex == 9)
						 {
							 return '<div style="color:#ED7210;text-align: center;"><img src="/html/images/dsp.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 2 && colindex == 9)
						 {
							 return '<div style="color:#EB624D;text-align: center;"><img src="/html/images/dsp.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 3 && colindex ==  9)
						 {
							 return '<div style="color:#C9478A;text-align: center;"><img src="/html/images/qyz.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 4 && colindex ==  9)
						 {
							 return '<div style="color:#3E82BC;text-align: center;"><img src="/html/images/dkg.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 5 && colindex == 9)
						 {
							 return '<div style="color:#009FA8;text-align: center;"><img src="/html/images/sgz.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 6 && colindex == 9)
						 {
							 return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/djj.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 9 && colindex == 9)
						 {
							 return '<div style="color:#A94F9A;text-align: center;"><img src="/html/images/yfb.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 else if(data.house_status == 10 && colindex == 9)
						 {
							 return '<div style="color:#D0B6D7;text-align: center;"><img src="/html/images/ysx.png">&nbsp;&nbsp;'+data.houseStatus+'</div>';
						 }
						 
						 if(colindex == 0)
						 {
							 var html = "";
							 if(data.house_status == 9)
							 {
								 html = '&nbsp; <span style="color:red;cursor: pointer;" onclick="baseHouse.istop(0,'+data.id+')">精&nbsp;<i title="精品房源" class="fa fa-thumbs-o-up" ></i></span>';
								 if (data.is_top==0) 
								 {
									 html = '&nbsp; <span style="color:green;cursor: pointer;" onclick="baseHouse.istop(1,'+data.id+')"><i title="非精品房源" class="fa fa-thumbs-o-down" ></i></span>';
								 }
							 }	 
							 return "<a style='cursor: pointer;' title='点击查看房源信息' onclick='baseHouse.edit2("+data.id+");return false;'>"+value+"</a></div>"+html;
						 }	 
						 
						 if(data.publish == 0 && colindex == 10)
						 {
							 return '<div style="color:#009FAB;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.publishname+'</div>';
						 }
						 else if(data.publish == 1 && colindex == 10)
						 {
								 return '<div style="color:#A94F9A;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.publishname+'</div>';
						 }
						 else if(data.publish == 2 && colindex == 10)
						 {
								 return '<div style="color:#45B78D;text-align: center;"><img src="/html/images/xj.png">&nbsp;&nbsp;'+data.publishname+'</div>';
						 }
						 return value;
					},// 是否精品样式处理事件
					fnDrawCallback:function(){
					 	// 获取当前列表中房源对应的所有小区信息
					 	var keyword = $('#keyword').val();
					 	var status = $('#status').val();
					 	var areaid = $('#areaid').val();
					 	var publish = $('#publish').val();
					 	var createtime = $('#createtime').val();
					 	var isSelf = $('#isSelf:checkbox').prop('checked') ? '1' : '0';
					 	var kzhouse = $('#kzhouse:checkbox').prop('checked') ? '1' : '0';
					 	var trading = $('#trading').val();
					 	var groupid = $('#groupid').val();
					 	
						common.ajax({
							url : common.root + '/BaseHouse/getGroupList.do',
							data : {
								keyword : keyword,
								status : status,
								areaid : areaid,
								publish : publish,
								createtime : createtime,
								isSelf : isSelf,
								kzhouse : kzhouse,
								trading : trading,
								groupid : groupid
							},
							dataType : 'json',
							loadfun : function(isloadsucc, data) {
								if (isloadsucc) {
									if (data.state == '1') {
										if (data.data != null && data.data != undefined && data.data.length > 0) {
							      	var html = '<option value="">请选择...</option>';
							        $("#groupid").html('');
							        var groupidArray = "";
							        if (groupid != null && groupid != '') {
							        	if (groupid.indexOf(",") != -1) {
							        		groupidArray = groupid.split(",");	
							        	} else {
							        		groupidArray = groupid;
							        	}
							        }
							        for (var i = 0; i < data.data.length; i++) {
							        	html += '<option  value="' + data.data[i].id + '" >' + data.data[i].group_name + '</option>';
							        }
							        $("#groupid").html(html);
							        
										}
									} else {
										common.alert({
											msg : '房源状态发生改变,请重新确认!',
											fun : function() {
												table.refreshRedraw('basehouse_table');
											}
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
	signSigleHouse:function(title,id,rankType,houseId,fee,address,m,rank_type,roomarea,rank_code,room_count)
	{
		// 验证出租房源是否签约
		common.ajax({
			url : common.root + '/houserank/checkRankHouseStatus.do',
			data : {
				id : id
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc) 
				{
					if (data.state == '1') 
					{
						var rentroomcnt = rank_type == 0 ? m:1;
						// 查看单个房间信息
						common.openWindow({
							name:'signHouse',
							type : 3,
							data:{id:id,rankType:rankType,title:title,houseId:houseId,agreementId:'',fee:fee,address:address,roomcnt:m,rentroomcnt:rentroomcnt,area:roomarea,rank_code:rank_code,flag:3},//20180116 修改flag由1为3,为区分签约页面、详情、修改页面
							title : '签约房源信息',
							url : '/html/pages/house/houseInfo/new_rank_house_agreement.html' 
						});
					}
					else
					{
						common.alert({
							msg : '房源状态发生改变,请重新确认!',
							fun : function() {
								table.refreshRedraw('basehouse_table');
							}
						});
					}
				} else {
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},// 撤销发布
	soldOut:function(obj)
	{
		var id = rowdata.id;
		common.alert({
			msg : "是否撤销房发布？",
			confirm : true,
			closeIcon:true,
			confirmButton:'是',
			cancelButton:'否',
			fun : function(action)
			{
			 if (action)
			 {
			   common.ajax({
				url : common.root + '/BaseHouse/soldOut.do',
				data : {id : id},
				dataType : 'json',
				loadfun : function(isloadsucc, data)
					{
					if (isloadsucc)
					{
					  if (data.state == '1') 
					  {
						common.alert({msg : '操作成功!',fun : function() {table.refreshRedraw('basehouse_table');}});
					  }
					   else if (data.state == '-2') 
					  {
						common.alert({msg : '操作失败!'});
					  }
					}
					else
					{
					common.alert({msg : common.msg.error});
					}
				}
					    });
			}
			}
		});
	},
	sigleHouseInfo:function(id)
	{
		// 查看单个房间信息
		common.openWindow({
			name:'sigleHouseInfo',
			type : 1,
			data:{id:id},
			title : '查看房间信息',
			url : '/html/pages/house/houseInfo/sigleHouseInfo.html' 
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
	},// 修改租赁房源
	rankUpdate : function(houseid,id,groupid,spec) 
	{
		// 打开模态框
		$modal.modal();
		// 初始化数据开始------------------
		common.ajax(
				{
					url : common.root + '/BaseHouse/seeRentHouse.do',
					data : {id : houseid,rank_id: id},
					dataType : 'json',
					async:false,
					loadfun : function(isloadsucc, data) 
					{
						if (isloadsucc)
						{
							$('#houseId').val(houseid);
							$("#rankid").val(data[0].id);
							$("#rankArea").val(data[0].rank_area);
							$("#fee").val(data[0].fee);
							$('#myModalLabel')[0].innerHTML=data[0].title+"("+data[0].rank_code+")";
							$('#image')[0].innerHTML='<div class="dropzone" id="rankPic'+data[0].id+'">';
							var imag =data[0].path;
							if (imag!=null&&imag!="") 
							{
								var pas = imag.split("|");
								var paths = new Array();
								for ( var int = 0; int < pas.length; int++) 
								{
									if (int == 0) 
									{
										paths.push({path : pas[int],first : 1});
									}
									else 
									{
										paths.push({path : pas[int],first : 0});
									}
								}
								if (paths != null && paths != "")
								{
									common.dropzone.init({
										id : '#rankPic' + id,
										defimg : paths,
										maxFiles : 10
									                     });
								}
								else
								{
									common.dropzone.init({
										id : '#rankPic' + id,
										maxFiles : 10
									                     });
								}
							}
							 else
							{
								common.dropzone.init({
									id : '#rankPic' + id,
									maxFiles : 10
								});
							}
                             // 初始化数据结束------------------
							
						}
					}
				});
		var specroom=spec.split("|");
		common.ajax(
		{
			url : common.root + '/grouproom/getRoom.do',
			data : {group_id : groupid,bedroom: specroom[0],spec:specroom[1]},
			dataType : 'json',
			async:false,
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
			    var imag =data.list[0].urlpath;
				if (imag!=null&&imag!="") 
				{
					var pas = imag.split("|");
					var paths = new Array();
				    var	html="";
					for ( var int = 0; int < pas.length; int++) 
					{
						if (int == 0) 
						{
							paths.push({path : pas[int],first : 1});
						}
						else 
						{
							paths.push({path : pas[int],first : 0});
						}
					}
					if (paths != null && paths != "")
					{
						common.dropzone.init({id : '#roomadd' ,defimg : paths,maxFiles : 10 ,clickEventOk:true});
					}
					else
					{
						common.dropzone.init({id : '#roomadd',maxFiles : 10,clickEventOk:true });
					}
				}
				
				}
			}
		});
		$("#roomadd .dz-details").unbind();
		$("#roomadd .dz-details").click(function()
		{
			var ranks='#rankPic' + id;
			var nownum = parseInt($(ranks+' .dz-image-preview').size());
			if(nownum+1 > 10){
				common.alert({msg:'超过可上传的图片数量'});
				return;
			}
			
			var html = '<div class="dz_def_img_div dz-preview dz-processing dz-image-preview dz-success dz-complete"> '
				       + $(this).parent().find(".dz-image")[0].outerHTML;
	          html += ' <div class="dz-details">    <div class="dz-size"><span data-dz-size=""><strong></strong> KB</span></div>  '
	               + ' <div class="dz-filename"><span data-dz-name=""></span></div></div> '
		           + ' <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress="" style="width: 100%;"></span></div>  <div class="dz-error-message"><span data-dz-errormessage=""></span></div>  <div class="dz-success-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Check</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <path d="M23.5,31.8431458 L17.5852419,25.9283877 C16.0248253,24.3679711 13.4910294,24.366835 11.9289322,25.9289322 C10.3700136,27.4878508 10.3665912,30.0234455 11.9283877,31.5852419 L20.4147581,40.0716123 C20.5133999,40.1702541 20.6159315,40.2626649 20.7218615,40.3488435 C22.2835669,41.8725651 24.794234,41.8626202 26.3461564,40.3106978 L43.3106978,23.3461564 C44.8771021,21.7797521 44.8758057,19.2483887 43.3137085,17.6862915 C41.7547899,16.1273729 39.2176035,16.1255422 37.6538436,17.6893022 L23.5,31.8431458 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" stroke-opacity="0.198794158" stroke="#747474" fill-opacity="0.816519475" fill="#FFFFFF" sketch:type="MSShapeGroup"></path>      </g>    </svg>  </div>  <div class="dz-error-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Error</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <g id="Check-+-Oval-2" sketch:type="MSLayerGroup" stroke="#747474" stroke-opacity="0.198794158" fill="#FFFFFF" fill-opacity="0.816519475">          <path d="M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" sketch:type="MSShapeGroup"></path>        </g>      </g>    </svg>  </div><div class="dz_bnt1"><a class="dz-fist" href="javascript:undefined;" data-dz-fist="">设置首图</a><a class="dz-remove" href="javascript:undefined;" data-dz-remove="">删除</a></div></div>';
	        $('#rankPic' + id).append(html);
			
			$(ranks).addClass('dz-started'); 
			// 添加处理事件信息
			$(ranks +" .dz_def_img_div .dz-remove").click(function(){
				$(this).parent().parent().remove();
				var myDropzone = $(ranks).data('obj');
				var size = $(ranks+" .dz_def_img_div").size()+myDropzone.files.length;
					$(ranks+" .dropzone_title_ysc").text(size);
				if(size == 0){
					$(ranks).removeClass('dz-started');
				}
			});
			$(ranks +" .dz_def_img_div .dz-fist").click(function(){
				var myDropzone = $(ranks).data('obj');
				for(var i = 0;i<myDropzone.files.length;i++){
					myDropzone.files[i].first = 0;
					$(myDropzone.files[i].previewElement).find('.dz_fisrt_t').remove();
				}
				$(ranks+" .dz_def_img_div .dz_fisrt_t").remove();
				$(this).parent().parent().find('.dz-image').append('<div class="dz_fisrt_t">首图</div>');							
			});
		});
	},
	// 发布审批
	approvalPublish:function(obj,monery){
		var id = rowdata.id;
		common.ajax({
			url : common.root + '/BaseHouse/checkHouseMenory.do',
			data : {
				houseId : id 
			},
			dataType : 'json',
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc)
				{
					if(data.flag < 2)
					{ 
						var isPass = 0;
						common.alert({
							msg :"发布房源价格小于基准价格:"+data.sumcnt+"元,请撤销后重新编辑价格!",
							confirm : true,
							closeIcon:true,
							confirmButton:'是',
							cancelButton:'否',
							fun : function(action) {
								if(action)
								{
									common.ajax({
										url : common.root + '/BaseHouse/approvalPublish.do',
										data : {
											id : id,
											isPass:isPass
										},
										dataType : 'json',
										loadfun : function(isloadsucc, data) {
											if (isloadsucc) {
												if (data.state == '1') {
													// msg = isPass == 1?'审批通过操作成功!':'操作成功！';
													common.alert({
														msg : '操作成功！',
														fun : function() {
															table.refreshRedraw('basehouse_table');
														}
													});
												} else if(data.state == -2){
													common.alert({
														msg : '房源状态已经改变，请稍候操作!',
														fun : function() {
															table.refreshRedraw('basehouse_table');
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
						return;
					}
					var isPass = 0;
					common.alert({
						msg :"房源基准参考价格:" + data.sumcnt+'元<br/>此房源发布是否审核通过？',
						confirm : true,
						closeIcon:true,
						confirmButton:'通过',
						cancelButton:'拒绝',
						fun : function(action) {
							if (action) 
							{
								isPass = 2;
							}
							common.ajax({
								url : common.root + '/BaseHouse/approvalPublish.do',
								data : {
									id : id,
									isPass:isPass
								},
								dataType : 'json',
								loadfun : function(isloadsucc, data) {
									if (isloadsucc) {
										if (data.state == '1') {
											// msg = isPass == 1?'审批通过操作成功!':'操作成功！';
											common.alert({
												msg : '操作成功！',
												fun : function() {
													table.refreshRedraw('basehouse_table');
												}
											});
										} else if(data.state == -2){
											common.alert({
												msg : '房源状态已经改变，请稍候操作!',
												fun : function() {
													table.refreshRedraw('basehouse_table');
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
				}
			}
		});
		
		
	},
	/*
	 * 发布房源
	 */
	houseRelease:function(obj){
		var id = rowdata.id;
		 common.alert({
				msg : '是否发布？',
				confirm : true,
				closeIcon:true,
				confirmButton:'是',
				cancelButton:'否',
				fun : function(action)
				{
				 if (action)
				 {
					common.ajax({
						url : common.root + '/BaseHouse/checkHouseMenory.do',
						data : {
							houseId : id 
						},
						async: false,
						dataType : 'json',
						loadfun : function(isloadsucc, data) 
						{
							if (isloadsucc)
							{
								if(data.flag < 2)
								{ 
									common.alert({"msg":"发布的房源价格低于基准价格:"+data.sumcnt+"元,请重新编辑房源价格!"})
									return ;
								}
								else
								{
									 common.ajax({
							             url: '/BaseHouse/houseRelease.do',
							             data: {
							                 id: id
							             },
							             dataType: 'json',
							             loadfun: function(isloadsucc, data)
							             {
							                 if (isloadsucc) 
							                 {
							                     if (data.state == '1')
							                     {
							                         common.alert({ msg: '操作成功',fun: function(){table.refreshRedraw('basehouse_table');}});
							                     }
							                     else if (data.state == '-2')
							                     {
							                    	 common.alert({ 
							                    		 msg: '当前房源有未编辑房源',
							                    		 fun:function(){
							                    			 $(obj).find('.fa-plus-square').click();
							                    		 }
							                    	 });
							                     }
							                    
							                     else{
							                    	 common.alert({msg: common.msg.error});
							                     }
							                 }
							                 else
							                 {
							                     common.alert({ msg: common.msg.error});
							                 }
							             }
							         }); 
								}	
							}
							else
							{
								return;
							}
						}});
					 
					
				 }
				}
			});
},
	add : function() { 
		common.openWindow({
			area:['900px','630px'],
			name:'addHouse',
			type : 3,
			data:{id:'',flag:1},
			title : '新增房源',
			url : '/html/pages/house/houseInfo/baseHouse_add.html' 
		});
	},
	// 修改
	edit:function(flag){
		var id = rowdata.id;
		var title = '修改房源';
		if(flag == 0)
		{
			title = '房源详细';
		}
		common.openWindow({
			area:['900px','630px'],
			name:'addHouse',
			type : 3,
			data:{id:id,flag:flag},
			title : title,
			url : '/html/pages/house/houseInfo/baseHouse_add.html' 
		});
	},
	edit2:function(id){ 
		var flag = 0;
		var title = '修改房源';
		if(flag == 0)
		{
			title = '房源详细';
		}
		common.openWindow({
			area:['900px','630px'],
			name:'addHouse',
			type : 3,
			data:{id:id,flag:flag},
			title : title,
			url : '/html/pages/house/houseInfo/baseHouse_add.html' 
		});
	},
	// 审批房源
	approvalHouse:function(){
		var id = rowdata.id;
		var isPass = 0;
		common.alert({
			msg : '此房源是否审核通过？',
			confirm : true,
			closeIcon:true,
			confirmButton:'通过',
			cancelButton:'拒绝',
			fun : function(action) {
				if (action) 
				{
					isPass = 2;
				}
				common.ajax({
					url : common.root + '/BaseHouse/approvalHouse.do',
					data : {
						id : id,
						isPass:isPass
					},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								// msg = isPass == 1?'审批通过操作成功!':'操作成功！';
								common.alert({
									msg : '操作成功！',
									fun : function() {
										table.refreshRedraw('basehouse_table');
									}
								});
							} else if(data.state == -2){
								common.alert({
									msg : '房源状态已经改变，请稍候操作!',
									fun : function() {
										table.refreshRedraw('basehouse_table');
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
	// 设置精品房源||取消精品
	istop:function(state,id){
		var msg="";
			if (state==1) {
				msg="是否设置为精品房源？";
			}else{
				msg="是否取消精品房源？";
			}
		common.alert({
			msg : msg,
			confirm : true,
			closeIcon:true,
			confirmButton:'是',
			cancelButton:'否',
			fun : function(action)
			{
			 if (action)
			 {
			   common.ajax({
				url : common.root + '/BaseHouse/rankIstop.do',
				data : {id : id,istop:state},
				dataType : 'json',
				loadfun : function(isloadsucc, data)
					{
					if (isloadsucc)
					{
					  if (data.state == '1') 
					  {
						common.alert({msg : '操作成功!',fun : function() {table.refreshRedraw('basehouse_table');}});
					  }
					   else if (data.state == '-2') 
					  {
						common.alert({msg : '操作失败!'});
					  }
					}
					else
					{
					common.alert({msg : common.msg.error});
					}
				}
					    });
			}
			}
		});
	},
	// 提交房源
	submitHouse:function(){
		var id = rowdata.id;
		common.alert({
			msg : '确定提交房源吗？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/BaseHouse/submitHouse.do',
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
											table.refreshRedraw('basehouse_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '房源已被提交,不能更改房源状态!',
										fun : function() {
											table.refreshRedraw('basehouse_table');
										}
									});
								} else if(data.state == -3){
									common.alert({
										msg : '房屋已签合约,不能更改房源状态!',
										fun : function() {
											table.refreshRedraw('basehouse_table');
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
	// 交接房源
	houseTransfer:function(obj){
		var id = rowdata.id;
		var isPass = 5;
		common.alert({
			msg : '此房源是否通过交接？',
			confirm : true,
			closeIcon:true,
			confirmButton:'通过',
			cancelButton:'拒绝',
			fun : function(action) {
				if (action) {
					isPass = 9;
				}
				common.ajax({
					url : common.root + '/BaseHouse/houseTransfer.do',
					data : {id : id,isPass:isPass},
					dataType : 'json',
					loadfun : function(isloadsucc, data) {
						if (isloadsucc) {
							if (data.state == '1') {
								common.alert({
									msg : '操作成功!',
									fun : function() {
										table.refreshRedraw('basehouse_table');
									}
								});
							} else if(data.state == -2){
								common.alert({
									msg : '房源状态审批,请重新查看!',
									fun : function() {
										table.refreshRedraw('basehouse_table');
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
	del: function(){
		common.alert({
			msg : '确定需要删除吗？',
			confirm : true,
			fun : function(action) {
				if (action) {
					common.ajax({
						url : common.root + '/BaseHouse/deleteHouse.do',
						data : {
							id : rowdata.id
						},
						
						dataType : 'json',
						loadfun : function(isloadsucc, data) {
							if (isloadsucc) {
								if (data.state == '1') {
									common.alert({
										msg : '删除成功',
										fun : function() {
											table.refreshRedraw('basehouse_table');
										}
									});
								} else if(data.state == -2){
									common.alert({
										msg : '房源状态发生改变,请重新确认!',
										fun : function() {
											table.refreshRedraw('basehouse_table');
										}
									});
								} else if(data.state == -3){
									common.alert({
										msg : '房源已经被签约,不能删除!',
										fun : function() {
											table.refreshRedraw('basehouse_table');
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
	expAllHouse:function()
	{
		layer.close(index2);
		var newColumns= new Array();
		newColumns.push({name:"house_code",title:"房源编码"});
		newColumns.push({name:"house_name",title:"房源名称"});
		newColumns.push({name:"areaName",title:"所属区域"});
		newColumns.push({name:"sqname",title:"商圈"});
		newColumns.push({name:"group_name",title:"小区名称"});
		newColumns.push({name:"specName",title:"户型"});
		newColumns.push({name:"username",title:"业主"});
		newColumns.push({name:"houseStatus",title:"房源状态"});
		newColumns.push({name:"publishname",title:"发布状态"});
		newColumns.push({name:"name",title:"操作人"});
		newColumns.push({name:"decorateType",title:"装修"});
		newColumns.push({name:"area",title:"面积"});
		newColumns.push({name:"nm",title:"入住情况"});
		var href = common.root + '/BaseHouse/getHouseList.do?isDc=true&expname='+encodeURIComponent(encodeURI('收房列表'))+"&colum="+encodeURIComponent(encodeURI(JSON.stringify(newColumns)))+'&istz_=1';
		var paramlist = getParamArray();
		for(var i=0;i<paramlist.length;i++)
		{
			href += "&"+paramlist[i].name+"="+encodeURI(encodeURI(paramlist[i].value));
		}
	// console.log(href);
		common.load.load('正在执行导出，请稍等');
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
	exportHouse:function()
	{
		var newColumns= new Array();
		newColumns.push({name:"house_code",title:"房源编码"});
		newColumns.push({name:"house_name",title:"房源名称"});
		newColumns.push({name:"houseStatus",title:"房源状态"});
		newColumns.push({name:"publishname",title:"发布状态"});
		newColumns.push({name:"areaname",title:"所属区域"});
		newColumns.push({name:"sqname",title:"商圈"});
		newColumns.push({name:"group_name",title:"小区名称"});
		newColumns.push({name:"specname",title:"原始户型"});
		newColumns.push({name:"newSpecName",title:"开工后户型"});
		newColumns.push({name:"user_name",title:"业主"});  
		newColumns.push({name:"title",title:"出租房间名称"});  
		newColumns.push({name:"fee",title:"出租费用"});  
		newColumns.push({name:"rank_area",title:"出租面积"});  
		newColumns.push({name:"rankType",title:"出租类型"});  
		var href = common.root + '/BaseHouse/exportHouse.do?isDc=true&expname='+encodeURIComponent(encodeURI('收房列表'))+"&colum="+encodeURIComponent(encodeURI(JSON.stringify(newColumns)))+'&istz_=1';
		var paramlist = getParamArray();
		for(var i=0;i<paramlist.length;i++)
		{
			href += "&"+paramlist[i].name+"="+encodeURI(encodeURI(paramlist[i].value));
		}
		window.location.href = href;
	},
	expKZHouse:function()
	{
		layer.close(index2);
		var newColumns= new Array();
		newColumns.push({name:"house_code",title:"房源编码"});
		newColumns.push({name:"house_name",title:"房源名称"});
		newColumns.push({name:"houseStatus",title:"房源状态"});
		newColumns.push({name:"publishname",title:"发布状态"});
		newColumns.push({name:"areaname",title:"所属区域"});
		newColumns.push({name:"sqname",title:"商圈"});
		newColumns.push({name:"group_name",title:"小区名称"});
		newColumns.push({name:"specname",title:"户型"});
		newColumns.push({name:"user_name",title:"业主"});  
		newColumns.push({name:"title",title:"出租房间名称"});  
		newColumns.push({name:"fee",title:"出租费用"});  
		newColumns.push({name:"rank_area",title:"出租面积"});  
		newColumns.push({name:"rankType",title:"出租类型"});  
		var href = common.root + '/BaseHouse/getKZHouseList.do?isDc=true&expname='+encodeURIComponent(encodeURI('收房列表'))+"&colum="+encodeURIComponent(encodeURI(JSON.stringify(newColumns)))+'&istz_=1&kzhouse=1';
		var paramlist = getParamArray();
		for(var i=0;i<paramlist.length;i++)
		{
			href += "&"+paramlist[i].name+"="+encodeURI(encodeURI(paramlist[i].value));
		}

		common.load.load('正在执行导出，请稍等');
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
	// 录入水电煤
	submitSDM:function()
	{
		common.openWindow({
			area:['1200px','600px'],
			name:'houseSign',
			type : 1,
			data:{id:rowdata.id},
			title : '缴纳水电煤费',
			url : '/html/pages/house/houseInfo/sdmFee.html'
		});
	},
	// 更新房源价格页面
	updateRankHousePrice:function(id,fee,title,rank_code)
	{
		// 打开模态框
		$updatePricemodal.modal();
		$('#updateModalLabel').html(title);
		$('#rankPriceSpan').html(fee);
		$('#hideRankId').val(id); 
		$('#hideHouseId').val(rank_code);
	},
	// 更新房源价格
	updateRankPrice:function()
	{
		var regi=/^[1-9]\d*$/;
		var updateFee = $('#updateFee').val();
		var hideHouseId = $('#hideHouseId').val();
		var hideRankId = $('#hideRankId').val();
		if(!regi.test(updateFee))
		{
			common.alert('请填写整数发布价格');
			 return false;
		}
		common.ajax(
		{
			url : common.root + '/BaseHouse/updateRankPrice.do',
			data : {id : hideRankId,fee:updateFee,rankCode:hideHouseId},
			dataType : 'json', 
			loadfun : function(isloadsucc, data) 
			{
				if(data.state == 1)
				{
					common.alert({
						msg : '价格修改成功!',
						fun : function() {
							$("#fee"+hideRankId).html(updateFee);
							$updatePricemodal.modal('hide');
						}
					});
				}
				else
				{
					common.alert('网络忙，请重试！');
				}	
			}
		})
	},
	// 房源签约
	houseSign:function(obj){
		var username = rowdata.username; // 房主姓名
		var user_id = rowdata.user_id; // 房主id
		var mobile = rowdata.mobile; // 房主mobile
		var house_name = rowdata.house_name; // 房源名称
		var id = rowdata.id;
		var floor = rowdata.floor; // 楼层
		var roomCnt = rowdata.spec.split("|")[0]; // roomCnt
		var is_cubicle = rowdata.is_cubicle; // roomCnt
		var houseCode = rowdata.house_code; // roomCnt
		var free_rent = rowdata.free_rent; // roomCnt
		var areaid = rowdata.areaid; // areaid
		var cost_a = rowdata.cost_a; // cost_a
		var lease_period = rowdata.lease_period; // lease_period
		var title = '签约房源';
		// 验证房源现在状态是否签约中，如果是签约中，不能签约房源
		common.ajax(
		{
			url : common.root + '/BaseHouse/checkHouseStatus.do',
			data : {id : id},
			dataType : 'json', 
			loadfun : function(isloadsucc, data) 
			{
				if (isloadsucc) 
				{
					if (data.state == '1') 
					{
						common.openWindow({
							area:['900px','630px'],
							name:'houseSign',
							type : 3,
							data:{id:id,username:username,user_id:user_id,mobile:mobile,house_name:house_name,floor:floor,roomCnt:roomCnt,is_cubicle:is_cubicle,houseCode:houseCode,free_rent:free_rent,areaid:areaid,agreementId:'',cost_a:cost_a,lease_period:lease_period,email:rowdata.email,address:rowdata.desc_text},
							title : title,
							url : '/html/pages/house/houseInfo/new_house_agreement.html'
						});
					}
					else
					{
						common.alert(
						{
							msg : '房源或许已经被签约,请重新操作!'
						});
						table.refreshRedraw('basehouse_table');
					}
				}
			}
		});
	},
	// 租房修改保存
	rankseve: function() {
	var id=$("#rankid").val();
	var fee=$("#fee").val();
	var rankArea=$("#rankArea").val();
	var houseId = $('#houseId').val();
	var pictpath = common.dropzone.getFiles('#rankPic'+id);
	var pict = "";
	var spic = "";
	var returnT = false;
	for ( var n = 0; n < pictpath.length; n++) {
		if (pictpath[n].fisrt == 1) {
			pict = pictpath[n].path + '|' + pict;
		} else {
			pict += pictpath[n].path + "|";
		}
		returnT = true;
	}
	if (returnT) {
		spic += pict.substring(0, pict.length - 1);
	} else {
		
	}
	
var regm=/^[1-9]{1}\d*(\.\d{1,2})?$/;
var regi=/^[1-9]\d*$/;
if (regm.test(fee)&&regi.test(rankArea)&&spic!=null&&spic!="") 
{
	$("#rankseve").attr("disabled", true);
	common.ajax({
		url : common.root + '/houserank/Rankseve.do',
		data : {
			id:id,
			fee:fee,
			rankArea:rankArea,
			images:spic,
			houseId:houseId
		},
		dataType : 'json',
		loadfun : function(isloadsucc, data) 
		{
			if (isloadsucc) 
			{// 操作成功
				if (data.state == 1)
				{
					$("#rankseve").attr("disabled", false);
					$modal.modal('hide');
					common.alert({msg : '操作成功'});
					$("#area"+id)[0].innerHTML=rankArea;
					$("#fee"+id)[0].innerHTML=fee;
					$("#status"+id)[0].innerHTML='<font color="red">已编辑</font>';
				} 
				else 
				{
					$("#rankseve").attr("disabled", false);
					common.alert({msg : common.msg.error});
				}
			}
			else 
			{
				$("#rankseve").attr("disabled", false);
				common.alert({msg : common.msg.error});
			}
		}
	});
	
	
}
else
{
	common.alert({msg :"请检查录入信息"});	
}
	
}
	
};
baseHouse.init();
// 进入
function movediv(x)
{
	$('.tab').removeClass('movediv');
	$("#"+x.id).addClass('movediv');
}

// 退出
function normaldiv(x)
{
	$('.tab').removeClass('movediv');
}

function getParamArray()
{
	var paramlist = new Array();
	paramlist.push({
		"name" : "keyword",
		"value" : $('#keyword').val()
	});
	paramlist.push({
		"name" : "status",
		"value" : $('#status').val()
	});
	paramlist.push({
		"name" : "areaid",
		"value" : $('#areaid').val()
	});
	paramlist.push({
		"name" : "publish",
		"value" : $('#publish').val()
	});
	paramlist.push({
		"name" : "createtime",
		"value" : $('#createtime').val()
	}); 
	paramlist.push({
		"name" : "isSelf",
		"value" : $('#isSelf:checkbox').prop('checked') ? '1' : '0'
	}); 
	paramlist.push({
		"name" : "kzhouse",
		"value" : $('#kzhouse:checkbox').prop('checked') ? '1' : '0'
	}); 
	paramlist.push({
		"name" : "trading",
		"value" : $('#trading').val()
	});
	paramlist.push({
		"name" : "groupid",
		"value" : $('#groupid').val()
	}); 		
	return paramlist;
}

