package com.rjkj.dao.imp;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkTjxmDao;
import com.rjkj.entities.JkTjxm;
import com.rjkj.util.web.Page;

@Transactional
@Component("JkSxjgDao")
public class JkTjxmDaoImp extends GenericDaoImpl<JkTjxm, Long> implements JkTjxmDao{

	@Override
	public List<JkTjxm> findByParentId(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(JkTjxm.class, hql);
	}

	@Override
	public List<JkTjxm> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(JkTjxm.class,query);
		page.setTotalRows(tt);
		return this.findAll(JkTjxm.class, query, ss, ee);
	}

}
