package com.rjkj.web.zgjk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.JkTjUserDao;
import com.rjkj.dao.JkTjxmDao;
import com.rjkj.dao.JkYcxmDao;
import com.rjkj.dao.ZgjkTjxxDao;
import com.rjkj.entities.JkTjxm;
import com.rjkj.entities.JkYcxm;
import com.rjkj.model.TjUser;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.web.sys.OrgMControl;

@Controller
@RequestMapping("/zgjk/xxcs")
public class TjxxcsMControl {
	
	private static final Log log = LogFactory.getLog(TjxxcsMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private ZgjkTjxxDao tjService;
	
	@Autowired
	private JkYcxmDao ycService;
	
	@Autowired
	private JkTjxmDao tjxService;
	
	@Autowired
	private JkTjUserDao userService;
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String cId = request.getParameter("carId");
		String tjlb= request.getParameter("tjlb");;
		String id = request.getParameter("tjbm");
		String name = request.getParameter("uname");		
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		String pageNo= request.getParameter("pager.pageNo");
		String pageSize= request.getParameter("pager.pageSize");
		Page page=new Page();
		page.setPageNo(Integer.valueOf(pageNo));
		page.setPageSize(Integer.valueOf(pageSize));
		
		String query=" WHERE tjAges <> -1 ";
		
		if(StringUtils.isNotBlank(id)){
			query=query+" AND deptId="+id;
		}
		if(StringUtils.isNotBlank(tjlb)){
			query=query+" AND tjType ='"+tjlb+"'";
		}
		
		if(StringUtils.isNotBlank(name)){
			query=query+" AND name LIKE '%"+name+"%'";
		}
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND cardId ='"+cId+"'";
		}
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray;
		List<JkYcxm> list;
		list=this.ycService.findAll(query, page);
		String[] excludes={"children","users","news","docs"};
		JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
		jsonArray=JSONArray.fromObject(list, config);
		
		
		json.put("pager.pageNo", page.getPageNo());
		json.put("pager.totalRows", page.getTotalRows());
		json.put("rows", jsonArray);
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	

	//@RequiresPermissions("LmM:save")
	@RequestMapping(value="/saveXxcs", method={RequestMethod.POST})
	public @ResponseBody String saveXxcs(Map<String, Object> map ,HttpServletRequest request){
		String tjlb= request.getParameter("tjlb");
		String tjbm = request.getParameter("tjbm");
		String cId = request.getParameter("carId");
		String tjdate = request.getParameter("tjdate");	
		Long index=0L;
		
		String query=" ";
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND xx_5 ='"+cId+"' ";
		}
		
		if(StringUtils.isNotBlank(tjlb)){
			query=query+" AND txx_2 ='"+tjlb+"' ";
		}
		if(StringUtils.isNotBlank(tjbm)){
			query=query+" AND txx_16 ="+tjbm+" ";
		}
		
		
		
		JkYcxm max=this.ycService.findMaxId();
		if(max!=null){
			index=max.getTjxxId();
		}
		
		List<JkTjxm> cstj=this.tjxService.findAll(JkTjxm.class, " WHERE type1Value IS NOT NULL AND type1Value <> ''"); 				
		HashMap<String, TjUser> userHash=userService.findAll("");
		
		String msgs=this.tjService.xxcs(cstj,userHash,index,query);
	
		JSONObject json = new JSONObject();				   
			//this.tjxService.save(dic);
		json.put("status", "true");
		json.put("message", msgs);
			//map.put("dic", dic);
		String result=json.toString();
		return result;
	}
	
	
	@RequestMapping(value="/delXxcs", method={RequestMethod.POST})
	public @ResponseBody String delXxcs(HttpServletRequest request  ,Map<String, Object> map ){
		String cId = request.getParameter("carId");
		String tjpc= request.getParameter("tjlb");;
		String id = request.getParameter("tjbm");
		String name = request.getParameter("uname");
		
		JSONObject json = new JSONObject();
		
		String query=" ";
		
		if(StringUtils.isNotBlank(id)){
			query=query+" AND o.deptId="+id;
		}
		if(StringUtils.isNotBlank(tjpc)){
			query=query+" AND o.tjpc ='"+tjpc+"'";
		}
		
		if(StringUtils.isNotBlank(name)){
			query=query+" AND o.name LIKE '%"+name+"%'";
		}
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND o.cardId ='"+cId+"'";
		}
        	
		query=query.trim();
		
		try{
			String msg=ycService.delByHql(query);
			json.put("status", "true");
			json.put("message", msg);
		}catch(Exception e){
			json.put("status", "false");
			json.put("message", e.toString());
		}
		
		
		String result=json.toString();
		return result;
	}

	
	
}
