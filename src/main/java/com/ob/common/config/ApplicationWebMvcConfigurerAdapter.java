package com.ob.common.config;

import com.ob.common.page.PageRequestHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * @Author: oubin
 * @Date: 2019/10/22 16:59
 * @Description:
 */
@Configuration
public class ApplicationWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //配置Pageable参数解析
        argumentResolvers.add(new PageRequestHandlerMethodArgumentResolver());
        super.addArgumentResolvers(argumentResolvers);
    }

}
