package com.rjkj.editor.ueditor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import com.rjkj.editor.MultipartFile;
import com.rjkj.editor.PathFormat;
import com.rjkj.editor.StandardMultipartFile;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.FileType;
import com.baidu.ueditor.define.State;

public class UeditorBinaryUploader {
	
	public static final State save(HttpServletRequest request, Map<String, Object> conf, UeditorService ueditorService) {
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}
		String filedName = (String) conf.get("fieldName");
		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
		
		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}
		
		FileItemStream fileStream = null;
		try {
			//fileList = upload.parseRequest(request);
			//Iterator<FileItem> it = fileList.iterator();
		
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				fileStream = iterator.next();

				if (!fileStream.isFormField())
					break;
				fileStream = null;
			}

			if (fileStream == null) {
				return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
			}
			
			String savePath = (String) conf.get("savePath");
			String originFileName = fileStream.getName();
			String suffix = FileType.getSuffixByFilename(originFileName);
			
			String name = originFileName.split("\\\\")[originFileName.split("\\\\").length - 1];
			
			if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
				return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
			}
			
			

			originFileName = originFileName.substring(0,
					originFileName.length() - suffix.length());
			savePath = savePath + suffix;
			
			long maxSize = ((Long) conf.get("maxSize")).longValue();
			InputStream is = fileStream.openStream();
			MultipartFile mtf = new StandardMultipartFile(filedName, is, name, is.available());
			
			savePath = PathFormat.parse(savePath, originFileName);

			String physicalPath = conf.get("physicalPath") + savePath;
			String  urlPrefix=(String)conf.get("urlPrefix");
			State storageState = ueditorService.saveFileByInputStream(mtf, maxSize,physicalPath,savePath,urlPrefix);
			is.close();
			if (storageState.isSuccess()) {
				//storageState.putInfo("url", (String)conf.get("contextPath")+conf.get("rootPath")+savePath);
				storageState.putInfo("type", suffix);
				storageState.putInfo("original", originFileName);
			}

			return storageState;			
		
		} catch (FileUploadException e) {
			return new BaseState(false, AppInfo.PARSE_REQUEST_ERROR);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);
		return list.contains(type);
	}

}
