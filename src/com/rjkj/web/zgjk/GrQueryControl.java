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

import com.rjkj.dao.OrgDao;
import com.rjkj.dao.ZgjkTjxxDao;
import com.rjkj.entities.Organize;
import com.rjkj.model.Tjxx;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;
import com.rjkj.web.sys.OrgMControl;


@Controller
@RequestMapping("/zgjk/gr")
public class GrQueryControl {
	
	private static final Log log = LogFactory.getLog(OrgMControl.class);  
	//protected ErrMg error; 
	 
	@Autowired
	private ZgjkTjxxDao tjService;
	
	
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getTreeList(HttpServletRequest request){
		String cId = request.getParameter("carId");
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
