package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.model.Subscriber;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.service.exception.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 7, 2012
 * Time: 11:20:59 AM
 */
public interface ISubscriberService {

    public static String BEAN_NAME = "subscriberService";

    public void subscribe(Subscriber subscriber)  throws DAOException;

    public Subscriber findByEmail(String email) throws DAOException;
}
