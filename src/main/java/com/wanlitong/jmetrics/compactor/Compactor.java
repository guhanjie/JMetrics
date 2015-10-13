package com.wanlitong.jmetrics.compactor;

import org.springframework.stereotype.Component;

@Component
public class Compactor {
    
    public void compact() {
        System.out.println(Thread.currentThread().getName()+" "+"compacting: 每5秒执行一次");  
    }
}
