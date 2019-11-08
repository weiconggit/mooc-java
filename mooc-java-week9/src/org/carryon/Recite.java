package org.carryon;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * @description 
 * jdk: 1.8以上
 * encoding: UTF-8
 * 注意：如果拷贝代码测试，请将单词库txt文件（College_Grade4.txt）放到项目根目录下，直接导入项目到IDE测试的可忽略
 * 
 * 	Mooc Week9 背单词小程序优化
 * 	1、窗口添加面板分层，单词、按钮组分别隔开
 * 	2、添加三个按钮：记忆（暂停）、自测、退出程序三个功能
 * 	3、自测功能：选择正确的中文意义，会跳转到下一个单词，选择错误，该选项会有红色提示
 * 	4、单词功能：记忆/暂停可以控制单词显示
 * 
 * @author carryon
 * @date 2019年11月6日
 * @version 1.0
 */
public class Recite extends JFrame {

	private static final long serialVersionUID = -5089523113964821145L;
	
	JPanel panel0 = new JPanel();	// 背景面板
	JPanel panel1 = new JPanel(new GridLayout(5,1,0,0));	// 单词面板，网格布局 2行1列，水平间距0，垂直间距0
	JPanel panel2 = new JPanel(new GridLayout(1,3,5,5));	// 按钮面板
	
	JButton btn1 = getJButton("记忆");
	JButton btn2 = getJButton("自测");
	JButton btn3 = getJButton("退出");
	
	JLabel lblWord = new JLabel("word", JLabel.CENTER);
	JLabel lblMeaning = new JLabel("meaning", JLabel.CENTER);
	
	JRadioButton radio1 = getJRadioButton();
	JRadioButton radio2 = getJRadioButton();
	JRadioButton radio3 = getJRadioButton();
	JRadioButton radio4 = getJRadioButton();
	
	public static boolean PAUSE = true;	// 记忆/暂停控制
	javax.swing.Timer timer;	// 单词定时器 
	int radioValue;
	int rightValue;
	
	public void init() {
		setSize( 450,400 );	// 窗体基本设置
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));	// 盒子y布局
		
		// 单词字体设置
		lblWord.setFont(new Font("西文", Font.CENTER_BASELINE, 40));
		lblMeaning.setFont(new Font("楷书", Font.PLAIN, 20));
		
		// 单选按钮组监听事件
		radio1.addActionListener(e -> {
			if (rightValue == 0) {
				radio1.setBackground(Color.green);
				refreshRadio();
			}else {
				radio1.setBackground(Color.red);
			}
		});
		radio2.addActionListener(e -> {
			if (rightValue == 1) {
				radio2.setBackground(Color.green);
				refreshRadio();
			}else {
				radio2.setBackground(Color.red);
			}
		});
		radio3.addActionListener(e -> {
			if (rightValue == 2) {
				radio3.setBackground(Color.green);
				refreshRadio();
			}else {
				radio3.setBackground(Color.red);
			}
		});
		radio4.addActionListener(e -> {
			if (rightValue == 3) {
				radio4.setBackground(Color.green);
				refreshRadio();
			}else {
				radio4.setBackground(Color.red);
			}
		});
		
		// 记忆/暂停 按钮
		btn1.setBackground(Color.green);
		btn1.addActionListener(e -> {
			panel1.removeAll();
			panel1.setBackground(Color.white);
			lblWord.setText(words.get(current));
			lblMeaning.setText(meanings.get(current));
			panel1.add(lblWord);
			panel1.add(lblMeaning);
			panel1.repaint();	// or panel1.updateUI();
			if (PAUSE) {
				timer.start();
				btn1.setText("暂停");
				btn1.setBackground(Color.red);
				PAUSE = false;
			}else {
				timer.stop();
				btn1.setText("记忆");
				btn1.setBackground(Color.green);
				PAUSE = true;
			}
		});
		// 自测 按钮
		btn2.setBackground(Color.white);
		btn2.addActionListener(e -> {
			// 1暂停单词定时器
			timer.stop();
			btn1.setText("记忆");
			btn1.setBackground(Color.green);
			PAUSE = true;
			// 2清除内容替换为单选数据
			refreshRadio();
		});
		// 退出 按钮
		btn3.setBackground(Color.RED);
		btn3.addActionListener(e -> System.exit(0));
		
		// 单词区面板
		panel1.setBackground(Color.white);
		panel1.add(lblWord);
		panel1.add(lblMeaning);
		// 按钮组面板
		panel2.setBackground(Color.gray);
		panel2.add(btn1);
		panel2.add(btn2);
		panel2.add(btn3);
		// 背景面板		
		panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
		panel0.setBackground(Color.gray);
		panel0.add(Box.createVerticalStrut(10));	// 添加页面适配固定间隔
		panel0.add(panel1);
		panel0.add(Box.createVerticalStrut(10));
		panel0.add(panel2);
		panel0.add(Box.createVerticalStrut(10));
		
		getContentPane().add(panel0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	/**
	 * 刷新单选按钮
	 */
	private void refreshRadio() {
		panel1.removeAll();
		panel1.setBackground(Color.white);
		ButtonGroup group = new ButtonGroup();
		group.add(radio1);
		group.add(radio2);
		group.add(radio3);
		group.add(radio4);
		
		panel1.add(lblWord);
		panel1.add(radio1);
		panel1.add(radio2);
		panel1.add(radio3);
		panel1.add(radio4);
		test();	// 自测数据渲染
	}
	
	/**
	 * 生产普通按钮
	 * @param name
	 * @return
	 */
	private JButton getJButton(String name) {
		JButton btn = new JButton(name);
		btn.setFont(new Font("楷书", Font.CENTER_BASELINE, 20));
		return btn;
	}
	
	/**
	 * 生产单选按钮
	 * @return
	 */
	private JRadioButton getJRadioButton() {
		JRadioButton radio = new JRadioButton();
		radio.setFont(new Font("楷书", Font.PLAIN, 17));
		radio.setBackground(Color.white);
		return radio;
	}
	
	/**
	 * 自测单选数据生成与渲染
	 */
	private void test() {
		new Thread(()->{
			radioValue = randomNum();
			lblWord.setText(words.get(radioValue));
			int num = new Random().nextInt(4);
			switch (num) {
			case 0: 
				radio1.setText("A. " + meanings.get(radioValue));
				radio2.setText("B. " + meanings.get(randomNum()));
				radio3.setText("C. " + meanings.get(randomNum()));
				radio4.setText("D. " + meanings.get(randomNum()));
				rightValue = 0;
				break;
			case 1:
				radio1.setText("A. " + meanings.get(randomNum()));
				radio2.setText("B. " + meanings.get(radioValue));
				radio3.setText("C. " + meanings.get(randomNum()));
				radio4.setText("D. " + meanings.get(randomNum()));
				rightValue = 1;
				break;
			case 2:
				radio1.setText("A. " + meanings.get(randomNum()));
				radio2.setText("B. " + meanings.get(randomNum()));
				radio3.setText("C. " + meanings.get(radioValue));
				radio4.setText("D. " + meanings.get(randomNum()));
				rightValue = 2;
				break;
			case 3:
				radio1.setText("A. " + meanings.get(randomNum()));
				radio2.setText("B. " + meanings.get(randomNum()));
				radio3.setText("C. " + meanings.get(randomNum()));
				radio4.setText("D. " + meanings.get(radioValue));
				rightValue = 3;
				break;
			default:
				break;
			}
		}).start();
		radio1.setBackground(Color.white);
		radio2.setBackground(Color.white);
		radio3.setBackground(Color.white);
		radio4.setBackground(Color.white);
		panel1.repaint();
	}
	
	/**
	 * 产生随机下标
	 * @return
	 */
	private int randomNum() {
		int num = new Random().nextInt(words.size());
		return num;
	}
	
	List<String> words = new ArrayList<>();
	List<String> meanings = new ArrayList<>();
	int current = 0;
	public void start() {
		new Thread(()->{
			try{
				readAll();
			}catch(IOException ex){}
			timer = new javax.swing.Timer(1500,(e)->{
				lblWord.setText( words.get(current) );
				lblMeaning.setText( meanings.get(current) );
				current++;
			});
		}).start();
	}

	public void readAll( ) throws IOException{
		String fileName = "College_Grade4.txt";
		String charset = "GB2312";
		BufferedReader reader = new BufferedReader(
			new InputStreamReader(
				new FileInputStream(fileName), charset)); 
		String line; 
		while ((line = reader.readLine()) != null) { 
			line = line.trim();
			if( line.length() == 0 ) continue;
			int idx = line.indexOf("\t");
			words.add( line.substring(0, idx ));
			meanings.add( line.substring(idx+1));
		} 
		reader.close();
	}

	public static void main( String[] args){
		Recite f = new Recite();
		f.init();
		f.start();
	}
}
