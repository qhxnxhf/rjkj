package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.Role;
import com.rjkj.entities.User;
import com.rjkj.entities.UserRole;


public interface UserRoleDao extends GenericDao<UserRole, Long> {

	public List<Role> findByUser(User user,String query);
	
	public List<User> findByRole(Role role,String query);
	
	
	
}
