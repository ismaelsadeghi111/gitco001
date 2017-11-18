package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.core.Constant;
import com.sefryek.doublepizza.dao.StoreDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Store;
import com.sefryek.doublepizza.service.IStore;
import com.sefryek.doublepizza.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: sepehr
 * Date: Sep 13, 2011
 * Time: 7:52:37 AM
 */
public class StoreServiceImpl implements IStore {

    private static Logger logger = Logger.getLogger(StoreServiceImpl.class);
    private StoreDAO storeDAO;

    public void setStoreDAO(StoreDAO storeDAO) {
        this.storeDAO = storeDAO;
    }

    public List<Map<String, Object>> loadStoreMastTBL() throws DAOException, ServiceException {
        return storeDAO.loadStoreMastTBL();
    }

    public List<Map<String, Object>> loadStoreHoursTBL() throws DAOException, ServiceException {
        return storeDAO.loadStoreHoursTBL();
    }

    public void fillStoreLocation(List<Store> storeList) throws DAOException, ServiceException {
        List<Map<String,Object>> locationList = storeDAO.loadPcodsTBL();
        for(Store store:storeList){
            for(Map<String,Object> pcode:locationList){
                if(store.getPostalCode().equals(pcode.get(Constant.PCODES_POSTAL_CODE).toString())){
                    store.setLatitude((Double)pcode.get(Constant.PCODES_LATITUDE));
                    store.setLongitude((Double)pcode.get(Constant.PCODES_LONGITUDE));
                    break;
                }
            }
        }
    }
    
}