package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import org.apache.log4j.Logger;
import org.hibernate.*;
import org.hibernate.annotations.FlushModeType;
import org.hibernate.cfg.annotations.ResultsetMappingSecondPass;
import org.hibernate.classic.Session;
import org.hibernate.connection.ConnectionProvider;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class BaseDAO extends HibernateDaoSupport {
    private static Logger logger = Logger.getLogger(BaseDAO.class);
    Connection connection = null;
    ResultSet resultSet = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;


    protected void save(Object obj) throws DAOException{
        getHibernateTemplate().setFlushMode(FlushModeType.AUTO.ordinal());
        Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
        session.setFlushMode(FlushMode.AUTO);
//        Transaction tx= session.beginTransaction();

        try {
              getHibernateTemplate().saveOrUpdate(obj);
//            session.flush();
        } catch (Exception e){
//            tx.rollback();
            logger.debug("error on saving"+ obj.toString() +" \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }

    }

    protected void update(Object obj) {
        getHibernateTemplate().update(obj);
    }

    protected void saveOrUpdate(Object obj) {
        getHibernateTemplate().saveOrUpdate(obj);
    }

    protected void saveOrUpdateAll(Collection coll) {
        getHibernateTemplate().saveOrUpdateAll(coll);
    }

    protected void delete(Object obj) {
        getHibernateTemplate().delete(obj);
    }

    protected void deleteAll(List list) {
        getHibernateTemplate().deleteAll(list);
    }

    protected Object findById(Class classObj, Integer id) {
        return getHibernateTemplate().get(classObj, id);
    }

    protected List findAll(Class classObj) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classObj)
                .list();
    }

    protected List findByIds(Class classObj, String property, String[] ids) {

        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj);

        Criterion criterion = null;
        for (String id : ids) {
            if (criterion == null)
                criterion = Restrictions.eq(property, Integer.valueOf(id));
            else
                criterion = Restrictions.or(criterion, Restrictions.eq(property, Integer.valueOf(id)));
        }

        if (criterion != null)
            criteria.add(criterion);
        return criteria.list();
    }

    protected Object findObjectByPropertyEqualTo(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.eq(propertyName, propertyValue))
                .setMaxResults(1)
                .uniqueResult();
    }

    protected List findByPropertyEqualTo(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.eq(propertyName, propertyValue))
                .list();
    }

    protected List findByProperties(Class classObj, String propertyName, Object[] propertyValue) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classObj);
        Criterion criterion = null;
        for (Object aPropertyValue : propertyValue) {
            if (criterion == null) criterion = Restrictions.eq(propertyName, aPropertyValue);
            else criterion = Restrictions.or(criterion, Restrictions.eq(propertyName, aPropertyValue));
        }
        return criteria.add(criterion).list();
    }

    protected List findByProperties(Class classObj, String propertyName, String[] propertyValue) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classObj);
        Criterion criterion = null;
        for (String aPropertyValue : propertyValue) {
            if (criterion == null) criterion = Restrictions.eq(propertyName, aPropertyValue);
            else criterion = Restrictions.or(criterion, Restrictions.eq(propertyName, aPropertyValue));
        }
        return criteria.add(criterion).list();
    }

    protected List findByProperties(Class classObj, String[] propertyName, String[] propertyValue) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classObj);
        for (int i = 0; i < propertyName.length; ++i) {
            criteria.add(Restrictions.eq(propertyName[i], propertyValue[i]));
        }
        return criteria.list();
    }

    protected Object findObjectByProperties(Class classObj, String[] propertyName, String[] propertyValue) {
        Criteria criteria = getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession()
                .createCriteria(classObj);

        Criterion firstCriterion = Restrictions.eq(propertyName[0], propertyValue[0]);
        List<Criterion> criterions = new ArrayList<Criterion>();
        for (int i = 1; i < propertyName.length; i++) {
            Criterion criterion = Restrictions.eq(propertyName[i], propertyValue[i]);
            criterions.add(criterion);
        }


        for (Criterion aCriterion : criterions) {
            firstCriterion = Restrictions.or(firstCriterion, aCriterion);
        }

        criteria.add(firstCriterion);
        return criteria.setMaxResults(1).uniqueResult();
    }

    protected List findByPropertyOfPropertyEqualTo(Class classObj, String property, String propertyOfProperty, Object value) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .createCriteria(property)
                .add(Restrictions.eq(propertyOfProperty, value))
                .list();
    }

    protected Object findObjectByPropertyOfPropertyEqualTo(Class classObj, String property, String propertyOfProperty, Object value) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .createCriteria(property)
                .add(Restrictions.eq(propertyOfProperty, value))
                .setMaxResults(1)
                .uniqueResult();
    }

    protected List findByPropertyEqualToIgnoreCase(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.eq(propertyName, propertyValue).ignoreCase())
                .list();
    }

    protected List findByPropertyLessThanOrEqualTo(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.le(propertyName, propertyValue))
                .list();
    }

    protected List findByPropertyGreaterThanOrEqualTo(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.ge(propertyName, propertyValue))
                .list();
    }

    protected List findByPropertyLessThan(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.lt(propertyName, propertyValue))
                .list();
    }

    protected List findByPropertyGreaterThan(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.gt(propertyName, propertyValue))
                .list();
    }

    protected List findByPropertyLikeAnyWhereMode(Class classObj, String propertyName, String propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.like(propertyName, propertyValue, MatchMode.ANYWHERE))
                .list();
    }

    protected List findByPropertyLikeAnyWhereModeIgnoreCase(Class classObj, String propertyName, String propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.like(propertyName, propertyValue, MatchMode.ANYWHERE).ignoreCase())
                .list();
    }

    protected void deleteAll(Collection coll) {
        getHibernateTemplate().deleteAll(coll);
        commit();
    }

    protected void commit() {
        getHibernateTemplate().getSessionFactory().getCurrentSession().flush();
    }

    protected Object findObjectByPropertyEqualToIgnoreCase(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.eq(propertyName, propertyValue).ignoreCase())
                .setMaxResults(1)
                .uniqueResult();
    }

    protected List findByProperties(Class classObj, String[] propertyName, Object[] propertyValue) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classObj);
        for (int i = 0; i < propertyName.length; ++i) {
            criteria.add(Restrictions.eq(propertyName[i], propertyValue[i]));
        }
        return criteria.list();
    }

    protected List findByPropertiesWithDescOrder(Class classObj, String[] propertyName, Object[] propertyValue, String propertyForOrder) {
        Criteria criteria = getHibernateTemplate().getSessionFactory().getCurrentSession().createCriteria(classObj);
        for (int i = 0; i < propertyName.length; ++i) {
            criteria.add(Restrictions.eq(propertyName[i], propertyValue[i]))
                    .addOrder(Order.desc(propertyForOrder));
        }
        return criteria.list();
    }

    protected List findByPropertyNotEqualTo(Class classObj, String propertyName, Object propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.ne(propertyName, propertyValue))
                .list();
    }

    protected List findByPropertyLikeExact(Class classObj, String propertyName, String propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.ilike(propertyName, propertyValue, MatchMode.EXACT))
                .list();
    }

    protected Object findByPropertyLikeExactModeIgnoreCase(Class classObj, String propertyName, String propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.like(propertyName, propertyValue, MatchMode.EXACT).ignoreCase())
                .setMaxResults(1)
                .uniqueResult();
    }

    protected Object findByPropertyLikeModeIgnoreCase(Class classObj, String propertyName, String propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.like(propertyName, propertyValue).ignoreCase())
                .setMaxResults(1)
                .uniqueResult();
    }

    protected List findByPropertyLikeAnyWhereModeIgnoreCasePartially(Class classObj, String propertyName, String propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .add(Restrictions.like(propertyName, "%" + propertyValue + "%", MatchMode.ANYWHERE).ignoreCase())
                .list();
    }

    protected List findByAssociatedProperty(Class classObj, String associatedProperty, String propertyName, Integer propertyValue) {
        return getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createCriteria(classObj)
                .createCriteria(associatedProperty)
                .add(Restrictions.eq(propertyName, propertyValue))
                .list();
    }

    protected Connection getConnection() throws SQLException {
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSession().getSessionFactory();
        ConnectionProvider cp = sfi.getConnectionProvider();
        return cp.getConnection();
    }

    protected Session getHibernateSession(){
        Session session = getHibernateTemplate()
                .getSessionFactory()
                .getCurrentSession();
        session.setFlushMode(FlushMode.AUTO);
        return session;
    }
/*

    protected void save(Object obj) throws DAOException{
        Session session = getHibernateSession();
        Transaction tx= session.beginTransaction();
        try {
            tx.begin();
            session.saveOrUpdate(obj);
            session.flush();
            tx.commit();
        }catch (Exception e){
            logger.error("Error :" + e.getMessage());
            tx.rollback();
            throw new DAOException(e.getCause(),e.getClass(),"DB Exception");
        }
    }

*/
    protected void reattachToSession(Object obj){
        Session session = getHibernateSession();

        try {
            session.merge(obj);
            session.refresh(obj, LockMode.NONE);

        }catch (HibernateException e){

        }

    }

    protected void freeResources() throws DAOException {
        try {
            if (resultSet != null)
                resultSet.close();

            if (statement != null)
                statement.close();

            if (preparedStatement != null)
                preparedStatement.close();

            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            logger.error("can not close connections. cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }

    }


}
