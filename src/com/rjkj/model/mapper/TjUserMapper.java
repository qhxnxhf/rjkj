package com.rjkj.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.rjkj.model.TjUser;

public class TjUserMapper implements RowMapper<TjUser>{
	@Override
	public TjUser mapRow(ResultSet arg0, int arg1) throws SQLException {
		TjUser xx=new TjUser();
		xx.setId(arg0.getLong("id"));
		xx.setName(arg0.getString("xx_1"));
		xx.setSex(arg0.getString("xx_2"));
		xx.setCsrq(arg0.getDate("xx_3"));
		xx.setMedicareId(arg0.getString("xx_4"));
		xx.setCardId(arg0.getString("xx_5"));
		xx.setMinzu(arg0.getString("xx_6"));
		xx.setJiguan(arg0.getString("xx_7"));
		xx.setJtzz(arg0.getString("xx_8"));
		xx.setJobe(arg0.getDate("xx_9"));
		xx.setGzdw(arg0.getString("xx_10"));
		xx.setDwdz(arg0.getString("xx_11"));
		xx.setDwPhone(arg0.getString("xx_12"));
		xx.setYoubian(arg0.getString("xx_13"));
		xx.setWhcd(arg0.getString("xx_14"));
		xx.setDwlx(arg0.getString("xx_15"));
		xx.setDabh(arg0.getString("xx_16"));
		xx.setDwbm(arg0.getLong("xx_17"));
		xx.setHf(arg0.getString("xx_18"));
		xx.setGzdd(arg0.getString("xx_19"));
		xx.setZw(arg0.getString("xx_20"));
		xx.setTelephone(arg0.getString("xx_21"));
		return xx;
	}
	
}
