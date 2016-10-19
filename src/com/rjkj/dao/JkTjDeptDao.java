package com.rjkj.dao;

import java.util.List;

import com.rjkj.model.TjDept;

public interface JkTjDeptDao {
	
	List<TjDept> findByPId(String pId);
	
	List<TjDept> findDw();
}
