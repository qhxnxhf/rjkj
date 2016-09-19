package com.rjkj.editor.ueditor;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;
import com.rjkj.editor.PathFormat;

public class UeditorBase64Uploader {

	public static State save(String content, Map<String, Object> conf, UeditorService ueditorService) {

		byte[] data = decode(content);

		long maxSize = ((Long) conf.get("maxSize")).longValue();

		if (!validSize(data, maxSize)) {
			return new BaseState(false, AppInfo.MAX_SIZE);
		}
		
		String suffix = FileType.getSuffix("JPG");

		String savePath = PathFormat.parse((String) conf.get("savePath"),
				(String) conf.get("filename"));
		
		savePath = savePath + suffix;
		String physicalPath = conf.get("physicalPath") + savePath;
		String  urlPrefix=(String)conf.get("urlPrefix");
		
		State storageState = ueditorService.saveBinaryFile(data, conf.get("filename")+ suffix,physicalPath,savePath,urlPrefix);
		
		if (storageState.isSuccess()) {
			
			//storageState.putInfo("url", (String)conf.get("contextPath")+conf.get("rootPath")+savePath);
			storageState.putInfo("type", suffix);
			storageState.putInfo("original", conf.get("filename")+ suffix);
		}

		return storageState;
	}

	private static byte[] decode(String content) {
		return Base64.decodeBase64(content);
	}

	private static boolean validSize(byte[] data, long length) {
		return data.length <= length;
	}

}
