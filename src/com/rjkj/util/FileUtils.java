/**
 * FileUtils.java
 */
package com.rjkj.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author Hawking Zhou
 *
 */
public class FileUtils {
	/**
	 * 计算文件的MD5
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 计算文件的MD5
	 * 
	 * @param fileName
	 *            文件的绝对路径
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getFileMD5(String fileName) throws IOException,
			NoSuchAlgorithmException {
		File f = new File(fileName);
		return getFileMD5(f);
	}

	/**
	 * 计算文件的MD5，重载方法
	 * 
	 * @param file
	 *            文件对象
	 * @return
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static String getFileMD5(File file) throws IOException,
			NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		FileInputStream in = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 1024];
		int len = 0;
		while ((len = in.read(buffer)) > 0) {
			messageDigest.update(buffer, 0, len);
		}
		in.close();
		return bufferToHex(messageDigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	/**
	* @Title: getStringFromFile
	* @Description: 从文件数组中，获得文件内容字符串
	* @param @param f
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	*/ 
	public static String getStringFromFile(File[] f){
    	StringBuffer sb=new StringBuffer();
    	for(int i=0;i<f.length;i++){
    		sb.append(getStringFromFile(f[i])+"\r\n");
    	}
    	return sb.toString();
    }
    /**
    * @Title: getStringFromFile
    * @Description: 从单个文件中，获得文件内容字符串
    * @param @param f
    * @param @return    设定文件
    * @return String    返回类型
    * @throws
    */ 
    public static String getStringFromFile(File f){
    	try {
			StringBuffer sb=new StringBuffer();
			BufferedReader br = new BufferedReader(new FileReader(f));
			String temp = null;
			while ((temp = br.readLine()) != null) {
				sb.append(temp+"\r\n");
			}
			return sb.toString();
		} catch (Exception e) {
            
			e.printStackTrace();
			return null;
		}
    }
    /**
    * @Title: writeStringToFile
    * @Description: 将字符串写入指定文件
    * @param @param f
    * @param @param str
    * @param @return    设定文件
    * @return boolean    返回类型
    * @throws
    */ 
    public static boolean writeStringToFile(File f,String str){
			try {
				f.getParentFile().mkdirs();
				f.createNewFile();
				FileOutputStream fos=new FileOutputStream(f,true);
				fos.write(str.getBytes("utf-8"));
				fos.close();
				return true;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		
    }
    /**
    * @Title: writeStringToFile
    * @Description: 将字符串写入指定文件,isAppend表示从文件开头开始最后位置开始写
    * @param @param f
    * @param @param str
    * @param @param isAppend
    * @param @return    设定文件
    * @return boolean    返回类型
    * @throws
    * @author KFS1
    * @date 2012-3-21 下午02:23:25
    */ 
    public static boolean writeStringToFile(File f,String str,boolean isAppend){
		try {
			f.getParentFile().mkdirs();
			f.createNewFile();
			FileOutputStream fos=new FileOutputStream(f,isAppend);
			fos.write(str.getBytes("utf-8"));
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	
   }
    /**
    * @Title: getFilesName
    * @Description: 传入目录及需要过滤文件名的正则表达式，返回符合条件的文件列表
    * @param @param dir
    * @param @param strFilter
    * @param @return    
    * @return String[]   
    * @throws 
    * @author Hawking Zhou
    * @date 2012-1-18 下午04:02:42
    */ 
    /*public static File[] getFilesName(File dir,String strFilter){
    	//如果不是目录，返回null
    	if(!dir.isDirectory())
    		return null;
    	Filter filter = new Filter(strFilter);
		return dir.listFiles(filter);
    }
    class Filter implements FilenameFilter{
		String filterString;
		public Filter(String fString){
			this.filterString = fString;
		}
		public boolean accept(File dir, String name) {
			return name.matches(filterString);
		}	
	}*/
    
	/**
	* @Title: getFileExtension
	* @Description:  返回文件扩展名
	* @param @param fileName
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	* @author KFS1
	* @date 2012-2-26 下午05:48:23
	*/ 
	public static String getFileExtension(final String fileName) {
		if (fileName == null
				|| fileName.lastIndexOf(".") == -1
				|| fileName.lastIndexOf(".") == fileName.length() - 1) {
			return null;
		}
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	
	/**
	* @Title: getFileNameWithoutExtension
	* @Description: 返回文件名，不带扩展名部分
	* @param @param fileName
	* @param @return    设定文件
	* @return String    返回类型
	* @throws
	* @author KFS1
	* @date 2012-2-26 下午05:49:10
	*/ 
	public static String getFileNameWithoutExtension(final String fileName) {
		if (fileName == null || fileName.lastIndexOf(".") == -1) 
			return null;
		return fileName.substring(0, fileName.lastIndexOf("."));
	}
	public static void main(String[] args){
		String str="123.txt";
		System.out.println(getFileExtension(str));
		System.out.println(getFileNameWithoutExtension(str));
	}
}
