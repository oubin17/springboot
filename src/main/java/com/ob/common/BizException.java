package com.ob.common;

/**
 * @author: oubin
 * @date: 2019/3/27 15:57
 * @Description:
 */
public class BizException extends RuntimeException {

    public BizException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }

    public BizException(ErrorCode errorCode, String message) {
        super(message);
    }

    public BizException(ErrorCode errorCode, String message, Throwable throwable) {
        super(message, throwable);
    }
}
