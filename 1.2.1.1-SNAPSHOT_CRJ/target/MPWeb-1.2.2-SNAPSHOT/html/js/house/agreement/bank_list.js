var bank_list=
{
		init:function()
		{
			common.loadArea('101', '3', function(json) {
				var html = "";
				for ( var i = 0; i < json.length; i++) {
					html += '<option value="' + json[i].id + '" >'
							+ json[i].area_name + '</option>';
				}
				$('#areaids').append(html);
			},false); 
			common.ajax({
			 	url:common.root + '/agreementMge/bankList.do',
			 	dataType:'json',
			 	async:false,
			 	data:{bankId:-1},
			 	loadfun:function(isloadsucc, json){
					if(isloadsucc){
						var html = "";
						for ( var i = 0; i < json.length; i++) {
							html += '<option value="' + json[i].id + '" >'
									+ json[i].name + '</option>';
						}
						$('#bankList').append(html);
					}
				}
			});
			bank_list.createTable();
			$('#areaids,#bankList').change(function(){
				$("#search").click();
			});
		},
		  /**
	     * 表格创建
	     */
	    createTable: function(){
	        table.init({
	            id: '#bank_table',
	            url: common.root + '/agreementMge/bankList.do',
	            columns: ["areaname","name","code"],
	            "iDisplayLength":5,
	            isexp: false,
	            issingle:true,
	            param: function()
	            {
	                var a = new Array();
					a.push({ "name": "group_name", "value": $('#group_name').val()});
					a.push({name:'areaid',value:$('#areaids').val()}); 
					a.push({name:'bankId',value:$('#bankList').val()}); 
	                return a;
	            },
	            bFilter: false,
	            select:function(data){
	            	console.log(data);
	            	if($.type(data) == 'object')
	            	{
	        			$('#bankId').val(data.code);
	        			$('#carBank').val(data.name);
	        			common.closeWindow('selectBank');
	            	}
	            },
	        });
	    },
};
$(bank_list.init());

