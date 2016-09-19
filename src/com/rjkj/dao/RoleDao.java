package com.rjkj.dao;


import java.util.List;

import com.rjkj.entities.Role;
import com.rjkj.util.web.Page;


public interface RoleDao extends GenericDao<Role, Long>{
	
	public List<Role> findAll(String query,Page page);

}
