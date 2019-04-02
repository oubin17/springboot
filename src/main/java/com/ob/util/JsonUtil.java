package com.ob.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationConfig;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.TimeZone;

/**
 * @author: oubin
 * @date: 2019/4/2 15:57
 * @Description:
 */
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
     * @param jsonString
     * @param objectType
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T parse(String jsonString, Class<T> objectType) throws IOException {
        if (null == jsonString) {
            return null;
        }
        Assert.notNull(objectType, "objectType not be null");
        return mapper.readValue(jsonString, objectType);
    }

    /**
     * 对象转json
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static void setMapper(ObjectMapper mapper) {
        JsonUtil.mapper = mapper;
    }
}
