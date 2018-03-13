var arry=new Array();
var user_view = {
        //页面初始化操作
	    init : function() {
		//初始化数据
	    	user_view.initData(); 
	    },
  //加载修改数据信息
	initData:function(){
		if(rowdata != null){
			 $('#username')[0].innerHTML=rowdata.username;
			 $('#sex')[0].innerHTML=rowdata.sexname;
			 $('#mobile')[0].innerHTML=rowdata.mobile;
			 $('#certificateno')[0].innerHTML=rowdata.certificateno;
			 $('#qq')[0].innerHTML=rowdata.qq!==null|rowdata.qq!==''?rowdata.qq:'';
			 $('#email')[0].innerHTML=rowdata.email==null|rowdata.email?rowdata.email:"";
			 $('#wechat')[0].innerHTML=rowdata.wechat==null|rowdata.wechat?rowdata.wechat:"";
			 $('#birthday')[0].innerHTML=rowdata.birthday==null|rowdata.birthday?rowdata.birthday:"";
			 console.log(rowdata);
			 $('#registertime')[0].innerHTML=rowdata.registertime;
			 $('#lastlogintime')[0].innerHTML=rowdata.lastlogintime;
			 $('#ipadress')[0].innerHTML=rowdata.ipadress;
		}
	}
};
user_view.init();