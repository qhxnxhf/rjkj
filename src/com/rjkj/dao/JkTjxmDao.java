package com.rjkj.dao;

import java.util.List;


import com.rjkj.entities.JkTjxm;
import com.rjkj.util.web.Page;

public interface JkTjxmDao extends GenericDao<JkTjxm, Long>{
	
	List<JkTjxm> findByParentId(Long valueOf, String string);

	List<JkTjxm> findAll(String string, Page page);
	
}
