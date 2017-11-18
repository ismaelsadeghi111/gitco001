package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.User;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 12, 2011
 * Time: 2:35:29 AM
 */

public class UserDAO extends BaseDAO {

    private static Logger logger = Logger.getLogger(UserDAO.class);
    public static final String USEREMAIL = "email";
    public static final String IS_REGISTERED = "isRegistered";
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
            logger.error("Error : "+ e.getMessage());
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            return null;
        }
    }
    public User findByEmailAndIsRegistered(String userEmail, Boolean isRegistered) {
        try {

            return (User) getHibernateTemplate().getSessionFactory().getCurrentSession()
                    .createCriteria(User.class)
                    .add(Restrictions.eq(USEREMAIL, userEmail))
                    .add(Restrictions.eq(IS_REGISTERED,isRegistered))
                    .setMaxResults(1)
                    .uniqueResult();

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    public User findByEmail(String userEmail) {

        return (User) super.findObjectByPropertyEqualTo(User.class, USEREMAIL, userEmail);

    }

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
//    }

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

    public String findStoreForUser(String postalCode,String streetNumber) throws DAOException {
//    final String SQL_Find_Store_For_User =
//        "SELECT TOP 1 Store FROM Pcodes " +
//        "WHERE (POSTAL_CODE = :postalCode) AND " +
//        "(Street_from_No <= :streetNumber AND STREET_TO_NO >= :streetNumber) " +
//        "ORDER BY Store, Street_from_No DESC , STREET_TO_NO";


        try {
            Query query = getHibernateTemplate()
                    .getSessionFactory()
                    .getCurrentSession()
                    .createSQLQuery(SQL_Find_STORE_FOR_USER)
                    .addScalar("Store", Hibernate.STRING)
                    .setString("postalCode", postalCode.trim())
                    .setString("streetNumber", streetNumber.trim());
            Object result = query.uniqueResult();
            return (String) result;
        } catch (Exception e) {
            logger.debug("Debug: find store for user on DB has been failed.\n cause: " + e.getMessage());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }
    }

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo) {
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
        if (obj != null) {
            Object[] arrayObj = (Object[]) obj;
            streetName = (String) arrayObj[0];
            city = (String) arrayObj[1];
            cityAndStreet.add(streetName);
            cityAndStreet.add(city);
        }
        return cityAndStreet;

    }


    public boolean isRegistered(String username) {
        try {

            Criteria criteria = getHibernateTemplate()
                    .getSessionFactory()
                    .getCurrentSession()
                    .createCriteria(User.class);

            Criterion email = Restrictions.eq("email", username);
            Criterion isRegistered = Restrictions.eq("isRegistered", new Boolean(true));

            LogicalExpression expression = Restrictions.and(email, isRegistered);

            criteria.add(expression);
            List list = criteria.list();

            if (list.isEmpty()) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
//            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return true;
    }

   public List<String> getAllUsersId() throws SQLException {
        List<String> result = new ArrayList<>();
        try {
            connection = getConnection();
            String queryString = "select  distinct Id from web_user";
            preparedStatement = connection.prepareStatement(queryString);
            logger.debug("SQL Query: "+queryString);
            resultSet = preparedStatement.executeQuery();
            logger.debug("Result count: "+resultSet.getFetchSize());
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
       while (resultSet.next()) {
           result.add(resultSet.getString("Id"));
       }
        return result;
    }

}

