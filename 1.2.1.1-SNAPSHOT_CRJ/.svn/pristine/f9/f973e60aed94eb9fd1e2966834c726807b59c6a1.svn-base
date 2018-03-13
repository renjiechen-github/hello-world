var returni=false;

var checkHouse = {
    //页面初始化操作
	    init : function() {
	    // 加载结算类型
	    	checkHouse.initType();
	    	checkHouse.checkinit();
	},
    /**
     加载区域选择
     **/
    initType: function(){
    
    },
    checkinit: function() {
    	if (returni) {
    		return
		}
    	var cfg_step_id=$("#cfg_step_id").val();
    	if (cfg_step_id==3) 
    	{
    		$("#hidedic").html("暂无信息");
		}else{
			var task_id=$("#task_check_id").val();
			njyc.phone.ajax({
				url:njyc.phone.getSysInfo('serverPath') + 'mobile/task/getInfo.do',
				data : {task_id:task_id},
				dataType : 'json',
				async:false,
				loadfun : function(isloadsucc, data) 
				{
					if (isloadsucc)
					{
						returni=true;
						if (data.endelectricdegree==null||data.endelectricdegree=="") 
						{
							$("#hidedic").html("暂无信息");
							return;
						}
						$("#alipay").html(data.alipay);
						$("#bankcard").html(data.bank);
						$("#bankname").html(data.bankname);
						$("#endwaterdegree").html(data.endwaterdegree+"m³");
						$("#endgasdegree").html(data.endgasdegree+"m³");
						$("#endelectricdegree").html(data.endelectricdegree+"°");
						$("#startwaterdegree").html(data.startwaterdegree+"m³");
						$("#startgasdegree").html(data.startgasdegree+"m³");
						$("#startelectricdegree").html(data.startelectricdegree+"°");
						$("#reasons").html(data.reasonsname);
						$("#key").html(data.keyrecieve+"&nbsp;把");
						$("#doorcard").html(data.doorcard+"&nbsp;张");
						$("#otherdesc").html(data.otherdesc);
						$("#checkdesc").html(data.housedesc);
						$("#favourable").html(data.favourable);
						checkHouse.picInit(data.degreepic,"degreepic");
						checkHouse.picInit(data.housepic,"housepic");
					}
				}
			 });
		}
	},
    picInit:function(url,id){
		 //初始化图片上传
		 var picurl = url;
		 if (picurl!=null&&picurl!="") 
		 {
			 picurl=picurl.replace(/,/g,"|");
		 }
		 njyc.phone.showPic(picurl,id);
		 $("#"+id+" .item_close").hide();
	}
};
$(function(){
	checkHouse.init();
});
