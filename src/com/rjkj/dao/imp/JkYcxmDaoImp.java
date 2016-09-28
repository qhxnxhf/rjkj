package com.rjkj.dao.imp;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkYcxmDao;
import com.rjkj.entities.JkYcxm;
import com.rjkj.entities.News;
import com.rjkj.util.web.Page;

@Transactional
@Component("JkYcxmDao")
public class JkYcxmDaoImp extends GenericDaoImpl<JkYcxm, Long> implements JkYcxmDao{

	@Override
	public List<JkYcxm> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		//String hql=" WHERE o.sort.id = "+parentId+" "+query;
		int tt=this.getTotalCount(JkYcxm.class,query);
		page.setTotalRows(tt);
		return this.findAll(JkYcxm.class, query, ss, ee);
	}

}
