package com.ob.common;

import com.sun.xml.internal.bind.v2.TODO;
import org.springframework.http.ResponseEntity;

/**
 * @author: oubin
 * @date: 2019/3/27 15:57
 * @Description:
 */
public class BizException extends RuntimeException {

    /**
     * TODO 待优化，自定义异常返回信息
     */
    private ResponseEntity<ExceptionMessage> responseEntity;

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
