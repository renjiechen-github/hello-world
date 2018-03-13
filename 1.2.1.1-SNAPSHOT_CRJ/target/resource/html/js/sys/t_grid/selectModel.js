 var modals=$('#myModal');
var selectModel = {
    //页面初始化操作
	    init : function() 
	    {
	    selectModel.initType();
		$('#area').change(function()
		{
	 		   $('#searchs').click();
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
    },
    Open: function()
    {
    	modals.modal();
    	selectModel.grouplist();
    },
    grouplist:function()
    {
		var tableobj = $("#selectTable").data('table');
	if (tableobj == undefined)
	{
	table.init({
		id : '#selectTable',
		url : common.root + '/Grid/getGroupList.do',
		columns : [ "areaname",
  		            "group_name", 
		            "type_name",  
		            "g_name", 
		            "name"
		            ],
		param : function() 
		{
		   var a = new Array();
		   a.push({name : 'flag',value : 1});
		   a.push({name : 'areaid',value :  $('#area').val()});
		   a.push({name : 'out_g_id',value : rowdata.g_id});
		   a.push({name : 'keyword',value : $('#keywordT').val()});
		   return a;
		},
		issingle:true,
		isexp : false,
		select : function(data) 
		{
			var sendmsg="是否将此小区添加入网格？";
			if (data.g_name!=null&&data.g_name!="")
			{   if (data.name==null||data.name=="")
			   {
				   sendmsg="此小区已在_"+data.g_name+"_网格内是否改变此小区网格归属？";
			   }
				sendmsg="此小区已在_"+data.g_name+"_"+data.name+"的网格内是否改变此小区网格归属？";
			}
			 common.alert({
				            msg: sendmsg,
				            confirm: true,
				            fun: function(action)
				            {
				                if (action) 
				                {
				                	common.ajax({
				                        url: common.root + '/Grid/groupUpdate.do',
				                        data: {g_id: rowdata.g_id,id:data.id},
				                        dataType: 'json',
				                        loadfun: function(isloadsucc, data)
				                        {
				                            if (isloadsucc)
				                            {
				                                if (data.state == '1') 
				                                {
				                                    common.alert({
				                                        msg: '操作成功',
				                                        fun: function()
				            				            {
				                                        	 $('#searchs').click();
				                                        	 table.refreshRedraw('group_table');
				            				            }
				                                    });
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
			 })
		},
		createRow : function(rowindex, colindex, name, value, data) 
		{
		    if (colindex == 1)
		    {
		      return selectModel.dealColum({"value" : value,"length" : 8});
		    }
		}
	});
	}
	else
	{
		 $('#searchs').click();
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
	}
};
selectModel.init();