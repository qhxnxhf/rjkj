package com.rjkj.dao;


import java.util.List;
import com.rjkj.entities.Dic;
import com.rjkj.util.web.Page;



public interface DicDao extends GenericDao<Dic, Long>{
	
	public List<Dic> findByParentId(Long parentId,String query);
	
	public List<Dic> findByParentId(Long parentId,String query,int start, int limit);
	
	public Integer getTotle(Long parentId,String query);
	
	public List<Dic> findAll(String query,Page page);

	public List<Dic> getAllProperties(Integer id);

	public String getPropertiesByKey(String key);

	public Dic getDicById(String string, String string2);
}
