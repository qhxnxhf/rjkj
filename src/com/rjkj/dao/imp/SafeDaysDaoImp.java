package com.rjkj.dao.imp;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.SafeDaysDao;
import com.rjkj.entities.Dic;
import com.rjkj.entities.Organize;
import com.rjkj.entities.SafeDays;
import com.rjkj.util.web.Page;

@Transactional
@Component("SafeDaysDao")
public class SafeDaysDaoImp   extends GenericDaoImpl<SafeDays, Long> implements SafeDaysDao{

	@Override
	public List<SafeDays> findAll(String query, Page page) {
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(SafeDays.class,query);
		page.setTotalRows(tt);
		return this.findAll(SafeDays.class, query, ss, ee);
	}

	@Override
	public void save(Organize org, List<Dic> dics) {
		Date dd=new Date();
		int i=0;
		for(Dic dic:dics){
			i=i+1;
			SafeDays day=new SafeDays();
			day.setOrg(org);
			day.setDic(dic);
			day.setTitle(dic.getDicName());
			day.setBeginTime(dd);
			day.setOrderNum(i);
			day.setAllowed("y");
			this.save(day);
		}
		
	}

}
