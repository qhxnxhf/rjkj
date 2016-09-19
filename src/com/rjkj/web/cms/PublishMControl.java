package com.rjkj.web.cms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.DicDao;
import com.rjkj.dao.NewsAttachDao;
import com.rjkj.dao.NewsDao;
import com.rjkj.dao.NewsSendDao;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.service.PropertiesService;
import com.rjkj.util.StringUtil;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;


@Controller
@RequestMapping("/cms/pub")
public class PublishMControl {
	
	
	private static final Log log = LogFactory.getLog(NewsMControl.class);  
	//protected ErrMg error; 
	@Resource(name = "PropertiesService")
	private PropertiesService propertiesService; 
	
	@Resource(name = "DicDao")
	private DicDao DicService; 
	
	@Autowired
	private NewsDao newsService;
	
	@Autowired
	private NewsAttachDao attService;
	
	@Autowired
	private NewsSendDao sendService;
	
	private static final String MSG = "gz/MsgPage";
	
	private static final String OPEN="cms/news/Receive";
	
	
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/checktable", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getCheckTable(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
	
        
		String keyword = request.getParameter("keyword");
		String status= request.getParameter("status");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 AND o.org.id ="+org.getId();
		
		
		
		if(keyword!=null&&!keyword.equals("")){
			query=query +" AND ( o.newsTitle LIKE '%"+keyword+"%' ) ";
		}
		
		if(status!=null&&!status.equals("")&&!status.equals("0")){
			query=query +" AND o.status ='"+status+"' ";
		}else{
			query=query +" AND o.status ='2' ";
		}
		
		
		
		if(!StringUtil.isEmpty(beginTime)&&!StringUtil.isEmpty(endTime)){
			//query=query+"  AND o.pubDate between to_date('"+beginTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') " ;
			query=query+" AND o.pubDate between '"+beginTime+" 00:00:00' and '"+endTime+" 23:59:59'" ;
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
	
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/pubtable", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getPubTable(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
	
        
		String keyword = request.getParameter("keyword");
		String status= request.getParameter("status");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 ";
		
		
		
		if(keyword!=null&&!keyword.equals("")){
			query=query +" AND ( o.newsTitle LIKE '%"+keyword+"%' ) ";
		}
		
		if(status!=null&&!status.equals("")&&!status.equals("0")){
			query=query +" AND o.status ='"+status+"' ";
		}else{
			query=query +" AND o.status ='3' ";
		}
		
		
		
		if(!StringUtil.isEmpty(beginTime)&&!StringUtil.isEmpty(endTime)){
			//query=query+"  AND o.pubDate between to_date('"+beginTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') " ;
			query=query+" AND o.pubDate between '"+beginTime+" 00:00:00' and '"+endTime+" 23:59:59'" ;
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
	
	
	
	@RequestMapping(value="/authNews", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String authNews(HttpServletRequest request,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        
		String ids = request.getParameter("ids");
		String shyj = request.getParameter("shyj");
		String zt = request.getParameter("zt");
		
		if (ids != null && ids!="") {
			
				News doc=this.newsService.findById(News.class,Long.valueOf(ids));
				doc.setStatus(zt);
				String dd = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
				shyj=doc.getRemark()+"<hr/>"+shyj+"<br/>"+user.getUserName()+dd;
				doc.setRemark(shyj);
				newsService.update(doc);
				json.put("status", "true");
				json.put("message", "OK!");
			
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择!");
	    }
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("PubM:delete")
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
	
	
	@RequiresPermissions("PubM:batchdel")
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
	
	private boolean include(String uslist, Long id) {
		String [] usls=uslist.split(",");
		String ids=id.toString();
		for(int i=0;i<usls.length;i++){
			if(usls[i].equals(ids))
				return true;
		}
		return false;
	}

}
