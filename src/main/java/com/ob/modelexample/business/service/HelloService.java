package com.ob.modelexample.business.service;

import org.springframework.stereotype.Service;

/**
 * @Author: oubin
 * @Date: 2019/4/25 10:48
 * @Description:
 */
@Service
public class HelloService {

    public void proxyText() {
        System.out.println("是否生成代理对象");
    }
}
