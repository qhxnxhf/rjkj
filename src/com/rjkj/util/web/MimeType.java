package com.rjkj.util.web;

import java.util.HashMap;

public class MimeType {
	
	public static MimeType mime;
	
	
	HashMap<String, String> extMap = new HashMap<String, String>();
	
	//定义允许上传的文件扩展名
	HashMap<String, String> allowed = new HashMap<String, String>();
	
	public MimeType(){
		
		extMap.put("htm","text/html");
		extMap.put("html","text/html");
		extMap.put("jsp","text/html");
		extMap.put("txt","text/plain");
		extMap.put("xml","text/xml");
		extMap.put("json","text/json");
		
		extMap.put("gif","image/gif");
		extMap.put("jpg","image/jpeg");
		extMap.put("jpe","image/jpeg");
		extMap.put("jpeg","image/jpeg");
		extMap.put("png","image/png");
		extMap.put("bmp","image/bmp");
		
		extMap.put("doc","application/msword");
		extMap.put("dot","application/x-dot");
		
		extMap.put("xls","application/vnd.ms-excel");
		extMap.put("xlt","application/vnd.ms-excel");
		extMap.put("xlw","application/vnd.ms-excel");
		extMap.put("ppt","application/vnd.ms-powerpoint");
		extMap.put("pps","application/vnd.ms-powerpoint");
		
		extMap.put("pdf","application/pdf");
		
		extMap.put("bin","pplication/octet-stream");
		extMap.put("exe","pplication/octet-stream");
		extMap.put("so","pplication/octet-stream");
		extMap.put("dll","pplication/octet-stream");
		extMap.put("class","pplication/octet-stream");
		
		        
		extMap.put("asf", "video/x-ms-asf");
		extMap.put("asp ", "application/x-asap");
		extMap.put("asx", "video/x-ms-asf");
		extMap.put("au", "audio/basic");
		extMap.put("avi", "video/x-msvideo");
		extMap.put("mov", "video/quicktime");
		extMap.put("movie", "video/x-sgi-movie");
		extMap.put("mp2", "audio/x-mpeg");
		extMap.put("mp3", "audio/x-mpeg");
		extMap.put("mp4", "video/mp4");
		extMap.put("mpe", " video/mpeg");
		extMap.put("mpeg", "video/mpeg");
		extMap.put("mpg", "video/mpeg");
		extMap.put("mpg4", "video/mp4");
		extMap.put("swf", "application/x-shockwave-flash");
		extMap.put("swfl", "application/x-shockwave-flash");
		extMap.put("wav", "audio/x-wav");
		extMap.put("wax", "audio/x-ms-wax");
		extMap.put("wma", "audio/x-ms-wma");
		extMap.put("mid", "audio/midi");
		extMap.put("midi", "audio/midi");
		extMap.put("ra", "audio/x-pn-realaudio");
		extMap.put("ram", "audio/x-pn-realaudio");
		extMap.put("rar", "application/octet-stream");
		extMap.put("rtf", "application/rtf");
		extMap.put("rm", "audio/x-pn-realaudio");
		extMap.put("rmm", "audio/x-pn-realaudio");
		extMap.put("rmvb", "audio/x-pn-realaudio");
		extMap.put("rnx", "application/vnd.rn-realplayer");
		
		extMap.put("zip","application/zip");
		extMap.put("gz","application/x-gzip");
		
		
		
		//allowed.put("htm","text/html");
		//allowed.put("html","text/html");
		
		
		//allowed.put("xml","text/xml");
		//allowed.put("json","text/json");
		
		//allowed.put("gif","image/gif");
		//allowed.put("jpg","image/jpeg");
		//allowed.put("jpe","image/jpeg");
		//allowed.put("jpeg","image/jpeg");
		//allowed.put("png","image/png");
		//allowed.put("bmp","image/bmp");
		
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
		
		
		//allowed.put("rar", "application/octet-stream");
		//allowed.put("zip","application/x-zip-compressed");
		
		//allowed.put("swf", "application/x-shockwave-flash");
		//allowed.put("swfl", "application/x-shockwave-flash");
		
		
		//allowed.put("mp3", "audio/x-mpeg");
		//allowed.put("wav", "audio/x-wav");
		//allowed.put("mid", "audio/midi");
		//allowed.put("midi", "audio/midi");
		
		//allowed.put("avi", "video/x-msvideo");
		//allowed.put("mov", "video/quicktime");
		//allowed.put("movie", "video/x-sgi-movie");
		//allowed.put("mpe", " video/mpeg");
		//allowed.put("mpeg", "video/mpeg");
		//allowed.put("mpg", "video/mpeg");
		//allowed.put("mp4", "video/mp4");
		//allowed.put("mpg4", "video/mp4");
		
		
	}
	
	public static MimeType getMimeType(){
		if(mime==null){
			mime = new MimeType();
		}
		return mime;
	}
	
	public void addMime(String key,String value){
		if(!this.ifInclude(key))
			allowed.put(key, value);
	}
	
	public boolean ifInclude(String key){
		if(allowed.get(key)!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public String getMime(String key){
		return extMap.get(key);
	}
	

}
