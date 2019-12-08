package org.carryon.web;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.carryon.util.FileUtil;
import org.carryon.util.LocalSwingUtil;
import org.carryon.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @description 电影网站爬虫
 * @author carryon
 * @date 2019年12月2日
 * @version 1.0
 */
public abstract class Crawler {
	
	private static ExecutorService executor = Executors.newFixedThreadPool(1);
	private static List<String> list = FileUtil.readFileByNIO("conf/film.txt");
	static MouseAdapter mouseAdapter;
	
	public static void getFromXl720Com(String searchValue, JTable table, JLabel loading) throws IOException {
		// 1、一级页面解析
		Document doc1ST = Jsoup.connect(list.get(0) + searchValue).get();
		Elements page1ST = doc1ST.select("[class=post clearfix]");

		if (null == page1ST || 0 >= page1ST.size()) return;
		
		table.removeMouseListener(mouseAdapter);
		List<Element> origin = new ArrayList<>();
		List<Film> list = new ArrayList<>();
		for (Element element : page1ST) {
			// 过滤掉电视剧类型
			String isTV = element.select("[rel=category tag]").text();
			if (!StringUtil.isBlank(isTV) && isTV.indexOf("电视剧") == -1) origin.add(element);
		}
		
		
		// 2、二级页面解析
		for (int i = 0; i < origin.size(); i++) {
			final int j = i;
			// 多线程解析电影数据
			executor.submit(() -> {
				Film film = null;
				try {
					film = parseDetails(origin.get(j));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				list.add(film);
				JLabel jLabel1 = new JLabel();
				jLabel1.setText(film.getPreview());
			    JLabel jLabel2 = new JLabel();
			    jLabel2.setText(film.getDetails());
			    
			    // 增加table行数
			    SwingUtilities.invokeLater(()->{
				    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
			    	tableModel.addRow(new Object[]{jLabel1, jLabel2});
			    	if (j == origin.size()-1) {
			    		// 复制电影链接
			    		mouseAdapter = new MouseAdapter() {
			    			public void mouseClicked(MouseEvent e){
			    	            if (e.getButton() == MouseEvent.BUTTON3){ // 鼠标右键监听
			    	            	int rowNum = table.rowAtPoint(e.getPoint());
			    	            	JPopupMenu jpm = new JPopupMenu();
			    	            	jpm.add("复制成功");
			                		LocalSwingUtil.setSysClipboardText(list.get(rowNum).getDownload().get(0));
			                		jpm.show(table, e.getX(), e.getY());
			    	            }
			    	         }
						};
						table.addMouseListener(mouseAdapter);
			    		loading.setVisible(false);
			    		loading.updateUI();
			    	}
			    });
			});
		}
		
	}

	/**
	 * 解析电影数据
	 * 
	 * @param element
	 * @param list
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static Film parseDetails(Element element) throws IOException {
		Film film = new Film();
		Document doc2ND = Jsoup.connect(element.select("[class=entry-thumb lazyload]").attr("href")).get();

		// 电影名称、预览图
		String name = doc2ND.selectFirst("h1").text();
		String preview = doc2ND.getElementById("mainpic").getElementsByAttribute("src").attr("src");
		preview = new StringBuilder("<html><img src='").append(preview).append("' height='180' width='130'></html>").toString();
		
		// 电影基本详情
		StringTokenizer tokenizer = new StringTokenizer(String.valueOf(doc2ND.getElementById("info")), "类型");
		tokenizer.nextToken(); // 去掉第一段字符
		String details = tokenizer.nextToken(); // 取得截取二段字符
		details = details.substring(0, details.lastIndexOf("<br>")); // 去掉最后<br>之后的html
		StringBuilder builder = new StringBuilder("<html>")
				.append(name)
				.append("<br> 类型")
				.append(details)
				.append("<br> 右键复制链接</html>"); // 拼接为html
		
		// 电影下载链接
		Elements downloadUrls = doc2ND.select("[id=zdownload]").select("a");
		for (Element downloadUrl : downloadUrls) film.getDownload().add(downloadUrl.attr("href"));
		
		film.setName(name);
		film.setPreview(preview);
		film.setDetails(builder.toString());
		return film;
	}
}
