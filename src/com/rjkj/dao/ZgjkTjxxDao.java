package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkTjxm;
import com.rjkj.model.Tjjl;
import com.rjkj.model.Tjlb;
import com.rjkj.model.Tjxx;

public interface ZgjkTjxxDao {
	
	public List<Tjxx> findByPId(String pId);
	
	public List<Tjxx> findByCardId(String cId);
	
	public List<Tjxx> findByMedicareId(String cId);
	
	public List<Tjlb> findTjlb();//添加类别

	public String xxcs(List<JkTjxm> cstj, Long index,String qu);
	
	public Tjjl findTjjlById(String id);
	
	public List<Tjjl> findTjjl(String cId);
	
	public List<Tjjl> findTjjlByType(String cId,String tjType);
	
	public List<Tjjl> findTjjlByPc(String cId,String tjpc);

}
