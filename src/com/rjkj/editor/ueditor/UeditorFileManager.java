package com.rjkj.editor.ueditor;

import java.util.Map;

import com.baidu.ueditor.define.State;

public class UeditorFileManager {
	
	private String[] allowFiles = null;
	private int count = 0;
	private String urlPrefix=null;
	private UeditorService ueditorService = null;
	
	public UeditorFileManager(Map<String, Object> conf, UeditorService ueditorService) {
		this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
		this.count = (Integer) conf.get("count");
		this.urlPrefix=(String)conf.get("urlPrefix");
		this.ueditorService = ueditorService;
	}
	
	public State listFile(int index) {
		return ueditorService.listFile(this.allowFiles,index, this.count,urlPrefix);
	}
	
	private String[] getAllowFiles(Object fileExt) {
		
		String[] exts = null;
		String ext = null;

		if (fileExt == null) {
			return new String[0];
		}

		exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {

			ext = exts[i];
			exts[i] = ext.replace(".", "");

		}

		return exts;

	}

}
