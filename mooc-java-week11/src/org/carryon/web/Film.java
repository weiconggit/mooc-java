package org.carryon.web;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 影片
 * @author carryon
 * @date 2019年12月2日
 * @version 1.0
 */
public class Film {
	
	private String preview;     	// 预览图
	private String details;			// 影片详情
	private List<String> download = new ArrayList<String>(10);  // 下载链接
	
	public String getPreview() {
		return preview;
	}
	public void setPreview(String preview) {
		this.preview = preview;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public List<String> getDownload() {
		return download;
	}
	public void setDownload(List<String> download) {
		this.download = download;
	}
	
}
