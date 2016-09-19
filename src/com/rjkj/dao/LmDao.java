package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.NewsLm;
import com.rjkj.util.web.Page;



public interface LmDao  extends GenericDao<NewsLm, Long>{

	List<NewsLm> findByParentId(Long valueOf, String string);

	String getLmIdsByCase(Long valueOf);

	List<NewsLm> findAll(String string, Page page);

	void caseAuth(NewsLm dic);
	

	List<NewsLm> findByCase(Long parentId);

	NewsLm getLmById(String lmid, String string);

	

}
