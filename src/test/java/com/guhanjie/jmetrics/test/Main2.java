/** 
 * Project Name:		jmetric 
 * Package Name:	jmetric 
 * File Name:			Main2.java 
 * Create Date:		2016年8月9日 下午3:27:51 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.guhanjie.jmetrics.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Class Name:		Main2<br/>
 * Description:		[description]
 * @time				2016年8月9日 下午3:27:51
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class Main2 {
	/**
	 * Method Name:	main<br/>
	 * Description:			[description]
	 * @author				GUHANJIE
	 * @time					2016年8月9日 下午3:27:51
	 * @param args 
	 * @throws ConfigurationException 
	 */
	public static void main(String[] args) throws ConfigurationException {
		PropertiesConfiguration c = new PropertiesConfiguration("hadoop-metrics2-test.properties");
		Configuration subC = c.subset("test");
		System.out.println(toString(c));
		System.out.println("==========================");
		System.out.println(toString(subC));
	}
	
	static String toString(Configuration c) {
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    PrintStream ps = new PrintStream(buffer);
	    PropertiesConfiguration tmp = new PropertiesConfiguration();
	    tmp.copy(c);
	    try { tmp.save(ps); }
	    catch (Exception e) {
	    }
	    return buffer.toString();
	  }
}
