package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkTjxm;
import com.rjkj.entities.JkYcxm;
import com.rjkj.model.Tjxx;
import com.rjkj.util.web.Page;

public interface JkYcxmDao extends GenericDao<JkYcxm, Long>{
	
	public List<JkYcxm> findAll(String query, Page page);

	public JkYcxm findMaxId();

	public void saveXxcs(Tjxx tjx, List<JkTjxm> cstj);

	public String delByHql(String query);

}
