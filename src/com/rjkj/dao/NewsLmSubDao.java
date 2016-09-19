package com.rjkj.dao;


import java.util.List;

import com.rjkj.entities.NewsLm;
import com.rjkj.entities.NewsLmSub;
import com.rjkj.util.web.Page;

public interface NewsLmSubDao extends GenericDao<NewsLmSub, Long>{

	public List<NewsLmSub> findByLm(String query, Page page);

	public List<NewsLm> findByUserId(String userId,String qu);

	public void delByUserId(String userId);

	public void delByLmId(String lmId);

	public void saveLmReceiver(NewsLm lm, String receiverIds);

}
