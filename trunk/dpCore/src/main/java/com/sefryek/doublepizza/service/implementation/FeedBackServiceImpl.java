package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.FeedBackDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.FeedBack;
import com.sefryek.doublepizza.service.IFeedBackService;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 13, 2011
 * Time: 7:52:37 AM
 */

public class FeedBackServiceImpl implements IFeedBackService{

    private FeedBackDAO feedBackDAO;

    public void setFeedBackDAO(FeedBackDAO feedBackDAO) {
        this.feedBackDAO = feedBackDAO;
    }

    public FeedBack findByEmail(String email) throws DAOException, ServiceException {
        return feedBackDAO.findByEmail(email);
    }

    public void save(FeedBack feedBack) throws DAOException, ServiceException {
        feedBackDAO.save(feedBack);
    }

    public void deleteAll(List list) {
        feedBackDAO.deleteAll(list);
    }

    public void delete(FeedBack feedBack) {
        feedBackDAO.delete(feedBack);
    }

    public List findAll() {
        return feedBackDAO.findAll();
    }

    public FeedBack findById(Integer id) {
        return feedBackDAO.findById(id);
    }

    public FeedBack findFeedBackByPropertyEqualTo(String propertyName, Object propertyValue) {
        return feedBackDAO.findFeedBackByPropertyEqualTo(propertyName,propertyValue);
    }

    public void update(FeedBack feedBack) {
        feedBackDAO.update(feedBack);
    }
}