/** 
 * Project Name:		owl.metrics 
 * Package Name:	com.pinganfu.owl.metrics.annotation 
 * File Name:			TimeConsumed.java 
 * Create Date:		2016年11月2日 下午3:46:00 
 * Copyright (c) 2008-2016, 平安集团-平安付 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AOP annotation for time consumed of method
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeConsumed {
	String value() default "";
}
