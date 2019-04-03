package com.ob.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.TimeZone;

/**
 * @author: oubin
 * @date: 2019/4/2 15:57
 * @Description:
 */
@Slf4j
public class JsonUtil {

    private JsonUtil() {

    }

    private static ObjectMapper mapper;
    static {
        mapper = new ObjectMapper();
        //驼峰转下划线
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        //设置时区
        mapper.setTimeZone(TimeZone.getDefault());
        // 如果输入不存在的字段时不会报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //不存在的字段不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * json转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, Class<T> clazz) {
        if (null == json) {
            return null;
        }
        Assert.notNull(clazz, "clazz not be null");
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("json to bean error: " + json, e);
            return null;
        }
    }

    /**
     * json转对象
     *
     * @param json
     * @param tTypeReference
     * @param <T>
     * @return
     */
    public static <T> T jsonToBean(String json, TypeReference<T> tTypeReference) {
        if (null == json) {
            return null;
        }
        try {
            return mapper.readValue(json, tTypeReference);
        } catch (IOException e) {
            log.error("json to bean error: " + json, e);
            return null;
        }
    }

    /**
     * 对象转json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("object to json error: " + object, e);
            return null;
        }
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }
}
