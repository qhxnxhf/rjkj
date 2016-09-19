package com.rjkj.web.cms;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.DicDao;
import com.rjkj.dao.NewsDao;
import com.rjkj.dao.NewsSendDao;
import com.rjkj.entities.NewsSend;
import com.rjkj.entities.User;
import com.rjkj.service.PropertiesService;
import com.rjkj.util.StringUtil;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;


@Controller
@RequestMapping("/cms/inbox")
public class InboxMControl {
	private static final Log log = LogFactory.getLog(NewsMControl.class);  
	//protected ErrMg error; 
	@Resource(name = "PropertiesService")
	private PropertiesService propertiesService; 
	
	@Resource(name = "DicDao")
	private DicDao DicService; 
	
	@Autowired
	private NewsDao newsService;
	
	@Autowired
	private NewsSendDao sendService;
	
	private static final String MSG = "gz/MsgPage";
	
	private static final String OPEN="cms/news/Receive";
	
	
	
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/table", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getNewsTable(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        //Organize org=user.getOrg();
	
		String keyword = request.getParameter("keyword");
		String senderName = request.getParameter("senderName");
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String status= request.getParameter("status");
		
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 AND o.taker.id="+user.getId();
		
		if(StringUtils.isNotBlank(senderName)){
			query=query +" AND ( o.sender.userName LIKE '%"+senderName+"%' ) ";
		}
		
		if(keyword!=null&&!keyword.equals("")){
			query=query +" AND ( o.news.newsTitle LIKE '%"+keyword+"%' ) ";
		}
		
		if(status!=null&&!status.equals("")&&!status.equals("0")){
			query=query +" AND o.status ='"+status+"' ";
		}
		
		
		if(!StringUtil.isEmpty(beginTime)&&!StringUtil.isEmpty(endTime)){
			query=query+"  AND o.sendDate between to_date('"+beginTime+"','yyyy-mm-dd') and to_date('"+endTime+"','yyyy-mm-dd') " ;
		}
		
		query=query+" ORDER BY o."+sort+" "+direction;
		
		JSONObject json = new JSONObject();
		   
		List<NewsSend> list=this.sendService.findByNews(query, page);
		
		String[] excludes={"children","attach","docs","users","newsBody","receiver","comment"};
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
	
	
	
	//收件
	@RequestMapping(value="/open/{recId}", method={RequestMethod.GET, RequestMethod.POST})
	public String openNews(@PathVariable("recId") Long recId,Map<String, Object> map ,HttpServletRequest request ){
			
			NewsSend newsrec=this.sendService.findById(NewsSend.class, Long.valueOf(recId));
			if(newsrec.getStatus().equals("1")){
				newsrec.setReceiveDate(new Date());
				newsrec.setStatus("2");
				sendService.update(newsrec);
			}
			map.put("rec", newsrec);
			return OPEN;
	}
		
	//回复
	@RequiresPermissions("NewsM:view")
	@RequestMapping(value="/save", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String replayNews(HttpServletRequest request){
		//HttpSession session = request.getSession(true);  
        //User user=(User)session.getAttribute("LoginUser"); 
       
		String id = request.getParameter("id");
		String replywd = request.getParameter("replywd");
		
		JSONObject json = new JSONObject();
		
		if (StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(replywd)) {
			
			NewsSend newsrec=this.sendService.findById(NewsSend.class, Long.valueOf(id));
			newsrec.setReplywd(replywd);
			sendService.update(newsrec);
			
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
