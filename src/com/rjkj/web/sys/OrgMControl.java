package com.rjkj.web.sys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.OrgDao;
import com.rjkj.entities.Organize;
import com.rjkj.web.interceptor.CsrfTokenUtils;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;



@Controller
@RequestMapping("/sys/org")
public class OrgMControl {
	private static final Log log = LogFactory.getLog(OrgMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private OrgDao orgService;
	
	private static final String ADD = "sys/org/OrgAdd";
	private static final String EDIT = "sys/org/OrgEdit";
	private static final String VIEW = "sys/org/OrgView";
	
	//组织单选树
	@RequestMapping(value="/orgTree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getOrgTree(@RequestParam("parentId") String parentId, HttpServletRequest request){
					
					List<Organize> modlist=this.orgService.findAll(Organize.class," WHERE o.status='y' ORDER BY o.orderNum ASC ");
					LinkedList<Organize> modlinked=new LinkedList<Organize>(modlist); 
					Organize root=this.orgService.findById(Organize.class,Long.valueOf(parentId));
					JSONArray jsonArray = new JSONArray();  
					createTree1(root, modlinked,jsonArray);
					JSONObject json = new JSONObject();
					json.put("treeNodes", jsonArray);
					String result=json.toString();
					return result;
				    
	}
	
	
	//组织单选树，修改父节点时使用，自身及其所有子节点排除。
	@RequestMapping(value="/orgPrtTree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String orgPrtTree(HttpServletRequest request){
		String parentId=request.getParameter("parentId");
        String selfId=request.getParameter("selfId");//自身节点ID
        String query=" WHERE o.id!="+selfId+" AND o.type!='y'  ORDER BY o.orderNum,o.id DESC";		        
		
        List<Organize> modlist=this.orgService.findAll(Organize.class,query);
		LinkedList<Organize> modlinked=new LinkedList<Organize>(modlist); 
		Organize root=this.orgService.findById(Organize.class,Long.valueOf(parentId));
		JSONArray jsonArray = new JSONArray();  
		createTree2(root, modlinked,jsonArray);
		JSONObject json = new JSONObject();
		json.put("treeNodes", jsonArray);
		String result=json.toString();
		return result;						
						        
	}
	
	public void createTree1(Organize root,LinkedList<Organize> modlist,JSONArray jsonArray){
		 TreeNode node1 = new TreeNode();
		 node1.setId(root.getId()+"");
	     node1.setName(root.getOrgName());
	     node1.setMenuType(root.getNodeType());
	     Organize pmod=root.getParent();
	     if(pmod!=null){
	    	 node1.setParentId(pmod.getId()+"");
	     }else{
	    	 node1.setParentId("0");
	     }
	     
	     if(root.getNodeType().equals("y")){
			 node1.setIsParent(Boolean.valueOf(false));
		 }else{
			 if(root.getNodeType().equals("r")){
				 node1.setOpen(true);
			 }
			 if(root.getNodeType().equals("b")){
				 node1.setClickExpand(true);//使非子节点不能被选中
			 }
			 //node1.setNocheck(true);
			 node1.setIsParent(Boolean.valueOf(true));
		 }
	     
	     //node1.setChecked(this.ifModule(rmlist, root));
	     jsonArray.add(node1);
	     List<Organize> children=getChildren(root,modlist);
	     if(children!=null&&children.size()>0){
	    	 for(int i=0;i<children.size();i++){
	    		 Organize mod=children.get(i);
	    		 createTree1(mod,modlist,jsonArray);
	    	 }
	     }else{
	    	 return ;
	     }
	}
				
					
	public void createTree2(Organize root,LinkedList<Organize> modlist,JSONArray jsonArray){
		TreeNode node1 = new TreeNode();
		 node1.setId(root.getId()+"");
		 //node1.setPhone(root.getKey1()+"");
	     node1.setName(root.getOrgName());
	     
	     node1.setMenuType(root.getNodeType());
	     Organize pmod=root.getParent();
	     if(pmod!=null){
	    	 node1.setParentId(pmod.getId()+"");
	     }else{
	    	 node1.setParentId("0");
	     }
	     
	     if(root.getNodeType().equals("y")){
			 node1.setIsParent(Boolean.valueOf(false));
			 node1.setClickExpand(true);//使子节点不能被选中
		 }else{
			 
			 node1.setIsParent(Boolean.valueOf(true));
			 if(root.getNodeType().equals("r")){
				 node1.setOpen(true);
			 }else{
				 
			 }
		 }
	     
	     //node1.setChecked(this.ifModule(rmlist, root));
	     jsonArray.add(node1);
	     List<Organize> children=getChildren(root,modlist);
	     if(children!=null&&children.size()>0){
	    	 for(int i=0;i<children.size();i++){
	    		 Organize mod=children.get(i);
	    		 createTree2(mod,modlist,jsonArray);
	    	 }
	     }else{
	    	 return ;
	     }			 
	}
				
	private List<Organize> getChildren(Organize root, LinkedList<Organize> modlist) {
		Long pid=root.getId();
		List<Organize> childre=new ArrayList<Organize>();
		ListIterator<Organize> listIter=modlist.listIterator();
		while(listIter.hasNext()){
			Organize mod=listIter.next();
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
		
	
	@RequiresPermissions("OrgM:view")
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
	    	List<Organize> list=this.orgService.findByParentId(Long.valueOf(id), " ORDER BY o.orderNum ASC");
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
	
	@RequiresPermissions("OrgM:view")
	@RequestMapping(value="/treelist", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String pId = request.getParameter("parentId");
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		String deptName= request.getParameter("deptName");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		JSONObject json = new JSONObject();
		   
		List<Organize> list;
		String query=" WHERE o.id!=1 ";
		if (StringUtils.isNotBlank(pId)) {
			query=query+"AND o.parent.id ="+pId;
		}
		if (StringUtils.isNotBlank(deptName)) {
			query=query+" AND o.name LIKE '%"+deptName+"%' ";
	    }
		
		query=query+"  ORDER BY "+sort+" "+direction;
		
		list=this.orgService.findAll(query, page);
		
		 String[] excludes={"children","users","news","docs"};
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
	
	@RequiresPermissions("OrgM:edit")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET, RequestMethod.POST})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("orgid");
		Organize porg=this.orgService.findById(Organize.class,Long.valueOf(id));
		Organize org=new Organize();
		org.setParent(porg);
		map.put("organize", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return ADD;
	}
	
	@RequiresPermissions("OrgM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET, RequestMethod.POST})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("orgid");
		Organize org=this.orgService.findById(Organize.class,Long.valueOf(id));
		map.put("organize", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return EDIT;
	}
	
	@RequiresPermissions("OrgM:view")
	@RequestMapping(value="/view", method={RequestMethod.GET, RequestMethod.POST})
	public String orgView(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("orgid");
		Organize org=this.orgService.findById(Organize.class,Long.valueOf(id));
		map.put("organize", org);
		return VIEW;
	}
	
	
	@RequiresPermissions("OrgM:save")
	@RequestMapping(value="/addSave", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String addSave(Organize org ,Map<String, Object> map,HttpServletRequest request ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		Long pid=org.getParent().getId();
		Organize orgp=this.orgService.findById(Organize.class, pid);
		org.setParent(orgp);
		
		
		//String inherit=orgp.getInheritNb()+orgp.getId()+"$";
    	//Organize.setInheritNb(inherit);
    	this.orgService.save(org);
    	json.put("status", "true");
		json.put("message", "添加成功!");
		map.put("organize", org);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("OrgM:edit")
	@RequestMapping(value="/editSave", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String editSave(Organize org ,Map<String, Object> map,HttpServletRequest request ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		
		Long id = org.getId();
		Long pid=org.getParent().getId();
		//Organize orgp=this.orgService.findById(Organize.class, pid);
		
		if (id != null && id>0) {
			this.orgService.update(org);//正常更新
			json.put("status", "true");
			json.put("message", "修改成功!");
			//json.put("Organize", Organize);
	    }else{
	    	json.put("status", "true");
			json.put("message", "请选择修改对象!");
	    	
	    }
		map.put("organize", org);
		
		String result=json.toString();
		return result;
	}
	
	
	
	@RequiresPermissions("OrgM:delete")
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
				this.orgService.delIds(Organize.class,ids);
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
	
	@RequiresPermissions("OrgM:edit")
	@RequestMapping(value="/allowed", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String allowed(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String orgid = request.getParameter("orgid");
		
		if (orgid != null && orgid!="") {
			try{
				//String hql=" DELETE FROM Organize WHERE id IN("+ids+")";
				Organize org=this.orgService.findById(Organize.class, Long.valueOf(orgid));
				
				if(org.getStatus().equals("y")){
					org.setStatus("n");
				}else{
					org.setStatus("y");
				}
				
				
				this.orgService.update(org);
				json.put("status", "true");
				json.put("message", "成功!");
			}catch(Exception e){
				json.put("status", "false");
				json.put("message", e.toString());
			}
	    }else{
	    	json.put("status", "false");
			json.put("message", "请选择节点!");
	    }
		
		String result=json.toString();
		return result;
	}

}
