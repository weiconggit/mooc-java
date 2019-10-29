package org.carryon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description Mooc Week7 为了方便验证测试，银行类和银行账户类都放到一个java文件中
 * @author carryon
 * @time 2019年10月29日
 * @version 1.0
 */
public class BankDemo {

	/**
	 * 编码：UTF-8
	 * 环境：jdk1.5以上
	 * @param args
	 */
	public static void main(String[] args) {
		BankDemo bankDemo = new BankDemo();

		// 创建一个银行
		Bank bank = bankDemo.new Bank();
		bank.setName("中国银行");

		// 张三开户
		BankAccount account = bankDemo.new BankAccount();
		account.setUsername("zhangsan");
		account.setPassword("123456");
		System.out.println(bank.open(account));

		// 存钱
		System.out.println(bank.save(account, new BigDecimal("100.00")));
		// 查询余额
		System.out.println(bank.query(account));
		// 取钱
		System.out.println(bank.withdraw(account, new BigDecimal("20.50")));
		// 查询余额
		System.out.println(bank.query(account));

		// 李四开户
		BankAccount account2 = bankDemo.new BankAccount();
		account2.setUsername("lisi");
		account2.setPassword("123456");
		System.out.println(bank.open(account2));

		// 存钱
		System.out.println(bank.save(account2, new BigDecimal("200.00")));
		// 查询余额
		System.out.println(bank.query(account2));
		// 取钱
		System.out.println(bank.withdraw(account2, new BigDecimal("300.00")));
		// 查询余额
		System.out.println(bank.query(account2));
	}

	/**
	 * @description 银行类
	 * @author carryon
	 * @time 2019年10月29日
	 * @version 1.0
	 */
	class Bank {
		private String name;
		private List<BankAccount> accounts = new ArrayList<>();

		/**
		 * 开户
		 * 
		 * @return
		 */
		public String open(BankAccount account) {
			if (null == account || null == account.getUsername() || "".equals(account.getUsername())
					|| null == account.getPassword() || "".endsWith(account.getPassword())) {
				return "请输入开户账号和密码";
			}
			account.setBalance(new BigDecimal("0.00"));
			accounts.add(account);
			return account.getUsername() + "，欢迎使用" + name;
		}

		/**
		 * 账户余额查询
		 * 
		 * @return
		 */
		public String query(BankAccount account) {
			if (null == account) {
				return "请输入账号密码";
			}

			BankAccount bankAccount = queryAccount(account);
			if (null == bankAccount) {
				return "未查询到账户信息";
			}
			return name + "提示：您当前账户余额为" + String.valueOf(bankAccount.getBalance()) + "元";
		}

		/**
		 * 存钱
		 * 
		 * @param account 存钱账户
		 * @param amount  存钱金额
		 * @return
		 */
		public String save(BankAccount account, BigDecimal amount) {
			BankAccount bankAccount = queryAccount(account);
			if (null == bankAccount) {
				return "未查询到账户信息";
			}
			bankAccount.setBalance(bankAccount.save(amount));
			return name + "提示：您成功存入" + String.valueOf(bankAccount.getBalance()) + "元";
		}

		/**
		 * 取钱
		 * 
		 * @param account 取钱账户
		 * @param amount  取钱金额
		 * @return
		 */
		public String withdraw(BankAccount account, BigDecimal amount) {
			BankAccount bankAccount = queryAccount(account);
			if (null == bankAccount) {
				return "未查询到账户信息";
			}
			if (bankAccount.getBalance().compareTo(amount) >= 0) {
				bankAccount.setBalance(bankAccount.withdraw(amount));
				return name + "提示：您取出人民币" + String.valueOf(amount) + "元";
			}
			return "余额不足";
		}

		/**
		 * 查询账户存在与否
		 * 
		 * @param account
		 * @return
		 */
		public BankAccount queryAccount(BankAccount account) {
			for (BankAccount bankAccount : accounts) {
				if (bankAccount.getUsername().equals(account.getUsername())
						&& bankAccount.getPassword().equals(account.getPassword())) {
					return bankAccount;
				}
			}
			return null;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<BankAccount> getAccounts() {
			return accounts;
		}

		public void setAccounts(List<BankAccount> accounts) {
			this.accounts = accounts;
		}

	}

	/**
	 * @description 银行账户类
	 * @author carryon
	 * @time 2019年10月29日
	 * @version 1.0
	 */
	class BankAccount {
		private String username;
		private String password;
		private BigDecimal balance;

		/**
		 * 存钱
		 * 
		 * @param amount 存钱金额
		 * @return
		 */
		public BigDecimal save(BigDecimal amount) {
			return balance.add(amount);
		}

		/**
		 * 取钱
		 * 
		 * @param amount 取钱金额
		 * @return
		 */
		public BigDecimal withdraw(BigDecimal amount) {
			if (balance.compareTo(amount) >= 0) {
				return balance.subtract(amount);
			}
			return null;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public BigDecimal getBalance() {
			return balance;
		}

		public void setBalance(BigDecimal balance) {
			this.balance = balance;
		}

	}

}
