package com.sefryek.doublepizza.service;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.*;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;


/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 16, 2011
 * Time: 10:44:04 PM
 */
public interface IUserService {

    public static String BEAN_NAME = "userService";

    public User findByEmailAndPassword(String email,String password) throws DAOException, ServiceException;

    public User findByEmail(String email) throws DAOException, ServiceException;

    public void save(User user)  throws DataIntegrityViolationException, DAOException, ServiceException;

    public void deleteAll(List list);

    public void delete(User user);

    public List findAll();

    public User findById(Integer id);

    public User findUserByPropertyEqualTo(String propertyName, Object propertyValue);

    public void update(User user);
    
    public String findStoreForUser(User user) throws DAOException;

    public List<String> findStreetNameByPostalCodeAndStreetNo(String postalCode, String streetNo);

}