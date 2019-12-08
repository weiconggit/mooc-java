package org.carryon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.carryon.util.LocalSwingUtil;
import org.carryon.util.StringUtil;
import org.carryon.web.Crawler;

/**
 * 说明：
 * 	1、编码:UTF-8
 * 	2、JDK:1.8以上
 * 	3、请将lib目录下的jsoup包添加到IDE编译的配置中
 * 	4、空闲时间有限，仅对 https://www.xl720.com/ 搜索第一页做了数据解析工作
 * 
 * @description 程序入口
 * @author carryon
 * @date 2019年11月26日
 * @version 1.0
 */
public class App extends JFrame {

	private static final long serialVersionUID = 763670611593645586L;

	Font fontBase = new Font("楷书", Font.CENTER_BASELINE, 15);
	
	JLabel jLabelSearch = new JLabel();			// 导航栏图标
	JLabel jLabelPlayer = new JLabel();
	JLabel jLabelCollect = new JLabel();
	
	JTextField jTextSearch = new JTextField(); 	// 搜索框
	JButton jButtonSearch = new JButton();		// 搜索按钮
	JLabel loading = new JLabel("loading...");
	
	JPanel jPanelNavigation = new JPanel(); 	// 导航栏
	JPanel jPanelSearch = new JPanel(); 		// 搜索页
	JPanel jPanelPlayer = new JPanel(); 		// 播放页 未开发
	JPanel jPanelCollect = new JPanel(); 		// 收藏页 未开发
	
	JPanel jPanelSearchTop = new JPanel();		// 搜索页顶部
	JPanel jPanelSearchBottom = new JPanel();   // 搜索页底部数据展示
	
	ImageIcon imageIconSe = LocalSwingUtil.scalingImg("icon/search.png");
	ImageIcon imageIconPl = LocalSwingUtil.scalingImg("icon/player.png");
	ImageIcon imageIconCo = LocalSwingUtil.scalingImg("icon/collect.png");
	ImageIcon imageIconSeGray = LocalSwingUtil.scalingImg("icon/search_gray.png");
	ImageIcon imageIconPlGray = LocalSwingUtil.scalingImg("icon/player_gray.png");
	ImageIcon imageIconCoGray = LocalSwingUtil.scalingImg("icon/collect_gray.png");

	JTable table = LocalSwingUtil.getTableStyle();
	JScrollPane jScrollPane = new JScrollPane(table);
	
	/**
	 * 窗体初始化
	 * @throws IOException
	 */
	public void init() throws IOException {
		setSize(800, 600);
		setTitle("CarryOn Mooc Week11 电影资源嗅探器");
		setLayout(new BorderLayout());
		// 窗口整体居中
		setLocationRelativeTo(null); 
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// 1、导航栏组件
		initNavigationComponent();
		
		// 2、搜索页
		initSearchTopComponent();
		initSearchBottomComponent();		
		
		// 3、播放页
		// 4、收藏页

		// 5、布局
		initPanel();
		
		// 一定要放到最后，不然组件显示不完整
		setVisible(true); 
	}
	
	/**
	 * 右侧导航栏组件初始化
	 */
	public void initNavigationComponent() {
		jLabelSearch.setIcon(imageIconSe);
		jLabelSearch.setBounds(20, 50, 50, 50);
		jLabelSearch.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				jLabelSearch.setIcon(imageIconSe);
				jLabelPlayer.setIcon(imageIconPlGray);
				jLabelCollect.setIcon(imageIconCoGray);
			}
		});
		
		jLabelPlayer.setIcon(imageIconPlGray);
		jLabelPlayer.setBounds(20, 120, 50, 50);
		jLabelPlayer.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				jLabelSearch.setIcon(imageIconSeGray);
				jLabelPlayer.setIcon(imageIconPl);
				jLabelCollect.setIcon(imageIconCoGray);
			}
		});
		
		jLabelCollect.setIcon(imageIconCoGray);
		jLabelCollect.setBounds(20, 190, 50, 50);
		jLabelCollect.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				jLabelSearch.setIcon(imageIconSeGray);
				jLabelPlayer.setIcon(imageIconPlGray);
				jLabelCollect.setIcon(imageIconCo);
			}
		});
	}
	
	/**
	 * 搜索页顶部组件初始化
	 */
	public void initSearchTopComponent() {
		jTextSearch.setFont(fontBase);
		jTextSearch.setPreferredSize(new Dimension(150, 30));
		
		jButtonSearch.setText("搜索");
		jButtonSearch.setBackground(Color.white);
		jButtonSearch.setForeground(Color.green);
		jButtonSearch.setFont(fontBase);
		jButtonSearch.setPreferredSize(new Dimension(70, 30));
		jButtonSearch.addActionListener(e->{
			String filmName = jTextSearch.getText();
			if (StringUtil.isBlank(filmName)) {
				LocalSwingUtil.diglog("请输入关键词");
			}
			loading.setVisible(true);
			loading.updateUI();
		    try {
				DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				tableModel.setRowCount(0);
				Crawler.getFromXl720Com(filmName, table, loading);
			} catch (Exception e1) {
				LocalSwingUtil.diglog("加载出错，请重试");
				e1.printStackTrace();
			}
		});
		loading.setVisible(false);
		
		jPanelSearchTop.setLayout(new FlowLayout()); // 可写可不写，默认即是流动布局
		jPanelSearchTop.setPreferredSize(new Dimension(730, 45));
		jPanelSearchTop.setBackground(Color.white);
		
		jPanelSearchTop.add(jTextSearch);
		jPanelSearchTop.add(jButtonSearch);
		jPanelSearchTop.add(loading);
	}

	/**
	 * 搜索页底部组件初始化
	 */
	public void initSearchBottomComponent() {   
	    jScrollPane.setBorder(null);
		jScrollPane.setPreferredSize(new Dimension(600, 510));
		try {
			Crawler.getFromXl720Com("周星驰", table, loading);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jPanelSearchBottom.setBackground(Color.white);
		jPanelSearchBottom.add(jScrollPane);
	}
	
	/**
	 * 主体布局初始化
	 */
	public void initPanel() {
		jPanelNavigation.setLayout(null);
		jPanelNavigation.setPreferredSize(new Dimension(70, 600));
		jPanelNavigation.add(jLabelSearch);
		jPanelNavigation.add(jLabelPlayer);
		jPanelNavigation.add(jLabelCollect);
		jPanelNavigation.setBackground(new Color(51, 51, 51));
		
		jPanelSearch.setLayout(new BorderLayout());
		jPanelSearch.add(jPanelSearchTop, BorderLayout.NORTH);
		jPanelSearch.add(jPanelSearchBottom, BorderLayout.CENTER);
		
		add(jPanelNavigation, BorderLayout.WEST);
		add(jPanelSearch, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) throws IOException {
		App app = new App();
		app.init();
	}
	
}
