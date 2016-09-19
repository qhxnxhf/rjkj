package com.rjkj.dao.imp;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.rjkj.dao.GenericDao;

@Transactional
public class GenericDaoImpl <T, PK extends Serializable> implements GenericDao<T, PK>  {

	@PersistenceContext 
	private EntityManager em; 
	
	
	public EntityManager getEm() {
		return em;
	}

	@Override
	public void save(T entity) {
		em.persist(entity);
		
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);
	}
	
	@Override
	public void delIds(Class<T> entityClass,String ids) {
		String hql=" DELETE FROM "+entityClass.getName()+" WHERE id IN("+ids+")";
		Query query = em.createQuery(hql); 
        query.executeUpdate(); 
	}


	@Override
	public void update(T entity) {
		em.merge(entity);
	}
	
	
	@Override
	public void update(String hql) {
		  Query query = em.createQuery(hql); 
          query.executeUpdate();   
	}
	
	@Override
	public T findById(Class<T> entityClass, PK id) {
		
		return em.find(entityClass, id);
	}


	@Override
	public List<T> findAll(Class<T> entityClass) {
		 Query query=em.createQuery("select o from "+entityClass.getName()+" o");   
         
		 return query.getResultList();   
	}
	
	@Override
	public List<T> findAll(Class<T> entityClass, String hql) {
		Query query=em.createQuery("select o from "+entityClass.getName()+" o "+hql); 
		return query.getResultList(); 
	}


	@Override
	public List<T> findAll(Class<T> entityClass, String hql, int start, int limit) {
		Query query=em.createQuery("select o from "+entityClass.getName()+" o "+hql);   
		query.setFirstResult(start).setMaxResults(limit);   
		return query.getResultList(); 
		
	}

	
	
	@Override
	public Integer getTotalCount(Class<T> entityClass) {
		Query query = em.createQuery("select count(o) from "+entityClass.getName()+" o"); 
				
		Object tt=query.getSingleResult();
		if(tt!=null){
			Long ll=(Long)tt;
			return ll.intValue();
		}else{
			return 0;
		}
		
		
	}

	@Override
	public Integer getTotalCount(Class<T> entityClass, String hql) {
		Query query = em.createQuery("select count(o) from "+entityClass.getName()+" o "+hql); 
		Object tt=query.getSingleResult();
		if(tt!=null){
			Long ll=(Long)tt;
			return ll.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public Integer getTotalCount(String hql) {
		Query query = em.createQuery(hql); 
		
		Object tt=query.getSingleResult();
		if(tt!=null){
			Long ll=(Long)tt;
			return ll.intValue();
		}else{
			return 0;
		}
	}

	@Override
	public List<T> findAll(String hql) {
		Query query = em.createQuery(hql); 
		return query.getResultList();   
	}
	
	
	
	/**

	@Override
	public List<T> findByHql(String hql, Object[] params) {
		 Query query = em.createQuery(hql);   
         for(int i=0; i<params.length; i++){   
             query.setParameter(i, params[i]);   
         }   
        return  query.getResultList();
	}
	
	@Override
	public List<T> findByHql(String hql, Object[] params, int start, int limit) {
		Query query = em.createQuery(hql);   
        for(int i=0; i<params.length; i++){   
            query.setParameter(i, params[i]);   
        }   
        query.setFirstResult(start).setMaxResults(limit);   
        return  query.getResultList();
	}

	@Override
	public Integer getTotalCount(String hql, Object[] params) {
		Query query = em.createQuery(hql);   
        for(int i=0; i<params.length; i++){   
            query.setParameter(i, params[i]);   
        }   
        Object tt=query.getSingleResult();
		if(tt!=null){
			Long ll=(Long)tt;
			return ll.intValue();
		}else{
			return 0;
		}
	}

	**/

	

}
