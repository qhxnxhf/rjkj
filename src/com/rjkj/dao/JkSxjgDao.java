package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkSxjg;
import com.rjkj.entities.News;
import com.rjkj.util.web.Page;

public interface JkSxjgDao extends GenericDao<JkSxjg, Long>{
	
	List<JkSxjg> findAll(String query, Page page);


}
