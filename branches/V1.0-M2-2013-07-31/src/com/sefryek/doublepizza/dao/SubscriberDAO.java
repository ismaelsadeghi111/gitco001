package com.sefryek.doublepizza.dao;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.Criteria;
import com.sefryek.doublepizza.model.Subscriber;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.core.Constant;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 7, 2012
 * Time: 10:58:52 AM
 */
public class SubscriberDAO extends BaseDAO{

    private static Logger logger = Logger.getLogger(UserDAO.class);

    public void subscribe(Subscriber subscriber) throws DAOException {
        try {
            super.save(subscriber);

        } catch (DataIntegrityViolationException e) {
//             e.printStackTrace();
//            throw  e;
            throw new DAOException(e.getCause(), e.getClass(), "Duplication subscriber");
        } catch (Exception e) {
            logger.debug("Debug: save subscriber on DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }
    

    public Subscriber findByEmail(String email) throws DAOException{
        try {

            return (Subscriber)super.findObjectByPropertyEqualTo(Subscriber.class, "email", email);            

//            Criteria criteria = getHibernateTemplate()
//                    .getSessionFactory()
//                    .getCurrentSession()
//                    .createCriteria(Subscriber.class)
//                    .add(Restrictions.eq("email", email));
//
//            List list = criteria.list();
//
//            if (!list.isEmpty()) {
//                return (Subscriber) list.get(0);
//            } else {
//                return null;
//            }


        } catch (DataIntegrityViolationException e) {
            throw new DAOException(e.getCause(), e.getClass(), "Duplication subscriber");
        } catch (Exception e) {
            logger.debug("Debug: find subscriber on DB by email has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }
}
