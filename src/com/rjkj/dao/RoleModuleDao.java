package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.Module;
import com.rjkj.entities.Role;
import com.rjkj.entities.RoleModule;


public interface RoleModuleDao extends GenericDao<RoleModule, Long> {

	public List<Module> findByRole(String roleId,String query);
	
	public List<Role> findByModule(String modId,String query);
	
	public void saveRoleModule(String roleId,String modIds);
	

}
