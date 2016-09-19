package com.rjkj.util;

import java.io.File;
import com.rjkj.service.PropertiesService;
import com.rjkj.service.imp.PropertiesServiceImpl;



public class UploadFileProperties {

	private String saveHeadDir = null;
	private boolean saveSuccess = false;// 保存是否成功，用于调用saveUploadFile方法返回说明
	private String message = "";// 如果出错，错误信息，用于调用saveUploadFile方法返回说明
	private String srcFileName = null;// 上传文件在客户端的名字，不含后缀
	private String scrSuffix = null;// 上传文件在客户端的后缀名，一律改为小写如doc
	private long maxSize;// 上传文件最大大小，默认来自配置文件application_upload.properties
	private String[] accessFileType ;// 可接受上传文件的扩展名,默认来自配置文件application_upload.properties
	private boolean defaultSavePath = true;// 是否使用缺省的存储路径及文件名

	private boolean compressImage = true;// 图片文件是否压缩
	private int maxLengthOfImage = 800;// 如果压缩图片文件，图片的最大边长像素
	private File saveDir = null;// 文件存储目录，如果DefaultSavePath为true，放到默认目录
	private String saveFileName = null;// 文件名，如果不包括扩展名和.
										// 如果DefaultSavePath为true，采用默认扩展名
	private String attachFileUuid; // 附件资源表组件ID,uuid可以唯一识别附件资源
	private String busiType = "1"; // 附件资源的业务类型；比如：通知公告是0;投诉建议是：1；等
//	private final String saveHeadDir = request.getSession().getServletContext().getRealPath("/") + "WEB-INF"+File.separator+"uploadfiles" + File.separator; // 服务存放附件地址的前面固定路径部分
	private String saveFullPath; // 附件的完整路径。根据路径可以直接通过img查看图片
	
	
	public UploadFileProperties() {
		PropertiesService propertiesService = SpringApplicationContextUtils.getBean("PropertiesService", PropertiesServiceImpl.class);
		maxSize = Long.valueOf(propertiesService.getProperty("uploadFileMaxSize"));// 上传文件最大大小，默认来自配置文件application_upload.properties
		accessFileType = StringUtil.getMergeArray(propertiesService.getProperty("fileAllowedExt").split(","),
				propertiesService.getProperty("imageAllowedExt").split(","));// 可接受上传文件的扩展名,默认来自配置文件application_upload.properties
	}


	public boolean isSaveSuccess() {
		return saveSuccess;
	}

	public void setSaveSuccess(boolean saveSuccess) {
		this.saveSuccess = saveSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSrcFileName() {
		return srcFileName;
	}

	public void setSrcFileName(String srcFileName) {
		this.srcFileName = srcFileName;
	}

	public String getScrSuffix() {
		return scrSuffix;
	}

	public void setScrSuffix(String scrSuffix) {
		this.scrSuffix = scrSuffix;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public String[] getAccessFileType() {
		return accessFileType;
	}

	public void setAccessFileType(String[] accessFileType) {
		this.accessFileType = accessFileType;
	}

	public boolean isDefaultSavePath() {
		return defaultSavePath;
	}

	public void setDefaultSavePath(boolean defaultSavePath) {
		this.defaultSavePath = defaultSavePath;
	}

	public boolean isCompressImage() {
		return compressImage;
	}

	public void setCompressImage(boolean compressImage) {
		this.compressImage = compressImage;
	}

	public int getMaxLengthOfImage() {
		return maxLengthOfImage;
	}

	public void setMaxLengthOfImage(int maxLengthOfImage) {
		this.maxLengthOfImage = maxLengthOfImage;
	}

	public File getSaveDir() {
		return saveDir;
	}

	public void setSaveDir(File saveDir) {
		this.saveDir = saveDir;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}

	public String getAttachFileUuid() {
		return attachFileUuid;
	}

	public void setAttachFileUuid(String attachFileUuid) {
		this.attachFileUuid = attachFileUuid;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getSaveFullPath() {
		return saveFullPath;
	}

	public void setSaveFullPath(String saveFullPath) {
		this.saveFullPath = saveFullPath;
	}


	public String getSaveHeadDir() {
		return saveHeadDir;
	}

	public void setSaveHeadDir(String saveHeadDir) {
		this.saveHeadDir = saveHeadDir;
	}
	
}
