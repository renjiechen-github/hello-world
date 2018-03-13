/**
 * 公共信息内容
 */
var common = {
    //返回如：http://localhost/形式
    hash: window.location.protocol + "//" + window.location.host + "/",
    root: "",
    webroot: window.location.protocol + "//" + window.location.host + window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1),
    init: function () {
        if (window.location.host.indexOf('localhost') == 0 || window.location.host.indexOf('192.168.191.1')) {
            //common.root = window.document.location.pathname.substring(0, window.document.location.pathname.substr(1).indexOf('/') + 1);
            common.root = "";
        } else {
            common.root = "";
        }

//		alert(common.mark({
//			type:2,
//			data:1212343,
//			color:'red',
//			offset:1,
//			offsetTime:'2016-10-13'
//		}));
    },
    /**
     * 测试环境下使用
     * @param p 标示符号 传入：?/&
     */
    math: function (b) {
        if (window.location.host.indexOf('localhost') == 0 || window.location.host.indexOf('192.168.191.1')) {
            return b + "1=" + Math.random();
        } else {
            return "";
        }
    },
    error: function (msg) {
        console.error(msg);
    },
    log: function (msg) {
        console.log(msg);
    },
    // 设置cookie（不兼容ie，兼容ie需要设置domain为ip或域名）
    setCookie: function(name, value, expiry, path, domain, secure) {
        var nameString = "rm-" + name + "=" + value;
        var expiryString = "";
        if (expiry != null) {
            if (expiry) {
                var lsd = new Date();
                lsd.setTime(lsd.getTime() + expiry * 1000);
                expiryString = "; expires=" + lsd.toGMTString();
            }
        } else {
            var lsd = new Date();
            lsd.setTime(lsd.getTime() + 365 * 24 * 60 * 60 * 1000);
            expiryString = "; expires=" + lsd.toGMTString();
        }
        var pathString = (path == null) ? ";path=/" : ";path=" + path;
        var domainString = (domain == null) ? "" : " ;domain=" + domain;
        var secureString = (secure) ? ";secure=" : "";
        document.cookie = nameString + expiryString + pathString + domainString + secureString;
    },
    getCookie: function(name) {
        var i, aname, value, ARRcookies = document.cookie.split(";");
        for (i = 0; i < ARRcookies.length; i++) {
            aname = ARRcookies[i].substr(0, ARRcookies[i].indexOf("="));
            value = ARRcookies[i].substr(ARRcookies[i].indexOf("=") + 1);
            aname = aname.replace(/^\s+|\s+$/g, "");
            if (aname == "rm-" + name) {
                return (value);
            }
        }
        return '';
    },    
    /**
     * 用来判断是否被包含
     */
    window: top.location != location ? window.parent : window,
    //所有消息接口信息
    msg: {
        error: '系统内部错误，请联系管理员',
        usererror: '用户信息失效，即将重新获取您的微信信息',
        codeerror: '验证码已失效',
        succ: '操作成功',
        develop: '该功能正在开发中,敬请期待.',
        reg: {
            regSucc: '注册成功',
            bdMsg: '您已经绑定了哦，请不要进行重复绑定操作'
        },
        unbind: {
            unbindTip: '确定要解除绑定关系吗？'
        },
        pay: {
            error: '支付生成失败，请重新操作。',
            nodd: '没有查询到该订单'
        }
    },
    /**
     * 根据相关参数值返回对应重新生成的标记
     * 
     * @param opt
     */
    mark: function (opt) {
        var def = {
            type: 1, //返回类型，1 格式化成金额如：￥222.00  形式  2 
            data: null, //传入的数据值 
            offset: 0, //偏移值  跟当前时间进行对比的偏移值  偏移的值与offsetTime对比，如果超过此时间就标红参数值 偏移值针对day进行偏移
            color: 'red', //达到偏移值后的颜色形式      默认红色
            offsetTime: null//偏移对比时间 格式 2015-02-03 
        };
        jQuery.extend(def, opt);
        def.data = def.data + "";
        var data = '';
        if ($.trim(def.data) == null || $.trim(def.data) == 'null' || $.trim(def.data) == '') {
            return "";
        }

        if (def.type == 1) {//格式化金额
            //检查参数中是否包含.
            var e = def.data.indexOf('.');
            if (e > 0) {
                data = "￥" + def.data;
            } else {
                data += "￥" + def.data + ".00";
                //data += "￥"+def.data.substr(0,def.data.length -2)+"."+def.data.substr(def.data.length -2);
            }
        } else {
            data = def.data;
        }
        if (def.offsetTime != null) {
            var offsetTime = (def.offsetTime.replace('-', '').replace('-', '')).substr(0, 8);
            var offsetDate = common.addDate(new Date(), def.offset);

            if (parseInt(offsetDate) >= parseInt(offsetTime)) {
                return "<b style='color:" + def.color + "' >" + data + "</b>";
            }

        }
        return data;
    },
    addDate: function (date, days) {
        var d = new Date(date);
        d.setDate(d.getDate() + days);
        var month = d.getMonth() + 1;
        var day = d.getDate();
        if (month < 10) {
            month = "0" + month;
        }
        if (day < 10) {
            day = "0" + day;
        }
        var val = d.getFullYear() + "" + month + "" + day;
        return val;
    },
    /**
     * 添加收藏夹
     */
    addFavorite: function (title) {
        var url = window.location;
        //var title = document.title;
        var ua = navigator.userAgent.toLowerCase();
        if (ua.indexOf("360se") > -1) {
            common.alert({msg: "由于360浏览器功能限制，请按 Ctrl+D 手动收藏！"});
        } else if (ua.indexOf("msie 8") > -1) {
            window.external.AddToFavoritesBar(url, title); //IE8
        } else if (document.all) {
            try {
                window.external.addFavorite(url, title);
            } catch (e) {
                common.alert({msg: '您的浏览器不支持,请按 Ctrl+D 手动收藏!'});
            }
        } else if (window.sidebar) {
            window.sidebar.addPanel(title, url, "");
        } else {
            common.alert({msg: '您的浏览器不支持,请按 Ctrl+D 手动收藏!'});
        }
    },
    toDesktop: function (sUrl, sName) {
        try {
            var WshShell = new ActiveXObject("WScript.Shell");
            var oUrlLink = WshShell.CreateShortcut(WshShell.SpecialFolders("Desktop") + "\\" + sName + ".url");
            oUrlLink.TargetPath = sUrl;
            oUrlLink.Save();
        } catch (e) {
            alert("当前IE安全级别不允许操作！");
        }
    },
    /**
     * 页面跳转，会自动添加验证信息,传入的链接请不要带上../../直接已跟路径webroot下面的文件夹开始写路径
     * 
     * @param url
     * @param type 1 需要进行验证操作验证获取用户信息成功后可进行访问页面信息 2无需进行验证操作直接进行页面跳转
     */
    href: function (url, type) {
        //此项会统一在跳转链接后面添加上对应的server_id
        var tzurl = url;
        if (url.indexOf('http') < 0) {
            tzurl = common.hash + common.root.substr(1) + url;
        }

        window.location.href = tzurl;
    },
    /**
     * 去除code的url链接 页面链接带有东西
     * 
     * @returns
     */
    getUrl: function () {
        var url = window.location.href;
        if (url.indexOf('code') != -1) {
            url = url.substr(0, url.indexOf('code') - 1);
        }
        return url;
    },
    /**
     * 获取连接url参数
     * @param {} name 参数名称
     * @return {String}
     */
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)
            return  unescape(r[2]);
        return "";
    },
    /**
     * 检查参数值是否正确,已不再使用
     */
    checkCode: function (fwoperid) {
        var url = window.location.href;
        var codeIndex = url.indexOf('code');
        url = url.substr(0, codeIndex == -1 ? url.length : codeIndex - 1);
        if (fwoperid == '' || fwoperid == null || fwoperid == undefined) {
            //需要跳转重新获取链接
            window.location.href = wxShare.getShareUrl(url, '');
        }
//		//检查是否包含code,如果不包含需要重新跳转获取值
//		var code = getQueryString('code');
//		
    },
    /**
     * 检查是否已经登录
     */
    checkLogin: function (type) {
        common.ajax({
            url: common.hash + common.root + '/oauth2/checkLogin.do',
            data: {
                server_id: common.getQueryString('server_id')
            },
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data == '-1') {
                        if (type != 'snsapi_base') {
                            type = 'snsapi_userinfo';
                        }
                        window.location.href = wxShare.getShareUrl(window.location.href, type);
                    }
                }
            }
        });
    },
    /**
     * 添加历史信息
     */
    addHis: function (opt) {
        var def = {
            data: {},
            title: '',
            url: ''
        }
        jQuery.extend(def, opt);
        if (def.url != '') {
            history.pushState(def.data, def.title, def.url);
        }
    },
    /**
     * 移除历史信息
     */
    rmHis: function () {

    },
    /**
     * 判断json数据是否为空，
     * @param {Object} data json对象 主要判断json对象是否是{}
     * @return true为空 false不为空
     */
    isJsomEmpty: function (data) {
        try {
            for (var key in data) {
                return false;
            }
        } catch (e) {
            return false;
        }
        return true;
    },
    /**
     * 验证参数是否为空
     */
    isEmpty: function (val) {
        if (val == undefined || val == '' || val == null || val == 'null') {
            return true;
        }
        return false;
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
    ajax: function (opt) {
        var def = {
            url: '',
            dataType: 'text', //'json'|'text'|'jsonp'
            async: true,
            type: 'post', //post
            timeout: 100000, //超时时间
            loadfun: function (isloadsucc, data) {
            }, //判断是否加载成功，isloadsucc为true表示加载成功，否则表示失败
            data: {}, //数据
            delay: 0, //延迟多长时间在加载
            encode: true, //是否进行自动编码，自动将中文转码
            cache: false,
            contentType: 'application/x-www-form-urlencoded; charset=UTF-8'
        };
        jQuery.extend(def, opt);
        if (def.encode) {
            //讲传入参数值进行编码处理
            if (typeof (def.data) != 'string' && def.data != null && def.data != undefined) {
                for (var p in def.data) {
                    var value = def.data[p];
                    def.data[p] = encodeURIComponent(value);
                }
            } else {
                var value = decodeURIComponent(def.data, true);
                def.data = encodeURI(encodeURI(value));
            }
        }
        if (def.delay != 0 && typeof (def.delay) == 'number') {
            setTimeout(function () {
                $.ajax({
                    type: def.type,
                    url: def.url,
                    data: def.data,
                    dataType: def.dataType,
                    cache: def.cache,
                    async: def.async,
                    contentType: def.contentType,
                    timeout: def.timeout, //超时时间
                    success: function (data) {
                        try {
                            if (data.state == '-99999') {
                                common.alert({
                                    msg: '您还未登陆或登陆已经失效，请登录。',
                                    fun: function () {
                                        window.location.href = 'login.html';
                                    }
                                });
                                return;
                            } else if (data.state == '-99998') {
                                common.alert({
                                    msg: '很抱歉，您没有权限进行此操作。'
                                });
                                return;
                            } else if (data.state == '-99997') {
                                common.alert({
                                    msg: '很抱歉，您已在其他地方登陆了系统，如不是本人操作可重新登陆修改密码。',
                                    fun: function () {
                                        window.location.href = 'login.html';
                                    }
                                });
                                return;
                            }
                            def.loadfun(true, data)
                        } catch (e) {
                            console.log(e);
                            def.loadfun(false, e)
                        }
                    },
                    error: function (e,data) {
                    	console.log(e);
                        console.log(data);
                        def.loadfun(false, data)
                    }
                });
            }, def.delay);
        } else {

            $.ajax({
                type: def.type,
                url: def.url,
                data: def.data,
                dataType: def.dataType,
                contentType: def.contentType,
                cache: def.cache,
                async: def.async,
                timeout: def.timeout, //超时时间
                success: function (data) {
                    // try {
                        if (data.state == '-99999') {
                            common.alert({
                                msg: '您还未登陆或登陆已经失效，请登录。',
                                fun: function () {
                                    window.location.href = 'login.html';
                                }
                            });
                            return;
                        } else if (data.state == '-99998') {
                            common.alert({
                                msg: '很抱歉，您没有权限进行此操作。'
                            });
                            return;
                        } else if (data.state == '-99997') {
                            common.alert({
                                msg: '很抱歉，您已在其他地方登陆了系统，如不是本人操作可重新登陆修改密码。',
                                fun: function () {
                                    window.location.href = 'login.html';
                                }
                            });
                            return;
                        }
                        def.loadfun(true, data);
                    // } catch (e) {
                    //     console.log("aa:" + def.url);
                    //     console.error(e);
                    //     def.loadfun(false, e);
                    // }
                },
                error: function (data) {
                    console.error(data);
                    def.loadfun(false, data);
                }
            });
        }
    },
    /**
     * 隐藏对话框
     */
    alertHide: function () {
        if (this.alertObj != null) {
            this.alertObj.close();
        }
    },
    /**
     * 关闭页面
     */
    cloasWindow: function () {
        window.close();
    },
    /**
     * 顶部提示框
     * @param {Object} opt
     */
    tip: function (opt) {
        var def = {
            msg: '',
            type: false, //消息类型：true为正确 false为错误
            time: 3000, //消息显示多长时间
            callback: function () {}//消失之后的回调函数
        }
        jQuery.extend(def, opt);
        $.tooltip(def.msg, def.time, def.type, def.callback);
    },
    /**
     * 选择人员的时候使用到的对象信息
     * @param {Object} opt
     */
    userobj: null,
    /**
     * 选择人员信息
     * @param {Object} opt
     */
    openUser: function (opt) {
        var def = {
            title: '人员选择',
            /**
             * 可被查询到哪些人员信息，放入的是组织编号id 以逗号隔开，如：1,2,3
             */
            orgids: '',
            /**
             * 可呗查询到哪些人员信息，放入的是角色编号id
             */
            roles: '',
            /**
             * 是否是单项选择，如果是单选选择就只能选择一项内容，否则可以进行多选，针对单选没有对应
             * 单项选择没有确定按钮，否则存在确定按钮
             */
            issingle: true,
            /**
             * 选择成功后返回的操作信息
             * @param data 返回json数据信息，如果为空就是没有选择
             */
            succee: function (data) {

            },
            /**
             * 关闭窗口
             */
            cancel: function () {

            }
        };
        jQuery.extend(def, opt);
        common.userobj = def;
        var buts = {};
        if (!def.issingle) {
            buts = {
                btn: ['确定选择'],
                btn1: function () {
                    var data = $("#_usercommon_").data('multipledata');
                    def.succee(data);
                    common.closeWindow('__userwindow__');
                }
            };
        }
        common.openWindow({
            id: 'layerframe__userwindow__',
            name: '__userwindow__',
            title: def.title,
            type: 1,
            area: ['500px', '630px'],
            url: '/html/pages/common/user.html',
            success: function (layero, index, id1) {
                $("#" + id1).data('succee', def.succee);
            },
            cancel: function () {
                def.cancel();
                table.obj.pop();
            },
            buts: buts
        });
    },
    windowobj: [],
    /**
     * 根据窗口名称获取对象
     * @param {Object} name
     */
    getWindow: function (name) {
        if (common.windowobj.length != 0) {
            for (var i = 0; i < common.windowobj.length; i++) {
                if (common.windowobj[i][0] == name) {
                    return common.windowobj[i][1];
                }
            }
        } else {
            return "";
        }
    },
    // 验证手机号是否可用
    validMobile: function(mobile) {
        var reg = /^1[0-9]{10}$/;
        return reg.test(mobile);
    },
    /**
     * 关闭窗口
     * @param {Object} name
     */
    closeWindow: function (name, type) {

        if (type == 3) {
            var url = "/" + $('.sticky-header').data('menuurl');
            common.load.load('加载中');
//			if(common.originalWindowData != null){
//				$('.homehead_ .breadcrumb').html($('.sticky-header').data('menutitle'));
//				$('.sticky-header').removeData('data'+name);
//				index.addStateInfo('');
//				$('.wrapper').replaceWith(common.originalWindowData);	
//				var tableId = $('.yc_seach').attr('table')
//				common.load.hide();
//			}else{
            common.ajax({
                url: url,
                dataType: 'html',
                loadfun: function (isloadsucc, data) {
                    common.load.hide();
                    if (isloadsucc) {
                        $('.wrapper').html(data);
                        $('.homehead_ .breadcrumb').html($('.sticky-header').data('menutitle'));
                        $('.sticky-header').removeData('data' + name);
                        index.addStateInfo('');

                        if (common.originalWindowData.length != 0) {
                            $('.wrapper .panel-seach').each(function (i, n) {
                                for (var j = 0; j < common.originalWindowData.length; j++) {
                                    if (i == common.originalWindowData[j].id) {
                                        var data = common.originalWindowData[i].data;
                                        $(this).replaceWith(common.originalWindowData[i].data);
                                        //$(data).find('.yc_seach').click();
                                    }
                                }
                            });
                            $('.wrapper .panel-seach .yc_seach').click();
                        }
                    } else {
                        common.alert({
                            msg: '页面加载失败，错误信息：' + data,
                            fun: function () {
                                //common.load.hide('zoomOut');
                            }
                        });
                    }
                }
            });
            common.load.hide();
//			}
        } else {
            layer.close(common.getWindow(name));
            if (common.windowobj.length != 0) {
                for (var i = 0; i < common.windowobj.length; i++) {
                    if (common.windowobj[i][0] == name) {
                        $('.sticky-header').removeData('data' + name);
                        common.windowobj.splice(i, 1);
                    }
                }
            }
        }
    },
    /**
     * 获取窗口数据传递信息
     * @param name 窗口的名称
     */
    getWindowsData: function (name) {
        //console.log($('.sticky-header').data('data'+name));
        return $('.sticky-header').data('data' + name);
    },
    /**
     * 窗口覆盖的原始内容
     */
    originalWindowData: [],
    /**
     * 打开窗口
     */
    openWindow: function (opt) {
        var def = {
            name: '', //窗口对应的编号名称，窗口名称保持不一样
            id: '', //可手动指定id值，如果没有指定就会随机
            title: '新窗口',
            /**
             * 2 为以iframe加载内容  1 为直接在页面中加载对话框  3当前页面之间进行切换
             */
            type: 2,
            maxmin: true, //是否显示最大化最小化
            data: {},
            /**
             * 初始化对于的宽与高 ['10px','10px']
             */
            area: null,
            url: '',
            /**
             * 内容
             * @param {Object} layero
             * @param {Object} index
             */
            content: '',
            /**
             * 弹出层加载完成后
             * @param {Object} layero
             * @param {Object} index
             */
            success: function (layero, index, id) {

            },
            /**
             * 关闭弹出层的调用处理事件
             * @param {Object} index
             */
            cancel: function (index) {

            },
            /**
             * 遮罩层显示透明度，为0就是不显示
             */
            shade: 0.5,
            /**
             * 添加按钮信息如：{btn：[‘11’，‘21’],btn1：function(){},btn2：function(){},}
             */
            buts: null
        };
        jQuery.extend(def, opt);
        if (def.id == '') {
            def.id = 'layerframe' + parseInt(Math.random() * 100000);
        }
        //var id = 'layerframe'+parseInt(Math.random()*100000);
        if (def.area == null) {
            var width = $(window).width();
            var height = $(window).height();
            def.area = [width / 2 + 'px', height / 2 + 'px'];
        }
        if (def.type == 2) {
            def.content = def.url;
        }
        if (def.name == '' || def.name == undefined) {
            alert('打开窗口函数参数缺失：请指定参数name,该参数必填，改参数是打开窗口的一个标示，确保全局唯一');
            return;
        }
        //先关闭
        common.closeWindow(def.name);
        $('.sticky-header').data('data' + def.name, def.data);
        //console.log("--"+$('.sticky-header').data('data'+def.name));
        var cancel = function (index) {
            $('.sticky-header').removeData('data' + def.name);
            def.cancel(index);
            //console.log(def.name);
        }
        if (def.type == 3) {
            common.load.load('加载中');
            common.ajax({
                url: def.url,
                dataType: 'html',
                loadfun: function (isloadsucc, data) {
                    common.load.hide();
                    if (isloadsucc) {
                        table.obj = [];
                        $('.homehead_ .breadcrumb').html("<li class='active' >" + def.title + "</li>");
                        index.addStateInfo('<button type="submit" id="__return_bnt__" class="btn btn-primary"><i class="fa fa-mail-reply"></i>返回</button>');
                        $('#__return_bnt__').click(function () {
                            common.closeWindow(def.name, 3);
                        });

                        common.originalWindowData = [];
                        $('.wrapper .panel-seach').each(function (i, n) {
                            //common.log(i);
                            common.originalWindowData.push({id: i, data: $(this).clone(true)});
                        });
                        //common.originalWindowData = $('.wrapper .panel-seach').clone(true);
                        common.log("------------------------------");
                        common.log(common.originalWindowData);
                        common.log("------------------------------");
                        $('.wrapper').html(data);
                        $('input[title]').focus(function (o) {
                            var title = $(this).attr('title');
                            layer.tips(title, this, {
                                tips: [1, '#d9534f'] //还可配置颜色
                            });
                        });
                    } else {
                        common.alert({
                            msg: '页面加载失败，错误信息：' + data,
                            fun: function () {
                                //common.load.hide('zoomOut');
                            }
                        });
                    }
                }
            });
        } else {
            var but = [];
            if (window.location.host.indexOf('localhost') == 0) {
                but = ['刷新页面'];
            }
            //默认按钮配置信息
            var buts = {
                btn: but,
                btn1: function () {
                    if (window.location.host.indexOf('localhost') == 0 || window.location.host.indexOf('192.168.191.1')) {
                        if (def.type == 1) {
                            layer.load();
                            //加载页面内容
                            common.ajax({
                                url: def.url,
                                dataType: 'html',
                                loadfun: function (isloadsucc, data) {
                                    if (isloadsucc) {
                                        $('#' + def.id).html(data);
                                        layer.closeAll('loading');
                                    } else {
                                        common.alert({
                                            msg: common.msg.error
                                        });
                                        layer.closeAll('loading');
                                    }
                                }
                            });
                        } else {
                            $('#' + def.id + ' iframe').attr('src', def.url);
                            layer.iframeAuto(layerindex);
                        }
                    }
                }
            };
            if (def.buts != null) {
                buts = def.buts;
            }
            var opts = {
                id: def.id,
                title: def.title,
                type: def.type,
                maxmin: def.maxmin,
                area: def.area,
                content: def.content,
                success: function (layero, index) {
                    if (def.type == 1 && def.url != '') {
                        layer.load();
                        //加载页面内容
                        common.ajax({
                            url: def.url,
                            dataType: 'html',
                            loadfun: function (isloadsucc, data) {
                                if (isloadsucc) {
                                    $('#' + def.id).html(data);
                                    layer.closeAll('loading');
                                    $('input[title]').focus(function (o) {
                                        var title = $(this).attr('title');
                                        layer.tips(title, this, {
                                            tips: [1, '#d9534f'] //还可配置颜色
                                        });
                                    });
                                } else {
                                    common.alert({
                                        msg: common.msg.error
                                    });
                                    layer.closeAll('loading');
                                }
                                def.success(layero, index, def.id);
                            }
                        });
                    } else {
                        $('input[title]').focus(function (o) {
                            var title = $(this).attr('title');
                            layer.tips(title, this, {
                                tips: [1, '#d9534f'] //还可配置颜色
                            });
                        });
                        def.success(layero, index, def.id);
                    }
                },
                cancel: cancel,
                shade: def.shade
            };
            jQuery.extend(opts, buts);
            var layerindex = layer.open(opts);
            var win = new Array();
            win[0] = def.name;
            win[1] = layerindex;
            common.windowobj.push(win);
        }
    },
    alertObj: null,
    /**
     * 弹框
     * @param {Object} opt
     */
    alert: function (opt) {
        var def = {
            title: '系统提示',
            msg: '',
            /**
             * 是否是确定对话框模式 true 是 false 否
             * @param {Object} action
             */
            confirm: false,
            /**
             * 对话框出现的动画
             * 
             * @param {Object} action
             */
            animate: 'bounceIn',
            titleStyle: '',
            confirmButton: '确定',
            cancelButton: '取消',
            closeIcon: false,
            fun: function (action) {return true},
            onIconClose: function () {}
        };
        if (typeof (opt) == 'string') {
            def.msg = opt;
        } else {
            jQuery.extend(def, opt);
        }
        if (this.alertObj != null) {
            this.alertObj.close();
        }
        if (def.confirm) {//确定对话框
            this.alertObj = $.confirm({
                title: def.title,
                content: def.msg,
                closeIcon: def.closeIcon,
                keyboardEnabled: false,
                backgroundDismiss: false,
                confirmButton: def.confirmButton,
                cancelButton: def.cancelButton,
                confirm: function () {
                    return def.fun(true);
                },
                cancel: function () {
                    def.fun(false);
                },
                onIconClose: function () {
                    def.onIconClose();
                }
            });
        } else {
            this.alertObj = $.alert({
                title: def.title,
                content: def.msg,
                closeIcon: def.closeIcon,
                keyboardEnabled: false,
                backgroundDismiss: false,
                confirmButton: def.confirmButton,
                confirm: function () {
                    def.fun(true);
                },
                onIconClose: function () {
                    def.onIconClose();
                }
            });
        }
    },
    /**
     * 根据页面传入的code参数获取用户id
     */
    getOperId: function () {

    },
    /**
     * 显示加载框
     * @param {Object} id
     */
    load: {
        obj: null,
        moveobj: null,
        Tsqobj: null,
        /**
         * 
         * @param {Object} id
         */
        load: function (title) {
            clearInterval(common.load.moveobj);
            clearInterval(common.load.Tsqobj);
            if (common.load.obj == null) {
                /*common.open({
                 url:'/js/load/load.js',
                 fun:function(obj){
                 common.load.obj = obj;
                 obj.show();
                 }
                 });*/
                if (title == '' || title == undefined) {
                    title = '加载中'
                }
                var html = '<div class="_loading_" >' +
                        '	<div id="_loading_bg_" style="position: fixed;width: 100%;height: 100%;background-color: #868483;top: 0px;left: 0px;opacity: 0;    z-index: 9999;" ></div>' +
                        '	<div id="_loading_ts_" style="border: 1px solid #D2C7C7;background: #F5FAFD;padding: 10px;width: 500px;text-align: center;border-radius: 5px;position: fixed;z-index: 9999;top: 50%;left: 50%;margin-left: -250px;margin-top: -42px;">' +
                        '	<div style="position: relative;height:50px; line-height:50px; width:531px; text-align:center; font-size:30px; color:#29B6FF; font-family:Arial; margin:0 auto;" ><b id="_loadtitle_" >' + title + '</b><span class="mvSq">.</span><span class="mvSq">.</span><span class="mvSq">.</span></div>' +
                        '	<div style="height:15px; background:#F5FAFD url(/html/adminX/images/load/test2.jpg) no-repeat left center; width:471px; position:relative; padding:0 30px; margin:0 auto;" >' +
                        '	 <img style="position:absolute; left:50px; top:0;" class="mvBtn" src="/html/adminX/images/load/test1.jpg" />' +
                        '	</div>' +
                        '	</div>' +
                        '</div>';
                $('body').append(html);
                common.load.obj = $('._loading_');
            } else {
                if (title == '' || title == undefined) {
                    title = '加载中'
                }
                common.load.obj.find('#_loadtitle_').text(title);
                common.load.obj.show();
                common.load.moveobj = setInterval(autoMove, 8);
                common.load.Tsqobj = setInterval(autoTsq, 1500);
                return;
            }
            var delVal = 50;
            function autoMove() {
                delVal++;
                if (delVal > 400) {
                    delVal = 50;
                }
                $(".mvBtn").css("left", delVal);
            }
            common.load.moveobj = setInterval(autoMove, 8);
            var deNum = 0;
            function autoTsq() {
                $(".mvSq").css("color", "#F5FAFD");
                setTimeout(function () {
                    $(".mvSq").eq(0).css("color", "#29B6FF")
                }, 0);
                setTimeout(function () {
                    $(".mvSq").eq(1).css("color", "#29B6FF")
                }, 500);
                setTimeout(function () {
                    $(".mvSq").eq(2).css("color", "#29B6FF")
                }, 1000);
            }
            common.load.Tsqobj = setInterval(autoTsq, 1500);
        },
        /**
         * 隐藏load对话框
         */
        hide: function (anmin, msg) {
            if (common.load.obj == null) {
                //console.log('?11???');
                common.log(common.load.obj);
            } else {
                clearInterval(common.load.moveobj);
                clearInterval(common.load.Tsqobj);
                if (msg != undefined && msg != '') {
                    common.load.obj.find('#_loadtitle_').text(msg);
                }
                if (anmin != undefined && anmin != '') {
                    //zoomOut
                    //$('#_loading_bg_').addClass(anmin+' animated');
                    $('#_loading_ts_').addClass(anmin + ' animated');
                    setTimeout(function () {
                        //console.log('???1?'); 
                        common.load.obj.hide();
                        //$('#_loading_bg_').removeClass('animated').removeClass(anmin);
                        $('#_loading_ts_').removeClass('animated').removeClass(anmin);
                    }, 1000);
                } else {
                    common.log(common.load.obj);
                    common.load.obj.hide();
                }

            }
        }
    },
    /**
     * 加载基础项信息
     * @param {Object} group_id
     * @param {Object} fun
     */
    loadItem: function (group_id, fun, async) {
        if (async == undefined)
        {
            async = true;
        }
        common.ajax({
            url: common.root + '/sys/queryItem.do',
            data: {group_id: group_id},
            dataType: 'json',
            async: async,
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {
                        fun(data.list);
                    } else {
                        common.alert({msg: common.msg.error});
                        console.error(data);
                    }
                } else {
                    common.alert({msg: common.msg.error});
                    console.error(data);
                }
            }
        });
    },
    /**
     * 加载基础项信息
     * @param {Object} group_id
     * @param {Object} fun
     */
    loadItemAll: function (group_id, fun, async) {
        if (async == undefined)
        {
            async = true;
        }
        common.ajax({
            url: common.root + '/sys/queryItemAll.do',
            data: {group_id: group_id},
            dataType: 'json',
            async: async,
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {
                        fun(data.list);
                    } else {
                        common.alert({msg: common.msg.error});
                        console.error(data);
                    }
                } else {
                    common.alert({msg: common.msg.error});
                    console.error(data);
                }
            }
        });
    },
    /**
     * 加载基础项信息
     * @param {Object} group_id
     * @param {Object} fun
     */
    autoLoadItem: function (group_id, id, async, val, fun) {
        if (async == undefined)
        {
            async = true;
        }
        common.ajax({
            url: common.root + '/sys/queryItem.do',
            data: {group_id: group_id},
            dataType: 'json',
            async: async,
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {
                        var json = data.list;
                        var html = "";
                        for (var i = 0; i < json.length; i++) {
                            if (val == json[i].item_id) {
                                html += '<option selected="selected" value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
                            } else {
                                html += '<option  value="' + json[i].item_id + '" >' + json[i].item_name + '</option>';
                            }
                        }
                        $('#' + id).empty();
                        $('#' + id).append('<option value="">请选择...</option>');
                        $('#' + id).append(html);
                        if (typeof (fun) == 'function') {
                            fun(json);
                        }
                    } else {
                        common.alert({msg: common.msg.error});
                        console.error(data);
                    }
                } else {
                    common.alert({msg: common.msg.error});
                    console.error(data);
                }
            }
        });
    },
    /**
     * 加载材料清单信息
     * @param {Object} fatherid
     * @param {Object} type
     *  @param {Object} fun
     */
    loadMcate: function (fatherid, fun) {
        common.ajax({
            url: common.root + '/sys/queryMcate.do',
            data: {fatherid: fatherid},
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {
                        fun(data.list);
                    } else {
                        common.alert({msg: common.msg.error});
                        console.error(data);
                    }
                } else {
                    common.alert({msg: common.msg.error});
                    console.error(data);
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
    loadArea: function (fatherid, type, fun, async) {
        if (async == undefined) {
            async = true;
        }
        common.ajax({
            url: common.root + '/sys/queryArea.do',
            data: {fatherid: fatherid, type: type},
            async: async,
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {
                        fun(data.list);
                    } else {
                        common.alert({msg: common.msg.error});
                        console.error(data);
                    }
                } else {
                    common.alert({msg: common.msg.error});
                    console.error(data);
                }
            }
        });
    },
    /**
     * 图片上传控件
     * @param {Object} opt
     */
    dropzone: {
        init: function (opt) {
            //图片查看刘飞 初始化图片查看器
            var def = {
                id: '',
                url: "/sys/uploadFiles.do",
                maxFiles: 2,
                /**
                 * 默认的图片信息，保护[{path:'',first:1}]
                 */
                defimg: [],
                /**
                 * MB
                 */
                maxFilesize: 10,
                dictResponseError: '上传错误',
                dictMaxFilesExceeded: '超过可上传文件数量',
                dictDefaultMessage: '点击空白处上传图片',
                addRemoveLinks: true,
                clickEventOk: true,
                /**
                 * 可上传文件类型
                 */
                acceptedFiles: 'image/gif, image/jpeg, image/png',
                dictFisrtFile: '设置首图',
                dictRemoveFile: '删除',
                removeRemotFuc: null, //id,url fun
                dictCancelUpload: '取消上传',
                dictCancelUploadConfirmation: '你是否取消本次上传？',
                dictInvalidFileType: '文件类型错误',
                dictFileTooBig: '文件太大 ({{filesize}}MiB). 最大: {{maxFilesize}}MiB.',
                dictFallbackMessage: '你浏览器不支持托拽',
                init: function () {
                    if (def.defimg.length != 0) {//默认的图片显示不为0就需要添加图片信息进行展示
                        for (var i = 0; i < def.defimg.length; i++) {
                            var html = '<div class="dz_def_img_div dz-preview dz-processing dz-image-preview dz-success dz-complete"> ' +
                                    ' <div class="dz-image"><img data-dz-thumbnail="" style="width:120px;height:120px;" alt="" src="' + def.defimg[i].path + '"> ';
                            if (def.defimg[i].first == 1) {
                                html += '<div class="dz_fisrt_t">首图</div>';
                            }
                            html += '</div>' +
                                    ' <div class="dz-details">    <div class="dz-size"><span data-dz-size=""><strong></strong> KB</span></div>  ' +
                                    ' <div class="dz-filename"><span data-dz-name=""></span></div>  </div> ' +
                                    '';
                            if (def.clickEventOk) {
                                html += ' <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress=""'
                                        + 'style="width: 100%;"></span></div>  <div class="dz-error-message"><span data-dz-errormessage=""></span></div>'
                                        + '<div class="dz-success-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">'
                                        + '<title>Check</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <path d="M23.5,31.8431458 L17.5852419,25.9283877 C16.0248253,24.3679711 13.4910294,24.366835 11.9289322,25.9289322 C10.3700136,27.4878508 10.3665912,30.0234455 11.9283877,31.5852419 L20.4147581,40.0716123 C20.5133999,40.1702541 20.6159315,40.2626649 20.7218615,40.3488435 C22.2835669,41.8725651 24.794234,41.8626202 26.3461564,40.3106978 L43.3106978,23.3461564 C44.8771021,21.7797521 44.8758057,19.2483887 43.3137085,17.6862915 C41.7547899,16.1273729 39.2176035,16.1255422 37.6538436,17.6893022 L23.5,31.8431458 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" stroke-opacity="0.198794158" stroke="#747474" fill-opacity="0.816519475" fill="#FFFFFF" sketch:type="MSShapeGroup"></path>      </g> '
                                        + '</svg>  </div>  <div class="dz-error-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Error</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">'
                                        + '        <g id="Check-+-Oval-2" sketch:type="MSLayerGroup" stroke="#747474" stroke-opacity="0.198794158" fill="#FFFFFF" fill-opacity="0.816519475">          <path d="M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" sketch:type="MSShapeGroup"></path>        </g>      </g>    </svg>  </div>'
                                        + '<div class="dz_bnt1"><a class="dz-fist" href="javascript:undefined;" data-dz-fist="">设置首图</a><a class="dz-remove" href="javascript:undefined;" data-src="' + def.defimg[i].path + '" data-dz-remove="">删除</a><a class="' + def.id.replace('#', '') + ' cboxElement" href="' + def.defimg[i].path + '" rel="' + def.id.replace('#', '') + '" >查看图片</a></div></div>';
                            } else {
                                html += ' <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress=""'
                                        + 'style="width: 100%;"></span></div>  <div class="dz-error-message"><span data-dz-errormessage=""></span></div>'
                                        + '<div class="dz-success-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">'
                                        + '<title>Check</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <path d="M23.5,31.8431458 L17.5852419,25.9283877 C16.0248253,24.3679711 13.4910294,24.366835 11.9289322,25.9289322 C10.3700136,27.4878508 10.3665912,30.0234455 11.9283877,31.5852419 L20.4147581,40.0716123 C20.5133999,40.1702541 20.6159315,40.2626649 20.7218615,40.3488435 C22.2835669,41.8725651 24.794234,41.8626202 26.3461564,40.3106978 L43.3106978,23.3461564 C44.8771021,21.7797521 44.8758057,19.2483887 43.3137085,17.6862915 C41.7547899,16.1273729 39.2176035,16.1255422 37.6538436,17.6893022 L23.5,31.8431458 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" stroke-opacity="0.198794158" stroke="#747474" fill-opacity="0.816519475" fill="#FFFFFF" sketch:type="MSShapeGroup"></path>      </g> '
                                        + '</svg>  </div>  <div class="dz-error-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Error</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">'
                                        + '        <g id="Check-+-Oval-2" sketch:type="MSLayerGroup" stroke="#747474" stroke-opacity="0.198794158" fill="#FFFFFF" fill-opacity="0.816519475">          <path d="M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" sketch:type="MSShapeGroup"></path>        </g>      </g>    </svg>  </div>'
                                        + '<div class="dz_bnt1"><a class="' + def.id.replace('#', '') + ' cboxElement" href="' + def.defimg[i].path + '" rel="' + def.id.replace('#', '') + '" >查看图片</a></div></div>';
                            }
                            $(def.id).append(html);
                            $(def.id.replace("#", ".")).colorbox({opacity: "0.3", width: "900px", height: "800px", current: "图片 {current} 总计 {total}"});
                        }
                        $(def.id).addClass('dz-started');
                        //添加处理事件信息
                        $(def.id + " .dz_def_img_div .dz-remove").click(function () {
                            var $this = this;
                            if (typeof (def.removeRemotFuc) == 'function') {
                                def.removeRemotFuc(def.id, $(this).attr('data-src'), function () {
                                    $($this).parent().parent().remove();
                                    var myDropzone = $(def.id).data('obj');
                                    var size = $(def.id + " .dz_def_img_div").size() + myDropzone.files.length;
                                    $(def.id + " .dropzone_title_ysc").text(size);
                                    if (size == 0) {
                                        $(def.id).removeClass('dz-started');
                                    }
                                });
                            } else {
                                $(this).parent().parent().remove();
                                var myDropzone = $(def.id).data('obj');
                                var size = $(def.id + " .dz_def_img_div").size() + myDropzone.files.length;
                                $(def.id + " .dropzone_title_ysc").text(size);
                                if (size == 0) {
                                    $(def.id).removeClass('dz-started');
                                }
                            }

                        });


                        $(def.id + " .dz_def_img_div .dz-fist").click(function () {
                            //$(this).parent().parent().remove();
                            var myDropzone = $(def.id).data('obj');
                            for (var i = 0; i < myDropzone.files.length; i++) {
                                myDropzone.files[i].first = 0;
                                $(myDropzone.files[i].previewElement).find('.dz_fisrt_t').remove();
                            }

                            $(def.id + " .dz_def_img_div .dz_fisrt_t").remove();
                            $(this).parent().parent().find('.dz-image').append('<div class="dz_fisrt_t">首图</div>');
                        });
                    }
                    if (def.clickEventOk)
                    {
                        $(def.id).prepend("<div class='dropzone_title' >可上传文件数量：" + def.maxFiles + "，已上传文件数量：<b class='dropzone_title_ysc' >0</b>，可上传文件大小：" + def.maxFilesize + "MB，可上传文件类型：" + def.acceptedFiles.replaceAll('image/', '') + "</div>");
                        $(def.id + " .dropzone_title_ysc").text(def.defimg.length);
                        //添加设置首页处理事件
                        $(def.id).find('.dz-p .dz-fist').live('click', function () {
                            //alert(2);
                            var dz_o = $(this).parent().parent();
                            var file = dz_o.data('dz_file_');
                            //console.log($(this).parent().parent().data('dz_file_'));
                            //先全部设置不是首图，然后更改首图
                            //common.dropzone.getFiles(def.id);
                            $(def.id + " .dz_def_img_div .dz_fisrt_t").remove();
                            var myDropzone = $(def.id).data('obj');
                            for (var i = 0; i < myDropzone.files.length; i++) {
                                $(myDropzone.files[i].previewElement).find('.dz_fisrt_t').remove();
                                if (file.path_ == myDropzone.files[i].path_) {
                                    myDropzone.files[i].first = 1;
                                    //添加对应的首图标记
                                    dz_o.find('.dz-image').append('<div class="dz_fisrt_t" >首图</div>');
                                } else {
                                    myDropzone.files[i].first = 0;
                                }
                            }
                            //console.log(myDropzone);
                        });
                    }
                }
            }
            jQuery.extend(def, opt);
            /**
             * 重写对应的点击处理事件
             * @param {Object} obj
             */
            def.clickfun = function (obj) {
                if (!def.clickEventOk)
                {
                    return;
                }
                if (parseInt($(def.id + ' .dz-image-preview').size()) >= def.maxFiles) {
                    common.alert({msg: '超过可上传的图片数量'});
                    return;
                }

                layer.msg('请选择是本地上传还是图片空间选择图片', {
                    offset: [JSON.stringify(document.body.clientHeight/2)],
                    shadeClose: true,
                    time: 0, //不自动关闭
//                   btn: ['本地上传', '图片空间','取消'],
                    btn: ['本地上传', '取消'],
                    yes: function (index) {
                        obj.hiddenFileInput.click();
                        layer.close(index);
                    }
//               		,
//                   btn2: function(index){
//                       //加載圖片信息
//                       common.openWindow({
//                           type: 1,
//						   id:'_images_',
//                           name: 'loadimgdiv',
//                           area: ['900px', '600px'],
//                           title: '图片空间管理',
//                           url: '/html/pages/common/img.html',
//                           success: function(layero, index, id){
//						   		$('#'+id).attr('index',index);
//                           		document.oncontextmenu = function(){return false;}
//                           },
//                           cancel: function(index){
//                           		document.oncontextmenu = function(){return true;}
//                           },
//						   buts:{
//						   		btn:['确定选择'],
//								btn1:function(){
//									var nownum = parseInt($(def.id+' .dz-image-preview').size());
//									if(_img_.selectobj.length+nownum > def.maxFiles){
//										common.alert({msg:'超过可上传的图片数量'});
//										return;
//									}
//									for(var i=0;i<_img_.selectobj.length;i++){
//										var html = '<div iskj="1" class="dz_def_img_div dz-preview dz-processing dz-image-preview dz-success dz-complete"> '+
//												   ' <div class="dz-image"><img data-dz-thumbnail="" style="width:120px;height:120px;" alt="" src="'+_img_.selectobj[i].path+'"> ';
//										html += '</div>'+
//												' <div class="dz-details">    <div class="dz-size"><span data-dz-size=""><strong></strong> KB</span></div>  '+
//											   ' <div class="dz-filename"><span data-dz-name=""></span></div>  </div> '+
//											   ' <div class="dz-progress"><span class="dz-upload" data-dz-uploadprogress="" style="width: 100%;"></span></div>  <div class="dz-error-message"><span data-dz-errormessage=""></span></div>  <div class="dz-success-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Check</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <path d="M23.5,31.8431458 L17.5852419,25.9283877 C16.0248253,24.3679711 13.4910294,24.366835 11.9289322,25.9289322 C10.3700136,27.4878508 10.3665912,30.0234455 11.9283877,31.5852419 L20.4147581,40.0716123 C20.5133999,40.1702541 20.6159315,40.2626649 20.7218615,40.3488435 C22.2835669,41.8725651 24.794234,41.8626202 26.3461564,40.3106978 L43.3106978,23.3461564 C44.8771021,21.7797521 44.8758057,19.2483887 43.3137085,17.6862915 C41.7547899,16.1273729 39.2176035,16.1255422 37.6538436,17.6893022 L23.5,31.8431458 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" stroke-opacity="0.198794158" stroke="#747474" fill-opacity="0.816519475" fill="#FFFFFF" sketch:type="MSShapeGroup"></path>      </g>    </svg>  </div>  <div class="dz-error-mark">    <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns:sketch="http://www.bohemiancoding.com/sketch/ns">      <title>Error</title>      <defs></defs>      <g id="Page-1" stroke="none" stroke-width="1" fill="none" fill-rule="evenodd" sketch:type="MSPage">        <g id="Check-+-Oval-2" sketch:type="MSLayerGroup" stroke="#747474" stroke-opacity="0.198794158" fill="#FFFFFF" fill-opacity="0.816519475">          <path d="M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" id="Oval-2" sketch:type="MSShapeGroup"></path>        </g>      </g>    </svg>  </div><div class="dz_bnt1"><a class="dz-fist" href="javascript:undefined;" data-dz-fist="">设置首图</a><a class="dz-remove" href="javascript:undefined;" data-dz-remove="">删除</a><a class="'+ def.id.replace('#','')+' cboxElement" href="'+_img_.selectobj[i].path+'" rel="'+def.id.replace('#','')+'" >查看图片</a></div></div>';
//										$(def.id).append(html);
//									}
//									//初始化图片查看器---刘飞
//									$(def.id.replace("#",".")).colorbox({opacity :"0.3",width:"900px",height:"800px",current:"图片 {current} 总计 {total}"});
//									$(def.id).addClass('dz-started'); 
//									//添加处理事件信息
//									$(def.id +" .dz_def_img_div .dz-remove").click(function(){
//										$(this).parent().parent().remove();
//										var myDropzone = $(def.id).data('obj');
//										var size = $(def.id+" .dz_def_img_div").size()+myDropzone.files.length;
//						   				$(def.id+" .dropzone_title_ysc").text(size);
//										if(size == 0){
//											$(def.id).removeClass('dz-started');
//										}
//									});
//									$(def.id +" .dz_def_img_div .dz-fist").click(function(){
//										//$(this).parent().parent().remove();
//										var myDropzone = $(def.id).data('obj');
//										for(var i = 0;i<myDropzone.files.length;i++){
//											myDropzone.files[i].first = 0;
//											$(myDropzone.files[i].previewElement).find('.dz_fisrt_t').remove();
//										}
//										
//										$(def.id+" .dz_def_img_div .dz_fisrt_t").remove();
//										$(this).parent().parent().find('.dz-image').append('<div class="dz_fisrt_t">首图</div>');							
//									});
//									$(def.id+" .dropzone_title_ysc").text($(def.id+' .dz-image-preview').size());
//									layer.close($('#_images_').attr('index'));
//								}
//						   }
//                       });
//                   }
                });
            }
            var myDropzone = new Dropzone(def.id, def);
            myDropzone.on("sending", function (file, xhr, formData) {
//			   console.log(file);
                //console.log(xhr);
                //formData.path = '/upload/tmp/';
                var path = '/upload/tmp/';

                formData.append("path", path);
                var rand = parseInt(Math.random() * 100000);
                var qz = file.name.substr(0, file.name.indexOf('\.'));
                var name = $.md5(qz + rand) + file.name.substr(file.name.indexOf('\.'));
                //formData.name = (qz+ rand)+file.name.substr(file.name.indexOf('\.'));
                formData.append("name", name);
                file.path_ = path + name;
                //console.log(formData);
                //console.log(xhr);
            });
            myDropzone.on("maxfilesreached", function (file) {
                //alert(2);
            });
            myDropzone.on("canceled", function (file) {
                // alert(3);
            });

            myDropzone.on("removedfile", function (data) {
                //alert(5);
                //console.log(data);
                var myDropzone = $(def.id).data('obj');
                $(def.id + " .dropzone_title_ysc").text($(def.id + " .dz_def_img_div").size() + myDropzone.files.length);
            });
            myDropzone.on("complete", function (data) {
                //alert(5);
                //console.log(data);
            });
            myDropzone.on("success", function (file) {
                //alert(6);
                $(file.previewElement).data('dz_file_', file);
                var myDropzone = $(def.id).data('obj');
                //console.log(file);
                //$(def.id).data('obj',myDropzone);
                if ($(def.id + " .dz_def_img_div .dz_fisrt_t").size() == 0) {
                    //检查是否存在设置首图，没有就进行设置默认第一张
                    var truth = false;
                    for (var i = 0; i < myDropzone.files.length; i++) {
                        if (myDropzone.files[i].first == 1) {
                            truth = true;
                        } else {
                            myDropzone.files[i].first = 0;
                        }
                    }

                    if (!truth) {
                        myDropzone.files[0].first = 1
                        for (var i = 0; i < myDropzone.files.length; i++) {
                            $(myDropzone.files[i].previewElement).find('.dz_fisrt_t').remove();
                        }
                        $(myDropzone.files[0].previewElement).find('.dz-image').append('<div class="dz_fisrt_t" >首图</div>');
                    }
                }
                $(file.previewElement).find('.dz_bnt1').append("<a class='" + def.id.replace("#", "") + " cboxElement' href='" + file.path_ + "' rel='" + def.id.replace("#", "") + "' >查看图片</a>");
                //图片查看器初始化--刘飞
                $(def.id.replace("#", ".")).colorbox({opacity: "0.3", width: "900px", height: "800px", current: "图片 {current} 总计 {total}"});
                // $(def.id).removeClass("cboxElement");
                //file
                // 	console.log(parseInt($(def.id+" .dz_def_img_div").size())+parseInt(myDropzone.files.length));
                if (def.maxFiles < parseInt($(def.id + " .dz_def_img_div").size()) + parseInt(myDropzone.files.length)) {
                    common.alert({msg: '当前图片上传超过可上传数量'});
                    myDropzone.removeFile(file);
                }
                $(def.id + " .dropzone_title_ysc").text($(def.id + " .dz_def_img_div").size() + myDropzone.files.length);
            });
            myDropzone.on("error", function (file) {
                //alert(4);
                common.alert({msg: '当前文件上传失败，请重新尝试。'});
                try {
                    myDropzone.removeFile(file);
                } catch (e) {

                }
            });
            myDropzone.on("maxfilesexceeded", function (file) {
                common.alert({msg: '当前图片上传超过可上传数量'});
                try {
                    myDropzone.removeFile(file);
                } catch (e) {
                }

            });
            $(def.id).data('obj', myDropzone);
        },
        getFiles: function (id) {
            var myDropzone = $(id).data('obj');
            //console.log(myDropzone);
            var filepath = [];
            for (var i = 0; i < myDropzone.files.length; i++) {
                filepath.push({path: myDropzone.files[i].path_, fisrt: myDropzone.files[i].first == undefined ? 0 : myDropzone.files[i].first, isdef: 0});
            }
            //检查是否存在默认上传的图片
            //dz_def_img_div
            var de_obj = $(id).find('.dz_def_img_div');
            if (de_obj.size() != 0) {
                var def_o = de_obj.find('.dz-image');
                for (var i = 0; i < de_obj.size(); i++) {
                    var iskj = $(de_obj[i]).attr('iskj');
                    var s = $(de_obj[i]).find('img').attr('src');
                    var f = $(de_obj[i]).find('.dz_fisrt_t').size();
                    var isdef = 1;
                    if (iskj == 1) {
                        isdef = 0;
                    }
                    //
                    filepath.push({path: s, fisrt: f, isdef: isdef});
                }
            }
            return filepath;
        },
    },
    /**
     * 支付统一入口  
     */
    pay: function (data) {
        common.load.load();
        common.ajax({
            url: common.root + '/weixin/insertPay.do',
            data: data,
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {
                        window.location.href = common.root + "/client/new/pay/pay.html?1=2" + rand + "&server_id=" + common.getQueryString('server_id') + "&pay_id=" + data.pay_id + '&url=' + encodeURIComponent(window.location.href);
                    } else if (data.state == -2) {
                        common.alert({
                            msg: '未查询到该订单需要进行支付，请刷新本页面。'
                        });
                        common.load.hide();
                        return;
                    } else {
                        common.alert({
                            msg: '生成支付信息失败，请重新操作。'
                        });
                        common.load.hide();
                        return;
                    }
                } else {
                    common.alert({msg: '生成支付信息失败，请重新操作。'});
                    common.load.hide();
                }
            }
        });
    },
    /**
     * 实时预览接口
     * @params imgFile 
     * @params id div对应的id值
     */
    previewImage: function (imgFile, id) {
        var filextension = imgFile.value.substring(imgFile.value.lastIndexOf("."), imgFile.value.length);
        filextension = filextension.toLowerCase();

        if ((filextension != '.jpg') && (filextension != '.gif') && (filextension != '.jpeg') && (filextension != '.png') && (filextension != '.bmp'))
        {
            alert("对不起，系统仅支持标准格式的照片，请您调整格式后重新上传，谢谢 !");
            $(imgFile).replaceWith($(imgFile).clone());
            imgFile.focus();
        } else
        {
            var path;
            if (document.all)//IE
            {
                imgFile.select();
                path = document.selection.createRange().text;
                document.getElementById(id).innerHTML = "";
                document.getElementById(id).style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled='true',sizingMethod='scale',src=\"" + path + "\")";//使用滤镜效果  
            } else//FF
            {
                try {
                    path = imgFile.files[0].getAsDataURL();
                    document.getElementById(id).innerHTML = "<img id='img_' style='width:100%;' src='" + path + "'/>";
                } catch (e) {
                    document.getElementById(id).innerHTML = "<img id='img_' style='width:100%;' src='" + window.URL.createObjectURL(imgFile.files[0]) + "'/>";
                }
            }
        }
    }
}

Validator = {
    mode: "",
    Require: /.+/,
    Email: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    Phone: /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/,
    Msisdn: /^(147|150|151|152|158|159|182|183|187|188|13[4-9]{1})[0-9]{8}$/,
    Mobile: /^1(3|4|5|7|8)[0-9]{9}$/,
    Url: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
    IdCard: "this.IsIdCard(value)",
    Currency: /^\d+(\.\d+)?$/,
    Number: /^\d+$/,
    Zip: /^[1-9]\d{5}$/,
    QQ: /^[1-9]\d{4,8}$/,
    Integer: /^[-\+]?\d+$/,
    Integer2: /^\d+$/,
    Integer3: /^[1-9]\d*(\.\d+)?$/,
    IP: /^(\d+)\.(\d+)\.(\d+)\.(\d+)$/,
    Double: /^[-\+]?\d+(\.\d+)?$/,
    Double2: /^\d+(\.\d+)?$/,
    Money: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
    Money2: /^(([0-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/,
    English: /^[A-Za-z]+$/,
    Chinese: /^[\u0391-\uFFE5]+$/,
    Username: /^[a-z]\w{3,}$/i,
    UnSafe: /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
    IsSafe: function (str) {
        return !this.UnSafe.test(str);
    },
    SafeString: "this.IsSafe(value)",
    Filter: "this.DoFilter(value, getAttribute('accept'))",
    Limit: "this.limit(value.length,getAttribute('min'),  getAttribute('max'))",
    LimitB: "this.limit(this.LenB(value), getAttribute('min'), getAttribute('max'))",
    Date: "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
    Repeat: "value == document.getElementsByName(getAttribute('to'))[0].value",
    Range: "getAttribute('min') <= (value|0) && (value|0) <= getAttribute('max')",
    Compare: "this.compare(value,getAttribute('operator'),getAttribute('to'))",
    Custom: "this.Exec(value, getAttribute('regexp'))",
    Group: "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
    ErrorItem: [document.forms[0]],
    ErrorMessage: [],
    Validate: function (theForm, mode) {
        mode = mode || 1;
        this.mode = mode;
        try {
            theForm = document.getElementById(theForm);
        } catch (e) {
        }
        var obj = theForm || event.srcElement;
        var count = obj.elements == undefined ? obj.all.length : obj.elements.length;//modify chang
        this.ErrorMessage.length = 1;
        this.ErrorItem.length = 1;
        this.ErrorItem[0] = obj;
        for (var i = 0; i < count; i++) {
            var foundError = false;
            with (obj.elements == undefined ? obj.all[i] : obj.elements[i]) {
                var _dataType = getAttribute("dataType");

                //alert(_dataType);
                if (typeof (_dataType) == "object" || typeof (this[_dataType]) == "undefined") {
                    continue;
                }
                this.ClearState((obj.elements == undefined ? obj.all[i] : obj.elements[i]));
                if ((obj.elements == undefined ? obj.all[i] : obj.elements[i]).disabled) {
                    continue;
                }
                if (getAttribute("require") == "false" && value == "") {
                    continue;
                }
                switch (_dataType) {
                    case "IdCard":
                    case "Date":
                    case "Repeat":
                    case "Range":
                    case "Compare":
                    case "Custom":
                    case "Group":
                    case "Limit":
                    case "LimitB":
                    case "SafeString":
                    case "Filter":
                        if (!eval(this[_dataType])) {
                            this.AddError(i, getAttribute("msg"));
                            if (mode == 1) {
                                foundError = true;
                            }
                        }
                        break;
                    default:
                        if (!this[_dataType].test(value.trim())) {

                            this.AddError(i, getAttribute("msg"));
                            if (mode == 1) {
                                foundError = true;
                            }
                        }
                        break;
                }
            }
            if (foundError == true) {
                break;
            }
        }
        if (this.ErrorMessage.length > 1) {
            var errCount = this.ErrorItem.length;
            switch (mode) {
                case 1:
                    layer.tips(this.ErrorMessage.join("\n"), this.ErrorItem[1], {
                        tips: [1, '#d9534f'] //还可配置颜色
                    });
                    //alert(this.ErrorMessage.join("\n"));
                    try {
                        this.ErrorItem[1].focus();
                    } catch (e) {
                    }
                    ;
                    break;
                case 2:
                    for (var i = 1; i < errCount; i++) {
                        try {
                            var span = document.createElement("SPAN");
                            span.id = "__ErrorMessagePanel";
                            span.style.color = "red";
                            span.style.fontSize = "12px";
                            this.ErrorItem[i].parentNode.appendChild(span);
                            span.innerHTML = this.ErrorMessage[i].replace(/\d+:/, "*");
                        } catch (e) {
                            alert(e.description);
                        }
                    }
                    try {
                        this.ErrorItem[1].focus();
                    } catch (e) {
                    }
                    ;
                    break;
                default:
                    this.ErrorMessage = ["\u4ee5\u4e0b\u539f\u56e0\u5bfc\u81f4\u63d0\u4ea4\u5931\u8d25\uff1a\t\t\t\t"];
                    alert(this.ErrorMessage.join("\n"));
                    break;
            }
            return false;
        }
        return true;
    },
    limit: function (len, min, max) {
        min = min || 0;
        max = max || Number.MAX_VALUE;
        return min <= len && len <= max;
    },
    LenB: function (str) {
        return str.replace(/[^\x00-\xff]/g, "**").length;
    },
    ClearState: function (elem) {
        with (elem) {
            if (style.color == "red") {
                style.color = "";
            }
            var lastNode = parentNode.childNodes[parentNode.childNodes.length - 1];
            if (lastNode.id == "__ErrorMessagePanel") {
                parentNode.removeChild(lastNode);
            }
        }
    },
    AddError: function (index, str) {
        this.ErrorItem[this.ErrorItem.length] = (this.ErrorItem[0].elements == undefined ? this.ErrorItem[0].all[index] : this.ErrorItem[0].elements[index]);
        if (this.mode == 1) {
            this.ErrorMessage[this.ErrorMessage.length] = str;
        } else {
            this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
        }
    },
    Exec: function (op, reg) {
        return new RegExp(reg, "g").test(op);
    },
    compare: function (op1, operator, op2) {
        switch (operator) {
            case "NotEqual":
                return (op1 != op2);
            case "GreaterThan":
                return (op1 > op2);
            case "GreaterThanEqual":
                return (op1 >= op2);
            case "LessThan":
                return (op1 < op2);
            case "LessThanEqual":
                return (op1 <= op2);
            default:
                return (op1 == op2);
        }
    },
    MustChecked: function (name, min, max) {
        //alert('11');
        var groups = document.getElementsByName(name);
        var hasChecked = 0;
        min = min || 1;
        max = max || groups.length;
        for (var i = groups.length - 1; i >= 0; i--) {
            if (groups[i].checked) {
                hasChecked++;
            }
        }
        return min <= hasChecked && hasChecked <= max;
    },
    DoFilter: function (input, filter) {
        return new RegExp("^.+.(?=EXT)(EXT)$".replace(/EXT/g, filter.split(/\s*,\s*/).join("|")), "gi").test(input);
    },
    IsIdCard: function (number) {
        var number = number.toLowerCase();
        var date, Ai;
        var verify = "10x98765432";
        var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
        var area = ["", "", "", "", "", "", "", "", "", "", "", "\u5317\u4eac", "\u5929\u6d25", "\u6cb3\u5317", "\u5c71\u897f", "\u5185\u8499\u53e4", "", "", "", "", "", "\u8fbd\u5b81", "\u5409\u6797", "\u9ed1\u9f99\u6c5f", "", "", "", "", "", "", "", "\u4e0a\u6d77", "\u6c5f\u82cf", "\u6d59\u6c5f", "\u5b89\u5fae", "\u798f\u5efa", "\u6c5f\u897f", "\u5c71\u4e1c", "", "", "", "\u6cb3\u5357", "\u6e56\u5317", "\u6e56\u5357", "\u5e7f\u4e1c", "\u5e7f\u897f", "\u6d77\u5357", "", "", "", "\u91cd\u5e86", "\u56db\u5ddd", "\u8d35\u5dde", "\u4e91\u5357", "\u897f\u85cf", "", "", "", "", "", "", "\u9655\u897f", "\u7518\u8083", "\u9752\u6d77", "\u5b81\u590f", "\u65b0\u7586", "", "", "", "", "", "\u53f0\u6e7e", "", "", "", "", "", "", "", "", "", "\u9999\u6e2f", "\u6fb3\u95e8", "", "", "", "", "", "", "", "", "\u56fd\u5916"];
        var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/i);
        if (re == null) {
            return false;
        }
        if (re[1] >= area.length || area[re[1]] == "") {
            return false;
        }
        if (re[2].length == 12) {
            Ai = number.substr(0, 17);
            date = [re[9], re[10], re[11]].join("-");
        } else {
            Ai = number.substr(0, 6) + "19" + number.substr(6);
            date = ["19" + re[4], re[5], re[6]].join("-");
        }
        if (!this.IsDate(date, "ymd")) {
            return false;
        }
        var sum = 0;
        for (var i = 0; i <= 16; i++) {
            sum += Ai.charAt(i) * Wi[i];
        }
        Ai += verify.charAt(sum % 11);
        return (number.length == 15 || number.length == 18 && number == Ai);
    },
    IsDate: function (op, formatString) {
        formatString = formatString || "ymd";
        var m, year, month, day;
        switch (formatString) {
            case "yyyy-mm-dd":
                m = op.match(new RegExp("^((\\d{4}))([-])(\\d{2})([-])(\\d{2})$"));
                if (m == null) {
                    return false;
                }
                day = m[6];
                month = m[4] * 1;
                year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
                break;
            case "ymd":
                m = op.match(new RegExp("^((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})$"));
                if (m == null) {
                    return false;
                }
                day = m[6];
                month = m[5] * 1;
                year = (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
                break;
            case "dmy":
                m = op.match(new RegExp("^(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))$"));
                if (m == null) {
                    return false;
                }
                day = m[1];
                month = m[3] * 1;
                year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
                break;
            default:
                break;
        }
        if (!parseInt(month)) {

            return false;
        }
        month = month == 0 ? 12 : month;
        var date = new Date(year, month - 1, day);
        return (typeof (date) == "object" && year == date.getFullYear() && month == (date.getMonth() + 1) && day == date.getDate());
        function GetFullYear(y) {
            return ((y < 30 ? "20" : "19") + y) | 0;
        }
    },    
    isEmpty: function (value) {
        return this.Require.test(value);
    },
    isMoney: function (value) {
        return this.Money.test(value);
    },
    isMsisdn: function (value) {
        return this.Msisdn.test(value.trim());
    },
    isMobile: function (value) {
        return this.Mobile.test(value.trim());
    },
    isInteger2: function (value) {
        return this.Integer2.test(value);
    },
    isIP: function (value) {
        if (this.IP.test(value)) {
            if (RegExp.$1 < 256 && RegExp.$2 < 256 && RegExp.$3 < 256 && RegExp.$4 < 256)
                return true;
        }
        return false;

    }

};
common.init();
String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};
Array.prototype.remove = function (str) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == str) {
            this.splice(i, 1);
        }
    }
    return this;
};
if (window.atob == undefined) {
    function btoa(stringToEncode) {
        return stringToEncode;
    }
    function atob(stringToEncode) {
        return stringToEncode;
    }
}
;
