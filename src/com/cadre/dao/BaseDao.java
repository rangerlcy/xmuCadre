package com.cadre.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.cadre.pojo.EducationHistory;
@Repository
public class BaseDao<T> extends BaseHibernateDaoSupport {
	/**
	 * 分页查询获取结果集
	 * @param hql
	 * @param args
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findForPage(final String hql,final Object[] args, final int currPage,final int pageSize)throws Exception{
		List<T> result = getHibernateTemplate().executeFind(
			new  HibernateCallback() {
	           @Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
	        	   Query query = session.createQuery(hql);
	        	   setParams(query,args);
	               query.setFirstResult((currPage-1) * pageSize);
	               query.setMaxResults(pageSize);
	               List<T> list = query.list();
	               return list;
	           }
            }
		);
		return result;
	}
	
    /**
     * 执行hql删除、修改语句
     * @param hql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
    public void executeUpdateQuery(final String hql, final Object[] args) {
        getHibernateTemplate().execute(new HibernateCallback() {
            @Override
			public Integer doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
                setParams(query, args);
                return query.executeUpdate();
            }
        });
    }
    
    /**
     * 批量修改
     * @param hql
     * @param args
     * @return 受影响行数
     */
    public int bulkUpdate(String hql, Object[] args) {
        return getHibernateTemplate().bulkUpdate(hql, args);
    }
 
    /**
     * 批量删除
     * 
     * @param collection 要删除的集合
     */
    public void deleteAll(Collection<?> collection) {
    	getHibernateTemplate().deleteAll(collection);
    }
	
	/**
	 * 统计查询
	 * @param hql
	 * @param args
	 * @return
	 */
    @SuppressWarnings("unchecked")
    public int findforCount(final String hql,final Object[] args) {
        List<Long> count = new ArrayList<Long>();
        count = getHibernateTemplate().executeFind(
			new  HibernateCallback() {
	           @Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
	        	   Query query = session.createQuery(hql);
	        	   setParams(query,args);
	               List<Long> list = query.list();
	               return list;
	           }
            }
		);
        if(count.size() > 0){
        	return count.get(0).intValue();
        }else{
        	return 0;
        }
    }

	/**
	 * 根据条件查找数据
	 * @param hql
	 * @param args
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public List<T> queryList(final String hql,final Object[] args){
		List<T> result = getHibernateTemplate().executeFind(
			new  HibernateCallback() {
	           @Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
	        	   Query query = session.createQuery(hql);
	        	   setParams(query,args);
	               List<T> list = query.list();
	               return list;
	           }
            }
		);
		return result;
	}
    
	/**
	 * 根据id查询某个对象
	 * @param clazz
	 * @param id
	 * @return
	 */
	public T findOne(Class clazz,Integer id){
		return (T)getHibernateTemplate().get(clazz, id);
	}
	
	/**
	 * 保存某个对象
	 * @param t
	 */
	public void save(T t){
		getHibernateTemplate().save(t);
	}
	
	/**
	 * 更新对象
	 * @param t
	 */
	public void update(T t){
		getHibernateTemplate().update(t);
	}
	
	/**
	 * 保存或更新对象
	 * @param t
	 */
	public void saveOrUpdate(T t){
		getHibernateTemplate().saveOrUpdate(t);
	}
	
	/**
	 * 删除对象
	 * @param t
	 */
	public void delete(Class clazz,Integer id){
		T t = (T)getHibernateTemplate().load(clazz, id);
		getHibernateTemplate().delete(t);
	}
	
	/**
	 * 删除对象
	 * @param t
	 */
	public void delete(T t){
		getHibernateTemplate().delete(t);
	}
	
	
	/**
	 * 设置参数
	 * @param query
	 * @param values
	 */
    public void setParams(Query query, Object[] values) {
        if (!isEmptyOrNull(values)) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
 
        }
    }
 
    /**
     * 判断值是否为空值
     * 
     * @param values
     * @return
     */
    public boolean isEmptyOrNull(Object[] values) {
        if (values == null || values.length == 0)
            return true;
        return false;
    }
	
}
