/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			Main.java 
 * Create Date:		2016年8月1日 上午10:43:10 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import java.util.Random;

import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.lib.DefaultMetricsSystem;
import com.pinganfu.owl.metrics.lib.MetricsSourceBuilder;
import com.pinganfu.owl.metrics.lib.MutableCounterLong;
import com.pinganfu.owl.metrics.lib.MutableGaugeLong;

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
		
		// 1. Initialize the metric system at somewhere in your app's startup code
		DefaultMetricsSystem.initialize("test");
		MetricsSystem ms = DefaultMetricsSystem.instance();
		
		// 2. Create the metrics source object built by MetricsSourceBuilder
		MetricsSourceBuilder msb = new MetricsSourceBuilder(ms, "test", "系统监控目标1");
		final MutableCounterLong mcl = msb.newCounter("访问计数", "d", 0L);
		final MutableGaugeLong mgl = msb.newGauge("缓存命中数", "d", 0L);
		
		// 2. Create the metric source object built by custom
		final MetricsSourceAnnotation source = new MetricsSourceAnnotation().registerWith(ms);
		final MetricsSourceCustom source1 = new MetricsSourceCustom("target1", "monitor target1");
		final MetricsSourceCustom2 source2 = new MetricsSourceCustom2();
		
		// 3. Register source into metrics system
		ms.register("自定义监控目标1", "目标1范围内的指标", source1);
		ms.register("自定义监控目标2", "目标2范围内的指标", source2);
		
		// 4. Operate your source metrics
		Thread t = new Thread() {
			@Override
			public void run() {
				//add some metrics
				source1.newCounter("request", "request count", 0L);
				source1.newGauge("variable", "some variable", 0L);
				while(true) {
					Random random = new Random();
					int value = random.nextInt(10000);
					// Update the metrics
					MutableCounterLong counterMetric = (MutableCounterLong)source1.get("request");
					MutableGaugeLong gaugeMetric = (MutableGaugeLong)source1.get("variable");
					mcl.incr();
					mgl.set(value);
					counterMetric.incr();
					gaugeMetric.set(value);
					try {
						Thread.sleep(500);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
		Thread.sleep(1000000000L);
		
		// 5. Wait to sink metrics data in MetricsSink implementations...
	}
}
