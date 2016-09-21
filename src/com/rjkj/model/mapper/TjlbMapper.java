package com.rjkj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjkj.model.Tjlb;

public class TjlbMapper implements RowMapper<Tjlb>{
	
	@Override
	public Tjlb mapRow(ResultSet arg0, int arg1) throws SQLException {
		Tjlb tjx = new Tjlb();
		tjx.setId(arg0.getLong("id"));
		tjx.setLbName(arg0.getString("bxx_1"));
		return tjx;
	}

}
