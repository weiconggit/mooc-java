package org.carryon.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 文件相关工具
 * @author carryon
 * @date 2019年12月2日
 * @version 1.0
 */
public abstract class FileUtil {

	/**
	 * NIO方式读取文件内容
	 * @return
	 */
	public static List<String> readFileByNIO(String path){
		if (StringUtil.isBlank(path)) return new ArrayList<>(); 

		List<String> list = new ArrayList<>();
		try {
			list = Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 传统方式读取文件内容
	 * @return
	 * @throws IOException 
	 */
	public static List<String> readFile(String path) throws IOException{
		if (StringUtil.isBlank(path)) return new ArrayList<>(); 
			
		List<String> list = new ArrayList<>();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(path), "UTF-8"));
		String line;
		while ((line = bufferedReader.readLine()) != null) {	
			list.add(line);
		}
		bufferedReader.close();
		return list;
	}
}
