package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.Module;
import com.rjkj.util.web.Page;



 
public interface ModuleDao extends GenericDao<Module, Long>{
	
	public List<Module> findByParentId(Long parentId,String query);
	
	public List<Module> findByParentId(Long parentId,String query,int start, int limit);

	public Integer getTotle(Long parentId,String query);
	
	public List<Module> findAll(String query,Page page);
	
	


}
