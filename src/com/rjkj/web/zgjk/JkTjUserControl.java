package com.rjkj.web.zgjk;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.rjkj.dao.JkTjUserDao;
import com.rjkj.model.TjUser;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.web.interceptor.CsrfTokenUtils;


@Controller
@RequestMapping("/zgjk/tjuser")
public class JkTjUserControl {
	
	private static final Log log = LogFactory.getLog(JkTjUserControl.class);  
	//protected ErrMg error; 
	 
	
	
	@Autowired
	private JkTjUserDao userService;
	
	@RequestMapping(value="/openUrl", method={RequestMethod.GET})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		
		String id = request.getParameter("userid");
		String url = request.getParameter("url");
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		TjUser user=this.userService.findById(id);
		
		map.put("user", user);
		return url;	
		
	}
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String id = request.getParameter("tjbm");
		String name = request.getParameter("uname");
		String cId = request.getParameter("carId");
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		//String deptName= request.getParameter("deptName");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		
		if(id!=null&&!id.equals("")){
			query=query+" AND xx_17="+id;
		}
		if(name!=null&&!name.equals("")){
			query=query+" AND xx_1 LIKE '%"+name+"%'";
		}
		
		if(cId!=null&&!cId.equals("")){
			query=query+" AND xx_5 ='"+cId+"'";
		}
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray;
		List<TjUser> list;
		
		
		list=this.userService.findAll(query, page);
		String[] excludes={"children","users","news","docs"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		jsonArray=JSONArray.fromObject(list, config);
		
		
		
		
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}

}
