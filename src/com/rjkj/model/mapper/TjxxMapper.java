package com.rjkj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjkj.model.Tjxx;

public class TjxxMapper implements RowMapper<Tjxx>{

	@Override
	public Tjxx mapRow(ResultSet arg0, int arg1) throws SQLException {
		Tjxx tjx = new Tjxx();
		tjx.setId(arg0.getLong("id"));
		tjx.setMedicareId(arg0.getString("xx_4"));
		tjx.setCardId(arg0.getString("xx_5"));
		tjx.setTjDate(arg0.getDate("txx_1"));
		tjx.setTjpc(arg0.getString("gxx_6"));
		tjx.setTjType(arg0.getString("txx_2"));
		//tjx.setTjrId(tjrId);
		tjx.setTjkm(arg0.getString("txx_3"));
		tjx.setTjx(arg0.getString("txx_4"));
		//tjx.setTjxId(tjxId);
		tjx.setTjjg(arg0.getString("txx_9"));
		tjx.setDeptId(arg0.getLong("txx_16"));
		tjx.setTjAges(arg0.getInt("txx_15"));
		tjx.setSex(arg0.getString("txx_14"));
		
		return tjx;
	}

}
