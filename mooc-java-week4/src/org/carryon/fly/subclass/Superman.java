package org.carryon.fly.subclass;

import org.carryon.fly.Animal;
import org.carryon.fly.Fly;

/**
 * @description 超人
 * @author carryon
 * @date 2019年10月8日
 * @version 1.0
 */
public class Superman extends Animal implements Fly {

	public static final String NAME = "超人";
	
	@Override
	public void fly() {
		System.out.print(NAME);
		eat();
		System.out.println("准备飞行");
		System.out.println(NAME + "正在飞行");
		System.out.println(NAME + "飞行完毕");
	}

	@Override
	protected void eat() {
		System.out.print("吃饭");
	}

}
