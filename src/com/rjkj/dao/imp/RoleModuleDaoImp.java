package com.rjkj.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.RoleModuleDao;
import com.rjkj.entities.Module;
import com.rjkj.entities.Role;
import com.rjkj.entities.RoleModule;

import javax.persistence.Query;


@Transactional
@Component("RoleModuleDao")
public class RoleModuleDaoImp extends GenericDaoImpl<RoleModule, Long>  implements RoleModuleDao {
	
	private EntityManager em; 
	
	@Override
	public List<Module> findByRole(String roleId,String qu) {
		String hql="SELECT o.module FROM RoleModule o WHERE o.role.id = "+roleId +qu;
		em= this.getEm();
		Query query=em.createQuery(hql);
		List<Module> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public List<Role> findByModule(String modId,String qu) {
		String hql="SELECT o.role FROM RoleModule o WHERE o.module.id = "+modId+qu;
		em= this.getEm();
		Query query=em.createQuery(hql);
		List<Role> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public void saveRoleModule(String roleId, String modIds) {
		String hql=" DELETE FROM RoleModule WHERE role.id ="+roleId;
		this.update(hql);
		if(modIds!=null&&!modIds.equals("")){
			String[] mods=modIds.split(",");
			for(int i=0;i<mods.length;i++){
				String modid=mods[i];
				RoleModule rm=new RoleModule();
				Role role=new Role();
				role.setId(Long.valueOf(roleId));
				Module module=new Module();
				module.setId(Long.valueOf(modid));
				rm.setRole(role);
				rm.setModule(module);
				this.save(rm);
			}
		}
	}

	

}
