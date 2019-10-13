package org.carryon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @description Mooc java 第2周互评作业
 * @author carryon
 * @time 2019年9月22日
 * @version 1.0
 */
public class Week2 {

	/**
	 * 编码格式：
	 * UTF-8
	 * 
	 * 环境说明：
	 * jdk1.7、jdk1.8
	 * 
	 * 算法说明： 
	 * 3^3 + 4^3 + 5^3 = 6^3 可认为是 连续正整数的起点：3，连续正整数的个数：3；
	 * 6^3 + 7^3 + ... + 69^3 = 180^3 可认为是 连续正整数的起点：6，连续正整数的个数：64；
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入连续正整数起点范围的最大值：");
		int startMax = scanner.nextInt();
		System.out.println("请输入连续正整数个数范围的最大值：");
		int limitMax = scanner.nextInt();
		
		List<String> list = getEquation(startMax, limitMax);
		if (null == list || 0 < list.size()) {
			System.out.println("在指定范围内找到以下等式：");
			for (String string : list) {
				System.out.println(string);
			}
		}
	}

	/**
	 * 获取等式
	 * 
	 * @param startMax 连续正整数起点范围的最大值
	 * @param limitMax 连续正整数个数范围的最大值
	 * @return 满足条件的等式
	 */
	private static List<String> getEquation(int startMax, int limitMax) {
		List<String> list = new ArrayList<>();
		// i 用于控制连续正整数的起点
		for (int i = 1; i < startMax; i++) {
			// j 用于控制连续正整数的个数
			for (int j = 1; j < limitMax; j++) {
				double sum = 0;
				// k 用于控制连续正数的相加
				for (int k = 0; k <= j; k++) {
					sum = sum + Math.pow((double) (i + k), 3);
				}
				double cbrt = Math.cbrt(sum);
				if (isIntegerForDouble(cbrt) && j != 1) {
					list.add("连续正整数的起点为：" + i + "，连续正整数的个数为：" + (j + 1) + "，等式右边立方前的值为：" + cbrt + ";");
				}
			}
		}
		return list;
	}

	/**
	 * 判断double是否是整数
	 * 
	 * @param obj
	 * @return 是否（是整数）
	 */
	public static boolean isIntegerForDouble(double obj) {
		double eps = 1e-10; // 精度范围
		return obj - Math.floor(obj) < eps;
	}

}
