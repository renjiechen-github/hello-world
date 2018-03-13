package com.room1000.core.exception;

/**
 * 
 * Description: 业务异常
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class BaseAppException extends RuntimeException {

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 异常编码，冗余，暂时不用
     */
    private String errorCode;
    
    /**
     * 异常信息
     */
    private String errorMessage;
    
    /**
     * 异常类型，冗余
     */
    private String errorType;

    /**
     * 构造函数
     * 
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     * @param errorType errorType
     */
    public BaseAppException(String errorCode, String errorMessage, String errorType) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }
    
    /**
     * 构造函数
     * 
     * @param errorCode errorCode
     * @param errorMessage errorMessage
     */
    public BaseAppException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, null);
    }
    
    /**
     * 构造函数
     * 
     * @param errorMessage errorMessage
     */
    public BaseAppException(String errorMessage) {
        this(null, errorMessage, null);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }
    
    
}
