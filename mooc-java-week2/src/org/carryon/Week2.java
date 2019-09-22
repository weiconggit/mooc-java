package org.carryon;

import java.util.ArrayList;
import java.util.List;

/**
 * @description Mooc java 第2周互评作业
 * @author weicong
 * @time 2019年9月22日
 * @version 1.0
 */
public class Week2 {

	public static void main(String[] args) {
//		double d = Math.pow(11, 3) + Math.pow(12, 3) + Math.pow(13, 3) + Math.pow(14, 3);
//		System.out.println(d);
//		System.out.println(Math.cbrt(d));
//		System.err.println(isIntegerForDouble(Math.cbrt(d)));
		
		
		System.out.println(jugde());
	}

	private static List<String> jugde() {
		List<String> list = new ArrayList<>();
		for (int i = 1; i < 80; i++) {// i 用于控制连续正数的起点
			for (int j = 1; j < 70; j++) {// j 用于控制连续正数的个数
				double a = 0;
				for (int k = 0; k <= j; k++) {// k 用于连续正数的相加
//					System.out.println("k=" + k);
//					System.out.println((double)(i + k));
					a = a + ((double)(i + k))*((double)(i + k))*((double)(i + k));
//					System.out.println("a=" + a);
				}
				// a 计算结果对比
				double temp = Math.cbrt(a);
				if (isIntegerForDouble(temp) && j != 1) {
					list.add("i=" + i + " j=" + (j + 1));
				}
			}
		}
		return list;
	}
	
	/** 
	 * 判断double是否是整数 
	 * @param obj 
	 * @return 
	 */  
	public static boolean isIntegerForDouble(double obj) {  
	    double eps = 1e-10;  // 精度范围  
	    return obj-Math.floor(obj) < eps;  
	}


}
