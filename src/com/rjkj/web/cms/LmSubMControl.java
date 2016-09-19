package com.rjkj.web.cms;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.NewsDao;
import com.rjkj.dao.NewsLmSubDao;
import com.rjkj.dao.NewsSendDao;
import com.rjkj.dao.OrgDao;
import com.rjkj.dao.UserDao;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.NewsLmSub;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;

@RequestMapping("/cms/lmsub")
public class LmSubMControl {
	
	@Autowired
	private OrgDao orgService;
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private NewsSendDao sendService;
	
	@Autowired
	private NewsDao newsService;
	
	@Autowired
	private NewsLmSubDao subService;
	
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/taker", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTakerTable(HttpServletRequest request){
		String lmId = request.getParameter("lmId");
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		
		if (StringUtils.isNotBlank(lmId)){
			query=query +" AND o.lm.id ="+lmId;
		}
		
		query=query+" ORDER BY o."+sort+" "+direction;
		
		JSONObject json = new JSONObject();
		   
		List<NewsLmSub> list=this.subService.findByLm(query, page);
		
		String[] excludes={"children","news","attach","docs","users","receiver","comment"};
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
	
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/userlm", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getUserLm(HttpServletRequest request){
		String userId = request.getParameter("userId");
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		
		if (StringUtils.isNotBlank(userId)){
			query=query +" AND o.user.id ="+userId;
		}
		
		query=query+" ORDER BY o."+sort+" "+direction;
		
		JSONObject json = new JSONObject();
		   
		List<NewsLm> list=this.subService.findByUserId(userId,"");
		
		String[] excludes={"children","news","attach","docs","users","receiver","comment"};
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
	
	//修改
		@RequiresPermissions("NewsM:edit")
		@RequestMapping(value="/preEdit", method={RequestMethod.GET})
		public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
			HttpSession session = request.getSession(true);  
	        User user=(User)session.getAttribute("LoginUser"); 
	        Organize org=user.getOrg();
			
			String newsId = request.getParameter("newsId");
			
			List<User> list;
			String url = request.getParameter("url");
			String selectedValue="";
			if(StringUtils.isNotBlank(newsId)){
				
				list=this.sendService.findReceiverByNews(Long.valueOf(newsId), "");
			
			
			
			if(list!=null&&list.size()>0){
				for(User user1:list){
					selectedValue=selectedValue+","+user1.getId();
				}
				selectedValue=selectedValue.substring(1);
			}
			}
			
			map.put("newsId", newsId);
			map.put("org", org);
			map.put("selectedValue", selectedValue);
			return url;
			
		}
	
	
	
	
	@RequiresPermissions("UserM:view")
	@RequestMapping(value="/save", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String saveUsers(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);  
        User sender=(User)session.getAttribute("LoginUser"); 
        Organize org=sender.getOrg();
        
		String newsId = request.getParameter("newsId");
		String relValue= request.getParameter("relValue");
			
			JSONObject json = new JSONObject();
			
			
			if (StringUtils.isNotBlank(newsId)&&StringUtils.isNotBlank(relValue)) {
				
				News news=this.newsService.findById(News.class,	Long.valueOf(newsId));
				this.sendService.saveNewsSend(news, sender, relValue);
				
				json.put("status", "true");
				json.put("message", "保存成功！");
				
		    }else{
		    	json.put("status", "false");
				json.put("message", "请选择用户！");
		    	
		    }
			
			String result=json.toString();
			return result;
	}
	
	
}
