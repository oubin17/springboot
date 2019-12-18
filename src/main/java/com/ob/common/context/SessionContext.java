package com.ob.common.context;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @Author: oubin
 * @Date: 2019/9/23 16:33
 * @Description:
 */
@Slf4j
public class SessionContext {

    public static final String HTTP_SERVLET_REQUEST = "HTTP_SERVLET_REQUEST";
    private final static ThreadLocal<HashMap> LOCAL = new ThreadLocal<>();

    public static final String AUTHORIZATION = "AUTHORIZATION";
    public static final String USER_ID = "USER_ID";
    public static final String REAL_IP = "REAL_IP";

    /**
     * 当前用户
     *
     * @return
     */
    public static String currentUserId() {
        HashMap hashMap = getLocal();
        return (String) hashMap.get(USER_ID);
    }

    /**
     * 获取当前用户真实ip地址
     * @return
     */
    public static String realIp() {
        HashMap hashMap = getLocal();
        return (String) hashMap.get(REAL_IP);
    }

    public static HashMap getLocal() {
        HashMap hashMap = LOCAL.get();
        if (null == hashMap) {
            hashMap = Maps.newHashMap();
            LOCAL.set(hashMap);
        }
        return hashMap;
    }

    public static void setLocal(HashMap hashMap) {
        LOCAL.set(hashMap);
    }

    public static void put(String key, Object value) {
        getLocal().put(key, value);
    }
}
