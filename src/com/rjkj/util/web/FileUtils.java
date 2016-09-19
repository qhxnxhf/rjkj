package com.rjkj.util.web;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
	public static Logger log = LoggerFactory.getLogger(FileUtils.class);

	public final static long ONE_KB = 1024;
	public final static long ONE_MB = ONE_KB * 1024;
	public final static long ONE_GB = ONE_MB * 1024;
	public final static long ONE_TB = ONE_GB * 1024;
	public final static long ONE_PB = ONE_TB * 1024;
	
	public final long maxSize = 10*1024*1024;
	
	//定义允许上传的文件扩展名
	public  static HashMap<String, String> allowed = new HashMap<String, String>();
	
	static{
		allowed.put("txt","text/plain");
		allowed.put("rtf", "application/rtf");
		
		allowed.put("dot","application/x-dot");
		allowed.put("doc","application/msword");
		allowed.put("docx","application/msword");
		
		allowed.put("xls","application/vnd.ms-excel");
		allowed.put("xlsx","application/vnd.ms-excel");
		allowed.put("xlt","application/vnd.ms-excel");
		allowed.put("xlw","application/vnd.ms-excel");
		allowed.put("ppt","application/vnd.ms-powerpoint");
		allowed.put("pps","application/vnd.ms-powerpoint");
		allowed.put("pptx","application/vnd.ms-powerpoint");
		
		allowed.put("pdf","application/pdf");
		allowed.put("gif","image/gif");
		allowed.put("jpg","image/jpeg");
		allowed.put("jpe","image/jpeg");
		allowed.put("jpeg","image/jpeg");
		allowed.put("png","image/png");
		allowed.put("bmp","image/bmp");
	}
	
	
	
	//检查文件大小
	public String ifMaxSize(Long filesize){
		if(filesize > maxSize){
			return "上传文件大小超过限制:"+(maxSize/1024*1024)+"MB";
		}else{
			return "ok";
		}
	}
	
	//检查扩展名
	public String ifFileExt(String suffix){
	
		if(ifInclude(suffix)){
			return  suffix+":上传文件扩展名是不允许的";
		}else{
			return "ok";
		}
	}
	
	/**
	 * 
	 * 得到文件大小。
	 * @param fileSize
	 * @return
	 */
	public static String getHumanReadableFileSize(long fileSize) {
		if (fileSize < 0) {
			return String.valueOf(fileSize);
		}
		String result = getHumanReadableFileSize(fileSize, ONE_PB, "PB");
		if (result != null) {
			return result;
		}

		result = getHumanReadableFileSize(fileSize, ONE_TB, "TB");
		if (result != null) {
			return result;
		}
		result = getHumanReadableFileSize(fileSize, ONE_GB, "GB");
		if (result != null) {
			return result;
		}
		result = getHumanReadableFileSize(fileSize, ONE_MB, "MB");
		if (result != null) {
			return result;
		}
		result = getHumanReadableFileSize(fileSize, ONE_KB, "KB");
		if (result != null) {
			return result;
		}
		return String.valueOf(fileSize) + "B";
	}

	private static String getHumanReadableFileSize(long fileSize, long unit,
			String unitName) {
		if (fileSize == 0)
			return "0";

		if (fileSize / unit >= 1) {
			double value = fileSize / (double) unit;
			DecimalFormat df = new DecimalFormat("######.##" + unitName);
			return df.format(value);
		}
		return null;
	}
	
	public static String getFileExt(String fileName) {
		if (fileName == null)
			return "";

		String ext = "";
		int lastIndex = fileName.lastIndexOf(".");
		if (lastIndex >= 0) {
			ext = fileName.substring(lastIndex + 1).toLowerCase();
		}

		return ext;
	}

	/**
	 * 得到不包含后缀的文件名字。
	 * 
	 * @return
	 */
	public static String getRealName(String name) {
		if (name == null) {
			return "";
		}

		int endIndex = name.lastIndexOf(".");
		if (endIndex == -1) {
			return null;
		}
		return name.substring(0, endIndex);
	}
	
	
	public void addMime(String key,String value){
		if(!FileUtils.ifInclude(key))
			allowed.put(key, value);
	}
	
	public static boolean ifInclude(String key){
		if(allowed.get(key)!=null){
			return true;
		}else{
			return false;
		}
	}
	


}
