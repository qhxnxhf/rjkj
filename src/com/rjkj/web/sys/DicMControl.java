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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.DicDao;
import com.rjkj.entities.Dic;
import com.rjkj.web.interceptor.CsrfTokenUtils;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;





@Controller
@RequestMapping("/sys/dic")
public class DicMControl {
	private static final Log log = LogFactory.getLog(DicMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private DicDao dicService;
	
	private static final String ADD = "sys/dic/DicAdd";
	private static final String EDIT = "sys/dic/DicEdit";
	
	
	//组织机构单选树
	@RequestMapping(value="/dicTree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getDicTree(@RequestParam("parentId") String parentId, HttpServletRequest request){
				
				List<Dic> modlist=this.dicService.findAll(Dic.class," ORDER BY o.orderNum,o.id DESC ");
				LinkedList<Dic> modlinked=new LinkedList<Dic>(modlist); 
				JSONArray jsonArray = new JSONArray();   
				Dic root=this.dicService.findById(Dic.class,Long.valueOf(parentId));
				createTree(root, modlinked,jsonArray);
				JSONObject json = new JSONObject();
				json.put("treeNodes", jsonArray);
				String result=json.toString();
				return result;
			    
			}
			
				
			public void createTree(Dic root,LinkedList<Dic> modlist,JSONArray jsonArray){
				 TreeNode node1 = new TreeNode();
				 node1.setId(root.getId()+"");
				 node1.setPhone(root.getDicKey()+"");
			     node1.setName(root.getDicName());
			     
			     node1.setMenuType(root.getNodeType());
			     Dic pmod=root.getParent();
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
			     List<Dic> children=getChildren(root,modlist);
			     if(children!=null&&children.size()>0){
			    	 for(int i=0;i<children.size();i++){
			    		 Dic mod=children.get(i);
			    		 createTree(mod,modlist,jsonArray);
			    	 }
			     }else{
			    	 return ;
			     }
			}
			
			private List<Dic> getChildren(Dic root, LinkedList<Dic> modlist) {
				Long pid=root.getId();
				List<Dic> childre=new ArrayList<Dic>();
				ListIterator<Dic> listIter=modlist.listIterator();
				while(listIter.hasNext()){
					Dic mod=listIter.next();
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
	
	
	@RequiresPermissions("DicM:view")
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      
	      Dic org=this.dicService.findById(Dic.class,1L);
	      
	      node1.setId(org.getId()+"");
	      node1.setName(org.getDicName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	     // node1.setOldParentId(null);
	      node1.setDesc(org.getDicBrief());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<Dic> list=this.dicService.findByParentId(Long.valueOf(id), " ORDER BY o.orderNum ASC");
	    	if(list!=null&&list.size()>0){
	    		for (int i = 0; i <list.size(); i++) {
	    			Dic org=list.get(i);
	    			
	    			TreeNode node1 = new TreeNode();
	    			node1.setId(org.getId()+"");
	    		    node1.setName(org.getDicName());
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
	
	@RequiresPermissions("DicM:view")
	@RequestMapping(value="/treelist", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
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
		   
		List<Dic> list;
		//Long ids;
		if (id == null || "".equals(id.trim())) {
			id=1+"";
	    }
		//list=this.dicService.findByParent(ids, "");
		
		if(deptName !=null &&!deptName.equals("")){
			list=this.dicService.findAll(" WHERE o.parent.id ="+id+" AND o.dicName LIKE '%"+deptName+"%' "+"  ORDER BY "+sort+" "+direction, page);
		}else{
			list=this.dicService.findAll(" WHERE o.parent.id ="+id+" ORDER BY "+sort+" "+direction, page);
		}
		
		
		
		
		 String[] excludes={"children","users"};
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
	
	@RequiresPermissions("DicM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET, RequestMethod.POST})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("orgid");
		Dic porg=this.dicService.findById(Dic.class,Long.valueOf(id));
		Dic org=new Dic();
		org.setParent(porg);
		map.put("dic", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return ADD;
	}
	
	@RequiresPermissions("DicM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET, RequestMethod.POST})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("orgid");
		Dic org=this.dicService.findById(Dic.class,Long.valueOf(id));
		map.put("dic", org);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return EDIT;
	}
	
	
	
	@RequiresPermissions("DicM:save")
	@RequestMapping(value="/saveAdd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String saveAdd(Dic dic ,Map<String, Object> map, HttpServletRequest request ){
		
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		Dic org=this.dicService.findById(Dic.class,dic.getParent().getId());
		//dic.setPathkey(org.getPathkey()+org.getKey1()+"/");
	    this.dicService.save(dic);
	    json.put("status", "true");
		json.put("message", "添加成功!");
		map.put("dic", dic);
		String result=json.toString();
		return result;
	}
	
	@RequiresPermissions("DicM:edit")
	@RequestMapping(value="/saveEdit", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String saveEdit(Dic dic ,Map<String, Object> map, HttpServletRequest request ){
		Long id = dic.getId();
		
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		if (id != null && id>0) {
			this.dicService.update(dic);
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
	
	@RequiresPermissions("DicM:delete")
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
				//String hql=" DELETE FROM Dic WHERE id IN("+ids+")";
				this.dicService.delIds(Dic.class, ids);//.update(hql);
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
	
	
	//组织机构单选框
	@RequestMapping(value="/dicSelect", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getDicSelect(@RequestParam("parentId") String parentId, HttpServletRequest request){
					
				JSONObject json = new JSONObject();
				
				JSONArray jsonArray=new JSONArray();
				List<Dic> dics=this.dicService.findByParentId(Long.valueOf(parentId), " AND status='y' ORDER BY o.orderNum,o.id DESC ");
				
				for(Dic dic:dics){
					JSONObject js = new JSONObject();
					
					js.put("key", dic.getDicName());
					js.put("value", dic.getId());
					js.put("value1", dic.getValue1());
					js.put("value2", dic.getValue2());
					jsonArray.add(js);
				}
					
				json.put("list", jsonArray);
				String result=json.toString();
				return result;
				    
		}
				
					
				
	

}
