package com.wanlitong.jmetrics.task.analyzer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Analyzer {
    
	@Scheduled(fixedRate = 5000)  
    public void analyze() {
        System.out.println(Thread.currentThread().getName()+" "+"analyzing: 每5秒执行一次");  
    }
}
