package org.carryon;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

import javax.swing.ImageIcon;

/**
 * @description Swing本地工具类
 * @author carryon
 * @time   2019年12月1日
 * @version 1.0
 */
public abstract class LocalSwingUtil {

	/** 
     * 将字符串复制到剪切板。 
     */  
    public static void setSysClipboardText(String writeMe) {  
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        Transferable tText = new StringSelection(writeMe);  
        clip.setContents(tText, null);
    }
    
    /**
	 *    缩放ICON图片尺寸
	 * @param imgPath
	 * @return
	 */
	public static ImageIcon scalingImg1(String imgPath) {
		ImageIcon imageIconCo = new ImageIcon(imgPath);
		imageIconCo.setImage(imageIconCo.getImage().getScaledInstance(100, 500, Image.SCALE_DEFAULT ));
		return imageIconCo;
	}
	
	/**
	 *    缩放ICON图片尺寸
	 * @param imgPath
	 * @return
	 */
	public static ImageIcon scalingImg(String imgPath) {
		ImageIcon imageIconCo = new ImageIcon(imgPath);
		imageIconCo.setImage(imageIconCo.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT ));
		return imageIconCo;
	}
}
