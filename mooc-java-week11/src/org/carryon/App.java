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
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @description 程序入口
 * 	
 * 	1.setVisible(true); 一定要放到初始化最后
 * 
 * 
 * 	底层布局：边界布局，分成导航栏和功能栏
 * 	导航栏：绝对布局，固定三个导航按钮
 * 
 * @author carryon
 * @date 2019年11月26日
 * @version 1.0
 */
public class App extends JFrame {

	private static final long serialVersionUID = 763670611593645586L;

	
	Font fontBase = new Font("楷书", Font.CENTER_BASELINE, 15);
	
	JLabel jLabelSearch = new JLabel();
	JLabel jLabelPlayer = new JLabel();
	JLabel jLabelCollect = new JLabel();
	JLabel jLabel = new JLabel();
	
	JTextField jTextSearch = new JTextField(); // 搜索框
	JButton jButtonSearch = new JButton();
	
	JPanel jPanelNavigation = new JPanel(); // 导航栏
	JPanel jPanelSearch = new JPanel(); // 搜索页
	JPanel jPanelPlayer = new JPanel(); // 播放页
	JPanel jPanelCollect = new JPanel(); // 收藏页
	
	JPanel jPanelSearchTop = new JPanel();
	JPanel jPanelSearchBottom = new JPanel();
	
	ImageIcon imageIconSe = LocalSwingUtil.scalingImg("icon/search.png");
	ImageIcon imageIconPl = LocalSwingUtil.scalingImg("icon/player.png");
	ImageIcon imageIconCo = LocalSwingUtil.scalingImg("icon/collect.png");
	ImageIcon imageIconSeGray = LocalSwingUtil.scalingImg("icon/search_gray.png");
	ImageIcon imageIconPlGray = LocalSwingUtil.scalingImg("icon/player_gray.png");
	ImageIcon imageIconCoGray = LocalSwingUtil.scalingImg("icon/collect_gray.png");
	ImageIcon imageIcon = LocalSwingUtil.scalingImg1("img/2019-11-25 143932.jpg");

	JPopupMenu jpm;
	LocalTableCellRenderer localTableCellRenderer = new LocalTableCellRenderer();
	
	public void init() throws IOException {
		setSize(800, 600);
		setTitle("Mooc Week11 电影资源嗅探管理器");
		setLayout(new BorderLayout());
		setLocationRelativeTo(null); // 窗口整体居中
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// 1、导航栏组件
		initNavigationComponent();
		
		// 2、搜索页
		initSearchTopComponent();
		initSearchBottomComponent();		
		
		// 3、播放页
//		initPlayer();
		
		// 4、布局
		initPanel();
		
		setVisible(true); // 一定要放到最后，不然组件显示不完整
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
//				jPanelSearch.setVisible(true);
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
//				jPanelSearch.setVisible(false);
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
		
		jPanelSearchTop.setLayout(new FlowLayout()); // 可写可不写，默认即是流动布局
		jPanelSearchTop.setPreferredSize(new Dimension(730, 45));
		jPanelSearchTop.setBackground(Color.white);
		
		jPanelSearchTop.add(jTextSearch);
		jPanelSearchTop.add(jButtonSearch);
	}

	/**
	 * 搜索页底部组件初始化
	 */
	public void initSearchBottomComponent() {
		JLabel jLabel1 = new JLabel();
		jLabel1.setSize(30, 50);
		jLabel1.setText("<html><img src=\"http://img.idyjy.com/pic/uploadimg/2016-9/14425.jpg\" height=\"180\" width=\"130\"></html>");
	    final JLabel jLabel2 = new JLabel();
	    jLabel2.setText("<html><p>机械师2</p><p>导演：苇丛</p><p>下载地址：http://img.idyjy.com/pic/uploadimg/2016-9/14425.jpg</p></html>");
	    
	    String[] columnName = new String[]{"", ""};
	    Object[][] columnDate = new Object[6][2];
	    columnDate[0][0] = jLabel1;
	    columnDate[0][1] = jLabel2;
	    columnDate[1][0] = jLabel1;
	    columnDate[1][1] = jLabel2;
	    
	    TableModel tm = new DefaultTableModel(columnDate, columnName);
	    final JTable table = new JTable(tm);
	    table.getTableHeader().setVisible(false);  
	    table.setEnabled(false);
	    table.setAutoscrolls(true);
	    table.setRowHeight(200);
	    table.setShowGrid(false);
	    table.setShowHorizontalLines(false);
	    table.setShowVerticalLines(false);
	    table.getColumnModel().getColumn(0).setPreferredWidth(140);
	    table.getColumnModel().getColumn(1).setPreferredWidth(460);
        table.getColumnModel().getColumn(0).setCellRenderer(localTableCellRenderer); // 设置可让图片显示在JTable中
        table.getColumnModel().getColumn(1).setCellRenderer(localTableCellRenderer); 
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
              if (e.getButton() == MouseEvent.BUTTON3){ // 鼠标右键监听
                //在table显示
                jpm = new JPopupMenu();
                //表格 的rowAtPoint方法返回坐标所在的行号，参数为坐标类型，
                int i = table.rowAtPoint(e.getPoint());
//              jpm.add(i+"");
                jpm.add(jLabel2.getText());
                LocalSwingUtil.setSysClipboardText(jLabel2.getText());
                jpm.show(table, e.getX(), e.getY());
              }
            }
          });
        
	    JScrollPane jScrollPane = new JScrollPane(table);
	    jScrollPane.setBorder(null);
		jScrollPane.setPreferredSize(new Dimension(600, 510));
		
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
