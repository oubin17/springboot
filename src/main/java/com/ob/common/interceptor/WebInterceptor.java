package com.ob.common.interceptor;

import com.google.common.collect.Maps;
import com.ob.common.context.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @Author: oubin
 * @Date: 2019/12/18 09:09
 * @Description:
 */
@Slf4j
@Component
public class WebInterceptor implements AsyncHandlerInterceptor {

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HashMap local = Maps.newHashMap();
        local.put(SessionContext.HTTP_SERVLET_REQUEST, httpServletRequest);
        local.put(SessionContext.USER_ID, getUserId(httpServletRequest));
        local.put(SessionContext.REAL_IP, getRealIp(httpServletRequest));
        SessionContext.setLocal(local);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

    }

    /**
     * 获取认证
     *
     * @param request
     * @return
     */
    private static String getUserId(HttpServletRequest request) {
        String authorization;
        if (!StringUtils.isEmpty(request.getHeader(SessionContext.AUTHORIZATION))) {
            authorization = request.getHeader(SessionContext.AUTHORIZATION);
            if (!StringUtils.isEmpty(authorization)) {
                String[] split = authorization.split("=");
                if (split.length >= 2) {
                    return split[1];
                }
            }
        }
        return null;
    }


    /**
     * 获取用户真实ip地址，不使用request.getRemoteAddr()的是可能用户使用了代理软件方式避免了真实ip地址
     * 如果通过了多级反向代理的话，x-forwarded-for的值不止一个，而是一串ip值
     * 需要获取x-forwarded-for中第一个非unknown的有效ip字符串
     *
     * @return
     */
    public static String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
