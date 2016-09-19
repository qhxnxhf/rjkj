package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.Module;
import com.rjkj.entities.Role;
import com.rjkj.entities.User;
import com.rjkj.util.web.Page;


public interface UserDao extends GenericDao<User, Long> {

public void saveUser(User user) throws Exception;
	
	//public List<User> findByName(String name, String query);
	
	public List<User> findByLoginName(String name, String query);

	public List<User> findByOrg(String orgId, String query, int start, int limit);

	public Integer getTotle(String orgId, String query);

	public List<User> findAll(Page page);

	public List<User> findByOrg(String orgId,String query,Page page);
	
	public List<Role> findRoleByUser(User user,String query);
	
	public List<Module> findModuleByUser(User user, String uquery, String mquery);
	
	public void saveUserRoles(String userId,String roleIds);
	
	public void resetPswd(Long userId,String pswd);

	public List<User> findAll(String query, Page page);

	public void roleToUsers(String ids, String roleid);
	
	public void actUsers(String ids, String status);
	
	public void delete(String hql);

}
