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
import com.rjkj.model.Tjlb;
import com.rjkj.model.Tjxx;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.web.sys.OrgMControl;

@Controller
@RequestMapping("/zgjk/tjxx")
public class JkTjxxControl {
	
	private static final Log log = LogFactory.getLog(OrgMControl.class);  
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
		//String medId = request.getParameter("medId");
		
		String sort= request.getParameter("sort");;
		String direction= request.getParameter("direction");
		
		JSONObject json = new JSONObject();
		JSONArray jsonArray;
		List<Tjxx> list;
		
		if (StringUtils.isNotBlank(cId)) {
			list=this.tjService.findByCardId(cId);
			String[] excludes={"children","users","news","docs"};
			JsonConfig config =JsonUtil.configJson(excludes, "yyyy-MM-dd HH:mm:ss");
			jsonArray=JSONArray.fromObject(list, config);
			json.put("rows", jsonArray);
		}
		
		
		//json.put("pager.pageNo", page.getPageNo());
		//json.put("pager.totalRows", page.getTotalRows());
		
		json.put("sort", sort);
		json.put("direction", direction);
		
		String result=json.toString();
		return result;
	}
	
}
