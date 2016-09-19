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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.DicDao;
import com.rjkj.dao.ModuleDao;
import com.rjkj.dao.RoleDao;
import com.rjkj.dao.RoleModuleDao;
import com.rjkj.entities.Module;
import com.rjkj.entities.Role;
import com.rjkj.web.interceptor.CsrfTokenUtils;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;


@Controller
@RequestMapping("/sys/role")
public class RoleMControl {
	
	private static final Log log = LogFactory.getLog(OrgMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private RoleDao roleService;
	
	@Autowired
	private ModuleDao modService;
	
	@Autowired
	private RoleModuleDao rmService;
	
	//@Autowired
	//RepositoryService repositoryService;
	
	@Autowired
	private DicDao dicService;
	
	private static final String ADD = "sys/role/RoleAdd";
	private static final String EDIT = "sys/role/RoleEdit";
	private static final String MODULE = "sys/role/RoleModule";
	
	private static final String MODULE2 = "sys/role/ModTree";
	private static final String FLOW = "sys/role/RoleFlow";
	
	public boolean ifModule(List<Module> modList,Module mod){
		boolean flag=false;
		if(modList!=null&&modList.size()>0){
			for(int i=0;i<modList.size();i++){
				Module mod1=modList.get(i);
				if(mod1.getId()-mod.getId()==0){
					flag=true;
					break;
				}
				
				
			}
		}
		return flag;
	}
	
	
	@RequiresPermissions("RoleM:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		
		String id = request.getParameter("id");
		String roleId = request.getParameter("roleid");
		
		List<Module> modList=this.rmService.findByRole(roleId, "");
		
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      Module org=this.modService.findById(Module.class,1L);
	      node1.setId(org.getId()+"");
	      node1.setName(org.getModName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	      node1.setChecked(this.ifModule(modList, org));
	      jsonArray.add(node1);
	     
	    }else{
	    	List<Module> list=this.modService.findByParentId(Long.valueOf(id), "");
	    	if(list!=null&&list.size()>0){
	    		for (int i = 0; i <list.size(); i++) {
	    			Module org=list.get(i);
	    			TreeNode node1 = new TreeNode();
	    			node1.setId(org.getId()+"");
	    		    node1.setName(org.getModName());
	    		    node1.setMenuType(org.getNodeType());
	    		    if(org.getNodeType().equals("y")){
	    				 node1.setIsParent(Boolean.valueOf(false));
	    			}else{
	    				 node1.setIsParent(Boolean.valueOf(true));
	    			}
	    		    node1.setChecked(this.ifModule(modList, org));
	    		    jsonArray.add(node1);
	    		}
	    	}
	    } 
		String json=jsonArray.toString();
		return json;
	}
	
	
	public void createTree(Module root,LinkedList<Module> modlist,List<Module> rmlist,JSONArray jsonArray){
		 TreeNode node1 = new TreeNode();
		 node1.setId(root.getId()+"");
	     node1.setName(root.getModName());
	     node1.setMenuType(root.getNodeType());
	     Module pmod=root.getParent();
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
			 }else{
				 
			 }
			 node1.setIsParent(Boolean.valueOf(true));
		 }
	     
	     node1.setChecked(this.ifModule(rmlist, root));
	     jsonArray.add(node1);
	     List<Module> children=getChildren(root,modlist);
	     if(children!=null&&children.size()>0){
	    	 for(int i=0;i<children.size();i++){
	    		 Module mod=children.get(i);
	    		 createTree(mod,modlist,rmlist,jsonArray);
	    	 }
	     }else{
	    	 return ;
	     }
	     
		
	}
	
	private List<Module> getChildren(Module root, LinkedList<Module> modlist) {
		Long pid=root.getId();
		List<Module> childre=new ArrayList<Module>();
		ListIterator<Module> listIter=modlist.listIterator();
		while(listIter.hasNext()){
			Module mod=listIter.next();
			if(mod.getId()==pid){
				listIter.remove();
			}
			if(mod.getParent()!=null&&mod.getParent().getId()!=null&&mod.getParent().getId()==pid){
				childre.add(mod);
				listIter.remove();
			}
			
			
		}
		return childre;
	}

	
	@RequiresPermissions("RoleM:view")
	@RequestMapping(value="/treeAll", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeAll(HttpServletRequest request){
		
		String roleId = request.getParameter("roleid");
		List<Module> rmlist=this.rmService.findByRole(roleId, "");
		List<Module> modlist=this.modService.findAll(Module.class," ORDER BY o.orderNum,o.id DESC ");
		LinkedList<Module> modlinked=new LinkedList<Module>(modlist); 
		JSONArray jsonArray = new JSONArray();   
		Module root=this.modService.findById(Module.class,1L);
		//createTree(root, modlist,rmlist,jsonArray);
		createTree(root, modlinked,rmlist,jsonArray);
		JSONObject json = new JSONObject();
		json.put("treeNodes", jsonArray);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("RoleM:view")
	@RequestMapping(value="/rolelist", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getRoleList(HttpServletRequest request){
		
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		String roleName= request.getParameter("roleName");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		JSONObject json = new JSONObject();
		   
		List<Role> list;
		
		if(roleName !=null &&!roleName.equals("")){
			list=this.roleService.findAll(" WHERE  o.roleName LIKE '%"+roleName+"%' "+"  ORDER BY "+sort+" "+direction, page);
		}else{
			list=this.roleService.findAll(" ORDER BY "+sort+" "+direction, page);
		}
		
		
		String[] excludes={"userRoles","roleModule"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		   //调用ExtHelper将你的JSONConfig传递过去  
		   JSONArray jsonArray=JSONArray.fromObject(list, config);
		
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("RoleM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET, RequestMethod.POST})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("roleid");
		Role org=new Role();
		map.put("role", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return ADD;
	}
	
	@RequiresPermissions("RoleM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET, RequestMethod.POST})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("roleid");
		Role org=this.roleService.findById(Role.class,Long.valueOf(id));
		map.put("role", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return EDIT;
	}
	
	//@RequiresPermissions("RoleM:module")
	@RequestMapping(value="/preModule", method={RequestMethod.GET, RequestMethod.POST})
	public String orgView(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("roleid");
		Role org=this.roleService.findById(Role.class,Long.valueOf(id));
		map.put("role", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return MODULE;
	}
	
	//@RequiresPermissions("RoleM:module")
	@RequestMapping(value="/preModule2", method={RequestMethod.GET, RequestMethod.POST})
	public String orgView2(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("roleid");
		Role org=this.roleService.findById(Role.class,Long.valueOf(id));
		map.put("role", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return MODULE2;
	}
	
	
	
	
	
	@RequiresPermissions("RoleM:save")
	@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String add(Role role ,Map<String, Object> map,HttpServletRequest request ){
		
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		role.setStatus("y");
	    this.roleService.save(role);
	    json.put("status", "true");
		json.put("message", "添加成功!");
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("RoleM:edit")
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(Role role ,Map<String, Object> map,HttpServletRequest request ){
		Long id = role.getId();
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		if (id != null && id>0) {
			this.roleService.update(role);
			json.put("status", "true");
			json.put("message", "修改成功!");
			//json.put("Module", Module);
	    }else{
	    	json.put("status", "true");
			json.put("message", "修改失败!");
			//json.put("Module", Module);
	    }
		map.put("role", role);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("RoleM:edit")
	@RequestMapping(value="/update2", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update2(HttpServletRequest request ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String modids = request.getParameter("ids");
		String roleid = request.getParameter("id");
		
		if(roleid!=null&&!roleid.equals("")){
			this.rmService.saveRoleModule(roleid, modids);
			json.put("status", "true");
			json.put("message", "修改成功!");
		}else{
			json.put("status", "true");
			json.put("message", "修改失败!");
		}
		
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("RoleM:delete")
	@RequestMapping(value="/del", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String del(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		String ids = request.getParameter("ids");
		
		if (ids != null && ids!="") {
			try{
				this.roleService.delIds(Role.class,ids);
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

}
