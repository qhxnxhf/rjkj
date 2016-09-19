package com.rjkj.dao.imp;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.NewsCommentDao;
import com.rjkj.entities.NewsComment;

@Transactional
@Component("NewsCommentDao")
public class NewsCommentDaoImp extends GenericDaoImpl<NewsComment, Long> implements NewsCommentDao{

}
