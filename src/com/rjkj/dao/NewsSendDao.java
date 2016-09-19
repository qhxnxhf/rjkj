package com.rjkj.dao;

import java.util.List;

import com.rjkj.entities.News;
import com.rjkj.entities.NewsSend;
import com.rjkj.entities.User;
import com.rjkj.util.web.Page;

public interface NewsSendDao   extends GenericDao<NewsSend, Long>{
	
	public List<NewsSend> findByNews(Long newsId,String query);
	
	public List<NewsSend> findByNews(String query,Page page);
	
	public List<User> findReceiverByNews(Long newsId,String query);
	
	public List<User> findSenderByNews(Long newsId,String query);
	
	public List<News> findByReceiver(Long userId,String query);
	
	public List<News> findBySender(Long userId,String query);
	
	public void delByNewsId(String newsId);
	
	public void saveNewsSend(News news, User sender, String receiverIds);

	public NewsSend findByUserAndNews(Long id, String newsId);
	
	

}
