package com.ob.common.exception;

import org.springframework.http.HttpStatus;


/**
 * @author: oubin
 * @date: 2019/3/27 15:18
 * @Description:
 */
public enum ErrorCode {

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "无效请求"),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "DATA_NOT_FOUND", "数据不存在");

    private HttpStatus httpStatus;

    private String code;

    private String message;

    ErrorCode(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
