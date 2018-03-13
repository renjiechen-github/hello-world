if(typeof njyc == 'undefined') {
	var njyc = new Object();
}


//IOS使JS和客户端交互使用
function execute(url){ 
   var iframe = document.createElement("IFRAME");
   iframe.setAttribute("src", url);
   document.documentElement.appendChild(iframe);
   iframe.parentNode.removeChild(iframe);
   iframe = null;
} 

function showOrHide(hide1){
	var id = "#"+hide1;
	var display =$(id).css('display');
	if(display == 'none'){
	   $(id).show();
	}else{
	$(id).hide();
	}
}

function showOrHide(hide2){
	var id = "#"+hide2;
	var display =$(id).css('display');
	if(display == 'none'){
	   $(id).show();
	}else{
	$(id).hide();
	}
}

function showOrHide(hide3){
	var id = "#"+hide3;
	var display =$(id).css('display');
	if(display == 'none'){
	   $(id).show();
	}else{
	$(id).hide();
	}
}

var xxx='';
var t1 = null;
var t3 = null;

njyc.phone = {
	clientType: /android/i.test(navigator.userAgent) ? 'ANDROID' : (/ipad|iphone|mac/i.test(navigator.userAgent) ? 'IOS' : 'PC'),
	showAlert: function (msg) {//alert消息框
		if(this.clientType == 'ANDROID'){
			window.client.showAlert(msg);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::showAlert::"+msg; 
			execute(url);
		} else {
			
		}
	},
	showProgress: function (msg) {//显示加载等待框
		if(this.clientType == 'ANDROID'){
			window.client.showProgress(msg);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::showProgress::"+msg; 
			execute(url);
		} else {
			
		}
	},
	closeProgress: function () {//关闭加载等待框
		if(this.clientType == 'ANDROID'){
			window.client.closeProgress();
		} else if(this.clientType == 'IOS'){
			var url="cmopios::closeProgress"; 
			execute(url);
		} else {
			
		}
	},
	showShortMessage: function (msg) {//显示短消息
		if(this.clientType == 'ANDROID'){
			window.client.showShortMessage(msg);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::showShortMessage::"+msg; 
			execute(url)
		} else { 
			
		}
	},
	openWebKit: function (opt) {//打开webkit
		var def = {
				url:'', //  地址
				title:'', //  标题
				titleFlag:'1', // 显示标题 0：无 1：有
				screenType:'0', // 0：竖屏 1：横屏
				param:'',
				hasRightBtn:'',
				rightIcon:'',
				rightJs:'',
				hasRightBtn1:'',
				rightIcon1:'',
				rightJs1:'',
				isref:'1'
		};
		jQuery.extend(def, opt);
		console.log(def.url)
		if(this.clientType == 'ANDROID'){
//			window.location.href=def.url;
			window.client.openWebKit(def.url, def.title, def.titleFlag, def.screenType, def.param, def.hasRightBtn, def.rightIcon, def.rightJs, def.hasRightBtn1, def.rightIcon1, def.rightJs1, def.isref);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::openWebKit::"+def.url+"::"+def.title+"::"+def.titleFlag+"::"+def.screenType+"::"+def.param+"::"+def.hasRightBtn+"::"+def.rightIcon+"::"+def.rightJs+"::"+def.hasRightBtn1+"::"+def.rightIcon1+"::"+def.rightJs1+"::"+def.isref; 
			execute(url);
		} else {
			
		}
	},
	closeWebView: function () {//关闭webkit
		if(this.clientType == 'ANDROID'){
			window.client.closeWebView();
		} else if(this.clientType == 'IOS'){
			var url="cmopios::closeWebView"; 
			execute(url)
		} else {
			
		}
	},
	closeWebViewRef: function (className) {//关闭webkit并刷新父类
		if(this.clientType == 'ANDROID'){
			window.client.closeWebViewRef(className);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::closeWebViewRef::"+className; 
			execute(url)
		} else {
			
		}
	},
	closeCallBack: function (jsStr) {//关闭webkit并执行父页面的js方法
		if(this.clientType == 'ANDROID'){
			window.client.closeCallBack(jsStr);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::closeCallBack::"+jsStr; 
			execute(url);
		} else {
			
		}
	},
	getLastLocation: function () {//获取地址经纬度json字符串
		if(this.clientType == 'ANDROID'){
			return window.client.getLastLocation();
		} else if(this.clientType == 'IOS'){
			xxx=''
			var url="cmopios::getLastLocation"; 
			execute(url)
			return xxx;
		} else {
			
		}
	},
	getLocation2: function (jsStr) {//获取地址经纬度json字符串
		if(this.clientType == 'ANDROID'){
			window.client.getLocation2(jsStr);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::getLocation2::"+jsStr; 
			execute(url)
		} else {
			
		}
		
	},
	getSysInfo: function (key) {//获取系统信息json字符串
		// return "http://192.168.90.207:80/";
		if(this.clientType == 'ANDROID'){
			return window.client.getSysInfo(key);
		} else if(this.clientType == 'IOS'){
			 
			return "http://manager.room1000.com:8187/";
		} else {
		}
	},
	navigationMap:function(opt)
	{
		var def = 
		{
			latlng:'latlng:118.741195,32.059332|name:我的位置', //  地址
			dist:'' //  目的地
		};
		jQuery.extend(def, opt);
		if(this.clientType == 'ANDROID'){
			return window.client.navigationMap(def.latlng,def.dist);
		} else if(this.clientType == 'IOS'){
			xxx='';
			var url="cmopios::navigationMap::"+def.latlng; 
		    execute(url)
		    return xxx;
		} else {
			
		}
	},
	scanRQcode:function(jsCallBack)
	{
		if(this.clientType == 'ANDROID'){
			return window.client.scanQRCode(jsCallBack);
		} else if(this.clientType == 'IOS'){
			xxx='';
			var url="cmopios::scanQRCode::"+jsCallBack;
		    execute(url)
		    return xxx;
		} else {
			
		}
	},
	getUserInfo: function (key) {//获取用户信息json字符串
		if(this.clientType == 'ANDROID'){
			return window.client.getUserInfo(key);
		} else if(this.clientType == 'IOS'){
			xxx='';
			var url="cmopios::getUserInfo::"+key; 
		    execute(url)
		    return xxx;
		} else {
			
		}
	},
	showSelectDlg: function(data, textId, valueId, textKey, valueKey, hasSearch){//显示下拉框
		if(this.clientType == 'ANDROID'){
			window.client.showSelectDlg(data, textId, valueId, textKey, valueKey, hasSearch);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::showSelectDlg::"+data.replace(/\'/gi,'\"')+"::"+textId+"::"+valueId+"::"+textKey+"::"+valueKey+"::"+hasSearch; 
		    execute(url)
		} else {
			
		}
	},
	showDateDlg: function(type, domId, defaultValue){//显示日期框
		if(this.clientType == 'ANDROID'){
			window.client.showDateDlg(type, domId, defaultValue);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::showDateDlg::"+type+"::"+domId+"::"+defaultValue; 
		    execute(url)
		} else {
			
		}
	},
	openCamera: function(fileNameShowDom, filePathDom, fileIdDom, fileNameDom){//打开照相机拍照
		if(this.clientType == 'ANDROID'){
			window.client.openCamera(fileNameShowDom, filePathDom, fileIdDom, fileNameDom);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::openCamera::"+fileNameShowDom+"::"+filePathDom+"::"+fileIdDom+"::"+fileNameDom; 
		    execute(url)
		} else {
			
		}
	},
	selectImageForPhoto: function(fileNameShowDom, filePathDom, fileIdDom, fileNameDom){//从图片库中选择照片
		if(this.clientType == 'ANDROID'){
			window.client.selectImageForPhoto(fileNameShowDom, filePathDom, fileIdDom, fileNameDom);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::openCamera::"+fileNameShowDom+"::"+filePathDom+"::"+fileIdDom+"::"+fileNameDom; 
		    execute(url)
		} else {
			
		}
	},
	openFileSelect: function(fileType, fileNameShowDom, filePathDom, fileIdDom, fileNameDom){//打开照相机拍照
		if(this.clientType == 'ANDROID'){
			window.client.openFileSelect(fileType, fileNameShowDom, filePathDom, fileIdDom, fileNameDom);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::openCamera::"+fileNameShowDom+"::"+filePathDom+"::"+fileIdDom+"::"+fileNameDom; 
		    execute(url)
		} else {
			
		}
	},
    submitFileForm: function(formDomId, filePaths, fileIds, fileSavePath){//提交拍照文件表单
		if(this.clientType == 'ANDROID'){
			window.client.submitFormWithMultiFiles(formDomId, filePaths, fileIds, fileSavePath);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::submitFormWithMultiFiles::"+formDomId+"::"+filePaths+"::"+fileIds+"::"+fileSavePath; 
		    execute(url)
		} else {
			
		}
	},
    submitFileAjaxForm: function(jscript, filePaths, fileIds, fileSavePath, errorjscript){//提交拍照文件表单
		if(this.clientType == 'ANDROID'){
			window.client.submitFormWithMultiFiles2(jscript, filePaths, fileIds, fileSavePath, errorjscript);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::submitFormWithMultiFiles::"+jscript+"::"+filePaths+"::"+fileIds+"::"+fileSavePath; 
		    execute(url)
		} else {
			
		}
	},
	previewServerPhoto: function(photoPath){//服务器图片预览
		if(this.clientType == 'ANDROID'){
			window.client.previewServerPhoto(photoPath);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::previewServerPhoto::"+photoPath; 
		    execute(url)
		} else {
			
		}
	},
	previewClientPhoto: function(photoPath){//本地图片预览
		if(this.clientType == 'ANDROID'){
			window.client.previewClientPhoto(photoPath);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::previewClientPhoto::"+photoPath; 
		    execute(url)
		} else {
			
		}
	},
	selectImage:function(picNumber,selector)
	{
		// 选择照片 从相机或者图片库中
		if(this.clientType == 'ANDROID'){
			window.client.selectImage(picNumber,selector);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::selectImage::"+picNumber+"::"+selector;
		    execute(url)
		} else { 
			
		}
	},
	showPic:function(picPath, selector)
	{
		if(!common.isEmpty(selector))
		{
			if(common.isEmpty(picPath))
			{
				return ;
			}
			var picSize = $('#picSize').val();
			//		alert(picPath);
//			picPath = '\/upload\/tmp\/2f44c32b-8ea7-4ef2-8b57-3a5dfcfde8a5.png|\/upload\/tmp\/2f44c32b-8ea7-4ef2-8b57-3a5dfcfde8a5.png|\/upload\/tmp\/2f44c32b-8ea7-4ef2-8b57-3a5dfcfde8a5.png|\/upload\/tmp\/4834710b-66db-4573-85e3-f0028df1f810.png	';
			picPath = picPath.split('|');
			for(var i = 0; i < picPath.length; i++)
			{
				var url = picPath[i];
				var newUrl = '';
				if(url.indexOf('/tmp/') > -1)
				{
					newUrl = njyc.phone.getSysInfo('serverPath') + url;
				}
				else
				{
					newUrl = url;
				}
//				var pathImageSize = $('.imgbox').size(); // 已经上传的图片 
				var html = '<li class="imgbox">'
					+ ' <b class="item_new_close item_close" href="javascript:void(0)" onclick="njyc.phone.deletImage(this)" data_select="'+selector+'" data_src="'+newUrl+'"  title="删除"></b>'
					+ ' <a data-lightbox="example-set" href="'+newUrl+'"><input type="hidden" value="'+url+'" name="picImage"><span class="item_box"><img src="'+newUrl+'" width="50" height="50" onclick="return false;">'
					+ ' </span></a></li>';
				$('#'+selector+'').append(html);
				
				var pathImageSize = $('#'+selector+'').find('.imgbox').size(); // 已经上传的图片
				if(pathImageSize >= picSize)
				{
					$('#'+selector).prev().hide();
				//	$('#selectImage').hide();
				}
			}
		}
		else
		{
			if(!common.isEmpty(picPath))
			{
				var picSize = $('#picSize').val();
				//		alert(picPath);
//				picPath = '\/upload\/tmp\/2f44c32b-8ea7-4ef2-8b57-3a5dfcfde8a5.png|\/upload\/tmp\/2f44c32b-8ea7-4ef2-8b57-3a5dfcfde8a5.png|\/upload\/tmp\/2f44c32b-8ea7-4ef2-8b57-3a5dfcfde8a5.png|\/upload\/tmp\/4834710b-66db-4573-85e3-f0028df1f810.png	';
				picPath = picPath.split('|');
				for(var i = 0; i < picPath.length; i++)
				{
					var url = picPath[i];
					var newUrl = '';
					if(url.indexOf('/tmp/') > -1)
					{
						newUrl = njyc.phone.getSysInfo('serverPath') + url;
					}
					else
					{
						newUrl = url;
					}
					var pathImageSize = $('.imgbox').size(); // 已经上传的图片 
					var html = '<li class="imgbox">'
						+ ' <b class="item_new_close item_close" href="javascript:void(0)" onclick="njyc.phone.deletImage(this)" data_select="" data_src="'+newUrl+'"  title="删除"></b>'
						+ ' <a data-lightbox="example-set" href="'+newUrl+'"><input type="hidden" value="'+url+'" name="picImage"><span class="item_box"><img src="'+newUrl+'">'
						+ ' </span></a></li>';
					$('.ipost-list').append(html);
					pathImageSize = $('.ipost-list').find('.imgbox').size(); // 已经上传的图片
					if(pathImageSize >= picSize)
					{
						$('#selectImage').hide();
					}
				}	
			}	
		}	
	},
	showPicById:function(picPath, selector)
	{
		
		
	},
	deletImage:function(obj)
	{
		// 删除上传图片 
		 var picSize = $('#picSize').val();
		 var selector = $(obj).attr('data_select');
		 var dataUrl = $(obj).attr('data_src');
		 if((selector.indexOf('selectImage1') > -1 || selector.indexOf('selectPropertyPath') > -1 || selector.indexOf('selectAgentPath') > -1) && dataUrl.indexOf('/tmp/') == -1)
		 {
			 var path = "";
			 var returnI = false;
			 var filepath = $('#'+selector+' input[name="picImage"]');
			 for (var i = 0; i < filepath.length; i++) 
			 {
				 path += ',' + $(filepath[i]).val();
				 returnI = true;
			 }
			 if (returnI)
			 {
				 path = path.substring(1);
			 }
			 
			 if(selector.indexOf('selectImage1') > -1)
			 {
				 selector = 'file_path';
			 }	
			 else if(selector.indexOf('selectPropertyPath') > -1)
			 {
				 selector = 'propertyPath';
			 }	
			 else
			 {
				 selector = 'agentPath';
			 }
			 
			window.wxc.xcConfirm('此文件删除后不能恢复，是否删除？', window.wxc.xcConfirm.typeEnum.confirm,{title:'提示',onOk:function(){
				njyc.phone.ajax({
					url : njyc.phone.getSysInfo('serverPath') + '/mobile/houseMgr/removeRemotFile.do',
					dataType : 'json',
					data : {id:$('#agreementId').val(),src:dataUrl,type:selector,filePath:path},
					loadfun : function(isloadsucc, data)
					{
						if (isloadsucc) 
						{
							if(data.state != -1)
							{
								var pathImageSize = $(obj).parent().parent().parent().find('.imgbox').size() - 1; // 已经上传的图片
								if(pathImageSize < picSize)
								{
									$(obj).parent().parent().parent().parent().find('.file').show();
									$(obj).parent().parent().parent().find('.file').show();
								}
								$(obj).parent().remove();
							}
							else
							{
								njyc.phone.showShortMessage('网络忙,请稍候重试!');
							}	
						}
					}
				});	
			},onCancel:function(){
//					house_detail.istopFunc(state);
				}
			});
	 	 }
		 else
		 {
			 var pathImageSize = $(obj).parent().parent().parent().find('.imgbox').size() - 1; // 已经上传的图片
			 if(pathImageSize < picSize)
			 {
				 $(obj).parent().parent().parent().parent().find('.file').show();
				 $(obj).parent().parent().parent().find('.file').show();
			 }
			 $(obj).parent().remove();
		 }	 
	},
	initCookie: function(){//COOKIE设置
		if(this.clientType == 'ANDROID'){
		
		} else if(this.clientType == 'IOS'){
		    document.cookie ='httpServerPort='+njyc.phone.getSysInfo('httpServerPort')+';path=/'
			document.cookie ='client_type=IOS;path=/';
			document.cookie ='CMOPBASE='+njyc.phone.getSysInfo('CMOPBASE')+';path=/';
			document.cookie ='mobileModel='+njyc.phone.getSysInfo('mobileModel')+';path=/';
			document.cookie ='versionName='+njyc.phone.getSysInfo('versionName')+';path=/';
			document.cookie ='versionIoS='+njyc.phone.getSysInfo('versionIoS')+';path=/';
			document.cookie ='ORG_ID='+njyc.phone.getSysInfo('ORG_ID')+';path=/';
			document.cookie ='POST_ID='+njyc.phone.getSysInfo('POST_ID')+';path=/'
			document.cookie ='aaa=wwe;path=/'
		} else {
			
		}
	},
	showAlertSelDialog: function(data){//弹出对话框，里面有几项供点击选择
		if(this.clientType == 'ANDROID'){
			window.client.showAlertSelDialog(data);
		} else if(this.clientType == 'IOS'){
			var url="cmopios::showAlertSelDialog::"+data; 
		    execute(url)
		} else {
			
		}
	},
	gotoSetting: function(){//设置
		if(this.clientType == 'ANDROID'){
			window.client.gotoSetting();
		} else if(this.clientType == 'IOS'){
			var url="cmopios::gotoSetting"; 
		    execute(url)
		} else {
			
		}
	},
	getLocation: function(){//获取登录位置
		if(this.clientType == 'ANDROID'){
			window.client.getLocation();
		} else if(this.clientType == 'IOS'){
			var url="cmopios::gotoSetting"; 
			execute(url)
		} else {
			
		}
	},
	showAddressInfo:function(jsonStr)
	{
		// 显示地址信息
		var json = eval('('+jsonStr+')');
		if(common.isEmpty(json.address))
		{
			njyc.phone.showShortMessage('地址获取失败,请重新获取');
			return ;
		}
		$('#groupAddress').val(json.address); // 地址
		$('#groupCoordinate').val(json.jwd); //经纬度
	},
	callSomeOne:function(tel) // 打电话
	{
		if(this.clientType == 'ANDROID')
		{
			window.client.callSomeOne("tel:"+tel);
		}
		else if(this.clientType == 'IOS')
		{
			var url="cmopios::callSomeOne::"+tel; 
		    execute(url)
		}
	},
	logOut:function() // 退出登录
	{
		if(this.clientType == 'ANDROID')
		{
			window.client.logOut();
		}
		else if(this.clientType == 'IOS')
		{
			var url="cmopios::logOut"; 
		    execute(url)
		}
	},
	loginOut:function()
	{
		// 系统中session过期调用退出方法
		if(this.clientType == 'ANDROID')
		{
			window.client.loginOut();
		}
		else if(this.clientType == 'IOS')
		{
			var url="cmopios::loginOut"; 
		    execute(url)
		}
	},
	eidtUserInfo:function()
	{
		if(this.clientType == 'ANDROID')
		{
			window.client.eidtUserInfo();
		}
		else if(this.clientType == 'IOS')
		{
			var url="cmopios::eidtUserInfo"; 
		    execute(url)
		}
	},
	/**
     * 进行ajax请求
     * url:'',
     dataType:'text',//'json'|'text'|'jsonp'
     async:true,
     type:'get',//post
     timeout:100000,//超时时间
     success:function(data){},
     error:function(){},
     xhrFields:{}//数据
     * @param {Object} opt
     */
	
    ajax: function(opt){
        var def = {
            url: '',
            dataType: 'text',//'json'|'text'|'jsonp'
            async: true,
            type: 'post',//post
            timeout: 100000,//超时时间
            loadfun: function(isloadsucc, data){
            },//判断是否加载成功，isloadsucc为true表示加载成功，否则表示失败
            data: {},//数据
            delay: 0,//延迟多长时间在加载
            encode:true,//是否进行自动编码，自动将中文转码
            cache:false
        };
        jQuery.extend(def, opt);
//        alert(def.url)
//        njyc.phone.showShortMessage('网络忙,请稍候重试!'+def.url);
		if(def.encode){
			//讲传入参数值进行编码处理
			if(typeof(def.data) != 'string' && def.data != null && def.data != undefined){
				for(var p in def.data){
					var value = def.data[p];
				    def.data[p] = encodeURIComponent(value);
				}
			}else{
				var value = decodeURIComponent(def.data,true);
				def.data = encodeURI(encodeURI(value));
			}
		}
        if (def.delay != 0 && typeof(def.delay) == 'number') {
            setTimeout(function(){
                $.ajax({
				type:def.type,
				url: def.url,
				data:def.data,
				dataType:def.dataType,
				cache:def.cache,
				async:def.async,
				timeout: def.timeout,//超时时间
				success:function(data){
				njyc.phone.closeProgress();
                    try {
						if(data.state == '-99999'){
							njyc.phone.showShortMessage('您还未登陆或登陆已经失效，请登录!');
							// 退出系统
							njyc.phone.loginOut();
							return;
						}else if(data.state == '-99998'){
							njyc.phone.showShortMessage('很抱歉，您没有权限进行此操作!');
							return;
						}
                        def.loadfun(true, data)
                    } 
                    catch (e) {
						console.error(e);
                        def.loadfun(false, e)
                    }
                },
				error: function(data){
					console.error(data);
                    def.loadfun(false, data)
                }
			});
            }, def.delay);
        }
        else {
            $.ajax({
				type:def.type,
				url: def.url,
				data:def.data,
				dataType:def.dataType,
				cache:def.cache,
				async:def.async,
				timeout: def.timeout,//超时时间
				success:function(data){
				njyc.phone.closeProgress();
                    try {
                    	if(data.state == '-99999'){
							njyc.phone.showShortMessage('您还未登陆或登陆已经失效，请登录!');
							// 退出系统
							njyc.phone.loginOut();
							return;
						}else if(data.state == '-99998'){
							njyc.phone.showShortMessage('很抱歉，您没有权限进行此操作!');
							return;
						}
                        def.loadfun(true, data)
                    } 
                    catch (e) {
						console.error(e);
                        def.loadfun(false, e)
                    }
                },
				error: function(data){
					console.error(data);
					njyc.phone.closeProgress();
                    def.loadfun(false, data)
                }
			});
        }
    },
    /**
	 * 加载基础项信息
	 * @param {Object} group_id
	 * @param {Object} fun
	 */
	loadItem:function(group_id,fun,async){
		if(async == undefined )
		{
			async = true;
		}
		njyc.phone.ajax({
			url:njyc.phone.getSysInfo('serverPath')+'sys/queryItem.do',
			data:{group_id:group_id},
			dataType: 'json',
			async:async,
			loadfun: function(isloadsucc, data){
				if(isloadsucc){
					if(data.state == 1){
						fun(data.list);						
					}else{
						njyc.phone.showShortMessage('网络错误,请稍候重试');
					}
				}else{
					njyc.phone.showShortMessage('网络错误,请稍候重试');
				}
			}
		});
	},
	/**
	 * 加载区域信息
	 * @param {Object} fatherid
	 * @param {Object} type
	 *  @param {Object} fun
	 */
	loadArea:function(fatherid,type,fun,async)
	{
		if(async == undefined )
		{
			async = true;
		}
		njyc.phone.ajax({
			url:njyc.phone.getSysInfo('serverPath')+'sys/queryArea.do',
			data:{fatherid:fatherid,type:type},
			async: async,
			dataType: 'json',
			loadfun: function(isloadsucc, data)
			{
				if(isloadsucc)
				{
					if(data.state == 1)
					{
						fun(data.list);						
					}
					else
					{
						njyc.phone.showShortMessage('网络错误,请稍候重试');
					}
				}else{
					njyc.phone.showShortMessage('网络错误,请稍候重试');
				}
			}
		});
	},
	queryString:function(name) 
	{
		return njyc.phone.urlSearch(name);
//	   var urlstr = window.location.search;
//	   var reg = new RegExp("(^|&|?)" +val+ "=([^&?]*)", "ig");
//	   return decodeURI(((urlstr.match(reg))?(urlstr.match(reg)[0].substr(val.length+1)).replace('=',''):''));
//	   var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
//	   var r = window.location.search.substr(1).match(reg);
//	   if(r!=null)return unescape(r[2]); return "";
	},
	urlSearch:function(val) 
	{
	   var name,value; 
	   var str=location.href; //取得整个地址栏
	   var num=str.indexOf("?") 
	   str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]
	   var arr=str.split("&"); //各个参数放到数组里
	   for(var i=0;i < arr.length;i++)
	   { 
		    num=arr[i].indexOf("="); 
		    if(num>0)
		    { 
			    name=arr[i].substring(0,num);
			    value=arr[i].substr(num+1);
			    if(name==val)
			    {
			    	return decodeURI(decodeURI(value));
			    }	
		    } 
	   }
	   return '';
	} 
} 

function showSchDlg(){
	$('#searchDlg').show();
	$('#ibg').show();
}
function hideSchDlg(){
	$('#searchDlg').hide();
	$('#ibg').hide();
}

function noBarsOnTouchScreen(arg){
	var elem, tx, ty;

	if('ontouchstart' in document.documentElement ) {
		if (elem = document.getElementById(arg)) {
			elem.style.overflow = 'hidden';
			elem.ontouchstart = ts;
			elem.ontouchmove = tm;
		}
	}

	function ts(e) {
		var tch;
		
		if(e.touches.length == 1){
			e.stopPropagation();
			tch = e.touches[0];
			tx = tch.pageX;
			ty = tch.pageY;
		}
	}

	function tm(e) {
		var tch;
		
		if(e.touches.length == 1){
			e.preventDefault();
			e.stopPropagation();
			tch = e.touches[ 0 ];
			this.scrollTop +=  ty - tch.pageY;
			ty = tch.pageY;
		}
	}
}

function setSelectValue(selectId, selectValue, optionValue, optionLabel){
	var jsonData = eval($('#'+selectId+'_search').attr('data'));
	for(var j=0;j<jsonData.length;j++){
		if(selectValue == jsonData[j][optionValue]){
			$('#'+selectId+'_search').html(jsonData[j][optionLabel]);
			$('#'+selectId).val(jsonData[j][optionValue]);
			break;
		}
	}
}

function replaceNull(oldValue){
	if(oldValue == 'null' || oldValue == null){
		return '';
	}
	return oldValue;
}

function getRow(src) {
	var row = src;
	while (row && row.tagName != "TR") {
		row = row.parentElement;
	}
	return row;
}


function requestCheck(){
	if (t1 == null){
        t1 = new Date().getTime();
    }else{       
        var t2 = new Date().getTime();
        if(t2 - t1 < 1000){
            t1 = t2;
            return false;
        }else{
            t1 = t2;
        }
    }
    return true;
}
function requestCheck2(){
	if (t3 == null){
        t3 = new Date().getTime();
    }else{       
        var t4 = new Date().getTime();
        if(t4 - t3 < 1000){
            t3 = t4;
            return false;
        }else{
            t3 = t4;
        }
    }
    return true;
} 
$(function(){
	njyc.phone.initCookie(); 
	/**
   	$('.label-title').each(function(){
   		$(this).click(function(){
   		    var httpserverport = njyc.phone.getSysInfo('httpServerPort');
   			if($(this).attr('showFlag') == '1' || $(this).attr('showFlag') == undefined){
   				$(this).next().fadeOut('normal');
   				$(this).attr('showFlag', '0');
   				if($(this).find('img')){
   					$(this).find('img').attr('src', 'http://127.0.0.1:'+httpserverport+'/client/res/images/close.png');
   				}
   			} else {
   				$(this).next().fadeIn('normal');
   				$(this).attr('showFlag', '1');
   				if($(this).find('img')){
   					$(this).find('img').attr('src', 'http://127.0.0.1:'+httpserverport+'/client/res/images/open.png');
   				}
   			}
   		});
	});
	
	$('select').each(function(){
		var selectId = $(this).attr('id');//下拉框的ID值
		var selectName = $(this).attr('name');//下拉框的NAME值
		var selectSearchId = selectId + "_search";//下拉外面的SPAN值ID
		var selectSearchName = selectName + "_search";//下拉外面的SPAN值NAME
		var style = $(this).attr('style');
		var searchFlag = $(this).attr('searchFlag');
		if(searchFlag == 'undefined' || searchFlag == '' || searchFlag == null){
        	searchFlag = 'false';
        }
		
       	var value = $(this).val();//默认选中的值
       	var i = 0;
       	var jsonStr = '';
       	var optionLabel = $(this).attr('optionLabel');
        var optionValue = $(this).attr('optionValue');
        if(optionLabel == 'undefined' || optionLabel == '' || optionLabel == null){
        	optionLabel = 'TEXT';
        }
        if(optionValue == 'undefined' || optionValue == '' || optionValue == null){
        	optionValue = 'VALUE';
        }
		$(this).find('option').each(function(){
			if(i==0){
				jsonStr+="{'"+optionLabel+"':'"+$(this).html()+"','"+optionValue+"':'"+$(this).val()+"'}";
			}else{
				jsonStr+=",{'"+optionLabel+"':'"+$(this).html()+"','"+optionValue+"':'"+$(this).val()+"'}";
			}
			i ++;
		});
		jsonStr = "["+jsonStr+"]";
       
		$(this).prop('outerHTML', '<span id="'+selectSearchId+'" data="'+jsonStr+'" style="'+style+'" class="client-select" name="'
				+selectSearchName+'">请选择...</span><input type="hidden" name="'+selectName+'" id="'+selectId+'">');
				
		var jsonData = eval(jsonStr);
		for(var j=0;j<jsonData.length;j++){
			if(value == jsonData[j][optionValue]){
				$('#'+selectSearchId).html(jsonData[j][optionLabel]);
				$('#'+selectId).val(jsonData[j][optionValue]);
				break;
			}
		}
		
		$('#'+selectSearchId).click(function(){
			if(eval($(this).attr('data')).length > 0){
				njyc.phone.showSelectDlg($(this).attr('data'), selectSearchId, selectId, optionLabel, optionValue, searchFlag);
			}
		});
	});
	*/
}) 