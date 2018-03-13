
(function() {
    "use strict";
    // custom scrollbar

    $("html").niceScroll({autohidemode:false,smoothscroll:true,bouncescroll:true,styler:"fb",cursorcolor:"#65cea7", cursorwidth: '16', cursorborderradius: '5px', background: '#424f63', spacebarenabled:false, cursorborder: '0',  zindex: '1000'});

    $(".left-side").niceScroll({bouncescroll:true,styler:"fb",cursorcolor:"#65cea7", cursorwidth: '3', cursorborderradius: '0px', background: '#424f63', spacebarenabled:true, cursorborder: '0'});
    
	
   	$(".left-side").getNiceScroll();
    if ($('body').hasClass('left-side-collapsed')) {
        $(".left-side").getNiceScroll().hide();
    }

    
   // Menu Toggle
   jQuery('.toggle-btn').click(function(){
	   $('.sidebar-menu').toggleClass('sidebar-mini');
	   $('.main-menu').find('.openable').removeClass('open');
	   $('.main-menu').find('.submenu').removeAttr('style');
	   
       $(".left-side").getNiceScroll().hide();
       
       if ($('body').hasClass('left-side-collapsed')) {
           $(".left-side").getNiceScroll().hide();
       }
       var body = jQuery('body');
       var bodyposition = body.css('position');

       $('.dropdown').addClass('left-side-inner');
       $('.dropdown li.selected>a').click();
       
      if(bodyposition != 'relative') {

         if(!body.hasClass('left-side-collapsed')) {
            body.addClass('left-side-collapsed');
            jQuery('.custom-nav ul').attr('style','');

            jQuery(this).addClass('menu-collapsed');

         } else {
            body.removeClass('left-side-collapsed chat-view');
            jQuery('.custom-nav li.active ul').css({display: 'block'});
            jQuery(this).removeClass('menu-collapsed');
         }
      } else {

         if(body.hasClass('left-side-show'))
            body.removeClass('left-side-show');
         else
            body.addClass('left-side-show');

         mainContentHeightAdjust();
      }

   });
   

   searchform_reposition();

   jQuery(window).resize(function(){

      if(jQuery('body').css('position') == 'relative') {

         jQuery('body').removeClass('left-side-collapsed');

      } else {

         jQuery('body').css({left: '', marginRight: ''});
      }

      searchform_reposition();

   });

   function searchform_reposition() {
      if(jQuery('.searchform').css('position') == 'relative') {
         jQuery('.searchform').insertBefore('.left-side-inner .logged-user');
      } else {
         jQuery('.searchform').insertBefore('.menu-right');
      }
   }

    // panel 切换
    $('.panel .tools .fa-chevron-down').live('click',function () {
        var el = $(this).parents(".panel").children(".panel-body").first();
        $(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
        el.slideUp(200);
    });
	$('.panel .tools .fa-chevron-up').live('click',function () {
        var el = $(this).parents(".panel").children(".panel-body").first();
        $(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
        el.slideDown(200); 
    });

    $('.todo-check label').live('click',function () {
        $(this).parents('li').children('.todo-title').toggleClass('line-through');
    });

    /*$(document).live('click', '.todo-remove', function () {
        $(this).closest("li").remove();
        return false;
    });*/

    $("#sortable-todo").sortable();


    // panel close
    $('.panel .tools .fa-times').live('click',function () {
        $(this).parents(".panel").parent().remove();
    });

    // tool tips

    $('.tooltips').tooltip();

    // popovers

    $('.popovers').popover();

})(jQuery);

 /**
 首页操作
 **/
 var index = {
 	/**
 	 * 菜单离开被卸载事件处理
 	 * 返回 true 继续卸载，到下一个菜单，否则不到下一个菜单
 	 */
 	changeFun:function(){return true;},
 	/**
 	 * 菜单加载完成后执行的方法
 	 * @returns {Boolean}
 	 */
 	loadMenuFun:null,
 	user:null,
    init:function(){
		index.loadTime();
		index.loadUser();
		$(window).resize(function(){
			//$('.dataTable').fnDraw();
			//alert(1);
		});
		index.loadSearchEvent();
		$('.user .sign-out').click(function(){
			index.loginout();
		});
		index.modifyPass();
		index.loadMenuEventNew(); 
		$('.notDefMenu').die('click');
		$('.notDefMenu').live('click',function(event){
			var ul = '<div class="table_menu" ><ul class="ulClass">'+$(this).find('ul').html()+'</ul></div>';
			$('body').append(ul);
			var table_menu_w = $('.table_menu').width();
			var caozuobnt_h = $('.table_menu').height();
			var caozuobnt_w = $(this).width();
			var top = $(this).offset().top;
			var left = $(this).offset().left;
			$('.table_menu').css({'left':left-table_menu_w+caozuobnt_w,'top':top+34});
		});
    },
    loadMenuNewSim:function(menu){
		var size = menu.length;
		var html = "";
		//进行循环操作
		for(var i=0;i<size;i++){
			var menu0 = menu[i];
			if(menu0.menu_level == 1){//1级菜单
				if(menu0.isnext == '1'){
					html += '<li class="openable bg-palette'+(i%4+1)+'">';
				}else{
					html += '<li class="openable bg-palette'+(i%4+1)+'">';
				}
				if(menu0.menu_ico == undefined||menu0.menu_ico == ''){
					menu0.menu_ico = 'fa-file-text-o';
				}
				html += '<a href="#" url="'+(menu0.menu_url!=null?menu0.menu_url:'#')+'" menu_id="'+menu0.menu_id+'">';
				html += '<span class="menu-content block">';
				html += '<span class="menu-icon"><i class="block fa '+menu0.menu_ico+' fa-lg"></i></span>';
				html += '<span class="text m-left-sm">'+menu0.menu_name+'</span>';
				if(menu0.isnext == '1'){
					html += '<span class="submenu-icon"></span>';
				}
				html += '</span>';
				html += '<span class="menu-content-hover block">'+menu0.menu_name.charAt(0)+'</span></a>';
				if(menu0.isnext == '1'){
					html += '<ul class="submenu">';
					for(var j=0;j<size;j++){
						var menu1 = menu[j];
						if(menu1.menu_level == 2 && menu1.super_id == menu0.menu_id){
							if(menu1.isnext == '1'){
								html += '<li class="openable">';
							}else{
								html += '<li>';
							}
							html += '<a menu_id="'+menu1.menu_id+'"  href="#" url="'+(menu1.menu_url!=null?menu1.menu_url:'#')+'"><span class="submenu-label">'+menu1.menu_name+'</span>';
							if(menu1.isnext == 1){
								html += '<span class="submenu-icon"></span>';
							}
							html += '</a>';
							if(menu1.isnext == 1){
								html += '<ul class="submenu third-level">';
								for(var b=0;b<size;b++){
									var menu2 = menu[b];
									if(menu2.menu_level == 3 && menu2.super_id == menu1.menu_id){
										if(menu2.isnext == '1'){
											html += '<li class="openable">';
										}else{
											html += '<li>';
										}
										html += '<a  menu_id="'+menu2.menu_id+'" href="#" url="'+(menu2.menu_url!=null?menu1.menu_url:'#')+'"><span class="submenu-label">'+menu2.menu_name+'</span>';
										if(menu2.isnext == 1){
											html += '<span class="submenu-icon"></span>';
										}
										html += '</a></li>';
									}
								}
								html += '</ul>';
							}else{
								html += '</li>';
							}
						}
					}
					html += '</ul>';
				}else{
					html += '</li>';
				}
			}
		}
		$('.accordion').append(html);
		//加载对应事件信息
		index.loadMenuEventNew();
	},
    /**
     * 加载菜单处理事件
     */
    loadMenuEventNew:function(){ 
    	$('.table_menu').remove();
    	$('.sidebar-menu .openable  a').click(function()	{
    		if(!$('aside').hasClass('sidebar-mini')||$('.sidebar-menu.sidebar-mini').width() == 240)	{
    			var h = $(this).attr('url');
    			if(h != '#'){
    				index.openMenuNew(this,h);
    			}
    			if( $(this).parent().children('.submenu').is(':hidden') ) {
    				$(this).parent().siblings().removeClass('open').children('.submenu').slideUp(200);
    				$(this).parent().addClass('open').children('.submenu').slideDown(200);
    			} 
    			else{
    				$(this).parent().removeClass('open').children('.submenu').slideUp(200);
    			}
    		}else{
    			var h = $(this).attr('url');
    			if(h != '#'){
    				index.openMenuNew(this,h);
    			}
    		}
    		return false;
    	});

    	//Open active menu
    	if(!$('.sidebar-menu').hasClass('sidebar-mini'))	{
    		$('.openable.open').children('.submenu').slideDown(200);
    	}
    },
    /**
     * 记录打开的tab数量 记录格式 [id,url]
     */
    loadmenuObj:null,
    //打开菜单操作
 	openMenuNew:function(obj,url,isnext,loadfun){
 		$('.table_menu').remove();
		if(!index.changeFun()){
			return ;
		}
		common.load.load('菜单打开中');
		index.addStateInfo('');
		$('.sticky-header').data('menuurl',url);
		//菜单打开后进行颜色标注
		$('.sidebar-menu li').removeClass('active');
		$(obj).parents('li').addClass('active');
		
		//清除时间控件插件
		$('.datetimepicker').remove();
		$('.daterangepicker').remove();
		var menuid = $(obj).attr('menu_id');
		if( url == null||url.trim() == '' ){
			common.load.hide('',"打开成功");
			return;
		}
 		if(url != '' && url != null){
 			common.ajax({
			 	url:"/"+url,
			 	dataType:'html',
			 	loadfun:function(isloadsucc, data){
					if(isloadsucc){
						common.load.hide('',"打开成功");
						$("html").getNiceScroll(0).doScrollTop(0, 0);
						//获取上级对应的
						//先进行清空table实例操作
						//table.obj = [];
						$('.wrapper').html(data);
						if(obj == null){//首次进入，直接显示首页
							$('.homehead_ .breadcrumb').html("<li class='active' >银城千万间后台管理系统</li>");
						}else{//否则获取对应的菜单级别
							//alert($(obj).find('span').text());
							var now1 = $(obj).find('span').text();
							if(window.location.host.indexOf('localhost')==0){
								now1 += '--'+url;
							}
							var now2 = $(obj).parent().parent().parent().find('a').first().find('.text').text();
//							var now3 = $(obj).parent().parent().parent().parent().parent().find('a').first().find('.text').text();
							var html = "";
//							if(now3 != ''){
//								html += '<li>'+now3+'</li>';
//							}
							if ($(obj).parents('.submenu').length <= 0) {
								now1 = '--'+url;
								now2 = $(obj).parent().find('a').first().find('.text').text();
							}
							if(now2 != ''){
								html += '<li>'+now2+'</li>';
							}
							if(now1 != ''){
								html += '<li class="active">'+now1+'</li>';
							} 
							$('.sticky-header').data('menutitle',html);
							$('.homehead_ .breadcrumb').html(html);
							index.openobj = url;
							index.menu_id = menuid;
						}
						//菜单加载完成后执行的方法
						if(typeof(loadfun) == 'function'){
							loadfun();
						}
					}else{
						common.load.hide('zoomOut',"打开失败");
						common.alert({
							msg:'页面加载失败，错误信息：'+data,
							fun:function(){
								common.load.hide('zoomOut');
							}
						});	
						index.loadMenuFun = null;
					}
					//index.removeTable();
				}
			 });
 		}
 	},
    //处理菜单信息
	loadMenuEvent:function(){
		//菜单的操作信息
		 $('.dropdown').tendina({
	        animate: true,
	        speed: 500,
	        openCallback: function($clickedEl) {
	          var a = $($clickedEl).find('a').first();
	          if(a.hasClass('more')){
	          	a.attr('style','background-image:url(/html/adminX/images/minus.png);');
	          }
	        },
	        closeCallback: function($clickedEl) {
	        	 var a = $($clickedEl).find('a').first();
		         if(a.hasClass('more')){
		         	a.attr('style','background-image:url(/html/adminX/images/plus-white.png);');
		         }
	        }
	      });
		  $('.dropdown').attr('style','opacity：1');
	},
	/**
	 * 给查询按钮添加处理事件
	 */ 
	loadSearchEvent:function(){
		$('.yc_seach').live('click',function(){
			var tableId = $(this).attr('table');
			var tableobj = $("#"+tableId).data('table');
			table.addkRedraw(tableId);
			tableobj.fnDraw();
		});
		
		$('.yc_seach').parents('.panel-seach').find('input').live('keydown keyup',function(event){
			//event.preventDefault();
			if (event.which == "13"&&event.type=='keyup') {
				common.log('11111');
    			// var focusActId = document.activeElement.id;获取焦点ID，根据ID判断执行事件
				$('.wrapper .yc_seach').click();
    			return false;// 禁用回车事件
    		}
		});
	},
	/**
	 * 退出
	 */
	loginout:function(){
		common.alert({
			msg:'确定要退出吗？',
			confirm:true,
			fun:function(action){
				if(action){
					common.load.load('正在登出中...');
					common.ajax({
					 	url:common.root+'/caas/checklogin/loginOut',
						dataType: 'json',					
						loadfun: function(isloadsucc, data){
							if(isloadsucc){ 
								if(data == 1){
									common.alert({
										msg:'感谢您的使用，下次再见。',
										fun:function(){
											window.location.href='/html/loginNew.html';
										}
									});
								}
							}else{
								common.alert({
									msg:common.msg.error
								});
							}
						}
					});
				}
			}
		});
	},
	/**
	 * 时钟
	 */
	loadTime:function(){
		setInterval(function(){
			var now = new Date(); //当前日期 
			var nowDay = now.getDate(); //当前日  
			var nowMonth = now.getMonth(); //当前月  
			var nowYear = now.getFullYear(); //当前年  
			var nowDayOfWeek = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[now.getDay()];; //今天本周的第几天  
			var hours = now.getHours(); //获取系统时，
			var minutes = now.getMinutes(); //分
			var seconds = now.getSeconds(); //秒
			var titme = nowYear+'年'+(nowMonth+1)+"月"+nowDay+"日  "+(nowDayOfWeek)+"  "+hours+":"+minutes+":"+seconds;
			$('.index_time').html(titme);
		},1000);
	},
	//加载用户数据信息
	loadUser:function(){
		var this_ = this;
		common.ajax({
		 	url:common.root+'/caas/checklogin/loadUserMenu',
			dataType: 'json',
			cache:true,
			loadfun: function(isloadsucc, data){
				if(isloadsucc){
					if(data.state == '-2' || data.appserverCode == '-1'){
						common.alert({
							msg:'您还未登录，请先登录。',
							fun:function(){
								window.location.href='/html/login.html';
							}
						});
					}else if(data.state == 1){
						this_.user = data.user;
						this_.setUser(this_);
						//设置菜单
						this_.loadMenuNewSim(data.menu);
						common.load.hide('','加载成功');
						index.openMenuNew(null,'html/home.html');
					}else{
						common.alert({ 
							msg:common.msg.error
						});
					}
				}else{
					common.alert({
						msg:common.msg.error
					});
				}
			}
		 });
	},
	setUser:function(this_){
		$('.index_user').text(this_.user.userName);
		$('#userId').val(this_.user.userId);
	},
	loadMenuNew:function(menu){
		var size = menu.length;
		var html = "";
		//进行循环操作
		for(var i=0;i<size;i++){
			var menu0 = menu[i];
			if(menu0.menu_level == 1){//1级菜单
				if(menu0.menu_ico == undefined||menu0.menu_ico == ''){
					menu0.menu_ico = 'fa-file-text-o';
				}
				html += '<li><a menu_id="'+menu0.menu_id+'" class="'+(menu0.isnext=='1'?'more':'')+'" '+(menu0.menu_url != null?'onclick="index.openMenu(this,\''+menu0.menu_url+'\');return false;"':'')+'  href="#"><i class="fa '+(menu0.menu_ico)+'"></i><span>'+menu0.menu_name+'</span></a>';
				if(menu0.isnext != '1'){
					html += '</li>';
				}else{//存在下级 二级
				    html += '<ul>';
				    for(var j=0;j<size;j++){
				    	var menu1 = menu[j];
				    	if(menu1.menu_level == 2 && menu1.super_id == menu0.menu_id){
							if(menu1.menu_ico == undefined||menu1.menu_ico == ''){
								menu1.menu_ico = 'fa-file-text-o';
							}
					    	html += '<li><a menu_id="'+menu1.menu_id+'" class="'+(menu1.isnext=='1'?'more':'')+'" '+(menu1.menu_url != null?'onclick="index.openMenu(this,\''+menu1.menu_url+'\');return false;"':'')+'  href="#"><i class="fa fa-null"></i><i class="fa '+(menu1.menu_ico)+'"></i><span>'+menu1.menu_name+'</span></a>';
					    	if(menu1.isnext != '1'){
					    		html += '</li>';
					    	}else{//是否存在下级
					    		html += '<ul>';
					    		for(var b=0;b<size;b++){
					    			var menu2 = menu[b];
					    			if(menu2.menu_level == 3 && menu2.super_id == menu1.menu_id){
										if(menu2.menu_ico == undefined||menu2.menu_ico == ''){
											menu2.menu_ico = 'fa-file-text-o';
										}
					    				html += '<li><a menu_id="'+menu2.menu_id+'" class="'+(menu2.isnext=='1'?'more':'')+'" '+(menu2.menu_url != null?'onclick="index.openMenu(this,\''+menu2.menu_url+'\');return false;"':'')+'  href="#"><i class="fa fa-null"></i><i class="fa fa-null"></i><i class="fa '+(menu2.menu_ico)+'"></i><span>'+menu2.menu_name+'</span></a>';
								    	if(menu2.isnext != '1'){
								    		html += '</li>';
								    	}
					    			}
					    		}
					    		html += '</ul>';
					    	}
				    	} 
				    }
				    html += '</ul>';
				}
			}
		}
		$('.dropdown').append(html);
		//加载对应事件信息
		index.loadMenuEvent();
	},
	/**
	 * 密码修改弹框
	 */
	modifyPass:function(){
		$('.modify_pass').click(function(){
			common.openWindow({
			  name:'modifypassNew',
			  type: 1, 
			  url: common.root+'/html/modifypassNew.html'
			});
		});
	},
	/**
	 * 进行移除菜单项
	 * 已没有作用
	 */
	removeTable:function(){
		if(table.obj.length == 0){
			return;
		}
		for(var i=0;i<table.obj.length;i++){
			/*if(table.obj[i][0] == name){
				table.obj.splice(i, 1);
			}*/
			var tableid = $(table.obj[i][0]).attr('id');
			//检查是否存在此id值，如果不存在就进行移除
			if($('#'+tableid).size() == 0){
				table.obj.splice(i, 1);
			}
		}
	},
	/**
	 * 当前打开的菜单信息
	 * @param {Object} obj
	 * @param {Object} url
	 * @param {Object} isnext
	 */
	openobj:null,
	menu_id:'',
 	//打开菜单操作
 	openMenu:function(obj,url,isnext){
		if(!index.changeFun()){
			return ;
		}
		common.load.load('菜单打开中');
		index.addStateInfo('');
		$('.sticky-header').data('menuurl',url);
		//菜单打开后进行颜色标注
		$('.dropdown li').removeClass('open_menu');
		$(obj).parent().addClass('open_menu');
		
		//清除时间控件插件
		$('.datetimepicker').remove();
		$('.daterangepicker').remove();
		if(isnext == '1'){//存在下级的同时打开下级菜单
			
		}
		var menuid = $(obj).attr('menu_id');
 		if(url != '' && url != null){
 			common.ajax({
			 	url:"/"+url,
			 	dataType:'html',
			 	loadfun:function(isloadsucc, data){
					if(isloadsucc){
						common.load.hide('',"打开成功");
						$("html").getNiceScroll(0).doScrollTop(0, 0);
						//获取上级对应的
						//先进行清空table实例操作
						//table.obj = [];
						$('.wrapper').html(data);
						if(obj == null){//首次进入，直接显示首页
							$('.homehead_ .breadcrumb').html("<li class='active' >银城千万间后台管理系统</li>");
						}else{//否则获取对应的菜单级别
							//alert($(obj).find('span').text());
							var now1 = $(obj).find('span').text();
							if(window.location.host.indexOf('localhost')==0){
								now1 += '--'+url;
							}
							var now2 = $(obj).parent().parent().parent().find('a').first().find('span').text()
							var now3 = $(obj).parent().parent().parent().parent().parent().find('a').first().find('span').text();
							var html = "";
							if(now3 != ''){
								html += '<li>'+now3+'</li>';
							}
							if(now2 != ''){
								html += '<li>'+now2+'</li>';
							}
							if(now1 != ''){
								html += '<li class="active">'+now1+'</li>';
							}
							$('.sticky-header').data('menutitle',html);
							$('.homehead_ .breadcrumb').html(html);
							index.openobj = url;
							index.menu_id = menuid;
						}
					}else{
						common.load.hide('zoomOut',"打开失败");
						common.alert({
							msg:'页面加载失败，错误信息：'+data,
							fun:function(){
								common.load.hide('zoomOut');
							}
						});						
					}
					//index.removeTable();
				}
			 });
 		}
 	},
	/**
	 * 在顶部
	 * 添加对应状态信息
	 * @param {Object} html
	 */
	addStateInfo:function(html){
		$('.homehead_ .panelinfo_').html(html);
	}
 }
 $(function(){
 	index.init();
 });
 
