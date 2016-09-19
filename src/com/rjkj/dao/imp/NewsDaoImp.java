package com.rjkj.dao.imp;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.NewsDao;
import com.rjkj.entities.News;
import com.rjkj.util.web.Page;



@Transactional
@Component("NewsDao")
public class NewsDaoImp extends GenericDaoImpl<News, Long> implements NewsDao{

	@Override
	public List<News> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		//String hql=" WHERE o.sort.id = "+parentId+" "+query;
		int tt=this.getTotalCount(News.class,query);
		page.setTotalRows(tt);
		return this.findAll(News.class, query, ss, ee);
	}

	@Override
	public News getNewsById(String newsId, String query) {
		News news=this.findById(News.class, Long.valueOf(newsId));
		return news;
	}

	@Override
	public List<News> getNewsList(String pId, String size, String query) {
		// TODO Auto-generated method stub
		String hql=" WHERE 1=1 AND o.lm.id="+pId+query;
		return this.findAll(News.class, hql, 0, Integer.valueOf(size));
	}

	@Override
	public List<News> getNewsTjList(String pId, String size, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<News> getNewsTtList(String pId, String size, String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getNewsList(Page page, String query) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delIds(String ids) {
		String hql=" DELETE FROM News WHERE id IN("+ids+") AND newslock<1 ";
		Query query = this.getEm().createQuery(hql); 
	    query.executeUpdate(); 
	}

	@Override
	public List<News> getNewsByLms(String lms, String size, String query) {
		String hql=" WHERE 1=1 AND o.lm.id IN("+lms+")"+query;
		return this.findAll(News.class, hql, 0, Integer.valueOf(size));
	}
	
}
