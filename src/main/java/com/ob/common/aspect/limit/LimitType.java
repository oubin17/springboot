package com.ob.common.aspect.limit;

/**
 * @Author: oubin
 * @Date: 2019/12/18 08:26
 * @Description: 限流类型
 */
public enum LimitType {

    /**
     * redis key
     */
    KEY,

    /**
     * 物理机ip
     */
    IP;

}
