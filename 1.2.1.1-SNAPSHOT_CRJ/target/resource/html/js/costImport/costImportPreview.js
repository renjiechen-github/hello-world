var costImportPreview = {
		// 页面初始化操作
	    init: function () {
	    	$(".title span:first").css("color", "red");
	    	$(".title span:first").css("font-weight", "bold");
	    	
	    },
	    changeFileName:function(){
	    	var file = $("#inputfile").val();
	    	var fileName = file.substring(file.lastIndexOf('\\')+1,file.length);
	    	$("#fileName").val(fileName);
	    },
	    checkExcelInfo:function(){
	    	var path = $("#inputfile").val();
	    	var formData = new FormData();
	        formData.append('file',$("#inputfile")[0].files[0]);
	        $.ajax({
	            type:"post",
	            url:common.root+'/costImport/checkExcelInfo.do',
	            async:false,
	            contentType: false,    //这个一定要写
	            processData: false, //这个也一定要写，不然会报错
	            data:formData,
	            dataType:'text',    //返回类型，有json，text，HTML。这里并没有jsonp格式，所以别妄想能用jsonp做跨域了。
	            success:function(data){
	            	var obj = eval('(' + data + ')');
	                var state = obj.state;
	                var handleId = obj.handleId;
	                var totalRows = obj.totalRows;
	                var filePath = obj.filePath;
	                var list = obj.data;
	                if(1==state){
	                	common.openWindow({
	        	            type: 1,
	        	            name:'checkExcelInfo', 
	        	            data:{handleId:handleId,totalRows:totalRows,filePath:filePath,state:state},
	        	            title: '预览结果',
	        	            url: '/html/pages/costImport/costImportPreviewResult.html' 
	        	        });
	                }else if(-2==state){
	                	common.openWindow({
	        	            type: 1,
	        	            name:'checkExcelInfo', 
	        	            data:{state:state,list:list},
	        	            title: '预览结果',
	        	            url: '/html/pages/costImport/costImportPreviewResult.html' 
	        	        });
	                }else if(-1==state){
	                	common.alert("导入格式错误");
	                }else if(-3==state){
	                	common.alert("费用类型不统一");
	                }else if(-4==state){
	                	common.alert("请上传Excel文件");
	                }else if(-5==state){
	                	common.alert("文件内容为空");
	                }else if(99==state){
	                	common.alert("当天导入次数超过最大值");
	                }else if(9==state){
	                	common.alert("导入数量超过最大值");
	                }else{
	                	common.alert("系统异常请重试");
	                }
	            },
	            error:function(XMLHttpRequest, textStatus, errorThrown, data){
	                alert(errorThrown);
	            }            
	        });
	    },
	    closeWindow:function(){
//	    	alert(123)
	    },
	    import:function(){
	    	common.closeWindow('checkExcelInfo',1);
	    	common.openWindow({
	            type: 3,
	            name:'costImport', 
	            title: '导入结果',
	            url: '/html/pages/costImport/costImport.html' 
	        });
	    },
	    downLoadFile:function(){
//	    	window.loaction.href="http://test.room10000.com:9003/data/costImport/templet/导入模板.xls";
	    	window.location.href='http://test.room10000.com:9003/data/costImport/templet/导入模板.xls';
	    }
}
costImportPreview.init();