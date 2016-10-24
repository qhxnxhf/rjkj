package com.rjkj.web.zgjk;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rjkj.dao.JkSxjgDao;
import com.rjkj.entities.JkSxjg;
import com.rjkj.util.web.JsonUtil;
import com.rjkj.util.web.Page;

@Controller
@RequestMapping("/zgjk/sxjgfind/")
public class SxjgFindControl {
	
	@Autowired
	private JkSxjgDao sxService;
	
	
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
		
		String query=" WHERE 1=1 ";
		
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
		List<JkSxjg> list;
		list=this.sxService.findAll(query, page);
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
	
	

}
