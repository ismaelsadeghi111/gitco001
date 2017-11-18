package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.model.FeedBack;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.LogicalExpression;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 12, 2011
 * Time: 2:35:29 AM
 */

public class FeedBackDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(FeedBackDAO.class);
    public static final String EMAIL = "email";
    public static final String STORENUMBER = "storeNumber";
    public static final String ID = "id";

    public void save(FeedBack feedBack)  throws DAOException{

        try{
            super.save(feedBack);
        }catch (Exception e){
            logger.debug("Debug: save feedback into DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }

    }

    public void update(FeedBack feedBack) {
        super.update(feedBack);
    }

    public void delete(FeedBack feedBack) {
        super.delete(feedBack);
    }

    public void deleteAll(List list) {
        super.deleteAll(list);
    }

    public List findAll() {
        return super.findAll(FeedBack.class);
    }

    public FeedBack findById(Integer id) {
        try{
            return (FeedBack)super.findById(FeedBack.class, id);
        }catch (Exception e){
            return null;
        }
    }

    public FeedBack findByEmail(String email){

        try{
            Criteria criteria =getHibernateTemplate()
                    .getSessionFactory()
                    .getCurrentSession()
                    .createCriteria(FeedBack.class);

            criteria.add(Restrictions.eq(EMAIL,email));
            List list = criteria.list();

            if(!list.isEmpty()){
                return (FeedBack)list.get(0);
            }else{
                return null;
            }

        }catch (Exception e){
//            e.printStackTrace();
            logger.error( e.getMessage() );
            return null;
        }
    }

//    public User findByEmail(String username){
//        try{
//            Criteria criteria =getHibernateTemplate()
//                    .getSessionFactory()
//                    .getCurrentSession()
//                    .createCriteria(User.class);
//
//            criteria.add( Restrictions.eq(USEREMAIL, username));
//
//            List list = criteria.list();
//
//            if(!list.isEmpty()){
//                return (User)list.get(0);
//            }else{
//                return null;
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }

    public FeedBack findFeedBackByPropertyEqualTo(String propertyName, Object propertyValue){
        return (FeedBack)super.findObjectByPropertyEqualTo(FeedBack.class, propertyName, propertyValue);
    }

}