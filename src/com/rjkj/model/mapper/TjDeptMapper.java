package com.rjkj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjkj.model.TjDept;

public class TjDeptMapper implements RowMapper<TjDept>{
	
	@Override
	public TjDept mapRow(ResultSet arg0, int arg1) throws SQLException {
		TjDept mtd=new TjDept();
		mtd.setId(arg0.getLong("id"));
		mtd.setName(arg0.getString("name"));
		mtd.setPhone(arg0.getString("telephone"));
		mtd.setAddress(arg0.getString("address"));
		mtd.setYoubian(arg0.getString("zcp"));
		mtd.setDwlx(arg0.getString("dwlx"));
		return mtd;
	}

}
