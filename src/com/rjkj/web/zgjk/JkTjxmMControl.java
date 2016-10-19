package com.rjkj.web.zgjk;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.JkTjxmDao;
import com.rjkj.entities.JkTjxm;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;
import com.rjkj.web.interceptor.CsrfTokenUtils;

@Controller
@RequestMapping("/zgjk/tjxm")
public class JkTjxmMControl {
	
	private static final Log log = LogFactory.getLog(JkTjxmMControl.class);  
	
	
	@Autowired
	private JkTjxmDao tjxService;
	
	
	//栏目单选树
	
	@RequestMapping(value="/tjxTree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getLmTree(@RequestParam("parentId") String parentId, HttpServletRequest request){
				
		List<JkTjxm> modlist=this.tjxService.findAll(JkTjxm.class," ORDER BY o.orderNum,o.id DESC ");
		LinkedList<JkTjxm> modlinked=new LinkedList<JkTjxm>(modlist); 
		JkTjxm root=this.tjxService.findById(JkTjxm.class,Long.valueOf(parentId));
		JSONArray jsonArray = new JSONArray();  
		createTree(root, modlinked,jsonArray);
		JSONObject json = new JSONObject();
		json.put("treeNodes", jsonArray);
		String result=json.toString();
		return result;
	}
	
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      
	      JkTjxm org=this.tjxService.findById(JkTjxm.class,1L);
	      
	      node1.setId(org.getId()+"");
	      node1.setName(org.getName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	     // node1.setOldParentId(null);
	      node1.setDesc(org.getShortName());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<JkTjxm> list=this.tjxService.findByParentId(Long.valueOf(id), " ORDER BY o.orderNum ASC");
	    	if(list!=null&&list.size()>0){
	    		for (int i = 0; i <list.size(); i++) {
	    			JkTjxm org=list.get(i);
	    			
	    			TreeNode node1 = new TreeNode();
	    			node1.setId(org.getId()+"");
	    		    node1.setName(org.getName());
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
	
	//@RequiresPermissions("LmM:view")
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTable(HttpServletRequest request){
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
		   
		List<JkTjxm> list;
		//Long ids;
		if (id == null || "".equals(id.trim())) {
			id=1+"";
	    }
		//list=this.sortService.findByParent(ids, "");
		
		if(deptName !=null &&!deptName.equals("")){
			list=this.tjxService.findAll(" WHERE o.parent.id ="+id+" AND o.name LIKE '%"+deptName+"%' "+"  ORDER BY "+sort+" "+direction, page);
		}else{
			list=this.tjxService.findAll(" WHERE o.parent.id ="+id+" ORDER BY "+sort+" "+direction, page);
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
	
	
	//@RequiresPermissions("LmM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		//HttpSession session = request.getSession(true);  
		//User user=(User)session.getAttribute("LoginUser"); 
		//JkTjxm org=user.getOrg();
		
		String id = request.getParameter("orgid");
		String url = request.getParameter("url");
		JkTjxm porg=this.tjxService.findById(JkTjxm.class,Long.valueOf(id));
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		JkTjxm sort=new JkTjxm();
		sort.setSxValueH(0.0);
		sort.setSxValueL(0.0);
		sort.setParent(porg);
		map.put("dic", sort);
		return url;
		
	}
	
	//@RequiresPermissions("LmM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		
		String id = request.getParameter("orgid");
		String url = request.getParameter("url");
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		JkTjxm sort=this.tjxService.findById(JkTjxm.class,Long.valueOf(id));
		map.put("dic", sort);
		return url;
		
		
	}
	
	//@RequiresPermissions("LmM:save")
	@RequestMapping(value="/saveAdd", method={RequestMethod.POST})
	public @ResponseBody String saveAdd(JkTjxm dic ,Map<String, Object> map ,HttpServletRequest request){
		
		
		
		JSONObject json = new JSONObject();
		
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
	   
		this.tjxService.save(dic);
	    json.put("status", "true");
		json.put("message", "添加成功!");
		map.put("dic", dic);
		String result=json.toString();
		return result;
	}
	
	//@RequiresPermissions("LmM:edit")
	@RequestMapping(value="/saveEdit", method={RequestMethod.POST})
	public @ResponseBody String saveEdit(JkTjxm dic ,Map<String, Object> map,HttpServletRequest request ){
		Long id = dic.getId();
		
		JSONObject json = new JSONObject();
		
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		if (id != null && id>0) {
			this.tjxService.update(dic);//更新
			json.put("status", "true");
			json.put("message", "修改成功!");
			//json.put("JkTjxm", JkTjxm);
	    }else{
	    	json.put("status", "false");
			json.put("message", "修改失败!");
	    }
		map.put("dic", dic);
		
		String result=json.toString();
		return result;
	}
	
	
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
				this.tjxService.delIds(JkTjxm.class, ids);//.update(hql);
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
	
	
	//生成目录树，			
	public void createTree(JkTjxm root,LinkedList<JkTjxm> modlist,JSONArray jsonArray){
					 TreeNode node1 = new TreeNode();
					 node1.setId(root.getId()+"");
					 //node1.setPhone(root.getKey1()+"");
				     node1.setName(root.getName());
				     
				     node1.setMenuType(root.getNodeType());
				     JkTjxm pmod=root.getParent();
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
				     List<JkTjxm> children=getChildren(root,modlist);
				     if(children!=null&&children.size()>0){
				    	 for(int i=0;i<children.size();i++){
				    		 JkTjxm mod=children.get(i);
				    		 createTree(mod,modlist,jsonArray);
				    	 }
				     }else{
				    	 return ;
				     }
		}
	
	private List<JkTjxm> getChildren(JkTjxm root, LinkedList<JkTjxm> modlist) {
		Long pid=root.getId();
		List<JkTjxm> childre=new ArrayList<JkTjxm>();
		ListIterator<JkTjxm> listIter=modlist.listIterator();
		while(listIter.hasNext()){
			JkTjxm mod=listIter.next();
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
	

}
