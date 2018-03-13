var dispatch = {
	// 页面初始化操作
	init : function() {
		common.ajax({
    		url : common.root + '/houserank/viewFile.do',
    		dataType : 'json',
    		data:{id:rowdata.id},
    		async : false,
    		loadfun : function(isloadsucc, data) 
    		{
    		  if (isloadsucc)
    		  {
    			  if (data.path!=null&&data.path!="") {
    					var pas=data.path.split(",");
    					var paths=new Array();
    					for ( var int = 0; int < pas.length-1; int++) {
    						if (int==0) {
    							paths.push({path:pas[int],first:1});	
    						}else{
    							paths.push({path:pas[int],first:0});
    						}
    					}
    					common.dropzone.init({
    						id : '#upurl',
    						defimg:paths,
    						maxFiles:10,
    						clickEventOk:false
    					});
    				}else{
    					common.dropzone.init({
    						id : '#upurl',
    						maxFiles:10,
    						clickEventOk:false
    					});
    				}
    		  } 
    		  else 
    		  {
    		      common.alert({msg : common.msg.error});
    		  }
    		}
    	});
	},
};
dispatch.init();