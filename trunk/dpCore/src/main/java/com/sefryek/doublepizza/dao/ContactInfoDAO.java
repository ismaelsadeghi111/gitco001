package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.CateringContactInfo;
import com.sefryek.doublepizza.model.ContactInfo;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateJdbcException;

import java.util.List;

/**
 * User: E_Ghasemi
 * Date: 1/27/14
 * Time: 5:14 PM
 */
public class ContactInfoDAO extends BaseDAO {

    public void save(ContactInfo contactInfo){
        Session session = getHibernateSession();
        Transaction tx= session.beginTransaction();
        try {
            tx.begin();
            session.save(contactInfo);
            session.flush();
            tx.commit();
        }catch (HibernateJdbcException e){
            tx.rollback();
        }

    }

    public void update(ContactInfo contactInfo){
        Session session = getHibernateSession();
        Transaction tx= session.beginTransaction();
        try {
            tx.begin();
            session.update(contactInfo);
            session.flush();
            tx.commit();
        }catch (HibernateJdbcException e){
            tx.rollback();
        }

        super.update(contactInfo);
    }

    public List<ContactInfo> getAll(int userId){
        Criteria criteria = getHibernateSession().createCriteria(ContactInfo.class).add(Restrictions.eq("userId",userId));
        return criteria.list();
    }

}
