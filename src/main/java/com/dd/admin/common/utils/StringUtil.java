package com.dd.admin.common.utils;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtil {

	public static boolean isEmpty(String str){
		if(null==str||str.trim().equals("")||"null".equals(str)){
			return true;
		}
		return false;
	}

	public static boolean isNotEmpty(String str){
		if(null==str||str.trim().equals("")||"null".equals(str)){
			return false;
		}
		return true;
	}

	public static String getNumberForPK(){
		String id="";
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String temp = sf.format(new Date());
		id=temp;
		return id;
	}

	public static String getDateStringNow(){
		String id="";
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		String temp = sf.format(new Date());
		id=temp;
		return id;
	}

	public static String createCode(Integer len){
		String zi = "0123456789";
		String ret = "";
		Random random = new Random();
		for (int i =0;i<len;i++){
			int a=random.nextInt(zi.length());
			char s = zi.charAt(a);
			ret+=s;
		}
		return ret;
	}


	public static String getQuotesStr(String v){
		if(isEmpty(v)){
			return v;
		}
		v = "'" + v + "'";
		return v;
	}

	public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
		// ^ 匹配输入字符串开始的位置
		// \d 匹配一个或多个数字，其中 \ 要转义，所以是 \\d
		// $ 匹配输入字符串结尾的位置
		String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
				"|(18[0-9])|(19[8,9]))\\d{8}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

}

