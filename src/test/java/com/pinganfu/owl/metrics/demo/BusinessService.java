/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.demo 
 * File Name:			BusinessService.java 
 * Create Date:		2016年11月1日 下午4:08:05 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.pinganfu.owl.metrics.annotation.TimeConsumed;

/**
 * Class Name:		BusinessService<br/>
 * Description:		[应用的业务逻辑Service组件示例]
 * @time				2016年11月1日 下午4:08:05
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
@Component("businessService")
public class BusinessService {

	private static final Log LOG = LogFactory.getLog(BusinessService.class);
	
	public void put() {
		LOG.debug("doing put...");
		LOG.debug("class:"+this.getClass().getName());
	}

	@TimeConsumed
	public void get() {
		LOG.debug("doing get...");
		LOG.debug("class:"+this.getClass().getName());
		try {
			//Simulate the latency of this method
			Thread.sleep(new Random().nextInt(500));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@TimeConsumed
	public void update() {
		LOG.debug("doing update...");
		LOG.debug("class:"+this.getClass().getName());
		try {
			//Simulate the latency of this method
			Thread.sleep(new Random().nextInt(1000));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
