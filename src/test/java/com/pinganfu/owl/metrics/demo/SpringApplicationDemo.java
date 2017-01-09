/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.demo 
 * File Name:			Application.java 
 * Create Date:		2016年11月10日 下午1:39:16 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pinganfu.owl.metrics.MetricsSystem;

/**
 * Class Name:		Application<br/>
 * Description:		[description]
 * @time				2016年11月10日 下午1:39:16
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class SpringApplicationDemo {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年11月10日 下午1:39:16
	 * @param args 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//Spring context start
		ClassPathXmlApplicationContext cax = new ClassPathXmlApplicationContext("application.xml");
		
		//Reference Metrics System instance from spring context container(but not used in normal)
		//Which used to shutdown metrics system manually, or used to register metrics source(such as: MetricsSourceBuilder)
		MetricsSystem ms = cax.getBean(MetricsSystem.class);
		
		//Reference Metrics Source component from spring context configuration
		final MetricsSourceAnnotation annotatedSource = cax.getBean(MetricsSourceAnnotation.class);
		
		//Reference service componet from spring context container
		final BusinessService service = cax.getBean(BusinessService.class);
		System.out.println("service: "+service.getClass().getName());
		
		//Simulate service doing...
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
					int i = 0;
					while(true) {
						i++;
						if(i>60)
							service.update();
						service.put();
						service.get();
						annotatedSource.g1.set(i);
						annotatedSource.c1.incr();
						Thread.sleep(500);
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		Thread.sleep(10000L);
		
		//Close spring application, it will shutdown metrics system as well, which lifecycle managed by spring context.
		cax.close();
		cax.stop();
	}
}
