package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkSxjg;
import com.rjkj.entities.JkYcxm;
import com.rjkj.util.web.Page;

public interface JkSxjgDao extends GenericDao<JkSxjg, Long>{
	
	public List<JkSxjg> findAll(String query, Page page);

	public JkSxjg findMaxId();

	public String saveToSxjg(List<JkYcxm> list);


}
