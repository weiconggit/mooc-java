package org.carryon.web;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.carryon.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @description 电影网站爬虫，空闲时间有限仅仅对 https://www.xl720.com/ 做了数据解析工作
 * @author carryon
 * @date 2019年12月2日
 * @version 1.0
 */
public abstract class Crawler {

	private static String PATH1 = "https://www.xl720.com/?s=";
	public static int AMOUNT = 5;

	public static void getFromXl720Com(String searchValue, JTable table) throws IOException {
		// 1、一级页面解析
		Document doc1ST = Jsoup.connect(PATH1 + searchValue).get();
		Elements page1ST = doc1ST.select("[class=post clearfix]");
		AMOUNT = page1ST.size();

		if (null == page1ST || 0 >= page1ST.size()) return;
		
		// 2、二级页面解析
		for (Element element : page1ST) {
			// 过滤掉电视剧类型
			String isTV = element.select("[rel=category tag]").text();
			
			if (!StringUtil.isBlank(isTV) && isTV.indexOf("电视剧") != -1) continue;

			// 多线程解析电影数据
			new Thread(() -> {
				try {
					Film film = parseDetails(element);
					JLabel jLabel1 = new JLabel();
					jLabel1.setText(film.getPreview());
				    JLabel jLabel2 = new JLabel();
				    jLabel2.setText(film.getDetails());
				    // 增加table行数
//				    SwingUtilities.invokeLater(()->{
				    	DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				    	tableModel.addRow(new Object[]{jLabel1, jLabel2});
//				    });
				} catch (IOException e) {
					e.printStackTrace();
				}
			}).start();
			
		}
        // 复制连接
//      table.addMouseListener(new MouseAdapter() {
//          public void mouseClicked(MouseEvent e){
//            if (e.getButton() == MouseEvent.BUTTON3){ // 鼠标右键监听
//              //在table显示
//              jpm = new JPopupMenu();
//              //表格 的rowAtPoint方法返回坐标所在的行号，参数为坐标类型，
//              int i = table.rowAtPoint(e.getPoint());
////            jpm.add(i+"");
//              jpm.add(jLabel2.getText());
//              LocalSwingUtil.setSysClipboardText(jLabel2.getText());
//              jpm.show(table, e.getX(), e.getY());
//            }
//          }
//        });
		
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
				.append("</html>"); // 拼接为html
		
		// 电影下载链接
		Elements downloadUrls = doc2ND.select("[id=zdownload]").select("a");
		for (Element downloadUrl : downloadUrls) film.getDownload().add(downloadUrl.attr("href"));
		
		System.out.println(preview);
		film.setPreview(preview);
		film.setDetails(builder.toString());
		return film;
	}
}
