package com.rjkj.web.sys;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.rjkj.dao.ModuleDao;
import com.rjkj.dao.RoleModuleDao;
import com.rjkj.dao.UserDao;
import com.rjkj.entities.Module;
import com.rjkj.entities.User;
import com.rjkj.util.web.TreeAccordion;
import com.rjkj.util.web.TreeAccordionLM;


@Controller
@RequestMapping("/layout/")
public class LayoutControl {
	

	private static final Log log = LogFactory.getLog(OrgMControl.class);  
	//protected ErrMg error; 
	@Autowired
	protected UserDao userService;
	
	@Autowired
	private ModuleDao modService;
	
	@Autowired
	private RoleModuleDao rmService;
	
	private static final String TREE = "layout/leftTree";
	
	private static final String TREE2 = "layout/left";
	
	private static final String NAV = "layout/nav";
	
	private static final String MAIN = "layout/main";
	
	private static final String LOGIN = "/";
	
	String path ="";
	
	@RequestMapping(value="/openUrl", method={RequestMethod.GET, RequestMethod.POST})
	public String preMain(Map<String, Object> map ,HttpServletRequest request ){
		path = request.getContextPath();
		String url=request.getParameter("url");
		User user=(User) request.getSession().getAttribute("LoginUser");
		map.put("loginUser", user);
		return url;
	}


	@RequestMapping(value="/nav", method={RequestMethod.GET, RequestMethod.POST})
	public String preNav(Map<String, Object> map ,HttpServletRequest request ){
		path = request.getContextPath();
		JSONArray jsonArray = new JSONArray();   
		User user=(User) request.getSession().getAttribute("LoginUser");
		map.put("loginUser", user);
		return NAV;
	}
	
	
	@RequestMapping(value="/preTree", method={RequestMethod.GET, RequestMethod.POST})
	public String preTree(Map<String, Object> map ,HttpServletRequest request ){
		//String id = request.getParameter("roleid");
		path = request.getContextPath();
		JSONArray jsonArray = new JSONArray();   
		User user=(User) request.getSession().getAttribute("LoginUser");
		//用户有权的所有功能节点
		List<Module> userMods = userService.findModuleByUser(user, "", " AND o.module.nodeType != 'y' ");
		//全部功能节点
		List<Module> modlist=this.modService.findAll(Module.class," WHERE o.nodeType !='y' ORDER BY o.orderNum,o.id ASC ");
		LinkedList<Module> modlinked=new LinkedList<Module>(modlist);
		Module root=this.modService.findById(Module.class,1L);
		//生成 ZTree树的json格式
		createTree(root, modlinked,userMods,jsonArray);
		String result=jsonArray.toString();
		map.put("ztNodes", result);
		return TREE;
	}
	
	
	
	
	public void createTree(Module root,LinkedList<Module> modlist,List<Module> rmlist,JSONArray jsonArray){
		 
		if(this.ifModule(rmlist, root)){
	     if(!root.getNodeType().equals("r")){
	    	 
	    	 if(root.getNodeType().equals("b")){
	    		 TreeAccordionLM node1 = new TreeAccordionLM();
	    		 node1.setId(root.getId()+"");
			     node1.setName(root.getModName());
			     Module pmod=root.getParent();
			     if(pmod.getNodeType().equals("r")){
			    	 node1.setParentId("0");
			     }else{
			    	 node1.setParentId(pmod.getId()+"");
			     }
	    		 node1.setIcon("skin/icons/icon_1.png");
	    		 node1.setIconSkin("diy01");
	    		 jsonArray.add(node1);
	    	 }else{
	    		 TreeAccordion node1 = new TreeAccordion();
	    		 node1.setId(root.getId()+"");
			     node1.setName(root.getModName());
			     Module pmod=root.getParent();
			     node1.setParentId(pmod.getId()+"");
	    		 node1.setTarget("frmright");
	    		 node1.setIcon("skin/titlebar_arrow.gif");
	    	     node1.setTabUrl(path+"/layout/openUrl?url="+root.getUrl());
	    	     node1.setIconOpen(path+"/resource/libs/icons/tree_open.gif");
	    	     node1.setIconClose(path+"/resource/libs/icons/tree_close.gif");
	    	     jsonArray.add(node1);
	    		 
	    	 }
	    	
	    	
	     }
	     
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
	}
	
	private List<Module> getChildren(Module root, LinkedList<Module> modlist) {
		Long pid=root.getId();
		List<Module> childre=new ArrayList<Module>();
		ListIterator<Module> listIter=modlist.listIterator();
		while(listIter.hasNext()){
			Module mod=listIter.next();
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
	
	@RequestMapping(value="/main", method={RequestMethod.GET, RequestMethod.POST})
	public String main(Map<String, Object> map ,HttpServletRequest request ){
			
		HttpSession session = request.getSession(true); 
		//Subject sub = SecurityUtils.getSubject();
		
		Object obj=session.getAttribute("LoginUser");
		User user;
		if(obj==null){
			return LOGIN;
	        //String  username =sub.getPrincipal().toString();  
	       // System.out.println(username);
	        //List<User> users = userService.findByLoginName(username, "");
		    //user=users.get(0);
	        //session.setAttribute("LoginUser", user);  
		}else{
			user=(User)obj;
			request.setAttribute("loginUser", user);
			return MAIN;
		}
		
		
	}

	/**
	public String getLeftTree(List<Module> rmlist,HttpServletRequest request){
		path = request.getContextPath();
		 
		List<Module> modlist=this.modService.findAll(" WHERE o.type !='y' ORDER BY o.priority,o.id DESC ");//全部功能节点
		LinkedList<Module> modlinked=new LinkedList<Module>(modlist);
		JSONArray jsonArray = new JSONArray();   
		Module root=this.modService.findById(1L);
		createTree(root, modlinked,rmlist,jsonArray);//rmlist为用户有权的所有功能节点
		String result=jsonArray.toString();
		return result;
	}
	**/
}
