package com.rjkj.dao;

import java.util.List;

import com.rjkj.model.TjUser;
import com.rjkj.util.web.Page;

public interface JkTjUserDao {
	
	public List<TjUser> findAll(String query,Page page);
	
	public TjUser findById(String cardId);
}
