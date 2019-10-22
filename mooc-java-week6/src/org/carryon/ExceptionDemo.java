package org.carryon;

/**
 * @description mooc week6 为方便展示，所有的类都放到了一个java文件中
 * @author carryon
 * @time   2019年10月22日 
 * @version 1.0
 */
public class ExceptionDemo {
	
	/**
	 * 编码格式：UTF-8
	 * 说明：以管理员登录过程来演示自定义异常类
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		AdminLogin admin = new ExceptionDemo().new AdminLogin();
		admin.login("admin", "adm");
	}
	
	/**
	 * @description 自定义登录异常
	 * @author carryon 
	 * @time   2019年10月22日 
	 * @version 1.0
	 */
	class LoginException extends Exception {
		
		private static final String NO_LOGIN_PRA = "用户名或密码不能为空";
		private static final String PRA_ERROR = "用户名或密码错误";
		
		private static final long serialVersionUID = 520362345024241122L;

		public LoginException() {}
		
		public LoginException(String msg) {
			super(msg);
		}
		
	}
	
	/**
	 * @description 管理员登录
	 * @author carryon
	 * @time   2019年10月22日
	 * @version 1.0
	 */
	class AdminLogin {
		
		/**
		 * 登录
		 * @param username 用户名
		 * @param password 密码
		 */
		public void login(String username, String password) {
			try {
				check(username, password);
			} catch (LoginException e) {
				System.err.println(e.getMessage());
				return;
			} 
			System.out.println("管理员登录成功");
		}
		
		/**
		 * 登录参数校验
		 * @param username			用户名
		 * @param password			密码
		 * @throws LoginException	登录失败异常
		 */
		public void check(String username, String password) throws LoginException {
			if (null == username || null == password || "".equals(username)|| "".equals(password)) {
				throw new LoginException(LoginException.NO_LOGIN_PRA);
			}
			if (!"admin".equals(username) || !"admin".equals(password)) {
				throw new LoginException(LoginException.PRA_ERROR);
			}
		}

	}
	
}
