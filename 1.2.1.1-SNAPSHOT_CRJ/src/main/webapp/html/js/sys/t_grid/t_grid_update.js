var gridUpdate =
{
    //页面初始化操作
    init : function()
    { 
       // 加载结算类型
  	   gridUpdate.initData();
	   gridUpdate.addEvent();
	},
    addEvent: function()
    {
    	
    	
    
    },
    initData: function()
    {
	 if (rowdata != null)
	 {
		 table.init({
	            id: '#group_table',
	            url: common.root + '/Grid/getGroupList.do',
	            columns:[   "area_name",
	    		            "group_name", 
	    		            "type_name",  
	    		            "address", 
	    		            {name:"group_desc",isover:true,isshow:false,title:'小区说明'},
	    		            "basecount",
	    		            "createtime",
	    		            "status_name"
	    		            ],
	            bnt:[{  
			    	 name:'删除',
					 fun:function(data,row)
					 {
	            	    common.alert({
			            msg: '是否移除此小区？',
			            confirm: true,
			            fun: function(action)
			            {
			                if (action) 
			                {
		                	    common.ajax({
		                        url: common.root + '/Grid/groupUpdate.do',
		                        data: {g_id: "",id:data.id},
		                        dataType: 'json',
		                        loadfun: function(isloadsucc, data)
		                        {
		                            if (isloadsucc)
		                            {
		                                if (data.state == '1') 
		                                {
	                                        common.alert({msg: '操作成功'});
	                                        table.refreshRedraw('group_table');
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
				        }
	            }],
	            param: function()
	            {
	                var a = new Array();
	                a.push({name: "g_id", value: rowdata.g_id});
	                a.push({name: "keyName", value:$('#keyName').val()});
	                //keyName
	                return a;
	            },
	        });
	 }
    },
   
};
gridUpdate.init();