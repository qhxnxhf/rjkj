package com.rjkj.dao.imp;


import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.NewsLmSubDao;
import com.rjkj.entities.NewsLm;
import com.rjkj.entities.NewsLmSub;
import com.rjkj.entities.User;
import com.rjkj.util.web.Page;

@Transactional
@Component("NewsLmSubDao")
public class NewsLmSubDaoImp extends GenericDaoImpl<NewsLmSub, Long> implements NewsLmSubDao{

	@Override
	public List<NewsLmSub> findByLm(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		//String hql=" WHERE o.sort.id = "+parentId+" "+query;
		int tt=this.getTotalCount(NewsLmSub.class,query);
		page.setTotalRows(tt);
		return this.findAll(NewsLmSub.class, query, ss, ee);
	}

	@Override
	public List<NewsLm> findByUserId(String userId,String qu) {
		String hql="SELECT o.lm FROM NewsLmSub o WHERE o.user.id = "+userId +qu;
		Query query=this.getEm().createQuery(hql);
		List<NewsLm> listMd=query.getResultList();
		return listMd;
	}
	
	@Override
	public void delByUserId(String userId) {
		String hql=" DELETE FROM NewsLmSub  WHERE user.id ="+userId;
		this.update(hql);
		
	}
	
	@Override
	public void delByLmId(String lmId) {
		String hql=" DELETE FROM NewsLmSub  WHERE lm.id ="+lmId;
		this.update(hql);
		
	}

	@Override
	public void saveLmReceiver(NewsLm lm, String receiverIds) {
		String hql=" DELETE FROM NewsLmSub  WHERE lm.id ="+lm.getId();
		this.update(hql);
		if(receiverIds!=null&&!receiverIds.equals("")){
			String[] mods=receiverIds.split(",");
			Date sendDate=new Date();
			for(int i=0;i<mods.length;i++){
				String roleid=mods[i];
				NewsLmSub ur=new NewsLmSub();
				User taker=new User();
				taker.setId(Long.valueOf(roleid));
				ur.setLm(lm);;
				ur.setCreateDate(sendDate);
				ur.setUser(taker);
				ur.setStatus("1");
				this.save(ur);
			}
		}
		
	}

}
