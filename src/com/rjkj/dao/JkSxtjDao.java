package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkSxtj;
import com.rjkj.util.web.Page;

public interface JkSxtjDao extends GenericDao<JkSxtj, Long>{
	
	List<JkSxtj> findByParentId(Long valueOf, String string);

	List<JkSxtj> findAll(String string, Page page);

}
