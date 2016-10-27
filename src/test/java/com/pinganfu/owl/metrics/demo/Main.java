/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			Main.java 
 * Create Date:		2016年8月1日 上午10:43:10 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.demo;

import java.lang.management.ManagementFactory;
import java.util.Random;

import com.pinganfu.owl.metrics.MetricsSystem;
import com.pinganfu.owl.metrics.lib.DefaultMetricsSystem;
import com.pinganfu.owl.metrics.lib.MetricsSourceBuilder;
import com.pinganfu.owl.metrics.lib.MutableCounterLong;
import com.pinganfu.owl.metrics.lib.MutableGaugeLong;
import com.pinganfu.owl.metrics.lib.MutableQuantiles;
import com.pinganfu.owl.metrics.lib.MutableRate;
import com.pinganfu.owl.metrics.source.JvmMetrics;

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
//		final MutableCounterLong mcl = msb.newCounter("累积计数", "常用于访问或调用计数", 0L);
//		final MutableGaugeLong mgl = msb.newGauge("变化量", "常用于现场时刻指标", 0L);
//		final MutableRate mr = msb.newRate("put", "常用于流量和耗时统计（Ops&AvgTime）");
		final MutableQuantiles mq = msb.newQuantiles("duration", "常用于指标分布统计", "Ops", "Latency", 10);
		msb.build();
		
		// 2. Create the metric source object built by custom
//		final MetricsSourceAnnotation source = new MetricsSourceAnnotation().registerWith(ms);
//		final MetricsSourceCustom source1 = new MetricsSourceCustom("target1", "monitor target1");
//		final MetricsSourceCustom2 source2 = new MetricsSourceCustom2();
		
		// 3. Register source into metrics system
//		ms.register("自定义监控目标1", "目标1范围内的指标", source1);
//		ms.register("自定义监控目标2", "目标2范围内的指标", source2);
		
		// 4. Register jvm source into metrics system
		String name = ManagementFactory.getRuntimeMXBean().getName();
		JvmMetrics.create(name.split("@")[0], name, ms);
		
		// 5. Operate your source metrics in somewhere of your code, for example as blow:
		Thread t = new Thread() {
			@Override
			public void run() {
				//add some metrics dynamically
//				source1.newCounter("request", "request count", 0L);
//				source1.newGauge("variable", "some variable", 0L);
				while(true) {
					Random random = new Random();
					int value = random.nextInt(100);
					// Update the metrics
//					MutableCounterLong counterMetric = (MutableCounterLong)source1.get("request");
//					counterMetric.incr();
//					MutableGaugeLong gaugeMetric = (MutableGaugeLong)source1.get("variable");
//					gaugeMetric.set(value);
//					mcl.incr();
//					mgl.set(value);
//					mr.add(value);
					System.out.print(value+"-");
					mq.add(value);
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
		
		// 6. Wait to sink metrics data in MetricsSink implementations...
	}
}
