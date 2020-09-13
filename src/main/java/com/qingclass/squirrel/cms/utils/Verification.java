package com.qingclass.squirrel.cms.utils;

import org.apache.commons.lang3.StringUtils;

public class Verification {
	public static boolean isNumber(String str) 
    { 
		if(StringUtils.isBlank(str)){
			return false; 
		}
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后2位的数字的正则表达式
        java.util.regex.Matcher match=pattern.matcher(str); 
        if(match.matches()==false) 
        { 
           return false; 
        } 
        else 
        { 
           return true; 
        } 
    }
	
	public static void main(String[] args){
		System.out.println(Verification.isNumber(null));
	}
}
