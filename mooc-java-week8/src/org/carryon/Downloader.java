package org.carryon;

import java.net.URL;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.io.*;
/**
 * @description Mooc Week8 多线程
 * @author carryon
 * @date 2019年11月6日
 * @version 1.0
 */
public class Downloader {
	
	// @formatter:off
    /**
     * jdk:1.8以上
     * encode：UTF-8
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args)
        throws Exception
    {
        final URL[] urls = {
            new URL("https://www.pku.edu.cn"),
            new URL("https://www.baidu.com"),
            new URL("https://www.sina.com.cn"),
            new URL("http://www.dstang.com") // 原https无法访问，改为http
        };
        final String[] files = {
            "pku.htm", 
            "baidu.htm",
            "sina.htm",
            "study.htm",
        };
 
        // 多线程下载
        CountDownLatch countDownLatch = new CountDownLatch(urls.length);
        long startTime = new Date().getTime();
        for(int idx=0; idx<urls.length; idx++){
        	final int i = idx;
        	new Thread(() -> {
            	try{
            		System.out.println( urls[i] );
            		download( urls[i], files[i]);
            		countDownLatch.countDown();
            	}catch(Exception ex){
            		ex.printStackTrace();
            	}
            }).start();
        }
        // 等待所有子线程结束
        countDownLatch.await();
        // 计算耗时
		long endTime = new Date().getTime();
        long spendTime = endTime - startTime;
        System.out.println("全部文件下载完毕，用时" + spendTime + "毫秒");
    }
    
    static void download( URL url, String file)
        throws IOException
    {
        try(InputStream input = url.openStream();
            OutputStream output = new FileOutputStream(file))
        {
            byte[] data = new byte[1024];
            int length;
            while((length=input.read(data))!=-1){
                output.write(data,0,length);
            }
        }
    }
    // @formatter:on
}
