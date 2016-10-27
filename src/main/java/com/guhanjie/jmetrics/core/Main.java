/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			Main.java 
 * Create Date:		2016年8月1日 上午10:43:10 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.jmetrics.core;

import java.util.Random;

import org.apache.hadoop.metrics2.MetricsSystem;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
import org.apache.hadoop.metrics2.source.JvmMetrics;

/**
 * Class Name:		Main<br/>
 * Description:		[description]
 * @time				2016年8月1日 上午10:43:10
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class Main {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年8月1日 上午10:43:10
	 * @param args 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
//		// Somewhere in your app's startup code, initialize the metric system.
//		DefaultMetricsSystem.initialize("JobTracker");
//
//		// Create the metrics source object
//		MyMetrics myMetrics = new MyMetrics().registerWith(DefaultMetricsSystem.instance());
//
//		// Update the metrics
//		myMetrics.g1.set(67);
//		myMetrics.c1.incr();
		
		DefaultMetricsSystem.initialize("test");
		MetricsSystem ms = DefaultMetricsSystem.instance();
		final MyAnnotationSource source = new MyAnnotationSource().registerWith(ms);
		final MyCustomSource source2 = new MyCustomSource().registerWith(ms);
//		final MyCustomSink sink = new MyCustomSink().registerWith(ms);
//		JvmMetrics.initSingleton("jvm", "123456");
		Thread t = new Thread() {
			@Override
			public void run() {
				while(true) {
					Random random = new Random();
					int value = random.nextInt(10000);
					source.g1.set(value);
					source.c1.incr();
					try {
						Thread.sleep(500);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
//		ms.register("source1", "source1 description", new MyComponentSource());
//		ms.register("sink2", "sink2 description", new MyComponentSink());
		Thread.sleep(1000000000L);
	}
}
