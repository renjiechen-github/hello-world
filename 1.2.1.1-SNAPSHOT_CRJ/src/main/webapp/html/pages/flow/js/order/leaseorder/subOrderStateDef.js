var subOrderStateDef = {
	/**
     * 订单录入
     */
    ORDER_INPUT: "A",
    
    /**
     * 指派订单
     */
    ASSIGN_ORDER: "B",
    
    /**
     * 员工接单
     */
    TAKE_ORDER: "C",
    
    /**
     * 员工处理
     */
    DO_IN_ORDER: "D",
    
    /**
     * 费用清算
     */
    COST_LIQUIDATION: "X",
    
    /**
     * 费用清算不通过
     */
    NOT_COST_LIQUIDATION: "Y",
    
    /**
     * 客服回访
     */
    STAFF_REVIEW: "E",
    
    /**
     * 客服回访待追踪
     */
    WAIT_2_TACE: "F",
    
    /**
     * 客服回访重新指派
     */
    REASSIGNING_IN_STAFF_REVIEW: "G",
    
    /**
     * 评价
     */
    COMMENT: "H",
    
    /**
     * 订单关闭
     */
    CLOSED: "I",
    
    /**
     * 请求支付中
     */
    APPLY_2_PAY: "J",
    
    /**
     * 客户支付
     */
    PAY: "K",
    
    /**
     * 市场部高管审批
     */
    MARKETING_EXECUTIVE_AUDITING: "L",

    /**
     * 租务审核
     */
    RENTAL_ACCOUNTING: "M",

    /**
     * 财务审核
     */
    FINANCE_AUDITING: "N",

    /**
     * 财务数据生成
     */
    FINANCE_DATA_CREATING: "O",

    /**
     * 重新指派订单
     */
    REASSIGNING: "P",

    /**
     * 租务审核未通过
     */
    NOT_PASS_IN_RENTAL_ACCOUNTING: "Q",

    /**
     * 市场部高管审批未通过
     */
    NOT_PASS_IN_MARKETING_AUDITING: "R",
    
    /**
     * 财务审核未通过
     */
    NOT_PASS_IN_FINANCE_AUDITING: "S",
        
    /**
     * 房源释放
     */
    RELEASE_HOUSE_RANK: "U"
}