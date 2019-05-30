package com.ob.test;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author: oubin
 * @Date: 2019/5/9 14:39
 * @Description:
 */
public class TokenDemo {

    /**
     * 生成token
     *
     * @param tenantId 租户id
     * @param method    Http method，<p>UpperCase</p>
     * @param curTime   当前时间戳，<p>精确到毫秒</p>
     * @param url       url
     * @param accessKey AK
     * @return token
     */
    public static String generate(String tenantId, String method, String curTime, String url, String accessKey, String secretKey) {
        StringBuilder token = new StringBuilder();
        token.append(curTime).append(";")
                .append(accessKey).append(";");
        String signature = generate(tenantId, method, curTime, url, secretKey);
        token.append(signature);
        return token.toString();
    }

    private static String generate(String tenantId,String method, String curTime, String url, String secretKey) {
        String result = null;
        String tmp = curTime + tenantId + url + method;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(secretKey.getBytes(), "HmacSHA1"));
            byte[] bytes = mac.doFinal(tmp.getBytes("UTF-8"));
            result = Base64.encodeBase64URLSafeString(bytes);
        } catch (Exception e) {
//            log.error("token 计算失败", e);
        }
        return result;
    }

    public static void main(String[] args) {
        String tenantId = String.valueOf(1);
        String method = "POST";
        // 当前时间戳
        String curTime = String.valueOf(System.currentTimeMillis());
        // 相对路径
//        String url = "/v1.0/1/resources/1234564646/status";
        String url = "https://ndr-gateway.sdp.101.com/v1.0/1/search/query";
        String accessKey = "AK";
        String secretKey = "secret key";
        System.out.println(generate(tenantId, method, curTime, url, accessKey, secretKey));
    }
}
