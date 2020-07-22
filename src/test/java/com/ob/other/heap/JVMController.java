package com.ob.other.heap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: oubin
 * @Date: 2020/7/20 17:04
 * @Description:
 */
@RestController
public class JVMController {

    @Autowired
    private JVMService jvmService;

    @GetMapping(value = "/heap")
    public void heap() {
        int i = 0;
        while (true) {
            new Heap("a name");
            i ++;
            if (i%5000 == 0) {
                System.out.println(i);
            }
        }
    }

    @GetMapping("/dead_lock")
    public void deadLock() {
        jvmService.deadLock();

    }
}
