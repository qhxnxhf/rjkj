package com.rjkj.web.cms;


import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.editor.ueditor.UeditorActionEnter;
import com.rjkj.editor.ueditor.UeditorService;



/**
 * Ueditor后台处理入口
 */
@Controller("UeditorController")
@RequestMapping("/resources/ueditor")
public class UeditorController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	
	@Resource(name="UeditorServiceImpl")
	private UeditorService ueditoreService;
	
	
	@RequestMapping(value = "/execute",method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String execute(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		String resultMsg;
		
		String rootPath = request.getServletContext().getInitParameter("rootPath");
		resultMsg = new UeditorActionEnter(request,rootPath, this.ueditoreService).exec();
		logger.error("ueditor execute ... resultMsg:" + com.rjkj.util.web.UnicodeUtil.fromUnicode(resultMsg));
		return resultMsg;
		
	}

}
