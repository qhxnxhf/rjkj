package com.rjkj.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.LmDao;
import com.rjkj.entities.NewsLm;
import com.rjkj.util.web.Page;




@Transactional
@Component("LmDao")
public class LmDaoImp extends GenericDaoImpl<NewsLm, Long> implements LmDao{

	@Override
	public List<NewsLm> findByParentId(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(NewsLm.class, hql);
	}

	

	@Override
	public List<NewsLm> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(NewsLm.class,query);
		page.setTotalRows(tt);
		return this.findAll(NewsLm.class, query, ss, ee);
	}

	@Override
	public void caseAuth(NewsLm dic) {
		String ids=getLmIdsByCase(dic.getId());
		String userhql="UPDATE NewsLm set orgIdsMl='"+dic.getOrgIdsMl()+"',orgIdsWj='"+dic.getOrgIdsWj()+"' WHERE id IN ("+ids+")";
		this.update(userhql);
		
	}

	

	@Override
	public List<NewsLm> findByCase(Long parentId) {
		List<NewsLm> orgs=new ArrayList<NewsLm>();
		findAllSun(parentId,orgs);
		return orgs;
		
	}

	@Override
	public String getLmIdsByCase(Long parentId) {
		StringBuffer ids=new StringBuffer();
		ids.append(parentId);
		List<NewsLm> orgs=findByCase(parentId);
		if(orgs!=null&&orgs.size()>0){
			for(NewsLm org:orgs){
				ids.append(",");
				ids.append(org.getId());
			}
		}
		return ids.toString();
	}
	
	//递归查询所有的字节点
	public void findAllSun(Long parentId,List<NewsLm> sunOrgs ) {
			String hql=" WHERE o.parent.id="+parentId;
			List<NewsLm> orgs=this.findAll(NewsLm.class, hql);
			if(orgs!=null&&orgs.size()>0){
				for(NewsLm org:orgs){
					sunOrgs.add(org);
					findAllSun(org.getId(),sunOrgs);
				}
			}else{
				return ;
			}
			
	}



	@Override
	public NewsLm getLmById(String lmid, String query) {
		String hql=" WHERE o.id = "+lmid+" "+query;
		List<NewsLm> lms= this.findAll(NewsLm.class, hql);
		if(lms!=null&&lms.size()>0)
			return lms.get(0);
		else
			return null;
		
	}

}
