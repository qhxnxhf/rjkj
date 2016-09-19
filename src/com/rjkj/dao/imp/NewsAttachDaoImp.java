package com.rjkj.dao.imp;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;









import com.rjkj.dao.NewsAttachDao;
import com.rjkj.entities.NewsAttach;
import com.rjkj.util.web.Page;

@Transactional
@Component("NewsAttachDao")
public class NewsAttachDaoImp extends GenericDaoImpl<NewsAttach, String> implements NewsAttachDao{

	@Override
	public List<NewsAttach> findAll(String query, Page page) {
		// TODO Auto-generated method stub
		int ss=(page.getPageNo()-1)*page.getPageSize();
		int ee=page.getPageSize();
		int tt=this.getTotalCount(NewsAttach.class,query);
		page.setTotalRows(tt);
		return this.findAll(NewsAttach.class, query, ss, ee);
	}

	@Override
	public List<NewsAttach> getAttachList(String newsId, String size, String query) {
		
		String hql=" WHERE 1=1 AND o.news.id="+newsId+query;
		return this.findAll(NewsAttach.class, hql, 0, Integer.valueOf(size));
		
	}

	@Override
	public void delFileByNewsIds(String ids,String rootPath) {
		String hql=" WHERE 1=1 AND o.news.id IN("+ids+")";	
		List<NewsAttach> attachs=this.findAll(NewsAttach.class, hql);
		File docfile=null;
		for(NewsAttach attach:attachs){
			docfile=new File(rootPath+attach.getFilePath());
			if(docfile.exists())
				 docfile.delete();
			this.delete(attach);
			
		}
	}

	@Override
	public void delFileByIds(String ids,String rootPath) {
		String[] id1=ids.split(",");
		String ids2="";
		for(int i=0;i<id1.length;i++){
			ids2=ids2+",'"+id1[i]+"'";
		}
		ids2=ids2.substring(1);
		String hql=" WHERE 1=1 AND o.id IN("+ids2+")";	
		List<NewsAttach> attachs=this.findAll(NewsAttach.class, hql);
		File docfile=null;
		for(NewsAttach attach:attachs){
			docfile=new File(rootPath+attach.getFilePath());
			if(docfile.exists())
				 docfile.delete();
			this.delete(attach);
			
		}
		
	}

}
