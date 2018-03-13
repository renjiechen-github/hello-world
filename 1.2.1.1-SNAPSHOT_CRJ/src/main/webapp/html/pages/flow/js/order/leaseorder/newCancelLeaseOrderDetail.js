var respData;
var workOrder;
var isMobile;
var dealerId;
var isLeader;
var cancelLeaseOrderValueMap = {};
var jsNewCancelLeaseOrderDetail = {
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
        
    		// 保存应退款项信息
    		$("#saveRefundItem").click(function() {
    			jsNewCancelLeaseOrderDetail.saveRefundItem();
    		});
    		
    		// 控制DIV（费用清算）
    		$("#closeDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.hideDiv();
    		});
    		$("#openDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.openDiv();
    		});
    		$("#closeFinanceDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.closeFinanceDiv();
    		});
    		$("#openFinanceDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.openFinanceDiv();
    		});
    		
    		// 控制DIV（租务核算）
    		$("#closeRentalFinanceDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.closeRentalFinanceDiv();
    		});
    		$("#openRentalFinanceDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.openRentalFinanceDiv();
    		});
    		$("#closeRentalDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.closeRentalDiv();
    		});
    		$("#openRentalDiv").click(function() {
    			jsNewCancelLeaseOrderDetail.openRentalDiv();
    		});
    },
    
    // 加载各项数据信息
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
                    jsNewCancelLeaseOrderDetail.createCancelLeaseOrderValueMap(data.subOrder);
                    // 赋值父工单信息
                    jsNewCancelLeaseOrderDetail.generatorData(data.subOrder);
                    // 赋值各流程信息
                    jsNewCancelLeaseOrderDetail.generatorHtml(data.subOrder);
                    // 创建财务明细
                    jsNewCancelLeaseOrderDetail.createFinancePayTable(data.subOrder.rentalLeaseOrderId);
                    // 初始化各TAB页
                    jsNewCancelLeaseOrderDetail.initTab(data.subOrder);
                }
            }
        });
    },

    // 退租订单转成MAP
    createCancelLeaseOrderValueMap: function(data) {
        var valueList = data.subOrderValueList;
        for (var i = 0; i < valueList.length; i++) {
            var cancelLeaseOrderValue = valueList[i];
            var key = cancelLeaseOrderValue.attrPath;
            cancelLeaseOrderValueMap[key] = cancelLeaseOrderValue;
        }
    },    
    
    closeRentalFinanceDiv: function() {
    	$("#financeRentalPayDiv").hide();
    	$("#closeRentalFinanceDiv").hide();
    	$("#openRentalFinanceDiv").show();
    },
    openRentalFinanceDiv: function() {
    	$("#financeRentalPayDiv").show();
    	$("#closeRentalFinanceDiv").show();
    	$("#openRentalFinanceDiv").hide();
    },    
    closeRentalDiv: function() {
    	$("#financeRentalDiv").hide();
    	$("#closeRentalDiv").hide();
    	$("#openRentalDiv").show();
    },    
    openRentalDiv: function() {
    	$("#financeRentalDiv").show();
    	$("#closeRentalDiv").show();
    	$("#openRentalDiv").hide();
    },
    
    closeFinanceDiv: function() {
    	$("#financeDiv").hide();
    	$("#closeFinanceDiv").hide();
    	$("#openFinanceDiv").show();
    },
    openFinanceDiv: function() {
    	$("#financeDiv").show();
    	$("#closeFinanceDiv").show();
    	$("#openFinanceDiv").hide();
    },
    hideDiv: function() {
    	$("#financePayDiv").hide();
    	$("#closeDiv").hide();
    	$("#openDiv").show();
    },
    openDiv: function() {
    	$("#financePayDiv").show();
    	$("#closeDiv").show();
    	$("#openDiv").hide();
    },
    
    delInfo: function(id) {
      common.alert({
        msg: "确定需要删除吗？",
        confirm: true,
        fun: function(action) {
            if (action) {
                common.load.load();
            		common.ajax({
            			url : common.root + '/CertificateLeave/delete.do',
            			data : {
            				id : id,
            				orderId : workOrder.id,
            				cancelType: respData.type,
            				rentalLeaseOrderId : workOrder.rentalLeaseOrderId
            			},
            			dataType : 'json',
            			loadfun : function(isloadsucc, data) {
            				if (isloadsucc) {
            					common.load.hide();
            					if (data.state == -2) {
            						common.alert({
            							msg : "删除失败"
            						});
            						return;
            					} else {
            						common.alert({ // 操作成功
            							msg : '删除成功'
            						});
            					}
            					location.reload();
            				} else {
            					common.load.hide();
            					common.alert({
            						msg : common.msg.error
            					});
            				}
            			}
            		});
            }
        }
      });     	
    },
    
  
    // 处理验房说明的内容
    commentsDeal: function(valStr) {
    	// 如果为空，则显示默认值
    	var str = valStr.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '');
    	$('.js-house-inspection-comments-textarea').val(str);
    },
    
    // 处理验房说明的内容
    commentsLiquidationDeal: function(valStr) {
    	// 如果为空，则显示默认值
    	var str = valStr.replace(/[^\u0020-\u007E\u00A0-\u00BE\u2E80-\uA4CF\uF900-\uFAFF\uFE30-\uFE4F\uFF00-\uFFEF\u0080-\u009F\u2000-\u201f]/g, '');
    	$('.js-costLiquidation-comments-textarea').val(str);
    },
    
    // 保存应退款项信息
    saveRefundItem: function() {
    	var costLiquidationName = $.trim($("#costLiquidationName").val());
    	if (costLiquidationName == '') {
    		common.alert({
    			msg : '请填写名称'
    		});
    		return;
    	}
    	var costLiquidationType = $.trim($("#costLiquidationType").val());
    	if (costLiquidationType == '') {
    		common.alert({
    			msg : '请选择类型'
    		});
    		return;   		
    	}
    	var costLiquidationCost = $.trim($("#costLiquidationCost").val());
    	if (costLiquidationCost == '') {
    		common.alert({
    			msg : '请填写金额'
    		});
    		return;
    	}
    	var costLiquidationDescription = $.trim($("#costLiquidationDescription").val());
    	if (costLiquidationDescription == '') {
    		common.alert({
    			msg : '请填写说明'
    		});
    		return;   		
    	}
    	
//    	if (costLiquidationCost == 0) {
//    		common.alert({
//    			msg : '金额不能填写0！'
//    		});
//    		return;
//    	}
    	var reg = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^(0){1}$)|(^\d\.\d{1,2}?$)/;
      // 正则判断金额填写是否正确
      if (!reg.test(costLiquidationCost) || costLiquidationCost < 0) {
          common.alert({
              msg: "金额填写错误！只填写正数且保留两位小数点"
          });
          return;
      }
      // 根据结算类型，判断金额是否为负数
      var costLiquidationCostType = $("#costLiquidationCostType").val();
      if (costLiquidationCostType == '1') {
      	// 应收，正数
      	costLiquidationCost = costLiquidationCost;
      } else {
      	// 应付，负数
      	costLiquidationCost = -costLiquidationCost;
      }
      
      common.alert({
        msg: "确定需要保存信息吗？",
        confirm: true,
        fun: function(action) {
            if (action) {
                common.load.load();
            		common.ajax({
            			url : common.root + '/CertificateLeave/create.do',
            			data : {
            				orderId : workOrder.id,
            				taskId : -1,
            				financial_type : costLiquidationCostType,
            				cancelType: respData.type,
            				rentalLeaseOrderId : workOrder.rentalLeaseOrderId,
            				starttime : '',
            				endtime : '',
            				name : '补录-'+costLiquidationName,
            				type : costLiquidationType,
            				cost : costLiquidationCost,
            				desc : costLiquidationDescription,
            				step_type : -1
            			},
            			dataType : 'json',
            			loadfun : function(isloadsucc, data) {
            				if (isloadsucc) {
            					common.load.hide();
            					if (data.state == -2) {
            						common.alert({
            							msg : data.msg
            						});
            					} else {
            						common.alert({ // 操作成功
            							msg : '新增成功'
            						});
            						$("#addRefundItem").modal('hide');
            						location.reload();
            					}
            				} else {
            					common.load.hide();
            					common.alert({
            						msg : common.msg.error
            					});
            				}
            			}
            		});
            }
        }
      });   	
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
        // 费用清算
        this.generatorCostLiquidationHtml(data);
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
        $('#takeOrderCloseBtn').addClass('hidden');
    },

    generatorReleaseHouseRankData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.HOUSE_RELEASE.COMMENTS'] != null) {
            $('.js-comments-release-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.HOUSE_RELEASE.COMMENTS'].textInput);
        }
    },

    generatorReleaseHouseRankReadOnlyHtml: function() {
        this.generatorReleaseHouseRankData();
        $('.js-comments-release-textarea').attr('readonly', true);
        $('.js-butler-date').attr("disabled",true);
        $('#houseReleaseBtn').addClass('hidden');
        $('#houseCloseBtn').addClass('hidden');
    },
    
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
        // 物业费
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.PROPERTY_FEE'] != null) {
            $('.js-property-fee-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.PROPERTY_FEE'].textInput);
        } else {
        		$('.js-property-fee-input').val(0);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_TO'] != null) {
            $('.js-water-nbr-to-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_TO'].textInput);
        }
        // 垃圾费
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WASTE_FEE'] != null) {
            $('.js-waste-fee-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WASTE_FEE'].textInput);
        } else {
        		$('.js-waste-fee-input').val(0);
        }
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_TO'] != null) {
            $('.js-gas-nbr-to-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_TO'].textInput);
        }
        // 水费单价
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_UNIT_PRICE'] != null) {
            $('.js-water-unit-price-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_UNIT_PRICE'].textInput);
        } else {
        		$('.js-water-unit-price-input').val(0);
        }
        // 燃气单价
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_UNIT_PRICE'] != null) {
        		$('.js-gas-unit-price-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_UNIT_PRICE'].textInput);
        } else {
        		$('.js-gas-unit-price-input').val(0);
        }
        // 电费单价
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_UNIT_PRICE'] != null) {
        		$('.js-ammeter-unit-price-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_UNIT_PRICE'].textInput);
        } else {
        		$('.js-ammeter-unit-price-input').val(0);
        }
        // 实际退租时间
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ACTUAL_CANCEL_LEASE_TIME'] != null) {
        		$('.js-actual-cancel-lease-time-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.ACTUAL_CANCEL_LEASE_TIME'].textInput);
        } else {
        		// 显示【房源释放】环节填写的上门时间
        		$('.js-actual-cancel-lease-time-input').val(respData.butlerGetHouseDate);
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

        // 备注展示
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_INSPECTION_COMMENTS'] != null) {
        		var testareaStr = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_INSPECTION_COMMENTS'].textInput;
        		if (testareaStr != null && testareaStr != '') {
        			$('.js-house-inspection-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_INSPECTION_COMMENTS'].textInput);
        		} else {
            	// 如果为空，则显示默认信息
            	var str = "特殊审批流程单号：\n"+ respData.typeName + "：\n" + "退预缴费（）+退押金()+退房租（合计数）（期间）+已交水（）+已交电（）+已交燃（）+已交物业（）-扣房租（合计数）（期间）-应交水（）-应交电（）-应交燃（）-应交物业费（）-活动追缴款（）（期间）-赔偿款（）（赔偿XXX费）-保洁费（）=实退（）";
            	$('.js-house-inspection-comments-textarea').html(str);        			
        		}
        } else {
        	// 如果为空，则显示默认信息
        	var str = "特殊审批流程单号：\n"+ respData.typeName + "：\n" + "退预缴费（）+退押金()+退房租（合计数）（期间）+已交水（）+已交电（）+已交燃（）+已交物业（）-扣房租（合计数）（期间）-应交水（）-应交电（）-应交燃（）-应交物业费（）-活动追缴款（）（期间）-赔偿款（）（赔偿XXX费）-保洁费（）=实退（）";
        	$('.js-house-inspection-comments-textarea').html(str);
        }
        
        // 根据出租合约主键id，获取水电燃初始值
        common.ajax({
          url: common.root + '/cancelLease/getInitialValue.do',
          dataType: 'json',
          contentType: 'application/json; charset=utf-8',
          encode: false,
          data: JSON.stringify({'rentalLeaseOrderId': respData.rentalLeaseOrderId}),
          loadfun: function(isloadsucc, data) {
          	// 水表初始读数
          	var waterMeter = data.waterMeter;
          	if (waterMeter == null || waterMeter == undefined || waterMeter == '' || waterMeter == 'null') {
          		waterMeter = 0;
          	}
            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM'] != null) {
              $('.js-water-nbr-from-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.WATER_NBR_FROM'].textInput);
            } else {
            	$(".js-water-nbr-from-input").val(waterMeter);
            }          	
          	
          	// 电表初始度数
          	var electricityMeter = data.electricityMeter;
          	if (electricityMeter == null || electricityMeter == undefined || electricityMeter == '' || electricityMeter == 'null') {
          		electricityMeter = 0;
          	}
            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM'] != null) {
              $('.js-ammeter-nbr-from-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.AMMETER_NBR_FROM'].textInput);
            } else {
            	$(".js-ammeter-nbr-from-input").val(electricityMeter);
            }
          	
          	// 燃气初始度数
          	var gasMeter = data.gasMeter;
          	if (gasMeter == null || gasMeter == undefined || gasMeter == '' || gasMeter == 'null') {
          		gasMeter = 0;
          	}
            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM'] != null) {
              $('.js-gas-nbr-from-input').val(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.GAS_NBR_FROM'].textInput);
            } else {
            	$(".js-gas-nbr-from-input").val(gasMeter);
            }
          }
      });        
    },

    generatorButlerGetHomeReadOnlyHtml: function() {
        this.generatorButlerGetHomeData();
        $('.js-actual-cancel-lease-time-input').attr("disabled",true);
        $('.js-property-fee-input').attr('readonly', true);
        $('.js-waste-fee-input').attr('readonly', true);
        $('.js-water-unit-price-input').attr('readonly', true);
        $('.js-gas-unit-price-input').attr('readonly', true);
        $('.js-ammeter-unit-price-input').attr('readonly', true);
        $('.js-alipay-input').attr('readonly', true);
        $('.js-bank-card-nbr-input').attr('readonly', true);
        $('.js-bank-adress-input').attr('readonly', true);
        $('.js-water-nbr-from-input').attr('readonly', true);
        $('.js-water-nbr-to-input').attr('readonly', true);
        $('.js-gas-nbr-from-input').attr('readonly', true);
        $('.js-gas-nbr-to-input').attr('readonly', true);
        $('.js-ammeter-nbr-from-input').attr('readonly', true);
        $('.js-ammeter-nbr-to-input').attr('readonly', true);
        // NBR_ATTACHMENT_UPLOAD 灰化
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
        $('#houseCloseGetHomeBtn').addClass('hidden');
        $("#againAssignOrder").addClass("hidden");
    },
    
    // 赋值费用清单，并且只读
    generatorCostLiquidationReadOnlyHtml: function() {
      this.generatorCostLiquidationData();
      $("#costLiquidationBtn").css("display","none");
//      $('.js-costLiquidation-type-radio input').attr('disabled', true);
      $('.js-costLiquidation-total-money-input').attr('readonly', true);
      $('.js-costLiquidation-comments-textarea').attr('readonly', true);
      $('#costLiquidationPassBtn').addClass('hidden');
      $('#costLiquidationReturnBtn').addClass('hidden');
    },
    
    // 赋值费用清单数据，并可编辑
    generatorCostLiquidationData: function() {
//    	if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.FINANCE_TYPE'] != null) {
//    		var financeTypeSelected = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.FINANCE_TYPE'].textInput;
//    		if (financeTypeSelected != null && financeTypeSelected != undefined && financeTypeSelected != '') {
//    			if (financeTypeSelected == 0) {
//    				$(".costLiquidationRadioIn").attr('checked', 'checked');
//    			} else {
//    				$(".costLiquidationRadioOut").attr('checked', 'checked');
//    			}
//    		}
//    	}
      var totalMoney = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.TOTAL_MONEY'];
      if (totalMoney != null) {
          if (totalMoney.textInput < 0) {
              $('.js-costLiquidation-total-money-input').val(-totalMoney.textInput);
          } else {
              $('.js-costLiquidation-total-money-input').val(totalMoney.textInput);
          }
      }
      if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.COMMENTS'] != null) {
          $('.js-costLiquidation-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.COMMENTS'].textInput);
      } else {
      	// 如果为空，则显示默认信息
      	var str = "特殊审批流程单号：\n"+ respData.typeName + "：\n" + "退预缴费（）+退押金()+退房租（合计数）（期间）+已交水（）+已交电（）+已交燃（）+已交物业（）-扣房租（合计数）（期间）-应交水（）-应交电（）-应交燃（）-应交物业费（）-活动追缴款（）（期间）-赔偿款()（赔偿XXX费）-保洁费(   ）=实退（）";
      	$('.js-costLiquidation-comments-textarea').html(str);
      }
    },    

    generatorRentalAccountData: function() {
        if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.COMMENTS'] != null) {
            $('.js-rental-account-comments-textarea').html(cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.RENTAL_ACCOUNT.COMMENTS'].textInput);
        }
    },

    generatorRentalAccountReadOnlyHtml: function() {
        this.generatorRentalAccountData();
        $(".js-liquidation-rental-account").hide();
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
            case subOrderStateDef.REASSIGNING: // 重新指派订单
            case subOrderStateDef.TAKE_ORDER: // 员工接单
                $('.js-tab > li').find("a[href='#TAKE_ORDER']").parent().addClass('active');
                $('#TAKE_ORDER').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderData();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                }
                break;
            case subOrderStateDef.RELEASE_HOUSE_RANK: // 房源释放
                $('.js-tab > li').find("a[href='#HOUSE_RELEASE']").parent().addClass('active');
                $('#HOUSE_RELEASE').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankData();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                }
                break;
            case subOrderStateDef.DO_IN_ORDER: // 管家上门
            case subOrderStateDef.NOT_COST_LIQUIDATION: // 费用清算未通过
                $('.js-tab > li').find("a[href='#BUTLER_GET_HOME']").parent().addClass('active');
                $('#BUTLER_GET_HOME').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeData();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                }
                break;
            case subOrderStateDef.COST_LIQUIDATION: // 费用清算
            case subOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING: // 租务审核未通过
	            	$('.js-tab > li').find("a[href='#COST_LIQUIDATION']").parent().addClass('active');
	            	$('#COST_LIQUIDATION').addClass('in active');
	            	jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
	            	jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
	            	jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
	            	jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
	            	jsNewCancelLeaseOrderDetail.generatorCostLiquidationData();
	            	jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
	            	jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
	            	if (!resFlag) {
	            		jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
	            		jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
	            	} else {
	            		jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 1);
	            	}
//	            	jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
	            	break;
            case subOrderStateDef.RENTAL_ACCOUNTING: // 租务审核
                $('.js-tab > li').find("a[href='#TAKE_ORDER']").parent().removeClass('active');
                $('.js-tab > li').find("a[href='#RENTAL_ACCOUNT']").parent().addClass('active');
                $('#RENTAL_ACCOUNT').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountData();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                // 租务核算特有财务列表
                jsNewCancelLeaseOrderDetail.createFinanceRentalTableOutBtn(data, 0);
                jsNewCancelLeaseOrderDetail.createFinancePayRentalTableOutBtn(data.rentalLeaseOrderId);
                jsNewCancelLeaseOrderDetail.createWait2PayRentalTableWithOutBtn(data);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                }
                break;
            case subOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING: // 财务审核未通过
                $('.js-tab > li').find("a[href='#RENTAL_ACCOUNT']").parent().addClass('active');
                $('#RENTAL_ACCOUNT').addClass('in active');
                $('#TAKE_ORDER').removeClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountData();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                }
                break;
            case subOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING: // 市场部高管审批未通过
                $('.js-tab > li').find("a[href='#RENTAL_ACCOUNT']").parent().addClass('active');
                $('#RENTAL_ACCOUNT').addClass('in active');
                $('#TAKE_ORDER').removeClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountData();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                }
                break;
            case subOrderStateDef.MARKETING_EXECUTIVE_AUDITING: // 市场部高管审批
                $('.js-tab > li').find("a[href='#MARKETING_EXECUTIVE_AUDIT']").parent().addClass('active');
                $('#MARKETING_EXECUTIVE_AUDIT').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditData();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (respData.type == 'C') {
                    // 未入住违约退租，高管审批屏蔽打回按钮
                    $("#marketingExecutiveAuditReturnBtn").addClass('hidden');
                }
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                }
                break;
            case subOrderStateDef.FINANCE_AUDITING: // 财务审核
                $('.js-tab > li').find("a[href='#FINANCE_AUDIT']").parent().addClass('active');
                $('#FINANCE_AUDIT').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditData();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
                if (!resFlag) {
                    jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                }
                break;
            default:
                $('.js-tab > li').find("a[href='#TAKE_ORDER']").parent().addClass('active');
                $('#TAKE_ORDER').addClass('in active');
                jsNewCancelLeaseOrderDetail.generatorTakeOrderReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorReleaseHouseRankReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorCostLiquidationReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorButlerGetHomeReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorRentalAccountReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorMarketingExecutiveAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.generatorFinanceAuditReadOnlyHtml();
                jsNewCancelLeaseOrderDetail.createfinanceTableOutBtn(data, 0);
//                jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
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
                        $('.js-rental-name').html("<a onclick='jsNewCancelLeaseOrderDetail.sigleHouseInfo(" + date.house_rank_id + ",0," + date.house_id + ",\"" + date.rankType + "\",\"" + date.title + "\"," + data.rentalLeaseOrderId + ")'>" + date.name + "</a>");
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
//            case subOrderStateDef.COST_LIQUIDATION:
//              	stateName = 'COST_LIQUIDATION';
//              	break;                
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
      	// 回显管家上门时间，如果没有值，回显订单录入时间
    		if (data.butlerGetHouseDate == null || data.butlerGetHouseDate == undefined || data.butlerGetHouseDate == '' || data.butlerGetHouseDate == 'null') {
    			$('.js-butler-date').val(workOrder.appointmentDate);
    		} else {
    			$('.js-butler-date').val(data.butlerGetHouseDate);
    		}
      	
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
        $("#houseCloseGetHomeBtn").addClass("hidden");

        // AJAX获取管家信息
        common.ajax({
          url: common.root + '/cascading/getUserListByAuthority.do',
          dataType: 'json',
          data: {
              roleId: '21,22,29',
              type: 'A'
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
        $("#houseCloseGetHomeBtn").removeClass("hidden");

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
                  var commentsData = $('.js-house-inspection-comments-textarea').data('attr');
                  var param = {
                      'code': respData.code,
                      'newButlerId': butlerId,
                      'dealerId': dealerId,
                      'subOrderValueList': [{
                        'attrId': commentsData.id,
                        'attrPath': commentsData.attrPath,
                        'textInput': ""
                      }]
                  };
                  common.ajax({
                      url: common.root + '/cancelLease/reassignOrder.do',
                      dataType: 'json',
                      encode: false,
                      contentType: 'application/json; charset=utf-8',
                      data: JSON.stringify(param),
                      loadfun: function(isloadsucc, data) {
	                    	common.load.hide();
	                    	jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-alipay-label'));
                            $('.js-alipay-input').data('attr', childAttr);
                        } else if (childAttr.code == 'BANK_CARD_NBR') {
                            $('.js-bank-card-nbr-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-bank-card-nbr-label'));
                            $('.js-bank-card-nbr-input').data('attr', childAttr);
                        } else if (childAttr.code == 'BANK_ADDRESS') {
                            $('.js-bank-address-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-bank-address-label'));
                            $('.js-bank-adress-input').data('attr', childAttr);
                        } else if (childAttr.code == 'WATER_NBR_FROM') {
                            $('.js-water-nbr-from-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-water-nbr-from-label'));
                            $('.js-water-nbr-from-input').data('attr', childAttr);
                        } else if (childAttr.code == 'WATER_NBR_TO') {
                            $('.js-water-nbr-to-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-water-nbr-to-label'));
                            $('.js-water-nbr-to-input').data('attr', childAttr);
                        } else if (childAttr.code == 'GAS_NBR_FROM') {
                            $('.js-gas-nbr-from-label').html(childAttr.name);
                            $('.js-gas-nbr-from-input').data('attr', childAttr);
                        } else if (childAttr.code == 'GAS_NBR_TO') {
                            $('.js-gas-nbr-to-label').html(childAttr.name);
                            $('.js-gas-nbr-to-input').data('attr', childAttr);
                        } else if (childAttr.code == 'AMMETER_NBR_FROM') {
                            $('.js-ammeter-nbr-from-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-ammeter-nbr-from-label'));
                            $('.js-ammeter-nbr-from-input').data('attr', childAttr);
                        } else if (childAttr.code == 'AMMETER_NBR_TO') {
                            $('.js-ammeter-nbr-to-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-ammeter-nbr-to-label'));
                            $('.js-ammeter-nbr-to-input').data('attr', childAttr);
                        } else if (childAttr.code == 'NBR_ATTACHMENT') {
                            $('.js-nbr-attachment-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-nbr-attachment-label'));
                            var readonly = false;
                            if (data.state == subOrderStateDef.DO_IN_ORDER ||
                                data.state == subOrderStateDef.NOT_COST_LIQUIDATION) {
                                readonly = true;
                            }
                            var image = null;
                            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.NBR_ATTACHMENT'] != null) {
                                image = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.NBR_ATTACHMENT'].textInput;
                            }
                            if (isMobile == 'N') {
                                $('.js-nbr-attachment-upload').data('attr', childAttr);
                                $('.js-nbr-attachment-upload').addClass('dropzone');
                                jsNewCancelLeaseOrderDetail.imageDeal(image == null ? '' : image, 'NBR_ATTACHMENT_UPLOAD', readonly);
                            } else {
                                $('#js-nbr-attachment-images').data('attr', childAttr);
                                njyc.phone.showPic(jsNewCancelLeaseOrderDetail.changeImagePath(image), 'js-nbr-attachment-images');
                                if (!readonly) {
                                    $('#js-nbr-attachment-upload').addClass('hidden');
                                    $('#js-nbr-attachment-images b').addClass('hidden');
                                }
                            }
                        } else if (childAttr.code == 'CANCEL_LEASE_REASON') {
                            $('.js-cancel-lease-reson-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-cancel-lease-reson-label'));
                            $('.js-cancel-lease-reason-select').data('attr', childAttr);
                            if (childAttr.attrValueList) {
                                for (var k = 0; k < childAttr.attrValueList.length; k++) {
                                    var reason = childAttr.attrValueList[k];
                                    $('.js-cancel-lease-reason-select').append('<option value="' + reason.id + '">' + reason.valueMask + '</option>');
                                }
                            }
                        } else if (childAttr.code == 'PROMOTION_EXPLANATION') {
                            $('.js-promotion-explanation-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-promotion-explanation-label'));
                            $('.js-promotion-explanation-input').data('attr', childAttr);
                        } else if (childAttr.code == 'KEY_RECOVERY') {
                            $('.js-key-recovery-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-key-recovery-label'));
                            $('.js-key-recovery-input').data('attr', childAttr);
                        } else if (childAttr.code == 'DOOR_CARD_RECOVERY') {
                            $('.js-door-card-recovery-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-door-card-recovery-label'));
                            $('.js-door-card-recovery-input').data('attr', childAttr);
                        } else if (childAttr.code == 'OTHER_RECOVERY') {
                            $('.js-other-recovery-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-other-recovery-label'));
                            $('.js-other-recovery-input').data('attr', childAttr);
                        } else if (childAttr.code == 'HOUSE_PICTURE') {
                            $('.js-house-picture-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-house-picture-label'));
                            var readonly = false;
                            if (data.state == subOrderStateDef.DO_IN_ORDER ||
                                data.state == subOrderStateDef.NOT_COST_LIQUIDATION) {
                                readonly = true;
                            }
                            var image = null;
                            if (cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_PICTURE'] != null) {
                                image = cancelLeaseOrderValueMap['CANCEL_LEASE_ORDER_PROCESS.BUTLER_GET_HOME.HOUSE_PICTURE'].textInput;
                            }
                            if (isMobile == 'N') {
                                $('.js-house-picture-upload').data('attr', childAttr);
                                $('.js-house-picture-upload').addClass('dropzone');
                                jsNewCancelLeaseOrderDetail.imageDeal(image == null ? '' : image, 'HOUSE_PICTURE_UPLOAD', readonly);
                            } else {
                                $('#js-house-picture-images').data('attr', childAttr);
                                njyc.phone.showPic(jsNewCancelLeaseOrderDetail.changeImagePath(image), 'js-house-picture-images');
                                if (!readonly) {
                                    $('#js-mobile-house-picture-upload').addClass('hidden');
                                    $('#js-house-picture-images b').addClass('hidden');
                                }
                            }
                        } else if (childAttr.code == 'HOUSE_INSPECTION_COMMENTS') {
                            $('.js-house-inspection-comments-label').html(childAttr.name);
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-house-inspection-comments-label'));
                            $('.js-house-inspection-comments-textarea').data('attr', childAttr);
                        } else if (childAttr.code == 'WASTE_FEE') {
                        	$('.js-waste-fee-label').html(childAttr.name);
                          jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-waste-fee-label'));
                          $('.js-waste-fee-input').data('attr', childAttr);                        		
                        } else if (childAttr.code == 'WATER_UNIT_PRICE') {
                        	$('.js-water-unit-price-label').html(childAttr.name);
                          jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-water-unit-price-label'));
                          $('.js-water-unit-price-input').data('attr', childAttr);                        		
                        } else if (childAttr.code == 'GAS_UNIT_PRICE') {
                        	$('.js-gas-unit-price-label').html(childAttr.name);
                          jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-gas-unit-price-label'));
                          $('.js-gas-unit-price-input').data('attr', childAttr);                        		
                        } else if (childAttr.code == 'AMMETER_UNIT_PRICE') {
                        	$('.js-ammeter-unit-price-label').html(childAttr.name);
                          jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-ammeter-unit-price-label'));
                          $('.js-ammeter-unit-price-input').data('attr', childAttr);                        		
                        } else if (childAttr.code == 'ACTUAL_CANCEL_LEASE_TIME') {
                        	$('.js-actual-cancel-lease-time-label').html(childAttr.name);
                          jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-actual-cancel-lease-time-label'));
                          $('.js-actual-cancel-lease-time-input').data('attr', childAttr);                        		
                        } else if (childAttr.code == 'PROPERTY_FEE') {
                        	$('.js-property-fee-label').html(childAttr.name);
                          jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-property-fee-label'));
                          $('.js-property-fee-input').data('attr', childAttr);                        		
                        }
                    }
                }
            }
        }
    },
    
    // 费用清算
    generatorCostLiquidationHtml: function(data) {
      if (data.attrCatg.code == 'CANCEL_LEASE_ORDER_PROCESS') {
      	for (var i = 0; i < data.attrCatg.attrCatgChildren.length; i++) {
      		var childCatg = data.attrCatg.attrCatgChildren[i];
      		if (childCatg.code == 'COST_LIQUIDATION') {
      			var childAttrList = childCatg.attrChildren;
      			for (var j = 0; j < childAttrList.length; j++) {
      				var defaultPath = "CANCEL_LEASE_ORDER_PROCESS.COST_LIQUIDATION.";
      				var childAttr = childAttrList[j];
      				var attrPath = defaultPath + childAttr.code;
      				childAttr.attrPath = attrPath;
      				if (childAttr.code == 'FINANCE_TYPE') {
      					$('.js-costLiquidation-type-label').html(childAttr.name);
      					jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-costLiquidation-type-label'));
      					$('.js-costLiquidation-type-radio').data('attr', childAttr);
//      					if (childAttr.attrValueList != null && childAttr.attrValueList != '') {
//      						for (var k = 0; k < childAttr.attrValueList.length; k++) {
//      							var attrValue = childAttr.attrValueList[k];
//      							if (attrValue.value == 0) {
//      								$('.js-costLiquidation-type-radio').append('<div class="radio-inline">' +'<input name="costLiquidationRadioType" type="radio" code="in" checked="checked" value="' + attrValue.value + '"/>' + attrValue.valueMask + '</div>');
//      							} else {
//      								$('.js-costLiquidation-type-radio').append('<div class="radio-inline">' +'<input name="costLiquidationRadioType" type="radio" code="out" value="' + attrValue.value + '"/>' + attrValue.valueMask + '</div>');
//      							}
//      						}
//      					}
      				} else if (childAttr.code == 'TOTAL_MONEY') {
                $('.js-costLiquidation-total-money-label').html(childAttr.name);
                $('.js-costLiquidation-total-money-input').val("系统自动核算").attr('readonly', "readonly");
                jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-costLiquidation-total-money-label'));  
                $('.js-costLiquidation-total-money-input').data('attr', childAttr);
      				} else if (childAttr.code == 'COMMENTS') {
      					$('.js-costLiquidation-comments-label').html(childAttr.name);
      					jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-costLiquidation-comments-label'));
      					$('.js-costLiquidation-comments-textarea').data('attr', childAttr);
      				} else if (childAttr.code == 'PASSED') {
      					$('#costLiquidationPassBtn').data('attr', childAttr);
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
                        if (childAttr.code == 'COMMENTS') {
                            $('.js-rental-account-comments-label').html("核算"+childAttr.name);
                            
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-rental-account-comments-label'));
                            $('.js-rental-account-comments-label').prepend('<b style="color: red ;">*</b>');
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
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-marketing-executive-audit-comments-label'));
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
                            jsNewCancelLeaseOrderDetail.addMandatory(childAttr, $('.js-finance-audit-comments-label'));
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
                njyc.phone.showPic(jsNewCancelLeaseOrderDetail.changeImagePath(picurls), 'js-judge-audit-image-url-mobile');
                if (data.state != 'D') {
                    $('#upurl-1').addClass('hidden');
                    $('#upurl-1 b').addClass('hidden');
                }
            } else {
                if (data.state == subOrderStateDef.DO_IN_ORDER && resFlag == true) {
                    jsNewCancelLeaseOrderDetail.initPicurls(picurls, 'upurl-1', true);
                } else {
                    jsNewCancelLeaseOrderDetail.initPicurls(picurls, 'upurl-1', false);
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
        $('#takeOrderCloseBtn').addClass('hidden');

        common.ajax({
          url: common.root + '/cascading/getUserListByAuthority.do',
          dataType: 'json',
          data: {
              roleId: '21,22,29',
              type: 'A'
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
	  		// 管家上门时间（可以小于录入的时间，但不能小于当前时间）
	  		var butlerGetHouseDate = $(".js-butler-date").val();
	  		if (butlerGetHouseDate == '') {
	        common.alert({
	          msg: "上门时间不能为空，请选择！"
	        });
	        return;	  			
	  		}
	  		var butlerGetHouseDateStr = butlerGetHouseDate.split(" ")[0].replaceAll("-", "");
	  		// 当前时间
	  		var nowDate = new Date();
	  		var query_year = nowDate.getFullYear();
	  		var query_month = nowDate.getMonth() + 1;
	  		query_month = query_month < 10 ? "0" + (query_month) : query_month;
	  		var query_day = nowDate.getDate();
	  		query_day = query_day < 10 ? "0" + query_day : query_day;
	  		var nowDateStr = query_year+query_month+query_day;
	  		var nowTime = query_year+"-"+query_month+"-"+query_day;
	  		if (parseInt(butlerGetHouseDateStr) < parseInt(nowDateStr)) {
	        common.alert({
	          msg: "上门时间不能小于当前时间【"+nowTime+"】！"
	        });
	        return;
	  		}     	
    	
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
                        'butlerGetHouseDate': butlerGetHouseDate,
                        'subOrderValueList': [{
                            'attrId': commentsData.id,
                            'attrPath': commentsData.attrPath,
                            'textInput': $.trim($('.js-comments-release-textarea').val())
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
        $('#takeOrderCloseBtn').removeClass('hidden');
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
            } else if (data.state == 2) {
              common.alert({
                msg: data.errorMsg
            });
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
            str = "确定提交？提交后租客将无法再使用优惠券，请告知租客";
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
                    var propertyFeeData = $('.js-property-fee-input').data('attr');
                    var wasteFeeData = $('.js-waste-fee-input').data('attr');
                    var waterUnitPriceData = $('.js-water-unit-price-input').data('attr');
                    var gasUnitPriceData = $('.js-gas-unit-price-input').data('attr');
                    var ammeterUnitPriceData = $('.js-ammeter-unit-price-input').data('attr');
                    var actualCancelLeaseTimeData = $('.js-actual-cancel-lease-time-input').data('attr');
                    var nbrAttachmentVal;
                    var nbrAttachmentData;
                    if (isMobile == 'Y') {
                        nbrAttachmentData = $('#js-nbr-attachment-images').data('attr');
                        nbrAttachmentVal = jsNewCancelLeaseOrderDetail.getMobileImagePath($('#js-nbr-attachment-images input[name="picImage"]'));
                    } else {
                        nbrAttachmentData = $('.js-nbr-attachment-upload').data('attr');
                        nbrAttachmentVal = jsNewCancelLeaseOrderDetail.imagework('NBR_ATTACHMENT_UPLOAD');
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
                        housePictureVal = jsNewCancelLeaseOrderDetail.getMobileImagePath($('#js-house-picture-images input[name="picImage"]'));
                    } else {
                        housePictureData = $('.js-house-picture-upload').data('attr');
                        housePictureVal = jsNewCancelLeaseOrderDetail.imagework('HOUSE_PICTURE_UPLOAD');
                    }
                    var houseInspectionCommentsData = $('.js-house-inspection-comments-textarea').data('attr');
                    var param = {
                        'code': respData.code,
                        'dealerId': dealerId,
                        'submitFlag': flag,
                        'subOrderValueList': [{
                            'attrId': alipayData.id,
                            'attrPath': alipayData.attrPath,
                            'textInput': $.trim($('.js-alipay-input').val())
                        }, {
                          	'attrId': propertyFeeData.id,
                          	'attrPath': propertyFeeData.attrPath,
                          	'textInput': $.trim($('.js-property-fee-input').val()).replace(/ /g,'')
                        }, {
	                      		'attrId': wasteFeeData.id,
	                      		'attrPath': wasteFeeData.attrPath,
	                      		'textInput': $.trim($('.js-waste-fee-input').val()).replace(/ /g,'')
                        }, {
                        		'attrId': waterUnitPriceData.id,
                        		'attrPath': waterUnitPriceData.attrPath,
                        		'textInput': $.trim($('.js-water-unit-price-input').val()).replace(/ /g,'')
                        }, {
	                      		'attrId': gasUnitPriceData.id,
	                      		'attrPath': gasUnitPriceData.attrPath,
	                      		'textInput': $.trim($('.js-gas-unit-price-input').val()).replace(/ /g,'')
                        }, {
	                      		'attrId': ammeterUnitPriceData.id,
	                      		'attrPath': ammeterUnitPriceData.attrPath,
	                      		'textInput': $.trim($('.js-ammeter-unit-price-input').val()).replace(/ /g,'')
                        }, {
	                      		'attrId': actualCancelLeaseTimeData.id,
	                      		'attrPath': actualCancelLeaseTimeData.attrPath,
	                      		'textInput': $.trim($('.js-actual-cancel-lease-time-input').val())
                        }, {
                            'attrId': bankCardNbrData.id,
                            'attrPath': bankCardNbrData.attrPath,
                            'textInput': $.trim($('.js-bank-card-nbr-input').val())
                        }, {
                            'attrId': bankAddressData.id,
                            'attrPath': bankAddressData.attrPath,
                            'textInput': $.trim($('.js-bank-adress-input').val())
                        }, {
                            'attrId': waterNbrFromData.id,
                            'attrPath': waterNbrFromData.attrPath,
                            'textInput': $.trim($('.js-water-nbr-from-input').val())
                        }, {
                            'attrId': waterNbrToData.id,
                            'attrPath': waterNbrToData.attrPath,
                            'textInput': $.trim($('.js-water-nbr-to-input').val())
                        }, {
                            'attrId': gasNbrFromData.id,
                            'attrPath': gasNbrFromData.attrPath,
                            'textInput': $.trim($('.js-gas-nbr-from-input').val())
                        }, {
                            'attrId': gasNbrToData.id,
                            'attrPath': gasNbrToData.attrPath,
                            'textInput': $.trim($('.js-gas-nbr-to-input').val())
                        }, {
                            'attrId': ammeterNbrFromData.id,
                            'attrPath': ammeterNbrFromData.attrPath,
                            'textInput': $.trim($('.js-ammeter-nbr-from-input').val())
                        }, {
                            'attrId': ammeterNbrToData.id,
                            'attrPath': ammeterNbrToData.attrPath,
                            'textInput': $.trim($('.js-ammeter-nbr-to-input').val())
                        }, {
                            'attrId': nbrAttachmentData.id,
                            'attrPath': nbrAttachmentData.attrPath,
                            'textInput': nbrAttachmentVal
                        }, {
                            'attrId': cancelLeaseReasonData.id,
                            'attrPath': cancelLeaseReasonData.attrPath,
                            'textInput': $.trim($('.js-cancel-lease-reason-select').val())
                        }, {
                            'attrId': promotionExplanationData.id,
                            'attrPath': promotionExplanationData.attrPath,
                            'textInput': $.trim($('.js-promotion-explanation-input').val())
                        }, {
                            'attrId': keyRecoveryData.id,
                            'attrPath': keyRecoveryData.attrPath,
                            'textInput': $.trim($('.js-key-recovery-input').val())
                        }, {
                            'attrId': doorRecoveryData.id,
                            'attrPath': doorRecoveryData.attrPath,
                            'textInput': $.trim($('.js-door-card-recovery-input').val())
                        }, {
                            'attrId': otherRecoveryData.id,
                            'attrPath': otherRecoveryData.attrPath,
                            'textInput': $.trim($('.js-other-recovery-input').val())
                        }, {
                            'attrId': housePictureData.id,
                            'attrPath': housePictureData.attrPath,
                            'textInput': housePictureVal
                        }, {
                            'attrId': houseInspectionCommentsData.id,
                            'attrPath': houseInspectionCommentsData.attrPath,
                            'textInput': $.trim($('.js-house-inspection-comments-textarea').val())
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
            nbrAttachment = jsNewCancelLeaseOrderDetail.getMobileImagePath($('#js-nbr-attachment-images input[name="picImage"]'));
        } else {
            nbrAttachment = jsNewCancelLeaseOrderDetail.imagework("NBR_ATTACHMENT_UPLOAD");
        }
        var cancelLeaseReason = $.trim($('.js-cancel-lease-reason-select').val());
        var promotionExplanation = $.trim($('.js-promotion-explanation-input').val());
        var keyRecovery = $.trim($('.js-key-recovery-input').val());
        var doorRecovery = $.trim($('.js-door-card-recovery-input').val());
        var otherRecovery = $.trim($('.js-other-recovery-input').val());
        var housePicture;
        if (isMobile == 'Y') {
            housePicture = jsNewCancelLeaseOrderDetail.getMobileImagePath($('#js-house-picture-images input[name="picImage"]'));
        } else {
            housePicture = jsNewCancelLeaseOrderDetail.imagework("HOUSE_PICTURE_UPLOAD");
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
        var regInfo = /(^[1-9](\d+)?(\.\d{1,2})?$)|(^(0){1}$)|(^\d\.\d{1,2}?$)/;
        // 验证物业费
        var propertyFee = $.trim($(".js-property-fee-input").val()).replace(/ /g,'');
        if (propertyFee == "") {
        	$(".js-property-fee-input").val(0);
          common.alert({
            msg: "请填写物业费，没有则填0"
          });
          return true;
        }
        
        if (!regInfo.test(propertyFee)) {
          common.alert({
              msg: "请填写正确的物业费，小数点保留两位！"
          });
          return true;
        }
        // 验证垃圾费
        var wasteFee = $.trim($(".js-waste-fee-input").val()).replace(/ /g,'');
        if (wasteFee == "") {
        	$(".js-waste-fee-input").val(0);
          common.alert({
            msg: "请填写垃圾费，没有则填0"
          });
          return true;
        }
        if (!regInfo.test(wasteFee)) {
          common.alert({
              msg: "请填写正确的垃圾费，小数点保留两位！"
          });
          return true;
        }        
        // 水费单价
        var waterUnitPrice = $.trim($(".js-water-unit-price-input").val()).replace(/ /g,'');
        if (waterUnitPrice == "") {
        	$(".js-water-unit-price-input").val(0);
          common.alert({
            msg: "请填写水费单价，没有则填0"
          });
          return true;
        }
        if (!regInfo.test(waterUnitPrice)) {
          common.alert({
              msg: "请填写正确的水费单价，小数点保留两位！"
          });
          return true;
        }         
        // 燃气单价 
        var gasUnitPrice = $.trim($(".js-gas-unit-price-input").val()).replace(/ /g,'');
        if (gasUnitPrice == "") {
        	$(".js-gas-unit-price-input").val(0);
          common.alert({
            msg: "请填写燃气单价 ，没有则填0"
          });
          return true;
        }
        if (!regInfo.test(gasUnitPrice)) {
          common.alert({
              msg: "请填写正确的燃气单价 ，小数点保留两位！"
          });
          return true;
        }          
        // 电费单价
        var ammeterUnitPrice = $.trim($(".js-ammeter-unit-price-input").val()).replace(/ /g,'');
        if (ammeterUnitPrice == "") {
        	$(".js-ammeter-unit-price-input").val(0);
          common.alert({
            msg: "请填写电费单价 ，没有则填0"
          });
          return true;
        }
        if (!regInfo.test(ammeterUnitPrice)) {
          common.alert({
              msg: "请填写正确的电费单价 ，小数点保留两位！"
          });
          return true;
        } 
        // 验证实际退租时间
        var actualCancelLeaseTime = $.trim($(".js-actual-cancel-lease-time-input").val());
        if (actualCancelLeaseTime == "") {
          common.alert({
            msg: "请选择实际退租时间"
          });
          return true;
        }
        
				var regInfo1 = /(^[1-9](\d+)?(\.\d{1,3})?$)|(^(0){1}$)|(^\d\.\d{1,3}?$)/;
        // 验证燃气起始度数
        if (gasNbrFrom == "") {
          common.alert({
            msg: "请确认合约的起始燃气度数！"
          });
          return true;
        } else {
            if (!regInfo1.test(gasNbrFrom)) {
                common.alert({
                    msg: "请填写正确的燃气上期度数！"
                });
                return true;
            }
        }
        // 验证燃气结束度数
        if (gasNbrTo == "") {
          common.alert({
            msg: "请填写燃气结束度数！"
          });
          return true;           
        } else {
            if (!regInfo1.test(gasNbrTo)) {
                common.alert({
                    msg: "请填写正确的燃气结束度数！"
                });
                return true;
            }
        }
        
        if (waterNbrFrom == "") {
          common.alert({
            msg: "请确认合约的起始水表度数！"
          });
          return true;
        }   
        
        if (ammeterNbrFrom == "") {
        	common.alert({
        		msg: "请确认合约的起始电表度数！"
        	});
        	return true;
        }        

        if (!Validator.Validate('butlerGetHomeForm')) {
            return true;
        }
        if (parseFloat(waterNbrFrom) >= parseFloat(waterNbrTo)) {
            common.alert({
                msg: "水表上期度数不得大于等于结束度数"
            });
            layer.tips("上期度数不得大于结束度数", $('.js-water-nbr-to-input'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
        
        if (parseFloat(gasNbrTo) > 0) {
          if (parseFloat(gasNbrFrom) > parseFloat(gasNbrTo)) {
            common.alert({
                msg: "燃气上期度数不得大于结束度数"
            });
            layer.tips("上期度数不得大于结束度数", $('.js-gas-nbr-to-input'), {
                tips: [1, '#d9534f']
            });
            return true;
          }        	
        }
        if (parseFloat(ammeterNbrFrom) >= parseFloat(ammeterNbrTo)) {
            common.alert({
                msg: "电表上期度数不得大于等于结束度数"
            });
            layer.tips("上期度数不得大于结束度数", $('.js-gas-nbr-to-input'), {
                tips: [1, '#d9534f']
            });
            return true;
        }
    },

    // 添加待缴费明细
    createCost: function() {
    		// 打开对于的窗口
    	$('#addRefundItem').on('show.bs.modal', function() {
    		// 清空之前信息
    		$("#costLiquidationName").val("");
    		$("#costLiquidationCost").val("");		
    		$("#costLiquidationDescription").val("");
    		
        common.ajax({
        	url: common.root + '/financial/type/getTypeListInfo.do',
        	data:{},
        	dataType: 'json',
        	loadfun: function(isloadsucc, data){
            	if (isloadsucc) {
            		var html = "";
		            for (var i = 0; i < data.length; i++) {
		            		html += '<option  value="' + data[i].id + '" >' + data[i].name + '</option>';
		            }
		            $('#costLiquidationType').empty();
		            $('#costLiquidationType').append(html);
            	}
            }
        });    		
    	});
    	$('#addRefundItem').modal('show');
    },
    
    /**
     * 财务
     */
    createfinanceTableOutBtn: function(data, isAgen) {
    	$('#financeTable').attr('width', '100%');
    	var type = respData.type;
    	var isNormal;
    	if (type == 'A') {
    		isNormal = 1;
    	} else {
    		isNormal = 0;
    	}
    	table.init({
    		id: '#financeTable',
    		url: common.root + '/flow/getFinanceTable.do',
    		"iDisplayLength": 100,
    		isexp: false,
    		bStateSave: false,
    		columns: [
    			"typeName",
    			"rent",
    			"property",
    			"pay",
    			"deposit",
    			"serviceFee",
    			"other",
    			"waterFee",
    			"electricityFee",
    			"gasCosts",
    			"trashFee"
    			],
    			param: function() {
    				var qryConf = new Array();
    				qryConf.push({
    					"name": "ager_id",
    					"value": respData.rentalLeaseOrderId
    				});
    				qryConf.push({
    					"name" : "isAgen",
    					"value" : isAgen
    				});
    				qryConf.push({
    					"name" : "isNormal",
    					"value" : isNormal
    				});            
    				qryConf.push({
    					"name" : "orderId",
    					"value" : workOrder.id
    				});            
    				return qryConf;
    			},
    			aoColumnDefs: [{
    				"bSortable": false,
    				"aTargets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
    			}],
    			createRow: function(rowindex, colindex, name, value, data, row) {
    				if (colindex == 0) {
    					return jsNewCancelLeaseOrderDetail.dealColumn({
    						"value": value,
    						"length": 5
    					});
    				}
    				if (rowindex == 0) {
    					// 应收水费
    					var waterFee = data.waterFee;
    					$("#waterYingShou").val(waterFee);
    					// 应收燃气费
    					var gasCosts = data.gasCosts;
    					$("#cardgasYingShou").val(gasCosts);
    					// 应收电费
    					var electricityFee = data.electricityFee;
    					$("#eMeterYingShou").val(electricityFee);
    				}
    			},        
    			fnDrawCallback: function() {
    				var summarizing = $("#financeTable").data('datajson').summarizing;
    				var cost = $(".js-costLiquidation-total-money-input").val();
    				if (summarizing.indexOf("-") > -1) {
    					summarizing = summarizing.substring(1, summarizing.length);
    					$(".costLiquidationRadioOut").attr('checked', 'checked');
    				} else {
    					$(".costLiquidationRadioIn").attr('checked', 'checked');
    				}
    				if (cost == '') {
    					$(".js-costLiquidation-total-money-input").val(summarizing);
    				} else {
    					// 判断数值是否一致，如果不一致，以结算为准
    					if (summarizing == cost) {
    						
    					} else {
    						$(".js-costLiquidation-total-money-input").val(summarizing);
    					}
    				}
    				// 水费参考值
    				var water = $("#financeTable").data('datajson').water;
    				$("#waterCanKao").val(water);
    				// 电费参考值
    				var eMeter = $("#financeTable").data('datajson').eMeter;
    				$("#eMeterCanKao").val(eMeter);
    				// 燃气参考值
    				var cardgas = $("#financeTable").data('datajson').cardgas;
    				$("#cardgasCanKao").val(cardgas);
    				// 备注信息
    				var comments = $('.js-costLiquidation-comments-textarea').html();
    				
    				if (respData.state == subOrderStateDef.COST_LIQUIDATION) {
    					// 费用清算
      				var str = "【水电煤系统结算参考金额：水费"+water+"元， 电费"+eMeter+"元，燃气费"+cardgas+"元】";
      				if (comments.indexOf("【") != -1) {
      					comments = comments.substring(comments.lastIndexOf("】") + 1, comments.length);
      				}
      				var comment = str + "\n" + comments;
      				$('.js-costLiquidation-comments-textarea').html(comment);    					
    				}
    				
    				$("#financeTable tr :first-child").remove();
    				jsNewCancelLeaseOrderDetail.createWait2PayTableWithOutBtn(data);
    			}
    	});
    	$('.dataTables_wrapper').hide();
    },
    
    createFinanceRentalTableOutBtn: function(data, isAgen) {
    	$('#financeRentalTable').attr('width', '100%');
    	var type = respData.type;
    	var isNormal;
    	if (type == 'A') {
    		isNormal = 1;
    	} else {
    		isNormal = 0;
    	}
    	table.init({
        id: '#financeRentalTable',
        url: common.root + '/flow/getFinanceTable.do',
        "iDisplayLength": 100,
        isexp: false,
        bStateSave: false,
        columns: [
            "typeName",
            "rent",
            "property",
            "pay",
            "deposit",
            "serviceFee",
            "other",
            "waterFee",
            "electricityFee",
            "gasCosts",
            "trashFee"
        ],
        param: function() {
            var qryConf = new Array();
            qryConf.push({
                "name": "ager_id",
                "value": respData.rentalLeaseOrderId
            });
            qryConf.push({
          		"name" : "isAgen",
          		"value" : isAgen
          	});
            qryConf.push({
          		"name" : "isNormal",
          		"value" : isNormal
          	});
            qryConf.push({
            	"name" : "orderId",
            	"value" : workOrder.id
            });            
            return qryConf;
        },
        aoColumnDefs: [{
            "bSortable": false,
            "aTargets": [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
        }],
        createRow: function(rowindex, colindex, name, value, data, row) {
          if (colindex == 0) {
            return jsNewCancelLeaseOrderDetail.dealColumn({
                "value": value,
                "length": 5
            });
          }
        },        
        fnDrawCallback: function() {
        	$("#financeRentalTable tr :first-child").remove();
        }
    	});
    	$('.dataTables_wrapper').hide();
    },

    /**
     * 查询财务明细
     */
    createFinancePayTable: function(agree) {
    	$('#financePayTable').attr('width', '100%');
    	table.init({
        id: '#financePayTable',
        url: common.root + '/flow/getFinanceDetails.do',
        "iDisplayLength": 100,
        isexp: false,
        bStateSave: false,
        columns: [
            "type_name",
            "cost",
            "yet_cost",
            "discount_cost",
            "start_time",
            "end_time",
            "state",
            "remarks"
        ],
        param: function() {
            var qryConf = new Array();
            qryConf.push({
                "name": "ager_id",
                "value": agree
            });
            return qryConf;
        },
        aoColumnDefs: [{
            "bSortable": false,
            "aTargets": [0, 1, 2, 3, 4, 5, 6, 7, 8]
        }],
        createRow: function(rowindex, colindex, name, value, data, row) {
          if (colindex == 0) {
            return jsNewCancelLeaseOrderDetail.dealColumn({
                "value": value,
                "length": 5
            });
          }
        },        
        fnDrawCallback: function() {
        	$("#financePayTable tr :first-child").remove();
        }
    	});
    	$('.dataTables_wrapper').hide();
    },
    
    createFinancePayRentalTableOutBtn: function(agree) {
    	$('#financePayRentalTable').attr('width', '100%');
    	table.init({
    		id: '#financePayRentalTable',
    		url: common.root + '/flow/getFinanceDetails.do',
    		"iDisplayLength": 100,
    		isexp: false,
    		bStateSave: false,
    		columns: [
    			"type_name",
    			"cost",
    			"yet_cost",
    			"discount_cost",
    			"start_time",
    			"end_time",
    			"state",
    			"remarks"
    			],
    			param: function() {
    				var qryConf = new Array();
    				qryConf.push({
    					"name": "ager_id",
    					"value": agree
    				});
    				return qryConf;
    			},
    			aoColumnDefs: [{
    				"bSortable": false,
    				"aTargets": [0, 1, 2, 3, 4, 5, 6, 7, 8]
    			}],
    			createRow: function(rowindex, colindex, name, value, data, row) {
    				if (colindex == 0) {
    					return jsNewCancelLeaseOrderDetail.dealColumn({
    						"value": value,
    						"length": 5
    					});
    				}
    			},        
    			fnDrawCallback: function() {
    				$("#financePayRentalTable tr :first-child").remove();
    			}
    	});
    	$('.dataTables_wrapper').hide();
    },

    createWait2PayRentalTableWithOutBtn: function(cancelLeaseOrderData) {
    	$('#wait2PayRentalTable').attr('width', '100%');
    	table.init({
        id: '#wait2PayRentalTable',
        url: common.root + '/CertificateLeave/getList.do',
        "iDisplayLength": 100,
        isexp: false,
        bStateSave: false,
        columns: [
        		"name",
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
            a.push({
              "name": "flag",
              "value": "Y"
            });            
            return a;
        },
       createRow: function(rowindex, colindex, name, value, data, row) {
         if (colindex == 0) {
           return jsNewCancelLeaseOrderDetail.dealColumn({
               "value": value,
               "length": 5
           });
         }
       }
    	});
        $('.dataTables_wrapper').hide();
    },    
    
    
    /**
     * 费用清算
     */
    createWait2PayTableWithOutBtn: function(cancelLeaseOrderData) {
    	$('#wait2PayTable').attr('width', '100%');
    	table.init({
        id: '#wait2PayTable',
        url: common.root + '/CertificateLeave/getList.do',
        "iDisplayLength": 100,
        isexp: false,
        bStateSave: false,
        columns: [
        		"name",
            "typename",
            "cost",
            "costdesc"
        ],
        bnt:[
  			  {
  				  name:'删除',
  				  isshow:function(data,row)
  				  {
  					  return true;
  				  },
  				  fun:function(data,row)
  				  {
  				  	
  				  }
  			  }        	
        ],
        aoColumnDefs: [{
            "bSortable": false,
            "aTargets": [0, 1, 2, 3, 4, 5]
        }],
        param: function() {
            var a = new Array();
            a.push({
                "name": "orderId",
                "value": respData.workOrderId
            });
            a.push({
              "name": "flag",
              "value": "Y"
            }); 
            return a;
        },
       createRow: function(rowindex, colindex, name, value, data, row) {
         if (colindex == 0) {
           return jsNewCancelLeaseOrderDetail.dealColumn({
               "value": value,
               "length": 5
           });
         }
      	 if (colindex == 3) {
      		 return '<input type="hidden" value="'+data.id+'" />' + value;
      	 }
       },
        fnDrawCallback: function() {
        	jsNewCancelLeaseOrderDetail.operateTable();
        }
    	});
        $('.dataTables_wrapper').hide();
    },
    
    operateTable: function() {
      $('#wait2PayTable tr').each(function(i, n) {
        if ($(this).find('td').size() != 0) {        	
        	$(this).find('td:eq(5)').empty();
        	if (resFlag && (respData.state == 'X' || respData.state == 'Q')) {
          	if ($(this).find('td:eq(1)').text().indexOf("补录") >= 0) {
          		var id = $(this).find('td:eq(4)').find('input').val();
          		var html = '<div style="color:#E74086;"><a onclick="jsNewCancelLeaseOrderDetail.delInfo('+id+');return false;" href="#">删除</a></div>';
          		$(this).find('td:eq(5)').html(html);
          	}
        	}
        }
      });
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
    
    /**
     * 费用清算环节，打回到管家上门
     */
    rentalLiquidationReturn: function() {
      var financeComments = $('.js-costLiquidation-comments-textarea').val();
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
                var financeCommentsData = $('.js-costLiquidation-comments-textarea').data('attr');
                var fincancePassData = $('#costLiquidationPassBtn').data('attr');
                var param = {
                    'code': respData.code,
                    'dealerId': dealerId,
                    'subOrderValueList': [{
                        'attrId': financeCommentsData.id,
                        'attrPath': financeCommentsData.attrPath,
                        'textInput': $('.js-costLiquidation-comments-textarea').val()
                    }, {
                        'attrId': fincancePassData.id,
                        'attrPath': fincancePassData.attrPath,
                        'textInput': 'N'
                    }]
                };
                common.ajax({
                    url: common.root + '/cancelLease/rentalCostLiquidationReturn.do',
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    encode: false,
                    data: JSON.stringify(param),
                    loadfun: function(isloadsucc, data) {
                        common.load.hide();
                        jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                    }
                });
            }
        }
    });      
      
    },
    
    rentalAccountReturn: function() {
        var financeComments = $('.js-rental-account-comments-textarea').val();
        if (financeComments == null || financeComments == '') {
            common.alert("打回时请输入打回原因");
            return;
        }
        common.alert({
            msg: '确定打回至费用清算吗？',
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
                            'attrId': financeCommentsData.id,
                            'attrPath': financeCommentsData.attrPath,
                            'textInput': $('.js-rental-account-comments-textarea').val()
                        }, {
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });
    },
    
    /**
     * 费用清算提交
     */
    rentalLiquidationPass: function() {
    	
    	var totalMoney = $('.js-costLiquidation-total-money-input').val();
    	if (totalMoney == '') {
        common.alert("总计金额为空！");
        return;
    	}
    	
    	var flag = "确定费用清算完成吗？";
    	
    	var canKaoStr = "";
    	// 判断应收水费小于参考值
    	var waterYingShou = $("#waterYingShou").val();
    	var waterCanKao = $("#waterCanKao").val();
    	if (parseFloat(waterYingShou) < parseFloat(waterCanKao)) {
    		canKaoStr += "本次水费结算费用【"+waterYingShou+"】小于系统参考值【"+waterCanKao+"】,";
    	}
    	
    	// 判断应收电费小于参考值
    	var eMeterYingShou = $("#eMeterYingShou").val();
    	var eMeterCanKao = $("#eMeterCanKao").val();
    	if (parseFloat(eMeterYingShou) < parseFloat(eMeterCanKao)) {
    		canKaoStr += "本次电费结算费用【"+eMeterYingShou+"】小于系统参考值【"+eMeterCanKao+"】,";
    	}
    	
    	// 判断应收燃气费小于参考值
    	var cardgasYingShou = $("#cardgasYingShou").val();
    	var cardgasCanKao = $("#cardgasCanKao").val();
    	if (parseFloat(cardgasYingShou) < parseFloat(cardgasCanKao)) {
    		canKaoStr += "本次燃气费结算费用【"+cardgasYingShou+"】小于系统参考值【"+cardgasCanKao+"】,";
    	}
    	if (canKaoStr != "") {
    		canKaoStr += "请确认没有缺漏并且结算结果正确？";
    		flag = canKaoStr;
    	}
    	
      common.alert({
        msg: flag,
        confirm: true,
        fun: function(action) {
            if (action) {
                common.load.load();
                var financeTypeData = $('.js-costLiquidation-type-radio').data('attr');
                var totalMoneyData = $('.js-costLiquidation-total-money-input').data('attr');
                var financeCommentsData = $('.js-costLiquidation-comments-textarea').data('attr');
                var fincancePassData = $('#costLiquidationPassBtn').data('attr');

                if ($('input:radio:checked').attr('code') == 'out') {
                    totalMoney = -totalMoney;
                }

                var param = {
                    'code': respData.code,
                    'dealerId': dealerId,
                    'subOrderValueList': [{
                        'attrId': financeTypeData.id,
                        'attrPath': financeTypeData.attrPath,
                        'textInput': $('input:radio:checked').val()
                    }, {
                        'attrId': totalMoneyData.id,
                        'attrPath': totalMoneyData.attrPath,
                        'textInput': totalMoney
                    }, {
                        'attrId': financeCommentsData.id,
                        'attrPath': financeCommentsData.attrPath,
                        'textInput': $('.js-costLiquidation-comments-textarea').val()
                    }, {
                        'attrId': fincancePassData.id,
                        'attrPath': fincancePassData.attrPath,
                        'textInput': 'Y'
                    }]
                };

                common.ajax({
                    url: common.root + '/cancelLease/rentalCostLiquidation.do',
                    dataType: 'json',
                    contentType: 'application/json; charset=utf-8',
                    encode: false,
                    data: JSON.stringify(param),
                    loadfun: function(isloadsucc, data) {
                        common.load.hide();
                        jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                    }
                });
            }
        }
      });   	
    },

    rentalAccountPass: function() {
	      var financeComments = $('.js-rental-account-comments-textarea').val();
	      if (financeComments == null || financeComments == '') {
	          common.alert("请填写备注信息！");
	          return;
	      }        
        common.alert({
            msg: '确定租务核算完成吗？',
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
                            'attrId': financeCommentsData.id,
                            'attrPath': financeCommentsData.attrPath,
                            'textInput': $('.js-rental-account-comments-textarea').val()
                        }, {
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
                            'attrId': marketingExecutiveAuditCommentsData.id,
                            'attrPath': marketingExecutiveAuditCommentsData.attrPath,
                            'textInput': $('.js-marketing-executive-audit-comments-textarea').val()
                        }, {
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });
    },
    
    /**
     * 租务核算打回
     */
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
                            'attrId': financeAuditCommentsData.id,
                            'attrPath': financeAuditCommentsData.attrPath,
                            'textInput': $('.js-finance-audit-comments-textarea').val()
                        }, {
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
                            jsNewCancelLeaseOrderDetail.closeOpenedWin(isloadsucc, data);
                        }
                    });
                }
            }
        });

    },

    sigleHouseInfo: function(house_rank_id, flag, houseId, rankType, title, agreementId) {
    	
    	jsNewCancelLeaseOrderDetail.rankHouseId_ = house_rank_id;
    	jsNewCancelLeaseOrderDetail.title_ = title;
    	jsNewCancelLeaseOrderDetail.rankType_ = rankType;
    	jsNewCancelLeaseOrderDetail.houseId_ = houseId;
    	jsNewCancelLeaseOrderDetail.agreementId_ = agreementId;
    	jsNewCancelLeaseOrderDetail.flag_ = flag;
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
	jsNewCancelLeaseOrderDetail.init();
});