package com.ob.work.trade.controller;

import com.ob.common.constant.Constants;
import com.ob.work.trade.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: oubin
 * @Date: 2020/9/11 09:22
 * @Description:
 */
@RestController
@RequestMapping(value = Constants.VERSION_01 + "/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping
    public void setCacheValue(@RequestParam(value = "key") String key,
                              @RequestParam(value = "val") String val) {
        cacheService.setGoodValue(key, Integer.valueOf(val));
    }

    @PutMapping
    public void descValue(@RequestParam(value = "key") String key) {
        cacheService.descValue(key);
    }


    @PutMapping(value = "/list")
    public void listValue(@RequestParam(value = "key") String key) {
        cacheService.listValue(key);
    }

}
