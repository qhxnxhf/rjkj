package com.rjkj.dao.imp;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkSxjgDao;
import com.rjkj.entities.JkSxjg;
import com.rjkj.entities.JkYcxm;
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

	@Override
	public JkSxjg findMaxId() {
		//String hql="SELECT o WHERE o.id IN(SELECT MAX(b.id) FROM JkSxjg b)";
		String hql="select a from JkSxjg a where a.id IN(select max(b.id) from JkSxjg b)";
		List<JkSxjg> list=this.findAll(hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveToSxjg(List<JkYcxm> list) {
		
		JkSxjg sxjg;
		if(list!=null&&list.size()>0){
			for(JkYcxm yc :list){
				sxjg=findByYcxm(yc);
				updateToSxjg(yc,sxjg);
			}
		}
		
		
		return null;
	}
	
	
	public void updateToSxjg(JkYcxm yc,JkSxjg sxjg){
		String hql="update JkSxjg o set o.indexId= "+yc.getId()+",o."+yc.getTjxm().getMappingField()+" = "+yc.getTjValue()+" where o.id = "+sxjg.getId();		
		this.update(hql);
	}
	
	public JkSxjg findByYcxm(JkYcxm yc){
		String hql="WHERE o.cardId='"+yc.getCardId()+"' AND o.tjDate='"+yc.getTjDate()+"'";
		List<JkSxjg> list=this.findAll(JkSxjg.class, hql);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			JkSxjg jg=new JkSxjg();
			jg.setCardId(yc.getCardId());
			jg.setAge(yc.getTjAges());
			jg.setSex(yc.getSex());
			jg.setDeptId(yc.getDeptId());
			jg.setTjpc(yc.getTjpc());
			jg.setTjType(yc.getTjType());
			jg.setTjDate(yc.getTjDate());
			//jg.setIndexId(yc.getId());
			this.save(jg);
			return jg;
		}
	}

}
