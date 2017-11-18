package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.UserDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.User;
import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 13, 2011
 * Time: 7:52:37 AM
 */

public class UserServiceImpl implements IUserService{
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findByEmailAndPassword(String email, String password) throws DAOException, ServiceException {
        return userDAO.findByEmailAndPassword(email,password);
    }
    public User findByEmailAndIsRegistered(String email, Boolean isRegistered) throws DAOException, ServiceException {
        return userDAO.findByEmailAndIsRegistered(email,isRegistered);
    }

    public User findByEmail(String email) throws DAOException, ServiceException {
        return userDAO.findByEmail(email);
    }

    public void save(User user) throws DAOException, ServiceException {
        try{
            userDAO.save(user);
        }catch(DAOException e){
            logger.debug("error on saving user \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
            throw new DAOException(e.getCause(), e.getClass(), e.getMessage());
        }catch(Exception e){
            logger.debug("error on saving user \t message: \n" + e.getMessage() + " \n \t stack trace: \n" +
                    e.getStackTrace() + " \n \t cause: \n" + e.getCause());
        }
    }

    public void deleteAll(List list) {
        userDAO.deleteAll(list);
    }

    public void delete(User user) {
        userDAO.delete(user);
    }

    public List findAll() {
        return userDAO.findAll();
    }

    public User findById(Integer id) {
        return userDAO.findById(id);
    }

    public User findUserByPropertyEqualTo(String propertyName, Object propertyValue) {
        return userDAO.findUserByPropertyEqualTo(propertyName,propertyValue);
    }

    public void update(User user) {
        userDAO.update(user);
    }

    public String findStoreForUser(String postalCode,String streetNumber) throws DAOException{
        return userDAO.findStoreForUser(postalCode,streetNumber);
    }

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo){
        return userDAO.findStreetNameByPostalCodeAndStreetNo(postalCode, streetNo);
    }

    @Override
    public boolean isRegistered(String username) {
        return userDAO.isRegistered(username);
    }

    @Override
    public List<String> getAllUsersId() throws SQLException {
        return userDAO.getAllUsersId();
    }


}