package com.rjkj.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.rjkj.dao.ZgjkTjxxDao;
import com.rjkj.model.Tjxx;
import com.rjkj.model.mapper.TjxxMapper;


@Component("ZgjkTjxxDao")
public class ZgjkTjxxDaoImp implements ZgjkTjxxDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Tjxx> findByPId(String pId) {
		List <Tjxx> list=new ArrayList<Tjxx>();
		String sql="SELECT * FROM jtjxx WHERE  id='"+pId+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjxxMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<Tjxx> findByCardId(String cId) {
		List <Tjxx> list=new ArrayList<Tjxx>();
		String sql="SELECT * FROM jtjxx WHERE  xx_5='"+cId+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjxxMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<Tjxx> findByMedicareId(String cId) {
		List <Tjxx> list=new ArrayList<Tjxx>();
		String sql="SELECT * FROM jtjxx WHERE  xx_4='"+cId+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjxxMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

}
