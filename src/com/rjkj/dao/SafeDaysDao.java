package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.Dic;
import com.rjkj.entities.Organize;
import com.rjkj.entities.SafeDays;
import com.rjkj.util.web.Page;

public interface SafeDaysDao  extends GenericDao<SafeDays, Long>{

	List<SafeDays> findAll(String query, Page page);

	void save(Organize org, List<Dic> dics);

}
