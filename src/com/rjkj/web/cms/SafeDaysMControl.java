package com.rjkj.web.cms;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

import com.rjkj.dao.DicDao;
import com.rjkj.dao.OrgDao;
import com.rjkj.dao.SafeDaysDao;
import com.rjkj.entities.Dic;
import com.rjkj.entities.Organize;
import com.rjkj.entities.SafeDays;
import com.rjkj.entities.User;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.web.interceptor.CsrfTokenUtils;

@Controller
@RequestMapping("/cms/safe")
public class SafeDaysMControl {
	
	@Resource(name = "DicDao")
	private DicDao DicService;
	
	@Autowired
	private OrgDao orgService;
	
	@Autowired
	private SafeDaysDao safeService;
	
	
	@RequestMapping(value="/safedays", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getSafeDay(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser");
        //Organize org=user.getOrg();
		
		String orgId= request.getParameter("orgId");
		String query=" WHERE 1=1 AND o.allowed='y' AND o.org.id="+orgId+"  ORDER BY o.orderNum ";
		JSONObject json = new JSONObject();
		   
		List<SafeDays> list=this.safeService.findAll(SafeDays.class, query);
		
		String[] excludes={"children","news","attach","docs","users","receiver","comment"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy/MM/dd HH:mm:ss");
		JSONArray jsonArray=JSONArray.fromObject(list, config);
		
		json.put("rows", jsonArray);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("SafeM:view")
	@RequestMapping(value="/table", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getNewsTable(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser");
        //Organize org=user.getOrg();
		
		String orgId= request.getParameter("orgId");
		
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE 1=1 AND o.org.id="+orgId;
		
		query=query+" ORDER BY o."+sort+" "+direction;
		
		JSONObject json = new JSONObject();
		   
		List<SafeDays> list=this.safeService.findAll(query, page);
		
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
	
	@RequiresPermissions("SafeM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET, RequestMethod.POST})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String url = request.getParameter("url");
		String id = request.getParameter("safeId");
		SafeDays org=this.safeService.findById(SafeDays.class,Long.valueOf(id));
		map.put("safe", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		return url;
		
	}
	
	
	@RequiresPermissions("SafeM:edit")
	@RequestMapping(value="/saveEdit", method={RequestMethod.POST})
	public @ResponseBody String saveEdit(SafeDays org, Map<String, Object> map,HttpServletRequest request ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		this.safeService.update(org);//正常更新
		map.put("safe", org);
		json.put("status", "true");
		json.put("message", "修改成功!");
		String result=json.toString();
		return result;
	}
	
	
	
	@RequiresPermissions("SafeM:delete")
	@RequestMapping(value="/ifshow", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String setIfShow(HttpServletRequest request  ,Map<String, Object> map ){
		String ids = request.getParameter("ids");
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		if (ids != null && ids!="") {
			try{
				
				SafeDays safe=this.safeService.findById(SafeDays.class,Long.valueOf(ids));
				
				if(safe.getAllowed()!=null&&!safe.getAllowed().equals("")){
					
					if(safe.getAllowed().equals("y")){
						safe.setAllowed("n");
					}else{
						safe.setAllowed("y");
					}
				}else{
					safe.setAllowed("y");
				}
				this.safeService.update(safe);
				json.put("status", "true");
				json.put("message", "成功!");
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
	
	
	
	@RequiresPermissions("SafeM:delete")
	@RequestMapping(value="/del", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String del(HttpServletRequest request  ,Map<String, Object> map ){
		String ids = request.getParameter("ids");
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		if (ids != null && ids!="") {
			try{
				//String hql=" DELETE FROM Organize WHERE id IN("+ids+")";
				this.safeService.delIds(SafeDays.class,ids);
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
	
	@RequiresPermissions("SafeM:save")
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String save(HttpServletRequest request  ,Map<String, Object> map ){
		String orgid = request.getParameter("orgid");
		String dicid = request.getParameter("dicid");
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		if (orgid != null && orgid!=""&&dicid != null && dicid!="") {
			try{
				List<Dic> dics=this.DicService.findByParentId(Long.valueOf(dicid), "");
				Organize org=this.orgService.findById(Organize.class, Long.valueOf(orgid));
				
				this.safeService.save(org,dics);
				
				json.put("status", "true");
				json.put("message", "成功!");
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", e.toString());
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择要删除的节点!");
	    }
		
		String result=json.toString();
		return result;
	}
	
	
	
	

}
