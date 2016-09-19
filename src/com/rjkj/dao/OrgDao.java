package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.Organize;
import com.rjkj.util.web.Page;



public interface OrgDao extends GenericDao<Organize, Long>{
	
	public List<Organize> findByParentId(Long parentId,String query);
	
	public List<Organize> findByParentId(Long parentId,String query,int start, int limit);
	
	public Integer getTotle(Long parentId,String query);
	
	public List<Organize> findAll(String query,Page page);
	
	public List<Organize> findByCase(Long parentId);
	
	public String getOrgIdsByCase(Long parentId);

}
