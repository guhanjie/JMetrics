/** 
 * Project Name:		owl.metrics 
 * Package Name:	org.apache.hadoop.metrics2.util 
 * File Name:			ReflectionUtils.java 
 * Create Date:		2016年10月9日 下午2:16:56 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */  
package com.pinganfu.owl.metrics.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.classification.InterfaceStability;

/**
 * General reflection utils
 */
@InterfaceAudience.Public
@InterfaceStability.Evolving
public class ReflectionUtils {
   
  /**
   * Gets all the declared fields of a class including fields declared in
   * superclasses.
   */
  public static List<Field> getDeclaredFieldsIncludingInherited(Class<?> clazz) {
    List<Field> fields = new ArrayList<Field>();
    while (clazz != null) {
      for (Field field : clazz.getDeclaredFields()) {
        fields.add(field);
      }
      clazz = clazz.getSuperclass();
    }
    
    return fields;
  }
  
  /**
   * Gets all the declared methods of a class including methods declared in
   * superclasses.
   */
  public static List<Method> getDeclaredMethodsIncludingInherited(Class<?> clazz) {
    List<Method> methods = new ArrayList<Method>();
    while (clazz != null) {
      for (Method method : clazz.getDeclaredMethods()) {
        methods.add(method);
      }
      clazz = clazz.getSuperclass();
    }
    
    return methods;
  }
}
