package com.ctbc.utils;

import org.apache.commons.lang3.StringUtils;

public class MyUtils {

	public static String subStringBeforeLastNum(String srcStr, String symbol , int num) {
		String resultStr = srcStr;
		for (int i = 0 ; i < num ; i++) {
			resultStr = StringUtils.substringBeforeLast(resultStr, symbol);
		}
		return resultStr;
	}
	
//	public static void main(String[] args) {
//		String ss = "E:\\CTBC_workspace_Oxygen_3a\\apache-tomcat-9.0.7\\endorsed";
////		System.out.println(StringUtils.substringBeforeLast(ss, "\\"));
//		System.out.println(	MyUtils.subStringBeforeLastNum(ss ,"\\" ,2));
//	}
	
}
