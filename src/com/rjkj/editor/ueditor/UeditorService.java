package com.rjkj.editor.ueditor;

import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

















import com.rjkj.editor.MultipartFile;
import com.baidu.ueditor.define.State;

public interface UeditorService {

	/**
	 * 获取上传的文件
	 * 
	 * @param filedName
	 *            参数名
	 * @param request
	 * @return
	 */
	public MultipartFile getMultipartFile(String filedName, HttpServletRequest request,String savePath);

	/**
	 * 存储文件
	 * 
	 * @param multipartFile
	 * @param maxSize
	 * @return
	 */
	public State saveFileByInputStream(MultipartFile multipartFile, long maxSize,String physicalPath, String savePath,String urlPrefix);
	
	public State saveFileByInputStream(InputStream multipartFile, long maxSize,String savePath);

	/**
	 * 存储文件
	 * 
	 * @param data
	 * @param fileName
	 * @return
	 */
	public State saveBinaryFile(byte[] data, String fileName,String physicalPath,String savePath,String urlPrefix);

	/**
	 * 获取文件列表
	 * 
	 * @param allowFiles
	 *            允许显示的文件
	 * @param start
	 *            起始位置
	 * @param pageSize
	 *            每页显示条数
	 * @return
	 */
	public State listFile(String[] allowFiles, int start, int pageSize,String basePaht);

	public void setId(String id);
	
}
