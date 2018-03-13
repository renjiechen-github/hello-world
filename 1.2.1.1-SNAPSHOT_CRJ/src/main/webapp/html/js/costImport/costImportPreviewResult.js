var htmlHead_TypeA_Begin="<tr num='";
var htmlHead_TypeA_End="' class='odd'>";
var htmlHead_TypeB_End="' class='even'>";
var htmlBody_Begin = "<td class=''>";
var htmlBody_End = "</td>";
var htmlFoot="</tr>";
var handleId="";
var filePath;
var costImportPreviewResult = {
		init:function(){
	    	var state = common.getWindowsData('checkExcelInfo').state;
	    	if(1==state){
	    		handleId = common.getWindowsData('checkExcelInfo').handleId;
	    		var totalRows = common.getWindowsData('checkExcelInfo').totalRows;
	    		filePath = common.getWindowsData('checkExcelInfo').filePath;
	    		if(undefined!=handleId && handleId.length!=0){
	    			$("#result").html("<span>本次将新增</span> <span style='font-weight:bold;font-size:18px'>"+totalRows+"</span> <span>条记录,请核实后开始导入。</span>")
	    		}else{
	    			
	    		}
	    	}else if(-2==state){
	    		$("#btn_import").css("display","none");
	    		var list = common.getWindowsData('checkExcelInfo').list;
	    		var html = "";
	    		for(var i =0;i<list.length;i++){
	    			if(i%2 ==0){
	    				html += htmlHead_TypeA_Begin+i+htmlHead_TypeA_End
	    				+htmlBody_Begin+list[i].roomName+htmlBody_End
	    				+htmlBody_Begin+list[i].year+htmlBody_End
	    				+htmlBody_Begin+list[i].month+htmlBody_End
	    				+htmlBody_Begin+list[i].agerId+htmlBody_End
	    				+htmlBody_Begin+list[i].sbCode+htmlBody_End
	    				+htmlBody_Begin+list[i].remark+htmlBody_End
	    			}else{
	    				html +=htmlHead_TypeA_Begin+i+htmlHead_TypeB_End
	    				+htmlBody_Begin+list[i].roomName+htmlBody_End
	    				+htmlBody_Begin+list[i].year+htmlBody_End
	    				+htmlBody_Begin+list[i].month+htmlBody_End
	    				+htmlBody_Begin+list[i].agerId+htmlBody_End
	    				+htmlBody_Begin+list[i].sbCode+htmlBody_End
	    				+htmlBody_Begin+list[i].remark+htmlBody_End
	    			}
	    		}
	    		$("#repeatResult").css("display","");
	    		$(".errorMsg").css("display","");
	    		$("#body").html(html);
	    	}else{
	    		$(".btn-lg").css("display","none");
	    		$("#result").html("<span style='font-weight:bold;font-size:18px'>Excel格式错误</span>")
	    	}
		},
		closeWindow:function(){
			common.ajax({
				url:common.root+'/costImport/delTempCostImport.do',
	 			 data:{
	 				handleId:handleId
	 			 },
	 			 dataType:'json',
	 			 loadfun:function(isSuc,data){
	 			 }
			});
		},
		import:function(){
			common.ajax({
				url:common.root+'/costImport/insertCostImport.do',
	 			 data:{
	 				handleId:handleId,
	 				filePath:filePath
	 			 },
	 			 dataType:'json',
	 			 loadfun:function(isSccess,data){
	 				common.load.hide();
	 				var totalRows = data.totalRows;
	 				if(data.state==1){
	 					common.closeWindow("checkExcelInfo", 1);
	 					common.openWindow({
	        	            type: 3,
	        	            name:'costImportResult', 
	        	            data:{totalRows:totalRows},
	        	            title: '导入结果',
	        	            url: '/html/pages/costImport/costImport.html' 
	        	        });
	 				}else{
	 					 common.alert(common.msg.error);
	 				 }
	 			 }
			});
			
		}
}
costImportPreviewResult.init();