package org.carryon.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @description 重写单元格内对象类型
 * @author carryon
 * @time   2019年12月1日
 * @version 1.0
 */
public class LocalTableCellRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = -8741799891622880282L;

	@Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return (JLabel)value;
    }
	
}
