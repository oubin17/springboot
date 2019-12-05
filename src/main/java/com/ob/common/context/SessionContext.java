package com.ob.common.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: oubin
 * @Date: 2019/9/23 16:33
 * @Description:
 */
@Slf4j
public class SessionContext {

    private static final String AUTHORIZATION = "AUTHORIZATION";

    public static String currentUserId() {
        String authorization = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            authorization = getUserIdFromRequest(request);
        } catch (Exception e) {

        }

        if (null == authorization) {
            return null;
        }
        try {
            return authorization.split("=")[1];
        } catch (Exception e) {
            log.error("get user error", e);
            return null;
        }
    }

    private static String getUserIdFromRequest(HttpServletRequest request) {
        if (!StringUtils.isEmpty(request.getHeader(AUTHORIZATION))) {
            return request.getHeader(AUTHORIZATION);
        }
        return null;
    }
}
