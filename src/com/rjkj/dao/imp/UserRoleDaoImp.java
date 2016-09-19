package com.rjkj.dao.imp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.UserRoleDao;
import com.rjkj.entities.Role;
import com.rjkj.entities.User;
import com.rjkj.entities.UserRole;


@Transactional
@Component("UserRoleDao")
public class UserRoleDaoImp  extends GenericDaoImpl<UserRole, Long> implements UserRoleDao {
	
	private EntityManager em; 
	
	@Override
	public List<Role> findByUser(User user,String qu) {
		String hql="SELECT o.role FROM UserRole o WHERE o.user.id = "+user.getId() +qu;
		em= this.getEm();
		Query query=em.createQuery(hql);
		List<Role> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public List<User> findByRole(Role role,String qu) {
		String hql="SELECT o.user FROM UserRole o WHERE o.role.id = "+role.getId() +qu;
		em= this.getEm();
		Query query=em.createQuery(hql);
		List<User> listMd=query.getResultList();
		return listMd;
	}

}
