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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.NewsDao;
import com.rjkj.dao.NewsSendDao;
import com.rjkj.dao.OrgDao;
import com.rjkj.dao.UserDao;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsSend;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.ListerNode;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;


@Controller
@RequestMapping("/cms/send")
public class SendMControl {
	
	@Autowired
	private OrgDao orgService;
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private NewsSendDao sendService;
	
	@Autowired
	private NewsDao newsService;
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      
	      Organize org=this.orgService.findById(Organize.class,1L);
	      
	      node1.setId(org.getId()+"");
	      node1.setName(org.getOrgName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	     // node1.setOldParentId(null);
	      node1.setDesc(org.getOrgBrief());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<Organize> list=this.orgService.findByParentId(Long.valueOf(id), " AND o.status='y'  ORDER BY o.orderNum ASC");
	    	if(list!=null&&list.size()>0){
	    		for (int i = 0; i <list.size(); i++) {
	    			Organize org=list.get(i);
	    			
	    			TreeNode node1 = new TreeNode();
	    			node1.setId(org.getId()+"");
	    		    node1.setName(org.getOrgName());
	    		    node1.setMenuType(org.getNodeType());
	    		    if(org.getNodeType().equals("y")){
	    				 node1.setIsParent(Boolean.valueOf(false));
	    			}else{
	    				 node1.setIsParent(Boolean.valueOf(true));
	    			}
	    		   // node1.setOldParentId(null);
	    		    //node1.setDesc(org.getDescription());
	    		    //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	    		    jsonArray.add(node1);
	    		}
	    	}
	    } 
		
		String json=jsonArray.toString();
		return json;
	}
	
	
	
	@RequestMapping(value="/taker", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTakerTable(HttpServletRequest request){
		String newsId = request.getParameter("newsId");
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		
		if (StringUtils.isNotBlank(newsId)){
			query=query +" AND o.news.id ="+newsId;
		}
		
		query=query+" ORDER BY o."+sort+" "+direction;
		
		JSONObject json = new JSONObject();
		   
		List<NewsSend> list=this.sendService.findByNews(query, page);
		
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
	
	//用户信息表格
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/userlist", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
			String deptId = request.getParameter("parentId");
			String fs = request.getParameter("fs");
			String userName= request.getParameter("userName");
			
			String newsId = request.getParameter("newsId");
			
			JSONObject json = new JSONObject();
			List<User> list;
			String query=" WHERE 1=1 ";
			if (StringUtils.isNotBlank(deptId)) {
				if(fs!=null&&fs.equals("1")){
					String orgs=this.orgService.getOrgIdsByCase(Long.valueOf(deptId));
					query=query+"AND o.org.id IN ("+orgs+") ";
				}else{
					query=query+"AND o.org.id="+deptId;
				}
			}
			
			if (StringUtils.isNotBlank(userName)) {
				query=query+"AND o.userName LIKE '%"+userName+"%' ";
		    }
			
			
			list=this.userService.findAll(User.class,query);
			
			JSONArray fromList = new JSONArray();    
			
			if(list!=null&&list.size()>0){
				for(User user:list){
					ListerNode node=new ListerNode();
					node.setKey(user.getOrg().getOrgName()+"_"+user.getUserName());
					node.setValue(user.getId().toString());
					fromList.add(node);
				}
			}
			
			JSONArray toList = new JSONArray();    
			
			/**
			list=this.sendService.findReceiverByNews(Long.valueOf(newsId), "");
			
			if(list!=null&&list.size()>0){
				for(User user1:list){
					ListerNode node=new ListerNode();
					node.setKey(user1.getOrg().getOrgName()+"_"+user1.getUserName());
					node.setValue(user1.getId().toString());
					toList.add(node);
				}
			}
			**/
			json.put("fromList", fromList);
			json.put("toList", toList);
			
			
			String result=json.toString();
			return result;
	}
	
	
	@RequiresPermissions("NewsM:view")
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
