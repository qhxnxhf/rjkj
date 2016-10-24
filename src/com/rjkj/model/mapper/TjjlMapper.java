package com.rjkj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjkj.model.Tjjl;

public class TjjlMapper implements RowMapper<Tjjl>{
	
	@Override
	public Tjjl mapRow(ResultSet arg0, int arg1) throws SQLException {
		Tjjl tjx = new Tjjl();
		tjx.setId(arg0.getLong("id"));
		tjx.setMedicareId(arg0.getString("xx_4"));
		tjx.setCardId(arg0.getString("xx_5"));
		tjx.setTjDate(arg0.getDate("txx_22"));
		tjx.setTjpc(arg0.getString("gxx_6"));
		tjx.setTjType(arg0.getString("txx_21"));
		tjx.setTjjl(arg0.getString("txx_14"));
		tjx.setJdjl(arg0.getString("txx_20"));
		tjx.setJkjl(arg0.getString("txx_24"));
		tjx.setHospital(arg0.getString("txx_15"));
		tjx.setDeptName(arg0.getString("txx_18"));
		tjx.setQrf(arg0.getString("txx_16"));
		return tjx;
	}

}
