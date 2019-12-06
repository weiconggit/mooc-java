package org.carryon.util;
/**
 * @description 
 * @author carryon
 * @date 2019年12月2日
 * @version 1.0
 */
public abstract class StringUtil {

	/**
	 * 判断字符是否为空
	 * 
	 * @param cs 待判断字符串
	 * @return
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
}
