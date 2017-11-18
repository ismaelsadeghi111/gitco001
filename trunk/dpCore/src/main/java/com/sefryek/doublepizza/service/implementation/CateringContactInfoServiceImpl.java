package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.CateringContactInfoDao;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.CateringContactInfo;

/**
 * Created by Hossein SadeghiFard on 1/26/14.
 */
public class CateringContactInfoServiceImpl {
    private CateringContactInfoDao cateringContactInfoDao;

    public CateringContactInfoDao getCateringContactInfoDao() {
        return cateringContactInfoDao;
    }

    public void setCateringContactInfoDao(CateringContactInfoDao cateringContactInfoDao) {
        this.cateringContactInfoDao = cateringContactInfoDao;
    }

    public void save(CateringContactInfo cateringContactInfo) throws DAOException {
        cateringContactInfoDao.save(cateringContactInfo);
    }
}
