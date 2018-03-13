 var modals=$('#rankModal');
 var rankid="";
var orderadd = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
    	orderadd.initType();
		// 添加处理事件
    	orderadd.addEvent();
    	// 时间按钮选择事件
    	//orderadd.initDateTimeP();
    	// 时间按钮选择事件
		var nowTemp = new Date();
		$('#oserviceTime').daterangepicker(
		{
		   startDate : nowTemp.getFullYear() + '-'+ (nowTemp.getMonth() + 1) + '-02',
		   timePicker12Hour:false,
		   timePicker: true,
		   timePickerIncrement: 10,
		   singleDatePicker : true,
		   format : 'YYYY-MM-DD HH:mm'
		}, function(start, end, label)
		{
		   console.log(start.toISOString(), end.toISOString(), label);
		});
	},
	//初始化时间
/*	initDateTimeP:function () {
	$('.form_datetime').datetimepicker({
		language : 'zh-CN',
		weekStart : 1,
		todayBtn : 1,
		autoclose : 1,
		todayHighlight : 1,
		startView : 2,
		forceParse : 0,
		showMeridian : 1
	});
  },*/
   addEvent: function(){
        $('#orderadd_bnt').click(function()
        {
        	orderadd.save();
        });
        
        $('#ouserMobile').blur(function()
        { 
             $('#ouserName').val(orderadd.getUserName());	
        });
    },
    /**
     加载区域选择
     **/
    initType: function(){
        var type="3";//区域
        var fatherid="";//上级Id
        /**
         * 区域
         * */
        common.loadArea(fatherid,type, function(json)
        {
            var html = "";
            for (var i = 0; i < json.length; i++)
            {
 				html += '<option  value="' + json[i].id + '" >' + json[i].area_name + '</option>';
            }
            $('#area').append(html);
        });
        /**
         * 租住类型
         * */
        common.loadItem('RANKHOUSE.TYPE', function(json)
        {
            var html = "";
            for (var i = 0; i < json.length; i++)
            {
 				html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
            }
            $('#rankType').append(html);
        });
    },
    Open: function()
    {
    	modals.modal();
    	orderadd.rankhouse();
    },
    rankhouse:function()
    {
	   $('#rankType').change(function() {
		   $('#search').click();
	   });
	   $('#area').change(function() {
		   $('#search').click();
	   });
		var tableobj = $("#rank_table").data('table');
	if (tableobj == undefined)
	{
	table.init({
		id : '#rank_table',
		url : common.root + '/houserank/getrankhouse.do',
		columns : [ "rank_code", "title", "rank_type", "spec","rank_area", ],
		param : function() 
		{
		var a = new Array();
		a.push({name : "type",value : $('#rankType').val()});
		a.push({name : 'status',value : "3"});
		
		a.push({name : 'areaid',value : $('#area').val()});
		a.push({name : 'houseName',value : $('#keywordT').val()});
		return a;
		},
		issingle : true,
		isexp : false,
		select : function(data) 
		{
			rankid = data.id;
			$('#rankName').val(data.title);
			$('#oname').val("预约看房-" + data.address);
			modals.modal('hide');
		},
		createRow : function(rowindex, colindex, name, value, data) 
		{
		 if (colindex == 1)
		 {
		   return orderadd.dealColum({"value" : value,"length" : 5});
		 }
		}
	});
	}
	else
	{
		 $('#search').click();
	}
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
	},save:function(){
	   if (!Validator.Validate('form2'))
	   {
     	return;
       }
		 common.ajax({
	            url: common.root + '/Order/createOrder.do',
	            data:{
	            	orderName:$('#oname').val(),
	            	correspondent:rankid,
	            	ouserMobile:$('#ouserMobile').val(),
	            	ouserName:$('#ouserName').val(),
	            	oserviceTime:$('#oserviceTime').val(),
	            	odesc:$('#odesc').val(),
	            	otype:0,
	            	childtype:0
	            },
				dataType:'json',
	            loadfun: function(isloadsucc, data){
	                if (isloadsucc)
	                {
	                    if (data.state == 1) 
	                    {//操作成功
	                 		common.alert({msg:'操作成功'});
							common.closeWindow('orderadd0',3);
	                    }
	                    else
	                    {
	                    	$("#group_add_bnt").attr("disabled",false);
							common.alert({ msg: common.msg.error});
						}
	                }
	                else 
	                {
	                	$("#group_add_bnt").attr("disabled",false);
	                    common.alert({ msg: common.msg.error });
	                }
	            }
	        });
	},//获取用户名称
	getUserName:function()
	{
		var mobile = $('#ouserMobile').val();
		if(mobile == '' || mobile.length != 11)
		{
			$('#ouserName').removeAttr("readonly","readonly");
			return ;
		}
		common.ajax({
			url : common.root + '/sys/getUserName.do',
			data : {userMobile : mobile},
			dataType : 'json',
			loadfun : function(isloadsucc, data) {
				if (isloadsucc)
				{
					var name = data.username;
					$('#ouserName').val(name);
					if(name != null && name != '' && name != "null")
					{
						$('#ouserName').attr("readonly","readonly");
					}
					else
					{
						$('#ouserName').removeAttr("readonly","readonly");
					}
				} 
				else 
				{
					common.alert({
						msg : common.msg.error
					});
				}
			}
		});
	},
};
orderadd.init();