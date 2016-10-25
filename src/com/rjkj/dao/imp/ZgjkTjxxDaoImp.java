package com.rjkj.dao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import com.rjkj.dao.JkYcxmDao;
import com.rjkj.dao.ZgjkTjxxDao;
import com.rjkj.entities.JkTjxm;
import com.rjkj.entities.JkYcxm;
import com.rjkj.model.TjUser;
import com.rjkj.model.Tjjl;
import com.rjkj.model.Tjlb;
import com.rjkj.model.Tjxx;
import com.rjkj.model.mapper.TjjlMapper;
import com.rjkj.model.mapper.TjlbMapper;
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
		String sql="SELECT * FROM jtjxx WHERE  xx_5='"+cId+"' Order By gxx_6,txx_3";
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
		String sql="SELECT * FROM jtjxx WHERE  xx_4='"+cId+"'"+"' Order By gxx_6,txx_3";;
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjxxMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	@Override
	public List<Tjxx> findBySql(String query) {
		List <Tjxx> list=new ArrayList<Tjxx>();
		String sql="SELECT * FROM jtjxx "+query+" Order By gxx_6,txx_3";;
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjxxMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	

	@Override
	public List<Tjlb> findTjlb() {
		List <Tjlb> list=new ArrayList<Tjlb>();
		String sql="SELECT * FROM jtjlb ";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjlbMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	@Autowired
	private JkYcxmDao ycService;

	@Override
	public String xxcs(final List<JkTjxm> cstj, final HashMap<String, TjUser> userHash,Long index,String qu) {
		
		String sql="SELECT * FROM jtjxx WHERE  id>"+index+qu+" order by id asc";
		jdbcTemplate.query(sql.toString(),new ResultSetExtractor<List<Tjxx>>(){
			
			@Override	
			public List<Tjxx> extractData(ResultSet rs) throws SQLException,DataAccessException {
				
				List<Tjxx> xxs=new ArrayList<Tjxx>();
				Tjxx tjx = new Tjxx();
				while(rs.next()){ 
					TjUser user=userHash.get(rs.getString("xx_5"));
					tjx.setTjrId(user.getId());
					tjx.setName(user.getName());
					
					tjx.setId(rs.getLong("id"));
					tjx.setMedicareId(rs.getString("xx_4"));
					tjx.setCardId(rs.getString("xx_5"));
					tjx.setTjDate(rs.getDate("txx_1"));
					tjx.setTjpc(rs.getString("gxx_6"));
					tjx.setTjType(rs.getString("txx_2"));
					
					tjx.setTjkm(rs.getString("txx_3"));
					tjx.setTjx(rs.getString("txx_4"));
					//tjx.setTjxId(tjxId);
					tjx.setTjjg(rs.getString("txx_9"));
					tjx.setDeptId(rs.getLong("txx_16"));
					tjx.setTjAges(rs.getInt("txx_15"));
					tjx.setSex(rs.getString("txx_14"));
					ycService.saveXxcs(tjx,cstj);
				}
				
				//添加检索ID的位置记录
				if(tjx.getId()!=null&&tjx.getId()>0){
					JkYcxm ycxm=new JkYcxm();
					ycxm.setTjxxId(tjx.getId());
					ycxm.setName("Index");
					ycxm.setTjAges(-1);
					ycService.save(ycxm);
				}
				return xxs;
			}
		});
		
		return null;
	}
	
	public String xxcs2(List<JkTjxm> cstj, Long index) {
		String sql="SELECT * FROM jtjxx WHERE  id>"+index;
		
		jdbcTemplate.query(sql.toString(), new Object[]{},new RowCallbackHandler(){
			
			public void processRow(ResultSet arg0)throws SQLException{
				
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
				
			}
		});
		
		return null;
	}

	@Override
	public Tjjl findTjjlById(String id) {
		List <Tjjl> list=new ArrayList<Tjjl>();
		String sql="SELECT * FROM jjlxx WHERE  id="+id;
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjjlMapper());
			if(list!=null&&list.size()>0)
				return list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	public List<Tjjl> findTjjl(String cId) {
		List <Tjjl> list=new ArrayList<Tjjl>();
		String sql="SELECT * FROM jjlxx WHERE  xx_5='"+cId+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjjlMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<Tjjl> findTjjlByPc(String cId, String tjpc) {
		List <Tjjl> list=new ArrayList<Tjjl>();
		String sql="SELECT * FROM jjlxx WHERE  xx_5='"+cId+"' AND  gxx_6='"+tjpc+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjjlMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<Tjjl> findTjjlByType(String cId, String tjType) {
		List <Tjjl> list=new ArrayList<Tjjl>();
		String sql="SELECT * FROM jjlxx WHERE  xx_5='"+cId+"' AND  txx_21='"+tjType+"'";
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjjlMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	@Override
	public List<Tjxx> findByCardId(String cId, String type) {
		List <Tjxx> list=new ArrayList<Tjxx>();
		String sql="SELECT * FROM jtjxx  xx_4='"+cId+"'"+"' AND txx_2='"+type+"' Order By gxx_6,txx_3";;
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjxxMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	
	

	@Override
	public List<Tjjl> findTjjlBySql(String query) {
		List <Tjjl> list=new ArrayList<Tjjl>();
		String sql="SELECT * FROM jjlxx "+query;;
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[]{},new TjjlMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}

	

}
