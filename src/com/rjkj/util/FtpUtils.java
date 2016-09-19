package com.rjkj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.rjkj.service.PropertiesService;
import com.rjkj.service.imp.PropertiesServiceImpl;





public class FtpUtils {
	
	private static Logger logger = Logger.getLogger(FtpUtils.class);
	
	/**
	 * @Title: saveUploadFile
	 * @Description: 将上传文件执行保存的方法，可以处理文件及图片，如果图片需要压缩，会自动压缩后保存
	 * @param @param ufp
	 * @param @param upload
	 * @param @param uploadFileName
	 * @param @return
	 * @return UploadFileProperties
	 * @throws
	 * @author Hawking Zhou
	 * @date 2012-2-28 下午07:27:12
	 */
	public static UploadFileProperties saveUploadFile(UploadFileProperties ufp, File upload, String uploadFileName,HttpServletRequest request) {
		ufp.setMessage("");
		ufp.setSaveSuccess(false);
		ufp.setScrSuffix(FileUtils.getFileExtension(uploadFileName.toLowerCase()));
		
		ufp.setSaveFileName(uploadFileName);
		String rootPath = System.getProperty("java.io.tmpdir");
		ufp.setSaveDir(new File(rootPath));
		logger.info("@@@ " + ufp.getSaveDir());
		if (ufp.isDefaultSavePath()) {
			StringBuffer sb = new StringBuffer(DateUtils.sdfDate11.format(new Date()));
			String temp = String.valueOf(System.nanoTime());
			sb.append(temp.substring(temp.length() - 6));
			ufp.setSaveFileName(sb.toString() + "." + ufp.getScrSuffix());
		}
		
		if (upload.length() > ufp.getMaxSize()) {
			ufp.setMessage("上传文件尺寸大于系统要求，系统可以接收不大于" + ufp.getMaxSize() + "字节的文件。");
			return ufp;
		}
		if (!StringUtil.inStringArray(ufp.getAccessFileType(), ufp.getScrSuffix())) {
			ufp.setMessage("上传文件类型违反系统规定，系统拒绝接收。");
			return ufp;
		}
		if (StringUtil.isEmpty(ufp.getSaveDir())) {
			ufp.setMessage("没有设定上传文件保存的文件夹。");
			return ufp;
		}
		if (StringUtil.isEmpty(ufp.getSaveFileName())) {
			ufp.setMessage("没有设定上传文件保存的文件名。");
			return ufp;
		}
		
		PropertiesService propertiesService = SpringApplicationContextUtils.getBean("PropertiesService", PropertiesServiceImpl.class);
		
		String path =  propertiesService.getProperty("FTP_SERVER_PATH");
		
		File file1 = new File(ufp.getSaveDir() + File.separator + ufp.getSaveFileName());
		
		boolean temp = false;
		if (StringUtil.inStringArray(propertiesService.getProperty("imageAllowedExt").split(","), ufp.getScrSuffix())) {
			temp = ImageUtils.saveUploadImage(upload, file1, ufp.getMaxLengthOfImage(), ufp.isCompressImage());
		}else{
			path = propertiesService.getProperty("FTP_SERVER_FILE_PATH");;
			try {
				org.apache.commons.io.FileUtils.copyFile(upload, file1);
				temp = true;
			} catch (IOException e) {
				temp = false;
				logger.info("@@@ copyFile error ", e);
			}
		}
		if (temp) {
			try {
				FTPClient ftp = new FTPClient();
				
				String url = propertiesService.getProperty("FTP_SERVER_URL");
				String username =  propertiesService.getProperty("FTP_SERVER_USERNAME");
				String password =  propertiesService.getProperty("FTP_SERVER_PASS");
				ftp.connect(url);
				ftp.login(username, password);
				
				int reply = ftp.getReplyCode();// 看返回的值是不是230，如果是，表示登陆成功
				if (!FTPReply.isPositiveCompletion(reply)) {// 以2开头的返回值就会为真
					ftp.disconnect();
					return ufp;
				}
				
				ufp.setSaveFullPath(path);

				ftp.setBufferSize(1024);
				ftp.enterLocalPassiveMode();
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
				ftp.changeWorkingDirectory(path);
				FileInputStream input = new FileInputStream(file1);
				ftp.storeFile(ufp.getSaveFileName(), input);// 将上传文件存储到指定目录
				input.close();
				ftp.logout();
			} catch (Exception e) {
				logger.info("@@@ upload File error ", e);
				ufp.setMessage("文件上传失败。");
				return ufp;
			}
			ufp.setSaveSuccess(true);
		} else {
			ufp.setMessage("上传的图片文件格式错误，或者是文件已损坏。");
			return ufp;
		}

		return ufp;
	}
	
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
	
	public static boolean uploadFile(String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		PropertiesService propertiesService = SpringApplicationContextUtils.getBean("PropertiesService", PropertiesServiceImpl.class);
		String url = propertiesService.getProperty("FTP_SERVER_URL");
		String username =  propertiesService.getProperty("FTP_SERVER_USERNAME");
		String password =  propertiesService.getProperty("FTP_SERVER_PASS");
		String path =  propertiesService.getProperty("FTP_SERVER_PATH")+ "/homeImg";
		try {
			int reply;
			ftp.connect(url, 21);//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);			
			
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	/**
	 * Description: 向FTP服务器上传文件
	 * @Version1.0 Jul 27, 2008 4:31:09 PM by 崔红保（cuihongbao@d-heaven.com）创建
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input 输入流
	 * @return 成功返回true，否则返回false
	 */
	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);//连接FTP服务器
			//如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);//登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);			
			
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}
	
	public static void main(String[] args) {
		
		try {
			File file = new File("E:/Workspaces/temp/01_bak.png");
			File file2 = new File("E:/Workspaces/temp2/01_bak.png");
			//FileInputStream in=new FileInputStream(file);
			
			//boolean flag = uploadFile("10.1.190.214", 21, "admin", "02191773", "/fileUploadMgnt/homeImg/", "95306.png", in);
			//System.out.println(in);System.out.println(file.length());
			
			org.apache.commons.io.FileUtils.copyFile(file, file2);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
