var user_update = {
    orgselect: null,
    roleselect: null,
    tramselect: null,
    roleVal: null,
    orgVal: null,
    roleUn: false,
    orgUn: false,
    //页面初始化操作
    init: function () {
        // 时间按钮选择事件
        var nowTemp = new Date();
        $('#groupDate').daterangepicker(
                {
                    startDate: nowTemp.getFullYear() + '-'
                            + (nowTemp.getMonth() + 1) + '-02',
                    singleDatePicker: true,
                    format: 'YYYY-MM-DD'
                }, function (start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });
        // 添加处理事件
        user_update.addEvent();
        common.ajax({
            url: common.root + '/account/selectRole.do',
            async: false,
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    var html = "";
                    for (var i = 0; i < data.list.length; i++) {
                        html += '<option  value="' + data.list[i].role_id + '" >' + data.list[i].role_name + '</option>';
                    }
                    $('#Role').append(html);
                    user_update.roleselect = $('#Role').select2();
                    window.setTimeout(function () {
                        user_update.roleselect.val(rowdata.role_id.split(',')).trigger("change");
                    }, 300);

                    user_update.roleVal = rowdata.role_id;
                }
            }
        });
        common.ajax({
            url: common.root + '/account/selectOrg.do',
            async: false,
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    var html = "";
                    for (var i = 0; i < data.list.length; i++) {
                        html += '<option  value="' + data.list[i].org_id + '" >' + data.list[i].org_name + '</option>';
                    }
                    $('#Org').append(html);
                    user_update.orgselect = $('#Org').select2();
                    window.setTimeout(function () {
                        user_update.orgselect.val(rowdata.org_id.split(',')).trigger("change");
                    }, 300);
                }
            }
        });
        /**
         * 获取团队信息
         */
        common.ajax({
            url: common.root + '/account/selectTeam.do',
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    var html = "";
                    var name = "";
                    for (var i = 0; i < data.list.length; i++) {
                      html += '<option  value="' + data.list[i].org_id + '" >' + data.list[i].org_name + '</option>';
                    }
                    $('#team').append(html);
                    user_update.teamselect = $('#team').select2();
                    $('#team').on("select2:select",function(e){
                        if(e.params.data.id == '-2'){
                            var val = user_update.teamselect.val();
                            console.log(val);
                            if(val.length != 0){
                                if($.inArray('-2',val) >=0 && val.length != 1){
                                    alert('选择了其他团队后无法选择无主队。');
                                    user_update.teamselect.val($.grep(user_update.teamselect.val(), function(n,i){
                                        return n != -2;
                                      })).trigger("change");
                                }
                            }
                        }else{
                            var val = user_update.teamselect.val();
                            console.log(val);   
                            console.log($.inArray('-2',val));
                            if($.inArray('-2',val) >=0){
                                alert('选择了无主队C后无法选择其他团队。');
                                user_update.teamselect.val('-2').trigger("change");
                            }
                        }
                    });
                    window.setTimeout(function () {
                        user_update.teamselect.val(rowdata.team.split(',')).trigger("change");
                    }, 300);
                }
            }
        });
        user_update.initData();
        $('#mobile').blur(function () {
            user_update.getUserName();
        });
    },
    checkExists: function (str, strs) {
        var s = strs.split(',');
        for (var i = 0; i < s.length; i++) {
            if (s[i] == str) {
                return "selected='selected'";
            }
        }
        return "";
    },
    addEvent: function ()
    {
        $('#user_add_bnt').click(function () {
            user_update.save();
        });
    },
    //加载修改数据信息
    initData: function () {
        if (rowdata != null) {
            $("#id").val(rowdata.id);
            $('#aname').val(rowdata.aname);
            $('#mobile').val(rowdata.mobile);
            $('#desc_text').val(rowdata.desc_text);
            $('#groupDate').val(rowdata.create_time);
            $('#Org').val(rowdata.org_id);
            $('#Role').val(rowdata.role_id);
        }
        ;
    },
    //获取用户名称
    getUserName: function ()
    {
        var mobile = $('#mobile').val();
        if (mobile == '' || mobile.length != 11)
        {
            $('#ouserName').removeAttr("readonly", "readonly");
            return;
        }
        common.ajax({
            url: common.root + '/sys/getUserName.do',
            data: {userMobile: mobile},
            dataType: 'json',
            loadfun: function (isloadsucc, data)
            {
                if (isloadsucc)
                {
                    if (data.id != null && data.id != "") {
                        common.alert({
                            msg: "该手机以在系统存在，请更换手机号码"
                        });
                    }
                } else
                {
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
        });
    },
    /**
     * 保存操作
     */
    save: function () {
        /*if (!Validator.Validate('form2')){
         return;
         }*/
        var role = user_update.roleselect.val();
        var org = user_update.orgselect.val();
        var team = user_update.teamselect.val();
        if (role == '' || role == null) {
            common.alert('请至少选择一项角色。');
            return;
        }
        if (org == '' || org == null) {
            common.alert('请至少选择一项组织。');
            return;
        }
        if (team == '' || team == null) {
            common.alert('请至少选择一项团队。');
            return;
        }
        common.ajax({
            url: common.root + '/account/update.do',
            data: {
                id: $('#id').val(),
                aname: $('#aname').val(),
                mobile: $('#mobile').val(),
                desc_text: $('#desc_text').val(),
                groupDate: $('#groupDate').val(),
                Org: org,
                team: team,
                Role: role
            },
            dataType: 'json',
            loadfun: function (isloadsucc, data) {
                if (isloadsucc) {
                    if (data.state == 1) {//操作成功
                        common.alert({
                            msg: '操作成功'
                        });
                        common.closeWindow('addgroupadd', 3);
                    } else {
                        $("#group_add_bnt").attr("disabled", false);
                        common.alert({
                            msg: common.msg.error
                        });
                    }
                } else {
                    $("#group_add_bnt").attr("disabled", false);
                    common.alert({
                        msg: common.msg.error
                    });
                }
            }
        });
    }
};
user_update.init();