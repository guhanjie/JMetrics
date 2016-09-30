/** 
 * Project Name:		JMetrics 
 * Package Name:	 
 * File Name:			TestStringBuilder.java 
 * Create Date:		2016年9月21日 下午3:14:47 
 * Copyright (c) 2008-2016, 平安集团-平安万里通 All Rights Reserved.
 */
/**
 * Class Name:		TestStringBuilder<br/>
 * Description:		[description]
 * @time				2016年9月21日 下午3:14:47
 * @author			GUHANJIE
 * @version			1.0.0 
 * @since 			JDK 1.7 
 */
public class TestStringBuilder {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("this is test");
		//sb.append("}");
		
		System.out.println(get(sb));
	}
	
	public static String get(StringBuilder builder) {
		try {
			System.out.println("try..."+builder.length()+" "+builder.toString());
	        return builder.append('}').toString();
	      } finally {
	        // Slice off the closing brace in case there are additional calls to
	        // #add or #addValue.
	    	System.out.println("finally before..."+builder.length()+" "+builder.toString());
	        builder.setLength(builder.length() - 1);
	        System.out.println("finally after..."+builder.length()+" "+builder.toString());
	      }
	}
}
