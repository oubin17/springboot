package com.ob.common.appconfig;

import com.ob.common.interceptor.WebInterceptor;
import com.ob.common.page.PageRequestHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/22 16:59
 * @Description:
 */
@Configuration
public class ApplicationWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    private WebInterceptor webInterceptor;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //配置Pageable参数解析
        argumentResolvers.add(new PageRequestHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

    /**
     * 添加自定义拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(webInterceptor);
        super.addInterceptors(registry);
    }
}
