/*
 * COPYRIGHT © 2012-2015 厦门优策信息科技有限公司
 */
package com.cadre.model.utils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {
	
	public static String toString(Object obj) {
		if(obj == null) return "";
		return obj.toString();
	}
	
	/**
	 * 得到字符串中所有子中的index
	 * 
	 * @param str 原字符串，如果为空，返回 null
	 * @param sub 要匹配的子串，如果为空，返回 null
	 * @return 返回int[]，如果没有匹配项，返回null
	 */
	public static int[] indexOfArr(String str,String sub){
		if(isBlank(str) || isBlank(sub)) {
			return null;
		}
		
		List<Integer> indexList = new ArrayList<Integer>();
		int fromIndex = 0;
		int tempIndex = 0;
		while(tempIndex > -1){
			tempIndex = str.indexOf(sub, fromIndex);
			if(tempIndex > -1) {
				indexList.add(tempIndex);
				fromIndex = tempIndex + sub.length();
			}
		}
		
		
		return contentToArr(indexList);
	}
	
	/**
	 * 将Integer list转换成int[]
	 * @param list
	 * @return
	 */
	public static int[] contentToArr(List<Integer> list) {
		if(list == null || list.size() == 0) {
			return null;
		}
		int[] indexArr = new int[list.size()];
		for(int i = 0; i< list.size(); i++) {
			indexArr[i] = list.get(i).intValue();
		}
		return indexArr;
	}
	
	/**
	 * 将数组转换成字符串,以','做连接符
	 * @param arr
	 * @param contactChar
	 * @return
	 */
	public static String shiftToString(String[] arr) {
		return shiftToString(arr,',');
	}
	/**
	 * 将数组转换成字符串
	 * @param arr
	 * @param contactChar
	 * @return
	 */
	public static String shiftToString(final String[] arr, final char contactChar) {
		return shiftToString(arr,contactChar,true);
	}
	
	public static String shiftToString(final String[] arr,final char contactChar,boolean keepEmptyStr){
		if(arr == null || arr.length == 0) {
			return "";
		}
		
		StringBuffer sb = new StringBuffer();
		for(String str : arr) {
			if(!keepEmptyStr && isEmpty(str)) continue;
			sb.append(str);
			sb.append(contactChar);
		}
		
		sb.delete(sb.length()-1, sb.length());
		return sb.toString();
	}
	
	
	/**
	 * 生成32位的UUID字符串(不包含-分隔符)
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String uuidGen() {
		String orig = UUID.randomUUID().toString();
		return orig.replaceAll("-", "");
	}

	/**
	 * 判断字符串src是否等于compareTo中的任意一个
	 * 
	 * @param src
	 * @param compareTo
	 * @return true若src与compareTo中任意一个相同，否则false
	 */
	public static boolean equalsAny(String src, String[] compareTo) {
		if (src == null) {
			return false;
		}

		for (String cp : compareTo) {
			if (StringUtils.equals(src, cp)) {
				return true;
			}
		}

		return false;
	}
	
	public static String trimAllWhitespace(String str) {
		if (!isEmpty(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		int index = 0;
		while (sb.length() > index) {
			if (Character.isWhitespace(sb.charAt(index))) {
				sb.deleteCharAt(index);
			}
			else {
				index++;
			}
		}
		return sb.toString();
	}
	public static String decodeUTF8(String str) throws Exception{
		if (null == str)
			return null;
		return java.net.URLDecoder.decode(str,"UTF-8");
	}

}
