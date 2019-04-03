package com.ob.common;

import java.io.Serializable;

/**
 * @author: oubin
 * @date: 2019/4/3 16:36
 * @Description:
 */
public class ExceptionMessage implements Serializable {

    private String code;

    private String message;

    public ExceptionMessage() {

    }

    public ExceptionMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
