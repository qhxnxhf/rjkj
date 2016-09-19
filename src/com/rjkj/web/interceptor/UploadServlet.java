package com.rjkj.web.interceptor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.rjkj.util.FtpUtils;
import com.rjkj.util.UploadFileProperties;

public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1953684640417882538L;

	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		String tempPath = System.getProperty("java.io.tmpdir");

		UploadFileProperties ufp = new UploadFileProperties();
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
		} catch (FileUploadException ex) {
			map.put("msg", "上传失败");
			map.put("success", false);
			logger.error("parse file error.", ex);
		}

		if (fileList == null) {
			map.put("msg", "上传失败");
			map.put("success", false);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(map));
			out.close();
			return;
		}

		Iterator<FileItem> it = fileList.iterator();
		String name = "";
		while (it.hasNext()) {
			FileItem item = it.next();
			if (!item.isFormField()) {
				name = item.getName();
				if (name == null || name.trim().equals("")) {
					continue;
				}

				name = name.split("\\\\")[name.split("\\\\").length - 1];
				//name = StringUtils.substringAfterLast(name, "\\");
				logger.info("tempPath:" + tempPath + "||name:" + name);
				String fileName = tempPath + File.separator + name;
				File saveFile = new File(fileName);

				String[] nameArry = name.split("\\.");
				String suffix = StringUtils.substringAfterLast(name, ".");
				name = nameArry[0] + new Date().getTime() + "." + suffix;
				ufp.setScrSuffix(suffix);
				ufp.setSaveFileName(name);
				ufp.setSaveDir(saveFile);
				ufp.setCompressImage(true);
				try {
					item.write(saveFile);
					FtpUtils.saveUploadFile(ufp, saveFile, name, request);
					saveFile.delete();
					map.put("path", ufp.getSaveFullPath() + "/" + ufp.getSaveFileName());
					map.put("success", ufp.isSaveSuccess());
					map.put("msg", ufp.getMessage());
				} catch (Exception e) {
					logger.error("upload file error.", e);
					map.put("success", false);
					map.put("msg", "上传失败");
				}
			} else {
				map.put("msg", "上传失败");
				map.put("success", false);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(new Gson().toJson(map));
		out.close();
	}

}
