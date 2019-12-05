package com.ob.common.util;

import com.ob.common.exception.BizException;
import com.ob.common.exception.ErrorCode;

/**
 * @Author: oubin
 * @Date: 2019/12/5 08:59
 * @Description:
 */
public class TimeUtil {

    /**
     * 两个日期的时间差：单位（秒）
     *
     * @param startTimeStamp
     * @param endTimeStamp
     * @return
     */
    public static Long intervalSecond(Long startTimeStamp, Long endTimeStamp) {
        if (startTimeStamp == null || endTimeStamp == null) {
            throw new BizException(ErrorCode.TIME_STAMP_NOT_NULL);
        }
        return (endTimeStamp - startTimeStamp) / 1000;
    }
}
