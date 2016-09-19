package com.rjkj.dao.imp;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.DicDao;
import com.rjkj.entities.Dic;
import com.rjkj.util.web.Page;


@Transactional
@Component("DicDao")
public class DicDaoImp extends GenericDaoImpl<Dic, Long> implements DicDao{
	

	@Override
	public List<Dic> findByParentId(Long parentId, String query,int start, int limit) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(Dic.class, hql, start, limit);
	}

	@Override
	public Integer getTotle(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.getTotalCount(Dic.class, hql);
	}

	@Override
	public List<Dic> findByParentId(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(Dic.class, hql);
	}

	@Override
	public List<Dic> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(Dic.class,query);
		page.setTotalRows(tt);
		return this.findAll(Dic.class, query, ss, ee);
	}

	@Override
	public List<Dic> getAllProperties(Integer id) {
		String hql=" WHERE o.parent.id = "+id;
		List<Dic> dics=this.findAll(Dic.class, hql);
		return dics;
	}

	@Override
	public String getPropertiesByKey(String key) {
		String hql=" WHERE o.dicKey = '"+key+"' ";
		List<Dic> dics=this.findAll(Dic.class, hql);
		
		if(dics!=null&&dics.size()>0){
			
			return dics.get(1).getValue1();
			
		}
		return null;
	}

	@Override
	public Dic getDicById(String dicId, String string2) {
		// TODO Auto-generated meththisod stub
		
		return this.findById(Dic.class, Long.valueOf(dicId));
	}

}
