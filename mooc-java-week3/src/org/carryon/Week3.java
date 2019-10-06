package org.carryon;

/**
 * @description Mooc java 第3周互评作业
 * @author carryon
 * @time   2019年10月6日
 * @version 1.0
 */
public class Week3 {
	
	public static void main(String[] args) {
		getPrimeNumber();
	}
	
	/**
	 * 环境：
	 * jdk:1.7、1.8
	 * 
	 * 说明：
	 * boolean数组定义时所有元素默认是false，以numbers[i]=false表示是素数，而true表示不是素数
	 */
	public static void getPrimeNumber() {
		boolean[] numbers = new boolean[100];
		int m = 2, k = 0;
		while (k <= 100) {
			// 去掉不是素数的正整数
			int i = 0;
			for (@SuppressWarnings("unused") boolean b : numbers) {
				if (i%m == 0 && i != m) {
					numbers[i] = true; 
				}
				i++;
			}
			// 筛选完毕后第一个素数作为新的筛选条件
			int j = 0;
			for (boolean b : numbers) {
				if (j > m && !b) {
					m = j;
					break;
				}
				j++;
			}
			k++;
		}
		
		// 晒选结果打印
		int p = 0;
		for (boolean b : numbers) {
			if (!b && p != 1) {
				System.out.println(p);
			}
			p++;
		}
 	}

}
