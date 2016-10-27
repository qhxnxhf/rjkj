package com.rjkj.web.zgjk;

import java.util.List;

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

import com.rjkj.dao.JkTjDeptDao;
import com.rjkj.dao.ZgjkTjxxDao;
import com.rjkj.model.TjDept;
import com.rjkj.model.Tjjl;
import com.rjkj.model.Tjlb;
import com.rjkj.model.Tjxx;
import com.rjkj.util.web.JsonUtil;

@Controller
@RequestMapping("/zgjk/tjxx")
public class JkTjxxControl {
	
	private static final Log log = LogFactory.getLog(JkTjxxControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private ZgjkTjxxDao tjService;
	
	@Autowired
	private JkTjDeptDao deptService;
	
	//组织机构单选框
		
	@RequestMapping(value="/typeTj", method={RequestMethod.GET, RequestMethod.POST})		
	public @ResponseBody String getTypeTj(HttpServletRequest request){
						
					JSONObject json = new JSONObject();
					
					JSONArray jsonArray=new JSONArray();
					List<Tjlb> dics=this.tjService.findTjlb();
					
					for(Tjlb dic:dics){
						JSONObject js = new JSONObject();
						
						js.put("key", dic.getLbName());
						js.put("value", dic.getLbName());
						
						jsonArray.add(js);
					}
						
					json.put("list", jsonArray);
					String result=json.toString();
					return result;
					    
	}
	
	//组织机构单选框		
		@RequestMapping(value="/deptTj", method={RequestMethod.GET, RequestMethod.POST})		
		public @ResponseBody String getDeptTj(HttpServletRequest request){
							
						JSONObject json = new JSONObject();
						JSONArray jsonArray=new JSONArray();
						List<TjDept> dics=this.deptService.findDw();
						for(TjDept dic:dics){
							JSONObject js = new JSONObject();
							js.put("key", dic.getName());
							js.put("value", dic.getId());
							jsonArray.add(js);
						}
						json.put("list", jsonArray);
						String result=json.toString();
						return result;
						    
		}	
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String cId = request.getParameter("carId");
		String tjlb= request.getParameter("tjlb");;
		//String name = request.getParameter("uname");
		
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		
		String query=" WHERE 1=1 ";
		
		
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND xx_5 ='"+cId+"'";
		}else{
			return "";
		}
		
		
		if(StringUtils.isNotBlank(tjlb)){
			query=query+" AND txx_2 ='"+tjlb+"'";
		}
		
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray;
		List<Tjxx> list;
		
		
		
			//list=this.tjService.findByCardId(cId);
			list=this.tjService.findBySql(query);
			String[] excludes={"children","users","news","docs"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
			jsonArray=JSONArray.fromObject(list, config);
			json.put("rows", jsonArray);
		
		
		
		//json.put("pager.pageNo", page.getPageNo());
		//json.put("pager.totalRows", page.getTotalRows());
		
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
	
	@RequestMapping(value="/listJl", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getJlList(HttpServletRequest request){
		String cId = request.getParameter("carId");
		String tjlb= request.getParameter("tjlb");;
		//String name = request.getParameter("uname");
		
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		
		String query=" WHERE 1=1 ";
		
		
		
		if(StringUtils.isNotBlank(cId)){
			query=query+" AND xx_5 ='"+cId+"'";
		}else{
			return "";
		}
		
		
		if(StringUtils.isNotBlank(tjlb)){
			query=query+" AND txx_21 ='"+tjlb+"'";
		}
		
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray;
		List<Tjjl> list;
		
			//list=this.tjService.findByCardId(cId);
			//list=this.tjService.findTjjlByType(cId, tjlb);
			list=this.tjService.findTjjlBySql(query);
			
			String[] excludes={"children","users","news","docs"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
			jsonArray=JSONArray.fromObject(list, config);
			json.put("rows", jsonArray);
		
		
		
		//json.put("pager.pageNo", page.getPageNo());
		//json.put("pager.totalRows", page.getTotalRows());
		
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
}
