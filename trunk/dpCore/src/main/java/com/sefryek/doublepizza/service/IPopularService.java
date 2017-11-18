package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Popular;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.text.ParseException;
import java.util.List;

/**
 * User: E_Ghasemi
 * Date: 12/13/13
 * Time: 9:56 AM
 */
public interface IPopularService {

    public static String BEAN_NAME = "popularService";
    public List<Popular> getActivePopulars() throws DAOException, ServiceException;
    public List<Popular> getPopularsByDate(String fromDate,String toDate)    throws DAOException, ServiceException;
    public void savePopular(Popular popular ,Integer disabledPopularId)  throws DAOException, ParseException ;

}
