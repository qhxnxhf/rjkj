package com.rjkj.dao.imp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.RoleDao;
import com.rjkj.dao.RoleModuleDao;
import com.rjkj.dao.UserDao;
import com.rjkj.dao.UserRoleDao;
import com.rjkj.entities.Module;
import com.rjkj.entities.Role;
import com.rjkj.entities.User;
import com.rjkj.entities.UserRole;
import com.rjkj.shiro.ShiroDbRealm;
import com.rjkj.shiro.ShiroDbRealm.HashPassword;
import com.rjkj.util.web.Page;


@Transactional
@Component("UserDao") 
public class UserDaoImp extends GenericDaoImpl<User, Long> implements UserDao{
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	public  UserRoleDao  urDao;
	
	@Autowired
	public  RoleModuleDao  rmDao;
	
	@Autowired(required = false)
	private ShiroDbRealm shiroRealm;
	
	private EntityManager em; 
	
	
	public void hashPswd(User user)  throws Exception{
		List<User> users=this.findByLoginName(user.getUserName(), "");
		if(users!=null&&users.size()>0){
			throw new Exception("用户添加失败，登录名：" + user.getUserName()+ "已存在。");
		}else{
			// 设定安全的密码，使用passwordService提供的salt并经过1024次 sha-1 hash
			if (user.getPlainPassword()!=null &&!user.getPlainPassword().equals("")&& shiroRealm != null) {
				HashPassword hashPassword = ShiroDbRealm.encryptPassword(user.getPlainPassword());
				user.setSalt(hashPassword.salt);
				user.setPassword(hashPassword.password);
			}else{
				throw new Exception("用户添加失败，密码设定不能为空：" );
			}
		}
		
	}
	
	@Override
	public List<User> findByLoginName(String name, String query) {
		String hql=" WHERE o.loginName = '"+name+"' "+query;
		return this.findAll(User.class, hql);
	}

	@Override
	public List<User> findByOrg(String orgId, String query, int start, int limit) {
		String hql=" WHERE o.organize.id = "+orgId+" "+query;
		return this.findAll(User.class, hql, start, limit);
	}

	@Override
	public Integer getTotle(String orgId, String query) {
		String hql=" WHERE o.organize.id = "+orgId+" "+query;
		return this.getTotalCount(User.class, hql);
	}

	@Override
	public List<User> findAll(Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(User.class);
		page.setTotalRows(tt);
		return this.findAll(User.class, "", ss, ee);
	}
	
	@Override
	public List<User> findAll(String query,Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(User.class,query);
		page.setTotalRows(tt);
		return this.findAll(User.class, query, ss, ee);
	}

	@Override
	public List<User> findByOrg(String orgId, String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotle(orgId, query);
		page.setTotalRows(tt);
		return this.findByOrg(orgId, query, ss, ee);
	}

	@Override
	public List<Role> findRoleByUser(User user, String query) {
		return this.urDao.findByUser(user, query);
	}

	

	@Override
	public List<Module> findModuleByUser(User user, String uquery, String mquery) {
		List<Role> roles=this.urDao.findByUser(user, uquery);
		Set<Module> moduleSet = new HashSet<Module>();
		for(int i=0;i<roles.size();i++){
			Role rr=roles.get(i);
			List<Module> modules=this.rmDao.findByRole(rr.getId()+"", mquery);
			for(int j=0;j<modules.size();j++){
				Module mm=modules.get(j);
				moduleSet.add(mm);
			}
			
		}
		
		List<Module> mdlist=new ArrayList<Module>(moduleSet);
		return mdlist;
	}

	@Override
	public void saveUserRoles(String userId, String roleIds) {
		String hql=" DELETE FROM UserRole WHERE user.id ="+userId;
		this.update(hql);
		if(roleIds!=null&&!roleIds.equals("")){
			String[] mods=roleIds.split(",");
			for(int i=0;i<mods.length;i++){
				String roleid=mods[i];
				UserRole ur=new UserRole();
				User user=new User();
				user.setId(Long.valueOf(userId));
				Role role=new Role();
				role.setId(Long.valueOf(roleid));
				ur.setUser(user);
				ur.setRole(role);
				urDao.save(ur);
			}
		}
		
		User user=this.findById(User.class, Long.valueOf(userId));
		user.setRoles(roleIds);
		this.update(user);
	}

	

	@Override
	public void resetPswd(Long userId, String pswd) {
		User user=this.findById(User.class, userId);
		HashPassword hashPassword = ShiroDbRealm.encryptPassword(pswd);
		user.setSalt(hashPassword.salt);
		user.setPassword(hashPassword.password);
		this.update(user);
	}

	@Override
	public void saveUser(User user) {
		// 保存系统用户
        try {
        	hashPswd(user);
        	this.save(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	@Override
	public void delete(String hql) {
       this.delIds(User.class,hql);
       
	}

	

	@Override
	public void actUsers(String ids, String status) {
		String userhql="UPDATE User set status='"+status+"' WHERE id IN("+ids+")";
		this.update(userhql);
		
	}

	@Override
	public void roleToUsers(String ids, String roleid) {
		if(ids!=null&&roleid!=null){
			String hql=" DELETE FROM UserRole WHERE user.id IN ("+ids+")";
			this.update(hql);
			String userhql="UPDATE User set roles='"+roleid+"' WHERE id IN("+ids+")";
			this.update(userhql);
			String[] mods=ids.split(",");
			for(int i=0;i<mods.length;i++){
				String userId=mods[i];
				UserRole ur=new UserRole();
				User user=new User();
				user.setId(Long.valueOf(userId));
				Role role=new Role();
				role.setId(Long.valueOf(roleid));
				ur.setUser(user);
				ur.setRole(role);
				urDao.save(ur);
			}
		}
	}

	

}
