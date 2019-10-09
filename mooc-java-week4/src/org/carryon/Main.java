package org.carryon;

import org.carryon.fly.Fly;
import org.carryon.fly.subclass.Aircraft;
import org.carryon.fly.subclass.Bird;
import org.carryon.fly.subclass.Superman;

/**
 * @description Mooc week4 互评作业 选题：飞行接力
 * @author carryon
 * @date 2019年10月8日
 * @version 1.0
 */
public class Main {

	/**
	 * 编码格式：UTF-8 
	 * 说明：依次调用拥有飞行能力的对象进行飞行行为接力，项目中每个类的说明请参考类中@description
	 * 运行：导入ide中，如果是eclipse，选择普通java项目导入，eclipse报错的话请添加jre到项目中
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Fly fly1 = new Aircraft();
		Fly fly2 = new Bird();
		Fly fly3 = new Superman();

		fly1.fly();
		fly2.fly();
		fly3.fly();
	}

}
