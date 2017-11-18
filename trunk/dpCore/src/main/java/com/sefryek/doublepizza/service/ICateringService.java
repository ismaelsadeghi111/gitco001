package com.sefryek.doublepizza.service;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Catering;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 1/21/14
 * Time: 11:20 AM
 * To change this template use File | Settings | File Templates.
 */

public interface ICateringService {

    public static String BEAN_NAME = "cateringService";
//    public  Catering getCatering() throws DAOException, ServiceException;
    public List<Catering> getAllCaterings() throws DAOException;
}
