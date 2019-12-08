package org.carryon.util;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @description Swing本地工具类
 * @author carryon
 * @time   2019年12月1日
 * @version 1.0
 */
public abstract class LocalSwingUtil {
	
	private static LocalTableCellRenderer LOCAL_RENDERER = new LocalTableCellRenderer();
	
	/**
	 * 电影列表jtable样式
	 * @return
	 */
	public static JTable getTableStyle() {
		Object[][] columnDate = new Object[0][2];
	    String[] columnName = new String[]{"", ""};
	    
		TableModel tm = new DefaultTableModel(columnDate, columnName);
	    final JTable table = new JTable(tm);
	    table.setAutoscrolls(true);
	    // 关闭可编辑、标题头等
	    table.getTableHeader().setVisible(false);  
	    table.setEnabled(false);
	    table.setShowGrid(false);
	    table.setShowHorizontalLines(false);
	    table.setShowVerticalLines(false);
	    // 高度
	    table.setRowHeight(200);
	    // 宽度
	    table.getColumnModel().getColumn(0).setPreferredWidth(140);
	    table.getColumnModel().getColumn(1).setPreferredWidth(460);
	    // 设置可让图片显示在JTable中
        table.getColumnModel().getColumn(0).setCellRenderer(LOCAL_RENDERER); 
        table.getColumnModel().getColumn(1).setCellRenderer(LOCAL_RENDERER); 
        return table;
	}
	
	/**
	 * 消息提示窗口
	 * @param content
	 */
	public static void diglog(String content) {
		JOptionPane.showMessageDialog(null, content, "提示", JOptionPane.WARNING_MESSAGE);
	}

	/** 
     * 将字符串复制到剪切板。 
     */  
    public static void setSysClipboardText(String content) {  
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        Transferable tText = new StringSelection(content);  
        clip.setContents(tText, null);
    }
	
	/**
	 * 缩放ICON图片尺寸
	 * @param imgPath
	 * @return
	 */
	public static ImageIcon scalingImg(String imgPath) {
		ImageIcon imageIconCo = new ImageIcon(imgPath);
		imageIconCo.setImage(imageIconCo.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT ));
		return imageIconCo;
	}
}
