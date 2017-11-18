package com.sefryek.doublepizza.service;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Store;
import com.sefryek.doublepizza.service.exception.ServiceException;

import java.util.List;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 16, 2011
 * Time: 10:44:04 PM
 */
public interface IStore {

    public static String
            BEAN_NAME = "storeService";

    public List<Map<String,Object>> loadStoreMastTBL() throws DAOException, ServiceException;

    public List<Map<String,Object>> loadStoreHoursTBL() throws DAOException, ServiceException;

    public void fillStoreLocation(List<Store> StoreList) throws DAOException, ServiceException;

}