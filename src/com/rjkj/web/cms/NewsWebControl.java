package com.rjkj.web.cms;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonView;
import com.rjkj.dao.DicDao;
import com.rjkj.dao.LmDao;
import com.rjkj.dao.NewsAttachDao;
import com.rjkj.dao.NewsDao;
import com.rjkj.dao.NewsSendDao;
import com.rjkj.entities.Dic;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsAttach;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.NewsSend;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.util.ClassTools;
import com.rjkj.util.StringUtil;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;


@Controller
@RequestMapping(value="/web")
@Scope("prototype")
public class NewsWebControl {
	
	@Resource(name = "NewsDao")
	private NewsDao newsService;
	
	@Resource(name = "DicDao")
	private DicDao DicService;
	
	@Resource(name = "NewsAttachDao")
	private NewsAttachDao attService;
	
	@Resource(name = "LmDao")
	private LmDao lmService;
	
	@Autowired
	private NewsSendDao sendService;
	
	public final String QH="web/Index";
	
	//页面调转用
	@RequestMapping(value="/index", method={RequestMethod.GET})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String url = request.getParameter("url");
		String kwd=request.getParameter("kwd");
		
		String lmid = request.getParameter("lmid");
		if(!StringUtil.isEmpty(lmid)){
			NewsLm lm=this.lmService.findById(NewsLm.class, Long.valueOf(lmid));
			
			map.put("lm", lm);
		}
		if(!StringUtil.isEmpty(kwd)){
			try {
				
				kwd = new String(kwd.getBytes("ISO-8859-1"),"UTF-8"); 
				//kwd=URLDecoder.decode(kwd,"UTF-8");
				map.put("kwd", kwd);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(url!=null&&!url.equals("")){
			
			return url;
		}else{
			return QH;
		}
	}
	
	//打开一条新闻
	@RequestMapping(value="/getNews/{newsId}")
	@JsonView
	public Map<String,Object>  getNews(@PathVariable("newsId") String newsId ,HttpServletRequest request ){
				
				Map<String,Object> map=new HashMap();
				News doc=this.newsService.findById(News.class, Long.valueOf(newsId));
				if(doc!=null&&doc.getStatus().equals("4")){
					map.put("id", doc.getId());
					map.put("newsTitle", doc.getNewsTitle());
					map.put("icoPath", doc.getIcoPath());
					map.put("newsBrief", doc.getNewsBrief());
					if(StringUtil.isEmpty(doc.getNewsBody())){
						map.put("newsBody", "");
					}else{
						map.put("newsBody", reXSS2(doc.getNewsBody()));
					}
				}
				
				return map;
	}
	
	//打开一条新闻,根据模板显示新闻
	@RequestMapping(value="/showNews2", method={RequestMethod.GET})
	public String newsShow2(Map<String, Object> map ,HttpServletRequest request ){
			HttpSession session = request.getSession(true); 
	        User user=(User)session.getAttribute("LoginUser");
	        String newsId=request.getParameter("newsid");
			News doc=this.newsService.findById(News.class, Long.valueOf(newsId));
			String url=request.getParameter("url");
			
			String Url="/web/NewsDisplay";
			String message="";
			if(doc!=null&&doc.getStatus().equals("4")){
				String allowed="";
				if(StringUtil.isEmpty(doc.getAllowed())){
					allowed="1";
					doc.setAllowed(allowed);
				}else{
					allowed=doc.getAllowed();
				}
				
				if(allowed.equals("1")){		//公开访问			
					Url=newsShow(doc,map);		
				}else if(allowed.equals("2")){		//登录访问
					if(user!=null&&user.getId()>0){
						Url=newsShow(doc,map);
					}else{
						message="登录系统访问！";
					}
				}else if(allowed.equals("3")){		//本部门访问
					if(user!=null&&user.getId()>0){
						Organize org=user.getOrg();
						Organize puborg=doc.getOrg();
						if(org.getId()-puborg.getId()==0){
							Url=newsShow(doc,map);
						}else{
							message="登录系统，发布部门人员能访问！";
						}
					}else{
						message="登录系统访问！";
					}
				}else if(allowed.equals("4")){		//个人访问
					if(user!=null&&user.getId()>0){
						User pubuser=doc.getUser();
						if(user.getId()-pubuser.getId()==0){
							Url=newsShow(doc,map);
						}else{
							message="登录系统，只有发布人能访问！";
						}
					}else{
						message="登录系统访问！";
					}
				}
				map.put("message", message);
				if(url!=null&&!url.equals("")){
					return url;
				}else{
					return Url;
				}
			
			}else{
				map.put("message", message);
				return "";
			}
		}
	
	//打开一条新闻,根据模板显示新闻
	@RequestMapping(value="/showNews/{newsId}", method={RequestMethod.GET, RequestMethod.POST})
	public String newsDisplay(@PathVariable("newsId") String newsId,Map<String, Object> map ,HttpServletRequest request ){
		HttpSession session = request.getSession(true); 
        User user=(User)session.getAttribute("LoginUser");
		News doc=this.newsService.findById(News.class, Long.valueOf(newsId));
		
		
		String Url="/web/NewsDisplay";
		
		String message="";
		if(doc!=null&&doc.getStatus().equals("4")){
			String allowed="";
			if(StringUtil.isEmpty(doc.getAllowed())){
				allowed="1";
				doc.setAllowed(allowed);
			}else{
				allowed=doc.getAllowed();
			}
			
			if(allowed.equals("1")){		//公开访问			
				Url=newsShow(doc,map);		
			}else if(allowed.equals("2")){		//登录访问
				if(user!=null&&user.getId()>0){
					Url=newsShow(doc,map);
				}else{
					message="登录系统访问！";
				}
			}else if(allowed.equals("3")){		//本部门访问
				if(user!=null&&user.getId()>0){
					Organize org=user.getOrg();
					Organize puborg=doc.getOrg();
					if(org.getId()-puborg.getId()==0){
						Url=newsShow(doc,map);
					}else{
						message="登录系统，发布部门人员能访问！";
					}
				}else{
					message="登录系统访问！";
				}
			}else if(allowed.equals("4")){		//个人访问
				if(user!=null&&user.getId()>0){
					User pubuser=doc.getUser();
					if(user.getId()-pubuser.getId()==0){
						Url=newsShow(doc,map);
					}else{
						message="登录系统，只有发布人能访问！";
					}
				}else{
					message="登录系统访问！";
				}
			}
			map.put("message", message);
			return Url;
		}else{
			map.put("message", message);
			return "";
		}
	}
	
	public String newsShow(News doc,Map<String, Object> map){
		String url="";
		doc.setReadCount(doc.getReadCount()+1);
		this.newsService.update(doc);
		if(!StringUtil.isEmpty(doc.getNewsBody())){
			doc.setNewsBody(reXSS2(doc.getNewsBody()));
		}
		map.put("news", doc);
		Integer dicId=doc.getMesgType();
		Dic dic=this.DicService.getDicById(dicId.toString(), "");
		if(dic!=null&&dic.getStatus().equals("y")){
			url="/web/"+dic.getValue1();
		}
		return url;
	}
	
	
	//新闻附件列表
	@RequestMapping(value="/newsAttachs/{pId}/{size}")
	public @ResponseBody String getAttachList(@PathVariable("pId") String pId,@PathVariable("size") String size,HttpServletRequest request,HttpServletResponse response){
		
		String query=" ORDER BY orderNum ";
		List <NewsAttach>  list =attService.getAttachList(pId, size, query);
		
		JSONObject json = new JSONObject();
		String[] excludes={"children","news","attach","docs","users","receiver","comment"};
		//JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd HH:mm:ss");
		JSONArray jsonArray=JSONArray.fromObject(list, config);
		json.put("rows", jsonArray);
		String result=json.toString();
		return result;
	}
	
	//根据一个父栏目得到子栏目
	//{title:"",id:"",lmtype:"",lmicon:"",tabs[{},{}]}
	@RequestMapping(value="/lmtab", method={RequestMethod.POST})
	public  @ResponseBody String getLmTab(HttpServletRequest request){
			String pid=request.getParameter("pId");
			String size1=request.getParameter("size");	
			JSONObject json = new JSONObject();
			//String query=" and allowed='1' ";
			NewsLm lm=this.lmService.getLmById(pid, "");
			//List<NewsLm> lms=this.lmService.findByParentId(lm.getId(), "");
			List<NewsLm> lms=this.lmService.findAll(NewsLm.class, " WHERE o.parent.id = "+lm.getId()+" ORDER BY o.orderNum ASC ", 0, Integer.valueOf(size1));
			json.put("title", lm.getLmName());
			json.put("lmicon", lm.getLmIcon());
			json.put("lmtype", lm.getModuleId());
			json.put("id", lm.getId());
			
			String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
			JSONArray jsonArray=JSONArray.fromObject(lms, config);	
			json.put("tabs", jsonArray);	
			String result=json.toString();
			return result;
		}
	
	
	
	//得到一个栏目的内容列表
	//{title:"",id:"",lmtype:"",lmicon:"",rows[{},{}]}
	@RequestMapping(value="/lmNews/{pId}/{size}", method={RequestMethod.GET, RequestMethod.POST})
	public  @ResponseBody String getLmNews(@PathVariable("pId") String pId,@PathVariable("size") String size,HttpServletRequest request){
			//String pid=request.getParameter("pId");
			//String size=request.getParameter("size");
			String query=" and  status='4' ORDER BY id DESC ";
			//String query=" and allowed='1' ";
			NewsLm lm=this.lmService.getLmById(pId, "");			
			List <News>  list =newsService.getNewsList(pId, size, query);
			JSONObject json = new JSONObject();
			String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
			JSONArray jsonArray=JSONArray.fromObject(list, config);
			json.put("title", lm.getLmName());
			json.put("lmicon", lm.getLmIcon());
			json.put("lmtype", lm.getModuleId());
			json.put("id", lm.getId());
			json.put("rows", jsonArray);
			String result=json.toString();
			return result;
	}
	
	
	
	//得到栏目新闻图片列表
	@RequestMapping(value="/lmNewsPic/{pId}/{size}", method={RequestMethod.GET, RequestMethod.POST})
	public  @ResponseBody String getLmNewsPic(@PathVariable("pId") String pId,@PathVariable("size") String size,HttpServletRequest request){
				//String pid=request.getParameter("pId");
				//String size=request.getParameter("size");
				String query=" and  status='4'  AND icoPath IS NOT NULL AND icoPath!='' ORDER BY id DESC ";
				//String query=" and allowed='1' ";
				NewsLm lm=this.lmService.getLmById(pId, "");
				List <News>  list =newsService.getNewsList(pId, size, query);
				
				JSONObject json = new JSONObject();
				
				String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
				JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
				JSONArray jsonArray=JSONArray.fromObject(list, config);
				
				json.put("title", lm.getLmName());
				json.put("lmicon", lm.getLmIcon());
				json.put("lmicon", lm.getLmIcon());
				json.put("lmtype", lm.getModuleId());
				json.put("rows", jsonArray);
				
				String result=json.toString();
				return result;
	}
	
	//部门介绍、通信地址等
	@RequestMapping(value="/lmNewsPx/{pId}/{size}", method={RequestMethod.GET, RequestMethod.POST})
	public  @ResponseBody String getLmNewsPx(@PathVariable("pId") String pId,@PathVariable("size") String size,HttpServletRequest request){
			//String pid=request.getParameter("pId");
			//String size=request.getParameter("size");
			String query=" and  status='4' ORDER BY orderNum ";
			//String query=" and allowed='1' ";
			NewsLm lm=this.lmService.getLmById(pId, "");
			List <News>  list =newsService.getNewsList(pId, size, query);
			JSONObject json = new JSONObject();
			String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
			JSONArray jsonArray=JSONArray.fromObject(list, config);
			json.put("title", lm.getLmName());
			json.put("lmicon", lm.getLmIcon());
			json.put("rows", jsonArray);
			String result=json.toString();
			return result;
			
	}
	
	@RequestMapping(value="/lmsNews/{pId}/{size}", method={RequestMethod.POST})
	public  @ResponseBody String getLmsNews(@PathVariable("pId") String pId,@PathVariable("size") String size,HttpServletRequest request){
			//String pid=request.getParameter("pId");
			//String size=request.getParameter("size");	
			
			//String query=" and allowed='1' ";
			NewsLm lm=this.lmService.getLmById(pId, "");
			String lms=this.lmService.getLmIdsByCase(lm.getId());
			String query=" and  status='4' ORDER BY id DESC ";
			//String query=" and allowed='1' ";
			List <News>  list =this.newsService.getNewsByLms(lms, size, query);
			JSONObject json = new JSONObject();
			String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
			JSONArray jsonArray=JSONArray.fromObject(list, config);
			json.put("title", lm.getLmName());
			json.put("lmicon", lm.getLmIcon());
			json.put("lmtype", lm.getModuleId());
			json.put("id", lm.getId());
			json.put("rows", jsonArray);
			String result=json.toString();
			return result;
			
			
		}
	
	//得到栏目新闻列表(头条)
	@RequestMapping(value="/lmNewsTt/{pId}/{size}", method={RequestMethod.GET, RequestMethod.POST})
	public  @ResponseBody String getNewsListTt(@PathVariable("pId") String pId,@PathVariable("size") String size,HttpServletRequest request,HttpServletResponse response){
					//String pid=request.getParameter("pId");
					//String size=request.getParameter("size");
					String query=" and status='4'  ORDER BY readCount DESC";
					NewsLm lm=this.lmService.getLmById(pId, "");
					List <News>  list =newsService.getNewsList(pId, size, query);
					JSONObject json = new JSONObject();
					String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
					JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
					JSONArray jsonArray=JSONArray.fromObject(list, config);
					json.put("title", lm.getLmName());
					json.put("lmicon", lm.getLmIcon());
					json.put("rows", jsonArray);
					String result=json.toString();
					return result;
	}
	
	
	
	
	//更多新闻
	@RequestMapping(value="/preMoreNews", method={RequestMethod.GET, RequestMethod.POST})
	public String moreNews(Map<String, Object> map ,HttpServletRequest request ){
			String url = request.getParameter("url");
			String lmid = request.getParameter("lmid");

			NewsLm lm=this.lmService.getLmById(lmid, "");
			map.put("lm", lm);
			if(url!=null&&!url.equals("")){
				return url;
			}else{
				return "web/MoreNews";
			}
	}
	
	//根据栏目ID分页查询
	@RequestMapping(value="/newsList", method={RequestMethod.GET, RequestMethod.POST})
	@JsonView
	public  Map<String,Object> getNewsList(HttpServletRequest request,HttpServletResponse response){
		String query="";
		String lmid= request.getParameter("lmid");
		String kwd= request.getParameter("keyword");
		String pageNo= request.getParameter("pageNumber");
		String pageSize= request.getParameter("pageSize");
		
		 try {
			 if(!StringUtil.isEmpty(kwd))
				 kwd=java.net.URLDecoder.decode(kwd,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!StringUtil.isEmpty(lmid)){
			query=query+" and lmId="+lmid;
		}
		
		if(!StringUtil.isEmpty(kwd)){
			query=query+" and newTitle like '%"+kwd+"%' ";
		}
		
		query=query+" and status='4' ORDER BY id DESC ";
		Page page = new Page();
		ClassTools.buildRequestBean(page, request);
		newsService.getNewsList(page, query);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return map;
	 }
	
	
	@RequestMapping(value="/newsPage", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getNewsPage(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser");
        //Organize org=user.getOrg();
		
		String pId= request.getParameter("lmid");
		String kwd= request.getParameter("keyword");
		String pageNo= request.getParameter("pageNumber");
		String pageSize= request.getParameter("pageSize");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1  AND  o.status='4' ";
		
		if(pId!=null&&!pId.equals("")){
				query=query+"AND o.lm.id="+pId;
		}
		
		if(kwd!=null&&!kwd.equals("")){
			query=query +" AND ( o.newsTitle LIKE '%"+kwd+"%' ) ";
		}
		
		query=query+" ORDER BY o.id DESC ";
		JSONObject json = new JSONObject();
		List<News> list=this.newsService.findAll(query, page);
		String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
		//JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
		JSONArray jsonArray=JSONArray.fromObject(list, config);
		
		json.put("totalCount", page.getTotalRows());
		json.put("currentPage", jsonArray);
		String result=json.toString();
		return result;
	}
	
		
	private String reXSS2(String value) {
			//value = value.replaceAll("&lt;","<").replaceAll( "&gt;",">");
			//value = value.replaceAll("\\(", "（").replaceAll("\\)", "）");
			value = value.replaceAll("&#39;","'");
			value = value.replaceAll( "&quot;","\"");
			return value;
	}
		
		
		
	//新闻签收	
	@RequestMapping(value="/signNews", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delBatch(HttpServletRequest request  ,Map<String, Object> map ){
			HttpSession session = request.getSession(true);  
	        User user=(User)session.getAttribute("LoginUser");
			//String rootPath = request.getServletContext().getInitParameter("rootPath");
			String newsId = request.getParameter("newsId");
			JSONObject json = new JSONObject();
			if(user!=null){
				
				NewsSend ns = this.sendService.findByUserAndNews(user.getId(),newsId);
				if(ns!=null){
					//ns.setReceiveDate(new Date());
					ns.setStatus("2");
					this.sendService.update(ns);
					
				}else{
					News news=this.newsService.findById(News.class, Long.valueOf(newsId));
					ns=new NewsSend();
					ns.setNews(news);
					ns.setSender(news.getUser());
					ns.setTaker(user);
					ns.setSendDate(news.getPubDate());
					ns.setReceiveDate(new Date());
					ns.setStatus("2");
					this.sendService.save(ns);
				}
				
				json.put("status", "true");
				json.put("message", "签收成功!");
				
			}else{
				
				json.put("status", "false");
				json.put("message", "请登录系统进行签收!");
			}
			
			String result=json.toString();
			return result;
	}
		
		
		//得到一个栏目中的多条新闻（跨域调用）
		@RequestMapping(value="/lmNewsKy/{pId}/{size}", method={RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody String getKyDocs(@PathVariable("pId") String pId,@PathVariable("size") Integer size,HttpServletRequest request){
			
			String callback= request.getParameter("callback");
			String query=" WHERE 1=1 ";
			
			if(size==0){
				size=20;
			}
			
			if(pId!=null&&!pId.equals("")){
				query=query+"AND o.lm.id="+pId+" ORDER BY o.pubDate DESC,o.showType DESC";
			}
			
			JSONObject json = new JSONObject();
			   
			List<News> list=this.newsService.findAll(News.class, query, 0, size);
			
			String[] excludes={"children","attach","remarks","users"};
			//JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd");
			JSONArray jsonArray=JSONArray.fromObject(list, config);
			
			
			json.put("rows", jsonArray);
			
			String result=callback+"("+json.toString()+")";
			return result;
		}
		
		
		
		@RequestMapping(value="/lmTree", method={RequestMethod.GET, RequestMethod.POST})
		public @ResponseBody String getLmTree(@RequestParam("parentId") String parentId, HttpServletRequest request){
					
			List<NewsLm> modlist=this.lmService.findAll(NewsLm.class," ORDER BY o.orderNum,o.id DESC ");
			LinkedList<NewsLm> modlinked=new LinkedList<NewsLm>(modlist); 
			NewsLm root=this.lmService.findById(NewsLm.class,Long.valueOf(parentId));
			JSONArray jsonArray = new JSONArray();  
			createTree(root, modlinked,jsonArray);
			JSONObject json = new JSONObject();
			json.put("treeNodes", jsonArray);
			String result=json.toString();
			return result;
		}
		
		public void createTree(NewsLm root,LinkedList<NewsLm> modlist,JSONArray jsonArray){			 
			
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
							 node1.setOpen(true);
							
						 }
					     
					     //node1.setChecked(this.ifModule(rmlist, root));
					     jsonArray.add(node1);
					     List<NewsLm> children=getChildren(root,modlist);
					     if(children!=null&&children.size()>0){
					    	 for(int i=0;i<children.size();i++){
					    		 NewsLm mod=children.get(i);
					    		 createTree(mod,modlist,jsonArray);
					    	 }
					     }else{
					    	 return ;
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
			

	private boolean include(String uslist, Long id) {
				String [] usls=uslist.split(",");
				String ids=id.toString();
				for(int i=0;i<usls.length;i++){
					if(usls[i].equals(ids))
						return true;
				}
				return false;
			}			
	
	
	//{title:"",id:"",titles:[{idSon:"",titleSon:"",lis:[{},{},{},{}]},{idSon:"",titleSon:"",lis:[{},{},{},{}]}]}
	//得到多子栏目新闻列表
	@RequestMapping(value="/lmtabNews", method={RequestMethod.POST})
	public  @ResponseBody String getLmNews(HttpServletRequest request){
		String pid=request.getParameter("pId");
		String size1=request.getParameter("size1");
		String size2=request.getParameter("size2");
		JSONObject json = new JSONObject();
		//String query=" and allowed='1' ";
		NewsLm lm=this.lmService.getLmById(pid, "");
		//List<NewsLm> lms=this.lmService.findByParentId(lm.getId(), "");
		List<NewsLm> lms=this.lmService.findAll(NewsLm.class, " WHERE o.parent.id = "+lm.getId()+" ORDER BY o.orderNum ASC ", 0, Integer.valueOf(size1));
		json.put("title", lm.getLmName());
		json.put("lmicon", lm.getLmIcon());
		json.put("lmtype", lm.getModuleId());
		json.put("id", lm.getId());
		JSONArray jsonArray=new JSONArray();
		
		String[] excludes={"children","news","lm","org","user","newsBody","attach","remark","users","receiver","comment"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd");
		String query=" and  status='4' ORDER BY id DESC ";
		
		for(NewsLm lm1:lms){
			JSONObject json2 = new JSONObject();
			json2.put("title", lm1.getLmName());
			json2.put("lmicon", lm1.getLmIcon());
			json2.put("lmtype", lm1.getModuleId());
			json2.put("id", lm1.getId());
			List <News>  list =newsService.getNewsList(lm1.getId().toString(), size2, query);
			JSONArray jsonArray2=JSONArray.fromObject(list, config);	
			json2.put("contents", jsonArray2);
			jsonArray.add(json2);
		}
		json.put("category", jsonArray);	
		String result=json.toString();
		return result;
				
	}
	
	
	

}
