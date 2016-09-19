package com.rjkj.dao;

import java.util.List;

public interface GenericDao<T, PK> {
	
	 /**    
     * 保存指定实体类    
     *     
     * @param entityobj    
     *            实体类    
     */    
    public  void save(T entity);   
       
       
      /**    
     * 删除指定实体    
     *     
     * @param entityobj    
     *            实体类    
     */     
    public void delete(T entity);   
       
    void delIds(Class<T> entityClass, String ids);
       
    /**    
    * 更新或保存指定实体    
    *     
    * @param entity 实体类    
    */    
    public void update(T entity);   
       
    
    public void update(final String hql);   
    
    /**    
     * 查找指定PK实体类对象    
     *     
     * @param entityClass    
     *            实体Class    
     * @param id    
     *            实体PK    
     * @return 实体对象    
     */      
    public T findById(Class<T> entityClass, PK id);  
       
       
    /**    
     * 获取所有实体集合    
     *     
     * @param entityClass    
     *            实体    
     * @return 集合    
     */     
    
    public List<T> findAll(String hql);  
    
    public List<T> findAll(Class<T> entityClass);   
    
    public List<T> findAll(Class<T> entityClass,String hql);  
       
    public List<T> findAll(Class<T> entityClass,String hql,int start, int limit);   
    
       
  
       
    /**  
     * 获得总记录数  
     */  
    public Integer getTotalCount(Class<T> entityClass);   
    
    public Integer getTotalCount(Class<T> entityClass,String hql);
    
    public Integer getTotalCount(String hql);   

	


}
