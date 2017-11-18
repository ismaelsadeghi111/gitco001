package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.User;

import java.util.*;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.LogicalExpression;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 12, 2011
 * Time: 2:35:29 AM
 */

public class UserDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(UserDAO.class);
    public static final String USEREMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String ID = "Id";
    private static final String SQL_Find_STORE_FOR_USER =
        "SELECT TOP 1 Store FROM Pcodes " +
        "WHERE (POSTAL_CODE = :postalCode) AND " +
        "(Street_from_No <= :streetNumber AND STREET_TO_NO >= :streetNumber) " +
        "ORDER BY Store, Street_from_No DESC , STREET_TO_NO";

    private static final String SQL_Find_StreetName_AND_City =
            "SELECT TOP 1 STREET_NAME, CITY FROM Pcodes " +
            "WHERE (POSTAL_CODE = :postalCode AND " +
            "STREET_FROM_NO <= :streetNo AND STREET_TO_NO >= :streetNo) ";

    public void save(User user) throws DAOException {
        try {
            super.save(user);

        } catch (DataIntegrityViolationException e) {
            throw new DAOException(e.getCause(), e.getClass(), "Duplication User");

        } catch (Exception e) {
            logger.debug("Debug: save user on DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }

    }

    public void update(User user) {
        super.update(user);
    }

    public void delete(User user) {
        super.delete(user);
    }

    public void deleteAll(List list) {
        super.deleteAll(list);
    }

    public List findAll() {
        return super.findAll(User.class);
    }

    public User findById(Integer id) {
        try {
            return (User) super.findById(User.class, id);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error( e.getMessage() );
            return null;
        }
    }

    public User findByEmailAndPassword(String username, String password) {
        try {

            Criteria criteria = getHibernateTemplate()
                    .getSessionFactory()
                    .getCurrentSession()
                    .createCriteria(User.class);

            Criterion email = Restrictions.eq("email", username);
            Criterion pass = Restrictions.eq("password", password);

            LogicalExpression expression = Restrictions.and(email, pass);

            criteria.add(expression);
            List list = criteria.list();

            if (!list.isEmpty()) {
                return (User) list.get(0);
            } else {
                return null;
            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error( e.getMessage() );
            return null;
        }
    }

    public User findByEmail(String username) {
        return (User)super.findObjectByPropertyEqualTo(User.class, USEREMAIL, username);




//        try {
//            Criteria criteria = getHibernateTemplate()
//                    .getSessionFactory()
//                    .getCurrentSession()
//                    .createCriteria(User.class);
//
//            criteria.add(Restrictions.eq(USEREMAIL, username));
//
//            List list = criteria.list();
//
//            if (!list.isEmpty()) {
//                return (User) list.get(0);
//            } else {
//                return null;
//            }
//
//        } catch (Exception e) {
////            e.printStackTrace();
//            logger.error( e.getMessage() );
//            return null;
//        }
    }

//    public User findByPassword(String password){
//        try{
//
//            Criteria criteria =getHibernateTemplate()
//                    .getSessionFactory()
//                    .getCurrentSession()
//                    .createCriteria(User.class);
//
//            criteria.add( Restrictions.eq(PASSWORD, password));
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

    public User findUserByPropertyEqualTo(String propertyName, Object propertyValue) {
        return (User) super.findObjectByPropertyEqualTo(User.class, propertyName, propertyValue);
    }

    public String findStoreForUser(User user) throws DAOException{
//    final String SQL_Find_Store_For_User =
//        "SELECT TOP 1 Store FROM Pcodes " +
//        "WHERE (POSTAL_CODE = :postalCode) AND " +
//        "(Street_from_No <= :streetNumber AND STREET_TO_NO >= :streetNumber) " +
//        "ORDER BY Store, Street_from_No DESC , STREET_TO_NO";


        try{
            Query query = getHibernateTemplate()
                    .getSessionFactory()
                    .getCurrentSession()
                    .createSQLQuery(SQL_Find_STORE_FOR_USER)
                    .addScalar("Store", Hibernate.STRING)
                    .setString("postalCode", user.getPostalCode())
                    .setString("streetNumber", user.getStreetNo());
            Object result = query.uniqueResult();
            return (String)result;
        } catch (Exception e) {
            logger.debug("Debug: find store for user on DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo){
        Query query = getHibernateTemplate().getSessionFactory().getCurrentSession()
                .createSQLQuery(SQL_Find_StreetName_AND_City)
                .addScalar("STREET_NAME", Hibernate.STRING)
                .addScalar("CITY", Hibernate.STRING)
                .setString("streetNo", streetNo)
                .setString("postalCode", postalCode);




        Object obj = query.uniqueResult();
        List<String> cityAndStreet = new ArrayList<String>();
        String streetName = null;
        String city = null;
        if (obj != null){
            Object[] arrayObj = (Object[])obj;
            streetName = (String)arrayObj[0];
            city = (String)arrayObj[1];
            cityAndStreet.add(streetName);
            cityAndStreet.add(city);
        }


        return cityAndStreet;

    }





}