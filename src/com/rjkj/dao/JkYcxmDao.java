package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkYcxm;
import com.rjkj.util.web.Page;

public interface JkYcxmDao extends GenericDao<JkYcxm, Long>{
	
	List<JkYcxm> findAll(String query, Page page);

}
