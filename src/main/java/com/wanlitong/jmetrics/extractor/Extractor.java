package com.wanlitong.jmetrics.extractor;

import org.springframework.stereotype.Component;

@Component
public class Extractor {
    
    public void extract() {
        System.out.println(Thread.currentThread().getName()+" "+"extracting: 每5秒执行一次");  
    }
}
