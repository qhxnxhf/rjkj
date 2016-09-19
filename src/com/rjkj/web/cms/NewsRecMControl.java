package com.rjkj.web.cms;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.NewsDao;
import com.rjkj.dao.NewsSendDao;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsSend;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.web.interceptor.CsrfTokenUtils;


@Controller
@RequestMapping("/cms/send")
public class NewsRecMControl {
	
	@Autowired
	private NewsDao newsService;
	
	@Autowired
	private NewsSendDao sendService;
	
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/table", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getSendTable(HttpServletRequest request){
		String pId= request.getParameter("newsId");
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		if(pId!=null&&!pId.equals("")){
			query=query+"AND o.news.id="+pId;
		}else{
			query=query+"AND o.news.id=0";
		}
		
		JSONObject json = new JSONObject();
		   
		List<NewsSend> list=this.sendService.findByNews(query,page);
		
		String[] excludes={"children","news","attach","docs","users","receiver","comment"};
		//JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd HH:mm:ss");
		JSONArray jsonArray=JSONArray.fromObject(list, config);
		
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	
	//新建
	@RequiresPermissions("NewsM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		
		String id = request.getParameter("newsid");
		String url = request.getParameter("url");	
		
		News news=this.newsService.findById(News.class, Long.valueOf(id));
		map.put("user", user);
		map.put("news", news);
		
		return url;	
			
	}
		
		
		
		
		
		
		//新建保存
		@RequiresPermissions("NewsM:save")
		@RequestMapping(value = "/saveAdd", method = RequestMethod.POST)
		public @ResponseBody String saveAdd(News doc,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response) {
			HttpSession session = request.getSession(true);  
	        User user=(User)session.getAttribute("LoginUser"); 
	        Organize org=user.getOrg();
			JSONObject json = new JSONObject();
			
			if(!CsrfTokenUtils.validCsrfToken(request)){
				json.put("status", "false");
				json.put("message", "非法操作!");
				String result=json.toString();
				return result;
			}
				
			//设置响应给前台内容的数据格式  
			//response.setContentType("text/plain; charset=UTF-8");  
	        //设置响应给前台内容的PrintWriter对象  
			//PrintWriter out;
			
			//out = response.getWriter();
				
			
			map.put("doc", doc);
				
			String result=json.toString();
			//out.print(result);  
		    //out.flush(); 
			
			return result; 
			 
		}
		
		
		
	
	

}
