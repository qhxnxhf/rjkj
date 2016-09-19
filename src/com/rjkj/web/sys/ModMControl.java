package com.rjkj.web.sys;

import java.util.List;
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

import com.rjkj.dao.ModuleDao;
import com.rjkj.entities.Module;
import com.rjkj.web.interceptor.CsrfTokenUtils;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;





@Controller
@RequestMapping("/sys/mod")
public class ModMControl {
	private static final Log log = LogFactory.getLog(OrgMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private ModuleDao modService;
	
	private static final String ADD = "sys/mod/ModAdd";
	private static final String EDIT = "sys/mod/ModEdit";
	private static final String VIEW = "sys/mod/ModView";
	
	
	@RequiresPermissions("ModM:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      
	      Module org=this.modService.findById(Module.class,1L);
	      
	      node1.setId(org.getId()+"");
	      node1.setName(org.getModName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	      //node1.setOldParentId(null);
	      //node1.setDesc(org.getDescription());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<Module> list=this.modService.findByParentId(Long.valueOf(id), " AND o.nodeType !='y'  ORDER BY o.orderNum ASC");
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
	    		   // node1.setOldParentId(null);
	    		   // node1.setDesc(org.getDescription());
	    		    //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	    		    jsonArray.add(node1);
	    		}
	    	}
	    } 
		
		String json=jsonArray.toString();
		return json;
	}
	
	@RequiresPermissions("ModM:view")
	@RequestMapping(value="/treelist", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String id = request.getParameter("parentId");
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		String modName= request.getParameter("modName");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		JSONObject json = new JSONObject();
		  
		List<Module> list;
		
		if (id == null || "".equals(id.trim())) {
			id=1+"";
	    }
		
		if(modName !=null &&!modName.equals("")){
			list=this.modService.findAll(" WHERE o.parent.id ="+id+" AND o.modName LIKE '%"+modName+"%' "+"  ORDER BY "+sort+" "+direction, page);
			
		}else{
			list=this.modService.findAll(" WHERE o.parent.id ="+id+" ORDER BY "+sort+" "+direction, page);
		}
		
		String[] excludes={"children","roleModule"};
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
	
	@RequiresPermissions("ModM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET, RequestMethod.POST})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("modid");
		String type = request.getParameter("type");
		Module porg=this.modService.findById(Module.class,Long.valueOf(id));
		Module org=new Module();
		org.setParent(porg);
		org.setNodeType(type);
		map.put("module", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return ADD;
	}
	
	@RequiresPermissions("ModM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET, RequestMethod.POST})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("modid");
		Module org=this.modService.findById(Module.class,Long.valueOf(id));
		map.put("module", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return EDIT;
	}
	
	@RequiresPermissions("ModM:view")
	@RequestMapping(value="/view", method={RequestMethod.GET, RequestMethod.POST})
	public String orgView(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("modid");
		Module org=this.modService.findById(Module.class,Long.valueOf(id));
		map.put("module", org);
		return VIEW;
	}
	
	
	@RequiresPermissions("ModM:save")
	@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String add(Module Module ,Map<String, Object> map,HttpServletRequest request ){
		
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		if(Module.getNodeType().equals("y")){
			Module.setPermission(Module.getParent().getSn()+":"+Module.getSn());
		}else{
			Module.setPermission("");
		}
		
	    this.modService.save(Module);
	    json.put("status", "true");
		json.put("message", "添加成功!");
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("ModM:save")
	@RequestMapping(value="/add2", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String add2(Map<String, Object> map ,HttpServletRequest request ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String id = request.getParameter("modid");
		Module porg=this.modService.findById(Module.class,Long.valueOf(id));
		Module module1=new Module();
		module1.setParent(porg);
		module1.setModName("添加");
		//module1.setPriority(1);
		module1.setSn(Module.PERMISSION_CREATE);
		module1.setPermission(porg.getSn()+":"+Module.PERMISSION_CREATE);
		module1.setNodeType("y");
		module1.setUrl("#");
		this.modService.save(module1);
		 
		Module module2=new Module();
		module2.setParent(porg);
		module2.setModName("修改");
		//module2.setPriority(2);
		module2.setSn(Module.PERMISSION_UPDATE);
		module2.setPermission(porg.getSn()+":"+Module.PERMISSION_UPDATE);
		module2.setNodeType("y");
		module2.setUrl("#");
		this.modService.save(module2);
		
		Module module3=new Module();
		module3.setParent(porg);
		module3.setModName("查询");
		//module3.setPriority(3);
		module3.setSn(Module.PERMISSION_SEARCH);
		module3.setPermission(porg.getSn()+":"+Module.PERMISSION_SEARCH);
		module3.setNodeType("y");
		module3.setUrl("#");
		this.modService.save(module3);
		
		Module module4=new Module();
		module4.setParent(porg);
		module4.setModName("删除");
		//module4.setPriority(4);
		module4.setSn(Module.PERMISSION_DELETE);
		module4.setPermission(porg.getSn()+":"+Module.PERMISSION_DELETE);
		module4.setNodeType("y");
		module4.setUrl("#");
		this.modService.save(module4);
		
		Module module5=new Module();
		module5.setParent(porg);
		module5.setModName("批量删除");
		//module5.setPriority(5);
		module5.setSn(Module.PERMISSION_BATCHDEL);
		module5.setPermission(porg.getSn()+":"+Module.PERMISSION_BATCHDEL);
		module5.setNodeType("y");
		module5.setUrl("#");
		this.modService.save(module5);
		
		Module module6=new Module();
		module6.setParent(porg);
		module6.setModName("查看");
		//module6.setPriority(6);
		module6.setSn(Module.PERMISSION_READ);
		module6.setPermission(porg.getSn()+":"+Module.PERMISSION_READ);
		module6.setNodeType("y");
		module6.setUrl("#");
		this.modService.save(module6);
		
		
		
	    json.put("status", "true");
		json.put("message", "添加成功!");
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("ModM:edit")
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(Module Module ,Map<String, Object> map,HttpServletRequest request ){
		
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		Module pmod=Module.getParent();
		String type=pmod.getNodeType();
		if(type.equals("m")){
			String sn=pmod.getSn();
			sn=sn+":"+Module.getSn();
			Module.setPermission(sn);
		}else{
			Module.setPermission("");
		}
		this.modService.update(Module);
		json.put("status", "true");
		json.put("message", "修改成功!");
		map.put("module", Module);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("ModM:delete")
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
				
				this.modService.delIds(Module.class,ids);
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
