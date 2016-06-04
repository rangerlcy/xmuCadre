package com.cadre.dao;


import org.springframework.dao.support.DaoSupport;
import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

public class BaseHibernateDaoSupport extends DaoSupport {
	private Logger logger = LogManager.getLogger(BaseHibernateDaoSupport.class);
	private SessionFactory sessionFactory;
    private HibernateTemplate hibernateTemplate;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Resource(name="sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate=createHibernateTemplate(sessionFactory);
    }

    public Session getSession() {
        if (this.sessionFactory == null) {
        	logger.error("Session Create Fail,SessionFactory is null!");
            throw new HibernateException("Session Create Fail,SessionFactory is null!");
        }
        return this.sessionFactory.getCurrentSession();
    }

    protected HibernateTemplate createHibernateTemplate(
            SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
        if (this.hibernateTemplate == null) {
        	logger.error("'sessionFactory' or 'hibernateTemplate' is required");
            throw new IllegalArgumentException("'sessionFactory' or 'hibernateTemplate' is required");
        }
    }
    
    protected final Session getSession(boolean allowCreate)
            throws DataAccessResourceFailureException, IllegalStateException {
        return (!allowCreate ? SessionFactoryUtils.getSession(
                getSessionFactory(), false) : SessionFactoryUtils.getSession(
                getSessionFactory(),

     this.hibernateTemplate.getEntityInterceptor(),    this.hibernateTemplate.getJdbcExceptionTranslator()));
    }

    protected final DataAccessException convertHibernateAccessException(
            HibernateException ex) {
        return this.hibernateTemplate.convertHibernateAccessException(ex);
    }

    protected final void releaseSession(Session session) {
        SessionFactoryUtils.releaseSession(session, getSessionFactory());
        if(session!=null)session=null;
    }

    public final void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public final HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }
}
