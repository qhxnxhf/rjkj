package com.rjkj.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.rjkj.dao.JkTjUserDao;
import com.rjkj.model.TjUser;
import com.rjkj.model.mapper.TjUserMapper;
import com.rjkj.util.web.Page;

@Component("JkTjUserDao")
public class JkTjUserDaoImp implements JkTjUserDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<TjUser> findAll(String query, Page page) {
		
//		String sql="SELECT TOP e FROM ARTICLE WHERE ID NOT IN(SELECT TOP 45000 ID FROM ARTICLE ORDER BY YEAR DESC, ID DESC) ORDER BY YEAR DESC,ID DESC";
   
		String sql="SELECT count(*) as cc FROM jbxx "+query;
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(sql);
		page.setTotalRows(tt);
		return this.findAll(query, ss, ee);
	}
	
	public Integer getTotalCount(String sql) {
		try {
			final List result = new ArrayList();
			jdbcTemplate.query(sql, new Object[]{}, new RowCallbackHandler() {  
			      @Override  
			      public void processRow(ResultSet rs) throws SQLException {  			         
			    	 result.add( rs.getInt("cc"));
			  }});  
			if(result!=null&&result.size()>0){
				Integer cc=(Integer)result.get(0);;
				return cc;
			}else{
				return 0;
			}
				
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
		}
	}
	
	
	public List<TjUser> findAll(String query, Integer ss,Integer ee) {
//		String sql="SELECT * FROM TjUser "+query+" ORDER BY id  LIMIT "+ss+","+ee;
		String sql="SELECT TOP "+ee+" * FROM jbxx "+query+" AND ID NOT IN(SELECT TOP "+ss+" ID FROM jbxx " +query+ ")";
//		String sql="select top "+ee+" * from TjUser  where id not in( select top 开始的位置 id from table1)";
		List<TjUser> list=new ArrayList<TjUser>();
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjUserMapper());
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	@Override
	public HashMap<String,TjUser> findAll(String query) {
		String sql="SELECT * FROM jbxx "+query+" ORDER BY id ";
			List<TjUser> list=new ArrayList<TjUser>();
			HashMap<String,TjUser> hash=new HashMap<String,TjUser>();
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjUserMapper());
			if(list!=null&&list.size()>0){
				for(TjUser user:list){
					hash.put(user.getCardId(), user);
				}
				return hash;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
	

	@Override
	public TjUser findById(String id) {
		List<TjUser> list=new ArrayList<TjUser>();
		String sql="SELECT * FROM jbxx WHERE  id="+id;
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjUserMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return list.get(0);
	}
	
	@Override
	public TjUser findBycardId(String cid) {
		List<TjUser> list=new ArrayList<TjUser>();
		String sql="SELECT * FROM jbxx WHERE  xx_5='"+cid+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjUserMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return list.get(0);
	}


}
