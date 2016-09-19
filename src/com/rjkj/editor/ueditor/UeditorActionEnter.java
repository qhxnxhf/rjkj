package com.rjkj.editor.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.rjkj.editor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;

public class UeditorActionEnter  {

	private HttpServletRequest request = null;

	private String rootPath = null;
	private String contextPath = null;
	private String physicalPath = null;
	private String actionType = null;
	private String id = null;
	
	private ConfigManager configManager = null;

	private UeditorService ueditorService = null;

	public UeditorActionEnter(HttpServletRequest request, String rootPath, UeditorService ueditorService) {
		//super(request, rootPath);

		this.request = request;
		this.contextPath = request.getContextPath();
		this.rootPath=rootPath;
		//this.physicalPath = request.getServletContext().getRealPath(rootPath);
		this.physicalPath = rootPath;
		this.actionType = request.getParameter("action");
		this.id = request.getParameter("newsId");
		
		this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, this.physicalPath);
		
		this.ueditorService = ueditorService;
		ueditorService.setId(id);
	}
	
	
	public String exec() {
		String callbackName = this.request.getParameter("callback");
		
		if ( callbackName != null ) {

			if ( !validCallbackName( callbackName ) ) {
				return new BaseState( false, AppInfo.ILLEGAL ).toJSONString();
			}
			
			return callbackName+"("+this.invoke()+");";
			
		} else {
			return this.invoke();
		}
	}

	
	public String invoke() {
		Integer catchimg=ActionMap.CATCH_IMAGE;
		if (actionType == null || !ActionMap.mapping.containsKey(actionType)) {
			return new BaseState(false, AppInfo.INVALID_ACTION).toJSONString();
		}

		if (this.configManager == null || !this.configManager.valid()) {
			return new BaseState(false, AppInfo.CONFIG_ERROR).toJSONString();
		}

		State state = null;

		int actionCode = ActionMap.getType(this.actionType);

		Map<String, Object> conf = null;

		switch (actionCode) {

			case ActionMap.CONFIG:
				return this.configManager.getAllConfig().toString();
	
			case ActionMap.UPLOAD_IMAGE:
			case ActionMap.UPLOAD_SCRAWL:
			case ActionMap.UPLOAD_VIDEO:
			case ActionMap.UPLOAD_FILE:
				conf = this.configManager.getConfig(actionCode);
				state = new UeditorUploader(request, conf, this.ueditorService).doExec();
				break;
	
			case ActionMap.CATCH_IMAGE:
				conf = configManager.getConfig(actionCode);
				String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
				state = new UeditorImageHunter(conf, this.ueditorService).capture(list);
				break;
	
			case ActionMap.LIST_IMAGE:
			case ActionMap.LIST_FILE:
				conf = configManager.getConfig(actionCode);
				int start = this.getStartIndex();
				state = new UeditorFileManager(conf, this.ueditorService).listFile(start);
				break;

		}
		
		//JSONObject jsonObj = new JSONObject(state.toJSONString());
		//String url=(String)jsonObj.get("url");
		
		//url=this.contextPath+this.basePath+url;
		//state.putInfo("url", url);
		return state.toJSONString();

	}
	
	public int getStartIndex () {
		
		String start = this.request.getParameter( "start" );
		
		try {
			return Integer.parseInt( start );
		} catch ( Exception e ) {
			return 0;
		}
		
	}
	
	/**
	 * callback参数验证
	 */
	public boolean validCallbackName ( String name ) {
		
		if ( name.matches( "^[a-zA-Z_]+[\\w0-9_]*$" ) ) {
			return true;
		}
		
		return false;
		
	}
	
}
