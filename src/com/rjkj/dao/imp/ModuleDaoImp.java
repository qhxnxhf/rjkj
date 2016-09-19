package com.rjkj.dao.imp;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.ModuleDao;
import com.rjkj.entities.Module;
import com.rjkj.util.web.Page;



@Transactional
@Component("ModuleDao")
public class ModuleDaoImp extends GenericDaoImpl<Module, Long> implements ModuleDao {
	
	
	@Override
	public List<Module> findByParentId(Long parentId, String query, int start,
			int limit) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(Module.class, hql, start, limit);
	}

	@Override
	public Integer getTotle(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.getTotalCount(Module.class, hql);
	}

	@Override
	public List<Module> findByParentId(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(Module.class, hql);
	}

	@Override
	public List<Module> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(Module.class,query);
		page.setTotalRows(tt);
		return this.findAll(Module.class, query, ss, ee);
	}
	
	
	
	
}
