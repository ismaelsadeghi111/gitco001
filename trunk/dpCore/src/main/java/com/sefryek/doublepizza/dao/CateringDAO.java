package com.sefryek.doublepizza.dao;

import com.sefryek.doublepizza.dao.exception.DAOException;
import com.sefryek.doublepizza.model.Catering;
import com.sefryek.doublepizza.model.CateringOrder;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mostafa Jamshid
 * Date: 1/21/14
 * Time: 9:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CateringDAO extends BaseDAO {
    private static Logger logger = Logger.getLogger(CateringDAO.class);


    public void save(Object obj) throws DAOException{
        try {
            super.save(obj);
        } catch (DAOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    public List<Catering> getAllCaterings() throws DAOException {
        Criteria criteria = getSession().createCriteria(Catering.class);
        return criteria.list();


    }

}

