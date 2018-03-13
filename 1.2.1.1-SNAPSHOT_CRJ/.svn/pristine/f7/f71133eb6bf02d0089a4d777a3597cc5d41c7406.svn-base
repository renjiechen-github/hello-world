package com.room1000.core.model;

import java.io.Serializable;

/**
 * 
 * Description: 返回信息
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class ResponseModel implements Serializable {
    
    /**
     * 正常返回
     */
    public static final String NORMAL = "1";
    
    /**
     * 业务异常
     */
    public static final String BUSINESS_ERROR = "2";

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 状态，默认为1正常返回
     */
    private String state = NORMAL;

    /**
     * 异常码
     */
    private String errorCode;

    /**
     * 异常信息
     */
    private String errorMsg;
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponseModel [state=");
        builder.append(state);
        builder.append(", errorCode=");
        builder.append(errorCode);
        builder.append(", errorMsg=");
        builder.append(errorMsg);
        builder.append("]");
        return builder.toString();
    }
    
    
}
