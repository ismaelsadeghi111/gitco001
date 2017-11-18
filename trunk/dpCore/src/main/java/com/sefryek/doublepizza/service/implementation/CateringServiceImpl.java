package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.CateringDAO;
import com.sefryek.doublepizza.dao.DollarScheduleDAO;
import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Catering;
import com.sefryek.doublepizza.model.CateringOrder;
import com.sefryek.doublepizza.service.ICateringService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 1/21/14
 * Time: 11:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class CateringServiceImpl implements ICateringService {
   private CateringDAO cateringDAO;



    public CateringDAO getCateringDAO() {
        return cateringDAO;
    }

    public void setCateringDAO(CateringDAO cateringDAO) {
        this.cateringDAO = cateringDAO;
    }

    public List<Catering>  getAllCaterings() throws DAOException {

        return cateringDAO.getAllCaterings();
    }

    public void save(Object obj) throws DAOException {
        cateringDAO.save(obj);
    }
}
