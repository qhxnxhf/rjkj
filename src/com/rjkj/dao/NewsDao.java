package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.News;
import com.rjkj.util.web.Page;



public interface NewsDao  extends GenericDao<News, Long>{

	List<News> findAll(String query, Page page);

	News getNewsById(String newsId, String query);

	List<News> getNewsList(String pId, String size, String query);
	
	List<News> getNewsByLms(String pId, String size, String query);

	List<News> getNewsTjList(String pId, String size, String query);

	List<News> getNewsTtList(String pId, String size, String query);

	void getNewsList(Page page, String query);

	void delIds(String ids);

}
