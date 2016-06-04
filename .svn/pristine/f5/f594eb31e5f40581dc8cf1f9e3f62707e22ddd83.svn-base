/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.model.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Type: com.idecisions.uitls.RegUtils.java
 * @ClassName: RegUtils
 * @Description: <br/>
 * 
 */
public class RegUtils {

	/** 
	 * 用正则表达式验证字符串
	 * @param reg 正则表达式
	 * @param matchStr 被验证的字符串
	 * @return
	 */
	protected static boolean check(String reg,String matchStr){
		boolean flag = false;
		try {
			Pattern regex = Pattern.compile(reg);
			Matcher matcher = regex.matcher(matchStr);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 验证是否是邮箱格式的字符串
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String emailReg = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)$";
		return check(emailReg, email);
	}

	
	/**
	 * 验证是否是手机号格式的字符串
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {
		String mobileReg = "^((\\+86)|(86))?1[3|4|5|8][0-9]\\d{8}$";
		return check(mobileReg,mobile);
	}

}
