package com.rjkj.dao.imp;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkSxtjDao;
import com.rjkj.entities.JkSxtj;
import com.rjkj.util.web.Page;

@Transactional
@Component("JkSxtjDao")
public class JkSxtjDaoImp extends GenericDaoImpl<JkSxtj, Long> implements JkSxtjDao{

	@Override
	public List<JkSxtj> findByParentId(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(JkSxtj.class, hql);
	}

	@Override
	public List<JkSxtj> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(JkSxtj.class,query);
		page.setTotalRows(tt);
		return this.findAll(JkSxtj.class, query, ss, ee);
	}
	
	

}
