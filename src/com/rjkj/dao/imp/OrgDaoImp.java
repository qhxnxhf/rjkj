package com.rjkj.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.OrgDao;
import com.rjkj.entities.Organize;
import com.rjkj.util.web.Page;


@Transactional
@Component("OrgDao")
public class OrgDaoImp extends GenericDaoImpl<Organize, Long> implements OrgDao {

	@Override
	public List<Organize> findByParentId(Long parentId, String query,int start, int limit) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(Organize.class, hql, start, limit);
	}

	@Override
	public Integer getTotle(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.getTotalCount(Organize.class, hql);
	}

	@Override
	public List<Organize> findByParentId(Long parentId, String query) {
		String hql=" WHERE o.parent.id = "+parentId+" "+query;
		return this.findAll(Organize.class, hql);
	}

	@Override
	public List<Organize> findAll(String query, Page page) {
		// TODO Auto-generated method stub
				int ss=(page.getPageNo()-1)*page.getPageSize();
				int ee=page.getPageSize();
				int tt=this.getTotalCount(Organize.class,query);
				page.setTotalRows(tt);
				return this.findAll(Organize.class, query, ss, ee);
	}

	

	@Override
	public String getOrgIdsByCase(Long parentId){
		StringBuffer ids=new StringBuffer();
		ids.append(parentId);
		List<Organize> orgs=findByCase(parentId);
		if(orgs!=null&&orgs.size()>0){
			for(Organize org:orgs){
				ids.append(",");
				ids.append(org.getId());
			}
		}
		return ids.toString();
	}
	
	@Override
	public List<Organize> findByCase(Long parentId){
		List<Organize> orgs=new ArrayList<Organize>();
		findAllSun(parentId,orgs);
		return orgs;
	}

	//递归查询所有的字节点
	public void findAllSun(Long parentId,List<Organize> sunOrgs ) {
		String hql=" WHERE o.parent.id="+parentId;
		List<Organize> orgs=this.findAll(Organize.class, hql);
		if(orgs!=null&&orgs.size()>0){
			for(Organize org:orgs){
				sunOrgs.add(org);
				findAllSun(org.getId(),sunOrgs);
			}
		}else{
			return ;
		}
		
	}

}
