package com.sefryek.doublepizza.service.implementation;

import java.util.*;

import com.sefryek.doublepizza.service.IUserService;
import com.sefryek.doublepizza.service.exception.ServiceException;
import com.sefryek.doublepizza.dao.UserDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.*;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 13, 2011
 * Time: 7:52:37 AM
 */

public class UserServiceImpl implements IUserService{

    private UserDAO userDAO;

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findByEmailAndPassword(String email, String password) throws DAOException, ServiceException {
        return userDAO.findByEmailAndPassword(email,password);
    }

    public User findByEmail(String email) throws DAOException, ServiceException {
        return userDAO.findByEmail(email);
    }

    public void save(User user) throws DAOException, ServiceException {
        userDAO.save(user);
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

    public String findStoreForUser(User user) throws DAOException{
        return userDAO.findStoreForUser(user);
    }

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo){
        return userDAO.findStreetNameByPostalCodeAndStreetNo(postalCode, streetNo);
    }

}