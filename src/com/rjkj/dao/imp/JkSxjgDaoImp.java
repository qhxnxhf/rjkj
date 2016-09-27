package com.rjkj.dao.imp;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkSxjgDao;
import com.rjkj.entities.JkSxjg;
import com.rjkj.util.web.Page;

@Transactional
@Component("JkSxjgDao")
public class JkSxjgDaoImp extends GenericDaoImpl<JkSxjg, Long> implements JkSxjgDao{

	@Override
	public List<JkSxjg> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		//String hql=" WHERE o.sort.id = "+parentId+" "+query;
		int tt=this.getTotalCount(JkSxjg.class,query);
		page.setTotalRows(tt);
		return this.findAll(JkSxjg.class, query, ss, ee);
	}

}
