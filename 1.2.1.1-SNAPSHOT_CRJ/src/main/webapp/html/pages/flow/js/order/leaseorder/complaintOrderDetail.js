var respData;
var workOrder;
var isMobile;
var resFlag;
var complaintOrderValueMap = {};
var jsComplaintOrderDetail = {

  // 初始化数据信息
  init: function() {
    var id = $('#js-work-order-id').val();
    if (common.getCookie('resFlag' + (id)) == '1' || common.getCookie('resFlag' + (id)) == 'undefined' || common.getCookie('resFlag' + (id)) == '') {
      resFlag = true;
    } else {
      resFlag = false;
    }
    isMobile = $('#js-is-mobile').val();
    dealerId = $('#js-staff-id').val();
    this.loadData(id);
  },

  // 订单数据处理
  loadData: function(workOrderId) {
    var param = {
      'workOrderId': workOrderId
    };
    common.ajax({
      url: common.root + '/workOrder/getWorkOrderDetail.do',
      dataType: 'json',
      contentType: 'application/json; charset=utf-8',
      encode: false,
      data: JSON.stringify(param),
      loadfun: function(isloadsucc, data) {
        if (isloadsucc) {
          // 父工单信息
          workOrder = data.workOrder;
          if (resFlag) {
            // 创建时间轴
            baseWorkOrder.loadStaft(workOrderId);
          }
          // 看房订单信息
          respData = data.subOrder;
          // 看房订单转成MAP
          jsComplaintOrderDetail.createComplaintOrderValueMap(respData);
          // 赋值父工单信息
          jsComplaintOrderDetail.generatorData(respData);
          // 赋值各流程信息
          jsComplaintOrderDetail.generatorHtml(respData);
          // 初始化各TAB页
          jsComplaintOrderDetail.initTab(respData);
        }
      }
    });
  },

  // 看房订单转成MAP
  createComplaintOrderValueMap: function(data) {
    var valueList = data.subOrderValueList;
    for (var i = 0; i < valueList.length; i++) {
      var complaintOrderValue = valueList[i];
      var key = complaintOrderValue.attrPath;
      complaintOrderValueMap[key] = complaintOrderValue;
    }
  },

  // 赋值父工单信息
  generatorData: function(data) {
    $('.js-tab > li').find("a[href='#" + data.stateName + "']").parent().addClass('active');
    $('#' + data.stateName).addClass('in active');
    $('.js-code').html(data.code);
    $('.js-order-name').html(workOrder.name);
    $('.js-state-name').html(data.stateName);
    $('.js-create-date').html(data.createdDate);
    $('.js-order-comments').html(data.comments);
    $('.js-appointment-date').html(data.complaintDate);
    var createdStaffName = "";
    if (workOrder.createdStaffName != null && workOrder.createdStaffName != "") {
      createdStaffName = workOrder.createdStaffName;
    } else if (workOrder.createdUserName != null && workOrder.createdUserName != "") {
      createdStaffName = workOrder.createdUserName;
    } else if (workOrder.userName != null && workOrder.userName != "") {
      createdStaffName = workOrder.userName;
    }
    $('.js-created-person').html(createdStaffName);
    $('.js-user-phone').html(workOrder.userPhone);
    if (workOrder.userPhone != null && workOrder.userPhone != "" && isMobile == "Y") {
      $('.js-user-phone').click(function() {
        njyc.phone.callSomeOne(workOrder.userPhone);
      });
      $('.js-user-phone').css('color', '#0040ff');
    }
    $('.js-user-name').html(workOrder.userName);
    if (isMobile == "Y") {
      var picurls = data.imageUrl;
      if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined && picurls != 'undefined') {
        njyc.phone.showPic(jsComplaintOrderDetail.changeImagePath(picurls), 'js-mobile-upurl');
        $('#js-mobile-upurl b').addClass('hidden');
      } else {
        if ($('#js-mobile-upurl') != null && $('#js-mobile-upurl') != undefined && $('#js-mobile-upurl') != 'undefined') {
          $('#js-mobile-upurl').addClass('remove');
        }
      }
      
    } else {
      // 图片回显
      var picurls = data.imageUrl;
      if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined && picurls != 'undefined') {
        var pas = picurls.split(",");
        var paths = new Array();
        for (var int = 0; int < pas.length; int++) {
          if (int == 0) {
            paths.push({
              path: pas[int],
              first: 1
            });
          } else {
            paths.push({
              path: pas[int],
              first: 0
            });
          }
        }
        common.dropzone.init({
          id: '#upurl-3',
          defimg: paths,
          maxFiles: 10,
          clickEventOk: false
        });
      } else {
        common.dropzone.init({
          id: '#upurl-3',
          maxFiles: 10,
          clickEventOk: false
        });
      }
    }


  },

  // 赋值各流程信息
  generatorHtml: function(data) {
    // 客服派单
    this.customerServAssignHtml(data);
    // 管家接单
    this.butlerTakeOrderHtml(data);
    // 管家执行
    this.butlerExecuteHtml(data);
    // 客服回访
    // this.customerServVisitHtml(data);
    // 用户评价
    this.generatorJudgeHtml(data);
  },

  // 初始化各流程TAB页面
  initTab: function(data) {
    if (!resFlag) {
      $("#managerSelect").attr("disabled", true);
      $("#managerSelect1").attr("disabled", true);
      $("#managerSelect2").attr("disabled", true);
    }
    switch (data.state) {
      case subOrderStateDef.ASSIGN_ORDER: // 客服派单
        // 显示按钮和备注
        $('.js-tab > li').find("a[href='#CUSTOMER_SERV_ASSIGN']").parent().addClass('active');
        $('#CUSTOMER_SERV_ASSIGN').addClass('in active');
        // 显示指派按钮
        if (!resFlag) {
          return;
        }

        $("#managerSelect").attr('readonly', false);
        $("#confirmReassignOrderBtn").removeClass("hidden");
        $(".js-comments-textarea").attr('readonly', false);
        break;
      case subOrderStateDef.REASSIGNING:
      case subOrderStateDef.REASSIGNING_IN_STAFF_REVIEW:
      case subOrderStateDef.TAKE_ORDER: // 管家接单
        // 显示按钮和备注
        $('.js-tab > li').find("a[href='#BUTLER_TAKE_ORDER']").parent().addClass('active');
        $('#BUTLER_TAKE_ORDER').addClass('in active');
        // 显示按钮
        if (!resFlag) {
          return;
        }
        $("#butlerGetHomeBtn").removeClass("hidden");
        $("#rework").removeClass("hidden");
        $(".js-house-inspection-comments-textarea").attr('readonly', false);
        break;
      case subOrderStateDef.DO_IN_ORDER:
        // 管家执行，显示按钮和备注
        $('.js-tab > li').find("a[href='#BUTLER_EXECUTE']").parent().addClass('active');
        $('#BUTLER_EXECUTE').addClass('in active');
        // 显示按钮
        if (!resFlag) {
          return;
        }
        $("#rentalAccountPassBtn").removeClass("hidden");
        $("#rentalAccountBtn").removeClass("hidden");
        $(".js-rental-account-comments-textarea").attr('readonly', false);
        break;
      case subOrderStateDef.STAFF_REVIEW: // 客服回访
        // 显示按钮和备注
        $('.js-tab > li').find("a[href='#CUSTOMER_SERV_VISIT']").parent().addClass('active');
        $('#CUSTOMER_SERV_VISIT').addClass('in active');
        // 显示按钮.
        if (!resFlag) {
          return;
        }
        $("#marketingExecutiveAuditPassBtn").removeClass("hidden");
        $("#rentalAccountReturn").removeClass("hidden");
        $("#reassignOrderBtn").removeClass("hidden");
        $(".js-marketing-executive-audit-comments-textarea").attr('readonly', false);
        break;
      case subOrderStateDef.WAIT_2_TACE: // 客服回访待追踪
        // 显示按钮和备注
        $('.js-tab > li').find("a[href='#CUSTOMER_SERV_VISIT']").parent().addClass('active');
        $('#CUSTOMER_SERV_VISIT').addClass('in active');
        // 显示按钮.
        if (!resFlag) {
          return;
        }
        $("#marketingExecutiveAuditPassBtn").removeClass("hidden");
        $("#rentalAccountReturn").removeClass("hidden");
        $("#reassignOrderBtn").removeClass("hidden");
        $(".js-marketing-executive-audit-comments-textarea").attr('readonly', false);
        break;
    }
  },

  // 客服派单
  customerServAssignHtml: function(data) {
    var butlerId = data.staffId;
    // AJAX获取管家信息
    common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '27,29',
          type: workOrder.type
      },
      async: false,
      loadfun: function(isloadsucc, json) {
        if (isloadsucc) {
          var html = "<option value=''> 请选择...</option>";
          for (var i = 0; i < json.length; i++) {
            html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
          }
          $('#managerSelect').html(html);
          if (butlerId != '' && butlerId != 'null' && butlerId != null && butlerId != undefined) {
            $("#managerSelect option[value='" + data.staffId + "']").attr("selected", "selected");
            $("#managerSelect").attr("disabled", "desabled");
          } else {
            $('#managerSelect').select2({
              'width': '200px'
            });
          }
        } else {
          common.alert({
            msg: common.msg.error
          });
        }
      }
    });
    // 赋值客服订单说明
    if (data.attrCatg.code == 'COMPLAINT_ORDER_PROCESS') {
      for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
        var childCatg = data.attrCatg.attrCatgChildren[i];
        if (childCatg.code == 'CUSTOMER_SERV_ASSIGN') {
          var childAttrList = childCatg.attrChildren;
          for (var j = 0; j < childAttrList.length; j++) {
            var defaultPath = "COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.";
            var childAttr = childAttrList[j];
            var attrPath = defaultPath + childAttr.code;
            childAttr.attrPath = attrPath;
            if (childAttr.code == 'COMMENTS') {
              $('.js-comments-label').html(childAttr.name);
              $('.js-comments-textarea').data('attr', childAttr);
            }
          }
        }
      }
    }

    var text = complaintOrderValueMap['COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS'];
    if (text != '' && text != 'null' && text != null && text != undefined) {
      text = text.textInput;
      // 设置备注回显
      $('.js-comments-textarea').text(text);
    }
  },

  // 管家接单
  butlerTakeOrderHtml: function(data) {
    // 赋值客服订单说明COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS
    if (data.attrCatg.code == 'COMPLAINT_ORDER_PROCESS') {
      for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
        var childCatg = data.attrCatg.attrCatgChildren[i];
        if (childCatg.code == 'BUTLER_TAKE_ORDER') {
          var childAttrList = childCatg.attrChildren;
          for (var j = 0; j < childAttrList.length; j++) {
            var defaultPath = "COMPLAINT_ORDER_PROCESS.BUTLER_TAKE_ORDER.";
            var childAttr = childAttrList[j];
            var attrPath = defaultPath + childAttr.code;
            childAttr.attrPath = attrPath;
            if (childAttr.code == 'COMMENTS') {
              $('.js-house-inspection-comments-label').html(childAttr.name);
              $('.js-house-inspection-comments-textarea').data('attr', childAttr);
            }
          }
        }
      }
    }

    var text = complaintOrderValueMap['COMPLAINT_ORDER_PROCESS.BUTLER_TAKE_ORDER.COMMENTS'];
    if (text != '' && text != 'null' && text != null && text != undefined) {
      text = text.textInput;
      // 设置备注回显
      $('.js-house-inspection-comments-textarea').text(text);
    }
  },

  // 管家执行
  butlerExecuteHtml: function(data) {
    if (data.attrCatg.code == 'COMPLAINT_ORDER_PROCESS') {
      for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
        var childCatg = data.attrCatg.attrCatgChildren[i];
        if (childCatg.code == 'BUTLER_EXECUTE') {
          var childAttrList = childCatg.attrChildren;
          for (var j = 0; j < childAttrList.length; j++) {
            var defaultPath = "COMPLAINT_ORDER_PROCESS.BUTLER_EXECUTE.";
            var childAttr = childAttrList[j];
            var attrPath = defaultPath + childAttr.code;
            childAttr.attrPath = attrPath;
            if (childAttr.code == 'COMMENTS') {
              $('.js-rental-account-comments-label').html(childAttr.name);
              $('.js-rental-account-comments-textarea').data('attr', childAttr);
            }
          }
        }
      }
    }

    var text = complaintOrderValueMap['COMPLAINT_ORDER_PROCESS.BUTLER_EXECUTE.COMMENTS'];
    if (text != '' && text != 'null' && text != null && text != undefined) {
      text = text.textInput;
      // 设置备注回显
      $('.js-rental-account-comments-textarea').text(text);
    }
  },

  // 客服回访TAB页
  customerServVisitHtml: function(data) {
    if (data.attrCatg.code == 'COMPLAINT_ORDER_PROCESS') {
      for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
        var childCatg = data.attrCatg.attrCatgChildren[i];
        if (childCatg.code == 'CUSTOMER_SERV_VISIT') {
          var childAttrList = childCatg.attrChildren;
          for (var j = 0; j < childAttrList.length; j++) {
            var defaultPath = "COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_VISIT.";
            var childAttr = childAttrList[j];
            var attrPath = defaultPath + childAttr.code;
            childAttr.attrPath = attrPath;
            if (childAttr.code == 'COMMENTS') {
              $('.js-marketing-executive-audit-comments-label').html(childAttr.name);
              $('.js-marketing-executive-audit-comments-textarea').data('attr', childAttr);
            }
            if (childAttr.code == 'PASSED') {
              // $('#confirmReassignOrderBtn1').html(childAttr.name);//
              $('#confirmReassignOrderBtn1').data('attr', childAttr);
              $('#marketingExecutiveAuditPassBtn').data('attr', childAttr);
            }
          }
        }
      }
    }
    var text = complaintOrderValueMap['COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_VISIT.COMMENTS'];
    if (text != '' && text != 'null' && text != null && text != undefined) {
      text = text.textInput;
      // 设置备注回显
      $('.js-marketing-executive-audit-comments-textarea').text(text);
    }
  },
  // 用户评价
  generatorJudgeHtml: function(data) {
    if (data.attrCatg.code == 'COMPLAINT_ORDER_PROCESS') {
      var comments = '';
      var commentDate = '';
      var picurls = '';
      var html = '<div class="form-group col-md-12 col-xs-12"> ' +
        '<label class="col-md-2 col-xs-4 control-label">用户评分</label> ' +
        '</div><div class="form-group col-md-12 col-xs-12">'
      if (workOrder.orderCommentaryList.length > 0) {
        comments = workOrder.orderCommentaryList[0].comments;
        commentDate = workOrder.orderCommentaryList[0].commentDate;
        picurls = workOrder.orderCommentaryList[0].imageUrl;
      }
      for (var i = 0; i < workOrder.orderCommentaryList.length; i++) {
        var commentary = workOrder.orderCommentaryList[i];
        html += '<label class="col-md-2 col-xs-4 control-label">' + commentary.typeName + '</label>' +
          '<label class="col-md-2 col-xs-4 control-label" style="'
        var color = '#993300'
        if (parseInt(commentary.score) < 3) {
          color = '#ff0000';
        } else if (parseInt(commentary.score) > 3) {
          color = '#778899'
        }
        html += 'color: ' + color + ';text-align:left;white-space:nowrap;">' + commentary.score + '</label>';
      }
      html += '</div>';
      $('#judgeAuditForm').prepend(html);
      $('.js-judge-audit-time-label-value').html(commentDate);
      $('.js-judge-audit-comments-textarea').attr('value', comments);

      // 图片回显
      if (isMobile == "Y") {
        njyc.phone.showPic(jsComplaintOrderDetail.changeImagePath(picurls), 'js-judge-audit-image-url-mobile');
        if (data.state != 'D') {
          $('#image-upurl-1').addClass('hidden');
          $('#image-upurl-1 b').addClass('hidden');
        }
      } else {
        if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
          jsComplaintOrderDetail.initPicurls(picurls, 'image-upurl-1', true);
        } else {
          jsComplaintOrderDetail.initPicurls(picurls, 'image-upurl-1', false);
        }
      }
    }
  },

  // 图片显示
  initPicurls: function(picurls, id, flag) {
    if (picurls != '' && picurls != 'null' && picurls != null && picurls != undefined && picurls != 'undefined') {
      var pas = picurls.split(",");
      var paths = new Array();
      for (var int = 0; int < pas.length; int++) {
        if (int == 0) {
          paths.push({
            path: pas[int],
            first: 1
          });
        } else {
          paths.push({
            path: pas[int],
            first: 0
          });
        }
      }
      common.dropzone.init({
        id: '#' + id,
        defimg: paths,
        maxFiles: 10,
        clickEventOk: flag
      });
    } else {
      common.dropzone.init({
        id: '#' + id,
        maxFiles: 10,
        clickEventOk: flag
      });
    }
  },


  // 客服指派操作
  assignOrder: function() {
    // 判断是否选择管家
    if ($('#managerSelect').val() == null || $('#managerSelect').val() == "") {
      common.alert({
        msg: "请选择指派人！"
      });
      return;
    }

    var manager = $('#managerSelect option:selected').text();
    common.alert({
      msg: "确定指派给(" + manager + ")吗？",
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var commentsData = $('.js-comments-textarea').data('attr');
          var param = {
            'code': respData.code,
            'staffId': $('#managerSelect').val(),
            'dealerId': dealerId,
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': commentsData.id,
              'attrPath': commentsData.attrPath,
              'textInput': $('.js-comments-textarea').val()
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/assignOrder.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },

  rework: function() {
    // 显示按钮
    $("#butlerGetHomeBtn").addClass("hidden");
    $("#rework").addClass("hidden");
    $("#butlerGetHomeBtn").addClass("hidden");
    $("#reassignOrder").removeClass("hidden");
    $("#reback").removeClass("hidden");
    $("#reback").removeClass("hidden");
    $(".js-reassign-manager-div2").removeClass("hidden");

    // AJAX获取管家信息
    common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '27,29',
          type: workOrder.type
      },
      async: false,
      loadfun: function(isloadsucc, json) {
        if (isloadsucc) {
          var html = "<option value=''> 请选择...</option>";
          for (var i = 0; i < json.length; i++) {
            html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
          }
          $('#managerSelect2').html(html);
          $('#managerSelect2').select2({
            'width': '200px'
          });
        } else {
          common.alert({
            msg: common.msg.error
          });
        }
      }
    });
  },
  reback: function() {
    // 显示按钮
    $("#butlerGetHomeBtn").removeClass("hidden");
    $("#rework").removeClass("hidden");
    $("#butlerGetHomeBtn").removeClass("hidden");
    $("#reassignOrder").addClass("hidden");
    $("#reback").addClass("hidden");
    $("#reback").addClass("hidden");
    $(".js-reassign-manager-div2").addClass("hidden");
  },
  // 客服指派操作(重新指派)
  assignOrder1: function() {
    // 判断是否选择管家
    if ($('#managerSelect2').val() == null || $('#managerSelect2').val() == "") {
      common.alert({
        msg: "请选择指派人！"
      });
      return;
    }
    var manager = $('#managerSelect2 option:selected').text();
    common.alert({
      msg: "确定指派给(" + manager + ")吗？",
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
          var param = {
            'code': respData.code,
            'staffId': $('#managerSelect2').val(),
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': commentsData.id,
              'attrPath': commentsData.attrPath,
              'textInput': $('.js-house-inspection-comments-textarea').val()
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/reassignOrder.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },

  // 管家接单
  takeOrder: function() {
    common.alert({
      msg: "确定保存吗？",
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
          var param = {
            'code': respData.code,
            'dealerId': dealerId,
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': commentsData.id,
              'attrPath': commentsData.attrPath,
              'textInput': $('.js-house-inspection-comments-textarea').val()
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/takeOrder.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },

  // 管家执行订单
  processOrder: function(flag) {

    var str = "";
    if (flag == 'Y') {
      str = "确定提交吗？";
    } else {
      str = "确定保存吗？";
    }

    common.alert({
      msg: str,
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var commentsData = $('.js-rental-account-comments-textarea').data('attr');
          var param = {
            'code': respData.code,
            'submitFlag': flag,
            'dealerId': dealerId,
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': commentsData.id,
              'attrPath': commentsData.attrPath,
              'textInput': $('.js-rental-account-comments-textarea').val()
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/processOrder.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },

  // 客服重新指派
  reassignOrderInStaffReview: function() {
    // 隐藏重新指派按钮，显示管家列表，显示返回、指派按钮
    $("#reassignOrderBtn").addClass("hidden");
    $("#marketingExecutiveAuditPassBtn").addClass("hidden");
    $("#rentalAccountReturn").addClass("hidden");
    $("#confirmReassignOrderBtn1").removeClass("hidden");
    $("#cancelReassignOrderBtn").removeClass("hidden");
    $(".js-reassign-manager-div1").removeClass("hidden");
    // AJAX获取管家信息
    common.ajax({
      url: common.root + '/cascading/getUserListByAuthority.do',
      dataType: 'json',
      data: {
          roleId: '27,29',
          type: workOrder.type
      },
      async: false,
      loadfun: function(isloadsucc, json) {
        if (isloadsucc) {
          var html = "<option value=''> 请选择...</option>";
          for (var i = 0; i < json.length; i++) {
            html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
          }
          $('#managerSelect1').html(html);
          $('#managerSelect1').select2({
            'width': '200px'
          });
        } else {
          common.alert({
            msg: common.msg.error
          });
        }
      }
    });
  },
  // 客服指派操作(重新指派)
  assignOrder2: function() {
    // 判断是否选择管家
    if ($('#managerSelect1').val() == null || $('#managerSelect1').val() == "") {
      common.alert({
        msg: "请选择指派人！"
      });
      return;
    }
    var manager = $('#managerSelect1 option:selected').text();
    common.alert({
      msg: "确定指派给(" + manager + ")吗？",
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var commentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
          var butData = $('#confirmReassignOrderBtn1').data('attr');
          var param = {
            'code': respData.code,
            'staffId': $('#managerSelect1').val(),
            'dealerId': dealerId,
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': commentsData.id,
              'attrPath': commentsData.attrPath,
              'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
            }, {
              'complaintOrderId': respData.id,
              'attrId': butData.id,
              'attrPath': butData.attrPath,
              'textInput': 'N'
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/reassignOrderInStaffReview.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },

  // 返回按钮
  cancelReassignOrder: function() {

    // 显示重新指派、通过按钮，隐藏管家列表、返回、指派按钮
    $("#reassignOrderBtn").removeClass("hidden");
    $("#marketingExecutiveAuditPassBtn").removeClass("hidden");
    $("#rentalAccountReturn").removeClass("hidden");
    $("#confirmReassignOrderBtn1").addClass("hidden");
    $("#cancelReassignOrderBtn").addClass("hidden");
    $(".js-reassign-manager-div1").addClass("hidden");
  },

  // 关闭
  closeOpenedWin: function(isloadsucc, data) {
    if (isloadsucc) {
      if (data.state == 1) {
        common.alert({
          msg: '操作成功',
          fun: function() {
            window.opener = null;
            window.open("", "_self");
            window.close();
            if (window) {
              window.location.href = "about:blank";
            }
          }
        });
        if (isMobile == 'Y') {
          window.location.href = 'http://manager.room1000.com?TASK_SUBMIT_SUCCESS=1';
        } else {
          // common.closeWindow('complaintOrderDetail', 3);
        }
      } else {
        common.alert({
          msg: common.msg.error
        });
      }
    }
  },

  // 客服通过
  marketingExecutiveAuditPass: function() {
    common.alert({
      msg: '确定审批通过？',
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var marketingExecutiveAuditCommentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
          var butData = $('#marketingExecutiveAuditPassBtn').data('attr');
          var param = {
            'code': respData.code,
            'dealerId': dealerId,
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': marketingExecutiveAuditCommentsData.id,
              'attrPath': marketingExecutiveAuditCommentsData.attrPath,
              'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
            }, {
              'complaintOrderId': respData.id,
              'attrId': butData.id,
              'attrPath': butData.attrPath,
              'textInput': 'Y'
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/passInStaffReview.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },
changeImagePath: function(images) {
        if (!images || images.length == 0) {
            return '';
        }
        return images.replace(/,/g, '|');
    },  

  rentalAccountReturn: function() {
    common.alert({
      msg: '确定跟踪？',
      confirm: true,
      fun: function(action) {
        if (action) {
          common.load.load();
          var marketingExecutiveAuditCommentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
          var param = {
            'code': respData.code,
            'dealerId': dealerId,
            'subOrderValueList': [{
              // 'complaintOrderId': respData.id,
              'attrId': marketingExecutiveAuditCommentsData.id,
              'attrPath': marketingExecutiveAuditCommentsData.attrPath,
              'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
            }]
          };
          common.ajax({
            url: common.root + '/complaintOrder/staffTace.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
              common.load.hide();
              jsComplaintOrderDetail.closeOpenedWin(isloadsucc, data);
            }
          });
        }
      }
    });
  },

  marketingExecutiveAuditReturn: function() {},

  financeAuditPass: function() {},

  financeAuditReturn: function() {},

};
$(function() {
  jsComplaintOrderDetail.init();
});