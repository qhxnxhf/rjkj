package com.rjkj.dao.imp;



import java.util.List;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.RoleDao;
import com.rjkj.entities.Role;
import com.rjkj.util.web.Page;


@Transactional
@Component("RoleDao")
public class RoleDaoImp  extends GenericDaoImpl<Role, Long> implements RoleDao {
	
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	 
	 @Override
		public List<Role> findAll(String query, Page page) {
			int ss=(page.getPageNo()-1)*page.getPageSize();
			int ee=page.getPageSize();
			int tt=this.getTotalCount(Role.class,query);
			page.setTotalRows(tt);
			return this.findAll(Role.class, query, ss, ee);
		}

	

	
}
