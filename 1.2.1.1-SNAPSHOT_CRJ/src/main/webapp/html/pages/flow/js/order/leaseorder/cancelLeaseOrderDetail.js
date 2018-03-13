var respData;
var workOrder;
var isMobile;
var dealerId;
var isLeader;
var cancelLeaseOrderValueMap = {};
var jsCancelLeaseOrderDetail = {
		rankHouseId_ : undefined,
		title_ : undefined,
		rankType_ : undefined,
		houseId_ : undefined,
		agreementId_ : undefined,
		flag_ : undefined,
    // 初始化数据信息
    init: function() {
        var id = $('#js-work-order-id').val();
        // true为可处理，false不可处理
        if (common.getCookie('resFlag' + (id)) == '1' || common.getCookie('resFlag' + (id)) == 'undefined' || common.getCookie('resFlag' + (id)) == '') {
            resFlag = true;
        } else {
            resFlag = false;
        }
        isMobile = $('#js-is-mobile').val();
        dealerId = $('#js-staff-id').val();
        isLeader = $('#js-is-leader').val();
        this.loadData(id);
    },
  
    // 处理验房说明的内容
    commentsDeal: function(valStr) {
    	// 如果为空，则显示默认值
    	var str = valStr.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '');
    	$('.js-house-inspection-comments-textarea').val(str);
    },

    /**
		 * 加载各项数据信息
		 * 
		 * @param {type}
		 *          workOrderId
		 * @returns {undefined}
		 */
    loadData: function(workOrderId) {
        common.load.load();
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
                    // console.log("------------1------------");
                    // console.log(data);
                    // console.log("------------1------------");
                    // 父工单信息
                    workOrder = data.workOrder;
                    if (resFlag) {
                        // 创建时间轴
                        baseWorkOrder.loadStaft(workOrderId);
                    }
                    common.load.hide();
                    // 退租工单信息
                    respData = data.subOrder;
                    // 退租订单转成MAP

                    jsCancelLeaseOrderDetail.createCancelLeaseOrderValueMap(data.subOrder);
                    // 赋值父工单信息
                    jsCancelLeaseOrderDetail.generatorData(data.subOrder);
                    /**
										 * 赋值各流程信息
										 */
                    jsCancelLeaseOrderDetail.generatorHtml(data.subOrder);
                    jsCancelLeaseOrderDetail.createFinanceIncomeTable(data.subOrder.rentalLeaseOrderId);
                    jsCancelLeaseOrderDetail.createFinancePayTable(data.subOrder.rentalLeaseOrderId);
                    // 初始化各TAB页
                    jsCancelLeaseOrderDetail.initTab(data.subOrder);

                }
            }
        });
    },

    // 退租订单转成MAP
    createCancelLeaseOrderValueMap: function(data) {
        // console.log(data);
        var valueList = data.subOrderValueList;
        for (var i = 0; i < valueList.length; i++) {
            var cancelLeaseOrderValue = valueList[i];
            var key = cancelLeaseOrderValue.attrPath;
            cancelLeaseOrderValueMap[key] = cancelLeaseOrderValue;
        }
    },

    /**
		 * 赋值各流程信息
		 * 
		 * @param {type}
		 *          data
		 * @returns {undefined}
		 */
    generatorHtml: function(data) {
        // 管家接单
        this.generatorTakeOrderHtml(data);
        // 房源释放
        this.generatorHouseReleaseHtml(data);
        // 管家上门
        this.generatorButlerGetHomeHtml(data);
        // 租务核算
        this.generatorRentalAccountHtml(data);
        // 市场部高管审批
        this.generatorMarketingExecutiveAuditHtml(data);
        // 财务审批
        this.generatorFinanceAuditHtml(data);
        // 用户评价
        this.generatorJudgeHtml(data);
    },

    generatorTakeOrderData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.TAKE_ORDER.COMMENTS'] != null) {
            $('.js-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.TAKE_ORDER.COMMENTS'].textInput);
        }
    },

    generatorTakeOrderReadOnlyHtml: function() {
        this.generatorTakeOrderData();
        $('.js-comments-textarea').attr('readonly', true);
        $('#takeOrderBtn').addClass('hidden');
        $('#reassignOrderBtn').addClass('hidden');
    },

    generatorReleaseHouseRankData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.HOUSE_RELEASE.COMMENTS'] != null) {
            $('.js-comments-release-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.HOUSE_RELEASE.COMMENTS'].textInput);
        }
    },

    generatorReleaseHouseRankReadOnlyHtml: function() {
        this.generatorReleaseHouseRankData();
        $('.js-comments-release-textarea').attr('readonly', true);
        $('#houseReleaseBtn').addClass('hidden');
        $('#houseCloseBtn').addClass('hidden');
    },
    // 
    generatorButlerGetHomeData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ALIPAY'] != null) {
            $('.js-alipay-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ALIPAY'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.BANK_CARD_NBR'] != null) {
            $('.js-bank-card-nbr-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.BANK_CARD_NBR'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.BANK_ADDRESS'] != null) {
            $('.js-bank-adress-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.BANK_ADDRESS'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM'] != null) {
            $('.js-water-nbr-from-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_TO'] != null) {
            $('.js-water-nbr-to-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_TO'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM'] != null) {
            $('.js-gas-nbr-from-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_TO'] != null) {
            $('.js-gas-nbr-to-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_TO'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM'] != null) {
            $('.js-ammeter-nbr-from-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_TO'] != null) {
            $('.js-ammeter-nbr-to-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_TO'].textInput);
        }

        // 图片展示
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.CANCEL_LEASE_REASON'] != null) {
            var cancelLeaseReasonSelected = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.CANCEL_LEASE_REASON'].textInput;
            $(".js-cancel-lease-reason-select").find("option[value='" + cancelLeaseReasonSelected + "']").attr('selected', true);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.PROMOTION_EXPLANATION'] != null) {
            $('.js-promotion-explanation-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.PROMOTION_EXPLANATION'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.KEY_RECOVERY'] != null) {
            $('.js-key-recovery-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.KEY_RECOVERY'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.DOOR_CARD_RECOVERY'] != null) {
            $('.js-door-card-recovery-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.DOOR_CARD_RECOVERY'].textInput);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.OTHER_RECOVERY'] != null) {
            $('.js-other-recovery-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.OTHER_RECOVERY'].textInput);
        }

        // 图片展示
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_INSPECTION_COMMENTS'] != null) {
            $('.js-house-inspection-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_INSPECTION_COMMENTS'].textInput);
        } else {
        	// 如果为空，则显示默认信息
        	var str = "特殊审批流程单号：\n"+ respData.typeName + "：\n" + "退预缴费（）+退押金()+退房租（合计数）（期间）+已交水（）+已交电（）+已交燃（）+已交物业（）-扣房租（合计数）（期间）-应交水（）-应交电（）-应交燃（）-应交物业费（）-活动追缴款（）（期间）-赔偿款()（赔偿XXX费）-保洁费(   ）=实退（）";
        	$('.js-house-inspection-comments-textarea').html(str);
        }
    },

    generatorButlerGetHomeReadOnlyHtml: function() {
        this.generatorButlerGetHomeData();
        $('.js-alipay-input').attr('readonly', true);
        $('.js-bank-card-nbr-input').attr('readonly', true);
        $('.js-bank-adress-input').attr('readonly', true);
        $('.js-water-nbr-from-input').attr('readonly', true);
        $('.js-water-nbr-to-input').attr('readonly', true);
        $('.js-gas-nbr-from-input').attr('readonly', true);
        $('.js-gas-nbr-to-input').attr('readonly', true);
        $('.js-ammeter-nbr-from-input').attr('readonly', true);
        $('.js-ammeter-nbr-to-input').attr('readonly', true);
        // TODO: NBR_ATTACHMENT_UPLOAD 灰化
        $('.js-nbr-attachment-upload').attr('readonly', true);
        $('.js-cancel-lease-reason-select').attr('readonly', true);
        $('.js-cancel-lease-reason-select > option').addClass('hidden');
        $('.js-promotion-explanation-input').attr('readonly', true);
        $('.js-key-recovery-input').attr('readonly', true);
        $('.js-door-card-recovery-input').attr('readonly', true);
        $('.js-other-recovery-input').attr('readonly', true);
        // TODO: HOUSE_PICTURE_UPLOAD 灰化
        $('.js-house-picture-upload').attr('readonly', true);
        $('.js-house-inspection-comments-textarea').attr('readonly', true);
        $('#butlerGetHomeBtn').addClass('hidden');
        $('#butlerGetBtn').addClass('hidden');
    },

    generatorRentalAccountData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.FINANCE_TYPE'] != null) {
            var financeTypeSelected = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.FINANCE_TYPE'].textInput;
            $('.js-finance-type-radio').find("input[value='" + financeTypeSelected + "']").attr('checked', 'checked');
        }
        var totalMoney = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY'];
        if (totalMoney != null) {
            if (totalMoney.textInput < 0) {
                $('.js-total-money-input').val(-totalMoney.textInput);
            } else {
                $('.js-total-money-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.TOTAL_MONEY'].textInput);
            }
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.COMMENTS'] != null) {
            $('.js-rental-account-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.COMMENTS'].textInput);
        }
    },

    generatorRentalAccountReadOnlyHtml: function() {
        this.generatorRentalAccountData();
        $('#createCostBtn').addClass('hidden');
        $('.js-finance-type-radio input').attr('disabled', true);
        $('.js-total-money-input').attr('readonly', true);
        $('.js-rental-account-comments-textarea').attr('readonly', true);
        $('#rentalAccountPassBtn').addClass('hidden');
        $('#rentalAccountReturnBtn').addClass('hidden');
    },

    generatorMarketingExecutiveAuditData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.COMMENTS'] != null) {
            $('.js-marketing-executive-audit-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.COMMENTS'].textInput);
        }
    },

    generatorMarketingExecutiveAuditReadOnlyHtml: function() {
        this.generatorMarketingExecutiveAuditData();
        $('.js-marketing-executive-audit-comments-textarea').attr('readonly', true);
        $('#marketingExecutiveAuditPassBtn').addClass('hidden');
        $('#marketingExecutiveAuditReturnBtn').addClass('hidden');
    },

    generatorFinanceAuditData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.FINANCE_AUDIT.COMMENTS'] != null) {
            $('.js-finance-audit-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.FINANCE_AUDIT.COMMENTS'].textInput);
        }
    },

    generatorFinanceAuditReadOnlyHtml: function() {
        this.generatorFinanceAuditData();
        $('.js-finance-audit-comments-textarea').attr('readonly', true);
        $('#financeAuditPassBtn').addClass('hidden');
        $('#financeAuditReturnBtn').addClass('hidden');
    },

    imageDeal: function(path, id, readonly) {
        // readonly == true;
        if (path != null && path != "") {
            var pas = path.split(",");
            var paths = new Array();
            for (var i = 0; i < pas.length; i++) {
                if (i == 0) {
                    paths.push({
                        path: pas[i],
                        first: 1
                    });
                } else {
                    paths.push({
                        path: pas[i],
                        first: 0
                    });
                }
            }
            common.dropzone.init({
                id: '#' + id,
                defimg: paths,
                maxFiles: 10,
                clickEventOk: readonly
            });
        } else {
            common.dropzone.init({
                id: '#' + id,
                maxFiles: 10,
                clickEventOk: readonly
            });
        }
    },

    imagework: function(id) {
        // 读取图片路径
        var filepath = common.dropzone.getFiles('#' + id);
        var pathe = "";
        var returnI = false;
        for (var i = 0; i < filepath.length; i++) { // 首图判断
            if (filepath[i].fisrt == 1) {
                pathe = filepath[i].path + ',' + pathe;
            } else {
                pathe += filepath[i].path + ",";
            }
            returnI = true;
        } // 图片截取
        if (returnI) {
            pathe = pathe.substring(0, pathe.length - 1);
        }
        return pathe;
    },

    initTab: function(data) {
        switch (data.state) {
            case subOrderStateDef.REASSIGNING:
            case subOrderStateDef.TAKE_ORDER:
                $('.js-tab > li').find("a[href='#TAKE_ORDER']").parent().addClass('active');
                $('#TAKE_ORDER').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderData();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                }
                break;
            case subOrderStateDef.RELEASE_HOUSE_RANK: // 房源释放
                $('.js-tab > li').find("a[href='#HOUSE_RELEASE']").parent().addClass('active');
                $('#HOUSE_RELEASE').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankData();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                }
                break;
            case subOrderStateDef.DO_IN_ORDER:
            case subOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING:
                $('.js-tab > li').find("a[href='#BUTLER_GET_HOME']").parent().addClass('active');
                $('#BUTLER_GET_HOME').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeData();
                jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                }
                break;
            case subOrderStateDef.RENTAL_ACCOUNTING:
                $('.js-tab > li').find("a[href='#TAKE_ORDER']").parent().removeClass('active');
                $('.js-tab > li').find("a[href='#RENTAL_ACCOUNT']").parent().addClass('active');
                $('#RENTAL_ACCOUNT').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountData();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithBtn(data);
                $("#createCostBtn").removeClass("hidden");
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                }
                break;
            case subOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING:
                $('.js-tab > li').find("a[href='#RENTAL_ACCOUNT']").parent().addClass('active');
                $('#RENTAL_ACCOUNT').addClass('in active');
                $('#TAKE_ORDER').removeClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountData();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithBtn(data);
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                }
                break;
            case subOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING:
                $('.js-tab > li').find("a[href='#RENTAL_ACCOUNT']").parent().addClass('active');
                $('#RENTAL_ACCOUNT').addClass('in active');
                $('#TAKE_ORDER').removeClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountData();
                jsCancelLeaseOrderDetail.createWait2PayTableWithBtn(data);
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                }
                break;
            case subOrderStateDef.MARKETING_EXECUTIVE_AUDITING: // 市场部高管审批
                $('.js-tab > li').find("a[href='#MARKETING_EXECUTIVE_AUDIT']").parent().addClass('active');
                $('#MARKETING_EXECUTIVE_AUDIT').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditData();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (respData.type == 'C') {
                    // 未入住违约退租，高管审批屏蔽打回按钮
                    $("#marketingExecutiveAuditReturnBtn").addClass('hidden');
                }
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                }
                break;
            case subOrderStateDef.FINANCE_AUDITING:
                $('.js-tab > li').find("a[href='#FINANCE_AUDIT']").parent().addClass('active');
                $('#FINANCE_AUDIT').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditData();
                jsCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                }
                break;
            default:
                $('.js-tab > li').find("a[href='#TAKE_ORDER']").parent().addClass('active');
                $('#TAKE_ORDER').addClass('in active');
                jsCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                break;
        }
    },
    // 退租时间
    appointmentDate: null,
    generatorData: function(data) {
        $('.js-tab > li').find("a[href='#" + data.stateName + "']").parent().addClass('active');
        $('#' + data.stateName).addClass('in active');
        $('.js-code').html(data.code);
        $('.js-state-name').html(data.stateName);
        $('.js-type').html(data.typeName);
        $('.js-create-date').html(data.createdDate);
        $('.js-order-comments').html(data.comments);
        $('.js-appointment-date').html(workOrder.appointmentDate);
        this.appointmentDate = workOrder.appointmentDate;
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
            $('.js-user-phone').css('color', '#0040ff')
        }
        $('.js-user-name').html(workOrder.userName);
        $('.js-order-name').html(workOrder.name);

        common.ajax({
            url: common.root + '/rankHouse/loadAgreementInfo.do',
            data: {
                id: data.rentalLeaseOrderId
            },
            dataType: 'json',
            async: false,
            loadfun: function(isloadsucc, date) {
                if (isloadsucc) {
                    if (isMobile == 'Y') {
                        $('.js-rental-name').html(date.name);
                    } else {
                        $('.js-rental-name').html("<a onclick='jsCancelLeaseOrderDetail.sigleHouseInfo(" + date.house_rank_id + ",0," + date.house_id + ",\"" + date.rankType + "\",\"" + date.title + "\"," + data.rentalLeaseOrderId + ")'>" + date.name + "</a>");
                    }
                    $(".js-agree-code").html(date.agree_code);
                }
            }
        });
        this.createStateName(data);
    },

    createStateName: function(data) {
        var stateName;
        switch (data.state) {
            case subOrderStateDef.TAKE_ORDER:
                stateName = 'TAKE_ORDER';
                break;
            case subOrderStateDef.DO_IN_ORDER:
                stateName = 'BUTLER_GET_HOME';
                break;
            case subOrderStateDef.RENTAL_ACCOUNTING:
                stateName = 'RENTAL_ACCOUNT';
                break;
            case subOrderStateDef.MARKETING_EXECUTIVE_AUDITING:
                stateName = 'MARKETING_EXECUTIVE_AUDIT';
                break;
            case subOrderStateDef.FINANCE_AUDITING:
                stateName = 'FINANCE_AUDIT';
                break;
            default:
                stateName = 'TAKE_ORDER';
                break;
        }
        data.stateName = stateName;
    },

    addMandatory: function(attr, el) {
        if (attr.mandatory == 'Y') {
            el.prepend('<b style="color: red ;">*</b>');
        }
    },

    // 管家接单
    generatorTakeOrderHtml: function(data) {
        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
            for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
                var childCatg = data.attrCatg.attrCatgChildren[i];
                if (childCatg.code == 'TAKE_ORDER') {
                    var childAttrList = childCatg.attrChildren;
                    for (var j = 0; j < childAttrList.length; j++) {
                        var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.TAKE_ORDER.";
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
    },

    // 释放房源
    generatorHouseReleaseHtml: function(data) {
        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
            for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
                var childCatg = data.attrCatg.attrCatgChildren[i];
                if (childCatg.code == 'HOUSE_RELEASE') {
                    var childAttrList = childCatg.attrChildren;
                    for (var j = 0; j < childAttrList.length; j++) {
                        var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.HOUSE_RELEASE.";
                        var childAttr = childAttrList[j];
                        var attrPath = defaultPath + childAttr.code;
                        childAttr.attrPath = attrPath;
                        if (childAttr.code == 'COMMENTS') {
                            $('.js-comments-release-label').html(childAttr.name);
                            $('.js-comments-release-textarea').data('attr', childAttr);
                        }
                    }
                }
            }
        }
    },

    // 管家上门阶段，显示重新指派
    againAssignOrder: function() {
        $(".div-againAssignOrder").removeClass("hidden");
        $("#againAssignOrderCancelBtn").removeClass("hidden");
        $("#againAssignOrderBtn").removeClass("hidden");

        // 隐藏
        $(".div-butler-info").addClass("hidden");
        $("#butlerGetHomeBtn").addClass("hidden");
        $("#butlerGetBtn").addClass("hidden");
        $("#againAssignOrder").addClass("hidden");

        // AJAX获取管家信息
        common.ajax({
          url: common.root + '/cascading/getUserListByAuthority.do',
          dataType: 'json',
          data: {
              roleId: '29',
              type: workOrder.type
          },
            async: false,
            loadfun: function(isloadsucc, json) {
                if (isloadsucc) {
                    var html = "<option value=''> 请选择...</option>";
                    for (var i = 0; i < json.length; i++) {
                        html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
                    }
                    $('#butlerId').html(html);
                    $('#butlerId').select2({
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

    // 管家上门阶段，隐藏重新指派
    againAssignOrderCancelBtn: function() {
        $(".div-butler-info").removeClass("hidden");
        $("#butlerGetHomeBtn").removeClass("hidden");
        $("#butlerGetBtn").removeClass("hidden");
        $("#againAssignOrder").removeClass("hidden");

        // 隐藏
        $(".div-againAssignOrder").addClass("hidden");
        $("#againAssignOrderCancelBtn").addClass("hidden");
        $("#againAssignOrderBtn").addClass("hidden");
    },

    // 管家上门阶段，提交指派订单
    againAssignOrderBtn: function() {
        // 管家id
        var butlerId = $('#butlerId').val();
        if (butlerId == null || butlerId == "") {
            common.alert({
                msg: "请选择指派人！"
            });
            return;
        }

        // 获取当前订单id
        var workOrderId = $("#js-work-order-id").val();
        var manager = $('#butlerId option:selected').text();
        common.alert({
            msg: "确定指派给（" + manager + "）吗？",
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    common.ajax({
                        url: common.root + '/workOrder/directActivity.do',
                        dataType: 'json',
                        encode: false,
                        data: {
                            workOrderId: workOrderId,
                            butlerId: butlerId,
                            subOrderId: respData.id,
                            activityId: "C",
                            type: 'A'
                        },
                        loadfun: function(isloadsucc, data) {
                            if (data.state == 1) {
                            	common.load.hide();
                            	jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                            }
                        }
                    });
                }
            }
        });

    },

    // 管家上门
    generatorButlerGetHomeHtml: function(data) {

        // 判断当前用户是否是负责人
        if (isLeader == 'Y') {
            // 显示重新指派按钮
            $("#againAssignOrder").removeClass("hidden");
        }

        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
            for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
                var childCatg = data.attrCatg.attrCatgChildren[i];
                if (childCatg.code == 'BUTLER_GET_HOME') {
                    var childAttrList = childCatg.attrChildren;
                    for (var j = 0; j < childAttrList.length; j++) {
                        var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.";
                        var childAttr = childAttrList[j];
                        var attrPath = defaultPath + childAttr.code;
                        childAttr.attrPath = attrPath;
                        if (childAttr.code == 'ALIPAY') {
                            $('.js-alipay-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-alipay-label'));
                            $('.js-alipay-input').data('attr', childAttr);
                        } else if (childAttr.code == 'BANK_CARD_NBR') {
                            $('.js-bank-card-nbr-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-bank-card-nbr-label'));
                            $('.js-bank-card-nbr-input').data('attr', childAttr);
                        } else if (childAttr.code == 'BANK_ADDRESS') {
                            $('.js-bank-address-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-bank-address-label'));
                            $('.js-bank-adress-input').data('attr', childAttr);
                        } else if (childAttr.code == 'WATER_NBR_FROM') {
                            $('.js-water-nbr-from-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-water-nbr-from-label'));
                            $('.js-water-nbr-from-input').data('attr', childAttr);
                        } else if (childAttr.code == 'WATER_NBR_TO') {
                            $('.js-water-nbr-to-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-water-nbr-to-label'));
                            $('.js-water-nbr-to-input').data('attr', childAttr);
                        } else if (childAttr.code == 'GAS_NBR_FROM') {
                            $('.js-gas-nbr-from-label').html(childAttr.name);
                            $('.js-gas-nbr-from-input').data('attr', childAttr);
                        } else if (childAttr.code == 'GAS_NBR_TO') {
                            $('.js-gas-nbr-to-label').html(childAttr.name);
                            $('.js-gas-nbr-to-input').data('attr', childAttr);
                        } else if (childAttr.code == 'AMMETER_NBR_FROM') {
                            $('.js-ammeter-nbr-from-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-ammeter-nbr-from-label'));
                            $('.js-ammeter-nbr-from-input').data('attr', childAttr);
                        } else if (childAttr.code == 'AMMETER_NBR_TO') {
                            $('.js-ammeter-nbr-to-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-ammeter-nbr-to-label'));
                            $('.js-ammeter-nbr-to-input').data('attr', childAttr);
                        } else if (childAttr.code == 'NBR_ATTACHMENT') {
                            $('.js-nbr-attachment-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-nbr-attachment-label'));
                            var readonly = false;
                            if (data.state == subOrderStateDef.DO_IN_ORDER ||
                                data.state == subOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING) {
                                readonly = true;
                            }
                            var image = null;
                            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.NBR_ATTACHMENT'] != null) {
                                image = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.NBR_ATTACHMENT'].textInput;
                            }
                            if (isMobile == 'N') {
                                $('.js-nbr-attachment-upload').data('attr', childAttr);
                                $('.js-nbr-attachment-upload').addClass('dropzone');
                                jsCancelLeaseOrderDetail.imageDeal(image == null ? '' : image, 'NBR_ATTACHMENT_UPLOAD', readonly);
                            } else {
                                $('#js-nbr-attachment-images').data('attr', childAttr);
                                njyc.phone.showPic(jsCancelLeaseOrderDetail.changeImagePath(image), 'js-nbr-attachment-images');
                                if (!readonly) {
                                    $('#js-nbr-attachment-upload').addClass('hidden');
                                    $('#js-nbr-attachment-images b').addClass('hidden');
                                }
                            }
                        } else if (childAttr.code == 'CANCEL_LEASE_REASON') {
                            $('.js-cancel-lease-reson-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-cancel-lease-reson-label'));
                            $('.js-cancel-lease-reason-select').data('attr', childAttr);
                            if (childAttr.attrValueList) {
                                for (var k = 0; k < childAttr.attrValueList.length; k++) {
                                    var reason = childAttr.attrValueList[k];
                                    $('.js-cancel-lease-reason-select').append('<option value="' + reason.id + '">' + reason.valueMask + '</option>');
                                }
                            }
                        } else if (childAttr.code == 'PROMOTION_EXPLANATION') {
                            $('.js-promotion-explanation-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-promotion-explanation-label'));
                            $('.js-promotion-explanation-input').data('attr', childAttr);
                        } else if (childAttr.code == 'KEY_RECOVERY') {
                            $('.js-key-recovery-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-key-recovery-label'));
                            $('.js-key-recovery-input').data('attr', childAttr);
                        } else if (childAttr.code == 'DOOR_CARD_RECOVERY') {
                            $('.js-door-card-recovery-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-door-card-recovery-label'));
                            $('.js-door-card-recovery-input').data('attr', childAttr);
                        } else if (childAttr.code == 'OTHER_RECOVERY') {
                            $('.js-other-recovery-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-other-recovery-label'));
                            $('.js-other-recovery-input').data('attr', childAttr);
                        } else if (childAttr.code == 'HOUSE_PICTURE') {
                            $('.js-house-picture-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-house-picture-label'));
                            var readonly = false;
                            if (data.state == subOrderStateDef.DO_IN_ORDER ||
                                data.state == subOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING) {
                                readonly = true;
                            }
                            var image = null;
                            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_PICTURE'] != null) {
                                image = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_PICTURE'].textInput;
                            }
                            if (isMobile == 'N') {
                                $('.js-house-picture-upload').data('attr', childAttr);
                                $('.js-house-picture-upload').addClass('dropzone');
                                jsCancelLeaseOrderDetail.imageDeal(image == null ? '' : image, 'HOUSE_PICTURE_UPLOAD', readonly);
                            } else {
                                $('#js-house-picture-images').data('attr', childAttr);
                                njyc.phone.showPic(jsCancelLeaseOrderDetail.changeImagePath(image), 'js-house-picture-images');
                                if (!readonly) {
                                    $('#js-mobile-house-picture-upload').addClass('hidden');
                                    $('#js-house-picture-images b').addClass('hidden');
                                }
                            }
                        } else if (childAttr.code == 'HOUSE_INSPECTION_COMMENTS') {
                            $('.js-house-inspection-comments-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-house-inspection-comments-label'));
                            $('.js-house-inspection-comments-textarea').data('attr', childAttr);
                        }


                    }
                }
            }
        }
    },

    /**
		 * 租务核算处理
		 * 
		 * @param {type}
		 *          data
		 * @returns {undefined}
		 */
    generatorRentalAccountHtml: function(data) {
        var param = {
            'cancelLeaseOrderId': respData.id
        };
        common.ajax({
            url: common.root + '/cancelLease/rentalMoneyCalculate.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
                // 已付租金
                $("#allCost").html(data.allCost);
                // 月租
                $("#cost_a").html(data.cost_a);
                // 入住天数
                $("#day").html(data.day);
                // 应收或者应付
                var nowcoat = data.nowcoat;
                if (nowcoat.indexOf("-") >= 0) {
                    // 负数
                    $("#cost_name").html("应收租金");
                    $("#nowcoat").html(nowcoat.substring(1, nowcoat.length));
                } else {
                    $("#cost_name").html("应付租金");
                    $("#nowcoat").html(nowcoat);
                }
            }
        });

        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
            for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
                var childCatg = data.attrCatg.attrCatgChildren[i];
                if (childCatg.code == 'RENTAL_ACCOUNT') {
                    var childAttrList = childCatg.attrChildren;
                    for (var j = 0; j < childAttrList.length; j++) {
                        var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.";
                        var childAttr = childAttrList[j];
                        var attrPath = defaultPath + childAttr.code;
                        childAttr.attrPath = attrPath;
                        if (childAttr.code == 'FINANCE_TYPE') {
                            $('.js-finance-type-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-finance-type-label'));
                            $('.js-finance-type-radio').data('attr', childAttr);
                            if (childAttr.attrValueList) {
                                for (var i = 0; i < childAttr.attrValueList.length; i++) {
                                    var attrValue = childAttr.attrValueList[i];
                                    if (attrValue.value == 0) {
                                        $('.js-finance-type-radio').append(
                                            '<div class="radio-inline">' +
                                            '<input name="financeType" type="radio" code="in" value="' + attrValue.value + '"  />' + attrValue.valueMask +
                                            '</div>');
                                    } else {
                                        $('.js-finance-type-radio').append(
                                            '<div class="radio-inline">' +
                                            '<input name="financeType" type="radio" code="out" value="' + attrValue.value + '"  />' + attrValue.valueMask +
                                            '</div>');
                                    }
                                }
                                // $("input[name='financeType']").attr('disabled',
																// "true");
                            }
                        } else if (childAttr.code == 'TOTAL_MONEY') {
                            $('.js-total-money-label').html(childAttr.name);
                            $('.js-total-money-input').val("系统自动核算").attr('readonly', "readonly");
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-total-money-label'));
                            $('.js-total-money-input').data('attr', childAttr);
                        } else if (childAttr.code == 'COMMENTS') {
                            $('.js-rental-account-comments-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-rental-account-comments-label'));
                            $('.js-rental-account-comments-textarea').data('attr', childAttr);
                        } else if (childAttr.code == 'PASSED') {
                            $('#rentalAccountPassBtn').data('attr', childAttr);
                        }
                    }
                }
            }
        }
    },

    // 市场部高管审批
    generatorMarketingExecutiveAuditHtml: function(data) {
        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {

            if (data.type == 'C') {
                // 未入住违约退租
                $("#marketingExecutiveAuditReturnBtn").addClass("hidden");
            }

            for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
                var childCatg = data.attrCatg.attrCatgChildren[i];
                if (childCatg.code == 'MARKETING_EXECUTIVE_AUDIT') {
                    var childAttrList = childCatg.attrChildren;
                    for (var j = 0; j < childAttrList.length; j++) {
                        var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.MARKETING_EXECUTIVE_AUDIT.";
                        var childAttr = childAttrList[j];
                        var attrPath = defaultPath + childAttr.code;
                        childAttr.attrPath = attrPath;
                        if (childAttr.code == 'COMMENTS') {
                            $('.js-marketing-executive-audit-comments-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-marketing-executive-audit-comments-label'));
                            $('.js-marketing-executive-audit-comments-textarea').data('attr', childAttr);
                        } else if (childAttr.code == 'PASSED') {
                            $('#marketingExecutiveAuditPassBtn').data('attr', childAttr);
                        }
                    }
                }
            }
        }
    },

    // 财务审批
    generatorFinanceAuditHtml: function(data) {
        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
            for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
                var childCatg = data.attrCatg.attrCatgChildren[i];
                if (childCatg.code == 'FINANCE_AUDIT') {
                    var childAttrList = childCatg.attrChildren;
                    for (var j = 0; j < childAttrList.length; j++) {
                        var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.FINANCE_AUDIT.";
                        var childAttr = childAttrList[j];
                        var attrPath = defaultPath + childAttr.code;
                        childAttr.attrPath = attrPath;
                        if (childAttr.code == 'COMMENTS') {
                            $('.js-finance-audit-comments-label').html(childAttr.name);
                            jsCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-finance-audit-comments-label'));
                            $('.js-finance-audit-comments-textarea').data('attr', childAttr);
                        } else if (childAttr.code == 'PASSED') {
                            $('#financeAuditPassBtn').data('attr', childAttr);
                        }
                    }
                }
            }
        }
    },
    // 用户评价
    generatorJudgeHtml: function(data) {
        if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
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
                njyc.phone.showPic(jsCancelLeaseOrderDetail.changeImagePath(picurls), 'js-judge-audit-image-url-mobile');
                if (data.state != 'D') {
                    $('#upurl-1').addClass('hidden');
                    $('#upurl-1 b').addClass('hidden');
                }
            } else {
                if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
                    jsCancelLeaseOrderDetail.initPicurls(picurls, 'upurl-1', true);
                } else {
                    jsCancelLeaseOrderDetail.initPicurls(picurls, 'upurl-1', false);
                }
            }
        }
    },

    uploadNbrAttachment: function() {
        var picSize = 10; // 可以上传图片数量
        var uploadPic = $('input[name="picImage"]').size();
        if (Math.abs(picSize) > uploadPic) {
            njyc.phone.selectImage((Math.abs(picSize) - uploadPic), 'js-nbr-attachment-images');
        } else {
            $('#js-nbr-attachment-images').hide();
        }
    },

    uploadhousePicture: function() {
        var picSize = 10; // 可以上传图片数量
        var uploadPic = $('input[name="picImage"]').size();
        if (Math.abs(picSize) > uploadPic) {
            njyc.phone.selectImage((Math.abs(picSize) - uploadPic), 'js-house-picture-images');
        } else {
            $('#js-house-picture-images').hide();
        }
    },

    // 管家重新指派
    reassignOrder: function(elem) {
        $(elem).prop('disabled', true);
        var staffId = workOrder.staffId;
        $('.js-reassign-manager-div').removeClass('hidden');
        $('#takeOrderBtn').addClass('hidden');
        $('#cancelReassignOrderBtn').removeClass('hidden');
        $('#confirmReassignOrderBtn').removeClass('hidden');
        $('#reassignOrderBtn').addClass('hidden');

        common.ajax({
          url: common.root + '/cascading/getUserListByAuthority.do',
          dataType: 'json',
          data: {
              roleId: '29',
              type: workOrder.type
          },
            async: false,
            loadfun: function(isloadsucc, json) {
                $(elem).prop('disabled', false);
                if (isloadsucc) {
                    var html = "<option value=''> 请选择...</option>";
                    for (var i = 0; i < json.length; i++) {
                        html += '<option value="' + json[i].id + '" >' + json[i].name + '</option>';
                    }
                    $('#managerSelect').html(html);
                    $("#managerSelect option[value='" + staffId + "']").attr("selected", "selected");
                    $('#managerSelect').select2({
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

    // 管家指派
    confirmReassignOrder: function(elem) {
        $(elem).prop('disabled', true);
        if ($('#managerSelect').val() == null || $('#managerSelect').val() == "") {
            common.alert({
                msg: "请选择指派人！"
            });
            return;
        }
        common.alert({
            msg: '确定重新指派吗？',
            confirm: true,
            fun: function(action) {
                $(elem).prop('disabled', false);
                if (action) {
                    common.load.load();
                    var commentsData = $('.js-comments-textarea').data('attr');
                    var param = {
                        'code': respData.code,
                        'newButlerId': $('#managerSelect').val(),
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': commentsData.id,
                            'attrPath': commentsData.attrPath,
                            'textInput': $('.js-comments-textarea').val()
                        }]
                    };
                    common.ajax({
                        url: common.root + '/cancelLease/reassignOrder.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            },
            cancel: function() {
                $(elem).prop('disabled', false);
            }
        });
    },

    // 提交释放房源
    houseRelease: function() {
        common.alert({
            msg: '确定提交吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var commentsData = $('.js-comments-release-textarea').data('attr');
                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': commentsData.id,
                            'attrPath': commentsData.attrPath,
                            'textInput': $('.js-comments-release-textarea').val()
                        }]
                    };
                    common.ajax({
                        url: common.root + '/cancelLease/releaseHouseRank.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });
    },

    // 关闭
    houseClose: function() {
        common.alert({
            msg: '确定关闭订单吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var commentsData = $('.js-comments-release-textarea').data('attr');
                    var param = {
                        'workOrderId': respData.workOrderId,
                        'dealerId': dealerId
                    };
                    common.ajax({
                        url: common.root + '/workOrder/endProcess.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });
    },

    // 返回
    cancelReassignOrder: function() {
        $('.js-reassign-manager-div').addClass('hidden');
        $('#takeOrderBtn').removeClass('hidden');
        $('#cancelReassignOrderBtn').addClass('hidden');
        $('#confirmReassignOrderBtn').addClass('hidden');
        $('#reassignOrderBtn').removeClass('hidden');
    },

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
                	// 刷新父页面表格
                	window.opener.table.refresh("workOrderTable");
                }
            } else {
                common.alert({
                    msg: common.msg.error
                });
            }
        }
    },

    // 管家接单
    takeOrder: function(elem) {
        $(elem).prop('disabled', true);
        common.alert({
            msg: '确定要接单吗？',
            confirm: true,
            fun: function(action) {
                $(elem).prop('disabled', false);
                if (action) {
                    common.load.load();
                    var commentsData = $('.js-comments-textarea').data('attr');
                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': commentsData.id,
                            'attrPath': commentsData.attrPath,
                            'textInput': $('.js-comments-textarea').val()
                        }]
                    };
                    common.ajax({
                        url: common.root + '/cancelLease/takeOrder.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            },
            cancel: function() {
                $(elem).prop('disabled', false);
            }
        });
    },

    changeImagePath: function(images) {
        if (!images || images.length == 0) {
            return '';
        }
        return images.replace(/,/g, '|');
    },

    getMobileImagePath: function(inputsEl) {
        var path = '';
        var picImage = inputsEl;
        if (picImage.length == 0) {
            // njyc.phone.showShortMessage('请上传图片');
            return;
        }
        for (var i = 0; i < picImage.length; i++) {
            path += ',' + $(picImage[i]).val();
        }
        if (path != '') {
            path = path.substring(1);
        }
        return path;
    },

    // 保存
    butlerGetHome: function(flag) {
        if (this.butlerGetHomeFormValidate()) {
            return;
        }

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
                    var alipayData = $('.js-alipay-input').data('attr');
                    var bankCardNbrData = $('.js-bank-card-nbr-input').data('attr');
                    var bankAddressData = $('.js-bank-adress-input').data('attr');
                    var waterNbrFromData = $('.js-water-nbr-from-input').data('attr');
                    var waterNbrToData = $('.js-water-nbr-to-input').data('attr');
                    var gasNbrFromData = $('.js-gas-nbr-from-input').data('attr');
                    var gasNbrToData = $('.js-gas-nbr-to-input').data('attr');
                    var ammeterNbrFromData = $('.js-ammeter-nbr-from-input').data('attr');
                    var ammeterNbrToData = $('.js-ammeter-nbr-to-input').data('attr');
                    var nbrAttachmentVal;
                    var nbrAttachmentData;
                    if (isMobile == 'Y') {
                        nbrAttachmentData = $('#js-nbr-attachment-images').data('attr');
                        nbrAttachmentVal = jsCancelLeaseOrderDetail.getMobileImagePath($('#js-nbr-attachment-images input[name="picImage"]'));
                    } else {
                        nbrAttachmentData = $('.js-nbr-attachment-upload').data('attr');
                        nbrAttachmentVal = jsCancelLeaseOrderDetail.imagework('NBR_ATTACHMENT_UPLOAD');
                    }
                    var cancelLeaseReasonData = $('.js-cancel-lease-reason-select').data('attr');
                    var promotionExplanationData = $('.js-promotion-explanation-input').data('attr');
                    var keyRecoveryData = $('.js-key-recovery-input').data('attr');
                    var doorRecoveryData = $('.js-door-card-recovery-input').data('attr');
                    var otherRecoveryData = $('.js-other-recovery-input').data('attr');
                    var housePictureData;
                    var housePictureVal;
                    if (isMobile == 'Y') {
                        housePictureData = $('#js-house-picture-images').data('attr');
                        housePictureVal = jsCancelLeaseOrderDetail.getMobileImagePath($('#js-house-picture-images input[name="picImage"]'));
                    } else {
                        housePictureData = $('.js-house-picture-upload').data('attr');
                        housePictureVal = jsCancelLeaseOrderDetail.imagework('HOUSE_PICTURE_UPLOAD');
                    }
                    var houseInspectionCommentsData = $('.js-house-inspection-comments-textarea').data('attr');
                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'submitFlag': flag,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': alipayData.id,
                            'attrPath': alipayData.attrPath,
                            'textInput': $('.js-alipay-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': bankCardNbrData.id,
                            'attrPath': bankCardNbrData.attrPath,
                            'textInput': $('.js-bank-card-nbr-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': bankAddressData.id,
                            'attrPath': bankAddressData.attrPath,
                            'textInput': $('.js-bank-adress-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': waterNbrFromData.id,
                            'attrPath': waterNbrFromData.attrPath,
                            'textInput': $('.js-water-nbr-from-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': waterNbrToData.id,
                            'attrPath': waterNbrToData.attrPath,
                            'textInput': $('.js-water-nbr-to-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': gasNbrFromData.id,
                            'attrPath': gasNbrFromData.attrPath,
                            'textInput': $('.js-gas-nbr-from-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': gasNbrToData.id,
                            'attrPath': gasNbrToData.attrPath,
                            'textInput': $('.js-gas-nbr-to-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': ammeterNbrFromData.id,
                            'attrPath': ammeterNbrFromData.attrPath,
                            'textInput': $('.js-ammeter-nbr-from-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': ammeterNbrToData.id,
                            'attrPath': ammeterNbrToData.attrPath,
                            'textInput': $('.js-ammeter-nbr-to-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': nbrAttachmentData.id,
                            'attrPath': nbrAttachmentData.attrPath,
                            'textInput': nbrAttachmentVal
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': cancelLeaseReasonData.id,
                            'attrPath': cancelLeaseReasonData.attrPath,
                            'textInput': $('.js-cancel-lease-reason-select').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': promotionExplanationData.id,
                            'attrPath': promotionExplanationData.attrPath,
                            'textInput': $('.js-promotion-explanation-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': keyRecoveryData.id,
                            'attrPath': keyRecoveryData.attrPath,
                            'textInput': $('.js-key-recovery-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': doorRecoveryData.id,
                            'attrPath': doorRecoveryData.attrPath,
                            'textInput': $('.js-door-card-recovery-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': otherRecoveryData.id,
                            'attrPath': otherRecoveryData.attrPath,
                            'textInput': $('.js-other-recovery-input').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': housePictureData.id,
                            'attrPath': housePictureData.attrPath,
                            'textInput': housePictureVal
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': houseInspectionCommentsData.id,
                            'attrPath': houseInspectionCommentsData.attrPath,
                            'textInput': $('.js-house-inspection-comments-textarea').val()
                        }]
                    };
                    common.ajax({
                        url: common.root + '/cancelLease/butlerGetHome.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });
    },

    // 管家上门信息验证
    butlerGetHomeFormValidate: function() {
        var alipay = $.trim($('.js-alipay-input').val());
        var bankCard = $.trim($('.js-bank-card-nbr-input').val());
        var bankAddress = $.trim($('.js-bank-adress-input').val());
        var waterNbrFrom = $.trim($('.js-water-nbr-from-input').val());
        var waterNbrTo = $.trim($('.js-water-nbr-to-input').val());
        var gasNbrFrom = $.trim($('.js-gas-nbr-from-input').val());
        var gasNbrTo = $.trim($('.js-gas-nbr-to-input').val());
        var ammeterNbrFrom = $.trim($('.js-ammeter-nbr-from-input').val());
        var ammeterNbrTo = $.trim($('.js-ammeter-nbr-to-input').val());
        var nbrAttachment;
        if (isMobile == 'Y') {
            nbrAttachment = jsCancelLeaseOrderDetail.getMobileImagePath($('#js-nbr-attachment-images input[name="picImage"]'));
        } else {
            nbrAttachment = jsCancelLeaseOrderDetail.imagework("NBR_ATTACHMENT_UPLOAD");
        }
        var cancelLeaseReason = $.trim($('.js-cancel-lease-reason-select').val());
        var promotionExplanation = $.trim($('.js-promotion-explanation-input').val());
        var keyRecovery = $.trim($('.js-key-recovery-input').val());
        var doorRecovery = $.trim($('.js-door-card-recovery-input').val());
        var otherRecovery = $.trim($('.js-other-recovery-input').val());
        var housePicture;
        if (isMobile == 'Y') {
            housePicture = jsCancelLeaseOrderDetail.getMobileImagePath($('#js-house-picture-images input[name="picImage"]'));
        } else {
            housePicture = jsCancelLeaseOrderDetail.imagework("HOUSE_PICTURE_UPLOAD");
        }

        var houseInspectionComments = $.trim($('.js-house-inspection-comments-textarea').val());
        if (houseInspectionComments == null || houseInspectionComments == "") {
          common.alert({
            msg: "请按照格式填写验房说明"
          });
          var str = "特殊审批流程单号：\n"+ respData.typeName + "：\n" + "退预缴费（）+退押金()+退房租（合计数）（期间）+已交水（）+已交电（）+已交燃（）+已交物业（）-扣房租（合计数）（期间）-应交水（）-应交电（）-应交燃（）-应交物业费（）-活动追缴款（）（期间）-赔偿款()（赔偿XXX费）-保洁费(   ）=实退（）";
          $('.js-house-inspection-comments-textarea').val(str);
          return true;
        }
        
        // 验证支付宝账号或银行卡号是否填写
        if ((alipay == null || alipay == "") && (bankCard == null || bankCard == "")) {
            common.alert({
                msg: "请填写支付宝账号或银行卡号"
            });
            layer.tips("请填写支付宝账号", $('.js-alipay-input'), {
                tips: [1, '#d9534f'] // 还可配置颜色
            });
            layer.tips("请填写银行卡号", $('.js-bank-card-nbr-input'), {
                tips: [1, '#d9534f'] // 还可配置颜色
            });
            return true;
        }
        // 验证银行卡的格式
        if (bankCard != null && bankCard != "") {
            var reg = /^\d+$/;
            if (!reg.test(bankCard)) {
                common.alert({
                    msg: "请填写正确的银行卡号"
                });
                layer.tips("请填写正确的银行卡号", $('.js-bank-card-nbr-input'), {
                    tips: [1, '#d9534f']
                });
                return true;
            }
        }
        // 验证银行卡的开户银行是否填写
        if (bankCard != "" && bankAddress == "") {
            common.alert({
                msg: "请填写开户银行"
            });
            layer.tips("请填写开户银行", $('.js-bank-adress-input'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
        if (nbrAttachment == null || nbrAttachment == "") {
            common.alert({
                msg: "请上传度数附件图片"
            });
            layer.tips("请上传度数附件图片", $('.js-nbr-attachment-upload'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
        var nbrAttachmentTotal = nbrAttachment.split(",");
        if (nbrAttachmentTotal.length < 2) {
          common.alert({
            msg: "度数附件图片不可少于2张，水电必须上传"
          });
          return true;
        }
        if (housePicture == null || housePicture == "") {
            common.alert({
                msg: "请上传房源图片"
            });
            layer.tips("请上传房源图片", $('.js-house-picture-upload'), {
                tips: [1, '#d9534f']
            });
            return true;
        }

        // 验证燃气起始度数
        var regInfo = /^(([0-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/;
        if (gasNbrFrom == "") {
            $('.js-gas-nbr-from-input').val("0");
        } else {
            if (!regInfo.test(gasNbrFrom)) {
                common.alert({
                    msg: "请填写正确的燃气起始度数！"
                });
                return true;
            }
        }
        // 验证燃气结束度数
        if (gasNbrTo == "") {
            $('.js-gas-nbr-to-input').val("0");
        } else {
            if (!regInfo.test(gasNbrTo)) {
                common.alert({
                    msg: "请填写正确的燃气结束度数！"
                });
                return true;
            }
        }

        if (!Validator.Validate('butlerGetHomeForm')) {
            return true;
        }
        if (parseFloat(waterNbrFrom) >= parseFloat(waterNbrTo)) {
            common.alert({
                msg: "水表起始度数不得大于等于结束度数"
            });
            layer.tips("起始度数不得大于结束度数", $('.js-water-nbr-to-input'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
        if (parseFloat(gasNbrFrom) > parseFloat(gasNbrTo)) {
            common.alert({
                msg: "燃气起始度数不得大于结束度数"
            });
            layer.tips("起始度数不得大于结束度数", $('.js-gas-nbr-to-input'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
        if (parseFloat(ammeterNbrFrom) >= parseFloat(ammeterNbrTo)) {
            common.alert({
                msg: "电表起始度数不得大于等于结束度数"
            });
            layer.tips("起始度数不得大于结束度数", $('.js-gas-nbr-to-input'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
    },

    // 添加待缴费明细
    createCost: function() {
        // 打开对于的窗口
        common.openWindow({
            type: 1,
            name: 'createCost',
            title: '新增待缴项目',
            area: [($(window).width() - 500) + 'px', ($(window).height() - 150) + 'px'],
            url: common.root + '/cancelLease/path2CreateCostPage.do?id=' + respData.workOrderId
        });
    },

    createFinanceIncomeTable: function(agree) {
        // console.log("income---" + agree);
        if (isMobile == 'Y') {
            $('#financeIncomeTable').attr('width', '250%');
        } else {
            $('#financeIncomeTable').attr('width', '100%');
        }
        table.init({
            id: '#financeIncomeTable',
            url: common.root + '/flow/getFinancialList.do',
            "iDisplayLength": 100,
            isexp: false,
            bStateSave: false,
            columns: [
                "name",
                "typename",
                "cost",
                "yet_cost",
                "yhcost",
                "starttime",
                "endtime",
                "statusname", {
                    name: "remark",
                    isover: true,
                    isshow: false,
                    title: '备注'
                },
                "isdeleteName"
            ],
            param: function() {
                var qryConf = new Array();
                qryConf.push({
                    "name": "agreeRankId",
                    "value": agree
                });
                return qryConf;
            },
            aoColumnDefs: [{
                "bSortable": false,
                "aTargets": [0, 1, 2, 3, 4, 5, 6, 7]
            }],
            createRow: function(rowindex, colindex, name, value, data, row) {
                // 检查时间信息
                var d1 = new Date(jsCancelLeaseOrderDetail.appointmentDate);
                var d2 = new Date(data.starttime);
                // console.log(data.starttime,
                // jsCancelLeaseOrderDetail.appointmentDate, (d2 <= d1));
                if (d2 <= d1 && data.statusname == '未发生') {
                    $(row).addClass('bzh');
                }
                if(data.statusname == '未发生' && (data.yet_cost > 0 || data.yhcost >0)){
                    $(row).find('td:eq(9)').text("支付中");
                }
                if (colindex == 0) {
                    return jsCancelLeaseOrderDetail.dealColumn({
                        "value": value,
                        "length": 5
                    });
                }
                return;
            },
            fnDrawCallback: function() {
                // jsCancelLeaseOrderDetail.autoDetailCost();
                jsCancelLeaseOrderDetail.autoAccounts();
            }
        });
        $('.dataTables_wrapper').hide();
    },

    createFinancePayTable: function(agree) {
        if (isMobile == 'Y') {
            $('#financePayTable').attr('width', '250%');
        } else {
            $('#financePayTable').attr('width', '100%');
        }
        table.init({
            id: '#financePayTable',
            url: common.root + '/flow/getPayList.do',
            "iDisplayLength": 100,
            isexp: false,
            bStateSave: false,
            columns: [
                "name",
                "typename",
                "cost",
                "yet_cost",
                "yhcost",
                "starttime",
                "endtime",
                "statusname", {
                    name: "remark",
                    isover: true,
                    isshow: false,
                    title: '备注'
                },
                "isdeleteName"
            ],
            param: function() {
                var qryConf = new Array();
                qryConf.push({
                    "name": "agreeRankId",
                    "value": agree
                });
                return qryConf;
            },
            aoColumnDefs: [{
                "bSortable": false,
                "aTargets": [0, 1, 2, 3, 4, 5, 6, 7]
            }],
            createRow: function(rowindex, colindex, name, value, data, row) {
                var d1 = new Date(jsCancelLeaseOrderDetail.appointmentDate);
                var d2 = new Date(data.starttime);
                if (d2 <= d1 && data.statusname == '未发生') {
                    $(row).addClass('bzh');
                }
                if (colindex == 0) {
                    return jsCancelLeaseOrderDetail.dealColumn({
                        "value": value,
                        "length": 5
                    });
                }
                return;
            },
            fnDrawCallback: function() {
                // jsCancelLeaseOrderDetail.autoDetailCost();
                jsCancelLeaseOrderDetail.autoAccounts();
            }
        });
        $('.dataTables_wrapper').hide();
    },

    createWait2PayTableWithOutBtn: function(cancelLeaseOrderData) {
        if (isMobile == 'Y') {
            $('#wait2PayTable').attr('width', '250%');
        } else {
            $('#wait2PayTable').attr('width', '100%');
        }
        total = 0.0;
        table.init({
            id: '#wait2PayTable',
            url: common.root + '/CertificateLeave/getList.do',
            "iDisplayLength": 100,
            isexp: false,
            bStateSave: false,
            columns: [{
                    name: "name",
                    sort: false
                },
                "typename",
                "cost",
                "costdesc"
            ],
            aoColumnDefs: [{
                "bSortable": false,
                "aTargets": [0, 1, 2, 3, 4]
            }],
            param: function() {
                var a = new Array();
                a.push({
                    "name": "orderId",
                    "value": respData.workOrderId
                });
                return a;
            },
            createRow: function(rowindex, colindex, name, value, data, row) {
                if (colindex == 2) {
                    total += value;
                }
                $("#costCountp").html(total.toFixed(2));
                return;
            },
            fnDrawCallback: function() {
                jsCancelLeaseOrderDetail.autoAccounts();
            }
        });
        $('.dataTables_wrapper').hide();
    },

    accAdd: function(arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2))
        return (arg1 * m + arg2 * m) / m
    },

    createWait2PayTableWithBtn: function(cancelLeaseOrderData) {
        if (isMobile == 'Y') {
            $('#wait2PayTable').attr('width', '250%');
        } else {
            $('#wait2PayTable').attr('width', '100%');
        }
        total = 0.0;
        table.init({
            id: '#wait2PayTable',
            url: common.root + '/CertificateLeave/getList.do',
            "iDisplayLength": 100,
            isexp: false,
            bStateSave: false,
            columns: [{
                    name: "name",
                    sort: false
                },
                "typename",
                "cost",
                "costdesc"
            ],
            bnt: [{
                name: '删除',
                isshow: function(data) {
                    if (data.name == '退还提前交款费用') {
                        return false;
                    }
                    if (data.name == '退还押金') {
                        return false;
                    }
                    return true;
                },
                fun: function(data, row) {
                    common.alert({
                        msg: "是否删除此项？",
                        confirm: true,
                        closeIcon: true,
                        confirmButton: '是',
                        cancelButton: '否',
                        fun: function(action) {
                            if (action) {
                                common.ajax({
                                    url: common.root + '/CertificateLeave/delete.do',
                                    data: {
                                        id: data.id
                                    },
                                    dataType: 'json',
                                    loadfun: function(isloadsucc, data) {
                                        if (isloadsucc) {
                                            if (data.state == 1) {
                                                common.alert({
                                                    msg: '删除成功'
                                                });
                                                table.refreshRedraw('wait2PayTable');
                                                total = 0.0;
                                                $("#costCountp").html(total);
                                                jsCancelLeaseOrderDetail.autoAccounts();
                                            } else {
                                                common.alert({
                                                    msg: common.msg.error
                                                });
                                            }
                                        } else {
                                            common.alert({
                                                msg: common.msg.error
                                            });
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }, {
                name: '修改',
                isshow: function(data) {
                    if (data.name == '退还提前交款费用') {
                        return false;
                    }
                    if (data.name == '退还押金') {
                        return false;
                    }
                    return true;
                },
                fun: function(data, row) {
                    rowdata = data;
                    // 打开对于的窗口
                    common.openWindow({
                        type: 1,
                        name: 'updateCost',
                        area: [($(window).width() - 500) + 'px', ($(window).height() - 150) + 'px'],
                        title: '修改待缴项目',
                        url: '/html/pages/flow/pages/order/leaseorder/updateCost.jsp'
                    });
                    total = 0.0;
                }
            }],
            aoColumnDefs: [{
                "bSortable": false,
                "aTargets": [0, 1, 2, 3, 4, 5]
            }],
            param: function() {
                var param = new Array();
                param.push({
                    "name": "orderId",
                    "value": respData.workOrderId
                });
                return param;
            },
            createRow: function(rowindex, colindex, name, value, data, row) {
                if (colindex == 2) {
                    total = jsCancelLeaseOrderDetail.accAdd(total, value);
                }
                $("#costCountp").html(total.toFixed(2));
                jsCancelLeaseOrderDetail.autoAccounts();
                return;
            },
            fnDrawCallback: function() {

                jsCancelLeaseOrderDetail.autoAccounts();
            }
        });
        $('.dataTables_wrapper').hide();
    },

    dealColumn: function(opt) {
        var def = {
            value: '',
            length: 5
        };
        jQuery.extend(def, opt);
        if (common.isEmpty(def.value)) {
            return "";
        }
        if (def.value.length > def.length) {
            return "<div title='" + def.value + "'>" + def.value.substr(0, def.length) + "...</div>";
        } else {
            return def.value;
        }
    },

    rentalAccountReturn: function() {
        var financeComments = $('.js-rental-account-comments-textarea').val();
        if (financeComments == null || financeComments == '') {
            common.alert("打回时请输入打回原因");
            return;
        }
        common.alert({
            msg: '确定打回吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var financeCommentsData = $('.js-rental-account-comments-textarea').data('attr');
                    var fincancePassData = $('#rentalAccountPassBtn').data('attr');

                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeCommentsData.id,
                            'attrPath': financeCommentsData.attrPath,
                            'textInput': $('.js-rental-account-comments-textarea').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': fincancePassData.id,
                            'attrPath': fincancePassData.attrPath,
                            'textInput': 'N'
                        }]
                    };
                    common.ajax({
                        url: common.root + '/cancelLease/rentalAccountReturn.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });

    },

    rentalAccountPass: function() {
        if (this.financeAccountFormValidate()) {
            return;
        }
        common.alert({
            msg: '确定租务核算完成吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var financeTypeData = $('.js-finance-type-radio').data('attr');
                    var totalMoneyData = $('.js-total-money-input').data('attr');
                    var financeCommentsData = $('.js-rental-account-comments-textarea').data('attr');
                    var fincancePassData = $('#rentalAccountPassBtn').data('attr');
                    var totalMoney = $('.js-total-money-input').val();

                    if ($('input:radio:checked').attr('code') == 'out') {
                        totalMoney = -totalMoney;
                    }

                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeTypeData.id,
                            'attrPath': financeTypeData.attrPath,
                            'textInput': $('input:radio:checked').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': totalMoneyData.id,
                            'attrPath': totalMoneyData.attrPath,
                            'textInput': totalMoney
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeCommentsData.id,
                            'attrPath': financeCommentsData.attrPath,
                            'textInput': $('.js-rental-account-comments-textarea').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': fincancePassData.id,
                            'attrPath': fincancePassData.attrPath,
                            'textInput': 'Y'
                        }]
                    };

                    common.ajax({
                        url: common.root + '/cancelLease/rentalAccount.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });


    },

    financeAccountFormValidate: function() {
        var totalMoney = $('.js-total-money-input').val();
        var reg = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
        // 正则判断金额填写是否正确
        if (!reg.test(totalMoney) || totalMoney < 0) {
            common.alert({
                msg: "金额填写错误！"
            });
            return true;
        }
    },
    marketingExecutiveAuditPass: function() {
        common.alert({
            msg: '确定审批通过？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var marketingExecutiveAuditCommentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
                    var marketingExecutiveAuditPassData = $('#marketingExecutiveAuditPassBtn').data('attr');

                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': marketingExecutiveAuditCommentsData.id,
                            'attrPath': marketingExecutiveAuditCommentsData.attrPath,
                            'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': marketingExecutiveAuditPassData.id,
                            'attrPath': marketingExecutiveAuditPassData.attrPath,
                            'textInput': 'Y'
                        }]
                    };
                    common.ajax({
                        url: common.root + '/cancelLease/marketingExecutiveAudit.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });

    },

    marketingExecutiveAuditReturn: function() {
        var comments = $('.js-marketing-executive-audit-comments-textarea').val();
        if (comments == null || comments == '') {
            common.alert("打回时请输入打回原因");
            return;
        }

        common.alert({
            msg: '确定要打回吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var marketingExecutiveAuditCommentsData = $('.js-marketing-executive-audit-comments-textarea').data('attr');
                    var marketingExecutiveAuditPassData = $('#marketingExecutiveAuditPassBtn').data('attr');

                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': marketingExecutiveAuditCommentsData.id,
                            'attrPath': marketingExecutiveAuditCommentsData.attrPath,
                            'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': marketingExecutiveAuditPassData.id,
                            'attrPath': marketingExecutiveAuditPassData.attrPath,
                            'textInput': 'N'
                        }]
                    };

                    common.ajax({
                        url: common.root + '/cancelLease/marketingExecutiveAudit.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });

    },
    financeAuditPass: function() {
        common.alert({
            msg: '确定要通过吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var financeAuditCommentsData = $('.js-finance-audit-comments-textarea').data('attr');
                    var financeAuditPassData = $('#financeAuditPassBtn').data('attr');

                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeAuditCommentsData.id,
                            'attrPath': financeAuditCommentsData.attrPath,
                            'textInput': $('.js-finance-audit-comments-textarea').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeAuditPassData.id,
                            'attrPath': financeAuditPassData.attrPath,
                            'textInput': 'Y'
                        }]
                    };

                    common.ajax({
                        url: common.root + '/cancelLease/financeAudit.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });

    },
    financeAuditReturn: function() {
        var comments = $('.js-finance-audit-comments-textarea').val();
        if (comments == null || comments == '') {
            common.alert("打回时请输入打回原因");
            return;
        }

        common.alert({
            msg: '确定要打回吗？',
            confirm: true,
            fun: function(action) {
                if (action) {
                    common.load.load();
                    var financeAuditCommentsData = $('.js-finance-audit-comments-textarea').data('attr');
                    var financeAuditPassData = $('#financeAuditPassBtn').data('attr');

                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'subOrderValueList': [{
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeAuditCommentsData.id,
                            'attrPath': financeAuditCommentsData.attrPath,
                            'textInput': $('.js-finance-audit-comments-textarea').val()
                        }, {
                            // 'cancelLeaseOrderId': respData.id,
                            'attrId': financeAuditPassData.id,
                            'attrPath': financeAuditPassData.attrPath,
                            'textInput': 'N'
                        }]
                    };

                    common.ajax({
                        url: common.root + '/cancelLease/financeAudit.do',
                        dataType: 'json',
                        contentType: 'application/json; charset=utf-8',
                        encode: false,
                        data: JSON.stringify(param),
                        loadfun: function(isloadsucc, data) {
                            common.load.hide();
                            jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });

    },

    sigleHouseInfo: function(house_rank_id, flag, houseId, rankType, title, agreementId) {
    	
    	jsCancelLeaseOrderDetail.rankHouseId_ = house_rank_id;
    	jsCancelLeaseOrderDetail.title_ = title;
    	jsCancelLeaseOrderDetail.rankType_ = rankType;
    	jsCancelLeaseOrderDetail.houseId_ = houseId;
    	jsCancelLeaseOrderDetail.agreementId_ = agreementId;
    	jsCancelLeaseOrderDetail.flag_ = flag;
        // 查看单个房间信息
        common.openWindow({
            name: 'signHouse',
            type: 1,
            data: {
                id: house_rank_id,
                flag: flag,
                houseId: houseId,
                rankType: rankType,
                title: title,
                agreementId: agreementId
            },
            area: [($(window).width() - 200) + 'px', ($(window).height() - 300) + 'px'],
            title: '查询出租信息',
            url: '/html/pages/house/houseInfo/rank_house_agreement.html'
        });
    },

    payPass: function() {
        var wait2PayCommentsData = $('.js-wait-2-pay-comments-textarea').data('attr');

        var param = {
            'code': respData.code,
            'dealerId': dealerId,
            'subOrderValueList': [{
                // 'cancelLeaseOrderId': respData.id,
                'attrId': wait2PayCommentsData.id,
                'attrPath': wait2PayCommentsData.attrPath,
                'textInput': $('.js-finance-audit-comments-textarea').val()
            }]
        };
        common.ajax({
            url: common.root + '/cancelLease/pay.do',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            encode: false,
            data: JSON.stringify(param),
            loadfun: function(isloadsucc, data) {
                jsCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
            }
        });

    },

    /**
		 * 自动进行核账操作
		 * 
		 * @returns {undefined}
		 */
    autoAccounts: function() {
        if ($('#createCostBtn').hasClass("hidden")) {
            return;
        }
        // 获取添加的所有待缴费明细数据
        var wait2PayTable_cost = 0;
        $('#wait2PayTable tr').each(function(i, n) {
            if ($(this).find('td').size() != 0) {
                // console.log(wait2PayTable_cost,
                // $(this).find('td:eq(4)').text());
                wait2PayTable_cost = jsCancelLeaseOrderDetail.floatAdd(wait2PayTable_cost, parseFloat($(this).find('td:eq(3)').text().trim()));
            }
        });
        if(wait2PayTable_cost == undefined || wait2PayTable_cost == NaN){
            wait2PayTable_cost = 0;
        }
        // 计算出收入合计
        // 合计应该收入
        var financeIncomeTable_cost = 0;
        // 押金
        var financeIncomeTable_yj_cost = 0;
        // 应该缴费但是没有缴费的金额
        var financeIncomeTable_wj_cost = 0;
        // 退租时间
        var js_appointment_date = $('.js-appointment-date').text().trim().substr(0, 10);
        var appointment_date = new Date($('.js-appointment-date').text().trim().substr(0, 10));
        // 当前退租时间点之前 已支付的需要退款给用户的金额
        var financeIncomeTable_tk_cost = 0;
        $('#financeIncomeTable tr').each(function(i, n) {
            if ($(this).find('td').size() != 0) {
                var je = parseFloat($(this).find('td:eq(4)').text().trim());// 总金额
                var yf = parseFloat($(this).find('td:eq(5)').text().trim());// 已交金额
                var yh = parseFloat($(this).find('td:eq(6)').text().trim());// 优惠金额
                var sy = jsCancelLeaseOrderDetail.floatSub(jsCancelLeaseOrderDetail.floatSub(je,yf),yh);// 还剩余多少需要缴费

                var startTime = new Date($(this).find('td:eq(7)').text().trim().substr(0, 10));
                var endTime = new Date($(this).find('td:eq(8)').text().trim().substr(0, 10));

                var typeName = $(this).find('td:eq(3)').text().trim();

                if (typeName == '365金融代付') {
                    return true;
                }
// alert(ze+typeName);
                if ($(this).hasClass("bzh")) {
                    // 需要计算到天数
                    if (appointment_date >= endTime) {
                        financeIncomeTable_wj_cost = jsCancelLeaseOrderDetail.floatAdd(financeIncomeTable_wj_cost, sy);
                    } else {
                        var days = Math.floor((appointment_date.getTime() - startTime.getTime()) / (24 * 3600 * 1000));
                        var jg = Math.floor((endTime.getTime() - startTime.getTime()) / (24 * 3600 * 1000));
                        var day_cost = jsCancelLeaseOrderDetail.floatDiv(je, jg);
                        var cost = jsCancelLeaseOrderDetail.floatMul(days, day_cost);
                        financeIncomeTable_wj_cost = jsCancelLeaseOrderDetail.floatAdd(financeIncomeTable_wj_cost, cost);
                    }
                }
            }
        });
// alert(financeIncomeTable_wj_cost);
        // 合计应该付款给用户金额
        var financePayTable_cost = 0;
        // 押金
        var financePayTable_yj_cost = 0;
        // 应该缴费但是没有缴费的金额
        var financePayTable_wj_cost = 0;
        // 退租时间
        var appointment_date = new Date($('.js-appointment-date').text().trim().substr(0, 10));

        // 当前退租时间点之后 已支付的需要用户退款的金额
        var financePayTable_tk_cost = 0;

        $('#financePayTable tr').each(function(i, n) {
            if ($(this).find('td').size() != 0) {
                var je = parseFloat($(this).find('td:eq(4)').text().trim());
                var yf = parseFloat($(this).find('td:eq(5)').text().trim());
                var yh = parseFloat($(this).find('td:eq(6)').text().trim());
                var ze = jsCancelLeaseOrderDetail.floatSub(jsCancelLeaseOrderDetail.floatSub(je,yf),yh);

                if ($(this).hasClass("bzh")) {
                    // 需要计算到天数
                    var startTime = new Date($(this).find('td:eq(7)').text().trim().substr(0, 10));
                    var endTime = new Date($(this).find('td:eq(8)').text().trim().substr(0, 10));

                    if (appointment_date >= endTime) {
                        financePayTable_wj_cost = jsCancelLeaseOrderDetail.floatAdd(financePayTable_wj_cost, ze);
                    } else {
                        var days = Math.floor((appointment_date.getTime() - startTime.getTime()) / (24 * 3600 * 1000));
                        var jg = Math.floor((endTime.getTime() - startTime.getTime()) / (24 * 3600 * 1000));
                        var day_cost = jsCancelLeaseOrderDetail.floatDiv(ze, jg);
                        var cost = jsCancelLeaseOrderDetail.floatMul(days, day_cost);
                        financePayTable_wj_cost = jsCancelLeaseOrderDetail.floatAdd(financePayTable_wj_cost, cost);
                    }
                }
            }
        });

        wait2PayTable_cost = isNaN(wait2PayTable_cost.toFixed(2))?0:wait2PayTable_cost.toFixed(2);
        var cost1 = jsCancelLeaseOrderDetail.floatAdd(wait2PayTable_cost, financeIncomeTable_wj_cost.toFixed(2));
        // console.log("cost1:"+cost1);
        var cost_ = jsCancelLeaseOrderDetail.floatSub(cost1.toFixed(2), financePayTable_wj_cost.toFixed(2));

        if (cost_ < 0) { // 需要支出
            $("input[name='financeType'][value=1]").attr('checked', 'true');
            $('.js-total-money-input').val(0 - cost_);
            $("input[name='financeType']").off('click');
            $("input[name='financeType']").on('click', function() {
                $("input[name='financeType'][value=1]").attr('checked', 'true');
            });
        } else { // 需要收入
            $("input[name='financeType'][value=0]").attr('checked', 'true');
            $('.js-total-money-input').val(cost_);
            $("input[name='financeType']").off('click');
            $("input[name='financeType']").on('click', function() {
                $("input[name='financeType'][value=0]").attr('checked', 'true');
            });
        }

        var h = "其他杂费用户应缴总和（cost1）：" + wait2PayTable_cost + "￥\r\n" +
            "收入----------------------\r\n" +
            "在退租时间点（" + js_appointment_date + "）之前需要支付但用户未支付的总额（cost2）：" + financeIncomeTable_wj_cost.toFixed(2) + "￥\r\n" +
            // "在退租时间点（" + js_appointment_date + "）之前需要支付总额（cost3）：" +
            // financeIncomeTable_cost + "￥\r\n" +
            // "在退租时间点（" + js_appointment_date + "）之后不需要用户缴费但是用户已支付总额（cost4）：" +
            // financeIncomeTable_tk_cost + "￥\r\n" +
            "支出----------------------\r\n" +
            "在退租时间点（" + js_appointment_date + "）之前需要支付给用户但未支付总额（cost3）：" + financePayTable_wj_cost.toFixed(2) + "￥\r\n" +
            // "在退租时间点（" + js_appointment_date + "）之前需要支付支付给用户总额（cost6）：" +
            // financePayTable_cost + "￥\r\n" +
            // "在退租时间点（" + js_appointment_date +
						// "）之后不需要支付支付给用户但确已支付给用户的总额（cost7）：" +
            // financePayTable_tk_cost + "￥\r\n" +
            "总费用合计：cost1+cost2-cost3=" + cost_;

        $('.js-rental-account-comments-textarea').html(h);
    },
    /**
		 * 加法精确计算
		 * 
		 * @param {type}
		 *          arg1
		 * @param {type}
		 *          arg2
		 * @returns {Number}
		 */
    floatAdd: function(arg1, arg2) {
        var r1, r2, m;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2));
        return (arg1 * m + arg2 * m) / m;
    },
    /**
		 * 减法精确计算
		 * 
		 * @param {type}
		 *          arg1
		 * @param {type}
		 *          arg2
		 * @returns {Number}
		 */
    floatSub: function(arg1, arg2) {
        var r1, r2, m, n;
        try {
            r1 = arg1.toString().split(".")[1].length
        } catch (e) {
            r1 = 0
        }
        try {
            r2 = arg2.toString().split(".")[1].length
        } catch (e) {
            r2 = 0
        }
        m = Math.pow(10, Math.max(r1, r2));
        // 动态控制精度长度
        n = (r1 >= r2) ? r1 : r2;
        return ((arg1 * m - arg2 * m) / m).toFixed(n);
    },
    /**
		 * 除法计算
		 * 
		 * @param {type}
		 *          arg1
		 * @param {type}
		 *          arg2
		 * @returns {Number}
		 */
    floatDiv: function(arg1, arg2) {
        var t1 = 0,
            t2 = 0,
            r1, r2;
        try {
            t1 = arg1.toString().split(".")[1].length
        } catch (e) {}
        try {
            t2 = arg2.toString().split(".")[1].length
        } catch (e) {}

        r1 = Number(arg1.toString().replace(".", ""));

        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * Math.pow(10, t2 - t1);
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
    floatMul: function(arg1, arg2) {
        var m = 0,
            s1 = arg1.toString(),
            s2 = arg2.toString();
        try {
            m += s1.split(".")[1].length
        } catch (e) {}
        try {
            m += s2.split(".")[1].length
        } catch (e) {}
        return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
    }
};
$(function() {
    jsCancelLeaseOrderDetail.init();
});