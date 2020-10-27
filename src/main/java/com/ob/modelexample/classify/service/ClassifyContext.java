package com.ob.modelexample.classify.service;

import com.google.common.collect.Maps;
import com.ob.modelexample.classify.aggregate.BaseAggregator;
import com.ob.modelexample.classify.aggregate.JpaAggregator;
import com.ob.modelexample.classify.base.BaseClassify;
import com.ob.modelexample.classify.base.ClassifyName;
import com.ob.modelexample.classify.base.Initializing;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

/**
 * @Author: oubin
 * @Date: 2019/10/12 16:31
 * @Description:
 */
@Slf4j
@Service
public class ClassifyContext implements ApplicationContextAware {

    private static Map<String, Class<? extends BaseClassify>> classifies = Maps.newHashMap();

    private static ApplicationContext applicationContext;

    static {
        Set<Class<?>> classSet = new Reflections("com.ob.other.classify").getTypesAnnotatedWith(ClassifyName.class);
        for (Class c : classSet) {
            if (!c.isAssignableFrom(BaseClassify.class)) {
                classifies.put(((ClassifyName) c.getAnnotation(ClassifyName.class)).name(), c);
            } else {
                log.info("获取注解对象失败");
            }
        }
    }

    public static BaseClassify getClassify(String type) throws Exception {
        Class c = classifies.get(type);
        ClassifyName classifyName = (ClassifyName) c.getAnnotation(ClassifyName.class);
        if (classifyName.name().equals(type)) {
            BaseClassify baseClassify = (BaseClassify) c.newInstance();
            if (baseClassify instanceof Initializing) {
                ((Initializing) baseClassify).afterPropertiesSet();
            }
            BaseAggregator baseAggregator = applicationContext.getBean(JpaAggregator.class);
            baseClassify.setBaseAggregator(baseAggregator);
            return baseClassify;
        }
        return null;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ClassifyContext.applicationContext = applicationContext;
    }
}
