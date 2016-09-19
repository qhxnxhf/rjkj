package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.NewsAttach;
import com.rjkj.util.web.Page;



public interface NewsAttachDao  extends GenericDao<NewsAttach, String>{

	public List<NewsAttach> findAll(String query, Page page);

	public List<NewsAttach> getAttachList(String pId, String size,String query);

	public void delFileByNewsIds(String ids,String rootPath);

	public void delFileByIds(String ids,String rootPath);

}
