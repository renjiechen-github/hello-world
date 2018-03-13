var project_add = {
    //页面初始化操作
    init: function(){
        //加载结算类型
        project_add.initType();
        //添加处理事件 
        project_add.addEvent();
		
		project_add.initData();
    },
    //添加处理事件
    addEvent: function(){
        $('#project_add_bnt').click(function(){
            project_add.save();
        });
    },
    /**
     加载结算类型数据
     **/
    initType: function(){
        common.loadItem('SETTLEMENTS.TYPE', function(json){
            var html = "";
            for (var i = 0; i < json.length; i++) {
				if(rowdata != null){
					if(rowdata.type == json[i].item_id){
						html += '<option selected="selected" value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
					}else{
						html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
					}
				}else{
					html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
				}
            }
            $('#type').append(html);
        });
    },
	//加载修改数据信息
	initData:function(){
		console.log(rowdata);
		if(rowdata != null){
			$('#name').val(rowdata.name);
			$('#capital_receivable').val(rowdata.capital_receivable);
			$('#capital_payable').val(rowdata.capital_payable);
			$('#remark').val(rowdata.remarks);
		}
		var correlation_id = '';
		if(rowdata != null){
			correlation_id = rowdata.correlation_id
		}
		common.ajax({
            url: common.root + '/financial/project/getProjectMap.do',
            data:{
	             id:correlation_id
            },
			dataType:'json',
			loadfun: function(isloadsucc, data){
				if(isloadsucc){
					common.log(data); 
					if(data.state == 1){
						var payHtml = "";
						for(var i=0;i<data.pay.length;i++){
							payHtml += '<tr>'+
			                    		'<td>'+data.pay[i].name+'</td>'+
			                    		'<td>'+data.pay[i].plat_time+'</td>'+
			                    		'<td>'+(data.pay[i].pay_time == null?'':data.pay[i].pay_time)+'</td>'+
			                    		'<td>'+data.pay[i].cost+'</td>'+
			                    		'<td>'+data.pay[i].status_name+'</td>'+
			                    	'</tr>';
						}
						$('#pay_pro_tab').append(payHtml);
						var resHtml = "";
						for(var i=0;i<data.res.length;i++){
							resHtml += '<tr>'+
			                    		'<td>'+data.res[i].name+'</td>'+
			                    		'<td>'+data.res[i].plat_time+'</td>'+
			                    		'<td>'+(data.res[i].pay_time == null?'':data.res[i].pay_time)+'</td>'+
			                    		'<td>'+data.res[i].cost+'</td>'+
			                    		'<td>'+data.res[i].status_name+'</td>'+
			                    	'</tr>';
						}
						$('#res_pro_tab').append(resHtml);
						var qdHtml = "";
						for(var i=0;i<data.qd.length;i++){
							qdHtml += '<tr>'+
			                    		'<td>'+data.qd[i].name+'</td>'+
			                    		'<td>'+data.qd[i].years+'</td>'+
			                    		'<td>￥'+data.qd[i].m_1+'</td>'+
			                    		'<td>￥'+data.qd[i].m_2+'</td>'+
			                    		'<td>￥'+data.qd[i].m_3+'</td>'+
			                    		'<td>￥'+data.qd[i].m_4+'</td>'+
			                    		'<td>￥'+data.qd[i].m_5+'</td>'+
			                    		'<td>￥'+data.qd[i].m_6+'</td>'+
			                    		'<td>￥'+data.qd[i].m_7+'</td>'+
			                    		'<td>￥'+data.qd[i].m_8+'</td>'+
			                    		'<td>￥'+data.qd[i].m_9+'</td>'+
			                    		'<td>￥'+data.qd[i].m_10+'</td>'+
			                    		'<td>￥'+data.qd[i].m_11+'</td>'+
			                    		'<td>￥'+data.qd[i].m_12+'</td>'+
			                    	'</tr>';
						}
						$('#qd_pro_tab').append(qdHtml);
						var qdresHtml = "";
						for(var i=0;i<data.qdres.length;i++){
							qdresHtml += '<tr>'+
			                    		'<td>'+data.qdres[i].name+'</td>'+
			                    		'<td>'+data.qdres[i].years+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_1+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_2+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_3+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_4+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_5+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_6+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_7+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_8+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_9+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_10+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_11+'</td>'+
			                    		'<td>￥'+data.qdres[i].m_12+'</td>'+
			                    	'</tr>';
						}
						$('#qdres_pro_tab').append(qdresHtml);
					}else{
						common.alert({
	                        msg: common.msg.error
	                    });
					}
				}else{
					common.alert({
                        msg: common.msg.error
                    });
				}
			}
		});
	},
    /**
     * 保存操作
     */
    save: function(){
        if (!Validator.Validate('form2')) {
            return;
        }
		var correlation_id = '';
		if(rowdata != null){
			correlation_id = rowdata.correlation_id
		}
        common.ajax({
            url: common.root + '/financial/project/save.do',
            data:{
	             type:$('#type').val(),
	             name:$('#name').val(),
	             capital_receivable:$('#capital_receivable').val(),
	             capital_payable:$('#capital_payable').val(),
	             remark:$('#remark').val(),
				 correlation_id:correlation_id,
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
								table.refreshRedraw('project_table');
							}
						});
                    }else if(data.state == -2){
						layer.tips('请选择结算类型','#type');
					}else if(data.state == -3){
						layer.tips('请填写财务项目名称','#name');
					}else if(data.state == -4){
						layer.tips('请输入正确的实收款项，如：1.30','#capital_receivable');
					}else if(data.state == -5){
						layer.tips('请输入正确的实付款项，如：1.30','#capital_payable');
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
project_add.init();