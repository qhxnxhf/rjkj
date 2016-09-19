package com.rjkj.dao.imp;


import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.rjkj.dao.NewsSendDao;
import com.rjkj.entities.News;
import com.rjkj.entities.NewsSend;
import com.rjkj.entities.User;
import com.rjkj.util.web.Page;


@Transactional
@Component("NewsSendDao") 
public class NewsSendDaoImp  extends GenericDaoImpl<NewsSend, Long> implements NewsSendDao{

	@Override
	public List<NewsSend> findByNews(Long newsId, String qu) {
		String hql="FROM NewsSend o WHERE o.news.id = "+newsId +qu;
		Query query=this.getEm().createQuery(hql);
		List<NewsSend> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public List<User> findReceiverByNews(Long newsId, String qu) {
		String hql="SELECT o.taker FROM NewsSend o WHERE o.news.id = "+newsId +qu;
		Query query=this.getEm().createQuery(hql);
		List<User> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public List<User> findSenderByNews(Long newsId, String qu) {
		String hql="SELECT o.sender FROM NewsSend o WHERE o.news.id = "+newsId +qu;
		Query query=this.getEm().createQuery(hql);
		List<User> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public List<News> findByReceiver(Long userId, String qu) {
		String hql="SELECT o.news FROM NewsSend o WHERE o.taker.id = "+userId +qu;
		Query query=this.getEm().createQuery(hql);
		List<News> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public List<News> findBySender(Long userId, String qu) {
		String hql="SELECT o.news FROM NewsSend o WHERE o.sender.id = "+userId +qu;
		Query query=this.getEm().createQuery(hql);
		List<News> listMd=query.getResultList();
		return listMd;
	}

	@Override
	public void delByNewsId(String newsId) {
		String hql=" DELETE FROM NewsSend  WHERE news.id ="+newsId;
		this.update(hql);
		
	}

	@Override
	public void saveNewsSend(News news, User sender, String receiverIds) {
		String hql=" DELETE FROM NewsSend  WHERE news.id ="+news.getId();
		this.update(hql);
		if(receiverIds!=null&&!receiverIds.equals("")){
			String[] mods=receiverIds.split(",");
			Date sendDate=new Date();
			for(int i=0;i<mods.length;i++){
				String roleid=mods[i];
				NewsSend ur=new NewsSend();
				User taker=new User();
				taker.setId(Long.valueOf(roleid));
				ur.setNews(news);
				ur.setSendDate(sendDate);
				ur.setSender(sender);
				ur.setTaker(taker);
				ur.setStatus("1");
				this.save(ur);
			}
		}
		
	}

	@Override
	public List<NewsSend> findByNews(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		//String hql=" WHERE o.sort.id = "+parentId+" "+query;
		int tt=this.getTotalCount(NewsSend.class,query);
		page.setTotalRows(tt);
		return this.findAll(NewsSend.class, query, ss, ee);
	}

	@Override
	public NewsSend findByUserAndNews(Long userid, String newsid) {
		String hql="SELECT o FROM NewsSend o WHERE o.taker.id = "+userid +" AND o.news.id="+newsid;
		Query query=this.getEm().createQuery(hql);
		List<NewsSend> listMd=query.getResultList();
		if(listMd!=null&&listMd.size()>0){
			return listMd.get(0);
		}
		return null;
	}

}
