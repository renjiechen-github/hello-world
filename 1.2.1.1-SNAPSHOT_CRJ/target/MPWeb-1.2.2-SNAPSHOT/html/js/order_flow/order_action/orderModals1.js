 var modals=$('#myModal');
var orderModal = {
    //页面初始化操作
	    init : function() {
	    orderModal.initType();
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
    	orderModal.agreelist();
    },
    agreelist:function()
    {
	   $('#area').change(function() {
		   $('#search').click();
	   });
		var tableobj = $("#agreetable").data('table');
	if (tableobj == undefined)
	{
	table.init({
		id : '#agreetable',
		url : common.root + '/agreementMge/agreementList.do',
		columns : [ "code", "name", "user_name", "user_mobile","begin_time", "end_time"],
		param : function() 
		{
		var a = new Array();
		a.push({name : 'status',value : "2"});
		a.push({name : 'type',value : "1"});
		a.push({name : 'arear',value : $('#area').val()});
		a.push({name : 'keyWord',value : $('#keywordT').val()});
		return a;
		},
		issingle : true,
		isexp : false,
		select : function(data) 
		{
			orderadd.getAgree(data.id,data.name,data.user_name,data.user_mobile);
			modals.modal('hide');
		},
		createRow : function(rowindex, colindex, name, value, data) 
		{
		    if (colindex == 1)
		    {
		      return orderModal.dealColum({"value" : value,"length" : 5});
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
	}
};
orderModal.init();