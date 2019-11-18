package org.carryon;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @description Mooc Week10
 * jdk: 1.7、1.8
 * encode: UTF-8
 * 	描述：正确输入数据后，点击“进行计算”即可，程序会进行计算，计算结果会根据具体情况以不同颜色显示
 * 
 * @author carryon
 * @date 2019年11月18日
 * @version 1.0
 */
public class BMI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 5874000900010870532L;

	/**
	 * 体重指数计算器主类入口
	 * @param args
	 */
	public static void main(String[] args) {
		BMI bmi = new BMI();
		bmi.init();
	}
	
	JPanel jPanelInput = new JPanel(new GridLayout(3,2,0,5));
	JPanel jPanelButton = new JPanel();
	JPanel jPanelDisplay = new JPanel(new GridLayout(1,2,0,5));
	
	JLabel jLabelGender = new JLabel("性别（男/女）：", JLabel.CENTER);
	JLabel jLabelWeight = new JLabel("体重（kg）：", JLabel.CENTER);
	JLabel jLabelStature = new JLabel("身高（m）：", JLabel.CENTER);
	JTextField jTextGender = new JTextField(); // 性别输入框
	JTextField jTextWeight = new JTextField(); // 体重输入框
	JTextField jTextStature = new JTextField(); // 身高输入框
	
	JButton jButtonConfirm = new JButton("进行计算");
	JLabel jLabelShow = new JLabel("健康值：", JLabel.CENTER);
	JLabel jLabelShowData = new JLabel("", JLabel.CENTER);
	
	Font fontBase = new Font("楷书", Font.CENTER_BASELINE, 20);
	
	/**
	 * 	窗口初始化
	 * 	底层布局：盒子y布局	层级：0
	 * 	输入框区：网格布局	层级：1
	 * 	按钮区：盒子x布局	层级：1
	 * 	显示区：网格布局	层级：1
	 */
	private void init() {
		setSize(550, 350);
		setTitle("体重指数计算器");
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		setLocationRelativeTo(null); // 窗口整体居中
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		// 1、组件属性设置
		jLabelGender.setFont(fontBase);
		jLabelWeight.setFont(fontBase);
		jLabelStature.setFont(fontBase);
		jTextGender.setFont(fontBase);
		jTextWeight.setFont(fontBase);
		jTextStature.setFont(fontBase);
		
		jButtonConfirm.setFont(new Font("楷书", Font.CENTER_BASELINE, 26));
		jButtonConfirm.setBackground(Color.green);
		jButtonConfirm.addActionListener(this);
		
		jLabelShow.setFont(fontBase);
		jLabelShowData.setFont(fontBase);
		
		// 2、组件布局设置
		jPanelInput.add(jLabelGender);
		jPanelInput.add(jTextGender);
		jPanelInput.add(jLabelWeight);
		jPanelInput.add(jTextWeight);
		jPanelInput.add(jLabelStature);
		jPanelInput.add(jTextStature);
		
		jPanelButton.setLayout(new BoxLayout(jPanelButton,BoxLayout.X_AXIS));
		jPanelButton.add(jButtonConfirm);
		
		jPanelDisplay.add(jLabelShow);
		jPanelDisplay.add(jLabelShowData);

		add(jPanelInput);
		add(Box.createVerticalStrut(10));
		add(jPanelButton);
		add(jPanelDisplay);
	}
	
	/**
	 * 按钮监听事件
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object instanceof JButton) {
			String gender = jTextGender.getText();
			String weight = jTextWeight.getText();
			String stature = jTextStature.getText();
			if (!Objects.equals("男", gender) && !Objects.equals("女", gender)) {
				diglog("请输入正确的性别");
				return;
			}
			if (!Pattern.matches("^[1-9][0-9]{0,2}(\\.[0-9]{1,2})?$", weight)) {
				diglog("体重介于1~999.99之间的数字，且最多有两位小数");
				return;
			}
			if (!Pattern.matches("^[1-9](\\.[0-9]{1,2})?$", stature)) {
				diglog("身高介于1~9.99之间的数字，且最多有两位小数");
				return;
			}
			BigDecimal weiBigDecimal = new BigDecimal(weight);
			BigDecimal staBigDecimal = new BigDecimal(stature);
			BigDecimal bmi = weiBigDecimal.divide(staBigDecimal.multiply(staBigDecimal), 2, BigDecimal.ROUND_HALF_DOWN);
			
			// 轻BMI 健康BMI  超重BMI	肥胖BMI
			// <18.5 18.5~24 24~28	28≤
			if (bmi.compareTo(new BigDecimal("18.5")) == -1) {
				jLabelShowData.setForeground(Color.orange.darker());
				jLabelShowData.setText("BMI值为：" + bmi + "，偏瘦");
			} else if (bmi.compareTo(new BigDecimal("24")) == -1) {
				jLabelShowData.setForeground(Color.green);
				jLabelShowData.setText("BMI值为：" + bmi + "，健康");
			} else if (bmi.compareTo(new BigDecimal("28")) == -1) {
				jLabelShowData.setForeground(Color.yellow.darker());
				jLabelShowData.setText("BMI值为：" + bmi + "，超重");
			} else  {
				jLabelShowData.setForeground(Color.red);
				jLabelShowData.setText("BMI值为：" + bmi + "，肥胖");
			}
		} 
	}

	private void diglog(String content) {
		JOptionPane.showMessageDialog(null, content, "提示", JOptionPane.WARNING_MESSAGE);
	}
	
}
