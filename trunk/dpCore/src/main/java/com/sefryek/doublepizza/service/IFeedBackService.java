package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.FeedBack;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 16, 2011
 * Time: 10:44:04 PM
 */
public interface IFeedBackService {

    public static String BEAN_NAME = "feedBackService";

//    public FeedBack findByEmail(String email) throws DAOException, ServiceException;

    public void save(FeedBack feedBack)  throws DAOException, ServiceException;

    public void deleteAll(List list);

    public void delete(FeedBack feedBack);

    public List findAll();

    public FeedBack findById(Integer id);

    public void update(FeedBack feedBack);

}