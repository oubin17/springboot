package com.ob.common.exception;

import org.springframework.http.HttpStatus;


/**
 * @author: oubin
 * @date: 2019/3/27 15:18
 * @Description:
 */
public enum ErrorCode {

    //seckill
    ON_SALE_TIME_NOT_CORRECT(HttpStatus.BAD_REQUEST, "ON_SALE_TIME_NOT_CORRECT", "on_sale_time数据不正确"),
    EXPIRE_TIME_NOT_CORRECT(HttpStatus.BAD_REQUEST, "EXPIRE_TIME_NOT_CORRECT", "expire_time数据不正确"),
    SECKILL_GOODS_LOCK_ERROR(HttpStatus.BAD_REQUEST, "SECKILL_GOODS_LOCK_ERROR", "获取秒杀商品失败"),


    //common
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "无效请求"),
    DATA_NOT_FOUND(HttpStatus.BAD_REQUEST, "DATA_NOT_FOUND", "数据不存在"),

    //time
    TIME_STAMP_NOT_NULL(HttpStatus.BAD_REQUEST, "TIME_STAMP_NOT_NULL", "时间戳不为空"),

    //interceptor
    REQUEST_TIME_LIMIT(HttpStatus.BAD_REQUEST, "REQUEST_TIME_LIMIT", "访问次数限制"),

  ;

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
