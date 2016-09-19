package com.rjkj.web.cms;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.rjkj.dao.DicDao;
import com.rjkj.dao.LmDao;
import com.rjkj.dao.NewsAttachDao;
import com.rjkj.dao.NewsDao;
import com.rjkj.entities.Dic;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.service.PropertiesService;
import com.rjkj.util.StringUtil;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;
import com.rjkj.web.interceptor.CsrfTokenUtils;




@Controller
@RequestMapping("/cms/news")
public class NewsMControl {
	private static final Log log = LogFactory.getLog(NewsMControl.class);  
	//protected ErrMg error; 
	@Resource(name = "PropertiesService")
	private PropertiesService propertiesService; 
	
	@Autowired
	private LmDao lmService;
	
	@Autowired
	private NewsDao newsService;
	
	@Autowired
	private NewsAttachDao attService;
	
	@Resource(name = "DicDao")
	private DicDao DicService;
	
	private static final String MSG = "gz/MsgPage";
	
	private static final String NEWSDISPLAY="cms/news/NewsDisplay";
	
	//根据文件操作授权生成规章分类单选树
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/sortTree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getSortTree(@RequestParam("parentId") String parentId, HttpServletRequest request){
			HttpSession session = request.getSession(true);  
	        User user=(User)session.getAttribute("LoginUser"); 
	        Organize org=user.getOrg();
			List<NewsLm> modlist=this.lmService.findAll(NewsLm.class," ORDER BY o.orderNum,o.id DESC ");
			LinkedList<NewsLm> modlinked=new LinkedList<NewsLm>(modlist); 
			NewsLm root=this.lmService.findById(NewsLm.class,Long.valueOf(parentId));
			JSONArray jsonArray = new JSONArray();  
			createTree(org,root, modlinked,jsonArray);
			JSONObject json = new JSONObject();
			json.put("treeNodes", jsonArray);
			String result=json.toString();
			return result;
	}
	
	public void createTree(Organize org,NewsLm root,LinkedList<NewsLm> modlist,JSONArray jsonArray){			 
		if(this.include(root.getOrgIdsWj(), org.getId())||!root.getNodeType().equals("y")){
					TreeNode node1 = new TreeNode();
					 node1.setId(root.getId()+"");
					 //node1.setPhone(root.getKey1()+"");
				     node1.setName(root.getLmName());
				     
				     node1.setMenuType(root.getNodeType());
				     NewsLm pmod=root.getParent();
				     if(pmod!=null){
				    	 node1.setParentId(pmod.getId()+"");
				     }else{
				    	 node1.setParentId("0");
				     }
				     
				     if(root.getNodeType().equals("y")){
						 node1.setIsParent(Boolean.valueOf(false));
					 }else{
						 node1.setClickExpand(true);//使非子节点不能被选中
						 node1.setIsParent(Boolean.valueOf(true));
						 if(root.getNodeType().equals("r")){
							 node1.setOpen(true);
						 }else{
							 
						 }
					 }
				     
				     //node1.setChecked(this.ifModule(rmlist, root));
				     jsonArray.add(node1);
				     List<NewsLm> children=getChildren(root,modlist);
				     if(children!=null&&children.size()>0){
				    	 for(int i=0;i<children.size();i++){
				    		 NewsLm mod=children.get(i);
				    		 createTree(org,mod,modlist,jsonArray);
				    	 }
				     }else{
				    	 return ;
				     }
			}
		}
		
		private List<NewsLm> getChildren(NewsLm root, LinkedList<NewsLm> modlist) {
			Long pid=root.getId();
			List<NewsLm> childre=new ArrayList<NewsLm>();
			ListIterator<NewsLm> listIter=modlist.listIterator();
			while(listIter.hasNext()){
				NewsLm mod=listIter.next();
				if(mod.getId()-pid==0){
					listIter.remove();
				}
				if(mod.getParent()!=null&&mod.getParent().getId()!=null&&mod.getParent().getId()-pid==0){
					childre.add(mod);
					listIter.remove();
				}
			}
			return childre;
	}
	
	//根据操作授权生成异步加载目录树
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      
	      NewsLm sort=this.lmService.findById(NewsLm.class,1L);
	      
	      node1.setId(sort.getId()+"");
	      node1.setName(sort.getLmName()+"["+sort.getId() +"]");
	      node1.setMenuType(sort.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	      node1.setOpen(true);
	     // node1.setOldParentId(null);
	      node1.setDesc(sort.getLmBrief());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<NewsLm> list=this.lmService.findByParentId(Long.valueOf(id), " AND o.status='y' ORDER BY o.orderNum ASC");
	    	if(list!=null&&list.size()>0){
	    		for (int i = 0; i <list.size(); i++) {
	    			NewsLm sort=list.get(i);
	    			//根据授权生成目录树
	    			if(this.include(sort.getOrgIdsWj(), org.getId())||!sort.getNodeType().equals("y")){
		    			TreeNode node1 = new TreeNode();
		    			node1.setId(sort.getId()+"");
		    		    node1.setName(sort.getLmName()+"["+sort.getId() +"]");
		    		    node1.setMenuType(sort.getNodeType());
		    		    if(sort.getNodeType().equals("y")){
		    				 node1.setIsParent(Boolean.valueOf(false));
		    			}else{
		    				 node1.setIsParent(Boolean.valueOf(true));
		    				 //node1.setOpen(Boolean.valueOf(sort.getOpen()));
		    			}
		    		   // node1.setOldParentId(null);
		    		    //node1.setDesc(org.getDescription());
		    		    //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
		    		    jsonArray.add(node1);
	    			}
	    		}
	    	}
	    } 
		
		String json=jsonArray.toString();
		return json;
	}
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/table", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getNewsTable(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser");
        //Organize org=user.getOrg();
		
		String pId= request.getParameter("parentId");
		String fs = request.getParameter("fs");
		String keyword = request.getParameter("keyword");
		String status= request.getParameter("status");
		String allowed = request.getParameter("allowed");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 AND o.user.id="+user.getId()+" ";
		
		if(pId!=null&&!pId.equals("")){
			if(fs!=null&&fs.equals("1")){
				String orgs=this.lmService.getLmIdsByCase(Long.valueOf(pId));
				query=query+"AND o.lm.id IN ("+orgs+") ";
			}else{
				query=query+"AND o.lm.id="+pId;
			}
		}
		
		if(keyword!=null&&!keyword.equals("")){
			query=query +" AND ( o.newsTitle LIKE '%"+keyword+"%' ) ";
		}
		
		if(status!=null&&!status.equals("")&&!status.equals("0")){
			query=query +" AND o.status ='"+status+"' ";
		}
		
		if(allowed!=null&&!allowed.equals("")&&!allowed.equals("0")){
			query=query +" AND o.allowed ='"+allowed+"' ";
		}
		
		if(!StringUtil.isEmpty(beginTime)&&!StringUtil.isEmpty(endTime)){
			query=query+" AND o.pubDate between '"+beginTime+" 00:00:00' and '"+endTime+" 23:59:59'" ;
			//query=query+"  AND o.pubDate between to_date('"+beginTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') " ;
			
		}
		
		query=query+" ORDER BY o."+sort+" "+direction;
		
		JSONObject json = new JSONObject();
		   
		List<News> list=this.newsService.findAll(query, page);
		
		String[] excludes={"children","news","attach","docs","newsBody","users","receiver","comment"};
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
		
		String id = request.getParameter("orgid");
		String url = request.getParameter("url");
		NewsLm porg=this.lmService.findById(NewsLm.class,Long.valueOf(id));
		String orgids=porg.getOrgIdsWj();
		
		String fileserverurl =  propertiesService.getProperty("FILE_SERVER_URL");
		map.put("fileserverurl", fileserverurl);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		if(include(orgids,org.getId())){
			News doc=new News();
			doc.setLm(porg);
			doc.setMesgType(porg.getModuleId());
			doc.setUser(user);
			doc.setOrg(org);
			doc.setStatus("1");
			doc.setAllowed("1");
			doc.setOrderNum(1);
			doc.setReadCount(0);
			doc.setPubDate(new Date());
			doc.setNewslock(0);
			this.newsService.save(doc);
			map.put("doc", doc);
			return url;
			
		}else{	
			map.put("msg", "权限不足！");
			return MSG;
		}
		
	}
	
	
	//修改
	@RequiresPermissions("NewsM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		
		String id = request.getParameter("newsId");
		String url = request.getParameter("url");
		News doc=this.newsService.findById(News.class,Long.valueOf(id));
		String status=doc.getStatus();
		
		NewsLm porg=doc.getLm();
		
		String orgids=porg.getOrgIdsWj();
		
		if(doc.getNewslock()>=2){
			map.put("status", "false");
			map.put("msg", "锁定不能修改");
			return MSG;
		}
		/**
		if(include(orgids,org.getId())){
			if(status!=null&&!status.equals("")){
				if(status.equals("1")){
					map.put("doc", doc);
					return url;
				}else{
					map.put("msg", "已经提交不能修改了");
					return MSG;
				}
			}
		}
		map.put("msg", "没有操作权限！");
		return MSG;
		**/
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		
		if(!StringUtil.isEmpty(doc.getNewsBody()))
			doc.setNewsBody(reXSS2(doc.getNewsBody()));
		map.put("doc", doc);
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
		
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
			
		//设置响应给前台内容的数据格式  
		//response.setContentType("text/plain; charset=UTF-8");  
        //设置响应给前台内容的PrintWriter对象  
		//PrintWriter out;
		
		//out = response.getWriter();
			
		doc.setOrg(org);
		doc.setUser(user);
		doc.setPubDate(new Date());
		doc.setStatus("1");
		doc.setReadCount(0);
		
		if(!StringUtil.isEmpty(doc.getNewsBody()))
			doc.setNewsBody(this.reXSS(doc.getNewsBody()));
		this.newsService.save(doc);	
		map.put("doc", doc);
			
		String result=json.toString();
		//out.print(result);  
	    //out.flush(); 
		
		return result; 
		 
	}
	
	
	
	//保存在线修改
	@RequiresPermissions("NewsM:edit")
	@RequestMapping(value="/saveEdit", method=RequestMethod.POST )
	public @ResponseBody String saveEdit(News doc,Map<String, Object> map,HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		JSONObject json = new JSONObject();
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
		//设置响应给前台内容的数据格式  
		//response.setContentType("text/plain; charset=UTF-8");  
        //设置响应给前台内容的PrintWriter对象  
		//PrintWriter out;
			
		//News old=this.newsService.findById(News.class, doc.getId());
		//out = response.getWriter();
			
		doc.setPubDate(new Date());
		doc.setUser(user);
		doc.setOrg(org);
		doc.setNewsBody(this.reXSS(doc.getNewsBody()));
		//doc.setStatus("1");
		this.newsService.update(doc);	
		map.put("doc", doc);
		json.put("status", "true");
		json.put("message", "保存成功!");
		 
		String result=json.toString();
		//out.print(result);  
        //out.flush();
		return result; 
		 
	}
	
	@RequiresPermissions("NewsM:edit")
	@RequestMapping(value="/tjNews",method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String tjNews(HttpServletRequest request,Map<String, Object> map ){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
		JSONObject json = new JSONObject();
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
		String ids = request.getParameter("ids");
		if (ids != null && ids!="") {
			try{
				News doc=this.newsService.findById(News.class,Long.valueOf(ids));
				if(StringUtil.isEmpty(doc.getStatus()))doc.setStatus("1");
				if(doc.getStatus().equals("1")){
					String dd = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
					String shyj="";
					if(StringUtil.isEmpty(doc.getRemark()))
						shyj="提交！<br/>"+user.getUserName()+dd;
					else
						shyj=doc.getRemark()+"<hr/>提交！<br/>"+user.getUserName()+dd;
					
					doc.setRemark(shyj);
					doc.setStatus("2");
				}else if(doc.getStatus().equals("2")){
					doc.setStatus("1");
				}
				
				
				newsService.update(doc);
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", e.toString());
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择!");
	    }
		String result=json.toString();
		return result;
	}
	
	
	@RequiresPermissions("NewsM:delete")
	@RequestMapping(value="/del", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String del(HttpServletRequest request  ,Map<String, Object> map ){
		HttpSession session = request.getSession(true);  
		String rootPath = request.getServletContext().getInitParameter("rootPath");
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		
		String ids = request.getParameter("ids");
		
		JSONObject json = new JSONObject();
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
		if (ids != null && ids!="") {
			try{
				News doc=this.newsService.findById(News.class,Long.valueOf(ids));
				if(doc.getNewslock()>0){
					json.put("status", "true");
					json.put("message", "文件锁定不能删除");
					String result=json.toString();
					return result;
				}
				
				NewsLm porg=doc.getLm();
				String orgids=porg.getOrgIdsWj();
				
					if(include(orgids,org.getId())){
						
						this.attService.delFileByNewsIds(ids,rootPath);
						this.newsService.delIds(ids);//.update(hql);
						//this.newsService.delete(doc);
						json.put("status", "true");
						json.put("message", "删除成功!");
					}else{	
						json.put("status", "false");
						json.put("message", "没有操作权限！");
					}
				
				
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", "有子节点不能删除！");
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择要删除的节点!");
	    }
		
		String result=json.toString();
		return result;
	}
	
	
	@RequiresPermissions("NewsM:batchdel")
	@RequestMapping(value="/delBatch", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delBatch(HttpServletRequest request  ,Map<String, Object> map ){
		String rootPath = request.getServletContext().getInitParameter("rootPath");
		
		String ids = request.getParameter("ids");
		
		JSONObject json = new JSONObject();
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
		if (ids != null && ids!="") {
			try{
				this.attService.delFileByNewsIds(ids,rootPath);
				this.newsService.delIds(ids);//.update(hql);
				json.put("status", "true");
				json.put("message", "删除成功!");
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", "有子节点不能删除！");
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择要删除的节点!");
	    }
		
		String result=json.toString();
		return result;
	}
	
	
	
	//重置用户状态
	@RequiresPermissions("NewsM:lock")
	@RequestMapping(value="/status", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String status(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
		
		String id = request.getParameter("newsid");
		if (id != null && id!="") {
			News doc=this.newsService.findById(News.class,Long.valueOf(id));
			Integer st=doc.getNewslock();
			
			if(st==2){
				doc.setNewslock(0);
			}else{
				doc.setNewslock(st+1);
			}
			this.newsService.update(doc);
			json.put("status", "true");
			json.put("message", "操作成功!");
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择!");
	    }
		
		String result=json.toString();
		return result;							
						
	}		

	//设定使用方式
	@RequiresPermissions("NewsM:edit")
	@RequestMapping(value="/allowed", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String setAllowed(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		//if(!CsrfTokenUtils.validCsrfToken(request)){
		//	json.put("status", "false");
		//	json.put("message", "非法操作!");
		//	String result=json.toString();
		//	return result;
		//}
		
		String id = request.getParameter("newsid");
		if (id != null && id!="") {
			News doc=this.newsService.findById(News.class,Long.valueOf(id));
			Integer st=Integer.valueOf(doc.getAllowed());
			
			if(st==4){
				doc.setAllowed("1");;
			}else{
				st=st+1;
				doc.setAllowed(st.toString());;
			}
			this.newsService.update(doc);
			json.put("status", "true");
			json.put("message", "操作成功!");
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择!");
	    }
		
		String result=json.toString();
		return result;							
						
	}			
	
	
	private boolean include(String uslist, Long id) {
		String [] usls=uslist.split(",");
		String ids=id.toString();
		for(int i=0;i<usls.length;i++){
			if(usls[i].equals(ids))
				return true;
		}
		return false;
	}
	
	
	private String reXSS(String value) {
		
		//value = value.replaceAll("&lt;","<").replaceAll( "&gt;",">");
		//value = value.replaceAll("\\(", "（").replaceAll("\\)", "）");
		//value = value.replaceAll("&#39;","'");
		//value = value.replaceAll( "&quot;","\"");
		
		return value;
	}
	
	private String reXSS2(String value) {
		
		//value = value.replaceAll("&lt;","<").replaceAll( "&gt;",">");
		//value = value.replaceAll("\\(", "（").replaceAll("\\)", "）");
		value = value.replaceAll("&#39;","'");
		value = value.replaceAll( "&quot;","\"");
		
		return value;
	}
	
	
	
	//打开一条新闻,根据模板显示新闻
	@RequestMapping(value="/showNews/{newsId}", method={RequestMethod.GET, RequestMethod.POST})
	public String newsDisplay(@PathVariable("newsId") String newsId,Map<String, Object> map ,HttpServletRequest request ){
			//HttpSession session = request.getSession(true);  
		    // QyzxQhUser user=(QyzxQhUser)session.getAttribute("LoginUser"); 
			//String id = request.getParameter("newsid");
			//String url = request.getParameter("url");
			String query="";
			News doc=this.newsService.getNewsById(newsId, query);
			if(StringUtil.isEmpty(doc.getNewsBody())){
				//map.put("newsBody", "");
			}else{
				doc.setNewsBody(reXSS2(doc.getNewsBody()));
			}
			
			map.put("news", doc);
			Integer dicId=doc.getMesgType();
			Dic dic=this.DicService.getDicById(dicId.toString(), "");
			String Url="/web/NewsDisplay";
			if(dic!=null&&dic.getStatus().equals("y")){
				Url="/web/"+dic.getValue1();
			}
			return Url;
			
	}
		
		
	
}
