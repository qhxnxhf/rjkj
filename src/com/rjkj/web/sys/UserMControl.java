package com.rjkj.web.sys;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.OrgDao;
import com.rjkj.dao.RoleDao;
import com.rjkj.dao.UserDao;
import com.rjkj.entities.Organize;
import com.rjkj.entities.Role;
import com.rjkj.entities.User;
import com.rjkj.web.interceptor.CsrfTokenUtils;
import com.rjkj.shiro.ShiroDbRealm;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.util.web.TreeNode;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

@Controller
@RequestMapping("/sys/user")
public class UserMControl {
	private static final Log log = LogFactory.getLog(OrgMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private OrgDao orgService;
	
	@Autowired
	private UserDao userService;
	
	@Autowired
	private RoleDao roleService;
	
	//@Autowired
	//ProcessEngine processEngine;
	
	private static final String ADD = "sys/user/UserAdd";
	private static final String EDIT = "sys/user/UserEdit";
	private static final String VIEW = "sys/user/UserView";
	private static final String ROLE = "sys/user/UserRoles";
	private static final String PSWD = "sys/user/PswdSet";
	
	//组织机构树
	
	@RequestMapping(value="/tree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTree(HttpServletRequest request){
		
		String id = request.getParameter("id");
		JSONArray jsonArray = new JSONArray();   
		
		if (id == null || "".equals(id.trim())) {
	      TreeNode node1 = new TreeNode();
	      HttpSession session = request.getSession(true);  
	      User user=(User)session.getAttribute("LoginUser"); 
	        Organize org=user.getOrg();
	     // Organize org=this.orgService.findById(Organize.class,1L);
	      
	      node1.setId(org.getId()+"");
	      node1.setName(org.getOrgName());
	      node1.setMenuType(org.getNodeType());
	      node1.setIsParent(Boolean.valueOf(true));
	     // node1.setOldParentId(null);
	      node1.setDesc(org.getOrgBrief());
	      //node1.setUrl(request.getContextPath()+"/system/manage/orgm/treelist");
	      jsonArray.add(node1);
	     
	    }else{
	    	List<Organize> list=this.orgService.findByParentId(Long.valueOf(id), " AND o.status='y' ORDER BY o.orderNum ASC");
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
	
	//角色列表
	
	@RequestMapping(value="/roles", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getRoles(HttpServletRequest request){
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();    
		List<Role> list=this.roleService.findAll(Role.class);
		if(list!=null&&list.size()>0){
    		for (int i = 0; i <list.size(); i++) {
    			Role org=list.get(i);
    			TreeNode node1 = new TreeNode();
    			node1.setId(org.getId()+"");
    		    node1.setName(org.getRoleName());
    		    node1.setParentId("0");
    		    jsonArray.add(node1);
    		}
    	}
		json.put("treeNodes", jsonArray);
		String result=json.toString();
		return result;
	    
	}
	
	//组织机构单选表
	
	@RequestMapping(value="/orgtree", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getOrgTree(HttpServletRequest request){
		String id = request.getParameter("orgId");
		Long orgId=1L;
		if(id!=null&&!id.equals("")){
			orgId=Long.valueOf(id);
		}
		
		List<Organize> modlist=this.orgService.findAll(Organize.class," ORDER BY o.orderNum,o.id DESC ");
		LinkedList<Organize> modlinked=new LinkedList<Organize>(modlist); 
		JSONArray jsonArray = new JSONArray();   
		Organize root=this.orgService.findById(Organize.class,orgId);
		createTree(root, modlinked,jsonArray);
		JSONObject json = new JSONObject();
		json.put("treeNodes", jsonArray);
		String result=json.toString();
		return result;
	    
	}
	
		
	public void createTree(Organize root,LinkedList<Organize> modlist,JSONArray jsonArray){
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
			 }else{
				 
			 }
			 node1.setNocheck(true);
			// node1.setClickExpand(true);//使非子节点不能被选中
			 node1.setIsParent(Boolean.valueOf(true));
		 }
	     
	     //node1.setChecked(this.ifModule(rmlist, root));
	     jsonArray.add(node1);
	     List<Organize> children=getChildren(root,modlist);
	     if(children!=null&&children.size()>0){
	    	 for(int i=0;i<children.size();i++){
	    		 Organize mod=children.get(i);
	    		 createTree(mod,modlist,jsonArray);
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
	
	//用户信息表格
	@RequiresPermissions("UserM:view")
	@RequestMapping(value="/userlist", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String deptId = request.getParameter("parentId");
		String fs = request.getParameter("fs");
		String sort= request.getParameter("sort");
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		String userName= request.getParameter("userName");
		String userStatus= request.getParameter("userStatus");
		String wjs= request.getParameter("wjs");
		String role= request.getParameter("userRole");
		
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		JSONObject json = new JSONObject();
		List<User> list;
		String query=" WHERE 1=1 ";
		if (StringUtils.isNotBlank(deptId)) {
			if(fs!=null&&fs.equals("1")){
				String orgs=this.orgService.getOrgIdsByCase(Long.valueOf(deptId));
				query=query+"AND o.org.id IN ("+orgs+") ";
			}else{
				query=query+"AND o.org.id="+deptId;
			}
		}
		
		if (StringUtils.isNotBlank(userName)) {
			query=query+"AND o.realname LIKE '%"+userName+"%' ";
	    }
		
		if (userStatus!=null&&!userStatus.equals("0")) {
			query=query+"AND o.status ='"+userStatus+"' ";
	    }
		
		if(StringUtils.isNotBlank(wjs)&&wjs.equals("1")){
			query=query+"AND (o.roles = '' OR o.roles IS NULL)";
		}else{
			if (StringUtils.isNotBlank(role)) {
				query=query+"AND o.roles = '"+role+"' ";
		    }
		}
		
		
		query=query+"  ORDER BY "+sort+" "+direction;
		
		list=this.userService.findAll(query,page);
		
		String[] excludes={"userRoles","children","users","docs","goods"};
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
	
	//打开新增用户界面
	@RequiresPermissions("UserM:save")
	@RequestMapping(value="/preAdd", method={RequestMethod.GET, RequestMethod.POST})
	public String preAdd(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("orgid");
		Organize org=this.orgService.findById(Organize.class,Long.valueOf(id));
		User user=new User();
		user.setOrg(org);
		map.put("user", user);
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return ADD;
	}
	
	
	@RequestMapping(value="/synAllUserToAct", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody  String synAllUserToAct(HttpServletRequest request ){
		JSONObject json = new JSONObject();
		try {
			//this.userService.synAllUserAndRoleToActiviti();
			json.put("status", "true");
			json.put("message", "同步成功");
		} catch (Exception e) {
			json.put("status", "false");
			json.put("message", e.toString());
			e.printStackTrace();
		}
		String result=json.toString();
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		return result;
	}
	
	//打开用户修改界面
	//@RequiresPermissions("UserM:edit")
	@RequestMapping(value="/preEdit", method={RequestMethod.GET, RequestMethod.POST})
	public String preEdit(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("userid");
		User user=this.userService.findById(User.class,Long.valueOf(id));
		List<Role> roles=userService.findRoleByUser(user, "");
		String rolesel="";
		if(roles!=null&&roles.size()>0){
			for(int i=0;i<roles.size();i++){
				Role role=roles.get(i);
				rolesel=rolesel+","+role.getId();
			}
			rolesel=rolesel.trim();
			rolesel=rolesel.substring(1);
		}
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		map.put("user", user);
		map.put("rolesel", rolesel);
		return EDIT;
	}
	
	//得到用户拥有角色
	@RequiresPermissions("UserM:role")
	@RequestMapping(value="/preUserRoles", method={RequestMethod.GET, RequestMethod.POST})
	public String preRole(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("userid");
		User user=this.userService.findById(User.class,Long.valueOf(id));
		List<Role> roles=userService.findRoleByUser(user, "");
		String rolesel="";
		if(roles!=null&&roles.size()>0){
			for(int i=0;i<roles.size();i++){
				Role role=roles.get(i);
				rolesel=rolesel+","+role.getId();
			}
			rolesel=rolesel.trim();
			rolesel=rolesel.substring(1);
		}
		request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
		
		map.put("user", user);
		map.put("rolesel", rolesel);
		return ROLE;
	}
	
	//查看用户信息
	@RequiresPermissions("UserM:view")
	@RequestMapping(value="/view", method={RequestMethod.GET, RequestMethod.POST})
	public String orgView(Map<String, Object> map ,HttpServletRequest request ){
		String id = request.getParameter("userid");
		User user=this.userService.findById(User.class,Long.valueOf(id));
		List<Role> roles=userService.findRoleByUser(user, "");
		String rolesel="";
		if(roles!=null&&roles.size()>0){
			for(int i=0;i<roles.size();i++){
				Role role=roles.get(i);
				rolesel=rolesel+","+role.getId();
			}
			rolesel=rolesel.trim();
			rolesel=rolesel.substring(1);
		}
		
		map.put("user", user);
		map.put("rolesel", rolesel);
		return VIEW;
	}
	
	//新增用户保存
	@RequiresPermissions("UserM:save")
	@RequestMapping(value="/add", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String add(User user ,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		try {	
				
				user.setCreateTime(new Date());
				user.setPlainPassword("000000");
				//user.setRoles("");
				//user.setInheritNb(user.getInheritNb()+user.getOrganize().getId()+"$");
				this.userService.saveUser(user);
				json.put("status", "true");
				json.put("message", "添加成功!");
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				json.put("status", "true");
				json.put("message", "失败！");
			};
	    	
			//json.put("Organize", Organize);
	    
		
		
		String result=json.toString();
		return result;
	}
	
	//用户修改保存
	//@RequiresPermissions("UserM:edit")
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(User user ,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		User user1=this.userService.findById(User.class,user.getId());
		user1.setAllowIp(user.getAllowIp());
		user1.setEmail(user.getEmail());
		user1.setOrg(user.getOrg());
		user1.setMobile(user.getMobile());
		user1.setUserName(user.getUserName());
		user1.setSex(user.getSex());
		user1.setLoginName(user.getLoginName());
		this.userService.update(user1);
		json.put("status", "true");
		json.put("message", "修改成功!");
		String result=json.toString();
		return result;
	}
	
	//用户角色设定保存
	@RequiresPermissions("UserM:role")
	@RequestMapping(value="/userRoles", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String userRoles(User user ,HttpServletRequest request){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String userid=request.getParameter("id");
		String roleIds=request.getParameter("roleIds");
		this.userService.saveUserRoles(userid, roleIds);
		
		json.put("status", "true");
		json.put("message", "修改成功!");
		String result=json.toString();
		return result;
	}
	
	//重置用户密码
	@RequiresPermissions("UserM:pswd")
	@RequestMapping(value="/pswd", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String pswd(HttpServletRequest request  ,Map<String, Object> map ){
				
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		String id = request.getParameter("userid");
				
				if (id != null && id!="") {
					try{
						this.userService.resetPswd(Long.valueOf(id),"000000");
						json.put("status", "true");
						json.put("message", "重置密码成功!");
					}catch(Exception e){
						json.put("status", "false");
						json.put("message", "重置密码失败！");
					}
			    }else{
			    	json.put("status", "false");
					json.put("message", "请选择!");
			    }
				
				String result=json.toString();
				return result;
		}
	
	
		
	//设置用户密码
	
	@RequestMapping(value="/prePswdSet", method={RequestMethod.GET, RequestMethod.POST})
	public String prePswdSet(HttpServletRequest request  ,Map<String, Object> map ){
		
		String id = request.getParameter("userid");
						User user=this.userService.findById(User.class,Long.valueOf(id));
						map.put("user", user);
						request.setAttribute(CsrfTokenUtils.CSRF_TOKEN_NAME, CsrfTokenUtils.getTokenForSession(request.getSession()));
						
						return PSWD;
	}
		
	//设置用户密码
	
	@RequestMapping(value="/pswdSet", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String pswdSet(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String id = request.getParameter("id");
					String oldpswd = request.getParameter("oldpswd");
					String plainPswd = request.getParameter("plainPswd");
					User user=this.userService.findById(User.class,Long.valueOf(id));
					
					if(ShiroDbRealm.validatePassword(oldpswd, user.getPassword(), user.getSalt())){
						
						this.userService.resetPswd(Long.valueOf(id),plainPswd);
						user=this.userService.findById(User.class,Long.valueOf(id));
						request.getSession().setAttribute("LoginUser",user);
						
						json.put("status", "true");
						json.put("message", "重置密码成功!");
					}else{
						json.put("status", "false");
						json.put("message", "不能重置密码！");
					}
					
					String result=json.toString();
					return result;
	}
		
	//重置用户状态
	@RequiresPermissions("UserM:edit")
	@RequestMapping(value="/status", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String status(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String id = request.getParameter("userid");
						
						if (id != null && id!="") {
							User user=this.userService.findById(User.class,Long.valueOf(id));
							String statue=user.getStatus().trim();
							Integer st=Integer.valueOf(statue);
							if(st==4){
								user.setStatus("1");
							}else{
								user.setStatus((st+1)+"");
							}
							this.userService.update(user);
							json.put("status", "true");
							json.put("message", "重置密码成功!");
					    }else{
					    	json.put("status", "false");
							json.put("message", "请选择!");
					    }
						
						String result=json.toString();
						return result;
	}

	//删除用户
	@RequiresPermissions("UserM:delete")
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
				this.userService.delete(ids);
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
	
	//删除用户
	@RequiresPermissions("UserM:delete")
	@RequestMapping(value="/resetPswds", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String resetPswds(HttpServletRequest request  ,Map<String, Object> map ){
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
					String hql="UPDATE User set password='fe3a87008112ed41ea25e17f411ecd6c28ea7179',salt='37a17ea15a4cbd50' WHERE id IN("+ids+")";
					this.userService.update(hql);
					json.put("status", "true");
					json.put("message", "重置成功!");
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
	
	//激活用户
	@RequiresPermissions("UserM:delete")
	@RequestMapping(value="/actUser", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String actUser(HttpServletRequest request  ,Map<String, Object> map ){
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
					this.userService.actUsers(ids,"2");
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
		
	//批量设定用户角色
	@RequiresPermissions("UserM:delete")
	@RequestMapping(value="/roleToUsers", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String roleToUsers(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		if(!CsrfTokenUtils.validCsrfToken(request)){
			json.put("status", "false");
			json.put("message", "非法操作!");
			String result=json.toString();
			return result;
		}
		
		String ids = request.getParameter("ids");
					String roleid = request.getParameter("roleid");
					
				
					
					if (ids != null && ids!="") {
						try{
							this.userService.roleToUsers(ids,roleid);
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
	
	//判断用户帐号是否会重复
	
	@RequestMapping(value="/findUser", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String findUser(HttpServletRequest request  ,Map<String, Object> map ){
		JSONObject json = new JSONObject();
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		if (userid != null && userid!="") {
			if (username != null && username!="") {
				List<User> users = userService.findByLoginName(username, "");
				User user=this.userService.findById(User.class,Long.valueOf(userid));
				if(users!=null&&users.size()>0){
					if(user.getLoginName().equals(username)){
						json.put("status", "false");
						json.put("message", "帐号有效");
					}else{
						json.put("status", "true");
						json.put("message", "帐号无效");
					}
					
				}else{
					json.put("status", "false");
					json.put("message", "帐号有效");
				}
		    }else{
		    	json.put("status", "false");
				json.put("message", "");
		    }
			
		}else{
			if (username != null && username!="") {
				List<User> users = userService.findByLoginName(username, "");
				if(users!=null&&users.size()>0){
					json.put("status", "true");
					json.put("message", "帐号无效");
				}else{
					json.put("status", "false");
					json.put("message", "帐号有效");
				}
		    }else{
		    	json.put("status", "false");
				json.put("message", "");
		    }
			
		}
			
		String result=json.toString();
		return result;
	}
	
	
	@RequiresPermissions("UserM:excel")
	@RequestMapping(value="/excel", method={RequestMethod.GET, RequestMethod.POST})
	public void  getExcel(HttpServletRequest request, HttpServletResponse response){
		
		String deptId = request.getParameter("parentId");
		String userName= request.getParameter("userName");
		String fs = request.getParameter("fs");
		String userStatus= request.getParameter("userStatus");
		String wjs= request.getParameter("wjs");
		String role= request.getParameter("userRole");
		
		String query=" WHERE 1=1 ";
		if (StringUtils.isNotBlank(deptId)) {
			if(fs!=null&&fs.equals("1")){
				String orgs=this.orgService.getOrgIdsByCase(Long.valueOf(deptId));
				query=query+"AND o.Organize.id IN ("+orgs+") ";
			}else{
				query=query+"AND o.Organize.id="+deptId;
			}
		}
		
		if (StringUtils.isNotBlank(userName)) {
			query=query+"AND o.realname LIKE '%"+userName+"%' ";
	    }
		if (userStatus!=null&&!userStatus.equals("0")) {
			query=query+"AND o.status ='"+userStatus+"' ";
	    }
		
		if(StringUtils.isNotBlank(wjs)&&wjs.equals("1")){
			query=query+"AND (o.roles = '' OR o.roles IS NULL)";
		}else{
			if (StringUtils.isNotBlank(role)) {
				query=query+"AND o.roles = '"+role+"' ";
		    }
		}
		
		//query=query+"  ORDER BY "+sort+" "+direction;
		
		List<User> list=this.userService.findAll(User.class,query);
		
		if(deptId!=null&&!deptId.equals("")){
			Organize sortGz=this.orgService.findById(Organize.class, Long.valueOf(deptId));
			Object[] data=list.toArray();
			commonExport(sortGz,response,data);
		}
		
		
	}
	
	
	
	//生成Excel文件
	private void commonExport( Organize sort,HttpServletResponse response, Object[] data) {
	       if (data == null)
	            return;
	        WritableWorkbook wwb = null;
	        try {
	        	//String filename=doc.getDocName()+"."+doc.getSuffix();
	    	    //设置文件输出类型
	        	response.setHeader("pragma", "no-cache");
	    	    response.setContentType("application/octet-stream");  
	    	    //response.setContentType(doc.getMimeType());
	    	    response.setHeader("Content-disposition", "attachment; filename="  
	    	       + new String((sort.getOrgName()+".xls").getBytes("GBK"), "iso-8859-1")); 
	    	    //设置输出长度
	    	    //response.setHeader("Content-Length", String.valueOf(fileLength));  
	    	    //获取输入流
	        	//ResponseStream stream
	        	//BufferedOutputStream stream= new BufferedOutputStream(response.getOutputStream()); 
	            wwb = Workbook.createWorkbook(response.getOutputStream());
	            WritableSheet ws = wwb.createSheet("DataGrid数据", 0);
	            ws.getSettings().setPaperSize(PaperSize.A4);
	            ws.getSettings().setHorizontalCentre(true);
	            ws.getSettings().setOrientation(PageOrientation.LANDSCAPE);
	               
	            ws.getSettings().setBottomMargin(0.5);
	            ws.getSettings().setTopMargin(0.5);
	            ws.getSettings().setRightMargin(0.2);
	            ws.getSettings().setLeftMargin(0.2);
	            
	           // ws.getSettings().setDefaultRowHeight(5);
	            //ws.getSettings().setDefaultRowHeight(100);
	           
	           // ws.getSettings().setVerticalCentre(true);
	           
	          //  ws.getSettings().setFitToPages(true);
	           
	           
	            ws.setColumnView(0, 5);
	            ws.setColumnView(1, 10);
	            ws.setColumnView(2, 10);
	            ws.setColumnView(3, 20);
	            ws.setColumnView(4, 10);
	            ws.setColumnView(5, 10);
	            ws.setColumnView(6, 10);
	            ws.setColumnView(7, 10);
	           
	           
	              
	            jxl.write.WritableFont wfont= new WritableFont(WritableFont.createFont("宋体"),18,WritableFont.BOLD); 
	            WritableCellFormat title = new WritableCellFormat(wfont);     
	            // 设置居中     
	            title.setAlignment(Alignment.CENTRE);     
	            title.setVerticalAlignment(VerticalAlignment.CENTRE);
	            jxl.write.WritableFont wfont2= new WritableFont(WritableFont.createFont("宋体"),10,WritableFont.BOLD); 
	            WritableCellFormat items = new WritableCellFormat(wfont2);     
	            // 设置居中     
	            items.setAlignment(Alignment.CENTRE); 
	           
	            items.setVerticalAlignment(VerticalAlignment.CENTRE);
	            // 设置边框线     
	            items.setBorder(Border.ALL, jxl.format.BorderLineStyle.THIN);     
	            // 设置单元格的背景颜色     
	            items.setBackground(jxl.format.Colour.WHITE); 
	            
	            jxl.write.WritableFont wfont3= new WritableFont(WritableFont.createFont("宋体"),10); 
	            WritableCellFormat cellstring = new WritableCellFormat(wfont3);     
	            // 设置居中     
	            cellstring.setAlignment(Alignment.LEFT);  
	            cellstring.setVerticalAlignment(VerticalAlignment.CENTRE);
	           
	            // 设置边框线     
	            cellstring.setBorder(Border.ALL, BorderLineStyle.THIN);     
	            // 设置单元格的背景颜色     
	            cellstring.setBackground(jxl.format.Colour.WHITE); 
	            
	            
	            WritableCellFormat cellint = new WritableCellFormat(wfont3);     
	            // 设置居中     
	            cellint.setAlignment(Alignment.RIGHT);  
	            cellint.setVerticalAlignment(VerticalAlignment.CENTRE);
	            // 设置边框线     
	            cellint.setBorder(Border.ALL, BorderLineStyle.THIN);     
	            // 设置单元格的背景颜色     
	            cellint.setBackground(jxl.format.Colour.WHITE); 
	            
	            jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#,##0.00");     
	            jxl.write.WritableCellFormat celldouble = new jxl.write.WritableCellFormat(nf);   
	            celldouble.setBorder(Border.ALL, BorderLineStyle.THIN);
	            celldouble.setAlignment(Alignment.RIGHT);
	            celldouble.setVerticalAlignment(VerticalAlignment.CENTRE);
	            
	            jxl.write.DateFormat df = new jxl.write.DateFormat("yyyy-MM-dd");
	            jxl.write.WritableCellFormat celldate = new jxl.write.WritableCellFormat(df);
	            celldate.setBorder(Border.ALL, BorderLineStyle.THIN);
	            celldate.setAlignment(Alignment.CENTRE);
	            celldate.setVerticalAlignment(VerticalAlignment.CENTRE);
	            
	            ws.setRowView(0, 500);
	            ws.mergeCells(0,0,7,0);     
	            Label labelItem = new Label(0,0,sort.getOrgName()+"人员账号",title);     
	            ws.addCell(labelItem);
	            
	            
	            ws.setRowView(1, 300);
	            ws.mergeCells(0,1,1,1);
	            labelItem = new Label(0,1,"制表人：");     
	            ws.addCell(labelItem);
	           
	           
	            
	           SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");     
	           String newdate1 = sdf1.format(new Date()); 
	           ws.mergeCells(6,1,7,1);
	           labelItem = new Label(6,1,"日期："+newdate1);    
	           ws.addCell(labelItem);
	            
	           labelItem = new Label(0, 2, "ID", items);
               ws.addCell(labelItem);
               labelItem = new Label(1, 2, "部门", items);
               ws.addCell(labelItem);
               labelItem = new Label(2, 2, "姓名", items);
               ws.addCell(labelItem);
               labelItem = new Label(3, 2, "性别", items);
               ws.addCell(labelItem);
               labelItem = new Label(4, 2, "登录账号", items);
               ws.addCell(labelItem);
               labelItem = new Label(5, 2, "注册日期", items);
               ws.addCell(labelItem);
               labelItem = new Label(6, 2, "电话", items);
               ws.addCell(labelItem);
               labelItem = new Label(7, 2, "状态", items);
               ws.addCell(labelItem);
	          // int xh=0;
	            // 写入数据
	            for (int i = 0; i < data.length; i++) {
	            	
	            	ws.setRowView(i+2, 300);
	            	
	            	User s = (User)data[i];
	            	int y=i+3;
	            	
	            	
	            		
	            		//xh=0;
	            	
	            		//xh=xh+1;
	            		labelItem = new Label(0, y, s.getId()+"",cellstring);
	            		ws.addCell(labelItem);
	            		
	            		 if(s.getOrg().getOrgName()!=null){
	     					labelItem = new Label(1, y, s.getOrg().getOrgName(),cellstring);
	     					ws.addCell(labelItem);
	     				
	     				}else{
	     					labelItem = new Label(1, y, "",cellstring);
	     					ws.addCell(labelItem);
	     				}
	            		 
	            		 if(s.getUserName()!=null){
	      					labelItem = new Label(2, y, s.getUserName(),cellstring);
	      					ws.addCell(labelItem);
	      				
	      				}else{
	      					labelItem = new Label(2, y, "",cellstring);
	      					ws.addCell(labelItem);
	      				}
	            		
	            		 
	            		 if(s.getSex()!=null){
		       					labelItem = new Label(3, y, this.getSex(s.getSex()),cellstring);
		       					ws.addCell(labelItem);
		       				
		       				}else{
		       					labelItem = new Label(3, y, "",cellstring);
		       					ws.addCell(labelItem);
		       				}
	            		 
	            		 if(s.getLoginName()!=null){
	       					labelItem = new Label(4, y, s.getLoginName(),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(4, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	            		 
	            		if(s.getCreateTime()!=null){
		     					jxl.write.DateTime labelDT = new jxl.write.DateTime(5, y, s.getCreateTime() ,celldate); 
		     					ws.addCell(labelDT);
		     				
		     			}else{
		     					labelItem = new Label(5, y, "",cellstring);
		     					ws.addCell(labelItem);
		     				}
	            		 
	            		 if(s.getMobile()!=null){
		       					labelItem = new Label(6, y, s.getMobile(),cellstring);
		       					ws.addCell(labelItem);
		       				
		       				}else{
		       					labelItem = new Label(6, y, "",cellstring);
		       					ws.addCell(labelItem);
		       				}
	            		 
	            		 if(s.getStatus()!=null){
	       					labelItem = new Label(7, y,this.getStatus(s.getStatus()),cellstring);
	       					ws.addCell(labelItem);
	       				
	       				}else{
	       					labelItem = new Label(7, y, "",cellstring);
	       					ws.addCell(labelItem);
	       				}
	            		
	            		
	            	
	            }
	            
	           
	            wwb.write();
	            wwb.close();
	            response.flushBuffer();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (RowsExceededException e) {
	            e.printStackTrace();
	        } catch (WriteException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    String getStatus(String aa){
	    	String bb="";
	    	if(aa!=null){
		    	if(aa.equals("1")){ return "等待激活"; }
				if(aa.equals("2")){ return "离线状态"; }
				if(aa.equals("3")){ return "活动状态"; }
				if(aa.equals("4")){ return "锁定状态"; }
	    	}
	    		
	    	return bb;
	    	
	     }
	    
	    String getSex(String aa){
	    	String bb="";
	    	if(aa!=null){
		    	if(aa.equals("1")){ return "男"; }
				if(aa.equals("2")){ return "女"; }
	    	}
	    		
	    	return bb;
	    	
	     }
}
