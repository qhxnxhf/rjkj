package com.rjkj.dao.imp;

import com.rjkj.dao.JkTjDeptDao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.rjkj.model.TjDept;
import com.rjkj.model.mapper.TjDeptMapper;

@Component("JkTjDeptDao")
public class JkTjDeptDaoImp implements JkTjDeptDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TjDept> findByPId(String pId) {
		List<TjDept> list=new ArrayList<TjDept>();
		String sql="SELECT * FROM metadept WHERE id='"+pId+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjDeptMapper());
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		
		
		
		return list;
	}
	@Override
	public List<TjDept> findDw() {
		List<TjDept> list= new ArrayList<TjDept>();
		String sql="SELECT * FROM metadept ";
		try {
			list=jdbcTemplate.query(sql.toString(), new Object[]{},new TjDeptMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

}
