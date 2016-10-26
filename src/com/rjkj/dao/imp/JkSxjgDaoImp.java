package com.rjkj.dao.imp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.JkSxjgDao;
import com.rjkj.entities.JkSxjg;
import com.rjkj.entities.JkSxtj;
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
			jg.setName(yc.getName());
			jg.setTjrId(yc.getTjrId());
			jg.setAge(yc.getTjAges());
			jg.setSex(yc.getSex());
			jg.setDeptId(yc.getDeptId());
			jg.setTjpc(yc.getTjpc());
			jg.setTjType(yc.getTjType());
			jg.setTjDate(yc.getTjDate());
			jg.setBmi(0.0);
			jg.setShzhya(0.0);
			jg.setZongdan(0.0);
			jg.setGaomi(0.0);
			jg.setDimi(0.0);
			jg.setKfxt(0.0);
			jg.setGysz(0.0);
			jg.setFev1(0.0);
			jg.setFev1fvc(0.0);
			
			jg.setXueshi(0.0);
			jg.setTnbs(0.0);
			jg.setGuanxb(0.0);
			jg.setNaocu(0.0);
			jg.setZhongliu(0.0);
			jg.setXjqx(0.0);
			jg.setXnxg(0.0);
			
			
			//jg.setIndexId(yc.getId());
			this.save(jg);
			return jg;
		}
	}
	
	

	@Override
	public String delByHql(String query) {
		String hql=" DELETE FROM JkSxjg o WHERE 1=1 "+query;
		this.update(hql);
		
		return "del OK!";
	}


	@Override
	public String updateToTjfl(List<JkSxtj> listTj, String query) {
		String hql="update JkSxjg o set ";
		for(JkSxtj tj:listTj){
			String uphql=hql+" o."+tj.getMappingField()+"="+tj.getId()+query+" AND ("+tj.getTypeSql()+")";
			this.update(uphql);
		}
		return null;
	}

	@Override
	public String reseTjfl(String query) {
		String hql="update JkSxjg o set o.type1=0,o.type2=0,o.type3=0,o.type4=0,o.type5=0,o.type6=0,o.type7=0,o.type8=0,o.type9=0,o.type10=0,o.type11=0,o.type12=0,o.type13=0,o.type14=0,o.type15=0,o.type16=0,o.type17=0,o.type18=0,o.type19=0,o.type20=0";
		this.update(hql+query);
		return null;
	}

}
