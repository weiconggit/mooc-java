package org.carryon.fly.subclass;

import org.carryon.fly.Fly;
import org.carryon.fly.Vehicle;

/**
 * @description 飞机
 * @author carryon
 * @date 2019年10月8日
 * @version 1.0
 */
public class Aircraft extends Vehicle implements Fly {

	public static final String NAME = "飞机";
	
	@Override
	public void fly() {
		System.out.print(NAME);
		charge();
		System.out.println("准备飞行");
		System.out.println(NAME + "正在飞行");
		System.out.println(NAME + "飞行完毕");
	}

	@Override
	protected void charge() {
		System.out.print("加油");
	}

}
