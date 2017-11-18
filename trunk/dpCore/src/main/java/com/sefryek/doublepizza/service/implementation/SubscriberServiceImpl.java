package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.SubscriberDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Subscriber;
import com.sefryek.doublepizza.service.ISubscriberService;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 7, 2012
 * Time: 11:23:23 AM
 */
public class SubscriberServiceImpl implements ISubscriberService {

    private SubscriberDAO subscriberDAO;

    public void setSubscriberDAO(SubscriberDAO subscriberDAO) {
        this.subscriberDAO = subscriberDAO;

    }


    public void subscribe(Subscriber subscriber) throws DAOException{
        subscriberDAO.subscribe(subscriber);

    }

    public Subscriber findByEmail(String email) throws DAOException{
        return subscriberDAO.findByEmail(email);
    }
}
