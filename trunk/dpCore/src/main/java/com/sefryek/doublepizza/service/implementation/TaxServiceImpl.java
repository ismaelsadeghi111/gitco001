package com.sefryek.doublepizza.service.implementation;

import com.sefryek.doublepizza.dao.TaxDAO;
import com.sefryek.doublepizza.service.ITaxService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 3, 2012
 * Time: 4:21:50 PM
 */
public class TaxServiceImpl implements ITaxService{
    private TaxDAO taxDAO;
    public void setTaxDAO(TaxDAO taxDAO) {
        this.taxDAO = taxDAO;
    }

    public List findAll() {
        return taxDAO.findAll();
    }
}
