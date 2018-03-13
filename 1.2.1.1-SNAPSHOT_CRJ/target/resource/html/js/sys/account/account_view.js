var arry=new Array();
var account_view = {
	//页面初始化操作
	init : function() {
		//初始化数据
	    account_view.initData(); 
	},
	//加载修改数据信息
	initData:function(){
		if(rowdata != null){
			 $('#aname')[0].innerHTML=rowdata.aname;
			 $('#mobile')[0].innerHTML=rowdata.mobile;
			 $('#role_')[0].innerHTML=rowdata.role_name;
			 $('#org_')[0].innerHTML=rowdata.org_name;
			 $('#equipment')[0].innerHTML=rowdata.equipment;
			 $('#create_time')[0].innerHTML=rowdata.create_time;
			 $('#lastlogintime')[0].innerHTML=rowdata.lastlogintime;
			 $('#ipadress')[0].innerHTML=rowdata.ipadress;
		}
	}
};
account_view.init();