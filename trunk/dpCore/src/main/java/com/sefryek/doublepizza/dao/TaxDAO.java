package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.model.Tax;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: ehsan
 * Date: Apr 3, 2012
 * Time: 4:05:52 PM
 */
public class TaxDAO extends BaseDAO{
    private static Logger logger = Logger.getLogger(UserDAO.class);


    public List<Tax> findAll() {
        return (List<Tax>)super.findAll(Tax.class);
    }
}
