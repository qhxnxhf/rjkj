package com.rjkj.web.cms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.LmDao;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.Organize;
import com.rjkj.entities.User;
import com.rjkj.service.PropertiesService;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;
import com.rjkj.web.interceptor.CsrfTokenUtils;




@Controller
@RequestMapping("/cms/lm")
public class LmMControl {
	private static final Log log = LogFactory.getLog(LmMControl.class);  
	//protected ErrMg error; 
	@Resource(name = "PropertiesService")
	private PropertiesService propertiesService; 
	
	@Autowired
	private LmDao lmService;
	
	
	private static final String MSG = "gz/MsgPage";
	
	//栏目单选树
	
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
	
	//栏目单选树，没有目录操作授权的节点不能选中，自身及其所有子节点排除，修改父节点时使用。
	
	@RequestMapping(value="/lmPrtTree",method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String sortPrtTree(HttpServletRequest request){
			HttpSession session = request.getSession(true);  
			User user=(User)session.getAttribute("LoginUser"); 
			Organize org=user.getOrg();
	        String parentId=request.getParameter("parentId");
	        String selfId=request.getParameter("selfId");//自身节点ID
	        String query=" WHERE o.id!="+selfId+" AND o.nodeType!='y'  ORDER BY o.orderNum,o.id DESC";		        
			
	        List<NewsLm> modlist=this.lmService.findAll(NewsLm.class,query);
			LinkedList<NewsLm> modlinked=new LinkedList<NewsLm>(modlist); 
			NewsLm root=this.lmService.findById(NewsLm.class,Long.valueOf(parentId));
			JSONArray jsonArray = new JSONArray();  
			createTree3(org,root, modlinked,jsonArray);
			JSONObject json = new JSONObject();
			json.put("treeNodes", jsonArray);
			String result=json.toString();
			return result;					
		}	
	
	//异步加载目录树，全部
		
	@RequestMapping(value="/tree",method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		HttpSession session = request.getSession(true);  
		User user=(User)session.getAttribute("LoginUser"); 
		Organize dept=user.getOrg();
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      
	      NewsLm org=this.lmService.findById(NewsLm.class,1L);
	      
	      node1.setId(org.getId()+"");
	      node1.setName(org.getLmName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	      
	     // node1.setOldParentId(null);
	      node1.setDesc(org.getLmBrief());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<NewsLm> list=this.lmService.findByParentId(Long.valueOf(id), " ORDER BY o.orderNum ASC");
	    	if(list!=null&&list.size()>0){
	    		for (int i = 0; i <list.size(); i++) {
	    			NewsLm org=list.get(i);
	    			
	    			TreeNode node1 = new TreeNode();
	    			node1.setId(org.getId()+"");
	    			
	    			String orgids=org.getOrgIdsMl();
	    			if(include(orgids,dept.getId())){
	    				 node1.setName(org.getLmName()+"*");
	    			}else{
	    				node1.setName(org.getLmName());
	    			}
	    			
	    		    
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
	
	
	@RequiresPermissions("LmM:view")
	@RequestMapping(value="/table", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeTable(HttpServletRequest request){
		String id = request.getParameter("parentId");
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		String deptName= request.getParameter("deptName");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		JSONObject json = new JSONObject();
		   
		List<NewsLm> list;
		//Long ids;
		if (id == null || "".equals(id.trim())) {
			id=1+"";
	    }
		//list=this.sortService.findByParent(ids, "");
		
		if(deptName !=null &&!deptName.equals("")){
			list=this.lmService.findAll(" WHERE o.parent.id ="+id+" AND o.lmName LIKE '%"+deptName+"%' "+"  ORDER BY "+sort+" "+direction, page);
		}else{
			list=this.lmService.findAll(" WHERE o.parent.id ="+id+" ORDER BY "+sort+" "+direction, page);
		}
		
		String[] excludes={"children","news","remarks"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		JSONArray jsonArray=JSONArray.fromObject(list, config);
		
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	
	@RequiresPermissions("LmM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		HttpSession session = request.getSession(true);  
		User user=(User)session.getAttribute("LoginUser"); 
		Organize org=user.getOrg();
		
		String id = request.getParameter("orgid");
		String url = request.getParameter("url");
		NewsLm porg=this.lmService.findById(NewsLm.class,Long.valueOf(id));
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		String fileserverurl =  propertiesService.getProperty("FILE_SERVER_URL");
		map.put("fileserverurl", fileserverurl);
		
		String orgids=porg.getOrgIdsMl();
		
		if(include(orgids,org.getId())){
			NewsLm sort=new NewsLm();
			sort.setParent(porg);
			map.put("dic", sort);
			return url;
		}else{	
			map.put("msg", "权限不足！");
			return MSG;
		}
		
		
	}
	
	@RequiresPermissions("LmM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		HttpSession session = request.getSession(true);  
        User user=(User)session.getAttribute("LoginUser"); 
        Organize org=user.getOrg();
		String id = request.getParameter("orgid");
		String url = request.getParameter("url");
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		NewsLm sort=this.lmService.findById(NewsLm.class,Long.valueOf(id));
		String orgids=sort.getOrgIdsMl();
		String fileserverurl =  propertiesService.getProperty("FILE_SERVER_URL");
		map.put("fileserverurl", fileserverurl);
		if(include(orgids,org.getId())){
			
			map.put("dic", sort);
			return url;
		}else{	
			map.put("msg", "权限不足！");
			return MSG;
		}
		
	}
	
	@RequiresPermissions("LmM:auth")
	@RequestMapping(value="/preAuth", method={RequestMethod.GET})
	public String preAuth(Map<String, Object> map ,HttpServletRequest request ){
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		String id = request.getParameter("orgid");
		String url = request.getParameter("url");
		NewsLm org=this.lmService.findById(NewsLm.class,Long.valueOf(id));
		map.put("dic", org);
		return url;
	}
	
	
	
	@RequiresPermissions("LmM:save")
	@RequestMapping(value="/saveAdd", method={RequestMethod.POST})
	public @ResponseBody String saveAdd(NewsLm dic ,Map<String, Object> map ,HttpServletRequest request){
		
		
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
		
		
	    dic.setOrgIdsMl(dic.getParent().getOrgIdsMl());
	    dic.setOrgIdsWj(dic.getParent().getOrgIdsWj()+","+org.getId());
	   
		this.lmService.save(dic);
	    json.put("status", "true");
		json.put("message", "添加成功!");
		map.put("dic", dic);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("LmM:edit")
	@RequestMapping(value="/saveEdit", method={RequestMethod.POST})
	public @ResponseBody String saveEdit(NewsLm dic ,Map<String, Object> map,HttpServletRequest request ){
		Long id = dic.getId();
		
		JSONObject json = new JSONObject();
		
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		if (id != null && id>0) {
			this.lmService.update(dic);//更新
			json.put("status", "true");
			json.put("message", "修改成功!");
			//json.put("organize", organize);
	    }else{
	    	json.put("status", "false");
			json.put("message", "修改失败!");
	    }
		map.put("dic", dic);
		
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("LmM:auth")
	@RequestMapping(value="/saveAuth", method={RequestMethod.POST})
	public @ResponseBody String saveAuth(NewsLm dic ,Map<String, Object> map,HttpServletRequest request ){
		Long id = dic.getId();
		String fs = request.getParameter("sqfs");
		JSONObject json = new JSONObject();
		
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		if (id != null && id>0) {
			
			if(fs!=null&&fs.equals("1")){
				this.lmService.update(dic);//单独授权
				json.put("status", "true");
				json.put("message", "修改成功!");
			}else{
				this.lmService.caseAuth(dic);//级联授权
				json.put("status", "true");
				json.put("message", "修改成功!");
			}
			
			
			//json.put("organize", organize);
	    }else{
	    	json.put("status", "false");
			json.put("message", "修改失败!");
	    }
		map.put("dic", dic);
		
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("LmM:batchdel")
	@RequestMapping(value="/delBatch", method={RequestMethod.POST})
	public @ResponseBody String delBatch(HttpServletRequest request  ,Map<String, Object> map ){
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
				//String hql=" DELETE FROM Dic WHERE id IN("+ids+")";
				this.lmService.delIds(NewsLm.class, ids);//.update(hql);
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
	
	@RequiresPermissions("LmM:delete")
	@RequestMapping(value="/del", method={RequestMethod.POST})
	public @ResponseBody String del(HttpServletRequest request  ,Map<String, Object> map ){
		String ids = request.getParameter("ids");
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
		
		if (ids != null && ids!="") {
			try{
				NewsLm sort=this.lmService.findById(NewsLm.class,Long.valueOf(ids));
				
				String orgids=sort.getOrgIdsMl();
				
				if(include(orgids,org.getId())){
					this.lmService.delIds(NewsLm.class, sort.getId().toString());
					//this.lmService.delete(sort);
					json.put("status", "true");
					json.put("message", "删除成功!");
				}else{
					json.put("status", "true");
					json.put("message", "此节点你没有删除的权限!");
					
				}
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
	
	//生成目录树，			
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
				    		 createTree(mod,modlist,jsonArray);
				    	 }
				     }else{
				    	 return ;
				     }
		}
		
	public void createTree3(Organize org,NewsLm root,LinkedList<NewsLm> modlist,JSONArray jsonArray){
		 
		if(this.include(root.getOrgIdsMl(), org.getId())||!root.getNodeType().equals("y")){
			
					TreeNode node1 = new TreeNode();
					 node1.setId(root.getId()+"");
					 //node1.setPhone(root.getKey1()+"");
					 
				     node1.setMenuType(root.getNodeType());
				     NewsLm pmod=root.getParent();
				     if(pmod!=null){
				    	 node1.setParentId(pmod.getId()+"");
				     }else{
				    	 node1.setParentId("0");
				     }
				     
				     if(root.getNodeType().equals("y")){
						 node1.setIsParent(Boolean.valueOf(false));
						 node1.setClickExpand(true);//使节点不能被选中
						 node1.setName(root.getLmName());
					 }else{
						 if(include(root.getOrgIdsMl(), org.getId())){
							 node1.setName(root.getLmName()+"*");
						 }else{
							 node1.setClickExpand(true);//使节点不能被选中
							 node1.setName(root.getLmName());
						 }	
						 
						 node1.setIsParent(Boolean.valueOf(true));
						 if(root.getNodeType().equals("r")){
							 node1.setOpen(true);
						 }
					 }
				     
				     //node1.setChecked(this.ifModule(rmlist, root));
				     jsonArray.add(node1);
				     List<NewsLm> children=getChildren(root,modlist);
				     if(children!=null&&children.size()>0){
				    	 for(int i=0;i<children.size();i++){
				    		 NewsLm mod=children.get(i);
				    		 createTree3(org,mod,modlist,jsonArray);
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
