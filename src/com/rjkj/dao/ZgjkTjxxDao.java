package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.JkTjxm;
import com.rjkj.model.Tjlb;
import com.rjkj.model.Tjxx;

public interface ZgjkTjxxDao {
	
	List<Tjxx> findByPId(String pId);
	
	List<Tjxx> findByCardId(String cId);
	
	List<Tjxx> findByMedicareId(String cId);
	
	List<Tjlb> findTjlb();//添加类别

	String xxcs(List<JkTjxm> cstj, Long index,String qu);

}
