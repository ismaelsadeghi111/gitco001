package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.PopularDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Popular;
import com.sefryek.doublepizza.service.IPopularService;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.text.ParseException;
import java.util.List;

/**
 * User: E_Ghasemi
 * Date: 12/13/13
 * Time: 10:00 AM
 */
public class PopularServiceImpl implements IPopularService {

    PopularDAO popularDAO;

    public PopularDAO getPopularDAO() {
        return popularDAO;
    }

    public void setPopularDAO(PopularDAO popularDAO) {
        this.popularDAO = popularDAO;
    }

    @Override
    public List<Popular> getActivePopulars() throws DAOException, ServiceException {
        return popularDAO.getPopulars();
    }
    public List<Popular> getPopularsByDate(String fromDate,String toDate) throws DAOException, ServiceException {
        return popularDAO.getPopularsByDate( toDate,fromDate);
    }

    @Override
    public void savePopular(Popular popular,Integer disabledPopularId ) throws DAOException, ParseException {
        popularDAO.disablePreviousPopulars(disabledPopularId);
        popularDAO.savePopular(popular);
    }


}
