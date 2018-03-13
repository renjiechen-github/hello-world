var addCoupon={
	init:function(){
		common.autoLoadItem('COUPON.CODE_TYPE', 'code_type',true);
		common.autoLoadItem('COUPON.CARD_TYPE', 'card_type',true);
		
		$("#color").select2({
		  templateResult: function(state){
			  if (!state.id) { 
				  return state.text; 
			  }
			  var $state = $(
			    '<span><b style="background-color:'+state.text.split('--')[1]+';display: inline-block;width:30px;height:30px;" ></b> ' + state.text.split('--')[0] + '</span>'
			  );
			  return $state;
		  },
		  templateSelection:function(state){
			  return state.id;
		  }
		});
		//在首页添加保存按钮
		$('.state-info').prepend('<button class="btn btn-info" id="type_add_bnt" type="button" onclick="addCoupon.save();" >确定提交</button>&nbsp;&nbsp;');
	},
	/**
	 * 使用日期
	 */
	selectTime:function(id){
		if(id == 1){//领取后
			$('#begin_timestamp').replaceWith('<input type="text" style="width: 275px" name="begin_timestamp" readonly="readonly" id="begin_timestamp" class="form-control" placeholder="2013-01-02 01:00 至 2013-01-02 01:10"  class="span4"/>');
			$('#fixed_begin_term').val(0).removeAttr('readonly');
			$('#fixed_term').val(1).removeAttr('readonly');
		}else{
			$('#fixed_begin_term').val(0).attr('readonly','readonly');
			$('#fixed_term').val(1).attr('readonly','readonly');
			var nowTemp = new Date();
			$('#begin_timestamp').daterangepicker({
				startDate:nowTemp.getFullYear()+'-'+(nowTemp.getMonth()+1)+'-02',
				timePicker12Hour:false,
		        timePicker: false,
				separator:'至',
				//分钟间隔时间
		        timePickerIncrement: 10,
		        format: 'YYYY-MM-DD',
		        dateLimit : {  
		            days : 90  
		        }, //起止时间的最大间隔 
		      }, function(start, end, label) {
		        console.log(start.toISOString(), end.toISOString(), label);
		      });
		}
	},
	selectDegree:function(id){
		if(id == 1){
			$('#rule_degree_month').val(1).removeAttr('readonly');
		}else{
			$('#rule_degree_month').val('').attr('readonly','readonly');
		}
	},
	/**
	 * 选择卡券类型
	 */
	selectCouponType:function(obj){
		var type  = $(obj).val();
		if(type == 1){//团购券
			$('#rowZdxf').hide();
			$('#rowSyfw').hide();
			$('.zhekoud').hide();//隐藏折扣比例
			$('.daijind').show();
			$('.detaild').hide();
			$("input[id='rule_degree'][value='0']").removeAttr('disabled');
			$('.meiyuezjd').hide();
		}else if(type == 2){//代金券
			$('#rowZdxf').show();
			$('#rowSyfw').show();
			$('.zhekoud').hide();//隐藏折扣比例
			$('.daijind').show();
			$('.detaild').hide();
			$("input[id='rule_degree'][value='0']").removeAttr('disabled');
			$('.meiyuezjd').hide();
		}else if(type == 3){//折扣券
			$('#rowZdxf').hide();
			$('#rowSyfw').show();
			$('.zhekoud').show();//隐藏折扣比例
			$('.daijind').hide();
			$('.detaild').hide();
			$("input[id='rule_degree'][value='0']").removeAttr('disabled');
			$('.meiyuezjd').hide();
		}else if(type == 4){//兑换券
			$('#rowZdxf').show();
			$('#rowSyfw').hide();
			$('.zhekoud').hide();//隐藏折扣比例
			$('.daijind').hide();
			$('.detaild').show();
			$("input[id='rule_degree'][value='0']").removeAttr('disabled');
			$('.meiyuezjd').hide();
		}else if(type == 5){//优惠券
			$('#rowZdxf').hide();
			$('#rowSyfw').hide();
			$('.zhekoud').hide();//隐藏折扣比例
			$('.daijind').hide();
			$('.detaild').show();
			$("input[id='rule_degree'][value='0']").removeAttr('disabled');
			$('.meiyuezjd').hide();
		}else if(type ==6){//月租券
			$('#rowZdxf').hide();
			$('#rowSyfw').hide();
			$('.zhekoud').hide();//隐藏折扣比例
			$('.daijind').hide();
			$('.detaild').hide();
			$("input[id='rule_degree'][value='0']").attr('disabled','disabled');
			$("input[id='rule_degree'][value='1']").click();
			$("#rule_degree_month").val(1);
			$('.meiyuezjd').show();
		}else{
			$('#rowZdxf').hide();
			$('#rowSyfw').hide();
			$('.zhekoud').hide();//隐藏折扣比例
			$('.daijind').hide();
			$('.detaild').hide();
			$("input[id='rule_degree'][value='0']").removeAttr('disabled');
			$('.meiyuezjd').hide();
		}
	},
	/**
	 * 上传图片信息
	 */
	upladFile:function(imgFile){
		var fileObj = document.getElementById("logo_url").files[0];// 获取文件对象
		var filextension=imgFile.value.substring(imgFile.value.lastIndexOf("."),imgFile.value.length);
		filextension=filextension.toLowerCase();
		if(filextension == ''){
			return;
		}   
		if ((filextension!='.jpg')&&(filextension!='.gif')&&(filextension!='.jpeg')&&(filextension!='.png')&&(filextension!='.bmp'))
	    {
		    common.alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 !");
		    $(imgFile).replaceWith($(imgFile).clone());
		    $('#imgyldiv').empty();
		    imgFile.focus();
		    return;
	    }
        var FileController = "/coupon/saveLogoImg.do";// 接收上传文件的后台地址 
        // FormData 对象
        var form = new FormData();
        form.append("author", "hooyes");                        // 可以增加表单数据
        form.append("file", fileObj);                           // 文件对象
        // XMLHttpRequest 对象
        var xhr = new XMLHttpRequest();
        xhr.open("post", FileController, true);
        xhr.onload = function (res) {
            if (xhr.readyState == 4) {
            	var data = $.parseJSON(xhr.responseText);
            	if(data.state == 1){
            		$('#logo_url_').val(data.path);
            		$('#imgyldiv').empty();
            		$('#imgyldiv').append("<img id='img_' style='width:100%;' src='"+data.path+"'/>");
            	}
            }else{
            	common.alert('上传文件失败，请重新操作！');
            }
        };
        xhr.send(form);
	},
	/**
	 * 保存操作
	 */
	save:function(){
		if (!Validator.Validate('form2')) {
            return;
        }
		//校验使用条件
		if(!this.checkSytj()){
			return;
		}
		if(!this.checkTime()){
			return;
		}
		var type = $('#card_type').val();
		if(type == 2){//代金券
			var reduce_cost = $('#reduce_cost').val();
			if(reduce_cost == ''){
				layer.tips('减免金额请输入如：12或者12.01', $('#reduce_cost'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
			if(!Validator.isMoney(reduce_cost)){
				layer.tips('减免金额请输入如：12或者12.01', $('#reduce_cost'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
		}else if(type == 3){//折扣券，
			var rule_discount = $('#rule_discount').val();
			if(rule_discount == ''){
				layer.tips('请输入折扣额度，必须是1-99之间的数', $('#rule_discount'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
			if(!(/^\d+$/.test(rule_discount))){
				layer.tips('请输入折扣额度，必须是1-99之间的数', $('#rule_discount'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
			if(!Validator.isInteger2(rule_discount)){
				layer.tips('请输入折扣额度，必须是1-99之间的数', $('#rule_discount'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
			if(parseInt(rule_discount)<1 || parseInt(rule_discount) > 99){
				layer.tips('请输入折扣额度，必须是1-99之间的数', $('#rule_discount'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
		}else if(type == 4 || type == 5){
			var detaild = $('#deal_detail').val();
			if(detaild.trim() == ''){
				layer.tips('请填写描述详情', $('#deal_detail'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
		}else if(type ==6){//月租券
			var rule_month_cost = $('#rule_month_cost').val();
			if(rule_month_cost == ''){
				layer.tips('每月房租金额请输入如：12.01', $('#rule_month_cost'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
			if(!Validator.isMoney(rule_month_cost)){
				layer.tips('每月房租金额请输入如：12.01', $('#rule_month_cost'), {
	                tips: [1, '#d9534f'] //还可配置颜色
	            });
				return;
			}
		}
		//校验库存数量
		if(parseInt($('#sku_quantity').val())<0 ||parseInt($('#sku_quantity').val())>100000000 ){
			layer.tips('请填写正确的库存数量，上限100000000', $('#sku_quantity'), {
                tips: [1, '#d9534f'] //还可配置颜色
            });
			return;
		}
		if(!this.checkHy()){
			return;
		}
		
		var description = $('#description').val();
		if(description.length > 1024){
			layer.tips('字数上限为1024个汉字', $('#description'), {
                tips: [1, '#d9534f'] //还可配置颜色
            });
			return;
		}
		
		common.load.load('提交中');
		common.ajax({
 			 url:common.root+'/coupon/addCoupon.do',
 			 data:$('#form2').serialize(),
 			 dataType:'json',
 			 loadfun:function(a,b){
 				common.load.hide();
 				 if(a){
 					if(b.state == 1){
 						common.alert({
 							msg:'保存成功',
 							fun:function(){
 								common.closeWindow("addCoupon", 3);
 							}
 						});
 					} else if(b.state == -4){
 						common.alert('请先登陆在进行操作');
 					} else if(b.state == -2){
 						common.alert('图标上传失败');
 					} else if(b.state == -3){
 						common.alert('有效期时间处理失败');
 					} else if(b.state == -5){
 						common.alert('卡券同步微信失败');
 					} else if(b.state == -6){
 						common.alert('卡券编号更新失败');
 					} else {//同步失败
 						common.alert('操作失败，请重新操作，如果还是失败请联系管理员，谢谢！');
 					}
 				 }else{
 					 common.alert(common.msg.error);
 				 }
 			 }
 		 });
		
	},
	/**
	 * 检查合约信息是否填写
	 * @returns
	 */
	checkHy:function(){
		var ruledegree = $('.ruledegree:checked').val();
		if(ruledegree == undefined){
			layer.tips('请设置合约作用类型。', document.getElementById("rule_degree"), {
	            tips: [1, '#d9534f'] //还可配置颜色
	        });
			return false;
		}else if(ruledegree == 1){
			var rule_degree_month = $('#rule_degree_month').val();
			if(rule_degree_month == ''){
				layer.tips('请设置在第几个月生效。', $('#rule_degree_month'), {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}
			if(!Validator.isInteger2(rule_degree_month)){
				layer.tips('请设置正确的第几个月生效。', $('#rule_degree_month'), {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}else{
				if(parseInt(rule_degree_month) < 1 || parseInt(rule_degree_month) > 12){
					layer.tips('生效月份只能是1-12月之间', $('#rule_degree_month'), {
                        tips: [1, '#d9534f'] //还可配置颜色
                    });
					return false;
				}
			}
			
		}
		return true;
	},
	/**
	 * 校验有效期
	 */
	checkTime:function(){
		var setTime = $('.setTime:checked').val();
		if(setTime == undefined){//未选择卡券有效期
			layer.tips('请设置卡券有效期。', document.getElementById("labelkqyxq"), {
	            tips: [2, '#d9534f'] //还可配置颜色
	        });
			return false;
		}else if(setTime == 0){//选择了卡券有效期 固定日期
			if($('#begin_timestamp').val() == ''){
				layer.tips('请设置卡券开始与结束时间。', $('#begin_timestamp'), {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}
		}else if(setTime == 1){
			var fixed_begin_term = $('#fixed_begin_term').val();
			var fixed_term = $('#fixed_term').val();
			if(fixed_begin_term == ''){
				layer.tips('生效日期必须是0到90之间的数。', $('#fixed_begin_term'), {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}
			if(!Validator.isInteger2(fixed_begin_term)){
				layer.tips('生效日期必须是0到90之间的数。', $('#fixed_begin_term'), {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}else{
				if(parseInt(fixed_begin_term) < 0 || parseInt(fixed_begin_term) > 90){
					layer.tips('生效日期必须是0到90之间的数。', $('#fixed_begin_term'), {
                        tips: [1, '#d9534f'] //还可配置颜色
                    });
					return false;
				}
			}
			if(fixed_term == ''){
				layer.tips('生效日期必须是0到90之间的数。', $('#fixed_term'), {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}
			if(!Validator.isInteger2(fixed_term)){
				layer.tips('生效日期必须是1到90之间的数。',$('#fixed_term'),  {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}else{
				if(parseInt(fixed_term) < 1 || parseInt(fixed_term) > 90){
					layer.tips('生效日期必须是1到90之间的数。',$('#fixed_term'),  {
                        tips: [1, '#d9534f'] //还可配置颜色
                    });
					return false;
				}
			}
			
		}
		return true;
	},
	/**
	 * 校验使用条件
	 */
	checkSytj:function(){
		var zdxf = $('#zdxf_c:visible:checked').size();
		if(zdxf == 1 ){//已选择
			var rule_least_cost = $('#rule_least_cost').val();
			if(rule_least_cost == ''){
				layer.tips('请填写使用条件中最低消费满多少元可用，请填入正数。',$('#rule_least_cost'),  {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}
			if(!Validator.isInteger2(rule_least_cost)){
				layer.tips('请填写使用条件中最低消费满多少元可用，请填入正数。',$('#rule_least_cost'),  {
                    tips: [1, '#d9534f'] //还可配置颜色
                });
				return false;
			}
		}
		var syfw = $('#syfw_c:visible:checked').size();
		if(syfw == 1 ){//已选择
			var rule_least_cost = $('#rule_least_cost').val();
			if($('#accept_category').val()==''&&$('#reject_category').val() == ''){
				common.alert('选择适应范围后，适应商品与不适应商品其中必填一项。');
				return false;
			}
		}
		return true;
	},
	/**
	 * 适用范围
	 * @param obj
	 */
	clickSyfw:function(obj){
		var truth = obj.checked;
		if(truth){
			$('#syfw').show();
		}else{
			$('#syfw').hide();
		}
	},
	/**
	 * 最低消费
	 * @param obj
	 */
	clickZdxf:function(obj){
		var truth = obj.checked;
		if(truth){
			$('#zdxfb').show();
		}else{
			$('#zdxfb').hide();
		}
	}
};
$(function(){
	addCoupon.init();
});